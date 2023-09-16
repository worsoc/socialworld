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
package org.socialworld.objects.concrete.clothes;

import org.socialworld.objects.concrete.HumanCrafted;
import org.socialworld.objects.enums.EnumHumanCrafted;
import org.socialworld.objects.properties.IWearable;

public abstract class Shirt extends HumanCrafted implements IWearable {

	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    creating instance for simulation    //////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public Shirt() {
		super();
		belongsTo = EnumHumanCrafted.Shirt;
	}
	


	public boolean isLeftHandGlove() {return false; }
	public boolean isRightHandGlove() {return false; }

	public boolean isLeftFootSock() {return false; }
	public boolean isRightFootSock() {return false; }

	public boolean isLeftFootShoe() {return false; }
	public boolean isRightFootShoe() {return false; }

	public boolean isShirt() {return true; }
	public boolean isTrousers() {return false; }
	public boolean isCap() {return false; }


}
