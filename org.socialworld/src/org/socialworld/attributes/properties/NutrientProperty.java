package org.socialworld.attributes.properties;

public class NutrientProperty extends SharesPropertyBase {

	public static float[] nothing = {1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F};
	
	public static int NUTPROP_COUNT = 8;

	public static int NUTPROP_INDEX_NOTHING  		= 0;
	public static int NUTPROP_INDEX_FAT  			= 1;
	public static int NUTPROP_INDEX_PROTEIN  		= 2;
	public static int NUTPROP_INDEX_CARBOHYDRATES  	= 3;
	public static int NUTPROP_INDEX_WATER  			= 4;
	public static int NUTPROP_INDEX_ROUGHAGE  		= 5;
	public static int NUTPROP_INDEX_VITAMINS  		= 6;
	public static int NUTPROP_INDEX_MINERALS  		= 7;
	
	public NutrientProperty(float[] sharesNutrientMain) {
		super(sharesNutrientMain);
	}
	
	protected int getMainSharesComponentCount() { return NUTPROP_COUNT;}
	protected float[] getMainNothing() {return NutrientProperty.nothing; }

	public float getShareCarbohydrates() { return getSharesMain()[NUTPROP_INDEX_CARBOHYDRATES];}
	public float getShareFat() { return getSharesMain()[NUTPROP_INDEX_FAT];}
	public float getShareProtein() { return getSharesMain()[NUTPROP_INDEX_PROTEIN];}
	public float getShareMinerals() { return getSharesMain()[NUTPROP_INDEX_MINERALS];}
	public float getShareWater() { return getSharesMain()[NUTPROP_INDEX_WATER];}
	public float getShareVitamins() { return getSharesMain()[NUTPROP_INDEX_VITAMINS];}
	public float getShareRoughage() { return getSharesMain()[NUTPROP_INDEX_ROUGHAGE];}
	
	
}
