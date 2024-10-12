package org.socialworld.collections;

import org.socialworld.map.IMapProp;
import org.socialworld.tools.mct.Tile;

public class MapPropTile implements IMapProp {

	private Tile tile;
	
	public MapPropTile(Tile tile) {
		this.tile = tile;
	}
	
	protected Tile getTile() {
		return this.tile;
	}

	public boolean equals(IMapProp propLike) {
		if (propLike instanceof MapPropTile) {
			if (this.tile == ((MapPropTile)propLike).tile)
				return true;
		}
		return false;
	}
	
}
