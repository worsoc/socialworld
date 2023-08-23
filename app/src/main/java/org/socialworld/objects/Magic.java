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
package org.socialworld.objects;

import java.util.List;

import org.socialworld.calculation.SimulationCluster;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.core.IEventParam;
import org.socialworld.tools.StringTupel;

/**
 * magic spells are simulation objects that offer some fantasy aspects. A magic
 * spells releases events that change the environment and effect simulation
 * objects.
 * 
 * @author Mathias Sikos (tyloesand)
 * 
 */
public abstract class Magic extends SimulationObject {
	
///////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////meta information    ////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	private static StringTupel[] propertiesMetaInfos = new StringTupel[]{
	} ;
	
	public static List<StringTupel> getPropertiesMetaInfos() {
		List<StringTupel> listOfPropertyMetaInfo = SimulationObject.getPropertiesMetaInfos();
		for (int indexAdd = 0; indexAdd < propertiesMetaInfos.length; indexAdd++) {
			listOfPropertyMetaInfo.add(propertiesMetaInfos[indexAdd]);
		}
		return listOfPropertyMetaInfo;
	}
	

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    creating instance for simulation    //////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	
// TODO implement Magic class functionality

	@Override
	public final boolean isSimulationObject() {return true;}

	protected SimulationObject_Type getSimObjectType() {
		return SimulationObject_Type.magic;
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    STATE  ///////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	protected void assignState(StateSimulationObject state) {
		//if (checkIsMyState(state) ) this.state = (StateMagic) state;
	}

/*	
	protected void createAddOnStates() {
		
	}
*/

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    PROPERTY LIST  ///////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public void requestPropertyList(SimulationCluster cluster, IEventParam paramObject) {
	
		super.requestPropertyList(cluster, paramObject);
		
		ValueArrayList propertiesAsValueList = new ValueArrayList();
		paramObject.answerPropertiesRequest(propertiesAsValueList);
	
	}

}
