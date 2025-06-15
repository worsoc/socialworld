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

import org.socialworld.core.EventTypeGeneral;

/**
 * The enumeration holds all specialization modes of an action type. So there
 * are (for example) modes for the action type move and for the action
 * type handleItem.
 * 
 * @author Mathias Sikos (MatWorsoc)
 */
public enum ActionMode {
	
	ignore(0, EventTypeGeneral.nothing),
	
	// for ActionType bodilyFunction
	sleep(1, EventTypeGeneral.sleep), drink(2, EventTypeGeneral.drink), eat(3, EventTypeGeneral.eat),
	piss(4,EventTypeGeneral.piss), shit(5, EventTypeGeneral.shit), 	
	
	// for ActionType move
	walk(11, EventTypeGeneral.moveWalk), run(12, EventTypeGeneral.moveRun), sneak(13, EventTypeGeneral.moveSneak), 
	jump(14, EventTypeGeneral.moveJump), swim(15, EventTypeGeneral.moveSwim), fly(16, EventTypeGeneral.moveFly), 

	// for ActionType examine
	look(21, EventTypeGeneral.examineByLook), smell(22, EventTypeGeneral.examineBySmell),
	taste(23, EventTypeGeneral.examineByTaste), touch(24, EventTypeGeneral.examineByTouch),
	
	// for ActionType touch
	hand(31, EventTypeGeneral.touchByHand), foot(32, EventTypeGeneral.touchByFoot),
	
	// for ActionType equip
	takeItem(41, EventTypeGeneral.inventoryTake), dropItem(42, EventTypeGeneral.inventoryDrop), switchItemToOtherHand(43, EventTypeGeneral.inventorySwitch),
	setItemToInventory(44, EventTypeGeneral.inventorySet), getItemFromInventory(45, EventTypeGeneral.inventoryGet),

	// for ActionType handleItem
	useTwoItems(51, EventTypeGeneral.handleItemUse2),	useItemLeftHand(52, EventTypeGeneral.handleItemUseLeft), useItemRightHand(53, EventTypeGeneral.handleItemUseRight),
	combineItems_AddRightToLeft(54, EventTypeGeneral.handleItemAddRtoL), combineItems_AddLeftToRight(55, EventTypeGeneral.handleItemAddLtoR),
	pull(56, EventTypeGeneral.handleItemPull), push(57, EventTypeGeneral.handleItemPush),

	// for ActionType useWeapon
	weaponLeftStab(61, EventTypeGeneral.weaponLeftStab), weaponLeftStroke(62, EventTypeGeneral.weaponLeftStroke), weaponLeftBackhand(63, EventTypeGeneral.weaponLeftBackhand),
	weaponRightStab(64, EventTypeGeneral.weaponRightStab), weaponRightStroke(65, EventTypeGeneral.weaponRightStroke), weaponRightBackhand(66, EventTypeGeneral.weaponRightBackhand),
	weaponClub(67, EventTypeGeneral.weaponClub), 

	// for ActionType punch
	punchLeftFistStraight(71, EventTypeGeneral.punchLeftFistStraight),  punchLeftFistSideways(72, EventTypeGeneral.punchLeftFistSideways), punchLeftFistUpward(73, EventTypeGeneral.punchLeftFistUpward),
	punchRightFistStraight(74, EventTypeGeneral.punchRightFistStraight),  punchRightFistSideways(75, EventTypeGeneral.punchRightFistSideways), punchRightFistUpward(76, EventTypeGeneral.punchRightFistUpward),
		
	// for ActionType hear
	listenToStatement(81, EventTypeGeneral.listenToStatement), listenToQuestion(82, EventTypeGeneral.listenToQuestion),
	listenToInstruction(83, EventTypeGeneral.listenToInstruction), understand(84, EventTypeGeneral.understand),

	// for ActionType talk
	answerNormal(91, EventTypeGeneral.answerNormal), answerScream(92, EventTypeGeneral.answerScream), answerWhisper(93, EventTypeGeneral.answerWhisper),
	askNormal(94, EventTypeGeneral.askNormal), askScream(95, EventTypeGeneral.askScream), askWhisper(96, EventTypeGeneral.askWhisper),
	
	// for ActionType say
	sayNormal(101, EventTypeGeneral.sayNormal), sayScream(102, EventTypeGeneral.sayScream), sayWhisper(103, EventTypeGeneral.sayWhisper);	

	private int index;
	private EventTypeGeneral etg;

	private ActionMode(int index, EventTypeGeneral etg) {
		this.index = index;
		this.etg = etg;
	}
	
	public static ActionMode getName(int index) {
		for (ActionMode actionmode : ActionMode.values())
			if (actionmode.index == index)
				return actionmode;
		return ignore;
	}

	/**
	 * The method returns the index of the ActionMode.
	 * 
	 * @return action mode's index
	 */
	public int getIndex() {
		return index;
	}

	public static ActionMode fromName(String name) {
		for (ActionMode mode : ActionMode.values())
			if (mode.toString().equals(name))
				return mode;
		return null;
	}

	public static ActionMode fromEventTypeGeneral(EventTypeGeneral etg) {
		for (ActionMode mode : ActionMode.values())
			if (mode.etg == etg)
				return mode;
		return ignore;
	}

	public static int maxIndex() {
		return 103;
	}
}
