/**
 * 
 */
package org.socialworld.attributes;

/**
 * The enumeration holds all specialization modes of an action type. So there
 * are (for example) modes for the action type changeMove and for the action
 * type handleItem.
 * 
 * @author Mathias Sikos (tyloesand)
 */
public enum ActionMode {
	run, walk, jump,

	sleepIntentioned, sleepCaused,

	weaponRightHand, weaponLeftHand,

	examineItem, takeItem, useItem, collectItem, switchItemToLeftHand, useTwoItems, dropItem

}
