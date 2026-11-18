# Event Influence Rules (EID): Meaningful Rules Instead of Index Arithmetic

Status 2026-11-18, branch `dev/claude0002`. Built and committed: C1, A1 to A6 and C6 (`09f7beb7`), A8 to A11 (`4dce61a6`), A13 to A15 with the characters 11 to 20 (`2571823f`), the characters 21 to 100 (A16 to A23, `f0c52dca`) and A7 (section 15.11). All 100 characters have their rules and an ideal; the rules are in the database. Things found outside EID along the way are in their own concepts (section 12.6). Section 13 balances the rules with the attribute matrix and adds the need of sleep and food per character. Section 14 adds an ideal per character for tiredness (rested and ready for bed) and hunger (full and hungry); the four tiers sit at this ideal instead of at the band. Section 15 covers the blocks 21 to 100 and A7. State2Action (A12) follows as a project of its own.

The concept is based on reading the code. Which roles (`self`, `target`, `candidates`, `percipient`) reach whom has been checked in the code since 2026-11-11 (section 5.1).

## 1. Starting Point

The button "MASS GENERATE 19.300 RULES" in `tools/dct/CreationTool_EventInfluenceDescription` (`runMassGeneration()`) writes one rule into the table through `TablePoolEID` for every combination of event type (193 without `nothing`) and influence type (0..99). Before that it empties the table (`table.clear()`), rules made by hand included.

All rules were built the same way:

- condition always `mood >= 5000`
- action always `MX+N` with `"basis;m;n"`
- target Y = `(et + infl) % 9`, basis X = `(target + 5) % 9`. So there were only 9 fixed pairs of target and basis, e.g. `mood` always from `curiosity`.
- M = 0.50 to 1.99, N = 0 to 1990, both computed from the indices and always positive

The rules were filler without content. Which event changed which attribute followed from index arithmetic only.

## 2. What the Evaluation Could Do Before the Rework

State before the rework. What has changed since (condition with two limits, function slots, sequence of entries) is in section 12.5.

The JSON rule is translated in `calculation/expressions/ChangeAttribute.initFromJson`.

| Point | Finding |
|---|---|
| MX+N | `Y := clamp(M·X + N, 0, 9999)`. Y is **overwritten**. If X is another attribute, the old value of Y is lost |
| X = Y | relative change of Y, see section 4 |
| Else | a third term is evaluated as else branch. The generator does not use it so far |
| several attributes | `attributeChanges` is a list, the entries run one after the other |
| condition | only one attribute against a constant |
| functions | only `MX+N` on the JSON route |
| event properties | e.g. the intensity does not reach MX+N |
| function slots | every MX+N took a slot in `functions` (10000 to 99999) |

**Series of single changes:** For different attributes one after the other it works, e.g. first tiredness, then hunger. That held only for certain numbers of entries: with 4 to 6, 10 to 12, … entries `Sequence` crashed or skipped entries (C6, fixed 2026-11-17). For one attribute from two different sources it does not work: every MX+N overwrites Y, so the last entry wins.

**Influence type before A7:** `GaussPoolInfluenceType` shuffled the numbers 0..99 for every event type. A human got an independent number for every event type. Type 37 for sleep had nothing to do with type 37 for a punch. Since A7 (15.11) a human has the same influence type for all event types.

## 3. Decided

| Topic | Decision |
|---|---|
| Overview | The table of basic effects (section 5) is an overview for us, not a DB table |
| Influence type | follows the 100 existing characters (`AttributeCharacterCatalog`, `characters.csv`): influence type = character number − 1 |
| Priority | The meaning of the event and its effect on the human come first. The band of the character only gives the rough frame (section 6.2) |
| Form | absolute changes for now (MX+N with M = 1), because they are transparent. Other forms later, with functions other than MX+N |
| Per character | For every character and every situation there is a sentence in natural language and steps derived from it, in four tiers at the band (sections 6.2 and 6.3) |
| Situations | The 91 relevant event types are bundled into 25 situations, variants get factors (section 7) |
| Source | a CSV file read by the mass generation button (section 8), plus `event_situations.csv` for situations and factors |
| Format | JSON. We do not go back to the old line format |
| Storage | The rules go back into the EID table, through the mass generation button and `TablePoolEID`. `table.clear()` stays (B2) |
| Neutral rule | Every pair of event type and influence type gets a rule. Without effect it is a rule with an empty `attributeChanges` list (5.4, B1) |
| Scope | first step only with MX+N. The rules are to be better than before, not perfect yet |
| Later | The intensity gets a meaning some day. Functions other than MX+N will follow |

## 4. Computing with MX+N: Fixed Step and Approach

Both forms use X = Y, the target attribute itself as basis.

**Fixed step:** M = 1, N = step. With `tiredness;1.00;-300.00` tiredness falls by 300 (3 displayed points).

**Approach to a target Z:** Z is not an attribute but a fixed target value set per rule. With share a:

- M = 1 − a
- N = a·Z

Example: after sleep tiredness is to approach Z = 2000, a = 10 %. Then M = 0.9, N = 200, the formula text is `tiredness;0.90;200.00`.

Y stays at Y = M·Y + N, that is at Y = N / (1 − M) = 200 / 0.1 = 2000 = Z. Each application shrinks the distance to Z by 10 %:

| tiredness before | after |
|---|---|
| 9000 | 0.9·9000 + 200 = 8300 |
| 8300 | 7670 |
| 2000 | 2000 (stays) |
| 500 | 650 (rises towards 2000) |

**Note:** The approach also pulls values below the target up, as the last row shows. If a rule is only to lower, a fixed step with negative N fits better. Alternatively a condition is put in front, e.g. `tiredness > 2000`.

**Advantage of the approach:** No value sticks to 0 or 9999 for good.

**Decided 2026-11-11:** Only the fixed step for now. The small steps outside the band protect against sticking (section 6.3). The approach stays here as an option for later.

## 5. Overview of the Basic Effects (Draft)

### 5.1 Who Gets Which Event (checked in the code 2026-11-11)

Attributes are recomputed only for animals and humans (`StateAnimal.calculateEventInfluence`). Items do get events, but no change of attributes.

| Role | who gets the event | source |
|---|---|---|
| `self…` | the actor himself | `EventMaster.determineInfluenceToCauser` |
| `target…` | what `getTargets()` of the action returns, mostly an **item** | `EventToTarget.getTargetObjects` |
| `candidates…` | everybody within the effect radius and angle of the event, **including the one hit** | `EventMaster.determineCandidates` |
| `percipient…` | everybody within reach | `EventMaster.determinePossiblePercipients` |

What `target` is for the single families:

| Family | target | for attributes |
|---|---|---|
| Eat, Drink | the item (`ActionBodilyFunction.getItem`) | of no meaning |
| Inventory | the item (`Equip.getTargets`) | of no meaning |
| HandleItem, Touch | items 1 and 2 (`Handle.getTargets`) | of no meaning |
| Weapon | the attacked object (ActionAttack.target) | **counts** |
| Punch | probably empty without a weapon | of no meaning |
| Examine | empty (`Examine.getTargets`) | of no meaning |
| Ask, Answer | the human addressed (`Ask.getTargets`) | **counts** |

**Effective check:** For Touch (152, 153), Pull and Push (173, 174) and Ask and Answer (200 to 205) candidates are searched as well (`EventType.isRelevantForEffectiveCheck`). The bystanders then get the same event with the **`target` type**.

**Actually created:**

| Group | Number | reaches humans and animals |
|---|---|---|
| `self…` | 55 | yes |
| `targetAsk…`, `targetAnswer…` | 6 | yes: the one addressed and the bystanders |
| `candidatesMove…` | 6 | yes |
| `candidatesWeapon…` | 7 | yes: the victim and whoever else stands in the angle |
| `candidatesPunch…` | 6 | yes: as Weapon |
| `candidatesSay…` | 3 | yes |
| `percipientExistsDistance5000`, `…1000` | 2 | yes (`SimulationObject.letBePerceived`) |
| all other `target…` | 33 | no, only items |
| all other `candidates…` (23) and `percipient…` (52) | 75 | no, only named in `EventTypeGeneral`, created by no action |

Of 193 types **85** counted. Since 2026-11-16 `ActionMove` also creates the 6 percipientMove types (section 7.2, A5). So **91** count, the other **102** get a neutral rule (5.4).

**Frequency:** The Exists events have the priority `AMBIENT`. `AttributeCalculator` let at most one of them per object per second through, so up to 3600 per hour. A fixed step of 100 would push an attribute from the middle to the edge in about 100 seconds. Exists may therefore act only very weakly or not at all for now (section 7, situation 24).

### 5.2 Effects on Humans and Animals

Since 2026-11-11 only an **orientation for the typical direction**. Binding is the table per character (sections 6.2 and 8).

Signs: ↑↑ strong, ↑ medium, (↑) slight, ± depends on the character. Attributes without an entry stay unchanged.

**self (55 types)**

| Family | Variants | Effect |
|---|---|---|
| **Sleep** | – | tiredness ↓↓, mood (↑), hunger (↑) |
| **Drink / Eat** | Eat stronger than Drink | hunger ↓↓, mood (↑), Eat: tiredness (↑) |
| **Piss / Shit** | – | mood (↑) |
| **Move** | Walk (↑), Sneak ↑, Jump ↑, Fly ↑, Run ↑↑, Swim ↑↑ | tiredness by variant, Run and Swim: hunger (↑) |
| **Examine** | Look, Smell, Taste, Touch | curiosity ↓, Taste: hunger (↑) |
| **Touch** | Hand, Foot | curiosity (↓) |
| **Inventory** | Take and Get (+), Drop (−), Switch and Set (neutral) | materialism (↑), mood (↑) |
| **HandleItem** | Use…, Add…, Pull and Push (heavier) | tiredness (↑), Pull and Push: tiredness ↑ |
| **Weapon** | Stab, Stroke, Backhand, Club (strongest) | tiredness ↑, courage (↑) |
| **Punch** | 6 variants, weaker than Weapon | tiredness (↑), courage (↑) |
| **Listen / Understand** | Statement, Question, Instruction, Understand | Statement: curiosity (↓), Question: curiosity (↑), Instruction: mood (↓), Understand: curiosity ↓ and mood (↑) |
| **Ask** | Normal, Scream (×1.5), Whisper (×0.5) | curiosity (↓) |
| **Answer** | as Ask | – |
| **Say** | as Ask | mood (↑), Scream: tiredness (↑) |

**target for Ask and Answer (6 types), for the one addressed and the bystanders**

| Family | Effect |
|---|---|
| **Ask** | Normal: mood (↑), Scream: mood ↓ and courage (↓), Whisper: curiosity ↑ |
| **Answer** | curiosity ↓ |

**candidates (22 types)**

| Family | Variants | Effect |
|---|---|---|
| **Move** | Walk, Sneak, Jump, Swim, Fly, Run | Run and Sneak: curiosity (↑), Sneak: courage ±, otherwise – |
| **Weapon** | as self, Club strongest | mood ↓↓, power ↓, courage ± |
| **Punch** | as self, weaker than Weapon | mood ↓, power (↓), courage ± |
| **Say** | Normal, Scream, Whisper | Normal: mood (↑), Scream: mood ↓ and courage (↓), Whisper: curiosity ↑ |

**percipient (2 types)**

| Family | Variants | Effect |
|---|---|---|
| **Exists** | Distance5000, Distance1000 (closer, a little stronger) | courage ±, curiosity (↑), step 0 for now (section 7, situation 24) |

### 5.3 Open Points of the Overview

- Because the bystanders get the same `target` type for Ask and Answer, a question acts on them just as on the one addressed. For the first step we accept that, or we keep the effect of Ask and Answer so small that it does not matter.
- With Weapon and Punch the effect hits everybody in the angle and radius, not only the intended victim.
- Whoever only watches a fight gets no event today, because `percipientWeapon…` and `percipientPunch…` are not created.

### 5.4 Neutral Rule for the Other 102 Types

Decided and built 2026-11-16 (A3, B1):

- Every pair of event type and influence type gets a rule, also the 102 types without effect and the situations without an attribute line.
- The neutral rule has an empty `attributeChanges` list. `ChangeAttributes` returns the attribute array unchanged for it (before: `Nothing`). It takes no function slot.
- `AttributeCalculator` counts "without rule" apart from "rule error". Because the pools return a placeholder instead of `null` for a missing rule, a rule without a function also counts as "without rule".
- With all 100 characters 12,784 of the 19,300 rules are neutral (in the database, 2026-11-18).


## 6. Influence Type and Characters

The characters are in `src/main/resources/org/socialworld/tools/dct/characters.csv`: number, name, sketch and one level per attribute. They are read through `AttributeCharacterCatalog`.

### 6.1 Assignment

**Influence type = character number − 1**, the same for all event types.

- Both sides have 100 entries and are ordered by frequency. No. 1 ("farmer") is the most ordinary character, from no. 86 on come the rare marginal figures.
- `AttributeArrayGenerator` already gives the gauss index "character number − 1": one array for no. 1, two (positive and negative) for all others. Influence type = |gauss index| fits.
- For this the assignment had to be changed. Before, `CreateAnimal` (attribute array) and `CreateSimulationObjects.initState` (influence type) drew separately. Built 2026-11-18 as A7 (section 15.11).

### 6.2 Rules per Character and Situation

Decided 2026-11-11:

- For **every character** it is laid down for **every situation** (section 7) how it acts on him.
- **First comes a sentence in natural language** describing the effect. From it we derive which attributes change and how.
- The changes are **absolute**: MX+N with X = Y, M = 1 and N = step.
- The steps **differ per character**, and within a character they are graded once more by the current value (6.3).
- The meaning of the event for this human comes first. The band of the character is only the rough frame.
- That gives a long table. In return everything is down in black and white.

### 6.3 Four Tiers at the Band

The band of a character for an attribute is [L, H] (`AttributeLevel`). The middle is M = (L + H) / 2. For every attribute affected the table has four steps:

| Tier | Range | typical |
|---|---|---|
| below the band | Y < L | very small step |
| lower half | L ≤ Y < M | graded |
| upper half | M ≤ Y < H | graded |
| above the band | Y ≥ H | very small step |

The bands:

| Level | `---` | `--` | `-` | `o` | `+` | `++` | `+++` | `~` |
|---|---|---|---|---|---|---|---|---|
| Band | 0–1000 | 800–2800 | 2500–4200 | 3800–6200 | 5800–7500 | 7200–9200 | 9000–9999 | 2000–8000 |

The very small steps outside the band do the braking. The buffer of 1500 from the previous draft is dropped.

For tiredness and hunger the tiers sit at the ideal of the character instead of the band since 2026-11-17 (section 14).

**Only one tier applies.** Every tier becomes an entry of its own in `attributeChanges`, with a condition for the lower and the upper limit. Because we know in which direction an attribute changes, we fix the order so that only one entry applies:

- **Y falls:** The entries run from the lowest tier to the highest. A value lowered in one tier falls at most into a tier already checked.
- **Y rises:** The entries run from the highest tier to the lowest, for the same reason.
- Therefore all four steps of a line have **the same sign** (or 0).

Prerequisite: a condition with a lower and an upper limit (section 9).

**Refinement (confirmed 2026-11-15):** The step is very small only on the side where the event leads **away** from the band. On the side where it leads **back** into the band it acts normally. Example sleep: below the band of tiredness there is a tiny step, above the band (overtired) the full one.

### 6.4 Example: 69 Wrestler, "Being Punched"

Levels of the wrestler: mood `+`, courage `+`, curiosity `-`, power `++`.

> The wrestler is used to fighting. A punch leaves him calm, if anything it spurs him on.

| Attribute | Band | < band | lower half | upper half | > band | meant |
|---|---|---|---|---|---|---|
| mood | 5800–7500 | 0 | 0 | −50 | −50 | stays almost the same |
| courage | 5800–7500 | +150 | +500 | +300 | +30 | fighting spirit |
| curiosity | 2500–4200 | +20 | +200 | +100 | +20 | alert, wants to know who hits |

morals does not change, because a punch makes him neither better nor more evil. power does not change either, because motivation brings no bodily strength.

The example dates from before "cause and effect" (section 7) and before the steps were tuned. In the CSV (block 61 to 70, section 15.6) the wrestler keeps mood as here, but courage gets the steps of the other strong characters (+30/+30/+15/+3) and the curiosity line is dropped.

### 6.5 Meaning of the Attributes

For the sentences and values:

| Attribute | Meaning |
|---|---|
| morals | good against evil (decided 2026-11-16) |
| courage | courage, also fighting spirit |
| curiosity | curiosity. Together with courage it makes up motivation |
| power | bodily strength. It does not rise through motivation |

The meaning lives only in the sentences and values of the table, not in the code. If it changes, only the table is adapted.

**Ranking of the attributes (confirmed 2026-11-15):** "basic" means less mobile, not constant.

| Rank | Attribute | Kind | changes through |
|---|---|---|---|
| 1 | hunger | state of the body, very mobile | eating, drinking, strain, time |
| 2 | tiredness | state of the body, very mobile | sleeping, strain, time |
| 3 | mood | state of mind, mobile | experiences that happen to oneself or that one watches: pain, praise, being shouted at, violence against others |
| 4 | power | constitution of the body, sluggish | injury lowers it, rest slowly restores it |
| 5 | courage | constitution, rather sluggish | threat, attack, coming through a danger. Swings briefly, but returns to the nature |
| 6 | curiosity | trait | practically not by single events. It shapes how someone reacts to something new |
| 7 | materialism | attitude | practically not by single events. It shapes how much possessions please or hurt |
| 8 | morals | attitude, good against evil | practically not by single events, at most later by very grave own deeds. It shapes how someone experiences wrong |
| 9 | spirituality | attitude, most basic | not at all by our events |

