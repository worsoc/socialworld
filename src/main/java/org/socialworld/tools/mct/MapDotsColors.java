/*
* Social World
* Copyright (C) 2021  Mathias Sikos
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

import java.awt.Color;
import org.socialworld.visualize.SimVisual;

class MapDotsColors {
	int[][] colorsForXY = new int[729][729];
	
	static int getColorForIsle(int isleID) {
		
		Color color = SimVisual.COLOR_GRAY;

		switch (isleID) {
		case 1:
			color = SimVisual.COLOR_MEDIUMSPRINGGREEN; break;
		case 2:
			color = SimVisual.COLOR_MEDIUMSEAGREEN; break;
		case 3:
			color = SimVisual.COLOR_SEAGREEN; break;
		case 4:
			color = SimVisual.COLOR_DARKGREEN; break;
		case 5:
			color = SimVisual.COLOR_GREEN; break;
		case 6:
			color = SimVisual.COLOR_LIMEGREEN; break;
		case 7:
			color = SimVisual.COLOR_LIME; break;
		case 8:
			color = SimVisual.COLOR_GREENYELLOW; break;
		default:
			
		}
		int colorRGB = color.getRGB();
		return colorRGB;
	}

	
	int getColorForHeight(int height) {
		
		Color color = SimVisual.COLOR_PINK;
		
		switch (height) {
			
			
		case -4:
			color = SimVisual.COLOR_DARKEBLUE; break;
		case -3:
			color = SimVisual.COLOR_NAVY; break;
		case -2:
			color = SimVisual.COLOR_BLUE; break;
		case -1:
			color = SimVisual.COLOR_LIGHTSEAGREEN; break;
		case 0:
			color = SimVisual.COLOR_MEDIUMSPRINGGREEN; break;
		case 1:
			color = SimVisual.COLOR_MEDIUMSEAGREEN; break;
		case 2:
			color = SimVisual.COLOR_SEAGREEN; break;
		case 3:
			color = SimVisual.COLOR_DARKGREEN; break;
		case 4:
			color = SimVisual.COLOR_GREEN; break;
		case 5:
			color = SimVisual.COLOR_LIMEGREEN; break;
		case 6:
			color = SimVisual.COLOR_LIME; break;
		case 7:
			color = SimVisual.COLOR_GREENYELLOW; break;
		case 8:
			color = SimVisual.COLOR_YELLOWGREEN; break;
		case 9:
			color = SimVisual.COLOR_OLIVE; break;
		case 10:
			color = SimVisual.COLOR_KHAKI; break;
		case 11:
			color = SimVisual.COLOR_GOLD; break;
		case 12:
			color = SimVisual.COLOR_ORANGE; break;
		case 13:
			color = SimVisual.COLOR_CHOCOLATE; break;
		case 14:
			color = SimVisual.COLOR_SIENNA; break;
		case 15:
			color = SimVisual.COLOR_SADDLEBROWN; break;
		case 16:
			color = SimVisual.COLOR_BROWN; break;
		case 17:
			color = SimVisual.COLOR_MAROON; break;
		default:
			System.out.println("keine Farbe für Höhe " + height);
		}
		int colorRGB = color.getRGB();
		return colorRGB;
	}
}
