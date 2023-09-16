/*
* Social World
* Copyright (C) 2022  Mathias Sikos
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.  
*
* or see http://www.gnu.org/licenses/gpl-2.0.html
*
*/
package org.socialworld.objects.concrete.animals;

import org.socialworld.objects.Animal;
import org.socialworld.objects.enums.EnumBaseSimObj;
import org.socialworld.objects.enums.EnumBird;
import org.socialworld.objects.statics.Mapping_Bird2LexemIDLowerPart;

public abstract class Bird extends Animal {

	protected EnumBird belongsTo;
	
	public Bird() {
		super();
		setBaseSimObjEnum(EnumBaseSimObj.Bird);
	}



	@Override
	protected final int getLexemIdLowPart() {
		return Mapping_Bird2LexemIDLowerPart.getInstance().get(belongsTo);
	}
}