Three groups: **state** (1 to 3) changes directly by events. **Constitution** (4 and 5) changes only by strong events. **Nature and attitude** (6 to 9) do not change by single events and act as disposition (section 7).

### 6.6 Superseded or Rejected

- **Fixed step, the same for all characters** (draft of the afternoon of 2026-11-11): superseded. The steps now differ per character and tier.
- **Frame = band ± 1500 as condition:** superseded. The four tiers do that.
- **± entries with direction by the level of the character, `o` and `~` with half a step:** superseded. Every character gets its direction explicitly in the table.
- **The band as target of an approach:** rejected. The strength would depend on the distance to the limit instead of the meaning of the event. The approach stays as an option for later (section 4).
- **Sensitivity from rarity:** rejected. The extremes are already in the bands.
- **Split `it = 10·a + b`** from the first draft in chat: rejected.

## 7. Situations and Factors

The 91 event types that reach humans and animals (section 5.1) are bundled into 25 situations. The assignment is in `src/main/resources/org/socialworld/tools/dct/event_situations.csv`. Variants within a situation get a factor on the steps. Results are rounded to integers.

One rule applies when bundling: if a variant reverses the direction or affects other attributes, it gets a situation of its own. A factor alone is not enough then. That is why e.g. "being shouted at" and "being asked" are separate.

| No. | Situation | Event types | Factors | may change | shapes the reaction (disposition) |
|---|---|---|---|---|---|
| 1 | sleeping | selfSleep | – | tiredness ↓, hunger ↑, mood (↑), power (↑, recovery) | – |
| 2 | drinking | selfDrink | – | hunger ↓ (thirst), tiredness (↓) | – |
| 3 | eating | selfEat | – | hunger ↓, mood (↑), tiredness (↑) | materialism (enjoyment) |
| 4 | relieving oneself | selfPiss, selfShit | 1 each | mood (↑) | – |
| 5 | moving | selfMove Walk, Sneak, Jump, Fly, Run, Swim | Walk 1, Sneak 1.2, Jump 1.5, Fly 1.5, Run 2, Swim 2 | tiredness ↑, hunger (↑) | power (how fast it tires) |
| 6 | examining | selfExamineBy Look, Smell, Taste, Touch | 1 each | nothing. The result is knowledge, that runs through perception | curiosity |
| 7 | touching | selfTouchBy Hand, Foot | 1 each | nothing | curiosity |
| 8 | taking something | selfInventory Take, Get | 1 each | mood (↑) | materialism (how much it pleases) |
| 9 | putting down or rearranging | selfInventory Drop, Switch, Set | Drop 1, Switch 0.5, Set 0.5 | nothing | – |
| 10 | using an item | selfHandleItem Use2, UseLeft, UseRight, AddRtoL, AddLtoR, Pull, Push | Use… and Add… 1, Pull and Push 1.5 | tiredness ↑, hunger (↑) | power |
| 11 | attacking | selfWeapon… (7), selfPunch… (6) | Weapon 1, Club 1.5, Backhand 0.8, Punch 0.6 | tiredness ↑, mood ±, courage ± | morals (guilt shows in mood), courage |
| 12 | listening | selfListenTo Statement, Question | 1 each | nothing. The content counts, that comes with the intensity | curiosity |
| 13 | hearing an instruction | selfListenToInstruction | – | mood ± | morals, courage (complies or resists) |
| 14 | understanding | selfUnderstand | – | mood (↑) | curiosity (how much it satisfies) |
| 15 | speaking oneself | selfAsk…, selfAnswer…, selfSay… (Normal, Scream, Whisper each) | Normal 1, Scream 1.5, Whisper 0.5 | mood (±) | – |
| 16 | being asked | targetAsk Normal, Whisper | Normal 1, Whisper 0.5 | mood ± | curiosity |
| 17 | getting an answer | targetAnswer Normal, Whisper | Normal 1, Whisper 0.5 | mood (±) | curiosity |
| 18 | being shouted at | targetAskScream, targetAnswerScream | 1 each | mood ↓, courage (↓) | courage, morals |
| 19 | someone moves really close (possibly threatening) | candidatesMove Walk, Sneak, Jump, Fly, Run, Swim | Walk 1, Sneak 1.5, Jump 1, Fly 1.5, Run 1.5, Swim 1 | mood ±, courage (±) | courage |
| 20 | being hit by a weapon | candidatesWeapon… (7) | Stab, Stroke 1, Backhand 0.8, Club 1.5 | mood ↓↓, power ↓ (injury), courage ± | courage |
| 21 | being punched | candidatesPunch… (6) | 1 each | mood ↓, power (↓), courage ± | courage |
| 22 | someone speaks nearby | candidatesSay Normal, Whisper | Normal 1, Whisper 0.5 | nothing | curiosity |
| 23 | someone screams nearby | candidatesSayScream | – | mood (↓), courage (↓) | courage |
| 24 | someone is there | percipientExistsDistance 5000, 1000 | step 0 for now (section 5.1) | nothing | – |
| 25 | sees someone move | percipientMove Walk, Sneak, Jump, Fly, Run, Swim (new, section 7.2) | Walk 0, Swim 0, Jump 0, Fly 0, Run 1, Sneak 1 | only for Run and Sneak: mood (↓) or courage (↓), unease | courage |

**Cause and effect (decided 2026-11-15):** An event changes only the attributes it really acts on. Seeing an apple does not make anyone fuller, eating it does. The more basic attributes (section 6.5) practically do not change by single events. They act as **disposition**: they decide how strongly and in which direction an event changes the mobile attributes. Watching someone hit a child makes angrier (mood ↓). How much depends on morals; morals itself does not change.

For now the disposition lives in the character: its sentence and its steps follow from its levels. Asking for the **current** value of another attribute, such as "if curiosity > 7000, then mood +", is possible in principle with the condition term. It does not go together with the four tiers at the own attribute and is put off until later.

The list is accepted for the moment and will be refined later. Example: there are touches that change the mood.

Variants that are to have no effect get the factor 0. That is how the lines of situation 25 apply only to Run and Sneak.

That gives 25 situations × 100 characters = 2500 sentences, each with one line per attribute affected.

Overview 5.2 stays as an **orientation for the typical direction**. Binding is the table per character.

### 7.1 Intervals Between the Events of an Action

**Finding (code, 2026-11-15):** `ActionHandler.doActualAction` calls `perform()` of a running action repeatedly, and every call creates the events anew. The interval was set by `getSecondsTillNextCalculation()` from the remaining duration:

- more than 600 s: √rest
- 60 to 600 s: ⁴√rest
- up to 60 s: on every pass

So the number of events of an action could hardly be predicted. A step in the table applies per event, though.

**Decided 2026-11-15:** Instead of counting we fix **fixed intervals per action**. The grading by the remaining duration is dropped. Then the number of events per action is known, and every step can be read: effect per event × number of events.

The interval belongs to the `ActionMode`, not to the event type. One call of `perform()` creates the events of all roles together. **Once** means: the event is created once at the start, after that only the duration runs out.

| Situation | ActionMode | Interval |
|---|---|---|
| 1 sleeping | sleep | 300 s (8 h give 96 events). Perhaps later three sleep events with their own beats |
| 2 drinking | drink | 2 s |
| 3 eating | eat | 10 s |
| 4 relieving oneself | piss, shit | once |
| 5, 19 moving, someone moves nearby | walk, run, sneak, swim, fly | 1 s |
| | jump | once |
| 6 examining | look, smell, taste, touch | 5 s |
| 7 touching | hand, foot | once |
| 8, 9 inventory | takeItem, getItemFromInventory, dropItem, switchItemToOtherHand, setItemToInventory | once |
| 10 using an item | useTwoItems, useItemLeftHand, useItemRightHand, combineItems_AddRightToLeft, combineItems_AddLeftToRight | 2 s |
| | pull, push | 2 s |
| 11, 20, 21 fight | weapon… (7), punch… (6) | once. Several blows are several actions |
| 12–14 listening, instruction, understanding | listenTo…, understand | once |
| 15–18 speaking, being asked, answer, being shouted at | ask…, answer… | once |
| 15, 22, 23 speaking, someone speaks or screams nearby | sayNormal, sayScream, sayWhisper | once |
| 24 someone is there | no action | see below |
| 25 sees someone move | walk, run, sneak, swim, fly, jump (as 5) | as 5, throttled by the cooldown (7.2) |

**Movement:** `PositionCalculator` moves the object per event by the strength of the event and does not take the time into account. So the beat sets the speed. That is why Move keeps a short fixed beat of 1 s.

**Exists (24):** These events do not come from an action but in turn from `ObjectMaster.perceiveNextObject`. Their frequency is set by the cooldown (section 7.2).

**Someone moves nearby (19):** no cooldown, the event comes every second (section 7.2).

**What this means for the table:**
- With "once" the step is the whole effect, e.g. a punch mood −600.
- With a beat the step is the effect per beat, e.g. sleep tiredness −60 every 5 minutes, so up to −5760 in 8 hours if no tier brakes.

**Implementation (A4, built 2026-11-16):** The intervals are fixed at the `ActionMode` (`getEventIntervalSeconds()`, 0 = once). `core/ActionEventTiming` calls `perform()` at t = 0, a, 2a, … while the action runs. With "once" there is no further call of `perform()` until the end of the duration. Details in 12.5.

### 7.2 Proximity, Seeing and Cooldowns

**Decided 2026-11-15:**

- **candidatesMove… (situation 19)** means "someone moves **really close**, possibly threatening". The event fires at the beat of the action, every second, and has **no cooldown**. candidates types have the priority `CRITICAL` and are never throttled.
- The ranges in `EventType.getEffectDistance()` stay as they are for now. Fine tuning comes later.
- **Seeing movement** runs through **percipientMove…** with a cooldown. For it **situation 25 "sees someone move"** is added (6 types). Then 91 event types count instead of 85.
- For this `ActionMove` also creates an `EventToPercipient` since 2026-11-16 (A5). Before, `new EventToPercipient` stood only in `SimulationObject.letBePerceived` (Exists). The recipients within reach are found by `EventMaster.determinePossiblePercipients`.

**How the cooldown worked before A6 (code, 2026-11-15):**

- `TickObjectCooldown` keeps a matrix: one row per object, one column per cooldown kind (`TYPE_…`, 8 kinds then).
- `checkAndApplyCooldown(objectId, kind, seconds)` opens a window of `seconds` length and lets at most `getMaxAllowedForType(kind)` events through in it.
- `AttributeCalculator.calculateAttributesChangedByEvent` throttled percipient events with a throttleable priority. `AMBIENT` ran through `TYPE_PERCEPTION_AMBIENT` (1 s, 1 event), `DYNAMIC` through `TYPE_PERCEPTION` (1 s, 5 events). `CRITICAL` was not throttled.
- The cooldown affects only the effect on the attributes. Perception (`calculatePerception`) and reaction (`reactToEvent`) are not affected.

**Groups by criticality (decided 2026-11-15):** The length of a cooldown reflects how critical a perception is. So the groups are formed **by criticality**, not by the meaning of the event. Every stage gets a cooldown kind of its own with a fixed window.

Consequences:
- All event types of a stage have the same window. That the first event sets the length no longer matters.
- A change into a more critical stage is perceived at once. If someone goes from Walk (stage 3) to Run (stage 1), the window of Walk does not block Run.
- A column per event type is not needed. Four new cooldown kinds are enough.

| Stage | Meaning | Cooldown kind | Window | Events per window |
|---|---|---|---|---|
| 0 | critical | – | no cooldown | all |
| 1 | high | `TYPE_ATTRIBUTES_HIGH` | 5 s | 1 |
| 2 | medium | `TYPE_ATTRIBUTES_MEDIUM` | 10 s | 1 |
| 3 | low | `TYPE_ATTRIBUTES_LOW` | 30 s | 1 |
| 4 | background | `TYPE_ATTRIBUTES_BACKGROUND` | 60 s | 1 |

**Assignment per event type (confirmed 2026-11-15):**

| Event type | Priority then | Stage | Window | created |
|---|---|---|---|---|
| percipientSleep | AMBIENT | 4 | 60 s | later |
| percipientDrink | DYNAMIC | 3 | 30 s | later |
| percipientEat | DYNAMIC | 3 | 30 s | later |
| percipientPiss | AMBIENT | 4 | 60 s | later |
| percipientShit | AMBIENT | 4 | 60 s | later |
| percipientMoveWalk | AMBIENT | 3 | 30 s | new from `ActionMove` |
| percipientMoveRun | DYNAMIC | 1 | 5 s | new |
| percipientMoveSneak | DYNAMIC | 1 | 5 s | new |
| percipientMoveJump | DYNAMIC | 2 | 10 s | new |
| percipientMoveSwim | DYNAMIC | 3 | 30 s | new |
| percipientMoveFly | DYNAMIC | 2 | 10 s | new |
| percipientExamineByLook | DYNAMIC | 3 | 30 s | later |
| percipientExamineBySmell | DYNAMIC | 3 | 30 s | later |
| percipientExamineByTaste | DYNAMIC | 3 | 30 s | later |
| percipientExamineByTouch | DYNAMIC | 3 | 30 s | later |
| percipientTouchByHand | DYNAMIC | 2 | 10 s | later |
| percipientTouchByFoot | DYNAMIC | 2 | 10 s | later |
| percipientInventoryTake | DYNAMIC | 2 | 10 s | later |
| percipientInventoryDrop | DYNAMIC | 3 | 30 s | later |
| percipientInventorySwitch | DYNAMIC | 3 | 30 s | later |
| percipientInventorySet | DYNAMIC | 3 | 30 s | later |
| percipientInventoryGet | DYNAMIC | 2 | 10 s | later |
| percipientHandleItemUse2 | DYNAMIC | 3 | 30 s | later |
| percipientHandleItemUseLeft | DYNAMIC | 3 | 30 s | later |
| percipientHandleItemUseRight | DYNAMIC | 3 | 30 s | later |
| percipientHandleItemAddRtoL | DYNAMIC | 3 | 30 s | later |
| percipientHandleItemAddLtoR | DYNAMIC | 3 | 30 s | later |
| percipientHandleItemPull | DYNAMIC | 2 | 10 s | later |
| percipientHandleItemPush | DYNAMIC | 2 | 10 s | later |
| percipientWeaponLeftStab | CRITICAL | 0 | – | later |
| percipientWeaponLeftStroke | CRITICAL | 0 | – | later |
| percipientWeaponLeftBackhand | CRITICAL | 0 | – | later |
| percipientWeaponRightStab | CRITICAL | 0 | – | later |
| percipientWeaponRightStroke | CRITICAL | 0 | – | later |
| percipientWeaponRightBackhand | CRITICAL | 0 | – | later |
| percipientWeaponClub | CRITICAL | 0 | – | later |
| percipientPunchLeftFistStraight | CRITICAL | 0 | – | later |
| percipientPunchLeftFistSideways | CRITICAL | 0 | – | later |
| percipientPunchLeftFistUpward | CRITICAL | 0 | – | later |
| percipientPunchRightFistStraight | CRITICAL | 0 | – | later |
| percipientPunchRightFistSideways | CRITICAL | 0 | – | later |
| percipientPunchRightFistUpward | CRITICAL | 0 | – | later |
| percipientAskNormal | DYNAMIC | 2 | 10 s | later |
| percipientAskScream | CRITICAL | 0 | – | later |
| percipientAskWhisper | DYNAMIC | 2 | 10 s | later |
| percipientAnswerNormal | DYNAMIC | 2 | 10 s | later |
| percipientAnswerScream | CRITICAL | 0 | – | later |
| percipientAnswerWhisper | DYNAMIC | 2 | 10 s | later |
| percipientExistsDistance100000 | AMBIENT | 4 | 60 s | no |
| percipientExistsDistance10000 | AMBIENT | 4 | 60 s | no |
| percipientExistsDistance5000 | AMBIENT | 4 | 60 s | yes |
| percipientExistsDistance2000 | AMBIENT | 3 | 30 s | no |
| percipientExistsDistance1000 | AMBIENT | 3 | 30 s | yes |
| percipientExistsDistance100 | AMBIENT | 2 | 10 s | no |

Thoughts behind the assignment:
- **Stage 1 (high):** running and sneaking. Something can come of it at once: flight, attack or secrecy.
- **Stage 2 (medium):** jump and flight, touch, taking something (Take, Get), pulling and pushing, overhearing a conversation. Whoever takes something could be stealing.
- **Stage 3 (low):** everyday things: walking, swimming, eating, drinking, examining, using items, putting down, rearranging.
- **Stage 4 (background):** rest and bodily matters, and presence at a greater distance.
- **Presence:** the closer, the more critical. Distance100 is stage 2, 2000 and 1000 are stage 3, from 5000 on it is stage 4.

**Remarks:**

- The **stage decides** whether and how an event is throttled: stage 0 means unthrottled, from stage 1 on throttled. The priority now serves only `EventMaster` (order, yield). For the percipient types stage 0 coincides exactly with `CRITICAL`. `AMBIENT` and `DYNAMIC` can lie in any stage from 1 to 4, e.g. percipientMoveWalk is `AMBIENT` and stage 3.
- The assignment is at the `EventType` as `EventType.getAttributeCooldownLevel()` since 2026-11-16 (A6). Window and cooldown kind follow from the stage. `TYPE_PERCEPTION` and `TYPE_PERCEPTION_AMBIENT` are dropped.
- Self, target and candidates events have stage 0 in principle, no cooldown. Their frequency is set by the fixed intervals of the action (7.1).
- **Exception, given at the event type (decided 2026-11-15):** selfMoveWalk, selfMoveRun, selfMoveSneak, selfMoveSwim and selfMoveFly have stage 3 (30 s). They stay `CRITICAL` for `EventMaster`, the position stays at the one-second beat. Reason: even the smallest step of 1 per second would give +3600 tiredness per hour of walking. selfMoveJump comes only once anyway.
- **Likewise (decided 2026-11-15):** selfHandleItemUse2, selfHandleItemUseLeft, selfHandleItemUseRight, selfHandleItemAddRtoL, selfHandleItemAddLtoR, selfHandleItemPull and selfHandleItemPush have stage 3 (30 s). Reason: at a beat of 2 s already the step +1 brought the farmer from tiredness 40 to 80 in just under 2 hours of work (day calculation, section 8.1).

