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

import org.socialworld.attributes.Position;
import org.socialworld.knowledge.KnownPaths;

/**
 * @author Mathias Sikos
 *
 */
public class Path {
	public static final int LOCATION_BASE25_ACCURACY = 7;
	
	private Position start;
	private Position end;

	private ArrayList<Position> points;
	int indexLastPassedPoint;
	
	private boolean completelyKnown;

	private int length;
	
	private KnownPaths refToKnownPathsWithStartingPoint;
	private KnownPaths refToKnownPathsWithEndPoint;
	
	public Path(Position start, Position end) {
		points = new ArrayList<Position>();
		
		this.start = start;
		this.end = end;
		
		points.add(start);
		points.add(end);
		
		completelyKnown = false;
		
		refresh();
	}
	
	public float costs() {
		// TODO
		return length;
	}
	
	public void setRefToKnownPathsWithStartingPoint(KnownPaths paths) {
		this.refToKnownPathsWithStartingPoint = paths;
	}

	public void setRefToKnownPathsWithEndPoint(KnownPaths paths) {
		this.refToKnownPathsWithEndPoint = paths;
	}

	public KnownPaths getRefToKnownPathsWithStartingPoint() {
		return this.refToKnownPathsWithStartingPoint;
		
	}

	public KnownPaths getRefToKnownPathsWithEndPoint() {
		return this.refToKnownPathsWithEndPoint;
		
	}
	
	public void add (Path b) {
		points.addAll(b.getPoints());
		
		this.end = b.getEndPoint();
		this.completelyKnown = this.completelyKnown & b.isCompletelyKnown();
		
		refresh();
	}

	public void concat (Path b) {
		points.remove(points.size());
		add(b);
	}

	public Position getEndPoint() {
		return end;
	}
	
	public Position getStartPoint() {
		return start;
	}
	
	public boolean isCompletelyKnown() {
		return completelyKnown;
	}
	
	private ArrayList<Position> getPoints() {
		return points;
	}
	
	private void refresh() {
		length = points.size();
	}
}
