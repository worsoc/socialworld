/*
* Social World
* Copyright (C) 2024  Mathias Sikos
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
package org.socialworld;

public class GlobalSwitches {

	// System.identityHashCode(this)
	
	
	
	public static boolean STATE_ANIMAL_CALCULATE_REFRESH_ATTRIBUTES = true;
	public static boolean STATE_ANIMAL_CALCULATE_REFRESH_ACTION = true;
	public static boolean STATE_ANIMAL_CALCULATE_EVENTINFLUENCE_ATTRIBUTES = true;
	public static boolean STATE_ANIMAL_CALCULATE_EVENTINFLUENCE_PERCEPTION = false;

	public static boolean OUTPUT_MEMORY_TELEMETRY = true;
	
	public static boolean OUTPUT_KNOWLEDGE_ADDKNOWLEDGE = false;

	public static boolean OUTPUT_DEBUG_VARIABLE_IS_NULL = false;
	public static boolean OUTPUT_DEBUG_ACTIONCREATOR_VARIABLE_IS_NULL = false;
	public static boolean OUTPUT_DEBUG_ATTRIBUTECALCULATOR_VARIABLE_IS_NULL = true;
	public static boolean OUTPUT_DEBUG_POSITIONCALCULATOR_VARIABLE_IS_NULL = false;
	public static boolean OUTPUT_DEBUG_TALKCALCULATOR_VARIABLE_IS_NULL = false;
	public static boolean OUTPUT_DEBUG_KNOWLEDGECALCULATOR_VARIABLE_IS_NULL = false;

	public static boolean OUTPUT_DEBUG_GETOBJECT = false;
	public static boolean OUTPUT_DEBUG_CHECKTYPE = true;
	public static boolean OUTPUT_DEBUG_GETPROPERTY = false;
	
	public static boolean OUTPUT_EVENTMASTER_DETERMINE_CANDIDATES = false;
	public static boolean OUTPUT_EVENTMASTER_DETERMINE_INFLUENCE_TO_CANDIDATES = false;
	public static boolean OUTPUT_EVENTMASTER_DETERMINE_INFLUENCE_TO_TARGETS = false;
	public static boolean OUTPUT_EVENTMASTER_DETERMINE_INFLUENCE_TO_PERCIPIENTS = false;

	public static boolean OUTPUT_CREATE_OBJECT = false;
	public static boolean OUTPUT_CREATE_OBJECT_HUMANS = false;
	public static boolean OUTPUT_CREATE_OBJECT_DETAILS = false;
	public static boolean OUTPUT_CREATE_OBJECT_CLASS_FOR_NAME = false;

	public static boolean OUTPUT_CALCULATE_ACTION = false;
	public static boolean OUTPUT_CALCULATE_REACTION = false;

	public static boolean OUTPUT_CALCULATE_ATTRIBUTE_BY_EVENT = false;

	public static boolean OUTPUT_CAPACITY_QUEUE_IS_FULL = false;

}
