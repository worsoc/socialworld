package org.socialworld.tools.dct;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import org.socialworld.attributes.Attribute;

public class CreationTool_EventInfluenceDescription {
	
	private JFrame frame;
	
	JPanel panel3LinesAbove;
	JPanel panelEventTypeAndInfluenceType;
	JPanel panelOrderNrAndAttribute;
	JPanel panelFunctionAddOrDelete;
	
	JComboBox<String> chooseEventType;
	JComboBox<String> chooseInfluenceType;
	JComboBox<String> chooseOrderNr;
	JComboBox<String> chooseAttribute;

	JPanel panelTermsLeftSettingsRight;
	JPanel panelTerms;
	JPanel panelSettings;
	
	List<JLabel> termLabels;
	
	private List<String> entrysComboBoxAttributes;

	/**
	 * Create the application.
	 */
	public	CreationTool_EventInfluenceDescription() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		
		
		panelEventTypeAndInfluenceType = new JPanel();
		panelEventTypeAndInfluenceType.setLayout(new GridLayout(1,4,30,0));
		panelEventTypeAndInfluenceType.setBackground(Color.ORANGE);

		JLabel lblEventType = new JLabel("EventType:");
		panelEventTypeAndInfluenceType.add(lblEventType);
	
		chooseEventType = new JComboBox<String>();
		chooseEventType.addItem("Hello");
		chooseEventType.addItem("World");
		chooseEventType.addItem("and");
		chooseEventType.addItem("Folk");
		panelEventTypeAndInfluenceType.add(chooseEventType);

		JLabel lblInfluenceType = new JLabel("InfluenceType:");
		panelEventTypeAndInfluenceType.add(lblInfluenceType);

		chooseInfluenceType = new JComboBox<String>();
		chooseInfluenceType.addItem("1");
		chooseInfluenceType.addItem("2");
		chooseInfluenceType.addItem("3");
		chooseInfluenceType.addItem("4");
		panelEventTypeAndInfluenceType.add(chooseInfluenceType);

		
		panelOrderNrAndAttribute = new JPanel();
		panelOrderNrAndAttribute.setLayout(new GridLayout(1,4,30,0));
		panelOrderNrAndAttribute.setBackground(Color.WHITE);
		
		JLabel lblOrderNr = new JLabel("OrderNr:");
		panelOrderNrAndAttribute.add(lblOrderNr);
	
		chooseOrderNr = new JComboBox<String>();
		chooseOrderNr.addItem("1");
		chooseOrderNr.addItem("2");
		chooseOrderNr.addItem("3");
		chooseOrderNr.addItem("4");
		panelOrderNrAndAttribute.add(chooseOrderNr);

		JLabel lblAttribute = new JLabel("Attribut:");
		panelOrderNrAndAttribute.add(lblAttribute);

		chooseAttribute = new JComboBox<String>();
		panelOrderNrAndAttribute.add(chooseAttribute);
		
		
		
		
		panelFunctionAddOrDelete = new JPanel();
		panelFunctionAddOrDelete.setLayout(new GridLayout(1,3,30,0));
		panelFunctionAddOrDelete.setBackground(Color.GRAY);

		panel3LinesAbove = new JPanel();
		panel3LinesAbove.setLayout(new GridLayout(3,1));
		panel3LinesAbove.setBackground(Color.PINK);
		panel3LinesAbove.setPreferredSize(new Dimension(0, 100));
		panel3LinesAbove.add(panelEventTypeAndInfluenceType);
		panel3LinesAbove.add(panelOrderNrAndAttribute);
		panel3LinesAbove.add(panelFunctionAddOrDelete);
		
		
		panelTerms = new JPanel();
		panelTerms.setLayout(new GridLayout(7,1,5,5));
		panelTerms.setBackground(Color.BLUE);
		
		termLabels = new ArrayList<JLabel>(7);
		for (int i = 1; i <= 7; i++) {
			JLabel label = new JLabel("Term " + i);
			label.setOpaque(true);
			label.setBackground(Color.GREEN);
			panelTerms.add(label);
			termLabels.add(label);
		}
		
		
		panelTermsLeftSettingsRight = new JPanel();
		panelTermsLeftSettingsRight.setLayout(new BorderLayout());
		panelTermsLeftSettingsRight.setBackground(Color.GREEN);
		panelTermsLeftSettingsRight.add(panelTerms,BorderLayout.CENTER);

		panelSettings = new JPanel();
		panelSettings.setLayout(new BorderLayout());
		panelSettings.setBackground(Color.RED);
		panelSettings.setPreferredSize(new Dimension(400, 0));
		JButton btnTest= new JButton("HelloWord");
		panelSettings.add(btnTest, BorderLayout.CENTER);
		panelTermsLeftSettingsRight.add(panelSettings,BorderLayout.EAST);

		frame = new JFrame();
		frame.setBounds(50, 50, 1300, 750);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());
		
		frame.getContentPane().add(panel3LinesAbove,BorderLayout.NORTH);

		frame.getContentPane().add(panelTermsLeftSettingsRight,BorderLayout.CENTER);

		fillComboBoxes();
	}
	
	private void fillComboBoxes() {
		
		entrysComboBoxAttributes = Attribute.getNameList();
		
		chooseAttribute.addItem("");
		for(String name : entrysComboBoxAttributes)
			chooseAttribute.addItem(name);

	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreationTool_EventInfluenceDescription window = new CreationTool_EventInfluenceDescription();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
			
	}	

}
