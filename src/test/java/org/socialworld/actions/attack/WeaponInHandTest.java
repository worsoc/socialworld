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
package org.socialworld.actions.attack;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.socialworld.objects.NoSimulationObject;
import org.socialworld.objects.concrete.eatable.bakery.Bread;
import org.socialworld.objects.concrete.tools.Knife;
import org.socialworld.objects.concrete.weapons.Cudgel;
import org.socialworld.objects.properties.IWeapon;

/**
 * A blow with the weapon in the hand (inventory concept V7, 2026-11-23): which weapon a club blow takes, and F1.
 */
class WeaponInHandTest {

	@Test
	void f1_aRealWeapon_isNotNothing_NoIWeapon_is() {
		assertThat(((IWeapon) new Cudgel()).isObjectNothing()).isFalse();
		assertThat(((IWeapon) new Knife()).isObjectNothing()).isFalse();
		assertThat(IWeapon.getObjectNothing().isObjectNothing()).isTrue();
	}

	@Test
	void aClubBlow_takesTheRightHand_elseTheLeft() {
		Cudgel cudgel = new Cudgel();
		Knife knife = new Knife();
		assertThat(ActionAttack.clubWeapon(NoSimulationObject.getObjectNothing(), cudgel)).isSameAs(cudgel);
		assertThat(ActionAttack.clubWeapon(knife, cudgel)).isSameAs(cudgel);
		assertThat(ActionAttack.clubWeapon(knife, new Bread())).isSameAs(knife);
		assertThat(ActionAttack.clubWeapon(new Bread(), NoSimulationObject.getObjectNothing())).isSameAs(IWeapon.getObjectNothing());
	}
}
