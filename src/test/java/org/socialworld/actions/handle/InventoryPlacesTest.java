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
import org.socialworld.calculation.ValueProperty;
import org.socialworld.objects.NoSimulationObject;
import org.socialworld.objects.SimulationObject_Type;
import org.socialworld.objects.concrete.clothes.caps.StrawHat;
import org.socialworld.objects.concrete.clothes.dresses.LinenDress;
import org.socialworld.objects.concrete.clothes.gloves.LeatherGlove;
import org.socialworld.objects.concrete.clothes.overgarments.Apron;
import org.socialworld.objects.concrete.clothes.overgarments.ChainMail;
import org.socialworld.objects.concrete.clothes.overgarments.Cloak;
import org.socialworld.objects.concrete.clothes.shirts.LinenShirt;
import org.socialworld.objects.concrete.clothes.shoes.LeatherShoe;
import org.socialworld.objects.concrete.clothes.trousers.WorkTrousers;
import org.socialworld.objects.concrete.containers.Purse;
import org.socialworld.objects.concrete.containers.Sack;
import org.socialworld.objects.concrete.containers.Satchel;
import org.socialworld.objects.concrete.devotional.Amulet;
import org.socialworld.objects.concrete.devotional.Rosary;
import org.socialworld.objects.concrete.eatable.bakery.Bread;
import org.socialworld.objects.concrete.eatable.liquids.Beer;
import org.socialworld.objects.concrete.pastime.Lute;
import org.socialworld.objects.concrete.solids.Stone;
import org.socialworld.objects.concrete.tools.Hammer;
import org.socialworld.objects.concrete.tools.Knife;
import org.socialworld.objects.concrete.tools.Pitchfork;
import org.socialworld.objects.concrete.valuables.Ring;
import org.socialworld.objects.concrete.weapons.Dagger;
import org.socialworld.objects.concrete.weapons.LongSword;
import org.socialworld.objects.concrete.weapons.Shield;
import org.socialworld.objects.properties.CarrySize;
import org.socialworld.objects.properties.ICarryable;

/**
 * The places of the inventory (inventory concept V1, 2026-11-23): one object per place, empty places answer
 * NoSimulationObject (F4), what fits where (IWearable, IEatable/IDrinkable, ICarryable with its size), and the
 * dress that takes the upper body and the legs.
 */
class InventoryPlacesTest {

	private static Inventory inventory() {
		return new Inventory(SimulationObject_Type.human);
	}

	@Test
	void emptyPlaces_answerNoSimulationObject_notNull() {
		Inventory inventory = inventory();
		for (InventoryPlace place : InventoryPlace.values()) {
			assertThat(inventory.get(place)).as(place.name()).isSameAs(NoSimulationObject.getObjectNothing());
		}
		assertThat(inventory.getLeftHand()).isSameAs(NoSimulationObject.getObjectNothing());
		ValueProperty hand = inventory.getProperty(null, PropertyName.inventory_rightHand, PropertyName.inventory_rightHand.toString());
		assertThat(hand.isValid()).isTrue();
	}

	@Test
	void oldPlaceIndices_stayAsTheyWere() {
		assertThat(InventoryPlace.getName(1)).isEqualTo(InventoryPlace.leftHand);
		assertThat(InventoryPlace.getName(2)).isEqualTo(InventoryPlace.rightHand);
		assertThat(InventoryPlace.getName(3)).isEqualTo(InventoryPlace.mouth);
		assertThat(InventoryPlace.getName(11)).isEqualTo(InventoryPlace.leftHandGlove);
		assertThat(InventoryPlace.getName(16)).isEqualTo(InventoryPlace.rightFootShoe);
		assertThat(InventoryPlace.getMaxIndex()).isEqualTo(45);
	}

	@Test
	void clothes_fitTheirPlaceOnly() {
		assertThat(Inventory.fits(InventoryPlace.head, new StrawHat())).isTrue();
		assertThat(Inventory.fits(InventoryPlace.upperBody, new LinenShirt())).isTrue();
		assertThat(Inventory.fits(InventoryPlace.legs, new WorkTrousers())).isTrue();
		assertThat(Inventory.fits(InventoryPlace.dress, new LinenDress())).isTrue();
		assertThat(Inventory.fits(InventoryPlace.head, new LinenShirt())).isFalse();
		assertThat(Inventory.fits(InventoryPlace.upperBody, new LinenDress())).isFalse();
	}

	@Test
	void theSide_decidesLeftOrRight() {
		LeatherShoe shoe = new LeatherShoe();
		assertThat(Inventory.fits(InventoryPlace.leftFootShoe, shoe)).isTrue();
		assertThat(Inventory.fits(InventoryPlace.rightFootShoe, shoe)).isFalse();
		shoe.setLeftSide(false);
		assertThat(Inventory.fits(InventoryPlace.rightFootShoe, shoe)).isTrue();
		LeatherGlove glove = new LeatherGlove();
		glove.setLeftSide(false);
		assertThat(Inventory.fits(InventoryPlace.rightHandGlove, glove)).isTrue();
		assertThat(Inventory.fits(InventoryPlace.leftHandGlove, glove)).isFalse();
	}

