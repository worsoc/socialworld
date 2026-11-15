# Event-Einfluss-Regeln (EID): sinnvollere Regeln statt Indexrechnung

Stand 2026-11-15 nachmittags, Branch `dev/claude0002`. Reines Konzept, noch nichts gebaut.

Das Konzept beruht auf dem Lesen des Codes. Die Datenbank wurde nicht abgefragt. Welche Rollen (`self`, `target`, `candidates`, `percipient`) wen erreichen, ist seit 2026-11-11 im Code geprüft (Abschnitt 5.1).

## 1. Ausgangslage

Der Knopf „MASS GENERATE 19.300 RULES“ in `tools/dct/CreationTool_EventInfluenceDescription` (`runMassGeneration()`) schreibt für jede Kombination aus EventType (193 ohne `nothing`) und InfluenceType (0..99) eine Regel über `TablePoolEID` in die Tabelle. Vorher leert er die Tabelle (`table.clear()`), auch von Hand angelegte Regeln.

Alle Regeln sind gleich gebaut:

- Bedingung immer `mood >= 5000`
- Aktion immer `MX+N` mit `"basis;m;n"`
- Ziel Y = `(et + infl) % 9`, Basis X = `(Ziel + 5) % 9`. Es gibt also nur 9 feste Paare aus Ziel und Basis, z. B. `mood` immer aus `curiosity`.
- M = 0.50 bis 1.99, N = 0 bis 1990, beide aus den Indizes gerechnet und immer positiv

Die Regeln sind ein Füllbestand ohne Inhalt. Welcher Event welches Attribut verändert, folgt nur aus Indexrechnung.

## 2. Was die Auswertung heute kann

Die Übersetzung der JSON-Regel geschieht in `calculation/expressions/ChangeAttribute.initFromJson`.

| Punkt | Befund |
|---|---|
| MX+N | `Y := clamp(M·X + N, 0, 9999)`. Y wird **überschrieben**. Ist X ein anderes Attribut, ist der alte Wert von Y weg |
| X = Y | relative Änderung von Y, siehe Abschnitt 4 |
| Sonst | ein dritter Term wird als Sonst-Zweig ausgewertet. Der Generator nutzt das bisher nicht |
| mehrere Attribute | `attributeChanges` ist eine Liste, die Einträge laufen nacheinander |
| Bedingung | nur ein Attribut gegen eine Konstante |
| Funktionen | im JSON-Weg nur `MX+N` |
| Event-Eigenschaften | z. B. die Intensität kommen in MX+N nicht an |
| Funktionsplätze | jedes MX+N belegt einen Platz in `functions` (10000 bis 99999) |

**Serie von Einzeländerungen:** Für verschiedene Attribute nacheinander funktioniert sie, z. B. erst tiredness, dann hunger. Für ein Attribut aus zwei verschiedenen Quellen funktioniert sie nicht: Jedes MX+N überschreibt Y, also gewinnt der letzte Eintrag.

**InfluenceType heute:** `GaussPoolInfluenceType` mischt die Zahlen 0..99 für jeden EventType zufällig. Ein Mensch bekommt also für jeden EventType eine unabhängige Zahl. Typ 37 bei Schlaf hat nichts mit Typ 37 bei einem Faustschlag zu tun.

## 3. Entschieden

| Thema | Entscheidung |
|---|---|
| Übersicht | Die Tabelle der Grundwirkungen (Abschnitt 5) ist eine Übersicht für uns, keine DB-Tabelle |
| InfluenceType | richtet sich nach den 100 vorhandenen Charakteren (`AttributeCharacterCatalog`, `characters.csv`): InfluenceType = Charakternummer − 1 |
| Vorrang | Die Bedeutung des Events und seine Wirkung auf den Menschen stehen im Vordergrund. Das Band des Charakters gibt nur den groben Rahmen (Abschnitt 6.2) |
| Form | vorerst absolute Änderungen (MX+N mit M = 1), weil das durchschaubar ist. Andere Formen später, mit weiteren Funktionen außer MX+N |
| pro Charakter | Für jeden Charakter und jede Situation gibt es einen Satz in natürlicher Sprache und daraus abgeleitete Schritte, gestaffelt in vier Stufen am Band (Abschnitt 6.2 und 6.3) |
| Situationen | Die 85 relevanten EventTypes werden zu Situationen gebündelt, Varianten bekommen Faktoren (Abschnitt 7) |
| Quelle | eine CSV-Datei, die der Massengenerierknopf liest (Abschnitt 8) |
| Format | JSON. Zum alten Zeilen-Format kehren wir nicht zurück |
| Ablage | Die Regeln kommen wieder in die EID-Tabelle, über den Massengenerierknopf und `TablePoolEID` |
| Umfang | erster Schritt nur mit MX+N. Die Regeln sollen besser sein als vorher, noch nicht perfekt |
| später | Die Intensität bekommt irgendwann eine Bedeutung. Es kommen weitere Funktionen außer MX+N |

## 4. Rechnen mit MX+N: fester Schritt und Annäherung

Beide Formen nutzen X = Y, also das Zielattribut selbst als Basis.

**Fester Schritt:** M = 1, N = Schrittweite. Bei `tiredness;1.00;-300.00` sinkt tiredness um 300 (3 angezeigte Punkte).

**Annäherung an ein Ziel Z:** Z ist kein Attribut, sondern ein fester Zielwert, den wir pro Regel festlegen. Bei Anteil a gilt:

- M = 1 − a
- N = a·Z

Beispiel: Nach dem Schlaf soll tiredness auf Z = 2000 zugehen, a = 10 %. Dann M = 0.9, N = 200, der Formeltext ist `tiredness;0.90;200.00`.

Y bleibt stehen bei Y = M·Y + N, also bei Y = N / (1 − M) = 200 / 0.1 = 2000 = Z. Jede Anwendung verkleinert den Abstand zu Z um 10 %:

| tiredness vorher | nachher |
|---|---|
| 9000 | 0.9·9000 + 200 = 8300 |
| 8300 | 7670 |
| 2000 | 2000 (bleibt) |
| 500 | 650 (steigt auf 2000 zu) |

**Achtung:** Die Annäherung zieht auch Werte unterhalb des Ziels hoch, wie die letzte Zeile zeigt. Soll eine Regel nur senken, passt ein fester Schritt mit negativem N besser. Alternativ setzt man eine Bedingung davor, z. B. `tiredness > 2000`.

**Vorteil der Annäherung:** Kein Wert klebt dauerhaft an 0 oder 9999.

**Entschieden 2026-11-11:** Vorerst nur der feste Schritt. Vor dem Festkleben schützen die kleinen Schritte außerhalb des Bands (Abschnitt 6.3). Die Annäherung bleibt hier als Möglichkeit für später stehen.

## 5. Übersicht der Grundwirkungen (Entwurf)

### 5.1 Wer welches Event bekommt (im Code geprüft 2026-11-11)

Attribute werden nur für Tiere und Menschen umgerechnet (`StateAnimal.calculateEventInfluence`). Gegenstände bekommen Events zwar, aber keine Attributänderung.

| Rolle | wer das Event bekommt | Quelle |
|---|---|---|
| `self…` | der Handelnde selbst | `EventMaster.determineInfluenceToCauser` |
| `target…` | was `getTargets()` der Aktion liefert, meist ein **Gegenstand** | `EventToTarget.getTargetObjects` |
| `candidates…` | alle im Wirkradius und Winkel des Events, **also auch der Getroffene** | `EventMaster.determineCandidates` |
| `percipient…` | alle in Reichweite | `EventMaster.determinePossiblePercipients` |

Was `target` bei den einzelnen Familien ist:

