/*
* Social World
* Copyright (C) 2019  Mathias Sikos
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
package org.socialworld.objects.properties;

public interface IWearable {

	public static final String NAME = "IWearable";

	
	public abstract boolean isLeftHandGlove();
	public abstract boolean isRightHandGlove();

	public abstract boolean isLeftFootSock();
	public abstract boolean isRightFootSock();

	public abstract boolean isLeftFootShoe();
	public abstract boolean isRightFootShoe();

	public abstract boolean isShirt();
	public abstract boolean isTrousers();
	public abstract boolean isCap();

}
