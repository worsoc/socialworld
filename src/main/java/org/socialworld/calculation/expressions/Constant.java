/*
 * Social World
 * Copyright (C) 2019  Mathias Sikos
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
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;

public class Constant extends Expression {

	public static final Constant INTEGER_1 = new Constant(
	        new Value(Type.integer, 1)
	    );
	public static final  Constant FLOATINGPOINT_1_DOT_0 = new Constant(
	        new Value(Type.floatingpoint, 1.0F)
	    );
	/**
	 * The steepness k of the sigmoid for comparisons of attribute values:
	 * 5 * AttributeArray.ATTRIBUTE_VALUE_SCALE = 5 * 100 = 500.
	 * Stored as the finished number, so no evaluation has to multiply.
	 */
	public static final Constant SIGMOID_K_ATTRIBUTE = new Constant(
	        new Value(Type.floatingpoint, 500.0F)
	    );

	public Constant(Value constant) {
		
		super();
		
		setOperation(Expression_Function.value);
		setValue(constant);
		
		setValid();

	}

}
