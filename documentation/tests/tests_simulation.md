# Tests: Simulation

Part of the [test overview](TESTS.md): the running simulation core: start, attributes, core, actions, objects.

**17 test classes, 91 tests.**

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
| Core | `ObjectMasterIdRangeTest` | 11 | every type keeps to its band of 10000 IDs; beyond it the simulation stops; the perception jumps from band to band; the highest given ID counts only types with objects | – |
| Actions | `InventoryMouthTest` | 1 | the inventory gives the item in the mouth as property (eating, drinking) | – |
| Actions | `ActionMoveVelocityTest` | 8 | velocity of a move action: start velocity per mode and intensity, then from event to event (uniform, accelerated, braking) | – |
| Objects | `SimObjectsCompletelyImplemented` | 3 | lexeme mappings of the object enums | – |
| Objects | `ClothesCatalogTest` | 5 | the 26 concrete clothes: created without database, one place each, lexeme value, side as property, abilities | – |
| Objects | `ToolsAndWeaponsCatalogTest` | 5 | the 29 tools and 7 weapons: created without database, lexeme value in its level, weapon properties, abilities | – |
| Objects | `FoodAndDrinksCatalogTest` | 5 | the 11 kinds of food and 2 new drinks: created without database, eatable or drinkable, lexeme value, food values, perishable, abilities | – |
| Objects | `NatureCatalogTest` | 5 | the 7 things of nature (parts of plants and animals, solids): created without database, lexeme value in its own base group, abilities | – |
| Objects | `ContainersAndGoodsCatalogTest` | 5 | the 10 containers and 12 valuables, devotional, writing and pastime goods: created without database, lexeme value, capacity, jewellery and bag, abilities | – |

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

`ObjectMaster` gives every type of simulation object a band of 10000 IDs (1000 until 2026-11-23): (index − 1) × 10000 to + 9999, the first object gets the start + 1. So humans 1..9999, animals 10001.., gods 20001.., items 30001..39999, magic 40001..49999. Since 2026-11-17 `checkIdInRange` stops creating and loading with an `IllegalStateException` (also on the log and `System.err`) when an ID lies outside the band of its type. Since 2026-11-23 the perception (`perceiveNextObject`) jumps from band to band over the unused IDs (`nextIdForPerceive`); before, it went through every ID up to the highest, and every empty one cost a pass with `Thread.yield()` in the `PercipienceMaster`.

| Test | Checks |
|---|---|
| `bands_areTenThousandWide_andFollowTheTypeIndex` | humans 0..9999, items 30000..39999, magic from 40000; the highest ID stays below `LIVING_SIM_OBJECTS_MAX_NUMBER`, the size of `TickRandomCache` and `TickObjectCooldown` |
| `idsInsideTheBand_areAccepted` | human 1, items 30001 and 39999 |
| `theTenThousandthItem_wouldLandInTheBandOfMagic_andStopsTheSimulation` | item 40000 is refused; the message names the ID, the type, its band 30001..39999 and the band of magic it would land in |
| `theStartOfTheBand_isNeverGivenOut` | human 0 is refused; magic 50000 is refused without naming a band (no type above magic) |
| `perception_goesOnInsideTheBand` | the next ID inside an occupied band (0 → 1, 57 → 58, 30001 → 30002) |
| `perception_jumpsOverEmptyBands_toTheNextBandWithObjects` | after human 100 comes item 30001 when animals and gods have no objects |
| `perception_afterTheLastBandWithObjects_beginsAgain` | after the last item, and from an ID behind all bands, it begins with 1 |
| `perception_oneRound_visitsExactlyTheGivenIds` | one round with 100 humans and 776 items visits 876 IDs (3777 before) |
| `perception_withoutAnyObject_staysAtZero` | without any object the next ID is 0 |
| `perception_onlyMagic_wrapsInsideItsBand` | with only magic it goes 40001 .. 40003 and back to 40001 |
| `maxGivenObjectId_countsOnlyTypesWithObjects` | empty bands do not count with their start (the old `getMaxObjectId` did, and compared magic but took the item value); magic counts when it is highest; no object gives 0 |

- **Assumes:** only the static calculations are tested (`checkIdInRange`, `nextIdForPerceive`, `maxGivenObjectId`), without creating objects; the running simulation creates 776 items (7 clothes for each of the 100 humans, 75 loose apples, one apple shared by all mouths). Items are also limited by their 799 start positions (`swgauss_position`, which point to only 200 places in `swpool_position`, each 4 times); since 2026-11-17 `CreateSimulationObjects.initState` stops with an `IllegalStateException` when they are used up instead of drawing for ever (not tested: the drawing is random and inside the creator).
- **Not checked:** `perceiveNextObject` itself in the running `PercipienceMaster`.
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

The move action keeps its velocity and gives every event a new `Move` with the current value (movement concept G2, step M5): v = v + a × Δt. The start velocity is the base speed of the mode (`ActionMode.getBaseSpeed`: sneak 500, walk 1400, run 4000, swim 700, fly 3000 mm/s) times the tempo factor intensity / 100, limited to 0.5..1.5 (numbers V1–V3, 2026-11-20).

