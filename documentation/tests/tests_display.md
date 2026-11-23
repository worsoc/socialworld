# Tests: Display and ACT

Part of the [test overview](TESTS.md): the dashboard and the Action Creation Tool: what shows or nudges the running simulation.

**16 test classes, 202 tests.**

## Overview

| Area | Test class | Tests | Checks | Skipped when |
|---|---|--:|---|---|
| Display | `VisualModelTest` | 21 | the model between the simulation threads and the display, remaining duration counted down between two reports | – |
| Display | `ActionGridTest` | 25 | action families (also by mode), short names, fading, field under the mouse, own colour for fly | – |
| Display | `AttributeMatrixTest` | 23 | colour scale, sorting, the two blocks of 50, change mark | – |
| Display | `ObjectDetailTest` | 25 | detail column, type of the human, direction of the running move, last actions, history of one human, selection by click | – |
| Display | `PopulationHistoryTest` | 11 | history of all humans: families, mean, percentiles | – |
| Display | `AutomaticSelectionTest` | 13 | the detail column follows the largest change | – |
| Display | `PictureSequenceTest` | 9 | figures and timing of the picture sequence in the action grid | – |
| Display | `TypeOverviewTest` | 5 | overview of the types: which human has which type, number of humans per type | – |
| ACT | `ActionRequestTest` | 9 | values and checks of an action request, duration included | – |
| ACT | `ActionSenderTest` | 6 | properties of the action given into the pipeline, invalid requests are not given, no stop for an invalid causer | – |
| ACT | `EventRangeTest` | 14 | say modes, their ranges and the humans within the range | – |
| ACT | `ItemReachTest` | 5 | taking, dropping, switching, setting and getting in the tool, and the blows with a weapon: modes and places, free items within 1 m, a take needs an item, the properties of ActionEquip and ActionAttack | – |
| ACT | `NeedsResetTest` | 3 | the dashboard intervention ends only sleep and eat, sets tiredness and hunger to 2000 | – |
| ACT | `ActionMapScaleTest` | 11 | world position ↔ pixel on the range map, finding a human under the mouse | – |
| ACT | `ActionMapFrameTest` | 9 | scrollable range map: scale fitted once and kept, area only grows, shift of the seen part | – |
| ACT | `ActionColorsTest` | 13 | colour of a human by its running action: family colour from `ActionFamily`, darker for the stronger mode | – |

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
| Family by mode | `typeOfMode_followsTheNumbering`: mode index / 10 is the type index (sleep → bodilyFunction … sayScream → say, ignore and null → none); `everyActionModeHasAFamily_exceptIgnore`; `colorOfMode_isTheColorOfTypeAndMode` (fly keeps `COLOR_FLY`). Since 2026-11-20 `ActionFamily` is public and also gives the colours of the ACT map |
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

### `ItemReachTest`

`src/test/java/org/socialworld/workbench/act/ItemReachTest.java`

Since 2026-11-23 (inventory concept V4) the tool offers `takeItem` and `dropItem` besides the say modes, with an item id and a hand (none given, right, left). Since V5 also switch, set and get, with every place to choose. The button "Items und Inventar" lists the free items within 1 m of the causer and its whole inventory with the contents of its containers (`Animal.getInventoryItem`, only for the tool). The action is an `ActionEquip` with the item as simulation object and the hand as index of `InventoryPlace`.

| Test | Checks |
|---|---|
| `offersTheFiveItemModes_andAllPlacesHandsFirst` | the item modes are take, drop, switch, set and get; the places are none, right, left and then every other place once |
| `itemsInReach_withinOneMetre_sorted` | an item exactly 1 m away and one on the spot are in reach, one 1.001 m away is not; sorted by id |
| `takingNeedsAnItem_droppingDoesNot` | take without item: `INVALID_ITEM`; take with item and drop without item are fine; walk is still not supported |
| `theItemActionProperties_nameTypeEquip_theItemAndTheHand` | type equip, the hand as index (left 1), a valid item value |
| `offersTheWeaponBlows_withAnOptionalTarget` | the 7 weapon blows (V7) are offered and accepted; their properties have type useWeapon and, without a target id, no target |

- **Not checked:** the window itself and the listing of the items (it needs the running simulation).

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

### `ActionMapFrameTest`

