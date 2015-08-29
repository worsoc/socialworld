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
import org.socialworld.attributes.AttributeArray;
import org.socialworld.calculation.FunctionByMatrix;
import org.socialworld.calculation.Vector;
import org.socialworld.calculation.application.AttributeCalculator;
import org.socialworld.core.Event;
import org.socialworld.knowledge.KnownPathsPool;
import org.socialworld.objects.access.GrantedAccessToProperty;
import org.socialworld.objects.access.HiddenAnimal;

/**
 * @author Mathias Sikos
 *
 */
public class StateAnimal extends StateSimulationObject {

	private AttributeArray attributes;
	private FunctionByMatrix attributeCalculatorMatrix;

	private Vector directionChest;
	private Vector directionView;
	private Vector directionMove;


	private KnownPathsPool knownPathsPool;

	private GrantedAccessToProperty grantAccessToPropertyAttributes[];
	
	public StateAnimal() {
		super();
		
		knownPathsPool = new KnownPathsPool();
		
		grantAccessToPropertyAttributes = new GrantedAccessToProperty[1];
		grantAccessToPropertyAttributes[0] = GrantedAccessToProperty.attributes;
	}

	void calculateEventInfluence(Event event) {
		
		super.calculateEventInfluence(event);
		
		
		AttributeCalculator.calculateAttributesChangedByEvent(event, ((StateAnimal)getMeReadableOnly()), ((HiddenAnimal)getMeWritableButHidden(grantAccessToPropertyAttributes)));
		
	}

	public void setAttributes(AttributeArray attributes, WriteAccessToAnimal guard) {
		if (checkGuard(guard)) {
			this.attributes = attributes;
		}
	}
	
	public AttributeArray getAttributes() {
		return new AttributeArray(this.attributes);
	}
	
	public void setMatrix(FunctionByMatrix matrix, WriteAccessToAnimal guard) {
		if (checkGuard(guard)) {
			this.attributeCalculatorMatrix  = matrix;
		}
	}
	
	public FunctionByMatrix getMatrix() {
		return this.attributeCalculatorMatrix;
	}

	public void setDirectionChest(Vector directionChest, WriteAccessToAnimal guard) {
		if (checkGuard(guard)) {
			this.directionChest = directionChest;
		}
	}

	public void setDirectionView(Vector directionView, WriteAccessToAnimal guard) {
		if (checkGuard(guard)) {
			this.directionView = directionView;
		}
	}

	public void setDirectionMove(Vector directionMove, WriteAccessToAnimal guard) {
		if (checkGuard(guard)) {
			this.directionMove = directionMove;
		}
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
	
	public void addPath(Path path, WriteAccessToAnimal guard)  {
		if (checkGuard(guard)) {
			this.knownPathsPool.addPath(path);
		}
	}

}
