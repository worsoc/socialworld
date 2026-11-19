# Tests at a Glance

What the automated tests check, what they assume, and what they need to run.

| | |
|---|---|
| **Framework** | JUnit 5 with AssertJ |
| **Test classes** | 72 |
| **Test methods** | 624 active, 1 commented out |
| **Database** | no test writes to MariaDB; a few tests read from it and skip themselves without it or with an empty table |
| **Runs in CI** | yes, in a fresh clone without `application.properties` |
| **Last updated** | 2026-11-19 |

> Keep this file up to date: every new or changed test changes this file in the same step.

---

## Overview

| Area | Test class | Tests | Checks | Skipped when |
|---|---|--:|---|---|
| Start | `SocialWorldTest` | 1 | the main program exists as a singleton | – |
| Attributes | `AttributeArrayTest` | 5 | range 0..9999 and the display as 0..99 | – |
| Attributes | `PropertyNameVelocityTest` | 2 | active and passive velocity as properties of the simulation object | – |
| Core | `TickRandomCacheTest` | 10 | random value per object and tick | – |
| Core | `TickObjectCooldownTest` | 10 | own step of the matrix with an offset per object, cooldown of the effect on the attributes by criticality stage | – |
| Core | `ActionHandlerTest` | 3 | a new action handler has no running action and nothing to stop | – |
| Core | `EventTypeDistancesTest` | 3 | reach of seeing someone move, who can be moved by an event | – |
| Core | `ActionEventTimingTest` | 9 | fixed intervals between the events of a running action, one event for single actions | – |
| Core | `ObjectMasterIdRangeTest` | 4 | every type keeps to its band of 1000 IDs; beyond it the simulation stops | – |
| Actions | `InventoryMouthTest` | 1 | the inventory gives the item in the mouth as property (eating, drinking) | – |
| Actions | `ActionMoveVelocityTest` | 4 | velocity of a move action from event to event: uniform, accelerated, braking | – |
| Objects | `SimObjectsCompletelyImplemented` | 3 | lexeme mappings of the object enums | – |
| Calculation | `KnowledgeElementLinesVsJsonTest` | 17 | line and JSON build the same knowledge | partly: no `swpool_epd.sql` |
| Calculation | `FunctionByTableTest` | 11 | table functions at 0..9999 | – |
| Calculation | `FunctionByMatrixSimpleTest` | 9 | SIMPLE mode of the real `FunctionByMatrix` | – |
| Calculation | `ConstantTest` | 1 | steepness of the sigmoid | – |
| Calculation | `BranchingProbabilityTest` | 3 | a single comparison is decided by the sigmoid | – |
| Calculation | `MXPlusNTest` | 1 | many MX+N expressions are all valid and share the func_id of the same function | – |
| Calculation | `ChangeAttributeConditionTest` | 4 | condition term of an event influence rule: several comparisons are strict, one stays soft | – |
| Calculation | `SequenceTest` | 2 | every expression of a sequence runs exactly once and in order, for 1 to 15 expressions | – |
| Calculation | `State2ActionDescriptionTest` | 4 | state-to-action rules from JSON as one chain, no event parameter; the reaction entry keeps the common fields | – |
| Calculation | `EventReactionDescriptionTest` | 4 | reaction rules as JSON for swpool_erd: readable tags, pair and one function, unknown event type | – |
| Calculation | `EventInfluenceRulesFromDatabaseTest` | 1 | real rules with real arrays | no database or empty table |
| Calculation | `PositionCalculatorSectorsTest` | 9 | whether a new position stays in the same search sectors | – |
| Calculation | `PositionMovementTest` | 8 | technical position movement: impulse, friction, time step, technical event types, no self-push | – |
| Calculation | `CalculationVectorTest` | 11 | vector operations in `Calculation`: add, subtract, negate, scale, component product, normalize, length | – |
| Calculation | `DirectionToReferenceTest` | 10 | tag `ActionDir`: direction to or away from a reference position, height weight, offset, fallback | – |
| Calculation | `VectorTest` | 7 | `Vector`: normalizing (the zero vector has length 0) and the component product | – |
| Data | `Action2PerformerDescriptionPoolTest` | 7 | intensity × power, expression parser, constant intensity of the moves, move velocity without double acceleration | – |
| Data | `FunctionPoolTest` | 5 | building a matrix from flat arrays, table functions under func_id 0..10, func_ids for functions created at runtime | – |
| Data | `GaussPoolAAACMMappingTest` | 4 | mapping of the two drawn indices | – |
| Data | `CreateAnimalInfluenceTypeTest` | 4 | the influence type is the character of the attribute array, the same for every event type | – |
| Data | `CreateAnimalState2ActionTypeTest` | 3 | the state-to-action type is the character of the attribute array, equal to the influence type | – |
| Data | `EventReactionFamilyTest` | 4 | 116 reaction types, drawn per event family, other event types keep 1 | – |
| Data | `DatabaseAssumptionsTest` | 1 | a test is skipped without a database | – |
| Map | `CubeSurfaceTest` | 11 | cube addresses against a surface computed independently | – |
| Map | `CubeTileAnalysisTest` | 10 | which tiles exist, their statistics and families | – |
| Map | `CubeCatalogTest` | 11 | catalog equals `createTile`, `isFilled` | – |
| MCT | `TileGridCubesTest` | 10 | map positions → cubes, against `LTileRaster` | – |
| DCT | `AttributeArrayGeneratorTest` | 30 | attribute arrays from characters and text, need and ideal of the characters | – |
| DCT | `AttributeMatrixGeneratorTest` | 19 | matrices for the characters and the mapping | – |
| DCT | `TextFileTest` | 2 | reading a text file | – |
| DCT | `CreationToolEventInfluenceDescriptionTest` | 3 | typed numbers get a decimal point | – |
| DCT | `EventInfluenceCatalogTest` | 6 | reading event_situations.csv and event_influences.csv | – |
| DCT | `EventInfluenceGeneratorTest` | 11 | generated event influence rules: tiers (hunger and tiredness at the ideal), order, factor, one character per influence type, neutral rule, need of sleep and food per character | – |
| DCT | `State2ActionGeneratorTest` | 12 | state-to-action rules per character: sleep and eat at the ideal, sleep for sleepHours, own rules from the most pronounced levels, fallback | – |
| DCT | `EventReactionGeneratorTest` | 17 | reaction rules: 7 styles × 16 variants + 4 former types per family, 540 rules; moves go away from the event (`ActionDir`); test flight | – |
| DCT | `AttributeCharacterOverviewTest` | 11 | overview of arrays and matrices per character | partly: no database or empty table |
| DCT | `AttributeMatrixCellTest` | 6 | text of a matrix cell | – |
| DCT | `AttributeMatrixTargetsTest` | 8 | target values of a matrix | partly: no database or empty table |
| DCT | `TablePoolAttributeDataReaderTest` | 5 | reading the real attribute tables | no database or empty table |
| PCT | `PerceptionGenerationTest` | 10 | generated perception paths | – |
| PCT | `PerceptionPathCatalogTest` | 12 | filters of the path catalog | – |
| PCT | `PerceptionDescriptionComposerTest` | 10 | paths → JSON | partly: no `swpool_epd.sql` |
| PCT | `PerceptionDescriptionReaderTest` | 14 | JSON → paths, round trip | – |
| PCT | `PerceptionEditorTest` | 29 | the logic of the dialog: open, change, save, clear | – |
| PCT | `PerceptionSlotListTest` | 7 | slot input such as `3-18/5` | – |
| PCT | `PerceptionSqlExportTest` | 5 | SQL export, nothing gets overwritten | partly: no `swpool_epd.sql` |
| PCT | `PerceptionCreationToolTest` | 8 | store, table, texts, dialog | dialog: no screen |
| Display | `VisualModelTest` | 21 | the model between the simulation threads and the display, remaining duration counted down between two reports | – |
| Display | `ActionGridTest` | 22 | action families, short names, fading, field under the mouse, own colour for fly | – |
| Display | `AttributeMatrixTest` | 23 | colour scale, sorting, the two blocks of 50, change mark | – |
| Display | `ObjectDetailTest` | 25 | detail column, type of the human, direction of the running move, last actions, history of one human, selection by click | – |
| Display | `PopulationHistoryTest` | 11 | history of all humans: families, mean, percentiles | – |
| Display | `AutomaticSelectionTest` | 13 | the detail column follows the largest change | – |
| Display | `PictureSequenceTest` | 9 | figures and timing of the picture sequence in the action grid | – |
| Display | `TypeOverviewTest` | 5 | overview of the types: which human has which type, number of humans per type | – |
| ACT | `ActionRequestTest` | 9 | values and checks of an action request, duration included | – |
| ACT | `ActionSenderTest` | 6 | properties of the action given into the pipeline, invalid requests are not given, no stop for an invalid causer | – |
| ACT | `EventRangeTest` | 14 | say modes, their ranges and the humans within the range | – |
| ACT | `NeedsResetTest` | 3 | the dashboard intervention ends only sleep and eat, sets tiredness and hunger to 2000 | – |
| ACT | `ActionMapScaleTest` | 11 | world position ↔ pixel on the range map, finding a human under the mouse | – |

