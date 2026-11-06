# Tests at a Glance

What the automated tests check, what they assume, and what they need to run.

| | |
|---|---|
| **Framework** | JUnit 5 with AssertJ |
| **Test classes** | 16 |
| **Test methods** | 166 active, 1 commented out |
| **Database** | no test reads from or writes to MariaDB |
| **Runs in CI** | yes, in a fresh clone without `application.properties` |
| **Last updated** | 2026-11-06 |

> Keep this file up to date: every new or changed test changes this file in the same step.

---

## Overview

| Area | Test class | Tests | Checks | Skipped when |
|---|---|--:|---|---|
| Start | `SocialWorldTest` | 1 | the main program exists as a singleton | – |
| Core | `TickRandomCacheTest` | 8 | random value per object and tick | – |
| Objects | `SimObjectsCompletelyImplemented` | 3 | lexeme mappings of the object enums | – |
| Calculation | `KnowledgeElementLinesVsJsonTest` | 17 | line and JSON build the same knowledge | partly: no `swpool_epd.sql` |
| Map | `CubeSurfaceTest` | 11 | cube addresses against a surface computed independently | – |
| Map | `CubeTileAnalysisTest` | 10 | which tiles exist, their statistics and families | – |
| Map | `CubeCatalogTest` | 11 | catalog equals `createTile`, `isFilled` | – |
| MCT | `TileGridCubesTest` | 10 | map positions → cubes, against `LTileRaster` | – |
| PCT | `PerceptionGenerationTest` | 10 | generated perception paths | – |
| PCT | `PerceptionPathCatalogTest` | 12 | filters of the path catalog | – |
| PCT | `PerceptionDescriptionComposerTest` | 10 | paths → JSON | partly: no `swpool_epd.sql` |
| PCT | `PerceptionDescriptionReaderTest` | 14 | JSON → paths, round trip | – |
| PCT | `PerceptionEditorTest` | 29 | the logic of the dialog: open, change, save, clear | – |
| PCT | `PerceptionSlotListTest` | 7 | slot input such as `3-18/5` | – |
| PCT | `PerceptionSqlExportTest` | 5 | SQL export, nothing gets overwritten | partly: no `swpool_epd.sql` |
| PCT | `PerceptionCreationToolTest` | 8 | store, table, texts, dialog | dialog: no screen |

---

## Conditions for All Tests

- **No database.** The PCT tests work on `InMemoryPerceptionDescriptionStore`, never on `swpool_epd` in MariaDB.
- **Test data outside git.** `src/test/swpool_epd.sql`, `src/test/swpool_parseinputs.sql` and `src/test/onlyLs/` are ignored by `.gitignore`. Tests that need `swpool_epd.sql` skip themselves with `assumeTrue` when the file is missing, which is the case in CI. No test reads `swpool_parseinputs.sql` or `onlyLs/`.
- **Screen.** Only `PerceptionCreationToolTest.dialog_canBeBuilt` needs one; without a screen (headless) it is skipped.
- **Directions on the map.** Corner bits are bit0 = SO, bit1 = SW, bit2 = NO, bit3 = NW. Inside a tile x points east and y points north. On the map, rows grow to the south.
- **Tile sizes.** Small = 1 m (`CUBE_SIZE_TILE_SMALL`), medium = 9 m, large = 81 m. One step of a tile is always 1 m high.

---

## Start

### `SocialWorldTest`

`src/test/java/org/socialworld/SocialWorldTest.java`

- **Checks:** `SocialWorld.getCurrent()` does not return `null`.
- **Assumes:** the singleton can be created without a database and without a screen.

---

## Core

### `TickRandomCacheTest`

`src/test/java/org/socialworld/core/TickRandomCacheTest.java`

Works on its own instances with 10 objects (package-private constructor), not on the global singleton.

