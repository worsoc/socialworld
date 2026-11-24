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
package org.socialworld.objects.access;

import org.socialworld.objects.WriteAccessToItem;
import org.socialworld.objects.enums.EnumSide;

/**
 * @author Mathias Sikos
 *
 */
public class HiddenItem extends HiddenSimulationObject {

	private WriteAccessToItem wa;

	public HiddenItem(WriteAccessToItem wa, GrantedAccessToProperty properties[]) {
		super(wa, properties);
		this.wa = wa;
	}

	public int setSide(EnumSide side) {
		return wa.setSide(side, this);
	}
	
}
