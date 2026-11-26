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
package org.socialworld.visualize;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.socialworld.actions.handle.Inventory;
import org.socialworld.actions.handle.InventoryPlace;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.core.IAccessToken;
import org.socialworld.datasource.mariaDB.DatabaseAssumptions;
import org.socialworld.objects.Human;
import org.socialworld.objects.SimulationObject_Type;
import org.socialworld.objects.StateHuman;
import org.socialworld.objects.StateItem;
import org.socialworld.objects.WriteAccessToHuman;
import org.socialworld.objects.WriteAccessToItem;
import org.socialworld.objects.access.GrantedAccessToProperty;
import org.socialworld.objects.access.HiddenHuman;
import org.socialworld.objects.access.HiddenSimulationObject;
import org.socialworld.attributes.properties.Material;
import org.socialworld.attributes.properties.MaterialSet;
import org.socialworld.objects.concrete.StateComposition;
import org.socialworld.objects.concrete.clothes.overgarments.ChainMail;
import org.socialworld.objects.concrete.containers.Purse;
import org.socialworld.objects.concrete.eatable.bakery.Bread;
import org.socialworld.objects.concrete.tools.Knife;
import org.socialworld.objects.concrete.valuables.Coins;

/**
 * The data of the inventory window (2026-11-24): what is shown per place, the counts and the contents of the
 * containers. read() goes through the token of the display (cluster visualize), which may read the places and
 * the contents since the inventory window. Since 2026-11-26 the tooltip of a slot shows the properties of the item
 * as well: material shares (read with the same token), value, protection and burning.
 */
class InventoryViewTest {

	private static final IAccessToken INVENTORY = new IAccessToken() {
		public boolean isValid() { return true; }
		public SimulationCluster getSimulationCluster() { return SimulationCluster.inventory; }
	};

	private static <T extends org.socialworld.objects.Item> T withState(T item) {
		new WriteAccessToItem(item, new StateItem());
		return item;
	}

	/** A state and a material set, as StartCreation gives them to a new instance. */
	private static <T extends org.socialworld.objects.Item> T withMaterials(T item, Object... materialsAndShares) {
		WriteAccessToItem wa = new WriteAccessToItem(item, new StateItem());
		MaterialSet set = new MaterialSet();
		for (int i = 0; i < materialsAndShares.length; i += 2)
			set.add((Material) materialsAndShares[i], (Integer) materialsAndShares[i + 1]);
		new HiddenSimulationObject(wa, new GrantedAccessToProperty[] {GrantedAccessToProperty.all})
				.setSomething(StateComposition.class.getName(), StateComposition.METHODNAME_SET_MATERIAL_SET, set);
		return item;
	}

	@Test
	void withoutHuman_aMessage_andNothingOccupied() {
		assertThat(InventoryView.none().getMessage()).isEqualTo("kein Mensch ausgew\u00E4hlt");
		assertThat(InventoryView.none().title()).isEqualTo("Inventar");
		assertThat(InventoryView.missing(7).summary()).isEqualTo("Mensch 7 gibt es nicht");
		assertThat(InventoryView.missing(7).countOccupied()).isZero();
	}

	@Test
	void aContainer_showsTheNumberOfItsContents_atTheFirstLevel() {
		Map<InventoryPlace, InventoryView.Slot> slots = new EnumMap<>(InventoryPlace.class);
		slots.put(InventoryPlace.belt2, new InventoryView.Slot("30200 Purse", Arrays.asList("30201 Coins", "30202 Sack", "  30203 Bread")));
		slots.put(InventoryPlace.rightHand, new InventoryView.Slot("30300 Hammer", null));
		InventoryView view = InventoryView.of(12, slots);

		assertThat(view.slot(InventoryPlace.belt2).text()).isEqualTo("30200 Purse (2)");
		assertThat(view.slot(InventoryPlace.rightHand).text()).isEqualTo("30300 Hammer");
		assertThat(view.slot(InventoryPlace.head).isEmpty()).isTrue();
		assertThat(view.countOccupied()).isEqualTo(2);
		assertThat(view.countInContainers()).isEqualTo(3);
		assertThat(view.summary()).isEqualTo("2 von 23 Pl\u00E4tzen belegt, 3 Items in Beh\u00E4ltern");
		assertThat(view.title()).isEqualTo("Inventar Mensch 12");
		assertThat(view.contentLines()).containsExactly("G\u00FCrtel 2: 30200 Purse", "    30201 Coins", "    30202 Sack", "      30203 Bread");
	}

	@Test
	void aDress_coversUpperBodyAndLegs_whenTheyAreEmpty() {
		Map<InventoryPlace, InventoryView.Slot> slots = new EnumMap<>(InventoryPlace.class);
		slots.put(InventoryPlace.dress, new InventoryView.Slot("30400 Habit", null));
		InventoryView view = InventoryView.of(1, slots);
		assertThat(view.isCoveredByDress(InventoryPlace.upperBody)).isTrue();
		assertThat(view.isCoveredByDress(InventoryPlace.legs)).isTrue();
		assertThat(view.isCoveredByDress(InventoryPlace.head)).isFalse();
		assertThat(InventoryView.of(1, new EnumMap<>(InventoryPlace.class)).isCoveredByDress(InventoryPlace.legs)).isFalse();
	}