| Test | Checks |
|---|---|
| `getInstance_isAlwaysTheSame` | the singleton is always the same object |
| `sameObjectInTheSameTick_getsTheSameValue` | within a tick, an object gets the same value every time |
| `values_areBetweenZeroAndOne` | 50 ticks × 10 objects, all values in [0, 1) |
| `nextTick_givesTheObjectANewValue` | after `nextTick()` the value changes |
| `objectsInTheSameTick_getDifferentValues` | two objects in the same tick get different values |
| `value_dependsOnlyOnObjectAndTick_notOnEarlierCalls` | the value is deterministic and does not depend on earlier calls |
| `setTick_isLikeReachingTheTickStepByStep` | `setTick(4)` gives the same result as four `nextTick()` calls |
| `objectOutsideOfTheRange_getsZero` | an ID below 0 or ≥ the number of objects gives 0.0 |

- **Assumes:** that a value is the same within a tick is intended behaviour. One consequence: several `probability` expressions of one object in the same tick share the same r.
- **Not checked:** the value before the first tick. It is 0.0 there, so every `probability` takes its first branch during setup. Whether that is wanted is still open.
- **Not checked:** how evenly the values spread. That was checked by hand on 2026-11-06, in the running simulation with `GlobalSwitches.OUTPUT_TICK_RANDOM`.

---

## Objects

### `SimObjectsCompletelyImplemented`

`src/test/java/org/socialworld/objects/SimObjectsCompletelyImplemented.java`

| Test | Checks |
|---|---|
| `existsMappingToLexemIDHigherPart` | every `EnumBaseSimObj` has an entry in `Mapping_Base2LexemIDHigherPart` |
| `existsMappingToLexemIDLowerPart` | every element of `EnumMammal`, `EnumBird`, `EnumFood`, `EnumLiquid` and `EnumHumanCrafted` has an entry in its lower-part mapping |
| `generatedLexemID_isUnique` | no two subclasses of `SimulationObject` get the same lexeme ID |

- **Assumes:** the lexeme ID is `OFFSET_LEXEMID_NOUN_SIMOBJ + higher × RANGE_FOR_LOWER_VALUE + lower`. Classes without a mapping, and classes where both parts are 0, are left out of the uniqueness check.
- **Note:** `generatedLexemID_isUnique` scans the classpath with ClassGraph. On failure the details go to the console, and the assertion only says `false`.
- **Commented out:** `everySimObjClass_HasEnumElem` would check that every class of a simulation object has an enum entry.

---

## Calculation

### `KnowledgeElementLinesVsJsonTest`

`src/test/java/org/socialworld/calculation/KnowledgeElementLinesVsJsonTest.java`

Compares the two constructors of `CreateKnowledgeElementExpression`, one from a line and one from a JSON entry. Only the structure of the expression trees is compared, nothing is evaluated: subject and source, the number of atoms, and per atom the fact type, criterion, value names and paths.

**Temporary:** the test goes away together with the String constructor, as part of the move from lines to JSON.

| Group | Tests | Checks |
|---|---|---|
| Property, existing data | `property_templates0To3_buildTheSameKnowledge`, `property_withoutLabel_buildsTheSameKnowledge` | templates 0–3 from `swpool_epd.sql` give the same knowledge as the lines from `initializeWithTestData_Lines` |
| Intended differences | `property_criterion_isRepeatedInLinesButSetOnceInJson`, `property_template2_criterionNameFollowsOrderNr`, `property_template4_isOneAtomWithBothValuesInJsonButTwoAtomsInLines`, `property_template4_lineWithListingBuildsTheSameKnowledge` | JSON sets the criterion once per atom; the criterion's name follows `orderNr`; template 4 is one atom in JSON and two in the line |
| Value | `value_withoutLabel…`, `value_withLabel…`, `value_withResultType…` | `KVal` lines and `"category":"value"` agree; `resultType` is read but not used |
| Relations | `relationUnary/Binary/Ternary_buildsTheSameKnowledge` | relations with 3, 4 and 5 parts agree |
| Self-check | 5 × `comparison_notices…` | the comparison notices a different path, value name, criterion, subject or relation slot |

- **Assumes:** where line and JSON differ, the JSON is right. `orderNr` is only a technical index without meaning.
- **Needs:** `swpool_epd.sql`, for the 5 tests based on the templates (`property_templates0To3`, `property_criterion…`, `property_template2…`, `property_template4_isOneAtom…`, `property_template4_lineWithListing…`). Without the file they are skipped; the other 12 always run.
- **Technique:** reads private fields through reflection (`Value.value`) and writes trees as text.

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

