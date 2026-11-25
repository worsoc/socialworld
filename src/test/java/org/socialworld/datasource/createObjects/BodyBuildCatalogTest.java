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

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.socialworld.attributes.BodyBuild;
import org.socialworld.tools.dct.AttributeCharacter;
import org.socialworld.tools.dct.AttributeCharacterCatalog;

/**
 * The reader of the body builds for the running simulation (body build concept B3, 2026-11-25): it reads the same
 * column of characters.csv as the AttributeCharacterCatalog of the DCT.
 */
class BodyBuildCatalogTest {

	@Test
	void theCatalog_isReadWithoutProblems_andHasAll100Characters() {
		BodyBuildCatalog catalog = BodyBuildCatalog.load();
		assertThat(catalog.getProblems()).isEmpty();
		assertThat(catalog.size()).isEqualTo(100);
	}

	@Test
	void everyCharacter_hasTheBuildThatTheDctReads() {
		BodyBuildCatalog catalog = BodyBuildCatalog.load();
		for (AttributeCharacter character : AttributeCharacterCatalog.load().getCharacters()) {
			assertThat(catalog.get(character.getNumber())).as(character.getName()).isSameAs(character.getBuild());
		}
	}

	@Test
	void withoutTheColumn_orForAnUnknownNumber_theDefault_andAnUnknownBuildIsNamed() {
		List<String> lines = Arrays.asList(
				"# a comment", "",
				"1;a;b;o;o;o;o;o;o;o;o;o;8.0;3;20;70;40;60;stout",
				"2;a;b;o;o;o;o;o;o;o;o;o;8.0;3;20;70;40;60",
				"3;a;b;o;o;o;o;o;o;o;o;o;8.0;3;20;70;40;60;giant",
				"x;a;b");
		BodyBuildCatalog catalog = BodyBuildCatalog.parse(lines);
		assertThat(catalog.get(1)).isSameAs(BodyBuild.stout);
		assertThat(catalog.get(2)).isSameAs(BodyBuild.DEFAULT);
		assertThat(catalog.get(3)).as("left out").isSameAs(BodyBuild.DEFAULT);
		assertThat(catalog.get(99)).isSameAs(BodyBuild.DEFAULT);
		assertThat(catalog.size()).isEqualTo(2);
		assertThat(catalog.getProblems()).hasSize(2);
		assertThat(String.join("\n", catalog.getProblems())).contains("giant", "no character number");
	}
}
