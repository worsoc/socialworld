# Tests at a Glance

What the automated tests check, what they assume, and what they need to run.

| | |
|---|---|
| **Framework** | JUnit 5 with AssertJ |
| **Test classes** | 30 |
| **Test methods** | 261 active, 1 commented out |
| **Database** | no test writes to MariaDB; one test reads from it and skips itself without it |
| **Runs in CI** | yes, in a fresh clone without `application.properties` |
| **Last updated** | 2026-11-09 |

> Keep this file up to date: every new or changed test changes this file in the same step.

---

## Overview

| Area | Test class | Tests | Checks | Skipped when |
|---|---|--:|---|---|
| Start | `SocialWorldTest` | 1 | the main program exists as a singleton | – |
| Attributes | `AttributeArrayTest` | 5 | range 0..9999 and the display as 0..99 | – |
| Core | `TickRandomCacheTest` | 10 | random value per object and tick | – |
| Core | `TickObjectCooldownTest` | 6 | own step of the matrix with an offset per object | – |
| Objects | `SimObjectsCompletelyImplemented` | 3 | lexeme mappings of the object enums | – |
| Calculation | `KnowledgeElementLinesVsJsonTest` | 17 | line and JSON build the same knowledge | partly: no `swpool_epd.sql` |
| Calculation | `FunctionByTableTest` | 11 | table functions at 0..9999 | – |
| Calculation | `FunctionByMatrixSimpleTest` | 9 | SIMPLE mode of the real `FunctionByMatrix` | – |
| Calculation | `ConstantTest` | 1 | steepness of the sigmoid | – |
| Calculation | `BranchingProbabilityTest` | 3 | a single comparison is decided by the sigmoid | – |
| Calculation | `EventInfluenceRulesFromDatabaseTest` | 1 | real rules with real arrays | no database or no data |
| Data | `Action2PerformerDescriptionPoolTest` | 4 | intensity × power, expression parser | – |
| Data | `FunctionPoolTest` | 2 | building a matrix from flat arrays | – |
| Data | `GaussPoolAAACMMappingTest` | 4 | mapping of the two drawn indices | – |
| Map | `CubeSurfaceTest` | 11 | cube addresses against a surface computed independently | – |
| Map | `CubeTileAnalysisTest` | 10 | which tiles exist, their statistics and families | – |
| Map | `CubeCatalogTest` | 11 | catalog equals `createTile`, `isFilled` | – |
| MCT | `TileGridCubesTest` | 10 | map positions → cubes, against `LTileRaster` | – |
| DCT | `AttributeArrayGeneratorTest` | 23 | attribute arrays from characters and text | – |
| DCT | `AttributeMatrixGeneratorTest` | 19 | matrices for the characters and the mapping | – |
| DCT | `TextFileTest` | 2 | reading a text file | – |
| DCT | `CreationToolEventInfluenceDescriptionTest` | 3 | typed numbers get a decimal point | – |
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

- **No database.** The PCT tests work on `InMemoryPerceptionDescriptionStore`, never on `swpool_epd` in MariaDB. `EventInfluenceRulesFromDatabaseTest` is the only test that reads from MariaDB, it only reads and skips itself without a database or without data. No test writes to it; the DCT writers are tested with stores in memory.
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

## Attributes

### `AttributeArrayTest`

`src/test/java/org/socialworld/attributes/AttributeArrayTest.java`

| Test | Checks |
|---|---|
| `range_isZeroTo9999_displayedAsZeroTo99` | maximum 9999, middle 5000, scale 100 |
| `displayValue_isRoundedDown` | 99 → 0, 100 → 1, 9950 → 99, 9999 → 99 |
| `set_limitsToTheInternalRange` | `set()` limits to 0..9999 |
| `displayString_showsDisplayedValues_toStringKeepsInternalOnes` | `toDisplayString()` shows 0..99, `toString()` the internal values |
| `displayValue_keepsMinusOneForAnInvalidIndex` | an invalid index stays −1 |

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
| `beforeTheFirstTick_theValueIsReallyCalculated` | before the first `nextTick()` the value lies in (0, 1) and differs per object |
| `beforeTheFirstTick_isTheSameAsTickZero` | the value before the first tick equals that of `setTick(0)` |
| `objectOutsideOfTheRange_getsZero` | an ID below 0 or ≥ the number of objects gives 0.0 |

- **Assumes:** that a value is the same within a tick is intended behaviour. One consequence: several `probability` expressions of one object in the same tick share the same r.
- **Not checked:** how evenly the values spread. That was checked by hand on 2026-11-06, in the running simulation with `GlobalSwitches.OUTPUT_TICK_RANDOM`.

### `TickObjectCooldownTest`

`src/test/java/org/socialworld/core/TickObjectCooldownTest.java`

