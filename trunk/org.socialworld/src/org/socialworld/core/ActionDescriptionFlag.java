/**
 * 
 */
package org.socialworld.core;

/**
 * The enumeration collects flags for describing an action object. The
 * description is needed for searching action objects in the action list. The
 * flag shows whether an action attribute is search criteria or not.
 * 
 * @author Mathias Sikos (tyloesand)
 * 
 */
public enum ActionDescriptionFlag {
	searchByType, searchByMode, searchByMinTime, searchByMaxTime, searchByPriority, searchByTarget, searchByDirection, searchByIntensity, searchByDuration, searchByRemainedDuration,

	dontSearchByType, dontSearchByMode, dontSearchByMinTime, dontSearchByMaxTime, dontSearchByPriority, dontSearchByTarget, dontSearchByDirection, dontSearchByIntensity, dontSearchByDuration, dontSearchByRemainedDuration

}
