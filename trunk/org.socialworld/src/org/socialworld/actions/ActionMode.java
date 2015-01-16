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
 * are (for example) modes for the action type changeMove and for the action
 * type handleItem.
 * 
 * @author Mathias Sikos (tyloesand)
 */
public enum ActionMode {
	
	// for ActionType sleep
	sleepIntentioned,	sleepCaused,
	
	// for ActionType move
	walk, run, sneak, jump, swim, fly, 

	// for ActionType examine
	look, smell, taste, touch,
	
	// for ActionType touch
	hand, foot,
	
	// for ActionType itemAndInventory
	takeItem, collectItem, dropItem, switchItemToOtherHand,

	// for ActionType handleItem
	useTwoItems,	useItemLeftHand, useItemRightHand,
	combineItems_AddRightToLeft, combineItems_AddLeftToRight,
	pull, push,

	// for ActionType useWeaponLeft and useWeaponRight
	stab, stroke, backhand, club, 

	// for ActionType hear
	listenTo, understand,

	// for ActionType talk
	answerNormal, answerScream, answerWhisper, askNormal, askScream, askWhisper,
	
	// for ActionType say
	normal, scream, whisper
}
