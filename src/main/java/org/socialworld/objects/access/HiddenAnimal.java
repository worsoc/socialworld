/*
 * Social World
 * Copyright (C) 2015  Mathias Sikos
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
package org.socialworld.objects.access;

import org.socialworld.objects.SimulationObject;
import org.socialworld.actions.handle.InventoryPlace;
import org.socialworld.actions.handle.Inventory;
import org.socialworld.actions.move.Path;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.functions.FunctionByMatrix;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.knowledge.KnowledgeElement;
import org.socialworld.objects.WriteAccessToAnimal;

/**
 * @author Mathias Sikos
 *
 */
public class HiddenAnimal extends HiddenSimulationObject {
	
	private WriteAccessToAnimal wa;

	public HiddenAnimal(WriteAccessToAnimal wa, GrantedAccessToProperty properties[]) {
		super(wa, properties);
		this.wa = wa;
	}

	
	public int setAttributes(Value attributes) {
		return wa.setAttributes(attributes, this);
	}
	
	public int setInventory(Inventory inventory) {
		return wa.setInventory(inventory, this);
	}

	public boolean putToInventory(InventoryPlace place, SimulationObject item) {
		return wa.putToInventory(place, item, this);
	}

	public SimulationObject removeFromInventory(InventoryPlace place) {
		return wa.removeFromInventory(place, this);
	}

	public int setMatrix(FunctionByMatrix matrix) {
		return wa.setMatrix(matrix, this);
	}

	public int addPath(Path path) {
		return wa.addPath(path, this);
	}

	public int addKnowledgeElement(KnowledgeElement knowledgeElement) {
		return wa.addKnowledgeElement(knowledgeElement, this);
	}
	
	public int setDirectionChest(Vector directionChest) {
		return wa.setDirectionChest(directionChest, this);
	}

	public int setDirectionView(Vector directionView) {
		return wa.setDirectionView(directionView, this);
	}

	public int setDirectionActiveMove(Vector directionMove) {
		return wa.setDirectionActiveMove(directionMove, this);
	}

}
