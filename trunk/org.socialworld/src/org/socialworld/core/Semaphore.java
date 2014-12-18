package org.socialworld.core;

public class Semaphore {

	/**
	 * the object that holds the semaphore 
	 * an so can use the semaphore saved object
	 */
	protected Object locker = null;

	/**
	 * The method locks the semaphore saved object.
	 * The object can be locked if and only
	 * if there is no other locking object.
	 * 
	 * @param user
	 *            the locking object / the user of the saved object
	 * @return  Semaphore_ReturnCode         
	 */
	public Semaphore_ReturnCode lockBy(Object user) {
		if (this.locker == null) {
			this.locker = user;
			return Semaphore_ReturnCode.success;
		}
		if (this.locker == user) 
			return Semaphore_ReturnCode.lockedByUser;
		return Semaphore_ReturnCode.lockedByAnotherUser;
	}

	/**
	 * The method releases the semaphore saved object.
	 * The object can be released by the
	 * locking object only.
	 * 
	 * @param user
	 *            the locking object / the user of the saved object
	 * @return  Semaphore_ReturnCode          
	 */
	public Semaphore_ReturnCode releaseBy(Object user) {
		if (this.locker == user) {
			this.locker = null;
			return Semaphore_ReturnCode.success;
		}
		if (this.locker == null)
			return Semaphore_ReturnCode.notLockedByAnyone;
		return Semaphore_ReturnCode.lockedByAnotherUser;
	}
	
}
