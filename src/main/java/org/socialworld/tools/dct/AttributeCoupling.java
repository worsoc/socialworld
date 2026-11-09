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
package org.socialworld.tools.dct;

import org.socialworld.attributes.Attribute;
import org.socialworld.calculation.functions.FunctionByTable_Type;

/**
 * One named influence of an attribute on another one in an attribute calculation matrix.
 * The strength is given in displayed points per matrix step at full function value.
 */
public final class AttributeCoupling {

	public enum Moderation { none, dampens, amplifies, proportionalPower }

	final String id;
	final Attribute source;
	final Attribute target;
	final FunctionByTable_Type function;
	final float strength;
	final Attribute moderator;
	final Moderation moderation;

	AttributeCoupling(String id, Attribute source, Attribute target, FunctionByTable_Type function, float strength,
			Attribute moderator, Moderation moderation) {
		this.id = id;
		this.source = source;
		this.target = target;
		this.function = function;
		this.strength = strength;
		this.moderator = moderator;
		this.moderation = moderation;
	}

	AttributeCoupling withStrength(float newStrength) {
		return new AttributeCoupling(id, source, target, function, newStrength, moderator, moderation);
	}

	/** Factor for a level, see attribute_matrix_characters.md, section 5. */
	float factorFor(AttributeLevel level, int targetPower) {
		switch (moderation) {
		case dampens:
			return dampening(level);
		case amplifies:
			return amplification(level);
		case proportionalPower:
			return Math.min(1f, dampening(level)) * targetPower / 5000f;
		default:
			return 1f;
		}
	}

	private static float dampening(AttributeLevel level) {
		switch (level) {
		case extremelyLow: return 1.8f;
		case veryLow: return 1.5f;
		case low: return 1.2f;
		case high: return 0.7f;
		case veryHigh: return 0.4f;
		case extremelyHigh: return 0.15f;
		default: return 1f;
		}
	}

	private static float amplification(AttributeLevel level) {
		switch (level) {
		case extremelyLow: return 0.15f;
		case veryLow: return 0.4f;
		case low: return 0.7f;
		case high: return 1.3f;
		case veryHigh: return 1.6f;
		case extremelyHigh: return 2f;
		default: return 1f;
		}
	}
}
