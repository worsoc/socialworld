# Attributes from Characters and Natural Text

## 1. Basics

| Point | Detail |
|---|---|
| Range | **0 to 99 inclusive** (`AttributeArray.ATTRIBUTE_VALUE_MAX = 99`, `set()` raises values below 0 to 0). The middle is `ATTRIBUTE_VALUE_MIDDLE = 50`. |
| Order | mood, courage, morals, materialism, tiredness, curiosity, spirituality, hunger, power |
| `power` | Physical strength: `Action2PerformerDescriptionPool` multiplies the intensity of kicking, pulling and pushing by it. |
| States and traits | `tiredness`, `hunger` and partly `mood` are states that change during the simulation. courage, morals, materialism, curiosity, spirituality and power are traits. |
| Use | `CreateAnimal.initState` draws a Gaussian value, `mapGaussToIndex` turns it into an index from −99 to +99, and `GaussPoolAttributeArray` returns the array for it. **Index 0 is drawn most often, ±99 least often.** |
| Database | `swpool_attribute (aa_id, attrib_nr 1..9, value)` and `swgauss_aa (gauss_index, aa_id)` |

## 2. Concept: The Character Sets the Bands, the Text Picks the Value

```
character (e.g. "village smith")
   └─ one level per attribute (e.g. power = ++)
         └─ level → band (72–92)
               └─ text → value inside the band (e.g. 81)
```

Three layers work together, and none of them aims at a uniform distribution:

1. **The character** decides where a person lies for each attribute. This keeps the result interpretable.
2. **The text** decides the exact value inside the band. Its letters are not evenly distributed, and that is intended: the natural irregularity of a language shapes the values.
3. **The Gaussian pool** decides how often a character appears in the simulation.

### 2.1 Levels and Bands

The bands overlap on purpose, so there are no hard edges.
Values are attribute values 0..9999; the game shows them divided by 100 (0..99).

| `---` | extremely low | 0–1000 |
| `--` | very low | 800–2800 |
| `-` | low | 2500–4200 |
| `o` | average | 3800–6200 |
| `+` | high | 5800–7500 |
| `++` | very high | 7200–9200 |
| `+++` | extremely high | 9000–9999 |
| `~` | open (a state; the text decides almost alone) | 2000–8000 |

`~` is meant for mood, tiredness and hunger when the character says nothing special about them.

### 2.2 Text as a Natural Die

1. Take a long natural text (for example a public-domain novel), write it in lower case and keep only letters (ä→a, ö→o, ü→u, ß→s).
2. Read every **third** letter, so that common letter pairs such as "en" or "ch" do not repeat in the result.
3. Each letter gets its position in the alphabet as a share: `s(letter) = index / 25` (a = 0, z = 1).
4. For one attribute, take `k = 3` of these letters and average them: `f = (s1 + s2 + s3) / 3`.
5. The value is `lo + round((hi − lo) · f)`. For `---` and `+++` one letter is enough (`k = 1`), because the band is narrow anyway.

**What this does to the distribution:**

- Letters such as e, n, i, s, r, t and a are much more frequent than q, x or y. The die is therefore loaded, and it is loaded the way the language is.
- In German the frequent letters lie partly in the first and partly in the second half of the alphabet. Single values therefore have irregular, language-specific peaks instead of a flat shape.
- Averaging three letters makes the middle of the band more likely than its edges, while the peaks of the language stay visible.
- A different text, or a text in a different language, gives a different flavour. That is part of the idea.

One array needs 9 attributes × up to 3 letters × every third letter, so about 81 letters of text. For the 199 arrays of the 100 characters exactly 15,865 letters are needed, about 20,000 characters of German prose or eight to ten book pages. Spaces, punctuation and digits do not count.

The same text, read from the same position, always gives the same values. To repeat a result, note the text and the starting position.

### 2.3 Mapping to the Gaussian Pool

- The characters are sorted by frequency: **no. 1 is the most ordinary person, no. 100 the rarest.**
- No. n gets `gauss_index +(n−1)` **and** `−(n−1)`, each with its own draw from the text. So 100 characters give 199 arrays, and no. 1 fills only index 0.
- As a result, everyday people appear often in the simulation and extreme ones rarely. This frequency comes from the order, not from the values.

### 2.4 Workflow in the DCT

The controls are arranged in this order around the `attributes` list:

```
Attribute:  [Charaktere]
attributes list
[Übernehmen]
aa_id ab: [  1 ]  [In DB]
```

