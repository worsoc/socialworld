# Tests: Calculation

Part of the [test overview](TESTS.md): expressions, functions, knowledge, positions and vectors.

**18 test classes, 120 tests.**

## Overview

| Area | Test class | Tests | Checks | Skipped when |
|---|---|--:|---|---|
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
| Calculation | `PositionMovementTest` | 16 | technical position movement: impulse, friction and rest at 50 mm/s, bystander push 0.2 × v, blows (share of the intensity, away from the hitter, only the candidates event), time step, technical event types in their own block 512..527, no self-push | – |
| Calculation | `CarriedFollowTest` | 5 | carried objects take carrier + offset per place without drift, ignore every event but followCarrier, followCarrier is technical (514) | – |
| Calculation | `InventoryCalculatorTest` | 5 | taking and dropping: the hand (requested, else right, else left), the reach of 1 m on the ground, the self inventory events | – |
| Calculation | `CalculationVectorTest` | 11 | vector operations in `Calculation`: add, subtract, negate, scale, component product, normalize, length | – |
| Calculation | `DirectionToReferenceTest` | 10 | tag `ActionDir`: direction to or away from a reference position, height weight, offset, fallback | – |
| Calculation | `VectorTest` | 7 | `Vector`: normalizing (the zero vector has length 0) and the component product | – |

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
| `friction_halvesThePassiveVelocityPerSecond_andEndsInRest` | friction 0.5 per second: 400 becomes 200; after 20 more seconds it is rest (below `MIN_SPEED`) |
| `friction_restBelowFiftyMillimetersPerSecond` | `MIN_SPEED` is 50 mm/s (numbers N3, 2026-11-20): 120 halves to 60 and moves on, 60 halves to 30 and is rest |
| `bystanderPush_isAFifthOfTheVelocityOfTheMover` | the push on a bystander is 0.2 × the velocity of the mover (N1): sneak 500 → 100, walk 1400 → 280, run 4000 → 800, standing 0 → 0 |
| `stepMovesBySumTimesOneSecond` | the step is (active + passive) × 1 s |
| `technicalEventTypes_lieOutsideAllCategories` | `positionStep` (512) and `activeMoveStop` (513) are technical, belong to no category and do not pass the gate of the EventMaster route |
| `technicalEventTypes_haveTheirOwnBlockBehindAllCategories` | the technical block starts at `MAX_EVENT_TYPE` (512) and ends before 528; exactly the types in it are technical, every other type is below 512 and fits into the pools; 128, 256 and 384 are empty again (2026-11-20) |
| `eventKinds_selfMove_candidatesMove_andSleepThroughTheGate` | `isSelfMove` for selfMoveWalk…Fly, `isCandidatesMove` for candidatesMoveWalk…Fly; selfSleep passes the gate |
| `ownCandidatesMove_isIgnored_bystandersAreStillPushed` | `isOwnEventSeenAsCandidate`: a candidatesMove that reaches its own causer is ignored for the velocity (the mover counts only its selfMove; seen in a run 2026-11-19: every mover pushed itself by 200 mm/s); bystanders are still pushed; candidates punches of the causer are not covered by this rule |
| `ownCandidatesBlow_isIgnored_whoIsHitIsPushed` | a candidates blow (punch, weapon) that reaches its own hitter is ignored for the velocity (P3, B5, 2026-11-20); who is hit is pushed; other candidate events of the causer such as its scream are not affected |
| `blowEventKinds_candidatesBlowAndWeapon` | `isCandidatesBlow` for 304-310 and 312-317, `isCandidatesWeapon` only for 304-310; moves and target events are no candidates blows |
| `onlyTheCandidatesEventOfABlowPushes_notTheTargetEvent` | targetWeapon… and targetPunch… (176-189) no longer change the position (B4), the candidates blows still do |
| `blowImpulse_isAShareOfTheEventIntensity_withAnUpperLimit` | event intensity 5000 gives 500 mm/s for a fist (0.1) and 750 for a weapon (0.15); at most 3000, never negative, 0 for NaN (B3) |
| `blowPushesAwayFromTheHitter_notAlongItsView` | the hitter looks east, the one hit stands north-east: pushed north-east (B2); standing on the hitter's place: along the blow |

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

### `CarriedFollowTest`

`src/test/java/org/socialworld/calculation/application/CarriedFollowTest.java`

A carried object (connection `carried`, inventory concept V2, 2026-11-23) has its own absolute position: the position of its carrier plus a fixed offset per place (`Inventory.offsetOf`). After every position step and every relocation of the carrier, the carried objects get the technical event `followCarrier` (514); the PositionCalculator sets their position and lets the search by position follow. A carried object ignores every other event: no own step, no push, no blow.

| Test | Checks |
|---|---|
| `followPosition_isCarrierPlusOffset_andLeavesBothAlone` | carrier (1000, 2000, 0) + offset (0, 0, 800) gives (1000, 2000, 800); carrier and offset stay as they were |
| `followPosition_afterAStep_keepsThePlace_noDrift` | over 1000 steps the left shoe stays at x + 150, z − 800 of its carrier (within 0.01 mm: the offset is added anew each time) |
| `aCarriedObject_ignoresEverythingButFollowCarrier` | carried: position step, push and active move stop are ignored, followCarrier is not; free objects as before |
| `followCarrier_isATechnicalEvent` | followCarrier lies in the technical block (514) |
| `offsetsPerPlace_asAtTheStartOfTheSimulation` | head z + 800, upper body + 300, legs − 400, left sock x + 150, right shoe x − 150, right hand x − 300, back y − 200, mouth and belt 0 |

- **Not checked:** `followCarrier` and `letCarriedFollow` in the running PositionCalculator (they need states and write access); the offset does not turn with the carrier.

### `InventoryCalculatorTest`

`src/test/java/org/socialworld/calculation/application/InventoryCalculatorTest.java`

The InventoryCalculator (inventory concept V4, 2026-11-23) changes the own inventory by `selfInventoryTake` and `selfInventoryDrop`, at once in the thread of the event, with the write access of the holder (grant `inventory`). Take: the item is free, at most 1 m away on the ground, and goes into a free hand; putting it on the place lets the holder carry it. Drop: a hand is emptied, the item stays where it is. ActionEquip passes the item and the place (`equip_item`, `equip_inventoryPlace`) as event parameters.

| Test | Checks |
|---|---|
| `take_theRequestedHand_ifItIsFree` | a requested hand is taken if it is free, else nothing |
| `take_withoutAHand_theRightOne_elseTheLeftOne` | without a hand the right one, else the left one, else none; another place counts as none given |
| `drop_theRequestedHand_ifItHoldsSomething_elseRightThenLeft` | the requested hand if it holds something; without one right, then left; empty hands give none |
| `reach_isOneMetreOnTheGround` | 1 m exactly is in reach, 1.001 m is not; the height does not count |
| `theInventoryEvents_areTheSelfInventoryEvents` | take, drop, get are self inventory events; the target event and a move are not |

- **Not checked:** the calculator with a real event, holder and item (it needs the running simulation); switch, set and get (V5).

## Not Covered

- `Expression` as a whole: only indirectly, through `KnowledgeElementLinesVsJsonTest`, the composer, `BranchingProbabilityTest`, the rule test and `SequenceTest`.
