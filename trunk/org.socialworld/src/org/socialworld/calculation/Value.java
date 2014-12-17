package org.socialworld.calculation;

public class Value {

	Type type;
	Object value;
	
	
	public Value(Type type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	public Value(String valueAsString, Type castToType) {
		this.type = castToType;

		switch (castToType) {
		case integer:
			this.value = Integer.parseInt(valueAsString);
			break;
		case longinteger:
			this.value = Integer.parseInt(valueAsString);
			break;
		case floatingpoint:
			this.value = Float.parseFloat(valueAsString);
			break;
		default:
			
		}
		
	}
	
	public Type getType() { return type; };
	
	public Object getValue() { return value; };

	public Object getValueCopy() { 
		switch (type) {
		case integer:
			return (int) value;
		case longinteger:
			return (long) value;
		case floatingpoint:
			return (double) value;
		case actionType:
			return value;
		default:
			return null;
		}
	};
	
	
	public boolean isTrue() {
		switch (type) {
		case bool:
			return (boolean) value;
		case integer:
			return (int) value > 0;
		case longinteger:
			return (long) value > 0;
		case floatingpoint:
			return (double) value > 0;
		default:
			return false;
		}
	}
}
