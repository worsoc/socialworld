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
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Value;
import org.socialworld.core.Simulation;
import org.socialworld.core.SocialWorldThread;
import org.socialworld.objects.Human;

public class SimVisual extends SocialWorldThread {
	
	public static final Color COLOR_BLACK = new Color(0,0,0);
	public static final Color COLOR_DIMGRAY = new Color(105,105,105);
	public static final Color COLOR_GRAY = new Color(128,128,128);
	public static final Color COLOR_DARKGRAY = new Color(169,169,169);
	public static final Color COLOR_SILVER = new Color(192,192,192);
	
	public static final Color COLOR_ALICEBLUE = new Color(240,248,255);
	public static final Color COLOR_LAVENDER = new Color(230,230,250);
	public static final Color COLOR_POWDERBLUE = new Color(176,224,230);
	public static final Color COLOR_LIGHTBLUE = new Color(173,216,230);
	public static final Color COLOR_LIGHTSKYBLUE = new Color(135,206,250);
	public static final Color COLOR_SKYBLUE = new Color(135,206,235);	
	public static final Color COLOR_DEEPSKYBLUE = new Color(0,191,255);
	public static final Color COLOR_LIGHTSTEELBLUE = new Color(176,196,222);
	public static final Color COLOR_DODGERBLUE = new Color(30,144,255);
	public static final Color COLOR_CORNFLOWERBLUE = new Color(100,149,237);
	public static final Color COLOR_STEELBLOW = new Color(70,130,180);
	public static final Color COLOR_CADETBLUE = new Color(95,158,160);
	public static final Color COLOR_MEDIUMSLATEBLUE = new Color(123,104,238);
	public static final Color COLOR_SLATEBLUE = new Color(106,90,205);
	public static final Color COLOR_DARKESLATEBLUE = new Color(72,61,139);
	public static final Color COLOR_ROYELBLUE = new Color(65,105,225);
	public static final Color COLOR_BLUE = new Color(0,0,255);
	public static final Color COLOR_MEDIUMBLUE = new Color(0,0,205);
	public static final Color COLOR_DARKEBLUE = new Color(0,0,139);
	public static final Color COLOR_NAVY = new Color(0,0,128);
	public static final Color COLOR_MIDNIGHTBLUE = new Color(25,25,112);
	public static final Color COLOR_BLUEVIOLET = new Color(138,43,226);
	public static final Color COLOR_INDIGO = new Color(75,0,130);
	
	public static final Color COLOR_CORNSILK = new Color(255,248,220);
	public static final Color COLOR_BLANCHEDALMOND = new Color(255,235,205);
	public static final Color COLOR_BISQUE = new Color(255,228,196);
	public static final Color COLOR_NAVAJOWHITE = new Color(255,222,173);
	public static final Color COLOR_WHEAT = new Color(245,222,179);
	public static final Color COLOR_SANDYBROWN = new Color(244,164,96);
	public static final Color COLOR_GOLDENROD = new Color(218,165,32);
	public static final Color COLOR_SADDLSEBROWN = new Color(139,69,19);
	public static final Color COLOR_SIENNA = new Color(160,82,45);
	public static final Color COLOR_BURLYWOOD =  new Color(222,184,135);
	public static final Color COLOR_TAN =  new Color(210,180,140);
	public static final Color COLOR_ROSYBROWN =  new Color(188,143,143);
	public static final Color COLOR_PERU =  new Color(205,133,63);
	public static final Color COLOR_CHOCOLATE =  new Color(210,105,30);
	public static final Color COLOR_BROWN =  new Color(165,42,42);
	public static final Color COLOR_MAROON = new Color(128,0,0);