| Familie | target | für Attribute |
|---|---|---|
| Eat, Drink | der Gegenstand (`ActionBodilyFunction.getItem`) | ohne Bedeutung |
| Inventory | der Gegenstand (`Equip.getTargets`) | ohne Bedeutung |
| HandleItem, Touch | die Gegenstände 1 und 2 (`Handle.getTargets`) | ohne Bedeutung |
| Weapon | die **Waffe** (`Attack.getTargets`), nicht das Opfer | ohne Bedeutung |
| Punch | ohne Waffe vermutlich leer | ohne Bedeutung |
| Examine | leer (`Examine.getTargets`) | ohne Bedeutung |
| Ask, Answer | der angesprochene Mensch (`Ask.getTargets`) | **zählt** |

**Wirkprüfung:** Für Touch (152, 153), Pull und Push (173, 174) sowie Ask und Answer (200 bis 205) wird zusätzlich nach Kandidaten gesucht (`EventType.isRelevantForEffectiveCheck`). Die Umstehenden bekommen dann dasselbe Event mit dem **`target`-Typ**.

**Tatsächlich erzeugt werden:**

| Gruppe | Anzahl | erreicht Menschen und Tiere |
|---|---|---|
| `self…` | 55 | ja |
| `targetAsk…`, `targetAnswer…` | 6 | ja: Angesprochener und Umstehende |
| `candidatesMove…` | 6 | ja |
| `candidatesWeapon…` | 7 | ja: das Opfer und wer sonst im Winkel steht |
| `candidatesPunch…` | 6 | ja: wie Weapon |
| `candidatesSay…` | 3 | ja |
| `percipientExistsDistance5000`, `…1000` | 2 | ja (`SimulationObject.letBePerceived`) |
| alle übrigen `target…` | 33 | nein, nur Gegenstände |
| alle übrigen `candidates…` (23) und `percipient…` (52) | 75 | nein, nur in `EventTypeGeneral` genannt, von keiner Aktion erzeugt |

Von 193 Typen zählen also heute **85**. Die übrigen 108 bekommen eine neutrale Regel (5.4).

Seit 2026-11-15 kommen die 6 percipientMove-Typen dazu, die `ActionMove` künftig erzeugt (Abschnitt 7.2). Es zählen dann **91**.

**Häufigkeit:** Die Exists-Events haben die Priorität `AMBIENT`. `AttributeCalculator` lässt davon pro Objekt höchstens eins pro Sekunde durch, also bis zu 3600 pro Stunde. Ein fester Schritt von 100 würde ein Attribut in etwa 100 Sekunden von der Mitte an den Rand schieben. Exists darf deshalb nur sehr schwach wirken oder vorerst gar nicht (Abschnitt 7, Situation 24).

### 5.2 Wirkungen auf Menschen und Tiere

Seit 2026-11-11 nur noch **Orientierung für die typische Richtung**. Bindend ist die Tabelle pro Charakter (Abschnitt 6.2 und 8).

Zeichen: ↑↑ stark, ↑ mittel, (↑) leicht, ± hängt vom Charakter ab. Attribute ohne Eintrag bleiben unverändert.

**self (55 Typen)**

| Familie | Varianten | Wirkung |
|---|---|---|
| **Sleep** | – | tiredness ↓↓, mood (↑), hunger (↑) |
| **Drink / Eat** | Eat stärker als Drink | hunger ↓↓, mood (↑), Eat: tiredness (↑) |
| **Piss / Shit** | – | mood (↑) |
| **Move** | Walk (↑), Sneak ↑, Jump ↑, Fly ↑, Run ↑↑, Swim ↑↑ | tiredness nach Variante, Run und Swim: hunger (↑) |
| **Examine** | Look, Smell, Taste, Touch | curiosity ↓, Taste: hunger (↑) |
| **Touch** | Hand, Foot | curiosity (↓) |
| **Inventory** | Take und Get (+), Drop (−), Switch und Set (neutral) | materialism (↑), mood (↑) |
| **HandleItem** | Use…, Add…, Pull und Push (schwerer) | tiredness (↑), Pull und Push: tiredness ↑ |
| **Weapon** | Stab, Stroke, Backhand, Club (am stärksten) | tiredness ↑, courage (↑) |
| **Punch** | 6 Varianten, schwächer als Weapon | tiredness (↑), courage (↑) |
| **Listen / Understand** | Statement, Question, Instruction, Understand | Statement: curiosity (↓), Question: curiosity (↑), Instruction: mood (↓), Understand: curiosity ↓ und mood (↑) |
| **Ask** | Normal, Scream (×1.5), Whisper (×0.5) | curiosity (↓) |
| **Answer** | wie Ask | – |
| **Say** | wie Ask | mood (↑), Scream: tiredness (↑) |

**target bei Ask und Answer (6 Typen), gilt für den Angesprochenen und die Umstehenden**

| Familie | Wirkung |
|---|---|
| **Ask** | Normal: mood (↑), Scream: mood ↓ und courage (↓), Whisper: curiosity ↑ |
| **Answer** | curiosity ↓ |

**candidates (22 Typen)**

| Familie | Varianten | Wirkung |
|---|---|---|
| **Move** | Walk, Sneak, Jump, Swim, Fly, Run | Run und Sneak: curiosity (↑), Sneak: courage ±, sonst – |
| **Weapon** | wie self, Club am stärksten | mood ↓↓, power ↓, courage ± |
| **Punch** | wie self, schwächer als Weapon | mood ↓, power (↓), courage ± |
| **Say** | Normal, Scream, Whisper | Normal: mood (↑), Scream: mood ↓ und courage (↓), Whisper: curiosity ↑ |

**percipient (2 Typen)**

| Familie | Varianten | Wirkung |
|---|---|---|
| **Exists** | Distance5000, Distance1000 (näher, etwas stärker) | courage ±, curiosity (↑), vorerst mit Schritt 0 (Abschnitt 7, Situation 24) |

### 5.3 Offene Punkte zur Übersicht

- Weil die Umstehenden bei Ask und Answer denselben `target`-Typ bekommen, wirkt eine Frage auf sie genauso wie auf den Angesprochenen. Für den ersten Schritt nehmen wir das hin, oder wir halten die Wirkung bei Ask und Answer so klein, dass es nicht stört.
- Bei Weapon und Punch trifft die Wirkung jeden im Winkel und Radius, nicht nur das gemeinte Opfer.
- Wer einem Kampf nur zusieht, bekommt heute kein Event, weil `percipientWeapon…` und `percipientPunch…` nicht erzeugt werden.

### 5.4 Neutrale Regel für die übrigen 108 Typen

Noch zu klären, bevor gebaut wird:

- Eine Regel mit leerer `attributeChanges`-Liste ergibt `Nothing`. `AttributeCalculator` zählt das vermutlich als Fehler.
- Eine Regel `Y := 1.0·Y + 0` ändert nichts, belegt aber pro Regel einen Funktionsplatz. Bei 108 × 100 Regeln sind das 10.800 der 90.000 Plätze.
- Ganz ohne Regel zählt `AttributeCalculator` ebenfalls einen Fehler (`counterErrors_Event`).

### 5.5 Nebenfunde (nicht Teil dieses Konzepts)

- `EventType.isRelevantForChangingPosition()` liefert immer `true`. In den Abfragen steht `||` statt `&&`, z. B. `index >= 8 || index <= 13`.
- `Attack.getTargets()` liefert die Waffe statt `ActionAttack.target`. Ob das Absicht ist, ist offen.

## 6. InfluenceType und Charaktere

Die Charaktere stehen in `src/main/resources/org/socialworld/tools/dct/characters.csv`: Nummer, Name, Skizze und eine Stufe pro Attribut. Gelesen werden sie über `AttributeCharacterCatalog`.

### 6.1 Zuordnung

**InfluenceType = Charakternummer − 1**, gleich für alle EventTypes.

- Beide Seiten haben 100 Einträge und sind nach Häufigkeit geordnet. Nr. 1 („farmer“) ist der gewöhnlichste Charakter, ab Nr. 86 kommen die seltenen Randfiguren.
- `AttributeArrayGenerator` vergibt schon heute den Gauss-Index „Charakternummer − 1“: ein Array für Nr. 1, je zwei (positiv und negativ) für alle anderen. InfluenceType = |Gauss-Index| passt also.
- Dafür muss die Zuteilung geändert werden. Heute würfeln `CreateAnimal` (Attribut-Array) und `CreateSimulationObjects.initState` (InfluenceType) getrennt. Das ist ein eigener, späterer Schritt (Abschnitt 11).

