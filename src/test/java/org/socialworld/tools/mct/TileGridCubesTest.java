package org.socialworld.tools.mct;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class TileGridCubesTest {

  // the first height raster of a generated world
  private static final String REAL_RASTER = "L_2_0,L_0_0,L_10_-1,L_1_-1,L_17_-1,L_6_-2,L_8_-2,L_0_-2,L_14_-3,L_9_0,L_3_0,L_17_0,L_7_-1,L_11_-1,L_17_-1,L_2_-2,L_14_-3,L_9_-3,L_4_0,L_12_0,L_8_0,L_14_-1,L_12_-1,L_19_-1,L_19_-2,L_19_-3,L_16_-3,L_13_-1,L_14_-1,L_13_-1,L_10_-1,L_14_-2,L_8_-2,L_10_-3,L_11_-4,L_18_-3,L_7_-1,L_10_-1,L_4_-1,L_19_-1,L_19_-2,L_12_-3,L_19_-3,L_12_-4,L_16_-3,L_0_0,L_10_-1,L_14_-2,L_19_-2,L_19_-3,L_13_-4,L_10_-4,L_1_-4,L_6_-4,L_12_-1,L_19_-1,L_19_-2,L_19_-3,L_8_-4,L_5_-4,L_11_-4,L_6_-4,L_9_-4,L_13_-2,L_10_-2,L_10_-3,L_11_-4,L_2_-4,L_4_-4,L_13_-4,L_11_-4,L_18_-3,L_5_-2,L_10_-2,L_11_-3,L_2_-3,L_11_-4,L_3_-4,L_6_-4,L_12_-4,L_16_-3";

  private static final int LARGE = 81;
  private static final int MEDIUM = 9;
  private static final String[] CORNERS = { "nw", "no", "so", "sw" };

  @Test
  void aRealRaster_followsTheSurfaceOfLTileRaster() {
    TileGridCubes cubes = new TileGridCubes(gridOf(REAL_RASTER.split(",")));
    LTileRaster raster = LTileRaster.fromTokens(Arrays.asList(REAL_RASTER.split(",")));
    int steps = 27;

    for (int stepEast = 0; stepEast < 9 * steps; stepEast++) {
      for (int stepSouth = 0; stepSouth < 9 * steps; stepSouth++) {
        double tilesEast = (stepEast + 0.5) / steps;
        double tilesSouth = (stepSouth + 0.5) / steps;
        double surface = raster.heightAt(tilesEast, tilesSouth);

        // a cube that the surface runs through is filled, and large cubes have an edge of 1/3 m
        assertThat(cubes.isFilled(tilesEast * LARGE, tilesSouth * LARGE, surface - 0.01))
            .as("below the surface at %s / %s, height %s", tilesEast, tilesSouth, surface).isTrue();
        assertThat(cubes.isFilled(tilesEast * LARGE, tilesSouth * LARGE, surface + 0.34))
            .as("above the surface at %s / %s, height %s", tilesEast, tilesSouth, surface).isFalse();
      }
    }
  }

  @Test
  void theCornersOfEveryTile_lieOnTheHeightsOfTileInfo() {
    for (int number = 0; number <= 19; number++) {
      TileGrid large = flatGrid(TileType.largeStandard, TileType.TILE_LEVEL_LARGE, null);
      large.addTile(new Tile(TileType.largeStandard, number, 5), 40);
      assertCorners(new TileGridCubes(large), number, 4 * LARGE, 4 * LARGE, LARGE, 0.5);

      TileGrid outer = flatGrid(TileType.largeStandard, TileType.TILE_LEVEL_LARGE, null);
      TileGrid medium = flatGrid(TileType.mediumStandard, TileType.TILE_LEVEL_MEDIUM, outer);
      medium.addTile(new Tile(TileType.mediumStandard, number, 5), 40);
      outer.addTile(medium, 0);
      assertCorners(new TileGridCubes(outer), number, 4 * MEDIUM, 4 * MEDIUM, MEDIUM, 0.2);
    }
  }

  private void assertCorners(TileGridCubes cubes, int number, double tileEast, double tileSouth, int edge, double distance) {
    double inset = edge / 1000.0;
    for (String corner : CORNERS) {
      double east = tileEast + (corner.endsWith("o") ? edge - inset : inset);
      double south = tileSouth + (corner.startsWith("s") ? edge - inset : inset);
      int height = 5 + TileInfo.getOffset(number, corner);

      assertThat(cubes.isFilled(east, south, height - distance)).as("tile %d, corner %s, below", number, corner).isTrue();
      assertThat(cubes.isFilled(east, south, height + distance)).as("tile %d, corner %s, above", number, corner).isFalse();
    }
  }

  @Test
  void twoOppositeRaisedCorners_formARidge() {
    for (int number : new int[] { 6, 9 }) {
      TileGrid grid = flatGrid(TileType.largeStandard, TileType.TILE_LEVEL_LARGE, null);
      grid.addTile(new Tile(TileType.largeStandard, number, 5), 0);
      TileGridCubes cubes = new TileGridCubes(grid);
      List<String> tokens = new ArrayList<String>();
      for (int index = 0; index < 81; index++) {
        tokens.add("L_" + number + "_5");
      }

      assertThat(cubes.isFilled(40.5, 40.5, 5.9)).as("tile %d, middle", number).isTrue();
      assertThat(LTileRaster.fromTokens(tokens).heightAt(0.5, 0.5)).as("tile %d, middle", number).isEqualTo(6);
    }
  }

  @Test
  void theNextRow_isTheSouthernNeighbour() {
    TileGrid grid = flatGrid(TileType.largeStandard, TileType.TILE_LEVEL_LARGE, null);
    grid.addTile(new Tile(TileType.largeStandard, 0, 20), 0);
    grid.addTile(new Tile(TileType.largeStandard, 0, 30), 9);
    TileGridCubes cubes = new TileGridCubes(grid);

    assertThat(cubes.isFilled(10, 80, 25)).isFalse();
    assertThat(cubes.isFilled(10, 82, 25)).isTrue();
  }

  @Test
  void aPointOnTheNorthEdgeOfATile_belongsToThatTile() {
    TileGrid grid = flatGrid(TileType.largeStandard, TileType.TILE_LEVEL_LARGE, null);
    grid.addTile(new Tile(TileType.largeStandard, 0, 20), 0);
    TileGridCubes cubes = new TileGridCubes(grid);

    assertThat(cubes.isFilled(10, 0, 19.5)).isTrue();
    assertThat(cubes.isFilled(10, 0, 20.5)).isFalse();
  }

  @Test
  void heightsOfTheMap_canLieOutsideTheCubeOfTheTile() {
    TileGrid grid = flatGrid(TileType.largeStandard, TileType.TILE_LEVEL_LARGE, null);
    grid.addTile(new Tile(TileType.largeStandard, 0, -3), 0);
    grid.addTile(new Tile(TileType.largeStandard, 0, 100), 1);
    TileGridCubes cubes = new TileGridCubes(grid);

    assertThat(cubes.isFilled(10, 10, -3.5)).isTrue();
    assertThat(cubes.isFilled(10, 10, -2.5)).isFalse();
    assertThat(cubes.isFilled(90, 10, 99.5)).isTrue();
    assertThat(cubes.isFilled(90, 10, 100.5)).isFalse();
  }

  @Test
  void subGrids_leadDownToSmallTiles() {
    TileGrid outer = flatGrid(TileType.largeStandard, TileType.TILE_LEVEL_LARGE, null);
    TileGrid medium = flatGrid(TileType.mediumStandard, TileType.TILE_LEVEL_MEDIUM, outer);
    TileGrid small = flatGrid(TileType.smallStandard, TileType.TILE_LEVEL_SMALL, medium);
    small.addTile(new Tile(TileType.smallStandard, 0, 12), 10);
    medium.addTile(small, 20);
    outer.addTile(medium, 30);
    TileGridCubes cubes = new TileGridCubes(outer);

    // large tile: row 3, column 3; medium tile: row 2, column 2; small tile: row 1, column 1
    double position = 3 * LARGE + 2 * MEDIUM + 1 + 0.5;
    assertThat(cubes.getEdge()).isEqualTo(729);
    assertThat(cubes.isFilled(position, position, 11.5)).isTrue();
    assertThat(cubes.isFilled(position, position, 12.5)).isFalse();
    assertThat(cubes.isFilled(position + 1, position, 11.5)).isFalse();
  }

  @Test
  void adapterTiles_areFound() {
    TileGrid outer = flatGrid(TileType.largeStandard, TileType.TILE_LEVEL_LARGE, null);
    TileGrid medium = flatGrid(TileType.mediumStandard, TileType.TILE_LEVEL_MEDIUM, outer);
    medium.addTile(new Tile(TileType.mediumAdapter, 309, 5), 0);
    outer.addTile(medium, 0);

    assertThat(new TileGridCubes(outer).isFilled(4.5, 4.5, 5.2)).isTrue();
    assertThat(new TileGridCubes(outer).isFilled(4.5, 4.5, 6.5)).isFalse();
  }

  @Test
  void besideTheGrid_nothingIsFilled() {
    TileGridCubes cubes = new TileGridCubes(flatGrid(TileType.largeStandard, TileType.TILE_LEVEL_LARGE, null));

    assertThat(cubes.isFilled(-1, 10, -50)).isFalse();
    assertThat(cubes.isFilled(10, 729, -50)).isFalse();
  }

  @Test
  void tilesWithoutCubes_areReported() {
    assertRefused(new Tile(TileType.todo));
    assertRefused(new Tile(TileType.smallSpecial, 1, 0));
    assertRefused(new Tile(TileType.largeStandard, 0, -999));
    assertRefused(null);
  }

  private void assertRefused(Tile tile) {
    TileGrid grid = flatGrid(TileType.largeStandard, TileType.TILE_LEVEL_LARGE, null);
    grid.tiles[0] = tile;
    assertThatThrownBy(() -> new TileGridCubes(grid).isFilled(1, 1, 0)).isInstanceOf(IllegalStateException.class);
  }

  private static TileGrid gridOf(String[] tokens) {
    TileGrid grid = new TileGrid(null);
    for (int index = 0; index < tokens.length; index++) {
      grid.addTile(new Tile(tokens[index]), index);
    }
    return grid;
  }

  private static TileGrid flatGrid(TileType type, int level, TileGrid parent) {
    TileGrid grid = new TileGrid(parent);
    assertThat(grid.getTileTypeLevel()).isEqualTo(level);
    for (int index = 0; index < 81; index++) {
      grid.addTile(new Tile(type, 0, 0), index);
    }
    return grid;
  }
}