	public static final Color COLOR_LIGHTCYAN = new Color(224,255,255);
	public static final Color COLOR_CYAN = new Color(0,255,255) ;
	public static final Color COLOR_AQUAMARINE = new Color(127,255,212) ;
	public static final Color COLOR_MEDIUMAQUAMARINE = new Color(102,205,170);	 
	public static final Color COLOR_PALETURQUOISE = new Color(175,238,238);
	public static final Color COLOR_TURQUOISE = new Color(64,224,208);
	public static final Color COLOR_MEDIUMTURQUOISE = new Color(72,209,204);
	public static final Color COLOR_DARKTURQUOISE = new Color(0,206,209);
	public static final Color COLOR_LIGHTSEAGREEN = new Color(32,178,170);
	public static final Color COLOR_DARKCYAN = new Color(0,139,139);
	public static final Color COLOR_TEAL = new Color(0,128,128);
	
	public static final Color COLOR_LIGHTGOLDENRODYELLOW = new Color(250,250,210);
	public static final Color COLOR_PALEGOLDENROD = new Color(238,232,170);
	public static final Color COLOR_KHAKI = new Color(240,230,140);
	public static final Color COLOR_GOLD = new Color(255,215,0);
	public static final Color COLOR_ORANGE = new Color(255,165,0);
	public static final Color COLOR_DARKORANGE = new Color(255,140,0);
	public static final Color COLOR_SADDLEBROWN = new Color(139,69,19);
	public static final Color COLOR_GOLDEN_YELLOW = new Color(255,223,0);
	public static final Color COLOR_METALLIC_GOLD = new Color(212,175,55);
	public static final Color COLOR_OLD_GOLD = new Color(207,181,59);
	public static final Color COLOR_VEGAS_GOLD = new Color(197,179,88);
	public static final Color COLOR_PALE_GOLD = new Color(230,190,138);
	public static final Color COLOR_GOLDEN_BROWN = new Color(153,101,21);
	
	public static final Color COLOR_LAWNGREEN = new Color(124,252,0);
	public static final Color COLOR_CHARTREUSE = new Color(127,255,0);
	public static final Color COLOR_LIMEGREEN = new Color(50,205,50);
	public static final Color COLOR_LIME = new Color(0,255,0);
	public static final Color COLOR_FORESTGREEN = new Color(34,139,34);
	public static final Color COLOR_GREEN = new Color(0,128,0);
	public static final Color COLOR_DARKGREEN = new Color(0,100,0);
	public static final Color COLOR_GREENYELLOW = new Color(173,255,47);
	public static final Color COLOR_YELLOWGREEN = new Color(154,205,50);
	public static final Color COLOR_SPRINGGREEN = new Color(0,255,127);
	public static final Color COLOR_MEDIUMSPRINGGREEN = new Color(0,250,154);
	public static final Color COLOR_LIGHTGREEN = new Color(144,238,144);
	public static final Color COLOR_PALEGREEN = new Color(152,251,152);
	public static final Color COLOR_DARKSEAGREEN = new Color(143,188,143);
	public static final Color COLOR_MEDIUMSEAGREEN = new Color(60,179,113);
	public static final Color COLOR_SEAGREEN = new Color(46,139,87);
	public static final Color COLOR_OLIVE = new Color(128,128,0);
	public static final Color COLOR_DARKOLIVEGREEN = new Color(85,107,47);
	public static final Color COLOR_OLIVEDRAB = new Color(107,142,35);

	public static final Color COLOR_GAINSBORO = new Color(220,220,220);
	public static final Color COLOR_LIGHTGRAY = new Color(211,211,211);
	public static final Color COLOR_LIGHTSLATEGRAY = new Color(119,136,153);
	public static final Color COLOR_SLATEGRAY = new Color(112,128,144);
	public static final Color COLOR_DARKSLATEGRAY = new Color(47,79,79);

	public static final Color COLOR_DARKRED = new Color(139,0,0);
	public static final Color COLOR_FIREBRICK = new Color(178,34,34);
	public static final Color COLOR_CRIMSONRGB = new Color(220,20,60);
	
	public static final Color COLOR_CORAL = new Color(255,127,80);
	public static final Color COLOR_TOMATO = new Color(255,99,71);
	public static final Color COLOR_ORANGERED = new Color(255,69,0);

