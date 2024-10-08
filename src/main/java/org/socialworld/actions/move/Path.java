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
import org.socialworld.attributes.PropertyName;
import org.socialworld.attributes.PropertyProtection;
import org.socialworld.attributes.SimProperty;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.core.IAccessToken;
import org.socialworld.knowledge.KnownPaths;
import org.socialworld.map.IMapProp;
/**
 * German:
 * Die Klasse Path enthaelt eine Wegbeschreibung, 
 *  ueber welche Zwischenpositionen jemand vom Startpunkt zum Endpunkt (Zielposition) gelangt.
 *  
 * Ein Index zeigt an, auf welcher Etappe (Teilstueck des Pfades) sich das Objekt befindet.
 * 
 * Es gibt eine Kennung, die angibt, ob der Pfad komplett bekannt ist oder ob es unbekannte Teilstuecke gibt.
 * 
 * Die Beschreibung enthaelt ausserdem die gesamte Weglaenge.
 * 
 * Ausserdem gibt es eine Referenz auf die Wissensbasis des Objektes (KnownPaths),
 *  ueber die die Wissensbasis bei Nutzung des Pfades aktualisiert wird.
 *
 * Weiterhin werden zu einem Pfad die Kindpfade angegeben.
 *  Dies sind die beiden Pfade, aus denen der neue Pfad durch Verknuepfung entstanden ist.
 *   Durch rekursive Aufrufe kann die Aktualisierung der Wissensbasis auch fuer die Teilabschnitte realisiert werden.
 *
 * @author Mathias Sikos
 *
 */
public class Path extends SimProperty implements IMapProp{
	public static final int LOCATION_BASE25_ACCURACY = 7;
	private static final int STOP_CHILD_INCREMETUSAGE_CALL = 10;
	
	private Position start = Position.getObjectNothing();
	private Position end = Position.getObjectNothing();

	private ArrayList<Position> points;
	int indexPointToPassNext = 0;
	
	private boolean completelyKnown;

	private int length;
	
	private KnownPaths refToKnownPathsWithStartingPoint;
	private KnownPaths refToKnownPathsWithEndPoint;
	
	private Path pathA;
	private Path pathB;
	
///////////////////////////////////////////////////////////////////////////////////////////
///////////// object nothing (abstract method from ISimProperty)    ///////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	private static Path objectNothing;
	
	public static Path getObjectNothing() {
		if (objectNothing == null) {
			objectNothing = new Path();
			objectNothing.setToObjectNothing();
		}
		return objectNothing;
	}
	
	public boolean checkIsObjectNothing() {
		return (this == objectNothing);
	}
	
	private Path() {
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
//////////////////creating instance for simulation    ///////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public Path(PropertyName prop, Position start, Position end) {
		super();
		setPropertyName(prop);
		
		points = new ArrayList<Position>();
		
		this.start = start;
		this.end = end;
		
		if (!start.isObjectNothing() && !end.isObjectNothing() ) {
			this.points.add(start);
			this.points.add(end);
		}
		
		this.completelyKnown = false;
		
		refresh();
	}
	
	// copy constructor
	public Path(PropertyName prop, Path original) {
		super();
		setPropertyName(prop);
		
		this.start = original.start;
		this.end = original.end;
		
		this.points = new ArrayList<Position>();
		this.points.addAll(original.getPoints());
		
		this.completelyKnown = original.completelyKnown;
		
		this.pathA = original;
		
		refresh();
	}
	
	public Path(Path original, PropertyProtection protectionOriginal, IAccessToken token ) {
		super(protectionOriginal, token);
		setPropertyName(original.getPropertyName());
		
		this.start = original.start;
		this.end = original.end;
		
		this.points = new ArrayList<Position>();
		this.points.addAll(original.getPoints());
		
		this.completelyKnown = original.completelyKnown;
		
		this.pathA = original;
		
		refresh();
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    ISavedValue  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public SimProperty copyForProperty(IAccessToken token) {
		return new Path(this, getPropertyProtection(), token);
	}
	
	public  ValueProperty getProperty(IAccessToken token, PropertyName prop, String valueName) {
		switch (prop) {
		default:
		return ValueProperty.getInvalid();
		}
	}
	
	
	public void resetCildPaths() {
		this.pathA = Path.getObjectNothing();
		this.pathB = Path.getObjectNothing();
	}
	
	public float costs() {
		// TODO calculating costs
		return length;
	}
	
	
	public void add (Path b) {
		if (!b.isObjectNothing()) {
			points.addAll(b.getPoints());
			
			this.end = b.getEndPoint();
			this.completelyKnown = this.completelyKnown & b.isCompletelyKnown();
	
			this.pathB = b;
	
			refresh();
		}
	}

	public void concat (Path b) {
		if (!b.isObjectNothing()) {
			points.remove(points.size());
			add(b);
		}
	}

	public Position getEndPoint() {
		return end;
	}
	
	public Position getStartPoint() {
		return start;
	}
	
	public Position getNextPoint() {
		if (indexPointToPassNext < length)
			return points.get(indexPointToPassNext);
		else
			return Position.getObjectNothing();
	}
	
	public void completeSection(Position position) {
		if (!position.isObjectNothing()) {
			if (position.equals(getNextPoint(), Position.LOCATIONBASE25, LOCATION_BASE25_ACCURACY))
				if (indexPointToPassNext < length)
					indexPointToPassNext++;
		}
	}
	
	public boolean isCompleted() {
		return (indexPointToPassNext == length);
	}
	
	public boolean isCompletelyKnown() {
		return completelyKnown;
	}
	
	public boolean hasRefToKnownPaths() {
		return ( (this.refToKnownPathsWithStartingPoint != null) &
				(this.refToKnownPathsWithEndPoint != null) ) ;
	}
	
	public void incrementUsageCounter(int countCalls) {
		if (this.refToKnownPathsWithStartingPoint != null)
			this.refToKnownPathsWithStartingPoint.incrementWalkCounter(this);
		if (this.refToKnownPathsWithEndPoint != null)
			this.refToKnownPathsWithEndPoint.incrementWalkCounter(this);
		
		if (countCalls < STOP_CHILD_INCREMETUSAGE_CALL) {
			countCalls = countCalls + 1;
			if (!this.pathA.isObjectNothing())
				this.pathA.incrementUsageCounter(countCalls);
			if (!this.pathB.isObjectNothing())
				this.pathB.incrementUsageCounter(countCalls);
		}
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

	private ArrayList<Position> getPoints() {
		return points;
	}
	
	private void refresh() {
		length = points.size();
		
		if ((length > 1) & (indexPointToPassNext == 0)) 			indexPointToPassNext = 1;
		else if (length == 0) indexPointToPassNext = -1;
	}
	
	
	public boolean equals(IMapProp propLike) {
		if (propLike instanceof Path) {
			 
			Path path = (Path) propLike;

			if ((this.start.equals(path.start)) && 
				(this.end.equals(path.end))  &&
				(checkPointsAreEqual(this.points, path.points))) {
				return true;
			}
		}
		return false;
	}

	private boolean checkPointsAreEqual(ArrayList<Position> a, ArrayList<Position> b) {
		if (a.size() == b.size()  ) {
			int size = a.size();
			int index = 0;
			while (a.get(index).equals(b.get(index))) {
				index++;
				if (index == size) {
					return true;
				}
			}
		}
		return false;
	}
}
