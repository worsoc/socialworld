package org.socialworld.objects.concrete.animals;

import org.socialworld.attributes.Direction;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.core.IAccessToken;

public interface IFlying {

public static final String NAME = "IFlying";

	public abstract float getWidthWings();
	public abstract float getNumberWings();
	public abstract Direction getDirectionFly();
	
	public StateFlying getSavedStateFlying(IAccessToken token);
	public ValueProperty getStateFlyingAsProperty(IAccessToken token, String name);
}
