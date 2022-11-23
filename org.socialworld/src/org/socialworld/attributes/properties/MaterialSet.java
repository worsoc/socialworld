package org.socialworld.attributes.properties;

import java.util.List;

import org.socialworld.attributes.PropertyName;
import org.socialworld.attributes.PropertyProtection;
import org.socialworld.attributes.SimProperty;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.tools.StringTupel;

public class MaterialSet extends PropPortionSet {

///////////////////////////////////////////////////////////////////////////////////////////
//////////////////static instance for meta information    ///////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	private static StringTupel[] propertiesMetaInfos = new StringTupel[]{
		new StringTupel(new String[] {"Material", PropertyName.compositionSet_mainMaterial.name(), PropertyName.compositionSet_mainMaterial.toString()}),
		new StringTupel(new String[] {Type.valueList.getIndexWithSWTPraefix(), PropertyName.compositionSet_materials.name(), PropertyName.compositionSet_materials.toString()})
	};
	
	public static List<StringTupel> getPropertiesMetaInfos() {
		List<StringTupel> listOfPropertyMetaInfo = SimProperty.getPropertiesMetaInfos();
		for (int indexAdd = 0; indexAdd < propertiesMetaInfos.length; indexAdd++) {
			listOfPropertyMetaInfo.add(propertiesMetaInfos[indexAdd]);
		}
		return listOfPropertyMetaInfo;
	}

///////////////////////////////////////////////////////////////////////////////////////////
//////////////////creating instance for simulation    ///////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////


	protected  MaterialSet(PropPortionSet original, PropertyProtection protectionOriginal, SimulationCluster cluster ) {
		super(original, protectionOriginal, cluster);
	}
	
	public  MaterialSet() {
		super();
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    ISavedValues  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public SimProperty copyForProperty(SimulationCluster cluster) {
		return new MaterialSet(this, getPropertyProtection(), cluster);
	}
	
	@Override
	public ValueProperty getProperty(SimulationCluster cluster, PropertyName propName, String valueName) {
		switch (propName) {
		case compositionSet_mainMaterial:
			return new ValueProperty(Type.object, valueName, getMain());
		case compositionSet_materials:
			return new ValueProperty(Type.valueList, valueName, getObjectsAsValueArrayList());
		default:
			return ValueProperty.getInvalid();
		}
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    MaterialSet  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	
	

}
