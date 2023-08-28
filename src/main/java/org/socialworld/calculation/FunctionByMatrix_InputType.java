/*
* Social World
* Copyright (C) 2014  Mathias Sikos
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

/**
 * The enumeration collects all calculation input types.
 * @author Mathias Sikos (MatWorsoc)
 *   NewAttributeValue - the input value is a new attribute value,
 *   	 that means it is the attribute of the last attribute calculation
 *   	it corresponds to AttributeArray.attributes[i]	
 *   AttributeChange - the input value is the difference of new and old attribute value
 *   	it corresponds to AttributeArray.differences[i]	
 *   OldAttributeValue - the input value is an old attribute value,
 *   	 that means it is the attribute value of the attribute calculation before
 *   	it corresponds to ( AttributeArray.attributes[i] - AttributeArray.differences[i] )	
 *   	  
 */
public enum FunctionByMatrix_InputType {
	NewAttributeValue, AttributeChange, OldAttributeValue 
}
