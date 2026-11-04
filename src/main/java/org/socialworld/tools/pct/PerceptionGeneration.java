package org.socialworld.tools.pct;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.socialworld.calculation.Value;
import org.socialworld.calculation.expressions.GetValue;
import org.socialworld.knowledge.KnowledgeFact_Criterion;
import org.socialworld.objects.GroupingOfSimulationObjects;
import org.socialworld.tools.Generation;
import org.socialworld.tools.SimulationMetaInformation;
import org.socialworld.tools.StringTupel;

/**
 * Generates all paths along which a perception can read something from the subject of an event.
 * The paths come from the meta information of the simulation classes.
 */
public class PerceptionGeneration extends Generation{

	public static final String CHAINED_NAME_SEPARATOR = ".";
	
	
	private List<Integer> allGroupingNumbers; 
	
	PerceptionGeneration() {
		initArrayAllGroupingNumbers();
	}
	

	/**
	 * @param subjectValueName for example Value.VALUE_BY_NAME_EVENT_CAUSER
	 */
	public List<PerceptionPath> generateAllValuePaths(String subjectValueName) {
		
		Set<PerceptionPath> paths = new LinkedHashSet<PerceptionPath>();
		
		collectPathsForAllGroupings(subjectValueName, PerceptionPath.NO_CRITERION, paths);
		
		return new ArrayList<PerceptionPath>(paths);
	}
	
	
	/**
	 * @param subjectValueName for example Value.VALUE_BY_NAME_EVENT_CAUSER
	 */
	public List<PerceptionPath> generateAllPropertyPaths(String subjectValueName) {
		
		Set<PerceptionPath> paths = new LinkedHashSet<PerceptionPath>();
		
		for (KnowledgeFact_Criterion criterion : KnowledgeFact_Criterion.values()) {
			
			// "nothing" is no criterion, and relations are no properties
			if (criterion == KnowledgeFact_Criterion.nothing || criterion.isRelation()) continue;
			
			collectPathsForAllGroupings(subjectValueName, criterion.getIndex(), paths);
		}
		
		return new ArrayList<PerceptionPath>(paths);
	}
	
	
	private void collectPathsForAllGroupings(String subjectValueName, int nrKFC, Set<PerceptionPath> paths) {
		
		for (int groupingNumber : allGroupingNumbers) {
			
			List<String> startSteps = new ArrayList<String>();
			startSteps.add(GetValue.getValue(Value.VALUE_BY_NAME_EVENT_PARAMS));
			startSteps.add(GetValue.getValue(subjectValueName) + "#" + GetValue.getIsElementOf(groupingNumber));
			
			String className = GroupingOfSimulationObjects.PRAEFIX_SIMOBJECT_GROUPING_NUMBER + groupingNumber;
			
			collectPaths(0, className, nrKFC, startSteps, "", new ArrayList<String>(), paths);
		}
	}
	
	
	private void collectPaths(
			int depth,  String lastClassName, int nrKFC, List<String> steps, String chainedName, List<String> ignoreNDEs, Set<PerceptionPath> paths) {
		
		List<NextDotElement> nextDotElements = getNextDotElements(lastClassName, nrKFC);
		
		if (nextDotElements.isEmpty()) {
			// at depth 0 the path would only check the grouping, but read nothing
			if (depth > 0) {
				// the type of a property is implicit, only a value names its type
				String resultType = (nrKFC == PerceptionPath.NO_CRITERION) ? lastClassName : "";
				paths.add(new PerceptionPath(nrKFC, chainedName, resultType, steps));
			}
			return;
		}
		
		for (NextDotElement nde :  nextDotElements) {
			
			if (ignoreNDEs.contains(nde.nextDotElement)) continue;
			
			List<String> nextSteps = new ArrayList<String>(steps);
			nextSteps.add(nde.nextDotElement);
			
			String nextChainedName = chainedName;
			if (!nde.knowledgeAtomNamePart.isEmpty()) {
				if (chainedName.isEmpty()) {
					nextChainedName = nde.knowledgeAtomNamePart;
				}
				else {
					nextChainedName = chainedName + CHAINED_NAME_SEPARATOR + nde.knowledgeAtomNamePart;
				}
			}
			
			List<String> ignoreNDEsNextDepth = new ArrayList<String>(ignoreNDEs);
			ignoreNDEsNextDepth.add(nde.nextDotElement);
			
			collectPaths(depth + 1, nde.className, nrKFC, nextSteps, nextChainedName, ignoreNDEsNextDepth, paths);
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
					// the path ends on the enum: KnowledgeProperty gets its lexem from the enum itself
					addToResult = false;
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




		// for GROUPING_NUMBER_ALL_MAMMALS 
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_MAMMALS);
		
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_MARSUPIALS);
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_PRIMATES);
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_RODENTS);
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_BATS);
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_PREDATORS);
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_WHALES);
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_UNGULATES);
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_ELSEMAMMALS);

		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_HUMANS);
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_DOGS);
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_CATS);
		
		// for GROUPING_NUMBER_ALL_BIRDS 
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_BIRDS);
		
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_RAPTORS);
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_COLUMBAVES);
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_PASSERIFORMES);
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
	
		
		// for GROUPING_NUMBER_ALL_FISHS 
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_FISHS);
		
		// for GROUPING_NUMBER_ALL_LIQUIDS 
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_LIQUIDS);
		
		// for GROUPING_NUMBER_ALL_EATABLES 
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_FOODS);
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_FRUITS);
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_VEGGIES);
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_MEATS);

		
		// for GROUPING_NUMBER_ALL_HUMANCRAFTEDS 
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_HUMANCRAFTEDS);
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_CLOTHES);
		
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_CAPS);
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_SHIRTS);
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_TROUSERS);
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_SHOES);
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_SOCKS);
		allGroupingNumbers.add(GroupingOfSimulationObjects.GROUPING_NUMBER_ALL_GLOVES);
		
		

	}
	
}
