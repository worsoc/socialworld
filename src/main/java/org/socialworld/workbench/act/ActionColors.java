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
package org.socialworld.workbench.act;

import java.awt.Color;

import org.socialworld.actions.ActionMode;
import org.socialworld.visualize.ActionFamily;

/**
 * The colour of a human on the ACT map by its running action (2026-11-20).
 * The hue is the family colour of the dashboard grid, taken from {@link ActionFamily} (the one source),
 * fly the colour of the test flight. Only the shading is the ACT's own: within a family the normal mode
 * has the family colour, a stronger one is darker, a weaker one lighter, e.g. run dark blue, sneak light
 * blue, scream dark orchid, whisper light orchid.
 */
final class ActionColors {

	private ActionColors() {
	}

	/** @return the colour of the mode, null for no action (ignore or null) */
	static Color of(ActionMode mode) {
		Color family = ActionFamily.colorOfMode(mode);
		if (family == null) return null;
		if (mode == ActionMode.fly) return family;
		return shade(family, shareOf(mode));
	}

	/** how much darker (< 0) or lighter (> 0) than its family colour a mode is drawn */
	static float shareOf(ActionMode mode) {
		switch (mode) {
		// body
		case sleep: return -0.35f;
		case shit: return -0.15f;
		case drink: return 0.3f;
		case piss: return 0.5f;

		// movement
		case run: return -0.5f;
		case jump: return -0.3f;
		case swim: return 0.3f;
		case sneak: return 0.55f;

		// perception: only darker, light slate gray would look like an idle human
		case understand: return -0.5f;
		case look: return -0.4f;
		case smell: return -0.3f;
		case taste: return -0.2f;
		case touch: return -0.1f;

		// item
		case pull:
		case push: return -0.4f;
		case useTwoItems:
		case useItemLeftHand:
		case useItemRightHand: return -0.2f;
		case takeItem:
		case dropItem:
		case switchItemToOtherHand:
		case setItemToInventory:
		case getItemFromInventory: return 0.35f;

		// contact
		case foot: return -0.3f;

		// fight
		case weaponLeftStab:
		case weaponRightStab:
		case weaponClub: return -0.5f;
		case weaponLeftStroke:
		case weaponLeftBackhand:
		case weaponRightStroke:
		case weaponRightBackhand: return -0.3f;
		case punchLeftFistSideways:
		case punchLeftFistUpward:
		case punchRightFistSideways:
		case punchRightFistUpward: return 0.3f;

		// speech (say, ask, answer) by loudness
		case sayScream:
		case askScream:
		case answerScream: return -0.45f;
		case sayWhisper:
		case askWhisper:
		case answerWhisper: return 0.5f;

		// the normal mode of a family: eat, walk, listening, combining, hand, straight punch, normal speech
		default: return 0f;
		}
	}

	/** share < 0 blends towards black, share > 0 towards white */
	static Color shade(Color color, float share) {
		int target = share < 0 ? 0 : 255;
		float s = Math.abs(share);
		return new Color(
				Math.round(color.getRed() + (target - color.getRed()) * s),
				Math.round(color.getGreen() + (target - color.getGreen()) * s),
				Math.round(color.getBlue() + (target - color.getBlue()) * s));
	}

	/** perceived brightness 0..255, for the shading within a family */
	static double brightness(Color color) {
		return 0.299 * color.getRed() + 0.587 * color.getGreen() + 0.114 * color.getBlue();
	}
}
