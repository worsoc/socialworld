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
package org.socialworld.objects;

import org.socialworld.objects.access.GrantedAccessToProperty;
import org.socialworld.objects.access.HiddenItem;

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
	
}
