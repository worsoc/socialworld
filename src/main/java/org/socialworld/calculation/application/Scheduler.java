/*
* Social World
* Copyright (C) 2019  Mathias Sikos
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
package org.socialworld.calculation.application;

import org.socialworld.core.Event;
import org.socialworld.objects.StateAnimal;
import org.socialworld.objects.StateHuman;
import org.socialworld.objects.StateSimulationObject;
import org.socialworld.objects.access.HiddenAnimal;
import org.socialworld.objects.access.HiddenHuman;
import org.socialworld.objects.access.HiddenSimulationObject;

public class Scheduler{

	private static Scheduler instance;
	
	boolean isRunning = false;
	
	private final PositionCalculator threadPositionCalculator;
	private final ActionCreator threadActionCreator;
	private final AttributeCalculator threadAttributeCalculator;
	private final KnowledgeCalculator threadKnowledgeCalculator;
	private final TalkCalculator threadTalkCalculator;
	
	private Scheduler() {

		
		threadPositionCalculator = PositionCalculator.getInstance();
		threadActionCreator = ActionCreator.getInstance();
		threadAttributeCalculator = AttributeCalculator.getInstance();
		threadKnowledgeCalculator = KnowledgeCalculator.getInstance();
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
			threadKnowledgeCalculator.startThread();
			threadTalkCalculator.startThread();
		}
	}
	
	public void calculatePositionChangedByEvent(final Event event, final StateSimulationObject state, final HiddenSimulationObject hiddenWriteAccess) {

		if (event.getEventType().isRelevantForChangingPosition()) {
			threadPositionCalculator.calculatePositionChangedByEvent(event, state,  hiddenWriteAccess);
		}

	}
	
	public void createReaction(final Event event, final StateSimulationObject state, final HiddenSimulationObject hiddenWriteAccess) {
		
		threadActionCreator.createReaction(event, state, hiddenWriteAccess);
		
	}

	public void createAction(final StateSimulationObject state, final HiddenSimulationObject hiddenWriteAccess) {
		
		threadActionCreator.createAction(state, hiddenWriteAccess);
		
	}
	
	public void calculatePerception(final Event event, final StateAnimal stateAnimal, final HiddenAnimal hiddenWriteAccess) {

		if (event.getEventType().isEventToPercipient()) {
			threadKnowledgeCalculator.calculatePerception(event,  stateAnimal,  hiddenWriteAccess);
		}
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
		
		threadTalkCalculator.calculateTalkInfluencedByEvent(event,  stateHuman,  hiddenWriteAccess);
		
	}

}