### 7.3 Cooldowns of the Self Events

Decided 2026-11-15. Derived from the fixed intervals (7.1): a beat of *a* seconds gives 3600/*a* events per hour. Because the smallest effective step is 1 (section 9), that is at least 3600/*a* points per hour. A cooldown is needed only where that is too much.

**Notation:** cooldown 1 means **no cooldown**. In the implementation it counts as stage 0, without any check. A real cooldown of 1 s would drop a second self event of the same object in the same second.

| Event type | Beat of the action | Events per hour | Cooldown (s) | Remark |
|---|---|---|---|---|
| selfSleep | 300 s | 12 | 1 | |
| selfDrink | 2 s | 1800 | 1 | Drinking lasts only seconds. A cooldown would cut every drink to one event |
| selfEat | 10 s | 360 | 1 | a meal is 90 to 120 events |
| selfPiss | once | – | 1 | |
| selfShit | once | – | 1 | |
| selfMoveWalk | 1 s | 3600 | **30** | |
| selfMoveRun | 1 s | 3600 | **30** | |
| selfMoveSneak | 1 s | 3600 | **30** | |
| selfMoveJump | once | – | 1 | |
| selfMoveSwim | 1 s | 3600 | **30** | |
| selfMoveFly | 1 s | 3600 | **30** | |
| selfExamineByLook | 5 s | 720 | 1 | to 30 as soon as the situation gets an effect on attributes |
| selfExamineBySmell | 5 s | 720 | 1 | as Look |
| selfExamineByTaste | 5 s | 720 | 1 | as Look |
| selfExamineByTouch | 5 s | 720 | 1 | as Look |
| selfTouchByHand | once | – | 1 | |
| selfTouchByFoot | once | – | 1 | |
| selfInventoryTake | once | – | 1 | |
| selfInventoryDrop | once | – | 1 | |
| selfInventorySwitch | once | – | 1 | |
| selfInventorySet | once | – | 1 | |
| selfInventoryGet | once | – | 1 | |
| selfHandleItemUse2 | 2 s | 1800 | **30** | |
| selfHandleItemUseLeft | 2 s | 1800 | **30** | |
| selfHandleItemUseRight | 2 s | 1800 | **30** | |
| selfHandleItemAddRtoL | 2 s | 1800 | **30** | |
| selfHandleItemAddLtoR | 2 s | 1800 | **30** | |
| selfHandleItemPull | 2 s | 1800 | **30** | |
| selfHandleItemPush | 2 s | 1800 | **30** | |
| selfWeapon… (7), selfPunch… (6) | once | – | 1 | several blows are several actions |
| selfListenToStatement, …Question, …Instruction, selfUnderstand | once | – | 1 | |
| selfAsk…, selfAnswer…, selfSay… (Normal, Scream, Whisper each) | once | – | 1 | |

With today's intervals only `selfMove…` (without Jump) and `selfHandleItem…` need a cooldown, both 30 s, that is stage 3 of 7.2. If intervals change or situations get an effect, the table is updated.

## 8. The Table as CSV

Decided 2026-11-11: The table is a CSV file and the **source** for the mass generation button. The button only translates it into JSON and writes it into the database through `TablePoolEID`.

Implemented like this (A3):
- **Place:** next to `characters.csv`, that is `src/main/resources/org/socialworld/tools/dct/`.
- **Name:** `event_influences.csv`.
- **Layout:** as with `characters.csv`, with `;` as separator and `#` for comments. Numbers use the decimal point.
- **Language and storage:** Decided 2026-11-15: the sentences are **English**, the file is in git.

```
# character;situation;sentence;attribute;below;lowerHalf;upperHalf;above
69;21;The wrestler is used to fighting. A punch leaves him calm, if anything it spurs him on.;mood;0;0;-50;-50
69;21;The wrestler is used to fighting. A punch leaves him calm, if anything it spurs him on.;courage;150;500;300;30
69;21;The wrestler is used to fighting. A punch leaves him calm, if anything it spurs him on.;curiosity;20;200;100;20
```

(the example of 6.4; the lines in the file differ, see there)

- The sentence stands in every line. That is redundant, but when filtering one always sees it.
- If a situation is missing for a character, or it has no attribute line, the situation changes nothing. The generator writes a neutral rule for it (section 5.4).
- The situations and factors of section 7 are in a second, small file: `event_situations.csv` (event type, situation, factor).

### 8.1 Check by a Day Calculation

Each block of characters was checked by recomputing a day of each character with the rules from the CSV (a replica of the formulas, not the real code). The first version took into account the tiers at the band, the intervals (7.1), the cooldowns (7.2) and the rounding (section 9), and showed the course of the day as a diagram: time on the x axis, displayed values 0..99 on the y axis, the events as letters. It computed only the event rules, without matrices and without the effect of time. Later versions added the matrix drive and the need factors (13) and the tiers at the ideal (14).

First check, farmer, 2026-11-15:
- A tool at a beat of 2 s drove tiredness to the upper limit in just under 2 hours. Thereupon `selfHandleItem…` got stage 3 (7.2), and the step was set to +3/+2/+2/0, plus hunger +1/+1/+1/0.
- A meal lowered hunger by about 35 points. The steps were halved.
- After that: tiredness from 33 in the morning to 66 in the evening, hunger falls by about 20 points with every meal. Because the effect of time was missing, hunger hardly rose between meals.

### 8.2 The Characters 1 to 10 at a Glance

Status 2026-11-15. The characters 1 to 10 are in `event_influences.csv`, 404 lines, 25 situations each. Only the mobile attributes are changed (lines over all 10): mood 148, tiredness 60, hunger 50, courage 44, power 30.

**The same day for all 10:** sleep, work with a tool, three meals, shouted at at 12:30, punch at 13:00, club at 21:00. Start values: tiredness 75, hunger 45, all others in the middle of their own band. Only the event rules are computed.

The effects of punch, club and shouting are given in attribute values 0..9999, the other columns in displayed values 0..99.

| No. | Character | tiredness 06:00 | tiredness 22:00 | punch mood / courage / power | club mood / courage | shouted at mood | mood 22:00 | courage 22:00 |
|---|---|---|---|---|---|---|---|---|
| 1 | farmer | 33 | 66 | −200 / +10 / −20 | −750 / −375 | −100 | 51 | 46 |
| 2 | farmer's wife | **56** | **75** | −250 / −80 / −40 | −825 / −300 | −120 | 45 | 46 |
| 3 | farmhand | **28** | 61 | **−120 / +15** / −10 | **−600** / −225 | **−50** | **65** | 47 |
| 4 | maid | **55** | **75** | **−300 / −120** / −40 | **−900** / −375 | **−180** | **43** | **26** |
| 5 | carpenter | 33 | 61 | −200 / +10 / −20 | −750 / −375 | −100 | **60** | 46 |
| 6 | shopkeeper | 33 | **69** | −250 / −60 / **−60** | −825 / −300 | −100 | 50 | 46 |
| 7 | fisherman | 33 | 61 | **−120 / +15** / −10 | **−600 / +15** | **−50** | 55 | **66** |
| 8 | fisherman's wife | **41** | 64 | −250 / −60 / −40 | −825 / −300 | −120 | **43** | 46 |
| 9 | shepherd | 33 | 64 | −200 / −30 / −40 | −750 / −225 | −120 | **67** | 47 |
| 10 | miller | 33 | 64 | −200 / +10 / −20 | −750 / −375 | −100 | 53 | 46 |

**The striking differences**

**The tough ones: fisherman (7) and farmhand (3).**
- A punch costs both only half as much mood as the farmer, shouting likewise.
- The **fisherman** is the only one whom even the club makes **braver** (courage +15, "defiant rather than afraid"). He is the only one to end with courage 66.
- The **farmhand** sleeps most deeply (28 in the morning). Eating is his high point: his meals lift mood more than with anyone else, he ends with 65. Seeing, hearing and being asked leave him almost cold.

**The vulnerable ones: maid (4), farmer's wife (2) and fisherman's wife (8).**
- Everything hits the **maid** hardest: punch −300, club −900, shouting −180. Her courage falls over the day down to 26. Two things exist only with her: an instruction **pleases** her ("serving is her place"), and speaking herself **costs** her something.
- The **farmer's wife** sleeps lightly (56 in the morning) and is at the limit in the evening (75). That fits "always on her feet" with tiredness `+`. As a mother she becomes uneasy with closeness, screams and running people.
- The **fisherman's wife** sleeps restlessly (41) because she waits for her husband. She fears screams and running people as bad news, an answer relieves her.

**The even-tempered ones: farmer (1), carpenter (5) and miller (10).**
- They react almost the same to violence. The difference is in everyday life.
- The **carpenter** is the only one who **enjoys his work** (mood +1 per beat with the tool). He therefore ends with mood 60, and it makes him proud to be asked for advice.
- The **miller** has **no bad conscience** when striking out (no mood line, morals `-`). Profit pleases him much (+40), and he suspects running people of being flour thieves.

**The sociable one: shopkeeper (6).**
- Talking, being asked, an answer and understanding lift his mood clearly more than with all others. That is curiosity `+` showing.
- Because he is weak (power `-`), a blow hits him hardest physically (power −60), and running tires him faster.

**The contented one: shepherd (9).**
- He is the only one with mood `+` and ends with the best mood (67).
- He is happy alone: someone nearby makes him slightly uneasy, conversations please him all the more.

**Observations**
- **The meals of the farmhand** are marked. With about 100 events per meal mood +12 lifts his mood by about 5 displayed points per meal. That fits "likes his food", but lies at the upper edge of the plausible.
- **The bands play along.** With the maid and the farmer's wife tiredness lies higher with `+`, so sleep brakes earlier. Their exhaustion comes from the character, not only from the steps. That is how the band is meant. (Superseded by section 14: for tiredness and hunger the tiers sit at the ideal.)

## 9. Technical Prerequisites

1. **Condition with a lower and an upper limit.** Before, `ChangeAttribute.createConditionExpression` built exactly one comparison from the condition term: attribute, operator, constant. The middle tiers need "L ≤ Y < M". **Done 2026-11-16 (A1):** a multiple of 3 arguments gives comparisons joined by `and` and decided strictly.
2. **Function slots.** Before, every MX+N took a slot of its own in `functions` (10000 to 99999), found by a linear search with `findNextFree`. Estimate: 100 characters × 91 types × about 3 attributes × 4 tiers gives about 100,000, more than the 90,000 slots. **Done 2026-11-16 (A2):** the class `Functions` is dissolved. `FunctionPool.register` gives func_ids from 10000 on without an upper limit and without searching; the same function always gets the same func_id.
3. **Integers and rounding (finding 2026-11-15).** `FunctionMXplusN` computes in floating point, `Calculation.createValue(Type.integer, …)` cuts off with `intValue()`. Because Y is positive, that means rounding down: an N of +7.5 acts as +7, an N of −7.5 as −8. With an integer N and M = 1 the result is exact. That is why **the generator rounds** `step × factor` symmetrically (from .5 away from 0) and writes N as an integer into the formula. A **0 is allowed** (decided 2026-11-15): a 0 in the CSV stays 0, a value then stays constant. With today's factors (at least 0.5) no step other than 0 becomes 0. The smallest effective step per event is 1.
4. **If/then/else-if chain:** we think about that elsewhere. We do not go back to the old line format, the rules stay JSON.

## 10. Open Questions

1. ~~What does `target` mean for Eat, Drink and HandleItem?~~ Settled 2026-11-11: there the target is an item, see section 5.1.
2. ~~Are the arrows in section 5.2 right?~~ Superseded by the table per character. 5.2 is only an orientation now.
3. ~~How large are the steps?~~ Superseded by the table per character. The steps are there, per tier. How often an action fires its event is fixed by fixed intervals since 2026-11-15 (section 7.1).
4. ~~How does the character act?~~ Decided 2026-11-11, see section 6.
5. Which event types actually occur in the simulation and how often? From the code it is settled which are created at all and that Exists acts up to once per second (section 5.1). How often the others occur only a run shows. Open, the run is still to come.
6. ~~What does the neutral rule look like?~~ Decided and built 2026-11-16: empty `attributeChanges` list, the attribute array stays unchanged (section 5.4).
7. ~~Are the situations and factors in section 7 right?~~ Confirmed 2026-11-15.
8. ~~In which language are the sentences?~~ Decided 2026-11-15: English, the CSV goes into git (section 8).
9. ~~morals: good against evil?~~ Decided 2026-11-16: morals is the alignment good against evil (section 6.5).
10. ~~Refinement of the tiers~~ Confirmed 2026-11-15: the step is very small only on the side where the event leads **away** from the band. On the side where it leads **back** into the band it acts normally. Example sleep: below the band of tiredness a tiny step, above the band (overtired) the full one. The wrestler example in 6.4 is already built that way.
11. ~~Cooldowns for Exists and "someone moves nearby"?~~ Decided 2026-11-15: candidatesMove without cooldown, seeing movement through percipientMove with cooldown (section 7.2).
12. ~~Are the stages, their windows and the assignment per event type in section 7.2 right?~~ Confirmed 2026-11-15.

## 11. Next Steps

1. ~~Fix situations and factors, refinement of the tiers, cooldown stages and language.~~ Done 2026-11-15.
2. ~~Trial: character 1 (farmer) with all 25 situations.~~ Done 2026-11-15, revised after "cause and effect" (section 7).
3. ~~Writing the table~~ in blocks of 10 characters, each block checked by the user before the next one: **1 to 10 done 2026-11-15** (section 8.2), **11 to 20 done 2026-11-17** (14.11), **21 to 100 done 2026-11-18** (section 15). Counted by the character number 21 to 100, that is the influence types 20 to 99.
4. ~~Technical prerequisites~~ Done 2026-11-16: A1, A2, A4, A5, A6, plus C1 (section 12.5) and on 2026-11-17 C6 (12.6).
5. ~~The generator reads the CSV and creates the rules from it.~~ Done 2026-11-16 (A3). The rules of all 100 characters were written into the database on 2026-11-18.
6. ~~**The first run** with the new rules.~~ Run 2026-11-17: rules load without exception, "without rule" and "rule error" are 0. Fixed on the way: C6 to C14, moved out (section 12.6).
7. ~~**Balancing with the attribute matrix**~~ (section 13): linear rise, daily balance, need of sleep and food per character. Built 2026-11-17.
7a. ~~**Ideal for tiredness and hunger**~~ (section 14): A/E and A_h/E_h per character, tiers at these intervals. Built 2026-11-17, for all 100 characters since 2026-11-18.
8. ~~Couple the assignment of the influence type to the character~~ (A7). Built 2026-11-18 (section 15.11).
9. **State2Action into the database** (project of its own, `state2action_konzept.md`).

All code changes with place, reason and order are in section 12.

## 12. Code Changes at a Glance

Built: C1, A1 to A11, A13 to A23 and A7. A12 (State2Action) is a project of its own. What was found and built outside EID (C6 to C14) is in 12.6. Tables 12.1 to 12.3 describe the points as they were planned. What is actually built is in 12.5 and in sections 13 to 15.

### 12.1 Needed for the Concept

| No. | What | Where | Why | Concept |
|---|---|---|---|---|
| A1 | Condition with lower **and** upper limit in the JSON condition term | `ChangeAttribute.createConditionExpression` (built exactly one `Comparison`); `and` already exists in `Expression_ConditionOperator` | The middle tiers need "L ≤ Y < M" | 6.3, 9.1 |
| A2 | More function slots | `MXPlusN.init` (`findNextFree(10000, 99999)`), `Functions` (`MAX_SIZE`) | About 100,000 MX+N do not fit into 90,000 slots. Optionally equal functions share a slot, `findNextFree` searches linearly | 9.2 |
| A3 | New generator | `CreationTool_EventInfluenceDescription.runMassGeneration` | Reads `event_influences.csv` and `characters.csv`. Assigns situations to event types, with factors. Computes the tiers from the band, rounds `step × factor` symmetrically and orders the entries by direction. Writes through `TablePoolEID`, influence type = character number − 1 | 6, 7, 8, 9.3 |
| A4 | Fixed intervals per `ActionMode` | `ActionHandler.getSecondsTillNextCalculation` (square root of the remaining duration). "Once" means: no further `perform()` until the end | The number of events per action becomes computable | 7.1 |
| A5 | Create percipientMove events | `ActionMove` (additionally an `EventToPercipient`) | Seeing movement (situation 25). Except Exists no percipient events were created | 7.2 |
| A6 | Cooldown stages per event type | `EventType` (new `getAttributeCooldownLevel()`), `TickObjectCooldown` (4 new kinds with `getMaxAllowedForType`), `AttributeCalculator.calculateAttributesChangedByEvent` (stage instead of priority) | Criticality for percipient events, plus the exceptions `selfMove…` and `selfHandleItem…` with 30 s | 7.2, 7.3 |
| A7 | Couple the influence type to the character | `CreateSimulationObjects.initState`, `CreateAnimal` (attribute array), `GaussPoolInfluenceType`. Do not forget the commented-out pool code in `LoadHuman` | Attribute array and influence type were drawn separately, and the influence type was shuffled per event type | 6.1, 15.11 |

**Dependencies:**
- A1 and A2 had to be done before A3 could write rules with four tiers.
- A4, A5 and A6 do not depend on A3, but the values in the CSV assume them.
- A7 made sense only once the new rules were in the database.

### 12.2 Open Points with Code Reference

| No. | What | Where | Status |
|---|---|---|---|
| B1 | Neutral rule for the 102 irrelevant types (193 − 91) and for situations without an attribute line | `ChangeAttributes.fromJsonEntries` (an empty list gave `Nothing`), `AttributeCalculator` (a missing rule counted as error in `counterErrors_Event`) | done with A3 2026-11-16: empty list, array stays unchanged, own counter "without rule" (5.4) |
| B2 | `table.clear()` deletes all rules on mass generation, also those made by hand | `runMassGeneration` | decided with A3 2026-11-16: `table.clear()` stays |

### 12.3 Side Findings, Not Part of the Concept

| No. | What | Where | Consequence |
|---|---|---|---|
| C1 | The queries used `\|\|` instead of `&&`, e.g. `index >= 8 \|\| index <= 13`. So the method **always** returned `true` | `EventType.isRelevantForChangingPosition()` | Probably **every** event to an animal or human also went through `PositionCalculator`, which moved the object by its own movement and by the strength of the event. Fixed 2026-11-16 (12.5) |
| C3 | Integer results are cut off instead of rounded | `Calculation.createValue(Type.integer, …)` with `intValue()` | Caught by A3, because the generator rounds to integers. Alternatively `Math.round` in the code. That affects all functions, though, so it would change semantics |
| C4 | The bystanders get the same `target` type as the one addressed for Ask, Answer, Touch, Pull and Push | `EventType.isRelevantForEffectiveCheck()` with `EventMaster` | A question acts on bystanders as on the one asked. Accepted for the first step (5.3) |
| C5 | In constant mode the editor writes `function: "value"` with a constant, but `createActionExpression` knows only `"MX+N"` | `CreationTool_EventInfluenceDescription.btnSaveToDatabasePressed`, `ChangeAttribute.createActionExpression` | A rule saved that way becomes `Nothing`, it is invalid. Affects only the editor, not the generator |

### 12.4 Suggested Order

1. ~~**Check C1 first**, because it could affect positions in every run.~~ Done 2026-11-16.
2. ~~Then **A1 and A2** as technical base.~~ Done 2026-11-16.
3. ~~After that **A3** and a run with the characters 1 to 10.~~ Done 2026-11-16 and 2026-11-17, plus C6 (12.6).
4. ~~**A4 to A7** after that, step by step.~~ A4 to A6 done 2026-11-16, before the run. A7 done 2026-11-18.

### 12.5 State of Implementation

| Point | Status | What is built |
|---|---|---|
| C1 | implemented 2026-11-16 | `isRelevantForChangingPosition()` with `&&`. New `EventType.isEventToCandidates()` and `getPositionChangeDistance()`: candidatesMove… 500, candidatesWeapon… and candidatesPunch… their full reach (`getEffectDistance()`), all others 0. `PositionCalculator` moves a candidate only within this distance. Self and target events pass without check |
| A1 | implemented 2026-11-16, test `ChangeAttributeConditionTest` | The condition term has 3 arguments (one comparison, soft through the sigmoid as before) or a multiple of 3 (comparisons joined by `and`, strict). Every comparison has its own attribute. No multiple of 3: console message, condition `Nothing`. If term 1 is no `condition`, there is only a console message. An "or" is mapped by further entries; then mind the order and check the negation if needed. The generator writes all four tiers as two comparisons, so that all are strict |
| A2 | implemented 2026-11-16, tests `FunctionPoolTest` and `MXPlusNTest` | The class `Functions` is dissolved, there is only `FunctionPool`; functions from rules get a func_id from 10000 on through `FunctionPool.register`, equal functions share it. Details: `functions_und_functionpool.md` |
| A3 | implemented 2026-11-16, tests `EventInfluenceCatalogTest` and `EventInfluenceGeneratorTest` | New classes in `tools/dct`: `EventInfluenceSituations` (reads `event_situations.csv`, 91 event types with situation and factor), `EventInfluenceCatalog` (reads `event_influences.csv`), `EventInfluenceRuleBuilder` (4 tiers per line, condition with 6 arguments, ordered by direction, step × factor rounded symmetrically, N = 0 and empty tiers without entry), `EventInfluenceGenerator`, `EventInfluenceStore` with `TablePoolEventInfluenceStore` and `InMemoryEventInfluenceStore`. **Every** pair of event type and influence type gets a rule (193 × 100). Without effect it is a **neutral rule** with an empty `attributeChanges` list; `ChangeAttributes` returns the attribute array unchanged for it (before `Nothing`). While the CSV had fewer than 100 characters they were repeated: influence type i got the (i mod n)-th character. Since 2026-11-18 there are 100, nothing is repeated. `AttributeCalculator` counts "without rule" apart from "rule error" and prints both. The button is called "GENERATE RULES FROM event_influences.csv", `table.clear()` stays |
| A4 | implemented 2026-11-16, test `ActionEventTimingTest` | `ActionMode.getEventIntervalSeconds()` with the intervals of 7.1 (0 = once). New `core/ActionEventTiming`: `perform()` at t = 0, a, 2a, … while the action runs; at the end only a **closing without `perform()`** that lets the remaining time pass. `ActionHandler` uses it instead of the square root of the remaining duration. Interrupting and stopping still trigger a `perform()`. **If something looks odd at the end of actions** (missing last step, missing last event), look there first: there is a comment at the place in `ActionHandler.doActualAction`. Note: `doActualAction` computes at most once per second |
| A6 | implemented 2026-11-16, tests in `TickObjectCooldownTest` | `EventType.getAttributeCooldownLevel()` with the stages of 7.2 (percipient) and 7.3 (`selfMove…` without Jump, `selfHandleItem…`: stage 3), otherwise 0. `TickObjectCooldown`: new kinds `TYPE_ATTRIBUTES_HIGH`, `_MEDIUM`, `_LOW`, `_BACKGROUND` (windows 5, 10, 30, 60 s, 1 event each) with `getAttributesCooldownType(level)` and `getAttributesCooldownSeconds(level)`. Dropped: `TYPE_PERCEPTION`, `TYPE_PERCEPTION_AMBIENT` and their `MAX_…` constants; they were used only in `AttributeCalculator` (since `a9f6274d`, 2026-07-05), the kinds are renumbered. `AttributeCalculator` throttles by stage, no longer by priority; the priority now serves only `EventMaster`. Consequence: before, up to 5 `DYNAMIC` or 1 `AMBIENT` event per second got through, now at most one per window of the stage |
| A5 | implemented 2026-11-16, test `EventTypeDistancesTest` | `ActionMove.perform()` also creates an `EventToPercipient` (percipientMoveWalk … Fly), every second, without thinning for now. Ranges in `getEffectDistance()`: Walk 20000, Run 30000, Sneak 10000, Jump 20000, Swim 20000, Fly 50000. **Correction in `EventToPercipient`:** the `Percipience` of the event now gets the position of the event; before it knew none, and `checkMaySeeingEvent` always failed, nobody would have seen the event (Exists not affected, it uses the percipience of the object). Only who looks towards the event within 100 m sees it. Reaction and perception have no rules for percipientMove yet: the pools return a placeholder without functions, nothing happens. **Correction to A3:** also for the event rules the pool returns a placeholder for a missing rule, not `null`; "without rule" now also counts a rule without a function |

The side findings of 5.5 have become C1 and C2.

### 12.6 Moved Out (2026-11-17)

These findings and changes came up during the work on the rules but do not belong to EID. They are now in concepts of their own, with the same numbers.

| No. | Topic | now in |
|---|---|---|
| C6 | `Sequence` with 4 to 6 members (affected all EID rules with 4, 5, 6, 10, 11, 12 entries) | `befunde_simulation_kern.md` |
| C7 | Direction of view in `Percipience.checkMaySeeingEvent` | `befunde_simulation_kern.md` |
| C8 | Double counting "without change" in `AttributeCalculator` | `befunde_simulation_kern.md` |
| C9 | Remaining time in the dashboard | `simvisual_aufwertung.md`, addendum 2026-11-17 |
| C10 to C13 | Sleep duration of the test rules, search for actions, mouth in the inventory, interim solution for eating | `state2action_konzept.md` |
| C14 | Start positions and ID ranges of the objects | `objekterzeugung_ids_positionen.md` |
| A2 (details) | `Functions` → `FunctionPool` | `functions_und_functionpool.md` |

## 13. Balancing with the Attribute Matrix: Hunger and Tiredness over the Day

Status 2026-11-17. A8 to A11 are built (13.5), A12 is open. The numbers come from a replica of the formulas extended by the matrix, not from a run. The counterpart in the matrix concept is `attribute_matrix_characters.md`, section 4b.

### 13.1 No Hunger from Sleeping (built)

Sleeping does not make hungry, only time passes. That is an effect of the state, not of the event, and belongs into the attribute matrix. In `event_influences.csv` the hunger lines of situation 1 are `0;0;0;0` for all characters since 2026-11-17, with a note in the head of the file.

Checked: nothing else of the state is smuggled into the rules. Every tier checks only the attribute it changes. The four tiers make the strength depend on the current value, but never change anything without an event. power ↑ and mood ↑ with sleep are recovery through the action and stay.

### 13.2 Finding: Matrix and Meals Do Not Fit

The matrix let hunger rise exponentially (diagonal 1.00125). Together with the meals from the rules the farmer stood at 99 from 4 o'clock at night and came down only to 76 to 85 after each meal. Without the matrix, on the other hand, hunger fell to about 16 (section 8.1: the effect of time was missing). Both sides had never been tuned together. Details: matrix concept 4b.1.

### 13.3 Balance over the Day

**Requirements (2026-11-17):** Three normal meals bring hunger back to where it was at the start. Eight hours of sleep bring tiredness back to the value before the waking day. Sleep also has to make up for the time in which the matrix keeps making the sleeper more tired.

```
hunger:     1440 · c_h + strain = sum of the steps of all eating events
tiredness:  1440 · c_t + strain = sum of the steps of the 96 sleep events
```

`c` is the rise per minute from the matrix. The 1440 minutes include the night. The four tiers make the balance stable: a higher value falls into a larger tier and is lowered more. Every start value runs towards the same course of the day.

**Matrix (M1):** linear rise, diagonal 1.0. Because `FunctionByMatrix` then rounds to whole numbers per minute, hunger 4 and tiredness 3 per minute for the median (matrix concept 4b.2).

**Rules (M2):** the same for all characters and only for the one attribute, not through the situation factor, which would take mood and power along:

| Situation | Attribute | Factor | Reason |
|---|---|--:|---|
| 3 eating | hunger | 0.775 | meals were a little too strong for the new rise |
| 1 sleeping | tiredness | 1.7 | sleep now also makes up for the time |
| 5 moving, 10 using an item | tiredness | 0.33 | time now carries the basic rise; work alone brought 33 points a day before |

Replica, settled after 5 days, hunger/tiredness:

| No. | Character | 22:00 | 06:00 | 08:00 | 20:00 |
|---|---|---|---|---|---|
| 1 | farmer | 44/69 | 63/19 | 48/28 | 39/66 |
| 2 | farmer's wife | 43/**91** | 62/57 | 46/64 | 38/89 |
| 3 | farmhand | 65/69 | 84/19 | 68/30 | 60/66 |
| 4 | maid | 43/**92** | 62/57 | 46/64 | 38/89 |
| 5 | carpenter | 44/65 | 63/19 | 48/28 | 39/62 |
| 6 | shopkeeper | 44/68 | 63/19 | 48/28 | 39/65 |
| 7 | fisherman | 44/65 | 63/19 | 48/28 | 39/62 |
| 8 | fisherman's wife | 44/75 | 63/36 | 48/44 | 39/72 |
| 9 | shepherd | 44/66 | 63/19 | 48/27 | 39/64 |
| 10 | miller | 32/68 | 51/19 | 33/29 | 27/65 |

farmhand (hunger `+`) is hungrier, miller (`-`, "well fed") fuller, as intended.

**farmer's wife and maid** come to 57 in the morning and about 90 in the evening. That is **not because of their sleep steps**: with the steps of the farmer the maid stays at exactly 92/57 (replica). It is because of their band. tiredness `+` is 5800 to 7500. Below 5800 only the tiny step applies, so sleep does not bring her below 58. The day adds about 33 points, which gives about 90 in the evening. With the farmer (`~`, 2000 to 8000) the same swing fits into the band, with a narrow band like `+` (17 points wide) it does not.

In content that fits their sentences ("never quite catches up", "not enough"). The consequence shows with State2Action, though: today's sleep rule applies for everybody from tiredness 70, so the maid would be there again around noon. This led to section 14.

### 13.4 Need of Sleep and Food per Character

Some characters are rested after 6 hours, others need 10. For some two meals are enough, others need four.

**Two new columns in `characters.csv`:** `sleepHours` (S, 6.0 to 10.0 in half hours, default 8.0) and `mealsPerDay` (M, 1 to 5, default 3). Half hours work out evenly, because 12 · S is always a whole number of sleep events. The columns describe the need of the body, not the way of life: whoever gets too little (scholar, beggar, night watchman) has an ordinary need and settles higher. The values for all 100 characters: `attribute_charaktere.md`, section 5.

**Effect through the rules, not the matrix.** The rule generator multiplies the sleep steps (tiredness, situation 1) by **8 / S** and the eating steps (hunger, situation 3) by **3 / M**, on top of the factors of 13.3, and rounds as with the situation factors. Derivation: S hours bring 12·S sleep events, the balance demands `f · 12 · S · r̄ = 1440 · c_t + strain`. With f = 8 / S the left side stays equal to that of the reference, so whoever sleeps as long as he needs gets the same course of the day. Likewise for eating with 3 / M.

**Tiredness from eating × 3 / M as well.** Eating makes a little tired in the rules (situation 3, tiredness). Without the factor, whoever eats five times would get more tired by it: day mean of tiredness 52 instead of 45, with one meal 39. With the factor the tiredness from eating stays the same over the day.

| Need | Factor sleep | Factor eating | Meaning |
|---|--:|--:|---|
| 6 h | 1.33 | | sleeps more deeply, rested faster |
| 10 h | 0.8 | | recovers more slowly |
| 2 meals | | 1.5 | a meal lasts longer |
| 4 meals | | 0.75 | a meal lasts shorter |
| 1 meal | | 3 | one meal is enough for the day |
| 5 meals | | 0.6 | eats often and little |

Replica, hunger/tiredness at 22:00 after 6 days:

| Need | lives by his need | lives like the reference (8 h, 3 meals) |
|---|---|---|
| 8 h / 3 | 50 / 68 | 50 / 68 |
| 6 h / 2 | 51 / 66 | 27 / 67, full and rather rested |
| 10 h / 4 | 50 / 70 | 76 / 85, hungry and overtired |

Day mean of hunger when living by the need: 55 (8 h / 3), 54 (7.5 h / 1), 56 (8.5 h / 5), 58 (6 h / 2), 55 (10 h / 4). Whoever lives by his need gets the course of the reference. Whoever sleeps or eats less does not run away: the tiers catch him at a higher, fixed level.

**Rejected: the need in the matrix.** The rise would have to be proportional to S, that is 2.25 or 3.75 per minute. Rounded only 2, 3 or 4 are possible, that is 5.3, 8 or 10.7 hours. Too coarse.

**Prerequisite: the actions have to follow the need.** How long someone sleeps and how often he eats comes from State2Action. Today these are rules written into the code, the same for everyone. Since 2026-11-17 there is an interim eating rule for everybody (`hunger >= 6000`, 18 min) and one apple that everybody has in the mouth (`state2action_konzept.md`). How often someone eats already follows the need with that: whoever needs 2 meals lowers more per bite and reaches 60 more rarely. The sleep duration stays 8 h for everybody. The need shows its full effect only when State2Action is in the database and sleep duration and meals follow `sleepHours` and `mealsPerDay`.

### 13.5 Code Changes

| No. | What | Where | Status |
|---|---|---|---|
| A8 | hunger from sleeping to 0 | `event_influences.csv` | built 2026-11-17 |
| A9 | Linear drive: diagonal 1.0, constant 4 and 3 per step, die ±20 % stays | `AttributeMatrixGenerator` (`HUNGER_DRIVE`, `TIREDNESS_DRIVE`, `Drive.linear`), `AttributeMatrixGeneratorTest` | built 2026-11-17; matrices written with "Matr. DB" in the DCT the same day |
| A10 | Factors 0.775 / 1.7 / 0.33 worked into the CSV once, rounded symmetrically | `event_influences.csv`, note in the head | built 2026-11-17: 40 lines; 15 steps +1 above the band (moving, working) became 0. For the later blocks the factors are applied when the block is written |
| A11 | Columns `sleepHours` and `mealsPerDay`, factors 8 / S (sleep, tiredness) and 3 / M (eating, hunger and tiredness) in the generator | `characters.csv` (all 100, values from `attribute_charaktere.md`, section 5), `AttributeCharacter` (checks 6.0..10.0 in half hours, 1..5), `AttributeCharacterCatalog` (12 or 14 fields), `EventInfluenceRuleBuilder.needFactor`, 6 new tests | built 2026-11-17 |
| A12 | State2Action into the database, sleep duration and meals by need | see `state2action_konzept.md` | project of its own |

### 13.6 Open

1. Is the requirement on sleep understood right: after 16 h awake and 8 h of sleep tiredness is back at the value of the day before?
2. Rise of 4 and 3 per minute (2.4 and 1.8 displayed points per hour)?
3. ~~farmer's wife and maid: raise their own sleep steps?~~ Does not help, it is the band (13.3). Superseded by section 14: threshold E, tiers at [A, E].
4. ~~`sleepHours` and `mealsPerDay` for the 100 characters.~~ In `attribute_charaktere.md`, section 5.
5. A slower rise at night is not possible with SIMPLE (matrix concept 7). For now sleep makes up for the night.

## 14. Ideal per Character: Rested and Ready for Bed, Full and Hungry

Decided and built 2026-11-17: the ideal and dropping the band for tiredness (14.1 to 14.7) and likewise for hunger (14.8). The numbers come from a replica of the formulas (event rules with tiers, need factor and matrix drive +4/+3 per minute, without die and without couplings), not from a run.

### 14.1 Finding: The Narrow Band Becomes a Cage for Tiredness

When drafting the block 11 to 20 the pattern of 13.3 (farmer's wife and maid) appeared more often. Five of the ten characters have tiredness `+`: baker, washerwoman, retired farmer, grandmother and barmaid. Over all 100 there are 24 with `+`, 7 with `++` and 1 with `+++`.

**The night.** Below the band only the tiny sleep step applies. The matrix adds 15 in 300 s. With the baker (8/7, A10 worked in):

| Tier | Sleep step | Matrix | net per event |
|---|--:|--:|--:|
| above the band (≥ 7500) | −117 | +15 | −102 |
| upper half | −97 | +15 | −82 |
| lower half | −69 | +15 | −54 |
| below the band (< 5800) | −10 | +15 | **+5** |

So a balance arises at the lower edge. The night ends at about 58, the rest of the sleep has no effect any more.

**The day.** The matrix knows no band and pushes on beyond the upper edge. In the evening the characters lie at 93 to 99, the grandmother at the stop. The day mean lies at 74 to 80, above the upper edge of `+` (75) and far above the middle (66).

**The consequences for State2Action** (today fixed: sleep from 7000, 8 h). A character with `+` is back at 70 about 6 h after waking and so sleeps about 8 of 14 hours. With `++` and `+++` the lower edge (72 or 90) lies above the threshold, they would sleep without end.

**The reason.** For mood, courage, power, curiosity, morals, materialism and spirituality the middle of the band is the target of the matrix (`AttributeMatrixTargets`). There the band rightly describes where someone mostly stands. hunger and tiredness have **no target**, only the linear drive (13.3), and swing every day by 35 to 50 points. A band of 17 points (`+`) cannot hold that. With a narrow band the day mean lies roughly at "lower edge + half the daily swing", not at the middle. With hunger it shows less, because several meals spread the swing, but it is not solved there either.

**The interpretation (2026-11-17).** tiredness `+` is to mean that someone tires faster and needs more sleep. That is expressed not by a narrow band but by the steps.

### 14.2 The Ideal

Besides `sleepHours` (S) every character has two values, displayed 0..99:

- **A, rested:** tiredness after sleeping S hours
- **E, ready for bed:** tiredness when going to bed

The ideal applies to a **reference day** (work, ways, meals). Whoever has such a day and sleeps S hours goes to bed with E and wakes up with A. If the day differs, the result differs as well. It is an ideal and not a requirement enforced by the system.

### 14.3 Calculation

The matrix brings 3 per minute, that is 1.8 displayed points per hour, awake as asleep. One is awake W = 24 − S hours. Sleep brings 12 · S events (one every 300 s).

| Quantity | Formula | farmer (S 8, 20/70) |
|---|---|---|
| swing | D = E − A | 50 |
| share of the matrix in the day | 1.8 · W | 28.8 |
| strain on the reference day | D − 1.8 · W | 21 points |
| recovery at night | D + 1.8 · S | 64.4 points |
| mean sleep step per event (0..9999) | (100 · D + 180 · S) / (12 · S) | −67 |

The sleep steps of the farmer (−60 / −85 in the middle tiers) fit. The 20/70 was the target for 1 to 10 (8.2, 13.3).

**Limit:** the strain cannot be negative, so D ≥ 1.8 · W. With 8 h of sleep that is a swing of at least 29 points, with 6 h at least 32, with 10 h at least 25.

### 14.4 What the Ideal Can Express

A and E separate two things that `+` used to mix:

| Ideal | Meaning | Consequence for the steps |
|---|---|---|
| 20/70 | reference | |
| 20/85 | tires faster (larger swing) | strain 36 instead of 21 points: larger steps for walking and work (situations 5, 10); sleep has to recover more |
| 35/85 | never quite rested (shifted, same swing) | same strain and same sleep steps as the farmer, only placed higher |

### 14.5 The Four Tiers at [A, E] Instead of the Band (decided 2026-11-17)

For tiredness the generator puts the four tiers (6.3) at **[A, E]**. It no longer uses the band of the level from `characters.csv` for it. The middle is M = (A + E) / 2.

**Why the band is dropped:** The rules are to have only **four intervals**. Band and ideal cannot both be mapped in them, and for the course of the day the ideal is what matters. In theory the band could reach further than [A, E] expresses, e.g. for values one has after a sleepless night or after oversleeping. The rules do not show that extension on purpose.

The roles of the tiers stay the same:
- **below A:** tiny sleep step. Whoever sleeps longer than needed gains nothing, the night ends at A.
- **lower and upper half:** graded steps, stronger in the upper half (deep first, then flatter).
- **above E:** tiny day steps (strain), full sleep step. Only the matrix pushes on.

So every character has an interval exactly as wide as its daily swing. The cage of 14.1 is gone.

For all attributes except tiredness and hunger (14.8) the band stays, because the matrix has a target there (14.1).

### 14.6 Replica: The Interval Alone Is Not Enough

With [A, E] as interval and the steps from the draft 11 to 20:

| Character | S | Ideal | reached 06:00 / 22:00 |
|---|---|---|---|
| farmer | 8 | 20/70 | 20/69 |
| baker | 7 | 30/85 | 35/81 (awake since 05:00) |
| farmer's wife | 8 | 35/85 | 48/90 |
| grandmother | 7 | 40/90 | 64/99 |

Where the steps do not fit the ideal, the character misses it. The grandmother needs −75 per sleep event on average, her "sleeps lightly" brings about −60. The ideal is therefore above all a **target of calculation**: sleep step (14.3) and strain follow from A, E and S. The lines in `event_influences.csv` are written accordingly, and the day calculation checks them.

### 14.7 Relation to State2Action

With State2Action in the database (A12): **sleep from tiredness ≥ E, duration S.** Ideal and behaviour then coincide. The fixed threshold 7000 for everybody is dropped, and with it the endless sleep with `++` and `+++` (14.1).

### 14.8 Hunger the Same Way (decided 2026-11-17)

For hunger the same holds as for tiredness: no target in the matrix, only the linear drive, so the same cage problem. With the bands of the level the day mean lies at 70 for hunger `+` (farmhand, apprentice), at 36 for `-` (miller, innkeeper, baker) (replica). With today's eating threshold 6000 in State2Action the ones would eat almost all the time and the others more rarely than their need (`mealsPerDay`) intends. Over all 100, 39 characters have hunger other than `~`, among them 5 with `++`, 1 with `+++` and 2 with `--`.

**The ideal**, displayed 0..99, together with `mealsPerDay` (M):

- **A_h, full:** hunger after a meal
- **E_h, hungry:** hunger at which he goes to eat

That is the same form as for tiredness: E is the threshold at which one acts, A the value at which the action ends.

**The four tiers at [A_h, E_h]**, for the same reason as in 14.5 (only four intervals, the band is dropped for hunger):
- **below A_h:** tiny eating step. One does not get fuller than full. The meal ends at A_h, however long it lasts.
- **lower and upper half:** graded eating steps.
- **above E_h:** full eating step, strain (walking, work) only tiny.

**Calculation.** The matrix brings 4 per minute, that is 2.4 displayed points per hour, 57.6 a day, awake as asleep. A meal lasts 18 min today with one event every 10 s, that is 108 events.

| Quantity | Formula | farmer (M 3) |
|---|---|---|
| swing per meal | D_h = E_h − A_h | 20 |
| balance over the day | M · D_h = 57.6 + strain − drinking | 3 · 20.1 = 57.6 + 12.6 − 10.0 |
| mean eating step per event (0..9999) | (100 · D_h + 4 · 18) / 108 | −19 |

Replica on the reference day, per day in displayed points:

| Character | M | eating | drinking | walking | work | matrix | per meal |
|---|--:|--:|--:|--:|--:|--:|--:|
| farmer | 3 | −60.2 | −10.0 | +3.0 | +9.6 | +57.6 | 20.1 |
| shepherd | 2 | −61.9 | −8.4 | +3.0 | +9.6 | +57.6 | 30.9 |
| farmhand | 4 | −64.7 | −8.0 | +3.6 | +11.5 | +57.6 | 16.2 |
| miller | 4 | −60.3 | −8.0 | +1.8 | +8.8 | +57.6 | 15.1 |

The eating steps of the farmer (−16 / −23 in the middle tiers) fit D_h = 20.

**Limit:** D_h ≥ (57.6 − drinking) / M, with two drinks about 48 / M. With 1 meal that is a swing of at least 48 points, with 2 at least 24, with 3 at least 16, with 4 at least 12, with 5 at least 10.

**Differences to tiredness:**
1. **Several cycles a day instead of one.** The night is the longest gap without food, one is hungriest before breakfast. With E_h as threshold that is no problem: he wakes up above E_h and eats first (State2Action already checks sleeping before eating). Above E_h the full eating step applies.
2. **Closer tie to M.** With tiredness the strain is free, so D can be chosen. With hunger the strain is small against the matrix (net +2 to +6 after drinking), D_h follows almost entirely from M. What is free is above all the **position**, that is A_h: full at 30 or full at 50. A large swing exists only together with few meals.
3. **Drinking lowers hunger too** (situation 2, thirst is part of hunger), on the reference day by 8 to 10 points. That belongs into the balance.

**Relation to State2Action:** eat from hunger ≥ E_h. How many meals there are per day then follows from the balance. Whoever lives by his ideal eats M times.

### 14.9 Open

1. ~~Where are A and E?~~ Built 2026-11-17 (14.11): four columns in `characters.csv`, displayed values 0..99. For all 100 characters since 2026-11-18.
2. Which reference day? So far: sleep at night, two walks of 30 min, two times 4 h of work with a tool, M meals, two drinks; with S hours of sleep instead of a fixed 8 h.
3. The need factor 8/S (13.4) for tiredness: if the sleep steps are computed from the ideal, it is superfluous. With it a longer night brings no extra recovery, it only shows "slow recovery", not "more load".
4. ~~`sleepHours` and the new meaning of `+`~~ Done 2026-11-18 with the ideals of all 100: `sleepHours` stays, a short need with tiredness `+`/`++` shows in a shifted or wider ideal (e.g. baker 25/80, ascetic 30/85, mad prophet 40/90).
5. The need factor 3/M (13.4) for hunger: the same question as no. 3.

### 14.10 Proposal for the Characters 1 to 20 (2026-11-17)

Values displayed 0..99. The conclusions come from the replica with the tiers already at the ideal intervals. "Strain" is the contribution of walking, work and eating to tiredness on the reference day: needed = D − 1.8 · W, now = what today's steps bring.

**tiredness**

| No. | Character | Level | S | A/E | D (min.) | strain needed / now | sleep step mean | Reason |
|---|---|---|---|---|---|---|---|---|
| 1 | farmer | ~ | 8 | 20/70 | 50 (29) | 21 / 22 | −67 | reference |
| 2 | farmer's wife | + | 8 | 30/85 | 55 (29) | 26 / 15 | −72 | tires faster, never quite catches up |
| 3 | farmhand | ~ | 8.5 | 20/70 | 50 (28) | 22 / 22 | −64 | |
| 4 | maid | + | 8 | 30/85 | 55 (29) | 26 / 17 | −72 | like the farmer's wife |
| 5 | carpenter | ~ | 8 | 20/70 | 50 (29) | 21 / 17 | −67 | |
| 6 | shopkeeper | ~ | 7.5 | 20/70 | 50 (30) | 20 / 21 | −71 | |
| 7 | fisherman | ~ | 7.5 | 20/70 | 50 (30) | 20 / 17 | −71 | |
| 8 | fisherman's wife | ~ | 8 | 30/75 | 45 (29) | 16 / 12 | −62 | sleeps restlessly, only shifted |
| 9 | shepherd | ~ | 8 | 20/65 | 45 (29) | 16 / 20 | −62 | quiet life |
| 10 | miller | ~ | 8 | 20/70 | 50 (29) | 21 / 20 | −67 | |
| 11 | baker | + | 7 | 25/80 | 55 (31) | 24 / 22 | −80 | night work, short nights |
| 12 | washerwoman | + | 8 | 25/85 | 60 (29) | 31 / 19 | −78 | hard work, largest swing |
| 13 | apprentice | ~ | 9 | 15/70 | 55 (27) | 28 / 20 | −66 | young, sleeps deeply |
| 14 | retired farmer | + | 7 | 35/80 | 45 (31) | 14 / 18 | −69 | sits a lot, never quite rested |
| 15 | grandmother | + | 7 | 40/85 | 45 (31) | 14 / 20 | −69 | frail, sleeps lightly |
| 16 | barmaid | + | 7.5 | 25/80 | 55 (30) | 25 / 17 | −76 | late evenings, much on her feet |
| 17 | innkeeper | ~ | 8 | 20/70 | 50 (29) | 21 / 22 | −67 | |
| 18 | carter | ~ | 8 | 20/70 | 50 (29) | 21 / 22 | −67 | |
| 19 | weaver | ~ | 8 | 20/65 | 45 (29) | 16 / 16 | −62 | sits all day |
| 20 | potter | ~ | 8 | 20/70 | 50 (29) | 21 / 22 | −67 | |

**hunger**

| No. | Level | M | A_h/E_h | D_h (min.) | per meal now | eating step mean | Reason |
|---|---|---|---|---|---|---|---|
| 1, 2, 4–8, 12, 14, 16, 18–20 | ~ | 3 | 40/60 | 20 (16) | 19.6–21.0 | −19 | reference, E_h = today's eating threshold 60 |
| 3 farmhand, 13 apprentice | + | 4 | 50/65 | 15 (12) | 15.8–16.0 | −15 | likes food, always hungry |
| 9 shepherd, 15 grandmother | ~ | 2 | 35/65 | 30 (24) | 29.7–31.1 | −28 | two big meals, eats little and seldom |
| 10 miller, 17 innkeeper | - | 4 | 25/40 | 15 (12) | 14.9–15.2 | −15 | well fed, eats with the guests |
| 11 baker | - | 3 | 30/50 | 20 (16) | 20.7 | −19 | |

**Finding hunger:** today's steps already fit. The swing per meal hits D_h within ±1 for all, because the need factor 3/M produces exactly the eating step the ideal demands. New for hunger is only the position, the tiers at [A_h, E_h].

**Decided 2026-11-17: no correction factors per character.** For tiredness today's steps deviate from the ideal with some. farmer's wife, maid, washerwoman, barmaid and apprentice lack 8 to 12 points of strain, retired farmer, grandmother and shepherd have 4 to 6 too many. A factor on the steps does not help because of the rounding. The work steps are tiny since A10 (1/1/1/0), and one unit is 9.6 points on the reference day (960 work events). A factor therefore brings either nothing (×1.4 on 1 gives 1) or too much (×1.5 gives 2). The factors stay as they are (A10, 8/S, 3/M). The deviations are made up later through further actions, because a human does more than eat, sleep and work with tools. Example: for the maid to reach her ideal, she gets more tired than others with certain work. Until then these characters miss their ideal by some points. The ideal stays the target with which these later steps are tuned.

### 14.11 Implementation (built 2026-11-17, committed `2571823f`)

| No. | What | Where |
|---|---|---|
| A13 | Block 11 to 20 into the CSV, 414 lines, A8 and A10 worked in | `event_influences.csv` |
| A14 | Ideal columns `tiredWake;tiredBed;hungerFull;hungerEat` (displayed 0..99), values of 14.10 for 1 to 20. Without the columns 20;70;40;60 applies (all 100 have them since 2026-11-18) | `characters.csv`, `AttributeCharacter` (checks 0 ≤ lower < upper ≤ 99, `hasIdeal`, `getIdealLowest/Highest`), `AttributeCharacterCatalog` (12, 14 or 18 fields) |
| A15 | Tiers for tiredness and hunger at the ideal instead of the band | `EventInfluenceRuleBuilder.build` |

Tests: `EventInfluenceCatalogTest` and `EventInfluenceGeneratorTest` on 20 characters (since extended to 100); new `maidSleeps_…`, `millerEats_…` (generator) and four tests on the ideal in `AttributeArrayGeneratorTest`.

**Replica with the built rules** (reference day, by need, day 5). tiredness 06:00/22:00, the ideal in brackets:

| No. | Character | reached | ideal | | No. | Character | reached | ideal |
|---|---|---|---|---|---|---|---|---|
| 1 | farmer | 20/69 | 20/70 | | 11 | baker | 31/76 | 25/80 |
| 2 | farmer's wife | **46**/88 | 30/85 | | 12 | washerwoman | 36/82 | 25/85 |
| 3 | farmhand | 19/68 | 20/70 | | 13 | apprentice | 14/61 | 15/70 |
| 4 | maid | 36/80 | 30/85 | | 14 | retired farmer | **52/92** | 35/80 |
| 5 | carpenter | 20/64 | 20/70 | | 15 | grandmother | **62/99** | 40/85 |
| 6 | shopkeeper | 20/69 | 20/70 | | 16 | barmaid | 25/70 | 25/80 |
| 7 | fisherman | 20/65 | 20/70 | | 17 | innkeeper | 19/69 | 20/70 |
| 8 | fisherman's wife | 38/78 | 30/75 | | 18 | carter | 20/69 | 20/70 |
| 9 | shepherd | 19/67 | 20/65 | | 19 | weaver | 19/63 | 20/65 |
| 10 | miller | 19/67 | 20/70 | | 20 | potter | 20/69 | 20/70 |

