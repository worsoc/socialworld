package org.socialworld.conversation;

import org.socialworld.core.AllWords;

public enum Relation {

	
		carry(1001),
		
		ignore(0);


		private int index;
		
		private Lexem lexem = null;
		private Tense tense = Tense.ignore;
		
		private Relation(int index) {
			this.index = index;
		}

		
		public int getIndex() {
			return index;
		}

//		private final int FIRST_INDEX_PAST = 1000000; 
//		private final int FIRST_INDEX_FUTURE = 2000000; 

		public static Relation getName(int index) {
			for (Relation prop : Relation.values())
				if (prop.index == index)
					return prop;
			return ignore;
		}
		
		public static Relation getName(Lexem lexem, Tense tense) {
			return AllWords.getRelation(lexem.getLexemID(), tense.getIndex());
		}
		
		public Tense getTense() {
			
			if (this.tense == Tense.ignore && this != Relation.ignore) {
				Lexem lexem = null;
				Tense tense = null;
				AllWords.getLexemAndTense(this.index, lexem, tense);
				this.lexem = lexem;
				this.tense = tense;
			}
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

		public Lexem getLexem() {
			if (lexem == null && this != Relation.ignore) {
				Lexem lexem = null;
				Tense tense = null;
				AllWords.getLexemAndTense(this.index, lexem, tense);
				this.lexem = lexem;
				this.tense = tense;
			}

			return this.lexem;
		}
}
