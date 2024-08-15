/*
* Social World
* Copyright (C) 2014  Mathias Sikos
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
import org.socialworld.calculation.descriptions.DescriptionBase;
import com.google.gson.Gson;

public abstract class DescriptionPool {

	protected int rangeMainIndex;
	protected int rangeSecondIndex;
	protected int sizeDescriptionsArray;

	protected DescriptionBase descriptions[];
	private List<List<FunctionByExpression>> expressions;

	private static Gson gson = null;
	protected static Gson getGsonInstance() {
		if (gson == null) {
			gson = new Gson();
		}
		return gson;
	}
	
	protected DescriptionPool(int rangeMainIndex, int rangeSecondIndex) {
		
		this.rangeMainIndex = rangeMainIndex;
		this.rangeSecondIndex = rangeSecondIndex;
		this.sizeDescriptionsArray = rangeMainIndex * rangeSecondIndex;

		this.expressions = new ArrayList<List<FunctionByExpression>>(sizeDescriptionsArray);
		ArrayList<FunctionByExpression> nothing;
		nothing = new  ArrayList<FunctionByExpression>();
		for (int i = 0; i < sizeDescriptionsArray; i++) {
			this.expressions.add(nothing);
		}


	}
	
	protected abstract void initialize();
	protected abstract DescriptionBase getNewDescription();
	protected abstract DescriptionBase getDescription(String description);
	protected abstract Expression getStartExpressionForLines(List<String> lines4OneExpression);
	protected abstract Expression getStartExpressionForIDs(List<Integer> ids4OneExpression);
	protected abstract void initializeWithTestData(InitializeDataModus modus);

	protected void initializeFromLines() {

		int index;
		
		DescriptionBase description;
		List<FunctionByExpression> oneDescriptionExpressions;
		
		initializeWithTestData(InitializeDataModus.lines);

		for (int mainIndex = 0; mainIndex < rangeMainIndex; mainIndex++) {
		
			for (int secondIndex = 0; secondIndex < rangeSecondIndex; secondIndex++) {
				
				index = mainIndex * rangeSecondIndex + secondIndex;
				oneDescriptionExpressions = this.expressions.get(index);
				
				description = getNewDescription();
				
				if (oneDescriptionExpressions != null) {
					
					for (int i = 0; i < oneDescriptionExpressions.size(); i++) {
						description.addFunction(oneDescriptionExpressions.get(i));
					}
					
				}
				
				descriptions[index] = description;
		
			}
			
		}
			
	}

	protected void initializeFromJson() {

		int index;
		
		DescriptionBase description;
		List<FunctionByExpression> oneDescriptionExpressions;
		
		initializeWithTestData(InitializeDataModus.json);

		for (int mainIndex = 0; mainIndex < rangeMainIndex; mainIndex++) {
		
			for (int secondIndex = 0; secondIndex < rangeSecondIndex; secondIndex++) {
				
				index = mainIndex * rangeSecondIndex + secondIndex;
				oneDescriptionExpressions = this.expressions.get(index);
				
				description = getNewDescription();
				
				if (oneDescriptionExpressions != null) {
					
					for (int i = 0; i < oneDescriptionExpressions.size(); i++) {
						description.addFunction(oneDescriptionExpressions.get(i));
					}
					
				}
				
				descriptions[index] = description;
		
			}
			
		}
			
	}

	public DescriptionBase getDescription(int mainIndex,	int secondIndex) {
		int index;

		DescriptionBase description = null;
		
		index = mainIndex *  rangeSecondIndex + secondIndex ;
		
		if (index >= 0 & sizeDescriptionsArray > index) 	
			description = descriptions[index];
		else
			// create a dummy description with an expression that returns the invalid "nothing" value
			description = getNewDescription();
			
		return description;
	}
	
	protected final void createDescriptionsAndExpressions(List<Jsons> allJsons) {
		
		int indexDescriptions;
		int lfdNr;
		DescriptionBase description;
		List<FunctionByExpression> oneDescriptionExpressions;
		List<String> jsons4OneDescription;
		Jsons jsons;
		int secondIndex;
		
		for (int index = 0; index < allJsons.size(); index++ ) {
			jsons = allJsons.get(index);
			indexDescriptions = jsons.getMainIndex() * rangeSecondIndex;
			for ( secondIndex = 0; secondIndex < rangeSecondIndex; secondIndex++) {
				jsons4OneDescription = jsons.getJsons(secondIndex);
				for (lfdNr = 0; lfdNr < jsons4OneDescription.size(); lfdNr++) {
					description = getDescription(jsons4OneDescription.get(lfdNr));
					description.setFunctions();
				}
			}
		}
	
	}

	
	protected final void createExpressionsForIDs(List<DescriptionIDs> dbTableIDs) {
		
		int indexExpressions;
		int lfdNr;
		Expression startExpression;
		List<FunctionByExpression> oneDescriptionExpressions;
		List<List<Integer>> ids4OneDescriptionExpressions;
		DescriptionIDs ids;
		int secondIndex;
		
		for (int index = 0; index < dbTableIDs.size(); index++ ) {
			ids = dbTableIDs.get(index);
			indexExpressions = ids.getMainIndex() * rangeSecondIndex;
			for ( secondIndex = 0; secondIndex < rangeSecondIndex; secondIndex++) {
				ids4OneDescriptionExpressions = ids.getIDs(secondIndex);
				oneDescriptionExpressions = new ArrayList<FunctionByExpression>();
				for (lfdNr = 0; lfdNr < ids4OneDescriptionExpressions.size(); lfdNr++) {
					startExpression = getStartExpressionForIDs(ids4OneDescriptionExpressions.get(lfdNr));
					oneDescriptionExpressions.add(new FunctionByExpression(startExpression));
				}
				this.expressions.set(indexExpressions + secondIndex, oneDescriptionExpressions);
			}
		}
	
	}
	
	
	protected final void createExpressions(List<Lines> allLines) {
		
		int indexExpressions;
		int lfdNr;
		Expression startExpression;
		List<FunctionByExpression> oneDescriptionExpressions;
		List<List<String>> lines4OneDescriptionExpressions;
		Lines lines;
		int secondIndex;
		
		for (int index = 0; index < allLines.size(); index++ ) {
			lines = allLines.get(index);
			indexExpressions = lines.getMainIndex() * rangeSecondIndex;
			for ( secondIndex = 0; secondIndex < rangeSecondIndex; secondIndex++) {
				lines4OneDescriptionExpressions = lines.getLines(secondIndex);
				oneDescriptionExpressions = new ArrayList<FunctionByExpression>();
				for (lfdNr = 0; lfdNr < lines4OneDescriptionExpressions.size(); lfdNr++) {
					startExpression = getStartExpressionForLines(lines4OneDescriptionExpressions.get(lfdNr));
					oneDescriptionExpressions.add(new FunctionByExpression(startExpression));
				}
				this.expressions.set(indexExpressions + secondIndex, oneDescriptionExpressions);
			}
		}
	
	}
	
	
/*
	protected void addExpression(Expression expression) {
		int ID;
	
		ID = expression.getID();
		if (ID > sizeExpressionsArray) ensureCapacity(ID);
		
		expressions.set(ID - 1, expression);
	}
	
	protected void ensureCapacity(int capacity) {
		if (capacity > sizeExpressionsArray) {
			for (int i = 1; i <= capacity - sizeExpressionsArray; i++)
				expressions.add(null);
			sizeExpressionsArray = capacity;
		}
	}

	protected void setTrueAndFalseExpressions() {
		ListIterator<Expression> iterator;
		Expression expression;

		int IDTrue;
		int IDFalse;
		
		iterator = expressions.listIterator();
		
		while (iterator.hasNext()) {
			expression = iterator.next();
			
			if (expression.getID() > 0 ) {
				IDTrue = expression.get_ID_Exp2();
				IDFalse = expression.get_ID_Exp3();
				
				if (IDTrue > 0) {
					expression.setTrueExpression(expressions.get(IDTrue - 1));
				}
				if (IDFalse > 0) {
					expression.setFalseExpression(expressions.get(IDFalse - 1));
				}
			}
		}
	}
*/
}
