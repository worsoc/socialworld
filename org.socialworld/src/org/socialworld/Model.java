/**
 * 
 */
package org.socialworld;

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
public class Model implements IModel {

	// FIXME (circlesmiler) Falls jemandem ein besserer Name für die Klasse
	// einfällt, dann bitte einfach den Namen anpassen.

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