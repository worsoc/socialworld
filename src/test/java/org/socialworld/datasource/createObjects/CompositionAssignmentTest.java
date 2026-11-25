/*
 * Social World
 * Copyright (C) 2026  Mathias Sikos
 * Written with the assistance of Claude (Anthropic), 2026
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
package org.socialworld.datasource.createObjects;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.socialworld.attributes.BodyBuild;
import org.socialworld.attributes.properties.Material;
import org.socialworld.attributes.properties.MaterialSet;
import org.socialworld.objects.concrete.StateComposition;

/**
 * Which material set a new instance gets when the simulation derives it from swkind_materialset (body build
 * concept B3, 2026-11-25), and the setter the simulation reaches through HiddenSimulationObject.setSomething.
 */
class CompositionAssignmentTest {

	@Test
	void anItem_takesItsClass_aHuman_theTemplateOfItsBuild() {
		assertThat(CompositionAssignment.templateName("Beret", false, null)).isEqualTo("Beret");
		assertThat(CompositionAssignment.templateName("Human", true, BodyBuild.sturdy)).isEqualTo("Human.sturdy");
		assertThat(CompositionAssignment.simpleName("org.socialworld.objects.concrete.clothes.Beret")).isEqualTo("Beret");
		assertThat(CompositionAssignment.simpleName("Human")).isEqualTo("Human");
	}

	@Test
	void theVariant_isObjectIDModuloTheNumberOfVariants() {
		int[] sets = {100, 101, 102, 103, 104};
		assertThat(CompositionAssignment.setID(sets, 30000)).isEqualTo(100);
		assertThat(CompositionAssignment.setID(sets, 30017)).isEqualTo(102);
		assertThat(CompositionAssignment.setID(sets, 4)).isEqualTo(104);
		assertThat(CompositionAssignment.setID(new int[] {7, 8}, 3)).as("two alternatives only").isEqualTo(8);
	}

	// item class or composition, step 2 (2026-11-25): Hat[straw] takes only the variants whose main material is straw
	@Test
	void withAMaterial_onlyTheVariantsOfThatMainMaterialCount() {
		int[] hats = {10, 11, 12, 13, 14};	// the alternatives take turns: felt, straw, felt, straw, felt
		java.util.function.IntFunction<Material> main = id -> (id % 2 == 0) ? Material.felt : Material.straw;
		assertThat(CompositionAssignment.setID(hats, 30017, Material.straw, main)).as("30017 % 2 of 11, 13").isEqualTo(13);
		assertThat(CompositionAssignment.setID(hats, 4, Material.felt, main)).as("4 % 3 of 10, 12, 14").isEqualTo(12);
		assertThat(CompositionAssignment.setID(hats, 4, Material.gold, main)).isEqualTo(CompositionAssignment.NO_SET);
		assertThat(CompositionAssignment.setID(hats, 4, Material.nothing, main)).as("without material as before")
				.isEqualTo(CompositionAssignment.setID(hats, 4));
	}

	@Test
	void withoutTemplate_thereIsNoSet() {
		assertThat(CompositionAssignment.setID(new int[0], 1)).isEqualTo(CompositionAssignment.NO_SET);
		assertThat(CompositionAssignment.setID(null, 1)).isEqualTo(CompositionAssignment.NO_SET);
	}

	// State.setSomething finds the method by its name among the declared methods and calls it with one argument;
	// it says nothing if it does not find it. So the name has to be there exactly once, with a MaterialSet.
	@Test
	void theStateComposition_hasThePrivateSetter_thatTheSimulationCalls() {
		List<Method> found = new ArrayList<>();
		for (Method method : StateComposition.class.getDeclaredMethods()) {
			if (method.getName().equals(StateComposition.METHODNAME_SET_MATERIAL_SET)) found.add(method);
		}
		assertThat(found).hasSize(1);
		assertThat(found.get(0).getParameterTypes()).containsExactly(MaterialSet.class);
		assertThat(Modifier.isPrivate(found.get(0).getModifiers())).isTrue();
	}

	// objects without a composition of their own share the object nothing; setting a set there would give it to all
	@Test
	void theSetter_leavesTheSharedObjectNothingAlone() throws Exception {
		StateComposition nothing = StateComposition.getObjectNothing();
		Field field = StateComposition.class.getDeclaredField("materialSet");
		field.setAccessible(true);
		Object before = field.get(nothing);
		Method setter = StateComposition.class.getDeclaredMethod(StateComposition.METHODNAME_SET_MATERIAL_SET, MaterialSet.class);
		setter.setAccessible(true);
		setter.invoke(nothing, new MaterialSet());
		assertThat(field.get(nothing)).isSameAs(before);
	}
}
