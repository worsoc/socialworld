package org.socialworld.calculation;


public class Calculation {

	private static Calculation instance;
	
	Value nothing;
	
	public static Calculation getInstance() {
		if (instance == null) {
			instance = new Calculation();
		}
		return instance;
		
	}
	
	private Calculation() {
		nothing = createValue(Type.nothing, null);
		
	}
	
	public  Value createValue(Type type, Object value) {
		return new Value(type, value);
	}
	
	public Value or(Value op1, Value op2) {
		return createValue(Type.bool, op1.isTrue() | op2.isTrue());
	}

	public Value and(Value op1, Value op2) {
		return createValue(Type.bool, op1.isTrue() & op2.isTrue());
	}

	public Value compare(Value op1, Value op2) {
		// TODO
		return createValue(Type.bool, false);
	}

	public Value addition(Value op1, Value op2){
		switch (op1.getType() ) {
		case integer:
			switch (op2.getType() ) {
			case integer:
				return createValue(Type.integer, (int) op1.getValue() + (int) op2.getValue() );
			case floatingpoint:
				return createValue(Type.floatingpoint, (int) op1.getValue() + (double) op2.getValue() );
			default:
				return null;
			}
		case floatingpoint:
			switch (op2.getType() ) {
			case integer:
				return createValue(Type.floatingpoint, (double) op1.getValue() + (int) op2.getValue() );
			case floatingpoint:
				return createValue(Type.floatingpoint, (double) op1.getValue() + (double) op2.getValue() );
			default:
				return null;
			}
			
		default:
			return null;
		}
	}

	public Value subtraction(Value op1, Value op2){
		switch (op1.getType() ) {
		case integer:
			switch (op2.getType() ) {
			case integer:
				return createValue(Type.integer, (int) op1.getValue() - (int) op2.getValue() );
			default:
				return null;
			}
		default:
			return null;
		}
	}

	public Value multiplication(Value op1, Value op2){
		switch (op1.getType() ) {
		case integer:
			switch (op2.getType() ) {
			case integer:
				return createValue(Type.integer, (int) op1.getValue() * (int) op2.getValue() );
			case floatingpoint:
				return createValue(Type.floatingpoint, (int) op1.getValue() * (double) op2.getValue() );
			default:
				return null;
			}
		case floatingpoint:
			switch (op2.getType() ) {
			case integer:
				return createValue(Type.floatingpoint, (double) op1.getValue() * (int) op2.getValue() );
			case floatingpoint:
				return createValue(Type.floatingpoint, (double) op1.getValue() * (double) op2.getValue() );
			default:
				return null;
			}
		default:
			return null;
		}
	}

	public Value copy(Value original) {
		return createValue(original.getType(), original.getValueCopy() );
	}
	
	
	public Value getNothing() {
		return nothing;
	}
}
