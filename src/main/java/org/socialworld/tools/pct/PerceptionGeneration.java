package org.socialworld.tools.pct;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.calculation.Value;
import org.socialworld.calculation.expressions.CreateKnowledgeElementExpression;
import org.socialworld.calculation.expressions.GetValue;
import org.socialworld.knowledge.KnowledgeFact_Type;
import org.socialworld.knowledge.KnowledgeFact_Criterion;
import org.socialworld.objects.GroupingOfSimulationObjects;
import org.socialworld.tools.Generation;
import org.socialworld.tools.SimulationMetaInformation;
import org.socialworld.tools.StringTupel;

public class PerceptionGeneration extends Generation{

	private static final int PERCEPTION_GENERATION_COUNT_VARIANTS = 4;
	private static final int PERCEPTION_GENERATION_STOP_DOT_NOTATION_MODULO_DIVISOR = 4;
	
	private String pd_subject_causer;
	private String pd_subject_target;

	private String pd_source;

	private String pd_value_praefix;
	private String pd_property_praefix;
	
	private String placeholder_valueName = "PLACEHOLDER_VALUENAME";
	private String placeholder_className = "PLACEHOLDER_CLASSNAME";
	
	public static final String CHAINED_NAME_SEPARATOR = ".";
	
	
	private List<Integer> allGroupingNumbers; 

	List<String> dotElementConcats;
	
	PerceptionGeneration() {
		pd_subject_causer = CreateKnowledgeElementExpression.LABEL_SUBJECT + ":" +
							GetValue.getValue(Value.VALUE_BY_NAME_EVENT_PARAMS) + "." +
							GetValue.getValue(Value.VALUE_BY_NAME_EVENT_CAUSER) + ";";
		
		pd_subject_target = CreateKnowledgeElementExpression.LABEL_SUBJECT + ":" +
							GetValue.getValue(Value.VALUE_BY_NAME_EVENT_PARAMS) + "." +
							GetValue.getValue(Value.VALUE_BY_NAME_EVENT_TARGET) + ";";

		pd_source = CreateKnowledgeElementExpression.LABEL_KNOWLEDGESOURCETYPE + ":1&" +	// always source type MYSELF
					CreateKnowledgeElementExpression.LABEL_KNOWLEDGESOURCE + ":" +
					GetValue.getValue(Value.VALUE_NAME_KNOWLEDGE_SOURCE_MYSELF) + ";" ;
		
		pd_value_praefix = CreateKnowledgeElementExpression.LABEL_KNOWLEDGEVALUE;
		pd_property_praefix = CreateKnowledgeElementExpression.LABEL_KNOWLEDGEPROPERTY ;

		

		initArrayAllGroupingNumbers();
	}
	