---

## Conditions for All Tests

- **No database.** The PCT tests work on `InMemoryPerceptionDescriptionStore`, never on `swpool_epd` in MariaDB. `EventInfluenceRulesFromDatabaseTest`, `TablePoolAttributeDataReaderTest` and one test each in `AttributeCharacterOverviewTest` and `AttributeMatrixTargetsTest` read from MariaDB, they only read. They call `DatabaseAssumptions` first: without a database, with a missing table or with an empty table they are skipped. Errors caused by the data in a filled table let them fail. No test writes to it; the DCT writers are tested with stores in memory.
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

### `PropertyNameVelocityTest`

`src/test/java/org/socialworld/attributes/PropertyNameVelocityTest.java`

The two velocities of the technical position movement (movement concept G1, step M1).

| Test | Checks |
|---|---|
| `velocities_arePropertiesOfTheSimulationObject` | `simobj_velocityActive` and `simobj_velocityPassive` have the type `simObjProp`; `toType(simObjProp)` keeps them and does not map them to `simobj_directionMove` |
| `velocities_haveTheirOwnIndexes` | indexes 1001024 and 1001025; no index occurs twice in the block 1001xxx |

- **Not checked:** writing the velocities (needs a running simulation), comes with M2.

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
| `attributesCooldown_typesAndWindowsOfTheStages` | stages 1 to 4 have the types `TYPE_ATTRIBUTES_HIGH` … `TYPE_ATTRIBUTES_BACKGROUND` and the windows 5, 10, 30, 60 s; stage 0 has no cooldown |
| `attributesCooldown_letsOneEventThroughPerWindow` | stage 3: the first event passes, a second one in the same second or within 30 s not, the next window again; another object is independent |
| `attributesCooldown_stagesDoNotBlockEachOther` | walking (stage 3) does not block running (stage 1), running passes again after 5 s |
| `attributeCooldownLevels_ofTheEventTypes` | stages of some event types (e.g. `percipientMoveRun` 1, `percipientExistsDistance5000` 4, `selfMoveWalk` 3, `selfSleep` and `candidatesMoveWalk` 0); of the percipient types exactly the `CRITICAL` ones have stage 0 |

### `EventTypeDistancesTest`

`src/test/java/org/socialworld/core/EventTypeDistancesTest.java`

Distances of `EventType`: how far an event reaches (`getEffectDistance`) and how close a candidate must be to be moved by it (`getPositionChangeDistance`, checked in `PositionCalculator`).

| Test | Checks |
|---|---|
| `seeingSomeoneMove_reachesFartherThanBeingReallyClose` | percipientMove… reach 20, 30, 10, 20, 20, 50 m (walk, run, sneak, jump, swim, fly), each farther than its candidatesMove… |
| `positionChangeDistance_onlyForTheCandidatesThatCanBeMoved` | candidatesMove… 500, candidatesWeapon… and candidatesPunch… their effect distance, all others 0; only candidates events relevant for changing the position have one |
| `relevantForChangingPosition_onlyTheListedRanges` | `isRelevantForChangingPosition` holds for moving, weapon and punch events and, since 2026-11-19, for sleep (it sets both velocities to 0); not for eating, talking, presence or seeing someone move (before 2026-11-16 it held for every event) |

- **Not checked:** `ActionMove` creating the percipientMove events and who sees them; that needs a running simulation.

### `ActionEventTimingTest`

`src/test/java/org/socialworld/core/ActionEventTimingTest.java`

`ActionEventTiming` decides when a running action is calculated again: `perform()` (and so its events) at t = 0, a, 2a, … with the interval a of its `ActionMode` (`getEventIntervalSeconds()`), once for the interval 0, and at the end of the duration only the remaining time passes without `perform()`. The test runs actions through these calculations the way `ActionHandler` does.

| Test | Checks |
|---|---|
| `drinkingTenSeconds_givesFiveEvents` | drink (2 s), 10 s: 5 events, done after 10 s |
| `drinkingFiveSeconds_givesEventsAt0_2_4` | drink, 5 s: 3 events, done after 5 s |
| `eatingFifteenMinutes_gives90Events` | eat (10 s), 15 min: 90 events |
| `sleepingEightHours_gives96Events` | sleep (300 s), 8 h: 96 events |
| `walkingThirtySeconds_givesOneEventPerSecond` | walk (1 s), 30 s: 30 events |
| `aPunchWithADuration_givesOneEvent_andEndsAfterItsDuration` | a punch with 3 s: 1 event, done after 3 s |
| `anActionWithoutDuration_givesOneEvent` | duration 0: 1 event |
| `aDurationWithMilliseconds_endsAtTheNextFullSecond` | look (5 s), 7.3 s: 2 events, done after 8 s |
| `intervals_ofTheActionModes` | the intervals of a few action modes |

- **Not checked:** `ActionHandler` itself; it needs a simulation object and the display. Interrupting and stopping still call `perform()` as before.

### `ObjectMasterIdRangeTest`

`src/test/java/org/socialworld/core/ObjectMasterIdRangeTest.java`

`ObjectMaster` gives every type of simulation object a band of 1000 IDs: (index − 1) × 1000 to + 999, the first object gets the start + 1. Since 2026-11-17 `checkIdInRange` stops creating and loading with an `IllegalStateException` (also on the log and `System.err`) when an ID lies outside the band of its type. Before, the 1000th item would silently have got an ID in the band of magic.

| Test | Checks |
|---|---|
| `bands_areOneThousandWide_andFollowTheTypeIndex` | humans 0..999, items 3000..3999, magic from 4000 |
| `idsInsideTheBand_areAccepted` | human 1, items 3001 and 3999 |
| `theThousandthItem_wouldLandInTheBandOfMagic_andStopsTheSimulation` | item 4000 is refused; the message names the ID, the type, its band 3001..3999 and the band of magic it would land in |
| `theStartOfTheBand_isNeverGivenOut` | human 0 is refused; magic 5000 is refused without naming a band (no type above magic) |

- **Assumes:** only the static check is tested, without creating objects; the running simulation creates 776 items (7 clothes for each of the 100 humans, 75 loose apples, one apple shared by all mouths). Items are also limited by their 799 start positions (`swgauss_position`); since 2026-11-17 `CreateSimulationObjects.initState` stops with an `IllegalStateException` when they are used up instead of drawing for ever (not tested: the drawing is random and inside the creator).
- **Needs:** no database.

### `ActionHandlerTest`

`src/test/java/org/socialworld/core/ActionHandlerTest.java`

| Test | Checks |
|---|---|
| `newHandler_hasNoRunningAction` | `isActionRunning()` is false for a new handler |
| `newHandler_withoutAnAction_doesNothing` | without an action `doActualAction` returns `ACTIONHANDLER_RETURN_NOACTION` and nothing runs |
| `newHandler_hasNothingToStop` | `requestStop()` is false without a running action, and the handler still does nothing |

- **Not checked:** a running or finished action and ending one by `requestStop()`, because starting an action needs a simulation object and the display.

---

## Actions

### `InventoryMouthTest`

`src/test/java/org/socialworld/actions/handle/InventoryMouthTest.java`

