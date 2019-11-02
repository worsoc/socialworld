package org.socialworld.datasource.pool;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.calculation.Expression;
import org.socialworld.calculation.FunctionByExpression;
import org.socialworld.calculation.descriptions.State2ActionDescription;
import org.socialworld.calculation.expressions.CreateActionExpression;
import org.socialworld.calculation.expressions.Nothing;

public class State2ActionDescriptionPool extends DescriptionPool {
	
	public static final int COUNT_FbE_TEST_ENTRIES = 2;		// Anzahl Testeintraege FunctionByExpression

	private static State2ActionDescriptionPool instance;
	
	private State2ActionDescription descriptions[];
	private FunctionByExpression expressions[];
	
	private State2ActionDescriptionPool () {
		
		sizeDescriptionsArray = GaussPoolState2ActionType.CAPACITY_GPS2A_ARRAY;
		descriptions = new State2ActionDescription[sizeDescriptionsArray];
		
		expressions = new FunctionByExpression[COUNT_FbE_TEST_ENTRIES];
		
		initializeWithTestData_FunctionByExpression();
		
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
		
		if (index >= 0 & sizeDescriptionsArray > index) 
			description = descriptions[index];
		else
			// create a dummy description with an expression that returns the invalid "nothing" value
			description = new State2ActionDescription();
		
		return description;
	}

	protected void initialize() {

		State2ActionDescription description;
		
		for (int index = 0; index < sizeDescriptionsArray; index++) {
			
			description = new State2ActionDescription();
			description.addFunctionCreateAction(expressions[0]);
			description.addFunctionCreateAction(expressions[1]);
			
			descriptions[index] = description;
	
		}
		
	}
	
	private void initializeWithTestData_FunctionByExpression() {
		
		
		int 		expressionsCount = COUNT_FbE_TEST_ENTRIES;
		List<List<String>> expressions = new ArrayList<List<String>>(expressionsCount);
		List<String> lines;
		Expression startExpression = Nothing.getInstance();

		lines = new ArrayList<String>(1);
		lines.add("WENN mood > 10 DANN <ACTIONTYPE><Const>1</Const></ACTIONTYPE><ACTIONMODE><Const>14</Const></ACTIONMODE><MINTIME><Now+N>10000</Now+N></MINTIME><MAXTIME><Now+N>100000</Now+N></MAXTIME><PRIORITY><Const>150</Const></PRIORITY><INTENSITY><MX+N>5;1.5;23</MX+N></INTENSITY><DURATION><Const>0</Const></DURATION>");
		expressions.add(lines);
		
		lines = new ArrayList<String>(1);
		lines.add("WENN tiredness == 41 DANN <ACTIONTYPE><Const>0</Const></ACTIONTYPE><ACTIONMODE><Const>2</Const></ACTIONMODE><MINTIME><Now+N>10000</Now+N></MINTIME><MAXTIME><Now+N>100000</Now+N></MAXTIME><PRIORITY><Const>50</Const></PRIORITY><INTENSITY><MX+N>4;1.5;0</MX+N></INTENSITY><DURATION><Const>610000</Const></DURATION>");
		expressions.add(lines);


		for (int i = 0; i <  expressionsCount; i++) {
			
			startExpression = new CreateActionExpression(expressions.get(i), CreateActionExpression.MODUS_CREATE_STATE2ACTION);
			this.expressions[i] = new FunctionByExpression(startExpression);

		}
		
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