	public static final Color COLOR_PINK = new Color(255,192,203);
	public static final Color COLOR_LIGHTPINK = new Color(255,182,193);
	public static final Color COLOR_HOTPINK = new Color(255,105,180);
	public static final Color COLOR_DEEPPINK = new Color(255,20,147);
	public static final Color COLOR_PALEVIOLETRED = new Color(219,112,147);
	public static final Color COLOR_MEDIUMVIOLETRED = new Color(199,21,133);
	
	public static final Color COLOR_AVENDER = new Color(230,230,250);
	public static final Color COLOR_THISTLE = new Color(216,191,216);
	public static final Color COLOR_PLUM = new Color(221,160,221);
	public static final Color COLOR_VIOLET = new Color(238,130,238);
	public static final Color COLOR_ORCHID = new Color(218,112,214);
	public static final Color COLOR_FUCHSIA = new Color(255,0,255);
	public static final Color COLOR_MAGENTA = new Color(255,0,255);
	public static final Color COLOR_MEDIUMORCHID = new Color(186,85,211);
	public static final Color COLOR_MEDIUMPURPLE = new Color(147,112,219);
	public static final Color COLOR_DARKVIOLET = new Color(148,0,211);
	public static final Color COLOR_DARKORCHID = new Color(153,50,204);
	public static final Color COLOR_DARKMAGENTA = new Color(139,0,139);
	public static final Color COLOR_PURPLE = new Color(128,0,128);
	
	public static final Color COLOR_LIGHTSALMON = new Color(255,160,122);
	public static final Color COLOR_SALMON = new Color(250,128,114);
	public static final Color COLOR_DARKSALMON = new Color(233,150,122);
	public static final Color COLOR_LIGHTCORAL = new Color(240,128,128);
	public static final Color COLOR_INDIANRED = new Color(205,92,92);
	public static final Color COLOR_CRIMSON = new Color(220,20,60);
	public static final Color COLOR_RED = new Color(255,0,0);;

	public static final Color COLOR_WHITE = new Color(255,255,255);
	public static final Color COLOR_SNOW = new Color(255,250,250);
	public static final Color COLOR_HONEYDEW = new Color(240,255,240);
	public static final Color COLOR_MINTCREAM= new Color(245,255,250);
	public static final Color COLOR_AZURE = new Color(240,255,255);
	public static final Color COLOR_GHOSTWHITE = new Color(248,248,255);
	public static final Color COLOR_WHITESMOKE = new Color(245,245,245);
	public static final Color COLOR_SEASHELL = new Color(255,245,238);
	public static final Color COLOR_BEIGE = new Color(245,245,220);
	public static final Color COLOR_OLDLACE = new Color(253,245,230);
	public static final Color COLOR_FLORALWHITE = new Color(255,250,240);
	public static final Color COLOR_IVORY = new Color(255,255,240);
	public static final Color COLOR_ANTIQUEWHITE = new Color(250,235,215);
	public static final Color COLOR_LINEN = new Color(250,240,230);
	public static final Color COLOR_LAVENDERBLUSH = new Color(255,240,245);
	public static final Color COLOR_MISTYROSE = new Color(255,228,225);
	
	public static final Color COLOR_LIGHTYELLOW = new Color(255,255,224);
	public static final Color COLOR_LEMONCHIFFON = new Color(255,250,205);
	public static final Color COLOR_PAPAYAWHIP = new Color(255,239,213);
	public static final Color COLOR_MOCCASIN = new Color(255,228,181);
	public static final Color COLOR_PEACHPUFF = new Color(255,218,185);
	public static final Color COLOR_DARKKHAKI = new Color(189,183,107);
	public static final Color COLOR_YELLOW = new Color(255,255,0);
	public static final Color COLOR_LIGHT_YELLOW1 = new Color(255,255,204);
	public static final Color COLOR_LIGHT_YELLOW2 = new Color(255,255,153);
	public static final Color COLOR_LIGHT_YELLOW3 = new Color(255,255,102);
  	public static final Color COLOR_LIGHT_YELLOW4 = new Color(255,255,51);
  	public static final Color COLOR_DARK_YELLOW1 = new Color(204,204,0);
  	public static final Color COLOR_DARK_YELLOW2 = new Color(153,153,0);
  	public static final Color COLOR_DARK_YELLOW3 = new Color(102,102,0);
  	public static final Color COLOR_DARK_YELLOW4 = new Color(51,51,0);
	
	
			
