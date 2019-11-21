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

	nothing(0),

//////////////////////////
// EventToCandidates	
//////////////////////////
	
	candidatesSleep(1), candidatesDrink(2), candidatesEat(3), candidatesPiss(4), candidatesShit(5), 	
	
	candidatesMoveWalk(8), candidatesMoveRun(9), candidatesMoveSneak(10), candidatesMoveJump(11), candidatesMoveSwim(12), candidatesMoveFly(13),
	
	candidatesExamineByLook(16), candidatesExamineBySmell(17), candidatesExamineByTaste(18), candidatesExamineByTouch(19),
	
	candidatesTouchByHand(24), candidatesTouchByFoot(25),
	
	candidatesInventoryTake(32), candidatesInventoryDrop(33), candidatesInventorySwitch(34), candidatesInventorySet(35), candidatesInventoryGet(36),
	
	candidatesHandleItemUse2(40), candidatesHandleItemUseLeft(41), candidatesHandleItemUseRight(42), candidatesHandleItemAddRtoL(43), candidatesHandleItemAddLtoR(44), candidatesHandleItemPull(45), candidatesHandleItemPush(46),
	
	candidatesWeaponLeftStab(48), candidatesWeaponLeftStroke(49), candidatesWeaponLeftBackhand(50), candidatesWeaponRightStab(51), candidatesWeaponRightStroke(52), candidatesWeaponRightBackhand(53), candidatesWeaponClub(54),

	candidatesPunchLeftFistStraight(56), candidatesPunchLeftFistSideways(57), candidatesPunchLeftFistUpward(58), candidatesPunchRightFistStraight(59), candidatesPunchRightFistSideways(60), candidatesPunchRightFistUpward(61),
	
/*	candidatesListenToStatement(64), candidatesListenToQuestion(65), candidatesListenToInstruction(66), candidatesUnderstand(67), */
	
/*	candidatesAskNormal(72), candidatesAskScream(73), candidatesAskWhisper(74), candidatesAnswerNormal(75), candidatesAnswerScream(76), candidatesAnswerWhisper(77), */
	
	candidatesSayNormal(80), candidatesSayScream(81), candidatesSayWhisper(82),
	
//////////////////////////////////////////////////////////////////////
//EventToCauser	(event with influence to causer itself)
//////////////////////////////////////////////////////////////////////

	selfSleep(129), selfDrink(130), selfEat(131), selfPiss(132), selfShit(133), 	
	
	selfMoveWalk(136), selfMoveRun(137), selfMoveSneak(138), selfMoveJump(139), selfMoveSwim(140), selfMoveFly(141),
	
	selfExamineByLook(144), selfExamineBySmell(145), selfExamineByTaste(146), selfExamineByTouch(147),
	
	selfTouchByHand(152), selfTouchByFoot(153),
	
	selfInventoryTake(160), selfInventoryDrop(161), selfInventorySwitch(162), selfInventorySet(163), selfInventoryGet(164),
	
	selfHandleItemUse2(168), selfHandleItemUseLeft(169), selfHandleItemUseRight(170), selfHandleItemAddRtoL(171), selfHandleItemAddLtoR(172), selfHandleItemPull(173),selfHandleItemPush(174),
	
	selfWeaponLeftStab(176), selfWeaponLeftStroke(177), selfWeaponLeftBackhand(178), selfWeaponRightStab(179), selfWeaponRightStroke(180), selfWeaponRightBackhand(181), selfWeaponClub(182),

	selfPunchLeftFistStraight(184),  selfPunchLeftFistSideways(185), selfPunchLeftFistUpward(186), selfPunchRightFistStraight(187),  selfPunchRightFistSideways(188), selfPunchRightFistUpward(189),

	selfListenToStatement(192), selfListenToQuestion(193), selfListenToInstruction(194), selfUnderstand(195),
	
	selfAskNormal(200), selfAskScream(201), selfAskWhisper(202), selfAnswerNormal(203), selfAnswerScream(204), selfAnswerWhisper(205),
	
	selfSayNormal(208), selfSayScream(209), selfSayWhisper(210),

