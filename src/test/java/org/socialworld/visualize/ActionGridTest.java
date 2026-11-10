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

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.socialworld.actions.ActionMode;
import org.socialworld.actions.ActionType;

public class ActionGridTest {

	private final VisualModel model = new VisualModel();

	private void startWalking() {
		model.startAction(1, new Object(), ActionType.move, ActionMode.walk, 62f, 10000, 4000, 120);
	}

	private VisualModel.ObjectState state() {
		return model.snapshot().get(1);
	}

	@Test
	void everyActionTypeHasAFamily_exceptIgnore() {
		for (ActionType type : ActionType.values()) {
			if (type == ActionType.ignore) {
				assertThat(ActionFamily.of(type)).isNull();
			}
			else {
				assertThat(ActionFamily.of(type)).as(type.name()).isNotNull();
			}
		}
	}

	@Test
	void nullType_hasNoFamily() {
		assertThat(ActionFamily.of(null)).isNull();
	}

	@Test
	void familyColors_areDistinctAndNotTheIdleColor() {
		Set<Color> colors = new HashSet<Color>();
		for (ActionFamily family : ActionFamily.values()) {
			colors.add(family.getColor());
		}
		assertThat(colors).hasSize(ActionFamily.values().length);
		assertThat(colors).doesNotContain(ActionGridPanel.COLOR_IDLE);
	}

	@Test
	void everyActionModeHasAShortUniqueName_exceptIgnore() {
		Set<String> names = new HashSet<String>();
		int modes = 0;
		for (ActionMode mode : ActionMode.values()) {
			String name = ActionFamily.shortName(mode);
			if (mode == ActionMode.ignore) {
				assertThat(name).isEmpty();
			}
			else {
				modes++;
				assertThat(name).as(mode.name()).isNotEmpty();
				assertThat(name.length()).as(mode.name()).isLessThanOrEqualTo(8);
				names.add(name);
			}
		}
		assertThat(names).hasSize(modes);
	}

	@Test
	void nullMode_hasAnEmptyName() {
		assertThat(ActionFamily.shortName(null)).isEmpty();
	}

	@Test
	void neverActed_isIdle() {
		assertThat(ActionGridPanel.fillColor(state(), 5000)).isEqualTo(ActionGridPanel.COLOR_IDLE);
		assertThat(ActionGridPanel.isVisible(state(), 5000)).isFalse();
	}

	@Test
	void runningAction_hasTheFullColorOfItsFamily() {
		startWalking();

		assertThat(ActionGridPanel.fillColor(state(), 99999)).isEqualTo(ActionFamily.MOVEMENT.getColor());
	}

	@Test
	void ignoredActionType_staysIdle() {
		model.startAction(1, new Object(), ActionType.ignore, ActionMode.ignore, 0f, 0, 0, 0);

		assertThat(ActionGridPanel.fillColor(state(), 5000)).isEqualTo(ActionGridPanel.COLOR_IDLE);
	}

	@Test
	void endedAction_fadesOutToIdle() {
		startWalking();
		model.stopAction(1, true, 1000);

		assertThat(ActionGridPanel.fillColor(state(), 1000)).isEqualTo(ActionFamily.MOVEMENT.getColor());
		assertThat(ActionGridPanel.fillColor(state(), 1000 + ActionGridPanel.FADE_MILLIS / 2))
				.isNotEqualTo(ActionFamily.MOVEMENT.getColor())
				.isNotEqualTo(ActionGridPanel.COLOR_IDLE);
		assertThat(ActionGridPanel.isVisible(state(), 1000 + ActionGridPanel.FADE_MILLIS / 2)).isTrue();
		assertThat(ActionGridPanel.fillColor(state(), 1000 + ActionGridPanel.FADE_MILLIS)).isEqualTo(ActionGridPanel.COLOR_IDLE);
		assertThat(ActionGridPanel.isVisible(state(), 1000 + ActionGridPanel.FADE_MILLIS)).isFalse();
	}

	@Test
	void remainingShare_isTheRemainedPartOfTheDuration() {
		startWalking();

		assertThat(ActionGridPanel.remainingShare(state())).isEqualTo(0.4f);
	}

	@Test
	void remainingShare_ofAnActionWithoutDurationIsZero() {
		model.startAction(1, new Object(), ActionType.move, ActionMode.walk, 62f, 0, 0, 120);

		assertThat(ActionGridPanel.remainingShare(state())).isZero();
	}

	@Test
	void readableText_isBlackOnBrightAndWhiteOnDark() {
		assertThat(ActionGridPanel.readableTextOn(Color.WHITE)).isEqualTo(Color.BLACK);
		assertThat(ActionGridPanel.readableTextOn(ActionGridPanel.COLOR_IDLE)).isEqualTo(Color.BLACK);
		assertThat(ActionGridPanel.readableTextOn(Color.BLACK)).isEqualTo(Color.WHITE);
		assertThat(ActionGridPanel.readableTextOn(SimColorConstants.COLOR_SADDLEBROWN)).isEqualTo(Color.WHITE);
	}

	@Test
	void objIdAt_findsTheFieldWithIdsStartingAtOne() {
		assertThat(ActionGridPanel.objIdAt(0, 0, 1000, 500)).isEqualTo(1);
		assertThat(ActionGridPanel.objIdAt(150, 0, 1000, 500)).isEqualTo(2);
		assertThat(ActionGridPanel.objIdAt(0, 60, 1000, 500)).isEqualTo(11);
		assertThat(ActionGridPanel.objIdAt(999, 499, 1000, 500)).isEqualTo(100);
	}

	@Test
	void objIdAt_outsideThePanelStaysInRange() {
		assertThat(ActionGridPanel.objIdAt(-5, -5, 1000, 500)).isEqualTo(1);
		assertThat(ActionGridPanel.objIdAt(5000, 5000, 1000, 500)).isEqualTo(100);
	}

	@Test
	void describe_tellsTheAction() {
		startWalking();

		assertThat(ActionGridPanel.describe(1, state()))
				.contains("Objekt 1", "walk", "move", "62", "Rest 4 s von 10 s", "120");
	}

	@Test
	void describe_tellsAnInterruption() {
		startWalking();
		model.stopAction(1, false, 1000);

		assertThat(ActionGridPanel.describe(1, state())).contains("unterbrochen");
	}

	@Test
	void describe_ofAnObjectWithoutAction() {
		assertThat(ActionGridPanel.describe(7, state())).contains("noch keine Aktion");
	}

	@Test
	void sumAttributeChanges_countsOnlyChanges() {
		org.socialworld.attributes.AttributeArray array = new org.socialworld.attributes.AttributeArray(new int[] {1});
		model.setAttributes(1, array, true, 1);
		model.setAttributes(2, array, true, 1);
		model.setAttributes(2, array, false, 1);

		assertThat(model.snapshot().sumAttributeChanges()).isEqualTo(2);
	}

	@Test
	void perSecondRate_startsAtZeroAndUpdatesOncePerSecond() {
		PerSecondRate rate = new PerSecondRate();

		assertThat(rate.update(100, 0)).isZero();
		assertThat(rate.update(150, 500)).isZero();
		assertThat(rate.update(200, 1000)).isEqualTo(100);
		assertThat(rate.update(900, 1250)).isEqualTo(100);
		assertThat(rate.update(900, 2000)).isEqualTo(700);
	}
}
