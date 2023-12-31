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

import java.util.ArrayList;
import java.util.List;

public class HeightIsle {

	List<Integer> rasterIndices;
	List<Integer> cornerMaximaNrs;
	List<Integer> isleRings;
	
	public HeightIsle(List<Integer> rasterIndices, List<Integer> cornerMaximaNrs) {
		this.rasterIndices = rasterIndices;
		this.cornerMaximaNrs = cornerMaximaNrs;
		this.isleRings = new ArrayList<Integer>();
	}

	public HeightIsle(List<Integer> rasterIndices, List<Integer> cornerMaximaNrs, List<Integer> isleRings) {
		this.rasterIndices = rasterIndices;
		this.cornerMaximaNrs = cornerMaximaNrs;
		this.isleRings = isleRings;
	}

	List<Integer> getRasterIndices() {
		return new ArrayList<Integer>(rasterIndices);
	}
	
	List<Integer> getCornerMaximaNrs() {
		return new ArrayList<Integer>(cornerMaximaNrs);
	}

	List<Integer> getRings() {
		return new ArrayList<Integer>(isleRings);
	}

	boolean isValid() {
		return (rasterIndices.size() == cornerMaximaNrs.size());
	}
}
