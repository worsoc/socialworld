/*
 * Social World
 * Copyright (C) 2022  Mathias Sikos
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
package org.socialworld.objects.concrete.eatable;

import org.socialworld.objects.concrete.StatePerceptible;
import org.socialworld.objects.concrete.StateComposition;
import org.socialworld.objects.concrete.StateAppearance;
import org.socialworld.objects.Item;
import org.socialworld.objects.State;
import org.socialworld.objects.enums.EnumBaseSimObj;
import org.socialworld.objects.enums.EnumLiquid;
import org.socialworld.objects.statics.Mapping_Liquid2LexemIDLowerPart;

public abstract class Liquid extends Item {

	protected EnumLiquid belongsTo;
	
///////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////meta information    ////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	

	public Liquid() {
		super();
		setBaseSimObjEnum(EnumBaseSimObj.Liquid);
	}

	@Override
	protected final int getLexemIdLowPart() {
		return Mapping_Liquid2LexemIDLowerPart.getInstance().get(belongsTo);
	}

	@Override
	public boolean checkObjectBelongsToGroup(int groupNumberSuffix) {
		// TODO Auto-generated method stub
		return false;
	}

	
	

	/**
	 * A liquid can be perceived, has an appearance and a composition, as every item (2026-11-23: before NoState,
	 * which SimulationObject.createAddOnStates cannot cast, so no liquid could be created).
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

}
