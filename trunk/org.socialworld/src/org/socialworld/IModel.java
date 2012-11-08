/**
 * 
 */
package org.socialworld;

import java.beans.PropertyChangeListener;

/**
 * </p>
 * All classes where data changes should propagate to other interested objects
 * should implement this interface.
 * </p>
 * 
 * @author André Schade (circlesmiler)
 */
public interface IModel {
	
	//  (circlesmiler) Falls jemandem ein besserer Name für die Klasse
	// einfällt, dann bitte einfach den Namen anpassen.

    /**
     * Add a PropertyChangeListener for a specific property.  The listener
     * will be invoked only when a call on firePropertyChange names that
     * specific property.
     * The same listener object may be added more than once.  For each
     * property,  the listener will be invoked the number of times it was added
     * for that property.
     * If <code>propertyName</code> or <code>listener</code> is null, no
     * exception is thrown and no action is taken.
     *
     * @param propertyName  The name of the property to listen on.
     * @param listener  The PropertyChangeListener to be added
     */
	public abstract void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener);
	
	/**
     * Remove a PropertyChangeListener for a specific property.
     * If <code>listener</code> was added more than once to the same event
     * source for the specified property, it will be notified one less time
     * after being removed.
     * If <code>propertyName</code> is null,  no exception is thrown and no
     * action is taken.
     * If <code>listener</code> is null, or was never added for the specified
     * property, no exception is thrown and no action is taken.
     *
     * @param propertyName  The name of the property that was listened on.
     * @param listener  The PropertyChangeListener to be removed
     */
	public abstract void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener);

}