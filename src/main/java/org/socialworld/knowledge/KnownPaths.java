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
package org.socialworld.knowledge;

import java.util.ArrayList;
import java.util.ListIterator;

import org.socialworld.actions.move.Path;
import org.socialworld.attributes.Position;
import org.socialworld.map.IMapProp;

/**
 * @author Mathias Sikos
 *
 */
public class KnownPaths implements IMapProp {
	
	private static KnownPaths objectNothing;
	
	public static KnownPaths getObjectNothing() {
		if (objectNothing == null) {
			objectNothing = new KnownPaths();
			objectNothing.isNothing = true;
		}
		return objectNothing;
	}

	private boolean isNothing = false;
	ArrayList<Path> knownPaths;
	
	int countUses[];
	long LastUsingTimeInMilliSeconds;
	
	public KnownPaths() {
		knownPaths = new ArrayList<Path>();
	}
	
	public void addPath(Path path) {
		if (isNothing) return;
		if (path.isObjectNothing() ) return;
		
		int index;
		knownPaths.add(path);
		index = knownPaths.size() - 1;
		countUses[index] = 1;
	}
	
	public ArrayList<Path> getPaths() {
		return knownPaths;
	}
	
	public ArrayList<Path> getPathsWithEnd(Position end) {
		
		if (isNothing) return knownPaths;
		
		ArrayList<Path> result;
		result = new ArrayList<Path>();
		if (end.isObjectNothing()) return result;
		
		Path path;
		ListIterator<Path> iterator = knownPaths.listIterator();
		
		while (iterator.hasNext()) {
			path = iterator.next();
			if (path.getEndPoint().equals(end, Position.LOCATIONBASE25, Path.LOCATION_BASE25_ACCURACY))
				result.add(path);
		}
		return result;
	}

	public ArrayList<Path> getPathsWithStart(Position start) {
		
		if (isNothing) return knownPaths;
		
		ArrayList<Path> result;
		result = new ArrayList<Path>();
		if (start.isObjectNothing()) return result;
		
		Path path;
		ListIterator<Path> iterator = knownPaths.listIterator();
		
		while (iterator.hasNext()) {
			path = iterator.next();
			if (path.getStartPoint().equals(start, Position.LOCATIONBASE25, Path.LOCATION_BASE25_ACCURACY))
				result.add(path);
		}
		return result;
	}

	public void incrementWalkCounter(Path path) {
		
		if (isNothing) return;
		if (path.isObjectNothing()) return;

		int index;
		
		index = getIndex(path);
		if (index >= 0 & index < countUses.length) countUses[index]++;
	}


	private int getIndex(Path path) {
		
		if (path.isObjectNothing()) return -1;
		
		int index;
		ListIterator<Path> iterator = knownPaths.listIterator();
		
		while (iterator.hasNext()) {
			index = iterator.nextIndex();
			if (iterator.next().equals(path)) return index;
		}
		
		return -2;
	}
}
