package org.socialworld.attributes.properties;

public abstract class SharesPropertyBase {

	private static int INDEX_NOTHING = 0;
	
	private float[] sharesMain;

	protected abstract int getMainSharesComponentCount();
	protected abstract float[] getMainNothing();
	
	protected SharesPropertyBase(float[] sharesMain) {
		setMainShares(sharesMain);
	}
	
	private void setMainShares(float[] sharesMain) {
		
		float nothing = checkShares(sharesMain, getMainSharesComponentCount());
		
		if (nothing >= 1.0F) {
			this.sharesMain = getMainNothing();
		}
		else {
			this.sharesMain = sharesMain;
			this.sharesMain[INDEX_NOTHING] = this.sharesMain[INDEX_NOTHING] + nothing;
		}

	}
	
	public float[] getSharesMain() {
		return sharesMain.clone();
	}
	
	protected float checkShares(float[] shares, int number) {
		
		float sum = 0.0F;
		if (shares.length != number) return 1.0F;
		
		for (int i = 0; i < shares.length; i++) {
			sum = sum + shares[i];
		}
		
		if (sum == 1) 
			return 0.0F;
		else
			return 1.0F - sum;
	}

}
