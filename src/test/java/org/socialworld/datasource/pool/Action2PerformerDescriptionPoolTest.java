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
package org.socialworld.datasource.pool;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.socialworld.actions.ActionMode;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.descriptions.Action2PerformerAssignment;
import org.socialworld.calculation.descriptions.Action2PerformerDescription;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.expressions.Calculate;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.collections.ValueArrayList;

public class Action2PerformerDescriptionPoolTest {

  private static String constant(int value) {
    return "CONST(" + Type.integer.getIndex() + " " + value + ")";
  }

  @Test
  void scaleConstant_isTheAttributeScale() {
    assertThat(Action2PerformerDescriptionPool.CONST_ATTRIBUTE_VALUE_SCALE).isEqualTo("CONST(1 100)");
  }

  @Test
  void commaInsideTheFirstOperand_isNotTheSeparator() {
    // before the fix the comma inside MUL(...) was taken as the separator of the outer SUB
    Calculate expression = new Calculate("SUB(MUL(" + constant(6) + "," + constant(7) + ")," + constant(2) + ")", "x");

    assertThat(expression.isValid()).isTrue();
    assertThat(((Number) expression.evaluate(new ValueArrayList()).getObject(Type.integer)).intValue()).isEqualTo(40);
  }

  @Test
  void commaOnlyInTheSecondOperand_worksAsBefore() {
    Calculate expression = new Calculate("SUB(" + constant(50) + ",MUL(" + constant(6) + "," + constant(7) + "))", "x");

    assertThat(((Number) expression.evaluate(new ValueArrayList()).getObject(Type.integer)).intValue()).isEqualTo(8);
  }

  @Test
  void intensityTimesPower_isDividedByTheScale() {
    // action intensity 30.0 (a float), power 5000 (displayed 50): 30 * 50 = 1500 as before with 0..99
    String intensity = "CONST(" + Type.floatingpoint.getIndex() + " 30.0)";
    String term = "DIV(MUL(" + intensity + "," + constant(5000) + "),"
        + Action2PerformerDescriptionPool.CONST_ATTRIBUTE_VALUE_SCALE + ")";

    Calculate expression = new Calculate(term, "intensity");
    Value result = expression.evaluate(new ValueArrayList());

    assertThat(expression.isValid()).isTrue();
    assertThat(result.getType()).isEqualTo(Type.floatingpoint);
    assertThat(((Number) result.getObject(Type.floatingpoint)).floatValue()).isEqualTo(1500.0f);
  }

  @Test
  void everyMoveMode_givesTheEventTheIntensityOfTheAction() {
    ActionMode[] moves = { ActionMode.walk, ActionMode.run, ActionMode.sneak, ActionMode.jump, ActionMode.swim, ActionMode.fly };
    ValueArrayList properties = new ValueArrayList();
    properties.add(new Value(Type.floatingpoint, Value.VALUE_BY_NAME_ACTION_INTENSITY, 120f));

    for (ActionMode mode : moves) {
      Action2PerformerDescription description = Action2PerformerAssignment.getInstance().getAction2PerformerDescription(mode);

      // direction, acceleration, velocity, intensity
      assertThat(description.countFunctions()).as(mode.name()).isEqualTo(4);
      Value intensity = description.getFunction(3).calculate(properties);
      assertThat(intensity.getName()).as(mode.name()).isEqualTo(Value.VALUE_BY_NAME_EVENT_INTENSITY);
      // taken as it is, not multiplied by the power (the rules already derive it from the power)
      assertThat(((Number) intensity.getObject(Type.floatingpoint)).floatValue()).as(mode.name()).isEqualTo(120f);
    }
  }

  @Test
  void everyBlow_hasADescription_withTheGeneralEventNames() {
    ActionMode[] punches = { ActionMode.punchLeftFistStraight, ActionMode.punchLeftFistSideways, ActionMode.punchLeftFistUpward,
        ActionMode.punchRightFistStraight, ActionMode.punchRightFistSideways, ActionMode.punchRightFistUpward };
    ActionMode[] weapons = { ActionMode.weaponLeftStab, ActionMode.weaponLeftStroke, ActionMode.weaponLeftBackhand,
        ActionMode.weaponRightStab, ActionMode.weaponRightStroke, ActionMode.weaponRightBackhand, ActionMode.weaponClub };
    ValueArrayList properties = new ValueArrayList();
    properties.add(new Value(Type.floatingpoint, Value.VALUE_BY_NAME_ACTION_INTENSITY, 80f));
    properties.add(new Value(Type.vector, PropertyName.stateSeer_directionView.toString(), new Vector(1, 0, 0)));

    for (ActionMode mode : punches) assertBlowDescription(mode, 5, properties);
    for (ActionMode mode : weapons) assertBlowDescription(mode, 6, properties);
  }

  private static void assertBlowDescription(ActionMode mode, int functions, ValueArrayList properties) {
    Action2PerformerDescription description = Action2PerformerAssignment.getInstance().getAction2PerformerDescription(mode);
    // direction, actor's intensity, event intensity, (weapon,) chest, view
    assertThat(description.countFunctions()).as(mode.name()).isEqualTo(functions);
    assertThat(description.getFunction(0).calculate(properties).getName()).as(mode.name()).isEqualTo(Value.VALUE_BY_NAME_EVENT_DIRECTION);
    Value actorsIntensity = description.getFunction(1).calculate(properties);
    assertThat(actorsIntensity.getName()).as(mode.name()).isEqualTo(Value.VALUE_BY_NAME_ACTION_INTENSITY);
    assertThat(((Number) actorsIntensity.getObject(Type.floatingpoint)).floatValue()).as(mode.name()).isEqualTo(80f);
    // function 2 (event_intensity) needs ATTR(power) of a real actor; its arithmetic is checked by
    // intensityTimesPower_isDividedByTheScale
  }

  @Test
  void moveVelocity_isTakenAsItIs_theAccelerationIsNotAddedTwice() {
    Action2PerformerDescription description = Action2PerformerAssignment.getInstance().getAction2PerformerDescription(ActionMode.walk);
    ValueArrayList properties = new ValueArrayList();
    properties.add(new Value(Type.floatingpoint, Value.VALUE_BY_NAME_ACTION_MOVE_VELOCITY, 100f));
    properties.add(new Value(Type.floatingpoint, Value.VALUE_BY_NAME_ACTION_MOVE_ACCELERATION, 50f));

    // function 2 is the velocity (direction, acceleration, velocity, intensity)
    Value velocity = description.getFunction(2).calculate(properties);

    assertThat(velocity.getName()).isEqualTo(Value.VALUE_BY_NAME_EVENT_MOVE_VELOCITY);
    assertThat(((Number) velocity.getObject(Type.floatingpoint)).floatValue()).isEqualTo(100f);
  }
}
