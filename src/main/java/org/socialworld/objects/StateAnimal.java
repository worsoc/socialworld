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
import org.socialworld.actions.handle.Inventory;
import org.socialworld.actions.move.Path;
import org.socialworld.attributes.Attribute;
import org.socialworld.attributes.AttributeArray;
import org.socialworld.attributes.Direction;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.calculation.application.Scheduler;
import org.socialworld.calculation.functions.FunctionByMatrix;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.core.Event;
import org.socialworld.core.IAccessToken;
import org.socialworld.knowledge.Knowledge;
import org.socialworld.knowledge.KnowledgeElement;
import org.socialworld.knowledge.KnownPathsPool;
import org.socialworld.knowledge.LastPerceivedObjects;
import org.socialworld.objects.access.GrantedAccessToProperty;
import org.socialworld.objects.access.HiddenAnimal;
import org.socialworld.tools.Generation;
import org.socialworld.tools.StringTupel;

/**
 * @author Mathias Sikos
 *
 */
public class StateAnimal extends StateSimulationObject {

	private final AttributeArray attributes;
	private FunctionByMatrix attributeCalculatorMatrix;

	private Direction directionChest = Direction.getObjectNothing();
	private Direction directionActiveMove = Direction.getObjectNothing();

	private LastPerceivedObjects lastPerceivedObjects;
	protected Knowledge knowledge;
	private KnownPathsPool knownPathsPool;

	
	private GrantedAccessToProperty grantAccessToPropertyAttributes[];
	private GrantedAccessToProperty grantAccessToPropertyAction[];
	private GrantedAccessToProperty grantAccessToPropertyKnowledge[];
	
	private static AccessTokenStateSimulationObject token = AccessTokenStateSimulationObject.getValid();
	
///////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////meta information    ////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	private static StateAnimal singletonDummyForGenerationTools;
	private static StringTupel[] propertiesMetaInfos = new StringTupel[]{
			new StringTupel(Type.attributeArray.getIndexWithSWTPraefix(), PropertyName.simobj_attributeArray.name()),
			new StringTupel("Knowledge", PropertyName.simobj_knowledge.name()),
			new StringTupel(new String[] {"Direction", PropertyName.simobj_directionChest.name(), PropertyName.simobj_directionChest.toString()}),
			new StringTupel(new String[] {"Direction", PropertyName.simobj_directionActiveMove.name(), PropertyName.simobj_directionActiveMove.toString()})
		} ;
	
	public static StateAnimal getInstance(Generation calledFromGeneration) {
		if (singletonDummyForGenerationTools == null) {
			singletonDummyForGenerationTools = new StateAnimal(calledFromGeneration);
		}
		return singletonDummyForGenerationTools;
	}

	protected StateAnimal(Generation calledFromGeneration) {
		super(calledFromGeneration);
		attributes = new AttributeArray(Attribute.NUMBER_OF_ATTRIBUTES);
		listOfPropertyMetaInfo = super.getPropertiesMetaInfos();
		for (int indexAdd = 0; indexAdd < propertiesMetaInfos.length; indexAdd++) {
			listOfPropertyMetaInfo.add(propertiesMetaInfos[indexAdd]);
		}
	}


///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    creating instance for simulation    //////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	public StateAnimal() {
		super();
		
		knowledge = new Knowledge();
		knownPathsPool = new KnownPathsPool();
		lastPerceivedObjects = new LastPerceivedObjects(10);
		
		grantAccessToPropertyAttributes = new GrantedAccessToProperty[1];
		grantAccessToPropertyAttributes[0] = GrantedAccessToProperty.attributes;

		grantAccessToPropertyAction = new GrantedAccessToProperty[1];
		grantAccessToPropertyAction[0] = GrantedAccessToProperty.action;
	
		grantAccessToPropertyKnowledge = new GrantedAccessToProperty[1];
		grantAccessToPropertyKnowledge[0] = GrantedAccessToProperty.knowledge;
		
		attributes = new AttributeArray(Attribute.NUMBER_OF_ATTRIBUTES);
		
		this.directionChest = new Direction(PropertyName.simobj_directionChest);
		this.directionActiveMove = new Direction(PropertyName.simobj_directionActiveMove);
		
	}


