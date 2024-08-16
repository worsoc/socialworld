package org.socialworld.calculation.descriptions;

import org.socialworld.actions.ActionMode;
import org.socialworld.actions.ActionType;
import org.socialworld.actions.handle.InventoryPlace;
import org.socialworld.datasource.parsing.JsonEventReactionDescriptionEntry;

public class EventReactionDescriptionEntry {
	
	
	public String conditions;
	
	public ActionType actionType;
	public ActionMode actionMode;
	public String minTime;
	public String maxTime;
	public String priority;
	public String intensity;
	public String duration;
	public String target;
	public String item;
	public InventoryPlace inventoryPlace;
	
	public EventReactionDescriptionEntry(JsonEventReactionDescriptionEntry json) {
		
		this.conditions = json.conditions;
		
		this.actionType = ActionType.fromName(json.actionType);
		this.actionMode = ActionMode.fromName(json.actionMode);
		this.minTime = json.minTime;
		this.maxTime = json.maxTime;
		this.priority = json.priority;
		this.intensity = json.intensity;
		this.duration = json.duration;
		this.target = json.target;
		this.item = json.item;
		this.inventoryPlace = InventoryPlace.fromName(json.inventoryPlace);
	}
}

