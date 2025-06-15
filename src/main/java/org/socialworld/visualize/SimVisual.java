/*
* Social World
* Copyright (C) 2019  Mathias Sikos 
* ^				2020  Daniel Litvak
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
package org.socialworld.visualize;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import org.socialworld.actions.AbstractAction;
import org.socialworld.actions.ActionMode;
import org.socialworld.actions.ActionType;
import org.socialworld.attributes.AttributeArray;
import org.socialworld.attributes.PropertyName;
import org.socialworld.attributes.percipience.PropsSeer;
import org.socialworld.calculation.IObjectReceiver;
import org.socialworld.calculation.ObjectRequester;
import org.socialworld.calculation.Value;
import org.socialworld.core.Simulation;
import org.socialworld.core.SocialWorldThread;
import org.socialworld.objects.Human;

public class SimVisual extends SocialWorldThread {
	
	
			
	private JFrame  frameActions;
	
	private JFrame frameAttributes;

	private JPanel panelAttributeList;
	private JPanel panelRaster;
	private RasterField raster[];

	private JTextField attributeLines[];
	private int orderNrForObjectID[];
	private int nrCallForObjectID[];
	private int lastChangedObjID = 100;
	int[] objIDforRow;
	
	
	private int sleepTime = 100;
	private int state; 

	private static AccessTokenVisualize token = AccessTokenVisualize.getValid();

	public SimVisual() {
		state = 0;
		init();

		this.startThread();	
	}
	
	public void run() {
		while (isRunning()) {
			
			
			if (isRunning()) {

				
				//nextSignalState();
			}
			
			try {
				sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}	

	public void show() {
		this.frameAttributes.setVisible(true);
		this.frameActions.setVisible(true);
	}
	
	public void setText(String meldung) {
	}
	

	public void stopAction(int objID) { 
		raster[objID - 1].stopAction();
	}

	

	public void startAction(int objID, AbstractAction action) { 
		raster[objID - 1].startAction(action);
	}

	
	public void setAttributes(int objID, AttributeArray attributes, boolean withChange) {
		if (objID > 100) return;
		if (withChange) orderNrForObjectID[objID-1]++;
		nrCallForObjectID[objID-1]++;
		if (orderNrForObjectID[objID-1] == 155) orderNrForObjectID[objID-1] = 1;
		this.attributeLines[objID-1].setBackground(SimColorConstants.getColorForOrderNr(orderNrForObjectID[objID-1]));
//		this.attributeLines[this.lastChangedObjID - 1].setBackground(Color.WHITE);
//		this.attributeLines[objID-1].setBackground(Color.CYAN);
		this.attributeLines[objID-1].setText("(" + nrCallForObjectID[objID-1] + ") - " + attributes.toString());
//		this.attributeLines[objID-1].setText(objID + " (" + nrCallForObjectID[objID-1] + ") - " + attributes.toString());
		this.lastChangedObjID = objID;
		

		
	}
	
	
	private void init() {
		
		raster = new RasterField[100];
		panelRaster = new JPanel();
		panelRaster.setLayout(new GridLayout(10,10));
		for (int i = 0; i < 100; i++) {
			raster[i] = new RasterField(i);
			panelRaster.add(raster[i]);
		}
		
		frameActions = new JFrame();
		frameActions.setTitle("Action");
		frameActions.setBounds(100, 100, 500, 500);
		frameActions.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frameActions.getContentPane().setLayout(new GridLayout(1,1));
		frameActions.getContentPane().add(panelRaster);
		
		
		panelAttributeList = new JPanel();
		panelAttributeList.setLayout(new GridLayout(25,4));
		objIDforRow = new int[100];
		orderNrForObjectID = new int[100];
		nrCallForObjectID = new int[100];
		attributeLines = new JTextField[100];
		for (int i = 0; i < 100; i++) {
			objIDforRow[i] = i + 1;
			attributeLines[i] = new JTextField("" + (i + 1));
			panelAttributeList.add(attributeLines[i]);
			orderNrForObjectID[i] = 0;
			nrCallForObjectID[i] = 0;
// only for showing the colors 			
//			this.attributeLines[i].setBackground(SimColorConstants.getColorForOrderNr(i+1));
		}

		frameAttributes = new JFrame();
		frameAttributes.setTitle("Attributes");
		frameAttributes.setBounds(100, 100, 1000, 600);
		frameAttributes.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frameAttributes.getContentPane().setLayout(new GridLayout(1,1));
		frameAttributes.getContentPane().add(panelAttributeList);
		
	}
	
	
	private class RasterField extends JButton implements IObjectReceiver {
		int index;
		
		static final long serialVersionUID = 1;
		
		private ObjectRequester objectRequester = new ObjectRequester();
		
		private RasterField(int index) {
			super();
			//this.setFont(new Font("Arial", Font.PLAIN, 15));
			setIndex(index);
			
			addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {				  
					clickRasterField(((RasterField) e.getSource()).index);
				}															
			});

			
		}
		
		private void clear() {
			this.setText("");
		}
		
		private void setIndex(int index) {
			this.index = index;
		}

			
		private void stopAction() {
			Color color = this.getBackground();
			Border thickBorder = new LineBorder(color, 4);
			this.setBorder(thickBorder);
			this.setBackground(Color.WHITE);
			this.clear();
			
		}
		
		private void startAction(AbstractAction action) {
			
			ActionType type = action.getType();
			ActionMode mode = action.getMode();
			float intensity;
			
			Color color;
			color = Color.WHITE;

			switch (type) {
			case bodilyFunction:
				
				switch (mode) {
				case sleep:
					color = SimColorConstants.COLOR_CORNSILK;
					break;
				case drink:
					color = SimColorConstants.COLOR_BLANCHEDALMOND;
					break;
				case eat:
					color = SimColorConstants.COLOR_BISQUE;
					break;
				case piss:
					color = SimColorConstants.COLOR_NAVAJOWHITE;
					break;
				case shit:
					color = SimColorConstants.COLOR_WHEAT;
					break;
				default:
				}
				break;
			case move:
				
				switch (mode) {
				case walk:
					color = SimColorConstants.COLOR_LIGHTCYAN;
					break;
				case run:
					color = SimColorConstants.COLOR_CYAN;
					break;
				case sneak:
					color = SimColorConstants.COLOR_MEDIUMAQUAMARINE;
					break;
				case jump:
					color = SimColorConstants.COLOR_TURQUOISE;
					break;
				case swim:
					color = SimColorConstants.COLOR_AQUAMARINE;
					break;
				case fly:
					color = SimColorConstants.COLOR_PALETURQUOISE;
					break;
				default:	
				}
				break;
			case examine:
				
				switch (mode) {
				case look:
					color = SimColorConstants.COLOR_SILVER;
					break;
				case smell:
					color = SimColorConstants.COLOR_GRAY;
					break;
				case taste:
					color = SimColorConstants.COLOR_DIMGRAY;
					break;
				case touch:
					color = SimColorConstants.COLOR_DARKGRAY;
					break;
				default:
				}
				break;
			case equip:
				
				switch (mode) {
				case takeItem:
					color = SimColorConstants.COLOR_GOLDEN_YELLOW;
					break;
				case dropItem:
					color = SimColorConstants.COLOR_METALLIC_GOLD;
					break;
				case switchItemToOtherHand:
					color = SimColorConstants.COLOR_OLD_GOLD;
					break;
				case setItemToInventory:
					color = SimColorConstants.COLOR_VEGAS_GOLD;
					break;
				case getItemFromInventory:
					color = SimColorConstants.COLOR_PALE_GOLD;
					break;
				default:
				}
				break;
			case handleItem:
				
				switch(mode) {
				case useTwoItems:
					color = SimColorConstants.COLOR_TURQUOISE;
					break;
				case useItemLeftHand:	
					color = SimColorConstants.COLOR_MEDIUMTURQUOISE;
					break;
				case useItemRightHand:
					color = SimColorConstants.COLOR_DARKTURQUOISE;
					break;
				case combineItems_AddRightToLeft:
					color = SimColorConstants.COLOR_LIGHTSEAGREEN;
					break;
				case combineItems_AddLeftToRight:
					color = SimColorConstants.COLOR_CADETBLUE;
					break;
				case pull:
					color = SimColorConstants.COLOR_DARKCYAN;
					break;
				case push:
					color = SimColorConstants.COLOR_TEAL;
					break;
				default:
				}
				break;
			case touch:
				
				switch (mode) {
				case hand:
				color = SimColorConstants.COLOR_BLUEVIOLET;
				break;
				case foot:
					color = SimColorConstants.COLOR_INDIGO;
					break; 
				default:
				}
				break;
			case punch:
				
				switch (mode) {
				case punchLeftFistStraight:
					color = SimColorConstants.COLOR_LIGHTSALMON;
					break; 
				case punchLeftFistSideways:
					color = SimColorConstants.COLOR_SALMON;
					break; 
				case punchLeftFistUpward:
					color = SimColorConstants.COLOR_DARKSALMON;
					break; 
				case punchRightFistStraight:
					color = SimColorConstants.COLOR_LIGHTCORAL;
					break; 
				case punchRightFistSideways:
					color = SimColorConstants.COLOR_INDIANRED;
					break; 
				case punchRightFistUpward:
					color = SimColorConstants.COLOR_CRIMSON;
					break; 
				default:
				}
				break;
			case useWeapon:
				
				switch(mode) {
				case weaponLeftStab:
					color = SimColorConstants.COLOR_CHARTREUSE;
					break; 
				case weaponLeftStroke:
					color = SimColorConstants.COLOR_LIMEGREEN;
					break; 
				case weaponLeftBackhand:
					color = SimColorConstants.COLOR_LIME;
					break; 
				case weaponRightStab:
					color = SimColorConstants.COLOR_FORESTGREEN;
					break; 
				case weaponRightStroke:
					color = SimColorConstants.COLOR_GREEN;
					break; 
				case weaponRightBackhand:
					color = SimColorConstants.COLOR_DARKGREEN;
					break; 
				case weaponClub:
					color = SimColorConstants.COLOR_GREENYELLOW;
					break; 
				default:
				}
				break;
			case hear:
				
				switch(mode) {
				case listenToStatement:
				case listenToQuestion:
				case listenToInstruction:
					color = SimColorConstants.COLOR_SNOW;
					break; 
				case understand:
					color = SimColorConstants.COLOR_HONEYDEW;
					break;
				default:
				}
				break;
			case talk:
				
				switch(mode) {
				case answerNormal:
					color = SimColorConstants.COLOR_PINK;
					break;
				case answerScream:
					color = SimColorConstants.COLOR_LIGHTPINK;
					break;
				case answerWhisper:
					color = SimColorConstants.COLOR_HOTPINK;
					break;
				case askNormal:
					color = SimColorConstants.COLOR_DEEPPINK;
					break;
				case askScream:
					color = SimColorConstants.COLOR_PALEVIOLETRED;
					break;
				case askWhisper:
					color = SimColorConstants.COLOR_MEDIUMVIOLETRED;
					break;
				default:
				}
				break;
			case say:
			
				switch(mode) {
				case sayNormal:
					color = SimColorConstants.COLOR_LINEN;
					break;
				case sayScream:
					color = SimColorConstants.COLOR_LAVENDERBLUSH;
					break;
				case sayWhisper:
					color = SimColorConstants.COLOR_MISTYROSE;
					break;
				default:
				}
				break;
			default:
				color = Color.WHITE;
			}

			intensity = action.getIntensity();
			this.setBackground(color);
			this.setText(Integer.toString((int)intensity));
			
		}

		private void clicked() {
			Human human;
			Value prop;
			org.socialworld.objects.State state;
			Value propSub;
			
			human = Simulation.getInstance().getObjectMaster().getHumans().get(index);
			prop = human.getProperty(token, PropertyName.stateSeer);
			state = objectRequester.requestStateSeer(token, prop, this);
			if (state != null) {
				propSub = state.getProperty(token, PropertyName.stateSeer_propsSeer, PropertyName.stateSeer_propsSeer.toString());
				PropsSeer propsSeer = objectRequester.requestPropsSeer(token, propSub, this);
				propSub = propsSeer.getProperty(token, PropertyName.propsSeer_angleViewPerceivingEvents, PropertyName.propsSeer_angleViewPerceivingEvents.toString());
				myPrint("Button " + index + " clicked: " + objectRequester.requestPropsSeer(token, propSub, this).toString() );
			}
			else {
				myPrint("Button " + index + " clicked "  );
			}
		}
		
		
///////////////////////////////////////////////////////////////////////////////////////////
//////////////////////implementing IObjectReceiver ///////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

		@Override
		public int receiveObject(int requestID, Object object) {
			objectRequester.receive(requestID, object);
			return 0;
		}

	}

	private void clickRasterField(int index) {
		
		
		raster[index].clicked();
	
	}

	public static void myPrint(String line) {
		System.out.println(line);
	}

	private void nextSignalState() {
		
		switch (state) {
		case 0:
			// lange Pause
			state++;
			break;
		case 1:
			// immer noch lange Pause
			state++;
			break;
		case 2:
			// immer noch lange Pause
			state++;
			break;
		case 3:
			// immer noch lange Pause
			state++;
			break;
		case 4:
			// immer noch lange Pause
			state++;
			break;
		case 5:
			clearAll();
			drawHeart();
			state++;
			break;
		case 6:
			// Herz stehen lassen
			state++;
			break;
		case 7:
			clearAll();
			state++;
			break;
		case 8:
			// lange Pause
			state++;
			break;
		case 9:
			// immer noch lange Pause
			state++;
			break;
		case 10:
			// immer noch lange Pause
			state++;
			break;
		case 11:
			// immer noch lange Pause
			state++;
			break;
		case 12:
			// immer noch lange Pause
			state++;
			break;
		case 13:
			drawM();
			state++;
			break;
		case 14:
			// M stehen lassen weitere 100 ms
			state++;
			break;
		case 15:
			clearAll();
			state++;
		case 16:
			// kurze Pause
			state++;
			break;
		case 17:
			drawA();
			state++;
			break;
		case 18:
			// A stehen lassen weitere 100 ms
			state++;
			break;
		case 19:
			clearAll();
			state++;
		case 20:
			// kurze Pause
			state++;
			break;
		case 21:
			drawN();
			state++;
			break;
		case 22:
			// N stehen lassen
			state++;
			break;
		case 23:
			clearAll();
			state++;
		case 24:
			// kurze Pause
			state++;
			break;
		case 25:
			drawU();
			state++;
			break;
		case 26:
			// U stehen lassen
			state++;
			break;
		case 27:
			clearAll();
			state++;
			break;
		case 28:
			// lange Pause
			state++;
			break;
		case 29:
			// immer noch lange Pause
			state++;
			break;
		case 30:
			// immer noch lange Pause
			state++;
			break;
		case 31:
			// immer noch lange Pause
			state++;
			break;
		case 32:
			// immer noch lange Pause
			state++;
			break;
		case 33:
			drawI();
			state++;
			break;
		case 34:
			// I stehen lassen
			state++;
			break;
		case 35:
			clearAll();
			state++;
			break;
		case 36:
			// lange Pause
			state++;
			break;
		case 37:
			// immer noch lange Pause
			state++;
			break;
		case 38:
			// immer noch lange Pause
			state++;
			break;
		case 39:
			// immer noch lange Pause
			state++;
			break;
		case 40:
			// immer noch lange Pause
			state++;
			break;
		case 41:
			drawL();
			state++;
			break;
		case 42:
			// L stehen lassen
			state++;
			break;
		case 43:
			clearAll();
			state++;
		case 44:
			// kurze Pause
			state++;
			break;
		case 45:
			drawO();
			state++;
			break;
		case 46:
			// O stehen lassen
			state++;
			break;
		case 47:
			clearAll();
			state++;
		case 48:
			// kurze Pause
			state++;
			break;
		case 49:
			drawV();
			state++;
			break;
		case 50:
			// V stehen lassen
			state++;
			break;
		case 51:
			clearAll();
			state++;
		case 52:
			// kurze Pause
			state++;
			break;
		case 53:
			drawE();
			state++;
			break;
		case 54:
			// E stehen lassen
			state++;
			break;
		case 55:
			clearAll();
			state++;
		case 56:
			// lange Pause
			state++;
			break;
		case 57:
			// immer noch lange Pause
			state++;
			break;
		case 58:
			// immer noch lange Pause
			state++;
			break;
		case 59:
			// immer noch lange Pause
			state++;
			break;
		case 60:
			// immer noch lange Pause
			state++;
			break;
		case 61:
			drawU();
			state++;
			break;
		case 62:
			// U2 stehen lassen
			state++;
			break;
		case 63:
			clearAll();
			state++;
		case 64:
			// lange Pause
			state++;
			break;
		case 65:
			// immer noch lange Pause
			state++;
			break;
		case 66:
			// immer noch lange Pause
			state++;
			break;
		case 67:
			// immer noch lange Pause
			state++;
			break;
		case 68:
			// immer noch lange Pause
			state++;
			break;
			
		default:
			state = 0;
		}

	}
	
	
	public void clearAll() {
		for(int i=0; i<100; i++)
		{
			this.raster[i].setBackground(Color.WHITE);
		}
	}
	
	
	public void  drawHeart() {
		this.raster[21].setBackground(Color.RED);
		this.raster[22].setBackground(Color.RED);
		this.raster[27].setBackground(Color.RED);
		this.raster[28].setBackground(Color.RED);
		this.raster[30].setBackground(Color.RED);
		this.raster[31].setBackground(Color.RED);
		this.raster[32].setBackground(Color.RED);
		this.raster[33].setBackground(Color.RED);
		this.raster[36].setBackground(Color.RED);
		this.raster[37].setBackground(Color.RED);
		this.raster[38].setBackground(Color.RED);
		this.raster[39].setBackground(Color.RED);
		this.raster[40].setBackground(Color.RED);
		this.raster[41].setBackground(Color.RED);
		this.raster[42].setBackground(Color.RED);
		this.raster[43].setBackground(Color.RED);
		this.raster[44].setBackground(Color.RED);
		this.raster[45].setBackground(Color.RED);
		this.raster[46].setBackground(Color.RED);
		this.raster[47].setBackground(Color.RED);
		this.raster[48].setBackground(Color.RED);
		this.raster[49].setBackground(Color.RED);
		this.raster[51].setBackground(Color.RED);
		this.raster[52].setBackground(Color.RED);
		this.raster[53].setBackground(Color.RED);
		this.raster[54].setBackground(Color.RED);
		this.raster[55].setBackground(Color.RED);
		this.raster[56].setBackground(Color.RED);
		this.raster[57].setBackground(Color.RED);
		this.raster[58].setBackground(Color.RED);
		this.raster[62].setBackground(Color.RED);
		this.raster[63].setBackground(Color.RED);
		this.raster[64].setBackground(Color.RED);
		this.raster[65].setBackground(Color.RED);
		this.raster[66].setBackground(Color.RED);
		this.raster[67].setBackground(Color.RED);
		this.raster[73].setBackground(Color.RED);
		this.raster[74].setBackground(Color.RED);
		this.raster[75].setBackground(Color.RED);
		this.raster[76].setBackground(Color.RED);
		this.raster[84].setBackground(Color.RED);
		this.raster[85].setBackground(Color.RED);
	}
	
	


	public void drawM() {
		this.raster[21].setBackground(Color.BLACK);
		this.raster[22].setBackground(Color.BLACK);
		this.raster[27].setBackground(Color.BLACK);
		this.raster[28].setBackground(Color.BLACK);
		this.raster[31].setBackground(Color.BLACK);
		this.raster[33].setBackground(Color.BLACK);
		this.raster[36].setBackground(Color.BLACK);
		this.raster[38].setBackground(Color.BLACK);
		this.raster[41].setBackground(Color.BLACK);
		this.raster[44].setBackground(Color.BLACK);
		this.raster[45].setBackground(Color.BLACK);
		this.raster[48].setBackground(Color.BLACK);
		this.raster[51].setBackground(Color.BLACK);
		this.raster[58].setBackground(Color.BLACK);
		this.raster[61].setBackground(Color.BLACK);
		this.raster[68].setBackground(Color.BLACK);
		this.raster[71].setBackground(Color.BLACK);
		this.raster[78].setBackground(Color.BLACK);
		this.raster[81].setBackground(Color.BLACK);
		this.raster[88].setBackground(Color.BLACK);
		
	}
	public void drawA() {
		this.raster[14].setBackground(Color.BLACK);
		this.raster[15].setBackground(Color.BLACK);
		this.raster[23].setBackground(Color.BLACK);
		this.raster[26].setBackground(Color.BLACK);
		this.raster[32].setBackground(Color.BLACK);
		this.raster[37].setBackground(Color.BLACK);
		this.raster[42].setBackground(Color.BLACK);
		this.raster[47].setBackground(Color.BLACK);
		this.raster[52].setBackground(Color.BLACK);
		this.raster[53].setBackground(Color.BLACK);
		this.raster[54].setBackground(Color.BLACK);
		this.raster[55].setBackground(Color.BLACK);
		this.raster[56].setBackground(Color.BLACK);
		this.raster[57].setBackground(Color.BLACK);
		this.raster[62].setBackground(Color.BLACK);
		this.raster[67].setBackground(Color.BLACK);
		this.raster[72].setBackground(Color.BLACK);
		this.raster[77].setBackground(Color.BLACK);
		this.raster[82].setBackground(Color.BLACK);
		this.raster[87].setBackground(Color.BLACK);
		
	}
	public void drawN() {
		
		this.raster[11].setBackground(Color.BLACK);
		this.raster[12].setBackground(Color.BLACK);
		this.raster[17].setBackground(Color.BLACK);
		this.raster[21].setBackground(Color.BLACK);
		this.raster[23].setBackground(Color.BLACK);
		this.raster[27].setBackground(Color.BLACK);
		this.raster[31].setBackground(Color.BLACK);
		this.raster[34].setBackground(Color.BLACK);
		this.raster[37].setBackground(Color.BLACK);
		this.raster[41].setBackground(Color.BLACK);
		this.raster[44].setBackground(Color.BLACK);
		this.raster[47].setBackground(Color.BLACK);
		this.raster[51].setBackground(Color.BLACK);
		this.raster[55].setBackground(Color.BLACK);
		this.raster[57].setBackground(Color.BLACK);
		this.raster[61].setBackground(Color.BLACK);
		this.raster[65].setBackground(Color.BLACK);
		this.raster[67].setBackground(Color.BLACK);
		this.raster[71].setBackground(Color.BLACK);
		this.raster[76].setBackground(Color.BLACK);
		this.raster[77].setBackground(Color.BLACK);
		this.raster[81].setBackground(Color.BLACK);
		this.raster[87].setBackground(Color.BLACK);
		this.raster[87].setBackground(Color.BLACK);

	}
	public void drawU() {
		
		this.raster[11].setBackground(Color.BLACK);
		this.raster[17].setBackground(Color.BLACK);
		this.raster[21].setBackground(Color.BLACK);
		this.raster[27].setBackground(Color.BLACK);
		this.raster[31].setBackground(Color.BLACK);
		this.raster[37].setBackground(Color.BLACK);
		this.raster[41].setBackground(Color.BLACK);
		this.raster[47].setBackground(Color.BLACK);
		this.raster[51].setBackground(Color.BLACK);
		this.raster[56].setBackground(Color.BLACK);
		this.raster[57].setBackground(Color.BLACK);
		this.raster[61].setBackground(Color.BLACK);
		this.raster[66].setBackground(Color.BLACK);
		this.raster[67].setBackground(Color.BLACK);
		this.raster[72].setBackground(Color.BLACK);
		this.raster[75].setBackground(Color.BLACK);
		this.raster[77].setBackground(Color.BLACK);
		this.raster[83].setBackground(Color.BLACK);
		this.raster[84].setBackground(Color.BLACK);
		this.raster[87].setBackground(Color.BLACK);
	}
	public void drawI() {
		
		this.raster[14].setBackground(Color.BLACK);
		this.raster[15].setBackground(Color.BLACK);
		this.raster[24].setBackground(Color.BLACK);
		this.raster[25].setBackground(Color.BLACK);
		this.raster[34].setBackground(Color.BLACK);
		this.raster[35].setBackground(Color.BLACK);
		this.raster[44].setBackground(Color.BLACK);
		this.raster[45].setBackground(Color.BLACK);
		this.raster[54].setBackground(Color.BLACK);
		this.raster[55].setBackground(Color.BLACK);
		this.raster[64].setBackground(Color.BLACK);
		this.raster[65].setBackground(Color.BLACK);
		this.raster[74].setBackground(Color.BLACK);
		this.raster[75].setBackground(Color.BLACK);
		this.raster[84].setBackground(Color.BLACK);
		this.raster[85].setBackground(Color.BLACK);
	}
	public void drawL() {
		
		this.raster[13].setBackground(Color.BLACK);
		this.raster[23].setBackground(Color.BLACK);
		this.raster[33].setBackground(Color.BLACK);
		this.raster[43].setBackground(Color.BLACK);
		this.raster[53].setBackground(Color.BLACK);
		this.raster[63].setBackground(Color.BLACK);
		this.raster[73].setBackground(Color.BLACK);
		this.raster[83].setBackground(Color.BLACK);
		this.raster[84].setBackground(Color.BLACK);
		this.raster[85].setBackground(Color.BLACK);
		this.raster[86].setBackground(Color.BLACK);
		this.raster[87].setBackground(Color.BLACK);
	}
	public void drawO() {
		
	this.raster[14].setBackground(Color.BLACK);
	this.raster[15].setBackground(Color.BLACK);
	this.raster[23].setBackground(Color.BLACK);
	this.raster[26].setBackground(Color.BLACK);
	this.raster[32].setBackground(Color.BLACK);
	this.raster[37].setBackground(Color.BLACK);
	this.raster[41].setBackground(Color.BLACK);
	this.raster[48].setBackground(Color.BLACK);
	this.raster[51].setBackground(Color.BLACK);
	this.raster[58].setBackground(Color.BLACK);
	this.raster[62].setBackground(Color.BLACK);
	this.raster[67].setBackground(Color.BLACK);
	this.raster[73].setBackground(Color.BLACK);
	this.raster[76].setBackground(Color.BLACK);
	this.raster[84].setBackground(Color.BLACK);
	this.raster[85].setBackground(Color.BLACK);
	}
	public void drawV() {
		
		this.raster[11].setBackground(Color.BLACK);
		this.raster[18].setBackground(Color.BLACK);
		this.raster[21].setBackground(Color.BLACK);
		this.raster[28].setBackground(Color.BLACK);
		this.raster[31].setBackground(Color.BLACK);
		this.raster[38].setBackground(Color.BLACK);
		this.raster[41].setBackground(Color.BLACK);
		this.raster[48].setBackground(Color.BLACK);
		this.raster[52].setBackground(Color.BLACK);
		this.raster[57].setBackground(Color.BLACK);
		this.raster[62].setBackground(Color.BLACK);
		this.raster[67].setBackground(Color.BLACK);
		this.raster[73].setBackground(Color.BLACK);
		this.raster[76].setBackground(Color.BLACK);
		this.raster[84].setBackground(Color.BLACK);
		this.raster[85].setBackground(Color.BLACK);
	}
	public void drawE() {
		
		this.raster[12].setBackground(Color.BLACK);
		this.raster[13].setBackground(Color.BLACK);
		this.raster[14].setBackground(Color.BLACK);
		this.raster[15].setBackground(Color.BLACK);
		this.raster[16].setBackground(Color.BLACK);
		this.raster[22].setBackground(Color.BLACK);
		this.raster[32].setBackground(Color.BLACK);
		this.raster[42].setBackground(Color.BLACK);
		this.raster[43].setBackground(Color.BLACK);
		this.raster[44].setBackground(Color.BLACK);
		this.raster[45].setBackground(Color.BLACK);
		this.raster[46].setBackground(Color.BLACK);
		this.raster[52].setBackground(Color.BLACK);
		this.raster[62].setBackground(Color.BLACK);
		this.raster[72].setBackground(Color.BLACK);
		this.raster[82].setBackground(Color.BLACK);
		this.raster[83].setBackground(Color.BLACK);
		this.raster[84].setBackground(Color.BLACK);
		this.raster[85].setBackground(Color.BLACK);
		this.raster[86].setBackground(Color.BLACK);
	}
}


