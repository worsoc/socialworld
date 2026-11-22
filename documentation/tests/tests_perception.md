# Tests: Perception (PCT)

Part of the [test overview](TESTS.md): perception paths and the Perception Creation Tool.

**8 test classes, 95 tests.**

## Overview

| Area | Test class | Tests | Checks | Skipped when |
|---|---|--:|---|---|
| PCT | `PerceptionGenerationTest` | 10 | generated perception paths | – |
| PCT | `PerceptionPathCatalogTest` | 12 | filters of the path catalog | – |
| PCT | `PerceptionDescriptionComposerTest` | 10 | paths → JSON | partly: no `swpool_epd.sql` |
| PCT | `PerceptionDescriptionReaderTest` | 14 | JSON → paths, round trip | – |
| PCT | `PerceptionEditorTest` | 29 | the logic of the dialog: open, change, save, clear | – |
| PCT | `PerceptionSlotListTest` | 7 | slot input such as `3-18/5` | – |
| PCT | `PerceptionSqlExportTest` | 5 | SQL export, nothing gets overwritten | partly: no `swpool_epd.sql` |
| PCT | `PerceptionCreationToolTest` | 8 | store, table, texts, dialog | dialog: no screen |

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

- nothing noted
