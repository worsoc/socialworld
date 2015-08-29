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

	public void setAttributes(AttributeArray attributes) {
		wa.setAttributes(attributes, this);
	}
	
	public void setMatrix(FunctionByMatrix matrix) {
		wa.setMatrix(matrix, this);
	}

	public void addPath(Path path) {
		wa.addPath(path, this);
	}

	public void setDirectionChest(Vector directionChest) {
		wa.setDirectionChest(directionChest, this);
	}

	public void setDirectionView(Vector directionView) {
		wa.setDirectionView(directionView, this);
	}

	public void setDirectionMove(Vector directionMove) {
		wa.setDirectionMove(directionMove, this);
	}

}
