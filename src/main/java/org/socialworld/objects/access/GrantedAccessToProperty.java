/*
 * Social World
 * Copyright (C) 2015  Mathias Sikos
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
package org.socialworld.objects.access;

/**
 * @author Mathias Sikos
 *
 */
public enum GrantedAccessToProperty {
	all, 
	
	// simulation object
	state, position,  directionMove, powerMove, velocity, action, influenceTypes, reactionTypes, perceptionTypes, state2ActionType,
	
	// animal
	attributes, matrix, path, knowledge, directionChest, directionView, directionActiveMove,
	
	// human
	inventory, sentence, factsFromSentence, answer, talks,  acquaintance, lastSaidSentence
	
	// item
	
	// magic
	
	// god
}
