/*
* Social World
* Copyright (C) 2024  Mathias Sikos
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
package org.socialworld.calculation;

public class NoObject {

	private NoObjectReason reason;
	
	private static NoObject noObjectBecauseValueIsNull = new NoObject(NoObjectReason.valueIsNull);
	private static NoObject noObjectBecauseValueIsNotValid = new NoObject(NoObjectReason.valueIsNotValid);
	private static NoObject noObjectBecauseValueIsProtectedAndNotPermitted = new NoObject(NoObjectReason.valueIsProtectedAndNotPermitted);
	private static NoObject noObjectBecauseValueIsNoValueProperty = new NoObject(NoObjectReason.valueIsNoValueProperty);
	private static NoObject noObjectBecauseTypeMismatchForGetObject = new NoObject(NoObjectReason.typeMismatchForGetObject);
	private static NoObject noObjectBecauseInstanceOfCheckFailed = new NoObject(NoObjectReason.instanceOfCheckFailed);
	private static NoObject noObjectBecauseObjectRequesterGetObjectNothingNotImplementedForType = new NoObject(NoObjectReason.objectRequesterGetObjectNothingNotImplementedForType);
	private static NoObject noObjectBecauseIsNull = new NoObject(NoObjectReason.isNull);
	
	
	
	private NoObject(NoObjectReason reason) {
		this.reason = reason;
	}
	
	public static NoObject getNoObject(NoObjectReason reason) {
		
		switch (reason) {
			case valueIsNull: 
				if (noObjectBecauseValueIsNull == null) noObjectBecauseValueIsNull = new NoObject(NoObjectReason.valueIsNull);
				return noObjectBecauseValueIsNull;
			case valueIsNotValid: 
				if (noObjectBecauseValueIsNotValid == null) noObjectBecauseValueIsNotValid = new NoObject(NoObjectReason.valueIsNotValid);
				return noObjectBecauseValueIsNotValid;
			case valueIsProtectedAndNotPermitted: 
				if (noObjectBecauseValueIsProtectedAndNotPermitted == null) 
					noObjectBecauseValueIsProtectedAndNotPermitted = new NoObject(NoObjectReason.valueIsProtectedAndNotPermitted);
				return noObjectBecauseValueIsNull;
			case valueIsNoValueProperty: 
				if (noObjectBecauseValueIsNoValueProperty == null) noObjectBecauseValueIsNoValueProperty = new NoObject(NoObjectReason.valueIsNoValueProperty);
				return noObjectBecauseValueIsNoValueProperty;
			case typeMismatchForGetObject: 
				if (noObjectBecauseTypeMismatchForGetObject == null) noObjectBecauseTypeMismatchForGetObject = new NoObject(NoObjectReason.typeMismatchForGetObject);
				return noObjectBecauseTypeMismatchForGetObject;
			case instanceOfCheckFailed: 
				if (noObjectBecauseInstanceOfCheckFailed == null) noObjectBecauseInstanceOfCheckFailed = new NoObject(NoObjectReason.instanceOfCheckFailed);
				return noObjectBecauseInstanceOfCheckFailed;
			case objectRequesterGetObjectNothingNotImplementedForType: 
				if (noObjectBecauseObjectRequesterGetObjectNothingNotImplementedForType == null) noObjectBecauseObjectRequesterGetObjectNothingNotImplementedForType = new NoObject(NoObjectReason.objectRequesterGetObjectNothingNotImplementedForType);
				return noObjectBecauseObjectRequesterGetObjectNothingNotImplementedForType;
			default:
				if (noObjectBecauseIsNull == null) noObjectBecauseIsNull = new NoObject(NoObjectReason.isNull);
				return noObjectBecauseIsNull;
		}
		
	}
	
	public static boolean checkNoObjectMatchesReason(NoObject object, NoObjectReason reason) {
		return (object.reason.equals(reason));
	}
	
	public NoObjectReason getReason() {
		return reason;
	}
}
