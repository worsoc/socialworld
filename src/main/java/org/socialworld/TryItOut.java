/*
 * Social World
 * Copyright (C) 2014  Mathias Sikos
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://gnu.org>.
 */
package org.socialworld;

import org.socialworld.calculation.descriptions.EventInfluenceDescription;


public class TryItOut {

	public static void tryItOut() {
		
		String json = "{"
				+ "eventType:candidatesWeaponLeftStab, influenceType:2,"
				+ "attributeChanges:["
				+ "{orderNr:3,attribute:Mood,term:[{termNr:4,function:Addition,functionArgs:["
				+ "{faNr:1,type:Const,value:{type:FloatingPoint,value:7}},"
				+ "{faNr:2,type:Const,value:{type:FloatingPoint,value:8}}"
				+ "]}]}]"
				+ "}";
		
        System.out.println(json);
 
   	
    	EventInfluenceDescription eid = null;
    	try {
    		 eid = new EventInfluenceDescription(json);
    	} catch (Exception e) {
            System.out.println(e.toString());
			e.printStackTrace();

    	}

    	if (eid != null) {
    		System.out.println(eid.toString());
    	}
    	else {
    		System.out.println("eid ist null");
    	}
	}
}
