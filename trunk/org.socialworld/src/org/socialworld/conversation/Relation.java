package org.socialworld.conversation;


public enum Relation {

	
		carry(1001),
		
		unknown(0);
	
		private int index;

		private Relation(int index) {
			this.index = index;
		}

		
		public int getIndex() {
			return index;
		}

		public static Relation getName(int index) {
			for (Relation prop : Relation.values())
				if (prop.index == index)
					return prop;
			return unknown;
		}
		

}
