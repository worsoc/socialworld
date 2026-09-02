package org.socialworld.tools.mct;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapLoader {
    public static VisualTile[][] loadMapData(String filePath, int gridSize) {
        VisualTile[][] grid = new VisualTile[gridSize][gridSize];
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            
            Pattern pattern = Pattern.compile("L_(\\d+)_(-?\\d+)");
            Matcher matcher = pattern.matcher(sb.toString());
            
            int r = 0, c = 0;
            while (matcher.find() && r < gridSize) {
                int type = Integer.parseInt(matcher.group(1));
                int height = Integer.parseInt(matcher.group(2));
                grid[r][c] = new VisualTile(type, height);
                
                c++;
                if (c == gridSize) {
                    c = 0;
                    r++;
                }
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Laden der Map: " + e.getMessage());
        }
        return grid;
    }
}

