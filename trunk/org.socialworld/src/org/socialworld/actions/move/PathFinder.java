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
package org.socialworld.actions.move;

import java.util.ArrayList;
import java.util.ListIterator;

import org.socialworld.attributes.Position;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.knowledge.KnownPathsPool;
import org.socialworld.objects.Animal;

/**
 * German:
 * Die Klasse PathFinder dient dem Finden des zu nutzenden Pfades,
 *  also der Klärung der Frage, auf welchem Weg ein Tier zum Ziel kommt.
 *  
 * Jedes PathFinder-Objekt hat eine Referenz auf sein Tier.
 * Jedes PathFinder-Objekt hat eine Referenz auf die Wissensbasis des Tieres, 
 *  nämlich die Gesamtheit aller bekannten Wege.
 *  
 * Beim Finden eines Pfades wird als Startpunkt die aktuelle Position des Tieres genommen.
 *  Die Zielposition wird angegeben.
 *  
 * Aus den bereits bekannten Pfaden werden unter Berücksichtigung von Bewertungen der bekannten Pfade, 
 *  der neue Pfad hergeleitet.
 * Ein neuer Pfad entsteht dabei 
 *  - aus Verknüpfung zweier bekannter Pfade 
 *  - oder aus Verknüpfung eines bekannten Pfades 
 *      mit einem  neu erzeugten Pfad, bei dem nur Start- und Zielposition angegeben sind.
 *  
 * @author Mathias Sikos
 *
 */
public class PathFinder {
	private Animal actor;
	private KnownPathsPool knownPathsPool;

	public PathFinder(Animal actor, KnownPathsPool knownPathsPool) {
		this.actor = actor;
		this.knownPathsPool = knownPathsPool;
	}
	
	public Path findPath(Position end) {
		// TODO more complex and better pathfinding
		
		
		Path result;
		Path pathStart;
		Path pathEnd;
		
		result = findPathWithStartAndEnd(actor.getPosition(), end);
		
		if (result == null) {
			pathStart = findPathWithStartAndNearestEnd(actor.getPosition(), end);
			pathEnd = findPathWithNearestStartAndEnd(actor.getPosition(), end);
			
			if (pathStart == null) 
				if (pathEnd != null) {
					result = createPath(actor.getPosition(), pathEnd.getStartPoint());
					result.concat(pathEnd);
				}
				else 
					result = createPath(actor.getPosition(), end);
			else
				if (pathEnd == null) {
					result = new Path(pathStart);
					result.concat(createPath(result.getEndPoint(), end));
				}
				else {
					Vector partStart = actor.getPosition().getDirectionTo(pathStart.getEndPoint());
//					Vector partEnd = pathEnd.getStartPoint().getDirectionTo(end);
//					Vector partUnknown = pathStart.getEndPoint().getDirectionTo(pathEnd.getStartPoint());
					Vector beelineToStartOfPathEnd = actor.getPosition().getDirectionTo(pathEnd.getStartPoint());
//					Vector beeline = actor.getPosition().getDirectionTo(end);
					
					if (partStart.length() > beelineToStartOfPathEnd.length()) {
						if (pathStart.costs() < pathEnd.costs() ) {
							result = new Path( pathStart);
							result.concat(createPath(result.getEndPoint(), end));
						}
						else {
							result = createPath(actor.getPosition(), pathEnd.getStartPoint());
							result.concat(pathEnd);
						}
					}
					else {
						result = new Path(pathStart);
						result.concat(createPath(pathStart.getEndPoint(), pathEnd.getStartPoint()));
						result.concat(pathEnd);
					}
				}
					
		}


		return result;
	}
	
	private Path findPathWithStartAndEnd(Position start, Position end) {
		
		ArrayList<Path> paths = knownPathsPool.findPaths(start, end);
		
		return choosePathFromList(paths);
	}
	
	private Path findPathWithStartAndNearestEnd(Position start, Position end) {
		Path path;
		Path bestPath = null;
		float distance;
		float smallestDistance;
		
		smallestDistance = start.getDistance(end);
		
		ArrayList<Path> paths = knownPathsPool.findPathsForStart(start);
		
		ListIterator<Path> iterator = paths.listIterator();
		while (iterator.hasNext()) {
			path = iterator.next();
			distance = path.getEndPoint().getDistance(end);
			if (distance < smallestDistance) {
				bestPath = path;
				smallestDistance = distance;
			}
		}
		
		return bestPath;
	}

	private Path findPathWithNearestStartAndEnd(Position start, Position end) {
		Path path;
		Path bestPath = null;
		float distance;
		float smallestDistance;
		
		smallestDistance = start.getDistance(end);
		
		ArrayList<Path> paths = knownPathsPool.findPathsForEnd(end);
		
		ListIterator<Path> iterator = paths.listIterator();
		while (iterator.hasNext()) {
			path = iterator.next();
			distance = path.getStartPoint().getDistance(start);
			if (distance < smallestDistance) {
				bestPath = path;
				smallestDistance = distance;
			}
		}
		
		return bestPath;
	}

	private Path choosePathFromList(ArrayList<Path> paths) {
		if (paths.size() == 0) 
			return null;
		if (paths.size() == 1) 
			return paths.get(0);
		// TODO implement how to decide which path is choosen
		return paths.get(0);
	}
	
	private Path createPath(Position start, Position end) {
		Path path = new Path(start, end);
		return path;
	}
	
	private void concat(Path a, Path b) {
		
		if (a.getEndPoint().equals(b.getStartPoint(), Position.LOCATIONBASE25, Path.LOCATION_BASE25_ACCURACY)) 
			a.concat(b);
		else
			a.add(b);
	}
}
