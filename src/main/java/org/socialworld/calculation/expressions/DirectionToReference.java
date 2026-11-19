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

import org.socialworld.calculation.Expression;
import org.socialworld.calculation.Expression_Function;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.functions.FunctionDirectionToReference;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.datasource.pool.FunctionPool;

/**
 * Expression for the tag {@code <ActionDir>L;kz;(ox,oy,oz);(fx,fy,fz)</ActionDir>} (concept ERD X2).
 *
 * <ul>
 * <li>L: length of the part towards the reference; positive = towards, negative = away</li>
 * <li>kz: weight of the height difference, 0 = horizontal only, 1 = spatial</li>
 * <li>(ox,oy,oz): fixed offset added at the end, optional, default (0,0,0)</li>
 * <li>(fx,fy,fz): fallback if no direction can be determined, optional, default (0,0,0)</li>
 * </ul>
 *
 * The reference position and the actor position are read by name from the arguments
 * ({@link Value#VALUE_BY_NAME_ACTION_REFERENCE_POSITION}, {@link Value#VALUE_BY_NAME_ACTION_ACTOR_POSITION}).
 * What the reference is, is decided by the place that evaluates the rule, not by the tag.
 */
public class DirectionToReference extends Expression {

	public DirectionToReference(String formula) {
		super();
		init(formula);
	}

	private void init(String formula) {

		if (formula == null) return;
		String[] elements = formula.split(";");
		if (elements.length < 2 || elements.length > 4) return;

		float length;
		float kz;
		Vector offset = new Vector(0, 0, 0);
		Vector fallback = new Vector(0, 0, 0);

		try {
			length = Float.parseFloat(elements[0].trim());
			kz = Float.parseFloat(elements[1].trim());
			if (elements.length >= 3) offset = parseVector(elements[2]);
			if (elements.length == 4) fallback = parseVector(elements[3]);
		}
		catch (IllegalArgumentException e) {
			// NumberFormatException included: the expression stays invalid
			return;
		}

		FunctionDirectionToReference function = FunctionDirectionToReference.getInstance(length, kz, offset, fallback);

		setFuncID(FunctionPool.getInstance().register(function));
		setFunction(function);

		setOperation(Expression_Function.function);

		setExpression1(new GetArgumentByName(Value.VALUE_BY_NAME_ACTION_REFERENCE_POSITION));
		setExpression2(new GetArgumentByName(Value.VALUE_BY_NAME_ACTION_ACTOR_POSITION));
		setExpression3(Nothing.getInstance());

		setValid();
	}

	/** "(x,y,z)" with exactly three numbers; otherwise IllegalArgumentException. */
	private static Vector parseVector(String text) {
		String trimmed = text.trim();
		if (!trimmed.startsWith("(") || !trimmed.endsWith(")")) {
			throw new IllegalArgumentException("no vector: " + text);
		}
		String[] parts = trimmed.substring(1, trimmed.length() - 1).split(",");
		if (parts.length != 3) throw new IllegalArgumentException("no vector: " + text);
		return new Vector(Float.parseFloat(parts[0].trim()), Float.parseFloat(parts[1].trim()), Float.parseFloat(parts[2].trim()));
	}
}
