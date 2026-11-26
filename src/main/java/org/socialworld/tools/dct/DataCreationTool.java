/*
 * Social World
 * Copyright (C) 2018  Mathias Sikos & Damian Zapadka
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
import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import java.awt.TextArea;
import javax.swing.JSlider;

import org.socialworld.datasource.tablesSimulation.TableLexem;
import org.socialworld.datasource.tablesSimulation.TableRelation;
import org.socialworld.datasource.tablesSimulation.TableWord;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.List;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.ToolTipManager;
import java.awt.Checkbox;
import java.awt.TextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.socialworld.attributes.AttributeArray;

import org.socialworld.tools.dct.AttributeArrayGenerator.GeneratedArray;
import org.socialworld.tools.dct.AttributeMatrixGenerator.GeneratedMatrix;

public class DataCreationTool {
	
	public static final int ATTRIB_COUNT = 9;
	
	static CreateAttributeCalcMatrix cacm;
	
	CreateVector vektoren;
		
	private JFrame frame;
	
	JFileChooser jfc;
	
	TextArea textInput = new TextArea();
	JButton buttonGenerate = button("Generieren", "Alte Erzeugung: Matrizen, Positionen und Attribute aus dem Text oben,", "nur in die Listen. Schreibt nichts in die DB.");
	
	List lastMatrix = new List();
	List matrices = new List();
	List positions = new List();
	List attributes = new List();
	
	JSlider[] sliders4Attribs = new JSlider[ATTRIB_COUNT];
/*	JSlider slider_1 = new JSlider();
	JSlider slider_2 = new JSlider();
	JSlider slider_3 = new JSlider();
	JSlider slider_4 = new JSlider();
	JSlider slider_5 = new JSlider();
	JSlider slider_6 = new JSlider();
	JSlider slider_7 = new JSlider();
	JSlider slider_8 = new JSlider();
	JSlider slider_9 = new JSlider();
*/	
	TextField tfStartAaID = new TextField("1");
	java.util.List<GeneratedArray> generatedArrays = new java.util.ArrayList<>();
	java.util.List<GeneratedMatrix> generatedMatrices = new java.util.ArrayList<>();
	java.util.List<AttributeMatrixMapping.Entry> generatedMapping = new java.util.ArrayList<>();

	TextField tfPathSave = new TextField();
	TextField tfSaveAs = new TextField();
	JButton buttonSave = button("Speichern", "Hängt die Listen Matrizen, Attribute und Positionen an Textdateien an", "(matrices_/attributes_/positions_&lt;Name&gt;.txt im Pfad links). Schreibt nichts in die DB.");
	
	double matrixT[][];
	private final JLabel lblMatrizen = new JLabel("Matrizen:");
	private final JLabel lblAnzeigeMatrix = new JLabel("Anzeige Matrix:");
	private final Checkbox checkboxEventReactionTestFly = new Checkbox("Test: fly", false);

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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DataCreationTool window = new DataCreationTool();
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
	public DataCreationTool() {
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
		lblTexteingabe.setBounds(10, 10, 100, 14);
		frame.getContentPane().add(lblTexteingabe);
		textInput.setBounds(10, 30, 900, 75);
		frame.getContentPane().add(textInput);

		lblMatrizen.setBounds(10, 112, 59, 14);
		frame.getContentPane().add(lblMatrizen);

		lblAnzeigeMatrix.setBounds(1021, 10, 96, 14);
		frame.getContentPane().add(lblAnzeigeMatrix);
	
		buttonGenerate.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			
			{
				
				String input;
				input=textInput.getText();
			
				generateMatrices(input);
				
				generatePositions(input);
	
				generateAttributes(input);
				
			}
		});
		buttonGenerate.setBounds(938, 30, 77, 26);
		frame.getContentPane().add(buttonGenerate);

		JButton buttonLoadText = button("Text laden", "Lädt einen Text aus einer Datei ins Textfeld", "und meldet, ob er für „Charaktere“ reicht.");
		buttonLoadText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadText();
			}
		});
		buttonLoadText.setBounds(938, 62, 77, 26);
		frame.getContentPane().add(buttonLoadText);
		
		lastMatrix.setBounds(1021, 31, 241, 203);
		frame.getContentPane().add(lastMatrix);
		
		matrices.setBounds(10, 132, 900, 102);
		frame.getContentPane().add(matrices);

		
		JLabel lblAttribute = new JLabel("Attribute:");
		lblAttribute.setBounds(370, 240, 60, 14);
		frame.getContentPane().add(lblAttribute);

		JLabel lblMood = new JLabel("mood");
		lblMood.setBounds(12, 260, 66, 14);
		frame.getContentPane().add(lblMood);
		sliders4Attribs[0] = new JSlider();
		sliders4Attribs[0].setBounds(90, 260, 274, 39);
		frame.getContentPane().add(sliders4Attribs[0]);
		sliders4Attribs[0].setMajorTickSpacing(10);
		sliders4Attribs[0].setMinorTickSpacing(1);
		sliders4Attribs[0].setPaintLabels(true);
		sliders4Attribs[0].setPaintTicks(true);
			
		JLabel lblCourage = new JLabel("courage");
		lblCourage.setBounds(12, 312, 66, 14);
		frame.getContentPane().add(lblCourage);
		sliders4Attribs[1] = new JSlider();
		sliders4Attribs[1].setBounds(90, 310, 274, 37);
		frame.getContentPane().add(sliders4Attribs[1]);
		sliders4Attribs[1].setMajorTickSpacing(10);
		sliders4Attribs[1].setMinorTickSpacing(1);
		sliders4Attribs[1].setPaintLabels(true);
		sliders4Attribs[1].setPaintTicks(true);

		JLabel lblMoral = new JLabel("moral");
		lblMoral.setBounds(12, 354, 66, 14);
		frame.getContentPane().add(lblMoral);
		sliders4Attribs[2] = new JSlider();
		sliders4Attribs[2].setBounds(90, 358, 274, 37);
		frame.getContentPane().add(sliders4Attribs[2]);
		sliders4Attribs[2].setMajorTickSpacing(10);
		sliders4Attribs[2].setMinorTickSpacing(1);
		sliders4Attribs[2].setPaintLabels(true);
		sliders4Attribs[2].setPaintTicks(true);
				
		JLabel lblMaterialism = new JLabel("materialism");
		lblMaterialism.setBounds(12, 406, 86, 14);
		frame.getContentPane().add(lblMaterialism);
		sliders4Attribs[3] = new JSlider();
		sliders4Attribs[3].setBounds(90, 406, 274, 37);
		frame.getContentPane().add(sliders4Attribs[3]);
		sliders4Attribs[3].setMajorTickSpacing(10);
		sliders4Attribs[3].setMinorTickSpacing(1);
		sliders4Attribs[3].setPaintLabels(true);
		sliders4Attribs[3].setPaintTicks(true);

		JLabel lblTiredness = new JLabel("tiredness");
		lblTiredness.setBounds(12, 455, 66, 14);
		frame.getContentPane().add(lblTiredness);
		sliders4Attribs[4] = new JSlider();
		sliders4Attribs[4].setBounds(90, 454, 274, 37);
		frame.getContentPane().add(sliders4Attribs[4]);
		sliders4Attribs[4].setMajorTickSpacing(10);
		sliders4Attribs[4].setMinorTickSpacing(1);
		sliders4Attribs[4].setPaintLabels(true);
		sliders4Attribs[4].setPaintTicks(true);
				
		JLabel lblCuriosity = new JLabel("curiosity");
		lblCuriosity.setBounds(12, 503, 66, 14);
		frame.getContentPane().add(lblCuriosity);
		sliders4Attribs[5] = new JSlider();
		sliders4Attribs[5].setBounds(90, 502, 274, 37);
		frame.getContentPane().add(sliders4Attribs[5]);
		sliders4Attribs[5].setMajorTickSpacing(10);
		sliders4Attribs[5].setMinorTickSpacing(1);
		sliders4Attribs[5].setPaintLabels(true);
		sliders4Attribs[5].setPaintTicks(true);
				
		JLabel lblSpirituality = new JLabel("spirituality");
		lblSpirituality.setBounds(12, 553, 86, 14);
		frame.getContentPane().add(lblSpirituality);
		sliders4Attribs[6] = new JSlider();
		sliders4Attribs[6].setBounds(90, 550, 274, 37);
		frame.getContentPane().add(sliders4Attribs[6]);
		sliders4Attribs[6].setMajorTickSpacing(10);
		sliders4Attribs[6].setMinorTickSpacing(1);
		sliders4Attribs[6].setPaintLabels(true);
		sliders4Attribs[6].setPaintTicks(true);
				
		JLabel lblHunger = new JLabel("hunger");
		lblHunger.setBounds(12, 597, 66, 14);
		frame.getContentPane().add(lblHunger);
		sliders4Attribs[7] = new JSlider();
		sliders4Attribs[7].setBounds(90, 598, 274, 37);
		frame.getContentPane().add(sliders4Attribs[7]);
		sliders4Attribs[7].setMajorTickSpacing(10);
		sliders4Attribs[7].setMinorTickSpacing(1);
		sliders4Attribs[7].setPaintLabels(true);
		sliders4Attribs[7].setPaintTicks(true);
				
		JLabel lblPower = new JLabel("power");
		lblPower.setBounds(12, 647, 66, 14);
		frame.getContentPane().add(lblPower);
		sliders4Attribs[8] = new JSlider();
		sliders4Attribs[8].setBounds(90, 646, 274, 37);
		frame.getContentPane().add(sliders4Attribs[8]);
		sliders4Attribs[8].setMajorTickSpacing(10);
		sliders4Attribs[8].setMinorTickSpacing(1);
		sliders4Attribs[8].setPaintLabels(true);
		sliders4Attribs[8].setPaintTicks(true);
		
		
		attributes.setBounds(370, 260, 175, 355);
		frame.getContentPane().add(attributes);
		
		attributes.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) selectedAttribsToSliders();
			}
		});
		
		JButton buttonCharacters = button("Charaktere", "Zieht aus characters.csv und dem Text die Attribut-Arrays (ab aa_id)", "in die Attributliste. Schreibt noch nichts in die DB.");
		buttonCharacters.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generateFromCharacters(textInput.getText());
			}
		});
		buttonCharacters.setBounds(430, 235, 80, 22);
		frame.getContentPane().add(buttonCharacters);

		JButton buttonCharactersDB = button("In DB", "Schreibt die mit „Charaktere“ gezogenen Arrays in die DB:", "swpool_attribute und swgauss_aa werden ersetzt (fragt vorher).");
		buttonCharactersDB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveCharactersToDB();
			}
		});
		buttonCharactersDB.setBounds(480, 657, 65, 26);
		frame.getContentPane().add(buttonCharactersDB);

		JButton buttonSliders2Selected = button("Übernehmen", "Übernimmt die Schieberegler in den gewählten Eintrag der Attributliste.", "„In DB“ schreibt danach diese Werte.");
		buttonSliders2Selected.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				slidersToSelectedAttribs();
			}
		});
		buttonSliders2Selected.setBounds(370, 622, 85, 26);
		frame.getContentPane().add(buttonSliders2Selected);

		JLabel lblStartAaID = new JLabel("aa_id ab:");
		lblStartAaID.setBounds(370, 657, 60, 26);
		frame.getContentPane().add(lblStartAaID);
		tfStartAaID.setBounds(430, 657, 45, 26);
		frame.getContentPane().add(tfStartAaID);

		JLabel lblPositionen = new JLabel("Positionen:");
		lblPositionen.setBounds(551, 240, 77, 14);
		frame.getContentPane().add(lblPositionen);
		positions.setBounds(551, 260, 225, 388);
		frame.getContentPane().add(positions);
		
		
		
		
		/////////////////////
		//"Matrizen" Button//
		/////////////////////
		
		JButton buttonAnzeigeMatrix = button("Anzeige", "Zeigt die in der Liste „Matrizen“ gewählte Matrix rechts oben an.");
		buttonAnzeigeMatrix.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				String matrix[][];
				//tabP = matrizenb.MatrizenZeichnen(textInput.getText());
				matrix = splitCompleteMatrix(matrices.getSelectedItem());

				lastMatrix.removeAll();
				for (int i = 0; i < 9; ++i)
				{
					lastMatrix.add("|" + matrix[i][0] +  "|" +  matrix[i][1] + "|" + matrix[i][2] + "|" + matrix[i][3] + "|" + matrix[i][4] + "|" + matrix[i][5] + "|" + matrix[i][6] + "|" + matrix[i][7] + "|" + matrix[i][8] + "|");
				}
				lastMatrix.add("==================");
			}
		});
		buttonAnzeigeMatrix.setBounds(938, 132, 77, 26);
		frame.getContentPane().add(buttonAnzeigeMatrix);

		JButton buttonCharacterMatrices = button("Matrizen", "Erzeugt aus characters.csv, den Kopplungen und dem Text", "die Attribut-Matrizen der Charaktere (nur in die Liste rechts).");
		buttonCharacterMatrices.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generateCharacterMatrices(textInput.getText());
			}
		});
		buttonCharacterMatrices.setBounds(938, 165, 77, 26);
		frame.getContentPane().add(buttonCharacterMatrices);

		JButton buttonCharacterMatricesDB = button("Matr. DB", "Schreibt die mit „Matrizen“ erzeugten Matrizen und Zuordnungen in die DB:", "swpool_matrix, swpool_matrix_rowcol, swgauss_acm, swgauss_aa_acm werden ersetzt", "(fragt vorher, dauert einige Minuten).");
		buttonCharacterMatricesDB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveCharacterMatricesToDB();
			}
		});
		buttonCharacterMatricesDB.setBounds(938, 197, 77, 26);
		frame.getContentPane().add(buttonCharacterMatricesDB);

		JButton buttonCharacterOverview = button("Übersicht", "Öffnet die Übersicht der Charaktere mit ihren Arrays und Matrizen aus der DB.", "Liest nur.");
		buttonCharacterOverview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showCharacterOverview();
			}
		});
		buttonCharacterOverview.setBounds(938, 229, 77, 26);
		frame.getContentPane().add(buttonCharacterOverview);

		JButton buttonState2ActionDB = button("S2A DB", "Löscht alle Regeln in swpool_s2a und schreibt je Charakter", "eine State2Action-Regel aus characters.csv neu (fragt vorher).");
		buttonState2ActionDB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveState2ActionRulesToDB();
			}
		});
		buttonState2ActionDB.setBounds(938, 261, 77, 26);
		frame.getContentPane().add(buttonState2ActionDB);

		JButton buttonEventReactionDB = button("ERD DB", "Löscht alle Regeln in swpool_erd und schreibt die Reaktionsregeln neu (fragt vorher).", "Mit „Test: fly“ fliegt der Ängstliche weg und nach oben.");
		buttonEventReactionDB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveEventReactionRulesToDB();
			}
		});
		buttonEventReactionDB.setBounds(938, 293, 77, 26);
		frame.getContentPane().add(buttonEventReactionDB);

		// test of the reaction direction (ERD X2, T1): the fearful style flies away and up
		checkboxEventReactionTestFly.setBounds(1021, 293, 100, 26);
		frame.getContentPane().add(checkboxEventReactionTestFly);
		
		buttonSave.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				save();
			}	
		});
		buttonSave.setBounds(1170, 657, 96, 26);
		frame.getContentPane().add(buttonSave);
		
		tfSaveAs.setBounds(938, 657, 225, 26);
		frame.getContentPane().add(tfSaveAs);
		
		tfPathSave.setBounds(585, 657, 225, 26);
		frame.getContentPane().add(tfPathSave);
		
		/////////////////////
		//Open JFileChooser//
		/////////////////////
		
		JButton ButtonPath = button("Pfad \u00E4ndern", "Wählt den Ordner für „Speichern“.");
		ButtonPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				
				String RealPath; 
				JFileChooser jfc = new JFileChooser();
				jfc.setCurrentDirectory(new java.io.File("."));
				jfc.setDialogTitle("choosertitle");
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				jfc.setAcceptAllFileFilterUsed(false);
				
				File file;
				
			    if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) 
			    {
			    	file = jfc.getSelectedFile();
			    	RealPath = file.getAbsolutePath();
			    	System.out.println(RealPath + "-first");
			    	tfPathSave.setText(RealPath);
			    } 
			    else 
			    {
			    	JOptionPane.showMessageDialog(frame, "No Selection");
			    }
			}
		});
		ButtonPath.setBounds(815, 657, 96, 26);
		frame.getContentPane().add(ButtonPath);
		
		
		JButton buttonFillTablesForProperties = button("Fill 4 Props", "Schreibt die Wörter der Eigenschaften neu in sw_lexem und sw_word:", "Colour 10000.., Material 11000.., Nutrient 12000.., Taste 13000.. (löscht die Bereiche vorher).", "Nach Änderungen an diesen Enums drücken, mit frischem Build.");
		buttonFillTablesForProperties.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				long timeStart;
				long timeEnde;
				TableLexem tableLexem = new TableLexem();
				TableWord tableWord = new TableWord();
				timeStart = java.lang.System.currentTimeMillis();
				tableLexem.fillTableForEnumProperties();
				tableWord.fillTableForEnumProperties();
				timeEnde = java.lang.System.currentTimeMillis();
				System.out.println(timeStart + "  --->  " + timeEnde);
			}
		});
		buttonFillTablesForProperties.setBounds(815, 557, 96, 26);
		frame.getContentPane().add(buttonFillTablesForProperties);

		JButton buttonFillTablesForRelations = button("Fill 4 Rels", "Schreibt sw_relation und die Wörter der Relationen in sw_lexem und sw_word neu", "(löscht sie vorher).");
		buttonFillTablesForRelations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				long timeStart;
				long timeEnde;
				TableLexem tableLexem = new TableLexem();
				TableWord tableWord = new TableWord();
				TableRelation tableRelation = new TableRelation();
				timeStart = java.lang.System.currentTimeMillis();
				tableLexem.fillTableForRelations();
				tableWord.fillTableForRelations();
				tableRelation.fillTableForRelations();
				timeEnde = java.lang.System.currentTimeMillis();
				System.out.println(timeStart + "  --->  " + timeEnde);
			}
		});
		buttonFillTablesForRelations.setBounds(815, 607, 96, 26);
		frame.getContentPane().add(buttonFillTablesForRelations);

		JButton buttonFillTablesForSimObjects = button("Fill 4 SimObjs", "Schreibt die Nomen aller Klassen der Simulationsobjekte neu in sw_lexem und sw_word", "(löscht den Bereich vorher). Nach neuen Objektklassen drücken, mit frischem Build.");
		buttonFillTablesForSimObjects.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				long timeStart;
				long timeEnde;
				TableLexem tableLexem = new TableLexem();
				TableWord tableWord = new TableWord();
				timeStart = java.lang.System.currentTimeMillis();
				tableLexem.fillTableForSimObjects();
				tableWord.fillTableForSimObjects();
				timeEnde = java.lang.System.currentTimeMillis();
				System.out.println(timeStart + "  --->  " + timeEnde);
			}
		});
		buttonFillTablesForSimObjects.setBounds(815, 507, 96, 26);
		frame.getContentPane().add(buttonFillTablesForSimObjects);

		
		//
	}
	
	/////////////////////////////////////////
	// printing a matrix in GUI //
	/////////////////////////////////////////

	private void showMatrix(double matrixT[][])
	{

		///////////////////////////////////////////////////////////////
		//Adding additional "0" or "00" for better view in listOutput//
		///////////////////////////////////////////////////////////////

		String matrixTopRight[][] = new String [9][9];

		for(int a = 0; a < 9; ++a)
		{
			for(int b = 0; b < 9; ++b)
			{
				if(matrixT[a][b] < 100)
				{
					if(matrixT[a][b] < 10)
					{
						matrixTopRight[a][b] = "00" + String.valueOf((int)matrixT[a][b]);
					}
					else
					{
						matrixTopRight[a][b] = "0" + String.valueOf((int)matrixT[a][b]);
					}		

				}

				else
				{
					matrixTopRight[a][b] =String.valueOf((int)matrixT[a][b]);
				}
			}
		}

		//////////////////////////////////
		//Printing results in listOutput//
		//////////////////////////////////

		for (int i = 0; i < 9; ++i)
		{
			lastMatrix.add("|" + matrixTopRight[i][0] +  "|" +  matrixTopRight[i][1] + "|" + matrixTopRight[i][2] + "|" + matrixTopRight[i][3] + "|" + matrixTopRight[i][4] + "|" + matrixTopRight[i][5] + "|" + matrixTopRight[i][6] + "|" + matrixTopRight[i][7] + "|" + matrixTopRight[i][8] + "|");
		}
		lastMatrix.add("==================");

		//////////////////////////////////
		//Printing results in textOutput//
		//////////////////////////////////

		String matrixAsLine[] = new String [9];

		for(int i = 0; i < 9; ++i)
		{

			matrixAsLine[i] = String.valueOf((int)matrixT[i][0]) + " " + String.valueOf((int)matrixT[i][1]) + " " + String.valueOf((int)matrixT[i][2]) + " " + String.valueOf((int)matrixT[i][3]) + " " + String.valueOf((int)matrixT[i][4]) + " " + String.valueOf((int)matrixT[i][5]) + " " + String.valueOf((int)matrixT[i][6]) + " " + String.valueOf((int)matrixT[i][7] + " " + String.valueOf((int)matrixT[i][8]));
			
		}

		matrices.add(matrixAsLine[0] + " " + matrixAsLine[1] + " " + matrixAsLine[2] +  " " + matrixAsLine[3] + " " + matrixAsLine[4] + " " + matrixAsLine[5] + " " + matrixAsLine[6] + " " + matrixAsLine[7] + " " + matrixAsLine[8] + " ");

	}
	
	
	
	private String[][] splitCompleteMatrix(String input)
	{
	
		//////////////////////////////////
		//Splitting input by Space symbol//
		//////////////////////////////////
		
		String[] splitInput = input.split("\\s+");
		int[] matrixValues = new int[splitInput.length]; 
		
		for(int x = 0; x < splitInput.length; ++x )
		{
			matrixValues[x] =  Integer.parseInt(splitInput[x]);
		}
		
		String[][] matrix = new String[9][9];
		int c = 0;
		
		//////////////////////////////////////////////
		//Adding "00" or "0" to text for better view//
		//////////////////////////////////////////////
		
		for(int a = 0; a < 9; ++a)
		{
			for(int b = 0; b < 9; ++b)
			{
					
				if(matrixValues[c] < 100)
				{
					if(matrixValues[c] < 10)
					{
						matrix[a][b] = "00" + Integer.toString(matrixValues[c]);
						c++;
					}
					else
					{
						matrix[a][b] = "0" + Integer.toString(matrixValues[c]);
						c++;
					}
				}
				else
				{
					matrix[a][b] = Integer.toString(matrixValues[c]);
					c++;
				}
			}
		
		}
		return matrix;
	}

	
	
	
	private void generateMatrices(String input) {
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		//If the input is bigger than 150, separation into parts of max 150 symbols and the part with rest//
		////////////////////////////////////////////////////////////////////////////////////////////////////

		if(input.length() > 150)
		{
			lastMatrix.removeAll();

			int mal;

			if(input.length() % 150 == 0)
			{
				mal = input.length() / 150;
			}

			else
			{
				mal = input.length() / 150 + 1; 
			}


			String inputTab[] = new String[mal];
			int a = 0;
			int b = 0;

			for(int i = 0; i < mal; i++)
			{
				
				if(i > 0)
				{
					a += 150;	
				}	

				if(i == mal - 1 )
				{
					b = input.length();
				}
				else
				{
					b += 150; 	
				}
	
		
				inputTab[i] = input.substring(a, b);
				cacm = new CreateAttributeCalcMatrix();
				matrixT = cacm.calculate (inputTab[i]);
				cacm.printResults(matrixT);
				showMatrix (matrixT);
	
			}
		}

		/////////////////////////////////////////////////////////////
		//If the input is smaller or equal 150 nothing is separated//
		/////////////////////////////////////////////////////////////

		else
		{


			lastMatrix.removeAll();
			cacm = new CreateAttributeCalcMatrix();
			matrixT = cacm.calculate (input);
			cacm.printResults(matrixT);
			showMatrix (matrixT);
		}
		
	}
	
	///////////////////////////////
	//Input -> generate position vector   //
	///////////////////////////////

	private void generatePositions(String input) {

		int[] resultVektor;
		int laengeTeil = 1000;
		int zero = 0; 
		int iteratorTeile;                                   
		int startTeil = 0;                              
		int endeTeil = 1000;   						  

		input = input.toLowerCase();	
		

		int anzahlTeileOhneRest = input.length() / laengeTeil;            
		int anzahlTeileGesamnt = anzahlTeileOhneRest;                             

		if ((input.length() % laengeTeil) > zero ) anzahlTeileGesamnt++;
		String[] tab = new String [anzahlTeileGesamnt];

		int laengeTextGesamt = (input.length());        		  
           
		for(iteratorTeile = zero; iteratorTeile < anzahlTeileOhneRest; ++iteratorTeile)
		{	            	
			tab[iteratorTeile] = input.substring(startTeil,endeTeil);
			startTeil += laengeTeil;
			endeTeil += laengeTeil;
		}

		
		// den Rest der Texteingabe behandeln
		if((input.length() % laengeTeil) > zero)
		{				
			endeTeil -= laengeTeil;	
			tab[iteratorTeile] = input.substring(endeTeil, laengeTextGesamt);
		}

		
		for(iteratorTeile = zero; iteratorTeile < anzahlTeileGesamnt; iteratorTeile++)	
		{		
			String input1 = tab[iteratorTeile];

			vektoren = new CreateVector(50);
			
			resultVektor = vektoren.calculateVectorPosition(input1);				

			String str1 = Integer.toString(resultVektor[0]); 
			String str2 = Integer.toString(resultVektor[1]);
			String str3 = Integer.toString(resultVektor[2]);

			positions.add("(" + str1 + "," + str2 + "," + str3 +")");
		}		    

	}
	
	private void generateAttributes(String input) {

		int[] resultVektor;
		int laengeTeil = 500;
		int zero = 0; 
		int iteratorTeile;                                   
		int startTeil = 0;                              
		int endeTeil = 500;   						  

		input = input.toLowerCase();	
		

		int anzahlTeileOhneRest = input.length() / laengeTeil;            
		int anzahlTeileGesamnt = anzahlTeileOhneRest;                             

		if ((input.length() % laengeTeil) > zero ) anzahlTeileGesamnt++;
		String[] tab = new String [anzahlTeileGesamnt];

		int laengeTextGesamt = (input.length());        		  
           
		for(iteratorTeile = zero; iteratorTeile < anzahlTeileOhneRest; ++iteratorTeile)
		{	            	
			tab[iteratorTeile] = input.substring(startTeil,endeTeil);
			startTeil += laengeTeil;
			endeTeil += laengeTeil;
		}

		
		// den Rest der Texteingabe behandeln
		if((input.length() % laengeTeil) > zero)
		{				
			endeTeil -= laengeTeil;	
			tab[iteratorTeile] = input.substring(endeTeil, laengeTextGesamt);
		}

		
		for(iteratorTeile = zero; iteratorTeile < anzahlTeileGesamnt; iteratorTeile++)	
		{		
			String input1 = tab[iteratorTeile];

			vektoren = new CreateVector(50);
			
			resultVektor = vektoren.calculateVectorAttributeArray(input1);				

			String str1 = Integer.toString(resultVektor[0]); 
			String str2 = Integer.toString(resultVektor[1]);
			String str3 = Integer.toString(resultVektor[2]);
			String str4 = Integer.toString(resultVektor[3]); 
			String str5 = Integer.toString(resultVektor[4]);
			String str6 = Integer.toString(resultVektor[5]);
			String str7 = Integer.toString(resultVektor[6]); 
			String str8 = Integer.toString(resultVektor[7]);
			String str9 = Integer.toString(resultVektor[8]);

			attributes.add("(" + str1 + "," + str2 + "," + str3 + "," +str4 + "," + str5 + "," + str6 + "," + str7 + "," + str8 + "," +str9 +")");
		}		    

	}
	
	
	///////////////////////////////////////////////
	//attribute arrays from characters and text  //
	///////////////////////////////////////////////

	private void loadText() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("."));
		chooser.setDialogTitle("Text laden");
		if (chooser.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION) return;

		String text;
		try {
			text = TextFile.read(chooser.getSelectedFile().toPath());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(frame, "Fehler: " + e.getMessage());
			return;
		}
		textInput.setText(text);

		int countLetters = TextDie.onlyLetters(text).length();
		int needed = AttributeArrayGenerator.countLettersNeeded(AttributeCharacterCatalog.load());
		JOptionPane.showMessageDialog(frame, countLetters + " Buchstaben geladen, "
				+ needed + " werden für 'Charaktere' gebraucht"
				+ (countLetters < needed ? ".\nDer Text ist zu kurz." : "."));
	}

	private void generateFromCharacters(String input) {
		AttributeCharacterCatalog catalog = AttributeCharacterCatalog.load();
		int startAaID;

		try {
			startAaID = Integer.parseInt(tfStartAaID.getText().trim());
			generatedArrays = AttributeArrayGenerator.generate(catalog, new TextDie(input), startAaID);
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(frame, e.getMessage() + "\n(at least "
					+ AttributeArrayGenerator.countLettersNeeded(catalog) + " letters)");
			return;
		}

		attributes.removeAll();
		for (GeneratedArray array : generatedArrays) {
			attributes.add(entryText(array));
		}
	}

	private static String entryText(GeneratedArray array) {
		return array.getCharacter().getNumber() + " " + array.getCharacter().getName()
				+ " [" + array.getGaussIndex() + "] " + array.getTuple();
	}

	/**
	 * Generated entries hold values 0..9999 and are shown on the sliders as 0..99;
	 * other entries (from "Generieren") hold 0..99 and go to the sliders as they are.
	 */
	private void selectedAttribsToSliders() {
		int selected = attributes.getSelectedIndex();
		String entry = attributes.getSelectedItem();
		if (entry == null) return;
		entry = entry.substring(entry.lastIndexOf('(') + 1, entry.lastIndexOf(')'));
		String[] attribs =  entry.split(",");
		for (int index = 0; index < attribs.length; index++  ) {
			int value = Integer.parseInt(attribs[index].trim());
			if (selected < generatedArrays.size()) value = AttributeArray.toDisplayValue(value);
			sliders4Attribs[index].setValue(value);
		}
	}

	/**
	 * Writes the slider values into the selected entry. For a generated entry the values
	 * also replace those of its array, so "In DB" writes the adjusted values: a slider
	 * that was not moved keeps the value 0..9999, a moved one gives slider × 100.
	 */
	private void slidersToSelectedAttribs() {
		int index = attributes.getSelectedIndex();
		if (index < 0) {
			JOptionPane.showMessageDialog(frame, "Erst einen Eintrag in der Attributliste wählen");
			return;
		}

		int[] values = new int[ATTRIB_COUNT];
		for (int i = 0; i < ATTRIB_COUNT; i++) {
			values[i] = Math.min(99, sliders4Attribs[i].getValue());
		}

		String entry;
		if (index < generatedArrays.size()) {
			GeneratedArray adjusted = generatedArrays.get(index).withDisplayValues(values);
			generatedArrays.set(index, adjusted);
			entry = entryText(adjusted);
		} else {
			StringBuilder tuple = new StringBuilder("(");
			for (int i = 0; i < values.length; i++) {
				if (i > 0) tuple.append(",");
				tuple.append(values[i]);
			}
			entry = tuple.append(")").toString();
		}
		attributes.replaceItem(entry, index);
		attributes.select(index);
	}

	private void generateCharacterMatrices(String input) {
		try {
			// passes 1 and 2: other letters than the attribute arrays, which use pass 0
			generatedMatrices = AttributeMatrixGenerator.generate(AttributeCharacterCatalog.load(),
					AttributeCouplingCatalog.load(), new TextDie(input, 1, 2));
			generatedMapping = AttributeMatrixMapping.generate(generatedMatrices);
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(frame, e.getMessage());
			return;
		}

		lastMatrix.removeAll();
		for (GeneratedMatrix matrix : generatedMatrices) {
			lastMatrix.add(matrix.getCharacter().getNumber() + " " + matrix.getCharacter().getName()
					+ " v" + matrix.getVariant() + " [" + matrix.getGaussIndex() + "] func " + matrix.getFuncID());
		}
	}

	private void showCharacterOverview() {
		try {
			AttributeCharacterOverviewPanel.open(
					AttributeCharacterOverview.read(AttributeCharacterCatalog.load(), new TablePoolAttributeDataReader()));
		} catch (RuntimeException e) {
			JOptionPane.showMessageDialog(frame, "Fehler: " + e.getMessage());
		}
	}

	private void saveCharacterMatricesToDB() {
		if (generatedMatrices.isEmpty()) {
			JOptionPane.showMessageDialog(frame, "Erst mit 'Matrizen' generieren");
			return;
		}

		int answer = JOptionPane.showConfirmDialog(frame,
				generatedMatrices.size() + " Matrizen und " + generatedMapping.size() + " Zuordnungen werden ersetzt"
						+ " (swpool_matrix, swpool_matrix_rowcol, swgauss_acm, swgauss_aa_acm).\n"
						+ "Das kann einige Minuten dauern.",
				"In DB schreiben", JOptionPane.OK_CANCEL_OPTION);
		if (answer != JOptionPane.OK_OPTION) return;

		try {
			AttributeMatrixWriter.write(generatedMatrices, generatedMapping, new TablePoolAttributeMatrixStore());
			JOptionPane.showMessageDialog(frame, generatedMatrices.size() + " Matrizen und " + generatedMapping.size() + " Zuordnungen geschrieben");
		} catch (IllegalStateException e) {
			JOptionPane.showMessageDialog(frame, "Fehler: " + e.getMessage());
		}
	}

	private void saveCharactersToDB() {
		if (generatedArrays.isEmpty()) {
			JOptionPane.showMessageDialog(frame, "Erst mit 'Charaktere' generieren");
			return;
		}

		int first = generatedArrays.get(0).getAaID();
		int last = generatedArrays.get(generatedArrays.size() - 1).getAaID();
		int answer = JOptionPane.showConfirmDialog(frame,
				"aa_id " + first + " bis " + last + " und gauss_index -" + (generatedArrays.size() / 2) + " bis "
						+ (generatedArrays.size() / 2) + " werden in swpool_attribute und swgauss_aa ersetzt.",
				"In DB schreiben", JOptionPane.OK_CANCEL_OPTION);
		if (answer != JOptionPane.OK_OPTION) return;

		try {
			AttributeArrayWriter.write(generatedArrays, new TablePoolAttributeArrayStore());
			JOptionPane.showMessageDialog(frame, generatedArrays.size() + " Arrays geschrieben");
		} catch (IllegalStateException e) {
			JOptionPane.showMessageDialog(frame, "Fehler: " + e.getMessage());
		}
	}

	private void saveEventReactionRulesToDB() {
		boolean testFly = checkboxEventReactionTestFly.getState();
		int answer = JOptionPane.showConfirmDialog(frame,
				"Alle Regeln in swpool_erd werden gelöscht und " + EventReactionGenerator.countRules()
						+ " Reaktionsregeln neu geschrieben (7 Stile × 16 Ausprägungen + 4 bisherige, je Event-Familie)."
						+ (testFly ? "\nTEST: der Ängstliche fliegt (fly) weg und nach oben statt zu schleichen." : ""),
				"In DB schreiben", JOptionPane.OK_CANCEL_OPTION);
		if (answer != JOptionPane.OK_OPTION) return;

		try {
			EventReactionStore store = new TablePoolEventReactionStore();
			EventReactionGenerator.Result result = new EventReactionGenerator(testFly).generate(store);
			System.out.println("Generated event reaction rules: " + result);
			JOptionPane.showMessageDialog(frame, result + " geschrieben nach " + store.getDescription());
		} catch (RuntimeException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame, "Fehler: " + e.getMessage());
		}
	}

	private void saveState2ActionRulesToDB() {
		int answer = JOptionPane.showConfirmDialog(frame,
				"Alle Regeln in swpool_s2a werden gelöscht und " + State2ActionGenerator.countRules()
						+ " State2Action-Regeln aus " + AttributeCharacterCatalog.RESOURCE + " neu geschrieben (eine je Charakter).",
				"In DB schreiben", JOptionPane.OK_CANCEL_OPTION);
		if (answer != JOptionPane.OK_OPTION) return;

		try {
			State2ActionStore store = new TablePoolState2ActionStore();
			State2ActionGenerator.Result result = State2ActionGenerator.fromResources().generate(store);
			System.out.println("Generated state-to-action rules: " + result);
			JOptionPane.showMessageDialog(frame, result + " geschrieben nach " + store.getDescription());
		} catch (RuntimeException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame, "Fehler: " + e.getMessage());
		}
	}

	///////////////////////////////
	//save to file  //
	///////////////////////////////
	
	private void save() 
	{
		
		String i = tfPathSave.getText();
		
		System.out.println(tfSaveAs.getText());
		
		////////////
		//Matrices//
		////////////
		
		String sNameMatrices = i + ((char)92) + "matrices_" + tfSaveAs.getText() + ".txt";
		System.out.println(sNameMatrices);
		try
		{
			// true ... append
			BufferedWriter writer = new BufferedWriter(new FileWriter(sNameMatrices, true));
			
			int mal = matrices.getItemCount();
			
			String matrix[] = matrices.getItems(); 

			for(int x = 0; x < mal; ++x)
			{
				writer.write(matrix[x]);
				writer.newLine();
			}
		
			writer.close();
			
		}
		
		catch(IOException e1)
		{
			e1.printStackTrace();	
		}
		
		//////////////
		//Attributes//
		//////////////
		
		String sNameAttributes = i  + ((char)92) + "attributes_" + tfSaveAs.getText() + ".txt";
		try
		{
			// true ... append
			BufferedWriter writer = new BufferedWriter(new FileWriter(sNameAttributes, true));
			
			int mal = attributes.getItemCount();
			
			String attribs[] = attributes.getItems(); 
			
			for(int x = 0; x < mal; ++x)
			{
			
				writer.write(attribs[x]);
				writer.newLine();

			}
		
			writer.close();
			
		}
		
		catch(IOException e1)
		{
			e1.printStackTrace();	
		}
		
		/////////////
		//Positions//
		/////////////
		
		String sNamePositions = i  + ((char)92) +  "positions_" + tfSaveAs.getText() + ".txt";
		try
		{
			// true ... append
			BufferedWriter writer = new BufferedWriter(new FileWriter(sNamePositions, true));
			
			int mal = positions.getItemCount();
			
			String position[] = positions.getItems(); 
			
			for(int x = 0; x < mal; ++x)
			{
			
				writer.write(position[x]);
				writer.newLine();
				
			}
		
			writer.close();
			
		}
		
		catch(IOException e1)
		{
			e1.printStackTrace();	
		}
		
		
	    System.out.println("saving done");		
	    JOptionPane.showMessageDialog(frame, "Fertig");
		
	}
}
