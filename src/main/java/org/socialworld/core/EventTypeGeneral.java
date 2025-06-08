package org.socialworld.core;

import java.util.ArrayList;
import java.util.List;

public enum EventTypeGeneral {


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
	
	sayNormal(80), sayScream(81), sayWhisper(82);

	private int index;

	private EventTypeGeneral(int index) {
		this.index = index;
	}

	/**
	 * The method returns the general event type name which belongs to the parameter  index.
	 * 
	 * @param index
	 *            general event type index
	 * @return general event type name
	 */
	public static EventTypeGeneral getEventType(int index) {
		for (EventTypeGeneral type : EventTypeGeneral.values())
			if (type.index == index)
				return type;
		return nothing; // instead of null
	}
	
	public static EventTypeGeneral fromName(String name) {
		for (EventTypeGeneral type : EventTypeGeneral.values())
			if (type.toString().toUpperCase().equals(name.toUpperCase()))
				return type;
		return null;
	}

	public static List<String> getNameList() {
		List<String> nameList = new ArrayList<String>();
		for (EventTypeGeneral elem : EventTypeGeneral.values()) {
			if (elem == nothing) continue;
			nameList.add(elem.toString());
		}
		return nameList;
	}
	
	static EventTypeGeneral getGeneralEventType(EventType type) {
		switch (type) {
		case selfSleep:
		case candidatesSleep:
		case percipientSleep: return sleep;
		case selfDrink:
		case targetDrink:
		case candidatesDrink:
		case percipientDrink: return drink;
		case selfEat:
		case targetEat:
		case candidatesEat:
		case percipientEat: return eat;
		case selfPiss:
		case candidatesPiss:
		case percipientPiss: return piss;
		case selfShit:
		case candidatesShit:
		case percipientShit: return shit;
		case selfMoveWalk:
		case candidatesMoveWalk:
		case percipientMoveWalk: return moveWalk;
		case selfMoveRun:
		case candidatesMoveRun:
		case percipientMoveRun: return moveRun;
		case selfMoveSneak:
		case candidatesMoveSneak:
		case percipientMoveSneak: return moveSneak;
		case selfMoveJump:
		case candidatesMoveJump:
		case percipientMoveJump: return moveJump;
		case selfMoveSwim:
		case candidatesMoveSwim:
		case percipientMoveSwim: return moveSwim;
		case selfMoveFly:
		case candidatesMoveFly:
		case percipientMoveFly: return moveFly;
		case selfExamineByLook:
		case targetExamineByLook:
		case candidatesExamineByLook:
		case percipientExamineByLook: return examineByLook;
		case selfExamineBySmell:
		case targetExamineBySmell:
		case candidatesExamineBySmell:
		case percipientExamineBySmell: return examineBySmell;
		case selfExamineByTaste:
		case targetExamineByTaste:
		case candidatesExamineByTaste:
		case percipientExamineByTaste: return examineByTaste;
		case selfExamineByTouch:
		case targetExamineByTouch:
		case candidatesExamineByTouch:
		case percipientExamineByTouch: return examineByTouch;
		case selfTouchByHand:
		case targetTouchByHand:
		case candidatesTouchByHand:
		case percipientTouchByHand: return touchByHand;
		case selfTouchByFoot:
		case targetTouchByFoot:
		case candidatesTouchByFoot:
		case percipientTouchByFoot: return touchByFoot;
		case selfInventoryTake:
		case targetInventoryTake:
		case candidatesInventoryTake:
		case percipientInventoryTake: return inventoryTake;
		case selfInventoryDrop:
		case targetInventoryDrop:
		case candidatesInventoryDrop:
		case percipientInventoryDrop: return inventoryDrop;
		case selfInventorySwitch:
		case targetInventorySwitch:
		case candidatesInventorySwitch:
		case percipientInventorySwitch: return inventorySwitch;
		case selfInventorySet:
		case targetInventorySet:
		case candidatesInventorySet:
		case percipientInventorySet: return inventorySet;
		case selfInventoryGet:
		case targetInventoryGet:
		case candidatesInventoryGet:
		case percipientInventoryGet: return inventoryGet;
		case selfHandleItemUse2:
		case targetHandleItemUse2:
		case candidatesHandleItemUse2:
		case percipientHandleItemUse2: return handleItemUse2;
		case selfHandleItemUseLeft:
		case targetHandleItemUseLeft:
		case candidatesHandleItemUseLeft:
		case percipientHandleItemUseLeft: return handleItemUseLeft;
		case selfHandleItemUseRight:
		case targetHandleItemUseRight:
		case candidatesHandleItemUseRight:
		case percipientHandleItemUseRight: return handleItemUseRight;
		case selfHandleItemAddRtoL:
		case targetHandleItemAddRtoL:
		case candidatesHandleItemAddRtoL:
		case percipientHandleItemAddRtoL: return handleItemAddRtoL;
		case selfHandleItemAddLtoR:
		case targetHandleItemAddLtoR:
		case candidatesHandleItemAddLtoR:
		case percipientHandleItemAddLtoR: return handleItemAddLtoR;
		case selfHandleItemPull:
		case targetHandleItemPull:
		case candidatesHandleItemPull:
		case percipientHandleItemPull: return handleItemPull;
		case selfHandleItemPush:
		case targetHandleItemPush:
		case candidatesHandleItemPush:
		case percipientHandleItemPush: return handleItemPush;
		case selfWeaponLeftStab:
		case targetWeaponLeftStab:
		case candidatesWeaponLeftStab:
		case percipientWeaponLeftStab: return weaponLeftStab;
		case selfWeaponLeftStroke:
		case targetWeaponLeftStroke:
		case candidatesWeaponLeftStroke:
		case percipientWeaponLeftStroke: return weaponLeftStroke;
		case selfWeaponLeftBackhand:
		case targetWeaponLeftBackhand:
		case candidatesWeaponLeftBackhand:
		case percipientWeaponLeftBackhand: return weaponLeftBackhand;
		case selfWeaponRightStab:
		case targetWeaponRightStab:
		case candidatesWeaponRightStab:
		case percipientWeaponRightStab: return weaponRightStab;
		case selfWeaponRightStroke:
		case targetWeaponRightStroke:
		case candidatesWeaponRightStroke:
		case percipientWeaponRightStroke: return weaponRightStroke;
		case selfWeaponRightBackhand:
		case targetWeaponRightBackhand:
		case candidatesWeaponRightBackhand:
		case percipientWeaponRightBackhand: return weaponRightBackhand;
		case selfWeaponClub:
		case targetWeaponClub:
		case candidatesWeaponClub:
		case percipientWeaponClub: return weaponClub;
		case selfPunchLeftFistStraight:
		case targetPunchLeftFistStraight:
		case candidatesPunchLeftFistStraight:
		case percipientPunchLeftFistStraight: return punchLeftFistStraight;
		case selfPunchLeftFistSideways:
		case targetPunchLeftFistSideways:
		case candidatesPunchLeftFistSideways:
		case percipientPunchLeftFistSideways: return punchLeftFistSideways;
		case selfPunchLeftFistUpward:
		case targetPunchLeftFistUpward:
		case candidatesPunchLeftFistUpward:
		case percipientPunchLeftFistUpward: return punchLeftFistUpward;
		case selfPunchRightFistStraight:
		case targetPunchRightFistStraight:
		case candidatesPunchRightFistStraight:
		case percipientPunchRightFistStraight: return punchRightFistStraight;
		case selfPunchRightFistSideways:
		case targetPunchRightFistSideways:
		case candidatesPunchRightFistSideways:
		case percipientPunchRightFistSideways: return punchRightFistSideways;
		case selfPunchRightFistUpward:
		case targetPunchRightFistUpward:
		case candidatesPunchRightFistUpward:
		case percipientPunchRightFistUpward: return punchRightFistUpward;
		case selfListenToStatement: return listenToStatement;
		case selfListenToQuestion: return listenToQuestion;
		case selfListenToInstruction: return listenToInstruction;
		case selfUnderstand: return understand;
		case selfAskNormal:
		case targetAskNormal:
		case percipientAskNormal: return askNormal;
		case selfAskScream:
		case targetAskScream:
		case percipientAskScream: return askScream;
		case selfAskWhisper:
		case targetAskWhisper:
		case percipientAskWhisper: return askWhisper;
		case selfAnswerNormal:
		case targetAnswerNormal:
		case percipientAnswerNormal: return answerNormal;
		case selfAnswerScream:
		case targetAnswerScream:
		case percipientAnswerScream: return answerScream;
		case selfAnswerWhisper:
		case targetAnswerWhisper:
		case percipientAnswerWhisper: return answerWhisper;
		case selfSayNormal:
		case candidatesSayNormal: return sayNormal;
		case selfSayScream:
		case candidatesSayScream: return sayScream;
		case selfSayWhisper:
		case candidatesSayWhisper: return sayWhisper;
		
		}
		
		return nothing;
	}
	
}
