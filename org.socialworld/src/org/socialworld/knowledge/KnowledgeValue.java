/*
* Social World
* Copyright (C) 2020  Mathias Sikos
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

import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;

public class KnowledgeValue extends KnowledgeAtom {

	private Value value;
	
	KnowledgeValue() {
		this.setValid(false);
		this.resetAccessCount();
	}
	
	public KnowledgeValue(Value value) {
		this.setValid(value.isValid());
		this.resetAccessCount();
		this.value = value;
	}
	
	private KnowledgeValue(KnowledgeValue original) {
		if (original != null) {
			this.setValid(original.isItemValid());
			this.resetAccessCount();
			// TODO copy value and source?
			this.value = original.value;
			this.setSource(original.getSource());

		}
	}

	KnowledgeAtom copy() {	
		return new KnowledgeValue(this);
	}
	
	Value getValue() {
		return this.value;
	}
	
	Type getType() {
		return this.value.getType();
	}
	
	String getName() {
		return this.value.getName();
	}


}
