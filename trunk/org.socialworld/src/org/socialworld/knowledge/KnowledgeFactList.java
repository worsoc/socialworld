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
package org.socialworld.knowledge;

import java.util.ArrayList;
import org.socialworld.conversation.Word;

public class KnowledgeFactList  {
	
	private ArrayList<KnowledgeFact> factSearchList;
	
	public KnowledgeFactList() {
		factSearchList = new ArrayList<KnowledgeFact>();
	}
	
	public KnowledgeFact find(Word  value) {
		KnowledgeFact fact;
		KnowledgeFact temp;
		
		int index = 0;
		int count;
		
		boolean found;
		
		count = factSearchList.size();
		
		// finding the element with the specified value wordID
		do
		{
			fact = factSearchList.get(index);
			index++;
			found =  ( fact.getValue().getWord() == value );
		}
		while( found  | (index == count) );
		
		index--;
		
		// exchange the element for the previous one
		if ( found && index > 0)  {
			
			// the previous list element
			temp = factSearchList.get(index - 1);
			
			factSearchList.set(index - 1, fact);
			factSearchList.set(index , temp);
			
		}
		
		if (!found) fact = null;
		
		return fact;
	}
	
	public void add (KnowledgeFact fact) {
		factSearchList.add(fact);
	}
}
