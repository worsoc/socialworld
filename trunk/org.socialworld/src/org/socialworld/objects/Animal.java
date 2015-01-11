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
package org.socialworld.objects;

import org.socialworld.actions.AbstractAction;
import org.socialworld.actions.ActionMode;
import org.socialworld.actions.ActionType;
import org.socialworld.actions.move.PathFinder;
import org.socialworld.attributes.Attribute;
import org.socialworld.attributes.AttributeArray;
import org.socialworld.core.Event;
import org.socialworld.calculation.FunctionByMatrix_Matrix;
import org.socialworld.calculation.Vector;
import org.socialworld.calculation.application.ActionCreator;
import org.socialworld.knowledge.KnownPathsPool;


/**
 * An animal is a simulation object with extensions to express the living kind.
 * There are methods for action handling and event effects.
 * @author Mathias Sikos (tyloesand)
 * 
 */
public class Animal extends SimulationObject {

	private StateAnimal state;
	
	protected FunctionByMatrix_Matrix attributeCalculatorMatrix;
	protected PathFinder pathFinder;

	public Animal(StateAnimal state) {
		super(state);
		this.state = state;
		init();
	}
	

	private void init() {
		
		pathFinder = new PathFinder(this);
		
	}

	void setAttributes(AttributeArray array, WriteAccessToAnimal guard) {
		if (checkGuard(guard) ) 
			this.state.setAttributes(array);
	}
	
	public AttributeArray getAttributes() {
		return new AttributeArray(this.state.getAttributes());
	}
	
	void setMatrix(FunctionByMatrix_Matrix matrix,  WriteAccessToAnimal guard) {
		if (checkGuard(guard) ) attributeCalculatorMatrix  = matrix;
	}
	
	public FunctionByMatrix_Matrix getMatrix() {
		return new FunctionByMatrix_Matrix(attributeCalculatorMatrix, Attribute.NUMBER_OF_ATTRIBUTES);
	}

	/**
	 * @return the directionChest
	 */
	public Vector getDirectionChest() {
		return this.state.directionChest;
	}


	/**
	 * @param directionChest the directionChest to set
	 */
	void setDirectionChest(Vector directionChest, WriteAccessToAnimal guard) {
		if (checkGuard(guard))
			this.state.setDirectionChest(directionChest);
	}


	/**
	 * @return the directionView
	 */
	public Vector getDirectionView() {
		return this.state.directionView;
	}


	/**
	 * @param directionView the directionView to set
	 */
	void setDirectionView(Vector directionView, WriteAccessToAnimal guard) {
		if (checkGuard(guard))
			this.state.setDirectionChest(directionView);
	}

	/**
	 * @return the knownPathsPool
	 */
	public KnownPathsPool getKnownPathsPool() {
		return this.state.knownPathsPool;
	}

	/**
	 * @return the pathFinder
	 */
	public PathFinder getPathFinder() {
		return pathFinder;
	}

	/**
	 * Depending on the action type the method calls the according procedure
	 * with special implementation of the action.
	 */
	@Override
	protected void doAction(final ActionType type, final AbstractAction action) {

		
		switch (type) {
		case sleep:
			 sleep(action);
			break;
		case kick:
			 kick(action);
			break;
		case move:
			break;
		case say:
			break;
		default:
			break;
		}
	}

	/**
	 * The method changes the move mode of an animal.
	 * 
	 * @param action
	 */
	protected void changeMove(final AbstractAction action) {
	}


	/**
	 * The method holds the implementation of kicking.
	 * 
	 * @param action
	 */
	protected void kick(final AbstractAction action) {
	}

	/**
	 * The method holds the implementation of sleep.
	 * 
	 * @param action
	 */
	protected void sleep(final AbstractAction action) {
		final ActionMode mode = action.getMode();

		switch (mode) {
		case sleepIntentioned:
			break;
		case sleepCaused:
			break;
		default:
		}
	}


	/**
	 * The method lets calculate how an event changes an object's state.
	 * 
	 * @param event
	 *            the event that effects to the object
	 */
	public void changeByEvent(final Event event) {
		this.state.calculateEventInfluence(event);
	}

	/**
	 * The method lets calculate how an object reacts to an event.
	 * 
	 * @param event
	 *            the event that the object reacts to
	 */
	public void reactToEvent(final Event event) {
		AbstractAction reaction;

		logger.debug("reactToEvent");

		reaction =	ActionCreator.createAction(	this, event);
		
		actionHandler.insertAction(reaction);
		
	}
	

}
