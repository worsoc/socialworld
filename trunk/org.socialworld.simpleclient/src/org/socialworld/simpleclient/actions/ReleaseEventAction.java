package org.socialworld.simpleclient.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.socialworld.SocialWorld;
import org.socialworld.core.Event;
import org.socialworld.core.EventMaster;
import org.socialworld.core.Simulation;
import org.socialworld.datasource.EventPool;

public class ReleaseEventAction implements IWorkbenchWindowActionDelegate {

	@Override
	public void dispose() {
		//  Auto-generated method stub

	}

	@Override
	public void init(IWorkbenchWindow window) {
		//  Auto-generated method stub

	}

	@Override
	public void run(IAction action) {
		Event event;
		EventMaster eventMaster;
		Simulation simulation = SocialWorld.getCurrent().getSimulation();
		event = EventPool.getInstance().getEvent(1);
		eventMaster = simulation.getEventMaster();
		eventMaster.addEvent(event);
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		//  Auto-generated method stub

	}

}
