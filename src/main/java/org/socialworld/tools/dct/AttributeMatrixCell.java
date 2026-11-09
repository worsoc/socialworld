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

import java.util.Locale;

import org.socialworld.attributes.Attribute;
import org.socialworld.calculation.functions.FunctionByTable_Type;
import org.socialworld.tools.dct.AttributeDataReader.StoredMatrix;

/**
 * What one cell of a stored matrix means and how it is shown.
 */
final class AttributeMatrixCell {

	enum Kind { EMPTY, DIAGONAL, COUPLING, CONSTANT }

	private final Kind kind;
	private final int row;
	private final int column;
	private final float share;
	private final int function;

	private AttributeMatrixCell(Kind kind, int row, int column, float share, int function) {
		this.kind = kind;
		this.row = row;
		this.column = column;
		this.share = share;
		this.function = function;
	}

	static AttributeMatrixCell of(StoredMatrix matrix, int row, int column) {
		float share = matrix.getShare(row, column);
		int function = matrix.getFunction(row, column);
		return new AttributeMatrixCell(kindOf(row, column, share, function), row, column, share, function);
	}

	private static Kind kindOf(int row, int column, float share, int function) {
		if (row == column) return Kind.DIAGONAL;
		if (share == 0f) return Kind.EMPTY;
		if (function == FunctionByTable_Type.horizontal_max.getIndex()) return Kind.CONSTANT;
		return Kind.COUPLING;
	}

	Kind getKind() {
		return kind;
	}

	/**
	 * @return displayed points per step at the full value of the function
	 */
	float getStrength() {
		return share * 100f;
	}

	String getValueText() {
		switch (kind) {
		case EMPTY:
			return "";
		case DIAGONAL:
			return share > 1f ? format("+%.4f", share - 1f) : format("%.3f", 1f - share);
		default:
			return format("%+.2f", getStrength());
		}
	}

	String getFunctionText() {
		switch (kind) {
		case COUPLING:
			return functionCode(function);
		case CONSTANT:
			return "konst";
		default:
			return "";
		}
	}

	String getToolTip() {
		String source = Attribute.getAttributeName(row).toString();
		String target = Attribute.getAttributeName(column).toString();

		switch (kind) {
		case EMPTY:
			return source + " → " + target + ": keine Wirkung";
		case DIAGONAL:
			String rate = share > 1f ? "Wachstum " + format("%.5f", share - 1f) : "Rückkehrrate " + format("%.5f", 1f - share);
			return target + ": Anteil " + format("%.5f", share) + ", " + rate;
		case CONSTANT:
			return "Konstante für " + target + ": " + format("%+.2f", getStrength())
					+ " Punkte je Schritt, Anteil " + format("%.7f", share) + " (die Zeile ist nur ein Platzhalter)";
		default:
			return source + " → " + target + ": " + format("%+.2f", getStrength())
					+ " Punkte je Schritt bei vollem Funktionswert, Anteil " + format("%.7f", share)
					+ ", Funktion " + functionName(function);
		}
	}

	private static String format(String pattern, float value) {
		return String.format(Locale.ROOT, pattern, value);
	}

	private static String functionName(int function) {
		FunctionByTable_Type type = FunctionByTable_Type.getType(function);
		return type == null ? "Nr. " + function : type.name();
	}

	private static String functionCode(int function) {
		FunctionByTable_Type type = FunctionByTable_Type.getType(function);
		if (type == null) return "#" + function;
		switch (type) {
		case hinge_high: return "hh";
		case hinge_low: return "hl";
		case v: return "v";
		case v_mirrored: return "vm";
		case u: return "u";
		case u_mirrored: return "um";
		case identical: return "id";
		case positive_raise: return "pos";
		case negative_raise: return "neg";
		case horizontal_min: return "min";
		default: return type.name();
		}
	}
}
