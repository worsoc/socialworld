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
import org.socialworld.objects.access.HiddenSimulationObject;

public class WriteAccessToSimulationObject {
	private SimulationObject object;
	private StateSimulationObject objectsState;
	
	boolean isDummy;
	
	public WriteAccessToSimulationObject(SimulationObject object, StateSimulationObject state, HiddenSimulationObject returnHidden) {
		this.object = object;
		this.objectsState = state;
		
		isDummy = false;

		object.setWriteAccess(this);
		object.setState(state, this);
		state.setWriteAccess(this);
		state.setObject(object);
		
		returnHidden = getMeHidden();
	}
	
	// Dummy
	public WriteAccessToSimulationObject() {
		isDummy = true;
	}

	protected long nextToken() {
		// TODO
		return 1;
	}
	
	public HiddenSimulationObject getMeHidden() {
		return new HiddenSimulationObject(this, nextToken());
	}
	
	public SimulationObject getObject() {
		return this.object;
	}
	
	
	protected final boolean checkCaller(HiddenSimulationObject caller){
		switch (caller.getClass().getName()) {

		case "org.socialworld.objects.access.HiddenSimulationObject":
			return true;
		case "org.socialworld.objects.access.HiddenAnimal":
			return true;
		case "org.socialworld.objects.access.HiddenHuman":
			return true;
			
		default:
			return false;
		}
		
	}
	
	public void setObjectID(int objectID, HiddenSimulationObject caller) {
		if (checkCaller(caller)) object.setObjectID(objectID, this);
	}

	public void setPosition(Position pos, HiddenSimulationObject caller) {
		if (checkCaller(caller)) objectsState.setPosition(pos, this);
	}
	
	public void setAction(AbstractAction action, HiddenSimulationObject caller) {
		if (checkCaller(caller)) object.setAction(action, this);
	}

	public void setInfluenceTypes(int types[], HiddenSimulationObject caller) {
		if (checkCaller(caller)) objectsState.setInfluenceTypes(types, this);
	}

	public void setReactionTypes(int types[], HiddenSimulationObject caller) {
		if (checkCaller(caller)) objectsState.setReactionTypes(types, this);
	}

	public void setState2ActionType(int type, HiddenSimulationObject caller) {
		if (checkCaller(caller)) objectsState.setState2ActionType(type, this);
	}
	

}