### 6.2 Regeln pro Charakter und Situation

Entschieden 2026-11-11:

- Für **jeden Charakter** wird für **jede Situation** (Abschnitt 7) festgelegt, wie sie auf ihn wirkt.
- **Zuerst kommt ein Satz in natürlicher Sprache**, der die Wirkung beschreibt. Daraus leiten wir ab, welche Attribute sich wie ändern.
- Die Änderungen sind **absolut**: MX+N mit X = Y, M = 1 und N = Schritt.
- Die Schritte sind **pro Charakter verschieden**, und innerhalb eines Charakters noch einmal nach dem aktuellen Wert gestaffelt (6.3).
- Die Bedeutung des Events für diesen Menschen steht im Vordergrund. Das Band des Charakters ist nur der grobe Rahmen.
- Das ergibt eine lange Tabelle. Dafür steht am Ende alles schwarz auf weiß.

### 6.3 Vier Stufen am Band

Das Band eines Charakters für ein Attribut ist [U, O] (`AttributeLevel`). Die Mitte ist H = (U + O) / 2. Für jedes betroffene Attribut stehen vier Schritte in der Tabelle:

| Stufe | Bereich | typisch |
|---|---|---|
| unter dem Band | Y < U | sehr kleiner Schritt |
| untere Hälfte | U ≤ Y < H | gestaffelt |
| obere Hälfte | H ≤ Y < O | gestaffelt |
| über dem Band | Y ≥ O | sehr kleiner Schritt |

Die Bänder:

| Stufe | `---` | `--` | `-` | `o` | `+` | `++` | `+++` | `~` |
|---|---|---|---|---|---|---|---|---|
| Band | 0–1000 | 800–2800 | 2500–4200 | 3800–6200 | 5800–7500 | 7200–9200 | 9000–9999 | 2000–8000 |

Die sehr kleinen Schritte außerhalb des Bands übernehmen die Bremse. Der Puffer von 1500 aus dem vorigen Entwurf entfällt.

**Nur eine Stufe greift.** Jede Stufe wird ein eigener Eintrag in `attributeChanges`, mit einer Bedingung für die Unter- und die Obergrenze. Weil wir wissen, in welche Richtung ein Attribut sich ändert, legen wir die Reihenfolge so fest, dass nur ein Eintrag greift:

- **Y sinkt:** Die Einträge laufen von der untersten Stufe zur obersten. Ein Wert, der in einer Stufe gesenkt wurde, fällt höchstens in eine Stufe, die schon geprüft ist.
- **Y steigt:** Die Einträge laufen von der obersten Stufe zur untersten, aus demselben Grund.
- Deshalb haben alle vier Schritte einer Zeile **dasselbe Vorzeichen** (oder 0).

Voraussetzung: eine Bedingung mit Unter- und Obergrenze (Abschnitt 9).

**Verfeinerung (bestätigt 2026-11-15):** Sehr klein ist der Schritt nur auf der Seite, auf der das Event vom Band **wegführt**. Auf der Seite, auf der es ins Band **zurückführt**, wirkt es normal. Beispiel Schlaf: Unter dem Band von tiredness gibt es einen winzigen Schritt, über dem Band (übermüdet) den vollen.

### 6.4 Beispiel: 69 wrestler, „geschlagen werden“

Stufen des Ringers: mood `+`, courage `+`, curiosity `-`, power `++`.

> The wrestler is used to fighting. A punch leaves him calm, if anything it spurs him on.

(Der Ringer ist Kampf gewohnt. Ein Faustschlag lässt ihn gelassen, er wird eher angestachelt.)

| Attribut | Band | < Band | untere Hälfte | obere Hälfte | > Band | gemeint |
|---|---|---|---|---|---|---|
| mood | 5800–7500 | 0 | 0 | −50 | −50 | bleibt fast gleich |
| courage | 5800–7500 | +150 | +500 | +300 | +30 | Kampfeslust |
| curiosity | 2500–4200 | +20 | +200 | +100 | +20 | wach, will wissen, wer da schlägt |

morals ändert sich nicht, denn ein Schlag macht ihn weder besser noch böser. power ändert sich auch nicht, denn Motivation bringt keine körperliche Kraft.

### 6.5 Bedeutung der Attribute (vorläufig)

Für die Sätze und Werte gilt vorerst:

| Attribut | Bedeutung |
|---|---|
| morals | gut gegen böse. Ist noch nicht endgültig, die Entscheidung dazu steht aus |
| courage | Mut, auch Kampfeslust |
| curiosity | Neugier. Zusammen mit courage ergibt sich die Motivation |
| power | körperliche Kraft. Sie steigt nicht durch Motivation |

Die Bedeutung steckt nur in den Sätzen und Werten der Tabelle, nicht im Code. Ändert sie sich, wird nur die Tabelle angepasst.

**Einordnung der Attribute (bestätigt 2026-11-15):** „Grundsätzlich“ heißt weniger beweglich, nicht konstant.

| Rang | Attribut | Art | ändert sich durch |
|---|---|---|---|
| 1 | hunger | Zustand des Körpers, sehr beweglich | essen, trinken, Anstrengung, Zeit |
| 2 | tiredness | Zustand des Körpers, sehr beweglich | schlafen, Anstrengung, Zeit |
| 3 | mood | Zustand des Gemüts, beweglich | Erlebnisse, die einem selbst widerfahren oder die man mit ansieht: Schmerz, Lob, Anschreien, Gewalt gegen andere |
| 4 | power | Verfassung des Körpers, träge | Verletzung senkt sie, Ruhe stellt sie langsam wieder her |
| 5 | courage | Verfassung, eher träge | Bedrohung, Angriff, Bestehen einer Gefahr. Schwankt kurz, kehrt aber zum Wesen zurück |
| 6 | curiosity | Wesenszug | einzelne Events praktisch nicht. Sie prägt, wie jemand auf Neues reagiert |
| 7 | materialism | Haltung | einzelne Events praktisch nicht. Sie prägt, wie sehr Besitz freut oder schmerzt |
| 8 | morals | Haltung, gut gegen böse | einzelne Events praktisch nicht, höchstens später sehr schwere eigene Taten. Sie prägt, wie jemand Unrecht erlebt |
| 9 | spirituality | Haltung, am grundsätzlichsten | durch unsere Events gar nicht |

Drei Gruppen: **Zustand** (1 bis 3) ändert sich durch Events direkt. **Verfassung** (4 und 5) ändert sich nur durch starke Events. **Wesen und Haltung** (6 bis 9) ändern sich durch einzelne Events nicht und wirken als Disposition (Abschnitt 7).

### 6.6 Überholt oder verworfen

- **Fester Schritt, für alle Charaktere gleich** (Entwurf vom Nachmittag): überholt. Die Schritte sind jetzt pro Charakter und Stufe verschieden.
- **Rahmen = Band ± 1500 als Bedingung:** überholt. Die vier Stufen übernehmen das.
- **±-Einträge mit Richtung nach der Stufe des Charakters, `o` und `~` mit halbem Schritt:** überholt. Jeder Charakter bekommt seine Richtung ausdrücklich in der Tabelle.
- **Das Band als Ziel einer Annäherung:** verworfen. Dabei hing die Stärke vom Abstand zur Grenze ab statt von der Bedeutung des Events. Die Annäherung bleibt als Möglichkeit für später (Abschnitt 4).
- **Empfindlichkeit aus der Seltenheit:** verworfen. Die Extreme stecken schon in den Bändern.
- **Aufteilung `it = 10·a + b`** aus dem ersten Entwurf im Chat: verworfen.

## 7. Situationen und Faktoren

Die 85 EventTypes, die Menschen und Tiere erreichen (Abschnitt 5.1), werden zu Situationen gebündelt. Varianten innerhalb einer Situation bekommen einen Faktor auf die Schritte. Gerundet wird auf ganze Zahlen.

