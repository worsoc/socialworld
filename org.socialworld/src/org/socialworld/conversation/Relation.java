package org.socialworld.conversation;


public enum Relation {

	
		carry(1001),
		
		ignore(0);


		private int index;

		private Relation(int index) {
			this.index = index;
		}

		
		public int getIndex() {
			return index;
		}

		private final int FIRST_INDEX_PAST = 1000000; 
		private final int FIRST_INDEX_FUTURE = 2000000; 

		public static Relation getName(int index) {
			for (Relation prop : Relation.values())
				if (prop.index == index)
					return prop;
			return ignore;
		}
		
		public static Relation getName(Lexem lexem, Tense tense) {
			// TODO Knowledge getting relation for lexem and tense
			return ignore;
		}
		
		public Tense getTense() {
			
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
		}

		public int getLexemID() {
			// TODO KNOWLEDGE Relation.getLexemID()
			return 0;
		}
}