`src/test/java/org/socialworld/workbench/act/ActionMapFrameTest.java`

The area of the scrollable range map (2026-11-20): the scale is fitted once to a view of 648 × 448 pixels and then stays, so that a movement is not seen as a zoom.

| Test | Checks |
|---|---|
| `fit_drawsLikeTheFittedScale` | the first fit fills the view and draws like `ActionMapScale` fitted to the same positions |
| `include_keepsTheScaleWhenAHumanLeaves` | a human 400 m away makes the map wider, 10 m keep their pixel length |
| `include_toTheRight_doesNotShiftTheOldContent` | growing to the right or down moves nothing already drawn |
| `include_toTheLeftAndUp_shiftsTheOldContentByTheReturnedPixels` | growing to the left or up moves the old content by exactly the returned pixels (the panel scrolls by them) |
| `include_keepsTheNewPositionInsideTheArea` | the human far away lies inside the grown map |
| `include_neverShrinksTheArea` | the area stays wide when the human comes back |
| `fill_growsEvenlyToABiggerView` | a bigger window extends the area evenly on both sides, the scale stays |
| `fill_doesNothingForASmallerView` | a smaller window changes nothing (scroll bars instead) |
| `fit_copesWithASinglePositionAndWithNone` | one position is centred, no position still gives a scale |

### `ActionColorsTest`

`src/test/java/org/socialworld/workbench/act/ActionColorsTest.java`

The colour of a human on the range map by the mode of its running action (2026-11-20). The hue is the family colour of the dashboard grid, taken from `visualize.ActionFamily` (the one source, `ofMode`/`colorOfMode`): body sienna, movement dodger blue, perception slate gray, item goldenrod, contact dark cyan, fight crimson, speech medium orchid, fly the neon green of the test flight. Within a family the normal mode has the family colour, a stronger one is darker, a weaker one lighter.

| Test | Checks |
|---|---|
| `everyActionHasAColour_noActionHasNone` | every mode except `ignore` has a colour; `ignore` and no action have none (idle dot) |
| `normalModes_haveTheFamilyColourOfTheDashboardGrid` | eat, walk, listen, combine, hand, straight punch and say have exactly the grid colour of their family |
| `everyMode_hasTheHueOfItsDashboardFamily` | every mode but fly is its `ActionFamily` colour shaded by its own share |
| `fly_isTheTestFlightColourOfTheDashboard` | fly is `ActionFamily.COLOR_FLY` as in the grid |
| `sleepAndEat_areBrown` | sleep and eat are brown (red > green > blue) |
| `shade_goesTowardsBlackOrWhite` | share 0 keeps the colour, −1 gives black, +1 white, −0.5 halves the channels |
| `moving_runDarkest_sneakLightest` | run darker than walk darker than sneak; jump and swim lie in between |
| `moving_isBlue_exceptTheTestFlight` | all move modes but fly are blue |
| `talking_screamDarkest_whisperLightest_sameForSayAskAnswer` | scream darker than normal darker than whisper; ask and answer share the colour of say at the same loudness |
| `talking_isOrchid` | the talk modes are orchid (red and blue above green) |
| `fighting_weaponsDarkerThanFists` | club darker than a straight punch, that darker than an upward punch |
| `body_sleepDarkest` | sleep darker than eat darker than drink |
| `keepsOrangeAndBlackFreeForRangeAndCauser` | no action uses the orange of the range or the black of the causer |

---

## Not Covered

- `SimDashboard` as a whole: window, timer, buttons and reading the position and the type of a human need a running program; only the model and the panels are tested. The same holds for the window of the types (`TypeOverviewWindow.show`) and reading the names from `characters.csv` there.
- ACT as a whole: the window, refusing a busy human or an action already planned, inserting the action into the pipeline, ending a running action, checking the search tree (`SearchTreeCheck`) and moving a human by drag need a running simulation; only the request, the properties, the range, the scale and the area of the scrollable map are tested; scrolling the view, the button "Karte einpassen" and the colours by running action with their legend need the window. The direct perform of the first version was checked by hand on 2026-11-10, the pipeline on 2026-11-11 (busy message and the say shown in the dashboard), ending a running action and the search tree check on 2026-11-11 as well (a human dragged far away is found there, and its scream reaches nobody).
