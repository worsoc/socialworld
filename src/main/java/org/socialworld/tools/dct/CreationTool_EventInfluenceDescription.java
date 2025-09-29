/*
* Social World
* Copyright (C) 2025  Mathias Sikos
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

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.socialworld.attributes.Attribute;
import org.socialworld.calculation.Expression_Function;
import org.socialworld.calculation.FunctionArgType;
import org.socialworld.calculation.descriptions.EventInfluenceDescription;
import org.socialworld.core.EventType;
import org.socialworld.datasource.parsing.Json;
import org.socialworld.datasource.parsing.JsonEventInfluenceDescription;
import org.socialworld.datasource.parsing.JsonEventInfluencesAttributeDescription;
import org.socialworld.datasource.parsing.JsonFunctionArg;
import org.socialworld.datasource.parsing.JsonTerm;
import org.socialworld.datasource.parsing.JsonValue;
import org.socialworld.datasource.pool.GaussPoolInfluenceType;
import org.socialworld.datasource.tablesPool.TablePoolEID;

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
	JPanel panelTermUpOrDown;
	
	JComboBox<String> chooseEventType;
	JComboBox<String> chooseInfluenceType;
	JComboBox<String> chooseOrderNr;
	JComboBox<String> chooseAttribute;
	JComboBox<String> chooseTerm;
	JComboBox<String> chooseFunction;

	JButton btnTermDown;
	JButton btnTermUp;
	JButton btnCreateDescription;
	JButton btnCreateAttributeDescription;
	
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
	
	private boolean argTypeComboBoxesFilled = false;
	
	private static int termNr;
	private static EventType eventType;
	private static int influenceType;
	
	private HashMap<Integer, JsonEventInfluencesAttributeDescription> inflAttrDescs = new HashMap<Integer, JsonEventInfluencesAttributeDescription>(); 
	private HashMap<Integer, JsonTerm> terms = new HashMap<Integer, JsonTerm>();
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
		panelEventTypeAndInfluenceType.setLayout(new GridLayout(1,5,30,0));
		panelEventTypeAndInfluenceType.setBackground(Color.ORANGE);

		JLabel lblEventType = new JLabel("EventType:");
		panelEventTypeAndInfluenceType.add(lblEventType);
	
		chooseEventType = new JComboBox<String>();
		panelEventTypeAndInfluenceType.add(chooseEventType);

		JLabel lblInfluenceType = new JLabel("InfluenceType:");
		panelEventTypeAndInfluenceType.add(lblInfluenceType);

		chooseInfluenceType = new JComboBox<String>();
		panelEventTypeAndInfluenceType.add(chooseInfluenceType);

		btnCreateDescription 		 = new JButton("Create Desc");
		btnCreateDescription.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
			    btnCreateDescriptionPressed();
			  } 
			} );
		panelEventTypeAndInfluenceType.add(btnCreateDescription);
		
		
		
		panelOrderNrAndAttribute = new JPanel();
		panelOrderNrAndAttribute.setLayout(new GridLayout(1,5,30,0));
		panelOrderNrAndAttribute.setBackground(Color.WHITE);
		
		JLabel lblOrderNr = new JLabel("OrderNr:");
		panelOrderNrAndAttribute.add(lblOrderNr);
	
		chooseOrderNr = new JComboBox<String>();
		chooseOrderNr.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                	chooseOrderNrStateChanged();
                }
            }
        });
		panelOrderNrAndAttribute.add(chooseOrderNr);

		JLabel lblAttribute = new JLabel("Attribut:");
		panelOrderNrAndAttribute.add(lblAttribute);

		chooseAttribute = new JComboBox<String>();
		chooseAttribute.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                	chooseAttributeStateChanged();
                }
            }
        });
		panelOrderNrAndAttribute.add(chooseAttribute);
		
		btnCreateAttributeDescription 		 = new JButton("Create Attrib Desc");
		btnCreateAttributeDescription.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  btnCreateAttributeDescriptionPressed();
			  } 
			} );
		panelOrderNrAndAttribute.add(btnCreateAttributeDescription);
		
		
		
		panelTermUpOrDown = new JPanel();
		panelTermUpOrDown.setLayout(new GridLayout(1,4,10,0));
		panelTermUpOrDown.setBackground(Color.GRAY);

		chooseTerm = new JComboBox<String>();
		chooseTerm.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                	chooseTermStateChanged();
                }
            }
        });
		panelTermUpOrDown.add(chooseTerm);

		chooseFunction = new JComboBox<String>();
		panelTermUpOrDown.add(chooseFunction);
		
		btnTermUp = new JButton("Up");
		btnTermUp.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  btnTermUpPressed();
			  } 
			} );
		panelTermUpOrDown.add(btnTermUp);

		btnTermDown = new JButton("Down");
		btnTermDown.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  btnTermDownPressed();
			  } 
			} );
		panelTermUpOrDown.add(btnTermDown);
		
		
		
		panel3LinesAbove = new JPanel();
		panel3LinesAbove.setLayout(new GridLayout(3,1));
		panel3LinesAbove.setBackground(Color.PINK);
		panel3LinesAbove.setPreferredSize(new Dimension(0, 100));
		panel3LinesAbove.add(panelEventTypeAndInfluenceType);
		panel3LinesAbove.add(panelOrderNrAndAttribute);
		panel3LinesAbove.add(panelTermUpOrDown);
		
		
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

            	int termNrOld = CreationTool_EventInfluenceDescription.termNr;
                String s = (String) chooseTerm.getSelectedItem();//get the selected item
                String sTermNr = s.substring(5,6);
                int termNr = Integer.parseInt(sTermNr);
                if (termNrOld > 0) {
                	terms.put(termNrOld, createTerm(termNrOld));
                }
                CreationTool_EventInfluenceDescription.termNr = termNr;
                
                chooseFunction.setSelectedIndex(0);
        		loadFunctionArgs();

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
                chooseEventTypeStateChanged();
             }
        };
        chooseEventType.addActionListener(chooseEventTypeActionListener);
		
        ActionListener chooseInfluenceTypeActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String influenceTypeNr = (String) chooseInfluenceType.getSelectedItem();//get the selected item
                CreationTool_EventInfluenceDescription.influenceType = Integer. parseInt( influenceTypeNr);
                chooseInfluenceTypeStateChanged();
               }
        };
        chooseInfluenceType.addActionListener(chooseInfluenceTypeActionListener);

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
		chooseFunction.addItem("");
		for(String name : entrysComboBoxFunctions) {
			chooseFunction.addItem(name);
		}
		
		List<String> entrysComboBoxArgTypes = FunctionArgType.getNameList();
		for (JComboBox<String> comboBox : listChooseArgumentType) {
			comboBox.addItem("");
			for(String name : entrysComboBoxArgTypes) {
				comboBox.addItem(name);
			}
		}
		argTypeComboBoxesFilled = true;
		
		for (JComboBox<String> comboBox : listArgValueAttribute) {
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
			JComboBox<String> cb = listArgTermNr.get(j);
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
			JComboBox<String> cb = listArgEventProp.get(j);
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
	
	private void btnCreateAttributeDescriptionPressed() {
		JsonEventInfluencesAttributeDescription attribDesc = createEvInfAttrDesc();
		inflAttrDescs.put(Integer.valueOf(attribDesc.orderNr), attribDesc);
		
		StringSelection selection = new StringSelection(attribDesc.toString());
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(selection, selection);

		//infoBox(attribDesc.toString(), "EventInfluencesAttributeDescription");
	} 

	private void btnCreateDescriptionPressed() {
		JsonEventInfluenceDescription desc = createEvInfDesc();
		String jsonEID = desc.toString();

		EventInfluenceDescription eid = new EventInfluenceDescription(desc);
		
/*		StringSelection selection = new StringSelection(eid.toString());
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(selection, selection);
*/		
		TablePoolEID table = new TablePoolEID();
		table.insert(eid.getNrEventType(), eid.getNrInfluenceType(), jsonEID);
	}

	private void btnTermDownPressed() {
		
	}

	private void btnTermUpPressed() {
		
	}

	private void chooseEventTypeStateChanged() {
        fillEventTypePropsComboBox();
		clearHashmapInflAttrDescs();
		clearHashmapTerms();
		loadFromDB();
	}
	
	private void chooseInfluenceTypeStateChanged() {
		clearHashmapInflAttrDescs();
		clearHashmapTerms();
		loadFromDB();
		}

	private void chooseOrderNrStateChanged() {
		clearHashmapTerms();
	}
	
	private void chooseAttributeStateChanged() {
		clearHashmapTerms();
	}

	private void chooseTermStateChanged() {
	}

	private JsonEventInfluencesAttributeDescription createEvInfAttrDesc() {
		JsonEventInfluencesAttributeDescription attribDesc = new JsonEventInfluencesAttributeDescription();
		attribDesc.orderNr = Integer.parseInt(chooseOrderNr.getSelectedItem().toString());
		attribDesc.attribute =  chooseAttribute.getSelectedItem().toString();
		attribDesc.term = new ArrayList<JsonTerm>();
		terms.forEach( (termNr,term) -> {if (term.termNr > 0) attribDesc.term.add(term);});
		
	
		return attribDesc;
	}
	
	private JsonEventInfluenceDescription createEvInfDesc() {
		JsonEventInfluenceDescription desc = new JsonEventInfluenceDescription();
		desc.eventType = chooseEventType.getSelectedItem().toString();
		desc.influenceType = Integer.parseInt(chooseInfluenceType.getSelectedItem().toString());
		desc.attributeChanges = new ArrayList<JsonEventInfluencesAttributeDescription>();
		inflAttrDescs.forEach( (orderNr,attrDesc) -> {desc.attributeChanges.add(attrDesc);});
		return desc;
	}

	private JsonTerm createTerm(int termNr) {
		JsonTerm term = new JsonTerm();
		term.termNr  = 0;
		
		if (chooseFunction.getSelectedItem() != null && chooseFunction.getSelectedItem().toString().length() > 0) {
			term.termNr = termNr;
			term.function = chooseFunction.getSelectedItem().toString();
			term.functionArgs = new ArrayList<JsonFunctionArg>();
			if (!chooseArgType1.getSelectedItem().toString().equals("")) {
				term.functionArgs.add(createFunctionArg(1));
			}
			if (!chooseArgType2.getSelectedItem().toString().equals("")) {
				term.functionArgs.add(createFunctionArg(2));
			}
			if (!chooseArgType3.getSelectedItem().toString().equals("")) {
				term.functionArgs.add(createFunctionArg(3));
			}
			if (!chooseArgType4.getSelectedItem().toString().equals("")) {
				term.functionArgs.add(createFunctionArg(4));
			}
		}
		
		return term;
	}

	private JsonFunctionArg createFunctionArg(int faNr) {
		JsonFunctionArg functionArg = new JsonFunctionArg();
		functionArg.faNr = faNr;
		switch (faNr) {
		case 1:
			functionArg.type = chooseArgType1.getSelectedItem().toString();
			break;
		case 2:
			functionArg.type = chooseArgType2.getSelectedItem().toString();
			break;
		case 3:
			functionArg.type = chooseArgType3.getSelectedItem().toString();
			break;
		case 4:
			functionArg.type = chooseArgType4.getSelectedItem().toString();
			break;
		}
		functionArg.value = createValue(faNr, functionArg.type);
		return functionArg;
	}

	private JsonValue createValue(int faNr, String faType) {
		JsonValue value = new JsonValue();
		value.type = "";
		value.name = "";
		value.value = "";
		
		if (faNr > 0) {
			switch (faType) {
			case "Const":
				value.type = "integer";
				value.value = listTextFieldsInputConst.get(faNr - 1).getText();
				break;
			case "Attribute":
				value.type = "string";
				value.value = listArgValueAttribute.get(faNr - 1).getSelectedItem().toString();
				break;
			case "EventProperty":
				value.type = "string";
				value.value = listArgEventProp.get(faNr - 1).getSelectedItem().toString();
				break;
			case "TermNr":
				value.type = "integer";
				value.value = listArgTermNr.get(faNr - 1).getSelectedItem().toString().substring(5,6);
				break;
			}
			
		}
		return value;
	}

	private void clearHashmapInflAttrDescs() {
		inflAttrDescs.clear(); 
	}
	
	private void clearHashmapTerms() {
		terms.clear(); 
	}

	private void loadFunctionArgs() {
		if (argTypeComboBoxesFilled) {
			resetArgType(1);
			resetArgType(2);
			resetArgType(3);
			resetArgType(4);
			
			Integer selectedTermNr = Integer.valueOf(CreationTool_EventInfluenceDescription.termNr);
			if (!terms.containsKey(selectedTermNr)) return;
			chooseFunction.setSelectedItem(terms.get(selectedTermNr).function);
			List<JsonFunctionArg> funcArgs = terms.get(selectedTermNr).functionArgs;
			
			if (funcArgs != null) {
				int faNr;
				for (JsonFunctionArg jfa : funcArgs) {
					faNr = jfa.faNr;
					switch (faNr) {
					case 1:
						chooseArgType1.setSelectedItem(jfa.type);
						break;
					case 2:
						chooseArgType2.setSelectedItem(jfa.type);
						break;
					case 3:
						chooseArgType3.setSelectedItem(jfa.type);
						break;
					case 4:
						chooseArgType4.setSelectedItem(jfa.type);
						break;
					}
				}
			
			}
		}
	}
	
	private void resetArgType(int faNr) {
		if (argTypeComboBoxesFilled) {
			switch (faNr) {
			case 1:
				chooseArgType1.setSelectedIndex(0);
				break;
			case 2:
				chooseArgType2.setSelectedIndex(0);
				break;
			case 3:
				chooseArgType3.setSelectedIndex(0);
				break;
			case 4:
				chooseArgType4.setSelectedIndex(0);
				break;
			}
		}
	}
	
	
	private void loadFromDB() {
		
		if (CreationTool_EventInfluenceDescription.eventType == null || CreationTool_EventInfluenceDescription.influenceType == 0) return;
		
		TablePoolEID table = new TablePoolEID();
		
		String jsonEID = table.getJsonEID(
				CreationTool_EventInfluenceDescription.eventType.getIndex(), 
				CreationTool_EventInfluenceDescription.influenceType);
		JsonEventInfluenceDescription jeid = Json.getGsonInstance().fromJson(jsonEID, JsonEventInfluenceDescription.class);
		List<JsonEventInfluencesAttributeDescription> attributeChanges = jeid.attributeChanges;
		for (JsonEventInfluencesAttributeDescription jeiad : attributeChanges) {
			int orderNr = jeiad.orderNr;
			this.inflAttrDescs.put(orderNr, jeiad);
		}
		if (attributeChanges.size() > 0) {
			JsonEventInfluencesAttributeDescription jeiad;
			jeiad = attributeChanges.get(0);
			List<JsonTerm> terms = jeiad.term;
			for (JsonTerm term : terms) {
				int termNr = term.termNr;
				this.terms.put(termNr, term);
			}
			
			if (terms.size() > 0) {
	            CreationTool_EventInfluenceDescription.termNr = terms.get(0).termNr;
	            
	            chooseTerm.setSelectedIndex(CreationTool_EventInfluenceDescription.termNr - 1);
	            chooseFunction.setSelectedIndex(0);
	        	loadFunctionArgs();
	
	            highlightTerm(CreationTool_EventInfluenceDescription.termNr);
				
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
	
    public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
    }



	
}
