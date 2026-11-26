/*
 * Social World
 * Copyright (C) 2026  Mathias Sikos
 * Written with the assistance of Claude (Anthropic), 2026
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://gnu.org>.
 */
package org.socialworld.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.socialworld.GlobalSwitches;
import org.socialworld.actions.handle.Inventory;
import org.socialworld.actions.handle.InventoryPlace;
import org.socialworld.actions.handle.StartEquipment;
import org.socialworld.attributes.ActualTime;
import org.socialworld.attributes.Position;
import org.socialworld.attributes.properties.Material;
import org.socialworld.attributes.properties.MaterialSet;
import org.socialworld.calculation.application.InventoryCalculator;
import org.socialworld.collections.ObjectByPositionSearch;
import org.socialworld.datasource.createObjects.BodyBuildCatalog;
import org.socialworld.datasource.createObjects.CompositionAssignment;
import org.socialworld.datasource.createObjects.StartEquipmentCatalog;
import org.socialworld.datasource.tablesSimulation.propertySets.TableKindMaterialSet;
import org.socialworld.datasource.tablesSimulation.propertySets.TableMaterialSet;
import org.socialworld.datasource.tablesSimulation.states.CacheTableStateComposition;
import org.socialworld.datasource.tablesSimulation.states.TableStateComposition;
import org.socialworld.objects.Human;
import org.socialworld.objects.Item;
import org.socialworld.objects.NoSimulationObject;
import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.SimulationObject_Type;
import org.socialworld.objects.access.HiddenHuman;
import org.socialworld.objects.access.HiddenItem;
import org.socialworld.objects.access.HiddenSimulationObject;
import org.socialworld.objects.concrete.StateComposition;
import org.socialworld.objects.enums.EnumSide;
import org.socialworld.objects.properties.IContainer;

/**
 * The creation of the simulation objects at the start (2026-11-25, taken out of Simulation, which keeps a reference
 * only until the creation is over): the humans, their start equipment and the apples, and the composition of every
 * new instance, derived from swkind_materialset and saved into swstate_composition (body build concept 9).
 */
final class StartCreation {

	private static final AccessTokenCore tokenCore = AccessTokenCore.getValid();

	private final ObjectMaster objectMaster;
	private final ObjectByPositionSearch searchByPosition;

	// body build concept B3 (2026-11-25): swstate_composition was empty at the start, so every new instance gets
	// its set derived from swkind_materialset, and the rows (objectID, setID) are saved after the creation
	private final List<int[]> compositionRows = new ArrayList<int[]>();
	private int compositionsWithoutSet = 0;
	private int compositionsWithoutMaterial = 0;	// a piece asked for a material that no variant has (step 2)
	private int lastDerivedSetID = CompositionAssignment.NO_SET;	// for the right piece of a pair

	StartCreation(ObjectMaster objectMaster, ObjectByPositionSearch searchByPosition) {
		this.objectMaster = objectMaster;
		this.searchByPosition = searchByPosition;
	}

	/**
	 * Since 2026-11-26 the start empties swstate_composition itself (before, the start was refused and the table had
	 * to be emptied by hand in the PropertySetCreationTool). Loading the states is not ready yet, so every run
	 * derives the composition anew. The table is emptied directly, before the cache of the states is read.
	 *
	 * @return the rows that were in swstate_composition, 0 if it was empty already
	 */
	int clearCompositionTable() {
		TableStateComposition table = new TableStateComposition();
		table.select(1, "", "");
		int rowsBefore = table.rowCount();
		if (rowsBefore > 0) table.clear();
		return rowsBefore;
	}

	/**
	 * Body build concept 9.1 (2026-11-25): the composition is the first state the simulation saves itself. Filled
	 * means loading, which is not promising yet. Since 2026-11-26 it is asked after clearCompositionTable(): more
	 * than 0 means the cache had been read before the table was emptied.
	 *
	 * @return the rows in the cache of swstate_composition; more than 0 means the start is refused
	 */
	int rowsInCompositionTable() {
		return CacheTableStateComposition.getInstance().rowCount();
	}

