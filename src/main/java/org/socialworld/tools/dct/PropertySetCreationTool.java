/*
 * Social World
 * Copyright (C) 2023  Mathias Sikos
 * Written with the assistance of Claude (Anthropic), 2026
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://gnu.org>.
 */
package org.socialworld.tools.dct;
import org.socialworld.datasource.mariaDB.Table;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.TextArea;

import java.awt.List;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.ToolTipManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import org.socialworld.attributes.properties.Colour;
import org.socialworld.attributes.properties.Material;
import org.socialworld.attributes.properties.Nutrient;
import org.socialworld.attributes.properties.Taste;
import org.socialworld.datasource.tablesSimulation.TableObject;
import org.socialworld.datasource.createObjects.CompositionCatalog;
import org.socialworld.datasource.createObjects.CompositionSets;
import org.socialworld.datasource.tablesSimulation.propertySets.TableColourSet;
import org.socialworld.datasource.tablesSimulation.propertySets.TableKindMaterialSet;
import org.socialworld.datasource.tablesSimulation.propertySets.TableMaterialSet;
import org.socialworld.datasource.tablesSimulation.propertySets.TableNutrientSet;
import org.socialworld.datasource.tablesSimulation.propertySets.TableSet;
import org.socialworld.datasource.tablesSimulation.propertySets.TableTasteSet;
import org.socialworld.datasource.tablesSimulation.states.TableStateAppearance;
import org.socialworld.datasource.tablesSimulation.states.TableStateComposition;
import org.socialworld.datasource.tablesSimulation.states.TableStateEatable;


public class PropertySetCreationTool {

	// the objects that get states with sets ("States mit Sets füllen"), along the ID bands of the ObjectMaster
	// (10.000 IDs per type since 2026-11-23): 100 humans today, about 1.060 items (start equipment of the
	// characters, loose apples) with room to spare
	static final int FIRST_HUMAN_ID = 1;
	static final int HUMAN_STATE_IDS = 200;
	static final int FIRST_ITEM_ID = 30001;
	static final int ITEM_STATE_IDS = 2000;

	
	
	private CreatePropertySet propertySets;
		
	private JFrame frame;
	
	
	TextArea textInput = new TextArea();
	JButton buttonGenerate = button("Generieren", "Erzeugt aus dem Text neue Sets der oben gewählten Art (Colour, Material, Nutrient, Taste)", "und hängt sie hinten an swset_colour / _material / _nutrient / _taste an.", "Material: neue Sets landen hinter den Katalog-Sets und werden", "vom nächsten „Material-Sets aus Katalog“ wieder gelöscht.");

	JButton buttonFillStatesWithSets = button("FillStates", "Vergibt pro Objekt-ID (Menschen ab 1, Items ab 30001) reihum Sets und leert die Tabellen vorher:", "swstate_appearance (15 Farb-Sets) und swstate_eatable (Nährstoff, Geschmack) für alle,", "swstate_composition leert es nur (die füllt die Simulation beim Start). Vorher FillObject.");

	JButton buttonFillObjectWithDummy = button("FillObject", "Legt in sw_object die fehlenden Zeilen an (Menschen ab 1, Items ab 30001).", "Löscht nichts. Nötig vor FillStates.");

	JButton buttonClearComposition = button("Zusammensetzung leeren", "Leert swstate_composition (fragt vorher).", "Nicht mehr nötig vor einem Lauf: Seit 2026-11-26 leert der Start der Simulation die Tabelle selbst,", "denn das Laden der States ist noch nicht fertig. Danach leitet die Simulation", "die Zusammensetzung jeder Instanz aus swkind_materialset ab und speichert sie hier.");
	JButton buttonMaterialSetsFromCatalog = button("Material-Sets aus Katalog", "Liest composition.csv und schreibt 5 Varianten pro Klasse:", "die Sets ab 100 in swset_material und die Zuordnung Klasse + Variante → Set in swkind_materialset.", "Löscht vorher die Sets ab 100 und alle Zuordnungen; die Sets unter 100 bleiben.");

	private String[] comboBoxSetsEntrys = {
			    "Colour", "Material", "Nutrient", "Taste"  };

	List sets = new List();
	
	TableSet tableSet;
	

