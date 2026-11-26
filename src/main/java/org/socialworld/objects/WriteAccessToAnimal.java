/*
 * Social World
 * Copyright (C) 2014  Mathias Sikos
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
package org.socialworld.objects;
import org.socialworld.actions.handle.InventoryPlace;
import org.socialworld.actions.handle.Inventory;
import org.socialworld.actions.move.Path;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.calculation.functions.FunctionByMatrix;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.knowledge.KnowledgeElement;
import org.socialworld.objects.access.GrantedAccessToProperty;
import org.socialworld.objects.access.HiddenAnimal;
import org.socialworld.objects.access.HiddenSimulationObject;

public class WriteAccessToAnimal extends WriteAccessToSimulationObject {
	
	private  Animal animal;
	private StateAnimal animalState;
	
	public WriteAccessToAnimal(Animal animal, StateAnimal state) {
		super(animal, state);
		this.animal = animal;
		this.animalState = state;
	
	}
	
	
	public HiddenAnimal getMeHidden(GrantedAccessToProperty properties[]) {
		return new HiddenAnimal(this, properties);
	}

	
	public int setAttributes(Value attributes, HiddenSimulationObject caller) {
		if (checkCaller(caller)) 
			if	(checkAccessToPropertyGranted(caller, GrantedAccessToProperty.attributes)) {
				animalState.setAttributes(attributes, this);
				return WRITE_ACCESS_RETURNS_SUCCESS;
			}
			else
				return WRITE_ACCESS_RETURNS_NO_GRANT_FOR_PROPERTY;
		else
			return WRITE_ACCESS_RETURNS_INVALID_CALLER;
	}

	public int setInventory(Inventory inventory, HiddenSimulationObject caller) {
		if (checkCaller(caller)) 
			if	(checkAccessToPropertyGranted(caller, GrantedAccessToProperty.inventory)) {
				// set inventory to the animal's (human's) state
				animalState.setInventory(inventory, this);
				return WRITE_ACCESS_RETURNS_SUCCESS;
			}
			else
				return WRITE_ACCESS_RETURNS_NO_GRANT_FOR_PROPERTY;
		else
			return WRITE_ACCESS_RETURNS_INVALID_CALLER;
	}

	public boolean putToInventory(InventoryPlace place, SimulationObject item, HiddenSimulationObject caller) {
		if (checkCaller(caller) && checkAccessToPropertyGranted(caller, GrantedAccessToProperty.inventory)) {
			return animalState.putToInventory(place, item, this);
		}
		return false;
	}

	public SimulationObject removeFromInventory(InventoryPlace place, HiddenSimulationObject caller) {
		if (checkCaller(caller) && checkAccessToPropertyGranted(caller, GrantedAccessToProperty.inventory)) {
			return animalState.removeFromInventory(place, this);
		}
		return NoSimulationObject.getObjectNothing();
	}

	public int setMatrix(FunctionByMatrix matrix, HiddenSimulationObject caller) {
		if (checkCaller(caller)) 
			if	(checkAccessToPropertyGranted(caller, GrantedAccessToProperty.matrix)) {
				animalState.setMatrix(matrix, this);
				return WRITE_ACCESS_RETURNS_SUCCESS;
			}
			else
				return WRITE_ACCESS_RETURNS_NO_GRANT_FOR_PROPERTY;
		else
			return WRITE_ACCESS_RETURNS_INVALID_CALLER;
	}

	public int addPath(Path path, HiddenSimulationObject caller) {
		if (checkCaller(caller)) 
			if	(checkAccessToPropertyGranted(caller, GrantedAccessToProperty.path)) {
				animalState.addPath(path, this);
				return WRITE_ACCESS_RETURNS_SUCCESS;
			}
			else
				return WRITE_ACCESS_RETURNS_NO_GRANT_FOR_PROPERTY;
		else
			return WRITE_ACCESS_RETURNS_INVALID_CALLER;
	}

	public int addKnowledgeElement(KnowledgeElement knowledgeElement, HiddenSimulationObject caller) {
		if (checkCaller(caller)) 
			if	(checkAccessToPropertyGranted(caller, GrantedAccessToProperty.knowledge)) {
				animalState.addKnowledgeElement(knowledgeElement, this);
				return WRITE_ACCESS_RETURNS_SUCCESS;
			}
			else
				return WRITE_ACCESS_RETURNS_NO_GRANT_FOR_PROPERTY;
		else
			return WRITE_ACCESS_RETURNS_INVALID_CALLER;
	}

	public int setDirectionChest(Vector directionChest, HiddenSimulationObject caller) {
		if (checkCaller(caller)) 
			if	(checkAccessToPropertyGranted(caller, GrantedAccessToProperty.directionChest)) {
				animalState.setDirectionChest(directionChest, this);
				return WRITE_ACCESS_RETURNS_SUCCESS;
			}
			else
				return WRITE_ACCESS_RETURNS_NO_GRANT_FOR_PROPERTY;
		else
			return WRITE_ACCESS_RETURNS_INVALID_CALLER;
	}

	public int setDirectionView(Vector directionView, HiddenSimulationObject caller) {
		if (checkCaller(caller)) 
			if	(checkAccessToPropertyGranted(caller, GrantedAccessToProperty.directionView)) {
				animalState.setStateProperty(PropertyName.stateSeer, PropertyName.stateSeer_directionView, new ValueProperty(Type.vector, PropertyName.stateSeer_directionView.name(), directionView), this);
				return WRITE_ACCESS_RETURNS_SUCCESS;
			}
			else
				return WRITE_ACCESS_RETURNS_NO_GRANT_FOR_PROPERTY;
		else
			return WRITE_ACCESS_RETURNS_INVALID_CALLER;
	}

	public int setDirectionActiveMove(Vector directionMove, HiddenSimulationObject caller) {
		if (checkCaller(caller)) 
			if	(checkAccessToPropertyGranted(caller, GrantedAccessToProperty.directionMove)) {
				animalState.setDirectionActiveMove(directionMove, this);
				return WRITE_ACCESS_RETURNS_SUCCESS;
			}
			else
				return WRITE_ACCESS_RETURNS_NO_GRANT_FOR_PROPERTY;
		else
			return WRITE_ACCESS_RETURNS_INVALID_CALLER;
	}
	

}