	@Test
	void read_withTheTokenOfTheDisplay_seesThePlacesAndTheContents() {
		DatabaseAssumptions.assumeDatabaseIsAvailable();	// a state reads its CacheTableState... from the database
		Human human = new Human();
		HiddenHuman hidden = new WriteAccessToHuman(human, new StateHuman())
				.getMeHidden(new GrantedAccessToProperty[] {GrantedAccessToProperty.inventory});
		hidden.setInventory(new Inventory(SimulationObject_Type.human));
		Knife knife = withState(new Knife());
		Purse purse = withState(new Purse());
		Coins coins = withState(new Coins());
		Bread bread = withState(new Bread());
		assertThat(hidden.putToInventory(InventoryPlace.rightHand, knife)).isTrue();
		assertThat(hidden.putToInventory(InventoryPlace.belt1, purse)).isTrue();
		assertThat(hidden.putToInventory(InventoryPlace.mouth, bread)).isTrue();
		assertThat(purse.putInside(INVENTORY, coins)).isTrue();

		InventoryView view = InventoryView.read(5, human, AccessTokenVisualize.getValid());
		assertThat(view.getMessage()).isNull();
		assertThat(view.countOccupied()).isEqualTo(3);
		assertThat(view.slot(InventoryPlace.rightHand).item).endsWith(" Knife");
		assertThat(view.slot(InventoryPlace.mouth).item).endsWith(" Bread");
		assertThat(view.slot(InventoryPlace.belt1).contents).hasSize(1);
		assertThat(view.slot(InventoryPlace.belt1).contents.get(0)).endsWith(" Coins");
	}

	@Test
	void read_withAnotherToken_seesNothing() {
		DatabaseAssumptions.assumeDatabaseIsAvailable();	// a state reads its CacheTableState... from the database
		Human human = new Human();
		HiddenHuman hidden = new WriteAccessToHuman(human, new StateHuman())
				.getMeHidden(new GrantedAccessToProperty[] {GrantedAccessToProperty.inventory});
		hidden.setInventory(new Inventory(SimulationObject_Type.human));
		hidden.putToInventory(InventoryPlace.rightHand, withState(new Knife()));

		IAccessToken position = new IAccessToken() {
			public boolean isValid() { return true; }
			public SimulationCluster getSimulationCluster() { return SimulationCluster.position; }
		};
		assertThat(InventoryView.read(5, human, position).countOccupied()).isZero();
	}

	@Test
	void theTooltip_showsThePropertiesBetweenTheItemAndTheContents() {
		InventoryView.Slot slot = new InventoryView.Slot("30200 Purse",
				Arrays.asList("Material: leather 95 %, linen 5 %", "Brennt: ja \u00B7 300 \u00B0C, 10 s: 0 %"),
				Arrays.asList("30201 Coins"));
		String tooltip = InventoryFigurePanel.tooltipOf(InventoryPlace.belt1, slot);

		assertThat(tooltip).contains("30200 Purse<br>Material: leather 95 %, linen 5 %<br>Brennt: ja");
		assertThat(tooltip.indexOf("Brennt")).isLessThan(tooltip.indexOf("30201 Coins"));
		// a slot without properties stays as before
		assertThat(InventoryFigurePanel.tooltipOf(InventoryPlace.belt1, new InventoryView.Slot("30300 Hammer", null)))
				.doesNotContain("Material").doesNotContain("Brennt");
	}

	@Test
	void aProbability_isShownInWholePercent() {
		assertThat(InventoryView.percent(0)).isEqualTo("0 %");
		assertThat(InventoryView.percent(0.001)).isEqualTo("<1 %");
		assertThat(InventoryView.percent(0.2997)).isEqualTo("30 %");
		assertThat(InventoryView.percent(1)).isEqualTo("100 %");
	}

	@Test
	void read_givesThePropertiesOfTheItem_withTheTokenOfTheDisplay() {
		DatabaseAssumptions.assumeDatabaseIsAvailable();	// a state reads its CacheTableState... from the database
		Human human = new Human();
		HiddenHuman hidden = new WriteAccessToHuman(human, new StateHuman())
				.getMeHidden(new GrantedAccessToProperty[] {GrantedAccessToProperty.inventory});
		hidden.setInventory(new Inventory(SimulationObject_Type.human));
		assertThat(hidden.putToInventory(InventoryPlace.rightHand, withMaterials(new Knife(), Material.steel, 70, Material.wood, 30))).isTrue();

		InventoryView view = InventoryView.read(5, human, AccessTokenVisualize.getValid());
		// the knife is burnable, not valuable and not protective: material and burning, no value, no protection
		// wood at its ignition temperature for 10 s: 1 - 0.5^10, times the share 30
		assertThat(view.slot(InventoryPlace.rightHand).properties)
				.containsExactly("Material: steel 70 %, wood 30 %", "Brennt: ja \u00B7 300 \u00B0C, 10 s: 30 %");
	}

	@Test
	void theProperties_ofAValuableProtectiveItem_andOfMetalThatDoesNotBurn() {
		DatabaseAssumptions.assumeDatabaseIsAvailable();
		ChainMail chainMail = withMaterials(new ChainMail(), Material.iron, 95, Material.leather, 5);
		assertThat(InventoryView.properties(chainMail, AccessTokenVisualize.getValid())).containsExactly(
				"Material: iron 95 %, leather 5 %",
				"Wert: 345 (pro kg)",
				"Schutz: Stich 50 \u00B7 Keule 30 \u00B7 Hieb 80 \u00B7 R\u00FCckhand 60",
				"Brennt: ja \u00B7 300 \u00B0C, 10 s: 0 %");
		Coins gold = withMaterials(new Coins(), Material.gold, 100);
		assertThat(InventoryView.properties(gold, AccessTokenVisualize.getValid()))
				.containsExactly("Material: gold 100 %", "Wert: 10000 (pro kg)");
	}
}
