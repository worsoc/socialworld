package org.socialworld.map.cubes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class CubeTileAnalysisTest {

  private static final byte SMALL = Cube.CUBE_SIZE_TILE_SMALL;
  private static final byte MEDIUM = Cube.CUBE_SIZE_TILE_MEDIUM;
  private static final byte LARGE = Cube.CUBE_SIZE_TILE_LARGE;

  @Test
  void choices_matchTheRulesOfTheSizes() {
    assertThat(CubeTileAnalysis.getStandardTiles(SMALL)).hasSize(16).doesNotContain(16, 17, 18, 19);
    assertThat(CubeTileAnalysis.getStandardTiles(MEDIUM)).hasSize(20);
    assertThat(CubeTileAnalysis.getStandardTiles(LARGE)).hasSize(20);
    assertThat(CubeTileAnalysis.hasAdapterTiles(LARGE)).isFalse();
    assertThat(CubeTileAnalysis.hasAdapterTiles(MEDIUM)).isTrue();
    assertThat(CubeTileAnalysis.getAdapterTiles()).hasSize(144);
    assertThat(CubeTileAnalysis.getMinHeight(MEDIUM, 16)).isEqualTo(1);
    assertThat(CubeTileAnalysis.getMinHeight(LARGE, 15)).isEqualTo(0);
    assertThat(CubeTileAnalysis.getMaxHeight(SMALL)).isEqualTo(0);
    assertThat(CubeTileAnalysis.getMaxHeight(LARGE)).isEqualTo(80);
  }

  @Test
  void everyChoiceCanBeCreated_atItsLowestAndHighestHeight() {
    for (byte size : new byte[] { SMALL, MEDIUM, LARGE }) {
      for (int tile : CubeTileAnalysis.getStandardTiles(size)) {
        createAtHeightLimits(tile, size);
      }
      if (CubeTileAnalysis.hasAdapterTiles(size)) {
        for (int tile : CubeTileAnalysis.getAdapterTiles()) {
          createAtHeightLimits(tile, size);
        }
      }
    }
  }

  private void createAtHeightLimits(int tile, byte size) {
    int[] heights = { CubeTileAnalysis.getMinHeight(size, tile), CubeTileAnalysis.getMaxHeight(size) };
    for (int height : heights) {
      Cube cube = CubeTileAnalysis.isAdapterTile(tile) ? new CubeAdapter(size, (byte) height) : new CubeStandard(size, (byte) height);
      cube.createTile(tile, (byte) 2, false);
    }
  }

  @Test
  void statistics_ofARidgeTile() {
    CubeTileAnalysis analysis = new CubeTileAnalysis(7, LARGE, (byte) 30, (byte) 5);

    assertThat(analysis.exactMeanHeight).isCloseTo(30 + 5.0 / 6, within(1e-3));
    assertThat(analysis.meanHeight).isGreaterThanOrEqualTo(analysis.exactMeanHeight - 1e-3);
    assertThat(Arrays.stream(analysis.addressesByDepth).sum()).isEqualTo(analysis.addresses.size());
    assertThat(analysis.lowestSurface).isCloseTo(30, within(0.1));
    assertThat(analysis.highestSurface).isCloseTo(31, within(1e-9));
  }

  @Test
  void surfaceHeight_followsTheCorners_andStopsAtTheGround() {
    // tile 1: only SO is raised, the rest of the tile lies on the height offset
    CubeTileAnalysis analysis = new CubeTileAnalysis(1, MEDIUM, (byte) 2, (byte) 2);

    assertThat(analysis.surfaceHeightAt(9, 0)).isCloseTo(3, within(1e-9));
    assertThat(analysis.surfaceHeightAt(0, 9)).isCloseTo(2, within(1e-9));
    assertThat(analysis.surfaceHeightAt(0, 0)).isCloseTo(2, within(1e-9));
  }

  @Test
  void cubesAndAddresses_belongTogether() {
    CubeTileAnalysis analysis = new CubeTileAnalysis(6, MEDIUM, (byte) 1, (byte) 3);

    for (int i = 0; i < analysis.addresses.size(); i += 50) {
      double middle = analysis.cubeEdge[i] / 2;
      assertThat(analysis.findCube(analysis.cubeX[i] + middle, analysis.cubeY[i] + middle, analysis.cubeZ[i] + middle))
          .isEqualTo(i);
      assertThat(analysis.cubeEdge[i]).isCloseTo(MEDIUM / Math.pow(3, analysis.addresses.get(i).length()), within(1e-9));
    }
  }

  @Test
  void cubesInColumn_reachFromTheBottomToTheTop_withoutGaps() {
    CubeTileAnalysis analysis = new CubeTileAnalysis(7, LARGE, (byte) 30, (byte) 4);

    for (double[] position : new double[][] { { 0.5, 80.5 }, { 40.2, 40.7 }, { 80.5, 0.5 }, { 13.1, 66.9 } }) {
      List<Integer> column = analysis.findCubesInColumn(position[0], position[1]);

      double expectedTop = analysis.cubeZ[column.get(0)] + analysis.cubeEdge[column.get(0)];
      assertThat(expectedTop).isGreaterThanOrEqualTo(analysis.surfaceHeightAt(position[0], position[1]) - 1e-9);
      for (int cube : column) {
        assertThat(analysis.cubeZ[cube] + analysis.cubeEdge[cube]).isCloseTo(expectedTop, within(1e-9));
        expectedTop = analysis.cubeZ[cube];
      }
      assertThat(expectedTop).isCloseTo(0, within(1e-9));
    }
  }

  @Test
  void familyTiles_fitTogetherAtTheirEdges_andRiseByOneLevel() {
    for (int firstTile : CubeTileAnalysis.getAdapterTiles()) {
      if (firstTile % 10 != 1) {
        continue;
      }
      CubeFamilyAnalysis family = new CubeFamilyAnalysis(firstTile + 4, MEDIUM, (byte) 1, (byte) 1);
      assertThat(family.getTile(firstTile + 4).tile).isEqualTo(firstTile + 4);

      for (int position = 0; position < 8; position++) {
        CubeTileAnalysis here = family.tilesByPosition[position];
        CubeTileAnalysis next = family.tilesByPosition[position + 1];
        for (double across = 0; across <= MEDIUM; across += 1.5) {
          double endHere = family.isWestToEast ? here.surfaceHeightAt(MEDIUM, across) : here.surfaceHeightAt(across, MEDIUM);
          double startNext = family.isWestToEast ? next.surfaceHeightAt(0, across) : next.surfaceHeightAt(across, 0);
          assertThat(endHere).as("family of " + firstTile + " after position " + position).isCloseTo(startNext, within(1e-9));
        }
      }

      // the rising edge goes from height 0 to 1 over the whole row, one way or the other
      double first = Double.MAX_VALUE;
      double last = Double.MIN_VALUE;
      for (double across : new double[] { 0, MEDIUM }) {
        double start = family.isWestToEast ? family.tilesByPosition[0].surfaceHeightAt(0, across)
            : family.tilesByPosition[0].surfaceHeightAt(across, 0);
        double end = family.isWestToEast ? family.tilesByPosition[8].surfaceHeightAt(MEDIUM, across)
            : family.tilesByPosition[8].surfaceHeightAt(across, MEDIUM);
        first = Math.min(first, Math.abs(start - end));
        last = Math.max(last, Math.abs(start - end));
      }
      assertThat(first).as("the fixed edge of family " + firstTile).isCloseTo(0, within(1e-9));
      assertThat(last).as("the rising edge of family " + firstTile).isCloseTo(1, within(1e-9));
    }
  }

  @Test
  void groundAddresses_canBeLongerThanTheDepth() {
    CubeTileAnalysis analysis = new CubeTileAnalysis(3, MEDIUM, (byte) 1, (byte) 1);

    assertThat(analysis.addressesByDepth.length).isGreaterThan(2);
    assertThat(Arrays.stream(analysis.addressesByDepth).sum()).isEqualTo(analysis.addresses.size());
  }

  @Test
  void emptyTile_hasNoAddresses() {
    CubeTileAnalysis analysis = new CubeTileAnalysis(0, SMALL, (byte) 0, (byte) 4);

    assertThat(analysis.addresses).isEmpty();
    assertThat(analysis.meanHeight).isEqualTo(0);
    assertThat(analysis.toString()).contains("tile 0");
  }

  @Test
  void edgeDescription() {
    assertThat(CubeTileAnalysis.describeEdge(LARGE, 4)).isEqualTo("1 m");
    assertThat(CubeTileAnalysis.describeEdge(LARGE, 5)).isEqualTo("1/3 m");
    assertThat(CubeTileAnalysis.describeEdge(MEDIUM, 1)).isEqualTo("3 m");
    assertThat(CubeTileAnalysis.describeEdge(SMALL, 2)).isEqualTo("1/9 m");
  }
}
