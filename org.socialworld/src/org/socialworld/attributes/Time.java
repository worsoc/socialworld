/**
 * 
 */
package org.socialworld.attributes;

/**
 * The class implements the simulation time.
 *         Because of being a real time simulation the simulation time should be
 *         mapped to real time. An object of class Type must be seen has a time
 *         point.
 * @author Mathias Sikos (tyloesand) 
 */
public class Time {
	
	int days;
	byte hours;
	byte minutes;
	byte seconds;
	short milliseconds;
	
	long totalMilliseconds;
	
	public Time() {
		this.totalMilliseconds = now();
		
		separate();
	}

	public Time(long totalmilliseconds) {
		this.totalMilliseconds = totalmilliseconds;
		separate();
	}

	public long getTotalMilliseconds() {
		return this.totalMilliseconds;
	}
	
	private void separate() {
		long rest;
		
		rest = this.totalMilliseconds;
		
		this.milliseconds = (short) (rest % 1000);
		rest = (long) (rest / 1000);
		
		this.seconds = (byte) (rest % 60);
		rest = (long) (rest / 60);

		this.minutes = (byte) (rest % 60);
		rest = (long) (rest / 60);

		this.hours = (byte) (rest % 24);
		rest = (long) (rest / 24);

		this.days = (int) rest;
		
	}
	
	private long now() { 
		return 0;
	}

}
