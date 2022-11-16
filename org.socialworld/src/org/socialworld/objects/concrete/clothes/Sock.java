package org.socialworld.objects.concrete.clothes;

import org.socialworld.objects.Item;
import org.socialworld.objects.properties.IWearable;

public abstract class Sock extends Item implements IWearable {

	public boolean isLeftHandGlove() {return false; }
	public boolean isRightHandGlove() {return false; }

	public boolean isLeftFootShoe() {return false; }
	public boolean isRightFootShoe() {return false; }

	public boolean isShirt() {return false; }
	public boolean isTrousers() {return false; }
	public boolean isCap() {return false; }

}
