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
package org.socialworld.actions;

import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.core.IEventParam;

/**
 * @author Mathias Sikos
 *
 */
public abstract class ActionPerformer implements IEventParam {

    private Value eventParams[];
    private int maxParams;
    private boolean valid = false;
    
    private AbstractAction action;
    
    public ActionPerformer (AbstractAction action) {
    	this.action = action;
    }
    
    public abstract void perform();
    
	/* (non-Javadoc)
	 * @see org.socialworld.core.IEventParam#evaluate()
	 */
	@Override
   public void evaluate() {
    	perform();
    }
    
	/* (non-Javadoc)
	 * @see org.socialworld.core.IEventParam#isValid()
	 */
	@Override
    public boolean isValid() {
		return valid;
	}
	
	/* (non-Javadoc)
	 * @see org.socialworld.core.IEventParam#getParam(int)
	 */
	@Override
	public Value getParam(int index) {
		if (index >= 0 &index < size())
			return eventParams[index];
		else
			// invalid "nothing" value
			return new Value();
	}

	/* (non-Javadoc)
	 * @see org.socialworld.core.IEventParam#size()
	 */
	@Override
	public int size() {
		return eventParams.length;
	}

	/* (non-Javadoc)
	 * @see org.socialworld.core.IEventParam#find(org.socialworld.calculation.Type)
	 */
	@Override
	public int find(Type type) {
		for (int index = 0; index < maxParams; index++) {
			if (eventParams[index].getType() == type) {
					return index;
			}
		}
		return -1;
	}

	/* (non-Javadoc)
	 * @see org.socialworld.core.IEventParam#find(String)
	 */
	@Override
	public int find(String name) {
		for (int index = 0; index < maxParams; index++) {
			if (eventParams[index].getName() == name) {
					return index;
			}
		}
		return -1;
	}

	protected void setMaxParam(int max) {
		this.maxParams = max;
		this.eventParams = new Value[max];
	}
	
	protected void setParam(int index, Value param) {
		if (index >= 0 & index < maxParams)
			eventParams[index] = param;
	}
	
	protected void setValid() {
		valid = true;
	}
	
	protected AbstractAction getOriginalActionObject() {
		return this.action;
	}

}
