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
	
	nothing(0),
	
	sleep(1), drink(2), eat(3), piss(4), shit(5), 	
	
	moveWalk(8), moveRun(9), moveSneak(10), moveJump(11), moveSwim(12), moveFly(13),
	
	examineByLook(16), examineBySmell(17), examineByTaste(18), examineByTouch(19),
	
	touchByHand(24), touchByFoot(25),
	
	inventoryTake(32), inventoryCollect(33), inventoryDrop(34), inventorySwitch(35), inventoryPut(36),
	
	handleItemUse2(40), handleItemUseLeft(41), handleItemUseRight(42), handleItemAddRtoL(43), handleItemAddLtoR(44), handleItemPull(45), handleItemPush(46),
	
	weaponLeftStab(48), weaponLeftStroke(49), weaponLeftBackhand(50), weaponRightStab(51), weaponRightStroke(52), weaponRightBackhand(53), weaponClub(54),

	punchLeftFistStraight(56),  punchLeftFistSideways(57), punchLeftFistUpward(58), punchRightFistStraight(59),  punchRightFistSideways(60), punchRightFistUpward(61),
	
	listenToStatement(64), listenToQuestion(65), listenToInstruction(66), understand(67),
	
	askNormal(72), askScream(73), askWhisper(74), answerNormal(75), answerScream(76), answerWhisper(77),
	
	sayNormal(80), sayScream(81), sayWhisper(82),

//////////////////////////
//EventSelfByAction	
//////////////////////////

	selfSleep(129), selfDrink(130), selfEat(131), selfPiss(132), selfShit(133), 	
	
	selfMoveWalk(136), selfMoveRun(137), selfMoveSneak(138), selfMoveJump(139), selfMoveSwim(140), selfMoveFly(141),
	
	selfExamineByLook(144), selfExamineBySmell(145), selfExamineByTaste(146), selfExamineByTouch(147),
	
	selfTouchByHand(152), selfTouchByFoot(153),
	
	selfInventoryTake(160), selfInventoryCollect(161), selfInventoryDrop(162), selfInventorySwitch(163), selfInventoryPut(164),
	
	selfHandleItemUse2(168), selfHandleItemUseLeft(169), selfHandleItemUseRight(170), selfHandleItemAddRtoL(171), selfHandleItemAddLtoR(172), selfHandleItemPull(173),selfHandleItemPush(174),
	
	selfWeaponLeftStab(176), selfWeaponLeftStroke(177), selfWeaponLeftBackhand(178), selfWeaponRightStab(179), selfWeaponRightStroke(180), selfWeaponRightBackhand(181), selfWeaponClub(182),

	selfPunchLeftFistStraight(184),  selfPunchLeftFistSideways(185), selfPunchLeftFistUpward(186), selfPunchRightFistStraight(187),  selfPunchRightFistSideways(188), selfPunchRightFistUpward(189),

	selfListenToStatement(192), selfListenToQuestion(193), selfListenToInstruction(194), selfUnderstand(195),
	
	selfAskNormal(200), selfAskScream(201), selfAskWhisper(202), selfAnswerNormal(203), selfAnswerScream(204), selfAnswerWhisper(205),
	
	selfSayNormal(208), selfSayScream(209), selfSayWhisper(210);

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

	public boolean isRelevantForEffectiveCheck() {
		
		if (this.index > 127) return false;
		if (this.index < 8) return false;
		if (this.index >= 16 & this.index < 24) return false;

		return true;
		
	}
	
	public float getEffectDistance() {
		switch (this) {
		// TODO getEffectDistance()
		case moveWalk: return 5000.0F;
		case moveRun: return 10000.0F;
		case moveSneak: return 1000.0F; 
		case moveJump: return 3000.0F;
		case moveSwim: return 3000.0F;
		case moveFly:  return 5000.0F;
			
		case touchByHand: return 1000.0F; 
		case touchByFoot: return 1000.0F; 
				
		case inventoryTake:
		case inventoryCollect:
		case inventoryDrop:
		case inventorySwitch:
			return 1000.0F;
			
		case handleItemUse2: 
		case handleItemUseLeft:
		case handleItemUseRight:
		case handleItemAddRtoL:
		case handleItemAddLtoR:
		case handleItemPull:
		case handleItemPush:
			return 1000.0F; 
			
		case weaponLeftStab:
		case weaponLeftStroke:
		case weaponLeftBackhand:
		case weaponRightStab:
		case weaponRightStroke:
		case weaponRightBackhand:
		case weaponClub:
			return 2000.0F; 
			
		case punchLeftFistStraight:
		case punchLeftFistSideways:
		case punchLeftFistUpward:
		case punchRightFistStraight:
		case punchRightFistSideways:
		case punchRightFistUpward:
			return 1000.0F; 
			
		case listenToStatement:
		case listenToQuestion:
		case listenToInstruction:
		case understand:
			return 10000.0F;
			
		case askNormal: return 10000.0F; 
		case askScream: return 50000.0F; 
		case askWhisper: return 1000.0F; 
		case answerNormal: return 10000.0F; 
		case answerScream: return 50000.0F; 
		case answerWhisper: return 1000.0F; 
					
		case sayNormal: return 10000.0F; 
		case sayScream: return 50000.0F; 
		case sayWhisper: return 1000.0F; 

		default:
			return 1000000.0F;
		}
	}

	public float getEffectAngle() {
		switch (this) {
		
		case moveWalk:
		case moveRun:
		case moveSneak:
		case moveJump:
		case moveSwim:
		case moveFly:
			return 45.0F;
			
		case touchByHand: 
		case touchByFoot:
			return 45.0F;

		case inventoryTake:
		case inventoryCollect:
		case inventoryDrop:
		case inventorySwitch:
			return 360.0F;
			
		case handleItemUse2:
		case handleItemUseLeft:
		case handleItemUseRight:
		case handleItemAddRtoL:
		case handleItemAddLtoR:
		case handleItemPull:
		case handleItemPush:
			return 360.0F;

		case weaponLeftStab:
		case weaponLeftStroke:
		case weaponLeftBackhand:
		case weaponRightStab:
		case weaponRightStroke:
		case weaponRightBackhand:
		case weaponClub:
			return 45.0F;

		case punchLeftFistStraight:
		case punchLeftFistSideways:
		case punchLeftFistUpward:
		case punchRightFistStraight:
		case punchRightFistSideways:
		case punchRightFistUpward:
			return 45.0F;
				
		case listenToStatement:
		case listenToQuestion:
		case listenToInstruction:
		case understand:
			return 360.0F;
		
		case askNormal:
		case askScream:
		case askWhisper:
		case answerNormal:
		case answerScream:
		case answerWhisper:
			return 360.0F;
					
		case sayNormal:
		case sayScream:
		case sayWhisper:
			return 360.0F;
		
		default:
			return 0.0F;
		}
	}
	
	
}
