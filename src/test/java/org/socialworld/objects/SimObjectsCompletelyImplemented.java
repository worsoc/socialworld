/*
* Social World
* Copyright (C) 2023  Mathias Sikos
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.  
*
* or see http://www.gnu.org/licenses/gpl-2.0.html
*
*/
package org.socialworld.objects;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.socialworld.Constants;
import org.socialworld.collections.IntHashMap;
import org.socialworld.conversation.Lexem;
import org.socialworld.objects.enums.EnumBaseSimObj;
import org.socialworld.objects.enums.EnumBird;
import org.socialworld.objects.enums.EnumFood;
import org.socialworld.objects.enums.EnumHumanCrafted;
import org.socialworld.objects.enums.EnumLiquid;
import org.socialworld.objects.enums.EnumMammal;
import org.socialworld.objects.statics.GetEnumSimObjForClassName;
import org.socialworld.objects.statics.GetLexemIDHigherPartFromMapping;
import org.socialworld.objects.statics.GetLexemIDLowerPartFromMapping;
import org.socialworld.objects.statics.Mapping_Base2LexemIDHigherPart;
import org.socialworld.objects.statics.Mapping_Bird2LexemIDLowerPart;
import org.socialworld.objects.statics.Mapping_Food2LexemIDLowerPart;
import org.socialworld.objects.statics.Mapping_HumanCrafted2LexemIDLowerPart;
import org.socialworld.objects.statics.Mapping_Liquid2LexemIDLowerPart;
import org.socialworld.objects.statics.Mapping_Mammal2LexemIDLowerPart;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;

public class SimObjectsCompletelyImplemented {

	  @Test
	  void existsMappingToLexemIDHigherPart() {
	    assertThat(TestSimObjectsCompletelyImplemented.existsMappingToLexemIDHigherPart()).isTrue();
	  }

	  @Test
	  void existsMappingToLexemIDLowerPart() {
	    assertThat(TestSimObjectsCompletelyImplemented.existsMappingToLexemIDLowerPart()).isTrue();
	  }

/*
	  @Test
	  void everySimObjClass_HasEnumElem() {
	    assertThat(TestSimObjectsCompletelyImplemented.check_everySimObjClass_HasEnumElem()).isTrue();
	  }
*/
	  
	  @Test
	  void generatedLexemID_isUnique() {
	    assertThat(TestSimObjectsCompletelyImplemented.check_GeneratedLexemID_isUnique()).isTrue();
	  }

	  
	

	private static class TestSimObjectsCompletelyImplemented {
		
		private static  String  SIM_OBJ_ROOT_CLASS_NAME = "org.socialworld.objects.SimulationObject";
		private static  String  NO_SIM_OBJ_ROOT_CLASS_NAME = "org.socialworld.objects.NoSimulationObject";
		
	
		private static boolean existsMappingToLexemIDHigherPart() {
			
			boolean resultCheck = true;
			
			int lexemIDhigherPart;
	
			for (EnumBaseSimObj elem : EnumBaseSimObj.values()) {
				lexemIDhigherPart = Mapping_Base2LexemIDHigherPart.getInstance().get(elem);
				
				if (lexemIDhigherPart == Constants.MAPPING_NO_ENTRY_FOR_KEY) {
     				resultCheck = false; 
     				System.out.println(elem.toString() + ": no mapping in Mapping_Base2LexemIDHigherPart");
				}
			}
								
			return resultCheck;
		}

