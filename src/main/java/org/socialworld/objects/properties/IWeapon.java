/*
 * Social World
 * Copyright (C) 2015  Mathias Sikos
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://gnu.org>.
 */
package org.socialworld.objects.properties;

import org.socialworld.attributes.properties.WeaponProperty;
import org.socialworld.objects.NoSimulationObject;

/**
 * German:
 * Das Interface IWeapon definiert die Methoden zum Ermitteln der Waffeneigenschaft von Simulationsobjekten.
 * Jedes Simulationsobjekt (insbesondere Gegenstand), 
 *  der als Waffe verwendet werden kann, implementiert dieses Interface.
 * Da die Methoden ein Objekt der Klasse WeaponProperty zur�ckgeben, erfordert es,
 *  dass Objekte mit Waffeneigenschaft die entsprechenden Auspr�gungen der WeaponProperty haben.
 *  
 * Die Methoden liefern die Beschreibung f�r folgende Anwendungsarten der Waffe:
 *  - Stechen (eine streckende Armbewegung)
 *  - Keule (beidh�ndiges Draufhauen, Bewegung von oben auf den Gegner herab)
 *  - Schlag (eine geschwungene Armbewegung von der Seite)
 *  - R�ckhandschlag (eine geschwungeneArmbewegung von der anderen Seite)
 *   
 * @author Mathias Sikos
 *
 */
public interface IWeapon {
	
	public static final String NAME = "IWeapon";

	public abstract WeaponProperty getStabProperties();
	public abstract WeaponProperty getClubProperties();
	public abstract WeaponProperty getStrokeProperties();
	public abstract WeaponProperty getBackhandProperties();
	
	// F1 (2026-11-23): a real weapon is not nothing; only NoIWeapon is (before, both were the wrong way round)
	public default boolean isObjectNothing() {return false;}
	
	static final IWeapon objectNothing = new NoIWeapon();

	public static IWeapon getObjectNothing() {
		return objectNothing;
	}
	
	static class NoIWeapon extends NoSimulationObject implements IWeapon {
		
		public boolean isObjectNothing() {
			return true;
		}
		public WeaponProperty getStabProperties() {
			return null;
		}
		public WeaponProperty getClubProperties() {
			return null;
		}
		public WeaponProperty getStrokeProperties() {
			return null;
		}
		public WeaponProperty getBackhandProperties() {
			return null;
		}

	}

}