	/** Creates the humans, their start equipment and the apples, and saves the composition of every instance. */
	void createObjects() {
		IncompleteSimulationObject incompleteObject;

		HiddenHuman myHiddenHuman;
		Human myHuman;

		HiddenItem myHiddenItem;
		Item myItem;

		List<HiddenHuman> hiddenHumans= new ArrayList<HiddenHuman>();
		List<Human> humans= new ArrayList<Human>();
		List<Integer> characterNumbers = new ArrayList<Integer>();	// from the creation, for the start equipment

		System.out.println("Start Erstellen Objekte "+ ActualTime.asTime().toString());

		for (int i = 0; i < 100; i++ ) {
			incompleteObject = createSimulationObject(SimulationObject_Type.human, "org.socialworld.objects.Human");

			myHiddenHuman = (HiddenHuman) incompleteObject.getHiddenObject();
			myHuman = (Human) incompleteObject.getObject();

			hiddenHumans.add(myHiddenHuman);
			humans.add(myHuman);
			characterNumbers.add(incompleteObject.getCharacterNumber());

			if (GlobalSwitches.OUTPUT_CREATE_OBJECT_HUMANS) System.out.println("Human(" + myHuman.getObjectID() + "): " + myHuman.getPosition(tokenCore).toString() + " " +  ActualTime.asTime().toString());
		}

		System.out.println("Start Erstellen Objekte Inventar "+ ActualTime.asTime().toString());

		// The start equipment of the characters (inventory concept V3, 2026-11-23): clothes and items from
		// equipment.csv; they are carried by their human (connection carried) and draw no start position.
		StartEquipmentCatalog catalog = StartEquipmentCatalog.getInstance();
		for (String problem : catalog.getProblems()) System.err.println("StartEquipmentCatalog: " + problem);
		int[] counts = new int[4];	// placed, put down at the feet, liquids left for V5, breads (D7)
		for (int i = 0; i < humans.size(); i++ ) {
			Inventory inventory = new Inventory(SimulationObject_Type.human);
			hiddenHumans.get(i).setInventory(inventory);
			equip(humans.get(i), hiddenHumans.get(i), inventory, catalog.get(characterNumbers.get(i)), counts);
		}
		System.out.println("Start equipment: " + counts[0] + " items placed, " + counts[1] + " put down at the feet, "
				+ counts[2] + " liquids without container, " + counts[3] + " breads for those without food");

		System.out.println("Start Erstellen Aepfel "+ ActualTime.asTime().toString());

		for (int i = 0; i < 75; i++ ) {
			incompleteObject = createSimulationObject(SimulationObject_Type.item, "org.socialworld.objects.concrete.eatable.fruits.Apple");
			myHiddenItem = (HiddenItem) incompleteObject.getHiddenObject();
			myItem = (Item) incompleteObject.getObject();
			if (GlobalSwitches.OUTPUT_CREATE_OBJECT) System.out.println("Apple(" + myItem.getObjectID() + "):" + myItem.getPosition(tokenCore).toString());
		}

		System.out.println("Ende Erstellen Objekte "+ ActualTime.asTime().toString());

		saveCompositions();
	}

