/*
 * Social World
 * Copyright (C) 2019  Mathias Sikos
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
package org.socialworld.core;

import org.socialworld.objects.NoSimulationObject;
import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.access.HiddenSimulationObject;

public class IncompleteSimulationObject {

	private boolean valid;
	private boolean isCompleted;
	
	private SimulationObject object;
	private HiddenSimulationObject hiddenObject;

	// the character (number in characters.csv, 1 .. 100) a human or an animal was made from, 0 = none; it is a value
	// of the generation, not a state of the object: it lives only until the object is completed (2026-11-24)
	private int characterNumber = 0;
	
	public IncompleteSimulationObject() {
		this.valid = false;
		this.isCompleted = true; 
	}
	
	public IncompleteSimulationObject(SimulationObject object, HiddenSimulationObject hiddenObject) {
		this.object = object;
		this.hiddenObject = hiddenObject;
		this.valid = true;
		this.isCompleted = false;
		
		hiddenObject.setIncompleteSimulationObject(this);
		
	}

	public IncompleteSimulationObject(SimulationObject object, HiddenSimulationObject hiddenObject, int characterNumber) {
		this(object, hiddenObject);
		this.characterNumber = characterNumber;
	}
	
	public boolean isValid() {
		return this.valid;
	}
	
	public boolean isIncomplete() {
		return this.isCompleted;
	}
	
	void setComplete() {
		this.isCompleted = true;
	}
	
	public int getObjectID() {
		if (!this.isCompleted) {
			return this.object.getObjectID();
		}
		return 0;
	}
	
	SimulationObject getObject() {
		if (!this.isCompleted) {
			return this.object;
		}
		return NoSimulationObject.getObjectNothing();
	}
	
	HiddenSimulationObject getHiddenObject() {
		if (!this.isCompleted) {
			return this.hiddenObject;
		}
		return new HiddenSimulationObject();
	}

	/** The character the object was made from, e.g. for the start equipment; 0 if none or completed. */
	int getCharacterNumber() {
		if (!this.isCompleted) {
			return this.characterNumber;
		}
		return 0;
	}
	
	public boolean checkForHiddenObject(HiddenSimulationObject hiddenObject) {
		
		return this.hiddenObject == hiddenObject;
		
	}
	
}