| Test | Checks |
|---|---|
| `period_isOneMinute` | one matrix step is 60 seconds |
| `objectGetsOneReleasePerPeriod_atItsOffset` | object 5 is released at 5, 65, 125, 185 |
| `objects_areSpreadOverThePeriod` | the objects are spread by `objectId % 60` |
| `aMissedSecond_isMadeUpInTheSamePeriod` | a missed second is made up, never a second release in a period |
| `objectOutsideOfTheRange_isLetThrough` | an ID outside of the range is let through |
| `otherCooldownTypes_areNotDisturbed` | other cooldown types are independent |

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

### `FunctionByTableTest`

`src/test/java/org/socialworld/calculation/functions/FunctionByTableTest.java`

| Group | Checks |
|---|---|
| `u` at 0..99 | the formula gives the former hand-written table within 1, symmetric, 0 from 18 to 81 |
| `u` at 0..9999 | keeps its shape: max at both ends, edge of 18 %, 0 in the middle, 0.287 of max halfway down the edge |
| `u_mirrored` | is max − `u` everywhere |
| `v`, `v_mirrored` | max (or 0) at both ends, 0 (or max) in the middle |
| `positive_raise`, `negative_raise` | correct at 0, in between and at 9999 |
| `hinge_high`, `hinge_low` | 0 up to 6000 and rising to 9999; 9999 at 0 and falling to 0 at 4000; both monotone |
| Input | values below 0 or above 9999 are limited; `horizontal_max` gives 9999 |

### `FunctionByMatrixSimpleTest`

`src/test/java/org/socialworld/calculation/functions/FunctionByMatrixSimpleTest.java`

The SIMPLE mode of the real `FunctionByMatrix` with floating-point shares. No database.

| Test | Checks |
|---|---|
| `identityMatrix_changesNothing` | a diagonal of 1.0 changes nothing |
| `anchor_makesTheTargetTheFixedPoint` | a constant from `horizontal_max`: target 6000, rate 0.02: 3000 → 3060, 6000 stays, 8000 → 7960 |
| `anchor_ignoresItsSource` | the source of the constant does not matter |
| `negativeShare_lowersTheTarget` | 6000 − 0.003 · 8000 = 5976 |
| `diagonalAboveOneWithConstant_letsHungerRise` | a diagonal slightly above 1 with a constant; from 0 only the constant acts |
| `aChangeBelowHalfAStep_isRoundedAway` | factor 1.00001 leaves 5000 unchanged |
| `result_isLimitedToTheRange` | results stay in 0..9999 |
| `tableFunction_isAppliedToTheSource` | `v` is applied to the source |
| `allCellsUseTheOldValues` | all cells use the values from before the step |

- **Assumes:** a full matrix is built with `new FunctionByMatrix_Matrix(9)` and the setters. The constructor with three arrays means one row.

### `ConstantTest`

`src/test/java/org/socialworld/calculation/expressions/ConstantTest.java`

- **Checks:** `SIGMOID_K_ATTRIBUTE` is 5 × the attribute scale (500). The value is a finished number, so this test notices a change of the scale.

### `BranchingProbabilityTest`

`src/test/java/org/socialworld/calculation/expressions/BranchingProbabilityTest.java`

A single comparison of an attribute with a constant is decided with a probability. Evaluates real expressions for 20,000 objects.

| Test | Checks |
|---|---|
| `greaterEqual_isLikelyWhenTheValueIsHigh` | THEN at 3000: 0.018, 4500: 0.269, 5500: 0.731, 7000: 0.982 |
| `less_isLikelyWhenTheValueIsLow` | the same, mirrored |
| `atTheThreshold_bothBranchesAreEquallyLikely` | 0.5 at the threshold |

### `EventInfluenceRulesFromDatabaseTest`

`src/test/java/org/socialworld/calculation/descriptions/EventInfluenceRulesFromDatabaseTest.java`

Evaluates a sample of the real rules of `swpool_eid` (every 97th) with all real arrays of `swpool_attribute`.

- **Checks:** the share of objects that change the target attribute follows the sigmoid of the condition attribute (k = 500), per 1000 steps of distance to the threshold, tolerance 0.03. A rule that fires writes m × basis + n (within 1, limited to 0..9999). Other attributes stay unchanged.
- **Needs:** MariaDB with data, it only reads. Without them it is skipped.
- **Prints:** the table of expected and observed shares as `RULECHECK` lines with `gradlew test -i`.

---

## Data

### `Action2PerformerDescriptionPoolTest`

`src/test/java/org/socialworld/datasource/pool/Action2PerformerDescriptionPoolTest.java`

| Test | Checks |
|---|---|
| `scaleConstant_isTheAttributeScale` | the constant is `CONST(1 100)` |
| `intensityTimesPower_isDividedByTheScale` | `DIV(MUL(30.0, 5000), 100)` gives 1500.0 as a float |
| `commaInsideTheFirstOperand_isNotTheSeparator` | `SUB(MUL(6,7),2)` gives 40 |
| `commaOnlyInTheSecondOperand_worksAsBefore` | `SUB(50,MUL(6,7))` gives 8 |