- **Checks:** an inventory with an apple in the mouth answers `inventory_mouth` with a valid property of type `simulationObject`, not with `ValueProperty.getInvalid()` as before 2026-11-17. `ActionBodilyFunction` takes the item for eating and drinking from there, so nobody could eat or drink before.
- **Limit:** the apple itself is protected and only comes out of the property with a permitted token, as in `ActionBodilyFunction` through the `ObjectRequester`; that needs the running simulation. `StateInventory`, which passes the property on, is not tested.
- **Needs:** no database.

### `ActionMoveVelocityTest`

`src/test/java/org/socialworld/actions/move/ActionMoveVelocityTest.java`

The move action keeps its velocity and gives every event a new `Move` with the current value (movement concept G2, step M5): v = v + a × Δt.

| Test | Checks |
|---|---|
| `uniformMove_keepsItsVelocity` | without acceleration the velocity stays 100 mm/s over 5 events |
| `acceleratedMove_growsByAccelerationTimesInterval` | 1400 with a = 1400 per second becomes 2800, then 4200 |
| `braking_isANegativeAcceleration` | 1000 with a = −250 over 2 s becomes 500 |
| `startSpeed_isThePlaceholder` | the start speed is 100 mm/s (placeholder) |

- **Not checked:** `ActionMove.perform` itself (a new `Move` per event, the event carrying `move_velocity`), it needs a running simulation.

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

### `MXPlusNTest`

`src/test/java/org/socialworld/calculation/expressions/MXPlusNTest.java`

- **Checks:** 20,000 `MXPlusN` expressions with 10 different functions (`tiredness;1.00;-50.00` … `-41.00`) are all valid. Each gets a func_id from `FunctionPool.FIRST_RUNTIME_FUNCTION_ID` on; the same function always the same func_id, different functions different ones. Before 2026-11-16 every expression took its own slot in the class `Functions`, which was limited to 10000..99999.
- **Assumes:** like the new tests of `FunctionPoolTest`, it builds the singleton `FunctionPool`.

### `ChangeAttributeConditionTest`

`src/test/java/org/socialworld/calculation/expressions/ChangeAttributeConditionTest.java`

The condition term of an event influence rule (`ChangeAttribute.createConditionExpression`) holds comparisons of three arguments each: attribute, operator, value. One comparison stays a single attribute comparison and is decided by the sigmoid. Two or more are joined by AND and decided strictly. Builds real rules from JSON objects ("then mood + 100") and evaluates them for 200 objects.

| Test | Checks |
|---|---|
| `twoComparisons_areStrictAtBothBounds` | `mood >= 3800` and `mood < 5000`: 3799 and 5000 never change mood, 3800 and 4999 always do |
| `threeComparisons_allMustHold` | adds `courage < 6000`: the rule applies only if all three hold |
| `oneComparison_staysSoftAtTheThreshold` | `mood >= 5000` at 5000: 25 % to 75 % of the objects change mood |
| `argumentsThatAreNoMultipleOfThree_changeNothing` | four arguments make the condition invalid, mood stays unchanged |

- **Assumes:** the random tick is fixed (`TickRandomCache.setTick(5)`), so the soft case is the same in every run.
- **Needs:** no database.

### `SequenceTest`

`src/test/java/org/socialworld/calculation/expressions/SequenceTest.java`

`Sequence` has exactly three parts, so it splits a longer list into a tree of groups of three: over 6 expressions it takes [0..2] and [3..5] and passes the rest one level down, 4 to 6 become [0..2], [rest] and `Nothing`, 1 to 3 are the parts themselves. `ChangeAttributes` builds the entries of an event influence rule this way. Each test expression writes its number into a list when it is evaluated.

| Test | Checks |
|---|---|
| `everyExpressionRunsOnceInOrder_forCounts1To15` | for 1 to 15 expressions the list is exactly 0, 1, …, n−1. Before the fix of 2026-11-17, 4, 5, 10 and 11 expressions threw `ArrayIndexOutOfBoundsException`, and 6 and 12 lost three expressions and ran three others twice |
| `emptySequenceRunsNothing` | an empty sequence runs nothing |

- **Assumes:** `Sequence` skips a third part with the operation `nothing` (`Expression.evaluate`, `case sequence`). A new `Expression` has that operation, so the test expressions get the operation `value`, like real entries (`Branching`).
- **Needs:** no database.

### `State2ActionDescriptionTest`

`src/test/java/org/socialworld/calculation/descriptions/State2ActionDescriptionTest.java`

The state-to-action rules are JSON since 2026-11-18 (`swpool_s2a`, `JsonState2ActionDescription`). Their entries and those of the event reactions share the abstract class `ActionDescriptionEntry`. Without database.

| Test | Checks |
|---|---|
| `entries_areReadFromJson_andGiveOneFunction` | type 7 with the sleep entry: one entry, one function (the WENN/SONST chain of `CreateActionExpression`) |
| `withoutEntries_thereIsNoFunction` | a rule without `entrys` gives no function, so no action |
| `anEventParameter_isRejected` | an entry with `<GetEvParm>` throws `IllegalArgumentException` naming the type; there is no event behind a state-to-action rule |
| `theReactionEntry_getsTheFieldsOfTheCommonEntryClass` | Gson fills `conditions`, `actionType`, `target` of `ActionDescriptionEntry` and `relevanceThreshold` of `EventReactionDescriptionEntry` |

- **Not checked:** evaluating the chain into an action; that needs `ActionCreator` and a running simulation.

### `EventReactionDescriptionTest`

`src/test/java/org/socialworld/calculation/descriptions/EventReactionDescriptionTest.java`

The event reaction rules come from `swpool_erd` since 2026-11-18 (`TablePoolERD`, `EventReactionDescriptionPool`, `erd_regeln_konzept.md` E1 to E3); the test data in code are gone. Without database.

| Test | Checks |
|---|---|
| `theJson_keepsTheTagsReadable` | `JsonEventReactionDescription.toString()`: `<GetEvParm>event_causer</GetEvParm>` and `say_loudness > 50` unescaped, no `null` |
| `aRuleFromTheDatabase_namesItsPair_andGivesOneFunction` | the scream rule: `candidatesSayScream` (337), reaction type 1, one function, relevance threshold 100 without a value |
| `anUnknownEventType_becomesNothing_soThePoolIgnoresTheRow` | an unknown name gives `EventType.nothing`; the pool then ignores the row because the pair does not match |
| `aNewJsonHolder_isAJson` | `JsonEventReactionDescription` extends `Json` |

- **Not checked:** loading `swpool_erd` in the pool (needs the database).

### `EventInfluenceRulesFromDatabaseTest`

`src/test/java/org/socialworld/calculation/descriptions/EventInfluenceRulesFromDatabaseTest.java`

Evaluates a sample of the real rules of `swpool_eid` (every 97th) with all real arrays of `swpool_attribute`.

- **Checks:** the share of objects that change the target attribute follows the sigmoid of the condition attribute (k = 500), per 1000 steps of distance to the threshold, tolerance 0.03. A rule that fires writes m × basis + n (within 1, limited to 0..9999). Other attributes stay unchanged.
- **Needs:** MariaDB with data, it only reads. Without a database or with an empty table it is skipped, as well as when the sample has no rule of the checked form. Errors caused by the data let it fail.
- **Note:** it checks the old form of rule (one comparison, MX+N with another attribute). The rules generated from `event_influences.csv` (since 2026-11-16) have two comparisons or none, so once they are in `swpool_eid` this test skips itself.
- **Prints:** the table of expected and observed shares as `RULECHECK` lines with `gradlew test -i`.

### `PositionCalculatorSectorsTest`

`src/test/java/org/socialworld/calculation/application/PositionCalculatorSectorsTest.java`

`isInSameSectors` decides whether a moved object has to be sorted anew into the object search (`Simulation.changePosition`). The tests use the search levels of a human: level 8 of the base 9 tree, level 5 of the base 25 tree.

| Test | Checks |
|---|---|
| `thePlaceItselfIsInTheSameSectors` | the same position |
| `aSmallStepStaysInTheSameSectors` | 0/0 → 100/100 |
| `aChangeOfTheHeightStaysInTheSameSectors` | only z changes (0 → 900) |
| `aStepAcrossABorderOfTheBase9TreeChangesTheSectors` | x 400 → 600 crosses the border at 500 (sectors of 1 m) |
| `aStepAlongTheYAxisChangesTheSectorsToo` | the same along y |
| `aLongWayChangesTheSectors` | −25000/−37000 → 76000/75000 |
| `comparesOnlyDownToTheLevels` | 400 and 600 are the same at level 7 of base 9, different at level 8 |
| `comparesTheBase25TreeAsWell` | 400 and 5000 differ in the base 25 tree at level 5, although base 9 at level 1 is the same |
| `levelZeroAlwaysMeansTheSameSectors` | ±3,000,000 at levels 0/0 |

