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

	@Override
	protected State getInitState(String stateClassName) {
		// TODO Auto-generated method stub
		return null;
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
		return objectRequester.requestNutrientSet(token, this.stateEatable.getPropertyFromMethod(token, StateEatable.METHODNAME_GET_NUTRIENT_SET, StateEatable.VALUENAME_NUTRIENT_SET), this);
	}
	
	public  TasteSet getTasteSet() {
		return objectRequester.requestTasteSet(token, this.stateEatable.getPropertyFromMethod(token, StateEatable.METHODNAME_GET_TASTE_SET, StateEatable.VALUENAME_TASTE_SET), this);
	}


}
