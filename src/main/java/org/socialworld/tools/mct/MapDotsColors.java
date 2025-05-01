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
import org.socialworld.visualize.SimColorConstants;

class MapDotsColors {
	int[][] colorsForXY = new int[729][729];
	
	static int getColorForIsle(int isleID) {
		
		Color color = SimColorConstants.COLOR_GRAY;

		switch (isleID) {
		case 1:
			color = SimColorConstants.COLOR_MEDIUMSPRINGGREEN; break;
		case 2:
			color = SimColorConstants.COLOR_MEDIUMSEAGREEN; break;
		case 3:
			color = SimColorConstants.COLOR_SEAGREEN; break;
		case 4:
			color = SimColorConstants.COLOR_DARKGREEN; break;
		case 5:
			color = SimColorConstants.COLOR_GREEN; break;
		case 6:
			color = SimColorConstants.COLOR_LIMEGREEN; break;
		case 7:
			color = SimColorConstants.COLOR_LIME; break;
		case 8:
			color = SimColorConstants.COLOR_GREENYELLOW; break;
		default:
			
		}
		int colorRGB = color.getRGB();
		return colorRGB;
	}

	
	int getColorForHeight(int height) {
		
		Color color = SimColorConstants.COLOR_PINK;
		
		switch (height) {
			
			
		case -4:
			color = SimColorConstants.COLOR_DARKEBLUE; break;
		case -3:
			color = SimColorConstants.COLOR_NAVY; break;
		case -2:
			color = SimColorConstants.COLOR_BLUE; break;
		case -1:
			color = SimColorConstants.COLOR_LIGHTSEAGREEN; break;
		case 0:
			color = SimColorConstants.COLOR_MEDIUMSPRINGGREEN; break;
		case 1:
			color = SimColorConstants.COLOR_MEDIUMSEAGREEN; break;
		case 2:
			color = SimColorConstants.COLOR_SEAGREEN; break;
		case 3:
			color = SimColorConstants.COLOR_DARKGREEN; break;
		case 4:
			color = SimColorConstants.COLOR_GREEN; break;
		case 5:
			color = SimColorConstants.COLOR_LIMEGREEN; break;
		case 6:
			color = SimColorConstants.COLOR_LIME; break;
		case 7:
			color = SimColorConstants.COLOR_GREENYELLOW; break;
		case 8:
			color = SimColorConstants.COLOR_YELLOWGREEN; break;
		case 9:
			color = SimColorConstants.COLOR_OLIVE; break;
		case 10:
			color = SimColorConstants.COLOR_KHAKI; break;
		case 11:
			color = SimColorConstants.COLOR_GOLD; break;
		case 12:
			color = SimColorConstants.COLOR_ORANGE; break;
		case 13:
			color = SimColorConstants.COLOR_CHOCOLATE; break;
		case 14:
			color = SimColorConstants.COLOR_SIENNA; break;
		case 15:
			color = SimColorConstants.COLOR_SADDLEBROWN; break;
		case 16:
			color = SimColorConstants.COLOR_BROWN; break;
		case 17:
			color = SimColorConstants.COLOR_MAROON; break;
		default:
			System.out.println("keine Farbe für Höhe " + height);
		}
		int colorRGB = color.getRGB();
		return colorRGB;
	}
}
