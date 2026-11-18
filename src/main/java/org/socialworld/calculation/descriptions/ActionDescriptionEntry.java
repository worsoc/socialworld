/*
 * Social World
 * Copyright (C) 2026  Mathias Sikos
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

/**
 * One rule of a description that creates an action: the condition and the properties of the action,
 * each as a function tag such as &lt;Const&gt;40&lt;/Const&gt;.
 * CreateActionExpression turns a list of entries into one chain: the first entry whose condition holds
 * gives the action.
 *
 * Gson fills the fields of this class as well, as long as the JSON holder declares the list with the
 * concrete subclass (EventReactionDescriptionEntry, State2ActionDescriptionEntry). A subclass must not
 * declare a field of the same name again.
 */
public abstract class ActionDescriptionEntry {

	public String conditions;

	public String actionType;
	public String actionMode;
	public String minTime;
	public String maxTime;
	public String priority;
	public String intensity;
	public String duration;
	public String direction;
	public String target;
	public String item;
	public String inventoryPlace;
	public String endPosition;

}
