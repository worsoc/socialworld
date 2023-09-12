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
package org.socialworld.core;

import org.socialworld.objects.*;
import org.socialworld.objects.access.HiddenHuman;
import org.socialworld.objects.access.HiddenItem;
import org.socialworld.objects.concrete.clothes.Cap;
import org.socialworld.objects.concrete.clothes.Shirt;
import org.socialworld.objects.concrete.clothes.Shoe;
import org.socialworld.objects.concrete.clothes.Sock;
import org.socialworld.objects.concrete.clothes.Trousers;
import org.socialworld.propertyChange.ChangedProperty;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.socialworld.SocialWorld;
import org.socialworld.actions.handle.Inventory;
import org.socialworld.attributes.ActualTime;
import org.socialworld.attributes.Position;
import org.socialworld.attributes.PropertyName;
import org.socialworld.attributes.Time;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.calculation.application.Scheduler;
import org.socialworld.collections.ObjectByPositionSearch;



/**
 * The class Simulation holds all simulation objects and runs the simulation
 * 
 * @author Mathias Sikos (MatWorsoc)
 * 
 */
public class Simulation extends SocialWorldThread {
	
	
	public static  int WITH_LOGGING = 1;
	public static  int WITH_ERROR_LOGGING = 1;

	private static Simulation instance;
	
	private final ObjectMaster objectMaster;

	// threads
	private final EventMaster eventMaster;
	private final ActionMaster actionMaster;
	private final RefreshMaster refreshMaster;
	private final PercipienceMaster percipienceMaster;

	private final ObjectByPositionSearch searchByPosition;
	
	private boolean run = false;
	private int sleepTime = 500;
	
	private Simulation() {
		
		
		this.objectMaster = ObjectMaster.getInstance();

		this.eventMaster = EventMaster.getInstance();
		this.actionMaster = ActionMaster.getInstance();
		this.refreshMaster = RefreshMaster.getInstance(this.objectMaster);
		this.percipienceMaster = PercipienceMaster.getInstance(this.objectMaster);
		
		this.searchByPosition = new ObjectByPositionSearch(1);

		AllWords.init();
	}
	
