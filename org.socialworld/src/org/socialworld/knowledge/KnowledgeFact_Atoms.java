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

import java.util.ArrayList;
import java.util.List;

import org.socialworld.conversation.Lexem;

public class KnowledgeFact_Atoms {
	private List<KnowledgeFactAtom> atoms;
	
	public KnowledgeFact_Atoms(List<KnowledgeFactAtom> atoms) {
		this.atoms = atoms;
	}

	public static List<KnowledgeFactAtom> translateToAtoms(List<Lexem> lexems) {
		List<KnowledgeFactAtom> atoms = new ArrayList<KnowledgeFactAtom>();
		for (Lexem lexem : lexems) {
			atoms.add(new KnowledgeFactAtom(lexem));
		}
		return atoms;
	}
	
	KnowledgeFact_Atoms(KnowledgeFact_Atoms original) {
		
		List<KnowledgeFactAtom> originalAtoms = original.getAtoms();
		this.atoms = new ArrayList<KnowledgeFactAtom>();
		
		for (KnowledgeFactAtom atom : originalAtoms) {
			this.atoms.add(atom);
		}
				
	}

	List<KnowledgeFactAtom> getAtoms() {
		return this.atoms;
	}
	

	List<Lexem> getLexems() {
		List<Lexem> lexems = new ArrayList<Lexem>();
		for (KnowledgeFactAtom atom : this.atoms) {
			if (atom.getType() == KnowledgeFactAtom_Type.lexem) {
				lexems.add(atom.getLexem());
			}
		}
		return lexems;
	}
	
	KnowledgeItemNotes removeNotes() {
		
		KnowledgeItemNotes notes = new KnowledgeItemNotes(this.atoms.size());
		int index = 0;
		
		for (KnowledgeFactAtom atom : this.atoms) {
			List<String> atomsNotes = atom.removeNotes();
			for (String atomsNote : atomsNotes) {
				notes.addNoteToIndex(index, atomsNote);
			}
			index++;
		}
		return notes;
	}


	protected boolean equals(KnowledgeFact_Atoms b) {
		return this.atoms.equals(b.atoms);
	}
}
