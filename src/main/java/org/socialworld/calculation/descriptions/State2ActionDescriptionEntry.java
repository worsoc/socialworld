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
 * One rule of a state-to-action description: when the condition on the actor's attributes holds,
 * the actor gets this action. The fields are in ActionDescriptionEntry.
 *
 * There is no event behind such an action (ActionCreator.createAnimalActionByState passes only
 * the object ID and the attribute array), so &lt;GetEvParm&gt; must not be used here.
 */
public class State2ActionDescriptionEntry extends ActionDescriptionEntry {

}
