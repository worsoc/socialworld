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
package org.socialworld.calculation.functions;

import org.socialworld.calculation.FunctionBase;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.collections.ValueArrayList;

/**
 * The direction of an action relative to a reference position (tag ActionDir, concept ERD X2).
 *
 * Arguments: 0 = reference position, 1 = actor position (both vectors).
 * <pre>
 *   d = (reference - actor) * (1, 1, kz)
 *   r = length * d / |d|    or the fallback, if a position is missing or |d| &lt; MIN_DISTANCE
 *   result = r + offset
 * </pre>
 * A positive length points to the reference, a negative one away from it.
 * The arithmetic is done by Calculation (and there by Vector).
 */
public class FunctionDirectionToReference extends FunctionBase {

	/** Below this distance the reference gives no direction, the fallback is used. Positions are in millimetres: 100 = 0.1 m. */
	public static final float MIN_DISTANCE = 100f;

	private final Value length;
	private final Value zWeight;
	private final Value offset;
	private final Value fallback;
	private final Value minDistance = new Value(Type.floatingpoint, MIN_DISTANCE);

	private static final java.util.Map<String, FunctionDirectionToReference> pool = new java.util.concurrent.ConcurrentHashMap<>();

	/** Same parameters give the same instance, so equal rules share one func_id. */
	public static FunctionDirectionToReference getInstance(float length, float kz, Vector offset, Vector fallback) {
		String key = length + ";" + kz + ";" + offset.getX() + "," + offset.getY() + "," + offset.getZ()
				+ ";" + fallback.getX() + "," + fallback.getY() + "," + fallback.getZ();
		return pool.computeIfAbsent(key, k -> new FunctionDirectionToReference(length, kz, offset, fallback));
	}

	private FunctionDirectionToReference(float length, float kz, Vector offset, Vector fallback) {
		this.length = new Value(Type.floatingpoint, length);
		this.zWeight = new Value(Type.vector, new Vector(1, 1, kz));
		this.offset = new Value(Type.vector, new Vector(offset.getX(), offset.getY(), offset.getZ()));
		this.fallback = new Value(Type.vector, new Vector(fallback.getX(), fallback.getY(), fallback.getZ()));
	}

	@Override
	public Value calculate(ValueArrayList arguments) {

		Value direction = fallback;

		if (arguments.size() >= 2) {
			Value reference = arguments.get(0);
			Value actor = arguments.get(1);

			if (isVector(reference) && isVector(actor)) {
				Value d = calculation.subtraction(reference, actor);
				d = calculation.multiplyComponents(d, zWeight);

				Value distance = calculation.length(d);
				if (distance.isValid() && calculation.compareGreaterEqual(distance, minDistance).isTrue()) {
					direction = calculation.multiplication(calculation.normalize(d), length);
				}
			}
		}

		return calculation.addition(direction, offset);
	}

	private static boolean isVector(Value value) {
		return value != null && value.isValid() && value.getType() == Type.vector;
	}
}
