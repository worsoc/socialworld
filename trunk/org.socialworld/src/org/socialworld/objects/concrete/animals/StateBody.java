package org.socialworld.objects.concrete.animals;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.socialworld.attributes.ISavedValues;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.objects.State;
import org.socialworld.tools.Generation;
import org.socialworld.tools.StringPair;

public class StateBody extends State {

	public static final String VALUENAME_FACE_COLOUR = "faceColour";
	public static final String VALUENAME_HAIR_COLOUR = "hairColour";

	public static final String METHODNAME_GET_FACE_COLOUR = "getFaceColour";
	public static final String METHODNAME_GET_HAIR_COLOUR = "getHairColour";

	///////////////////////////////////////////////////////////////////////////////////////////
	//////////////////static instance for meta information    ///////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////

	private static StateBody singletonDummyForGenerationTools;
	private static List<StringPair> listOfPropertyMetaInfo;
	private boolean listOfPropertyMetaInfoIsFilled = false;
	private static StringPair[] propertiesMetaInfos = new StringPair[]{};
	
	public static StateBody getInstance(Generation calledFromGeneration) {
		if (singletonDummyForGenerationTools == null) {
			singletonDummyForGenerationTools = new StateBody(calledFromGeneration);
		}
		return singletonDummyForGenerationTools;
	}
	
	private StateBody(Generation calledFromGeneration) 
	{
		
	}

	///////////////////////////////////////////////////////////////////////////////////////////
	////////////////// creating instance for simulation    ///////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	public StateBody() {
		super();
	}

	protected  void initPropertyName() {
		setPropertyName(PropertyName.stateBody);
	}

	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////  implementing  ISavedValues  ////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public ISavedValues copyForProperty(SimulationCluster cluster) {
		return null;
	}

	@Override
	public ValueProperty getProperty(SimulationCluster cluster, PropertyName propName, String valueName) {
		return null;
	}

	@Override
	protected void setProperty(PropertyName propName, ValueProperty property) {

	}

	public List<StringPair> getPropertiesMetaInfos() {
		if (!listOfPropertyMetaInfoIsFilled) {
			List<StringPair> result = super.getPropertiesMetaInfos();
			for (int indexAdd = 0; indexAdd < propertiesMetaInfos.length; indexAdd++) {
				result.add(propertiesMetaInfos[indexAdd]);
			}
			listOfPropertyMetaInfo = result;
			listOfPropertyMetaInfoIsFilled = true;
		}
		return new ArrayList<StringPair>(listOfPropertyMetaInfo);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////  implementing  StateBody methods  ////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	protected ValueProperty getFaceColour() {
		return new ValueProperty(Type.integer,  VALUENAME_FACE_COLOUR, Color.PINK.getRGB());
	}

	protected ValueProperty getHairColour() {
		return new ValueProperty(Type.integer,  VALUENAME_HAIR_COLOUR, Color.BLACK.getRGB());
	}
	
}
