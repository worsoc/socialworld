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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class ParseExpressionStrings {

    public static final Pattern TAG_PATTERN = Pattern.compile("<([^/ >]+)>");

    private static final Map<String, String[]> tagCache = new ConcurrentHashMap<>();

	public static String getTagValue(String text, String tagName) {
	
        if (text == null || text.isEmpty()) return "";

        String[] tags = tagCache.computeIfAbsent(tagName, name -> 
        	new String[] { "<" + name + ">", "</" + name + ">" }
        		);
    
	    String openTag = tags[0];
	    String closeTag = tags[1];
	
        int posOpenTag = text.indexOf(openTag);
        if (posOpenTag == -1) return ""; 

        int posValue =  posOpenTag + openTag.length();
        int posCloseTag = text.indexOf(closeTag, posValue);
	    
		
		String tagValue;
		if (posCloseTag >= 0)	
			tagValue = text.substring(posValue, posCloseTag);
		else
			tagValue = "";
		
		return tagValue;
		
	}
	
	
	public static String[] getTagValue(String text, String[] tagNames) {
	
	    if (text == null || text.isEmpty()) return new String[] {"", ""};

		
	    for (String tagName : tagNames) {
	        if (text.contains(tagName)) { 
	            String tagValue = getTagValue(text, tagName);
	            if (!tagValue.isEmpty()) {
	                return new String[] { tagName, tagValue };
	            }
	        }
		}

	    return new String[] {"", ""};
	}
	
}
