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
package org.socialworld.core;

import org.socialworld.actions.AbstractAction;
import org.socialworld.actions.ActionMode;
import org.socialworld.actions.ActionType;
import org.socialworld.attributes.Time;

/**
 * It's a description of an action template that is used for searching action
 * elements in action lists. Every object that meets the described search
 * criteria may be found. Every action property has an flag whether the property
 * is a search criteria.
 * 
 * @author Mathias Sikos (tyloesand)
 * 
 */
public class SearchActionDescription extends AbstractAction {

	private boolean searchByType;
	private boolean searchByMode;
	private boolean searchByMinTime;
	private boolean searchByMaxTime;
	private boolean searchByPriority;
	private boolean searchByIntensity;
	private boolean searchByDuration;
	private boolean searchByRemainedDuration;

	public SearchActionDescription() {
		super();
		this.searchByType = false;
		this.searchByMode = false;
		this.searchByMinTime = false;
		this.searchByMaxTime = false;
		this.searchByPriority = false;
		this.searchByIntensity = false;
		this.searchByDuration = false;
		this.searchByRemainedDuration = false;
	}

	public SearchActionDescription(AbstractAction search) {
		super();
		setType(search.getType());
		setMode(search.getMode());
		setPriority(search.getPriority());
		setIntensity(search.getIntensity());
		setDuration(search.getDuration());
	}
	
	public  void perform() {
		return;
	}


	/**
	 * @param type
	 *            the type to set
	 */
	@Override
	public void setType(final ActionType type) {
		this.type = type;
		this.searchByType = true;
	}

	/**
	 * @param mode
	 *            the mode to set
	 */
	@Override
	public void setMode(final ActionMode mode) {
		this.mode = mode;
		this.searchByMode = true;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	@Override
	public void setMinTime(final Time time) {
		this.minTime = time;
		this.searchByMinTime = true;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	@Override
	public void setMaxTime(final Time time) {
		this.maxTime = time;
		this.searchByMaxTime = true;
	}

	/**
	 * @param priority
	 *            the priority to set
	 */
	@Override
	public void setPriority(final int priority) {
		this.priority = priority;
		this.searchByPriority = true;
	}



	/**
	 * @param intensity
	 *            the intensity to set
	 */
	@Override
	public void setIntensity(final float intensity) {
		this.intensity = intensity;
		this.searchByIntensity = true;
	}

	/**
	 * @param duration
	 *            the duration to set
	 */
	@Override
	public void setDuration(final long duration) {
		this.duration = duration;
		this.searchByDuration = true;
	}

	/**
	 * @param remainedDuration
	 *            the remainedDuration to set
	 */
	@Override
	public void setRemainedDuration(final long remainedDuration) {
		this.remainedDuration = remainedDuration;
		this.searchByRemainedDuration = true;
	}

	/**
	 * The methods sets the flag, whether an action attribute is used by search
	 * 
	 * @param flag
	 */
	public void setSearchFlag(final ActionDescriptionFlag flag) {
		switch (flag) {
		case searchByType:
			this.searchByType = true;
			break;
		case searchByMode:
			this.searchByMode = true;
			break;
		case searchByMinTime:
			this.searchByMinTime = true;
			break;
		case searchByMaxTime:
			this.searchByMaxTime = true;
			break;
		case searchByPriority:
			this.searchByPriority = true;
			break;
		case searchByIntensity:
			this.searchByIntensity = true;
			break;
		case searchByDuration:
			this.searchByDuration = true;
			break;
		case searchByRemainedDuration:
			this.searchByRemainedDuration = true;
			break;

		case dontSearchByType:
			this.searchByType = false;
			break;
		case dontSearchByMode:
			this.searchByMode = false;
			break;
		case dontSearchByMinTime:
			this.searchByMinTime = false;
			break;
		case dontSearchByMaxTime:
			this.searchByMaxTime = false;
			break;
		case dontSearchByPriority:
			this.searchByPriority = false;
			break;
		case dontSearchByIntensity:
			this.searchByIntensity = false;
			break;
		case dontSearchByDuration:
			this.searchByDuration = false;
			break;
		case dontSearchByRemainedDuration:
			this.searchByRemainedDuration = false;
			break;
		default:
		}
	}

	/**
	 * @return the searchByType
	 */
	public boolean isSearchByType() {
		return this.searchByType;
	}

	/**
	 * @return the searchByMode
	 */
	public boolean isSearchByMode() {
		return this.searchByMode;
	}

	/**
	 * @return the searchByMinTime
	 */
	public boolean isSearchByMinTime() {
		return this.searchByMinTime;
	}

	/**
	 * @return the searchByMaxTime
	 */
	public boolean isSearchByMaxTime() {
		return this.searchByMaxTime;
	}

	/**
	 * @return the searchByPriority
	 */
	public boolean isSearchByPriority() {
		return this.searchByPriority;
	}


	/**
	 * @return the searchByIntensity
	 */
	public boolean isSearchByIntensity() {
		return this.searchByIntensity;
	}

	/**
	 * @return the searchByDuration
	 */
	public boolean isSearchByDuration() {
		return this.searchByDuration;
	}

	/**
	 * @return the searchByRemainedDuration
	 */
	public boolean isSearchByRemainedDuration() {
		return this.searchByRemainedDuration;
	}
}
