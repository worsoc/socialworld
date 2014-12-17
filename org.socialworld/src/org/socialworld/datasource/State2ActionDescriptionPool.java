package org.socialworld.datasource;

import org.socialworld.calculation.descriptions.State2ActionDescription;

public class State2ActionDescriptionPool extends DescriptionPool {
	

	private static State2ActionDescriptionPool instance;
	
	private State2ActionDescription descriptions[];
/*	int sizeDescriptionsArray;

	private static ArrayList<Expression> expressions;
	int sizeExpressionsArray;
*/	
	private State2ActionDescriptionPool () {
		
		sizeDescriptionsArray = State2ActionTypePool.CAPACITY_S2AP_ARRAY;
		descriptions = new State2ActionDescription[sizeDescriptionsArray];
		
		/*
		expressions = new ArrayList<Expression> ();
		sizeExpressionsArray = 0;

		*/
		initialize();
		
	}
	
	public static State2ActionDescriptionPool getInstance() {
		if (instance == null) {
			instance = new State2ActionDescriptionPool();
		}
		return instance;
	}
	
	public State2ActionDescription getDescription(int state2ActionType ) {
		int index;
		State2ActionDescription description = null;
		
		index = state2ActionType ;
		
		if (sizeDescriptionsArray > index) 	description = descriptions[index];
		return description;
	}

