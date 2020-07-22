package org.socialworld.objects.concrete;


import org.socialworld.attributes.ISavedValues;
import org.socialworld.attributes.PropertyName;
import org.socialworld.attributes.properties.Material;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.objects.State;

public class StateComposition extends State {

	public static final String VALUENAME_MAIN_MATERIAL = "mainColour";

	public static final String METHODNAME_GET_MAIN_MATERIAL = "getMainMaterial";
	
	@Override
	public ISavedValues copyForProperty(SimulationCluster cluster) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ValueProperty getProperty(SimulationCluster cluster, PropertyName propName, String valueName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setProperty(PropertyName propName, ValueProperty property) {
		// TODO Auto-generated method stub

	}

	public ValueProperty getMainMaterial() {
		return new ValueProperty(Type.integer, VALUENAME_MAIN_MATERIAL, Material.leather.getIndex());
	}
}
