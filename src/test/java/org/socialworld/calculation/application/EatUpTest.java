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
package org.socialworld.calculation.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.Test;
import org.socialworld.collections.ObjectByPositionSearch;
import org.socialworld.core.EventType;
import org.socialworld.objects.NoSimulationObject;
import org.socialworld.objects.concrete.eatable.bakery.Bread;
import org.socialworld.objects.concrete.eatable.liquids.Beer;
import org.socialworld.objects.concrete.plantparts.Herb;
import org.socialworld.objects.concrete.tools.Knife;

/**
 * A meal that is over eats up what is in the mouth (inventory concept V6, 2026-11-23): the eatable thing leaves the
 * mouth and the simulation, as a whole (D3).
 */
class EatUpTest {

	@Test
	void eatUp_isATechnicalEvent() {
		assertThat(EventType.eatUp.isTechnical()).isTrue();
		assertThat(EventType.eatUp.getIndex()).isEqualTo(515);
	}

	@Test
	void whatIsEatable_isEatenUp_nothingElse() {
		assertThat(InventoryCalculator.isEatenUp(new Bread())).isTrue();
		assertThat(InventoryCalculator.isEatenUp(new Herb())).isTrue();	// eatable without being food
		assertThat(InventoryCalculator.isEatenUp(new Beer())).isFalse();	// drinking is not eating
		assertThat(InventoryCalculator.isEatenUp(new Knife())).isFalse();
		assertThat(InventoryCalculator.isEatenUp(NoSimulationObject.getObjectNothing())).isFalse();
		assertThat(InventoryCalculator.isEatenUp(null)).isFalse();
	}

	@Test
	void anObjectThatWasNeverInTheSearch_canBeRemoved_withoutHarm() {
		ObjectByPositionSearch search = new ObjectByPositionSearch(1);
		assertThatCode(() -> search.remove(new Bread())).doesNotThrowAnyException();
	}
}
