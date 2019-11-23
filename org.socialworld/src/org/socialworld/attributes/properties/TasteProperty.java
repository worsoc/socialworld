package org.socialworld.attributes.properties;

public class TasteProperty extends SharesPropertyBase {

	public static float[] nothing = {1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F};
	
	public static int TASTEPROP_COUNT = 6;
	
	public static int TASTEPROP_INDEX_NOTHING = 0;
	public static int TASTEPROP_INDEX_SWEET  	= 1;
	public static int TASTEPROP_INDEX_SALTY  	= 2;
	public static int TASTEPROP_INDEX_SOUR  	= 3;
	public static int TASTEPROP_INDEX_BITTER  = 4;
	public static int TASTEPROP_INDEX_UMAMI  	= 5;


	public TasteProperty(float[] sharesTaste) {
		super(sharesTaste);
	}

	public TasteProperty(TasteProperty original) {
		super(original);
	}

	protected int getMainSharesComponentCount() { return TASTEPROP_COUNT;}
	protected float[] getMainNothing() {return TasteProperty.nothing; }
	
	public float getShareSweet() { return getSharesMain()[TASTEPROP_INDEX_SWEET]; }
	public float getShareSalty() { return getSharesMain()[TASTEPROP_INDEX_SALTY]; }
	public float getShareSour() { return getSharesMain()[TASTEPROP_INDEX_SOUR]; }
	public float getShareBitter() { return getSharesMain()[TASTEPROP_INDEX_BITTER]; }
	public float getShareUmami() { return getSharesMain()[TASTEPROP_INDEX_UMAMI]; }
	
}