//////////////////////////////////////////////////////////////////////
//EventToTargets	(event with influence to explicit involved objects (targets, items ...)
//////////////////////////////////////////////////////////////////////
	
/*	targetSleep(257),*/ targetDrink(258), targetEat(259), /* targetPiss(260), targetShit(261),*/ 	
	
/*	targetMoveWalk(264), targetMoveRun(265), targetMoveSneak(266), targetMoveJump(267), targetMoveSwim(268), targetMoveFly(269), */
	
	targetExamineByLook(272), targetExamineBySmell(273), targetExamineByTaste(274), targetExamineByTouch(275),
	
	targetTouchByHand(280), targetTouchByFoot(281),
	
	targetInventoryTake(288), targetInventoryDrop(289), targetInventorySwitch(290), targetInventorySet(291), targetInventoryGet(292),
	
	targetHandleItemUse2(296), targetHandleItemUseLeft(297), targetHandleItemUseRight(298), targetHandleItemAddRtoL(299), targetHandleItemAddLtoR(300), targetHandleItemPull(301),targetHandleItemPush(302),
	
	targetWeaponLeftStab(304), targetWeaponLeftStroke(305), targetWeaponLeftBackhand(306), targetWeaponRightStab(307), targetWeaponRightStroke(308), targetWeaponRightBackhand(309), targetWeaponClub(310),

	targetPunchLeftFistStraight(312),  targetPunchLeftFistSideways(313), targetPunchLeftFistUpward(314), targetPunchRightFistStraight(315),  targetPunchRightFistSideways(316), targetPunchRightFistUpward(317),

/*	targetListenToStatement(320), targetListenToQuestion(321), targetListenToInstruction(322), targetUnderstand(323),*/
	
	targetAskNormal(328), targetAskScream(329), targetAskWhisper(330), targetAnswerNormal(331), targetAnswerScream(332), targetAnswerWhisper(333),
	