hunger: the characters with 40/60 come to 46 after breakfast and 41 to 43 in the evening. miller and innkeeper (25/40) lie at 34 and 26, farmhand and apprentice (50/65) at 57 to 59 and 51 to 52. Before breakfast all lie above E_h, as expected in 14.8 no. 1.

**Finding:** retired farmer, grandmother and farmer's wife miss their ideal clearly, by 16 to 22 points in the morning. The grandmother stands at the stop in the evening. That is not the few points of 14.10. The cause is their **smaller sleep steps** ("sleeps lightly": raw −4/−25/−40/−50 instead of −5/−35/−50/−60). They date from before the ideal. According to 14.4, light sleep is now expressed by the higher A, not by smaller sleep steps. With retired farmer and grandmother large walking steps come on top.

**Decided and built 2026-11-17:** farmer's wife, retired farmer and grandmother get the sleep steps of the farmer in situation 1 (tiredness −9/−60/−85/−102). The sentences stay, because light sleep is now in the higher A. Replica after that (06:00/22:00, ideal in brackets): farmer's wife 29/73 (30/85), retired farmer 41/85 (35/80), grandmother 49/95 (40/85). The rest fits 14.10: the farmer's wife lacks strain, retired farmer and grandmother tire too much when walking. Both are made up later through further actions. With 7 h of sleep both wake up at 05:00, so by 06:00 an hour of day with breakfast already lies in between.

