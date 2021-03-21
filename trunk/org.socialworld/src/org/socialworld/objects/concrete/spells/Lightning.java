/*
* Social World
* Copyright (C) 2020  Mathias Sikos
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
package org.socialworld.objects.concrete.spells;


import java.util.ArrayList;
import java.util.List;

import org.socialworld.attributes.percipience.Percipience;
import org.socialworld.attributes.percipience.PercipienceType;
import org.socialworld.objects.Magic;
import org.socialworld.objects.State;
import org.socialworld.objects.concrete.StatePerceptible;
import org.socialworld.objects.concrete.eatable.fruits.Apple;
import org.socialworld.tools.Generation;
import org.socialworld.tools.StringPair;

public class Lightning extends Magic {

///////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////meta information    ////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	private static Lightning singletonDummyForGenerationTools;
	private static StringPair[] propertiesMetaInfos = new StringPair[]{
			} ;
	
	public static Lightning getInstance(Generation calledFromGeneration) {
		if (singletonDummyForGenerationTools == null) {
			singletonDummyForGenerationTools = new Lightning(calledFromGeneration);
		}
		return singletonDummyForGenerationTools;
	}
	
	protected Lightning(Generation calledFromGeneration) {
		super(calledFromGeneration);
		listOfPropertyMetaInfo = super.getPropertiesMetaInfos();
		for (int indexAdd = 0; indexAdd < propertiesMetaInfos.length; indexAdd++) {
			listOfPropertyMetaInfo.add(propertiesMetaInfos[indexAdd]);
		}
	}
	

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    creating instance for simulation    //////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	protected int getLexemID() {
		// TODO set lexemID
		return 0;
	}

	protected State getInitState(String stateClassName) {
		if (stateClassName.equals(StatePerceptible.class.getName())) {
			Percipience percipience = new Percipience(PercipienceType.dynamic);
			return new StatePerceptible(percipience);
		}
		
		return null;
		
	}

}
