package org.socialworld.tools.pct;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.calculation.Value;
import org.socialworld.calculation.expressions.CreateKnowledgeElementExpression;
import org.socialworld.calculation.expressions.GetValue;
import org.socialworld.knowledge.KnowledgeFact_Criterion;
import org.socialworld.tools.Generation;
import org.socialworld.tools.StringPair;

public class PerceptionGeneration extends Generation{

	private static final int PERCEPTION_GENERATION_COUNT_VARIANTS = 4;
	private static final int PERCEPTION_GENERATION_STOP_DOT_NOTATION_MODULO_DIVISOR = 4;
	
	private String pd_subject_causer;
	private String pd_subject_target;

	private String pd_source;

	private String pd_value_praefix;
	private String pd_kfc_praefix;
	private String pd_property_praefix;
	
	
	PerceptionGeneration() {
		pd_subject_causer = CreateKnowledgeElementExpression.LABEL_SUBJECT +
							GetValue.getValue(Value.VALUE_BY_NAME_EVENT_PARAMS) + "." +
							GetValue.getValue(Value.VALUE_BY_NAME_EVENT_CAUSER) + ";";
		
		pd_subject_target = CreateKnowledgeElementExpression.LABEL_SUBJECT +
							GetValue.getValue(Value.VALUE_BY_NAME_EVENT_PARAMS) + "." +
							GetValue.getValue(Value.VALUE_BY_NAME_EVENT_TARGET) + ";";

		pd_source = CreateKnowledgeElementExpression.LABEL_KNOWLEDGESOURCETYPE + "1," +	// always source type MYSELF
					CreateKnowledgeElementExpression.LABEL_KNOWLEDGESOURCE + 
					GetValue.getValue(Value.VALUE_NAME_KNOWLEDGE_SOURCE_MYSELF) + ")," ;
		
		pd_value_praefix = CreateKnowledgeElementExpression.LABEL_KNOWLEDGEVALUE;
		pd_kfc_praefix = CreateKnowledgeElementExpression.LABEL_KNOWLEDGEFACTCRITERION;
		pd_property_praefix = CreateKnowledgeElementExpression.LABEL_KNOWLEDGEPROPERTY;
		
	}
	
	public String generatePerceptionDescription(String someCharacters) {
		
		String perceptionDescription = "";
		int countChars = someCharacters.length();
		if (countChars > 4) {

			byte[] bytes;
			bytes = someCharacters.getBytes();

			NextDotElement nextDotElement = null;
			String lastClassName = "SimulationObject";

			boolean newSourceElement = false;  // new source and KFC number
			
			boolean newPropertyForKFC = false; // new property line for KFC
			int kfc;
			List<Integer> usedKFCNumberSoFar = new ArrayList<Integer>();
			
			switch (bytes[0] % PERCEPTION_GENERATION_COUNT_VARIANTS) {
				case 0:
					// subject causer, source myself, knowledge value
					perceptionDescription = pd_subject_causer + pd_source + pd_value_praefix;
					perceptionDescription += GetValue.getValue(Value.VALUE_BY_NAME_EVENT_PARAMS) + "." +
							GetValue.getValue(Value.VALUE_BY_NAME_EVENT_CAUSER);
					for (int index = 1; index < bytes.length; index++) {
						if ((bytes[index] % PERCEPTION_GENERATION_STOP_DOT_NOTATION_MODULO_DIVISOR) == 0) {
							break;
						}
						else {
							nextDotElement = getNextDotElement(lastClassName, bytes[index]);
							if (nextDotElement.nextDotElement.length() > 0) {
								perceptionDescription += ".";
								perceptionDescription += nextDotElement.nextDotElement;
								lastClassName = nextDotElement.className;
							}
							else
								break;
						}
					}
					break;
				case 1:
					// subject target, source myself, knowledge value
					perceptionDescription = pd_subject_target + pd_source + pd_value_praefix;
					perceptionDescription += GetValue.getValue(Value.VALUE_BY_NAME_EVENT_PARAMS) + "." +
							GetValue.getValue(Value.VALUE_BY_NAME_EVENT_TARGET);
					for (int index = 1; index < bytes.length; index++) {
						if ((bytes[index] % PERCEPTION_GENERATION_STOP_DOT_NOTATION_MODULO_DIVISOR) == 0) {
							break;
						}
						else {
							nextDotElement = getNextDotElement(lastClassName, bytes[index]);
							if (nextDotElement.nextDotElement.length() > 0) {
								perceptionDescription += ".";
								perceptionDescription += nextDotElement.nextDotElement;
								lastClassName = nextDotElement.className;
							}
							else
								break;
						}
					}
					break;
				case 2:
					// subject causer, source myself, knowledge property for kfc
					perceptionDescription = pd_subject_causer ;
					newSourceElement = true;
					for (int index = 1; index < bytes.length; index++) {
						if (newSourceElement == true || (bytes[index] % PERCEPTION_GENERATION_STOP_DOT_NOTATION_MODULO_DIVISOR) == 0) {
							kfc = getKFCNumber(index, bytes[index], usedKFCNumberSoFar);
							if (kfc == -1) {
								// try getKFCNumber for next index again
								newSourceElement = true;
								continue;
							}
							if (index > 1) {
								// it's the beginning of a further source-KFC-block
								// --> finish the last source-KFC-block
								perceptionDescription += ";" ;
							}
							perceptionDescription += pd_source + pd_kfc_praefix + kfc + "," + pd_property_praefix;
							perceptionDescription += GetValue.getValue(Value.VALUE_BY_NAME_EVENT_PARAMS) + "." +
									GetValue.getValue(Value.VALUE_BY_NAME_EVENT_CAUSER);
							lastClassName = "SimulationObject";
							newPropertyForKFC = false;
							newSourceElement = false;
						}
						else if (newPropertyForKFC == true || (bytes[index] % PERCEPTION_GENERATION_STOP_DOT_NOTATION_MODULO_DIVISOR) == 1) {
							perceptionDescription += "," + pd_property_praefix;
							perceptionDescription += GetValue.getValue(Value.VALUE_BY_NAME_EVENT_PARAMS) + "." +
									GetValue.getValue(Value.VALUE_BY_NAME_EVENT_CAUSER);
							lastClassName = "SimulationObject";
							newPropertyForKFC = false;
						}
						nextDotElement = getNextDotElement(lastClassName, bytes[index]);
						if (nextDotElement.nextDotElement.length() > 0) {
							perceptionDescription += ".";
							perceptionDescription += nextDotElement.nextDotElement;
							lastClassName = nextDotElement.className;
						}
						else {
							newPropertyForKFC = true;
						}
					}
					break;
				case 3:
					// subject causer, source myself, knowledge property for kfc
					perceptionDescription = pd_subject_target ;
					newSourceElement = true;
					for (int index = 1; index < bytes.length; index++) {
						if (newSourceElement == true || (bytes[index] % PERCEPTION_GENERATION_STOP_DOT_NOTATION_MODULO_DIVISOR) == 0) {
							kfc = getKFCNumber(index, bytes[index], usedKFCNumberSoFar);
							if (kfc == -1) {
								// try getKFCNumber for next index again
								newSourceElement = true;
								continue;
							}
							if (index > 1) {
								// it's the beginning of a further source-KFC-block
								// --> finish the last source-KFC-block
								perceptionDescription += ";" ;
							}
							perceptionDescription += pd_source + pd_kfc_praefix + kfc + "," + pd_property_praefix;
							perceptionDescription += GetValue.getValue(Value.VALUE_BY_NAME_EVENT_PARAMS) + "." +
									GetValue.getValue(Value.VALUE_BY_NAME_EVENT_TARGET);
							lastClassName = "SimulationObject";
							newPropertyForKFC = false;
							newSourceElement = false;
						}
						else if (newPropertyForKFC == true || (bytes[index] % PERCEPTION_GENERATION_STOP_DOT_NOTATION_MODULO_DIVISOR) == 1) {
							perceptionDescription += "," + pd_property_praefix;
							perceptionDescription += GetValue.getValue(Value.VALUE_BY_NAME_EVENT_PARAMS) + "." +
									GetValue.getValue(Value.VALUE_BY_NAME_EVENT_TARGET);
							lastClassName = "SimulationObject";
							newPropertyForKFC = false;
						}
						nextDotElement = getNextDotElement(lastClassName, bytes[index]);
						if (nextDotElement.nextDotElement.length() > 0) {
							perceptionDescription += ".";
							perceptionDescription += nextDotElement.nextDotElement;
							lastClassName = nextDotElement.className;
						}
						else {
							newPropertyForKFC = true;
						}
					}
					break;
			}
		}
		return perceptionDescription;
	}
	
