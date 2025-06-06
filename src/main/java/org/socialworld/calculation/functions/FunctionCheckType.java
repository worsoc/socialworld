package org.socialworld.calculation.functions;

import org.socialworld.calculation.FunctionBase;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.collections.ValueArrayList;

public class FunctionCheckType extends FunctionBase {

	Type type;
	
	public FunctionCheckType(Type type) {
		this.type = type;
	}

	@Override
	public Value calculate(ValueArrayList arguments) {

		if (arguments.size() < 0) {
			return new Value(Type.integer, 0);
		}
		else {
			if (arguments.get(0).getType() == type) {
				return new Value(Type.integer, 1);
			}
			else {
				return new Value(Type.integer, 0);
			}
		}
		
		
			
	}

}