	public List<String> generateAllPerceptionKnowledgeAtomDescriptions() {

		List<String> allDescriptions = new ArrayList<String>();
		List<String> descriptionsKnowledgeValue;
		List<String> descriptionsKnowledgeProperty;
		
		for (KnowledgeFact_Type k_a_t : KnowledgeFact_Type.values()) {
			
			switch (k_a_t) {
			case value: 
				//descriptionsKnowledgeValue = generateAllPerceptionKnowledgeValueDescriptions();
				//allDescriptions.addAll(descriptionsKnowledgeValue);
				break;
			case property: 
				//descriptionsKnowledgeProperty = generateAllPerceptionKnowledgePropertyDescriptions();
				//allDescriptions.addAll(descriptionsKnowledgeProperty);
				break;
			//default:
			}
		}
		
		return allDescriptions;
		
	}
	
	
	private List<String> generateAllPerceptionKnowledgeValueDescriptions() {
		
		List<String> allDescriptions = new ArrayList<String>();
		
		String lastClassName = "SimulationObject";
		
		int kfc = -1;
		
		String descriptionTemplate = "";

		descriptionTemplate = pd_value_praefix + "(" + placeholder_className +  placeholder_valueName + "):";
		descriptionTemplate += GetValue.getValue(Value.VALUE_BY_NAME_EVENT_PARAMS) + "." +
				GetValue.getValue(Value.VALUE_BY_NAME_EVENT_CAUSER) + "#" + "<GETVALUE_GETISELEMENTOF_NUMBER>";
		
		String description;
		String knowledgeAtomName = "";
		List<String> ignoreNDEs = new ArrayList<String> ();
		
		for (int indexGN = 0; indexGN < allGroupingNumbers.size();  indexGN++ ) {
			
			dotElementConcats = new ArrayList<String>();
			
			lastClassName = GroupingOfSimulationObjects.PRAEFIX_SIMOBJECT_GROUPING_NUMBER + allGroupingNumbers.get(indexGN);
			
			description = descriptionTemplate.replaceFirst("<GETVALUE_GETISELEMENTOF_NUMBER>", 
					GetValue.getIsElementOf(allGroupingNumbers.get(indexGN)));

			knowledgeAtomName = "";
			
			getDotElementConcats(0, lastClassName, kfc, description, knowledgeAtomName, ignoreNDEs);
			
			allDescriptions.addAll(dotElementConcats);
			
		}
		// "<GETVALUE_GETISELEMENTOF_NUMBER>" = GetValue.getIsElementOf(indexGroupingOfSimulationObjects)
		
		return allDescriptions;
	}
	
	
	private List<String> generateAllPerceptionKnowledgePropertyDescriptions() {
		
		List<String> allDescriptions = new ArrayList<String>();
		
		String lastClassName = "SimulationObject";
		
//		boolean newPropertyForKFC = false; // new property line for KFC
//		List<Integer> usedKFCNumberSoFar = new ArrayList<Integer>();
		
		String descriptionTemplate = "";
		String knowledgeAtomName = "";

		
		for (KnowledgeFact_Criterion criterion : KnowledgeFact_Criterion.values()) {
		
			int kfc = criterion.getIndex();
			
			descriptionTemplate =   pd_property_praefix + "(" + kfc +  placeholder_valueName + "):";
			descriptionTemplate += GetValue.getValue(Value.VALUE_BY_NAME_EVENT_PARAMS) + "." +
					GetValue.getValue(Value.VALUE_BY_NAME_EVENT_CAUSER) + "#" + "<GETVALUE_GETISELEMENTOF_NUMBER>";
		
			String description;
			List<String> ignoreNDEs = new ArrayList<String> ();
			
			for (int indexGN = 0; indexGN < allGroupingNumbers.size();  indexGN++ ) {
				
				dotElementConcats = new ArrayList<String>();
				
				lastClassName = GroupingOfSimulationObjects.PRAEFIX_SIMOBJECT_GROUPING_NUMBER + allGroupingNumbers.get(indexGN);
				
				description = descriptionTemplate.replaceFirst("<GETVALUE_GETISELEMENTOF_NUMBER>", 
						GetValue.getIsElementOf(allGroupingNumbers.get(indexGN)));
				
				knowledgeAtomName = "";
				
				getDotElementConcats(0, lastClassName, kfc, description, knowledgeAtomName, ignoreNDEs);
				
				allDescriptions.addAll(dotElementConcats);
				
			}
		
		}
		// "<GETVALUE_GETISELEMENTOF_NUMBER>" = GetValue.getIsElementOf(indexGroupingOfSimulationObjects)
		
		return allDescriptions;
		
	}
	
	
	
	private void getDotElementConcats(
			int depth,  String lastClassName, int nrKFC, String dotElementConcat, String chainedName, List<String> ignoreNDEs) {
		
		String nextDepthDotElementConcat;
		String nextDepthChainedName;

		List<NextDotElement> nextDotElements;
		boolean ignore;
		
		List<String> ignoreNDEsNextDepth;
		
		nextDotElements = getNextDotElements(lastClassName, nrKFC);
		
		if (nextDotElements.size()  > 0)  {
			
			
			for (NextDotElement nde :  nextDotElements) {
				
				// checking for ignoring next dot element
				ignore = false;
				for (String elem : ignoreNDEs) {
					if (nde.nextDotElement.equals(elem)) {
						ignore = true;
					}
				}
				if (ignore) continue;
				
				nextDepthDotElementConcat = dotElementConcat + "." + nde.nextDotElement;
				
				if (nde.knowledgeAtomNamePart.isEmpty()) {
					nextDepthChainedName = chainedName;
				}
				else {
					if (chainedName.isEmpty()) {
						nextDepthChainedName = nde.knowledgeAtomNamePart;
					}
					else {
						nextDepthChainedName = chainedName + CHAINED_NAME_SEPARATOR + nde.knowledgeAtomNamePart;
					}
				}
				
				ignoreNDEsNextDepth = new ArrayList<String>();
				ignoreNDEsNextDepth.addAll(ignoreNDEs);
				ignoreNDEsNextDepth.add(nde.nextDotElement);
				
				getDotElementConcats(depth + 1, nde.className, nrKFC, nextDepthDotElementConcat, nextDepthChainedName, ignoreNDEsNextDepth);
				
			}

		}
		else {
			
			// add Type name to KVal
			if (nrKFC == -1) {
				dotElementConcat = dotElementConcat.replaceFirst(	placeholder_className,	lastClassName);
			}
	
			if (chainedName.length() > 0) {
				dotElementConcat = dotElementConcat.replaceFirst(	placeholder_valueName,	"," + 	chainedName);
			}
			else {
				dotElementConcat = dotElementConcat.replaceFirst(	placeholder_valueName,	"");
			}
			
			
			//System.out.println(dotElementConcat);
			dotElementConcats.add(dotElementConcat);
		}
		
	}
	
