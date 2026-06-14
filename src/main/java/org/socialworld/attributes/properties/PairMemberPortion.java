/*
* Social World
* Copyright (C) 2026  Mathias Sikos
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

public class PairMemberPortion {
	private IEnumProperty property;
	private Integer portion;
	
	public PairMemberPortion(IEnumProperty property, Integer portion) {
		this.property = property;
		this.portion = portion;
	}
	
	private PairMemberPortion() {
	}
	
	
	public IEnumProperty getProperty() {
		return property;
	}

	public Integer getPortion() {
		return portion;
	}

	public PairMemberPortion copy() {
		PairMemberPortion copy = new PairMemberPortion();
		copy.property = property;
		copy.portion = portion;
		return copy;
	}
	
	public String toString() {
		return property.toString() + "(" + portion.toString() + ")";
	}

}
