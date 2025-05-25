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
package org.socialworld.calculation.descriptions;

import java.util.List;
import java.util.ArrayList;

import org.socialworld.core.EventType;
import org.socialworld.datasource.parsing.JsonEventInfluenceDescription;
import org.socialworld.datasource.parsing.JsonEventInfluencesAttributeDescription;

import com.google.gson.Gson;

/**
 * The class holds all informations for
 *         describing how an event influences a simulation object.

 * @author Mathias Sikos (MatWorsoc)   
 */
public class EventInfluenceDescription extends DescriptionBase {

	
	private EventType eventType;
	private int influenceType;
	
	private List<EventInfluencesAttributeEntry> entrysEIA;

	public EventInfluenceDescription() {
		super();
	}
	public EventInfluenceDescription(Gson gson, String json) {
		super();
		
		loadFromJson(gson, json);
	}

	@Override
	public void setFunctions() {
		// TODO Auto-generated method stub
		
	}

	private void loadFromJson(Gson gson, String json) {
		
		JsonEventInfluenceDescription jsonObject;
		jsonObject = gson.fromJson(json, JsonEventInfluenceDescription.class);
		
		this.eventType = EventType.fromName(jsonObject.eventType);
		this.influenceType = jsonObject.influenceType;
		
		List<JsonEventInfluencesAttributeDescription> listEIA = jsonObject.attributeChanges;
		this.entrysEIA = new ArrayList<EventInfluencesAttributeEntry>();
		for (JsonEventInfluencesAttributeDescription jeiad : listEIA) {
			entrysEIA.add(new EventInfluencesAttributeEntry(jeiad));
		}

	}

	public String toString() {
		return "IT:" + influenceType + ",ET:" + eventType.toString() +  ",entrysEIA:" + entrysEIA.toString();
	}
}
