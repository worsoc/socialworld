package org.socialworld.simpleclient.actions;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.socialworld.SocialWorld;
import org.socialworld.core.ObjectManager;
import org.socialworld.core.Simulation;
import org.socialworld.objects.Human;

public class AddHumanAction implements IWorkbenchWindowActionDelegate {
	
	private static final Logger logger = Logger.getLogger(AddHumanAction.class);

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void init(IWorkbenchWindow window) {
		// TODO Auto-generated method stub

	}

	public void run(IAction action) {
		Human human = new Human();
		logger.debug("Add new human object to human list: " + human);
		Simulation simulation = SocialWorld.getCurrent().getSimulation();
		List<Human> humans = simulation.getHumans();
		humans.add(human);
		logger.debug("Human list: " + humans);
	}

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

}
