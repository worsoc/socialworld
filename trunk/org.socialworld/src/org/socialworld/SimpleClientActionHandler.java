package org.socialworld;

import org.apache.log4j.Logger;
import org.socialworld.objects.IHumanWrite;
import org.socialworld.objects.WriteAccessToHuman;
import org.socialworld.actions.ActionMove;
import org.socialworld.attributes.ActionMode;
import org.socialworld.attributes.ActionType;
import org.socialworld.attributes.Time;
import org.socialworld.calculation.Vector;

public class SimpleClientActionHandler implements IHumanWrite {
	protected static final Logger logger = Logger.getLogger(SimpleClientActionHandler.class);

	private static SimpleClientActionHandler instance;
	
	private WriteAccessToHuman human1;
	private WriteAccessToHuman human2;
	private WriteAccessToHuman human3;
	private WriteAccessToHuman actualHuman;

	private SimpleClientActionHandler() {}
	
	public static SimpleClientActionHandler  getInstance() {
		if (instance == null)	instance = new SimpleClientActionHandler();
		return instance;
	}
	
	public void setHumanWrite(int nr, final WriteAccessToHuman human) {
		
		switch (nr) {
		case 1:
			human1 = human;
			break;
		case 2:
			human2 = human;
			break;
		case 3:
			human3 = human;
			break;
		}
	}
	
	public void chooseHuman( int nr) {
		logger.debug("Mensch " + nr + " angesprochen");
		switch (nr) {
		case 1:
			actualHuman = human1;
			break;
		case 2:
			actualHuman = human2;
			break;
		case 3:
			actualHuman = human3;
			break;
		}
		
	}
	
	public void doAction (int action) {
		switch (action) {
		case 1:   // step forward
			actualHuman.setAction(new ActionMove(ActionType.move ,  ActionMode.run,
					actualHuman.getObject(), new Vector(0,10,0),
					10, new Time(1000), new Time(2000),
					10, 500), this );
			break;
		case 2:   // step back
			actualHuman.setAction(new ActionMove(ActionType.move ,  ActionMode.run,
					actualHuman.getObject(), new Vector(0,-10,0),
					10, new Time(1000), new Time(2000),
					10, 500), this );
			break;
		}
	}
}
