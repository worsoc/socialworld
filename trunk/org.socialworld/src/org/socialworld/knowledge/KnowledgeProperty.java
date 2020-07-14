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

import org.socialworld.conversation.Lexem;

public class KnowledgeProperty extends KnowledgeFact {

	private KnowledgeFact_Criterion criterion;
	private KnowledgeFact_Value value;
	
	public KnowledgeProperty(KnowledgeFact_Criterion criterion, KnowledgeFact_Value value ) {
		this.criterion = criterion;
		this.value = value;
	}
	
	protected KnowledgeProperty(KnowledgeProperty original) {
		if (original != null) {
			this.criterion  = original.getCriterion();
			this.value = new KnowledgeFact_Value(original.getValue());
			this.setSource(original.getSource());
			this.setValid(original.isItemValid());
			this.resetAccessCount();
		}
	}
	
	KnowledgeFact copy() {
		return new KnowledgeProperty(this);
	}
	
	Lexem[] getValues() {
		return this.value.getLexems();
	}
	
	protected KnowledgeFact_Value getValue() {
		return value;
	}
	
	protected KnowledgeFact_Criterion getCriterion() {
		return criterion;
	}
	
	protected boolean equals(KnowledgeProperty b) {
		return ( this.criterion == b.criterion & this.value.equals(b.value) );
	}

}
