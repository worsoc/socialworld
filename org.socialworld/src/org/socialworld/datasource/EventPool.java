package org.socialworld.datasource;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.URL;

import org.apache.log4j.Logger;
import org.socialworld.core.Event;
import org.socialworld.attributes.*;

public class EventPool {
	private static final int EVENT_ARRAY_INCREASE = 100;
	private static final Logger logger = Logger.getLogger(EventPool.class);
	private static EventPool instance;
	
	private static Event events[];
	private int size = 0;
	private int nextFree = 0;
	
	private EventPool () {
		logger.debug("create EventPool");
		events = new Event[size];

		initialize();
	}
	
	public static EventPool getInstance() {
		if (instance == null) {
			instance = new EventPool();
		}
		return instance;
	}
	
	public Event getEvent(int index) {
		if (size > index) 	return events[index];
		else return null;
	}
	
	private void initialize() {
		
		addEvent(0,createEvent(1));
		addEvent(1, createEvent(2));
		addEvent(2, createEvent(3));
		nextFree = 3;
		
		initializeFromFile();
	}

	private Event createEvent(int number) {
		byte priority = 1;
		Event event = new Event(priority);

		switch (number) {
		case 1:
			event.setDirection(new Direction(1,2,3));
			event.setEffectAngle(30);
			event.setEffectDistance(100);
			event.setEventType(1);
			event.setMaxDistance(200);
			event.setMaxSee(200);
			event.setMaxHear(100);
			event.setMaxSmell(0);
			event.setStrength(50);
			return event;
		default:
			event.setDirection(new Direction(3,2,1));
			event.setEffectAngle(33);
			event.setEffectDistance(121);
			event.setEventType(1);
			event.setMaxDistance(180);
			event.setMaxSee(150);
			event.setMaxHear(180);
			event.setMaxSmell(0);
			event.setStrength(66);
			return event;
		}
	}

	


	private void initializeFromFile() {
		
		
		Event event;
		
		int eventType = 0;
		int priority = 0;
		int strength = 0;

		float effectDistance = 0;
		float effectAngle = 0;
		float maxDistance = 0;
		float maxSee = 0;
		float maxHear = 0;
		float maxSmell = 0;
		float maxFeel = 0;
	
	
		
		try
		{
			String line;
			
	
			InputStream input = new URL("http://sourceforge.net/projects/socialworld/files/events.swp").openStream();
			LineNumberReader lnr
			   = new LineNumberReader(new InputStreamReader(input));
	
	
			while ((line = lnr.readLine()) != null)
			{
	
				if (line.startsWith("<EVENT>")) {
					// reset the event properties
					 eventType = 0;
					 priority = 0;
					 strength = 0;
	
					 effectDistance = 0;
					 effectAngle = 0;
					 maxDistance = 0;
					 maxSee = 0;
					 maxHear = 0;
					 maxSmell= 0;
					 maxFeel=0;
					continue;
				}
	
				if (line.startsWith("</EVENT>")) {
					event = new Event(eventType, priority, strength, 
							 effectDistance,  effectAngle,
							 maxDistance,  maxSee,  maxHear,  maxSmell,  maxFeel);
					addEvent(nextFree, event);
					nextFree = nextFree + 1;
					continue;
				}
				
				if (line.startsWith("<Type>"))
				{
					
					line = line.substring(6);
					line = line.replace("</Type>", "");
					line = line.trim();
					eventType = (int) Float.parseFloat(line);
					continue;
				}
				
				if (line.startsWith("<Prio>"))
				{
					
					line = line.substring(6);
					line = line.replace("</Prio>", "");
					line = line.trim();
					priority = (int) Float.parseFloat(line);
					continue;
				}

				if (line.startsWith("<Strength>"))
				{
					
					line = line.substring(10);
					line = line.replace("</Strength>", "");
					line = line.trim();
					priority = (int) Float.parseFloat(line);
					continue;
				}
	
				if (line.startsWith("<effDist>"))
				{
					
					line = line.substring(9);
					line = line.replace("</effDist>", "");
					line = line.trim();
					effectDistance =  Float.parseFloat(line);
					continue;
				}
				
				if (line.startsWith("<effAngle>"))
				{
					
					line = line.substring(10);
					line = line.replace("</effAngle>", "");
					line = line.trim();
					effectAngle =  Float.parseFloat(line);
					continue;
				}

				if (line.startsWith("<maxDist>"))
				{
					
					line = line.substring(9);
					line = line.replace("</maxDist>", "");
					line = line.trim();
					maxDistance =  Float.parseFloat(line);
					continue;
				}

				if (line.startsWith("<maxSee>"))
				{
					
					line = line.substring(8);
					line = line.replace("</maxSee>", "");
					line = line.trim();
					maxSee =  Float.parseFloat(line);
					continue;
				}

				if (line.startsWith("<maxHear>"))
				{
					
					line = line.substring(9);
					line = line.replace("</maxHear>", "");
					line = line.trim();
					maxHear =  Float.parseFloat(line);
					continue;
				}

				if (line.startsWith("<maxSmell>"))
				{
					
					line = line.substring(10);
					line = line.replace("</maxSmell>", "");
					line = line.trim();
					maxSmell =  Float.parseFloat(line);
					continue;
				}

				if (line.startsWith("<maxFeel>"))
				{
					
					line = line.substring(9);
					line = line.replace("</maxFeel>", "");
					line = line.trim();
					maxFeel =  Float.parseFloat(line);
					continue;
				}

			
			}
			lnr.close();
		}
		catch (IOException e)
		{
			System.out.println("Fehler beim Lesen der Datei");
			e.printStackTrace();
		}
		
		
	}

	private void addEvent(int index, Event event) {
		Event newEvents[];
		int newSize;
		
		if (index >= size) {
			newSize = index + EVENT_ARRAY_INCREASE;
			newEvents = new Event[newSize];
			for (int i = 0; i < size; i++) newEvents[i] = events[i];
			events = newEvents;
			size = newSize;
		}
		events[index] = event;
	}
}