	private NextDotElement getNextDotElement(String lastClassName, byte selection) {
		
		NextDotElement result = new NextDotElement();
		
		StringPair selectedProperty = new StringPair("", "");
		StringPair selectedPropertyFromMethod = new StringPair("", "");
		
		List<StringPair> returnablePropertyTypes;
		List<StringPair> returnablePropertyFromMethodTypes;
		
		returnablePropertyTypes = simulationMetaInformation.getPropertiesMetaInfosForClass(lastClassName, this);
		returnablePropertyFromMethodTypes = simulationMetaInformation.getPropMethodsMetaInfosForClass(lastClassName, this);

		int selectProperty = -1;
		if (returnablePropertyTypes.size() > 0) {
			selectProperty = selection % returnablePropertyTypes.size();
			selectedProperty = returnablePropertyTypes.get(selectProperty);
		}
		int selectPropertyFromMethod = -1;
		if (returnablePropertyFromMethodTypes.size() > 0) {
			selectPropertyFromMethod = selection % returnablePropertyFromMethodTypes.size();
			selectedPropertyFromMethod = returnablePropertyFromMethodTypes.get(selectPropertyFromMethod);
		}

		if (selectProperty >= 0 && selectPropertyFromMethod >= 0) {
			int choosePropVSPropFromMethod = selection % 2;
			if (choosePropVSPropFromMethod == 0) {
				result.className = selectedProperty.getLeft();
				result.nextDotElement = GetValue.getProperty(selectedProperty.getRight());
			}
			else {
				result.className = selectedPropertyFromMethod.getLeft();
				result.nextDotElement = GetValue.getFctValue(selectedPropertyFromMethod.getRight());
			}
		}
		else if (selectProperty >= 0) {
			result.className = selectedProperty.getLeft();
			result.nextDotElement = GetValue.getProperty(selectedProperty.getRight());
		}
		else if (selectPropertyFromMethod >= 0) {
			result.className = selectedPropertyFromMethod.getLeft();
			result.nextDotElement = GetValue.getFctValue(selectedPropertyFromMethod.getRight());
		}
		
		return result;
	}
	
	private int getKFCNumber(int index, byte value, List<Integer> usedKFCNumberSoFar) {
		int big = index * value;
		int choose;
		choose = big % KnowledgeFact_Criterion.NUMBER_OF_KNOWLEDGE_FACT_CRITERION;
		if (usedKFCNumberSoFar.contains(choose)) {
			return -1;
		}
		else {
			usedKFCNumberSoFar.add(choose);
			return choose;
		}
	}
	
	private class NextDotElement {
		String nextDotElement = "";
		String className = "";
	}
	
}