Beim Bündeln gilt eine Regel: Kehrt eine Variante die Richtung um oder trifft sie andere Attribute, bekommt sie eine eigene Situation. Ein Faktor allein reicht dann nicht. Deshalb sind z. B. „angeschrien werden“ und „gefragt werden“ getrennt.

| Nr. | Situation | EventTypes | Faktoren | darf sich ändern | prägt die Reaktion (Disposition) |
|---|---|---|---|---|---|
| 1 | schlafen | selfSleep | – | tiredness ↓, hunger ↑, mood (↑), power (↑, Erholung) | – |
| 2 | trinken | selfDrink | – | hunger ↓ (Durst), tiredness (↓) | – |
| 3 | essen | selfEat | – | hunger ↓, mood (↑), tiredness (↑) | materialism (Genuss) |
| 4 | ausscheiden | selfPiss, selfShit | je 1 | mood (↑) | – |
| 5 | sich bewegen | selfMove Walk, Sneak, Jump, Fly, Run, Swim | Walk 1, Sneak 1.2, Jump 1.5, Fly 1.5, Run 2, Swim 2 | tiredness ↑, hunger (↑) | power (wie schnell es ermüdet) |
| 6 | untersuchen | selfExamineBy Look, Smell, Taste, Touch | je 1 | nichts. Das Ergebnis ist Wissen, das läuft über die Wahrnehmung | curiosity |
| 7 | berühren | selfTouchBy Hand, Foot | je 1 | nichts | curiosity |
| 8 | etwas an sich nehmen | selfInventory Take, Get | je 1 | mood (↑) | materialism (wie sehr es freut) |
| 9 | etwas ablegen oder umräumen | selfInventory Drop, Switch, Set | Drop 1, Switch 0.5, Set 0.5 | nichts | – |
| 10 | Gegenstand benutzen | selfHandleItem Use2, UseLeft, UseRight, AddRtoL, AddLtoR, Pull, Push | Use… und Add… 1, Pull und Push 1.5 | tiredness ↑, hunger (↑) | power |
| 11 | angreifen | selfWeapon… (7), selfPunch… (6) | Weapon 1, Club 1.5, Backhand 0.8, Punch 0.6 | tiredness ↑, mood ±, courage ± | morals (Schuld zeigt sich in mood), courage |
| 12 | zuhören | selfListenTo Statement, Question | je 1 | nichts. Der Inhalt zählt, der kommt erst mit der Intensität | curiosity |
| 13 | eine Anweisung hören | selfListenToInstruction | – | mood ± | morals, courage (fügt sich oder sträubt sich) |
| 14 | verstehen | selfUnderstand | – | mood (↑) | curiosity (wie sehr es befriedigt) |
| 15 | selbst sprechen | selfAsk…, selfAnswer…, selfSay… (je Normal, Scream, Whisper) | Normal 1, Scream 1.5, Whisper 0.5 | mood (±) | – |
| 16 | gefragt werden | targetAsk Normal, Whisper | Normal 1, Whisper 0.5 | mood ± | curiosity |
| 17 | eine Antwort bekommen | targetAnswer Normal, Whisper | Normal 1, Whisper 0.5 | mood (±) | curiosity |
| 18 | angeschrien werden | targetAskScream, targetAnswerScream | je 1 | mood ↓, courage (↓) | courage, morals |
| 19 | jemand bewegt sich wirklich nah (möglicherweise bedrohlich) | candidatesMove Walk, Sneak, Jump, Fly, Run, Swim | Walk 1, Sneak 1.5, Jump 1, Fly 1.5, Run 1.5, Swim 1 | mood ±, courage (±) | courage |
| 20 | von einer Waffe getroffen werden | candidatesWeapon… (7) | Stab, Stroke 1, Backhand 0.8, Club 1.5 | mood ↓↓, power ↓ (Verletzung), courage ± | courage |
| 21 | geschlagen werden | candidatesPunch… (6) | je 1 | mood ↓, power (↓), courage ± | courage |
| 22 | jemand spricht in der Nähe | candidatesSay Normal, Whisper | Normal 1, Whisper 0.5 | nichts | curiosity |
| 23 | jemand schreit in der Nähe | candidatesSayScream | – | mood (↓), courage (↓) | courage |
| 24 | jemand ist da | percipientExistsDistance 5000, 1000 | vorerst Schritt 0 (Abschnitt 5.1) | nichts | – |
| 25 | sieht jemanden sich bewegen | percipientMove Walk, Sneak, Jump, Fly, Run, Swim (neu, Abschnitt 7.2) | Walk 0, Swim 0, Jump 0, Fly 0, Run 1, Sneak 1 | nur bei Run und Sneak: mood (↓) oder courage (↓), Unruhe | courage |

**Ursache und Wirkung (entschieden 2026-11-15):** Ein Event ändert nur die Attribute, auf die es wirklich wirkt. Einen Apfel zu sehen macht nicht satter, ihn zu essen schon. Die grundsätzlicheren Attribute (Abschnitt 6.5) ändern sich durch einzelne Events praktisch nicht. Sie wirken als **Disposition**: Sie bestimmen, wie stark und in welche Richtung ein Event die beweglichen Attribute ändert. Zu sehen, wie jemand ein Kind schlägt, macht wütender (mood ↓). Wie sehr, hängt von morals ab, morals selbst ändert sich dabei nicht.

Vorerst steckt die Disposition im Charakter: Sein Satz und seine Schritte folgen aus seinen Stufen. Den **aktuellen** Wert eines anderen Attributs abzufragen, etwa „wenn curiosity > 7000, dann mood +“, kann der Bedingungsterm grundsätzlich. Es verträgt sich aber nicht mit den vier Stufen am eigenen Attribut und ist auf später verschoben.

Die Liste ist für den Moment akzeptiert und wird später weiter unterschieden. Beispiel: Es gibt Berührungen, die die Stimmung verändern.

Varianten, die nichts bewirken sollen, bekommen den Faktor 0. So gelten bei Situation 25 die Zeilen nur für Run und Sneak.

Das ergibt 25 Situationen × 100 Charaktere = 2500 Sätze, mit je einer Zeile pro betroffenem Attribut.

Die Übersicht 5.2 bleibt als **Orientierung für die typische Richtung** stehen. Bindend ist die Tabelle pro Charakter.

### 7.1 Abstände zwischen den Events einer Aktion

**Befund (Code, 2026-11-15):** `ActionHandler.doActualAction` ruft `perform()` einer laufenden Aktion wiederholt auf, und jeder Aufruf erzeugt die Events neu. Den Abstand bestimmt heute `getSecondsTillNextCalculation()` aus der Restdauer:

- mehr als 600 s: √Rest
- 60 bis 600 s: ⁴√Rest
- bis 60 s: bei jedem Durchlauf

Wie viele Events eine Aktion bringt, ist damit kaum vorherzusagen. Ein Schritt in der Tabelle gilt aber pro Event.

**Entschieden 2026-11-15:** Statt zu zählen, legen wir **feste Abstände pro Aktion** fest. Von der Staffelung nach der Restdauer verabschieden wir uns. Dann ist die Zahl der Events pro Aktion bekannt, und jeder Schritt lässt sich deuten: Wirkung pro Event × Anzahl der Events.

Der Abstand gehört zum `ActionMode`, nicht zum EventType. Ein Aufruf von `perform()` erzeugt die Events aller Rollen gemeinsam. **Einmal** heißt: Das Event entsteht einmal beim Start, danach läuft nur noch die Dauer ab.