	private JFrame  frameActions;
	
	private JFrame frameAttributes;

	private JPanel panelAttributeList;
	private JPanel panelRaster;
	private RasterField raster[];

	private JTextField attributeLines[];
	private int lastChangedObjID = 100;
	int[] objIDforRow;
	
	
	private int sleepTime = 100;
	private int state; 
	
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

	
	public void setAttributes(int objID, AttributeArray attributes) {
		if (objID > 100) return;
		this.attributeLines[this.lastChangedObjID - 1].setBackground(Color.WHITE);
		this.attributeLines[objID-1].setBackground(Color.CYAN);
		this.attributeLines[objID-1].setText(objID + " - " + attributes.toString());
		this.lastChangedObjID = objID;
		
/*		
		int row = 0;
		String entry;
		
				
		// find row for objID
		for (int i = 0; i < 100; i++) {
			if (objIDforRow[i] == objID) {
				row = i;
				break;
			}
		}
		
		// move all entries before the found row to the next row
		for (int i = row; i > 0; i--) {
			objIDforRow[i] = objIDforRow[i-1] ;
			entry = this.attributeLines[i-1].getText();
			this.attributeLines[i].setText(entry);
		}
		
		this.attributeLines[0].setText(objID + " - " + attributes.toString());
		this.objIDforRow[0] = objID;
*/
		
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
		attributeLines = new JTextField[100];
		for (int i = 0; i < 100; i++) {
			objIDforRow[i] = i + 1;
			attributeLines[i] = new JTextField("" + (i + 1));
			panelAttributeList.add(attributeLines[i]);
		}

		frameAttributes = new JFrame();
		frameAttributes.setTitle("Attributes");
		frameAttributes.setBounds(100, 100, 1000, 600);
		frameAttributes.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frameAttributes.getContentPane().setLayout(new GridLayout(1,1));
		frameAttributes.getContentPane().add(panelAttributeList);
		
	}
	
	
	private class RasterField extends JButton {
		int index;
		
		static final long serialVersionUID = 1;
		
		
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
					color = COLOR_CORNSILK;
					break;
				case drink:
					color = COLOR_BLANCHEDALMOND;
					break;
				case eat:
					color = COLOR_BISQUE;
					break;
				case piss:
					color = COLOR_NAVAJOWHITE;
					break;
				case shit:
					color = COLOR_WHEAT;
					break;
				default:
				}
				break;
			case move:
				
				switch (mode) {
				case walk:
					color = COLOR_LIGHTCYAN;
					break;
				case run:
					color = COLOR_CYAN;
					break;
				case sneak:
					color = COLOR_MEDIUMAQUAMARINE;
					break;
				case jump:
					color = COLOR_TURQUOISE;
					break;
				case swim:
					color = COLOR_AQUAMARINE;
					break;
				case fly:
					color = COLOR_PALETURQUOISE;
					break;
				default:	
				}
				break;
			case examine:
				
				switch (mode) {
				case look:
					color = COLOR_SILVER;
					break;
				case smell:
					color = COLOR_GRAY;
					break;
				case taste:
					color = COLOR_DIMGRAY;
					break;
				case touch:
					color = COLOR_DARKGRAY;
					break;
				default:
				}
				break;
			case equip:
				
				switch (mode) {
				case takeItem:
					color = COLOR_GOLDEN_YELLOW;
					break;
				case dropItem:
					color = COLOR_METALLIC_GOLD;
					break;
				case switchItemToOtherHand:
					color = COLOR_OLD_GOLD;
					break;
				case setItemToInventory:
					color = COLOR_VEGAS_GOLD;
					break;
				case getItemFromInventory:
					color = COLOR_PALE_GOLD;
					break;
				default:
				}
				break;
			case handleItem:
				
