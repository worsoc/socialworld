package org.socialworld.datasource;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.URL;

import org.socialworld.calculation.EventInfluenceExpression;
import org.socialworld.calculation.EventInfluenceDescription;
import org.socialworld.calculation.ExpressionFunction;
import org.socialworld.calculation.ConditionOperator;

import org.socialworld.core.Event;

public class EventInfluenceDescriptionPool extends DescriptionPool {

	private static EventInfluenceDescriptionPool instance;
	
	private static EventInfluenceDescription descriptions[];
	
	private EventInfluenceDescriptionPool () {
		
		sizeDescriptionsArray = Event.MAX_EVENT_TYPE * InfluenceTypePool.CAPACITY_ITP_ARRAY;
		descriptions = new EventInfluenceDescription[sizeDescriptionsArray];
		
		
		initialize();
	}
	
	public static EventInfluenceDescriptionPool getInstance() {
		if (instance == null) {
			instance = new EventInfluenceDescriptionPool();
		}
		return instance;
	}
	
	public EventInfluenceDescription getDescription(int eventType,	int influenceType) {
		int index;
		
		EventInfluenceDescription description = null;
		
		index = eventType * Event.MAX_EVENT_TYPE + influenceType;
		
		if (sizeDescriptionsArray > index) {
			description = descriptions[index];
		}	
		return description;
	}

	protected void initialize() {
		initializeFromFile();
	}
	
	private void initializeFromFile() {
		
		EventInfluenceExpression expression;
		
		EventInfluenceDescription eid;
		
		
		int index;
		int eventType = 0;
		int influenceType = 0;
		

		// temporary initialized with ActionDelayExpression
		// expression and eid must be initialized
		expression = new EventInfluenceExpression();
		eid = new EventInfluenceDescription();
		
		try
		{
			String line;
			
	
			InputStream input = new URL("http://sourceforge.net/projects/socialworld/files/hmn_eid.swp").openStream();
			LineNumberReader lnr
			   = new LineNumberReader(new InputStreamReader(input));

	
			while ((line = lnr.readLine()) != null)
			{

				if (line.startsWith("<EID>")) {
					eid = new EventInfluenceDescription();
					continue;
				}

				if (line.startsWith("</EID>")) {
					index = eventType * Event.MAX_EVENT_TYPE + influenceType;
					descriptions[index] = eid;
					continue;
				}
				
				if (line.startsWith("<ID>")) {
					line = line.substring(4);
					line = line.replace("</ID>", "");
					line = line.trim();
					expression.setID(line);
					continue;
				}

				if (line.startsWith("<IDTrue>")) {
					line = line.substring(8);
					line = line.replace("</IDTrue>", "");
					line = line.trim();
					expression.setIDTrue(line);
					continue;
				}

				if (line.startsWith("<IDFalse>")) {
					line = line.substring(9);
					line = line.replace("</IDFalse>", "");
					line = line.trim();
					expression.setIDFalse(line);
					continue;
				}

				if (line.startsWith("<Fct>"))
				{
					line = line.substring(5);
					line = line.replace("</Fct>", "");
					line = line.trim();
					switch(line) {
						case "condition":  expression.setFunction(ExpressionFunction.condition);break;
						case "addition":  expression.setFunction(ExpressionFunction.addition);break;
						case "multiplication":  expression.setFunction(ExpressionFunction.multiplication);break;
						case "replacement":  expression.setFunction(ExpressionFunction.replacement);break;
						case "identity":  expression.setFunction(ExpressionFunction.identity);break;
					}
					continue;
				}

				if (line.startsWith("<Op>"))
				{
					line = line.substring(4);
					line = line.replace("</Op>", "");
					line = line.trim();
					switch(line) {
						case "equal":  expression.setOperator(ConditionOperator.equal);break;
						case "notEqual":  expression.setOperator(ConditionOperator.notEqual);break;
						case "less":  expression.setOperator(ConditionOperator.less);break;
						case "lessEqual":  expression.setOperator(ConditionOperator.lessEqual);break;
						case "greaterEqual":  expression.setOperator(ConditionOperator.greaterEqual);break;
						case "greater":  expression.setOperator(ConditionOperator.greater);break;
					}
					continue;
				}

				if (line.startsWith("<Const>"))
				{
					line = line.substring(7);
					line = line.replace("</Const>", "");
					line = line.trim();
					expression.setConstant(line);
					continue;
				}

				if (line.startsWith("<AttrId>"))
				{
					line = line.substring(8);
					line = line.replace("</AttrId>", "");
					line = line.trim();
					expression.setAttributeIndex(line);
					continue;
				}


				if (line.startsWith("<EventInfluenceExp>")) {
					expression = new EventInfluenceExpression();
					continue;
				}

				if (line.startsWith("</EventInfluenceExp>")) {
					addExpression(expression);
					eid.setExpression( expression);
					continue;
				}
				
				if (line.startsWith("<EventType>"))
				{
					line = line.substring(11);
					line = line.replace("</EventType>", "");
					line = line.trim();
					eventType = (int) Float.parseFloat(line);
					eid.setEventType(eventType);
					continue;
				}

				if (line.startsWith("<InfluenceType>"))
				{
					line = line.substring(15);
					line = line.replace("</InfluenceType>", "");
					line = line.trim();
					influenceType = (int) Float.parseFloat(line);
					eid.setInfluenceType(influenceType);
					continue;
				}

				

			}
			lnr.close();
		}
		catch (IOException e)
		{
			System.out.println("Fehler beim Lesen der Datei");
			e.printStackTrace();
		}
		
		setTrueAndFalseExpressions();
	}


}
