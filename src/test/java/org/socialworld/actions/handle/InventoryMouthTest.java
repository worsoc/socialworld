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
package org.socialworld.actions.handle;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.objects.SimulationObject_Type;
import org.socialworld.objects.concrete.eatable.fruits.Apple;

/**
 * Eating and drinking take the item from the mouth (ActionBodilyFunction asks for
 * inventory_mouth). Until 2026-11-17 the inventory did not answer that property.
 */
class InventoryMouthTest {

	@Test
	void theItemInTheMouth_isGivenBackAsProperty() {
		Inventory inventory = new Inventory(SimulationObject_Type.human);
		Apple apple = new Apple();
		inventory.setMouth(apple);

		ValueProperty mouth = inventory.getProperty(null, PropertyName.inventory_mouth, PropertyName.inventory_mouth.toString());

		// before the fix the inventory gave ValueProperty.getInvalid() for the mouth
		assertThat(mouth).isNotSameAs(ValueProperty.getInvalid());
		assertThat(mouth.isValid()).isTrue();
		assertThat(mouth.getType()).isEqualTo(Type.simulationObject);
		// the apple itself is protected: only a permitted token gets it out of the property, as in
		// ActionBodilyFunction through the ObjectRequester; that needs the running simulation
		assertThat(inventory.getMouth()).isSameAs(apple);
	}
}
