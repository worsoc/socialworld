package org.socialworld;

import org.socialworld.calculation.descriptions.EventInfluenceDescription;

import com.google.gson.Gson;

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
 
    	Gson gson =  new Gson();
    	
    	EventInfluenceDescription eid = null;
    	try {
    		 eid = new EventInfluenceDescription(gson, json);
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
