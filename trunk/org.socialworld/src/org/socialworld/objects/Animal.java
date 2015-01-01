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
import org.socialworld.actions.move.Move;
import org.socialworld.actions.move.PathFinder;
import org.socialworld.attributes.Attribute;
import org.socialworld.attributes.AttributeArray;
import org.socialworld.core.Event;
import org.socialworld.calculation.FunctionByMatrix_Matrix;
import org.socialworld.calculation.Vector;
import org.socialworld.calculation.application.ActionCreator;
import org.socialworld.calculation.application.AttributeCalculator;
import org.socialworld.knowledge.KnownPathsPool;


/**
 * An animal is a simulation object with extensions to express the living kind.
 * There are methods for action handling and event effects.
 * @author Mathias Sikos (tyloesand)
 * 
 */
public class Animal extends SimulationObject {

	protected Move move;

	protected Vector directionChest;
	protected Vector directionView;
	
	protected AttributeArray attributes;
	protected FunctionByMatrix_Matrix attributeCalculatorMatrix;
	protected PathFinder pathFinder;
	protected KnownPathsPool knownPathsPool;
	
	public Animal() {
		super();
		attributes = new AttributeArray(Attribute.NUMBER_OF_ATTRIBUTES);
		pathFinder = new PathFinder(this);
		knownPathsPool = new KnownPathsPool();
	}


	void setAttributes(AttributeArray array, WriteAccessToAnimal guard) {
		if (this.guard == guard ) attributes = array;
	}
	
	
	public AttributeArray getAttributes() {
		if (attributes == null) return null;
		return new AttributeArray(attributes);
	}
	
	void setMatrix(FunctionByMatrix_Matrix matrix,  WriteAccessToAnimal guard) {
		if (this.guard == guard ) attributeCalculatorMatrix  = matrix;
	}
	
	public FunctionByMatrix_Matrix getMatrix() {
		return new FunctionByMatrix_Matrix(attributeCalculatorMatrix, Attribute.NUMBER_OF_ATTRIBUTES);
	}
	

	/**
	 * @return the directionChest
	 */
	public Vector getDirectionChest() {
		return directionChest;
	}


	/**
	 * @param directionChest the directionChest to set
	 */
	public void setDirectionChest(Vector directionChest) {
		this.directionChest = directionChest;
	}


	/**
	 * @return the directionView
	 */
	public Vector getDirectionView() {
		return directionView;
	}


	/**
	 * @param directionView the directionView to set
	 */
	public void setDirectionView(Vector directionView) {
		this.directionView = directionView;
	}



	/**
	 * @return the knownPathsPool
	 */
	public KnownPathsPool getKnownPathsPool() {
		return knownPathsPool;
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
		case changeMove:
			 changeMove(action);
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
		this.move.setMode(action.getMode());
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
	 * @return the Move
	 */
	public Move getMove() {
		return this.move;
	}

	/**
	 * @param Move
	 *            the Move to set
	 */
	public void setMove(final Move move) {
		this.move = move;
	}


	/**
	 * The method lets calculate how an event changes an object's state.
	 * 
	 * @param event
	 *            the event that effects to the object
	 */
	public void changeByEvent(final Event event) {
		AttributeArray tmp;
		
		tmp = AttributeCalculator.getAttributesChangedByEvent(event, this);
		this.attributes.set(tmp);
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
