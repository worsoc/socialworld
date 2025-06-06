/*
* Social World
* Copyright (C) 2014  Mathias Sikos
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
package org.socialworld;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.socialworld.actions.AbstractAction;
import org.socialworld.attributes.AttributeArray;
import org.socialworld.core.Simulation;
import org.socialworld.data.FillWithTestData_ACM;
import org.socialworld.data.FillWithTestData_Position;
import org.socialworld.visualize.SimVisual;



/**
 * @author Mathias Sikos (MatWorsoc)
 * 
 */
public class SocialWorld  {
	
	public static final String PLUGIN_ID = "org.socialworld";


	private static SocialWorld currentObject;

	private static Simulation simulation;
	
	private static boolean  visualize = false;
	private static SimVisual visualizeSimulation;

/*	private SocialWorld() {
		super();
		simulation = Simulation.getInstance();
		
	}
*/	
	
	public static void main(String[] args)
	{
				
		visualizeSimulation = new SimVisual();
		visualizeSimulation.show();

		
//		int a = (int) Float.parseFloat("1" );
//		int b = (int) Float.parseFloat("3.87" );
		
		currentObject = getCurrent();
			
		simulation = Simulation.getInstance();
		
		Simulation.showMessage("myPrint", "!!! AttributeCalculator.calculateAttributesChangedByEvent verwendet getAttributesChangedBySimpleMatrix");
		Simulation.showMessage("myPrint", "Bitte Geduld. Wir starten bei Sekunde 0.");
		
	
	//	System.out.println(Cube.getDelta_(0, 45, 1234, 0.005F, 0.000F));
		
/*		
        Cube cube = new Cube(true);

        cube.splitCube(2, 45, 0, false); // 1 -> Tiefe
        //System.out.println(cube.innerCubes[0][0][0].innerCubes[0][1][1].address);
        cube.printAddress();
*/
		
//		test();
		
//		fillTestData();
		
		simulation.startSimulation();
		visualize = true;
		simulation.stopSimulation();
		
	}
	
	
	public static void startAction(int objID, AbstractAction action) {
		visualizeSimulation.startAction(objID, action);
	}

	public static void stopAction(int objID) {
		visualizeSimulation.stopAction(objID);
	}
	
	public static void showAttributeChanges(int objID, AttributeArray attributes) {
		if (visualize) {
			visualizeSimulation.setAttributes(objID, attributes, true /* with changes */);
		}
	}

	public static void showNoAttributeChanges(int objID, AttributeArray attributes) {
		if (visualize) {
			visualizeSimulation.setAttributes(objID, attributes, false /* without changes */);
		}
	}

	public static SocialWorld getCurrent() {
		if (currentObject == null) {
			currentObject = new SocialWorld();
		}
		return currentObject;
	}

	public static SimVisual getSimVisual() {
		return visualizeSimulation;
	}
	
	/**
	 * @return the simulation
	 */
	public Simulation getSimulation() {
		return simulation;
	}

	private static void test()	{
		 
		 TryItOut.tryItOut();
	}
	
	
	
	private static void fillTestData() {
	
		
		
		FillWithTestData_ACM fillACM;
		fillACM = new FillWithTestData_ACM();
		
		fillACM.fill(4);
		
		
		FillWithTestData_Position fillPosition;
		fillPosition = new FillWithTestData_Position();
		fillPosition.fill();
		
	}
}

/* labels for comments
 * TODO
 * DEBUG
 * QUESTION
 * TEMP_SOLUTION
 * DEFAULT_INTERFACE_METHOD
 * -->
 * SUB_CLASS_IMPLEMENTATION
 * SUB_THREAD_IMPLEMENTATION
 * KNOWLEDGE
 */
