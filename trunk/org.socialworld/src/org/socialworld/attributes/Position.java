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

import java.util.List;

import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.tools.StringTupel;

/**
 * The class holds information about the position
 *
 * German:
 * Position ist die Klasse zur Angabe von Positionen f�r
 *   - Simulationsobjekte (�ber den Zustand des Objektes: StateSimulationObject)
 *   - Ereignisse (Event)
 *   
 * Die Position wird angegeben �ber
 *  a) einen Vektor mit den Koordinaten (x,y,z)
 *  b) eine Zahl zur Beschreibung des Pfades durch einen Suchbaum mit Basis 9
 *  c) eine Zeichenkette (Folge von Buchstaben) zur Beschreibung des Pfades durch einen Suchbaum mit Basis 25
 *  
 *  Die Angaben zum Suchbaum werden bei Setzen/�ndern des Vektors angepasst.
 *  
 *  Damit ist der Vektor die eigentliche Positionsangabe.
 *  
 *  Die beiden Varianten zum Suchbaum dienen der schnellen Findung von Objekten 
 *    �ber eine Teilfolge der Zahlen- bzw. Buchstabenfolge.
 *     (f�r weitere Informationen zum Suchbaum siehe bitte in Klasse ObjectByPositionSearch)
 *  Die Angaben zum Suchbaum werden also nicht gesetzt, sondern anhand des Vektors ermittelt.
 *  
 *  Neben der set-Methode zum Setzen des Vektors und 
 *    den Get-Methoden f�r die drei Varianten der Positionsangabe stellt die Klasse folgende Methoden zur Verf�gung:
 *  -   equals() zur Pr�fung auf Gleichheit �ber den Vektor
 *  -   equals() zur Pr�fung auf Gleichheit �ber den Suchbaumpfad
 *           hier wird in den Argumenten angegeben welche Basis (9 oder 25) und welche Genauigkeit (wieviele Schritte durch den Suchbaum) verwendet werden soll 
 *  -   getDistance() zur Berechnung des Abstandes (also L�nge des Weges zu) einer zweiten Position
 *  -   getDirectionFrom() zur Ermittlung eines Richtungsverktors von einer zweiten Position zur "eigenen"
 *  -   getDirectionTo() zur Ermittlung eines Richtungsverktors von der "eigenen" zu einer zweiten Position hin
 *  -   getX(), getY() und getZ() zur Abfrage der einzenen Koordianten desVektors
 *  
 * @author Mathias Sikos (tyloesand)  
 */
public class Position extends SimProperty {

	public final int LOCATIONBYBASEMAXLENGTH = 9;
	public final int TRANSLATE = 2000000;
	public static final int LOCATIONBASE9 = 9;
	public static final int LOCATIONBASE25 = 25;
	

	private SVVector vector;  // (x,y,z) millimeters
	
	private int locationByBase9;
	private String locationByBase25;

	
///////////////////////////////////////////////////////////////////////////////////////////
//////////////////	static instance for meta information    ///////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	private static StringTupel[] propertiesMetaInfos = new StringTupel[]{
				new StringTupel(new String[] {"SVVector", PropertyName.position_vector.name(), PropertyName.position_vector.toString()})
				};

	public static List<StringTupel> getPropertiesMetaInfos() {
		List<StringTupel> listOfPropertyMetaInfo = SimProperty.getPropertiesMetaInfos();
		for (int indexAdd = 0; indexAdd < propertiesMetaInfos.length; indexAdd++) {
			listOfPropertyMetaInfo.add(propertiesMetaInfos[indexAdd]);
		}
		return listOfPropertyMetaInfo;
	}


///////////////////////////////////////////////////////////////////////////////////////////
////////////////// creating instance for simulation    ///////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	// TODO Herleitung  property name (position's vector)

	public Position(PropertyName prop, Vector position) {
		super();
		setPropertyName(prop);
		this.vector =  new SVVector(position, prop);
		
		setLocationByBases(position);
	}
	
	public Position(Type propertyType, Position position) {
		// TODO constructor Position switching sim property type
		setPropertyName(position.getPropertyName().toType(propertyType));
		this.vector =  position.getVector();
		
		locationByBase9 = position.getLocationByBase9();
		locationByBase25 = position.getLocationByBase25();
	}

	private Position(Position original, PropertyProtection protectionOriginal, SimulationCluster cluster ) {
		super(protectionOriginal, cluster);
		setPropertyName(original.getPropertyName());
		this.vector =  original.getVector();
		
		locationByBase9 = original.getLocationByBase9();
		locationByBase25 = original.getLocationByBase25();
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    ISavedValues  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public SimProperty copyForProperty(SimulationCluster cluster) {
		return new Position(this, getPropertyProtection(), cluster);
	}

	public  ValueProperty getProperty(SimulationCluster cluster, PropertyName prop, String valueName) {
		switch (prop) {
		case position_vector:
			return this.vector.getAsValue(cluster, valueName);
		default:
			return ValueProperty.getInvalid();
		}

	}

	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    Position  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////


	public int getLocationByBase9() { return locationByBase9; }
	public String getLocationByBase25() { return locationByBase25 ; }

	public final Vector getVector(SimulationCluster cluster) {
		SVVector copy = (SVVector) this.vector.copyForProperty(cluster);
		Vector released = copy.getReleased(cluster);
		return released;
	}
	
	private SVVector getVector() {
		return (SVVector) this.vector.copyForProperty(getPropertyProtection().getCluster());
	}
			
	
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
		Vector direction = this.vector.getDirectionFrom(position.getVector());
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
		Vector direction = position.getVector().getDirectionFrom(this.vector);
		return direction;
	}


	public int getX(SimulationCluster cluster) { 
		return  this.vector.getX(cluster); 
	}
	public int getY(SimulationCluster cluster) { 
		return  this.vector.getY(cluster); 
	}
	public int getZ(SimulationCluster cluster) { 
		return  this.vector.getZ(cluster); 
	}
	
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
		return this.vector.equals(b.getVector());
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
		return this.vector.toString();
	}
}
