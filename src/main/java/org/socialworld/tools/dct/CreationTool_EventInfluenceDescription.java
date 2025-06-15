package org.socialworld.tools.dct;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import org.socialworld.attributes.Attribute;
import org.socialworld.calculation.Expression_Function;
import org.socialworld.calculation.FunctionArgType;
import org.socialworld.core.EventType;
import org.socialworld.datasource.pool.GaussPoolInfluenceType;

public class CreationTool_EventInfluenceDescription {
	
	private class MyComboBox<String> extends JComboBox<String> {
		private int index;
		
		
		private MyComboBox(int index) {
			super();
			this.index = index;
		}
		
	}
	
	private JFrame frame;
	
	JPanel panel3LinesAbove;
	JPanel panelEventTypeAndInfluenceType;
	JPanel panelOrderNrAndAttribute;
	JPanel panelFunctionAddOrDelete;
	
	JComboBox<String> chooseEventType;
	JComboBox<String> chooseInfluenceType;
	JComboBox<String> chooseOrderNr;
	JComboBox<String> chooseAttribute;
	JComboBox<String> chooseTerm;
	JComboBox<String> chooseFunction;

	JButton btnTermDown;
	JButton btnTermUp;
	
	JPanel panelTermsLeftSettingsRight;
	JPanel panelTerms;
	JPanel panelSettings;
	
	List<JPanel> panelsArguments;
	List<MyComboBox<String>> listChooseArgumentType;
	MyComboBox<String> chooseArgType1;
	MyComboBox<String> chooseArgType2;
	MyComboBox<String> chooseArgType3;
	MyComboBox<String> chooseArgType4;
	List<JPanel> panelsArgumentValue;
	List<JComboBox<String>> listArgValueAttribute;
	List<JTextArea> listTextFieldsInputConst;
	List<JComboBox<String>> listArgTermNr;
	List<JComboBox<String>> listArgEventProp;
	
	List<JLabel> termLabels;
	
	private static int termNr;
	private static EventType eventType;
	
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
		panelEventTypeAndInfluenceType.add(chooseEventType);

		JLabel lblInfluenceType = new JLabel("InfluenceType:");
		panelEventTypeAndInfluenceType.add(lblInfluenceType);

		chooseInfluenceType = new JComboBox<String>();
		panelEventTypeAndInfluenceType.add(chooseInfluenceType);

		
		
		panelOrderNrAndAttribute = new JPanel();
		panelOrderNrAndAttribute.setLayout(new GridLayout(1,4,30,0));
		panelOrderNrAndAttribute.setBackground(Color.WHITE);
		
		JLabel lblOrderNr = new JLabel("OrderNr:");
		panelOrderNrAndAttribute.add(lblOrderNr);
	
		chooseOrderNr = new JComboBox<String>();
		panelOrderNrAndAttribute.add(chooseOrderNr);

		JLabel lblAttribute = new JLabel("Attribut:");
		panelOrderNrAndAttribute.add(lblAttribute);

		chooseAttribute = new JComboBox<String>();
		panelOrderNrAndAttribute.add(chooseAttribute);
		
		
		
		
		panelFunctionAddOrDelete = new JPanel();
		panelFunctionAddOrDelete.setLayout(new GridLayout(1,4,10,0));
		panelFunctionAddOrDelete.setBackground(Color.GRAY);

		chooseTerm = new JComboBox<String>();
		panelFunctionAddOrDelete.add(chooseTerm);

		chooseFunction = new JComboBox<String>();
		panelFunctionAddOrDelete.add(chooseFunction);
		
		btnTermUp = new JButton("Up");
		panelFunctionAddOrDelete.add(btnTermUp);

		btnTermDown = new JButton("Down");
		panelFunctionAddOrDelete.add(btnTermDown);
		
		
		
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

		listChooseArgumentType = new ArrayList<MyComboBox<String>>();
		listTextFieldsInputConst = new ArrayList<JTextArea>();
		listArgValueAttribute = new ArrayList<JComboBox<String>>();
		listArgTermNr = new ArrayList<JComboBox<String>>();
		listArgEventProp = new ArrayList<JComboBox<String>>();
		panelsArguments = new ArrayList<JPanel>(4);
		panelsArgumentValue = new ArrayList<JPanel>(4);

		chooseArgType1 = new MyComboBox<String>(0);
		listChooseArgumentType.add(chooseArgType1);
		chooseArgType2 = new MyComboBox<String>(1);
		listChooseArgumentType.add(chooseArgType2);
		chooseArgType3 = new MyComboBox<String>(2);
		listChooseArgumentType.add(chooseArgType3);
		chooseArgType4 = new MyComboBox<String>(3);
		listChooseArgumentType.add(chooseArgType4);

