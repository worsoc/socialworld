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
package org.socialworld.datasource.parsing;

public class ParseExpressionStrings {

	public static String getTagValue(String text, String tagName) {
		
		String openTag = "<" + tagName + ">";
		String closeTag = "</" + tagName + ">";
		
		int posOpenTag = text.indexOf(openTag);
		int posCloseTag = text.indexOf(closeTag);
		
		String tagValue;
		if ((posOpenTag >= 0) && (posCloseTag >= 0)) 		
			tagValue = text.substring(posOpenTag + openTag.length(), posCloseTag);
		else
			tagValue = "";
		
		return tagValue;
		
	}
	
	
	public static String[] getTagValue(String text, String[] tagNames) {
		
		String tagValue;
		String[] result = new String[2];
		
		for (int i = 0; i < tagNames.length; i++ ) {
			
			tagValue = getTagValue(text, tagNames[i]);
			
			if (tagValue.length() > 0) {
				result[0] = tagNames[i];
				result[1] = tagValue;
				break;
			}
		}
		
		return result;
	}
	
}