	/**
	 * Launch the application.
	 */
	/**
	 * Counts in the table what is really there (not what the loop tried): the rows of the humans and of the items.
	 * A refused insert (e.g. no row in sw_object for the foreign key) shows as a missing row and a warning.
	 */
	private static void reportRows(String what, Table table) {
		int humans = countIds(table, FIRST_HUMAN_ID, HUMAN_STATE_IDS);
		int items = countIds(table, FIRST_ITEM_ID, ITEM_STATE_IDS);
		System.out.println(what + ": " + humans + " of " + HUMAN_STATE_IDS + " humans, " + items + " of " + ITEM_STATE_IDS + " items in the table");
		if (humans < HUMAN_STATE_IDS || items < ITEM_STATE_IDS) {
			System.out.println(what + ": WARNING, rows are missing (for the states: does sw_object have them? press FillObject first)");
		}
	}

	private static int countIds(Table table, int first, int count) {
		table.select(1, "WHERE id BETWEEN " + first + " AND " + (first + count - 1), "");
		return table.rowCount();
	}

	private static long secondsSince(long startMillis) {
		return (System.currentTimeMillis() - startMillis) / 1000;
	}

	/**
	 * A button with a tooltip that says what it does (2026-11-25). Swing instead of AWT, because an AWT button has no
	 * tooltip; the small margin keeps the texts readable at the old widths.
	 */
	private static JButton button(String text, String... tooltipLines) {
		JButton button = new JButton(text);
		button.setMargin(new Insets(1, 2, 1, 2));
		button.setToolTipText("<html>" + String.join("<br>", tooltipLines) + "</html>");
		return button;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PropertySetCreationTool window = new PropertySetCreationTool();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PropertySetCreationTool() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setBounds(50, 50, 1300, 750);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		ToolTipManager.sharedInstance().setDismissDelay(20000);	// long enough to read the tooltips of the buttons
		
		JLabel lblTexteingabe = new JLabel("Texteingabe:");
		lblTexteingabe.setBounds(10, 10, 177, 14);
		frame.getContentPane().add(lblTexteingabe);
		textInput.setBounds(10, 30, 900, 200);
		frame.getContentPane().add(textInput);

		JLabel lblChooseSet = new JLabel("Auswahl Sets:");
		lblChooseSet.setBounds(938, 10, 177, 14);
		frame.getContentPane().add(lblChooseSet);
		JComboBox comboBoxSets = new JComboBox();
		for(int i = 0; i < comboBoxSetsEntrys.length; i++)
			comboBoxSets.addItem(comboBoxSetsEntrys[i]);
		comboBoxSets.setBounds(938, 30, 177, 24);
		frame.getContentPane().add(comboBoxSets);
	
		buttonGenerate.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			
			{
				
				String input;
				input=textInput.getText();
			
				
				generatePropertySets(input, comboBoxSets.getSelectedItem().toString());
						//"Colour");
	
				
			}
		});
		buttonGenerate.setBounds(938, 60, 77, 26);
		frame.getContentPane().add(buttonGenerate);
		
		buttonFillStatesWithSets.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
			    // 1. Array für die nicht-fortlaufenden IDs dynamisch erstellen.
			    // Die IDs folgen den Bändern des ObjectMaster (10.000 je Typ seit 2026-11-23): Menschen ab 1,
			    // Items ab 30.001. Bis dahin standen hier 1..100 und 3001..3775 (Bänder von 1.000).
			    int totalIds = HUMAN_STATE_IDS + ITEM_STATE_IDS;
			    int[] activeIds = new int[totalIds];
			    int index = 0;
			    
			    // Menschen: 1 .. HUMAN_STATE_IDS
			    for (int id = FIRST_HUMAN_ID; id < FIRST_HUMAN_ID + HUMAN_STATE_IDS; id++) {
			        activeIds[index++] = id;
			    }
			    // Items: 30001 .. 30000 + ITEM_STATE_IDS
			    for (int id = FIRST_ITEM_ID; id < FIRST_ITEM_ID + ITEM_STATE_IDS; id++) {
			        activeIds[index++] = id;
			    }

			    int maxSetID_1;
			    int maxSetID_2;
			    int setID_1;
			    int setID_2;
			    int setID_3;
			    int setID_4;
			    int setID_5;
			    int setID_6;
			    int setID_7;
			    int setID_8;
			    int setID_9;
			    int setID_10;
			    int setID_11;
			    int setID_12;
			    int setID_13;
			    int setID_14;
			    int setID_15;
			    
			    // feedback on the console (2026-11-23): the button works in the Swing thread, the window waits until the end
			    long start = System.currentTimeMillis();
			    System.out.println("FillStates: start, " + totalIds + " IDs (humans from " + FIRST_HUMAN_ID + ", items from " + FIRST_ITEM_ID + ")");

			    TableStateAppearance tableAppearance = TableStateAppearance.getInstance();
			    tableAppearance.clear();
			    maxSetID_1 = tableAppearance.getNewID("swset_colour", "colour_set_id") - 1;
			    
			    // Für jede ID aus dem Array ausführen
			    for (int id : activeIds) {
			        setID_1 = (id % maxSetID_1);
			        setID_2 = ((id + 1) % maxSetID_1);
			        setID_3 = ((id + 3) % maxSetID_1);
			        setID_4 = ((id + 5) % maxSetID_1);
			        setID_5 = ((id + 7) % maxSetID_1);
			        setID_6 = ((id + 11) % maxSetID_1);
			        setID_7 = ((id + 13) % maxSetID_1);
			        setID_8 = ((id + 17) % maxSetID_1);
			        setID_9 = ((id + 19) % maxSetID_1);
			        setID_10 = ((id + 23) % maxSetID_1);
			        setID_11 = ((id + 29) % maxSetID_1);
			        setID_12 = ((id + 31) % maxSetID_1);
			        setID_13 = ((id + 37) % maxSetID_1);
			        setID_14 = ((id + 41) % maxSetID_1);
			        setID_15 = ((id + 43) % maxSetID_1);
			        tableAppearance.insert(id);
			        tableAppearance.updateColourSetID(id,  1, setID_1);
			        tableAppearance.updateColourSetID(id,  2, setID_2);
			        tableAppearance.updateColourSetID(id,  3, setID_3);
			        tableAppearance.updateColourSetID(id,  4, setID_4);
			        tableAppearance.updateColourSetID(id,  5, setID_5);
			        tableAppearance.updateColourSetID(id,  6, setID_6);
			        tableAppearance.updateColourSetID(id,  7, setID_7);
			        tableAppearance.updateColourSetID(id,  8, setID_8);
			        tableAppearance.updateColourSetID(id,  9, setID_9);
			        tableAppearance.updateColourSetID(id,  10, setID_10);
			        tableAppearance.updateColourSetID(id,  11, setID_11);
			        tableAppearance.updateColourSetID(id,  12, setID_12);
			        tableAppearance.updateColourSetID(id,  13, setID_13);
			        tableAppearance.updateColourSetID(id,  14, setID_14);
			        tableAppearance.updateColourSetID(id,  15, setID_15);
			    }
			    
			    reportRows("FillStates: swstate_appearance (" + secondsSince(start) + " s)", tableAppearance);

			    // no composition any more (body build concept B4, 2026-11-25): swstate_composition belongs to the
			    // simulation, which derives it per instance from swkind_materialset at its start (humans too) and
			    // saves it; FillStates only empties it (user 2026-11-25), like the button "Zusammensetzung leeren"
			    TableStateComposition tableComposition = new TableStateComposition();
			    tableComposition.clear();
			    System.out.println("FillStates: swstate_composition emptied (" + secondsSince(start) + " s); the simulation fills it at its start");

			    TableStateEatable tableEatable = new TableStateEatable();
			    tableEatable.clear();
			    maxSetID_1 = tableEatable.getNewID("swset_nutrient", "nutrient_set_id") - 1;
			    maxSetID_2 = tableEatable.getNewID("swset_taste", "taste_set_id") - 1;
			    
			    // Für jede ID aus dem Array ausführen
			    for (int id : activeIds) {
			        setID_1 = (id % maxSetID_1);
			        setID_2 = (id % maxSetID_2);
			        tableEatable.insert(id, setID_1, setID_2);
			    }
			    reportRows("FillStates: swstate_eatable (" + secondsSince(start) + " s)", tableEatable);
			    System.out.println("FillStates: finished after " + secondsSince(start) + " s");
			}
		});
		buttonFillStatesWithSets.setBounds(938, 230, 77, 26);
		frame.getContentPane().add(buttonFillStatesWithSets);
		
		buttonFillObjectWithDummy.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
			    // 2026-11-23: no clear any more (many tables refer to sw_object by foreign key, and the rows of the old
			    // bands stay); only the missing rows of the new bands are added: humans 1.., items 30001.. (type 1, 4)
			    TableObject table = new TableObject();
			    System.out.println("FillObject: start, humans " + FIRST_HUMAN_ID + ".." + (FIRST_HUMAN_ID + HUMAN_STATE_IDS - 1)
			    		+ ", items " + FIRST_ITEM_ID + ".." + (FIRST_ITEM_ID + ITEM_STATE_IDS - 1));
			    for (int id = FIRST_HUMAN_ID; id < FIRST_HUMAN_ID + HUMAN_STATE_IDS; id++) {
			        table.insertIfMissing(id, 1);
			    }
			    for (int id = FIRST_ITEM_ID; id < FIRST_ITEM_ID + ITEM_STATE_IDS; id++) {
			        table.insertIfMissing(id, 4);
			    }
			    reportRows("FillObject: sw_object", table);
			}
		});
		buttonFillObjectWithDummy.setBounds(938, 430, 77, 26);
		frame.getContentPane().add(buttonFillObjectWithDummy);

		buttonMaterialSetsFromCatalog.addActionListener(event -> writeMaterialSetsFromCatalog());
		buttonMaterialSetsFromCatalog.setBounds(938, 330, 177, 26);
		frame.getContentPane().add(buttonMaterialSetsFromCatalog);

		buttonClearComposition.addActionListener(event -> clearComposition());
		buttonClearComposition.setBounds(938, 380, 177, 26);
		frame.getContentPane().add(buttonClearComposition);
		
		JLabel lblPropertySets = new JLabel("PropertySets:");
		lblPropertySets.setBounds(10, 240, 77, 14);
		frame.getContentPane().add(lblPropertySets);
		sets.setBounds(10, 260, 800, 388);
		frame.getContentPane().add(sets);
		
		
		
		
		
		
		
	}
	
	
	
	
	///////////////////////////////
	// empty swstate_composition (body build concept B4, 2026-11-25)
	///////////////////////////////

	/**
	 * Empties swstate_composition after asking. Since 2026-11-26 the start of the simulation does it itself (loading
	 * the states is not ready yet), so the button is only for emptying by hand; then the simulation derives the
	 * composition of every instance and saves it.
	 */
	private void clearComposition() {
		TableStateComposition table = new TableStateComposition();
		table.select(1, "", "");
		int rows = table.rowCount();
		int answer = JOptionPane.showConfirmDialog(frame,
				"swstate_composition wird geleert (" + rows + " Zeilen).", "Zusammensetzung leeren", JOptionPane.OK_CANCEL_OPTION);
		if (answer != JOptionPane.OK_OPTION) return;
		table.clear();
		table.select(1, "", "");
		System.out.println("Zusammensetzung leeren: " + rows + " rows before, " + table.rowCount() + " now in swstate_composition");
	}

	///////////////////////////////
	// composition.csv -> material sets (composition concept Z3, 2026-11-24)
	///////////////////////////////

	/**
	 * Writes the variants of every class of composition.csv as material sets from CompositionSets.FIRST_SET_ID on and
	 * the coupling class + variant -> set into swkind_materialset. Deletes before only these sets and all couplings;
	 * the sets below FIRST_SET_ID stay. Nothing is written if the catalog has a problem.
	 */
	private void writeMaterialSetsFromCatalog() {
		long start = System.currentTimeMillis();
		CompositionCatalog catalog = CompositionCatalog.getInstance();
		if (!catalog.getProblems().isEmpty()) {
			for (String problem : catalog.getProblems()) System.out.println("Material-Sets: " + problem);
			System.out.println("Material-Sets: nothing written, composition.csv has " + catalog.getProblems().size() + " problems");
			return;
		}
		CompositionSets plan = CompositionSets.plan(catalog);
		if (!plan.fits()) {
			System.out.println("Material-Sets: nothing written, the last set " + plan.lastSetID + " does not fit below " + CompositionSets.MAX_SET_ID);
			return;
		}

		TableMaterialSet tableMaterialSet = TableMaterialSet.getInstance();
		TableKindMaterialSet tableKinds = new TableKindMaterialSet();
		tableMaterialSet.deleteFromSetID(CompositionSets.FIRST_SET_ID);
		tableKinds.clear();
		for (CompositionSets.SetRow row : plan.sets) {
			tableMaterialSet.insert(row.setID, row.lfdNr, row.material, row.share);
		}
		for (CompositionSets.KindRow row : plan.kinds) {
			tableKinds.insert(row.className, row.variant, row.setID);
		}

		sets.removeAll();
		java.util.Map<Integer, String> texts = new java.util.HashMap<>();
		for (CompositionSets.SetRow row : plan.sets) {
			String part = Material.getName(row.material).name() + " " + row.share;
			texts.merge(row.setID, part, (a, b) -> a + ", " + b);
		}
		for (CompositionSets.KindRow row : plan.kinds) {
			sets.add(row.className + " " + row.variant + " (" + row.setID + "): " + texts.get(row.setID));
		}
		System.out.println("Material-Sets: " + catalog.classNames().size() + " classes, " + plan.kinds.size() + " sets ("
				+ CompositionSets.FIRST_SET_ID + " .. " + plan.lastSetID + "), " + plan.sets.size() + " rows in swset_material ("
				+ secondsSince(start) + " s)");
	}

	///////////////////////////////
	//Input -> generate property set  //
	///////////////////////////////

	private void generatePropertySets(String input, String propName) {

		int set_id = 0;
		int maxSetElementValue = 0;

		switch(propName) {
		case "Colour":
			tableSet = TableColourSet.getInstance();
			set_id = tableSet.getNewID("colour_set_id");
			maxSetElementValue = Colour.getMaxIndex();
			break;
		case "Material":
			tableSet =  TableMaterialSet.getInstance();
			set_id = tableSet.getNewID("material_set_id");
			maxSetElementValue = Material.getMaxIndex();
			break;
		case "Nutrient":
			tableSet =  TableNutrientSet.getInstance();
			set_id = tableSet.getNewID("nutrient_set_id");
			maxSetElementValue = Nutrient.getMaxIndex();
			break;
		case "Taste":
			tableSet =  TableTasteSet.getInstance();
			set_id = tableSet.getNewID("taste_set_id");
			maxSetElementValue = Taste.getMaxIndex();
			break;
		default:
			return;
		}

		
		int[][] resultPropertySets;
		int laengeTeil = 100;
		int zero = 0; 
		int iteratorTeile;                                   
		int startTeil = 0;                              
		int endeTeil = 100;   						  

		
		input = input.toLowerCase();	
		

		int anzahlTeileGesamnt = input.length() / laengeTeil;            

		String[] tab = new String [anzahlTeileGesamnt];

           
		for(iteratorTeile = zero; iteratorTeile < anzahlTeileGesamnt; ++iteratorTeile)
		{	            	
			tab[iteratorTeile] = input.substring(startTeil,endeTeil);
			startTeil += laengeTeil;
			endeTeil += laengeTeil;
		}

		

		
		for(iteratorTeile = zero; iteratorTeile < anzahlTeileGesamnt; iteratorTeile++)	
		{		
			String input1 = tab[iteratorTeile];

			propertySets = new CreatePropertySet();
			
			resultPropertySets = propertySets.calculatePropertySet(input1,  maxSetElementValue);

			String output = "";
			for (int i = 0; i < resultPropertySets.length; i++) {
				if (output.length() > 0) output = output + ";";
				output = output + "(" + resultPropertySets[i][0] + "," + resultPropertySets[i][1] + ")";
			}
			addToTableSet(set_id, output ) ;
			set_id++;
			sets.add("{" + output + "}");
		}		    

	}
	
	private void addToTableSet(int set_id, String set) {
		int lfd_nr;
		
		String[] elements;
		String[] pair;
		
		elements = set.split(";");
		lfd_nr = 0;
		for (String elem : elements) {
			pair = elem.split(",");
			lfd_nr++;
			tableSet.insert(set_id, lfd_nr, 
					Integer.parseInt(pair[0].substring(1, pair[0].length())), 
					Integer.parseInt(pair[1].substring(0, pair[1].length()-1)));
		}
	}
	
}
