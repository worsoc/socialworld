/*
* Social World
* Copyright (C) 2023  Mathias Sikos
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
package org.socialworld.conversation;

import org.socialworld.core.AllWords;

public enum Relation {

	
		nothing(0, RelationCardinality.ignore, Tense.ignore),
		
		carry(1, RelationCardinality.binaer, Tense.simple_present_active);
		


		private int index;
		private RelationCardinality cardinality;
		private Tense tense;

		private Lexem lexem = null;

		private Relation(int index, RelationCardinality cardinality, Tense tense) {
			this.index = index;
			this.cardinality = cardinality;
			this.tense = tense;
		}

		
		public int getIndex() {
			return index;
		}

		public int getLexemID() {
			int lexemID;
			lexemID = this.index + Lexem.OFFSET_LEXEMID_RELATION;
			
			return lexemID;
		}

//		private final int FIRST_INDEX_PAST = 1000000; 
//		private final int FIRST_INDEX_FUTURE = 2000000; 

		public static Relation getName(int index) {
			for (Relation prop : Relation.values())
				if (prop.index == index)
					return prop;
			return nothing;
		}
		
		public static Relation getName(Lexem lexem, Tense tense) {
			return AllWords.getRelation(lexem.getLexemID(), tense.getIndex());
		}
		
		public Tense getTense() {
			
/*
			if (this.index > 0 && this.index < FIRST_INDEX_PAST) {
				return Tense.simple_present_active;
			}
			else if (this.index >= FIRST_INDEX_PAST && this.index < FIRST_INDEX_FUTURE) {
				return Tense.simple_past_active;
			}
			else if (this.index >= FIRST_INDEX_FUTURE) {
				return Tense.will_future_active;
			}
			return Tense.ignore;
*/
			return this.tense;
		}

		public RelationCardinality getCardinality() {
			return this.cardinality;
		}
		
		public Lexem getLexem() {
			if (this.lexem == null && this != Relation.nothing) {
				this.lexem = AllWords.getRelationsLexem(this.index);
			}

			return this.lexem;
		}
		
}