## PCT: Perception Creation Tool

All PCT tests use the event `percipientExistsDistance5000` (index 508) and the causer as subject, unless they say otherwise.

### `PerceptionGenerationTest`

`src/test/java/org/socialworld/tools/pct/PerceptionGenerationTest.java`

| Test | Checks |
|---|---|
| `valuePaths_haveNoCriterion` / `propertyPaths_haveAPropertyCriterion` | value paths have no criterion; property paths have one in 0–999 |
| `paths_readSomethingBehindTheGroupingCheck` | every path has more than two steps |
| `paths_endOnTheEnumNotOnItsIndex` | no path contains `GETFctVal(getIndex)` |
| `paths_areUnique` | no duplicates |
| `propertyPaths_containTheColourOfTheCapFromTheExistingData` | the colour of the cap from the existing data is generated |
| `valuePaths_knowTheirResultType` / `resultType_ofAValuePathIsTheClassAtItsEnd` | value paths know their type (`statePerceptible_cuboid` → `SWType_31`) |
| `propertyPaths_haveNoResultType_becauseTheirTypeIsImplicit` | property paths have no type |
| `subject_decidesTheStartOfThePath` | with the target as subject, the paths start at `event_target` |

- **Assumes:** paths end on the enum, because `KnowledgeProperty.translateToLexem` needs the enum.

### `PerceptionPathCatalogTest`

`src/test/java/org/socialworld/tools/pct/PerceptionPathCatalogTest.java`

| Test | Checks |
|---|---|
| `noFilter_findsAllPathsOfTheGenerator` | without a filter the catalog holds all generated paths |
| `kind_splitsTheCatalog` | value + property = everything |
| `criterion_findsOnlyItsPaths` | filter colour (0) and material (1) |
| `subjectGroup_findsOnlyItsPaths` / `subjectGroup_looksOnlyAtTheSubject…` | the group filter only looks at the subject, not at groups further along the path |
| `text_searchesInNameAndSteps_withoutCase` | text search ignores case and trims spaces |
| `filters_workTogether` | all filters combined |
| `filter_isNotChangedByItsWithMethods` | filters cannot be changed after creation |
| `subject_decidesWhichPathsAreFound` | the target gives target paths |
| `subjectGroups_areOnlyThoseWithPaths_andHaveNames` / `groupName_comesFromTheConstant` | group names come from the `ALL_…` constants; unknown groups show their number |
| `contains_tellsWhetherAPathIsOffered` | known and unknown paths |

### `PerceptionDescriptionComposerTest`

`src/test/java/org/socialworld/tools/pct/PerceptionDescriptionComposerTest.java`

| Test | Checks |
|---|---|
| `template0/2/4_isWordForWordTheExistingData` | the composed JSON equals the row in `swpool_epd.sql`, character for character |
| `pathsOfOneCriterion_becomeOneBlock_evenIfTheyWereSelectedApart` | paths of the same criterion end up in one block, i.e. one `orderNr` |
| `everyValue_getsItsOwnBlock_apartFromTheProperties` | every value gets its own block |
| `value_writesItsResultType_andCategory` / `property_writesNoResultType` / `value_withoutName_writesNoLabel` | which fields appear in the JSON |
| `composedJson_isLoadedLikeThePoolDoesIt` | `EventPerceptionDescription` loads the result, with event, type and one function |
| `everyGeneratedPath_buildsAValidExpression` | every generated path gives a valid `CreateKnowledgeElementExpression` |

- **Needs:** `swpool_epd.sql`, for the 3 `template…` tests. Without the file they are skipped.
- **Known:** `value` entries contain `"criterion":0`, which reads like colour. That is harmless, the decision on `nothing` in `KnowledgeFact_Criterion` is still open.

### `PerceptionDescriptionReaderTest`

`src/test/java/org/socialworld/tools/pct/PerceptionDescriptionReaderTest.java`

The counterpart to the composer: JSON → `PerceptionSelection`.