**Addendum 2026-11-18: measured at waking.** The values "06:00" in this table are measured an hour after waking for 7 h of sleep, and still during sleep for 9 h. Measured at waking (22:00 + S), baker 25 (ideal 25), retired farmer 34 (35) and grandmother 39 (40) sit on their ideal. In the evening the deviation remains: retired farmer 85 (80), grandmother 95 (85). From the block 21 to 30 on, A is measured at waking.

## 15. The Blocks 21 to 100

Status 2026-11-18. Counted by the character number in `characters.csv`: 21 to 100, that is the influence types 20 to 99.

### 15.1 Procedure per Block

1. Propose the ideal (A/E, A_h/E_h) with S, M and a reason.
2. Draft sentences and raw steps for the 25 situations. When the block is written, A8 is checked (hunger from sleeping 0) and A10 is applied (eating hunger ×0.775, sleeping tiredness ×1.7, moving and using an item tiredness ×0.33, rounded symmetrically). Every character gets all 25 situations, all four steps of a line have the same sign. Sleep uses the steps of the farmer for all (raw −5/−35/−50/−60, after A10 −9/−60/−85/−102, see 14.11), except where a larger swing needs more recovery.
3. Write the block as a draft and check it with the day calculation: the tiers of tiredness and hunger at the ideal from `characters.csv` (as in the code), A measured at waking, E at 22:00. Where a character misses its ideal, the steps or the ideal are adjusted.
4. Review by the user, then into `event_influences.csv` and the ideal into `characters.csv`. The tests follow (`EventInfluenceCatalogTest`, `EventInfluenceGeneratorTest`, `AttributeArrayGeneratorTest`).

