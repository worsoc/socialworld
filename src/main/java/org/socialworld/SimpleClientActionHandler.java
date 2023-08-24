/*
* Social World
* Copyright (C) 2014  Mathias Sikos
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
package org.socialworld;

import org.socialworld.objects.access.HiddenHuman;

import org.socialworld.actions.move.ActionMove;
import org.socialworld.collections.ValueArrayList;

public class SimpleClientActionHandler  {

	private static SimpleClientActionHandler instance;
	
	private HiddenHuman human1;
	private HiddenHuman human2;
	private HiddenHuman human3;
	private HiddenHuman actualHuman;

	private SimpleClientActionHandler() {}
	
	public static SimpleClientActionHandler  getInstance() {
		if (instance == null)	instance = new SimpleClientActionHandler();
		return instance;
	}
	
	public void setHumanWrite(int nr, final HiddenHuman human) {
		
		switch (nr) {
		case 1:
			human1 = human;
			break;
		case 2:
			human2 = human;
			break;
		case 3:
			human3 = human;
			break;
		}
	}
	
	
	public void chooseHuman( int nr) {
		switch (nr) {
		case 1:
			actualHuman = human1;
			break;
		case 2:
			actualHuman = human2;
			break;
		case 3:
			actualHuman = human3;
			break;
		}
		
	}
	
	public void doAction (int action) {
		
		ValueArrayList properties = new ValueArrayList();

		switch (action) {
		case 1:   // step forward
			
			actualHuman.setAction(new ActionMove(properties) );
			break;
		case 2:   // step back
			
			actualHuman.setAction(new ActionMove(properties) );
			break;
		}
		
	}
}