- **Assumes:** positions are in millimetres, the height does not count.
- **Not checked:** the re-sorting in `Simulation.changePosition` itself, it needs a running simulation.

### `PositionMovementTest`

`src/test/java/org/socialworld/calculation/application/PositionMovementTest.java`

The technical position movement (movement concept 2026-11-19, steps M2 and M3): events change only the active and the passive velocity, the position changes only in the time step `positionStep`, once per second. Only the vector arithmetic and the event types, without simulation.

| Test | Checks |
|---|---|
| `unit_hasLengthOne_andTheZeroVectorStaysZero` | the unit direction of (3,4,0) is (0.6,0.8,0); the zero vector stays zero with length 0 |
| `impulse_isAddedOnce_toThePassiveVelocity` | passive (100,0,0) plus an impulse of 200 in direction (0,2,0) gives (100,200,0); the starting vector is not changed |
| `impulseWithoutDirection_changesNothing` | an impulse without direction leaves the passive velocity as it is |
| `friction_halvesThePassiveVelocityPerSecond_andEndsInRest` | friction 0.5 per second (placeholder): 400 becomes 200; after 20 more seconds it is rest (below `MIN_SPEED`) |
| `stepMovesBySumTimesOneSecond` | the step is (active + passive) × 1 s |
| `technicalEventTypes_lieOutsideAllCategories` | `positionStep` (128) and `activeMoveStop` (256) are technical, belong to no category and do not pass the gate of the EventMaster route |
| `eventKinds_selfMove_candidatesMove_andSleepThroughTheGate` | `isSelfMove` for selfMoveWalk…Fly, `isCandidatesMove` for candidatesMoveWalk…Fly; selfSleep passes the gate |
| `ownCandidatesMove_isIgnored_bystandersAreStillPushed` | a candidatesMove that reaches its own causer is ignored for the velocity (the mover counts only its selfMove; seen in a run 2026-11-19: every mover pushed itself by 200 mm/s); bystanders are still pushed; candidates punches of the causer are not covered by this rule |

- **Not checked:** the processing in `PositionCalculator` (it reads and writes the state of an object), the list of moving objects, the time step from `Simulation.nextTimeStep` and the stop at the end of a move in `ActionHandler`; they need a running simulation.

### `CalculationVectorTest`

`src/test/java/org/socialworld/calculation/CalculationVectorTest.java`

Vector operations in `Calculation`, added for the reaction direction `ActionDir` (ERD X2, step X2.1a). The arithmetic itself is done by `Vector`.

| Group | Tests | Checks |
|---|---|---|
| Arithmetic | `addition_addsVectors`, `subtraction_subtractsVectors`, `negate_turnsVectorAround`, `multiplication_vectorTimesNumber_bothOrders` | vector + vector, vector − vector, −vector, vector × number and number × vector with integer and float |
| Component product | `multiplyComponents_isComponentWise_notCrossProduct` | (x1·x2, y1·y2, z1·z2), not the cross product |
| Normalize and length | `normalize_givesLengthOne`, `normalize_zeroVector_staysZero_withLengthZero`, `length_ofVector` | length 1 in the same direction; the zero vector stays (0,0,0) with length 0; lengths 5 and 7 |
| No side effects | `results_areUnchangeable_andOperandsStayUnchanged` | results are new unchangeable vectors; the operand keeps its values and is not marked normalized |
| Wrong operands | `mixedOrMissingOperands_giveNothing` | vector with number in addition, vector × vector in multiplication, Nothing and non-vectors give Nothing |
| Comparison | `compareEqual_worksForVectorsNow` | `compareEqual` and `compareNotEqual` work for vectors, since subtraction of vectors works now |

- **Assumes:** only values of type `vector` count as vectors; a `Direction` value (from position − position) is not accepted.

### `DirectionToReferenceTest`

`src/test/java/org/socialworld/calculation/expressions/DirectionToReferenceTest.java`

The expression of the tag `<ActionDir>L;kz;(ox,oy,oz);(fx,fy,fz)</ActionDir>` (ERD X2, step X2.1b), evaluated only through an argument list with `referencePosition` and `actorPosition`, without simulation.

| Group | Tests | Checks |
|---|---|---|
| To or away | `negativeLength_pointsAwayFromTheReference`, `positiveLength_pointsToTheReference` | reference (0,0,0), actor (3000,4000,0): L = −5 gives (3,4,0), L = +5 gives (−3,−4,0) |
| Height | `kzZero_ignoresTheHeight_andKeepsTheLength`, `kzOne_isSpatial_andTheOffsetIsAddedAfterwards` | kz = 0 drops the height difference and keeps length L; kz = 1 normalizes in space, the offset comes on top |
| Fallback | `samePlace_givesTheFallbackPlusOffset`, `belowMinimalDistance_givesTheFallback`, `missingPosition_givesTheFallback` | same place, distance below 100 mm = 0.1 m (also when only the height differs and kz = 0), or a missing position give the fallback, plus the offset |
| Optional parts | `missingParts_meanNoOffsetAndNoFallback` | without offset and fallback both are (0,0,0) |
| Formula | `wrongFormulas_giveAnInvalidExpression` | too few or too many parts, no number, no vector of three numbers: the expression is invalid |
| Sharing | `sameParameters_shareTheFuncID` | equal parameters (blanks do not matter) share one function and its func_id |

- **Assumes:** both positions are values of type `vector` in millimetres; what the reference is, is decided by the place that evaluates the rule (ERD: the event position, from X2.2 on).
- **Not checked:** the tag inside `CreateActionExpression` (building a whole action needs the simulation); that comes with X2.2 and the run.

### `VectorTest`

`src/test/java/org/socialworld/calculation/geometry/VectorTest.java`

| Test | Checks |
|---|---|
| `normalize_nonZero_isNormalizedWithLengthOne` | (3,4,0) becomes (0.6,0.8,0), is marked normalized, length 1 |
| `normalize_zeroVector_isNotNormalized_andHasLengthZero` | the zero vector stays (0,0,0), is not marked normalized, length 0 (before 2026-11-19 it was marked and `length()` gave 1) |
| `normalize_tinyVector_becomesZeroVector_withLengthZero` | a vector shorter than 0.00001 becomes the zero vector with length 0 |
| `mulComponents_multipliesInPlace_componentByComponent` | (2,3,4) · (−1,0.5,0) = (−2,1.5,0), in place |
| `mulComponents_resetsNormalizedFlag` | after the product the length is calculated again |
| `mulComponents_onUnchangeableVector_throws` | an unchangeable vector refuses the change |
| `mulComponents_onNothing_staysNothing` | the Nothing vector stays unchanged |

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
| `moveIntensityConstant_isTheFloatingPointHundred` | `CONST_MOVE_EVENT_INTENSITY` gives 100.0 as a float (100 mm = 0.1 m per move event) |
| `everyMoveMode_givesTheEventAnIntensity` | walk, run, sneak, jump, swim and fly have 4 functions, the fourth gives `event_intensity` = `MOVE_EVENT_INTENSITY` (quick solution 2026-11-19; since M5 the position follows `move_velocity`) |
| `moveVelocity_isTakenAsItIs_theAccelerationIsNotAddedTwice` | the event velocity is the `move_velocity` of the action (100), the acceleration (50) is not added again; the move action already integrated it (movement concept G2) |

### `FunctionPoolTest`

`src/test/java/org/socialworld/datasource/pool/FunctionPoolTest.java`

