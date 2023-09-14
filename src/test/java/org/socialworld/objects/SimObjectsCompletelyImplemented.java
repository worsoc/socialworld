package org.socialworld.objects;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.socialworld.collections.IntHashMap;
import org.socialworld.conversation.Lexem;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;

public class SimObjectsCompletelyImplemented {

	  @Test
	  void methodGetLexemIdLowerValue_Implemented() {
	    assertThat(TestSimObjectsCompletelyImplemented.checkMethod_getLexemIdLowerValue()).isTrue();
	  }

	  @Test
	  void generatedLexemID_isUnique() {
	    assertThat(TestSimObjectsCompletelyImplemented.check_GeneratedLexemID_isUnique()).isTrue();
	  }

	  
	

	private static class TestSimObjectsCompletelyImplemented {
		
		private static  String  SIM_OBJ_ROOT_CLASS_NAME = "org.socialworld.objects.SimulationObject";
		private static  String  NO_SIM_OBJ_ROOT_CLASS_NAME = "org.socialworld.objects.NoSimulationObject";
		
		private static String METHOD_NAME_GetLexemIdLowerValue = "getLexemIdLowerValue";
		private static String METHOD_NAME_GetLexemIdHigherValue = "getLexemIdHigherValue";
		
		private static boolean checkMethod_getLexemIdLowerValue() {
			
			boolean resultCheck = true;
			String className;
			String parentClassName;
			String declaringClassName;
			String methodName = METHOD_NAME_GetLexemIdLowerValue;
			
			try (ScanResult result = new ClassGraph().enableClassInfo().enableAnnotationInfo().scan()) {
			    
				ClassInfoList classInfos = result.getSubclasses(SIM_OBJ_ROOT_CLASS_NAME);
		     	classInfos = classInfos.getStandardClasses();
		     	List<Class<?>> list = classInfos.loadClasses();
		     	for (Class<?> simObjClass : list) {

		     		className = simObjClass.getName();
		     		parentClassName = simObjClass.getSuperclass().getName();
		     		
		     		
			     	// ignore NoSimulationObject sub classes
			     	if (parentClassName.equals(NO_SIM_OBJ_ROOT_CLASS_NAME)) continue;
		     		
		     		
		     		Method method;

		     			
		     		try {
		     			method = simObjClass.getMethod(methodName);
		     			declaringClassName = method.getDeclaringClass().getName();
		     			if (!declaringClassName.equals(SIM_OBJ_ROOT_CLASS_NAME)) {
			     			 if (!declaringClassName.equals(className)) {
				     				resultCheck = false; 
				     				System.out.println(className + "." + methodName + " not implemented");
				     		}
			     		}
		     		} catch (SecurityException e) { resultCheck = false; }
		     		  catch (NoSuchMethodException e) { resultCheck = false;}
	

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
		     		
		     		Method methodGetHigherValue;
		     		Method methodGetLowerValue;

		      		int lexemIdHigherValue = 0;
		      		int lexemIdLowerValue = 0;
		     		
		     		try {
		     			 methodGetHigherValue = simObjClass.getMethod(METHOD_NAME_GetLexemIdHigherValue);
		     			 lexemIdHigherValue = (int) methodGetHigherValue.invoke(null);
		     		} catch (SecurityException e) {  }
		     		  catch (NoSuchMethodException e) { }
		     		  catch (IllegalArgumentException e) {  }
		      		  catch (IllegalAccessException e) { }
		      		  catch (InvocationTargetException e) {  }
		  
		      		try {
		     			 methodGetLowerValue = simObjClass.getMethod(METHOD_NAME_GetLexemIdLowerValue);
		     			 lexemIdLowerValue = (int) methodGetLowerValue.invoke(null);
		     		} catch (SecurityException e) {  }
		     		  catch (NoSuchMethodException e) { }
		     		  catch (IllegalArgumentException e) {  }
		      		  catch (IllegalAccessException e) { }
		      		  catch (InvocationTargetException e) {  }
		    	
		     		// ignore if both id parts are 0
		      		if (lexemIdHigherValue  == 0 && lexemIdLowerValue == 0) continue;
		      		
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
