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
package org.socialworld.propertyChange;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * This is a wrapper class for the property change support.
 * <p>
 * All classes where data changes should propagate to other interested objects
 * should extend this class.
 * </p>
 * 
 * @author André Schade (circlesmiler)
 */
public abstract class ListenedBase implements IListened {
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
			this);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.socialworld.IModel#addPropertyChangeListener(java.lang.String,
	 *      java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.socialworld.IModel#removePropertyChangeListener(java.lang.String,
	 *      java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(propertyName,
				listener);
	}

	/**
	 * Report a bound property update to any registered listeners. No event is
	 * fired if old and new are equal and non-null.
	 * 
	 * <p>
	 * This is merely a convenience wrapper around the more general
	 * firePropertyChange method that takes {@code PropertyChangeEvent} value.
	 * 
	 * @param propertyName
	 *            The programmatic name of the property that was changed.
	 * @param oldValue
	 *            The old value of the property.
	 * @param newValue
	 *            The new value of the property.
	 */
	public void firePropertyChange(String propertyName, Object oldValue,
			Object newValue) {
		propertyChangeSupport.firePropertyChange(propertyName, oldValue,
				newValue);
	}


}
