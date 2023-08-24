package org.socialworld.tools;

public class StringTupel {

	
	private String[] tupel = new String[0];
	
	public StringTupel(String[] tupel) {
		this.tupel = tupel;
	}
	
	public StringTupel(String left, String right) {
		this.tupel = new String[2];
		this.tupel[0] = left;
		this.tupel[1] = right;
	}
	
	public String get(int index) {
		if (this.tupel.length > index) 
			return this.tupel[index];
		else
			return "";
	}
	
	// deprecated methods
	
	public String getLeft() { 
		if (this.tupel.length > 0) 
			return this.tupel[0];
		else
			return "";
		}
	
	public String getRight() { 
		if (this.tupel.length > 1) 
			return this.tupel[1];
		else
			return "";
	}

}
