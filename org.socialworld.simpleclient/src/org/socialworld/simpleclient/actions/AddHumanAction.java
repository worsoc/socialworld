package org.socialworld.simpleclient.actions;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.socialworld.core.ObjectManager;
import org.socialworld.core.Simulation;
import org.socialworld.objects.Human;
import org.socialworld.simpleclient.Activator;

public class AddHumanAction implements IWorkbenchWindowActionDelegate {
	
	private static final Logger logger = Logger.getLogger(AddHumanAction.class);

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(IWorkbenchWindow window) {
		// TODO Auto-generated method stub

	}

	@Override
	public void run(IAction action) {
		Human human = new Human();
		logger.debug("Add new human object to human list: " + human);
		List<Human> humans = ObjectManager.getCurrent().getHumans();
		humans.add(human);
		logger.debug("Human list: " + humans);
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

}