| Situation | ActionMode | Abstand |
|---|---|---|
| 1 schlafen | sleep | 300 s (8 h ergeben 96 Events). Später eventuell drei Schlaf-Events mit eigenen Takten |
| 2 trinken | drink | 2 s |
| 3 essen | eat | 10 s |
| 4 ausscheiden | piss, shit | einmal |
| 5, 19 sich bewegen, jemand bewegt sich in der Nähe | walk, run, sneak, swim, fly | 1 s |
| | jump | einmal |
| 6 untersuchen | look, smell, taste, touch | 5 s |
| 7 berühren | hand, foot | einmal |
| 8, 9 Inventar | takeItem, getItemFromInventory, dropItem, switchItemToOtherHand, setItemToInventory | einmal |
| 10 Gegenstand benutzen | useTwoItems, useItemLeftHand, useItemRightHand, combineItems_AddRightToLeft, combineItems_AddLeftToRight | 2 s |
| | pull, push | 2 s |
| 11, 20, 21 Kampf | weapon… (7), punch… (6) | einmal. Mehrere Schläge sind mehrere Aktionen |
| 12–14 zuhören, Anweisung, verstehen | listenTo…, understand | einmal |
| 15–18 sprechen, gefragt werden, Antwort, angeschrien werden | ask…, answer… | einmal |
| 15, 22, 23 sprechen, jemand spricht oder schreit in der Nähe | sayNormal, sayScream, sayWhisper | einmal |
| 24 jemand ist da | keine Aktion | siehe unten |
| 25 sieht jemanden sich bewegen | walk, run, sneak, swim, fly, jump (wie 5) | wie 5, gedrosselt durch den Cooldown (7.2) |

**Bewegung:** `PositionCalculator` versetzt das Objekt pro Event um die Stärke des Events und rechnet die Zeit nicht ein. Der Takt bestimmt also die Geschwindigkeit. Deshalb bleibt Move bei einem kurzen festen Takt von 1 s.

**Exists (24):** Die Events entstehen nicht aus einer Aktion, sondern reihum aus `ObjectMaster.perceiveNextObject`. Ihre Häufigkeit regelt der Cooldown (Abschnitt 7.2).

**Jemand bewegt sich in der Nähe (19):** kein Cooldown, das Event kommt im Sekundentakt (Abschnitt 7.2).

**Was das für die Tabelle heißt:**
- Bei „einmal“ ist der Schritt die ganze Wirkung, z. B. ein Faustschlag mood −600.
- Bei einem Takt ist der Schritt die Wirkung pro Takt, z. B. Schlaf tiredness −60 alle 5 Minuten, also bis zu −5760 in 8 Stunden, wenn keine Stufe bremst.

**Umsetzung (eigener Schritt):** `ActionHandler.getSecondsTillNextCalculation()` liefert den festen Abstand des `ActionMode`. Bei „einmal“ gibt es bis zum Ende der Dauer keinen weiteren Aufruf von `perform()`. Ob die Abstände fest am `ActionMode` stehen oder in einer kleinen Tabelle, ist noch zu entscheiden.

### 7.2 Nähe, Sehen und Cooldowns

**Entschieden 2026-11-15:**

- **candidatesMove… (Situation 19)** heißt „jemand bewegt sich **wirklich nah**, möglicherweise bedrohlich“. Das Event feuert im Takt der Aktion, also jede Sekunde, und hat **keinen Cooldown**. candidates-Typen haben die Priorität `CRITICAL` und werden nie gedrosselt.
- Die Reichweiten in `EventType.getEffectDistance()` bleiben vorerst, wie sie sind. Feinjustiert wird später.
- **Bewegung sehen** läuft über **percipientMove…** mit Cooldown. Dafür kommt **Situation 25 „sieht jemanden sich bewegen“** dazu (6 Typen). Es zählen dann 91 EventTypes statt 85.
- Dafür muss `ActionMove` zusätzlich ein `EventToPercipient` erzeugen. Das ist heute nicht der Fall: `new EventToPercipient` steht nur in `SimulationObject.letBePerceived` (Exists). Die Empfänger in Reichweite findet `EventMaster.determinePossiblePercipients` schon.

**Wie der Cooldown heute arbeitet (Code, 2026-11-15):**

- `TickObjectCooldown` führt eine Matrix: eine Zeile pro Objekt, eine Spalte pro Cooldown-Art (`TYPE_…`, heute 8 Arten).
- `checkAndApplyCooldown(objectId, art, sekunden)` öffnet ein Fenster von `sekunden` Länge und lässt darin höchstens `getMaxAllowedForType(art)` Events durch.
- `AttributeCalculator.calculateAttributesChangedByEvent` drosselt percipient-Events mit drosselbarer Priorität. `AMBIENT` läuft über `TYPE_PERCEPTION_AMBIENT` (1 s, 1 Event), `DYNAMIC` über `TYPE_PERCEPTION` (1 s, 5 Events). `CRITICAL` wird nicht gedrosselt.
- Der Cooldown betrifft nur die Wirkung auf die Attribute. Wahrnehmung (`calculatePerception`) und Reaktion (`reactToEvent`) sind davon nicht betroffen.

**Gruppen nach Kritikalität (entschieden 2026-11-15):** Die Dauer eines Cooldowns bildet ab, wie kritisch eine Wahrnehmung ist. Deshalb werden die Gruppen **nach Kritikalität** gebildet, nicht nach der Bedeutung des Events. Jede Stufe bekommt eine eigene Cooldown-Art mit einem festen Fenster.

Folgen:
- Alle EventTypes einer Stufe haben dasselbe Fenster. Dass das erste Event die Länge setzt, spielt damit keine Rolle mehr.
- Ein Wechsel in eine kritischere Stufe wird sofort wahrgenommen. Geht jemand von Walk (Stufe 3) in Run (Stufe 1) über, sperrt das Fenster von Walk das Run nicht.
- Eine Spalte pro EventType ist nicht nötig. Es reichen vier neue Cooldown-Arten.

| Stufe | Bedeutung | Cooldown-Art | Fenster | Events pro Fenster |
|---|---|---|---|---|
| 0 | kritisch | – | kein Cooldown | alle |
| 1 | hoch | `TYPE_ATTR_HIGH` | 5 s | 1 |
| 2 | mittel | `TYPE_ATTR_MEDIUM` | 10 s | 1 |
| 3 | niedrig | `TYPE_ATTR_LOW` | 30 s | 1 |
| 4 | Hintergrund | `TYPE_ATTR_BACKGROUND` | 60 s | 1 |

**Zuordnung pro EventType (bestätigt 2026-11-15):**

