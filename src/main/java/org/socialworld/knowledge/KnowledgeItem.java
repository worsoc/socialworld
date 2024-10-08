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

import org.socialworld.calculation.IObjectReceiver;
import org.socialworld.calculation.IObjectSender;
import org.socialworld.calculation.ObjectRequester;
import org.socialworld.conversation.Lexem;
import org.socialworld.core.IAccessToken;

public abstract class KnowledgeItem implements IObjectSender, IObjectReceiver{

	protected ObjectRequester objectRequester = new ObjectRequester();

	abstract KnowledgeItem getCopy();
	abstract KnowledgeItemNotes removeNotes();
	protected abstract boolean isEqual(KnowledgeItem item);
	
	// TODO implement equals()

	protected boolean checkWhetherTwoLexemsAreEqual(Lexem a, Lexem b) {
		
		if (a == null && b == null) return true;
		if (a == null && b != null) return false;
		if (a != null && b == null) return false;
		
		return a.equals(b);
		
		
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
//////////////////////implementing IObjectSender ///////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public int sendYourselfTo(IObjectReceiver receiver, int requestID) {
		return receiver.receiveObject(requestID, this);
	}
	
	public int sendYourselfTo(IAccessToken token, IObjectReceiver receiver, int requestID) {
		return receiver.receiveObject(requestID, this);
	}
	
	public IObjectSender copy() {
		return this;
	}
	
	
///////////////////////////////////////////////////////////////////////////////////////////
//////////////////////implementing IObjectReceiver ///////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public int receiveObject(int requestID, Object object) {
		objectRequester.receive(requestID, object);
		return 0;
	}
	
}