| Test | Checks |
|---|---|
| `uniformMove_keepsItsVelocity` | without acceleration a walk at intensity 100 stays 1400 mm/s over 5 events |
| `acceleratedMove_growsByAccelerationTimesInterval` | 1400 with a = 1400 per second becomes 2800, then 4200 |
| `braking_isANegativeAcceleration` | 1000 with a = −250 over 2 s becomes 500 |
| `startVelocity_normalIntensity_isTheBaseSpeedOfTheMode` | at intensity 100 each move mode starts with its base speed |
| `startVelocity_scalesWithIntensity` | walk at 120 gives 1680, run at 80 gives 3200 |
| `startVelocity_tempoFactorIsLimited` | the factor stays in 0.5..1.5: walk at 23 gives 700, at 173 gives 2100, run at 0 gives 2000 |
| `startVelocity_noIntensity_isNormalTempo` | NaN as intensity counts as normal tempo (1400 for walk) |
| `startVelocity_nonMoveModes_isZero` | jump and non-move modes such as eat have speed 0 |

- **Not checked:** `ActionMove.perform` itself (start velocity at the first event, a new `Move` per event, the event carrying `move_velocity`), it needs a running simulation.

---

## Objects

### `SimObjectsCompletelyImplemented`

`src/test/java/org/socialworld/objects/SimObjectsCompletelyImplemented.java`

| Test | Checks |
|---|---|
| `existsMappingToLexemIDHigherPart` | every `EnumBaseSimObj` has an entry in `Mapping_Base2LexemIDHigherPart` |
| `existsMappingToLexemIDLowerPart` | every element of `EnumMammal`, `EnumBird`, `EnumFood`, `EnumLiquid`, `EnumSolid`, `EnumAnimalPart`, `EnumPlantPart` (since 2026-11-23) and `EnumHumanCrafted` has an entry in its lower-part mapping |
| `generatedLexemID_isUnique` | no two subclasses of `SimulationObject` get the same lexeme ID |

- **Assumes:** the lexeme ID is `OFFSET_LEXEMID_NOUN_SIMOBJ + higher × RANGE_FOR_LOWER_VALUE + lower`. Classes without a mapping, and classes where both parts are 0, are left out of the uniqueness check.
- **Note:** `generatedLexemID_isUnique` scans the classpath with ClassGraph. On failure the details go to the console, and the assertion only says `false`.
- **Commented out:** `everySimObjClass_HasEnumElem` would check that every class of a simulation object has an enum entry.

### `ClothesCatalogTest`

`src/test/java/org/socialworld/objects/concrete/clothes/ClothesCatalogTest.java`

The concrete clothes of the item hierarchy (2026-11-23): 7 caps, 4 shirts, 2 trousers, 3 dresses, 5 overgarments, 3 shoes, 1 sock, 1 glove.

| Test | Checks |
|---|---|
| `all26_areCreatedWithoutDatabase` | every class has a constructor without arguments and is `IWearable` |
| `eachOne_saysExactlyOnePlace` | of cap, shirt, trousers, dress, overgarment, shoe, sock, glove, jewellery and bag exactly one is true |
| `lexemLowerValue_isUniqueAndJustAboveItsKind` | `GetLexemIDLowerPartFromMapping` gives kind + 1 .. 15 (e.g. cap 272, straw hat 273), no value twice |
| `side_isAProperty_leftUntilSet` | shoe, sock and glove are left until `setLeftSide(false)`, then right |
| `abilities_asInTheCatalogue` | helmet and chain mail `IProtective`, fur cloak and chain mail `IValuable`, habit `ISacred`, linen shirt none of them |

- **Assumes:** the catalogue of the item hierarchy concept; the ability values are constants in the classes.
- **Not checked:** creating clothes in the running simulation (states, position), it needs the database.

### `ToolsAndWeaponsCatalogTest`

`src/test/java/org/socialworld/objects/concrete/tools/ToolsAndWeaponsCatalogTest.java`

The 29 tools and 7 weapons of the item hierarchy (2026-11-23). Tools have purpose levels of + 16 below `TOOL` 512 (field 528, craft 544, textile 560, herding 576, household 592, light 608, travel 624), only as numbers; each tool is its level + 1, + 2, .... Each weapon is `WEAPON` 1024 + 16, + 32, ....

| Test | Checks |
|---|---|
| `all36_areCreatedWithoutDatabase` | every class has a constructor without arguments; tools are `Tool`, weapons are `Weapon` |
| `lexemLowerValue_isUniqueAndInItsLevel` | the lower lexeme value lies in its level, no value twice |
| `everyWeaponIsAnIWeapon_andToolsCanBeOneToo` | every `Weapon` is `IWeapon`; the knife is `IWeapon` without being a `Weapon`; the axe also cuts and can be thrown; the needle is no weapon |
| `weaponProperties_existForAllFourBlows_andCuttersHaveAnEdge` | every `IWeapon` gives stab, club, stroke and backhand properties with mass > 0, hardness and sharpness 0..100; cutting ones have a sharp edge for the stroke |
| `otherAbilities_asInTheCatalogue` | shovel and pickaxe dig, lantern and candle give light (the candle less far), the horn can be played, the shield protects, the long sword is worth more than the short one |

