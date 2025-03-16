/*
* Social World
* Copyright (C) 2020  Mathias Sikos
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
package org.socialworld.attributes;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.calculation.PropertyUsingAs;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.core.IAccessToken;

public class PropertyProtection {

	private static AccessTokenWithoutRestrictions tokenWithoutRestrictions = AccessTokenWithoutRestrictions.getValid();
	private IAccessToken token;
	private List<PropertyUsingAs> useAsPermissions;
	private PropertyUsingAs usedAs = null;
 
	boolean withTotalPermissions = false;
	
	List<ValueProperty> protectedValues;
	boolean allowOneAdding = false;
	
	private static PropertyProtection totalPermission;

	public static PropertyProtection getTotalPermission() {
		if (totalPermission == null) {
			totalPermission = new PropertyProtection();
		}
		return totalPermission;
	}

	private PropertyProtection() {
		this.token = tokenWithoutRestrictions;
		withTotalPermissions = true;
		this.allowOneAdding = false;
		this.useAsPermissions = new ArrayList<PropertyUsingAs>();
		this.protectedValues = new ArrayList<ValueProperty>();
	}
	
	
	private PropertyProtection(IAccessToken token, List<PropertyUsingAs> useAsPermissions) {
		this.token = token;
		this.useAsPermissions = useAsPermissions;
		this.usedAs = null;
		this.allowOneAdding = true;
		this.protectedValues = new ArrayList<ValueProperty>();
	}
	
	public static PropertyProtection getProtection(IAccessToken token) {
		
		return new PropertyProtection(token, token.getPossibleUsingAs());
		
	}

	public static PropertyProtection getProtection(List<PropertyUsingAs> useAsPermissions) {
		
		return new PropertyProtection(tokenWithoutRestrictions, useAsPermissions);
		
	}

	public static PropertyProtection getProtection(PropertyProtection original) {
		
		List<PropertyUsingAs> useAsPermissionsOriginal = original.getUseAsPermissions();
		return getProtection(useAsPermissionsOriginal);
		
	}

	public static PropertyProtection getProtection(ISavedValue value) {
		
		if (!value.isProtected()) {
			return getTotalPermission();
		}
		else {
			return new PropertyProtection(tokenWithoutRestrictions, tokenWithoutRestrictions.getPossibleUsingAs());
		}
		
	}

	public static PropertyProtection getProtection(IAccessToken token, ISavedValue value) {
		
		if (!value.isProtected() && token.isWithoutRestrictions()) {
			return getTotalPermission();
		}
		else {
			List<PropertyUsingAs> useAsPermissionsCluster = token.getPossibleUsingAs();
			List<PropertyUsingAs> reducedUseAsPermissions = value.getReducedUseAsPermissions(useAsPermissionsCluster);
			return new PropertyProtection(token, reducedUseAsPermissions);
		}
		
	}
	
	public static PropertyProtection getProtection(List<PropertyUsingAs> useAsPermissions, ISavedValue value) {
				
		List<PropertyUsingAs> reducedUseAsPermissions = value.getReducedUseAsPermissions(useAsPermissions);
		return new PropertyProtection(tokenWithoutRestrictions, reducedUseAsPermissions);
		
	}

	public static PropertyProtection getProtection(PropertyProtection original, ISavedValue value) {
		
		
		List<PropertyUsingAs> useAsPermissionsOriginal = original.getUseAsPermissions();
		boolean noMatch = value.checkUseAsPermissionsReductionNecessary(useAsPermissionsOriginal);
		if (noMatch) {
			return getProtection(useAsPermissionsOriginal, value);
		}
		else {
			original.allowOneAdding = true;
			return original;
		}
		
	}
	
	public static PropertyProtection getProtection(PropertyProtection original, IAccessToken token, ISavedValue value) {
		
		List<PropertyUsingAs> useAsPermissionsCluster = token.getPossibleUsingAs();
		List<PropertyUsingAs> useAsPermissionsIntersect = original.getIntersection(useAsPermissionsCluster);
		List<PropertyUsingAs> reducedUseAsPermissions = value.getReducedUseAsPermissions(useAsPermissionsIntersect);
		return new PropertyProtection(token, reducedUseAsPermissions);
		
	}

	public void addProtectedValueProperty(ValueProperty vp) {
		if (allowOneAdding) {
			protectedValues.add(vp);
		}
		allowOneAdding = false;
	}
	
	private List<PropertyUsingAs> getIntersection(List<PropertyUsingAs> toIntersectWith) {
		
		List<PropertyUsingAs> intersection = new ArrayList<PropertyUsingAs>();
		
		for (PropertyUsingAs a : this.useAsPermissions) {
			for (PropertyUsingAs b : toIntersectWith) {
				if (a.equals(b)) {
					intersection.add(b);
				}
			}
		}
		
		return intersection;
		
	}
	
	private List<PropertyUsingAs> getUseAsPermissions() {
		
		List<PropertyUsingAs> copy = new ArrayList<PropertyUsingAs>();
		
		for (PropertyUsingAs uap : this.useAsPermissions) {
			copy.add(uap);
		}
		
		return copy;
		
	}

	public boolean hasRestrictions() {
		return (!withTotalPermissions);
	}

	

	public IAccessToken getToken() {
		return this.token;
	}

	public boolean checkHasUseAsPermission(PropertyUsingAs useAsPermission) {
		
		for (PropertyUsingAs uap : this.useAsPermissions) {
			if  (uap.equals(useAsPermission)) return true;
		}
		// TEMP_SOLUTION
		return true;
// --> 		return false;
		
	}
	
	public boolean checkHasGetPermission(IAccessToken token) {
		
		
		// TODO check whether there is needed an extra method for checking get permission
		
		// there is a get permission if this.useAsPermissions and cluster's useAsPermissions are not intersection-free
		
		
		// TEMP_SOLUTION
		return true;

/*	--> wieder rein	
		List<PropertyUsingAs> tmp;
		tmp = cluster.getPossibleUsingAs();
		
		for (int indexA = 0; indexA < this.useAsPermissions.length; indexA++) {
			
			for (int indexB = 0; indexB < tmp.length; indexB++) {
			
				if (this.useAsPermissions[indexA].equals(tmp[indexB])) {
					
					return true;
					
				}
				
			}
			
		}

		return false;
*/		
	}

}