**Order:** 21 to 60 contain no characters with tiredness `++` or `+++`. For 61 to 100 (66, 73, 78, 89, 91, 97, 98, 99) State2Action should really go into the database first (14.7), otherwise they sleep too often at the fixed threshold 7000. **Decided 2026-11-18:** the blocks are carried through to 100 anyway; the peculiarities in the runs are known and accepted until State2Action is in the database.

**Rounding limit of the strain:** the work on the reference day is 960 events. After the factor 0.33 the work steps can only change in whole numbers, so every raw unit moves the evening tiredness by about 9 points. A large swing such as 25/85 therefore reaches 81 or 89, not 85 (15.3).

### 15.2 Block 21 to 30 (in the CSV since 2026-11-18)

405 lines (without comments), 25 situations each. Changed: mood 147, tiredness 60, hunger 50, courage 41, power 30, almost exactly as with 1 to 10.

**Ideal**

| No. | Character | ti | S | A/E | hu | M | A_h/E_h | Reason |
|---|---|---|---|---|---|---|---|---|
| 21 | shepherdess | ~ | 8.5 | 20/70 | ~ | 3 | 40/60 | |
| 22 | woodcutter | ~ | 9 | 20/70 | + | 4 | 50/65 | like the farmhand |
| 23 | tanner | ~ | 8 | 20/70 | ~ | 3 | 40/60 | |
| 24 | pedlar | ~ | 7.5 | 20/70 | ~ | 3 | 40/60 | used to walking |
| 25 | widow | ~ | 8 | 20/70 | ~ | 2 | 35/65 | first proposed 20/65; because she is weak (power `-`), she tires a little faster when walking, hence 20/70 |
| 26 | beekeeper | ~ | 8 | 20/65 | ~ | 3 | 40/60 | calm |
| 27 | ropemaker | ~ | 8 | 20/70 | ~ | 3 | 40/60 | reference |
| 28 | gardener | ~ | 8 | 20/70 | ~ | 2 | 35/65 | frugal |
| 29 | stable boy | + | 9 | 25/80 | + | 4 | 50/65 | tires fast, always hungry |
| 30 | midwife | + | 7 | 30/85 | ~ | 3 | 40/60 | called out at night, never quite catches up |

**Replica** (reference day, by need, day 5; tiredness at waking and at 22:00, hunger at 08:00 and 20:00; effects of shouting, punch and club in 0..9999):

| No. | Character | tiredness (ideal) | hunger (ideal) | shouted at mood | punch mood/courage/power | club mood/courage |
|---|---|---|---|---|---|---|
| 21 | shepherdess | 19/68 (20/70) | 45/41 (40/60) | −100 | −200 / −30 / −40 | −750 / −225 |
| 22 | woodcutter | 19/68 (20/70) | 57/51 (50/65) | −60 | −100 / +15 / −5 | −600 / −120 |
| 23 | tanner | 20/69 (20/70) | 46/42 (40/60) | −80 | −120 / +5 / −20 | −450 / −375 |
| 24 | pedlar | 19/69 (20/70) | 47/42 (40/60) | −50 | −150 / +5 / −20 | −750 / −120 |
| 25 | widow | 19/69 (20/70) | 39/39 (35/65) | −180 | −300 / −120 / −60 | −900 / −300 |
| 26 | beekeeper | 20/68 (20/65) | 46/42 (40/60) | −50 | −200 / −50 / −40 | −750 / −225 |
| 27 | ropemaker | 20/69 (20/70) | 46/42 (40/60) | −100 | −200 / +5 / −30 | −750 / −375 |
| 28 | gardener | 19/68 (20/70) | 39/39 (35/65) | −100 | −200 / −50 / −40 | −750 / −225 |
| 29 | stable boy | 27/79 (25/80) | 57/51 (50/65) | −50 | −150 / −20 / −40 | −825 / −300 |
| 30 | midwife | 30/82 (30/85) | 48/42 (40/60) | −50 | −150 / +5 / −40 | −600 / −225 |

All hit their ideal for tiredness within ±3. The values of mood and courage at 22:00 are not meaningful in the replica, because the matrix pulls them to their targets; so they are not shown.

**The striking differences**
- **woodcutter (22):** a punch hardly costs him strength (power −5) and makes him braver. He is taciturn: speaking himself changes nothing, being asked is slightly uncomfortable. Work with the axe makes him hungry (hunger `+`).
- **tanner (23):** grumpy. His work spoils his mood (mood −1 per beat), instructions and questions annoy him, understanding leaves him cold. Hardly any regret when striking out.
- **pedlar (24):** talking is his business. Speaking, being asked and understanding lift the mood clearly, taking something pleases him much (materialism `+`). The club makes him angry rather than afraid (courage −120).
- **widow (25):** vulnerable like the maid (`W_WEAK`, `SCREAM_TIMID`, shouted at −180). Being asked comforts her a little.
- **beekeeper (26) and gardener (28):** calm and friendly. Shouting hits the beekeeper only half, running people do not disturb him at all.
- **stable boy (29):** cheeky. He is used to shouting and a clip round the ear (−50, courage only −20), instructions annoy him. He walks and works more than the reference, hence the larger steps for walking and work (raw 10/8/5/1 and 5/4/3/0).
- **midwife (30):** has seen it all. A scream nearby leaves her cold (screams are part of her work), whoever runs may want to fetch her. She does not like instructions, she prefers to give them.

(`W_STD`, `W_WEAK`, `SCREAM` and `SCREAM_TIMID` are common lines for being hit by a weapon and for a scream nearby: standard and timid.)

**Tuned in the replica:** stable boy first with walking 11/9/6/1 and work 6/5/3/0: 29/83, too tired. With 10/8/5/1 and 5/4/3/0 he reaches 27/79.

**Decided 2026-11-18:** beekeeper stays at 20/65, although he ends at 68 in the replica.

**Implementation (A16, 2026-11-18):** block into `event_influences.csv`, ideal into `characters.csv`, as reviewed. Tests: `EventInfluenceCatalogTest` (characters 1 to 30), `EventInfluenceGeneratorTest` (30 characters, repetition at influence type 30, 60, 90; influence type 21 is the woodcutter, not the farmer), `AttributeArrayGeneratorTest` (ideal of the stable boy, default ideal at character 31).

### 15.3 Block 31 to 40 (in the CSV since 2026-11-18)

406 lines (without comments), 25 situations each. Changed: mood 147, tiredness 60, hunger 50, courage 45, power 30.

**Ideal**

| No. | Character | ti | S | A/E | hu | M | A_h/E_h | Reason |
|---|---|---|---|---|---|---|---|---|
| 31 | village smith | ~ | 8.5 | 20/70 | + | 4 | 50/65 | big appetite, like the farmhand |
| 32 | seamstress | ~ | 8 | 20/65 | ~ | 3 | 40/60 | sits at her sewing |
| 33 | cowherd girl | ~ | 9.5 | 15/65 | + | 3 | 45/65 | almost a child, sleeps long and deep; hunger `+` with 3 meals: swing 20 instead of 15, because M = 3 needs at least 16 |
| 34 | day labourer | + | 8 | 25/85 | + | 3 | 45/65 | always on the move, hard work, like the washerwoman |
| 35 | tinker | ~ | 7.5 | 20/70 | ~ | 3 | 40/60 | |
| 36 | brewer | ~ | 8 | 20/70 | - | 4 | 25/40 | well fed, like the innkeeper |
| 37 | charcoal burner | + | 8 | 25/85 | ~ | 3 | 40/60 | watches the kiln, hard work |
| 38 | ferryman | ~ | 8 | 20/65 | ~ | 3 | 40/60 | strong (power `+`), tires slowly |
| 39 | shoemaker | ~ | 8 | 20/65 | ~ | 3 | 40/60 | sits all day, like the weaver |
| 40 | old goatherd | ~ | 7 | 25/75 | ~ | 2 | 35/65 | old, sleeps lightly, climbing tires him |

**Replica** (as 15.2):

| No. | Character | tiredness (ideal) | hunger 08:00/20:00 (ideal) | shouted at mood | punch mood/courage/power | club mood/courage |
|---|---|---|---|---|---|---|
| 31 | village smith | 19/69 (20/70) | 59/52 (50/65) | −60 | −100 / +15 / −5 | −600 / −120 |
| 32 | seamstress | 19/63 (20/65) | 46/42 (40/60) | −120 | −250 / −50 / −50 | −750 / −225 |
| 33 | cowherd girl | 15/62 (15/65) | 70/47 (45/65) | −180 | −300 / −120 / −60 | −900 / −300 |
| 34 | day labourer | 24/81 (25/85) | 54/48 (45/65) | −120 | −200 / +5 / −20 | −750 / −375 |
| 35 | tinker | 19/69 (20/70) | 47/42 (40/60) | −60 | −150 / +5 / −20 | −750 / −375 |
| 36 | brewer | 19/69 (20/70) | 34/26 (25/40) | −100 | −200 / −40 / −30 | −750 / −225 |
| 37 | charcoal burner | 24/81 (25/85) | 48/43 (40/60) | −100 | −200 / +10 / −10 | −750 / −120 |
| 38 | ferryman | 19/63 (20/65) | 46/42 (40/60) | −100 | −200 / +10 / −10 | −750 / −375 |
| 39 | shoemaker | 19/63 (20/65) | 46/42 (40/60) | −120 | −250 / −80 / −40 | −900 / −300 |
| 40 | old goatherd | 24/76 (25/75) | 40/40 (35/65) | −50 | −150 / −50 / −60 | −750 / −225 |

All hit their ideal for tiredness within ±4. With 9.5 h of sleep the cowherd girl wakes up only at 07:30, at 08:00 her breakfast has just begun (hunger 70).

**The striking differences**
- **village smith (31):** strong and brave like the woodcutter (punch power −5, courage +15), and respected: being asked for advice pleases him, instructions annoy him.
- **seamstress (32):** ambitious. Taking something and understanding please her much, instructions and shouting hurt her pride. Weak (power `-`): a punch costs her −50 power.
- **cowherd girl (33):** the child. Understanding is the best thing for her (+50), an answer and being asked please her much. Violence and screams hit her like the maid (`W_WEAK`, `SCREAM_TIMID`). Striking out herself frightens her (courage −).
- **day labourer (34):** poor. Getting something pleases him much (+40), being asked may mean work. Shouting embitters him (−120).
- **tinker (35):** resourceful, not quite honest. Understanding how something works pleases him; hardly any conscience when striking out.
- **brewer (36):** full of life and business-minded. Talking lifts the mood most in the block (+25), taking something as much as with the pedlar.
- **charcoal burner (37):** superstitious. A scream in the forest and someone sneaking make him fear spirits, more than others. Taciturn like the woodcutter.
- **ferryman (38):** money counts (taking something +40). A scream at the river reminds him of the drowned.
- **shoemaker (39):** cautious. Violence, shouting and screams hit him like the widow. Striking out himself frightens him.
- **old goatherd (40):** needs nothing. Getting something and instructions leave him cold, shouting hardly (−50).

**Tuned in the replica:**
- day labourer and charcoal burner: with the steps of the washerwoman (walking 10/8/5/1, work 5/4/3/0) they came to 76. Because of the rounding limit (15.1) the evening tiredness jumps from 81 (work raw 7/6/4/0) to 89 (raw 9/8/5/0), 85 does not exist. Chosen: walking 15/12/8/2, work 7/6/4/0, that is 81, as with the washerwoman (82).
- cowherd girl: with 15/70 she came to 61, even with larger walking steps only to 63. Ideal therefore 15/65.
- ferryman: ideal 20/65 instead of 20/70, he tires slowly.
- old goatherd: ideal 25/75 instead of 25/70, climbing tires him.

**Implementation (A17, 2026-11-18):** block and ideal written as reviewed. Tests: characters 1 to 40, 40 characters with repetition at influence type 40 and 80, influence type 30 is the village smith, default ideal at character 41.

### 15.4 Block 41 to 50 (in the CSV since 2026-11-18)

403 lines (without comments), 25 situations each. Changed: mood 143, tiredness 60, hunger 50, courage 40, power 30. New are attitudes at the edges: morals `++` (priest, sister), morals `--` (moneylender), materialism `++` and `--`.

**Ideal**

| No. | Character | ti | S | A/E | hu | M | A_h/E_h | Reason |
|---|---|---|---|---|---|---|---|---|
| 41 | farmer's son | ~ | 8.5 | 20/70 | + | 4 | 50/65 | young and strong, like the farmhand |
| 42 | farmer's daughter | ~ | 8.5 | 20/70 | ~ | 3 | 40/60 | |
| 43 | market woman | ~ | 7.5 | 20/70 | ~ | 3 | 40/60 | |
| 44 | gatekeeper | + | 9 | 30/75 | + | 4 | 50/65 | drowsy: never quite awake, shifted rather than a larger swing (14.4); waits for his meal |
| 45 | farmhand fond of the jug | + | 8.5 | 35/85 | ~ | 3 | 40/60 | drinks, sleeps badly: never rested |
| 46 | village priest | ~ | 7 | 25/75 | ~ | 2 | 35/65 | short nights (prayer), weak (power `-`), needs little |
| 47 | convent sister | ~ | 7 | 25/75 | ~ | 2 | 35/65 | rises at night to pray and for the sick |
| 48 | rich merchant | ~ | 8 | 20/65 | - | 4 | 25/40 | does not work with his hands, well fed |
| 49 | moneylender | ~ | 7 | 25/70 | - | 3 | 30/50 | sleeps lightly, money under the bed; well fed like the baker |
| 50 | mercenary | ~ | 8 | 20/70 | + | 4 | 50/65 | a soldier eats when he can |

**Replica** (as 15.2):

| No. | Character | tiredness (ideal) | hunger 08:00/20:00 (ideal) | shouted at mood | punch mood/courage/power | club mood/courage |
|---|---|---|---|---|---|---|
| 41 | farmer's son | 19/69 (20/70) | 59/52 (50/65) | −100 | −120 / +15 / −10 | −750 / −120 |
| 42 | farmer's daughter | 19/68 (20/70) | 45/41 (40/60) | −100 | −250 / −50 / −40 | −750 / −225 |
| 43 | market woman | 19/69 (20/70) | 47/42 (40/60) | −50 | −150 / +5 / −30 | −750 / −120 |
| 44 | gatekeeper | 30/75 (30/75) | 57/51 (50/65) | −100 | −200 / +10 / −10 | −750 / −120 |
| 45 | farmhand fond of the jug | 35/84 (35/85) | 45/41 (40/60) | −100 | −200 / +5 / −30 | −750 / −375 |
| 46 | village priest | 24/76 (25/75) | 40/40 (35/65) | −120 | −250 / −50 / −50 | −900 / −225 |
| 47 | convent sister | 24/76 (25/75) | 40/40 (35/65) | −150 | −300 / −120 / −60 | −900 / −300 |
| 48 | rich merchant | 19/66 (20/65) | 34/26 (25/40) | −60 | −250 / −50 / −50 | −750 / −225 |
| 49 | moneylender | 24/70 (25/70) | 38/32 (30/50) | −120 | −250 / −80 / −50 | −900 / −300 |
| 50 | mercenary | 19/69 (20/70) | 60/52 (50/65) | −30 | −60 / +15 / −5 | −450 / +15 |