### `FunctionPoolTest`

`src/test/java/org/socialworld/datasource/pool/FunctionPoolTest.java`

- **Checks:** `createMatrix` puts every share, function and offset of a flat array into its own cell, and offsets stay offsets. With floating-point shares the matrix does not divide by 100.
- **Not checked:** reading from `swpool_matrix_rowcol`.

### `GaussPoolAAACMMappingTest`

`src/test/java/org/socialworld/datasource/pool/GaussPoolAAACMMappingTest.java`

Only the empty mapping, nothing is read from the database.

| Test | Checks |
|---|---|
| `withoutEntry_theDrawnIndexStays` | without an entry the drawn index is returned |
| `entry_givesTheMatrixIndex_forExactlyThisPair` | an entry applies only to its own pair |
| `allCornersAreReachable` | −99/−99 and 99/99 work |
| `indexOfExactlyTheCapacity_countsAsTheOutermostPlace` | an index of exactly ±100 counts as ±99 |

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

## DCT: Data Creation Tool

### `AttributeArrayGeneratorTest`

`src/test/java/org/socialworld/tools/dct/AttributeArrayGeneratorTest.java`

| Group | Checks |
|---|---|
| Text die | keeps only letters, folds ä/ö/ü/ß, reads every third letter as a share of the alphabet; with several passes it reads the other letters |
| Levels | bands in 0..9999 (`o`: 3800, 5000, 6200 for shares 0, 0.5, 1; `+++` ends at 9999), found by their code, inside the range of attribute values |
| Catalog | `characters.csv` holds 100 characters in order; a wrong number of levels is rejected |
| Generator | 199 arrays, every `gauss_index` once, every value in its band, same text same values, too short a text and impossible `aa_id`s rejected, the counted letters are used up exactly |
| Fine scale | more than half of the values are no multiples of 100 |
| Adjusting by hand | `withValues` keeps character, index and id; `withDisplayValues` keeps unmoved values, moved ones become slider × 100, limited to 0..9999 |
| Tuple | is read back by `AttributeArray` |
| Writer (in memory) | keeps the order of the foreign key, all 1,791 values and 199 gauss indices arrive, existing records are replaced, others stay |
| In-memory store | refuses a gauss index without attributes and deleting attributes still referred to |

### `AttributeMatrixGeneratorTest`

`src/test/java/org/socialworld/tools/dct/AttributeMatrixGeneratorTest.java`

Generated matrices, partly calculated with the real `FunctionByMatrix`. Nothing is written to the database.

| Group | Checks |
|---|---|
| Slots | all 199 places filled, every character has one, common characters more; every `gauss_index` once, own `func_id`s |
| Text | too short is rejected; the same text gives the same matrices; variants of a character differ |
| Targets | lie in the bands of the character |
| At rest | the real matrix keeps a person within 2 of the targets for 60 steps |
| Strain | hunger and tiredness press power and mood down |
| Signature | hunger lifts the mood of the ascetic; wrong lines in the signatures file are rejected |
| Anchor | exactly one constant cell per column, never on the diagonal |
| Hunger, tiredness | reach their reference points (8 h > 5000, 12 h ≥ 9000; 16 h > 5000, 24 h ≥ 9000); hunger rises from 0 in the real matrix |
| Writer (recording store) | order of the foreign keys: delete mapping, gauss rows, cells, matrices; insert matrices, cells, gauss rows, mapping; rows and columns from 1 |
| Mapping | all 199 × 199 combinations once; always a matrix of the character of the array; all variants are reached; both signs of the drawn index give the same variant |

### `TextFileTest`

`src/test/java/org/socialworld/tools/dct/TextFileTest.java`

- **Checks:** a file in UTF-8 is read with its umlauts; a file that is no valid UTF-8 is read as ISO-8859-1.
- **Writes:** only into a temporary JUnit directory (`@TempDir`).

### `CreationToolEventInfluenceDescriptionTest`

`src/test/java/org/socialworld/tools/dct/CreationToolEventInfluenceDescriptionTest.java`

- **Checks:** `withDecimalPoint` turns `1,5` into `1.5`, leaves `1.5` and `-20` as they are, and removes surrounding spaces.

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

- Simulation run, events, actions, conversation: no tests so far.
- `Expression` as a whole: only indirectly, through `KnowledgeElementLinesVsJsonTest`, the composer, `BranchingProbabilityTest` and the rule test.
- Loading from MariaDB (`datasource`): no tests except the reading rule test, so that CI runs without a database. In particular reading matrices and the mapping, and the DCT writers against the real tables.
- The lookup of the mapping in `CreateAnimal`: only one line, it would need the whole creation of objects.
- MCT generators and `MapPanel`: no tests; `TileGridCubes` has no caller yet.
- State2Action and Action2Performer: still on lines, only the arithmetic of the intensity is tested.