	protected void initialize() {
	}
	

	
	/*
	private void initializeFromFile() {
		
//		ListIterator<Expression> iterator;
		Expression expression;
		
		State2ActionDescription s2ad;
		
//		int IDTrue;
//		int IDFalse;
		

		int index;
		int state2ActionType = 0;

		// temporary initialized with Expression
		// expression and s2ad must be initialized
		expression = new Expression();
		s2ad = new State2ActionDescription();
		
		try
		{
			String line;
			
	
			InputStream input = new URL("http://sourceforge.net/projects/socialworld/files/hmn_s2ad.swp").openStream();
			LineNumberReader lnr
			   = new LineNumberReader(new InputStreamReader(input));

	
			while ((line = lnr.readLine()) != null)
			{

				if (line.startsWith("<S2AD>")) {
					s2ad = new State2ActionDescription();
					continue;
				}

				if (line.startsWith("</S2AD>")) {
					index = state2ActionType;
					descriptions[index] = s2ad;
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
					expression.set_ID_Exp2(line);
					continue;
				}

				if (line.startsWith("<IDFalse>")) {
					line = line.substring(9);
					line = line.replace("</IDFalse>", "");
					line = line.trim();
					expression.set_ID_Exp3(line);
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
					//expression.setValue(line);
					continue;
				}



				if (line.startsWith("<ActionDelayExp>")) {
					expression = new Expression();
					continue;
				}

				if (line.startsWith("</ActionDelayExp>")) {
					addExpression(expression);
					s2ad.setDelayExpression( expression);
					continue;
				}

				if (line.startsWith("<ActionDurationExp>")) {
					expression = new Expression();
					continue;
				}

				if (line.startsWith("</ActionDurationExp>")) {
					addExpression(expression);
					s2ad.setDurationExpression( expression);
					continue;
				}

				if (line.startsWith("<ActionIntensityExp>")) {
					expression = new Expression();
					continue;
				}

				if (line.startsWith("</ActionIntensityExp>")) {
					addExpression(expression);
					s2ad.setIntensityExpression( expression);
					continue;
				}

				if (line.startsWith("<ActionModeExp>")) {
					expression = new Expression();
					continue;
				}
				
				if (line.startsWith("<Mode>"))
				{
					Expression tmp;
					
					tmp =  expression;
					
					line = line.substring(6);
					line = line.replace("</Mode>", "");
					line = line.trim();
					switch(line) {
						case "run":  tmp.setValue(ActionMode.run);break;
						case "walk":  tmp.setValue(ActionMode.walk);break;
						case "jump":  tmp.setValue(ActionMode.jump);break;
						case "sleepIntentioned":  tmp.setValue(ActionMode.sleepIntentioned);break;
						case "sleepCaused":  tmp.setValue(ActionMode.sleepCaused);break;
						case "weaponRightHand":  tmp.setValue(ActionMode.weaponRightHand);break;
						case "weaponLeftHand":  tmp.setValue(ActionMode.weaponLeftHand);break;
						case "examineItem":  tmp.setValue(ActionMode.examineItem);break;
						case "takeItem":  tmp.setValue(ActionMode.takeItem);break;
						case "useItem":  tmp.setValue(ActionMode.useItem);break;
						case "collectItem":  tmp.setValue(ActionMode.collectItem);break;
						case "switchItemToLeftHand":  tmp.setValue(ActionMode.switchItemToLeftHand);break;
						case "useTwoItems":  tmp.setValue(ActionMode.useTwoItems);break;
						case "dropItem":  tmp.setValue(ActionMode.dropItem);break;
						case "say":  tmp.setValue(ActionMode.say);break;
						case "scream":  tmp.setValue(ActionMode.scream);break;
						case "whisper":  tmp.setValue(ActionMode.whisper);break;
					}
					continue;
				}
				
				if (line.startsWith("</ActionModeExp>")) {
					addExpression(expression);
					s2ad.setActionModeExpression( expression);
					continue;
				}

				if (line.startsWith("<ActionPriorityExp>")) {
					expression = new Expression();
					continue;
				}

				if (line.startsWith("</ActionPriorityExp>")) {
					addExpression(expression);
					s2ad.setPriorityExpression( expression);
					continue;
				}

				if (line.startsWith("<ActionRelDirExp>")) {
					expression = new Expression();
					continue;
				}

				if (line.startsWith("<Vector>"))
				{
					Expression tmp;
					
					tmp =  expression;
					
					line = line.substring(8);
					line = line.replace("</Vector>", "");
					line = line.trim();
					//tmp.setValue(new Vector(line));
					continue;
				}

				if (line.startsWith("</ActionRelDirExp>")) {
					addExpression(expression);
					s2ad.setRelativeDirectionExpression( expression);
					continue;
				}

				if (line.startsWith("<ActionTypeExp>")) {
					expression = new Expression();
					continue;
				}
				
				if (line.startsWith("<Type>"))
				{
					Expression tmp;
					
					tmp =  expression;
					
					line = line.substring(6);
					line = line.replace("</Type>", "");
					line = line.trim();
/*					switch(line) {
						case "touch":  tmp.setValue(ActionType.touch);break;
						case "sleep":  tmp.setValue(ActionType.sleep);break;
						case "changeMove":  tmp.setValue(ActionType.changeMove);break;
						case "kick":  tmp.setValue(ActionType.kick);break;
						case "controlHandManually":  tmp.setValue(ActionType.controlHandManually);break;
						case "spell":  tmp.setValue(ActionType.spell);break;
						case "useWeaponLeft":  tmp.setValue(ActionType.useWeaponLeft);break;
						case "useWeaponRight":  tmp.setValue(ActionType.useWeaponRight);break;
						case "move":  tmp.setValue(ActionType.move);break;
						case "say":  tmp.setValue(ActionType.say);break;
						case "handleItem":  tmp.setValue(ActionType.handleItem);break;
					}
					continue;
				}

				if (line.startsWith("</ActionTypeExp>")) {
					addExpression(expression);
					s2ad.setActionTypeExpression( expression);
				continue;
				}
				
				if (line.startsWith("<S2AType>"))
				{
					line = line.substring(9);
					line = line.replace("</S2AType>", "");
					line = line.trim();
					state2ActionType = (int) Float.parseFloat(line);
					s2ad.setState2ActionType(state2ActionType);
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
		
		setTrueAndFalseExpressions();
	}
*/

/*
	private void addExpression(Expression expression) {
		int ID;
	
		ID = expression.getID();
		if (ID > sizeExpressionsArray) ensureCapacity(ID);
		
		expressions.set(ID - 1, expression);
	}
	
	private void ensureCapacity(int capacity) {
		if (capacity > sizeExpressionsArray) {
			for (int i = 1; i <= capacity - sizeExpressionsArray; i++)
				expressions.add(null);
			sizeExpressionsArray = capacity;
		}
	}
*/

}
