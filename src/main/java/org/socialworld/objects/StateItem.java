/*
 * Social World
 * Copyright (C) 2015  Mathias Sikos
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
package org.socialworld.objects;

import org.socialworld.objects.enums.EnumSide;
import org.socialworld.tools.Generation;
import org.socialworld.tools.StringTupel;

/**
 * @author Mathias Sikos
 *
 */
public class StateItem extends StateSimulationObject {

	///////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////// meta information    ////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	private static StateItem singletonDummyForGenerationTools;
	private static StringTupel[] propertiesMetaInfos = new StringTupel[]{
		} ;
	
	public static StateItem getInstance(Generation calledFromGeneration) {
		if (singletonDummyForGenerationTools == null) {
			singletonDummyForGenerationTools = new StateItem(calledFromGeneration);
		}
		return singletonDummyForGenerationTools;
	}
	
	protected StateItem(Generation calledFromGeneration) {
		super(calledFromGeneration);
		listOfPropertyMetaInfo = super.getPropertiesMetaInfos();
		for (int indexAdd = 0; indexAdd < propertiesMetaInfos.length; indexAdd++) {
			listOfPropertyMetaInfo.add(propertiesMetaInfos[indexAdd]);
		}
	}


///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    creating instance for simulation    //////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public StateItem() {
		super();
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    SIDE      ////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	// the side (left, right or none) of the item (protection concept S3, 2026-11-24); set once at the creation,
	// written only through HiddenItem and WriteAccessToItem with the grant side
	private EnumSide side = EnumSide.none;

	final void setSide(EnumSide side, WriteAccessToItem guard) {
		if (checkGuard(guard) && side != null) this.side = side;
	}

	final EnumSide getSide() {
		return this.side;
	}
	
}
