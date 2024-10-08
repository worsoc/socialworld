package org.socialworld.objects.concrete.animals;

import java.util.List;

import org.socialworld.attributes.ISavedValue;
import org.socialworld.attributes.PropertyName;
import org.socialworld.attributes.properties.Colour;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.core.IAccessToken;
import org.socialworld.core.ReturnCode;
import org.socialworld.knowledge.KnowledgeFact_Criterion;
import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.State;
import org.socialworld.tools.StringTupel;

public class StateBody extends State {

	public static final String VALUENAME_FACE_COLOUR = "faceColour";
	public static final String VALUENAME_HAIR_COLOUR = "hairColour";

	public static final String METHODNAME_GET_FACE_COLOUR = "getFaceColour";
	public static final String METHODNAME_GET_HAIR_COLOUR = "getHairColour";

	///////////////////////////////////////////////////////////////////////////////////////////
	//////////////////static instance for meta information    ///////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////

	private static StringTupel[] propertiesMetaInfos = new StringTupel[]{};
	private static StringTupel[] propMethodsMetaInfos = new StringTupel[] {
			new StringTupel(new String[] {"Colour",METHODNAME_GET_FACE_COLOUR, VALUENAME_FACE_COLOUR}),
			new StringTupel(new String[] {"Colour",METHODNAME_GET_HAIR_COLOUR, VALUENAME_HAIR_COLOUR})
			} ;
	
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
			KnowledgeFact_Criterion.colour
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

	private static StateBody objectNothing;
	
	public static StateBody getObjectNothing() {
		if (objectNothing == null) {
			objectNothing = new StateBody();
			objectNothing.setToObjectNothing();
		}
		return objectNothing;
	}
	
	public boolean checkIsObjectNothing() {
		return (this == objectNothing);
	}

	private StateBody() {
	
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////
	////////////////// creating instance for simulation    ///////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	public StateBody(SimulationObject object) 
	{
		super(object);
	}

	protected  ReturnCode init() {
		
		return ReturnCode.todo;
	}

	protected  void initPropertyName() {
		setPropertyName(PropertyName.stateBody);
	}

	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////  implementing  ISavedValue  ////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public ISavedValue copyForProperty(IAccessToken token) {
		return null;
	}

	@Override
	public ValueProperty getProperty(IAccessToken token, PropertyName propName, String valueName) {
		return null;
	}

	@Override
	protected void setProperty(PropertyName propName, ValueProperty property) {

	}

	
	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////  implementing  StateBody methods  ////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	protected ValueProperty getFaceColour() {
		return new ValueProperty(Type.enumProp,  VALUENAME_FACE_COLOUR, Colour.black);
	}

	protected ValueProperty getHairColour() {
		return new ValueProperty(Type.enumProp,  VALUENAME_HAIR_COLOUR, Colour.silver);
	}
	
}
