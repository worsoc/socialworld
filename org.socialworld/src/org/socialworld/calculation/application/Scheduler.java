package org.socialworld.calculation.application;

import org.socialworld.core.Event;
import org.socialworld.objects.StateAnimal;
import org.socialworld.objects.StateHuman;
import org.socialworld.objects.StateSimulationObject;
import org.socialworld.objects.access.HiddenAnimal;
import org.socialworld.objects.access.HiddenHuman;
import org.socialworld.objects.access.HiddenSimulationObject;

public class Scheduler{

	////////////////////////////
	// TODO irgendwann wieder raus
	//just for testing
	private int number = 0;
	private String myThread = "HelloWorld" ;
	public void increment() {
		number++;
		show();
	}
	public void setThreadName(String name) {
		myThread = name;
	}
	private void show() {
		//System.out.println(number + " " + myThread);
	}
	//////////////////////////////
	
	private static Scheduler instance;
	
	boolean isRunning = false;
	
	private final PositionCalculator threadPositionCalculator;
	private final ActionCreator threadActionCreator;
	private final AttributeCalculator threadAttributeCalculator;
	private final TalkCalculator threadTalkCalculator;
	
	private Scheduler() {

		
		threadPositionCalculator = PositionCalculator.getInstance();
		threadActionCreator = ActionCreator.getInstance();
		threadAttributeCalculator = AttributeCalculator.getInstance();
		threadTalkCalculator = TalkCalculator.getInstance();
		

	}
	
	public static Scheduler getInstance() {
		if (instance == null) {
			instance = new Scheduler();
		}
		return instance;
	}
	
	public final  void startThreads() {
		if (!isRunning) {
			isRunning = true;
			threadPositionCalculator.startThread();
			threadActionCreator.startThread();
			threadAttributeCalculator.startThread();
			threadTalkCalculator.startThread();
		}
	}
	
	public void calculatePositionChangedByEvent(final Event event, final StateSimulationObject state, final HiddenSimulationObject hiddenWriteAccess) {

		threadPositionCalculator.calculatePositionChangedByEvent(event, state,  hiddenWriteAccess);

	}
	
	public void createReaction(final Event event, final StateSimulationObject state, final HiddenSimulationObject hiddenWriteAccess) {
		
		threadActionCreator.createReaction(event, state, hiddenWriteAccess);
		
	}
	
	public void calculateAttributesChangedByEvent(final Event event, final StateAnimal stateAnimal, final HiddenAnimal hiddenWriteAccess) {

		threadAttributeCalculator.calculateAttributesChangedByEvent(event,  stateAnimal,  hiddenWriteAccess);

	}
	
	public void calculateAttributesChangedByComplexMatrix(final StateAnimal stateAnimal, final HiddenAnimal hiddenWriteAccess) {

		threadAttributeCalculator.calculateAttributesChangedByComplexMatrix( stateAnimal,  hiddenWriteAccess);

	}
	
	public void calculateAttributesChangedBySimpleMatrix(final StateAnimal stateAnimal, final HiddenAnimal hiddenWriteAccess) {

		threadAttributeCalculator.calculateAttributesChangedBySimpleMatrix( stateAnimal,  hiddenWriteAccess);

	}
	
	public void calculateTalkChangedByEvent(final Event event, final StateHuman stateHuman, final HiddenHuman hiddenWriteAccess) {
		
		threadTalkCalculator.calculateTalkChangedByEvent(event,  stateHuman,  hiddenWriteAccess);
		
	}

}
