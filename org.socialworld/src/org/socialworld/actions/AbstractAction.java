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
package org.socialworld.actions;

import org.socialworld.attributes.Time;
import org.socialworld.core.Event;
import org.socialworld.core.Simulation;
import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.access.HiddenSimulationObject;

/**
 * @author Mathias Sikos (tyloesand)
 * 
 * It's the base class for all simulation actions (actions done by simulation
 * objects). It collects all base action attributes. That are action type,
 * action mode, priority, earliest execution time, latest execution time,
 * duration, remained duration, intensity.
 * 
 * 
 * German:
 * AbstractAction ist die Basisklasse (abstrakte Klasse) für Aktionen der Simlationsobjekte.
 * Die Aktionsobjekte von abgleiteten Klassen werden im ActionMaster verwaltet 
 * und vom ActionHandler zur Ausführung gebracht.
 * 
 * Die Ausführung besteht dabei aus 2 Schritten
 * a) Einleitung der Ausführung
 * b) Wirksamwerden der Aktion
 * 
 * a) Einleitung der Ausführung:
 * Der ActionMaster führt die ActionHandler aller Simulationsobjekte
 *  und weist den jeweiligen ActionHandler an, mit der Ausführung einer Aktion zu beginnen bzw. eine Aktion fortzusetzen.
 * Der ActionHandler sorgt dafür, 
 *  dass für das auszuführende Aktionsobjekt (Ableitung von AbstractAction) die Methode perform() aufgerufen wird.
 * Die Methode perform() ist abstract und muss in allen Ableitungen implementiert werden/sein.
 * Die Methode perform() führt Vorabprüfungen der Daten zur Aktion durch, 
 *  erzeugt das zugehörige Performer-Objekt (siehe Schritt b),
 *  erzeugt die auszulösenden Ereignisse, 
 *  fügt den Ereignissen das Performerobjekt als Ereigniseigenschaft hinzu,
 *  und trägt diese in die Ereignisverwaltung (EventMaster) ein (siehe Schritt b).
 *  
 * b)  Wirksamwerden der Aktion
 * Es gilt der Grundsatz, dass alle Aktionen durch ihre Ereignisverarbeitung wirksam werden.
 * Im Schritt a) wurden Ereignisse zu den Aktionen in die Ereignisverwaltung eingetragen.
 * Die Ereignisverwaltung arbeitet die Ereignisse nach ihren Regeln ab.
 * Für jedes Event der Klasse EventByAction, also von Aktionen ausgelöste Ereignisse, 
 *  wird die evaluate-Methode des dem Ereignis zugeordenten Performers (Ableitung der Klasse ActionPerformer) aufgerufen.
 * Diese wiederum ruft die Methode perform im Performerobjekt auf.
 * Diese Methode ermittelt die für die Ereignisverarbeitung benötigten Werte 
 * 	aus dem Aktionsobjekt, dem ausführenden Objekt (also dem Akteur) und ggf. dem Zielobjekt. 
 * Diese Werte werden standardisiert in einer Liste abgelegt 
 *  und können vom Ereignis über Standardmethoden ausgelesen werden.
 * Schließlich werden für die Ereignisse ihre Wirkung auf die Simulationsobjekte und ggf. Reaktionen ermittelt.
 *  
 * Die Klasse AbstractAction ist die Basisklasse für die Aktionsobjekte des Schrittes a), 
 *  enthält also die Daten für die Einleitung der Ausführung (Erzeugung von Performer und Event).
 *  
 */
public abstract class AbstractAction {
	public static final int MAX_ACTION_PRIORITY = 256;
	
	// must be in byte range
	// because there is used a byte variable for index
	public static final int MAX_ACTION_WAIT_SECONDS = 60;
	
	protected SimulationObject actor;
	private HiddenSimulationObject hiddenWriteAccesToActor;
	
	protected ActionType type;
	protected ActionMode mode;
	protected Time minTime;
	protected Time maxTime;
	protected int priority;
	protected float intensity;
	protected long duration;
	protected long remainedDuration;

	protected boolean interruptable = false;
	protected boolean done = false;
	
	private Simulation simulation = Simulation.getInstance();

	protected AbstractAction linkedAction;

	protected AbstractAction() {
		
	}
	
	protected AbstractAction(ActionType type, final ActionMode mode,
			final float intensity, final Time minTime, final Time maxTime,
			final int priority, final long duration) {
		setBaseProperties(type,  mode,
				intensity,  minTime, maxTime,
				 priority,  duration);
	}
	
	public void setActor(SimulationObject actor, HiddenSimulationObject hiddenWriteAccess) {
		this.hiddenWriteAccesToActor = hiddenWriteAccess;
		this.actor = actor;
	}