1. The characters are read from `src/main/resources/org/socialworld/tools/dct/characters.csv` (no;name;sketch;9 level codes). The table in section 3 shows the same data.
2. Put a text into the text input: paste it, or load a `.txt` file with "Text laden" (top right, below "Generieren"). The file is read as UTF-8, or as ISO-8859-1 if it is no valid UTF-8. A message shows how many letters it has and how many are needed (15,865 for the 100 characters).
3. Button "Charaktere" draws one array for character no. 1 and two for every other character and shows them in the `attributes` list with number, name and gauss_index, e.g. `31 village smith [30] (52,61,47,…)`.
4. Optional, adjusting by hand:
   1. Click an entry in the list: its values go into the nine sliders.
   2. Move the sliders.
   3. Press "Übernehmen": the entry and the array that "In DB" writes get the slider values (at most 99). Number, name and gauss_index stay.

   Clicking another entry before "Übernehmen" loads its values into the sliders, and the adjustment is lost. The bands of the character do not limit an adjustment.
5. Set the first aa_id ("aa_id ab:", default 1). The arrays use 199 aa_ids from there.
6. Button "In DB" asks for confirmation and writes the arrays with `TablePoolAttribute` and `TableGaussAA`, record by record. Because `swgauss_aa` refers to `swpool_attribute`, each step runs for all arrays before the next one starts:
   1. delete from `swgauss_aa`
   2. delete from `swpool_attribute`
   3. insert into `swpool_attribute`
   4. insert into `swgauss_aa`

   Records of other aa_ids stay as they are.

## 3. The 100 Characters

Columns: **mo** mood · **co** courage · **mr** morals · **ma** materialism · **ti** tiredness · **cu** curiosity · **sp** spirituality · **hu** hunger · **pw** power

### A. Everyday People (no. 1–45, gauss_index 0 to ±44, about two thirds of all draws)

| No | Character | Sketch | mo | co | mr | ma | ti | cu | sp | hu | pw |
|--:|---|---|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|
| 1 | farmer | middle-aged, steady and hard-working | ~ | o | o | o | ~ | o | o | ~ | + |
| 2 | farmer's wife | mother of four, practical, always on her feet | ~ | o | + | o | + | o | o | ~ | o |
| 3 | farmhand | strong, unambitious, likes his food | ~ | o | o | - | ~ | - | o | + | + |
| 4 | maid | diligent, pious, quiet | ~ | - | + | - | + | o | + | ~ | o |
| 5 | carpenter | reliable craftsman | ~ | o | o | o | ~ | o | o | ~ | + |
| 6 | shopkeeper | good at sums, hears all the gossip | ~ | o | o | + | ~ | + | o | ~ | - |
| 7 | fisherman | weather-beaten, makes offerings to the sea | ~ | + | o | o | ~ | o | + | ~ | + |
| 8 | fisherman's wife | mends nets, waits on the shore | ~ | o | o | o | ~ | - | + | ~ | o |
| 9 | shepherd | much alone, content | + | o | o | - | ~ | o | + | ~ | o |
| 10 | miller | a little stingy, well fed | ~ | o | - | + | ~ | o | o | - | + |
| 11 | baker | gets up at night, cheerful | + | o | o | o | + | o | o | - | o |
| 12 | washerwoman | hard work, little time for anything else | ~ | o | o | - | + | - | o | ~ | o |
| 13 | apprentice | young, curious, always hungry | ~ | o | o | o | ~ | + | - | + | o |
| 14 | retired farmer | has handed over the farm, sits a lot | ~ | - | + | o | + | - | + | ~ | - |
| 15 | grandmother | tells stories, frail | + | - | + | - | + | o | + | ~ | -- |
| 16 | barmaid | quick, hears a lot, believes little | + | o | o | o | + | + | - | ~ | - |
| 17 | innkeeper | sociable, eats with the guests | + | o | o | + | ~ | + | - | - | o |
| 18 | carter | often on the road, not easily scared | ~ | + | o | o | ~ | o | o | ~ | + |
| 19 | weaver | patient, sits all day | ~ | - | o | o | ~ | o | o | ~ | - |
| 20 | potter | likes to try new shapes | ~ | o | o | o | ~ | + | o | ~ | o |
| 21 | shepherdess | young, sings while working | + | o | o | - | ~ | + | o | ~ | o |
| 22 | woodcutter | bear of a man, taciturn | ~ | + | o | - | ~ | - | o | + | ++ |
| 23 | tanner | smelly, grumpy, believes in nothing | - | o | o | o | ~ | - | - | ~ | + |
| 24 | pedlar | talkative, not too strict with the truth | + | + | - | + | ~ | + | - | ~ | o |
| 25 | widow | withdrawn, prays a lot | - | - | + | o | ~ | - | + | ~ | - |
| 26 | beekeeper | calm, watches nature | + | o | + | o | ~ | + | + | ~ | o |
| 27 | ropemaker | unremarkable in every way | ~ | o | o | o | ~ | o | o | ~ | o |
| 28 | gardener | frugal, friendly | + | o | o | - | ~ | o | o | ~ | o |
| 29 | stable boy | tired, hungry, cheeky | ~ | o | o | - | + | o | - | + | o |
| 30 | midwife | has seen it all, fetches children at night | ~ | + | + | - | + | o | + | ~ | o |
| 31 | village smith | strong, respected, big appetite | ~ | + | o | o | ~ | o | o | + | ++ |
| 32 | seamstress | ambitious, wants to move to town | ~ | o | o | + | ~ | + | o | ~ | - |
| 33 | cowherd girl | almost still a child, asks about everything | + | - | o | - | ~ | ++ | o | + | - |
| 34 | day labourer | always on the move, always short of money | - | o | o | + | + | o | - | + | o |
| 35 | tinker | travelling, resourceful, not quite honest | ~ | o | - | o | ~ | + | - | ~ | o |
| 36 | brewer | business-minded, full of life | + | o | o | + | ~ | o | o | - | o |
| 37 | charcoal burner | lives in the forest, taciturn, superstitious | - | + | o | - | + | - | + | ~ | + |
| 38 | ferryman | takes anyone across who pays | ~ | o | o | + | ~ | - | + | ~ | + |
| 39 | shoemaker | cautious, sticks to his last | ~ | - | o | o | ~ | o | o | ~ | o |
| 40 | old goatherd | needs nothing, at peace with himself | + | o | o | -- | ~ | - | + | ~ | - |
| 41 | farmer's son | wants to leave and see the world | ~ | + | o | o | ~ | ++ | - | + | + |
| 42 | farmer's daughter | newly engaged, in good spirits | + | o | + | o | ~ | o | o | ~ | o |
| 43 | market woman | loud, bargains hard | + | + | o | + | ~ | + | - | ~ | o |
| 44 | gatekeeper | bored, drowsy, waiting for his meal | - | o | o | o | + | - | o | + | + |
| 45 | farmhand fond of the jug | drinks, sleeps badly | - | o | - | o | + | - | - | ~ | o |