- **Assumes:** the catalogue of the item hierarchy concept; the ability values are constants in the classes.
- **Not checked:** using a weapon in `ActionAttack` (needs the inventory and the running simulation).

### `ContainersAndGoodsCatalogTest`

`src/test/java/org/socialworld/objects/concrete/containers/ContainersAndGoodsCatalogTest.java`

The 10 containers and the 12 other human crafted goods of the item hierarchy (2026-11-23): 2 valuables, 3 devotional, 3 writing, 4 pastime. Each class is its group + 16, + 32, ... (container 2048, valuable 4096, devotional 8192, writing 16384, pastime 32768).

| Test | Checks |
|---|---|
| `all22_areCreatedWithoutDatabase` | every class has a constructor without arguments |
| `lexemLowerValue_isUniqueAndInItsGroup` | the lower lexeme value is its group + 16 .. 240 on a level of 16, no value twice |
| `everyContainer_holdsSomething` | every `Container` has a capacity > 0; jug and waterskin hold liquids, sack and purse do not |
| `jewelleryAndBag_areWornWithoutAClothesPlace` | ring, rosary and amulet are jewellery and no clothes place, the satchel is a bag, the relic is not worn |
| `abilities_asInTheCatalogue` | coins valuable and countable, relic holier than the amulet, book readable and longer than the scroll, quill writes, lute and flute can be played, dice and jug can be thrown |

- **Assumes:** the catalogue of the item hierarchy concept; the ability values are constants in the classes.
- **Not checked:** what a container holds, it comes with the inventory.

### `FoodAndDrinksCatalogTest`

`src/test/java/org/socialworld/objects/concrete/eatable/FoodAndDrinksCatalogTest.java`

The 11 kinds of food and the 2 new drinks of the item hierarchy (2026-11-23): berries (fruit), turnip, onion, root (veggie), sausage, ham (meat), dried fish (fish), bread, cake (bakery), cheese (dairy), honey (sweets); beer and wine (liquid, in `eatable/liquids/`). Each class is its group + 16, + 32, ... (fruit 256 after apple and banana, veggie 512, meat 1024, fish 2048, bakery 4096, dairy 8192, sweets 16384; liquid 256 after water).

| Test | Checks |
|---|---|
| `all13_areCreatedWithoutDatabase_foodIsEatable_drinksAreDrinkable` | every class has a constructor without arguments; food is `Food` and `IEatable`, drinks are `IDrinkable` and no `Food` |
| `lexemLowerValue_isUniqueAndInItsGroup` | the lower lexeme value is its group + 16 .. 240 on a level of 16; unique together with the higher part (berries and wine both have 304, in food 45 and liquid 41) |
| `foodValues_areInRange` | consistence and firmness of every food lie in 0..100 |
| `perishable_allFoodButHoney` | every food except honey is `IPerishable` with a shelf life > 0; bread keeps shorter than dried fish |
| `otherAbilities_asInTheCatalogue` | berries countable, turnip throwable, honey healing and valuable, wine valuable, beer perishable |

- **Assumes:** the catalogue of the item hierarchy concept; the ability values are constants in the classes.
- **Not checked:** nutrient and taste set (they come from `StateEatable`, i.e. from the database; the drinks give `null` as water does).

### `NatureCatalogTest`

`src/test/java/org/socialworld/objects/concrete/plantparts/NatureCatalogTest.java`

The 7 things of nature of the item hierarchy (2026-11-23): branch, herb, flower (`PlantPart`, higher lexeme value 44), hide (`AnimalPart`, 43), stone, charcoal, ore (`Solid`, 42). Each is + 16, + 32, ... in its group (as the liquids: 272, 288, 304).

| Test | Checks |
|---|---|
| `all7_areCreatedWithoutDatabase_inTheirGroup` | every class has a constructor without arguments and belongs to its group |
| `lexemId_higherPartIsTheGroup_lowerPartUniqueInIt` | the higher part is the group's value, the lower part 272..496 on a level of 16, unique together with the higher part |
| `branchAndStone_canBeWeaponsAndThrown_onlyTheBranchFloats` | branch and stone are `IWeapon` (blunt: sharpness 0) and `IThrowable`; the branch floats and burns, the stone does not float |
| `herb_isEatableWithoutBeingFood` | the herb is `IEatable` but no `Food`, it heals and wilts |
| `otherAbilities_asInTheCatalogue` | flower wilts, hide valuable and burns, charcoal burns and is countable, ore valuable and countable |

- **Assumes:** the catalogue of the item hierarchy concept; the ability values are constants in the classes.
- **Not checked:** nutrient and taste set of the herb (`null`, it has no eatable state).

---

## Not Covered

- Simulation run, events, actions, conversation: no tests so far.
