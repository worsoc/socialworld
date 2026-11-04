package org.socialworld.map.cubes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class CubeCatalogTest {

  private static final byte SMALL = Cube.CUBE_SIZE_TILE_SMALL;
  private static final byte MEDIUM = Cube.CUBE_SIZE_TILE_MEDIUM;
  private static final byte LARGE = Cube.CUBE_SIZE_TILE_LARGE;

  @Test
  void catalog_holdsTheSameCubesAsCreateTile_small() {
    for (int tile : allTiles(SMALL)) {
      assertSameCubesAsCreateTile(SMALL, tile, 0);
    }
  }

  @Test
  void catalog_holdsTheSameCubesAsCreateTile_medium() {
    for (int tile : allTiles(MEDIUM)) {
      for (int height = CubeTileAnalysis.getMinHeight(MEDIUM, tile); height <= CubeTileAnalysis.getMaxHeight(MEDIUM); height++) {
        assertSameCubesAsCreateTile(MEDIUM, tile, height);
      }
    }
  }

  @Test
  void catalog_holdsTheSameCubesAsCreateTile_large() {
    for (int tile : allTiles(LARGE)) {
      for (int height : new int[] { 1, 2, 3, 13, 27, 80 }) {
        assertSameCubesAsCreateTile(LARGE, tile, height);
      }
    }
  }

  private void assertSameCubesAsCreateTile(byte size, int tile, int height) {
    byte depth = CubeCatalog.getDepth(size);
    Cube cube = CubeTileAnalysis.isAdapterTile(tile) ? new CubeAdapter(size, (byte) height) : new CubeStandard(size, (byte) height);
    cube.createTile(tile, depth, false);
    // only the merged form is comparable: createTile does not merge cubes below the surface
    List<String> expected = new CubeCatalogTile(new CubeTree(cube.getEndAddresses(), depth), size, 0).getEndAddresses();

    assertThat(CubeCatalog.getTile(size, tile, height).getEndAddresses())
        .as("size %d, tile %d, height %d", size, tile, height)
        .isEqualTo(expected);
  }

  private List<Integer> allTiles(byte size) {
    List<Integer> tiles = new ArrayList<Integer>(CubeTileAnalysis.getStandardTiles(size));
    if (CubeTileAnalysis.hasAdapterTiles(size)) {
      tiles.addAll(CubeTileAnalysis.getAdapterTiles());
    }
    return tiles;
  }

  @Test
  void state_ofAFlatTile_followsTheHeight() {
    CubeCatalogTile tile = CubeCatalog.getTile(MEDIUM, 0, 4);

    assertThat(tile.getState("")).isEqualTo(CubeState.PARTIAL);
    assertThat(tile.getState("A")).isEqualTo(CubeState.FULL);    // z 0 to 3
    assertThat(tile.getState("J")).isEqualTo(CubeState.PARTIAL); // z 3 to 6
    assertThat(tile.getState("JA")).isEqualTo(CubeState.FULL);   // z 3 to 4
    assertThat(tile.getState("JJ")).isEqualTo(CubeState.EMPTY);  // z 4 to 5
    assertThat(tile.getState("R")).isEqualTo(CubeState.EMPTY);   // z 6 to 9
  }

  @Test
  void state_ofTheLowestFlatTile_isEmpty() {
    CubeCatalogTile tile = CubeCatalog.getTile(LARGE, 0, 0);

    assertThat(tile.getState("")).isEqualTo(CubeState.EMPTY);
    assertThat(tile.getEndAddresses()).isEmpty();
  }

  @Test
  void isFilled_followsTheSurface() {
    // tile 1: only SO is raised by 1 m, the rest lies on the height
    CubeCatalogTile tile = CubeCatalog.getTile(MEDIUM, 1, 2);

    assertThat(tile.isFilled(1, 8, 1.5)).isTrue();
    assertThat(tile.isFilled(1, 8, 2.5)).isFalse();
    assertThat(tile.isFilled(8.9, 0.1, 2.5)).isTrue();
    assertThat(tile.isFilled(8.9, 0.1, 3.5)).isFalse();
    assertThat(tile.isFilled(9, 0.1, 1)).isFalse();
    assertThat(tile.isFilled(-1, 0.1, 1)).isFalse();
  }

  @Test
  void aTileThatReachesBelowItsHeight_hasItsGroundOneLevelLower() {
    // tile 16 rises from 1 m below its height to 1 m above it
    CubeCatalogTile tile = CubeCatalog.getTile(MEDIUM, 16, 3);

    assertThat(tile.isFilled(0.1, 0.1, 1.5)).isTrue();
    assertThat(tile.isFilled(0.1, 0.1, 2.5)).isFalse();
    assertThat(tile.isFilled(8.9, 8.9, 3.5)).isTrue();
    assertThat(tile.isFilled(8.9, 8.9, 4.5)).isFalse();
  }

  @Test
  void heightsOfTheMap_canLieOutsideTheCube() {
    assertThat(CubeCatalog.isFilled(MEDIUM, 0, -2, 1, 1, -2.5)).isTrue();
    assertThat(CubeCatalog.isFilled(MEDIUM, 0, -2, 1, 1, -1.5)).isFalse();
    assertThat(CubeCatalog.isFilled(MEDIUM, 0, 50, 1, 1, 49.5)).isTrue();
    assertThat(CubeCatalog.isFilled(MEDIUM, 0, 50, 1, 1, 50.5)).isFalse();
    assertThat(CubeCatalog.isFilled(MEDIUM, 16, 50, 0.1, 0.1, 48.5)).isTrue();
    assertThat(CubeCatalog.isFilled(MEDIUM, 16, 50, 0.1, 0.1, 49.5)).isFalse();
    assertThat(CubeCatalog.isFilled(MEDIUM, 0, 50, 9, 1, 10)).isFalse();
    assertThatThrownBy(() -> CubeCatalog.isFilled(SMALL, 16, 1, 0, 0, 0)).isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> CubeCatalog.isFilled(MEDIUM, -8000, 0, 0, 0, 0)).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void allHeightsOfATileType_shareOneTree() {
    assertThat(CubeCatalog.getShape(MEDIUM, 5)).isSameAs(CubeCatalog.getShape(MEDIUM, 5));
    assertThat(CubeCatalog.getShape(MEDIUM, 5).getNodeCount()).isPositive();
  }

  @Test
  void addresses_mustFitIntoTheDepth() {
    CubeCatalogTile tile = CubeCatalog.getTile(SMALL, 1, 0);

    assertThat(tile.getState("AA")).isNotNull();
    assertThatThrownBy(() -> tile.getState("AAA")).isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> tile.getState("a")).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void impossibleTiles_areRefused() {
    assertThatThrownBy(() -> CubeCatalog.getTile(SMALL, 16, 1)).isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> CubeCatalog.getTile(LARGE, 101, 0)).isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> CubeCatalog.getTile(MEDIUM, 16, 0)).isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> CubeCatalog.getTile(MEDIUM, 0, 9)).isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> CubeCatalog.getTile((byte) 3, 0, 0)).isInstanceOf(IllegalArgumentException.class);
  }
}
