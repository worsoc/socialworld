/*
 * Social World
 * Copyright (C) 2019  Mathias Sikos
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://gnu.org>.
 */
package org.socialworld.objects.concrete;

import java.util.List;

import org.socialworld.attributes.PropertyName;
import org.socialworld.attributes.properties.NutrientSet;
import org.socialworld.attributes.properties.TasteSet;
import org.socialworld.objects.Item;
import org.socialworld.objects.State;
import org.socialworld.objects.enums.EnumBaseSimObj;
import org.socialworld.objects.enums.EnumFood;
import org.socialworld.objects.properties.IEatable;
import org.socialworld.objects.statics.Mapping_Food2LexemIDLowerPart;
import org.socialworld.tools.StringTupel;

public abstract class Food extends Item implements IEatable {

	protected EnumFood belongsTo;
	private StateEatable stateEatable;
	
	private static AccessTokenPackageConcrete token = AccessTokenPackageConcrete.getValid();
	
///////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////    meta information    ////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	private static StringTupel[] propertiesMetaInfos = new StringTupel[]{
				new StringTupel("stateEatable", PropertyName.stateEatable.name())
	} ;

	public static List<StringTupel> getPropertiesMetaInfos() {
		List<StringTupel> listOfPropertyMetaInfo = Item.getPropertiesMetaInfos();
			for (int indexAdd = 0; indexAdd < propertiesMetaInfos.length; indexAdd++) {
				listOfPropertyMetaInfo.add(propertiesMetaInfos[indexAdd]);
			}
		return listOfPropertyMetaInfo;
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    creating instance for simulation    //////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public Food() {
		super();
		setBaseSimObjEnum(EnumBaseSimObj.Food);
	}
	
	protected List<State> createAddOnStates() {
	
		List<State> result = super.createAddOnStates();
		
		this.stateEatable = (StateEatable) getInitState(StateEatable.class.getName());
		result.add(this.stateEatable);
		
		return result;
	
	}
	
	@Override
	protected final int getLexemIdLowPart() {
		return Mapping_Food2LexemIDLowerPart.getInstance().get(belongsTo);
	}

	/**
	 * Food can be perceived and eaten and has an appearance and a composition, as the apple
	 * (2026-11-23, was null here; Apple and Banana still decide themselves).
	 */
	@Override
	protected State getInitState(String stateClassName) {

		if (stateClassName.equals(StatePerceptible.class.getName())) {
			return new StatePerceptible(this);
		}
		else if (stateClassName.equals(StateEatable.class.getName())) {
			return new StateEatable(this);
		}
		else if (stateClassName.equals(StateAppearance.class.getName())) {
			return new StateAppearance(this);
		}
		else if (stateClassName.equals(StateComposition.class.getName())) {
			return new StateComposition(this);
		}

		return State.getObjectNothing();
	}

///////////////////////////////////////////////////////////////////////////////////////////
//////////checking whether the class belongs to a sub set of classes //////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public boolean checkObjectBelongsToGroup(int groupNumberSuffix) {
		// TODO Auto-generated method stub
		return false;
	}
	
	protected boolean isInterface(String nameInterface) {
		
		boolean result;
		
		switch (nameInterface) {
			case IEatable.NAME:
				result = (this instanceof IEatable);
				break;
			default:
				result = super.isInterface(nameInterface);

		}
		
		return  result;
	
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////      implementing IEatable     ////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	public  NutrientSet getNutrientSet() {
		return getObjectRequester().requestNutrientSet(token, this.stateEatable.getPropertyFromMethod(token, StateEatable.METHODNAME_GET_NUTRIENT_SET, StateEatable.VALUENAME_NUTRIENT_SET), this);
	}
	
	public  TasteSet getTasteSet() {
		return getObjectRequester().requestTasteSet(token, this.stateEatable.getPropertyFromMethod(token, StateEatable.METHODNAME_GET_TASTE_SET, StateEatable.VALUENAME_TASTE_SET), this);
	}


}
