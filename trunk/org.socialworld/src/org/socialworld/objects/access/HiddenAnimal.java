/*
* Social World
* Copyright (C) 2015  Mathias Sikos
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
package org.socialworld.objects.access;

import org.socialworld.actions.move.Path;
import org.socialworld.attributes.AttributeArray;
import org.socialworld.calculation.FunctionByMatrix;
import org.socialworld.calculation.Vector;
import org.socialworld.objects.WriteAccessToAnimal;

/**
 * @author Mathias Sikos
 *
 */
public class HiddenAnimal extends HiddenSimulationObject {
	
	private WriteAccessToAnimal wa;

	public HiddenAnimal(WriteAccessToAnimal wa, GrantedAccessToProperty properties[]) {
		super(wa, properties);
		this.wa = wa;
	}

	public int setAttributes(AttributeArray attributes) {
		return wa.setAttributes(attributes, this);
	}
	
	public int setMatrix(FunctionByMatrix matrix) {
		return wa.setMatrix(matrix, this);
	}

	public int addPath(Path path) {
		return wa.addPath(path, this);
	}

	public int setDirectionChest(Vector directionChest) {
		return wa.setDirectionChest(directionChest, this);
	}

	public int setDirectionView(Vector directionView) {
		return wa.setDirectionView(directionView, this);
	}

	public int setDirectionActiveMove(Vector directionMove) {
		return wa.setDirectionActiveMove(directionMove, this);
	}

}
