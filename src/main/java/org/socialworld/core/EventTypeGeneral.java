package org.socialworld.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	
	private static final Map<String, EventTypeGeneral> NAME_CACHE = new HashMap<>();
	private static final Map<Integer, EventTypeGeneral> INDEX_CACHE = new HashMap<>();
	private static final Map<EventType, EventTypeGeneral> EVENT_TYPE_MAP = new HashMap<>();

	static {
		for (EventTypeGeneral type : values()) {
			NAME_CACHE.put(type.name().toUpperCase(), type);
			INDEX_CACHE.put(type.index, type);
		}
		// Initialize  EventType mappings 
		initializeEventTypeMap();
	}

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
		EventTypeGeneral type = INDEX_CACHE.get(index);
		return (type != null) ? type : nothing;
	}
	
	public static EventTypeGeneral fromName(String name) {
		if (name == null) return nothing;
		EventTypeGeneral type = NAME_CACHE.get(name.toUpperCase());
		return (type != null) ? type : nothing;
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
		EventTypeGeneral genType = EVENT_TYPE_MAP.get(type);
		return (genType != null) ? genType : nothing;
	}
	
	private static void initializeEventTypeMap() {
		map(sleep, EventType.selfSleep, EventType.candidatesSleep, EventType.percipientSleep);
		map(drink, EventType.selfDrink, EventType.targetDrink, EventType.candidatesDrink, EventType.percipientDrink);
		map(eat, EventType.selfEat, EventType.targetEat, EventType.candidatesEat, EventType.percipientEat);
		map(piss, EventType.selfPiss, EventType.candidatesPiss, EventType.percipientPiss);
		map(shit, EventType.selfShit, EventType.candidatesShit, EventType.percipientShit);
		map(moveWalk, EventType.selfMoveWalk, EventType.candidatesMoveWalk, EventType.percipientMoveWalk);
		map(moveRun, EventType.selfMoveRun, EventType.candidatesMoveRun, EventType.percipientMoveRun);
		map(moveSneak, EventType.selfMoveSneak, EventType.candidatesMoveSneak, EventType.percipientMoveSneak);
		map(moveJump, EventType.selfMoveJump, EventType.candidatesMoveJump, EventType.percipientMoveJump);
		map(moveSwim, EventType.selfMoveSwim, EventType.candidatesMoveSwim, EventType.percipientMoveSwim);
		map(moveFly, EventType.selfMoveFly, EventType.candidatesMoveFly, EventType.percipientMoveFly);
		map(examineByLook, EventType.selfExamineByLook, EventType.targetExamineByLook, EventType.candidatesExamineByLook, EventType.percipientExamineByLook);
		map(examineBySmell, EventType.selfExamineBySmell, EventType.targetExamineBySmell, EventType.candidatesExamineBySmell, EventType.percipientExamineBySmell);
		map(examineByTaste, EventType.selfExamineByTaste, EventType.targetExamineByTaste, EventType.candidatesExamineByTaste, EventType.percipientExamineByTaste);
		map(examineByTouch, EventType.selfExamineByTouch, EventType.targetExamineByTouch, EventType.candidatesExamineByTouch, EventType.percipientExamineByTouch);
		map(touchByHand, EventType.selfTouchByHand, EventType.targetTouchByHand, EventType.candidatesTouchByHand, EventType.percipientTouchByHand);
		map(touchByFoot, EventType.selfTouchByFoot, EventType.targetTouchByFoot, EventType.candidatesTouchByFoot, EventType.percipientTouchByFoot);
		map(inventoryTake, EventType.selfInventoryTake, EventType.targetInventoryTake, EventType.candidatesInventoryTake, EventType.percipientInventoryTake);
		map(inventoryDrop, EventType.selfInventoryDrop, EventType.targetInventoryDrop, EventType.candidatesInventoryDrop, EventType.percipientInventoryDrop);
		map(inventorySwitch, EventType.selfInventorySwitch, EventType.targetInventorySwitch, EventType.candidatesInventorySwitch, EventType.percipientInventorySwitch);
		map(inventorySet, EventType.selfInventorySet, EventType.targetInventorySet, EventType.candidatesInventorySet, EventType.percipientInventorySet);
		map(inventoryGet, EventType.selfInventoryGet, EventType.targetInventoryGet, EventType.candidatesInventoryGet, EventType.percipientInventoryGet);
		map(handleItemUse2, EventType.selfHandleItemUse2, EventType.targetHandleItemUse2, EventType.candidatesHandleItemUse2, EventType.percipientHandleItemUse2);
		map(handleItemUseLeft, EventType.selfHandleItemUseLeft, EventType.targetHandleItemUseLeft, EventType.candidatesHandleItemUseLeft, EventType.percipientHandleItemUseLeft);
		map(handleItemUseRight, EventType.selfHandleItemUseRight, EventType.targetHandleItemUseRight, EventType.candidatesHandleItemUseRight, EventType.percipientHandleItemUseRight);
		map(handleItemAddRtoL, EventType.selfHandleItemAddRtoL, EventType.targetHandleItemAddRtoL, EventType.candidatesHandleItemAddRtoL, EventType.percipientHandleItemAddRtoL);
		map(handleItemAddLtoR, EventType.selfHandleItemAddLtoR, EventType.targetHandleItemAddLtoR, EventType.candidatesHandleItemAddLtoR, EventType.percipientHandleItemAddLtoR);
		map(handleItemPull, EventType.selfHandleItemPull, EventType.targetHandleItemPull, EventType.candidatesHandleItemPull, EventType.percipientHandleItemPull);
		map(handleItemPush, EventType.selfHandleItemPush, EventType.targetHandleItemPush, EventType.candidatesHandleItemPush, EventType.percipientHandleItemPush);
		map(weaponLeftStab, EventType.selfWeaponLeftStab, EventType.targetWeaponLeftStab, EventType.candidatesWeaponLeftStab, EventType.percipientWeaponLeftStab);
		map(weaponLeftStroke, EventType.selfWeaponLeftStroke, EventType.targetWeaponLeftStroke, EventType.candidatesWeaponLeftStroke, EventType.percipientWeaponLeftStroke);
		map(weaponLeftBackhand, EventType.selfWeaponLeftBackhand, EventType.targetWeaponLeftBackhand, EventType.candidatesWeaponLeftBackhand, EventType.percipientWeaponLeftBackhand);
		map(weaponRightStab, EventType.selfWeaponRightStab, EventType.targetWeaponRightStab, EventType.candidatesWeaponRightStab, EventType.percipientWeaponRightStab);
		map(weaponRightStroke, EventType.selfWeaponRightStroke, EventType.targetWeaponRightStroke, EventType.candidatesWeaponRightStroke, EventType.percipientWeaponRightStroke);
		map(weaponRightBackhand, EventType.selfWeaponRightBackhand, EventType.targetWeaponRightBackhand, EventType.candidatesWeaponRightBackhand, EventType.percipientWeaponRightBackhand);
		map(weaponClub, EventType.selfWeaponClub, EventType.targetWeaponClub, EventType.candidatesWeaponClub, EventType.percipientWeaponClub);
		map(punchLeftFistStraight, EventType.selfPunchLeftFistStraight, EventType.targetPunchLeftFistStraight, EventType.candidatesPunchLeftFistStraight, EventType.percipientPunchLeftFistStraight);
		map(punchLeftFistSideways, EventType.selfPunchLeftFistSideways, EventType.targetPunchLeftFistSideways, EventType.candidatesPunchLeftFistSideways, EventType.percipientPunchLeftFistSideways);
		map(punchLeftFistUpward, EventType.selfPunchLeftFistUpward, EventType.targetPunchLeftFistUpward, EventType.candidatesPunchLeftFistUpward, EventType.percipientPunchLeftFistUpward);
		map(punchRightFistStraight, EventType.selfPunchRightFistStraight, EventType.targetPunchRightFistStraight, EventType.candidatesPunchRightFistStraight, EventType.percipientPunchRightFistStraight);
		map(punchRightFistSideways, EventType.selfPunchRightFistSideways, EventType.targetPunchRightFistSideways, EventType.candidatesPunchRightFistSideways, EventType.percipientPunchRightFistSideways);
		map(punchRightFistUpward, EventType.selfPunchRightFistUpward, EventType.targetPunchRightFistUpward, EventType.candidatesPunchRightFistUpward, EventType.percipientPunchRightFistUpward);
		map(listenToStatement, EventType.selfListenToStatement);
		map(listenToQuestion, EventType.selfListenToQuestion);
		map(listenToInstruction, EventType.selfListenToInstruction);
		map(understand, EventType.selfUnderstand);
		map(askNormal, EventType.selfAskNormal);
		map(askScream, EventType.selfAskScream);
		map(askWhisper, EventType.selfAskWhisper);
		map(answerNormal, EventType.selfAnswerNormal);
		map(answerScream, EventType.selfAnswerScream);
		map(answerWhisper, EventType.selfAnswerWhisper);
		map(sayNormal, EventType.selfSayNormal);
		map(sayScream, EventType.selfSayScream);
		map(sayWhisper, EventType.selfSayWhisper);
	}
	private static void map(EventTypeGeneral target, EventType... sources) {
		for (EventType s : sources) EVENT_TYPE_MAP.put(s, target);
	}

}
