package org.socialworld.simpleclient.actions;


import org.apache.log4j.Logger;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.socialworld.SocialWorld;
import org.socialworld.core.Simulation;
import org.socialworld.objects.Human;
import org.socialworld.objects.SimulationObjectType;

public class AddHumanAction implements IWorkbenchWindowActionDelegate {
	
	private static final Logger logger = Logger.getLogger(AddHumanAction.class);

	public void dispose() {
		//  Auto-generated method stub

	}

	public void init(IWorkbenchWindow window) {
		//  Auto-generated method stub

	}

	public void run(IAction action) {
		Human human;
		Simulation simulation = SocialWorld.getCurrent().getSimulation();
		// objectID = 0 means that a new human is created
		// it doesn't exist in any data source yet
		human = (Human) simulation.createSimulationObject(SimulationObjectType.human, 0);
		logger.debug("Added new human object to human list: " + human);
		// only for test visualize we use an access to the human list
		logger.debug("Human list: " + simulation.getObjectMaster().getHumans() );
	}

	public void selectionChanged(IAction action, ISelection selection) {
		//  Auto-generated method stub

	}

}
