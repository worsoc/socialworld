package org.socialworld.datasource;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.ListIterator;

import org.apache.log4j.Logger;
import org.socialworld.calculation.*;
import org.socialworld.attributes.ActionMode;
import org.socialworld.attributes.ActionType;
import org.socialworld.core.Event;

public class EventReactionDescriptionPool {

	private static final Logger logger = Logger.getLogger(EventReactionDescriptionPool.class);
	private static EventReactionDescriptionPool instance;
	
	private static ArrayList<EventReactionDescription> descriptions;
	private static ArrayList<Expression> expressions;
	
	private EventReactionDescriptionPool () {
		logger.debug("create EventReactionDescriptionPool");
		descriptions = new ArrayList<EventReactionDescription> ();
		expressions = new ArrayList<Expression> ();

		initialize();
	}
	
	public static EventReactionDescriptionPool getInstance() {
		if (instance == null) {
			instance = new EventReactionDescriptionPool();
		}
		return instance;
	}
	
	public EventReactionDescription getDescription(int eventType,	int reactionType) {
		int index;
		int countFill;
		int maxFill;

		EventReactionDescription description;
		
		index = eventType * Event.MAX_EVENT_TYPE + reactionType;
		
		if (descriptions.size() >= index) {
			description = descriptions.get(index);
			if (description == null) {
				description = createDescription(eventType, reactionType);
				descriptions.set(index, description);
			}
		}	
		else {
			maxFill = index - descriptions.size();
			for ( countFill=1; countFill <= maxFill; countFill++) {
				description = null;
				descriptions.add(description);
			}
			description = createDescription(eventType, reactionType);
			descriptions.add(description);
		}
		return description;
	}

	private void initialize() {
		initializeFromFile();
	}
	
