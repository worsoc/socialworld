package org.socialworld.visualize;

import java.awt.Color;
import java.awt.GridLayout;

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

public class SimVisual {

	
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
				color = Color.BLACK;
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
				color = Color.RED;
				break;
			case hear:
				color = Color.LIGHT_GRAY;
				break;
			case talk:
				color = Color.GRAY;
				break;
			case say:
				color = Color.DARK_GRAY;
				break;
			default:
				color = Color.WHITE;
			}

			intensity = action.getIntensity();
			this.setBackground(color);
			this.setText(Integer.toString((int)intensity));
			
		}

		
	}

	public static void myPrint(String line) {
		System.out.println(line);
	}

}
