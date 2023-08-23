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

import org.socialworld.actions.move.Path;
import org.socialworld.attributes.Position;
import org.socialworld.map.MapPropTree;

/**
 * @author Mathias Sikos
 *
 */
public class KnownPathsPool {
	MapPropTree startPointsTree;
	MapPropTree endPointsTree;

	public KnownPathsPool () {
		startPointsTree = new MapPropTree(Position.LOCATIONBASE25, Path.LOCATION_BASE25_ACCURACY);
		endPointsTree = new MapPropTree(Position.LOCATIONBASE25, Path.LOCATION_BASE25_ACCURACY);
	}
	
	public ArrayList<Path> findPaths(Position start, Position end) {	
		ArrayList<Path> paths;
		KnownPaths pathsWithStartPoint = (KnownPaths) startPointsTree.getProperty(start.getLocationByBase25());
		paths = pathsWithStartPoint.getPathsWithEnd(end);
		
		return paths;
	}

	public ArrayList<Path> findPathsForStart(Position start) {	
		KnownPaths pathsWithStartPoint = (KnownPaths) startPointsTree.getProperty(start.getLocationByBase25());
		
		return pathsWithStartPoint.getPaths();
	}

	public ArrayList<Path> findPathsForEnd(Position end) {	
		KnownPaths pathsWithEndPoint = (KnownPaths) endPointsTree.getProperty(end.getLocationByBase25());
		
		return pathsWithEndPoint.getPaths();
	}

	public void addPath(Path path) {
		addPathByStartPoint(path, path.getStartPoint());
		addPathByEndPoint(path, path.getEndPoint());
	}
	
	private void addPathByStartPoint(Path path, Position start) {
		
		KnownPaths paths = (KnownPaths) startPointsTree.getProperty(start.getLocationByBase25());
		paths.addPath(path);
		path.setRefToKnownPathsWithStartingPoint(paths);
	}

	private void addPathByEndPoint(Path path, Position end) {
		
		KnownPaths paths = (KnownPaths) endPointsTree.getProperty(end.getLocationByBase25());
		paths.addPath(path);
		path.setRefToKnownPathsWithEndPoint(paths);
	}

	public void incrementWalkCounter(Path path) {
		
		KnownPaths paths;
		
		paths = path.getRefToKnownPathsWithStartingPoint();
		paths.incrementWalkCounter(path);
		
		paths = path.getRefToKnownPathsWithEndPoint();
		paths.incrementWalkCounter(path);
		
	}
}
