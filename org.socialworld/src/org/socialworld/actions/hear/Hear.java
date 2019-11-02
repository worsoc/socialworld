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
package org.socialworld.actions.hear;

import org.socialworld.actions.ActionMode;
import org.socialworld.actions.ActionPerformer;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.objects.Human;

/**
 * German:
 * Die Klasse Hear ist von der abstrakten Klasse ActionPerformer abgeleitet.
 * 
 * Die Klasse Hear dient der Wirksamwerdung der Aktion,
 *  nämlich als Argument für das zur Aktion gehörende Ereignis.
 *
 *  In der Ausführungsmethode perform() werden 
 *   - der (Gesprächs)partner (ein Objekt der Klasse Human)
 *   - der Satz
 *   für den Standardzugriff aus dem Ereignis heraus bereitgestellt.
 *   
 *  Bei der Bereitstellung der beiden Eigenschaften werden die Fälle
 *   - Zuhören/Hinhören
 *   - Verstehen
 *  gleich behandelt (beide leiten Partner und Satz auf die gleiche Weise her).
 * 
 * @author Mathias Sikos
 *
 */
public class Hear extends ActionPerformer {

    public Hear (ActionHear action) {
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
	public void perform() {
		
		if (!isValid()) {
	 		ActionHear originalAction;
			Human partner;
			String sentence;
			
			originalAction = (ActionHear) getOriginalActionObject();
			ActionMode mode = originalAction.getMode();
			partner = (Human) originalAction.getTarget();
			
			switch (mode) {
			case  listenTo:
		
				sentence = originalAction.getSentence();
		
				addParam( new Value(Type.simulationObject, "partner", partner));
				addParam( new Value(Type.string, "sentence", sentence));
				
				setValid();
				
				break;
					
			case understand:
	
				sentence = originalAction.getSentence();
				
				addParam( new Value(Type.simulationObject, "partner", partner));
				addParam( new Value(Type.string, "sentence", sentence));
				
				setValid();
				
				break;
				
			default:
			}
		}
	}
	
	


}
