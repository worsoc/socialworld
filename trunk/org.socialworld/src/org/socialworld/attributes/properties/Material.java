package org.socialworld.attributes.properties;


public enum Material {

	leather(1);
	
	private int arrayIndex;

	private Material(int index) {
		this.arrayIndex = index;
	}

	/**
	 * The method returns the index of the material.
	 * 
	 * @return material's index
	 */
	public int getIndex() {
		return arrayIndex;
	}

	/**
	 * The method returns the material name which belongs to the parameter
	 * material index.
	 * 
	 * @param arrayIndex
	 *            material index
	 * @return material name
	 */
	public static Material getName(int arrayIndex) {
		for (Material material : Material.values())
			if (material.arrayIndex == arrayIndex)
				return material;
		return null;
	}
	
}
