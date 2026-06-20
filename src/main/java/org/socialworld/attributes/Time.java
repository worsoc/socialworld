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
 *         mapped to real time. An object of class Time must be seen as a time
 *         point.
 *         
 * German:
 * Ein Objekt der Klasse Time beschreibt einen Simulationszeitpunkt,
 *  - zerlegt  in seine Bestandteile Tag, Stunde, Minute, Sekunde und Millisekunde
 *  - oder als Gesamtzahl Millisekunden
 * 
 *         
 * @author Mathias Sikos (MatWorsoc) 
 */
public class Time {
	
    private final int days;
    private final byte hours;
    private final byte minutes;
    private final byte seconds;
    private final short milliseconds;
    
    private final long totalMilliseconds;

    public Time() {
        this(true, System.currentTimeMillis()); 
    }

    public Time(boolean isAbsolut, long milliseconds) {
        this.totalMilliseconds = isAbsolut ? milliseconds : System.currentTimeMillis() + milliseconds;
        
        long rest = this.totalMilliseconds;
        this.milliseconds = (short) (rest % 1000);
        rest /= 1000;
        this.seconds = (byte) (rest % 60);
        rest /= 60;
        this.minutes = (byte) (rest % 60);
        rest /= 60;
        this.hours = (byte) (rest % 24);
        this.days = (int) (rest / 24);
    }

 
    // 3. Der Copy-Konstruktor kopiert nur noch die fertigen Werte
    public Time(Time original) {
        this.totalMilliseconds = original.totalMilliseconds;
        this.days = original.days;
        this.hours = original.hours;
        this.minutes = original.minutes;
        this.seconds = original.seconds;
        this.milliseconds = original.milliseconds;
    }	
	public long getTotalMilliseconds() {
		return this.totalMilliseconds;
	}

	public byte getSecond() {
		return this.seconds;
	}
	
		public String toString() {
		// Ein StringBuilder mit einer Kapazität von 24 Zeichen fängt das maximale Format 
		// "Days HH:MM:SS.mmm" komplett ab, ohne im Hintergrund intern zu wachsen.
		StringBuilder sb = new StringBuilder(24);
		
		sb.append(this.days)
		  .append(' ')
		  .append(this.hours)
		  .append(':')
		  .append(this.minutes)
		  .append(':')
		  .append(this.seconds)
		  .append('.')
		  .append(this.milliseconds);
		
		return sb.toString();
	}

}
