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

import org.socialworld.conversation.Lexem;

public class KnowledgeFact_Value {
	private Lexem value;
	private Lexem lexemValue[];
	
	public KnowledgeFact_Value(Lexem value) {
		this.value = value;
		this.lexemValue = new Lexem[1];
		this.lexemValue[0] = value;
	}
	
	public KnowledgeFact_Value(KnowledgeFact_Value original) {
		this.value = original.getLexem();
		this.lexemValue = new Lexem[1];
		this.lexemValue[0] = original.getLexem();
	}
	
	Lexem[] getLexems() {
		return lexemValue;
	}
	
	protected Lexem getLexem() {
		return value;
	}


	protected boolean equals(KnowledgeFact_Value b) {
		return this.value.equals(b.value);
	}
}
