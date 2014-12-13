package org.socialworld.calculation;

public class Argument {
	Object argument;
	ArgumentType type;
	
	public Argument(ArgumentType type, Object argument) { 
		this.argument = argument; 
		this.type = type;
	}
	
	public void setArgument(Object argument) { this.argument = argument; }
	
	public Object getArgument() { return argument; }
	
	public Object getType() { return type; }

}
