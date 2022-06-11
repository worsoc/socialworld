package org.socialworld.tools.pct;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.border.*;

import org.socialworld.calculation.Value;
import org.socialworld.calculation.expressions.CreateKnowledgeElementExpression;
import org.socialworld.calculation.expressions.GetValue;
import org.socialworld.datasource.tablesPool.TablePoolDotElem;
import org.socialworld.datasource.tablesPool.TablePoolDotElemLine;
import org.socialworld.datasource.tablesPool.TablePoolDotElement;
import org.socialworld.datasource.tablesPool.TablePoolParseInput;



public class PerceptionCreationTool extends JFrame{
	
	JPanel mainPanelOben = new JPanel(new BorderLayout());		
	JPanel mainPanelRight = new JPanel(new BorderLayout());	
	JPanel mainPanelLinksUnten = new JPanel(new BorderLayout());
	JPanel mainPanelMitte = new JPanel();
	
	ButtonGroup rbgroupMainOben = new ButtonGroup();
	JPanel pnlSource = new JPanel();
	JPanel pnlFactType = new JPanel();
	JRadioButton rbSubject = new JRadioButton("subject");
	JRadioButton rbSourceType = new JRadioButton("");
	JRadioButton rbFactType = new JRadioButton("");
    JComboBox cbSourceType; 
    JComboBox cbFactType1;
    JComboBox cbFactType2;
    
    JComboBox cbFunction;
    JComboBox cbPropertyName;
    JComboBox cbStaticName;
	TextField tfInputValue = new TextField();

    
	TextArea taOutput = new TextArea();
	
	JButton btnDot = new JButton ("Dot");
	JButton btnBack = new JButton ("Back");
	JButton btnAdd = new JButton ("Add");
	JButton btnSave = new JButton ("SAVE");

	TablePoolParseInput tableParseInput = new TablePoolParseInput();
	
	TablePoolDotElement tableDotElement = new TablePoolDotElement();
	TablePoolDotElemLine tableDotElemLine = new TablePoolDotElemLine();
	TablePoolDotElem tableDotElem = new TablePoolDotElem();

	
	public static void main(String args[]) {

		PerceptionGeneration pg = new PerceptionGeneration();
		CreateKnowledgeElementExpression exp;


 		PerceptionCreationTool pct = new PerceptionCreationTool();
 
		System.out.println("Hello World");

		
		List<String> result = pg.generateAllPerceptionKnowledgeAtomDescriptions();

		int lfdNr = 0;
		int newIDOffset = pct.tableParseInput.getNewID("swpool_parseinputs", "parse_input_id");
		int dot_elem_line_id = 0;
		
		for (String description : result) {
			System.out.println(description);
			dot_elem_line_id = pct.addDotElementLine(description);
			pct.addParseInputString(newIDOffset + lfdNr, dot_elem_line_id, description);
			lfdNr++;
		}

		System.out.println(lfdNr);

/*
		String result = pg.generatePerceptionDescription("gfjg48gtdzi57ugjjgkt");
		//String result = pg.generatePerceptionDescription("bnthnhiut8ub849206u0bu0");
		System.out.println(result);
		exp = new CreateKnowledgeElementExpression(result);
		System.out.println(exp.toString());
*/		
		System.out.println("Bye, Bye, World");

	}
	
	public PerceptionCreationTool() {
		//super();
		//initGUI();	
	}

	
	private void addParseInputString(int id, int refID, String dotElementLine) {
		
		tableParseInput.insert(id, 1, refID, dotElementLine);
		

		
	}
	
