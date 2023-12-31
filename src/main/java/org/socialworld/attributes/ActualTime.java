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
 * German:
 * Die Klasse ActualTime ist  von der Klasse Time abgeleitet.
 * 
 * Über die statischen Methoden asTime() und inMilliseconds() liefert die Klasse die aktuelle Zeit,
 * 	einmal als Objekt der Klasse Time und einmal als Zahl für die Millisekunden.
 * 
 * @author Mathias Sikos (MatWorsoc)
 */
public class ActualTime extends Time {
	private static ActualTime instance;
	
	private ActualTime() {
		super();
	}
	

	public static long inMilliseconds() {
		instance = new ActualTime();
		return instance.getTotalMilliseconds();
	}

	public static Time asTime() {
		return new Time();
	}
}
