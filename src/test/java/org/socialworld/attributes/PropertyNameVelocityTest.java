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
package org.socialworld.attributes;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.socialworld.calculation.Type;

/**
 * The two velocities of the technical position movement (movement concept G1, step M1).
 */
public class PropertyNameVelocityTest {

  @Test
  void velocities_arePropertiesOfTheSimulationObject() {
    assertThat(PropertyName.simobj_velocityActive.getType()).isEqualTo(Type.simObjProp);
    assertThat(PropertyName.simobj_velocityPassive.getType()).isEqualTo(Type.simObjProp);
    // not mixed up with the move direction (toType maps names with "direction" to simobj_directionMove)
    assertThat(PropertyName.simobj_velocityActive.toType(Type.simObjProp)).isEqualTo(PropertyName.simobj_velocityActive);
    assertThat(PropertyName.simobj_velocityPassive.toType(Type.simObjProp)).isEqualTo(PropertyName.simobj_velocityPassive);
  }

  @Test
  void velocities_haveTheirOwnIndexes() {
    assertThat(PropertyName.simobj_velocityActive.getIndex()).isEqualTo(1001024);
    assertThat(PropertyName.simobj_velocityPassive.getIndex()).isEqualTo(1001025);

    Set<Integer> indexes = new HashSet<Integer>();
    for (PropertyName name : PropertyName.values()) {
      if (name.getIndex() >= 1001000 && name.getIndex() < 1002000) {
        assertThat(indexes.add(name.getIndex())).as(name.name()).isTrue();
      }
    }
  }
}
