/*
* Social World
* Copyright (C) 2019  Mathias Sikos
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.  
*
* or see http://www.gnu.org/licenses/gpl-2.0.html
*
*/
package org.socialworld.attributes.properties;

public abstract class SharesPropertyBase {

	private static int INDEX_NOTHING = 0;
	
	private float[] sharesMain;

	protected abstract int getMainSharesComponentCount();
	protected abstract float[] getMainNothing();
	
	protected SharesPropertyBase(float[] sharesMain) {
		setMainShares(sharesMain);
	}
	
	protected SharesPropertyBase(SharesPropertyBase original) {
		setMainShares(original.sharesMain);
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