	private void initializeFromFile() {
		
		ListIterator<Expression> iterator;
		Expression expression;
		Expression expressionDummy;
		
		EventReactionDescription erd;
		
		int IDTrue;
		int IDFalse;
		

		expressionDummy = new ActionDelayExpression();
		expressionDummy.setID(0);

		// temporary initialized with ActionDelayExpression
		// expression and erd must be initialized
		expression = new ActionDelayExpression();
		erd = new EventReactionDescription();
		
		try
		{
			String line;
			
	
			InputStream input = new URL("http://sourceforge.net/projects/socialworld/files/hmn_swerd.txt").openStream();
			LineNumberReader lnr
			   = new LineNumberReader(new InputStreamReader(input));

	
			while ((line = lnr.readLine()) != null)
			{

				if (line.startsWith("<ERD>")) {
					erd = new EventReactionDescription();
					continue;
				}

				if (line.startsWith("</ERD>")) {
					// TODO add at index for event type and reaction type
					descriptions.add(erd);
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


				if (line.startsWith("<ActionDelayExp>")) {
					expression = new ActionDelayExpression();
					continue;
				}

				if (line.startsWith("</ActionDelayExp>")) {
					addExpression(expression, expressionDummy);
					erd.setDelayExpression((ActionDelayExpression) expression);
					continue;
				}

				if (line.startsWith("<ActionDurationExp>")) {
					expression = new ActionDurationExpression();
					continue;
				}

				if (line.startsWith("</ActionDurationExp>")) {
					addExpression(expression, expressionDummy);
					erd.setDurationExpression((ActionDurationExpression) expression);
					continue;
				}

				if (line.startsWith("<ActionIntensityExp>")) {
					expression = new ActionIntensityExpression();
					continue;
				}

				if (line.startsWith("</ActionIntensityExp>")) {
					addExpression(expression, expressionDummy);
					erd.setIntensityExpression((ActionIntensityExpression) expression);
					continue;
				}

				if (line.startsWith("<ActionModeExp>")) {
					expression = new ActionModeExpression();
					continue;
				}
				
				if (line.startsWith("<Mode>"))
				{
					ActionModeExpression tmp;
					
					tmp = (ActionModeExpression) expression;
					
					line = line.substring(6);
					line = line.replace("</Mode>", "");
					line = line.trim();
					switch(line) {
						case "run":  tmp.setMode(ActionMode.run);break;
						case "walk":  tmp.setMode(ActionMode.walk);break;
						case "jump":  tmp.setMode(ActionMode.jump);break;
						case "sleepIntentioned":  tmp.setMode(ActionMode.sleepIntentioned);break;
						case "sleepCaused":  tmp.setMode(ActionMode.sleepCaused);break;
						case "weaponRightHand":  tmp.setMode(ActionMode.weaponRightHand);break;
						case "weaponLeftHand":  tmp.setMode(ActionMode.weaponLeftHand);break;
						case "examineItem":  tmp.setMode(ActionMode.examineItem);break;
						case "takeItem":  tmp.setMode(ActionMode.takeItem);break;
						case "useItem":  tmp.setMode(ActionMode.useItem);break;
						case "collectItem":  tmp.setMode(ActionMode.collectItem);break;
						case "switchItemToLeftHand":  tmp.setMode(ActionMode.switchItemToLeftHand);break;
						case "useTwoItems":  tmp.setMode(ActionMode.useTwoItems);break;
						case "dropItem":  tmp.setMode(ActionMode.dropItem);break;
						case "say":  tmp.setMode(ActionMode.say);break;
						case "scream":  tmp.setMode(ActionMode.scream);break;
						case "whisper":  tmp.setMode(ActionMode.whisper);break;
					}
					continue;
				}
				
				if (line.startsWith("</ActionModeExp>")) {
					addExpression(expression, expressionDummy);
					erd.setActionModeExpression((ActionModeExpression) expression);
					continue;
				}

				if (line.startsWith("<ActionPriorityExp>")) {
					expression = new ActionPriorityExpression();
					continue;
				}

				if (line.startsWith("</ActionPriorityExp>")) {
					addExpression(expression, expressionDummy);
					erd.setPriorityExpression((ActionPriorityExpression) expression);
					continue;
				}

				if (line.startsWith("<ActionRelDirExp>")) {
					expression = new ActionRelativeDirectionExpression();
					continue;
				}

				if (line.startsWith("<Vector>"))
				{
					ActionRelativeDirectionExpression tmp;
					
					tmp = (ActionRelativeDirectionExpression) expression;
					
					line = line.substring(8);
					line = line.replace("</Vector>", "");
					line = line.trim();
					tmp.setVector(new Vector(line));
					continue;
				}

				if (line.startsWith("</ActionRelDirExp>")) {
					addExpression(expression, expressionDummy);
					erd.setRelativeDirectionExpression((ActionRelativeDirectionExpression) expression);
					continue;
				}

				if (line.startsWith("<ActionTypeExp>")) {
					expression = new ActionTypeExpression();
					continue;
				}
				
				if (line.startsWith("<Type>"))
				{
					ActionTypeExpression tmp;
					
					tmp = (ActionTypeExpression) expression;
					
					line = line.substring(6);
					line = line.replace("</Type>", "");
					line = line.trim();
					switch(line) {
						case "touch":  tmp.setType(ActionType.touch);break;
						case "sleep":  tmp.setType(ActionType.sleep);break;
						case "changeMove":  tmp.setType(ActionType.changeMove);break;
						case "kick":  tmp.setType(ActionType.kick);break;
						case "controlHandManually":  tmp.setType(ActionType.controlHandManually);break;
						case "spell":  tmp.setType(ActionType.spell);break;
						case "useWeaponLeft":  tmp.setType(ActionType.useWeaponLeft);break;
						case "useWeaponRight":  tmp.setType(ActionType.useWeaponRight);break;
						case "move":  tmp.setType(ActionType.move);break;
						case "say":  tmp.setType(ActionType.say);break;
						case "handleItem":  tmp.setType(ActionType.handleItem);break;
					}
					continue;
				}

				if (line.startsWith("</ActionTypeExp>")) {
					addExpression(expression, expressionDummy);
					erd.setActionTypeExpression((ActionTypeExpression) expression);
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

	private void addExpression(Expression expression, Expression expressionDummy) {
		int ID;
	
		ID = expression.getID();
		while (ID > expressions.size()) {
			expressions.add(expressionDummy);
		}
		expressions.set(ID - 1, expression);
	}
	
	private EventReactionDescription createDescription(int eventType,	int reactionType) {
		EventReactionDescription description = new	EventReactionDescription();
		
		return description;
	}

}
