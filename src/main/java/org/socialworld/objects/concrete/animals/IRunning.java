/*
* Social World
* Copyright (C) 2021 Merle Wolf
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

import org.socialworld.attributes.Direction;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.core.IAccessToken;

public interface IRunning {
	
	public static final String NAME = "IRunning";
	
	public abstract float getSpeed();
	public abstract float getNumberLegs();
	public Direction getDirectionRun();
	
	public StateRunning getSavedStateRunning(IAccessToken token);
	public ValueProperty getStateRunningAsProperty(IAccessToken token, String name);
}
