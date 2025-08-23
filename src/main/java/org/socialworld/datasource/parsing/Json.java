package org.socialworld.datasource.parsing;

import com.google.gson.Gson;

public abstract class Json {

	protected static Gson gson = new Gson();
	
	public String toString() {
		String json;
//		Gson gson = new Gson();
		json = gson.toJson(this);
		return json;
	}

}
