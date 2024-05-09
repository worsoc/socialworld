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

import org.socialworld.GlobalSwitches;
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
		startPointsTree = new MapPropTree(Position.LOCATIONBASE25, Path.LOCATION_BASE25_ACCURACY, KnownPaths.getObjectNothing());
		endPointsTree = new MapPropTree(Position.LOCATIONBASE25, Path.LOCATION_BASE25_ACCURACY, KnownPaths.getObjectNothing());
	}
	
	public ArrayList<Path> findPaths(Position start, Position end) {
		
		ArrayList<Path> paths = new ArrayList<Path>();
		
		if (!start.isObjectNothing() && !end.isObjectNothing()) {
			KnownPaths pathsWithStartPoint = (KnownPaths) startPointsTree.getProperty(start.getLocationByBase25());
			if (pathsWithStartPoint == null) {
				if (GlobalSwitches.OUTPUT_DEBUG_VARIABLE_IS_NULL) {
					System.out.println("KnownPathsPool.findPaths(): pathsWithStartPoint is null ");
				}
			}
			else {
				paths = pathsWithStartPoint.getPathsWithEnd(end);
			}
		}
		
		return paths;
	}

	public ArrayList<Path> findPathsForStart(Position start) {	
		
		ArrayList<Path> paths = new ArrayList<Path>();
		
		if (!start.isObjectNothing() ) {

			KnownPaths pathsWithStartPoint = (KnownPaths) startPointsTree.getProperty(start.getLocationByBase25());
	
			if (pathsWithStartPoint == null) {
				if (GlobalSwitches.OUTPUT_DEBUG_VARIABLE_IS_NULL) {
					System.out.println("KnownPathsPool.findPathsForStart(): pathsWithStartPoint is null ");
				}
			}
			else {
				paths = pathsWithStartPoint.getPaths();
			}
		}
		
		return paths;
	}

	public ArrayList<Path> findPathsForEnd(Position end) {	
		
		ArrayList<Path> paths = new ArrayList<Path>();
		
		if (!end.isObjectNothing() ) {

			KnownPaths pathsWithEndPoint = (KnownPaths) endPointsTree.getProperty(end.getLocationByBase25());
		
			if (pathsWithEndPoint == null) {
				if (GlobalSwitches.OUTPUT_DEBUG_VARIABLE_IS_NULL) {
					System.out.println("KnownPathsPool.findPathsForEnd(): pathsWithEndPoint is null ");
				}
			}
			else {
				paths = pathsWithEndPoint.getPaths();
			}
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
			KnownPaths paths = (KnownPaths) startPointsTree.getProperty(start.getLocationByBase25());
			paths.addPath(path);
			path.setRefToKnownPathsWithStartingPoint(paths);
		}
	}

	private void addPathByEndPoint(Path path, Position end) {
		
		if (!path.isObjectNothing() && !end.isObjectNothing()) {
			KnownPaths paths = (KnownPaths) endPointsTree.getProperty(end.getLocationByBase25());
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
