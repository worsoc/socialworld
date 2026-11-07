/*
 * Social World
 * Copyright (C) 2012  Mathias Sikos
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://gnu.org>.
 */
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
		return CreateActionExpression.createActionExpression(lines4OneExpression);
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
			lines.add(state2ActionType, 0, "WENN curiosity >= 5500 & curiosity < 6500 & power >= 3000 DANN <TYPE><Const>1</Const></TYPE><MODE><Const>11</Const></MODE><MINTIME><Now+N>1000</Now+N></MINTIME><MAXTIME><Now+N>5000</Now+N></MAXTIME><PRIORITY><Const>50</Const></PRIORITY><INTENSITY><MX+N>8;0.015;23</MX+N></INTENSITY><DURATION><Const>2000</Const></DURATION><DIRECTION><Const>(2,7,0)</Const></DIRECTION>");
			lines.add(state2ActionType, 1, "WENN courage < 1500 & mood < 1500 & power >= 1500 & power < 7500 DANN <TYPE><Const>10</Const></TYPE><MODE><Const>102</Const></MODE><MINTIME><Now+N>1000</Now+N></MINTIME><MAXTIME><Now+N>5000</Now+N></MAXTIME><PRIORITY><Const>70</Const></PRIORITY><INTENSITY><MX+N>8;0.03;0</MX+N></INTENSITY><DURATION><Const>1000</Const></DURATION>");
			lines.add(state2ActionType, 2, "WENN mood >= 2300 & mood < 4000 & power >= 5000 DANN <TYPE><Const>1</Const></TYPE><MODE><Const>12</Const></MODE><MINTIME><Now+N>1000</Now+N></MINTIME><MAXTIME><Now+N>5000</Now+N></MAXTIME><PRIORITY><Const>60</Const></PRIORITY><INTENSITY><MX+N>8;0.01;0</MX+N></INTENSITY><DURATION><Const>5000</Const></DURATION><DIRECTION><Const>(-3.21,2.09,0)</Const></DIRECTION>");
			lines.add(state2ActionType, 3, "WENN tiredness >= 7000 DANN <TYPE><Const>0</Const></TYPE><MODE><Const>1</Const></MODE><MINTIME><Now+N>10000</Now+N></MINTIME><MAXTIME><Now+N>100000</Now+N></MAXTIME><PRIORITY><Const>40</Const></PRIORITY><INTENSITY><MX+N>4;0.015;0</MX+N></INTENSITY><DURATION><Const>610000</Const></DURATION>");
		}
		allLines.add(lines);
		
		
// 		lines.add(0, 0, "WENN hunger >= 4500 & courage >= 4500 DANN <TYPE><Const>4</Const></TYPE><MODE><Const>41</Const></MODE><MINTIME><Now+N>1000</Now+N></MINTIME><MAXTIME><Now+N>50000</Now+N></MAXTIME><PRIORITY><Const>100</Const></PRIORITY><INTENSITY><MX+N>7;0.01;0</MX+N></INTENSITY><DURATION><Const>1000</Const></DURATION>");
// 		lines.add(0, 0, "WENN hunger >= 4500 & courage >= 4500 DANN <TYPE><Const>4</Const></TYPE><MODE><Const>44</Const></MODE><MINTIME><Now+N>1000</Now+N></MINTIME><MAXTIME><Now+N>50000</Now+N></MAXTIME><PRIORITY><Const>99</Const></PRIORITY><INTENSITY><MX+N>7;0.01;0</MX+N></INTENSITY><DURATION><Const>1000</Const></DURATION><INVENTORYPLACE><Const>3</Const></INVENTORYPLACE>");


		createExpressions(allLines);
		
	}
	
}
