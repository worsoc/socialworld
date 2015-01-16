/*
* Social World
* Copyright (C) 2015  Mathias Sikos
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
package org.socialworld.core;


/**
 * @author Mathias Sikos
 *
 */
public enum EventType {
	
//////////////////////////
// EventByAction	
//////////////////////////
	
	sleepCaused(0),	sleepIntentioned(1),
	
	moveWalk(8), moveRun(9), moveSneak(10), moveJump(11), moveSwim(12), moveFly(13),
	
	examineByLook(16), examineBySmell(17), examineByTaste(18), examineByTouch(19),
	
	touchByHand(24), touchByFoot(25),
	
	inventoryTake(32), inventoryCollect(33), inventoryDrop(34), inventorySwitch(35),
	
	handleItemUse2(40), handleItemUseLeft(41), handleItemUseRight(42), handleItemAddRtoL(43), handleItemAddLtoR(44), handleItemPull(45), handleItemPush(46),
	
	weaponLeftStab(48), weaponLeftStroke(49), weaponLeftClub(50), weaponLeftBackhand(51),

	weaponRightStab(56), weaponRightStroke(57), weaponRightClub(58), weaponRightBackhand(59),

	listenToStatement(64), listenToQuestion(65), listenToInstruction(66), understand(67),
	
	askNormal(72), askScream(73), askWhisper(74), answerNormal(75), answerScream(76), answerWhisper(77),
	
	sayNormal(80), sayScream(81), sayWhisper(82),

//////////////////////////
//EventSelfByAction	
//////////////////////////

	selfSleepCaused(128),	SelfSleepIntentioned(129),
	
	SelfmoveWalk(136), SelfmoveRun(137), SelfmoveSneak(138), SelfmoveJump(139), SelfmoveSwim(140), SelfmoveFly(141),
	
	SelfexamineByLook(144), SelfexamineBySmell(145), SelfexamineByTaste(146), SelfexamineByTouch(147),
	
	SelftouchByHand(152), SelftouchByFoot(153),
	
	SelfinventoryTake(160), selfinventoryCollect(161), selfinventoryDrop(162), selfinventorySwitch(163),
	
	selfhandleItemUse2(168), selfhandleItemUseLeft(169), selfhandleItemUseRight(170), selfhandleItemAddRtoL(171), selfhandleItemAddLtoR(172), selfhandleItemPull(173),selfHandleItemPush(174),
	
	selfweaponLeftStab(176), selfWeaponLeftStroke(177), selfweaponLeftClub(178), selfweaponLeftBackhand(179),

	selfweaponRightStab(184), selfweaponRightStroke(185), selfweaponRightClub(186), selfweaponRightBackhand(187),

	selflistenToStatement(192), selflistenToQuestion(193), selflistenToInstruction(194), selfunderstand(195),
	
	selfaskNormal(200), selfaskScream(201), selfaskWhisper(202), selfanswerNormal(203), selfanswerScream(204), selfanswerWhisper(205),
	
	selfsayNormal(208), selfsayScream(209), selfsayWhisper(210);

	public static final int MAX_EVENT_TYPE = 256;

	private int index;

	private EventType(int index) {
		this.index = index;
	}

	/**
	 * The method returns the event type name which belongs to the parameter  index.
	 * 
	 * @param index
	 *            event type index
	 * @return event type name
	 */
	public static EventType getEventType(int index) {
		for (EventType type : EventType.values())
			if (type.index == index)
				return type;
		return null;
	}
	
	
	/**
	 * The method returns the index of event type.
	 * 
	 * @return event type index
	 */
	public int getIndex() {
		return index;
	}

	
	
	public float getEffectDistance() {
		switch (this) {
		case touchByHand:
			return 100.0F;
		case weaponLeftStab:
			return 100.0F;
		case weaponRightStab:
			return 100.0F;
		default:
			return 10000.0F;
		}
	}

	public float getEffectAngle() {
		switch (this) {
		case touchByHand:
			return 45.0F;
		case weaponLeftStab:
			return 45.0F;
		case weaponRightStab:
			return 45.0F;
		default:
			return 360.0F;
		}
	}
	
	
}
