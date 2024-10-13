/*
* Social World
* Copyright (C) 2015  Mathias Sikos
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
package org.socialworld.attributes.properties;

import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.functions.FunctionMXplusN;
import org.socialworld.collections.ValueArrayList;

/**
 * German:
 * Die Klasse WeaponProperty beschreibt Eigenschaften von Simulationsobjekten
 *   hinsichtlich ihrer Verwendung als Waffe.
 *   
 * Diese Eigenschaften sind:
 * - Masse
 * - Härte(grad)
 * - Schärfe
 * 
 *   Anhand dieser Eigenschaften wird die Wirkung der Waffe auf das Ziel des Angriffs ermittelt.
 *   
 *   Außerdem führt die Klasse eine lineare Funktion, 
 *     mit der (in Abhängigkeit der intensität des Angriffs) 
 *     die Überwindung des (Rüstungsschutzes) des Zielobjektes ermittelt werden kann.
 *     Nur Wenn der Schwellwert des Schutzes des Ziels überwunden ist, 
 *       kann die Waffe ihre Wirkung auf das Ziel entfalten.
 *       
 *   Die Methode  getOvercomeThresholdForIntensity(float intensity) liefert anhand der Intensität den Schwellwert,
 *    der überwunden wird.
 *   Wenn das Ziel also einen Schutz kleiner gleich des Ergbnissees der Funktion hat, gilt der Rüstungsscutz als überwunden.
 *  
 *   Alternativ zur Berechnung des Schwellwertes hier, kann auch die Funktion selnbst erfragt werden.
 *   Diese wird mit der Methode getFunctionForThresholdOvercomeProtection() zurückgegeben.
 *      
 *   Mit der Methode getAsValues() werden die Eigenschaften Masse,Härte und Schärfe 
 *       als ValueArrayList und damit als Argumente für Berechnungsmethoden der Berechnungsklassen bereitgestellt.
 *           
 * @author Mathias Sikos
 *
 */
public class WeaponProperty {
	private float mass;
	private float hardness;
	private float sharpness;
	
	private FunctionMXplusN thresholdOvercomeProtectionByIntensity;
	
	public WeaponProperty(float mass, float hardness, float sharpness,
			FunctionMXplusN overcomeProtection) {
		
		this.mass = mass;
		this.hardness = hardness;
		this.sharpness = sharpness;
		
		this.thresholdOvercomeProtectionByIntensity = overcomeProtection;
	}
	
	public WeaponProperty(FunctionMXplusN overcomeProtection) {
		
		this.thresholdOvercomeProtectionByIntensity = overcomeProtection;
	}
	
	public FunctionMXplusN getFunctionForThresholdOvercomeProtection() {
		return this.thresholdOvercomeProtectionByIntensity;
	}
	
	public float getMass() { return this.mass; }
	public float getHardness() { return this.hardness; }
	public float getSharpness() { return this.sharpness; }

	public ValueArrayList getAsValues() {
		ValueArrayList result;
		
		result = new ValueArrayList();
		
		result.add( new Value(Type.floatingpoint, Value.VALUE_BY_NAME_WEAPON_MASS, mass));
		result.add( new Value(Type.floatingpoint, Value.VALUE_BY_NAME_WEAPON_HARDNESS, hardness));
		result.add( new Value(Type.floatingpoint, Value.VALUE_BY_NAME_WEAPON_SHARPNESS, sharpness));
		
		return result;
	}
	
	public float getOvercomeThresholdForIntensity(float intensity) {
		return thresholdOvercomeProtectionByIntensity.calculateFloatingPoint(intensity);
	}
	
	
}
