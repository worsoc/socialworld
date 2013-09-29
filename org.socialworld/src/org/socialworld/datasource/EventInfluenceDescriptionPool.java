package org.socialworld.datasource;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.ListIterator;

import org.apache.log4j.Logger;
import org.socialworld.calculation.EventInfluenceExpression;
import org.socialworld.calculation.EventInfluenceDescription;
import org.socialworld.calculation.ExpressionFunction;
import org.socialworld.calculation.ConditionOperator;
import org.socialworld.core.Event;

public class EventInfluenceDescriptionPool {

	private static final Logger logger = Logger.getLogger(EventInfluenceDescriptionPool.class);
	private static EventInfluenceDescriptionPool instance;
	
	private static ArrayList<EventInfluenceDescription> descriptions;
	private static ArrayList<EventInfluenceExpression> expressions;
	
	private EventInfluenceDescriptionPool () {
		logger.debug("create EventInfluenceDescriptionPool");
		descriptions = new ArrayList<EventInfluenceDescription> ();
		expressions = new ArrayList<EventInfluenceExpression> ();

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
		int countFill;
		int maxFill;
		
		EventInfluenceDescription description;
		
		index = eventType * Event.MAX_EVENT_TYPE + influenceType;
		
		if (descriptions.size() > index) {
			description = descriptions.get(index);
			if (description == null) {
				description = createDescription(eventType, influenceType);
				descriptions.set(index, description);
			}
		}	
		else {
			maxFill = index - descriptions.size();
			for ( countFill=1; countFill <= maxFill; countFill++) {
				description = null;
				descriptions.add(description);
			}
			
			description = createDescription(eventType, influenceType);
			descriptions.add(description);
		}
		return description;
	}

	private void initialize() {
		initializeFromFile();
	}
	
	private void initializeFromFile() {
		
		ListIterator<EventInfluenceExpression> iterator;
		EventInfluenceExpression expression;
		EventInfluenceExpression expressionDummy;
		
		EventInfluenceDescription eid;
		
		int IDTrue;
		int IDFalse;
		

		expressionDummy = new EventInfluenceExpression();
		expressionDummy.setID(0);

		// temporary initialized with ActionDelayExpression
		// expression and eid must be initialized
		expression = new EventInfluenceExpression();
		eid = new EventInfluenceDescription();
		
		try
		{
			String line;
			
	
			InputStream input = new URL("http://sourceforge.net/projects/socialworld/files/hmn_sweid.txt").openStream();
			LineNumberReader lnr
			   = new LineNumberReader(new InputStreamReader(input));

	
			while ((line = lnr.readLine()) != null)
			{

				if (line.startsWith("<EID>")) {
					eid = new EventInfluenceDescription();
					continue;
				}

				if (line.startsWith("</EID>")) {
					// TODO add at index for event type and reaction type
					descriptions.add(eid);
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
					addExpression(expression, expressionDummy);
					eid.setExpression( expression);
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
		
		iterator = expressions.listIterator();
		
		while (iterator.hasNext()) {
			expression = iterator.next();
			
			if (expression.getID() > 0 ) {
				IDTrue = expression.getIDTrue();
				IDFalse = expression.getIDFalse();
				
				if (IDTrue > 0) {
					expression.setTrueExpression(expressions.get(IDTrue - 1));
				}
				if (IDFalse > 0) {
					expression.setFalseExpression(expressions.get(IDFalse - 1));
				}
			}
		}
	}

	private void addExpression(EventInfluenceExpression expression, EventInfluenceExpression expressionDummy) {
		int ID;
	
		ID = expression.getID();
		while (ID > expressions.size()) {
			expressions.add(expressionDummy);
		}
		expressions.set(ID - 1, expression);
	}
	
	private EventInfluenceDescription createDescription(int eventType,	int influenceType) {
		EventInfluenceDescription description = new	EventInfluenceDescription();
		
		return description;
	}


}
