/*
 * Social World
 * Copyright (C) 2026  Mathias Sikos
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://gnu.org>.
 */
package org.socialworld.visualize;

import java.awt.Color;

import org.socialworld.actions.ActionMode;
import org.socialworld.actions.ActionType;

/**
 * The action types grouped into few families, so that the colour of a field can be read without a table.
 * The mode is told by a short text: capitals with ! for screaming, brackets for whispering.
 */
enum ActionFamily {
	BODY("K\u00F6rper", SimColorConstants.COLOR_SIENNA),
	MOVEMENT("Bewegung", SimColorConstants.COLOR_DODGERBLUE),
	PERCEPTION("Wahrnehmen", SimColorConstants.COLOR_SLATEGRAY),
	ITEM("Gegenstand", SimColorConstants.COLOR_GOLDENROD),
	CONTACT("Kontakt", SimColorConstants.COLOR_DARKCYAN),
	FIGHT("Kampf", SimColorConstants.COLOR_CRIMSON),
	SPEECH("Sprache", SimColorConstants.COLOR_MEDIUMORCHID);

	private final String label;
	private final Color color;

	private ActionFamily(String label, Color color) {
		this.label = label;
		this.color = color;
	}

	String getLabel() {
		return label;
	}

	Color getColor() {
		return color;
	}

	/**
	 * @return the family of the action type, null if the type is null or ignore
	 */
	static ActionFamily of(ActionType type) {
		if (type == null) return null;

		switch (type) {
		case bodilyFunction:
			return BODY;
		case move:
			return MOVEMENT;
		case examine:
		case hear:
			return PERCEPTION;
		case equip:
		case handleItem:
			return ITEM;
		case touch:
			return CONTACT;
		case punch:
		case useWeapon:
			return FIGHT;
		case talk:
		case say:
			return SPEECH;
		default:
			return null;
		}
	}

	/**
	 * @return the text for the field of the action grid, empty if the mode is null or ignore
	 */
	static String shortName(ActionMode mode) {
		if (mode == null) return "";

		switch (mode) {
		case sleep: return "sleep";
		case drink: return "drink";
		case eat: return "eat";
		case piss: return "piss";
		case shit: return "shit";

		case walk: return "walk";
		case run: return "run";
		case sneak: return "sneak";
		case jump: return "jump";
		case swim: return "swim";
		case fly: return "fly";

		case look: return "look";
		case smell: return "smell";
		case taste: return "taste";
		case touch: return "touch";

		case hand: return "hand";
		case foot: return "foot";

		case takeItem: return "take";
		case dropItem: return "drop";
		case switchItemToOtherHand: return "switch";
		case setItemToInventory: return "stow";
		case getItemFromInventory: return "fetch";

		case useTwoItems: return "use 2";
		case useItemLeftHand: return "use L";
		case useItemRightHand: return "use R";
		case combineItems_AddRightToLeft: return "add R>L";
		case combineItems_AddLeftToRight: return "add L>R";
		case pull: return "pull";
		case push: return "push";

		case weaponLeftStab: return "L stab";
		case weaponLeftStroke: return "L strk";
		case weaponLeftBackhand: return "L back";
		case weaponRightStab: return "R stab";
		case weaponRightStroke: return "R strk";
		case weaponRightBackhand: return "R back";
		case weaponClub: return "club";

		case punchLeftFistStraight: return "L str";
		case punchLeftFistSideways: return "L side";
		case punchLeftFistUpward: return "L up";
		case punchRightFistStraight: return "R str";
		case punchRightFistSideways: return "R side";
		case punchRightFistUpward: return "R up";

		case listenToStatement: return "hear st";
		case listenToQuestion: return "hear qu";
		case listenToInstruction: return "hear in";
		case understand: return "underst";

		case answerNormal: return "answer";
		case answerScream: return "ANSWER!";
		case answerWhisper: return "(answer)";
		case askNormal: return "ask";
		case askScream: return "ASK!";
		case askWhisper: return "(ask)";

		case sayNormal: return "say";
		case sayScream: return "SAY!";
		case sayWhisper: return "(say)";

		default:
			return "";
		}
	}
}
