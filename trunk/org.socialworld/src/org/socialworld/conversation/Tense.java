package org.socialworld.conversation;

public enum Tense {
	 simple_present_active,  simple_present_passive, 
	 simple_past_active, simple_past_passive,
	 present_perfect_active, present_perfect_passive,
	 past_perfect_active,  past_perfect_passive,
	 will_future_active, will_future_passive,
	 going_to_future_active, going_to_future_passive,
	 ignore;
	 
	 
	//
//		private int arrayIndex;
	//
//		private Tense(int index) {
//			this.arrayIndex = index;
//		}
	//
//		/**
//		 * The method returns the  name which belongs to the parameter index.
//		 * 
//		 * @param arrayIndex
//		 *            word type index
//		 * @return word type name
//		 */
//		public static Tense getName(int arrayIndex) {
//			for (Tense type : Tense.values())
//				if (type.arrayIndex == arrayIndex)
//					return type;
//			return null;
//		}

		public Word_Type getWordType() {
			
			switch (this) {
			case simple_present_active: return Word_Type.finitive;
			case simple_present_passive:  return Word_Type.past_participle;
			case simple_past_active: return Word_Type.simple_past;
			case simple_past_passive: return Word_Type.past_participle;
			case present_perfect_active: return Word_Type.past_participle;
			case present_perfect_passive: return Word_Type.past_participle;
			case past_perfect_active: return Word_Type.past_participle;
			case past_perfect_passive: return Word_Type.past_participle;
			case will_future_active: return Word_Type.infinitive;
			case will_future_passive: return Word_Type.past_participle;
			case going_to_future_active: return Word_Type.infinitive;
			case going_to_future_passive: return Word_Type.past_participle;
			default: return Word_Type.ignore;
			}
		}
}
