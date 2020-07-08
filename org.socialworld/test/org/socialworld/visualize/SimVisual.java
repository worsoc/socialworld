/*
* Social World
* Copyright (C) 2019  Mathias Sikos
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
	
	public static final Color COLOR_MAROON = new Color(128,0,0);

	
	
	/*
  	burlywood	#DEB887	rgb(222,184,135)
 	tan	#D2B48C	rgb(210,180,140)
 	rosybrown	#BC8F8F	rgb(188,143,143)
 	peru	#CD853F	rgb(205,133,63)
 	chocolate	#D2691E	rgb(210,105,30)
 	brown	#A52A2A	rgb(165,42,42)
*/
	
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
			
		}

		private void clicked() {
			Human human;
			Value prop;
			State state;
			Value propSub;
			
			human = Simulation.getInstance().getObjectMaster().getHumans().get(index);
			prop = human.getProperty(SimulationCluster.test, PropertyName.simobj_stateSeer);
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

*/