	private  int addDotElementLine(String dotElementLine) {
		int rowCount;

		int dot_element_id;
		int dotElemFunction = 0;
		String dotElemValueName = "";
		int dotElemAddon = 0;
		int dotElemAddonIntArg = 0;
		String dotElemAddonCharsArg = "";
		
		int dot_elem_line_id = 0;
		int dotelemline_function = 0;
		String dotElemLineResultValueName;
		String dotElemLineResultType;
		
		String main[];
		main = dotElementLine.split(":");

		String line = main[1];
		String elems[];
		elems = line.split("\\.");
		
		
		// new entry for SWPOOL_DOTELEMLINE
		if (elems.length > 0) {
			
			String fct = main[0].substring(0) ; // later perhaps: substring(0,  main[0].indexOf("("));
			if (fct.equals(CreateKnowledgeElementExpression.LABEL_KNOWLEDGEVALUE)) {
				dotelemline_function = 1;
			}
			else if (fct.indexOf(CreateKnowledgeElementExpression.LABEL_KNOWLEDGEPROPERTY) >= 0) {
				dotelemline_function = 2;
			}

			dotElemLineResultValueName = "";
			//later perhaps:
			//main[0].substring( main[0].indexOf("(") + 1, main[0].indexOf(")"));
			
			dotElemLineResultType = "";
			

			// insert new line to table SWPOOL_DOTELEMLINE
			dot_elem_line_id = tableDotElemLine.insert( dotelemline_function, dotElemLineResultValueName, dotElemLineResultType	);				

		}
		
		if (dot_elem_line_id > 0) {
			
			
			for (int lfdNr = 0; lfdNr < elems.length; lfdNr++) {
				
				dotElemFunction = 0;
				dotElemValueName = "";
				dotElemAddon = 0;
				
				String element = elems[lfdNr];
				
				String splitBySharp[];
				splitBySharp = element.split("#");
				
				String fct = splitBySharp[0].substring(0,  splitBySharp[0].indexOf("("));
				if (fct.equals(GetValue.GETVALUE)) {
					dotElemFunction = 1;
				}
				else if (fct.equals(GetValue.GETPROPERTY)) {
					dotElemFunction = 2;
				}
				
				dotElemValueName = splitBySharp[0].substring( splitBySharp[0].indexOf("(") + 1, splitBySharp[0].indexOf(")"));
				
				if (splitBySharp.length == 2) {
					
					String addon = splitBySharp[1].substring(0,  splitBySharp[1].indexOf("("));
					if (addon.equals(GetValue.GETISELEMENTOF)) {
						dotElemAddon = 1;
					}
					
					String addonarg = splitBySharp[1].substring( splitBySharp[1].indexOf("(") + 1, splitBySharp[1].indexOf(")"));
					dotElemAddonIntArg = Integer.parseInt(addonarg);
				}
				else {
					dotElemAddonIntArg = 0;
					dotElemAddonCharsArg = "";
				}
				
				String whereClause;
				
				whereClause = "WHERE dotelem_function = " + dotElemFunction + " and dotelem_value_name = '" + dotElemValueName + "'";
				if (dotElemAddon > 0 && (dotElemAddonIntArg > 0 || dotElemAddonCharsArg.length() > 0)) {
					whereClause = whereClause + " and dotelem_addon = " + dotElemAddon;
					if (dotElemAddonIntArg > 0) {
						whereClause = whereClause + " and dotelem_addon_intarg = " + dotElemAddonIntArg;
					}
					else {
						whereClause = whereClause + " and dotelem_addon_charsarg = '" + dotElemAddonCharsArg + "'";
					}
				}
				
				//tableDotElement = new TablePoolDotElement();
				tableDotElement.select(tableDotElement.SELECT_DOT_ELEMENT_ID, whereClause, 	"ORDER BY dot_element_id");
				rowCount = tableDotElement.rowCount();
	
				if (rowCount == 1) {
					dot_element_id = tableDotElement.getDotElementID(0);
				}
				else {
					// insert new line to table SWPOOL_DOTELEMENT
					dot_element_id = tableDotElement.insert( dotElemFunction, dotElemValueName, dotElemAddon, dotElemAddonIntArg, dotElemAddonCharsArg);
				}
				
				tableDotElem.insert(dot_elem_line_id, lfdNr + 1, dot_element_id);
				
			}
	
		}

		return dot_elem_line_id;
	}

	
	
