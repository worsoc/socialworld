/*
* Social World
* Copyright (C) 2014  Mathias Sikos
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
package org.socialworld.collections;

import java.util.ArrayList;

import org.socialworld.actions.move.Path;
import org.socialworld.map.IMapProp;
import org.socialworld.objects.SimulationObject;

/**
 * @author Mathias Sikos
 *
 */
public class MapPropSimulationObject implements IMapProp {
	private SimulationObject object;
	
	private ArrayList<SimulationObject> objects;

	public MapPropSimulationObject(SimulationObject object) {
		this.object = object;
	}
	
	protected SimulationObject getObject() {
		return this.object;
	}
}
