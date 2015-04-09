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
package org.socialworld.actions;

import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.core.IEventParam;

/**
 * @author Mathias Sikos
 * 
 * German:
 * ActionPerformer ist die Basisklasse (abstrakte Klasse) f�r die Wirksamwerdung von Aktionen der Simlationsobjekte.
 * Sie implementiert das Interface IEventParam
 *  und funktioniert damit als Eigenschaft von aus Aktionen abgeleiteten Ereignissen.
 * 
 * Die Ausf�hrung von Aktionen besteht aus 2 Schritten
 * a) Einleitung der Ausf�hrung
 * b) Wirksamwerden der Aktion
 * 
 * a) Einleitung der Ausf�hrung:
 * Der ActionMaster f�hrt die ActionHandler aller Simulationsobjekte
 *  und weist den jeweiligen ActionHandler an, mit der Ausf�hrung einer Aktion zu beginnen bzw. eine Aktion fortzusetzen.
 * Der ActionHandler sorgt daf�r, 
 *  dass f�r das auszuf�hrende Aktionsobjekt (Ableitung von AbstractAction) die Methode perform() aufgerufen wird.
 * Die Methode perform() ist abstract und muss in allen Ableitungen implementiert werden/sein.
 * Die Methode perform() f�hrt Vorabpr�fungen der Daten zur Aktion durch, 
 *  erzeugt das zugeh�rige Performer-Objekt von Unterklassen von ActionPerformer (siehe Schritt b),
 *  erzeugt die auszul�senden Ereignisse, 
 *  f�gt den Ereignissen das Performerobjekt als Ereigniseigenschaft hinzu,
 *  und tr�gt diese in die Ereignisverwaltung (EventMaster) ein (siehe Schritt b).
 *  
 * b)  Wirksamwerden der Aktion
 * Es gilt der Grundsatz, dass alle Aktionen durch ihre Ereignisverarbeitung wirksam werden.
 * Im Schritt a) wurden Ereignisse zu den Aktionen in die Ereignisverwaltung eingetragen.
 * Die Ereignisverwaltung arbeitet die Ereignisse nach ihren Regeln ab.
 * F�r jedes Event der Klasse EventByAction, also von Aktionen ausgel�ste Ereignisse, 
 *  wird die evaluate-Methode des dem Ereignis zugeordenten Performers (Ableitung der Klasse ActionPerformer) aufgerufen.
 * Diese wiederum ruft die (in der Klasse ActionPerformer abstrakte) Methode perform im Performerobjekt auf.
 * Diese Methode ermittelt die f�r die Ereignisverarbeitung ben�tigten Werte 
 * 	aus dem Aktionsobjekt, dem ausf�hrenden Objekt (also dem Akteur) und ggf. dem Zielobjekt. 
 * Diese Werte werden standardisiert in einer Liste abgelegt 
 *  und k�nnen vom Ereignis �ber Standardmethoden ausgelesen werden.
 * Schlie�lich werden f�r die Ereignisse ihre Wirkung auf die Simulationsobjekte und ggf. Reaktionen ermittelt.
 *  
 * Die Klasse ActionPerformer ist die Basisklasse f�r die Aktionsobjekte des Schrittes b), 
 *  enth�lt also die Daten zur von der Ereignisverarbeitung ausgel�sten Berechnung der Auswirkungen.
 *  
 * Die Daten werden in der Liste eventParams (ein Arrayvom typ Value) abgelegt.
 * Jeder Value dieser List hat einen Namen, �ber den das Eventgenau auf den gew�nschtne Wert zugreifen kann.
 * Damit kann die Ereignisverarbeitung standardisiert auf notwendige Daten zur ausl�senden Aktion zugreifen.
 *
 */
public abstract class ActionPerformer implements IEventParam {

    private Value eventParams[];
    private int maxParams;
    private boolean valid = false;
    
    private AbstractAction action;
    
    public ActionPerformer (AbstractAction action) {
    	this.action = action;
    }
    
    public abstract void perform();
    
	/* (non-Javadoc)
	 * @see org.socialworld.core.IEventParam#evaluate()
	 */
	@Override
   public void evaluate() {
    	perform();
    }
    
	/* (non-Javadoc)
	 * @see org.socialworld.core.IEventParam#isValid()
	 */
	@Override
    public boolean isValid() {
		return valid;
	}
	
	/* (non-Javadoc)
	 * @see org.socialworld.core.IEventParam#getParam(int)
	 */
	@Override
	public Value getParam(int index) {
		if (index >= 0 &index < size())
			return eventParams[index];
		else
			// invalid "nothing" value
			return new Value();
	}

	/* (non-Javadoc)
	 * @see org.socialworld.core.IEventParam#size()
	 */
	@Override
	public int size() {
		return eventParams.length;
	}

	/* (non-Javadoc)
	 * @see org.socialworld.core.IEventParam#find(org.socialworld.calculation.Type)
	 */
	@Override
	public int find(Type type) {
		for (int index = 0; index < maxParams; index++) {
			if (eventParams[index].getType() == type) {
					return index;
			}
		}
		return -1;
	}

	/* (non-Javadoc)
	 * @see org.socialworld.core.IEventParam#find(String)
	 */
	@Override
	public int find(String name) {
		for (int index = 0; index < maxParams; index++) {
			if (eventParams[index].getName() == name) {
					return index;
			}
		}
		return -1;
	}

	protected void setMaxParam(int max) {
		this.maxParams = max;
		this.eventParams = new Value[max];
	}
	
	protected void setParam(int index, Value param) {
		if (index >= 0 & index < maxParams)
			eventParams[index] = param;
	}
	
	protected void setValid() {
		valid = true;
	}
	
	protected AbstractAction getOriginalActionObject() {
		return this.action;
	}

}