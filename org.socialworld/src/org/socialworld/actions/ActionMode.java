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
package org.socialworld.actions;

/**
 * The enumeration holds all specialization modes of an action type. So there
 * are (for example) modes for the action type move and for the action
 * type handleItem.
 * 
 * @author Mathias Sikos (tyloesand)
 */
public enum ActionMode {
	
	ignore(0),
	
	// for ActionType sleep
	sleepIntentioned(1),	sleepCaused(2),
	
	// for ActionType move
	walk(11), run(12), sneak(13), jump(14), swim(15), fly(16), 

	// for ActionType examine
	look(21), smell(22), taste(23), touch(24),
	
	// for ActionType touch
	hand(31), foot(32),
	
	// for ActionType itemAndInventory
	takeItem(41), collectItem(42), dropItem(43), switchItemToOtherHand(44),

	// for ActionType handleItem
	useTwoItems(51),	useItemLeftHand(52), useItemRightHand(53),
	combineItems_AddRightToLeft(54), combineItems_AddLeftToRight(55),
	pull(56), push(57),

	// for ActionType useWeapon
	weaponLeftStab(61), weaponLeftStroke(62), weaponLeftBackhand(63), weaponRightStab(64), weaponRightStroke(65), weaponRightBackhand(66), weaponClub(67), 

	// for ActionType punch
	punchLeftFistStraight(71),  punchLeftFistSideways(72), punchLeftFistUpward(73), punchRightFistStraight(74),  punchRightFistSideways(75), punchRightFistUpward(76),
		
	// for ActionType hear
	listenTo(81), understand(82),

	// for ActionType talk
	answerNormal(91), answerScream(92), answerWhisper(93), askNormal(94), askScream(95), askWhisper(96),
	
	// for ActionType say
	normal(101), scream(102), whisper(103);
	
	private int index;

	private ActionMode(int index) {
		this.index = index;
	}
	
	public static ActionMode getName(int index) {
		for (ActionMode actionmode : ActionMode.values())
			if (actionmode.index == index)
				return actionmode;
		return null;
	}

	/**
	 * The method returns the index of the ActionMode.
	 * 
	 * @return action mode's index
	 */
	public int getIndex() {
		return index;
	}

}
