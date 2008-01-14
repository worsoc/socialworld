/**
 * 
 */
package org.socialworld.core;

import java.util.PriorityQueue;

import org.apache.log4j.Logger;

/**
 * @author Mathias Sikos (tyloesand)
 * 
 */
public class EventMaster extends Thread {

	private static final Logger logger = Logger.getLogger(EventMaster.class);
	private static EventMaster eventMaster;
	private PriorityQueue<Event> eventQueue;

	/**
	 * Private Constructor. (Singleton)
	 */
	private EventMaster() {
		// TODO Auto-generated constructor stub
	}

	public static EventMaster getEventMaster() {
		if (eventMaster == null) {
			eventMaster = new EventMaster();
		}
		return eventMaster;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		int sleepTime = 10;
		while (true) {
			calculateNextEvent();
			if (eventQueue.isEmpty())
				sleepTime = 100;
			else
				sleepTime = 10;
			try {
				sleep(sleepTime);
			} catch (InterruptedException e) {
				logger.error(e.getMessage());
				logger.error(e.getStackTrace());
				e.printStackTrace();
			}
		}
	}

	/**
	 * Calculates the influences of the event to other simulation objects.
	 */
	private void calculateNextEvent() {
		// Liefert Kopf und entfernt Element aus Liste
		Event event = this.eventQueue.poll();
		// TODO Eventauswirkungen implentieren
	}

	/**
	 * Adds a new {@link Event} to the event queue.
	 * 
	 * @param event -
	 *            The event, which should add to the event queue.
	 */
	public void addEvent(Event event) {
		eventQueue.add(event);
	}
}