	private List<NextDotElement> getNextDotElements(String lastClassName, int nrKFC) {
		
		List<NextDotElement> nextDotElements = new ArrayList<NextDotElement>();
		NextDotElement nde;
		
		StringTupel selectedProperty = new StringTupel("", "");
		StringTupel selectedPropertyFromMethod = new StringTupel("", "");
		
		List<StringTupel> returnablePropertyTypes;
		List<StringTupel> returnablePropertyFromMethodTypes;
		
		String propertyClassName;
		String knowledgeAtomNamePart;
		
		returnablePropertyTypes = simulationMetaInformation.getPropertiesMetaInfosForClass(lastClassName);
		returnablePropertyFromMethodTypes = simulationMetaInformation.getPropMethodsMetaInfosForClass(lastClassName);

		KnowledgeFact_Criterion kfc = null;
		if (nrKFC >= 0) {
			kfc = KnowledgeFact_Criterion.getName(nrKFC);
		}
		
		String nextDotElement;
		boolean addToResult;
		
		for (int indexRPT = 0; indexRPT < returnablePropertyTypes.size(); indexRPT++) {
			
			addToResult = true;
			selectedProperty = returnablePropertyTypes.get(indexRPT);
			
			if (selectedProperty.getLeft().equals(SimulationMetaInformation.CLASSNAME_SWT_SIMOBJECT)) {
				
				nextDotElement = GetValue.getProperty(selectedProperty.getRight());
				knowledgeAtomNamePart = selectedProperty.get(2);
				
				for (int indexGN = 0; indexGN < allGroupingNumbers.size();  indexGN++ ) {
					
					addToResult = true;
					propertyClassName = GroupingOfSimulationObjects.PRAEFIX_SIMOBJECT_GROUPING_NUMBER + allGroupingNumbers.get(indexGN);
					
					if (nrKFC >= 0) {

						if (simulationMetaInformation.checkClassForReturnableKFCs(propertyClassName, kfc) == false ) {
							addToResult = false;
						};
					}
					
					if (addToResult) {
						nde = new NextDotElement();
						nde.className = propertyClassName;
						nde.nextDotElement = nextDotElement + "#" + GetValue.getIsElementOf(allGroupingNumbers.get(indexGN));
						nde.knowledgeAtomNamePart = knowledgeAtomNamePart + CHAINED_NAME_SEPARATOR + GroupingOfSimulationObjects.PRAEFIX_SIMOBJECT_GROUPING_NUMBER + Integer.toString(allGroupingNumbers.get(indexGN));
						nextDotElements.add(nde);
					}
				}
			}
			else {
				
				propertyClassName = selectedProperty.getLeft();
				
				if (nrKFC >= 0) {
					if (simulationMetaInformation.checkClassForReturnableKFCs(propertyClassName, kfc) == false ) {
						addToResult = false;
					};
				}
				else {
					if (simulationMetaInformation.checkIsKFCPropertyOnly(propertyClassName) == true ) {
						addToResult = false;
					}
				}

				if (addToResult) {
					nde = new NextDotElement();
					nde.className = propertyClassName;
					nde.nextDotElement = GetValue.getProperty(selectedProperty.get(1));
					nde.knowledgeAtomNamePart = selectedProperty.get(2);
					nextDotElements.add(nde);
				}

			}
		}
		
		for (int indexRPFMT = 0; indexRPFMT < returnablePropertyFromMethodTypes.size(); indexRPFMT++) {
			
			addToResult = true;
			selectedPropertyFromMethod = returnablePropertyFromMethodTypes.get(indexRPFMT);
			
			if (selectedPropertyFromMethod.getLeft().equals(SimulationMetaInformation.CLASSNAME_SWT_SIMOBJECT)) {
				
				nextDotElement = GetValue.getFctValue(selectedPropertyFromMethod.getRight());
				
				for (int indexGN = 0; indexGN < allGroupingNumbers.size();  indexGN++ ) {
					
					addToResult = true;
					propertyClassName = GroupingOfSimulationObjects.PRAEFIX_SIMOBJECT_GROUPING_NUMBER + allGroupingNumbers.get(indexGN);
					
					if (nrKFC >= 0) {

						if (simulationMetaInformation.checkClassForReturnableKFCs(propertyClassName, kfc) == false ) {
							addToResult = false;
						};
						
					}
					
					if (addToResult) {
						nde = new NextDotElement();
						nde.className = GroupingOfSimulationObjects.PRAEFIX_SIMOBJECT_GROUPING_NUMBER + allGroupingNumbers.get(indexGN);
						nde.nextDotElement = nextDotElement + "#" + GetValue.getIsElementOf(allGroupingNumbers.get(indexGN));
						nde.knowledgeAtomNamePart = GroupingOfSimulationObjects.PRAEFIX_SIMOBJECT_GROUPING_NUMBER + Integer.toString(allGroupingNumbers.get(indexGN));
						nextDotElements.add(nde);
					}
					
				}
				
			}
			else {

				propertyClassName = selectedPropertyFromMethod.getLeft();
				
				if ( propertyClassName.equals(SimulationMetaInformation.CLASSNAME_ENUM_INDEX)) {
					
				}
				else {
					if (nrKFC >= 0) {
						if (simulationMetaInformation.checkClassForReturnableKFCs(propertyClassName, kfc) == false ) {
							addToResult = false;
						}
					}
					else {
						if (simulationMetaInformation.checkIsKFCPropertyOnly(propertyClassName) == true ) {
							addToResult = false;
						}
					}

				}
				
				if (addToResult) {
					nde = new NextDotElement();
					nde.className = propertyClassName;
					nde.nextDotElement = GetValue.getFctValue(selectedPropertyFromMethod.getRight());
					nde.knowledgeAtomNamePart = selectedPropertyFromMethod.get(2);
					nextDotElements.add(nde);
				}
			}
		}

		return nextDotElements;
		
	}
	
	
	public String generatePerceptionDescription(String someCharacters) {
		
		String perceptionDescription = "";
		int countChars = someCharacters.length();
		if (countChars > 4) {

			byte[] bytes;
			bytes = someCharacters.getBytes();

			NextDotElement nextDotElement = null;
			String lastClassName = "SimulationObject";
			int groupingNumber;
			

			boolean newKFCElement = false;  // new KFC number
			
			boolean newPropertyForKFC = false; // new property line for KFC
			int kfc = -1;
			List<Integer> usedKFCNumberSoFar = new ArrayList<Integer>();
			
			switch (bytes[0] % PERCEPTION_GENERATION_COUNT_VARIANTS) {
				case 0:
					// source myself, subject causer,  knowledge value
					perceptionDescription = pd_source + pd_subject_causer + pd_value_praefix + ":";
					perceptionDescription += GetValue.getValue(Value.VALUE_BY_NAME_EVENT_PARAMS) + "." +
							GetValue.getValue(Value.VALUE_BY_NAME_EVENT_CAUSER) + "#" + GetValue.getIsElementOf(getGroupingNumberForIndex(bytes[0]));
					for (int index = 1; index < bytes.length; index++) {
							nextDotElement = getNextDotElement(lastClassName, bytes[index], kfc);
							if (nextDotElement.nextDotElement.length() > 0) {
								perceptionDescription += ".";
								perceptionDescription += nextDotElement.nextDotElement;
								lastClassName = nextDotElement.className;
							}
							else
								break;
					}
					break;
				case 1:
					// source myself, subject target, knowledge value
					perceptionDescription = pd_source + pd_subject_target +  pd_value_praefix + ":";
					perceptionDescription += GetValue.getValue(Value.VALUE_BY_NAME_EVENT_PARAMS) + "." +
							GetValue.getValue(Value.VALUE_BY_NAME_EVENT_TARGET) + "#" + GetValue.getIsElementOf(getGroupingNumberForIndex(bytes[0]));
					for (int index = 1; index < bytes.length; index++) {
							nextDotElement = getNextDotElement(lastClassName, bytes[index], kfc);
							if (nextDotElement.nextDotElement.length() > 0) {
								perceptionDescription += ".";
								perceptionDescription += nextDotElement.nextDotElement;
								lastClassName = nextDotElement.className;
							}
							else
								break;
					}
					break;
				case 2:
					// source myself, subject causer,  knowledge property for kfc
					perceptionDescription = pd_source + pd_subject_causer  ;
					newKFCElement = true;
					for (int index = 1; index < bytes.length; index++) {
						if (newKFCElement == true ) {
							kfc = getKFCNumber(index, bytes[index], usedKFCNumberSoFar);
							if (kfc == -1) {
								// try getKFCNumber for next index again
								newKFCElement = true;
								continue;
							}
							if (index > 1) {
								// it's the beginning of a further KFC-block
								// --> finish the last KFC-block
								perceptionDescription += ";" ;
							}
							groupingNumber = getGroupingNumberForIndex(bytes[index]);
							perceptionDescription +=  pd_property_praefix + "(" + kfc + ",devValueName_" + index + "):";
							perceptionDescription += GetValue.getValue(Value.VALUE_BY_NAME_EVENT_PARAMS) + "." +
									GetValue.getValue(Value.VALUE_BY_NAME_EVENT_CAUSER) + "#" + GetValue.getIsElementOf(groupingNumber);
							lastClassName = GroupingOfSimulationObjects.PRAEFIX_SIMOBJECT_GROUPING_NUMBER + groupingNumber;
							newPropertyForKFC = false;
							newKFCElement = false;
						}
						else if (newPropertyForKFC == true ) {
							groupingNumber = getGroupingNumberForIndex(bytes[index]);
							perceptionDescription += "&" + pd_property_praefix + "(" + kfc + ",devValueName_" + index + "):";
							perceptionDescription += GetValue.getValue(Value.VALUE_BY_NAME_EVENT_PARAMS) + "." +
									GetValue.getValue(Value.VALUE_BY_NAME_EVENT_CAUSER) + "#" + GetValue.getIsElementOf(groupingNumber);
							lastClassName = GroupingOfSimulationObjects.PRAEFIX_SIMOBJECT_GROUPING_NUMBER + groupingNumber;
							newPropertyForKFC = false;
						}
						nextDotElement = getNextDotElement(lastClassName, bytes[index], kfc);
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
					// source myself, subject target, knowledge property for kfc
					perceptionDescription = pd_source + pd_subject_target;
					newKFCElement = true;
					for (int index = 1; index < bytes.length; index++) {
						if (newKFCElement == true) {
							kfc = getKFCNumber(index, bytes[index], usedKFCNumberSoFar);
							if (kfc == -1) {
								// try getKFCNumber for next index again
								newKFCElement = true;
								continue;
							}
							if (index > 1) {
								// it's the beginning of a further KFC-block
								// --> finish the last KFC-block
								perceptionDescription += ";" ;
							}
							groupingNumber = getGroupingNumberForIndex(bytes[index]);
							perceptionDescription +=  pd_property_praefix + "(" + kfc + ",devValueName_" + index + "):";
							perceptionDescription += GetValue.getValue(Value.VALUE_BY_NAME_EVENT_PARAMS) + "." +
									GetValue.getValue(Value.VALUE_BY_NAME_EVENT_TARGET) + "#" + GetValue.getIsElementOf(groupingNumber);
							lastClassName = GroupingOfSimulationObjects.PRAEFIX_SIMOBJECT_GROUPING_NUMBER + groupingNumber;
							newPropertyForKFC = false;
							newKFCElement = false;
						}
						else if (newPropertyForKFC == true) {
							groupingNumber = getGroupingNumberForIndex(bytes[index]);
							perceptionDescription += "&" + pd_property_praefix + "(" + kfc + ",devValueName_" + index + "):";
							perceptionDescription += GetValue.getValue(Value.VALUE_BY_NAME_EVENT_PARAMS) + "." +
									GetValue.getValue(Value.VALUE_BY_NAME_EVENT_TARGET) + "#" + GetValue.getIsElementOf(groupingNumber);
							lastClassName = GroupingOfSimulationObjects.PRAEFIX_SIMOBJECT_GROUPING_NUMBER + groupingNumber;
							newPropertyForKFC = false;
						}
						nextDotElement = getNextDotElement(lastClassName, bytes[index], kfc);
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
	

	
	private NextDotElement getNextDotElement(String lastClassName, int selection, int nrKFC) {
		
		NextDotElement result = new NextDotElement();
		
		StringTupel selectedProperty = new StringTupel("", "");
		StringTupel selectedPropertyFromMethod = new StringTupel("", "");
		
		List<StringTupel> returnablePropertyTypes;
		List<StringTupel> returnablePropertyFromMethodTypes;
		
		String propertyClassName;
		
		
		returnablePropertyTypes = simulationMetaInformation.getPropertiesMetaInfosForClass(lastClassName);
		returnablePropertyFromMethodTypes = simulationMetaInformation.getPropMethodsMetaInfosForClass(lastClassName);

		int groupingNumber = getGroupingNumberForIndex(selection);

		int index;
		int maxIndex;
		if (nrKFC >= 0) {
			KnowledgeFact_Criterion kfc = KnowledgeFact_Criterion.getName(nrKFC);
			maxIndex = returnablePropertyTypes.size() - 1;
			for (index = maxIndex; index >= 0; index--) {
				propertyClassName = returnablePropertyTypes.get(index).getLeft();
				if (propertyClassName.equals(SimulationMetaInformation.CLASSNAME_SWT_SIMOBJECT)) {
					propertyClassName = GroupingOfSimulationObjects.PRAEFIX_SIMOBJECT_GROUPING_NUMBER + groupingNumber;
				}
				if (simulationMetaInformation.checkClassForReturnableKFCs(propertyClassName, kfc) == false ) {
					returnablePropertyTypes.remove(index);
				};
			}
			maxIndex = returnablePropertyFromMethodTypes.size() - 1;
			for (index = maxIndex; index >= 0; index--) {
				propertyClassName = returnablePropertyFromMethodTypes.get(index).getLeft();
				if (propertyClassName.equals(SimulationMetaInformation.CLASSNAME_SWT_SIMOBJECT)) {
					propertyClassName = GroupingOfSimulationObjects.PRAEFIX_SIMOBJECT_GROUPING_NUMBER + groupingNumber;
				}
				if ( propertyClassName.equals(SimulationMetaInformation.CLASSNAME_ENUM_INDEX)) {
					
				}
				else if (simulationMetaInformation.checkClassForReturnableKFCs(propertyClassName, kfc) == false ) {
					returnablePropertyFromMethodTypes.remove(index);
				};
			}
		}
		
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
		
		if (result.className.equals(SimulationMetaInformation.CLASSNAME_SWT_SIMOBJECT)) {
			result.className = GroupingOfSimulationObjects.PRAEFIX_SIMOBJECT_GROUPING_NUMBER + groupingNumber;
			result.nextDotElement += "#" + GetValue.getIsElementOf(groupingNumber);
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
		String knowledgeAtomNamePart = "";
	}
	
	private void initArrayAllGroupingNumbers() {
		
		allGroupingNumbers = new ArrayList<Integer>();
		
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_OBJECTS); 
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_ANIMALS_AND_ITEMS); 
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_PLANTS_AND_ITEMS); 
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_ANIMALS);
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_PLANTS); 	
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_ITEMS); 	
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_GODS);
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_MAGICS);


		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_MAMMALS);
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_HUMANS);
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_DOGS);
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_CATS);
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_BIRDS);
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_FISHS);

		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_EATABLES);
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_FRUITS);
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_VEGGIES);
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_MEATS);

		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_CLOTHES);
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_CAPS);
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_SHIRTS);
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_TROUSERS);
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_SHOES);
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_SOCKS);
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_GLOVES);

		// for GROUPING_NUMBER_ALL_BIRDS 
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_PALAEOGNATHAE);
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_GALLOANSERAE);
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_STRISORES);
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_OTIDIMORPHAE);
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_COLUMBIMORPHAE);
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_GRUIFORMES);
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_AEQUORLITHORNIHES);
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_ACCIPITRIFORMES);
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_STRIGIFORMES);
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_CORACIIMORPHAE);
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_FALCONIFORMES);
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_PSITTACIFORMES);
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_SUBOSCINES);
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_OSCINES);
		
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_LIQUIDS);

	}

	private int getGroupingNumberForIndex(int index) {
		int indexModuloSize = index % allGroupingNumbers.size();
		return allGroupingNumbers.get(indexModuloSize);
	}
	
}