All hit their ideal for tiredness within ±1.

**The striking differences**
- **farmer's son (41):** wants to leave. An answer and understanding please him much, field work bores him (mood −1 per beat), his father's instructions annoy him.
- **farmer's daughter (42):** newly engaged. Sleep lifts the mood more than with all before (+8), talking about the wedding as well (+25). Striking out makes her sorry (morals `+`).
- **market woman (43):** loud. Talking (+30) and being asked (+40) please her, shouting and screams hardly impress her, a punch makes her angry.
- **gatekeeper (44):** bored. His meal is the high point of the day (mood +12), understanding leaves him cold, instructions disturb his rest.
- **farmhand fond of the jug (45):** the only one whom drinking pleases (mood +5 on top of `DRINK`). Sleep hardly restores his mood.
- **village priest (46):** striking out himself shames him most of all so far (mood −250, morals `++`). Possessions leave him cold (materialism `--`). That someone hits a priest shakes him (club −900).
- **convent sister (47):** the only one besides the maid whom an instruction pleases (obedience is her vow). Striking out shames her like the priest, violence hits her like the maid.
- **rich merchant (48):** profit pleases him very much (+50). He stays cool: shouted at −60. Someone nearby makes him think of his purse.
- **moneylender (49):** getting something pleases him most of all so far (+60). No regret when striking out (morals `--`, no mood line), but fear (courage −). Talking and understanding bring him nothing.
- **mercenary (50):** fighting is his trade. Striking out brings no regret and courage +40. A punch hardly costs him anything (−60), the club makes him more determined (courage +15, like the fisherman). A scream makes him reach for his weapon, not flinch.

**Tuned in the replica:**
- gatekeeper: first walking 8/6/4/1 and work 3/2/2/0: 29/80. With walking 6/5/3/1 ("seldom walks") and work 3/2/1/0 he hits 30/75.
- village priest: ideal 25/75 instead of 25/70 (he tires when walking, like the sister).

**Implementation (A18, 2026-11-18):** block and ideal written as reviewed. Tests: characters 1 to 50, 50 characters with repetition at influence type 50, influence type 49 is the mercenary, default ideal at character 51.

### 15.5 Block 51 to 60 (in the CSV since 2026-11-18)

404 lines (without comments), 25 situations each. Changed: mood 146, tiredness 60, hunger 50, courage 41, power 30. The last block before the characters with tiredness `++`/`+++` (15.1).

**Ideal**

| No. | Character | ti | S | A/E | hu | M | A_h/E_h | Reason |
|---|---|---|---|---|---|---|---|---|
| 51 | knight | ~ | 8 | 20/70 | ~ | 4 | 45/60 | four meals with hunger `~`: swing 15 (at least 12), placed like the reference |
| 52 | pickpocket | ~ | 7.5 | 20/70 | + | 3 | 45/65 | hungry, like the cowherd girl |
| 53 | beggar | + | 8 | 30/85 | ++ | 3 | 55/75 | first with hunger `++`: always hungry, never rested (sleeps outside). Ordinary need (13.4), the ideal is the position |
| 54 | scholar | + | 8 | 35/85 | ~ | 3 | 40/60 | sleeps too little and badly: shifted, like the farmhand fond of the jug |
| 55 | herb woman | ~ | 7.5 | 20/70 | ~ | 2 | 35/65 | |
| 56 | minstrel | ~ | 8 | 20/70 | + | 4 | 50/65 | never full |
| 57 | hunter | ~ | 7.5 | 20/65 | ~ | 3 | 40/60 | tireless |
| 58 | treasure hunter | ~ | 7.5 | 20/70 | ~ | 3 | 40/60 | |
| 59 | tax collector | ~ | 8 | 20/70 | - | 3 | 30/50 | well fed, like the baker |
| 60 | village mayor | ~ | 8 | 20/70 | - | 4 | 25/40 | eats at every invitation, like the innkeeper |

**Replica** (as 15.2):

| No. | Character | tiredness (ideal) | hunger 08:00/20:00 (ideal) | shouted at mood | punch mood/courage/power | club mood/courage |
|---|---|---|---|---|---|---|
| 51 | knight | 19/69 (20/70) | 54/46 (45/60) | −120 | −120 / +15 / −5 | −450 / +15 |
| 52 | pickpocket | 19/69 (20/70) | 53/47 (45/65) | −50 | −150 / −50 / −50 | −750 / −225 |
| 53 | beggar | 30/81 (30/85) | 64/58 (55/75) | −120 | −250 / −80 / −60 | −900 / −300 |
| 54 | scholar | 35/85 (35/85) | 46/41 (40/60) | −150 | −250 / −80 / −100 | −900 / −300 |
| 55 | herb woman | 19/69 (20/70) | 40/40 (35/65) | −100 | −250 / −50 / −50 | −750 / −225 |
| 56 | minstrel | 19/69 (20/70) | 60/51 (50/65) | −50 | −120 / +2 / −50 | −750 / −375 |
| 57 | hunter | 19/62 (20/65) | 47/42 (40/60) | −60 | −120 / +15 / −20 | −600 / +8 |
| 58 | treasure hunter | 19/69 (20/70) | 47/42 (40/60) | −50 | −120 / +15 / −20 | −600 / +8 |
| 59 | tax collector | 20/69 (20/70) | 36/32 (30/50) | −100 | −200 / −50 / −30 | −750 / −225 |
| 60 | village mayor | 19/69 (20/70) | 34/26 (25/40) | −110 | −200 / +5 / −30 | −750 / −120 |

All hit their ideal for tiredness within ±4.

**The striking differences**
- **knight (51):** honourable. The club makes him more determined (courage +15, like mercenary and fisherman), but unlike the mercenary he regrets an unjust blow (mood −50) and feels his honour hurt when shouted at (−120). Being asked for help pleases him.
- **pickpocket (52):** taking something is his life (+50). Honest work bores him, striking out without regret. A scream could be the watch, someone running could be after him. He is quick, not strong: punch power −50.
- **beggar (53):** every meal is a gift (mood +15), being asked means that someone sees him (+30). Violence hits him like the maid.
- **scholar (54):** understanding is his life, the strongest in the block (+60). He is frail (power `--`): club power −300, punch −100.
- **herb woman (55):** understanding how nature works pleases her much (+50). A scream makes her think of spirits and the sick. Striking out goes against her, she heals.
- **minstrel (56):** full of life. Wine pleases him (mood +3 on top of `DRINK`), every meal (+12), talking (+30), being asked for a song (+40). Shouting runs off him (−50).
- **hunter (57) and treasure hunter (58):** fearless. The club makes both rather braver (courage +8), a scream makes them alert, not afraid. Possessions please the treasure hunter much (+60, like the moneylender), an answer could be a clue.
- **tax collector (59):** pedantic and hated. Money pleases him (+50), questions mean complaints and annoy him, talking and understanding leave him cold. He accepts instructions from above: rules are rules.
- **village mayor (60):** self-confident. Making speeches (+20) and being asked (+30) please him, instructions annoy him, a punch makes him angry ("nobody hits the mayor").

**Tuned in the replica:** tax collector first with work 3/2/1/0: 20/64. He goes from house to house and writes; with 3/2/2/0 he hits 20/69.

**Implementation (A19, 2026-11-18):** block and ideal written as reviewed. Tests: characters 1 to 60, 60 characters with repetition at influence type 60, influence type 50 is the knight, default ideal at character 61.

### 15.6 Block 61 to 70 (in the CSV since 2026-11-18)

405 lines (without comments), 25 situations each. Changed: mood 139, tiredness 60, hunger 50, courage 44, power 30. With the miner (66) comes the first character with tiredness `++`.

**Ideal**

| No. | Character | ti | S | A/E | hu | M | A_h/E_h | Reason |
|---|---|---|---|---|---|---|---|---|
| 61 | stingy merchant's widow | ~ | 7.5 | 25/75 | - | 2 | 25/50 | frail (power `--`), well fed with 2 meals (swing at least 24) |
| 62 | seafarer | ~ | 7.5 | 20/70 | ~ | 3 | 40/60 | |
| 63 | scribe monk | + | 8 | 35/85 | ~ | 2 | 35/65 | copies late into the night, rises early to pray: shifted |
| 64 | poacher | ~ | 7.5 | 20/70 | + | 3 | 45/65 | |
| 65 | juggler | ~ | 8 | 20/70 | ~ | 3 | 40/60 | |
| 66 | miner | ++ | 9 | 30/90 | + | 4 | 50/65 | toils underground: largest swing so far (60) |
| 67 | wandering preacher | + | 7 | 30/85 | + | 2 | 45/70 | travels in hardship, often hungry; swing 25 with 2 meals |
| 68 | horse dealer | ~ | 7.5 | 20/70 | ~ | 3 | 40/60 | |
| 69 | wrestler | ~ | 9 | 20/65 | ++ | 5 | 60/72 | strong, tires slowly; eats for three: 5 meals, swing 12 (at least 10) |
| 70 | poet | + | 9 | 30/80 | ~ | 2 | 35/65 | sleeps long and restlessly, wakes up tired |

**Replica** (as 15.2):

| No. | Character | tiredness (ideal) | hunger 08:00/20:00 (ideal) | shouted at mood | punch mood/courage/power | club mood/courage |
|---|---|---|---|---|---|---|
| 61 | stingy merchant's widow | 24/75 (25/75) | 30/27 (25/50) | −150 | −250 / −80 / −100 | −900 / −300 |
| 62 | seafarer | 19/69 (20/70) | 47/42 (40/60) | −30 | −120 / +15 / −10 | −600 / +15 |
| 63 | scribe monk | 34/85 (35/85) | 39/39 (35/65) | −150 | −250 / −80 / −100 | −900 / −300 |
| 64 | poacher | 19/69 (20/70) | 53/47 (45/65) | −60 | −150 / +10 / −20 | −750 / −60 |
| 65 | juggler | 20/69 (20/70) | 46/42 (40/60) | −30 | −120 / +2 / −30 | −750 / −375 |
| 66 | miner | 29/92 (30/90) | 57/51 (50/65) | −100 | −150 / +15 / −5 | −600 / −120 |
| 67 | wandering preacher | 29/81 (30/85) | 51/48 (45/70) | −60 | −100 / +2 / −50 | −450 / −120 |
| 68 | horse dealer | 19/69 (20/70) | 47/42 (40/60) | −30 | −150 / −40 / −30 | −750 / −225 |
| 69 | wrestler | 19/61 (20/65) | 69/61 (60/72) | −30 | −50 / +15 / −3 | −600 / −120 |
| 70 | poet | 30/78 (30/80) | 35/38 (35/65) | −180 | −250 / −80 / −100 | −900 / −300 |

All hit their ideal for tiredness within ±4.

**The striking differences**
- **stingy merchant's widow (61):** getting something pleases her most (+60, like the moneylender), questions mean someone wants money. Frail: club power −300.
- **seafarer (62):** has weathered storms: shouted at −30, the club makes him defiant (courage +15). Telling of his journeys and being asked about them please him much.
- **scribe monk (63):** obedience pleases him slightly (mood +2, like the sister), understanding a text much (+50). Frail like the scholar.
- **poacher (64):** a scream could mean the forester has caught someone, someone running could be his men. Being asked makes him suspicious.
- **juggler (65):** jokes about everything. Instructions leave him cold, shouting hardly (−30), juggling itself pleases him (mood +2 per beat, more than the carpenter).
- **miner (66):** worn out and strong. Largest steps for walking and work in the whole catalogue (raw 15/12/8/2 and 8/7/5/0), plus larger sleep steps (raw −6/−45/−65/−75). A scream reminds him of the collapse in the gallery.
- **wandering preacher (67):** preaching (+30) and being asked about God (+40) fill him. He turns the other cheek: punch only mood −100, club −450.
- **horse dealer (68):** lies without blinking. A deal pleases him much (+60), striking out without regret. Being shouted at by a cheated buyer hardly touches him (−30).
- **wrestler (69):** situation 21 as in example 6.4 for mood (0/0/−50/−50). courage has the steps of the other strong characters (+30/+30/+15/+3) instead of +150/+500/+300/+30, and the curiosity line is dropped: the example dates from before "cause and effect" (7) and before the steps were tuned.
- **poet (70):** melancholic. Shouting hits him like the maid (−180), understanding lifts his gloom for a moment (+40).

**Tuned in the replica:**
- miner: first with work 9/8/5/0: 29/95. With 8/7/5/0 he comes to 29/92.
- wrestler: with 20/70 he came to 62, because he has little strain. Ideal therefore 20/65, he tires slowly.

**With today's sleep rule** (threshold 7000 for everybody) the miner goes to sleep already at 70 instead of 90 and wakes up at about 30, so he sleeps more often. With him that is still fine, because his A (30) lies below 70. Accepted until State2Action (15.1).

**Implementation (A20, 2026-11-18):** block and ideal written as reviewed. Tests: characters 1 to 70, 70 characters with repetition at influence type 70, influence type 68 is the wrestler, default ideal at character 71.

### 15.7 Block 71 to 80 (in the CSV since 2026-11-18)

397 lines (without comments), 25 situations each. Changed: mood 142, tiredness 60, hunger 50, courage 37, power 30. With tiredness `++`: pilgrim woman (73) and alchemist (78).

**Ideal**

| No. | Character | ti | S | A/E | hu | M | A_h/E_h | Reason |
|---|---|---|---|---|---|---|---|---|
| 71 | corrupt town guard | ~ | 8 | 20/70 | ~ | 3 | 40/60 | |
| 72 | barber-surgeon | + | 7 | 30/85 | ~ | 3 | 40/60 | called out at all hours, like the midwife |
| 73 | pilgrim woman | ++ | 8.5 | 25/90 | + | 3 | 45/65 | walks for weeks, her body is worn out: large swing (65) |
| 74 | legacy hunter | ~ | 8 | 20/70 | ~ | 3 | 40/60 | |
| 75 | wounded veteran | + | 9 | 35/85 | ~ | 3 | 40/60 | nightmares: sleeps long but badly, shifted |
| 76 | young widow | + | 7.5 | 25/85 | ~ | 3 | 40/60 | runs the farm alone, work for two, like the washerwoman |
| 77 | estate steward | ~ | 8 | 20/65 | - | 3 | 30/50 | desk work, well fed |
| 78 | alchemist | ++ | 8 | 40/90 | ~ | 3 | 40/60 | forgets to sleep: never rested, shifted most (A = 40) |
| 79 | woodswoman | ~ | 8 | 20/70 | ~ | 2 | 35/65 | |
| 80 | smuggler | + | 7 | 30/85 | ~ | 3 | 40/60 | out at night, short days in bed |

**Replica** (as 15.2):

| No. | Character | tiredness (ideal) | hunger 08:00/20:00 (ideal) | shouted at mood | punch mood/courage/power | club mood/courage |
|---|---|---|---|---|---|---|
| 71 | corrupt town guard | 20/69 (20/70) | 46/42 (40/60) | −100 | −150 / +10 / −20 | −750 / −120 |
| 72 | barber-surgeon | 30/82 (30/85) | 48/42 (40/60) | −50 | −150 / +5 / −30 | −600 / −225 |
| 73 | pilgrim woman | 24/89 (25/90) | 51/47 (45/65) | −100 | −150 / −50 / −50 | −750 / −225 |
| 74 | legacy hunter | 19/70 (20/70) | 46/42 (40/60) | −100 | −250 / −80 / −50 | −900 / −300 |
| 75 | wounded veteran | 34/84 (35/85) | 44/41 (40/60) | −150 | −300 / +5 / −40 | −1050 / −120 |
| 76 | young widow | 24/81 (25/85) | 48/43 (40/60) | −100 | −200 / +5 / −30 | −750 / −120 |
| 77 | estate steward | 19/64 (20/65) | 36/32 (30/50) | −110 | −250 / −50 / −50 | −750 / −225 |
| 78 | alchemist | 40/90 (40/90) | 46/41 (40/60) | −50 | −150 / +5 / −50 | −750 / −120 |
| 79 | woodswoman | 19/68 (20/70) | 39/39 (35/65) | −60 | −120 / +15 / −20 | −600 / +8 |
| 80 | smuggler | 30/82 (30/85) | 48/42 (40/60) | −30 | −120 / +15 / −20 | −600 / +8 |

All hit their ideal for tiredness within ±4.

**The striking differences**
- **corrupt town guard (71):** a bribe pleases him (+40), striking out without regret. Being asked means someone wants a favour.
- **barber-surgeon (72):** like the midwife: screams are part of his work, whoever runs may want to fetch him. Understanding an illness pleases him (+30).
- **pilgrim woman (73):** asked about the shrine (+30), possessions leave her cold (materialism `--`), striking out shames her deeply (−200).
- **legacy hunter (74):** cowardly and greedy. Getting something pleases him much (+60), violence hits him like the maid, striking out without regret, but with fear. Someone nearby could be another heir.
- **wounded veteran (75):** everything that reminds him of the war hits him hard: the club mood −1050 (as much as the grandmother), a scream −80, being asked about the war darkens him. Striking out brings the war back (mood −150). He takes instructions without a word.
- **young widow (76):** does not give up. Shouting makes her angry, not afraid, a punch likewise. She does not let anyone tell her how to run her farm.
- **estate steward (77):** orderly. The only one whom putting things away pleases (situation 9, mood +5). Instructions from his lord are right and proper.
- **alchemist (78):** understanding pleases him most of all (+60, like the scholar), an answer could be the key (+30). Someone nearby could want his secrets.
- **woodswoman (79):** free and fearless. Instructions annoy her more than most (−50), possessions leave her cold, the club makes her rather braver.
- **smuggler (80):** fearless, striking out without regret (courage +30). Whoever runs could be the customs men.

