# Tests at a Glance

What the automated tests check, what they assume, and what they need to run.

| | |
|---|---|
| **Framework** | JUnit 5 with AssertJ |
| **Test classes** | 97 |
| **Test methods** | 774 active, 1 commented out |
| **Database** | no test writes to MariaDB; a few tests read from it and skip themselves without it or with an empty table |
| **Runs in CI** | yes, in a fresh clone without `application.properties` |
| **Last updated** | 2026-11-24 |

> Keep these files up to date: every new or changed test changes its part in the same step, and the counts here.

---

## Parts

| Part | Areas | Test classes | Tests |
|---|---|--:|--:|
| [Simulation](tests_simulation.md) | Start, Attributes, Core, Actions, Objects | 29 | 141 |
| [Calculation](tests_calculation.md) | Calculation | 19 | 128 |
| [Data and DCT](tests_data.md) | Data, DCT | 21 | 166 |
| [Perception (PCT)](tests_perception.md) | PCT | 8 | 95 |
| [Map and MCT](tests_map.md) | Map, MCT | 4 | 42 |
| [Display and ACT](tests_display.md) | Display, ACT | 16 | 202 |
| **Total** | | **97** | **774** |

Each part has its own overview table, one section per test class and its own list of what is not covered.

---

## Conditions for All Tests

- **No database.** The PCT tests work on `InMemoryPerceptionDescriptionStore`, never on `swpool_epd` in MariaDB. `EventInfluenceRulesFromDatabaseTest`, `TablePoolAttributeDataReaderTest` and one test each in `AttributeCharacterOverviewTest` and `AttributeMatrixTargetsTest` read from MariaDB, they only read; `EatUpTest.anObjectThatWasNeverInTheSearch_canBeRemoved_withoutHarm` and `LiquidStatesTest` reach it through the objects they create (`ObjectMaster`, `CacheTableStateAppearance`). They call `DatabaseAssumptions` first: without a database, with a missing table or with an empty table they are skipped. Errors caused by the data in a filled table let them fail. No test writes to it; the DCT writers are tested with stores in memory.
- **Test data outside git.** `src/test/swpool_epd.sql`, `src/test/swpool_parseinputs.sql` and `src/test/onlyLs/` are ignored by `.gitignore`. Tests that need `swpool_epd.sql` skip themselves with `assumeTrue` when the file is missing, which is the case in CI. No test reads `swpool_parseinputs.sql` or `onlyLs/`.
- **Screen.** Only `PerceptionCreationToolTest.dialog_canBeBuilt` needs one; without a screen (headless) it is skipped.
- **Map conventions** (directions, tile sizes): see [Map and MCT](tests_map.md#conventions).
