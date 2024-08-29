package org.socialworld.collections;

public enum EventDistanceCategory {

	less_1_m, less_2_m, less_5_m, less_10_m, less_100_m, greater_100_m;
	
	public static EventDistanceCategory getCategory(float distance_in_mm) {
		EventDistanceCategory category = greater_100_m;
		if (distance_in_mm < 100000) category = less_100_m; 
		else return category;
		if (distance_in_mm < 10000) category = less_10_m; 
		else return category;
		if (distance_in_mm < 5000) category = less_5_m; 
		else return category;
		if (distance_in_mm < 2000) category = less_2_m; 
		else return category;
		if (distance_in_mm < 1000) category = less_1_m; 
		return category;
	}
}