				switch(mode) {
				case useTwoItems:
					color = COLOR_TURQUOISE;
					break;
				case useItemLeftHand:	
					color = COLOR_MEDIUMTURQUOISE;
					break;
				case useItemRightHand:
					color = COLOR_DARKTURQUOISE;
					break;
				case combineItems_AddRightToLeft:
					color = COLOR_LIGHTSEAGREEN;
					break;
				case combineItems_AddLeftToRight:
					color = COLOR_CADETBLUE;
					break;
				case pull:
					color = COLOR_DARKCYAN;
					break;
				case push:
					color = COLOR_TEAL;
					break;
				default:
				}
				break;
			case touch:
				
				switch (mode) {
				case hand:
				color = COLOR_BLUEVIOLET;
				break;
				case foot:
					color = COLOR_INDIGO;
					break; 
				default:
				}
				break;
			case punch:
				
				switch (mode) {
				case punchLeftFistStraight:
					color = COLOR_LIGHTSALMON;
					break; 
				case punchLeftFistSideways:
					color = COLOR_SALMON;
					break; 
				case punchLeftFistUpward:
					color = COLOR_DARKSALMON;
					break; 
				case punchRightFistStraight:
					color = COLOR_LIGHTCORAL;
					break; 
				case punchRightFistSideways:
					color = COLOR_INDIANRED;
					break; 
				case punchRightFistUpward:
					color = COLOR_CRIMSON;
					break; 
				default:
				}
				break;
			case useWeapon:
				
				switch(mode) {
				case weaponLeftStab:
					color = COLOR_CHARTREUSE;
					break; 
				case weaponLeftStroke:
					color = COLOR_LIMEGREEN;
					break; 
				case weaponLeftBackhand:
					color = COLOR_LIME;
					break; 
				case weaponRightStab:
					color = COLOR_FORESTGREEN;
					break; 
				case weaponRightStroke:
					color = COLOR_GREEN;
					break; 
				case weaponRightBackhand:
					color = COLOR_DARKGREEN;
					break; 
				case weaponClub:
					color = COLOR_GREENYELLOW;
					break; 
				default:
				}
				break;
			case hear:
				
				switch(mode) {
				case listenTo:
					color = COLOR_SNOW;
					break; 
				case understand:
					color = COLOR_HONEYDEW;
					break;
				default:
				}
				break;
			case talk:
				
				switch(mode) {
				case answerNormal:
					color = COLOR_PINK;
					break;
				case answerScream:
					color = COLOR_LIGHTPINK;
					break;
				case answerWhisper:
					color = COLOR_HOTPINK;
					break;
				case askNormal:
					color = COLOR_DEEPPINK;
					break;
				case askScream:
					color = COLOR_PALEVIOLETRED;
					break;
				case askWhisper:
					color = COLOR_MEDIUMVIOLETRED;
					break;
				default:
				}
				break;
			case say:
			
