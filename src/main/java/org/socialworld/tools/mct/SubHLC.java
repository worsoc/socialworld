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

public abstract class SubHLC {

	static SubHLC instance;
	
	abstract boolean checkExistAllowedHLC(int start, int end);
	abstract List<HeightLevelChanger> getAllowedHLC(int start, int end, List<Integer> subClusterSorted);
	
	protected boolean checkForMatchingHLC(List<Integer> hlcRasterIndices,
			 List<Integer> subClusterSorted) {
		return subClusterSorted.containsAll(hlcRasterIndices);
	}
	
	protected List<Integer> getRasterIndicesList(int start, int[] rasterIndices) {
		List<Integer> result = new ArrayList<Integer>();
		int offset = start - rasterIndices[0];
		for (int i = 0; i < rasterIndices.length; i++) {
			result.add(rasterIndices[i] + offset);
		}
		return result;
	}

	protected boolean checkForIntersection(int[] nrsForStart, int[] nrsForEnd) {
		int indexStart;
		int indexEnd;
		int nrHLC;
		
		for (indexStart = 0; indexStart < nrsForStart.length; indexStart++) {
			nrHLC = nrsForStart[indexStart];
			for (indexEnd = 0; indexEnd < nrsForEnd.length; indexEnd++) {
				if (nrsForEnd[indexEnd] == nrHLC) {
					return true;
				}
			}
		}
		return false;
		
	}
	
	protected List<Integer> getIntersection(int[] nrsForStart, int[] nrsForEnd) {
		List<Integer> result = new ArrayList<Integer>();
		int indexStart;
		int indexEnd;
		int nrHLC;

		for (indexStart = 0; indexStart < nrsForStart.length; indexStart++) {
			nrHLC = nrsForStart[indexStart];
			for (indexEnd = 0; indexEnd < nrsForEnd.length; indexEnd++) {
				if (nrsForEnd[indexEnd] == nrHLC) {
					result.add(nrHLC);
				}
			}
		}

		return result;
	}
}