	@Test
	void threeOvergarmentPlaces_apronMailAndCloakTogether() {
		Inventory inventory = inventory();
		assertThat(inventory.put(InventoryPlace.overgarmentFront, new Apron())).isTrue();
		assertThat(inventory.put(InventoryPlace.overgarmentArmour, new ChainMail())).isTrue();
		assertThat(inventory.put(InventoryPlace.overgarmentCloak, new Cloak())).isTrue();
		assertThat(Inventory.fits(InventoryPlace.overgarmentCloak, new Apron())).isFalse();
	}

	@Test
	void jewellery_ringAtTheFinger_amuletAndRosaryAtTheNeck() {
		assertThat(Inventory.fits(InventoryPlace.finger, new Ring())).isTrue();
		assertThat(Inventory.fits(InventoryPlace.neck, new Ring())).isFalse();
		assertThat(Inventory.fits(InventoryPlace.neck, new Amulet())).isTrue();
		assertThat(Inventory.fits(InventoryPlace.neck, new Rosary())).isTrue();
		assertThat(Inventory.fits(InventoryPlace.bag, new Satchel())).isTrue();
	}

	@Test
	void aDress_needsUpperBodyAndLegsFree_andBlocksThem() {
		Inventory inventory = inventory();
		assertThat(inventory.put(InventoryPlace.upperBody, new LinenShirt())).isTrue();
		assertThat(inventory.canPut(InventoryPlace.dress, new LinenDress())).isFalse();

		Inventory other = inventory();
		assertThat(other.put(InventoryPlace.dress, new LinenDress())).isTrue();
		assertThat(other.canPut(InventoryPlace.upperBody, new LinenShirt())).isFalse();
		assertThat(other.canPut(InventoryPlace.legs, new WorkTrousers())).isFalse();
	}

	@Test
	void aTakenPlace_takesNoSecondItem_untilRemoved() {
		Inventory inventory = inventory();
		Knife knife = new Knife();
		assertThat(inventory.put(InventoryPlace.rightHand, knife)).isTrue();
		assertThat(inventory.put(InventoryPlace.rightHand, new Hammer())).isFalse();
		assertThat(inventory.remove(InventoryPlace.rightHand)).isSameAs(knife);
		assertThat(inventory.isFree(InventoryPlace.rightHand)).isTrue();
		assertThat(inventory.remove(InventoryPlace.rightHand)).isSameAs(NoSimulationObject.getObjectNothing());
	}

	@Test
	void hands_takeEverythingCarryable_liquidsNeedAContainer() {
		for (ICarryable c : new ICarryable[] {new Knife(), new Pitchfork(), new Bread(), new Stone(), new LinenShirt()}) {
			assertThat(Inventory.fits(InventoryPlace.rightHand, (org.socialworld.objects.SimulationObject) c)).as(c.getClass().getSimpleName()).isTrue();
		}
		assertThat(new Beer()).isNotInstanceOf(ICarryable.class);
		assertThat(Inventory.fits(InventoryPlace.rightHand, new Beer())).isFalse();
		assertThat(Inventory.fits(InventoryPlace.mouth, new Beer())).isTrue();
		assertThat(Inventory.fits(InventoryPlace.mouth, new Bread())).isTrue();
		assertThat(Inventory.fits(InventoryPlace.mouth, new Knife())).isFalse();
	}

	@Test
	void belt_takesSmallThings_back_takesLargeOnes() {
		assertThat(new Knife().getCarrySize()).isEqualTo(CarrySize.small);
		assertThat(Inventory.fits(InventoryPlace.belt1, new Knife())).isTrue();
		assertThat(Inventory.fits(InventoryPlace.belt2, new Purse())).isTrue();
		assertThat(Inventory.fits(InventoryPlace.belt3, new Dagger())).isTrue();
		assertThat(Inventory.fits(InventoryPlace.belt1, new Hammer())).isFalse();	// medium
		assertThat(Inventory.fits(InventoryPlace.back, new Pitchfork())).isTrue();
		assertThat(Inventory.fits(InventoryPlace.back, new Sack())).isTrue();
		assertThat(Inventory.fits(InventoryPlace.back, new Shield())).isTrue();
		assertThat(Inventory.fits(InventoryPlace.back, new Knife())).isFalse();
	}

	@Test
	void exceptions_luteOnTheBack_swordAtTheBelt() {
		assertThat(new Lute().getCarrySize()).isEqualTo(CarrySize.medium);
		assertThat(Inventory.fits(InventoryPlace.back, new Lute())).isTrue();
		assertThat(Inventory.fits(InventoryPlace.belt1, new Lute())).isFalse();
		assertThat(Inventory.fits(InventoryPlace.belt1, new LongSword())).isTrue();
		assertThat(Inventory.fits(InventoryPlace.back, new LongSword())).isTrue();
	}

	@Test
	void theOldSetters_keepWorking() {
		Inventory inventory = Inventory.getTestInventory(SimulationObject_Type.human);
		assertThat(inventory.get(InventoryPlace.head).isSimulationObject()).isTrue();
		assertThat(inventory.get(InventoryPlace.upperBody).isSimulationObject()).isTrue();
		assertThat(inventory.get(InventoryPlace.leftFootShoe)).isNotSameAs(inventory.get(InventoryPlace.rightFootShoe));
		// null (as LoadHuman passes it for an empty hand) empties the place
		inventory.setLeftHand(null);
		assertThat(inventory.isFree(InventoryPlace.leftHand)).isTrue();
	}
}