	public final void removeWriteAccess() {
		this.hiddenWriteAccesToActor = null;
	}

	protected  final HiddenSimulationObject getHiddenWriteAccessToActor(AbstractAction concreteAction) {
		if (this == concreteAction )
			return hiddenWriteAccesToActor;
		else
			// dummy
			return new HiddenSimulationObject();
	}

	public boolean isToBeIgnored() {
		return (type == ActionType.ignore);
	}
	
	protected void setBaseProperties(final ActionType type, final ActionMode mode,
			final float intensity, final Time minTime, final Time maxTime,
			final int priority, final long duration) {
		
		this.setType(type);
		this.setMode(mode);
		this.setIntensity(intensity);
		this.setMinTime(minTime);
		this.setMaxTime(maxTime);
		this.setPriority(priority);
		this.setDuration(duration);
		this.setRemainedDuration(duration);

		this.linkedAction = null;
		
		if (duration > 0) interruptable = true;
		
	}

	protected void setBaseProperties(AbstractAction original) {
		this.type = original.type;
		this.mode = original.mode;
		this.intensity = original.intensity;
		this.minTime = original.minTime;
		this.maxTime = original.maxTime;
		this.priority = original.priority;
		this.duration = original.duration;
		
	}

	
	
	public abstract void perform();
	
	
	protected void addEvent(Event event) {
		simulation.getEventMaster().addEvent(event);
	}
	
	/**
	 * The method sets the linked action.
	 * 
	 * @param linked action
	 */
	public void setLinkedAction (AbstractAction linkedAction) {
		this.linkedAction = linkedAction;
	}
	
	/**
	 * The method returns the linked action.
	 * It is null if there is no linked action.
	 * 
	 * @return linked action
	 */
	public AbstractAction getLinkedAction() {
		return linkedAction;
	}

	public SimulationObject getActor() {
		return actor;
	}
	
	public void setDone() {
		done = true;
	}
	
	public boolean isDone() {
		return done;
	}
	
	public boolean isInterruptable() {
		return interruptable;
	}
	
	/**
	 * The methods returns the action type.
	 * 
	 * @return type
	 */
	public ActionType getType() {
		return this.type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(final ActionType type) {
		this.type = type;
	}

	/**
	 * @return the time
	 */
	public Time getMinTime() {
		return this.minTime;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setMinTime(final Time time) {
		this.minTime = time;
	}

	/**
	 * @return the time
	 */
	public Time getMaxTime() {
		return this.maxTime;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setMaxTime(final Time time) {
		this.maxTime = time;
	}

	/**
	 * The methods returns the action's priority.
	 * 
	 * @return priority
	 */
	public int getPriority() {
		return this.priority;
	}

	/**
	 * @param priority
	 *            the priority to set
	 */
	public void setPriority(final int priority) {
		this.priority = priority;
	}



	/**
	 * @return the mode
	 */
	public ActionMode getMode() {
		return this.mode;
	}

	/**
	 * @param mode
	 *            the mode to set
	 */
	public void setMode(final ActionMode mode) {
		this.mode = mode;
	}

	/**
	 * @return the intensity
	 */
	public float getIntensity() {
		return this.intensity;
	}

	/**
	 * @param intensity
	 *            the intensity to set
	 */
	public void setIntensity(final float intensity) {
		this.intensity = intensity;
	}

	/**
	 * @return the duration
	 */
	public long getDuration() {
		return this.duration;
	}

	/**
	 * @param duration
	 *            the duration to set
	 */
	public void setDuration(final long duration) {
		this.duration = duration;
	}

	/**
	 * The methods returns the action's remained duration.
	 * 
	 * @return remainedDuration
	 */
	public long getRemainedDuration() {
		return this.remainedDuration;
	}

	/**
	 * @param remainedDuration
	 *            the remainedDuration to set
	 */
	public void setRemainedDuration(final long remainedDuration) {
		this.remainedDuration = remainedDuration;
	}

	/**
	 * The method decrements the attribute remainedDuration. That means, an
	 * action needs less time to complete.
	 * 
	 * @param decrement
	 */
	public void lowerRemainedDuration(final long decrement) {
		this.remainedDuration -= decrement;
		
		System.out.println("AbstractAction.lowerRemainedDuration(): " + toString() + "  " + actor.toString() + " noch offen: " + this.remainedDuration);
		if (this.remainedDuration <= 0) done = true;
	}

	/**
	 * The method increments the attribute remainedDuration. That means, an
	 * action needs more time to complete.
	 * 
	 * @param increment
	 */
	public void raiseRemainedDuration(final long increment) {
		this.remainedDuration += increment;
	}

	
	
	@Override
	public String toString() {
		return  "(" + this.type.toString() + ")"; //$NON-NLS-1$
	}
	


}