| Test | Checks |
|---|---|
| `createMatrix_putsEveryCellInItsPlace` | `createMatrix` puts every share, function and offset of a flat array into its own cell, and offsets stay offsets |
| `createMatrix_withFloatingPointShares_doesNotDivideBy100` | with floating-point shares the matrix does not divide by 100 |
| `tableFunctions_haveTheIndexOfTheirTypeAsFuncID` | func_id 0..10 hold the table function of the `FunctionByTable_Type` with that index; they compute the same as a new `FunctionByTable` of the type at 0, 1111, …, 9999 |
| `register_givesEachFunctionObjectOneFuncIDFromTheRuntimeRange` | `register` gives a function a func_id from `FIRST_RUNTIME_FUNCTION_ID` (10000) on, the same object always the same func_id, another object another one; `getFunction` finds them |
| `getFunction_beyondTheRegisteredRuntimeFunctions_isNothing` | an unknown func_id gives `FunctionNothing` |

- **Assumes:** the three new tests use the singleton `FunctionPool`. Building it tries to read the matrices from the database (only reading); without a database it prints the caught exception and goes on with the test data.
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

### `CreateAnimalInfluenceTypeTest`

`src/test/java/org/socialworld/datasource/createObjects/CreateAnimalInfluenceTypeTest.java`

A human or an animal gets the influence type of the character of its attribute array, the same for every event type (EID concept A7, since 2026-11-18). Before, `CreateSimulationObjects.initState` drew an independent influence type per event type from `GaussPoolInfluenceType`; that stays for all other objects. Without database.

| Test | Checks |
|---|---|
| `gaussIndexZero_isTheFarmer_forEveryEventType` | gauss index 0 gives influence type 0 (farmer) in all 512 places |
| `bothSidesOfTheGaussPool_leadToTheSameCharacter` | +3 and −3 both give 3 (maid), +99 and −99 both give 99 (child prodigy) |
| `sleepAndPunch_getTheSameInfluenceType` | gauss index −68: `selfSleep` and `candidatesPunchLeftFistStraight` both get 68 (wrestler) |
| `gaussIndexOutsideThePool_staysAtTheLastInfluenceType` | ±100 (only for a gauss value of exactly 4.0) gives 99 |

- **Assumes:** the gauss index of an attribute array is ±(character number − 1) (`AttributeArrayGenerator`) and the influence type of the event influence rules is character number − 1 (generator).
- **Not checked:** that `CreateAnimal.initState` really calls it; that needs the whole creation of objects (see "Not Covered").

### `CreateAnimalState2ActionTypeTest`

`src/test/java/org/socialworld/datasource/createObjects/CreateAnimalState2ActionTypeTest.java`

A human or an animal gets the state-to-action type of the character of its attribute array, character number − 1, like the influence type (A7, since 2026-11-18). Before, every object got type 1 from `GaussPoolState2ActionType`; that stays for all other objects. Without database.

| Test | Checks |
|---|---|
| `bothSidesOfTheGaussPool_leadToTheSameCharacter` | 0 → 0, +3 and −3 → 3, −99 → 99 |
| `theTypeIsTheInfluenceType` | for every gauss index −99..99 the same as `influenceTypesOfCharacter` |
| `gaussIndexOutsideThePool_staysAtTheLastType` | ±100 gives 99 |

- **Not checked:** that `CreateAnimal.initState` really calls it (see "Not Covered").

### `EventReactionFamilyTest`

`src/test/java/org/socialworld/datasource/pool/EventReactionFamilyTest.java`

The reaction type does not follow the character: `CreateAnimal.initState` draws one type per event family (`EventReactionFamily`: scream, say, whisper heard, looked at, touched), uniformly from 0 to 115 (`erd_regeln_konzept.md` E4, since 2026-11-18). Without database.

| Test | Checks |
|---|---|
| `thereAre116ReactionTypes` | `CAPACITY_GPRT_ARRAY` is 116 (7 × 16 + 4) |
| `everyFamilyGetsADrawnType_theOtherEventTypesKeepType1` | the families get 0..115, `selfSleep` and `candidatesMoveWalk` keep 1 |
| `theFamiliesAreDrawnSeparately_andAllTypesOccur` | 2000 draws: scream and looked-at differ at least once, all 116 types occur |
| `drawingDoesNotChangeTheGaussPool` | the drawn array is a copy |

- **Not checked:** that `CreateAnimal.initState` calls it (see "Not Covered").

### `DatabaseAssumptionsTest`

`src/test/java/org/socialworld/datasource/mariaDB/DatabaseAssumptionsTest.java`

- **Checks:** when the table cannot be reached, the calling test is aborted and counts as skipped.

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
| Catalog | `characters.csv` holds 100 characters in order; a wrong number of levels is rejected; every character has a need of sleep (6 to 10 h) and food (1 to 5 meals), farmer 8 h and 3, ascetic 6 h and 1, wrestler 5 meals; without the two columns the default need (8 h, 3) applies; 7.3 h, 10.5 h, 0 and 6 meals are rejected |
| Ideal | all 100 characters have the ideal columns (maid tiredness 3000 / 8500, miller hunger 2500 / 4000, stable boy tiredness 2500 / 8000 and hunger after a meal 5000), a line without them gets the farmer's 20/70 and 40/60; 18 fields are read, 16 rejected; lower end not below the upper one or above 99 is rejected; only hunger and tiredness have an ideal, asking for mood is refused |
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
| Hunger, tiredness | rise linearly (diagonal 1.0): 240 and 180 per hour for the median person; in every real matrix hunger rises by 3 to 5 per step, the same at 1000 and at 8000; hunger rises from 0 in the real matrix. Until 2026-11-17 the rise was exponential with reference points (8 h > 5000, 12 h ≥ 9000) |
| Writer (recording store) | order of the foreign keys: delete mapping, gauss rows, cells, matrices; insert matrices, cells, gauss rows, mapping; rows and columns from 1 |
| Mapping | all 199 × 199 combinations once; always a matrix of the character of the array; all variants are reached; both signs of the drawn index give the same variant |

### `TextFileTest`

`src/test/java/org/socialworld/tools/dct/TextFileTest.java`

- **Checks:** a file in UTF-8 is read with its umlauts; a file that is no valid UTF-8 is read as ISO-8859-1.
- **Writes:** only into a temporary JUnit directory (`@TempDir`).

### `CreationToolEventInfluenceDescriptionTest`

`src/test/java/org/socialworld/tools/dct/CreationToolEventInfluenceDescriptionTest.java`

- **Checks:** `withDecimalPoint` turns `1,5` into `1.5`, leaves `1.5` and `-20` as they are, and removes surrounding spaces.

### `EventInfluenceCatalogTest`

`src/test/java/org/socialworld/tools/dct/EventInfluenceCatalogTest.java`

The two sources of the generated event influence rules: `event_situations.csv` (situation, event type, factor) and `event_influences.csv` (character, situation, sentence, attribute, four steps).

| Test | Checks |
|---|---|
| `situations_fromTheResource_coverTheRelevantEventTypesOnce` | 91 event types; e.g. `selfSleep` is situation 1, `candidatesWeaponClub` situation 20 with factor 1.5, `percipientMoveWalk` factor 0; `targetEat` has no situation |
| `situations_rejectAnUnknownEventTypeAndAnEventTypeTwice` | an unknown event type or one listed twice is refused |
| `catalog_fromTheResource_hasTheCharacters1To100WithAll25Situations` | the file has the characters 1 to 100, each with lines in all 25 situations |
| `catalog_lineWithoutAttribute_onlyRecordsTheSentence` | farmer, situation 9: one line with a sentence and no attribute |
| `catalog_readsTheFourSteps` | attribute and the four steps of a line |
| `catalog_rejectsStepsWithBothSignsAndUnknownAttributes` | steps with both signs in one line and an unknown attribute are refused |

- **Assumes:** the resource files as they are in git; the counts change when situations or characters are added.

### `EventInfluenceGeneratorTest`

`src/test/java/org/socialworld/tools/dct/EventInfluenceGeneratorTest.java`

`EventInfluenceGenerator` and `EventInfluenceRuleBuilder` build the rules from the two files and `characters.csv`. The tiers of hunger and tiredness sit at the ideal of the character, those of the other attributes at the band of its level. The built rules are parsed and evaluated like in the simulation (`EventInfluenceDescription`). Writes into `InMemoryEventInfluenceStore`.