	/**
	 * The method gets back the only instance of the Simulation.
	 * 
	 * @return singleton object of Simulation
	 */
	public static Simulation getInstance() {
		if (instance == null) {
			instance = new Simulation();
		}
		return instance;
		
	}
	
	
	public void run() {
		while (isRunning()) {
			
			
			if (run) 
				nextTimeStep();
			else
				waitForStartTime();
			
			try {
				sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}	
	
	public void startSimulation() {
	
		
		Scheduler myCalcScheduler;
		myCalcScheduler = Scheduler.getInstance();
		myCalcScheduler.startThreads();
		
		// TODO das muss irgendwann wieder rein
		//this.objectMaster.loadSimulationObjects();

	
		this.startThread();
		
		IncompleteSimulationObject incompleteObject;
		
		HiddenHuman myHiddenHuman; 
		Human myHuman;
		
		HiddenItem myHiddenItem;
		Item myItem;

		List<HiddenHuman> hiddenHumans= new ArrayList<HiddenHuman>();
		List<Human> humans= new ArrayList<Human>();
		
	//	ValueProperty propInventory[] = new ValueProperty[3];

		for (int i = 0; i < 3; i++ ) {
			incompleteObject = createSimulationObject(SimulationObject_Type.human, "org.socialworld.objects.Human");
			
			myHiddenHuman = (HiddenHuman) incompleteObject.getHiddenObject();
			myHuman = (Human) incompleteObject.getObject();
			
			
			hiddenHumans.add(myHiddenHuman);
			humans.add(myHuman);
			
			System.out.println("Human(" + myHuman.getObjectID() + "): " + myHuman.getPosition(SimulationCluster.test).toString());
		}
		
		for (int i = 0; i < 3; i++ ) {
			Human human = humans.get(i);
			Position positionParentHuman = human.getPosition(SimulationCluster.test);
			
			Inventory inventory = new Inventory(SimulationObject_Type.human);
			hiddenHumans.get(i).setInventory(inventory);

			incompleteObject = createSimulationObject(SimulationObject_Type.item, "org.socialworld.objects.concrete.clothes.caps.TestCap");
			myHiddenItem = (HiddenItem) incompleteObject.getHiddenObject();
			myItem = (Item) incompleteObject.getObject();
			// set the human's position to the inventory item
			myHiddenItem.setPosition(positionParentHuman);
			
			inventory.setCap( (Cap) myItem) ;

			
			incompleteObject =  createSimulationObject(SimulationObject_Type.item, "org.socialworld.objects.concrete.clothes.shirts.TestShirt");
			myHiddenItem = (HiddenItem) incompleteObject.getHiddenObject();
			myItem = (Item) incompleteObject.getObject();
			// set the human's position to the inventory item
			myHiddenItem.setPosition(positionParentHuman);

			inventory.setShirt((Shirt) myItem);
			
			Shoe shoeLeft;
			incompleteObject =  createSimulationObject(SimulationObject_Type.item, "org.socialworld.objects.concrete.clothes.shoes.TestShoeLeft");
			myHiddenItem = (HiddenItem) incompleteObject.getHiddenObject();
			shoeLeft = (Shoe) incompleteObject.getObject();
			// set the human's position to the inventory item
			myHiddenItem.setPosition(positionParentHuman);
			
			Shoe shoeRight;
			incompleteObject = createSimulationObject(SimulationObject_Type.item, "org.socialworld.objects.concrete.clothes.shoes.TestShoeRight");
			myHiddenItem = (HiddenItem) incompleteObject.getHiddenObject();
			shoeRight = (Shoe) incompleteObject.getObject();
			// set the human's position to the inventory item
			myHiddenItem.setPosition(positionParentHuman);

			inventory.setShoes(shoeLeft, shoeRight);

			Sock sockLeft;
			incompleteObject = createSimulationObject(SimulationObject_Type.item, "org.socialworld.objects.concrete.clothes.socks.TestSockLeft");
			myHiddenItem = (HiddenItem) incompleteObject.getHiddenObject();
			sockLeft = (Sock) incompleteObject.getObject();
			// set the human's position to the inventory item
			myHiddenItem.setPosition(positionParentHuman);
			
			Sock sockRight;
			incompleteObject = createSimulationObject(SimulationObject_Type.item, "org.socialworld.objects.concrete.clothes.socks.TestSockRight");
			myHiddenItem = (HiddenItem) incompleteObject.getHiddenObject();
			sockRight = (Sock) incompleteObject.getObject();
			// set the human's position to the inventory item
			myHiddenItem.setPosition(positionParentHuman);

			inventory.setSocks(sockLeft, sockRight);

			incompleteObject = createSimulationObject(SimulationObject_Type.item, "org.socialworld.objects.concrete.clothes.trousers.TestTrousers");
			myHiddenItem = (HiddenItem) incompleteObject.getHiddenObject();
			myItem = (Item) incompleteObject.getObject();
			// set the human's position to the inventory item
			myHiddenItem.setPosition(positionParentHuman);
	
			inventory.setTrousers((Trousers) myItem) ;
			
//			inventory.setLeftHand(leftHand);
//			inventory.setRightHand(rightHand);
			

		}
/*
		for (int i = 0; i < 75; i++ ) {
			myItem = (Item) createSimulationObject(SimulationObject_Type.item, "org.socialworld.objects.concrete.eatable.fruits.Apple");
			System.out.println("Apple(" + myItem.getObjectID() + "):" + myItem.getPosition(SimulationCluster.test).toString());
		}
*/		
	}
	
	public void stopSimulation() {
		this.eventMaster.stopThread();
		this.actionMaster.stopThread();
		this.refreshMaster.stopThread();
		this.percipienceMaster.stopThread();
	}

	public EventMaster getEventMaster() {
		return this.eventMaster;
	}

	private void nextTimeStep() {
		Time actualTime = ActualTime.asTime();
		System.out.println("Zeit: " + actualTime.toString());
		actionMaster.nextSecond(actualTime);
	}


	private void waitForStartTime() {
		Time time = ActualTime.asTime();
		byte secondOfTheActualMinute = time.getSecond();
		
		if (secondOfTheActualMinute == 0) {
			sleepTime = 1000;
			run = true;
			this.eventMaster.startThread();
			this.actionMaster.startThread();
			this.refreshMaster.startThread();
			this.percipienceMaster.startThread();
		}
		else if (secondOfTheActualMinute < 50)
			sleepTime = 500;
		else if (secondOfTheActualMinute < 55)
			sleepTime = 100;
		else if (secondOfTheActualMinute < 59)
			sleepTime = 1;
		
		//System.out.println("Sekunde: " + secondOfTheActualMinute);
	}
	
	// for test visualizes there is a public access to the object master
	public ObjectMaster getObjectMaster() {
		return this.objectMaster;
	}
	
	private IncompleteSimulationObject createSimulationObject(
			SimulationObject_Type simulationObjectType,
			String fullClassName) {
		
		int incompleteObjectIndex;
		int objectID;
		IncompleteSimulationObject incompleteObject = new IncompleteSimulationObject();
//		HiddenSimulationObject hiddenObject = NoHiddenSimulationObject.getObjectNothing();
		SimulationObject createdObject = NoSimulationObject.getObjectNothing() ;
	
		incompleteObjectIndex = this.objectMaster.createSimulationObject(simulationObjectType, fullClassName);
		if (incompleteObjectIndex >= 0) {
			objectID = this.objectMaster.getObjectIDForIncompleteObjectIndex(incompleteObjectIndex);
			if (objectID > 0) {
				incompleteObject = this.objectMaster.getIncompleteObject(incompleteObjectIndex, objectID);
				createdObject = incompleteObject.getObject();
				if (createdObject.isSimulationObject()) {
					this.searchByPosition.addObject(createdObject);
				}
//				hiddenObject = incompleteObject.getHiddenObject();
			}
		}
		
		return incompleteObject; 
	}

	
	public SimulationObject getFirstByPosition(Position position) {
		this.searchByPosition.findNearestObject(position.getX(SimulationCluster.objectMaster), position.getY(SimulationCluster.objectMaster));
		return getNextByPosition();
	}
	
	public SimulationObject getNextByPosition() {
		int objectID;
		objectID = this.searchByPosition.getNextObjectID();
		return this.objectMaster.getSimulationObject(objectID);
	}
	
	public void propertyChanged(SimulationObject changedObject, ChangedProperty property) {
		switch (property){
			case position: positionChanged(changedObject);
		}
	}
	
	private void positionChanged(SimulationObject changedObject) {
		this.searchByPosition.changePosition(changedObject);
	}
	
	public static void showMessage(String methodName, Object something) {
		Method[] methods = SocialWorld.getSimVisual().getClass().getDeclaredMethods();
		
		for (Method m : methods) {
			String mname = m.getName();
			if (mname.equals(methodName)) {

					try {
						m.setAccessible(true);
					    m.invoke(Simulation.getInstance(), something);
		
					// Handle any exceptions thrown by method to be invoked.
					}
					catch (InvocationTargetException ite) {
					    Throwable cause = ite.getCause();
					    System.out.println( cause.getMessage());
					}
					catch (IllegalAccessException iae) {
						iae.printStackTrace();
					} 
					
					break;
			}
		}

	}
	
}
