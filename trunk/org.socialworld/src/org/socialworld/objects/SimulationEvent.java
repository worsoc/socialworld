package org.socialworld.objects;

/**
 * 
 * @author Mathias Sikos
 *
 */
public class SimulationEvent {

	byte priority;
	SimulationObject causer;           
	int strength;					
	long  time;					

	Position position;			
	Direction direction;
	float effectDistance;
	float effectAngle;
	float maxDistance;			
	float maxSee;				
	float maxHear;
	float maxSmell;
	
}
