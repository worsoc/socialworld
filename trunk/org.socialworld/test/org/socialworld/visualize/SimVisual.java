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
import java.util.concurrent.TimeUnit;

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
import org.socialworld.objects.Human;
import org.socialworld.objects.State;

public class SimVisual {

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
	



	private JFrame  frameActions;
	
	private JFrame frameAttributes;

	private JPanel panelAttributeList;
	private JPanel panelRaster;
	private RasterField raster[];

	private JTextField attributeLines[];
	private int lastChangedObjID = 100;
	int[] objIDforRow;
	
	public SimVisual() {
		init();
//		drawHeart();
//		drawM();
//		drawA();
//		drawN();
//		drawU();
//		drawI();
//		drawL();
//		drawO();
//		drawV();
		drawE();
		/*
		try
		{
		    Thread.sleep(10000);
		}
		catch(InterruptedException ex)
		{
		    Thread.currentThread().interrupt();
		}
		*/
		//clearAll();
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
/*			Color color = this.getBackground();
			Border thickBorder = new LineBorder(color, 4);
			this.setBorder(thickBorder);
			this.setBackground(Color.WHITE);
			this.clear();
*/			
		}
		
		private void startAction(AbstractAction action) {
/*			
			ActionType type = action.getType();
			ActionMode mode = action.getMode();
			float intensity;
			
			Color color;

			
			

			switch (type) {
			case bodilyFunction:
				color = COLOR_CORNSILK;
				break;
			case move:
				color = Color.BLUE;
				break;
			case examine:
				color = Color.YELLOW;
				break;
			case equip:
				color = Color.CYAN;
				break;
			case handleItem:
				color = Color.GREEN;
				break;
			case touch:
				color = Color.PINK;
				break;
			case punch:
				color = Color.ORANGE;
				break;
			case useWeapon:
				color = Color.MAGENTA;
				break;
			case hear:
				color = Color.WHITE;
				break;
			case talk:
				color = Color.LIGHT_GRAY;
				break;
			case say:
				color = Color.GRAY;
				break;
			default:
				color = Color.BLACK;
			}

			intensity = action.getIntensity();
			this.setBackground(color);
			this.setText(Integer.toString((int)intensity));
*/			
		}

		private void clicked() {
			Human human;
			Value prop;
			State state;
			Value propSub;
			
			human = Simulation.getInstance().getObjectMaster().getHumans().get(index);
			prop = human.getProperty(SimulationCluster.test, PropertyName.stateSeer);
			state = (State) prop.getValue();
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
	
	
	public void clearAll() {
		for(int i=0; i<100; i++)
		{
			this.raster[i].setBackground(Color.WHITE);
		}
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

*/