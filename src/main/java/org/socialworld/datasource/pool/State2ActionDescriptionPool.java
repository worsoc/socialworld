package org.socialworld.datasource.pool;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.calculation.Expression;
import org.socialworld.calculation.descriptions.DescriptionBase;
import org.socialworld.calculation.descriptions.State2ActionDescription;
import org.socialworld.calculation.expressions.CreateActionExpression;
import org.socialworld.calculation.expressions.Nothing;

public class State2ActionDescriptionPool extends DescriptionPool {
	

	private static State2ActionDescriptionPool instance;
	
	
	private State2ActionDescriptionPool () {
		
		super(1, GaussPoolState2ActionType.CAPACITY_GPS2A_ARRAY);
		
		this.descriptions = new State2ActionDescription[sizeDescriptionsArray];
		
		initialize();
		
	}
	
	public static State2ActionDescriptionPool getInstance() {
		if (instance == null) {
			instance = new State2ActionDescriptionPool();
		}
		return instance;
	}
	
	protected final void initialize() {
		initializeFromLines();
	}

	@Override
	protected void initializeWithTestData(InitializeDataModus modus) {
		
		switch (modus) {
		case lines: 
			initializeWithTestData_Lines();
		
		default:
			// do nothing
		}
	}

	protected  final DescriptionBase getNewDescription() {
		return new State2ActionDescription();
	}

	protected final DescriptionBase getDescription(String description) {
		return new State2ActionDescription(getGsonInstance(), description);
	}

	protected final Expression getStartExpressionForLines(List<String> lines4OneExpression) {
		return new CreateActionExpression(lines4OneExpression);
	}

	protected final Expression getStartExpressionForIDs(List<Integer> ids4OneExpression) {
		return  Nothing.getInstance();
	}
	
	private void initializeWithTestData_Lines() {
		
		
		List<Lines> allLines;
		allLines = new ArrayList<Lines>();
		
		Lines lines;

		int state2ActionType;

		lines = new Lines(0, rangeSecondIndex);
		for ( state2ActionType = 0; state2ActionType < rangeSecondIndex; state2ActionType++) {
			lines.add(state2ActionType, 0, "WENN curiosity >= 55 & curiosity < 65 & power >= 30 DANN <TYPE><Const>1</Const></TYPE><MODE><Const>11</Const></MODE><MINTIME><Now+N>1000</Now+N></MINTIME><MAXTIME><Now+N>5000</Now+N></MAXTIME><PRIORITY><Const>50</Const></PRIORITY><INTENSITY><MX+N>8;1.5;23</MX+N></INTENSITY><DURATION><Const>2000</Const></DURATION><DIRECTION><Const>(2,7,0)</Const></DIRECTION>");
			lines.add(state2ActionType, 1, "WENN courage < 15 & mood < 15 & power >= 15 & power < 75 DANN <TYPE><Const>10</Const></TYPE><MODE><Const>102</Const></MODE><MINTIME><Now+N>1000</Now+N></MINTIME><MAXTIME><Now+N>5000</Now+N></MAXTIME><PRIORITY><Const>70</Const></PRIORITY><INTENSITY><MX+N>8;3;0</MX+N></INTENSITY><DURATION><Const>1000</Const></DURATION>");
			lines.add(state2ActionType, 2, "WENN mood >= 23 & mood < 40 & power >= 50 DANN <TYPE><Const>1</Const></TYPE><MODE><Const>12</Const></MODE><MINTIME><Now+N>1000</Now+N></MINTIME><MAXTIME><Now+N>5000</Now+N></MAXTIME><PRIORITY><Const>60</Const></PRIORITY><INTENSITY><MX+N>8;1;0</MX+N></INTENSITY><DURATION><Const>5000</Const></DURATION><DIRECTION><Const>(-3.21,2.09,0)</Const></DIRECTION>");
			lines.add(state2ActionType, 3, "WENN tiredness >= 70 DANN <TYPE><Const>0</Const></TYPE><MODE><Const>1</Const></MODE><MINTIME><Now+N>10000</Now+N></MINTIME><MAXTIME><Now+N>100000</Now+N></MAXTIME><PRIORITY><Const>40</Const></PRIORITY><INTENSITY><MX+N>4;1.5;0</MX+N></INTENSITY><DURATION><Const>610000</Const></DURATION>");
		}
		allLines.add(lines);
		
		
// 		lines.add(0, 0, "WENN hunger >= 45 & courage >= 45 DANN <TYPE><Const>4</Const></TYPE><MODE><Const>41</Const></MODE><MINTIME><Now+N>1000</Now+N></MINTIME><MAXTIME><Now+N>50000</Now+N></MAXTIME><PRIORITY><Const>100</Const></PRIORITY><INTENSITY><MX+N>7;1;0</MX+N></INTENSITY><DURATION><Const>1000</Const></DURATION>");
// 		lines.add(0, 0, "WENN hunger >= 45 & courage >= 45 DANN <TYPE><Const>4</Const></TYPE><MODE><Const>44</Const></MODE><MINTIME><Now+N>1000</Now+N></MINTIME><MAXTIME><Now+N>50000</Now+N></MAXTIME><PRIORITY><Const>99</Const></PRIORITY><INTENSITY><MX+N>7;1;0</MX+N></INTENSITY><DURATION><Const>1000</Const></DURATION><INVENTORYPLACE><Const>3</Const></INVENTORYPLACE>");


		createExpressions(allLines);
		
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