| EventType | Priorität heute | Stufe | Fenster | erzeugt |
|---|---|---|---|---|
| percipientSleep | AMBIENT | 4 | 60 s | später |
| percipientDrink | DYNAMIC | 3 | 30 s | später |
| percipientEat | DYNAMIC | 3 | 30 s | später |
| percipientPiss | AMBIENT | 4 | 60 s | später |
| percipientShit | AMBIENT | 4 | 60 s | später |
| percipientMoveWalk | AMBIENT | 3 | 30 s | neu aus `ActionMove` |
| percipientMoveRun | DYNAMIC | 1 | 5 s | neu |
| percipientMoveSneak | DYNAMIC | 1 | 5 s | neu |
| percipientMoveJump | DYNAMIC | 2 | 10 s | neu |
| percipientMoveSwim | DYNAMIC | 3 | 30 s | neu |
| percipientMoveFly | DYNAMIC | 2 | 10 s | neu |
| percipientExamineByLook | DYNAMIC | 3 | 30 s | später |
| percipientExamineBySmell | DYNAMIC | 3 | 30 s | später |
| percipientExamineByTaste | DYNAMIC | 3 | 30 s | später |
| percipientExamineByTouch | DYNAMIC | 3 | 30 s | später |
| percipientTouchByHand | DYNAMIC | 2 | 10 s | später |
| percipientTouchByFoot | DYNAMIC | 2 | 10 s | später |
| percipientInventoryTake | DYNAMIC | 2 | 10 s | später |
| percipientInventoryDrop | DYNAMIC | 3 | 30 s | später |
| percipientInventorySwitch | DYNAMIC | 3 | 30 s | später |
| percipientInventorySet | DYNAMIC | 3 | 30 s | später |
| percipientInventoryGet | DYNAMIC | 2 | 10 s | später |
| percipientHandleItemUse2 | DYNAMIC | 3 | 30 s | später |
| percipientHandleItemUseLeft | DYNAMIC | 3 | 30 s | später |
| percipientHandleItemUseRight | DYNAMIC | 3 | 30 s | später |
| percipientHandleItemAddRtoL | DYNAMIC | 3 | 30 s | später |
| percipientHandleItemAddLtoR | DYNAMIC | 3 | 30 s | später |
| percipientHandleItemPull | DYNAMIC | 2 | 10 s | später |
| percipientHandleItemPush | DYNAMIC | 2 | 10 s | später |
| percipientWeaponLeftStab | CRITICAL | 0 | – | später |
| percipientWeaponLeftStroke | CRITICAL | 0 | – | später |
| percipientWeaponLeftBackhand | CRITICAL | 0 | – | später |
| percipientWeaponRightStab | CRITICAL | 0 | – | später |
| percipientWeaponRightStroke | CRITICAL | 0 | – | später |
| percipientWeaponRightBackhand | CRITICAL | 0 | – | später |
| percipientWeaponClub | CRITICAL | 0 | – | später |
| percipientPunchLeftFistStraight | CRITICAL | 0 | – | später |
| percipientPunchLeftFistSideways | CRITICAL | 0 | – | später |
| percipientPunchLeftFistUpward | CRITICAL | 0 | – | später |
| percipientPunchRightFistStraight | CRITICAL | 0 | – | später |
| percipientPunchRightFistSideways | CRITICAL | 0 | – | später |
| percipientPunchRightFistUpward | CRITICAL | 0 | – | später |
| percipientAskNormal | DYNAMIC | 2 | 10 s | später |
| percipientAskScream | CRITICAL | 0 | – | später |
| percipientAskWhisper | DYNAMIC | 2 | 10 s | später |
| percipientAnswerNormal | DYNAMIC | 2 | 10 s | später |
| percipientAnswerScream | CRITICAL | 0 | – | später |
| percipientAnswerWhisper | DYNAMIC | 2 | 10 s | später |
| percipientExistsDistance100000 | AMBIENT | 4 | 60 s | nein |
| percipientExistsDistance10000 | AMBIENT | 4 | 60 s | nein |
| percipientExistsDistance5000 | AMBIENT | 4 | 60 s | ja |
| percipientExistsDistance2000 | AMBIENT | 3 | 30 s | nein |
| percipientExistsDistance1000 | AMBIENT | 3 | 30 s | ja |
| percipientExistsDistance100 | AMBIENT | 2 | 10 s | nein |

Gedanken hinter der Zuordnung:
- **Stufe 1 (hoch):** Rennen und Schleichen. Daraus kann gleich etwas werden, Flucht, Angriff oder Heimlichkeit.
- **Stufe 2 (mittel):** Sprung und Flug, Berührung, etwas an sich nehmen (Take, Get), Ziehen und Schieben, ein Gespräch mithören. Wer etwas nimmt, könnte stehlen.
- **Stufe 3 (niedrig):** Alltägliches: Gehen, Schwimmen, Essen, Trinken, Untersuchen, Gegenstände benutzen, Ablegen, Umräumen.
- **Stufe 4 (Hintergrund):** Ruhe und Körperliches, und Anwesenheit in größerer Entfernung.
- **Anwesenheit:** Je näher, desto kritischer. Distance100 ist Stufe 2, 2000 und 1000 sind Stufe 3, ab 5000 ist es Stufe 4.

**Anmerkungen:**

- Die **Stufe entscheidet**, ob und wie gedrosselt wird: Stufe 0 heißt ungedrosselt, ab Stufe 1 gedrosselt. Die Priorität dient nur noch `EventMaster` (Reihenfolge, Yield). Bei den percipient-Typen deckt sich Stufe 0 genau mit `CRITICAL`. `AMBIENT` und `DYNAMIC` können in jeder Stufe von 1 bis 4 liegen, z. B. ist percipientMoveWalk `AMBIENT` und Stufe 3.
- Die Zuordnung könnte wie `getEffectDistance()` als Methode am `EventType` stehen, z. B. `getAttributeCooldownLevel()`. Fenster und Cooldown-Art folgen aus der Stufe. `TYPE_PERCEPTION` und `TYPE_PERCEPTION_AMBIENT` werden heute nur in `AttributeCalculator` benutzt und würden von den Stufen abgelöst.
- Self-, target- und candidates-Events haben grundsätzlich Stufe 0, also keinen Cooldown. Ihre Häufigkeit regeln die festen Abstände der Aktion (7.1).
- **Ausnahme, am EventType angegeben (entschieden 2026-11-15):** selfMoveWalk, selfMoveRun, selfMoveSneak, selfMoveSwim und selfMoveFly haben Stufe 3 (30 s). Sie bleiben `CRITICAL` für `EventMaster`, die Position bleibt im Sekundentakt. Grund: Schon der kleinste Schritt von 1 pro Sekunde ergäbe beim Gehen +3600 tiredness pro Stunde. selfMoveJump kommt ohnehin nur einmal.
- **Ebenso (entschieden 2026-11-15):** selfHandleItemUse2, selfHandleItemUseLeft, selfHandleItemUseRight, selfHandleItemAddRtoL, selfHandleItemAddLtoR, selfHandleItemPull und selfHandleItemPush haben Stufe 3 (30 s). Grund: Bei einem Takt von 2 s brachte schon der Schritt +1 den farmer in knapp 2 Stunden Arbeit von tiredness 40 auf 80 (Tagesrechnung, Abschnitt 8.1).

### 7.3 Cooldowns der self-Events

