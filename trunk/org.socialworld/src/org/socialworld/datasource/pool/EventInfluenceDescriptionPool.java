/*
* Social World
* Copyright (C) 2019  Mathias Sikos
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
package org.socialworld.datasource.pool;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.calculation.Expression;
import org.socialworld.calculation.FunctionByExpression;
import org.socialworld.calculation.descriptions.EventInfluenceDescription;
import org.socialworld.calculation.expressions.ChangeAttributes;
import org.socialworld.calculation.expressions.Nothing;
import org.socialworld.calculation.expressions.Sequence;
import org.socialworld.core.EventType;
import org.socialworld.datasource.tablesPool.TablePoolEID;

public class EventInfluenceDescriptionPool extends DescriptionPool {

	public static final int COUNT_FbE_TEST_ENTRIES = 2;		// Anzahl Testeintraege FunctionByExpression
	
	private static EventInfluenceDescriptionPool instance;
	
	private static EventInfluenceDescription descriptions[];
	private FunctionByExpression expressions[];
	
	private EventInfluenceDescriptionPool () {
		
		sizeDescriptionsArray = EventType.MAX_EVENT_TYPE * GaussPoolInfluenceType.CAPACITY_GPIT_ARRAY;
		descriptions = new EventInfluenceDescription[sizeDescriptionsArray];

		expressions = new FunctionByExpression[COUNT_FbE_TEST_ENTRIES];

		initializeWithTestData_FunctionByExpression();
		
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
		
		EventInfluenceDescription description;
		
		index = eventType * GaussPoolInfluenceType.CAPACITY_GPIT_ARRAY + influenceType;
		
		if (index >= 0 & sizeDescriptionsArray > index) 
			description = descriptions[index];
		else 
			// create a dummy description with an expression that returns the invalid "nothing" value
			description = new EventInfluenceDescription();
		
		return description;
	}

	public void setDescription(int eventType,	int influenceType, EventInfluenceDescription eid) {
		int index;
			
		index = eventType * GaussPoolInfluenceType.CAPACITY_GPIT_ARRAY + influenceType;
		
		if (index >= 0 & sizeDescriptionsArray > index) 
			 descriptions[index] = eid;
		
	}

	
	private void initializeWithTestData_FunctionByExpression() {
		
		int 		expressionsCount = COUNT_FbE_TEST_ENTRIES;
		List<List<String>> expressions = new ArrayList<List<String>>(expressionsCount);
		List<String> lines;
		Expression startExpression = Nothing.getInstance();

		lines = new ArrayList<String>(2);
		lines.add("WENN mood > 45 & mood < 52 DANN <MOOD><MX+N>0;1.2;23</MX+N></MOOD><COURAGE><MX+1>1;1;1</MX+N></COURAGE>");
		lines.add("WENN mood > 70 & mood < 85 DANN <MOOD><MX+N>0;0.9;-10</MX+N></MOOD><COURAGE><MX+1>1;1;-1</MX+N></COURAGE>");
		expressions.add(lines);
		
		lines = new ArrayList<String>(1);
		lines.add("WENN power > 80 & morals > 60 DANN <POWER><MX+N>8;1;-1</MX+N></POWER><MORALS><MX+1>2;1;-1</MX+N></MORALS>");
		expressions.add(lines);

		for (int i = 0; i <  expressionsCount; i++) {
			startExpression = new ChangeAttributes(expressions.get(i));
			this.expressions[i] = new FunctionByExpression(startExpression);

		}
		
	}
	
	
	protected void initialize() {

		EventInfluenceDescription description;
		int indexExps;
		int linesCount = COUNT_FbE_TEST_ENTRIES;
		
		for (int index = 0; index < sizeDescriptionsArray; index++) {
			
			description = new EventInfluenceDescription();
			for (indexExps = 0; indexExps <  linesCount; indexExps++) {
				description.addFunctionEventInfluence(expressions[indexExps]);
			}
			descriptions[index] = description;
	
		}
		
		//loadFromDB();
	}

	
	private void loadFromDB() {
		TablePoolEID tableEID;

		int rowCountEID;
		int rowEID;
		
		int eventType;
		int influenceType;
		int exp_lfd_nr = 0;
		int exp_lfd_nr_last = 0;
		int exp_id;
		
		
		Expression[] expressions = new Expression[1];
		
		EventInfluenceDescription eid;
		Expression exp;
		
		tableEID = new TablePoolEID();


		tableEID.select(tableEID.SELECT_ALL_COLUMNS, "", "ORDER BY eventType, influenceType, exp_lfd_nr desc");
		rowCountEID = tableEID.rowCount();
		
		if (rowCountEID > 0) {
				
			for (rowEID = 0; rowEID < rowCountEID; rowEID++) {
				exp_lfd_nr = tableEID.getExpLfdNr(rowEID);
				// assumption: exp_lfd_nr is greater than zero
				if (exp_lfd_nr > 0) {
					eventType = tableEID.getEventType(rowEID);
					influenceType = tableEID.getInfluenceType(rowEID);
					exp_id = tableEID.getExpression(rowEID);
					
					exp = ExpressionPool.getInstance().getExpression(exp_id);
					
					// assumption: there are more than one expression describing the event influence
					if  (exp_lfd_nr > exp_lfd_nr_last)   {
						eid = new EventInfluenceDescription(new Sequence(expressions));
						setDescription(eventType, influenceType, eid);
						
						expressions = new Expression[exp_lfd_nr];
					}
					expressions[exp_lfd_nr - 1] = exp;
					exp_lfd_nr_last = exp_lfd_nr;
					
					if (rowEID == rowCountEID - 1) {
						eid = new EventInfluenceDescription(new Sequence(expressions));
						setDescription(eventType, influenceType, eid);
					}
				}	
			}
		}
	}
	
	
	/*
	private void initializeFromFile() {
		
		Expression expression;
		
		EventInfluenceDescription eid;
		
		
		int index;
		int eventType = 0;
		int influenceType = 0;
		

		// temporary initialized with Expression
		// expression and eid must be initialized
		expression = new Expression();
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
					index = eventType * InfluenceTypePool.CAPACITY_ITP_ARRAY + influenceType;
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



				if (line.startsWith("<EventInfluenceExp>")) {
					expression = new Expression();
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
*/

}
