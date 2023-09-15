package org.socialworld.objects.concrete.animals;

import java.util.List;

import org.socialworld.objects.GroupingOfSimulationObjects;
import org.socialworld.objects.State;
import org.socialworld.objects.enums.EnumMammal;
import org.socialworld.tools.StringTupel;

public abstract class Primate extends Mammal {

	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////meta information    ////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	private static StringTupel[] propertiesMetaInfos = new StringTupel[]{
	} ;
	
	public static List<StringTupel> getPropertiesMetaInfos() {
		List<StringTupel> listOfPropertyMetaInfo = Mammal.getPropertiesMetaInfos();
		for (int indexAdd = 0; indexAdd < propertiesMetaInfos.length; indexAdd++) {
			listOfPropertyMetaInfo.add(propertiesMetaInfos[indexAdd]);
		}
		return listOfPropertyMetaInfo;
	}

	public static int getLexemIdLowerValue() {
		return GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_PRIMATE;
	}

	@Override
	public boolean checkObjectBelongsToGroup(int groupNumberSuffix) {
		// TODO Auto-generated method stub
		return false;
	}


	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////    creating instance for simulation    //////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	public Primate() {
		super();
		belongsTo = EnumMammal.Primate;
	}
	
	

	@Override
	protected State getInitState(String stateClassName) {
		// TODO Auto-generated method stub
		return null;
	}
	

	
	protected List<State> createAddOnStates() {
	
		List<State> result = super.createAddOnStates();
		
		return result;
	
	}

}