/*	targetSayNormal(336), targetSayScream(337), targetSayWhisper(338) */ ;
	
	public static final int MAX_EVENT_TYPE =  384;

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

	public boolean isEventToCauserItself() {
		
		if (this.index > 127  & this.index < 256) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean isRelevantForEffectiveCheck() {
		
		// pull and push with effective check
		if (this.index == 301 || this.index == 302) return true;
		// touch with effective check
		if (this.index == 280 || this.index == 281) return true;
		// talk with effective check
		if (this.index >= 328 && this.index == 333) return true;
		
		// the following with noeffective check
		if (this.index > 127) return false;
		if (this.index < 8) return false;
		if (this.index >= 16 & this.index < 24) return false;

		// the rest with effective check
		return true;
		
	}
	
	public float getEffectDistance() {
		switch (this) {
		// TODO getEffectDistance()
		case candidatesMoveWalk: return 5000.0F;
		case candidatesMoveRun: return 10000.0F;
		case candidatesMoveSneak: return 1000.0F; 
		case candidatesMoveJump: return 3000.0F;
		case candidatesMoveSwim: return 3000.0F;
		case candidatesMoveFly:  return 5000.0F;
			
		case candidatesTouchByHand: return 1000.0F; 
		case candidatesTouchByFoot: return 1000.0F; 
				
		case candidatesInventoryGet:
		case candidatesInventorySet:
		case candidatesInventoryTake:
		case candidatesInventoryDrop:
		case candidatesInventorySwitch:
			return 1000.0F;
			
		case candidatesHandleItemUse2: 
		case candidatesHandleItemUseLeft:
		case candidatesHandleItemUseRight:
		case candidatesHandleItemAddRtoL:
		case candidatesHandleItemAddLtoR:
		case candidatesHandleItemPull:
		case candidatesHandleItemPush:
			return 1000.0F; 
			
		case candidatesWeaponLeftStab:
		case candidatesWeaponLeftStroke:
		case candidatesWeaponLeftBackhand:
		case candidatesWeaponRightStab:
		case candidatesWeaponRightStroke:
		case candidatesWeaponRightBackhand:
		case candidatesWeaponClub:
			return 2000.0F; 
			
		case candidatesPunchLeftFistStraight:
		case candidatesPunchLeftFistSideways:
		case candidatesPunchLeftFistUpward:
		case candidatesPunchRightFistStraight:
		case candidatesPunchRightFistSideways:
		case candidatesPunchRightFistUpward:
			return 1000.0F; 
			
					
		case candidatesSayNormal: return 10000.0F; 
		case candidatesSayScream: return 50000.0F; 
		case candidatesSayWhisper: return 1000.0F; 

		default:
			return 1000000.0F;
		}
	}

	public float getEffectAngle() {
		switch (this) {
		
		case candidatesMoveWalk:
		case candidatesMoveRun:
		case candidatesMoveSneak:
		case candidatesMoveJump:
		case candidatesMoveSwim:
		case candidatesMoveFly:
			return 45.0F;
			
		case candidatesTouchByHand: 
		case candidatesTouchByFoot:
			return 45.0F;

		case candidatesInventoryGet:
		case candidatesInventorySet:
		case candidatesInventoryTake:
		case candidatesInventoryDrop:
		case candidatesInventorySwitch:
			return 360.0F;
			
		case candidatesHandleItemUse2:
		case candidatesHandleItemUseLeft:
		case candidatesHandleItemUseRight:
		case candidatesHandleItemAddRtoL:
		case candidatesHandleItemAddLtoR:
		case candidatesHandleItemPull:
		case candidatesHandleItemPush:
			return 360.0F;

		case candidatesWeaponLeftStab:
		case candidatesWeaponLeftStroke:
		case candidatesWeaponLeftBackhand:
		case candidatesWeaponRightStab:
		case candidatesWeaponRightStroke:
		case candidatesWeaponRightBackhand:
		case candidatesWeaponClub:
			return 45.0F;

		case candidatesPunchLeftFistStraight:
		case candidatesPunchLeftFistSideways:
		case candidatesPunchLeftFistUpward:
		case candidatesPunchRightFistStraight:
		case candidatesPunchRightFistSideways:
		case candidatesPunchRightFistUpward:
			return 45.0F;
				
		case candidatesSayNormal:
		case candidatesSayScream:
		case candidatesSayWhisper:
			return 360.0F;
		
		default:
			return 0.0F;
		}
	}
	
}

/*
nothing(0),

sleep(1), drink(2), eat(3), piss(4), shit(5), 	

moveWalk(8), moveRun(9), moveSneak(10), moveJump(11), moveSwim(12), moveFly(13),

examineByLook(16), examineBySmell(17), examineByTaste(18), examineByTouch(19),

touchByHand(24), touchByFoot(25),

inventoryTake(32), inventoryDrop(33), inventorySwitch(34), inventorySet(35), inventoryGet(36),

handleItemUse2(40), handleItemUseLeft(41), handleItemUseRight(42), handleItemAddRtoL(43), handleItemAddLtoR(44), handleItemPull(45), handleItemPush(46),

weaponLeftStab(48), weaponLeftStroke(49), weaponLeftBackhand(50), weaponRightStab(51), weaponRightStroke(52), weaponRightBackhand(53), weaponClub(54),

punchLeftFistStraight(56),  punchLeftFistSideways(57), punchLeftFistUpward(58), punchRightFistStraight(59),  punchRightFistSideways(60), punchRightFistUpward(61),

listenToStatement(64), listenToQuestion(65), listenToInstruction(66), understand(67),

askNormal(72), askScream(73), askWhisper(74), answerNormal(75), answerScream(76), answerWhisper(77),

sayNormal(80), sayScream(81), sayWhisper(82),
*/