		private static boolean existsMappingToLexemIDLowerPart() {
			
			boolean resultCheck = true;
			
			int lexemIDlowerPart;
	
			for (EnumMammal elem : EnumMammal.values()) {
				lexemIDlowerPart = Mapping_Mammal2LexemIDLowerPart.getInstance().get(elem);
				
				if (lexemIDlowerPart == Constants.MAPPING_NO_ENTRY_FOR_KEY) {
     				resultCheck = false; 
     				System.out.println(elem.toString() + ": no mapping in Mapping_Mammal2LexemIDLowerPart");
				}
			}
			
			for (EnumBird elem : EnumBird.values()) {
				lexemIDlowerPart = Mapping_Bird2LexemIDLowerPart.getInstance().get(elem);
				
				if (lexemIDlowerPart == Constants.MAPPING_NO_ENTRY_FOR_KEY) {
     				resultCheck = false; 
     				System.out.println(elem.toString() + ": no mapping in Mapping_Bird2LexemIDLowerPart");
				}
			}

			for (EnumFood elem : EnumFood.values()) {
				lexemIDlowerPart = Mapping_Food2LexemIDLowerPart.getInstance().get(elem);
				
				if (lexemIDlowerPart == Constants.MAPPING_NO_ENTRY_FOR_KEY) {
     				resultCheck = false; 
     				System.out.println(elem.toString() + ": no mapping in Mapping_Food2LexemIDLowerPart");
				}
			}

			for (EnumLiquid elem : EnumLiquid.values()) {
				lexemIDlowerPart = Mapping_Liquid2LexemIDLowerPart.getInstance().get(elem);
				
				if (lexemIDlowerPart == Constants.MAPPING_NO_ENTRY_FOR_KEY) {
     				resultCheck = false; 
     				System.out.println(elem.toString() + ": no mapping in Mapping_Liquid2LexemIDLowerPart");
				}
			}
			
			for (EnumHumanCrafted elem : EnumHumanCrafted.values()) {
				lexemIDlowerPart = Mapping_HumanCrafted2LexemIDLowerPart.getInstance().get(elem);
				
				if (lexemIDlowerPart == Constants.MAPPING_NO_ENTRY_FOR_KEY) {
     				resultCheck = false; 
     				System.out.println(elem.toString() + ": no mapping in Mapping_HumanCrafted2LexemIDLowerPart");
				}
			}
					
			return resultCheck;
		}
		
		private static boolean check_everySimObjClass_HasEnumElem() {
			
			boolean resultCheck = true;
			String className;
			
			
			try (ScanResult result = new ClassGraph().enableClassInfo().enableAnnotationInfo().scan()) {
			    
				ClassInfoList classInfos = result.getSubclasses(SIM_OBJ_ROOT_CLASS_NAME);
		     	classInfos = classInfos.getStandardClasses();
		     	List<Class<?>> list = classInfos.loadClasses();
		     	for (Class<?> simObjClass : list) {
		     		
		     		className = simObjClass.getName();
		     		
		     		Object simObjEnum;
		     		simObjEnum = GetEnumSimObjForClassName.getForClassName(className);
		     		
		     		if (simObjEnum == null) {
				    	resultCheck = false;
	     				System.out.println(className +": no sim obj enum entry (not found in GetEnumSimObjForClassName)");
		     		}
		     	}
			}		
					
			return resultCheck;
		}

		
		
	
	
		
		
		private static boolean check_GeneratedLexemID_isUnique() {
			
			boolean resultCheck = true;
			String className;
			
			IntHashMap simObjClassNameForLexemID = new IntHashMap();
			
			try (ScanResult result = new ClassGraph().enableClassInfo().enableAnnotationInfo().scan()) {
			    
				ClassInfoList classInfos = result.getSubclasses(SIM_OBJ_ROOT_CLASS_NAME);
		     	classInfos = classInfos.getStandardClasses();
		     	List<Class<?>> list = classInfos.loadClasses();
		     	for (Class<?> simObjClass : list) {
		     		
		     		className = simObjClass.getName();
		     		
		      		int lexemIdLowerValue  = GetLexemIDLowerPartFromMapping.getForClassName(className);
		     		if (lexemIdLowerValue == Constants.MAPPING_NO_ENTRY_FOR_KEY) continue;
	      		 
		     		
		      		int lexemIdHigherValue = GetLexemIDHigherPartFromMapping.getForClassName(className);
		     		if (lexemIdHigherValue == Constants.MAPPING_NO_ENTRY_FOR_KEY) continue;
	    	
		     		// ignore if both id parts are 0
		      		if (lexemIdHigherValue  == GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_IGNORE &&
		      				lexemIdLowerValue == GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_IGNORE) continue;
		      		
				    int lexemID = Lexem.OFFSET_LEXEMID_NOUN_SIMOBJ + lexemIdHigherValue * GroupingOfSimulationObjects.RANGE_FOR_LOWER_VALUE + lexemIdLowerValue;
			    	
				    Object firstSimObjClassWithLexemID =  simObjClassNameForLexemID.get(lexemID);
				    String firstClassName =  (firstSimObjClassWithLexemID == null ? "" : (String) firstSimObjClassWithLexemID);

 			    
				    if (firstClassName.length() > 0) {
				    	resultCheck = false;
	     				System.out.println(className + ": LexemID " + lexemID + " is not unique (already used by " + firstClassName +")");
				    }
				    else {
					    simObjClassNameForLexemID.set(lexemID, className);
				    }
		     	}
			}		
					
			return resultCheck;
		}

		
		
	}
	


}
