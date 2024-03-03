/*
* Social World
* Copyright (C) 2020  Mathias Sikos
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

import org.socialworld.attributes.PropertyName;

public class NoSimulationObject extends SimulationObject {

	private static NoSimulationObject objectNothing;
	
	public static NoSimulationObject getObjectNothing() {
		if (objectNothing == null) {
			objectNothing = new NoSimulationObject();
		}
		return objectNothing;
	}
	
	private static PropertyName[] usedStateAppearanceColourPropertyNames = new PropertyName[0];
	private static byte[] stateAppearanceMainColourCalculationColourSetsShares = new byte[0];

	
	protected int getLexemIdHighPart() { return 0;}
	protected int getLexemIdLowPart() { return 0;}

	@Override
	public final boolean isSimulationObject() {return false;}

	@Override
	protected SimulationObject_Type getSimObjectType() {
		return SimulationObject_Type.noObject;
	}

	protected void initialize() {
		if (!isInitialized()) {
		}
	}


	@Override
	public boolean checkObjectBelongsToGroup(int groupNumberSuffix) {
		return false;
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    STATE      ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public PropertyName[] getUsedStateAppearanceColourPropertyNames() {
		return usedStateAppearanceColourPropertyNames;
	}
	
	public byte[] getStateAppearanceMainColourCalculationColourSetsShares() {
		return stateAppearanceMainColourCalculationColourSetsShares;
	}

	@Override
	protected void assignState(StateSimulationObject state) {
	
	}

	@Override
	protected State getInitState(String stateClassName) {
		return State.getObjectNothing();
	}

}
