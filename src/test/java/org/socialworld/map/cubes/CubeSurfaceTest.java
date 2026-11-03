package org.socialworld.map.cubes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.within;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

/**
 * Compares the addresses a cube produces with a surface that is derived here
 * from the corner heights only, independent of the planes in the cube classes.
 *
 * Corners by bit index, as in the MapCreationTool: bit0 = SO, bit1 = SW, bit2 =
 * NO, bit3 = NW. The x axis points to the east, the y axis to the north.
 */
public class CubeSurfaceTest {

  private static final int FOLD_SO_NW = 0;
  private static final int FOLD_SW_NO = 1;

  private static final int SO = 0;
  private static final int SW = 1;
  private static final int NO = 2;
  private static final int NW = 3;

  private static final byte SMALL = Cube.CUBE_SIZE_TILE_SMALL;
  private static final byte MEDIUM = Cube.CUBE_SIZE_TILE_MEDIUM;
  private static final byte LARGE = Cube.CUBE_SIZE_TILE_LARGE;

  private static final int[] STANDARD_TILES = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };

  @Test
  void standardTiles_small() {
    for (int tile : STANDARD_TILES) {
      checkStandardTile(tile, SMALL, (byte) 0, (byte) 4);
    }
  }

  @Test
  void standardTiles_medium() {
    for (int tile : STANDARD_TILES) {
      for (byte ground : new byte[] { 0, 1, 4 }) {
        checkStandardTile(tile, MEDIUM, ground, (byte) 4);
      }
    }
  }

  @Test
  void standardTiles_large() {
    for (int tile : STANDARD_TILES) {
      for (byte ground : new byte[] { 0, 1, 30 }) {
        checkStandardTile(tile, LARGE, ground, (byte) 5);
      }
    }
  }

  @Test
  void flatTiles_areExact() {
    assertThat(createdAddresses(new CubeStandard(SMALL, (byte) 0), 0, (byte) 4)).isEmpty();
    assertThat(meanHeight(createdAddresses(new CubeStandard(SMALL, (byte) 0), 15, (byte) 4), SMALL)).isCloseTo(1, within(1e-9));

    for (byte ground : new byte[] { 0, 1, 4 }) {
      checkFlatTiles(MEDIUM, ground, (byte) 4);
    }
    for (byte ground : new byte[] { 0, 1, 30 }) {
      checkFlatTiles(LARGE, ground, (byte) 6);
    }
  }

  private void checkFlatTiles(byte size, byte ground, byte depth) {
    Set<String> flat = createdAddresses(new CubeStandard(size, ground), 0, depth);
    Set<String> raised = createdAddresses(new CubeStandard(size, ground), 15, depth);

    assertThat(meanHeight(flat, size)).isCloseTo(ground, within(1e-9));
    assertThat(meanHeight(raised, size)).isCloseTo(ground + 1, within(1e-9));
    // only whole layers: no cube is smaller than the height of 1
    assertThat(flat).allMatch(address -> size / Math.pow(3, address.length()) >= 1 - 1e-9);
    assertThat(raised).allMatch(address -> size / Math.pow(3, address.length()) >= 1 - 1e-9);
  }

  private static Set<String> createdAddresses(Cube cube, int tile, byte depth) {
    cube.createTile(tile, depth, false);
    return new HashSet<String>(cube.getEndAddresses());
  }

  @Test
  void tilesWithTwoLevels_mediumAndLarge() {
    double[][] cornersByTile = { // SO, SW, NO, NW relative to the height offset, as in TileInfo.getOffset
        { 0, -1, 1, 0 }, { 0, 1, -1, 0 }, { 1, 0, 0, -1 }, { -1, 0, 0, 1 } };
    for (int tile = 16; tile <= 19; tile++) {
      double[] corners = cornersByTile[tile - 16];
      for (byte ground : new byte[] { 1, 4 }) {
        check("tile " + tile, new CubeStandard(MEDIUM, ground), tile, corners, FOLD_SO_NW, MEDIUM, ground, (byte) 5);
      }
      for (byte ground : new byte[] { 1, 30 }) {
        check("tile " + tile, new CubeStandard(LARGE, ground), tile, corners, FOLD_SO_NW, LARGE, ground, (byte) 5);
      }
    }
  }

  @Test
  void tilesWithTwoLevels_notPossibleOnSmallTile() {
    for (int tile = 16; tile <= 19; tile++) {
      int number = tile;
      // height offset 1, so that only the size of the tile can be the reason
      assertThatThrownBy(() -> new CubeStandard(SMALL, (byte) 1).createTile(number, (byte) 2, false))
          .isInstanceOf(IllegalArgumentException.class);
      assertThatThrownBy(() -> new CubeStandard(SMALL, (byte) 1).splitCube(number, (byte) 2, false))
          .isInstanceOf(IllegalArgumentException.class);
    }
  }

  @Test
  void tilesWithTwoLevels_needHeightOffset() {
    assertThatThrownBy(() -> new CubeStandard(MEDIUM, (byte) 0).createTile(16, (byte) 2, false))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> new CubeStandard(LARGE, (byte) 0).createTile(16, (byte) 2, false))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void adapterTiles_small() {
    checkAdapterTiles(SMALL, (byte) 0, (byte) 4);
  }

  @Test
  void adapterTiles_medium() {
    // depth 5, because at depth 4 the cubes are as high as the 1/9 steps of the adapters
    checkAdapterTiles(MEDIUM, (byte) 0, (byte) 5);
    checkAdapterTiles(MEDIUM, (byte) 1, (byte) 5);
  }

  @Test
  void cubesOfDifferentSizes_doNotAffectEachOther() {
    Cube small = new CubeStandard(SMALL, (byte) 0);
    Cube adapter = new CubeAdapterNorth(SMALL, (byte) 0);

    new CubeStandard(LARGE, (byte) 30);
    new CubeAdapterNorth(MEDIUM, (byte) 4);

    small.splitCube(7, (byte) 3, false);
    adapter.splitCube(105, (byte) 3, false);

    Cube freshSmall = new CubeStandard(SMALL, (byte) 0);
    Cube freshAdapter = new CubeAdapterNorth(SMALL, (byte) 0);
    freshSmall.splitCube(7, (byte) 3, false);
    freshAdapter.splitCube(105, (byte) 3, false);

    assertThat(small.getEndAddresses()).isEqualTo(freshSmall.getEndAddresses());
    assertThat(adapter.getEndAddresses()).isEqualTo(freshAdapter.getEndAddresses());
  }

  private void checkStandardTile(int tile, byte size, byte ground, byte depth) {
    double[] corners = new double[4];
    for (int bit = 0; bit < 4; bit++) {
      corners[bit] = (tile >> bit) & 1;
    }

    int fold;
    switch (tile) {
      case 1: case 7: case 8: case 9: case 14:
        fold = FOLD_SW_NO;
        break;
      default:
        fold = FOLD_SO_NW; // for the tiles 3, 5, 10, 12 both diagonals are the same
    }

    Cube cube = new CubeStandard(size, ground);
    check("standard tile " + tile, cube, tile, corners, fold, size, ground, depth);
  }

  private void checkAdapterTiles(byte size, byte ground, byte depth) {
    // in each family one edge rises by 1/9 per tile from the first to the second corner
    checkFamily(() -> new CubeAdapterNorth(size, ground), 101, NW, NO, FOLD_SO_NW, 0, size, ground, depth);
    checkFamily(() -> new CubeAdapterNorth(size, ground), 131, NO, NW, FOLD_SO_NW, 0, size, ground, depth);
    checkFamily(() -> new CubeAdapterNorth(size, ground), 301, NW, NO, FOLD_SO_NW, 1, size, ground, depth);
    checkFamily(() -> new CubeAdapterNorth(size, ground), 331, NO, NW, FOLD_SO_NW, 1, size, ground, depth);

    checkFamily(() -> new CubeAdapterEast(size, ground), 111, NO, SO, FOLD_SW_NO, 0, size, ground, depth);
    checkFamily(() -> new CubeAdapterEast(size, ground), 151, SO, NO, FOLD_SW_NO, 0, size, ground, depth);
    checkFamily(() -> new CubeAdapterEast(size, ground), 311, NO, SO, FOLD_SW_NO, 1, size, ground, depth);
    checkFamily(() -> new CubeAdapterEast(size, ground), 351, SO, NO, FOLD_SW_NO, 1, size, ground, depth);

    checkFamily(() -> new CubeAdapterWest(size, ground), 121, NW, SW, FOLD_SO_NW, 0, size, ground, depth);
    checkFamily(() -> new CubeAdapterWest(size, ground), 141, SW, NW, FOLD_SO_NW, 0, size, ground, depth);
    checkFamily(() -> new CubeAdapterWest(size, ground), 321, NW, SW, FOLD_SO_NW, 1, size, ground, depth);
    checkFamily(() -> new CubeAdapterWest(size, ground), 341, SW, NW, FOLD_SO_NW, 1, size, ground, depth);

    checkFamily(() -> new CubeAdapterSouth(size, ground), 161, SW, SO, FOLD_SW_NO, 0, size, ground, depth);
    checkFamily(() -> new CubeAdapterSouth(size, ground), 171, SO, SW, FOLD_SW_NO, 0, size, ground, depth);
    checkFamily(() -> new CubeAdapterSouth(size, ground), 361, SW, SO, FOLD_SW_NO, 1, size, ground, depth);
    checkFamily(() -> new CubeAdapterSouth(size, ground), 371, SO, SW, FOLD_SW_NO, 1, size, ground, depth);
  }

  private void checkFamily(Supplier<Cube> newCube, int firstTile, int risingFrom, int risingTo, int fold, double fixedEdgeHeight,
      byte size, byte ground, byte depth) {
    for (int step = 0; step < 9; step++) {
      double[] corners = { fixedEdgeHeight, fixedEdgeHeight, fixedEdgeHeight, fixedEdgeHeight };
      corners[risingFrom] = step / 9.0;
      corners[risingTo] = (step + 1) / 9.0;

      int tile = firstTile + step;
      Cube cube = newCube.get();
      check("adapter tile " + tile, cube, tile, corners, fold, size, ground, depth);
    }
  }

  private void check(String name, Cube cube, int tile, double[] corners, int fold, byte size, byte ground, byte depth) {
    cube.createTile(tile, depth, false);
    Set<String> addresses = new HashSet<String>(cube.getEndAddresses());

    String description = name + ", size " + size + ", ground " + ground;
    double edge = size / Math.pow(3, depth);
    double margin = edge * (1 + 2.0 / size);

    double exact = ground + exactMeanHeight(corners, fold);
    assertThat(meanHeight(addresses, size)).as("mean height of " + description).isBetween(exact - 1e-9,
        exact + margin);

    for (String address : addresses) {
      assertThat(isCompletelyAboveSurface(address, corners, fold, size, ground))
          .as("cube " + address + " of " + description + " is above the surface").isFalse();
    }

    int samples = 24;
    for (int i = 0; i < samples; i++) {
      for (int j = 0; j < samples; j++) {
        double x = (i + 0.37) / samples * size;
        double y = (j + 0.61) / samples * size;
        double height = ground + heightAt(corners, fold, x / size, y / size);

        double below = height - margin - 1e-6;
        double above = height + margin + 1e-6;
        if (below >= 0) {
          assertThat(isCovered(addresses, x, y, below, size, depth))
              .as("point below the surface of " + description + " at " + x + ", " + y).isTrue();
        }
        if (above < size) {
          assertThat(isCovered(addresses, x, y, above, size, depth))
              .as("point above the surface of " + description + " at " + x + ", " + y).isFalse();
        }
      }
    }
  }

  /** A cube that only touches the surface from above counts as being above it. */
  private static boolean isCompletelyAboveSurface(String address, double[] corners, int fold, double size, byte ground) {
    String letters = "ABCDEFGHIJKLM#NOPQRSTUVWXYZ";
    double x = 0;
    double y = 0;
    double z = 0;
    double edge = size;
    for (char letter : address.toCharArray()) {
      edge /= 3;
      int index = letters.indexOf(letter);
      x += (index % 3) * edge;
      y += ((index / 3) % 3) * edge;
      z += (index / 9) * edge;
    }

    double highest = Double.NEGATIVE_INFINITY;
    for (double cornerX : new double[] { x, x + edge }) {
      for (double cornerY : new double[] { y, y + edge }) {
        highest = Math.max(highest, ground + heightAt(corners, fold, cornerX / size, cornerY / size));
      }
    }
    return z >= highest - 1e-9;
  }

  private static double exactMeanHeight(double[] corners, int fold) {
    double firstTriangle;
    double secondTriangle;
    if (fold == FOLD_SO_NW) {
      firstTriangle = corners[SO] + corners[NW] + corners[SW];
      secondTriangle = corners[SO] + corners[NW] + corners[NO];
    } else {
      firstTriangle = corners[SW] + corners[NO] + corners[SO];
      secondTriangle = corners[SW] + corners[NO] + corners[NW];
    }
    return (firstTriangle + secondTriangle) / 6;
  }

  /** @param u, v position in the tile, 0 ... 1, u to the east, v to the north */
  private static double heightAt(double[] c, int fold, double u, double v) {
    if (fold == FOLD_SO_NW) {
      if (u + v <= 1) {
        return c[SW] + u * (c[SO] - c[SW]) + v * (c[NW] - c[SW]);
      }
      return c[NO] + (1 - u) * (c[NW] - c[NO]) + (1 - v) * (c[SO] - c[NO]);
    }
    if (u >= v) {
      return c[SW] + u * (c[SO] - c[SW]) + v * (c[NO] - c[SO]);
    }
    return c[SW] + v * (c[NW] - c[SW]) + u * (c[NO] - c[NW]);
  }

  private static double meanHeight(Set<String> addresses, double size) {
    double volume = 0;
    for (String address : addresses) {
      volume += Math.pow(size / Math.pow(3, address.length()), 3);
    }
    return volume / (size * size);
  }

  private static boolean isCovered(Set<String> addresses, double x, double y, double z, double size, int depth) {
    String[] layers = { "ABCDEFGHI", "JKLM#NOPQ", "RSTUVWXYZ" };
    StringBuilder address = new StringBuilder();
    double edge = size;
    double restX = x;
    double restY = y;
    double restZ = z;
    for (int level = 0; level < depth; level++) {
      edge /= 3;
      int indexX = Math.min(2, (int) (restX / edge));
      int indexY = Math.min(2, (int) (restY / edge));
      int indexZ = Math.min(2, (int) (restZ / edge));
      restX -= indexX * edge;
      restY -= indexY * edge;
      restZ -= indexZ * edge;
      address.append(layers[indexZ].charAt(indexY * 3 + indexX));
      if (addresses.contains(address.toString())) {
        return true;
      }
    }
    return false;
  }
}