### B. Distinctive People (no. 46–85, gauss_index ±45 to ±84)

| No | Character | Sketch | mo | co | mr | ma | ti | cu | sp | hu | pw |
|--:|---|---|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|
| 46 | village priest | strict in morals, needs little | ~ | o | ++ | -- | ~ | o | ++ | ~ | - |
| 47 | convent sister | humble, serves the sick | + | - | ++ | -- | ~ | - | ++ | ~ | - |
| 48 | rich merchant | well travelled, coolly calculating | ~ | o | - | ++ | ~ | + | - | - | - |
| 49 | moneylender | suspicious, knows no mercy | - | - | -- | ++ | ~ | o | - | - | - |
| 50 | mercenary | fights for whoever pays | ~ | ++ | - | + | ~ | o | - | + | ++ |
| 51 | knight | proud of his honour, god-fearing, strong | ~ | ++ | + | o | ~ | o | + | ~ | ++ |
| 52 | pickpocket | quick, cheeky, hungry | + | + | -- | + | ~ | + | - | + | - |
| 53 | beggar | has lost everything, hopes for mercy | - | - | o | -- | + | o | + | ++ | - |
| 54 | scholar | grown grey over books, sleeps too little | ~ | - | + | - | + | ++ | o | ~ | -- |
| 55 | herb woman | knows about plants and spirits | ~ | o | + | - | ~ | ++ | ++ | ~ | - |
| 56 | minstrel | merry, reckless, never full | ++ | + | - | - | ~ | ++ | - | + | - |
| 57 | hunter | tireless, fearless in the forest | ~ | ++ | o | - | ~ | + | o | ~ | + |
| 58 | treasure hunter | greedy for gold and adventure | + | ++ | - | ++ | ~ | ++ | - | ~ | + |
| 59 | tax collector | hated, pedantic | - | o | - | ++ | ~ | - | - | - | o |
| 60 | village mayor | self-confident, minds his own advantage | ~ | + | o | + | ~ | o | o | - | o |
| 61 | stingy merchant's widow | counts every coin | - | - | o | ++ | ~ | - | o | - | -- |
| 62 | seafarer | has seen distant coasts | + | ++ | o | o | ~ | ++ | + | ~ | + |
| 63 | scribe monk | copies texts by candlelight | ~ | - | ++ | -- | + | + | ++ | ~ | -- |
| 64 | poacher | cunning, risks a lot for a roast | ~ | + | - | o | ~ | o | - | + | + |
| 65 | juggler | jokes about everything, the gods included | ++ | + | o | - | ~ | + | -- | ~ | o |
| 66 | miner | toils underground, worn out and strong | - | + | o | o | ++ | - | + | + | ++ |
| 67 | wandering preacher | travels in hardship, burns for his faith | + | + | + | -- | + | o | ++ | + | - |
| 68 | horse dealer | cunning, charming, lies without blinking | + | o | -- | ++ | ~ | o | - | ~ | o |
| 69 | wrestler | strong, cheerful, eats for three | + | + | o | o | ~ | - | - | ++ | ++ |
| 70 | poet | melancholic, unworldly | -- | - | + | -- | + | ++ | + | ~ | -- |
| 71 | corrupt town guard | holds out his hand | ~ | o | -- | + | ~ | - | - | ~ | + |
| 72 | barber-surgeon | heals, cuts, sleeps little | ~ | + | + | o | + | + | o | ~ | o |
| 73 | pilgrim woman | walks for weeks to the shrine | + | o | + | -- | ++ | + | ++ | + | - |
| 74 | legacy hunter | flatters the old | ~ | - | -- | ++ | ~ | o | - | ~ | - |
| 75 | wounded veteran | has seen too much | -- | + | + | - | + | - | o | ~ | - |
| 76 | young widow | runs the farm alone and does not give up | - | + | + | o | + | o | o | ~ | o |
| 77 | estate steward | orderly, petty | ~ | o | o | + | ~ | - | o | - | - |
| 78 | alchemist | seeks gold and forgets to sleep | ~ | + | o | + | ++ | ++ | - | ~ | - |
| 79 | woodswoman | free, wild, owns nothing | + | ++ | o | -- | ~ | + | + | ~ | + |
| 80 | smuggler | out at night, fears nothing | ~ | ++ | -- | + | + | o | - | ~ | o |
| 81 | gambler | on top of the world, never enough | ++ | + | - | ++ | + | + | - | ~ | - |
| 82 | hermit | lives on roots, prays | + | o | + | -- | ~ | - | ++ | + | - |
| 83 | orphan | neglected, hungry, wide awake | - | - | o | - | ~ | ++ | o | ++ | -- |
| 84 | spoilt young noble | well fed, well rested, bored | + | - | - | ++ | -- | o | - | -- | - |
| 85 | zealous temple servant | knows only his god, hates questions | - | + | + | -- | ~ | -- | ++ | ~ | o |