| Group | Checks |
|---|---|
| Round trip | all 40 rows of the tool, and values with a type but without a name, come back word for word after read → compose |
| What is read | event, perception type, threshold, subject, paths with their kind and type; old rows without a type stay without one |
| Kept | paths outside the meta information (e.g. material of `ALL_ANIMALS_AND_ITEMS`) survive loading and saving |
| Can only be looked at | broken JSON, unknown event, two entries, a different source, a different form of the subject, different thresholds, relations, a criterion split into two blocks |

- **Assumes:** whatever the composer could not write back unchanged must not be editable, so that saving destroys nothing.

### `PerceptionEditorTest`

`src/test/java/org/socialworld/tools/pct/PerceptionEditorTest.java`

The logic behind the dialog, without Swing, on a fresh `InMemoryPerceptionDescriptionStore` per test.

| Group | Checks |
|---|---|
| Opening | an empty slot starts empty with the given subject; a filled slot opens with its content; all 40 example rows come out word for word |
| Can only be looked at | broken rows, rows of another slot or event; nothing can be added or saved, the store stays unchanged |
| Changing | `add` keeps criteria together (order as in the JSON), leaves out duplicates; nothing new is no change; `remove` changes nothing in the store yet; threshold > 0; the subject can only change while no path is selected |
| Perception events | for all six `percipientExistsDistance…` events only the causer is allowed, even if the target was set before; other events (e.g. `percipientMoveWalk`, `candidatesMoveWalk`) allow the target; a stored row with the target can only be looked at |
| Saving | writes the opened slot; several slots each get their own perception type; saves under `getIndex()` (508), not `ordinal()`; an empty selection cannot be saved; finds other filled slots |
| Clearing | only the opened slot; also rows that can only be looked at; drops unsaved changes; an empty slot is refused; the same slot of other events stays |

### `PerceptionSlotListTest`

`src/test/java/org/socialworld/tools/pct/PerceptionSlotListTest.java`

The input field for several slots, with 100 slots (0–99).

| Test | Checks |
|---|---|
| `singleSlots` | `"3, 8,13 21"` → 3, 8, 13, 21 |
| `range` / `rangeWithStep` | `"5-7"`, `"3-18/5"` |
| `partsTogether_withoutDuplicates` | `"1-3, 2, 90-99/9"` → 1, 2, 3, 90, 99 |
| `empty_meansNoSlots` | only spaces → no slots |
| `slotOutsideOfTheRange_isRefused` | 100 or `90-100` |
| `unreadable_isRefused` | e.g. `a`, `1-`, `7-5`, `1-3/0`, `1-2-3` |

### `PerceptionSqlExportTest`

`src/test/java/org/socialworld/tools/pct/PerceptionSqlExportTest.java`

| Test | Checks |
|---|---|
| `writtenFile_isByteForByteTheExistingData` | the export equals `swpool_epd.sql` byte for byte |
| `everyRow_isLoadedLikeThePoolDoesIt` | all 40 rows load like in the pool (event 508, type, one function) |
| `existingFile_staysAsItIs` | an existing file is not overwritten |
| `withoutArgument_nothingIsWritten` | without a file name nothing is written |
| `templates_reportThePathsOutsideOfTheMetaInformation` | exactly three paths lie outside the meta information |

- **Needs:** `swpool_epd.sql` for the byte comparison. Without the file it is skipped.
- **Writes:** only into a temporary JUnit directory (`@TempDir`).

### `PerceptionCreationToolTest`

`src/test/java/org/socialworld/tools/pct/PerceptionCreationToolTest.java`

| Group | Checks |
|---|---|
| Store | example data has 40 rows; events stay apart; saving overwrites; `loadAll` hands out a copy |
| Table | the columns of a property path and of a value path with its type |
| Texts | `formatPath` with the note `[not in the meta information]`; value with its type |
| Dialog | `PerceptionCreationTool` can be built and says "not the database" in its title; **skipped without a screen** |

---

## Not Covered

- Simulation run, events, actions, attributes, conversation: no tests so far.
- `Expression` as a whole: only indirectly, through `KnowledgeElementLinesVsJsonTest` and the composer.
- Loading from MariaDB (`datasource`): no tests, on purpose, so that CI runs without a database.
- MCT generators and `MapPanel`: no tests; `TileGridCubes` has no caller yet.
- State2Action and Action2Performer: still on lines, no tests.