		for (int i = 0; i < 4; i++) {
			JPanel panelArgument = new JPanel();
			panelArgument.setLayout(new BorderLayout());
			panelArgument.setBackground(Color.YELLOW);
			

			JTextArea tfValueConst = new JTextArea();
			listTextFieldsInputConst.add(tfValueConst);
			
			JComboBox<String> chooseValueAttribute = new JComboBox<String>();
			listArgValueAttribute.add(chooseValueAttribute);

			JComboBox<String> chooseValueTermNr = new JComboBox<String>();
			listArgTermNr.add(chooseValueTermNr);

			JComboBox<String> chooseValueEventTypeProp = new JComboBox<String>();
			listArgEventProp.add(chooseValueEventTypeProp);
			
			JPanel panelArgumenValue = new JPanel();
			panelArgumenValue.setLayout(new GridLayout(FunctionArgType.count(),2,0,0));
			panelArgumenValue.setBackground(Color.YELLOW);
			
			panelsArgumentValue.add(panelArgumenValue);
			
			
			panelArgument.add(listChooseArgumentType.get(i), BorderLayout.NORTH);
			panelArgument.add(panelArgumenValue, BorderLayout.CENTER);
			
			panelsArguments.add(panelArgument);
		}
		
		panelSettings = new JPanel();
		panelSettings.setLayout(new GridLayout(4,1,0,0));
		panelSettings.setBackground(Color.RED);
		panelSettings.setPreferredSize(new Dimension(400, 0));
		for (JPanel panelArgument : panelsArguments) {
			panelSettings.add(panelArgument);
		}
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
		
		
		for (int influenceType = 0; influenceType < GaussPoolInfluenceType.CAPACITY_GPIT_ARRAY; influenceType++) {
			chooseInfluenceType.addItem(Integer.toString(influenceType));
		}
		
		for (int orderNr = 0; orderNr < 100; orderNr++) {
			chooseOrderNr.addItem(Integer.toString(orderNr));
		}

		List<String> entrysComboBoxEventTypes = EventType.getNameList();
		chooseEventType.addItem("");
		for(String name : entrysComboBoxEventTypes) {
			chooseEventType.addItem(name);
		}

		List<String> entrysComboBoxAttributes = Attribute.getNameList();
		chooseAttribute.addItem("");
		for(String name : entrysComboBoxAttributes) {
			chooseAttribute.addItem(name);
		}

