/*
* Social World
* Copyright (C) 2019  Josef Pribbernow
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

package org.socialworld.tools.mct;

public class TileInfo {
	
	
	String getTileInfo (TileType type, int number) {
		
		switch (type) {
			case largeStandard:
				return getTileInfoLargeStandard(number);
				
			case mediumStandard:
				return getTileInfoMediumStandard(number);
			case mediumAdapter:
				if (number > 300) number = number - 100;
				if (number > 200) number = number - 100;
				if (number > 100) number = number - 100;
				return getTileInfoAdapter(number); 
			case smallAdapter:
				if (number > 300) number = number - 100;
				if (number > 200) number = number - 100;
				if (number > 100) number = number - 100;
				return getTileInfoAdapter(number); 
			case smallStandard:
				return getTileInfoSmallStandard(number);
			case smallSpecial:
				if (number > 300) number = number - 100;
				if (number > 200) number = number - 100;
				if (number > 100) number = number - 100;
				return getTileInfoSmallSpecial(number);				
			case smallSpecialAdapter:
				if (number > 300) number = number - 100;
				if (number > 200) number = number - 100;
				if (number > 100) number = number - 100;
				return getTileInfoSmallSpecialAdapter(number);
			default: return "";
		}
	}
	
	String  getTileInfoLargeStandard ( int number) {
		
		switch (number) {
			case 0:
				return " eben.";
			case 1:
				return " steigend von der Mitteldiagonalen in die Ecke SuedOst.";
			case 2:
				return " steigend von der Mitteldiagonalen in die Ecke SuedWest.";
			case 3:
				return " steigend von der Kante Nord zur Kante Sued.";
			case 4:
				return " steigend von der Mitteldiagonalen in die Ecke NordOst.";
			case 5:
				return " steigend von der Kante West zur Kante Ost.";
			case 6:
				return " steigend von Ecke SuedWest zur Mitteldiagonalen , steigend von Ecke NordOst zur Mitteldiagonalen"; 
			case 7:
				return " steigend von der Ecke NordWest auf die Mitteldiagonale und verlaeuft eben nach SuedOst";
			case 8:
				return " steigend von der Mitteldiagonalen in die Ecke NordWest.";
			case 9:
				return " steigend von Ecke NordWest zur Mitteldiagonalen , steigend von Ecke SuedOst zur Mitteldiagonalen"; 
			case 10:
				return " steigend von der Kante Ost zur Kante West.";
			case 11:
				return " steigend von der Ecke NordOst auf die Mitteldiagonale und verlaeuft eben nach SuedWest";
			case 12:
				return " steigend von der Kante Sued zur Kante Nord.";
			case 13:
				return " steigend von der Ecke SuedWest auf die Mitteldiagonale und verlaeuft eben nach NordOst";
			case 14:
				return " steigend von der Ecke SuedOst auf die Mitteldiagonale und verlaeuft eben nach NordWest";
			case 15:
				return " eben auf erhoehtem Level.";
			case 16:
				return " steigend um 2 Level von SuedWest nach NordOst.";
			case 17:
				return " steigend um 2 Level von NordOst nach SuedWest.";
			case 18:
				return " steigend um 2 Level von NordWest nach SuedOst.";
			case 19:
				return " steigend um 2 Level von SuedOst nach NordWest.";
		}
		return "";	
	}
	
	String  getTileInfoMediumStandard ( int number) {
		
		return getTileInfoLargeStandard(number);	
	}
		
	String  getTileInfoSmallStandard ( int number) {
		
		return getTileInfoLargeStandard(number);	
	}
	
	//TODO implement tiel info for small special adapters
	String  getTileInfoSmallSpecialAdapter ( int number) {
		
		switch (number) {
			case 0:
				return "";
			case 1:
				return "";
			case 2:
				return "";
			case 3:
				return "";
			case 4:
				return "";
			case 5:
				return "";
			case 6:
				return "";
			case 7:
				return "";
			case 8:
				return "";
			case 9:
				return "";
			case 10:
				return "";
			case 11:
				return "";
			case 12:
				return "";
			case 13:
				return "";
			case 14:
				return "";
			case 15:
				return "";
			case 16:
				return "";
			case 17:
				return "";
			case 18:
				return "";
			case 19:
				return "";
			case 20:
				return "";
			case 21:
				return "";
			case 22:
				return "";
			case 23:
				return "";
			case 24:
				return "";
			case 25:
				return "";
			case 26:
				return "";
			case 27:
				return "";
			case 28:
				return "";			
		}
		return "";	
	}
	
	String  getTileInfoSmallSpecial ( int number) {
		
		switch (number) {
			case 3:
				return "ansteigend von Nord nach Sued (Variante bestimmt das Anstiegsdrittel)";
			case 5:
				return "ansteigend von West nach Ost (Variante bestimmt das Anstiegsdrittel)";
			case 10:
				return "ansteigend von Ost nach West (Variante bestimmt das Anstiegsdrittel)";
			case 12:
				return "ansteigend von Sued nach Nord (Variante bestimmt das Anstiegsdrittel)";
		}
		return "";	
	}
	
	String  getTileInfoAdapter ( int number) {
		
		if (number > 70) return "Adapter nach Sued, leicht ansteigend von Ost nach West (Reihenfolge 79 ... 71)";
		if (number > 60) return "Adapter nach Sued, leicht ansteigend von West nach Ost (Reihenfolge 61 ... 69)";
		if (number > 50) return "Adapter nach Ost, leicht ansteigend von Sued nach Nord (Reihenfolge 59 ... 51)";
		if (number > 40) return "Adapter nach West, leicht ansteigend von Sued nach Nord (Reihenfolge 49 ... 41)";
		if (number > 30) return "Adapter nach Nord, leicht ansteigend von Ost nach West (Reihenfolge 39 ... 31)";
		if (number > 20) return "Adapter nach West, leicht ansteigend von Nord nach Sued (Reihenfolge 21 ... 29)";
		if (number > 10) return "Adapter nach Ost, leicht ansteigend von Nord nach Sued (Reihenfolge 11 ... 19)";
		if (number > 0) return "Adapter nach Nord, leicht ansteigend von West nach Ost (Reihenfolge 01 ... 09)";
		
		return "";
		
	}

}
