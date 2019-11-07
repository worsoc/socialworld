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

import org.socialworld.SocialWorld;
import org.socialworld.actions.move.Path;
import org.socialworld.attributes.Attribute;
import org.socialworld.attributes.AttributeArray;
import org.socialworld.calculation.FunctionByMatrix;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.Vector;
import org.socialworld.calculation.application.Scheduler;
import org.socialworld.core.Event;
import org.socialworld.knowledge.KnownPathsPool;
import org.socialworld.objects.access.GrantedAccessToProperty;
import org.socialworld.objects.access.HiddenAnimal;

/**
 * @author Mathias Sikos
 *
 */
public class StateAnimal extends StateSimulationObject {

	private final AttributeArray attributes;
	private FunctionByMatrix attributeCalculatorMatrix;

	private Vector directionChest;
	private Vector directionView;
	private Vector directionActiveMove;

	private KnownPathsPool knownPathsPool;

	private GrantedAccessToProperty grantAccessToPropertyAttributes[];
	private GrantedAccessToProperty grantAccessToPropertyAction[];
	
	public StateAnimal() {
		super();
		
		knownPathsPool = new KnownPathsPool();
		
		grantAccessToPropertyAttributes = new GrantedAccessToProperty[1];
		grantAccessToPropertyAttributes[0] = GrantedAccessToProperty.attributes;

		grantAccessToPropertyAction = new GrantedAccessToProperty[1];
		grantAccessToPropertyAction[0] = GrantedAccessToProperty.action;
		
		attributes = new AttributeArray(Attribute.NUMBER_OF_ATTRIBUTES);
		
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    ATTRIBUTES  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	void refresh() {
		
		super.refresh();
		Scheduler.getInstance().calculateAttributesChangedBySimpleMatrix((StateAnimal)getMeReadableOnly(), (HiddenAnimal)getMeWritableButHidden(grantAccessToPropertyAttributes));
		Scheduler.getInstance().createAction((StateAnimal)getMeReadableOnly(), (HiddenAnimal)getMeWritableButHidden(grantAccessToPropertyAction));

	}
	

	void calculateEventInfluence(Event event) {
		
		super.calculateEventInfluence(event);
		Scheduler.getInstance().calculateAttributesChangedByEvent(event, (StateAnimal)getMeReadableOnly(), (HiddenAnimal)getMeWritableButHidden(grantAccessToPropertyAttributes) );
		
	}

	
	final void setAttributes(Value attributes, WriteAccessToAnimal guard) {

		if (checkGuard(guard)) {
			
			SocialWorld.showAttributeChanges(getObject().getObjectID(), (AttributeArray) attributes.getValue());

			System.out.println(getObject().getObjectID() + " : " +  attributes.getValue().toString());
			this.attributes.set(attributes);
			
		}
	}
	
	final int getAttribute(Attribute attributeName) {
		return this.attributes.get(attributeName);
	}
	
	final public Value getAttributesAsValue(String valueName) {
		return new Value(Type.attributeArray, valueName, new AttributeArray(this.attributes) );
	}
	
	final void setMatrix(FunctionByMatrix matrix, WriteAccessToAnimal guard) {
		if (checkGuard(guard)) {
			this.attributeCalculatorMatrix  = matrix;
		}
	}
	
	final public FunctionByMatrix getMatrix() {
		return this.attributeCalculatorMatrix;
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    KNOWLEDGE  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	final public KnownPathsPool getKnownPathsPool() {
		return this.knownPathsPool;
	}
	
	final void addPath(Path path, WriteAccessToAnimal guard)  {
		if (checkGuard(guard)) {
			this.knownPathsPool.addPath(path);
		}
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    DIRECTIONS  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	final void setDirectionChest(Vector directionChest, WriteAccessToAnimal guard) {
		if (checkGuard(guard)) {
			this.directionChest = directionChest;
		}
	}

	final void setDirectionView(Vector directionView, WriteAccessToAnimal guard) {
		if (checkGuard(guard)) {
			this.directionView = directionView;
		}
	}

	final void setDirectionActiveMove(Vector directionMove, WriteAccessToAnimal guard) {
		if (checkGuard(guard)) {
			this.directionActiveMove = directionMove;
		}
	}

	final public Value getDirectionChestAsValue(String valueName) {
		return new Value( Type.vector, valueName, new Vector(this.directionChest) );
	}
	
	final public Value getDirectionViewAsValue(String valueName) {
		return new Value( Type.vector, valueName, new Vector(this.directionView) );
	}

	final public Value getDirectionActiveMoveAsValue(String valueName) {
		return new Value( Type.vector, valueName, new Vector(this.directionActiveMove) );
	}


}