	/**
	 * Equips one human from its entry of the start equipment: first the clothes (more clothes before the kit, so
	 * they take the place), then the items (the first food into the mouth), then a bread into an empty mouth (D7).
	 * A piece is only created when it has a place, except an item without place, which is put down at the feet.
	 * The pieces are put on their places and into the containers by the rail inventory (InventoryCalculator, with
	 * the token core, protection concept S2, E7); the inventory itself is only read here, for the decisions.
	 * The right piece of a pair takes the set of the left one, which the catalog gives right before it.
	 */
	private void equip(Human human, HiddenHuman hiddenHuman, Inventory inventory, StartEquipmentCatalog.Entry entry, int[] counts) {
		InventoryCalculator rail = InventoryCalculator.getInstance();
		Position at = human.getPosition(tokenCore);
		if (entry != null) {
			int leftSetID = CompositionAssignment.NO_SET;	// the set of the left piece of the pair just made
			for (StartEquipmentCatalog.Piece piece : entry.clothes) {
				SimulationObject prototype = prototype(piece.className);
				InventoryPlace place = StartEquipment.chooseWornPlace(inventory, prototype, sideOf(piece.side));
				if (place == InventoryPlace.noWhere) {	// the place is taken by the more clothes
					leftSetID = CompositionAssignment.NO_SET;
					continue;
				}
				int partner = (piece.side == StartEquipmentCatalog.Side.right) ? leftSetID : CompositionAssignment.NO_SET;
				Item item = createCarried(piece.className, piece.side, at, piece.material, partner);
				leftSetID = (piece.side == StartEquipmentCatalog.Side.left) ? lastDerivedSetID : CompositionAssignment.NO_SET;
				if (item != null && rail.startPutToInventory(tokenCore, hiddenHuman, place, item)) counts[0]++;
			}
			// containers and what they hold so far (inventory concept V5)
			List<SimulationObject> containers = new ArrayList<SimulationObject>();
			Map<SimulationObject, List<SimulationObject>> contents = new HashMap<SimulationObject, List<SimulationObject>>();
			for (String className : entry.items) {
				SimulationObject prototype = prototype(className);
				StartEquipment.Decision decision = StartEquipment.decide(inventory, containers, contents, prototype);
				Item item = createCarried(className, StartEquipmentCatalog.Side.none, at, Material.nothing, CompositionAssignment.NO_SET);
				if (item == null) continue;
				if (decision.isInContainer()) {
					if (rail.startPutIntoContainer(tokenCore, decision.container, item)) {
						contents.get(decision.container).add(item);
						counts[0]++;
					}
					else counts[1]++;
				}
				else if (!decision.isNowhere() && rail.startPutToInventory(tokenCore, hiddenHuman, decision.place, item)) counts[0]++;
				else counts[1]++;	// it stays where it was created: at the feet of the human
				if (item instanceof IContainer) {
					containers.add(item);
					contents.put(item, new ArrayList<SimulationObject>());
				}
			}
			// the liquids into their containers (a jug of beer, a waterskin of water)
			for (String className : entry.contents) {
				SimulationObject prototype = prototype(className);
				StartEquipment.Decision decision = StartEquipment.decide(inventory, containers, contents, prototype);
				if (!decision.isInContainer()) { counts[2]++; continue; }
				Item liquid = createCarried(className, StartEquipmentCatalog.Side.none, at, Material.nothing, CompositionAssignment.NO_SET);
				if (liquid != null && rail.startPutIntoContainer(tokenCore, decision.container, liquid)) {
					contents.get(decision.container).add(liquid);
					counts[0]++;
				}
				else counts[2]++;
			}
		}
		if (inventory.isFree(InventoryPlace.mouth)) {
			Item bread = createCarried("org.socialworld.objects.concrete.eatable.bakery.Bread", StartEquipmentCatalog.Side.none, at,
					Material.nothing, CompositionAssignment.NO_SET);
			if (bread != null && rail.startPutToInventory(tokenCore, hiddenHuman, InventoryPlace.mouth, bread)) counts[3]++;
		}
	}

	/** An object of the class without state (and so without side), only to see where it fits (or nothing). */
	private static SimulationObject prototype(String className) {
		try {
			return (SimulationObject) Class.forName(className).getDeclaredConstructor().newInstance();
		} catch (ReflectiveOperationException | ClassCastException e) {
			return NoSimulationObject.getObjectNothing();
		}
	}

	private static EnumSide sideOf(StartEquipmentCatalog.Side side) {
		if (side == StartEquipmentCatalog.Side.left) return EnumSide.left;
		if (side == StartEquipmentCatalog.Side.right) return EnumSide.right;
		return EnumSide.none;
	}

	/**
	 * Creates an item at the position of its carrier, without drawing a start position. The side goes into the
	 * item's state through its hidden object, once at the creation (protection concept S3).
	 */
	private Item createCarried(String className, StartEquipmentCatalog.Side side, Position at, Material material, int partnerSetID) {
		IncompleteSimulationObject incomplete = createSimulationObject(SimulationObject_Type.item, className, at, material, partnerSetID);
		SimulationObject object = incomplete.getObject();
		if (!(object instanceof Item)) return null;
		EnumSide itemSide = sideOf(side);
		if (itemSide != EnumSide.none) {
			HiddenSimulationObject hidden = incomplete.getHiddenObject();
			if (hidden instanceof HiddenItem) ((HiddenItem) hidden).setSide(itemSide);
		}
		if (GlobalSwitches.OUTPUT_CREATE_OBJECT) System.out.println("Equipment " + object.getClass().getSimpleName() + "(" + object.getObjectID() + ")");
		return (Item) object;
	}

	private IncompleteSimulationObject createSimulationObject(SimulationObject_Type simulationObjectType, String fullClassName) {
		return createSimulationObject(simulationObjectType, fullClassName, null, Material.nothing, CompositionAssignment.NO_SET);
	}

