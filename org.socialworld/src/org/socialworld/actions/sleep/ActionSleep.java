/*
* Social World
* Copyright (C) 2015  Mathias Sikos
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
package org.socialworld.actions.sleep;

import org.socialworld.actions.AbstractAction;
import org.socialworld.actions.ActionMode;
import org.socialworld.actions.ActionProperty;
import org.socialworld.actions.ActionType;
import org.socialworld.attributes.ActualTime;
import org.socialworld.attributes.Time;
import org.socialworld.core.EventByAction;
import org.socialworld.core.EventType;

/**
 * @author Mathias Sikos
 *
 */
public class ActionSleep extends AbstractAction {

	Sleep sleep;
	
	public ActionSleep(final ActionType type, final ActionMode mode,
			final float intensity, final Time minTime, final Time maxTime,
			final int priority, final long duration) {
		setBaseProperties(type,  mode,
				intensity,  minTime, maxTime,
				 priority,  duration);
			
	}

	/* (non-Javadoc)
	 * @see org.socialworld.actions.AbstractAction#perform()
	 */
	@Override
	public void perform() {
		
		EventByAction event;

		sleep = new Sleep(this);
		
		event = new EventByAction(EventType.sleep,    actor /* as causer*/,  ActualTime.asTime(),
					actor.getPosition(),  sleep /* as performer */);
		addEvent(event);
	}

	/* (non-Javadoc)
	 * @see org.socialworld.actions.AbstractAction#getConcreteProperty(org.socialworld.actions.ActionProperty)
	 */
	@Override
	public Object getConcreteProperty(ActionProperty prop) {
		// TODO Auto-generated method stub
		return null;
	}

}
