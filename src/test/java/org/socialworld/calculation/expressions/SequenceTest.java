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
package org.socialworld.calculation.expressions;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.socialworld.calculation.Calculation;
import org.socialworld.calculation.Expression;
import org.socialworld.calculation.Expression_Function;
import org.socialworld.calculation.Value;
import org.socialworld.collections.ValueArrayList;

/**
 * Sequence splits its expressions into a tree of groups of three. Every
 * expression must run exactly once and in the given order, whatever the count.
 */
class SequenceTest {

	/** Records its number in a shared log when evaluated. */
	private static final class Recorder extends Expression {
		private final int number;
		private final List<Integer> log;

		Recorder(int number, List<Integer> log) {
			this.number = number;
			this.log = log;
			// a new Expression has the operation nothing, and Sequence skips a third
			// part with nothing; real entries (Branching) have a real operation
			setOperation(Expression_Function.value);
			setValid();
		}

		@Override
		public Value evaluate(ValueArrayList arguments) {
			log.add(number);
			return Calculation.getNothing();
		}
	}

	private static List<Integer> runSequence(int count) {
		List<Integer> log = new ArrayList<>();
		Expression[] expressions = new Expression[count];
		for (int i = 0; i < count; i++) {
			expressions[i] = new Recorder(i, log);
		}
		new Sequence(expressions).evaluate(ValueArrayList.EMPTY_ARGUMENTS);
		return log;
	}

	@Test
	void everyExpressionRunsOnceInOrder_forCounts1To15() {
		for (int count = 1; count <= 15; count++) {
			List<Integer> expected = new ArrayList<>();
			for (int i = 0; i < count; i++) {
				expected.add(i);
			}
			assertThat(runSequence(count)).as("count " + count).isEqualTo(expected);
		}
	}

	@Test
	void emptySequenceRunsNothing() {
		assertThat(runSequence(0)).isEmpty();
	}
}
