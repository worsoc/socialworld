/*
* Social World
* Copyright (C) 2014  Mathias Sikos
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.  
*
* or see http://www.gnu.org/licenses/gpl-2.0.html
*
*/
package org.socialworld.objects;
import org.socialworld.actions.move.Path;
import org.socialworld.attributes.AttributeArray;
import org.socialworld.calculation.FunctionByMatrix;
import org.socialworld.calculation.Vector;
import org.socialworld.objects.access.HiddenAnimal;
import org.socialworld.objects.access.HiddenSimulationObject;

public class WriteAccessToAnimal extends WriteAccessToSimulationObject {
	
	private  Animal animal;
	private StateAnimal animalState;
	
	public WriteAccessToAnimal(Animal animal, StateAnimal state, HiddenAnimal returnHidden) {
		super(animal, state, returnHidden);
		this.animal = animal;
		this.animalState = state;
		this.animal.init();
	}
	
	
	public HiddenSimulationObject getMeHidden() {
		return new HiddenAnimal(this, nextToken());
	}

	public void setAttributes(AttributeArray attributes, HiddenSimulationObject caller) {
		if (checkCaller(caller)) animalState.setAttributes(attributes, this);
	}

	public void setMatrix(FunctionByMatrix matrix, HiddenSimulationObject caller) {
		if (checkCaller(caller)) animalState.setMatrix(matrix, this);
	}

	public void addPath(Path path, HiddenSimulationObject caller) {
		if (checkCaller(caller)) animalState.addPath(path, this);
	}
	
	
	public void setDirectionChest(Vector directionChest, HiddenSimulationObject caller) {
		if (checkCaller(caller)) animalState.setDirectionChest(directionChest, this);
	}

	public void setDirectionView(Vector directionView, HiddenSimulationObject caller) {
		if (checkCaller(caller)) animalState.setDirectionView(directionView, this);
	}

	public void setDirectionMove(Vector directionMove, HiddenSimulationObject caller) {
		if (checkCaller(caller)) animalState.setDirectionMove(directionMove, this);
	}

}