				switch(mode) {
				case normal:
					color = COLOR_LINEN;
					break;
				case scream:
					color = COLOR_LAVENDERBLUSH;
					break;
				case whisper:
					color = COLOR_MISTYROSE;
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
			prop = human.getProperty(SimulationCluster.test, PropertyName.stateSeer);
			state = (org.socialworld.objects.State) prop.getValue();
			if (state != null) {
				propSub = state.getValue("getAngleViewPerceivingEvents", "angleViewPerceivingEvents");
				myPrint("Button " + index + " clicked: " + propSub.getValue().toString() );
			}
			else {
				myPrint("Button " + index + " clicked "  );
			}
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



/*
 https://www.rapidtables.com/web/color

Color	HTML / CSS
Color Name	Hex Code
#RRGGBB	Decimal Code
(R,G,B)
 	cornsilk	#FFF8DC	rgb(255,248,220)
 	blanchedalmond	#FFEBCD	rgb(255,235,205)
 	bisque	#FFE4C4	rgb(255,228,196)
 	navajowhite	#FFDEAD	rgb(255,222,173)
 	wheat	#F5DEB3	rgb(245,222,179)
 	burlywood	#DEB887	rgb(222,184,135)
 	tan	#D2B48C	rgb(210,180,140)
 	rosybrown	#BC8F8F	rgb(188,143,143)
 	sandybrown	#F4A460	rgb(244,164,96)
 	goldenrod	#DAA520	rgb(218,165,32)
 	peru	#CD853F	rgb(205,133,63)
 	chocolate	#D2691E	rgb(210,105,30)
 	saddlebrown	#8B4513	rgb(139,69,19)
 	sienna	#A0522D	rgb(160,82,45)
 	brown	#A52A2A	rgb(165,42,42)
 	maroon	#800000	rgb(128,0,0)

  	black 	#000000 	rgb(0,0,0)
  	dimgray / dimgray 	#696969 	rgb(105,105,105)
  	gray / grey 	#808080 	rgb(128,128,128)
  	darkgray / darkgrey 	#A9A9A9 	rgb(169,169,169)
  	silver 	#C0C0C0 	rgb(192,192,192)
  	
  	aliceblue 	#F0F8FF 	rgb(240,248,255)
  	lavender 	#E6E6FA 	rgb(230,230,250)
  	powderblue 	#B0E0E6 	rgb(176,224,230)
  	lightblue 	#ADD8E6 	rgb(173,216,230)
  	lightskyblue 	#87CEFA 	rgb(135,206,250)
  	skyblue 	#87CEEB 	rgb(135,206,235)
  	deepskyblue 	#00BFFF 	rgb(0,191,255)
  	lightsteelblue 	#B0C4DE 	rgb(176,196,222)
  	dodgerblue 	#1E90FF 	rgb(30,144,255)
  	cornflowerblue 	#6495ED 	rgb(100,149,237)
  	steelblue 	#4682B4 	rgb(70,130,180)
  	cadetblue 	#5F9EA0 	rgb(95,158,160)
  	mediumslateblue 	#7B68EE 	rgb(123,104,238)
  	slateblue 	#6A5ACD 	rgb(106,90,205)
  	darkslateblue 	#483D8B 	rgb(72,61,139)
  	royalblue 	#4169E1 	rgb(65,105,225)
  	blue 	#0000FF 	rgb(0,0,255)
  	mediumblue 	#0000CD 	rgb(0,0,205)
  	darkblue 	#00008B 	rgb(0,0,139)
  	navy 	#000080 	rgb(0,0,128)
  	midnightblue 	#191970 	rgb(25,25,112)
  	blueviolet 	#8A2BE2 	rgb(138,43,226)
  	indigo 	#4B0082 	rgb(75,0,130)
  	
 	lightcyan	#E0FFFF	rgb(224,255,255) 
	cyan	#00FFFF	rgb(0,255,255) 
	aqua	#00FFFF	rgb(0,255,255) 
	aquamarine	#7FFFD4	rgb(127,255,212) 
	mediumaquamarine	#66CDAA	rgb(102,205,170)	 
	paleturquoise	#AFEEEE	rgb(175,238,238)	 
	turquoise	#40E0D0	rgb(64,224,208)	 
	mediumturquoise	#48D1CC	rgb(72,209,204)	 
	darkturquoise	#00CED1	rgb(0,206,209)	 
	lightseagreen	#20B2AA	rgb(32,178,170)	 
	cadetblue	#5F9EA0	rgb(95,158,160)	 
	darkcyan	#008B8B	rgb(0,139,139)	 
	teal	#008080	rgb(0,128,128)
	
	lightgoldenrodyellow	#FAFAD2	rgb(250,250,210)	 
	palegoldenrod	#EEE8AA	rgb(238,232,170)	 
	khaki	#F0E68C	rgb(240,230,140)	 
	goldenrod	#DAA520	rgb(218,165,32)	 
	gold	#FFD700	rgb(255,215,0)	 
	orange	#FFA500	rgb(255,165,0)	 
	darkorange	#FF8C00	rgb(255,140,0)	 
	peru	#CD853F	rgb(205,133,63)	 
	chocolate	#D2691E	rgb(210,105,30)	 
	saddlebrown	#8B4513	rgb(139,69,19)	 
	sienna	#A0522D	rgb(160,82,45)
	Golden yellow	#FFDF00	rgb(255,223,0)	 
	Metallic gold	#D4AF37	rgb(212,175,55)	 
	Old gold	#CFB53B	rgb(207,181,59)	 
	Vegas gold	#C5B358	rgb(197,179,88)	 
	Pale gold	#E6BE8A	rgb(230,190,138)	 
	Golden brown	#996515	rgb(153,101,21)
	
	 lawngreen 	#7CFC00 	rgb(124,252,0)
  	chartreuse 	#7FFF00 	rgb(127,255,0)
  	limegreen 	#32CD32 	rgb(50,205,50)
  	lime 	#00FF00 	rgb(0.255.0)
  	forestgreen 	#228B22 	rgb(34,139,34)
  	green 	#008000 	rgb(0,128,0)
  	darkgreen 	#006400 	rgb(0,100,0)
  	greenyellow 	#ADFF2F 	rgb(173,255,47)
  	yellowgreen 	#9ACD32 	rgb(154,205,50)
  	springgreen 	#00FF7F 	rgb(0,255,127)
  	mediumspringgreen 	#00FA9A 	rgb(0,250,154)
  	lightgreen 	#90EE90 	rgb(144,238,144)
  	palegreen 	#98FB98 	rgb(152,251,152)
  	darkseagreen 	#8FBC8F 	rgb(143,188,143)
  	mediumseagreen 	#3CB371 	rgb(60,179,113)
  	lightseagreen 	#20B2AA 	rgb(32,178,170)
  	seagreen 	#2E8B57 	rgb(46,139,87)
  	olive 	#808000 	rgb(128,128,0)
  	darkolivegreen 	#556B2F 	rgb(85,107,47)
  	olivedrab 	#6B8E23 	rgb(107,142,35)
  	
  	gainsboro 	#DCDCDC 	rgb(220,220,220)
  	lightgray / lightgrey 	#D3D3D3 	rgb(211,211,211)
  	silver 	#C0C0C0 	rgb(192,192,192)
  	darkgray / darkgrey 	#A9A9A9 	rgb(169,169,169)
  	gray / grey 	#808080 	rgb(128,128,128)
  	dimgray / dimgrey 	#696969 	rgb(105,105,105)
  	lightslategray / lightslategrey 	#778899 	rgb(119,136,153)
  	slategray / slategrey 	#708090 	rgb(112,128,144)
  	darkslategray / darkslategrey 	#2F4F4F 	rgb(47,79,79)
  	black 	#000000 	rgb(0,0,0)  
  	
  	maroon 	#800000 	rgb(128,0,0)
  	darkred 	#8B0000 	rgb(139,0,0)
  	brown 	#A52A2A 	rgb(165,42,42)
  	firebrick 	#B22222 	rgb(178,34,34)
  	crimson 	#DC143C 	rgb(220,20,60)	
  	
  	coral 	#FF7F50 	rgb(255,127,80)
  	tomato 	#FF6347 	rgb(255,99,71)
  	orangered 	#FF4500 	rgb(255,69,0)
  	gold 	#FFD700 	rgb(255,215,0)
  	orange 	#FFA500 	rgb(255,165,0)
  	darkorange 	#FF8C00 	rgb(255,140,0)
  		
  	pink 	#FFC0CB 	rgb(255,192,203)
  	lightpink 	#FFB6C1 	rgb(255,182,193)
  	hotpink 	#FF69B4 	rgb(255,105,180)
  	deeppink 	#FF1493 	rgb(255,20,147)
  	palevioletred 	#DB7093 	rgb(219,112,147)
  	mediumvioletred 	#C71585 	rgb(199,21,133)

  	avender 	#E6E6FA 	rgb(230,230,250)
  	thistle 	#D8BFD8 	rgb(216,191,216)
  	plum 	#DDA0DD 	rgb(221,160,221)
  	violet 	#EE82EE 	rgb(238,130,238)
  	orchid 	#DA70D6 	rgb(218,112,214)
  	fuchsia 	#FF00FF 	rgb(255,0,255)
  	magenta 	#FF00FF 	rgb(255,0,255)
  	mediumorchid 	#BA55D3 	rgb(186,85,211)
  	mediumpurple 	#9370DB 	rgb(147,112,219)
  	blueviolet 	#8A2BE2 	rgb(138,43,226)
  	darkviolet 	#9400D3 	rgb(148,0,211)
  	darkorchid 	#9932CC 	rgb(153,50,204)
  	darkmagenta 	#8B008B 	rgb(139,0,139)
  	purple 	#800080 	rgb(128,0,128)
  	indigo 	#4B0082 	rgb(75,0,130)
  	
  	lightsalmon 	#FFA07A 	rgb(255,160,122)
  	salmon 	#FA8072 	rgb(250,128,114)
  	darksalmon 	#E9967A 	rgb(233,150,122)
  	lightcoral 	#F08080 	rgb(240,128,128)
  	indianred 	#CD5C5C 	rgb(205,92,92)
  	crimson 	#DC143C 	rgb(220,20,60)
  	firebrick 	#B22222 	rgb(178,34,34)
  	red 	#FF0000 	rgb(255,0,0)
  	darkred 	#8B0000 	rgb(139,0,0)
  	maroon 	#800000 	rgb(128,0,0)
  	tomato 	#FF6347 	rgb(255,99,71)
  	orangered 	#FF4500 	rgb(255,69,0)
  	palevioletred 	#DB7093 	rgb(219,112,147)
  	
  	white 	#FFFFFF 	rgb(255,255,255)
  	snow 	#FFFAFA 	rgb(255,250,250)
  	honeydew 	#F0FFF0 	rgb(240,255,240)
  	mintcream 	#F5FFFA 	rgb(245,255,250)
  	azure 	#F0FFFF 	rgb(240,255,255)
  	aliceblue 	#F0F8FF 	rgb(240,248,255)
  	ghostwhite 	#F8F8FF 	rgb(248,248,255)
  	whitesmoke 	#F5F5F5 	rgb(245,245,245)
  	seashell 	#FFF5EE 	rgb(255,245,238)
  	beige 	#F5F5DC 	rgb(245,245,220)
  	oldlace 	#FDF5E6 	rgb(253,245,230)
  	floralwhite 	#FFFAF0 	rgb(255,250,240)
  	ivory 	#FFFFF0 	rgb(255,255,240)
  	antiquewhite 	#FAEBD7 	rgb(250,235,215)
  	linen 	#FAF0E6 	rgb(250,240,230)
  	lavenderblush 	#FFF0F5 	rgb(255,240,245)
  	mistyrose 	#FFE4E1 	rgb(255,228,225)
  	navajowhite 	#FFDEAD 	rgb(255,222,173)
  	
  	lightyellow 	#FFFFE0 	rgb(255,255,224)
  	lemonchiffon 	#FFFACD 	rgb(255,250,205)
  	lightgoldenrodyellow 	#FAFAD2 	rgb(250,250,210)
  	papayawhip 	#FFEFD5 	rgb(255,239,213)
  	moccasin 	#FFE4B5 	rgb(255,228,181)
  	peachpuff 	#FFDAB9 	rgb(255,218,185)
  	palegoldenrod 	#EEE8AA 	rgb(238,232,170)
  	khaki 	#F0E68C 	rgb(240,230,140)
  	darkkhaki 	#BDB76B 	rgb(189,183,107)
  	yellow 	#FFFF00 	rgb(255,255,0)
  	olive 	#808000 	rgb(128,128,0)
  	greenyellow 	#ADFF2F 	rgb(173,255,47)
  	yellowgreen 	#9ACD32 	rgb(154,205,50)
  	Light yellow1 	#FFFFCC 	rgb(255,255,204)
  	Light yellow2 	#FFFF99 	rgb(255,255,153)
  	Light yellow3 	#FFFF66 	rgb(255,255,102)
  	Light yellow4 	#FFFF33 	rgb(255,255,51)
  	Yellow 	#FFFF00 	rgb(255,255,0)
  	Dark yellow1 	#CCCC00 	rgb(204,204,0)
  	Dark yellow2 	#999900 	rgb(153,153,0)
  	Dark yellow3 	#666600 	rgb(102,102,0)
  	Dark yellow4 	#333300 	rgb(51,51,0)
*/