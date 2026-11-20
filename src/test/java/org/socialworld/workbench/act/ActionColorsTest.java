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

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.Color;

import org.junit.jupiter.api.Test;
import org.socialworld.actions.ActionMode;
import org.socialworld.visualize.ActionFamily;

/**
 * The colours of the ACT map by running action: the family colours of the dashboard grid,
 * darker for the stronger mode.
 */
public class ActionColorsTest {

	private static double brightnessOf(ActionMode mode) {
		return ActionColors.brightness(ActionColors.of(mode));
	}

	@Test
	void everyActionHasAColour_noActionHasNone() {
		for (ActionMode mode : ActionMode.values()) {
			if (mode == ActionMode.ignore) continue;
			assertThat(ActionColors.of(mode)).as(mode.name()).isNotNull();
		}
		assertThat(ActionColors.of(ActionMode.ignore)).isNull();
		assertThat(ActionColors.of(null)).isNull();
	}

	@Test
	void normalModes_haveTheFamilyColourOfTheDashboardGrid() {
		assertThat(ActionColors.of(ActionMode.eat)).isEqualTo(ActionFamily.BODY.getColor());
		assertThat(ActionColors.of(ActionMode.walk)).isEqualTo(ActionFamily.MOVEMENT.getColor());
		assertThat(ActionColors.of(ActionMode.listenToStatement)).isEqualTo(ActionFamily.PERCEPTION.getColor());
		assertThat(ActionColors.of(ActionMode.combineItems_AddLeftToRight)).isEqualTo(ActionFamily.ITEM.getColor());
		assertThat(ActionColors.of(ActionMode.hand)).isEqualTo(ActionFamily.CONTACT.getColor());
		assertThat(ActionColors.of(ActionMode.punchRightFistStraight)).isEqualTo(ActionFamily.FIGHT.getColor());
		assertThat(ActionColors.of(ActionMode.sayNormal)).isEqualTo(ActionFamily.SPEECH.getColor());
	}

	@Test
	void everyMode_hasTheHueOfItsDashboardFamily() {
		for (ActionMode mode : ActionMode.values()) {
			if (mode == ActionMode.ignore || mode == ActionMode.fly) continue;
			Color family = ActionFamily.ofMode(mode).getColor();
			assertThat(ActionColors.of(mode)).as(mode.name()).isEqualTo(ActionColors.shade(family, ActionColors.shareOf(mode)));
		}
	}

	@Test
	void fly_isTheTestFlightColourOfTheDashboard() {
		assertThat(ActionColors.of(ActionMode.fly)).isEqualTo(ActionFamily.COLOR_FLY);
	}

	@Test
	void sleepAndEat_areBrown() {
		for (ActionMode mode : new ActionMode[] { ActionMode.sleep, ActionMode.eat }) {
			Color color = ActionColors.of(mode);
			assertThat(color.getRed()).as(mode.name()).isGreaterThan(color.getGreen()).isGreaterThan(color.getBlue());
		}
	}

	@Test
	void shade_goesTowardsBlackOrWhite() {
		Color base = new Color(100, 150, 200);
		assertThat(ActionColors.shade(base, 0f)).isEqualTo(base);
		assertThat(ActionColors.shade(base, -1f)).isEqualTo(Color.BLACK);
		assertThat(ActionColors.shade(base, 1f)).isEqualTo(Color.WHITE);
		assertThat(ActionColors.shade(base, -0.5f)).isEqualTo(new Color(50, 75, 100));
	}

	@Test
	void moving_runDarkest_sneakLightest() {
		assertThat(brightnessOf(ActionMode.run)).isLessThan(brightnessOf(ActionMode.walk));
		assertThat(brightnessOf(ActionMode.walk)).isLessThan(brightnessOf(ActionMode.sneak));
		for (ActionMode mode : new ActionMode[] { ActionMode.walk, ActionMode.jump, ActionMode.swim }) {
			assertThat(brightnessOf(mode)).as(mode.name()).isBetween(brightnessOf(ActionMode.run), brightnessOf(ActionMode.sneak));
		}
	}

	@Test
	void moving_isBlue_exceptTheTestFlight() {
		for (ActionMode mode : new ActionMode[] { ActionMode.run, ActionMode.walk, ActionMode.sneak, ActionMode.jump,
				ActionMode.swim }) {
			Color color = ActionColors.of(mode);
			assertThat(color.getBlue()).as(mode.name()).isGreaterThan(color.getRed()).isGreaterThan(color.getGreen() - 1);
		}
	}

	@Test
	void talking_screamDarkest_whisperLightest_sameForSayAskAnswer() {
		assertThat(brightnessOf(ActionMode.sayScream)).isLessThan(brightnessOf(ActionMode.sayNormal));
		assertThat(brightnessOf(ActionMode.sayNormal)).isLessThan(brightnessOf(ActionMode.sayWhisper));

		assertThat(ActionColors.of(ActionMode.askScream)).isEqualTo(ActionColors.of(ActionMode.sayScream));
		assertThat(ActionColors.of(ActionMode.answerWhisper)).isEqualTo(ActionColors.of(ActionMode.sayWhisper));
	}

	@Test
	void talking_isOrchid() {
		for (ActionMode mode : new ActionMode[] { ActionMode.sayScream, ActionMode.sayNormal, ActionMode.sayWhisper }) {
			Color color = ActionColors.of(mode);
			assertThat(color.getRed()).as(mode.name()).isGreaterThan(color.getGreen());
			assertThat(color.getBlue()).as(mode.name()).isGreaterThan(color.getGreen());
		}
	}

	@Test
	void fighting_weaponsDarkerThanFists() {
		assertThat(brightnessOf(ActionMode.weaponClub)).isLessThan(brightnessOf(ActionMode.punchRightFistStraight));
		assertThat(brightnessOf(ActionMode.punchRightFistStraight)).isLessThan(brightnessOf(ActionMode.punchRightFistUpward));
	}

	@Test
	void body_sleepDarkest() {
		assertThat(brightnessOf(ActionMode.sleep)).isLessThan(brightnessOf(ActionMode.eat));
		assertThat(brightnessOf(ActionMode.eat)).isLessThan(brightnessOf(ActionMode.drink));
	}

	@Test
	void keepsOrangeAndBlackFreeForRangeAndCauser() {
		Color range = new Color(214, 96, 20);
		for (ActionMode mode : ActionMode.values()) {
			Color color = ActionColors.of(mode);
			if (color == null) continue;
			assertThat(color).as(mode.name()).isNotEqualTo(range).isNotEqualTo(Color.BLACK);
		}
	}
}
