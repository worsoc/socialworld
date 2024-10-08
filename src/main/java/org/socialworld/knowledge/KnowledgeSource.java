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

import org.socialworld.calculation.IObjectReceiver;
import org.socialworld.calculation.IObjectSender;
import org.socialworld.core.IAccessToken;
import org.socialworld.objects.SimulationObject;

public class KnowledgeSource implements IObjectSender {
	
	private static KnowledgeSource objectNothing;
	
	public static KnowledgeSource getObjectNothing() {
		if (objectNothing == null) {
			objectNothing = new KnowledgeSource();
		}
		return objectNothing;
	}

	private KnowledgeSource() {
		
	}
	
	KnowledgeSource_Type type = null;
	SimulationObject origin = null;
	
	public KnowledgeSource(KnowledgeSource_Type type, SimulationObject  origin) {
		this.type = type;
		this.origin = origin;
	}
	
	public KnowledgeSource(KnowledgeSource original) {
		this.type = original.type;
		this.origin = original.origin;
	}
	
	public void setSourceType(KnowledgeSource_Type type) {
		this.type = type;
	}
	
	public void setOrigin(SimulationObject origin) {
		this.origin = origin;
	}
	
	public boolean equals(KnowledgeSource b) {
		return (this.type == b.type & this.origin.equals(b.origin));
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
	
	public String toString() {
		String output;
		if (type == null || origin == null) {
			output = "()";
		}
		else {
			output = "(" + type.toString() + ", OID: " + origin.toString() + ")";
		}
		return output;
	}
}
