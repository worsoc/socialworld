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

import org.socialworld.attributes.PropertyName;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.knowledge.Acquaintance;
import org.socialworld.knowledge.IAnswer;
import org.socialworld.objects.concrete.*;
import org.socialworld.objects.concrete.animals.Primate;
import org.socialworld.objects.concrete.animals.StateBody;
import org.socialworld.objects.concrete.animals.StateInventory;
import org.socialworld.objects.concrete.animals.StateSeer;
import org.socialworld.objects.enums.EnumMammal;
import org.socialworld.tools.StringTupel;
import org.socialworld.conversation.Talk_SentenceType;
import org.socialworld.core.IAccessToken;
import org.socialworld.core.IEventParam;

/**
 * A human is described in most details. It is the most important simulation
 * object. The simulation of a human is the main target of the game. A human has
 * an attribute array that describes his inner state. There are values for
 * courage, spirituality and morals for example. There is a detailed
 * differentiation of action handling an event influence. A human is the only
 * simulation object that has an inventory. So it can carry items and use them.
 * 
 * @author Mathias Sikos (MatWorsoc)
 * 
 */
 public class Human extends Primate {
	
	 private StateHuman state;
	
///////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////meta information    ////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	private static StringTupel[] propertiesMetaInfos = new StringTupel[]{
		} ;
	
	public static List<StringTupel> getPropertiesMetaInfos() {
		List<StringTupel> listOfPropertyMetaInfo = Primate.getPropertiesMetaInfos();
		for (int indexAdd = 0; indexAdd < propertiesMetaInfos.length; indexAdd++) {
			listOfPropertyMetaInfo.add(propertiesMetaInfos[indexAdd]);
		}
		return listOfPropertyMetaInfo;
	}

	private static PropertyName[] usedStateAppearanceColourPropertyNames = new PropertyName[] {
			PropertyName.stateAppearance_colourHead,
			PropertyName.stateAppearance_colourBreast,
			PropertyName.stateAppearance_colourBack,
			PropertyName.stateAppearance_colourTail,
			PropertyName.stateAppearance_colourLegs,
			PropertyName.stateAppearance_colourArms,
			PropertyName.stateAppearance_colourSkin,
			PropertyName.stateAppearance_colourHair,
			PropertyName.stateAppearance_colourBeard,
			PropertyName.stateAppearance_colourEye
	};

	private static byte[] stateAppearanceMainColourCalculationColourSetsShares = new byte[] {
			10,
			15,
			20,
			0,
			35,
			20,
			0,
			0,
			0,
			0
	};

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    creating instance for simulation    //////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public Human() {
		super();
		this.belongsTo = EnumMammal.Human;
		setLevelObjectSearch(8 /* base 9: 0.6 meters */ , 5 /* base 25: 1.3 meters */);
		 
	}

	protected SimulationObject_Type getSimObjectType() {
		return SimulationObject_Type.human;
	}


	public boolean checkObjectBelongsToGroup(int groupNumberSuffix) {
		// SUB_CLASS_IMPLEMENTATION checkObjectBelongsToGroup()
		// TEMP_SOLUTION
		return true;
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    STATE       //////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	protected void assignState(StateSimulationObject state) {
		super.assignState(state);
		if (checkIsMyState(state) ) this.state = (StateHuman) state;
	}

	protected State getInitState(String stateClassName) {
		if (stateClassName.equals(StatePerceptible.class.getName())) {
			return new StatePerceptible(this);
		}
		else if (stateClassName.equals(StateAppearance.class.getName())) {
			return new StateAppearance(this);
		}
		else if (stateClassName.equals(StateComposition.class.getName())) {
			return new StateComposition(this);
		}
		else if (stateClassName.equals(StateSeer.class.getName())) {
			return new StateSeer(this);
		}
		else if (stateClassName.equals(StateBody.class.getName())) {
			return new StateBody(this);
		}
		else if (stateClassName.equals(StateInventory.class.getName())) {
			return new StateInventory(this);
		}
		
		return State.getObjectNothing();
		
	}
	

	public PropertyName[] getUsedStateAppearanceColourPropertyNames() {
		return usedStateAppearanceColourPropertyNames;
	}

	public byte[] getStateAppearanceMainColourCalculationColourSetsShares() {
		return stateAppearanceMainColourCalculationColourSetsShares;
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    TALK       ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
		
	final public String getSentence(Human partner, Talk_SentenceType type) {
		return this.state.findSentence( partner,  type);
	}
	
	final public String getLastSaidSentence() {
		return this.state.getLastSaidSentence();
	}

	final public List<IAnswer> getAnswersForQuestion(String question) {
		// no copy
		// because a new answer is created in method KnowledgePool.getAnswerForQuestion()
		return this.state.getAnswersForQuestion(question);
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    KNOWLEDGE  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	final public Acquaintance getAcquaintance(Human partner) {
		return this.state.getAcquaintance(partner);
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    INVENTORY  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	// TODO interface for more complex access to inventory
/*	final public IWeapon getLeftHandWeapon() {
		// no copy because it is a simulation object and that isn't allowed to be duplicated
		return this.state.getLeftHandWeapon();
	}
	
	final public IWeapon getRightHandWeapon() {
		// no copy because it is a simulation object and that isn't allowed to be duplicated
		return this.state.getRightHandWeapon();
	}
*/
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    PROPERTY LIST  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public void requestPropertyList(IAccessToken token, IEventParam paramObject) {
		
		super.requestPropertyList(token, paramObject);
		
		ValueArrayList propertiesAsValueList = new ValueArrayList();
		paramObject.answerPropertiesRequest(propertiesAsValueList);
		
	}


		
	
}