	/**
	 * @param material the main material asked for (start equipment, item class or composition step 2),
	 *                 Material.nothing for none
	 * @param partnerSetID the set of the other piece of the pair, CompositionAssignment.NO_SET for none
	 */
	private IncompleteSimulationObject createSimulationObject(SimulationObject_Type simulationObjectType,
			String fullClassName, Position startPosition, Material material, int partnerSetID) {

		int incompleteObjectIndex;
		int objectID;

		IncompleteSimulationObject incompleteObject = new IncompleteSimulationObject();
		SimulationObject createdObject = NoSimulationObject.getObjectNothing() ;

		if (GlobalSwitches.OUTPUT_CREATE_OBJECT_DETAILS) System.out.println("Erstellen SimObj > this.objectMaster.createSimulationObject: " + ActualTime.asTime().toString());
		incompleteObjectIndex = (startPosition == null)
				? this.objectMaster.createSimulationObject(simulationObjectType, fullClassName)
				: this.objectMaster.createSimulationObject(simulationObjectType, fullClassName, startPosition);
		if (incompleteObjectIndex >= 0) {
			objectID = this.objectMaster.getObjectIDForIncompleteObjectIndex(incompleteObjectIndex);
			if (objectID > 0) {
				incompleteObject = this.objectMaster.getIncompleteObject(incompleteObjectIndex, objectID);
				createdObject = incompleteObject.getObject();
				if (createdObject.isSimulationObject()) {
					deriveComposition(incompleteObject, simulationObjectType, fullClassName, material, partnerSetID);
					this.searchByPosition.addObject(createdObject);
				}
			}
		}

		return incompleteObject;
	}

	/**
	 * The composition of a new instance (body build concept B3, 2026-11-25): the set of its template in
	 * swkind_materialset, variant objectID % number of variants; a human takes the template of the body build of
	 * its character. The set goes into the state at once, the row is saved after the creation (saveCompositions).
	 * A piece of the start equipment may ask for a material (equipment.csv Apron[leather], item class or composition
	 * step 2): then only the variants with that main material count. The right piece of a pair takes the set of
	 * the left one.
	 */
	private void deriveComposition(IncompleteSimulationObject incompleteObject, SimulationObject_Type type,
			String fullClassName, Material material, int partnerSetID) {
		int objectID = incompleteObject.getObject().getObjectID();
		boolean isHuman = (type == SimulationObject_Type.human);
		String template = CompositionAssignment.templateName(CompositionAssignment.simpleName(fullClassName), isHuman,
				isHuman ? BodyBuildCatalog.getInstance().get(incompleteObject.getCharacterNumber()) : null);
		int[] variants = TableKindMaterialSet.getInstance().getMaterialSetIDs(template);
		int setID = CompositionAssignment.setID(variants, objectID, material, StartCreation::mainMaterialOf, partnerSetID);
		lastDerivedSetID = setID;
		if (setID == CompositionAssignment.NO_SET) {
			if (variants.length > 0) compositionsWithoutMaterial++;
			else compositionsWithoutSet++;
			return;
		}
		incompleteObject.getHiddenObject().setSomething(StateComposition.class.getName(),
				StateComposition.METHODNAME_SET_MATERIAL_SET, TableMaterialSet.getInstance().getMaterialSet(setID));
		compositionRows.add(new int[] {objectID, setID});
	}

	/** @return the main material (largest share) of a material set, Material.nothing if it has none */
	private static Material mainMaterialOf(int setID) {
		MaterialSet set = TableMaterialSet.getInstance().getMaterialSet(setID);
		Object main = set.getMain();
		return (main instanceof Material) ? (Material) main : Material.nothing;
	}

	/** Writes the derived compositions into swstate_composition, one row per instance, and says how many arrived. */
	private void saveCompositions() {
		long start = System.currentTimeMillis();
		TableStateComposition table = new TableStateComposition();
		for (int[] row : compositionRows) table.insert(row[0], row[1]);
		table.select(1, "", "");
		int saved = table.rowCount();
		System.out.println("Composition: derived from swkind_materialset and saved, " + saved + " of " + compositionRows.size()
				+ " rows in swstate_composition, " + compositionsWithoutSet + " objects without template, "
				+ compositionsWithoutMaterial + " without a variant of the material asked for ("
				+ (System.currentTimeMillis() - start) / 1000 + " s)");
		if (saved < compositionRows.size()) {
			System.out.println("Composition: WARNING, rows are missing (no row in sw_object for the ID? press FillObject)");
		}
	}
}
