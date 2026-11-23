# Tests: Data and DCT

Part of the [test overview](TESTS.md): pools and tables, and the Data Creation Tool that fills them.

**21 test classes, 166 tests.**

## Overview

| Area | Test class | Tests | Checks | Skipped when |
|---|---|--:|---|---|
| Data | `Action2PerformerDescriptionPoolTest` | 7 | intensity × power, expression parser, the moves take the intensity of the action, move velocity without double acceleration, a description for every blow | – |
| Data | `FunctionPoolTest` | 5 | building a matrix from flat arrays, table functions under func_id 0..10, func_ids for functions created at runtime | – |
| Data | `GaussPoolAAACMMappingTest` | 4 | mapping of the two drawn indices | – |
| Data | `StartEquipmentCatalogTest` | 7 | equipment.csv: all 100 characters, kits as pieces with pairs, more clothes first, barefoot, container and content apart, class names, totals | – |
| Data | `StartEquipmentDistributionTest` | 1 | the whole start equipment distributed as at the start of the simulation: nothing at the feet, 3 kit pieces left out | – |
| Data | `CreateAnimalInfluenceTypeTest` | 4 | the influence type is the character of the attribute array, the same for every event type | – |
| Data | `CreateAnimalState2ActionTypeTest` | 3 | the state-to-action type is the character of the attribute array, equal to the influence type | – |
| Data | `EventReactionFamilyTest` | 4 | 116 reaction types, drawn per event family, other event types keep 1 | – |
| Data | `DatabaseAssumptionsTest` | 1 | a test is skipped without a database | – |
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
| `everyMoveMode_givesTheEventTheIntensityOfTheAction` | walk, run, sneak, jump, swim and fly have 4 functions, the fourth gives `event_intensity` = the action intensity as it is (120 → 120, not multiplied by the power; numbers N4 2026-11-20, replaces the constant 100 of 2026-11-19; the position follows `move_velocity`) |
| `everyBlow_hasADescription_withTheGeneralEventNames` | all 6 punches have 5 functions, all 7 weapon modes 6; the first gives `event_direction` (the view), the second the actor's intensity (P3, B1, 2026-11-20; before only punchRightFistStraight and weaponClub had one, with punch_/useWeapon_ names). The event intensity needs the power of a real actor and is not evaluated here |
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

### `StartEquipmentCatalogTest`

`src/test/java/org/socialworld/datasource/createObjects/StartEquipmentCatalogTest.java`

The start equipment of the 100 characters (inventory concept V3, 2026-11-23) is read from `src/main/resources/org/socialworld/tools/dct/equipment.csv`: kit lines (`kit;<name>;<clothes>`) and one line per character (`<no>;<kit>;<more clothes>;<items>;<flags>`). Shoes, socks and gloves stand for a pair; `Container(Liquid)` gives the container and its content apart; the flag `barefoot` leaves out shoes and socks; the more clothes come before the kit.

| Test | Checks |
|---|---|
| `theFile_hasAll100Characters_andNoProblems` | every character 1..100 has an entry, no unknown kit or class |
| `theFarmer_getsKitK1AsPieces_shoesAndSocksAsPairs` | character 1: shirt, trousers, shoe left and right, sock left and right, straw hat; pitchfork and bread |
| `moreClothes_comeFirst` | character 48: the fur cloak before the cloak of the kit |
| `barefoot_leavesOutShoesAndSocks` | character 33 has no shoes and socks, but dress, apron and headscarf |
| `aContainerWithContent_isTheContainer_andTheContentApart` | the barmaid's jug and its beer; the shepherd's waterskin with water |
| `classNames_areFoundInTheirPackages_unknownOnesAreProblems` | Helmet and Stone are found in their packages; an unknown class and an unknown kit are listed as problems |
| `totals_asCountedInTheConcept` | 697 pieces of clothing (before replacing), 231 items with the contents |

- **Needs:** the file on the class path (it is in `src/main/resources`), no database.

### `StartEquipmentDistributionTest`

`src/test/java/org/socialworld/datasource/createObjects/StartEquipmentDistributionTest.java`

Distributes the whole start equipment as `Simulation.equip` does it (`StartEquipment.chooseWornPlace` for the clothes, `choosePlace` with the first food into the mouth for the items, a bread into an empty mouth), on objects without state.

| Test | Checks |
|---|---|
| `all100_areEquipped_nothingFallsToTheFeet` | every piece gets a place (915 on 2026-11-23), nothing is put down at the feet, exactly 3 kit pieces are left out (the fur cloak, the hood and the helmet take their place), every human wears something on the upper body; prints the counts and the breads (69) |

- **Not checked:** the creating of the objects and the carrying in the running simulation (they need the database).

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

## Not Covered

- Loading from MariaDB (`datasource`): no tests except the reading tests named under the conditions, so that CI runs without a database. In particular the DCT writers against the real tables.
- The lookup of the mapping in `CreateAnimal` and setting the influence types, the state-to-action type and the reaction types there (A7, E4): one line each, they would need the whole creation of objects; only `influenceTypesOfCharacter`, `state2ActionTypeOfCharacter` and `EventReactionFamily.drawReactionTypes` are tested.
- State2Action: loading `swpool_s2a` in `State2ActionDescriptionPool` and the action that a rule gives in a run; only JSON, chain, generator and type are tested.
- Event reactions: loading `swpool_erd` in `EventReactionDescriptionPool` and the reaction in a run; the target events of look (X1) and of attacks without a weapon (X3) need a running simulation.
- Action2Performer: still on lines, only the arithmetic of the intensity is tested.
