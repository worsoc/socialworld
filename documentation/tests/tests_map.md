# Tests: Map and MCT

Part of the [test overview](TESTS.md): ground and volume (cubes) and the Map Creation Tool.

**4 test classes, 42 tests.**

## Conventions

- **Directions on the map.** Corner bits are bit0 = SO, bit1 = SW, bit2 = NO, bit3 = NW. Inside a tile x points east and y points north. On the map, rows grow to the south.
- **Tile sizes.** Small = 1 m (`CUBE_SIZE_TILE_SMALL`), medium = 9 m, large = 81 m. One step of a tile is always 1 m high.

---

## Overview

| Area | Test class | Tests | Checks | Skipped when |
|---|---|--:|---|---|
| Map | `CubeSurfaceTest` | 11 | cube addresses against a surface computed independently | – |
| Map | `CubeTileAnalysisTest` | 10 | which tiles exist, their statistics and families | – |
| Map | `CubeCatalogTest` | 11 | catalog equals `createTile`, `isFilled` | – |
| MCT | `TileGridCubesTest` | 10 | map positions → cubes, against `LTileRaster` | – |

---

## Map: Cubes

### `CubeSurfaceTest`

`src/test/java/org/socialworld/map/cubes/CubeSurfaceTest.java`

Checks the addresses that `createTile` produces against a surface that the test **computes itself, from the corner heights only**, independently of the planes in the cube classes. Per tile:

1. The mean height of the cubes lies between the exact value and the exact value plus a margin of about one cube edge.
2. No cube lies completely above the surface.
3. On a 24 × 24 grid, a point just below the surface is covered and a point just above it is not.

| Test | Checks |
|---|---|
| `standardTiles_small / _medium / _large` | tiles 0–15 at several ground heights, depth 4 (S, M) or 5 (L) |
| `flatTiles_areExact` | tiles 0 and 15 give exactly the ground height or ground height + 1, only whole layers |
| `tilesWithTwoLevels_mediumAndLarge` | tiles 16–19 (from base − 1 to base + 1) |
| `tilesWithTwoLevels_notPossibleOnSmallTile` | tiles 16–19 on a small tile are refused |
| `tilesWithTwoLevels_needHeightOffset` | tiles 16–19 at height 0 are refused |
| `adapterTiles_unknownNumber` | unknown adapter numbers are refused |
| `adapterTiles_small / _medium` | all 16 adapter families, 9 steps of 1/9 m each |
| `cubesOfDifferentSizes_doNotAffectEachOther` | cubes of other sizes created in between change nothing (no shared static state) |

- **Assumes:** standard tile = corner bits of the tile number. The diagonal fold is SW–NO for tiles 1, 6, 7, 8, 14 and SO–NW for all others. Adapter families rise along one edge by 1/9 m per tile.
- **Note:** for medium adapters depth 5 is needed, because at depth 4 the cubes are as high as the 1/9 m steps.

### `CubeTileAnalysisTest`

`src/test/java/org/socialworld/map/cubes/CubeTileAnalysisTest.java`

| Test | Checks |
|---|---|
| `choices_matchTheRulesOfTheSizes` | S: 16 standard tiles (no 16–19); M, L: 20 each; adapters only for M (144); minimum and maximum heights (S 0, L 80) |
| `everyChoiceCanBeCreated_atItsLowestAndHighestHeight` | every tile at its height limits can be created without an error |
| `statistics_ofARidgeTile` | mean height, lowest and highest surface of tile 7 (L) |
| `surfaceHeight_followsTheCorners_andStopsAtTheGround` | `surfaceHeightAt` of tile 1 |
| `cubesAndAddresses_belongTogether` | `findCube` finds the cube from its centre; the edge fits the length of the address |
| `cubesInColumn_reachFromTheBottomToTheTop_withoutGaps` | a column of cubes reaches from 0 to the surface without gaps |
| `familyTiles_fitTogetherAtTheirEdges_andRiseByOneLevel` | within an adapter family the edges meet seamlessly; over 9 tiles one edge rises by 1 m |
| `groundAddresses_canBeLongerThanTheDepth` | addresses below the surface can be longer than the depth |
| `emptyTile_hasNoAddresses` | tile 0 at height 0 is empty |
| `edgeDescription` | edge lengths as text (`1 m`, `1/3 m`, `3 m`, `1/9 m`) |

### `CubeCatalogTest`

`src/test/java/org/socialworld/map/cubes/CubeCatalogTest.java`

The catalog holds one tree per tile type (1/9 m for S and M, 1/3 m for L). The map only keeps the tile number and its height.

| Test | Checks |
|---|---|
| `catalog_holdsTheSameCubesAsCreateTile_small / _medium / _large` | for every tile and height (L: samples 1, 2, 3, 13, 27, 80) the catalog gives the same addresses as `createTile` |
| `state_ofAFlatTile_followsTheHeight` | `FULL` / `PARTIAL` / `EMPTY` for addresses of a flat tile |
| `state_ofTheLowestFlatTile_isEmpty` | tile 0 at height 0 is empty |
| `isFilled_followsTheSurface` | `isFilled` below and above the surface, and outside the tile |
| `aTileThatReachesBelowItsHeight_hasItsGroundOneLevelLower` | tile 16 reaches 1 m below its height |
| `heightsOfTheMap_canLieOutsideTheCube` | negative heights and heights above the cube work; impossible combinations are refused |
| `allHeightsOfATileType_shareOneTree` | all heights share the same tree |
| `addresses_mustFitIntoTheDepth` | addresses that are too long or contain invalid letters are refused |
| `impossibleTiles_areRefused` | e.g. tile 16 on S, tile 101 on L, M at height 9, an unknown size |

- **Assumes:** only the merged form is comparable, because `createTile` does not merge cubes below the surface.

---

## MCT

### `TileGridCubesTest`

`src/test/java/org/socialworld/tools/mct/TileGridCubesTest.java`

`TileGridCubes` answers `isFilled(east, south, height)` for map positions through `CubeCatalog`. So far nothing calls it outside the tests.

| Test | Checks |
|---|---|
| `aRealRaster_followsTheSurfaceOfLTileRaster` | the first height raster of a generated world (in the code, 81 L tiles): at 243 × 243 points, just below the `LTileRaster` surface is filled and 0.34 m above it is not |
| `theCornersOfEveryTile_lieOnTheHeightsOfTileInfo` | for tiles 0–19, on L and on M inside L, the corners lie on `TileInfo.getOffset` |
| `twoOppositeRaisedCorners_formARidge` | tiles 6 and 9 form a ridge in the middle, as in `LTileRaster` |
| `theNextRow_isTheSouthernNeighbour` | row + 1 lies to the south |
| `aPointOnTheNorthEdgeOfATile_belongsToThatTile` | edge points belong to the tile |
| `heightsOfTheMap_canLieOutsideTheCubeOfTheTile` | heights −3 and 100 |
| `subGrids_leadDownToSmallTiles` | L → M → S, overall edge 729 m |
| `adapterTiles_areFound` | medium adapter 309 |
| `besideTheGrid_nothingIsFilled` | outside the map nothing is filled |
| `tilesWithoutCubes_areReported` | `todo`, `smallSpecial`, impossible height and `null` give `IllegalStateException` |

- **Assumes:** a map grid has 9 × 9 tiles. The map counts from the top left to the bottom right, and inside the tile y points north.
- **Not checked yet:** a real map with M and S tiles, because the generators only make L maps so far.

---

## Not Covered

- MCT generators and `MapPanel`: no tests; `TileGridCubes` has no caller yet.
