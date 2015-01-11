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
package org.socialworld.objects;

import org.socialworld.attributes.Inventory;
//import org.socialworld.conversation.Talk_SentenceType;

public class WriteAccessToHuman extends WriteAccessToAnimal {

	private  Human human;
	
	public WriteAccessToHuman(Human human) {
		super(human);
		this.human = human;
	}
	
	public void setInventory(Inventory inventory, Object caller) {
		if (checkCaller(caller)) human.setInventory(inventory, this);
	}

	/*
	public void addSentence(Human partner, Talk_SentenceType type, String sentence, Object caller) {
		if (checkCaller(caller)) human.addSentence(partner, type, sentence, this);
	}
	*/
}