### C. Rare and Extreme People (no. 86–100, gauss_index ±85 to ±99)

| No | Character | Sketch | mo | co | mr | ma | ti | cu | sp | hu | pw |
|--:|---|---|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|
| 86 | ascetic | fasts, owns nothing, is held to be holy | + | o | +++ | --- | + | - | +++ | ++ | -- |
| 87 | robber chief | ruthless, fearless, strong | ~ | +++ | --- | ++ | ~ | o | -- | ~ | ++ |
| 88 | giant | show fighter at the fair | + | + | o | - | ~ | - | - | ++ | +++ |
| 89 | paralysed old man | bedridden, tired, composed | - | -- | + | - | ++ | o | + | ~ | --- |
| 90 | cowardly courtier | crawls upwards, flees every danger | ~ | --- | -- | ++ | ~ | + | - | - | -- |
| 91 | mad prophet | sees the end coming and stays awake at night | -- | ++ | o | --- | ++ | + | +++ | + | - |
| 92 | greedy prince | wants everything, gives nothing | ~ | o | -- | +++ | ~ | - | - | -- | - |
| 93 | freethinker | doubts every god, questions everything | + | + | + | o | ~ | +++ | --- | ~ | o |
| 94 | daredevil | reckless, laughs in the face of danger | ++ | +++ | - | o | ~ | ++ | - | ~ | + |
| 95 | murderer | cold, without remorse | - | + | --- | + | ~ | - | --- | ~ | + |
| 96 | sunny nature | always in good spirits, whatever happens | +++ | + | + | - | - | + | o | ~ | o |
| 97 | deeply melancholic woman | sees no sense in anything | --- | - | + | - | ++ | -- | o | - | - |
| 98 | starving refugee | has been on the run for weeks | -- | - | o | -- | ++ | - | + | +++ | -- |
| 99 | exhausted night watchman | third night without sleep | - | o | o | o | +++ | - | o | + | o |
| 100 | child prodigy | grasps everything, wants to know everything | + | + | + | - | ~ | +++ | o | ~ | - |

## 4. Check of the Distribution

Counted over all 900 entries (100 characters × 9 attributes):

| Levels | Count | Share |
|---|--:|--:|
| `o` and `~` | 398 | 44 % |
| `+` and `-` | 376 | 42 % |
| `++` and `--` | 105 | 12 % |
| `+++` and `---` | 21 | 2 % |

Because `+` (58–75) and `-` (25–42) overlap with `o`, most values still lie between 30 and 70. The Gaussian weighting (section 2.3) makes the extreme characters even rarer in the simulation. Nobody has 0 everywhere or 99 everywhere.
