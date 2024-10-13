package org.socialworld.core;

import org.socialworld.calculation.PropertyUsingAs;

public interface IAccessToken {

	public boolean isValid();
	
	default  PropertyUsingAs[] getPossibleUsingAs() {return new PropertyUsingAs[0];}
	default boolean isWithoutRestrictions() {return true;}
	
}
