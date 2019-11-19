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
package org.socialworld.objects.properties;

import org.socialworld.actions.attack.WeaponProperty;

/**
 * German:
 * Das Interface IWeapon definiert die Methoden zum Ermitteln der Waffeneigenschaft von Simulationsobjekten.
 * Jedes Simulationsobjekt (insbesondere Gegenstand), 
 *  der als Waffe verwendet werden kann, implementiert dieses Interface.
 * Da die Methoden ein Objekt der Klasse WeaponProperty zurückgeben, erfordert es,
 *  dass Objekte mit Waffeneigenschaft die entsprechenden Ausprägungen der WeaponProperty haben.
 *  
 * Die Methoden liefern die Beschreibung für folgende Anwendungsarten der Waffe:
 *  - Stechen (eine streckende Armbewegung)
 *  - Keule (beidhändiges Draufhauen, Bewegung von oben auf den Gegner herab)
 *  - Schlag (eine geschwungene Armbewegung von der Seite)
 *  - Rückhandschlag (eine geschwungeneArmbewegung von der anderen Seite)
 *   
 * @author Mathias Sikos
 *
 */
public interface IWeapon {
	public abstract WeaponProperty getStabProperties();
	public abstract WeaponProperty getClubProperties();
	public abstract WeaponProperty getStrokeProperties();
	public abstract WeaponProperty getBackhandProperties();
}
