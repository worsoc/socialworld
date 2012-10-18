package org.socialworld.simpleclient.actions;

import org.apache.log4j.Logger;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.socialworld.SocialWorld;
import org.socialworld.core.Simulation;

public class KillHumanAction implements IWorkbenchWindowActionDelegate {

	private static final Logger logger = Logger.getLogger(KillHumanAction.class);
	
	@Override
	public void dispose() {

	}

	@Override
	public void init(IWorkbenchWindow window) {

	}

	@Override
	public void run(IAction action) {
		Simulation simulation = SocialWorld.getCurrent().getSimulation();
		// only for test visualize we use an access to the human list
		simulation.getObjectMaster().getHumans().remove(simulation.getObjectMaster().getHumans().size() - 1);
		logger.debug("Human list: " + simulation.getObjectMaster().getHumans() );

	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {

	}

}
