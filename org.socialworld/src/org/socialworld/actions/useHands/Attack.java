/*
* Social World
* Copyright (C) 2015  Mathias Sikos
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
package org.socialworld.actions.useHands;

import org.socialworld.actions.ActionPerformer;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.Vector;

/**
 * @author Mathias Sikos
 *
 */
public class Attack extends ActionPerformer {
    private IWeapon weapon;
 
	private Vector directionChest;
	private Vector directionView;
	private Vector directionHit;
	
	private float actorsIntensity;
	private int actorsPower;
 
    public Attack ( IWeapon weapon, 
    		Vector directionChest, Vector directionView, Vector directionHit,
    		float actorsIntensity, int actorsPower ) {
      	this.weapon = weapon;
    	
  	  	this.directionChest = directionChest;
    	this.directionView = directionView;
    	this.directionHit = directionHit;

    	this.actorsIntensity = actorsIntensity;
    	this.actorsPower = actorsPower;
    }
  
   	public void perform() {
		
		float intensity;
		
		// TODO
		intensity = actorsIntensity * actorsPower;
		
		
		if (!isValid()) {
			setMaxParam(6);
			setParam(0, new Value(Type.simulationObject, "weapon", weapon));
			setParam(1, new Value(Type.floatingpoint, "actorsIntensity", actorsIntensity));
			setParam(2, new Value(Type.floatingpoint, "intensity", intensity));
			setParam(3, new Value(Type.vector, "directionChest", directionChest));
			setParam(4, new Value(Type.vector, "directionView", directionView));
			setParam(5, new Value(Type.vector, "directionHit", directionHit));
			setValid();
		}
		
	}


}