///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    ATTRIBUTES  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	public ValueProperty getProperty(IAccessToken token, PropertyName prop, String name) {

		switch (prop) {
		case simobj_attributeArray:
			return this.attributes.getAsValue(token, name);
		case simobj_knowledge:
			return this.knowledge.getAsValue(token, name);
		case simobj_directionChest:
			return this.directionChest.getAsValue(token, name);
		case simobj_directionActiveMove:
			return this.directionActiveMove.getAsValue(token, name);
		default:
			return super.getProperty(token, prop, name);
		}
	}

	
	void refresh() {
		
		super.refresh();
		Scheduler.getInstance().calculateAttributesChangedBySimpleMatrix((StateAnimal)getMeReadableOnly(), (HiddenAnimal)getMeWritableButHidden(grantAccessToPropertyAttributes));
		Scheduler.getInstance().createAction((StateAnimal)getMeReadableOnly(), (HiddenAnimal)getMeWritableButHidden(grantAccessToPropertyAction));

	}
	

	void calculateEventInfluence(Event event) {
		
		super.calculateEventInfluence(event);
		Scheduler.getInstance().calculateAttributesChangedByEvent(event, (StateAnimal)getMeReadableOnly(), (HiddenAnimal)getMeWritableButHidden(grantAccessToPropertyAttributes) );
		Scheduler.getInstance().calculatePerception(event, (StateAnimal)getMeReadableOnly(), (HiddenAnimal)getMeWritableButHidden(grantAccessToPropertyKnowledge) );
		
	}

	
	final void setAttributes(Value attributes, WriteAccessToAnimal guard) {

		if (checkGuard(guard)) {

			AttributeArray attributeArray;
			attributeArray = objectRequester.requestAttributeArray(token, attributes, this);

			if (checkChangeRelevance_AttributeArray(attributeArray)) {
				this.attributes.set(attributes);
				SocialWorld.showAttributeChanges(getObject().getObjectID(), attributeArray);
			}
			else {
				SocialWorld.showNoAttributeChanges(getObject().getObjectID(), this.attributes);
			}
			
			
		}
	}
	
	final int getAttribute(Attribute attributeName) {
		return this.attributes.get(attributeName);
	}
	
	protected boolean checkChangeRelevance_AttributeArray(AttributeArray newValue) {
		if (this.attributes.equals(newValue)) {
			return false;
		}
		else {
			return true;
		}
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
	
	final public void addPath(Path path, WriteAccessToAnimal guard)  {
		if (checkGuard(guard)) {
			this.knownPathsPool.addPath(path);
		}
	}
	
	final public void addKnowledgeElement(KnowledgeElement knowledgeElement, WriteAccessToAnimal guard) {
		if (checkGuard(guard)) {
			this.knowledge.addKnowledge(knowledgeElement);
		}
	}

	final public void addLastSeen(SimulationObject element, WriteAccessToAnimal guard) {
		if (checkGuard(guard)) {
			this.lastPerceivedObjects.addLastSeen(element);
		}
	}
	
	final public void addLastHeard(SimulationObject element, WriteAccessToAnimal guard) {
		if (checkGuard(guard)) {
			this.lastPerceivedObjects.addLastHeard(element);
		}
	}

	final public void addLastSmelled(SimulationObject element, WriteAccessToAnimal guard) {
		if (checkGuard(guard)) {
			this.lastPerceivedObjects.addLastSmelled(element);
		}
	}

	final public SimulationObject getLastSeen() {
		return this.lastPerceivedObjects.getLastSeen();
	}

	final public SimulationObject getLastHeard() {
		return this.lastPerceivedObjects.getLastHeard();
	}

	final public SimulationObject getLastSmelled() {
		return this.lastPerceivedObjects.getLastSmelled();
	}

	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    DIRECTIONS  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	final void setDirectionChest(Vector directionChest, WriteAccessToAnimal guard) {
		if (checkGuard(guard)) {
			this.directionChest = new Direction(PropertyName.simobj_directionChest, directionChest );
		}
	}


	final void setDirectionActiveMove(Vector directionMove, WriteAccessToAnimal guard) {
		if (checkGuard(guard)) {
			this.directionActiveMove = new Direction(PropertyName.simobj_directionActiveMove, directionMove );
		}
	}

	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    INVENTORY  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	final void setInventory(Inventory inventory, WriteAccessToAnimal guard) {
		if (checkGuard(guard)) {
			setStateProperty(PropertyName.stateInventory, PropertyName.stateInventory_inventory, new ValueProperty(Type.simObjProp, PropertyName.stateInventory_inventory.name(), inventory), guard);
		}
	}

/*	

	final public SimulationObject getLeftHandItem() {
		// no copy because it is a simulation object and that isn't allowed to be duplicated
		return this.inventory.getLeftHand();
	}
	
	final public SimulationObject getRightHandItem() {
		// no copy because it is a simulation object and that isn't allowed to be duplicated
		return this.inventory.getRightHand();
	}
	
	final public SimulationObject getMouthItem() {
		// no copy because it is a simulation object and that isn't allowed to be duplicated
		return this.inventory.getMouth();
	}
	
	final protected IWeapon _getLeftHandWeapon() {
		// no copy because it is a simulation object and that isn't allowed to be duplicated
		if (getObject().getSimObjectType() == SimulationObject_Type.human) {
			return this.inventory.getLeftHandWeapon();
		}
		else {
			return null;
		}
	}
	
	final protected IWeapon _getRightHandWeapon() {
		// no copy because it is a simulation object and that isn't allowed to be duplicated
		if (getObject().getSimObjectType() == SimulationObject_Type.human) {
			return this.inventory.getRightHandWeapon();
		}
		else {
			return null;
		}
	}
*/	
	
}
