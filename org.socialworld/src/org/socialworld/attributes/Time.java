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
package org.socialworld.attributes;

/**
 * The class implements the simulation time.
 *         Because of being a real time simulation the simulation time should be
 *         mapped to real time. An object of class Type must be seen as a time
 *         point.
 *         
 * German:
 * Ein Objekt der Klasse Time beschreibt einen Simulationszeitpunkt,
 *  - zerlegt  in seine Bestandteile Tag, Stunde, Minute, Sekunde und Millisekunde
 *  - oder als Gesamtzahl Millisekunden
 * 
 *         
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

	public Time(boolean isAbsolut, long milliseconds) {
		if (isAbsolut) {
			this.totalMilliseconds = milliseconds;
		}
		else {
			this.totalMilliseconds = now() + milliseconds;
		}		
		separate();
	}

	public long getTotalMilliseconds() {
		return this.totalMilliseconds;
	}

	public byte getSecond() {
		return this.seconds;
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
		return System.currentTimeMillis();
	}

	public String toString() {
		return "" +(this.days) + " " + this.hours + ":" + this.minutes + ":" + this.seconds + "." + this.milliseconds;
	}
}
