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

import java.util.ArrayList;
import java.util.List;

import org.socialworld.attributes.properties.IEnumProperty;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.conversation.Lexem;

public class KnowledgeProperty extends KnowledgeFact {

	private KnowledgeFact_Criterion criterion;
	private KnowledgeFact_Atoms atoms;
	
	public KnowledgeProperty(Value criterion, ValueArrayList values ) {
		KnowledgeFactAtom kfa;
		this.criterion = KnowledgeFact_Criterion.getName( (int) criterion.getObject(Type.integer));
		List<KnowledgeFactAtom> atoms = new ArrayList<KnowledgeFactAtom>();
		for (int index =  0; index < values.size(); index++) {
			kfa = translateToKnowledgeFactAtom(values.get(index));
			if (kfa == null) continue;
			atoms.add(kfa);
		}
		this.atoms = new KnowledgeFact_Atoms(atoms);
	}

	public KnowledgeProperty(KnowledgeFact_Criterion criterion, KnowledgeFact_Atoms atoms ) {
		this.criterion = criterion;
		this.atoms = atoms;
	}
	
	protected KnowledgeProperty(KnowledgeProperty original) {
		if (original != null) {
			this.criterion  = original.getCriterion();
			this.atoms = new KnowledgeFact_Atoms(original.getAtoms());
		}
	}
	
	KnowledgeFact copy() {
		return new KnowledgeProperty(this);
	}
	
	List<KnowledgeFactAtom> getAtoms() {
		return atoms.getAtoms(); 
	}
	
	KnowledgeItemNotes removeNotes() {
		return atoms.removeNotes();
	}
	
	protected KnowledgeFact_Criterion getCriterion() {
		return criterion;
	}

	protected boolean isEqual(KnowledgeItem item) {
		if (item instanceof KnowledgeProperty) {
			KnowledgeProperty kp = (KnowledgeProperty)item;
			return this.criterion == kp.criterion && this.atoms.equals(kp.atoms);
		}
		return false;
	}
	
	
	protected KnowledgeFactAtom translateToKnowledgeFactAtom(Value value) {
		KnowledgeFactAtom result = null;
		Lexem lexem = translateToLexem(value);
		if (lexem != null) {
			result = new KnowledgeFactAtom(lexem);
		}
		return result;
	}

	protected Lexem translateToLexem(Value value) {
		Lexem result = null;
		if (value.getType() == Type.enumProp) {
			Object o = value.getObject(Type.enumProp);
			if (o instanceof IEnumProperty) {
				IEnumProperty enumProp = (IEnumProperty) o;
				result = enumProp.getLexem();
			}
			
		}
		return result;
	}

	public String toString() {
		String output = "";
		output = output + criterion.toString() + ":" + atoms.toString();
		return output;
	}

}
