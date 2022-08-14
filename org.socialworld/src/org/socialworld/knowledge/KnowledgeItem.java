/*
* Social World
* Copyright (C) 2020  Mathias Sikos
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

import java.util.List;

public abstract class KnowledgeItem {

	private int itemAccessCount;
	private boolean itemIsValid;

	private KnowledgeSource source;
	private List<String> notes; 
	
	abstract KnowledgeItem copy();

	void setValid(boolean isValid) {
		this.itemIsValid = isValid;
	}
	
	void setSource(KnowledgeSource source) {
		this.source = source;
	}
	
	KnowledgeSource getSource() {
		return this.source;
	}
	
	void addNote(String note) {
		this.notes.add(note);
	}

	KnowledgeSource getSourceAsCopy() {
		return new KnowledgeSource(this.source);
	}

	boolean isItemValid() {
		return this.itemIsValid;
	}
	
	void resetAccessCount() {
		this.itemAccessCount = 0;
	}
	
	void incrementAccessCount() {
		this.itemAccessCount++;
	}
	
	int getItemAccessCount() {
		return this.itemAccessCount;
	}
	
	// TODO implement equals()

}
