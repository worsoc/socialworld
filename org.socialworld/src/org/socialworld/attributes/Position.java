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
package org.socialworld.attributes;

import org.socialworld.calculation.geometry.Vector;

/**
 * The class holds information about the position
 *
 * German:
 * Position ist die Klasse zur Angabe von Positionen für
 *   - Simulationsobjekte (über den Zustand des Objektes: StateSimulationObject)
 *   - Ereignisse (Event)
 *   
 * Die Position wird angegeben über
 *  a) einen Vektor mit den Koordinaten (x,y,z)
 *  b) eine Zahl zur Beschreibung des Pfades durch einen Suchbaum mit Basis 9
 *  c) eine Zeichenkette (Folge von Buchstaben) zur Beschreibung des Pfades durch einen Suchbaum mit Basis 25
 *  
 *  Die Angaben zum Suchbaum werden bei Setzen/Ändern des Vektors angepasst.
 *  
 *  Damit ist der Vektor die eigentliche Positionsangabe.
 *  
 *  Die beiden Varianten zum Suchbaum dienen der schnellen Findung von Objekten 
 *    über eine Teilfolge der Zahlen- bzw. Buchstabenfolge.
 *     (für weitere Informationen zum Suchbaum siehe bitte in Klasse ObjectByPositionSearch)
 *  Die Angaben zum Suchbaum werden also nicht gesetzt, sondern anhand des Vektors ermittelt.
 *  
 *  Neben der set-Methode zum Setzen des Vektors und 
 *    den Get-Methoden für die drei Varianten der Positionsangabe stellt die Klasse folgende Methoden zur Verfügung:
 *  -   equals() zur Prüfung auf Gleichheit über den Vektor
 *  -   equals() zur Prüfung auf Gleichheit über den Suchbaumpfad
 *           hier wird in den Argumenten angegeben welche Basis (9 oder 25) und welche Genauigkeit (wieviele Schritte durch den Suchbaum) verwendet werden soll 
 *  -   getDistance() zur Berechnung des Abstandes (also Länge des Weges zu) einer zweiten Position
 *  -   getDirectionFrom() zur Ermittlung eines Richtungsverktors von einer zweiten Position zur "eigenen"
 *  -   getDirectionTo() zur Ermittlung eines Richtungsverktors von der "eigenen" zu einer zweiten Position hin
 *  -   getX(), getY() und getZ() zur Abfrage der einzenen Koordianten desVektors
 *  
 * @author Mathias Sikos (tyloesand)  
 */
public class Position extends SimObjProperty {

	public final int LOCATIONBYBASEMAXLENGTH = 9;
	public final int TRANSLATE = 2000000;
	public static final int LOCATIONBASE9 = 9;
	public static final int LOCATIONBASE25 = 25;
	

	private Vector m_position;  // (x,y,z) millimeters
	
	private int locationByBase9;
	private String locationByBase25;

	public Position(Vector position) {
		m_position =  position;
		
		setLocationByBases(position);
	}
	
	public Position(Position position) {
		m_position =  position.getVector();
		
		locationByBase9 = position.getLocationByBase9();
		locationByBase25 = position.getLocationByBase25();
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    ISimObjProperty  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	protected SimObjProperty copyForProperty() {
		return new Position(this);
	}


///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    Position  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////


	public int getLocationByBase9() { return locationByBase9; }
	public String getLocationByBase25() { return locationByBase25 ; }

	public Vector getVector() {return new Vector(m_position);}
			
	
	public float getDistance(Position position) {
		Vector direction;
		float distance;

		direction = getDirectionFrom(position);
		distance = direction.length();

		return distance;
	}

	/**
	 * The method calculates the direction between two positions. The result
	 * vector has the direction from parameter position to this position.
	 * 
	 * @param position
	 * @return the direction
	 */
	public Vector getDirectionFrom(Position position) {
		Vector direction = m_position.getDirectionFrom(position.getVector());
		return direction;
	}

	/**
	 * The method calculates the direction between two positions. The result
	 * vector has the direction from this position to parameter position.
	 * 
	 * @param position
	 * @return the direction
	 */
	public Vector getDirectionTo(Position position) {
		Vector direction = position.getVector().getDirectionFrom(m_position);
		return direction;
	}

	public int getX() { return (int) m_position.getX(); }
	public int getY() { return (int) m_position.getY(); }
	public int getZ() { return (int) m_position.getZ(); }
	
	public boolean equals(Position b, int locationBase, int accuracyLevel) {
		if (locationBase == LOCATIONBASE25)  {
			return (locationByBase25.substring(0, accuracyLevel - 1).
					compareTo(b.getLocationByBase25().substring(0, accuracyLevel - 1)) == 0 );
		}
		if (locationBase == LOCATIONBASE9)  {
			return (Integer.toString(locationByBase9).substring(0, accuracyLevel - 1).
					compareTo(Integer.toString(b.getLocationByBase9()).substring(0, accuracyLevel - 1)) == 0 );
		}

		return equals(b);
	}
	
	public boolean equals(Position b) {
		return m_position.equals(b.getVector());
	}
	
	private void setLocationByBases(Vector position)
	{
		this.locationByBase25 = getLocationByBase(position, LOCATIONBASE25);
		
		String locationByBase9 = getLocationByBase(position, LOCATIONBASE9);
		this.locationByBase9 = Integer.parseInt( locationByBase9 ); 
	}

	private String getLocationByBase(Vector position, int base)
	{ 
		String locationString = "";
		int baseSquareRoot;
		
		int i;
		int sectorX;
		int sectorY;
		int sector;
		
		int max_xy = 2 * TRANSLATE;
		
		float rangeSector;
		
		float x = position.getX() + TRANSLATE;
		float y = position.getY() + TRANSLATE;
		
		rangeSector = max_xy;

		baseSquareRoot = (int) Math.sqrt(base);
		
		for (i = 0; i < LOCATIONBYBASEMAXLENGTH; i++) {
			
			rangeSector = rangeSector / baseSquareRoot;
			
			sectorX = (int) ( x / rangeSector ) + 1;
			x = x % rangeSector;
			
			sectorY = (int) ( y / rangeSector );
			y = y % rangeSector;
			
			sector = sectorY * baseSquareRoot + sectorX;
			
			locationString = locationString + getSectorCode(sector, base) ;
		}
		
		return locationString;
		
	}
	
	private String getSectorCode(int sector, int base ) {
		
		if (base == LOCATIONBASE9) return Integer.toString(sector);
		
		if (base == LOCATIONBASE25) 
			// 64 + 1 --> "A", ... , 64 + 25 --> "Y"
			return String.valueOf(Character.toChars(64 + sector  ));
		else
			return Integer.toString(0);
		
	}
	
	public String toString() {
		return m_position.toString();
	}
}