		for(int termNr = 1; termNr <= 7; termNr++) {
			chooseTerm.addItem("Term " + termNr);
		}
       ActionListener chooseTermActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String s = (String) chooseTerm.getSelectedItem();//get the selected item
                String sTermNr = s.substring(5,6);
                int termNr = Integer.parseInt(sTermNr);
                CreationTool_EventInfluenceDescription.termNr = termNr;
                highlightTerm(termNr);
             }
        };
        chooseTerm.addActionListener(chooseTermActionListener);

		
        ActionListener chooseEventTypeActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String eventTypeName = (String) chooseEventType.getSelectedItem();//get the selected item
                EventType eventType = EventType.fromName(eventTypeName);
                CreationTool_EventInfluenceDescription.eventType = eventType;
                fillEventTypePropsComboBox();
             }
        };
        chooseEventType.addActionListener(chooseEventTypeActionListener);
		
        ActionListener chooseArgTypeActionListener;
       	chooseArgTypeActionListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String argTypeName =  (String) chooseArgType1.getSelectedItem();//get the selected item
                    FunctionArgType argType = FunctionArgType.fromName(argTypeName);
                    enableChooseValue(chooseArgType1.index, argType);
                 }
            };
        chooseArgType1.addActionListener(chooseArgTypeActionListener);
       	chooseArgTypeActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String argTypeName =  (String) chooseArgType2.getSelectedItem();//get the selected item
                FunctionArgType argType = FunctionArgType.fromName(argTypeName);
                enableChooseValue(chooseArgType2.index, argType);
             }
        };
        chooseArgType2.addActionListener(chooseArgTypeActionListener);
       	chooseArgTypeActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String argTypeName =  (String) chooseArgType3.getSelectedItem();//get the selected item
                FunctionArgType argType = FunctionArgType.fromName(argTypeName);
                enableChooseValue(chooseArgType3.index, argType);
             }
        };
        chooseArgType3.addActionListener(chooseArgTypeActionListener);
       	chooseArgTypeActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String argTypeName =  (String) chooseArgType4.getSelectedItem();//get the selected item
                FunctionArgType argType = FunctionArgType.fromName(argTypeName);
                enableChooseValue(chooseArgType4.index, argType);
             }
        };
        chooseArgType4.addActionListener(chooseArgTypeActionListener);
     
        
		

		
		

		List<String> entrysComboBoxFunctions = Expression_Function.getNameList();
		for(String name : entrysComboBoxFunctions) {
			chooseFunction.addItem(name);
		}
		
		List<String> entrysComboBoxArgTypes = FunctionArgType.getNameList();
		for (JComboBox comboBox : listChooseArgumentType) {
			comboBox.addItem("");
			for(String name : entrysComboBoxArgTypes) {
				comboBox.addItem(name);
			}
		}

		for (JComboBox comboBox : listArgValueAttribute) {
			for(String name : entrysComboBoxAttributes) {
				comboBox.addItem(name);
			}
		}

		int index = 0;
		for (JPanel panel : panelsArgumentValue) {
			panel.add(new JLabel(entrysComboBoxArgTypes.get(0)));
			panel.add(listTextFieldsInputConst.get(index));
			panel.add(new JLabel(entrysComboBoxArgTypes.get(1)));
			panel.add(listArgValueAttribute.get(index));
			panel.add(new JLabel(entrysComboBoxArgTypes.get(2)));
			panel.add(listArgEventProp.get(index));
			panel.add(new JLabel(entrysComboBoxArgTypes.get(3)));
			panel.add(listArgTermNr.get(index));
			
			index++;
		}
		



		
	}
	
	
	private void highlightTerm(int termNr) {
		for (int j = 0; j < 4; j++) {
			JComboBox cb = listArgTermNr.get(j);
			cb.removeAllItems();
			for (int i = 1; i < termNr; i++) {
				cb.addItem("Term " + i);
			}
		}
		for (int i = 1; i <= 7; i++) {
			termLabels.get(i - 1).setBackground(Color.GREEN);
		}
		termLabels.get(termNr - 1).setBackground(Color.BLUE);
		
	}
	
	private void fillEventTypePropsComboBox() {
		List<String> eventPropNames = eventType.getEventParamNameList();
		for (int j = 0; j < 4; j++) {
			JComboBox cb = listArgEventProp.get(j);
			cb.removeAllItems();
			for (String evPropname  : eventPropNames) {
				cb.addItem(evPropname);
			}
		}

	}
	
	public void enableChooseValue(int indexArg, FunctionArgType selectedArgType) {
		if (selectedArgType != null) {
			switch (selectedArgType) {
			case Const:
				listTextFieldsInputConst.get(indexArg).setEnabled(true);
				listArgValueAttribute.get(indexArg).setEnabled(false);
				listArgEventProp.get(indexArg).setEnabled(false);
				listArgTermNr.get(indexArg).setEnabled(false);
				return;
			case Attribute:
				listTextFieldsInputConst.get(indexArg).setEnabled(false);
				listArgValueAttribute.get(indexArg).setEnabled(true);
				listArgEventProp.get(indexArg).setEnabled(false);
				listArgTermNr.get(indexArg).setEnabled(false);
				return;
			case EventProperty:
				listTextFieldsInputConst.get(indexArg).setEnabled(false);
				listArgValueAttribute.get(indexArg).setEnabled(false);
				listArgEventProp.get(indexArg).setEnabled(true);
				listArgTermNr.get(indexArg).setEnabled(false);
				return;
			case TermNr:
				listTextFieldsInputConst.get(indexArg).setEnabled(false);
				listArgValueAttribute.get(indexArg).setEnabled(false);
				listArgEventProp.get(indexArg).setEnabled(false);
				listArgTermNr.get(indexArg).setEnabled(true);
				return;
			}
		}
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

/*
 *               switch (s) {//check for a match
                    case "Day":
                        emailvalue = 1.1;
                        System.out.println("Day selected, emailvalue:" + emailvalue);
                        break;
                    case "Week":
                        emailvalue = 2.2;
                        System.out.println("Week selected, emailvalue:" + emailvalue);
                        break;
                    case "Month":
                        emailvalue = 3.3;
                        System.out.println("Month selected, emailvalue:" + emailvalue);
                        break;
                    default:
                        emailvalue = 4.4;
                        System.out.println("No match selected, emailvalue:" + emailvalue);
                        break;
                }
	
 */
	
}