**Tuned in the replica:** pilgrim woman first with walking raw 18/15/10/2 and work 4/3/2/0: 24/77. On the reference day she walks only one hour (120 events with cooldown), more walking steps hardly help (walking 24/20/12/3: 79). Chosen: work 9/8/5/0 ("her body is worn out by the long road, any work with tools tires her quickly"), giving 24/89. The alternative would have been the ideal 25/80 with the first steps: then her large swing would have been kept for the days on the pilgrim road, which the reference day does not show. **Decided 2026-11-18:** the variant with work 9/8/5/0.

**Implementation (A21, 2026-11-18):** block and ideal written as reviewed. Tests: characters 1 to 80, 80 characters with repetition at influence type 80, influence type 74 is the wounded veteran, default ideal at character 81.

### 15.8 Block 81 to 90 (in the CSV since 2026-11-18)

404 lines (without comments), 25 situations each. Changed: mood 134, tiredness 60, hunger 50, courage 48, power 29. From here on the rare marginal figures with levels `+++` and `---`.

**Ideal**

| No. | Character | ti | S | A/E | hu | M | A_h/E_h | Reason |
|---|---|---|---|---|---|---|---|---|
| 81 | gambler | + | 7 | 30/85 | ~ | 3 | 40/60 | plays until dawn |
| 82 | hermit | ~ | 6.5 | 20/70 | + | 1 | 35/85 | one meal: swing 50 (at least 48) |
| 83 | orphan | ~ | 9.5 | 15/65 | ++ | 3 | 55/75 | wide awake, sleeps long like the cowherd girl; always hungry |
| 84 | spoilt young noble | -- | 10 | 10/45 | -- | 4 | 10/25 | well fed and well rested: the only one low; swing 35 (at least 25 at 10 h) |
| 85 | zealous temple servant | ~ | 7 | 25/75 | ~ | 2 | 35/65 | short nights, like the priest |
| 86 | ascetic | + | 6 | 30/85 | ++ | 1 | 45/95 | fasts: one meal, swing 50, very hungry |
| 87 | robber chief | ~ | 7.5 | 20/70 | ~ | 4 | 45/60 | like the knight |
| 88 | giant | ~ | 9.5 | 20/60 | ++ | 5 | 60/72 | hardly tires; eats for five, like the wrestler |
| 89 | paralysed old man | ++ | 10 | 50/90 | ~ | 2 | 35/65 | bedridden: never rested, placed highest (A = 50) |
| 90 | cowardly courtier | ~ | 8.5 | 20/70 | - | 4 | 25/40 | well fed at court |

**Replica** (as 15.2):

| No. | Character | tiredness (ideal) | hunger 08:00/20:00 (ideal) | shouted at mood | punch mood/courage/power | club mood/courage |
|---|---|---|---|---|---|---|
| 81 | gambler | 30/82 (30/85) | 48/42 (40/60) | −30 | −120 / +2 / −50 | −750 / −375 |
| 82 | hermit | 19/71 (20/70) | 84/59 (35/85) | −50 | −100 / −40 / −50 | −750 / −225 |
| 83 | orphan | 15/61 (15/65) | 80/57 (55/75) | −180 | −300 / −120 / −100 | −900 / −300 |
| 84 | spoilt young noble | 10/44 (10/45) | 35/11 (10/25) | −220 | −300 / −80 / −50 | −900 / −300 |
| 85 | zealous temple servant | 24/75 (25/75) | 40/40 (35/65) | −100 | −150 / +10 / −30 | −750 / +8 |
| 86 | ascetic | 29/83 (30/85) | 94/69 (45/95) | −30 | −100 / −40 / −100 | −450 / −90 |
| 87 | robber chief | 20/70 (20/70) | 54/46 (45/60) | −100 | −60 / +20 / −5 | −450 / +23 |
| 88 | giant | 19/58 (20/60) | 85/61 (60/72) | −30 | 0 / +10 / 0 | −375 / −60 |
| 89 | paralysed old man | 50/90 (50/90) | 61/36 (35/65) | −150 | −300 / −120 / −150 | −900 / −300 |
| 90 | cowardly courtier | 20/70 (20/70) | 33/26 (25/40) | −220 | −350 / −150 / −50 | −1050 / −20 |

All hit their ideal for tiredness within ±4. hunger at 08:00: hermit and ascetic eat only once, at 12:00, in the morning they lie just below E_h. orphan and giant wake up only at 07:30 with 9.5 h of sleep, their breakfast has just begun.

**The striking differences**
- **gambler (81):** getting something is like a win (+60). Being shouted at by a loser hardly touches him (−30). Whoever runs could be a creditor.
- **hermit (82):** needs nothing. Talking, an answer and instructions leave him cold, someone nearby disturbs his solitude. He forgives a punch (mood −100).
- **orphan (83):** like a child and a beggar at once: every meal is a feast (+15), being asked means that someone notices him (+40), understanding +50. Weak (power `--`): club power −300.
- **spoilt young noble (84):** bored. Shouting and instructions hurt him most of all (−220 and −100), an answer bores him, work as well (mood −2 per beat). He strikes servants without regret.
- **zealous temple servant (85):** hates questions: being asked annoys him (−50), understanding leaves him cold (curiosity `--`). He strikes out for his god and becomes braver, the club makes him defiant.
- **ascetic (86):** possessions burden him: the only one whom taking something depresses (mood −20). Eating leaves his mood untouched. Striking out is the gravest sin for him (mood −300, morals `+++`). His body is weak from fasting: club power −300.
- **robber chief (87):** ruthless. The only one whom striking out pleases (mood +5) with courage +40. The club makes him wilder (courage +23), a scream leaves him cold. Instructions enrage him.
- **giant (88):** a punch does nothing to him (only courage +10, he laughs). The club hardly hits him (−375).
- **paralysed old man (89):** helpless. Violence hits his power hardest (club −600, punch −150). Being asked means someone visits him (+25); talking is his only pastime.
- **cowardly courtier (90):** flees every danger. Violence, shouting (−220), a scream and someone running or sneaking frighten him like nobody else. Striking out costs him courage (courage −50). Instructions from above please him, he bows.

**Tuned in the replica:**
- paralysed old man: first with large steps for walking (raw 20/16/10/3) and work (9/8/5/0), because every movement exhausts him: 52/99, at the stop. With 50/90 and 10 h of sleep only 15 points of strain remain, and the matrix alone brings 25. The largest share came from eating: the tiredness from eating is multiplied by 3/M = 1.5. Chosen: eating 2/2/1/1, walking 10/8/5/1, work 3/2/2/0, giving 50/90. The sentences now say "costs him effort" instead of "exhausts him". On the reference day he walks and works like everybody else; that he is bedridden is in A = 50.
- giant: ideal 20/60 instead of 20/65, he hardly tires.

**Implementation (A22, 2026-11-18):** block and ideal written as reviewed. Tests: characters 1 to 90, 90 characters with repetition at influence type 90, influence type 87 is the giant, default ideal at character 91.

### 15.9 Block 91 to 100 (in the CSV since 2026-11-18)

403 lines (without comments), 25 situations each. Changed: mood 131, tiredness 60, hunger 50, courage 41, power 30. The last block: after it all 100 characters have rules and an ideal.

**Ideal**

| No. | Character | ti | S | A/E | hu | M | A_h/E_h | Reason |
|---|---|---|---|---|---|---|---|---|
| 91 | mad prophet | ++ | 6 | 40/90 | + | 2 | 45/70 | stays awake at night: never rested, shifted |
| 92 | greedy prince | ~ | 9 | 20/65 | -- | 5 | 10/22 | is carried; five meals, swing 12 (at least 10) |
| 93 | freethinker | ~ | 8 | 20/70 | ~ | 3 | 40/60 | |
| 94 | daredevil | ~ | 7.5 | 20/70 | ~ | 3 | 40/60 | |
| 95 | murderer | ~ | 7 | 20/70 | ~ | 3 | 40/60 | sleeps well, nothing troubles him |
| 96 | sunny nature | - | 7.5 | 15/60 | ~ | 3 | 40/60 | always in good spirits, tires slowly |
| 97 | deeply melancholic woman | ++ | 10 | 45/85 | - | 2 | 25/50 | sleeps long and wakes up tired; eats little, swing 25 (at least 24) |
| 98 | starving refugee | ++ | 8 | 40/90 | +++ | 3 | 75/95 | weeks on the run: never rested, starving, placed highest |
| 99 | exhausted night watchman | +++ | 8 | 50/95 | + | 3 | 45/65 | third night without sleep: placed highest for tiredness |
| 100 | child prodigy | ~ | 9 | 15/65 | ~ | 3 | 40/60 | a child, sleeps long and deep, like the cowherd girl |

**Replica** (as 15.2):

| No. | Character | tiredness (ideal) | hunger 08:00/20:00 (ideal) | shouted at mood | punch mood/courage/power | club mood/courage |
|---|---|---|---|---|---|---|
| 91 | mad prophet | 40/92 (40/90) | 51/47 (45/70) | −30 | −100 / +10 / −50 | −450 / +15 |
| 92 | greedy prince | 19/61 (20/65) | 19/11 (10/22) | −220 | −300 / −100 / −50 | −900 / −300 |
| 93 | freethinker | 20/69 (20/70) | 46/42 (40/60) | −60 | −150 / +5 / −30 | −750 / −120 |
| 94 | daredevil | 19/69 (20/70) | 47/42 (40/60) | 0 | −40 / +20 / −20 | −375 / +23 |
| 95 | murderer | 19/70 (20/70) | 48/42 (40/60) | −20 | −100 / +10 / −20 | −450 / +8 |
| 96 | sunny nature | 14/58 (15/60) | 47/42 (40/60) | −30 | −80 / −20 / −30 | −375 / −120 |
| 97 | deeply melancholic woman | 44/89 (45/85) | 51/26 (25/50) | −150 | −250 / −60 / −50 | −715 / −300 |
| 98 | starving refugee | 40/90 (40/90) | 82/78 (75/95) | −220 | −350 / −150 / −100 | −1050 / −450 |
| 99 | exhausted night watchman | 50/97 (50/95) | 51/47 (45/65) | −150 | −200 / +2 / −30 | −750 / −375 |
| 100 | child prodigy | 14/63 (15/65) | 44/41 (40/60) | −150 | −250 / −30 / −50 | −825 / −225 |

All hit their ideal for tiredness within ±4. With the deeply melancholic woman the club comes out smaller at −715 than `W_WEAK` × 1.5 (−900), because her mood (`---`, 0 to 1000) is already almost at the bottom.

**The striking differences**
- **mad prophet (91):** understanding a sign confirms his fears: the only one whom understanding darkens (mood −25). Proclaiming the end (+30) and being asked about it (+40) fill him. He sees violence as a sign and becomes defiant. A scream darkens him without frightening him.
- **greedy prince (92):** getting something pleases him most of all (+80, materialism `+++`). Instructions and shouting outrage him (−120 and −220), being asked means someone wants something from him.
- **freethinker (93):** understanding (+60) and being asked (+40) please him, an answer leads to the next question (+30). Instructions without a reason make him rebel (−50).
- **daredevil (94):** laughs in the face of danger. Shouting makes him laugh (no effect), a scream excites him (courage +5), the club makes him bolder (courage +23), a punch costs only −40.
- **murderer (95):** cold. Striking out without any stir (no mood line), shouted at −20, a scream leaves him cold, talking and understanding bring him nothing.
- **sunny nature (96):** everything bad hits her only half: punch −80, club −375. Sleep (+10), eating (+12) and work (+2 per beat) lift the mood.
- **deeply melancholic woman (97):** nothing pleases her: sleep does not lift the mood at all, eating hardly (+1), possessions leave her cold ("what for?"). Being asked does her a little good (+10).
- **starving refugee (98):** every bite is a gift (mood +20, the strongest meal of all). Screams and running people remind her of the flight. Violence hits her hardest of all (club mood −1050, courage −450).
- **exhausted night watchman (99):** irritable. Instructions (−50), questions and shouting (−150) irritate him, understanding is too much for his tired head.
- **child prodigy (100):** understanding is its greatest joy, greater than for anyone else (+70). An answer and being asked please it much (+40), eating is a waste of time (+4).

**Tuned in the replica:** exhausted night watchman first with eating 5/5/3/1, walking 10/8/5/1, work 4/3/2/0: 49/99, at the stop. With 50/95, 16 points of strain remain. With eating 3/3/2/1, walking 8/6/4/1, work 3/2/2/0 he comes to 50/97; with these steps it goes no lower.

**With today's sleep rule** (threshold 7000): all four `++`/`+++` characters of this block have A ≤ 50, so they lie below the threshold at waking. But they go to sleep already at 70 instead of at their E. Accepted until State2Action (15.1).

**Implementation (A23, 2026-11-18):** block and ideal written as reviewed. The head of `characters.csv` now says "All 100 characters have it since 2026-11-18". Tests:
- `EventInfluenceCatalogTest`: characters 1 to 100.
- `EventInfluenceGeneratorTest`: the test is now called `generate_writesOneRulePerEventTypeAndInfluenceType_oneCharacterEach`. 100 characters, `isRepeated()` is false; instead of the repetition of the farmer it checks that influence type 99 is the child prodigy.
- `AttributeArrayGeneratorTest`: the default ideal is checked on a line built in the test without ideal columns, because no character is without an ideal any more.

**Generated 2026-11-18:** with "GENERATE ALL RULES" the rules of all 100 characters are in the database. Checked by reading: 19,300 rows in `swpool_eid`, influence types 0 to 99, 12,784 neutral, 6,516 with effect, 100 different rule sets per influence type. The sleep rule of the exhausted night watchman (influence type 98) has its tiers at 5000, 7250 and 9500, that is at his ideal 50/95.

### 15.10 State after All Blocks (2026-11-18)

- `event_influences.csv`: 100 characters × 25 situations. Every influence type 0..99 has its own character, nothing is repeated.
- `characters.csv`: all 100 with an ideal (18 fields).
- Database: the matrices are current (linear drive, "Matr. DB" on 2026-11-17), the EID rules of all 100 characters are generated.
- Balance with the ideal (replica): tiredness within ±5 for all, most within ±2. Larger deviations only with farmer's wife, retired farmer and grandmother (14.11), to be made up later through further actions.
- All 100 rule sets differ, also without the sentences. The closest pairs differ in 3 lines (farmer and ropemaker, baker and gardener) or 4 (midwife and barber-surgeon), all other pairs in at least 5.

**Open:**
1. State2Action into the database: sleep from tiredness ≥ E for S hours, eat from hunger ≥ E_h (14.7, 14.8; `state2action_konzept.md`). Until then the fixed threshold 7000 applies to everybody.
2. ~~A7: couple the influence type to the character (6.1).~~ Built 2026-11-18, see 15.11.
3. 14.9 nos. 2, 3 and 5: reference day and the need factors 8/S and 3/M.

### 15.11 A7: Influence Type = Character of the Attribute Array (built 2026-11-18)

Decided 2026-11-18, without a concept of its own: the influence type follows the character of the attribute array, the same for all event types. Animals (none in the simulation so far) get the rules of the human characters as well. The generator stays unchanged.

- **Where:** `CreateAnimal.initState`, right after the attribute array has been drawn by the gauss index `indexGPAA`. New `CreateAnimal.influenceTypesOfCharacter(int)` (package-private): influence type = |gauss index|, at most 99, the same for each of the 512 event type places. That overwrites what `CreateSimulationObjects.initState` drew from `GaussPoolInfluenceType` before. For items and all other objects `GaussPoolInfluenceType` stays.
- **Why it fits:** the attribute array of the gauss index ±(n − 1) belongs to character n (`AttributeArrayGenerator`), the EID rules to influence type n − 1 (generator). So both sides of the gauss pool lead to the same character. The matrix is already coupled to the array through `GaussPoolAAACMMapping`.
- **Checked:** `swgauss_aa` holds all 199 gauss indices (−99 to 99), there is no gap with test data.
- **Edge case:** `mapGaussToIndex` gives ±100 only for a gauss value of exactly 4.0. Then `GaussPoolAttributeArray` returns no array anyway, the influence type stays at 99.
- **Not affected:** loaded objects (`LoadSimulationObjects`) keep the stored influence types from their table; the commented-out pool code in `LoadHuman` stays as it is.
- **Test:** `CreateAnimalInfluenceTypeTest` (without database, 4 tests).
- **Confirmed in a run (2026-11-18, short):** the simulation starts, "without rule" and "rule error" are 0.
- **Lost variety:** before A7 a human could react to sleep like one character and to a punch like another, drawn at random and unrelated to its attribute array. That variety could not be balanced. Variety can come back later on this consistent base: own rules for the two variants of a character (positive and negative gauss index), conditions on the current value of another attribute (disposition, section 7), or a meaning for the intensity.

**Display (2026-11-18):** the dashboard shows the type of the selected human in the detail column ("Typ 4 (InfluenceType 3)"). The button "Typen" opens a window with the list of humans and their types and, switchable, the number of humans per type that occurs.

**Distribution in two runs with 100 humans:** 42 and 47 different types (expected 43), 70 and 65 humans with type 1 to 25 (expected 68), highest type 75 and 77. Because `mapGaussToIndex` cuts off instead of rounding, index 0 covers the same width as +k and −k together: the farmer is as frequent as the other common types (about 3 of 100), not more frequent. From type 61 on, only about 1.8 humans are to be expected among 100, from type 86 on about 0.01 each. The rare characters therefore practically never appear in a run of 100 humans.
