package org.socialworld.collections;


import java.util.LinkedList;

import org.socialworld.map.IMapProp;
import org.socialworld.map.MapPropTree_Node;
import org.socialworld.tools.mct.Tile;
import org.socialworld.tools.mct.TileGrid;
import org.socialworld.tools.mct.TileType;

public class TileSearchNode extends MapPropTree_Node {

	protected TileSearchNode(TileSearchTree tree, int level)  {
		
		super(tree, level);
	}

	protected  TileSearchNode insertTile(Tile tile, String location) {
		if (checkLocation(tile, location) == true) {
			return (TileSearchNode) addProperty(new MapPropTile(tile), location);
		}
		else {
			return null;
		}
	}
	
	public TileSearchNode getSubTreeNode(String location) {
		MapPropTree_Node node = getSubTreeNode(location);
		if (node != null && node instanceof TileSearchNode) {
			return (TileSearchNode) node;
		}
		else {
			return null;
		}
	}
	
	public Tile getTile(String location) {
		LinkedList<IMapProp> props = getProperties(location);
		if (props.size() == 1) {
			IMapProp prop = props.get(0);
			if (prop instanceof MapPropTile) {
				return ((MapPropTile) prop).getTile();
			}
		}
		return Tile.getNothing();
	}
	
	private boolean checkLocation(Tile tile, String location) {
		TileType type = tile.getType();
		int tileTypeLevel;
		if (type == TileType.sub && tile instanceof TileGrid) {
			tileTypeLevel = (((TileGrid) tile).getTileTypeLevel());
		}
		else {
			tileTypeLevel = type.getTileTypeLevel();
		}
		
		int lengthLocation = location.length();
		int length = getLevel() + lengthLocation;
		switch (tileTypeLevel) {
		case TileType.TILE_LEVEL_SMALL:
				if (length == 8) return true;
				else return false;
		case TileType.TILE_LEVEL_MEDIUM:
			if (length == 6) return true;
			else return false;
		case TileType.TILE_LEVEL_LARGE:
			if (length == 4) return true;
			else return false;
			
		default:
			return false;
		}
	}

}