Entschieden 2026-11-15. Abgeleitet aus den festen Abständen (7.1): Ein Takt von *a* Sekunden ergibt 3600/*a* Events pro Stunde. Weil der kleinste wirksame Schritt 1 ist (Abschnitt 9), sind das mindestens 3600/*a* Punkte pro Stunde. Ein Cooldown ist nur dort nötig, wo das zu viel wird.

**Schreibweise:** Cooldown 1 heißt **kein Cooldown**. In der Umsetzung gilt das als Stufe 0, also ganz ohne Prüfung. Ein echter Cooldown von 1 s würde ein zweites self-Event desselben Objekts in derselben Sekunde verwerfen.

| EventType | Takt der Aktion | Events pro Stunde | Cooldown (s) | Bemerkung |
|---|---|---|---|---|
| selfSleep | 300 s | 12 | 1 | |
| selfDrink | 2 s | 1800 | 1 | Trinken dauert nur Sekunden. Ein Cooldown würde jedes Trinken auf ein Event kürzen |
| selfEat | 10 s | 360 | 1 | eine Mahlzeit sind 90 bis 120 Events |
| selfPiss | einmal | – | 1 | |
| selfShit | einmal | – | 1 | |
| selfMoveWalk | 1 s | 3600 | **30** | |
| selfMoveRun | 1 s | 3600 | **30** | |
| selfMoveSneak | 1 s | 3600 | **30** | |
| selfMoveJump | einmal | – | 1 | |
| selfMoveSwim | 1 s | 3600 | **30** | |
| selfMoveFly | 1 s | 3600 | **30** | |
| selfExamineByLook | 5 s | 720 | 1 | auf 30, sobald die Situation eine Attributwirkung bekommt |
| selfExamineBySmell | 5 s | 720 | 1 | wie Look |
| selfExamineByTaste | 5 s | 720 | 1 | wie Look |
| selfExamineByTouch | 5 s | 720 | 1 | wie Look |
| selfTouchByHand | einmal | – | 1 | |
| selfTouchByFoot | einmal | – | 1 | |
| selfInventoryTake | einmal | – | 1 | |
| selfInventoryDrop | einmal | – | 1 | |
| selfInventorySwitch | einmal | – | 1 | |
| selfInventorySet | einmal | – | 1 | |
| selfInventoryGet | einmal | – | 1 | |
| selfHandleItemUse2 | 2 s | 1800 | **30** | |
| selfHandleItemUseLeft | 2 s | 1800 | **30** | |
| selfHandleItemUseRight | 2 s | 1800 | **30** | |
| selfHandleItemAddRtoL | 2 s | 1800 | **30** | |
| selfHandleItemAddLtoR | 2 s | 1800 | **30** | |
| selfHandleItemPull | 2 s | 1800 | **30** | |
| selfHandleItemPush | 2 s | 1800 | **30** | |
| selfWeaponLeftStab | einmal | – | 1 | mehrere Schläge sind mehrere Aktionen |
| selfWeaponLeftStroke | einmal | – | 1 | |
| selfWeaponLeftBackhand | einmal | – | 1 | |
| selfWeaponRightStab | einmal | – | 1 | |
| selfWeaponRightStroke | einmal | – | 1 | |
| selfWeaponRightBackhand | einmal | – | 1 | |
| selfWeaponClub | einmal | – | 1 | |
| selfPunchLeftFistStraight | einmal | – | 1 | |
| selfPunchLeftFistSideways | einmal | – | 1 | |
| selfPunchLeftFistUpward | einmal | – | 1 | |
| selfPunchRightFistStraight | einmal | – | 1 | |
| selfPunchRightFistSideways | einmal | – | 1 | |
| selfPunchRightFistUpward | einmal | – | 1 | |
| selfListenToStatement | einmal | – | 1 | |
| selfListenToQuestion | einmal | – | 1 | |
| selfListenToInstruction | einmal | – | 1 | |
| selfUnderstand | einmal | – | 1 | |
| selfAskNormal | einmal | – | 1 | |
| selfAskScream | einmal | – | 1 | |
| selfAskWhisper | einmal | – | 1 | |
| selfAnswerNormal | einmal | – | 1 | |
| selfAnswerScream | einmal | – | 1 | |
| selfAnswerWhisper | einmal | – | 1 | |
| selfSayNormal | einmal | – | 1 | |
| selfSayScream | einmal | – | 1 | |
| selfSayWhisper | einmal | – | 1 | |

Bei den heutigen Abständen brauchen nur `selfMove…` (ohne Jump) und `selfHandleItem…` einen Cooldown, beide 30 s, also Stufe 3 aus 7.2. Ändern sich Abstände oder bekommen Situationen eine Wirkung, wird die Tabelle nachgezogen.

## 8. Die Tabelle als CSV

Entschieden 2026-11-11: Die Tabelle ist eine CSV-Datei und die **Quelle** für den Massengenerierknopf. Der Knopf übersetzt sie nur noch in JSON und schreibt über `TablePoolEID` in die Datenbank.

Vorschlag:
- **Ort:** neben `characters.csv`, also `src/main/resources/org/socialworld/tools/dct/`.
- **Name:** etwa `event_influences.csv`.
- **Aufbau:** wie bei `characters.csv`, mit `;` als Trenner und `#` für Kommentare. Es gilt der Dezimalpunkt.
- **Sprache und Ablage:** Entschieden 2026-11-15: Die Sätze sind **englisch**, die Datei kommt ins Git. Bei Bedarf übersetzt Claude einzelne Sätze ins Deutsche.

```
# character;situation;sentence;attribute;below;lowerHalf;upperHalf;above
69;21;The wrestler is used to fighting. A punch leaves him calm, if anything it spurs him on.;mood;0;0;-50;-50
69;21;The wrestler is used to fighting. A punch leaves him calm, if anything it spurs him on.;courage;150;500;300;30
69;21;The wrestler is used to fighting. A punch leaves him calm, if anything it spurs him on.;curiosity;20;200;100;20
```

- Der Satz steht in jeder Zeile. Das ist doppelt, aber beim Filtern sieht man ihn immer.
- Fehlt für einen Charakter eine Situation, oder hat sie keine Attributzeile, dann ändert die Situation nichts. Wie eine Regel ohne Wirkung aussieht, ist Frage 6 (Abschnitt 5.4).
- Die Situationen und Faktoren aus Abschnitt 7 stehen in einer zweiten, kleinen Datei oder fest im Generator.

### 8.1 Prüfung durch eine Tagesrechnung

Ein kleines Skript rechnet einen Tag eines Charakters mit den Regeln aus der CSV durch. Es berücksichtigt die Stufen am Band, die Abstände (7.1), die Cooldowns (7.2) und die Rundung (Abschnitt 9). Das Ergebnis ist ein ASCII-Diagramm: Zeit auf der x-Achse, angezeigte Werte 0..99 auf der y-Achse, die Ereignisse als Buchstaben, darunter eine Legende. Gerechnet werden nur die Event-Regeln, ohne Matrizen und ohne Wirkung der Zeit.

Erste Prüfung, farmer, 2026-11-15:
- Werkzeug mit 2 s Takt trieb tiredness in knapp 2 Stunden an die Obergrenze. Daraufhin bekam `selfHandleItem…` Stufe 3 (7.2), und der Schritt wurde auf +3/+2/+2/0 angepasst, dazu hunger +1/+1/+1/0.
- Eine Mahlzeit senkte hunger um etwa 35 Punkte. Die Schritte wurden halbiert.
- Danach: tiredness von 33 am Morgen bis 66 am Abend, hunger fällt mit jeder Mahlzeit um etwa 20 Punkte. Weil die Wirkung der Zeit fehlt, steigt hunger zwischen den Mahlzeiten kaum.

Das Skript wird für jeden Block von Charakteren mitgenutzt.

### 8.2 Die Charaktere 1 bis 10 im Bild

Stand 2026-11-15. Die Charaktere 1 bis 10 stehen in `event_influences.csv`, 404 Zeilen, je 25 Situationen. Verändert werden nur die beweglichen Attribute (Zeilen über alle 10): mood 148, tiredness 60, hunger 50, courage 44, power 30.

**Derselbe Tag für alle 10** (`compare.py`, nutzt `farmer_day.py`): Schlaf, Arbeit mit Werkzeug, drei Mahlzeiten, angeschrien um 12:30, Faustschlag um 13:00, Keule um 21:00. Startwerte: tiredness 75, hunger 45, alle anderen in der Mitte des eigenen Bands. Gerechnet sind nur die Event-Regeln.

Die Wirkungen von Schlag, Keule und Anschreien sind in Attributwerten 0..9999 angegeben, die übrigen Spalten in angezeigten Werten 0..99.

| Nr. | Charakter | tiredness 06:00 | tiredness 22:00 | Faustschlag mood / courage / power | Keule mood / courage | angeschrien mood | mood 22:00 | courage 22:00 |
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

**Die prägnanten Unterschiede**

**Die Harten: fisherman (7) und farmhand (3).**
- Ein Faustschlag kostet beide nur halb so viel mood wie den farmer, Anschreien ebenso.
- Der **fisherman** ist der einzige, den auch die Keule **mutiger** macht (courage +15, „defiant rather than afraid“). Er endet als einziger mit courage 66.
- Der **farmhand** schläft am tiefsten (28 am Morgen). Essen ist sein Höhepunkt: Seine Mahlzeiten heben mood stärker als bei allen anderen, er endet mit 65. Sehen, Hören und Gefragtwerden lassen ihn fast kalt.

**Die Verletzlichen: maid (4), farmer's wife (2) und fisherman's wife (8).**
- Die **maid** trifft alles am härtesten: Schlag −300, Keule −900, Anschreien −180. Ihr Mut fällt über den Tag bis auf 26. Zwei Dinge gibt es nur bei ihr: Eine Anweisung **freut** sie („serving is her place“), und selbst zu sprechen **kostet** sie etwas.
- Die **farmer's wife** schläft leicht (56 am Morgen) und ist abends an der Grenze (75). Das passt zu „always on her feet“ mit tiredness `+`. Als Mutter wird sie bei Nähe, Schreien und rennenden Menschen unruhig.
- Die **fisherman's wife** schläft unruhig (41), weil sie auf ihren Mann wartet. Schreie und rennende Menschen fürchtet sie als schlechte Nachricht, eine Antwort erleichtert sie.

**Die Gleichmütigen: farmer (1), carpenter (5) und miller (10).**
- Sie reagieren fast gleich auf Gewalt. Der Unterschied steckt im Alltag.
- Der **carpenter** hat als einziger **Freude an der Arbeit** (mood +1 pro Takt beim Werkzeug). Er endet deshalb mit mood 60, und es macht ihn stolz, um Rat gefragt zu werden.
- Der **miller** hat beim Zuschlagen **kein schlechtes Gewissen** (keine mood-Zeile, morals `-`). Gewinn freut ihn stark (+40), und rennende Leute verdächtigt er als Mehldiebe.

**Der Gesellige: shopkeeper (6).**
- Reden, Gefragtwerden, eine Antwort und Verstehen heben seine Stimmung deutlich stärker als bei allen anderen. Darin zeigt sich curiosity `+`.
- Weil er schwach ist (power `-`), trifft ihn ein Schlag körperlich am stärksten (power −60), und Laufen ermüdet ihn schneller.

**Der Zufriedene: shepherd (9).**
- Er hat als einziger mood `+` und endet mit der besten Stimmung (67).
- Er ist allein glücklich: Jemand in der Nähe macht ihn leicht unruhig, Gespräche freuen ihn umso mehr.

**Beobachtungen**
- **Die Mahlzeiten beim farmhand** sind deutlich. Bei rund 100 Events pro Mahlzeit hebt mood +12 seine Stimmung pro Mahlzeit um etwa 5 angezeigte Punkte. Das passt zu „likes his food“, liegt aber an der oberen Grenze des Plausiblen.
- **Die Bänder spielen mit.** Bei der maid und der farmer's wife liegt tiredness mit `+` höher, darum bremst der Schlaf früher. Ihre Erschöpfung kommt also aus dem Charakter, nicht nur aus den Schritten. Genau so ist das Band gedacht.

**Hilfsskripte** (in `~/worsoc/concept`): `farmer_day.py` (Tag eines Charakters als ASCII-Diagramm), `compare.py` (derselbe Tag für mehrere Charaktere als Tabelle), `block_2_10.py` (schreibt den Block 2 bis 10 in die CSV und prüft ihn).


## 9. Technische Voraussetzungen

1. **Bedingung mit Unter- und Obergrenze.** Heute baut `ChangeAttribute.createConditionExpression` aus dem Bedingungsterm genau einen Vergleich: Attribut, Operator, Konstante. Für die mittleren Stufen braucht es „U ≤ Y < H“. In der Ausdrucksschicht gibt es `and` schon (`Expression_ConditionOperator`). Der JSON-Bedingungsterm muss dafür erweitert werden.
2. **Funktionsplätze.** Jedes MX+N belegt einen eigenen Platz in `functions` (10000 bis 99999). Rechnung: 100 Charaktere × 85 Typen × etwa 3 Attribute × 4 Stufen ergibt rund 100.000, mehr als die 90.000 Plätze. Du bist bereit, den Bereich zu vergrößern. Zusätzlich wäre denkbar, dass sich gleiche Funktionen einen Platz teilen. Außerdem sucht `findNextFree` linear, bei so vielen Regeln wird das Laden langsam.
3. **Ganze Zahlen und Rundung (Befund 2026-11-15).** `FunctionMXplusN` rechnet in Gleitkomma, `Calculation.createValue(Type.integer, …)` schneidet mit `intValue()` ab. Weil Y positiv ist, heißt das abrunden: Ein N von +7.5 wirkt als +7, ein N von −7.5 als −8. Mit ganzzahligem N und M = 1 ist das Ergebnis exakt. Deshalb **rundet der Generator** `Schritt × Faktor` symmetrisch (ab .5 weg von 0) und schreibt N ganzzahlig in die Formel. Eine **0 ist zugelassen** (entschieden 2026-11-15): Eine 0 in der CSV bleibt 0, ein Wert bleibt dann konstant. Bei den heutigen Faktoren (mindestens 0.5) wird kein Schritt ungleich 0 zu 0. Der kleinste wirksame Schritt pro Event ist 1.
4. **Wenn/Dann/Sonst-wenn-Kette:** darüber machen wir uns an anderer Stelle Gedanken. Zum alten Zeilen-Format kehren wir nicht zurück, die Regeln bleiben JSON.

## 10. Offene Fragen

1. ~~Was heißt `target` bei Eat, Drink und HandleItem?~~ Geklärt 2026-11-11: Dort ist das Ziel ein Gegenstand, siehe Abschnitt 5.1.
2. ~~Stimmen die Pfeile in Abschnitt 5.2?~~ Durch die Tabelle pro Charakter überholt. 5.2 dient nur noch der Orientierung.
3. ~~Wie groß sind die Schritte?~~ Durch die Tabelle pro Charakter überholt. Die Schritte stehen dort, pro Stufe. Wie oft eine Aktion ihr Event auslöst, ist seit 2026-11-15 durch feste Abstände geregelt (Abschnitt 7.1).
4. ~~Wie wirkt der Charakter ein?~~ Entschieden 2026-11-11, siehe Abschnitt 6.
5. Welche EventTypes treten in der Simulation tatsächlich auf und wie oft? Aus dem Code ist geklärt, welche überhaupt erzeugt werden und dass Exists bis zu einmal pro Sekunde wirkt (Abschnitt 5.1). Wie oft die übrigen vorkommen, zeigt erst ein Lauf.
6. Wie sieht die neutrale Regel aus (Abschnitt 5.4), auch für Situationen ohne Attributzeile?
7. ~~Stimmen die Situationen und Faktoren in Abschnitt 7?~~ Bestätigt 2026-11-15.
8. ~~In welcher Sprache stehen die Sätze?~~ Entschieden 2026-11-15: englisch, die CSV kommt ins Git (Abschnitt 8).
9. morals: gut gegen böse? Die endgültige Entscheidung steht aus (Abschnitt 6.5).
10. ~~Verfeinerung der Stufen~~ Bestätigt 2026-11-15: Sehr klein ist der Schritt nur auf der Seite, auf der das Event vom Band **wegführt**. Auf der Seite, auf der es ins Band **zurückführt**, wirkt es normal. Beispiel Schlaf: Unter dem Band von tiredness ein winziger Schritt, über dem Band (übermüdet) der volle. Das Ringer-Beispiel in 6.4 ist schon so gebaut.
11. ~~Cooldowns für Exists und „jemand bewegt sich in der Nähe“?~~ Entschieden 2026-11-15: candidatesMove ohne Cooldown, Bewegung sehen über percipientMove mit Cooldown (Abschnitt 7.2).
12. ~~Stimmen die Stufen, ihre Fenster und die Zuordnung pro EventType in Abschnitt 7.2?~~ Bestätigt 2026-11-15.

## 11. Nächste Schritte

1. ~~Situationen und Faktoren, Verfeinerung der Stufen, Cooldown-Stufen und Sprache festlegen.~~ Erledigt 2026-11-15.
2. Probe: Charakter 1 (farmer) mit allen 25 Situationen, um Ton, Werte und Format abzustimmen. Liegt seit 2026-11-15 in `src/main/resources/org/socialworld/tools/dct/event_influences.csv` (Arbeitsbaum), überarbeitet nach „Ursache und Wirkung“ (Abschnitt 7).
3. Die Tabelle schreiben (1 bis 10 erledigt 2026-11-15, Abschnitt 8.2): Claude entwirft Sätze und Werte in Blöcken von etwa 10 Charakteren, du prüfst jeden Block, bevor der nächste kommt. Jeder Block wird bei dir zwischengespeichert.
4. Technische Voraussetzungen (Abschnitt 9): die Bedingung mit Unter- und Obergrenze, mehr Funktionsplätze, die festen Abstände pro `ActionMode` (Abschnitt 7.1), percipientMove-Events aus `ActionMove` und die Cooldown-Stufen (Abschnitt 7.2).
5. `runMassGeneration()` liest die CSV und erzeugt daraus die Regeln. Weiter mit `TablePoolEID` und den vorhandenen JSON-Klassen.
6. Die Zuteilung des InfluenceTypes an den Charakter koppeln (eigener Schritt).
