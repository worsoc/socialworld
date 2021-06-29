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
	
	int getColorForHeight(int height) {
		
		Color color = SimVisual.COLOR_PINK;
		
		switch (height) {
		case 17:
			color = SimVisual.COLOR_POWDERBLUE; break;
		case 18:
			color = SimVisual.COLOR_CADETBLUE; break;
		case 19:
			color = SimVisual.COLOR_LIGHTSEAGREEN; break;
		case 20:
			color = SimVisual.COLOR_MEDIUMSPRINGGREEN; break;
		case 21:
			color = SimVisual.COLOR_MEDIUMSEAGREEN; break;
		case 22:
			color = SimVisual.COLOR_SEAGREEN; break;
		case 23:
			color = SimVisual.COLOR_DARKGREEN; break;
		case 24:
			color = SimVisual.COLOR_GREEN; break;
		case 25:
			color = SimVisual.COLOR_LIMEGREEN; break;
		case 26:
			color = SimVisual.COLOR_LIME; break;
		case 27:
			color = SimVisual.COLOR_GREENYELLOW; break;
		case 28:
			color = SimVisual.COLOR_YELLOWGREEN; break;
		case 29:
			color = SimVisual.COLOR_OLIVE; break;
		case 30:
			color = SimVisual.COLOR_KHAKI; break;
		case 31:
			color = SimVisual.COLOR_GOLD; break;
		case 32:
			color = SimVisual.COLOR_ORANGE; break;
		case 33:
			color = SimVisual.COLOR_CHOCOLATE; break;
		case 34:
			color = SimVisual.COLOR_SIENNA; break;
		case 35:
			color = SimVisual.COLOR_SADDLEBROWN; break;
		case 36:
			color = SimVisual.COLOR_BROWN; break;
		case 37:
			color = SimVisual.COLOR_MAROON; break;
		default:
			System.out.println("keine Farbe für Höhe " + height);
		}
		int colorRGB = color.getRGB();
		return colorRGB;
	}
}