| Test | Checks |
|---|---|
| `roundSymmetric_roundsHalfAwayFromZero` | 7.5 → 8, −7.5 → −8, 0.4 → 0, −0.5 → −1 |
| `farmerPunched_eachTierOfTheBandHasItsStep` | farmer, punched: mood 1500 / 3000 / 6000 / 8500 lose 10 / 100 / 200 / 200 (band 2000..8000, middle 5000) |
| `farmerPunched_onlyOneTierApplies_inBothDirections` | falling mood 2050 → 1950 (no second step below the band), rising courage 6195 → 6205 (no second step above the band) |
| `factorOfTheEventType_isAppliedAndRounded` | farmer hit by the club (factor 1.5): mood −45 / −450 / −750 / −750, by a stab: −30 / −300 / −500 / −500 |
| `generate_writesOneRulePerEventTypeAndInfluenceType_oneCharacterEach` | 193 × 100 rules; 100 characters for the 100 influence types, nothing repeated (`isRepeated()` false); influence types 1 (farmer's wife), 10 (baker), 21 (woodcutter), 30 (village smith), 49 (mercenary), 50 (knight), 68 (wrestler), 74 (wounded veteran), 87 (giant) and 99 (child prodigy) differ from the farmer's rule |
| `eventTypeWithoutSituation_andSituationWithoutEffect_getANeutralRuleThatChangesNothing` | `targetEat` (no situation) and `selfExamineByLook` (no effect for the farmer) get an empty rule; evaluated, it gives the attribute array back unchanged |
| `maidSleeps_theTiersOfTirednessSitAtHerIdeal_notAtTheBandOfHerLevel` | maid sleeping (ideal 30/85, band of `+` 5800..7500): tiredness 2500 / 5000 / 7000 / 9000 lose 7 / 51 / 77 / 94; 5000 lies below her band but in the lower half of her ideal |
| `millerEats_theTiersOfHungerSitAtHisIdeal` | miller eating (ideal 25/40, 4 meals): hunger 2000 / 3000 / 3500 / 4100 lose 2 / 12 / 17 / 20; 4100 lies inside his band but above his ideal |
| `needOfSleep_scalesTheSleepSteps` | tiredness while sleeping × 8 / sleepHours: farmer (8 h) −9 / −60 / −85 / −102 unchanged, shopkeeper (7.5 h) −10 / −64 / −91 / −109 |
| `needOfFood_scalesHungerAndTirednessWhileEating` | × 3 / mealsPerDay on hunger and tiredness while eating: shepherd (2 meals) hunger −3 / −24 / −35 / −41, tiredness +5 / +3 / +2; farmhand (4 meals, 8.5 h) hunger −12 / −17 / −20, sleep −64 / −96 / −112 |
| `needFactor_isOneOutsideSleepingAndEating` | ascetic (6 h, 1 meal): 8 / 6 for sleep, 3 for hunger while eating, 1 for mood while eating and for walking |

- **Assumes:** the resource files as they are in git (values of the farmer, maid and miller).
- **Needs:** no database.

### `State2ActionGeneratorTest`

`src/test/java/org/socialworld/tools/dct/State2ActionGeneratorTest.java`

`State2ActionGenerator` writes one rule per state-to-action type from `characters.csv` (DCT button "S2A DB" → `swpool_s2a`, `state2action_konzept.md` section 7). Type t gets character t + 1. The chain: sleep, eat, then up to three own rules from the most pronounced levels (hunger and tiredness left out); a character without one gets the former curiosity rule. Writes into `InMemoryState2ActionStore`.

| Test | Checks |
|---|---|
| `generate_writesOneRulePerType_andClearsTheStoreFirst` | 100 rules with 474 entries, 1 fallback, each with its own type; an old entry is gone |
| `typeT_getsCharacterTPlusOne` | type 0 farmer, 77 character 78, 98 night watchman |
| `sleepAndEat_comeFirst_atTheIdealOfTheCharacter` | farmer `tiredness >= 7000`, `hunger >= 6000` (as before); alchemist `tiredness >= 9000`; starving refugee `hunger >= 9500`; modes sleep 1, eat 3 |
| `sleepLasts_theSleepHoursOfTheCharacter_eatingStays18Minutes` | farmer 8 h, mad prophet 6 h, paralysed old man 10 h; eating 1080000 for all |
| `farmer_powerHigh_runs_inHisDirection` | power `+` → `power >= 6650`, run (move 12, 5 s), direction `(5.00,0.00,0)` |
| `maid_fourPronouncedLevels_theFirstThreeInTheOrderOfTheAttributes` | courage `-` → sneak, morals `+` → sayNormal (no direction), materialism `-` → sayWhisper; spirituality `+` is the fourth and left out |
| `nightWatchman_hungerAndTirednessGiveNoOwnRule` | sleep at 9500; mood `-` → sayScream, curiosity `-` → sneak; tiredness `+++` and hunger `+` give no own rule |
| `theMostPronouncedLevel_comesFirst` | alchemist: curiosity `++` before courage `+` and materialism `+` |
| `ropemaker_withoutAPronouncedLevel_getsTheFormerCuriosityRule` | ropemaker (only `o` and `~`): the curiosity rule with `(2,7,0)` |
| `middleOfTheBand_roundedUp` | 6650, 8200, 9500, 3350, 1800, 500 |
| `direction_dependsOnTheNumber_withADecimalPoint_andWithoutMinusZero` | maid `(4.91,0.94,0)`, alchemist `(0.63,-4.96,0)`, 90° and 270° without `-0.00` |
| `everyRule_becomesOneChain_withoutEventParameters` | all 100: no `GetEvParm`, no escaping, no `null`, no `'`; read back as `State2ActionDescription`: 3 to 5 entries, 1 function |

- **Assumes:** `characters.csv` as it is in git (values of the named characters).
- **Needs:** no database.
- **Not checked:** that sneak, sayNormal and sayWhisper run through the pipeline; that needs a run.

### `EventReactionGeneratorTest`

`src/test/java/org/socialworld/tools/dct/EventReactionGeneratorTest.java`

`EventReactionGenerator` writes the reaction rules (DCT button "ERD DB" → `swpool_erd`, `erd_regeln_konzept.md` section 4). Type t < 112: style t / 16 (observer, toucher, fearful, turning away, echo, indifferent, brawler), variant t mod 16 = c + 4 e + 8 k (condition, fallback, length). Types 112..115: look, else hand as before. Writes into `InMemoryEventReactionStore`.

| Test | Checks |
|---|---|
| `generate_writes540Rules_40PairsStayWithoutReaction` | 5 × 116 pairs, 540 rules with 800 entries, 40 without a reaction (indifferent without fallback); the old row is replaced |
| `theTypeGivesStyleAndVariant` | 112 former types from, 116 types; 0 observer, 52 turning away, 111 brawler; 46 is c 2, e 1, k 1 |
| `observer0_looksShortlyInAGoodMood_otherwiseNothing` | `mood >= 5000` → look at the causer, 5 s |
| `observer5_looksInABadMood_touchesInAGoodOne` | `mood < 5000` look, else hand at the causer |
| `fearful46_sneaksLongWhenBrave_otherwiseWalksAwayLong` | `courage >= 5000` sneak 6 s, else walk 15 s, both away from the event (`ActionDir`) |
| `turningAway52_walksAway_otherwiseScreamsBack` | walk 5 s, else sayScream |
| `echo68_screamsBack_otherwisePunches` | sayScream, else punch (7 / 74) at the causer |
| `echo_answersInTheMannerOfTheFamily` | scream 102, say 101, whisper 103, looked at 101, touched 102 |
| `indifferent_withoutFallbackNothing_withFallbackALookUnderTheOppositeCondition` | 80: no rule; 85: `mood >= 5000` → look |
| `brawler_punchesShort_andTakesTheClubForOneBlowWhenLong` | 96 punch 2 s, 104 club (6 / 67) 1 s, not stretched |
| `formerTypes_lookElseHand_withTheLoudnessOnlyForTheScream` | 112 with `say_loudness > 50` / `> 30`, hand now with the causer as target; 115 looked at without loudness |
| `awayFromEvent_isTheTagActionDir_withTheTypeDirectionAsFallback` | `<ActionDir>-5;0;(0,0,0);(5.00,0.00,0)</ActionDir>` for type 0, the formula gives a valid `DirectionToReference` |
| `onlyMoves_haveADirection_andItIsAlwaysAwayFromTheEvent` | in all rules of all families only move entries (fearful, turning away, also as fallback) have a direction, always `awayFromEvent` of their type |
| `testFly_letsTheFearfulFlyAwayAndUp_everythingElseStays` | with the test switch (DCT "Test: fly") every sneak becomes fly with `flyAwayFromEvent`, same conditions and duration; all other entries stay as without the switch |
| `flyAwayFromEvent_isSpatialWithAnUpwardOffset` | `<ActionDir>-5;1;(0,0,3);(5.00,0.00,0)</ActionDir>` for type 0, a valid `DirectionToReference` |
| `direction_dependsOnTheType_withoutMinusZero` | the fallback direction: 0 `(5.00,0.00,0)`, 29 `(0.00,5.00,0)`, 87 `(0.00,-5.00,0)` |
| `everyRule_namesItsPair_andBecomesOneChain` | all 540 read back as `EventReactionDescription`: event type and reaction type match, one function, no escaping, no `null`, no `'` |

- **Needs:** no database.
- **Not checked:** the reactions in a run, ping-pong.

### `AttributeCharacterOverviewTest`

`src/test/java/org/socialworld/tools/dct/AttributeCharacterOverviewTest.java`

| Group | Checks |
|---|---|
| Content | every character gets its arrays, positive index first, and its matrices; matrices sorted by the smallest drawn distance; the variants of the generator from typical to unusual |
| Errors | fails without an array or a matrix for a character, for a matrix of two characters, for a mapping to an unknown Gauss index, for a matrix without cells and for an array of an unknown character |
| Real tables | `read_fitsTheRealTables` reads the database and is skipped without it or with an empty table; errors caused by the data let it fail |

### `AttributeMatrixCellTest`

`src/test/java/org/socialworld/tools/dct/AttributeMatrixCellTest.java`

| Group | Checks |
|---|---|
| Diagonal | shows the return rate; above one it shows the growth |
| Coupling | shows the points per step and the function; a `horizontalMax` outside the diagonal is a constant |
| Edge cases | a share of zero is empty; an unknown function is shown by its number |

### `AttributeMatrixTargetsTest`

`src/test/java/org/socialworld/tools/dct/AttributeMatrixTargetsTest.java`

| Group | Checks |
|---|---|
| Targets | the constant divided by the return rate; no target for hunger and tiredness; a coupling between attributes with targets counts, a coupling of rested hunger does not; finds the targets of the generator |
| Errors | fails without a return rate and for an unknown function |
| Real tables | `compute_staysInTheBandsForTheRealTables` reads the database and is skipped without it or with an empty table; errors caused by the data let it fail |

### `TablePoolAttributeDataReaderTest`

`src/test/java/org/socialworld/tools/dct/TablePoolAttributeDataReaderTest.java`

| Group | Checks |
|---|---|
| Arrays | all arrays with all values in range |
| Matrices | every matrix of the Gauss indices; the diagonal is `identical` for every attribute; the row is the source and the column the target |
| Mapping | all combinations and only known matrices |

- **Needs:** MariaDB with data, only read. Without a database or with an empty table all five tests are skipped, errors caused by the data let them fail.

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

## Display

The display of the running simulation (`SimDashboard`). The panels are built but never shown, so no screen is needed. Mouse clicks are simulated by events sent to the panels.

### `VisualModelTest`

`src/test/java/org/socialworld/visualize/VisualModelTest.java`

| Group | Checks |
|---|---|
| Actions | a repeated report of the same action is not new, another action is; IDs outside 1..100 are ignored |
| End of an action | an ended action stays readable; an action that is not done counts as interrupted |
| Attributes | the first report is no change; changes are calculated per attribute and survive reports without a change; values are copied; the nothing object is ignored |
| Start values | do not count as a report and do not overwrite a reported value |
| Remaining duration | counted down from the time of the last report, never below 0 and not raised by a time before the report; each report of the action handler starts again from its value (a sleep reports only every 300 s, EID concept 7.1) |
| Threads | a snapshot does not change afterwards; reports from several threads all arrive |

### `ActionGridTest`

`src/test/java/org/socialworld/visualize/ActionGridTest.java`

| Group | Checks |
|---|---|
| Families | every `ActionType` except `ignore` has a family; the colours differ from each other and from the idle gray |
| Fly | `runningFly_hasTheConspicuousFlyColor_otherMovesKeepTheFamilyColor`, `flyColor_differsFromAllFamilyColorsAndTheIdleColor`: a running fly has `COLOR_FLY` (test flight, ERD X2 T2), other moves keep the family colour, no type no colour; `COLOR_FLY` differs from all family colours and the idle gray |
| Short names | every `ActionMode` except `ignore` has a unique name of at most 8 characters |
| Field | a running action has the full colour, an ended one fades to gray; share of the remaining duration, counted down between two reports; black or white text by contrast |
| Mouse | position to ID with IDs starting at 1, positions outside stay in range; tool tip text |
| Rates | sum of the attribute changes; the rate per second is renewed once per second |

### `AttributeMatrixTest`

`src/test/java/org/socialworld/visualize/AttributeMatrixTest.java`

| Group | Checks |
|---|---|
| Colours | three anchors: low, middle, high; values between them |
| Column heads | two letters, unique, first letter of the attribute; `morals` is `mr` |
| Sorting | high to low and back; unknown values last in both directions; equal values stay in the order of their IDs; a click cycles through high, low and IDs |
| Two blocks | a click on the left selects from the first 50, on the right from the second 50; the column does not matter; the sorted list goes on at the top of the right block; a click on a head of the right block sorts, a click on the ID part of the head does not |
| Change mark | fades over time; gets longer with the size of the change |
| Tool tip | value, last change and its age; text for an unknown object |

### `ObjectDetailTest`

`src/test/java/org/socialworld/visualize/ObjectDetailTest.java`

| Group | Checks |
|---|---|
| Last actions | newest first; the oldest drops out when full; ignored actions are left out; a snapshot does not change afterwards |
| History of one human | unknown humans are skipped; one sample per interval; oldest first and forgets beyond its length |
| Texts | current action or idle, its remaining time counted down between two reports; change with arrow on the display scale; type of the human as character number and influence type (`Typ 4 (InfluenceType 3)`, since 2026-11-18); position in meters with a decimal point; direction of the running move action (`Bewegungsrichtung x 3.54, y 3.54, z 3.01`, `Bewegungsrichtung keine` without a running move, since 2026-11-19), the model keeps the action reported last; age and interruption |
| Graphics | share of the bar; the scale of the history follows the values and keeps a smallest span |
| Selection | a press selects the right human in the grid and in the sorted matrix; a press on the head of the matrix selects nobody |

### `PopulationHistoryTest`

`src/test/java/org/socialworld/visualize/PopulationHistoryTest.java`

| Group | Checks |
|---|---|
| Sampling | one sample per second; what is older than five minutes is forgotten |
| Actions | running actions counted per family; ended and ignored actions left out |
| Attributes | mean and percentiles only from humans with known attributes; no spread without any; percentiles of 100 values and of a single value |
| Drawing | stacks add up; time and value to pixels |

### `AutomaticSelectionTest`

`src/test/java/org/socialworld/visualize/AutomaticSelectionTest.java`

| Group | Checks |
|---|---|
| Off | the current human stays |
| Start | changes of the last 5 seconds count at once, older ones do not |
| Choice | the largest single change wins; a fall counts like a rise; one strong change beats several small ones |
| Holding time | no switch during the holding time, a switch after it; without a new change the human stays; a change before the last switch does not count again |
| Stop and texts | stop keeps the human and ends the reason; texts for waiting and for the reason |

### `PictureSequenceTest`

`src/test/java/org/socialworld/visualize/PictureSequenceTest.java`

The picture sequence taken over from the former display `SimVisual`: a heart, then MANU I LOVE U, shown in the action grid.

| Test | Checks |
|---|---|
| `blank_hasNoFields` / `everyFigure_hasFields` | the blank figure is empty, every other figure has fields |
| `heart_isRedAndLiesWhereItLayInTheOldDisplay` | red, 42 fields, e.g. 21 and 84 set, 0 and 24 not |
| `letters_areBlack` | `M` and `E` are black |
| `cycle_lastsSixtyTwoTicks` | one cycle is 62 ticks |
| `figureAt_startsWithAPauseAndThenTheHeart` | ticks 0–4 blank, 5–6 heart, 7 blank |
| `figureAt_repeatsAfterTheCycle` | after 62 ticks it starts again |
| `figures_comeInTheOrderOfTheMessage` | heart, M, A, N, U, I, L, O, V, E, U |
| `grid_hasNoToolTipWhileShowingAFigure` | the action grid shows no tool tip while a figure is on it |

### `TypeOverviewTest`

`src/test/java/org/socialworld/visualize/TypeOverviewTest.java`

The extra window behind the button "Typen" of the dashboard (since 2026-11-18): which human has which type, and with the toggle "Anzahl pro Typ" how many humans each type has. The type is the character number, influence type + 1 (EID concept A7). Only the data and the table models, without a window.

| Test | Checks |
|---|---|
| `assignments_areOrderedByHuman_andTheTypeIsTheInfluenceTypePlusOne` | humans ordered by object ID; influence type 3 is type 4 (maid); a type without a name gets "?" |
| `counts_haveOnlyTheTypesThatOccur_orderedByType` | only the types that occur, ordered by type, with the number of their humans; number of humans and of types |
| `button_namesTheOtherView` | the button says "Anzahl pro Typ" in the list of humans and "Menschen einzeln" in the counts |
| `withoutHumans_bothListsAreEmpty` | without humans both lists are empty, the empty table has no number column |
| `tables_keepNumbersAsNumbers_soThatSortingIsNumeric` | column heads; numbers stay `Integer` (sorting by a column puts 2 before 10), names stay `String`; nothing can be edited |

---

## ACT: Action Creation Tool

The tool in `workbench.act` gives a chosen human an action into the normal pipeline of the running simulation: the action is inserted into the human's action list and handled like any other action. Only a human without a running action may get one, and not the same action twice. The running action of a human can also be ended, e.g. to wake a sleeping one, and the tool can check whether the search by position finds every human at its real place. So far only the say family: whisper, normal, scream. All tests run without a simulation, without a database and without a screen; positions are in millimetres.

### `ActionRequestTest`

`src/test/java/org/socialworld/workbench/act/ActionRequestTest.java`

What the user entered in the window, before anything is sent.

| Test | Checks |
|---|---|
| `keepsItsValues` | mode, causer, intensity, priority and duration come back unchanged |
| `acceptsAValidRequest` / `acceptsTheLimits` | `NONE` for a valid request, also for intensity 0, priority 0 and 256, duration 0 and one hour |
| `rejectsAMissingMode` / `rejectsAModeOfAnotherFamily` | no mode or `walk` → `MODE_NOT_SUPPORTED` |
| `rejectsAnInvalidCauser` | ID 0 or −1 → `INVALID_CAUSER` |
| `rejectsAnInvalidIntensity` | negative, NaN, infinite → `INVALID_INTENSITY` |
| `rejectsAnInvalidPriority` | −1 or 257 → `INVALID_PRIORITY` |
| `rejectsAnInvalidDuration` | −1 ms or more than one hour → `INVALID_DURATION` |

### `ActionSenderTest`

`src/test/java/org/socialworld/workbench/act/ActionSenderTest.java`

Only what works without a running simulation: every action fetches the simulation in its constructor, so creating the action and inserting it into the action list are not tested.

| Test | Checks |
|---|---|
| `setsTypeAndMode` | type `say`, mode `sayScream` |
| `setsIntensityAndPriority` | 60.0 and 120 |
| `setsTimesAndDuration` | min time = now, max time = now + `START_WINDOW_IN_MILLISECONDS`, duration from the request |
| `coversAllStandardPropertiesOfAnAction` | every name of `ActionType.getStandardPropertyNames()` has a valid value |
| `doesNotSendAnInvalidRequest` | `send` returns the problem of the check for each of the five kinds and gives no action into the pipeline |
| `doesNotStopForAnInvalidCauser` | `stop` with ID 0 or −1 returns `INVALID_CAUSER` |

### `EventRangeTest`

`src/test/java/org/socialworld/workbench/act/EventRangeTest.java`

Which humans an action reaches, as drawn on the range map.

| Group | Checks |
|---|---|
| Modes | whisper, normal, scream in this order; their events `candidatesSay…`; ranges 1000, 5000 and 10000 mm from the event type; all act in every direction (effect angle > 180°) |
| Unsupported | `walk` and `null` are not supported, give `EventType.nothing` and range 0 |
| Distance | exactly at the range counts, 1 mm beyond does not; measured in the plane (3000/4000 is 5000); also around a causer away from the origin; no overflow for `Integer.MIN_VALUE` and `MAX_VALUE` |
| Result | never the causer, not a human on the very spot of the event, sorted by ID ascending, empty for an unknown causer |

- **Assumes:** the event master sorts out the candidates the same way: at most the range away, and not on the very spot of the event.

### `NeedsResetTest`

`src/test/java/org/socialworld/workbench/act/NeedsResetTest.java`

The dashboard button "Wecken & Sättigen" (since 2026-11-18) calls `NeedsReset.endSleepingAndEating()`: every human whose running action is sleep or eat gets it ended (`stopActualAction`) and its tiredness and hunger set to 2000 (`setAttributeFromOutside`, cluster act only, without an event). The logic is in the workbench because of the token; `SocialWorld` hands it to `SimDashboard.setNeedsReset`. Without database.

| Test | Checks |
|---|---|
| `onlySleepingAndEatingAreEnded` | sleep and eat yes; drink, walk, sayScream and no action no |
| `tirednessAndHungerGoTo2000` | both values |
| `theResultSaysHowManyWereWokenAndFetched` | "12 geweckt, 5 vom Essen geholt" |

- **Not checked:** ending the actions and setting the attributes in a running simulation.

### `ActionMapScaleTest`

`src/test/java/org/socialworld/workbench/act/ActionMapScaleTest.java`

The scale of the range map, on a panel of 648 × 448 pixels.

| Group | Checks |
|---|---|
| Fitting | all positions lie inside the map and are centred; the same scale on both axes; y grows upwards; a length is scaled like a position |
| Edge cases | a single position, no position, a panel without a size yet |
| Back to the world | pixel → position within one pixel, y upwards (for moving a human by drag) |
| Mouse | the nearest human within the tolerance is found, beyond it `NO_OBJECT` |

---

## Not Covered

- Simulation run, events, actions, conversation: no tests so far.
- `SimDashboard` as a whole: window, timer, buttons and reading the position and the type of a human need a running program; only the model and the panels are tested. The same holds for the window of the types (`TypeOverviewWindow.show`) and reading the names from `characters.csv` there.
- ACT as a whole: the window, refusing a busy human or an action already planned, inserting the action into the pipeline, ending a running action, checking the search tree (`SearchTreeCheck`) and moving a human by drag need a running simulation; only the request, the properties, the range and the scale are tested. The direct perform of the first version was checked by hand on 2026-11-10, the pipeline on 2026-11-11 (busy message and the say shown in the dashboard), ending a running action and the search tree check on 2026-11-11 as well (a human dragged far away is found there, and its scream reaches nobody).
- `Expression` as a whole: only indirectly, through `KnowledgeElementLinesVsJsonTest`, the composer, `BranchingProbabilityTest`, the rule test and `SequenceTest`.
- Loading from MariaDB (`datasource`): no tests except the reading tests named under the conditions, so that CI runs without a database. In particular the DCT writers against the real tables.
- The lookup of the mapping in `CreateAnimal` and setting the influence types, the state-to-action type and the reaction types there (A7, E4): one line each, they would need the whole creation of objects; only `influenceTypesOfCharacter`, `state2ActionTypeOfCharacter` and `EventReactionFamily.drawReactionTypes` are tested.
- MCT generators and `MapPanel`: no tests; `TileGridCubes` has no caller yet.
- State2Action: loading `swpool_s2a` in `State2ActionDescriptionPool` and the action that a rule gives in a run; only JSON, chain, generator and type are tested.
- Event reactions: loading `swpool_erd` in `EventReactionDescriptionPool` and the reaction in a run; the target events of look (X1) and of attacks without a weapon (X3) need a running simulation.
- Action2Performer: still on lines, only the arithmetic of the intensity is tested.
