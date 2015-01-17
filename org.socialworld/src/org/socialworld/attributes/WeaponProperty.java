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
package org.socialworld.attributes;

import org.socialworld.calculation.FunctionMXplusN;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;

/**
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

	public Value[] getAsArguments() {
		Value result[];
		
		result = new Value[3];
		
		result[0] = new Value(Type.floatingpoint, "mass", mass);
		result[1] = new Value(Type.floatingpoint, "hardness", hardness);
		result[2] = new Value(Type.floatingpoint, "sharpness", sharpness);
		
		return result;
	}
	
	public float getOvercomeThresholdForIntensity(float intensity) {
		return thresholdOvercomeProtectionByIntensity.calculateFloatingPoint(intensity);
	}
	
	
}
