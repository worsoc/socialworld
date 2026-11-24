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

import org.socialworld.objects.access.GrantedAccessToProperty;
import org.socialworld.objects.access.HiddenItem;
import org.socialworld.objects.access.HiddenSimulationObject;
import org.socialworld.objects.enums.EnumSide;

/**
 * @author Mathias Sikos
 *
 */
public class WriteAccessToItem extends WriteAccessToSimulationObject {

	private  Item item;
	private StateItem itemState;
	
	public WriteAccessToItem(Item item, StateItem state ) {
		super(item, state);
		this.item = item;
		this.itemState = state;
	}

	public HiddenItem getMeHidden(GrantedAccessToProperty properties[]) {
		return new HiddenItem(this, properties);
	}

	/** The side of the item (protection concept S3): only with the grant side. */
	public int setSide(EnumSide side, HiddenSimulationObject caller) {
		if (checkCaller(caller))
			if (checkAccessToPropertyGranted(caller, GrantedAccessToProperty.side)) {
				itemState.setSide(side, this);
				return WRITE_ACCESS_RETURNS_SUCCESS;
			}
			else
				return WRITE_ACCESS_RETURNS_NO_GRANT_FOR_PROPERTY;
		else
			return WRITE_ACCESS_RETURNS_INVALID_CALLER;
	}
	
}
