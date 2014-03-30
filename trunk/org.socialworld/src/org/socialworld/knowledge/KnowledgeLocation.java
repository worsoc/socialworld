package org.socialworld.knowledge;

import org.socialworld.attributes.Position;

public class KnowledgeLocation {
	protected int xmin;
	protected int xmax;
	protected int ymin;
	protected int ymax;
	protected int zmin;
	protected int zmax;
	
	public Position getAverageLocation() {
		int x;
		int y;
		int z;
		
		x = (xmin + xmax) / 2;
		y = (ymin + ymax) / 2;
		z = (zmin + zmax) / 2;

		return new Position(x, y, z);
	}
	
	public Position getMinLocation() {
		return new Position(xmin, ymin, zmin);
	}

	public Position getMaxLocation() {
		return new Position(xmax, ymax, zmax);
	}

}
