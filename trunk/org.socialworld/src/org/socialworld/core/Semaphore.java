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
	 * @return  SemaphoreReturnCode         
	 */
	public SemaphoreReturnCode lockBy(Object user) {
		if (this.locker == null) {
			this.locker = user;
			return SemaphoreReturnCode.success;
		}
		if (this.locker == user) 
			return SemaphoreReturnCode.lockedByUser;
		return SemaphoreReturnCode.lockedByAnotherUser;
	}

	/**
	 * The method releases the semaphore saved object.
	 * The object can be released by the
	 * locking object only.
	 * 
	 * @param user
	 *            the locking object / the user of the saved object
	 * @return  SemaphoreReturnCode          
	 */
	public SemaphoreReturnCode releaseBy(Object user) {
		if (this.locker == user) {
			this.locker = null;
			return SemaphoreReturnCode.success;
		}
		if (this.locker == null)
			return SemaphoreReturnCode.notLockedByAnyone;
		return SemaphoreReturnCode.lockedByAnotherUser;
	}
	
}
