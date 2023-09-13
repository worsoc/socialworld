/*
* Social World
* Copyright (C) 2022  Mathias Sikos
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

import org.socialworld.calculation.Value;
import org.socialworld.conversation.Lexem;

public class KnowledgeFactAtom {

	private KnowledgeFactAtom_Type type;
	
	private Value value = null;
	private Lexem lexem = null;
	
	private List<String> notes;
	
	private KnowledgeFactAtom(Value value) {
		this.type = KnowledgeFactAtom_Type.value;
		this.value = value;
		this.notes = new ArrayList<String>();
	}
	
	private KnowledgeFactAtom(Value value, String note) {
		this.type = KnowledgeFactAtom_Type.value;
		this.value = value;
		this.notes = new ArrayList<String>();
		this.notes.add(note);
	}
	
	private KnowledgeFactAtom(Value value, List<String> notes) {
		this.type = KnowledgeFactAtom_Type.value;
		this.value = value;
		this.notes = notes;
	}

	
	public KnowledgeFactAtom(Lexem lexem) {
		this.type = KnowledgeFactAtom_Type.lexem;
		this.lexem = lexem;
		this.notes = new ArrayList<String>();
	}
	
	public KnowledgeFactAtom(Lexem lexem, String note) {
		this.type = KnowledgeFactAtom_Type.lexem;
		this.lexem = lexem;
		this.notes = new ArrayList<String>();
		this.notes.add(note);
	}
	
	public KnowledgeFactAtom(Lexem lexem, List<String> notes) {
		this.type = KnowledgeFactAtom_Type.lexem;
		this.lexem = lexem;
		this.notes = notes;
	}

	
	public KnowledgeFactAtom_Type getType() {
		return this.type;
	}
	
	public Value getValue() {
		return this.value;
	}

	public Lexem getLexem() {
		return this.lexem;
	}
	
	public boolean hasNotes() {
		return (notes.size() > 0);
	}
	
	public List<String> removeNotes() {
		List<String> notes = new ArrayList<String>(this.notes);
		this.notes.clear();
		return notes;
	}
	
	boolean equals(KnowledgeFactAtom b) {
		
		// !!! notes are ignored for checking whether two knowledge fact atoms are equal
		
		boolean isEqual = (this.type == b.type);
		
		if (isEqual) {
			
			switch(this.type) {
			case value: 
				isEqual = this.value.equals(b.value); break;
			case lexem:
				isEqual = this.lexem.equals(b.lexem); break;
			default:
				isEqual = false;
				
			}
		}
		
		return isEqual;
	}

	public String toString() {
		String output = "";
		if (value == null && lexem == null) output = "(---)";
		else {
			output = output + "(" + type.toString() + ": ";
			switch (type) {
			case value:	
				output = output + (value == null ? "" : value.toString());
			case lexem:	
				output = output + (lexem == null ? "" : lexem.toString());
			}
			output = output + ")";
		}
		return output;
	}
		
}
