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

public enum Tense {
	 simple_present_active(1),  simple_present_passive(2), 
	 simple_past_active(11), simple_past_passive(12),
	 present_perfect_active(21), present_perfect_passive(22),
	 past_perfect_active(31),  past_perfect_passive(32),
	 will_future_active(41), will_future_passive(42),
	 going_to_future_active(51), going_to_future_passive(52),
	 ignore(0);
	 
	 
	
		private int index;
	
		private Tense(int index) {
			this.index = index;
		}
	
		public int getIndex() {
			return index;
		}

		/**
		 * The method returns the  name which belongs to the parameter index.
		 * 
		 * @param index
		 *            word type index
		 * @return tense name
		 */
		public static Tense getName(int index) {
			for (Tense type : Tense.values())
				if (type.index == index)
					return type;
			return null;
		}

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
