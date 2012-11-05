package org.socialworld.attributes;

public class ActualTime extends Time {
	private static ActualTime instance;
	
	private ActualTime() {
		super();
	}
	
	public static ActualTime get() {
		instance = new ActualTime();
		return instance;
	}
	
	public static long inMilliseconds() {
		instance = new ActualTime();
		return instance.getTotalMilliseconds();
	}

}
