/*
 * Social World
 * Copyright (C) 2014  Mathias Sikos
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://gnu.org>.
 */
package org.socialworld;


import org.socialworld.actions.AbstractAction;
import org.socialworld.attributes.AttributeArray;
import org.socialworld.core.Simulation;
import org.socialworld.workbench.act.ActionCreationTool;
import org.socialworld.visualize.ISimVisualization;
import org.socialworld.visualize.SimDashboard;



/**
 * @author Mathias Sikos (MatWorsoc)
 * 
 */
public class SocialWorld  {
	
	public static final String PLUGIN_ID = "org.socialworld";


	private static SocialWorld currentObject;

	private static Simulation simulation;
	
	private static boolean  visualize = false;
	private static ISimVisualization visualizeSimulation;

/*	private SocialWorld() {
		super();
		simulation = Simulation.getInstance();
		
	}
*/	
	
	public static void main(String[] args)
	{
		//System.out.println("Headless: " + java.awt.GraphicsEnvironment.isHeadless());
	
		visualizeSimulation = new SimDashboard();
		visualizeSimulation.show();

		
//		int a = (int) Float.parseFloat("1" );
//		int b = (int) Float.parseFloat("3.87" );
		
		currentObject = getCurrent();
			
		simulation = Simulation.getInstance();
		
		Simulation.showMessage("myPrint", "Bitte Geduld. Wir starten bei Sekunde 0.");
		
	

		
//		test();
		
		simulation.startSimulation();
		visualize = true;
		visualizeSimulation.simulationStarted();
		if (GlobalSwitches.SHOW_ACTION_CREATION_TOOL) {
			new ActionCreationTool().show();
		}
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

	public static ISimVisualization getSimVisual() {
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
