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
import org.socialworld.actions.AbstractAction;
import org.socialworld.attributes.Position;
import org.socialworld.objects.access.IHiddenStateBox;

public class WriteAccessToSimulationObject {
	private SimulationObject object;
	private StateSimulationObject objectsState;
	
	boolean isDummy;
	
	public WriteAccessToSimulationObject(SimulationObject object, StateSimulationObject state) {
		this.object = object;
		this.objectsState = state;
		
		isDummy = false;

		object.setWriteAccess(this);
	}
	
	// Dummy
	public WriteAccessToSimulationObject() {
		isDummy = true;
	}

	public SimulationObject getObject() {
		return this.object;
	}
	
	public final void requestForState(IHiddenStateBox box) {
		box.putStateIntoBox(objectsState);
	}
	
	public final boolean checkGrantRightFor(Object request) {
		switch (request.getClass().getName()) {
		case "org.socialworld.actions.move.ActionMove":
			return true;
		case "org.socialworld.actions.attack.ActionAttack":
			return true;
			
		default:
			return false;
		}
	}
	
	protected final boolean checkCaller(Object caller){
		switch (caller.getClass().getName()) {
		case "org.socialworld.actions.move.ActionMove":
			return true;

		case "org.socialworld.calculation.application.EventInfluenceCalculator":
			return true;
			
		case "org.socialworld.datasource.LoadAnimal":
			return true;
//		case "org.socialworld.datasource.LoadGod":
//			return true;
		case "org.socialworld.datasource.LoadHuman":
			return true;
//		case "org.socialworld.datasource.LoadItem":
//			return true;
//		case "org.socialworld.datasource.LoadMagic":
//			return true;
		case "org.socialworld.datasource.LoadSimulationObject":
			return true;

		case "org.socialworld.SimpleClientActionHandler":
			// TODO delete, its just for test
			return true;
		default:
			return false;
		}
		
	}
	
	public void setObjectID(int objectID, Object caller) {
		if (checkCaller(caller)) object.setObjectID(objectID, this);
	}

	public void setPosition(Position pos, Object caller) {
		if (checkCaller(caller)) object.setPosition(pos, this);
	}
	
	public void setAction(AbstractAction action, Object caller) {
		if (checkCaller(caller)) object.setAction(action, this);
	}

	public void setInfluenceTypes(int types[], Object caller) {
		if (checkCaller(caller)) object.setInfluenceTypes(types, this);
	}

	public void setReactionTypes(int types[], Object caller) {
		if (checkCaller(caller)) object.setReactionTypes(types, this);
	}

	public void setState2ActionType(int type, Object caller) {
		if (checkCaller(caller)) object.setState2ActionType(type, this);
	}
	

}
