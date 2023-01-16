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
import org.socialworld.attributes.AttributeArray;
import org.socialworld.objects.Human;

public class Acquaintance {
	private Human human;
	
	AttributeArray attributes = AttributeArray.getObjectNothing();
	
	public Acquaintance(Human human){
		this.human = human;
		this.attributes = new AttributeArray(Acquaintance_Attribute.NUMBER_OF_ATTRIBUTES);
	}

	public Acquaintance(Human human, AttributeArray attributes){
		this.human = human;
		this.attributes = attributes;
	}

	// copy constructor
	public Acquaintance (Acquaintance original) {
		this.human = original.human;
		this.attributes = new AttributeArray(original.attributes);
	}
	
	protected boolean isValid() {
		return (human != null);
	}
	
	public boolean isAcquaintanceOfHuman(Human human) {
		return (human == this.human);
	}
	
	public boolean isAttributeValueGreaterThan(Acquaintance_Attribute attribute, int value) {
		if (attributes.get(attribute.getIndex()) > value) return true;
		else return false;
	}

	public boolean isAttributeValueLessThan(Acquaintance_Attribute attribute, int value) {
		if (attributes.get(attribute.getIndex()) < value) return true;
		else return false;
	}

	public boolean equals(Acquaintance b) {
		// we don't check the attributes!
		return this.human == b.human;
	}
}
