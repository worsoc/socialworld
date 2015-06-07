/*
* Social World
* Copyright (C) 2015  Mathias Sikos
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

import org.socialworld.actions.move.Path;
import org.socialworld.attributes.Attribute;
import org.socialworld.attributes.AttributeArray;
import org.socialworld.calculation.FunctionByMatrix_Matrix;
import org.socialworld.calculation.Vector;
import org.socialworld.calculation.application.AttributeCalculator;
import org.socialworld.core.Event;
import org.socialworld.knowledge.KnownPathsPool;

/**
 * @author Mathias Sikos
 *
 */
public class StateAnimal extends StateSimulationObject {

	private AttributeArray attributes;
	private FunctionByMatrix_Matrix attributeCalculatorMatrix;

	private Vector directionChest;
	private Vector directionView;
	private Vector directionMove;


	private KnownPathsPool knownPathsPool;

	public StateAnimal() {
		super();
		
		knownPathsPool = new KnownPathsPool();
	}

	void calculateEventInfluence(Event event) {
		
		super.calculateEventInfluence(event);
		
		AttributeCalculator.calculateAttributesChangedByEvent(event, this);
		
	}

	public void setAttributes(AttributeArray attributes) {
		this.attributes = attributes;
	}
	
	public AttributeArray getAttributes() {
		return new AttributeArray(this.attributes);
	}
	
	void setMatrix(FunctionByMatrix_Matrix matrix) {
		 this.attributeCalculatorMatrix  = matrix;
	}
	
	public FunctionByMatrix_Matrix getMatrix() {
		return new FunctionByMatrix_Matrix(attributeCalculatorMatrix, Attribute.NUMBER_OF_ATTRIBUTES);
	}

	public void setDirectionChest(Vector directionChest) {
		this.directionChest = directionChest;
	}

	public void setDirectionView(Vector directionView) {
		this.directionView = directionView;
	}

	public void setDirectionMove(Vector directionMove) {
		this.directionMove = directionMove;
	}

	public Vector getDirectionChest() {
		return new Vector(this.directionChest);
	}
	
	public Vector getDirectionView() {
		return new Vector(this.directionView);
	}

	public Vector getDirectionMove() {
		return new Vector(this.directionMove);
	}

	public KnownPathsPool getKnownPathsPool() {
		return this.knownPathsPool;
	}
	
	public void addPath(Path path)  {
		this.knownPathsPool.addPath(path);
	}

}
