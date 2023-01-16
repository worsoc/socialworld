package org.socialworld.objects.concrete.animals;

import java.util.List;

import org.socialworld.attributes.Direction;
import org.socialworld.attributes.ISavedValues;
import org.socialworld.attributes.ISimProperty;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.core.ReturnCode;
import org.socialworld.knowledge.KnowledgeFact_Criterion;
import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.State;
import org.socialworld.tools.StringTupel;

public class StateFlying extends State {

	private double widthWings;
	private int numberWings;
	private Direction directionFly;
	
	///////////////////////////////////////////////////////////////////////////////////////////
	//////////////////static instance for meta information    ///////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////

	private static StringTupel[] propertiesMetaInfos = new StringTupel[]{
			new StringTupel(new String[] {Type.floatingpoint.getIndexWithSWTPraefix(),PropertyName.stateFlying_widthWings.name(),PropertyName.stateFlying_widthWings.toString()}),
			new StringTupel(new String[] {Type.integer.getIndexWithSWTPraefix(), PropertyName.stateFlying_numberWings.name(), PropertyName.stateFlying_numberWings.toString()}),
			new StringTupel(new String[] {"Direction", PropertyName.stateFlying_directionFly.name(), PropertyName.stateFlying_directionFly.toString()})
			} ;
	
	private static StringTupel[] propMethodsMetaInfos = new StringTupel[] {} ;
	
	public static List<StringTupel> getPropertiesMetaInfos() {
		List<StringTupel> listOfPropertyMetaInfo = State.getPropertiesMetaInfos();
		for (int indexAdd = 0; indexAdd < propertiesMetaInfos.length; indexAdd++) {
			listOfPropertyMetaInfo.add(propertiesMetaInfos[indexAdd]);
		}
		return listOfPropertyMetaInfo;
	}

	public static List<StringTupel> getPropMethodsMetaInfos() {
		List<StringTupel> listOfPropMethodMetaInfo = State.getPropMethodsMetaInfos();
		for (int indexAdd = 0; indexAdd < propMethodsMetaInfos.length; indexAdd++) {
			listOfPropMethodMetaInfo.add(propMethodsMetaInfos[indexAdd]);
		}
		return listOfPropMethodMetaInfo;
	}
	
	private static KnowledgeFact_Criterion[] resultingKFCs = new KnowledgeFact_Criterion[] {
		};

	public static List<KnowledgeFact_Criterion> getResultingKFCs() {
		List<KnowledgeFact_Criterion> listOfResultingKFCs = State.getResultingKFCs();
		for (int indexAdd = 0; indexAdd < resultingKFCs.length; indexAdd++) {
		listOfResultingKFCs.add(resultingKFCs[indexAdd]);
		}
		return listOfResultingKFCs;
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////
	///////////// object nothing (abstract method from ISimProperty)    ///////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////

	private static StateFlying objectNothing;
	
	public  ISimProperty getObjectNothing() {
		if (objectNothing == null) {
			objectNothing = new StateFlying();
			objectNothing.setToObjectNothing();
		}
		return objectNothing;
	}
	
	public boolean checkIsObjectNothing() {
		return (this == objectNothing);
	}

	private StateFlying() {
	
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////
	////////////////// creating instance for simulation    ///////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	public StateFlying(SimulationObject object) 
	{
		super(object);
	}

	protected  ReturnCode init() {
		return ReturnCode.todo;

	}
	
	@Override
	protected void initPropertyName() {
		setPropertyName(PropertyName.stateFlying);
	}

	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////  implementing  ISavedValues  ////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	
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

}
