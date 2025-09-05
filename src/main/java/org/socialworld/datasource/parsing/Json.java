package org.socialworld.datasource.parsing;

import com.google.gson.Gson;

public abstract class Json {

	private static Gson gson = null;
	protected static Gson getGsonInstance() {
		if (gson == null) {
			gson = new Gson();
		}
		return gson;
	}
	
	public String toString() {
		String json;
		json = getGsonInstance().toJson(this);
		return json;
	}
}
