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
package org.socialworld.actions.bodilyfunctions;

import org.socialworld.actions.ActionPerformer;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.collections.ValueArrayList;

/**
 * German:
 * Die Klasse BodilyFunction ist von der abstrakten Klasse ActionPerformer abgeleitet.
 * 
 * Die Klasse BodilyFunction dient der Wirksamwerdung der Aktion,
 *  nämlich als Argument für das zur Aktion gehörende Ereignis.
 *
 *   
 *  In der Ausfuehrungsmethode perform() wird
 *   - die Intensitaet des Schlafens (also die Tiefe des Schlafs)
 *   für den Standardzugriff aus dem Ereignis heraus bereitgestellt.
 *   
 * @author Mathias Sikos
 *
 */
public class BodilyFunction extends ActionPerformer {

    public BodilyFunction (ActionBodilyFunction action) {
    	super(action);
    }
	
    protected final void choosePropertiesFromPropertyList(ValueArrayList properties) {
    	
		for (int i = 0; i < properties.size(); i++) {
			addProperty(properties.get(i));
		}
   	
    }

	/* (non-Javadoc)
	 * @see org.socialworld.actions.ActionPerformer#perform()
	 */
	@Override
	protected void perform() {
		
		if (!isEvaluated()) {
			
			ActionBodilyFunction originalAction;
			float actorsIntensity;
			
			originalAction  = (ActionBodilyFunction) getOriginalActionObject();
			actorsIntensity = originalAction.getIntensity();
	
			addParam( new Value(Type.floatingpoint, "actorsIntensity", actorsIntensity));
			
			setEvaluated();
		}
		
	}

}
