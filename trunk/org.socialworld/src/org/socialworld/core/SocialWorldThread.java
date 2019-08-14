package org.socialworld.core;

public class SocialWorldThread extends Thread{

	
	/**
	 * says whether the thread is running or not
	 */
	private boolean isRunning;
	
	protected boolean isRunning() {return this.isRunning;}
	
	/**
	 * the method stops the event processing
	 */
	public void stopThread() {
		isRunning = false;
	}

	/**
	 * the method starts the event processing
	 */
	public void startThread() {
		isRunning = true;
		this.start();
	}

}
