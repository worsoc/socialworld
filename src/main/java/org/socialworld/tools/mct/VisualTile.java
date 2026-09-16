package org.socialworld.tools.mct;


public class VisualTile {
    public final int type;
    public final int baseHeight;

    public VisualTile(int type, int baseHeight) {
        this.type = type;
        this.baseHeight = baseHeight;
    }

    double[] getCorners() {
    	return TileInfo.getCorners(this.type /* it's the number */, this.baseHeight);
    }
}
