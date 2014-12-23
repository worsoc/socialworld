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
