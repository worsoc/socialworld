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
package org.socialworld.objects.concrete.solids;

import org.socialworld.objects.properties.CarrySize;
import org.socialworld.objects.properties.ICarryable;
import org.socialworld.objects.Item;
import org.socialworld.objects.State;
import org.socialworld.objects.concrete.StateAppearance;
import org.socialworld.objects.concrete.StateComposition;
import org.socialworld.objects.concrete.StatePerceptible;
import org.socialworld.objects.enums.EnumBaseSimObj;
import org.socialworld.objects.enums.EnumSolid;
import org.socialworld.objects.statics.Mapping_Solid2LexemIDLowerPart;

/**
 * A lifeless solid thing of nature: a stone, charcoal, ore.
 * The class shows the main property only; other abilities come from interfaces (e.g. IThrowable).
 */
public abstract class Solid extends Item implements ICarryable {

	protected EnumSolid belongsTo;

	public Solid() {
		super();
		setBaseSimObjEnum(EnumBaseSimObj.Solid);
	}

	@Override
	protected final int getLexemIdLowPart() {
		return Mapping_Solid2LexemIDLowerPart.getInstance().get(belongsTo);
	}

	@Override
	public boolean checkObjectBelongsToGroup(int groupNumberSuffix) {
		// TODO as in the other item groups
		return false;
	}

	/**
	 * It can be perceived, has an appearance and a composition, as the human crafted things.
	 */
	@Override
	protected State getInitState(String stateClassName) {
		State initState = State.getObjectNothing();

		if (stateClassName.equals(StatePerceptible.class.getName())) {
			initState = new StatePerceptible(this);
		}
		else if (stateClassName.equals(StateAppearance.class.getName())) {
			initState = new StateAppearance(this);
		}
		else if (stateClassName.equals(StateComposition.class.getName())) {
			initState = new StateComposition(this);
		}

		return initState;
	}

	/** carried: small by default for the whole group (inventory concept, D5); exceptions override it */
	public CarrySize getCarrySize() {return CarrySize.small; }

}
