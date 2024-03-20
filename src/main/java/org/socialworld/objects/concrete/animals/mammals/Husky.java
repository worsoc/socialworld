/*
* Social World
* Copyright (C) 2022  Mathias Sikos
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
package org.socialworld.objects.concrete.animals.mammals;

import org.socialworld.attributes.Direction;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.objects.concrete.animals.IRunning;
import org.socialworld.objects.concrete.animals.StateRunning;
import org.socialworld.objects.enums.EnumMammal;

public class Husky extends Dog implements IRunning {
	
	private StateRunning stateRunning = StateRunning.getObjectNothing();

	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    creating instance for simulation    //////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public Husky() {
		super();
		belongsTo = EnumMammal.Husky;
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////// implementing IRunning ///////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public  float getSpeed() {return 50; };
	public  float getNumberLegs() {return 4; };
	public Direction getDirectionRun() {
		return new Direction(PropertyName.stateRunning_directionRun , new Vector(1.7F, 1.8F, 1.9F),30.8F);
	}
	
	
	public StateRunning getSavedStateRunning(SimulationCluster cluster) {
	//make a copy as ValueProperty
	ValueProperty vp = this.stateRunning.getAsValue(cluster);
	//the copy is permitted for cluster only
	return objectRequester.requestStateRunning(cluster, vp, this);
	}
	
	public ValueProperty getStateRunningAsProperty(SimulationCluster cluster, String name) {
	return this.stateRunning.getAsValue(cluster, name);
	}
	
	
}
