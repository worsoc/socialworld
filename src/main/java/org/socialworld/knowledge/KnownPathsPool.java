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


import java.util.LinkedList;

import org.socialworld.actions.move.Path;
import org.socialworld.attributes.Position;
import org.socialworld.map.IMapProp;
import org.socialworld.map.MapPropTree;

/**
 * @author Mathias Sikos
 *
 */
public class KnownPathsPool {
	MapPropTree startPointsTree;
	MapPropTree endPointsTree;

	public KnownPathsPool () {
		startPointsTree = new MapPropTree(Position.LOCATIONBASE25, Path.LOCATION_BASE25_ACCURACY, Path.getObjectNothing());
		endPointsTree = new MapPropTree(Position.LOCATIONBASE25, Path.LOCATION_BASE25_ACCURACY, Path.getObjectNothing());
	}
	
	public LinkedList<Path> findPaths(Position start, Position end) {
		
		LinkedList<Path> paths = new LinkedList<Path>();
		
		if (!start.isObjectNothing() && !end.isObjectNothing()) {
			LinkedList<Path> pathsForStart = new LinkedList<Path>();
			for (IMapProp prop : startPointsTree.getProperties(start.getLocationByBase25())) {
				pathsForStart.add((Path) prop);
			}
			KnownPaths pathsWithStartPoint = new KnownPaths(pathsForStart);
			paths = pathsWithStartPoint.getPathsWithEnd(end);
		}
		
		return paths;
	}

	public LinkedList<Path> findPathsForStart(Position start) {	
		
		LinkedList<Path> paths = new LinkedList<Path>();
		
		if (!start.isObjectNothing() ) {

			LinkedList<Path> pathsForStart = new LinkedList<Path>();
			for (IMapProp prop : startPointsTree.getProperties(start.getLocationByBase25())) {
				pathsForStart.add((Path) prop);
			}
			KnownPaths pathsWithStartPoint = new KnownPaths(pathsForStart);
				
			paths = pathsWithStartPoint.getPaths();
		}
		
		return paths;
	}

	public LinkedList<Path> findPathsForEnd(Position end) {	
		
		LinkedList<Path> paths = new LinkedList<Path>();
		
		if (!end.isObjectNothing() ) {

			LinkedList<Path> pathsForEnd = new LinkedList<Path>();
			for (IMapProp prop : endPointsTree.getProperties(end.getLocationByBase25())) {
				pathsForEnd.add((Path) prop);
			}
			KnownPaths pathsWithEndPoint = new KnownPaths(pathsForEnd);
		
			paths = pathsWithEndPoint.getPaths();
		}
		
		return paths;

	}

	public void addPath(Path path) {
		if (!path.isObjectNothing()) {
			addPathByStartPoint(path, path.getStartPoint());
			addPathByEndPoint(path, path.getEndPoint());
		}
	}
	
	private void addPathByStartPoint(Path path, Position start) {
		
		if (!path.isObjectNothing() && !start.isObjectNothing()) {
			LinkedList<Path> pathsForStart = new LinkedList<Path>();
			for (IMapProp prop : startPointsTree.getProperties(start.getLocationByBase25())) {
				pathsForStart.add((Path) prop);
			}
			KnownPaths paths = new KnownPaths(pathsForStart);

			paths.addPath(path);
			path.setRefToKnownPathsWithStartingPoint(paths);
		}
	}

	private void addPathByEndPoint(Path path, Position end) {
		
		if (!path.isObjectNothing() && !end.isObjectNothing()) {
			LinkedList<Path> pathsForEnd = new LinkedList<Path>();
			for (IMapProp prop : endPointsTree.getProperties(end.getLocationByBase25())) {
				pathsForEnd.add((Path) prop);
			}
			KnownPaths paths = new KnownPaths(pathsForEnd);
			paths.addPath(path);
			path.setRefToKnownPathsWithEndPoint(paths);
		}
	}

	public void incrementWalkCounter(Path path) {
		
		if (path.isObjectNothing()) return;
		
		KnownPaths paths;
		
		paths = path.getRefToKnownPathsWithStartingPoint();
		paths.incrementWalkCounter(path);
		
		paths = path.getRefToKnownPathsWithEndPoint();
		paths.incrementWalkCounter(path);
		
	}
}
