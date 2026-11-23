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
package org.socialworld.objects.concrete.clothes;

import org.socialworld.objects.concrete.HumanCrafted;
import org.socialworld.objects.enums.EnumHumanCrafted;
import org.socialworld.objects.properties.IWearable;

/**
 * A human crafted thing made to be worn: the group of caps, shirts, trousers, dresses, overgarments,
 * shoes, socks and gloves.
 */
public abstract class Clothes extends HumanCrafted implements IWearable {

	public Clothes() {
		super();
		belongsTo = EnumHumanCrafted.Clothes;
	}

}
