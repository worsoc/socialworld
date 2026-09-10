package org.socialworld.tools.mct;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TileGeometry {

    // Eindeutig umbenannt, um Konflikte zu vermeiden
    public static class NeighbourAnalyzerTile {
        public final int type;
        public final int baseHeight;

        public NeighbourAnalyzerTile(int type, int baseHeight) {
            this.type = type;
            this.baseHeight = baseHeight;
        }
    }

    // Parst den String einer Datei in eine Liste von NeighbourAnalyzerTiles
    public static List<NeighbourAnalyzerTile> parseGrid(String content) {
        List<NeighbourAnalyzerTile> tiles = new ArrayList<>();
        Matcher m = Pattern.compile("L_(-?\\d+)_(-?\\d+)").matcher(content);
        while (m.find()) {
            int type = Integer.parseInt(m.group(1));
            int baseHeight = Integer.parseInt(m.group(2));
            tiles.add(new NeighbourAnalyzerTile(type, baseHeight));
        }
        return tiles;
    }

    // Liefert das Höhen-Offset basierend auf dem MapVisualizer-Regelwerk
    public static int getOffset(int type, String corner) {
        int nw = 0, no = 0, so = 0, sw = 0;
        switch (type) {
            case 0: break;
            case 1: so = 1; break;
            case 2: sw = 1; break;
            case 3: so = 1; sw = 1; break;
            case 4: no = 1; break;
            case 5: no = 1; so = 1; break;
            case 6: sw = 1; no = 1; break; 
            case 7: sw = 1; no = 1; so = 1; break;
            case 8: nw = 1; break;
            case 9: nw = 1; so = 1; break; 
            case 10: nw = 1; sw = 1; break;
            case 11: nw = 1; so = 1; sw = 1; break;
            case 12: nw = 1; no = 1; break;
            case 13: nw = 1; no = 1; so = 1; break;
            case 14: no = 1; nw = 1; sw = 1; break;
            case 15: nw = 1; no = 1; so = 1; sw = 1; break;
            case 16: no = 1; sw = -1; break;
            case 17: sw = 1; no = -1; break;
            case 18: so = 1; nw = -1; break;
            case 19: nw = 1; so = -1; break;
        }
        
        switch (corner) {
            case "nw": return nw;
            case "no": return no;
            case "so": return so;
            case "sw": return sw;
            default: return 0;
        }
    }
}
