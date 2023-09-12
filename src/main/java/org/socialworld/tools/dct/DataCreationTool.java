/*
* Social World
* Copyright (C) 2018  Mathias Sikos & Damian Zapadka
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.  
*
* or see http://www.gnu.org/licenses/gpl-2.0.html
*
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
import java.awt.Button;
import java.awt.TextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class DataCreationTool {
	
	public static final int ATTRIB_COUNT = 9;
	
	static CreateAttributeCalcMatrix cacm;
	
	CreateVector vektoren;
		
	private JFrame frame;
	
	JFileChooser jfc;
	
	TextArea textInput = new TextArea();
	Button buttonGenerate = new Button("Generieren");
	
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
	TextField tfPathSave = new TextField();
	TextField tfSaveAs = new TextField();
	Button buttonSave = new Button("Speichern");
	
	double matrixT[][];
	private final JLabel lblMatrizen = new JLabel("Matrizen:");
	private final JLabel lblAnzeigeMatrix = new JLabel("Anzeige Matrix:");

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
		
		JLabel lblTexteingabe = new JLabel("Texteingabe:");
		lblTexteingabe.setBounds(10, 10, 77, 14);
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
		
		lastMatrix.setBounds(1021, 31, 241, 203);
		frame.getContentPane().add(lastMatrix);
		
		matrices.setBounds(10, 132, 900, 102);
		frame.getContentPane().add(matrices);

		
		JLabel lblAttribute = new JLabel("Attribute:");
		lblAttribute.setBounds(350, 240, 57, 14);
		frame.getContentPane().add(lblAttribute);

		JLabel lblMood = new JLabel("mood");
		lblMood.setBounds(12, 260, 46, 14);
		frame.getContentPane().add(lblMood);
		sliders4Attribs[0] = new JSlider();
		sliders4Attribs[0].setBounds(70, 260, 274, 39);
		frame.getContentPane().add(sliders4Attribs[0]);
		sliders4Attribs[0].setMajorTickSpacing(10);
		sliders4Attribs[0].setMinorTickSpacing(1);
		sliders4Attribs[0].setPaintLabels(true);
		sliders4Attribs[0].setPaintTicks(true);
			
		JLabel lblCourage = new JLabel("courage");
		lblCourage.setBounds(12, 312, 46, 14);
		frame.getContentPane().add(lblCourage);
		sliders4Attribs[1] = new JSlider();
		sliders4Attribs[1].setBounds(70, 310, 274, 37);
		frame.getContentPane().add(sliders4Attribs[1]);
		sliders4Attribs[1].setMajorTickSpacing(10);
		sliders4Attribs[1].setMinorTickSpacing(1);
		sliders4Attribs[1].setPaintLabels(true);
		sliders4Attribs[1].setPaintTicks(true);

		JLabel lblMoral = new JLabel("moral");
		lblMoral.setBounds(12, 354, 46, 14);
		frame.getContentPane().add(lblMoral);
		sliders4Attribs[2] = new JSlider();
		sliders4Attribs[2].setBounds(70, 358, 274, 37);
		frame.getContentPane().add(sliders4Attribs[2]);
		sliders4Attribs[2].setMajorTickSpacing(10);
		sliders4Attribs[2].setMinorTickSpacing(1);
		sliders4Attribs[2].setPaintLabels(true);
		sliders4Attribs[2].setPaintTicks(true);
				
		JLabel lblMaterialism = new JLabel("materialism");
		lblMaterialism.setBounds(12, 406, 57, 14);
		frame.getContentPane().add(lblMaterialism);
		sliders4Attribs[3] = new JSlider();
		sliders4Attribs[3].setBounds(70, 406, 274, 37);
		frame.getContentPane().add(sliders4Attribs[3]);
		sliders4Attribs[3].setMajorTickSpacing(10);
		sliders4Attribs[3].setMinorTickSpacing(1);
		sliders4Attribs[3].setPaintLabels(true);
		sliders4Attribs[3].setPaintTicks(true);

		JLabel lblTiredness = new JLabel("tiredness");
		lblTiredness.setBounds(12, 455, 46, 14);
		frame.getContentPane().add(lblTiredness);
		sliders4Attribs[4] = new JSlider();
		sliders4Attribs[4].setBounds(70, 454, 274, 37);
		frame.getContentPane().add(sliders4Attribs[4]);
		sliders4Attribs[4].setMajorTickSpacing(10);
		sliders4Attribs[4].setMinorTickSpacing(1);
		sliders4Attribs[4].setPaintLabels(true);
		sliders4Attribs[4].setPaintTicks(true);
				
		JLabel lblCuriosity = new JLabel("curiosity");
		lblCuriosity.setBounds(12, 503, 46, 14);
		frame.getContentPane().add(lblCuriosity);
		sliders4Attribs[5] = new JSlider();
		sliders4Attribs[5].setBounds(70, 502, 274, 37);
		frame.getContentPane().add(sliders4Attribs[5]);
		sliders4Attribs[5].setMajorTickSpacing(10);
		sliders4Attribs[5].setMinorTickSpacing(1);
		sliders4Attribs[5].setPaintLabels(true);
		sliders4Attribs[5].setPaintTicks(true);
				
		JLabel lblSpirituality = new JLabel("spirituality");
		lblSpirituality.setBounds(10, 553, 59, 14);
		frame.getContentPane().add(lblSpirituality);
		sliders4Attribs[6] = new JSlider();
		sliders4Attribs[6].setBounds(70, 550, 274, 37);
		frame.getContentPane().add(sliders4Attribs[6]);
		sliders4Attribs[6].setMajorTickSpacing(10);
		sliders4Attribs[6].setMinorTickSpacing(1);
		sliders4Attribs[6].setPaintLabels(true);
		sliders4Attribs[6].setPaintTicks(true);
				
		JLabel lblHunger = new JLabel("hunger");
		lblHunger.setBounds(12, 597, 46, 14);
		frame.getContentPane().add(lblHunger);
		sliders4Attribs[7] = new JSlider();
		sliders4Attribs[7].setBounds(70, 598, 274, 37);
		frame.getContentPane().add(sliders4Attribs[7]);
		sliders4Attribs[7].setMajorTickSpacing(10);
		sliders4Attribs[7].setMinorTickSpacing(1);
		sliders4Attribs[7].setPaintLabels(true);
		sliders4Attribs[7].setPaintTicks(true);
				
		JLabel lblPower = new JLabel("power");
		lblPower.setBounds(12, 647, 46, 14);
		frame.getContentPane().add(lblPower);
		sliders4Attribs[8] = new JSlider();
		sliders4Attribs[8].setBounds(70, 646, 274, 37);
		frame.getContentPane().add(sliders4Attribs[8]);
		sliders4Attribs[8].setMajorTickSpacing(10);
		sliders4Attribs[8].setMinorTickSpacing(1);
		sliders4Attribs[8].setPaintLabels(true);
		sliders4Attribs[8].setPaintTicks(true);
		
		
		attributes.setBounds(350, 260, 175, 388);
		frame.getContentPane().add(attributes);
		
		Button buttonSliders2Attribs = new Button("-->");
		buttonSliders2Attribs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String entry = "";
				for (int index = 0; index < ATTRIB_COUNT; index++  ) {
					if (index > 0) {
						entry = entry + ",";
					}
					entry = entry + Math.min(99, sliders4Attribs[index].getValue());
				}
				entry = "(" + entry + ")";
				attributes.add(entry);
			}
		});
		buttonSliders2Attribs.setBounds(350, 657, 40, 26);
		frame.getContentPane().add(buttonSliders2Attribs);
		
		Button buttonAttribs2Sliders = new Button("<--");
		buttonAttribs2Sliders.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String entry = attributes.getSelectedItem();
				entry = entry.substring(1, entry.length()-1);
				String[] attribs =  entry.split(",");
				for (int index = 0; index < attribs.length; index++  ) {
					sliders4Attribs[index].setValue(Integer.parseInt(attribs[index]));
				}
			}
		});
		buttonAttribs2Sliders.setBounds(400, 657, 40, 26);
		frame.getContentPane().add(buttonAttribs2Sliders);
		
		Button buttonDeleteAttribsEntry = new Button("L�schen");
		buttonDeleteAttribsEntry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				attributes.remove(	attributes.getSelectedIndex());	}
		});
		buttonDeleteAttribsEntry.setBounds(450, 657, 75, 26);
		frame.getContentPane().add(buttonDeleteAttribsEntry);
		
		JLabel lblPositionen = new JLabel("Positionen:");
		lblPositionen.setBounds(531, 240, 77, 14);
		frame.getContentPane().add(lblPositionen);
		positions.setBounds(531, 260, 225, 388);
		frame.getContentPane().add(positions);
		
		
		
		
		/////////////////////
		//"Matrizen" Button//
		/////////////////////
		
		Button buttonAnzeigeMatrix = new Button("Anzeige");
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
		
		Button ButtonPath = new Button("Pfad \u00E4ndern");
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
		
		
		Button buttonFillTablesForProperties = new Button("Fill 4 Props");
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

		Button buttonFillTablesForRelations = new Button("Fill 4 Rels");
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

		Button buttonFillTablesForSimObjects = new Button("Fill 4 SimObjs");
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
			String str4 = Integer.toString(resultVektor[0]); 
			String str5 = Integer.toString(resultVektor[1]);
			String str6 = Integer.toString(resultVektor[2]);
			String str7 = Integer.toString(resultVektor[0]); 
			String str8 = Integer.toString(resultVektor[1]);
			String str9 = Integer.toString(resultVektor[2]);

			attributes.add("(" + str1 + "," + str2 + "," + str3 + "," +str4 + "," + str5 + "," + str6 + "," + str7 + "," + str8 + "," +str9 +")");
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
