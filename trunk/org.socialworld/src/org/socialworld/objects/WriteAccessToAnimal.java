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
import org.socialworld.objects.access.GrantedAccessToProperty;
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
	
	
	public HiddenSimulationObject getMeHidden(GrantedAccessToProperty properties[]) {
		return new HiddenAnimal(this, properties);
	}

	public boolean setAttributes(AttributeArray attributes, HiddenSimulationObject caller) {
		if (checkCaller(caller) & checkAccessToPropertyGranted(caller, GrantedAccessToProperty.attributes)) {
			animalState.setAttributes(attributes, this);
			return true;
		}
		else
			return false;
	}

	public boolean setMatrix(FunctionByMatrix matrix, HiddenSimulationObject caller) {
		if (checkCaller(caller) & checkAccessToPropertyGranted(caller, GrantedAccessToProperty.matrix)) {
			animalState.setMatrix(matrix, this);
			return true;
		}
		else
			return false;
	}

	public boolean addPath(Path path, HiddenSimulationObject caller) {
		if (checkCaller(caller) & checkAccessToPropertyGranted(caller, GrantedAccessToProperty.path)) {
			animalState.addPath(path, this);
			return true;
		}
		else
			return false;
	}
	
	
	public boolean setDirectionChest(Vector directionChest, HiddenSimulationObject caller) {
		if (checkCaller(caller) & checkAccessToPropertyGranted(caller, GrantedAccessToProperty.directionChest)) {
			animalState.setDirectionChest(directionChest, this);
			return true;
		}
		else
			return false;
	}

	public boolean setDirectionView(Vector directionView, HiddenSimulationObject caller) {
		if (checkCaller(caller) & checkAccessToPropertyGranted(caller, GrantedAccessToProperty.directionView)) {
			animalState.setDirectionView(directionView, this);
			return true;
		}
		else
			return false;
	}

	public boolean setDirectionMove(Vector directionMove, HiddenSimulationObject caller) {
		if (checkCaller(caller) & checkAccessToPropertyGranted(caller, GrantedAccessToProperty.directionMove)) {
			animalState.setDirectionMove(directionMove, this);
			return true;
		}
		else
			return false;
	}

}
