package org.socialworld.tools;

import java.util.ArrayList;
import java.util.List;

public class ListOperations {

	public static List<String> addPrefixToElements(String prefix, List<String> list) {
		List<String> result = new ArrayList<String>();
		for (String elem : list) {
			result.add(prefix + elem);
		}
		return result;
	}
}