	private void initGUI() {
		
		initPanelOben();
		initPanelLinksUnten();
		initPanelRight();
		
		this.getContentPane().add(mainPanelOben, BorderLayout.NORTH);
		this.getContentPane().add(mainPanelLinksUnten, BorderLayout.CENTER);
		this.getContentPane().add(mainPanelRight, BorderLayout.EAST);

		
		this.setSize(new Dimension(168, 124));
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setVisible(true);
		
	}
	
	
	private void initPanelOben() {
		
		Border green = 
		BorderFactory.createLineBorder(Color.green.darker().darker(),5);
		
		Border blue = 
		BorderFactory.createLineBorder(Color.blue.darker().darker(),5);
		
		mainPanelOben.setBorder(green);
		mainPanelOben.setBackground (new Color(200,235,200));
		mainPanelOben.setPreferredSize(new Dimension(0, 100));
		
		mainPanelRight.setBorder(blue);
		mainPanelRight.setBackground (new Color(200,235,200));
		mainPanelRight.setPreferredSize(new Dimension(200, 0));
		 
        String SourceTypeListe[] = {"own experience", "heard of", "read about"};
        cbSourceType = new JComboBox(SourceTypeListe);

		cbFactType2 = new JComboBox();
	    final String FactTypeListe2_1[] = {"colour", "material"};
	    final String FactTypeListe2_2[] = {"unaer", "binaer", "trinaer"};

        
        String FactTypeListe1[] = {"value", "relation", "property"};
        cbFactType1 = new JComboBox(FactTypeListe1);
        cbFactType1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				  
				switch ((String)((JComboBox) e.getSource()).getSelectedItem()) {
				case "relation":
					cbFactType2.removeAllItems();
					cbFactType2.addItem(FactTypeListe2_2[0]);
					cbFactType2.addItem(FactTypeListe2_2[1]);
					cbFactType2.addItem(FactTypeListe2_2[2]);
					break;
				case "property":
					cbFactType2.removeAllItems();
					cbFactType2.addItem(FactTypeListe2_1[0]);
					cbFactType2.addItem(FactTypeListe2_1[1]);
					break;
				case "value":
					cbFactType2.removeAllItems();
					break;
				default: 
					cbFactType2.removeAllItems();
				}
			}															
		});
 
        mainPanelOben.add(cbSourceType);
        
        pnlSource.setLayout(null);
		rbSourceType.setBounds(5, 34, 20, 20);
		cbSourceType.setBounds(40, 33, 300, 20);
		pnlSource.add(rbSourceType);
		pnlSource.add(cbSourceType);
		
		pnlFactType.setLayout(null);
		rbFactType.setBounds(5, 34, 20, 20);
		cbFactType1.setBounds(40, 15, 300, 20);
		cbFactType2.setBounds(40, 55, 300, 20);
		pnlFactType.add(rbFactType);
		pnlFactType.add(cbFactType1);
		pnlFactType.add(cbFactType2);
		
		mainPanelOben.setLayout(new GridLayout(1,3));
		mainPanelOben.add(rbSubject);
		mainPanelOben.add(pnlSource);
		mainPanelOben.add(pnlFactType);
		
		rbgroupMainOben.add(rbSubject);
		rbgroupMainOben.add(rbSourceType);
		rbgroupMainOben.add(rbFactType);
		
	}
	
	private void initPanelLinksUnten() {

		Border red = 
		BorderFactory.createLineBorder(Color.red.darker().darker(),5);

		mainPanelLinksUnten.setBorder(red);
		mainPanelLinksUnten.setBackground (new Color(255,135,100));
		//mainPanelLinksUnten.setPreferredSize(new Dimension(200, 0));
		
		
		
		taOutput.setPreferredSize(new Dimension(0,200));

		mainPanelLinksUnten.add(taOutput, BorderLayout.SOUTH);
		
		JPanel[] raster = new JPanel[6];
		mainPanelMitte.setLayout(new GridLayout(3,2));	
		
		for (int i = 0; i < 6; i++) {
			raster[i] = new JPanel();
			raster[i].setBackground (new Color((i* 20),(Math.min(i*15, 250)),(Math.min(i*10, 200))));
			raster[i].setForeground(Color.WHITE);
			raster[i].setLayout(null);
			mainPanelMitte.add(raster[i]);
		}
		
		mainPanelLinksUnten.add(mainPanelMitte, BorderLayout.CENTER);
		
		String A1 [] = {"GetVal", "GetProp", "GetFctVal"};
		cbFunction = new JComboBox(A1);
		
		cbFunction.setBounds(10, 50, 300, 35);
		raster[0].add(cbFunction);
		
		String A2 [] = {"1", "2", "3"};
		cbPropertyName = new JComboBox(A2);
		
		cbPropertyName.setBounds(10, 50, 300, 35);
		raster[1].add(cbPropertyName);
		
		String A3 [] = {"1", "2", "3"};
		cbStaticName = new JComboBox(A3);
		
		cbStaticName.setBounds(10, 50, 300, 35);
		raster[3].add(cbStaticName);
			
		raster[5].add(tfInputValue);
		tfInputValue.setBounds(10, 50, 300, 35);
	
	
	}
	
	private void initPanelRight() {
		
//	absolute Positionierung
		mainPanelRight.setLayout(null);
		btnDot.setBounds(90, 400, 100, 20);
		btnBack.setBounds(90,425,100,20);
		btnAdd.setBounds(90,450,100,20);
		btnSave.setBounds(90,505,100,20);  
		mainPanelRight.add(btnDot);
		mainPanelRight.add(btnBack);
		mainPanelRight.add(btnAdd);
		mainPanelRight.add(btnSave);
		
		btnDot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				taOutput.append("Dot ");	
			}
		});
		
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				taOutput.append("Back ");	
			}
		});
		
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				taOutput.append("Add ");	
			}
		});
		
	}	
			      
}
		