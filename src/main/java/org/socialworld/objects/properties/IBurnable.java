/*
* Social World
* Copyright (C) 2019  Mathias Sikos
* Written with the assistance of Claude (Anthropic), 2026
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
package org.socialworld.objects.properties;

/**
 * A simulation object that can catch fire. Since 2026-11-26 (concept value and protection from the material,
 * step 6) no marker any more: the answers come from the materials of the instance and their ignition temperature
 * (Item calculates them; a class may override). An object of a class that can burn may still be made of
 * something that does not (a crown of gold): then it never ignites.
 */
public interface IBurnable {

	public static final String NAME = "IBurnable";

	/**
	 * @param temperature the temperature in degrees Celsius
	 * @param seconds how long the object is exposed to it
	 * @return the probability that the object catches fire, 0 .. 1
	 */
	public abstract double getIgnitionProbability(float temperature, float seconds);

	/**
	 * @return whether at least one material of the object can burn
	 */
	public abstract boolean canBurn();

}
