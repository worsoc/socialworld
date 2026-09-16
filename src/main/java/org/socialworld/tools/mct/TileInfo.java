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
				return " steigend von Ecke NordWest zur Mitteldiagonalen , steigend von Ecke SuedOst zur Mitteldiagonalen"; 
			case 7:
				return " steigend von der Ecke NordWest auf die Mitteldiagonale und verlaeuft eben nach SuedOst";
			case 8:
				return " steigend von der Mitteldiagonalen in die Ecke NordWest.";
			case 9:
				return " steigend von Ecke SuedWest zur Mitteldiagonalen , steigend von Ecke NordOst zur Mitteldiagonalen"; 
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

    // Gibt die Höhen-Offsets (0=Nord, 1=Ost, 2=Süd, 3=West) der Kachel zurück
    static double[] getCorners(int number, double baseHeight) {
        double n = 0, o = 0, s = 0, w = 0;
        switch (number) {
            case 0: // eben
                break;
            case 1: // steigend von Mitteldiagonale in Ecke SuedOst
                s = 1.0; 
                break;
            case 2: // steigend von Mitteldiagonale in Ecke SuedWest
                w = 1.0; 
                break;
            case 3: // steigend von Kante Nord zur Kante Sued
                s = 1.0; w = 1.0; 
                break;
            case 4: // steigend von Mitteldiagonale in Ecke NordOst
                o = 1.0; 
                break;
            case 5: // steigend von Kante West zur Kante Ost
                o = 1.0; s = 1.0; 
                break;
            case 6: // steigend von Ecke NW zur Mitteldiagonale, steigend von Ecke SO zur Mitteldiagonale
                // Die Diagonale von West nach Ost bildet den hohen Grat
                w = 1.0; 
                o = 1.0; 
                n = 0.0; 
                s = 0.0; 
                break; 
            case 7: // steigend von Ecke NW auf die Mitteldiagonale und eben nach SO
                w = 1.0; o = 1.0; s = 1.0; // Nur NW-Ecke (n) bleibt unten
                break;
            case 8: // steigend von Mitteldiagonale in Ecke NordWest
                n = 1.0; 
                break;
            case 9: // steigend von Ecke SW zur Mitteldiagonale, steigend von Ecke NO zur Mitteldiagonale
                // Die Diagonale von Nord nach Süd bildet den hohen Grat
                n = 1.0; 
                s = 1.0; 
                o = 0.0; 
                w = 0.0; 
                break; 
            case 10: // steigend von Kante Ost zur Kante West
                n = 1.0; w = 1.0; 
                break;
            case 11: // steigend von Ecke NO auf die Mitteldiagonale und eben nach SW
                n = 1.0; s = 1.0; w = 1.0; // Nur NO-Ecke (o) bleibt unten
                break;
            case 12: // steigend von Kante Sued zur Kante Nord
                n = 1.0; o = 1.0; 
                break;
            case 13: // steigend von Ecke SW auf die Mitteldiagonale und eben nach NO
                n = 1.0; o = 1.0; s = 1.0; // Nur SW-Ecke (w) bleibt unten
                break;
            case 14: // steigend von Ecke SO auf die Mitteldiagonale und eben nach NW
                o = 1.0; n = 1.0; w = 1.0; // Nur SO-Ecke (s) bleibt unten
                break;
            case 15: // eben auf erhoehtem Level
                n = 1.0; o = 1.0; s = 1.0; w = 1.0; 
                break;
            case 16: // steigend um 2 Level von SW nach NO
                o = 1.0; w = -1.0;
                break;
            case 17: // steigend um 2 Level von NO nach SW
                w = 1.0; o = -1.0; 
                break;
            case 18: // steigend um 2 Level von NW nach SO
                s = 1.0; n = -1.0; 
                break;
            case 19: // steigend um 2 Level von SO nach NW
                n = 1.0; s = -1.0; 
                break;
        }
        return new double[]{baseHeight + n, baseHeight + o, baseHeight + s, baseHeight + w};
    }

    // Liefert das Höhen-Offset basierend auf dem MapVisualizer-Regelwerk
    static int getOffset(int number, String corner) {
        int nw = 0, no = 0, so = 0, sw = 0;
        switch (number) {
            case 0: break;
            case 1: so = 1; break;
            case 2: sw = 1; break;
            case 3: so = 1; sw = 1; break;
            case 4: no = 1; break;
            case 5: no = 1; so = 1; break;
            case 6: sw = 1; no = 1; break; 
            case 7: sw = 1; no = 1; so = 1; break;
            case 8: nw = 1; break;
            case 9: nw = 1; so = 1; break; 
            case 10: nw = 1; sw = 1; break;
            case 11: nw = 1; so = 1; sw = 1; break;
            case 12: nw = 1; no = 1; break;
            case 13: nw = 1; no = 1; so = 1; break;
            case 14: no = 1; nw = 1; sw = 1; break;
            case 15: nw = 1; no = 1; so = 1; sw = 1; break;
            case 16: no = 1; sw = -1; break;
            case 17: sw = 1; no = -1; break;
            case 18: so = 1; nw = -1; break;
            case 19: nw = 1; so = -1; break;
        }
        
        switch (corner) {
            case "nw": return nw;
            case "no": return no;
            case "so": return so;
            case "sw": return sw;
            default: return 0;
        }
    }
}
