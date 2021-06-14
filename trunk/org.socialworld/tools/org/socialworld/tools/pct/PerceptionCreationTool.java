package org.socialworld.tools.pct;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.*;

import org.socialworld.calculation.Value;
import org.socialworld.calculation.expressions.CreateKnowledgeElementExpression;



public class PerceptionCreationTool extends JFrame{
	
	JPanel mainPanelOben = new JPanel(new BorderLayout());		// Panel �ber BorerLayxout
	JPanel mainPanelRight = new JPanel(new BorderLayout());		// Panel �ber BorerLayxout
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

	public static void main(String args[]) {
		//new PerceptionCreationTool();

		CreateKnowledgeElementExpression exp;
		System.out.println("Hello World");

		PerceptionGeneration pg = new PerceptionGeneration();
		String result = pg.generatePerceptionDescription("gfjg48gtdzi57ugjjgkt");
		System.out.println(result);
		exp = new CreateKnowledgeElementExpression(result);
		System.out.println(exp.toString());
/*		
		String test = 
		"KSbj:GETVal(" + Value.VALUE_BY_NAME_EVENT_PARAMS + ").GETVal(" + Value.VALUE_BY_NAME_EVENT_CAUSER + ");" +
		"KSrcT:1," +
		"KSrc:GETVal(" + Value.VALUE_NAME_KNOWLEDGE_SOURCE_MYSELF + ")," +
		"KVal:GETVal(" + Value.VALUE_BY_NAME_EVENT_PARAMS + ").GETVal(" + Value.VALUE_BY_NAME_EVENT_CAUSER + ")#IsElem(1056964607).GETProp(simobj_position).GETProp(position_vector)";
		System.out.println(test);

		exp = new CreateKnowledgeElementExpression(test);
		System.out.println(exp.toString());
*/
		System.out.println("Bye, Bye, World");
	}
	
	public PerceptionCreationTool() {
		super();
		initGUI();	
	}


	public void initGUI() {
		
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
	    final String FactTypeListe2_2[] = {"unear", "binaer", "trinaer"};

        
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
		