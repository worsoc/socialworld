/*
* Social World
* Copyright (C) 2023  Mathias Sikos 
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

import javax.swing.JFrame;
import java.awt.TextArea;

import java.awt.List;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

import org.socialworld.attributes.properties.Colour;
import org.socialworld.attributes.properties.Material;
import org.socialworld.attributes.properties.Nutrient;
import org.socialworld.attributes.properties.Taste;
import org.socialworld.datasource.tablesSimulation.propertySets.TableColourSet;
import org.socialworld.datasource.tablesSimulation.propertySets.TableMaterialSet;
import org.socialworld.datasource.tablesSimulation.propertySets.TableNutrientSet;
import org.socialworld.datasource.tablesSimulation.propertySets.TableSet;
import org.socialworld.datasource.tablesSimulation.propertySets.TableTasteSet;


public class PropertySetCreationTool {
	
	
	private CreatePropertySet propertySets;
		
	private JFrame frame;
	
	
	TextArea textInput = new TextArea();
	Button buttonGenerate = new Button("Generieren");
	
	List sets = new List();
	
	TableSet tableSet;
	

	/**
	 * Launch the application.
	 */
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
		
		JLabel lblTexteingabe = new JLabel("Texteingabe:");
		lblTexteingabe.setBounds(10, 10, 77, 14);
		frame.getContentPane().add(lblTexteingabe);
		textInput.setBounds(10, 30, 900, 200);
		frame.getContentPane().add(textInput);

	
		buttonGenerate.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			
			{
				
				String input;
				input=textInput.getText();
			
				
				generatePropertySets(input, "Nutrient");
	
				
			}
		});
		buttonGenerate.setBounds(938, 30, 77, 26);
		frame.getContentPane().add(buttonGenerate);
		
		
		
		JLabel lblPropertySets = new JLabel("PropertySets:");
		lblPropertySets.setBounds(10, 240, 77, 14);
		frame.getContentPane().add(lblPropertySets);
		sets.setBounds(10, 260, 225, 388);
		frame.getContentPane().add(sets);
		
		
		
		
		
		
		
	}
	
	
	
	
	///////////////////////////////
	//Input -> generate property set  //
	///////////////////////////////

	private void generatePropertySets(String input, String propName) {

		int set_id = 0;
		int maxSetElementValue = 0;

		switch(propName) {
		case "Colour":
			tableSet = new TableColourSet();
			set_id = tableSet.getNewID("colour_set_id");
			maxSetElementValue = Colour.getMaxIndex();
			break;
		case "Material":
			tableSet = new TableMaterialSet();
			set_id = tableSet.getNewID("material_set_id");
			maxSetElementValue = Material.getMaxIndex();
			break;
		case "Nutrient":
			tableSet = new TableNutrientSet();
			set_id = tableSet.getNewID("nutrient_set_id");
			maxSetElementValue = Nutrient.getMaxIndex();
			break;
		case "Taste":
			tableSet = new TableTasteSet();
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
