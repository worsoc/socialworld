/*
 * Social World
 * Copyright (C) 2026  Mathias Sikos
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
package org.socialworld.visualize;

import org.socialworld.actions.AbstractAction;
import org.socialworld.attributes.AttributeArray;

/**
 * What the simulation reports to a display of the running simulation.
 * SocialWorld creates the implementation at start.
 *
 * An implementation also needs its own static method myPrint(String):
 * Simulation.showMessage looks it up by name in the declared methods of the concrete class.
 */
public interface ISimVisualization {

	void show();

	void startAction(int objID, AbstractAction action);

	void stopAction(int objID);

	void setAttributes(int objID, AttributeArray attributes, boolean withChange);

	/**
	 * Called once when all simulation objects exist. Reports before that moment do not reach the display.
	 */
	default void simulationStarted() {
	}
}
