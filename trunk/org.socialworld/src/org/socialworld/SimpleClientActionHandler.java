package org.socialworld;

import org.socialworld.objects.access.HiddenHuman;
import org.socialworld.actions.ActionMode;
import org.socialworld.actions.ActionType;
import org.socialworld.actions.move.ActionMove;
import org.socialworld.attributes.Time;
import org.socialworld.calculation.Vector;

public class SimpleClientActionHandler  {

	private static SimpleClientActionHandler instance;
	
	private HiddenHuman human1;
	private HiddenHuman human2;
	private HiddenHuman human3;
	private HiddenHuman actualHuman;

	private SimpleClientActionHandler() {}
	
	public static SimpleClientActionHandler  getInstance() {
		if (instance == null)	instance = new SimpleClientActionHandler();
		return instance;
	}
	
	public void setHumanWrite(int nr, final HiddenHuman human) {
		
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
					new Vector(0,10,0),
					10, new Time(false, 1000), new Time(false,2000),
					10, 500) );
			break;
		case 2:   // step back
			actualHuman.setAction(new ActionMove(ActionType.move ,  ActionMode.run,
					new Vector(0,-10,0),
					10, new Time(false,1000), new Time(false,2000),
					10, 500) );
			break;
		}
	}
}
