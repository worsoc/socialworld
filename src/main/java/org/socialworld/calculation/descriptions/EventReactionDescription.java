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

import org.socialworld.core.EventType;

import org.socialworld.calculation.FunctionByExpression;
import org.socialworld.calculation.Expression;
import org.socialworld.calculation.expressions.CreateActionExpression;

import org.socialworld.datasource.parsing.JsonEventReactionDescription;
import com.google.gson.Gson;

public class EventReactionDescription extends DescriptionBase {
	

	
	
	private EventType eventType;
	private int reactionType;
	
	private List<EventReactionDescriptionEntry> entrys;
	
	public EventReactionDescription() {
		super();
	}

	public EventReactionDescription(Gson gson, String json) {
		super();
		
		loadFromJson(gson, json);
	}

	private void loadFromJson(Gson gson, String json) {
		JsonEventReactionDescription jsonObject;
		jsonObject = gson.fromJson(json, JsonEventReactionDescription.class);
		
		this.eventType = EventType.fromName(jsonObject.eventType);
		this.reactionType = jsonObject.reactionType;
		this.entrys = jsonObject.entrys;

	}

	@Override
	public void setFunctions() {
		
		Expression startExpression = new CreateActionExpression(this.entrys, true /* dummy */);
		addFunction(new FunctionByExpression(startExpression));
		
	}
	

	
}
