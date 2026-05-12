/*
 * Social World
 * Copyright (C) 2014  Mathias Sikos
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://gnu.org>.
 */
package org.socialworld.calculation.descriptions;

import java.util.List;
import java.util.ArrayList;

import org.socialworld.calculation.Expression;
import org.socialworld.calculation.expressions.ChangeAttributes;
import org.socialworld.calculation.functions.FunctionByExpression;
import org.socialworld.core.EventType;
import org.socialworld.datasource.parsing.JsonEventInfluenceDescription;
import org.socialworld.datasource.parsing.JsonEventInfluencesAttributeDescription;


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
	
	public EventInfluenceDescription(String json) {
		super();
		
		loadFromJson(json);
	}

	public EventInfluenceDescription(JsonEventInfluenceDescription jeid) {
		super();
		create(jeid);
		
	}


	@Override
	public void setFunctions() {
		Expression startExpression = ChangeAttributes.fromJsonEntries(this.entrysEIA);
		addFunction(new FunctionByExpression(startExpression));
	
	}

	public int getNrEventType() {
		return eventType.getIndex();
	}

	public int getNrInfluenceType() {
		return influenceType;
	}

	private void loadFromJson(String json) {
		
		JsonEventInfluenceDescription jsonObject;
		jsonObject = getGsonInstance().fromJson(json, JsonEventInfluenceDescription.class);

		create(jsonObject);
	}

	private void create(JsonEventInfluenceDescription jeid) {
		this.eventType = EventType.fromName(jeid.eventType);
		this.influenceType = jeid.influenceType;
		
		List<JsonEventInfluencesAttributeDescription> listEIA = jeid.attributeChanges;
		this.entrysEIA = new ArrayList<EventInfluencesAttributeEntry>();
		for (JsonEventInfluencesAttributeDescription jeiad : listEIA) {
			entrysEIA.add(new EventInfluencesAttributeEntry(jeiad));
		}
	}
	
	public String toString() {
		return "IT:" + influenceType + ",ET:" + eventType.toString() +  ",entrysEIA:" + entrysEIA.toString();
	}
}
