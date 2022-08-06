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

import java.util.List;

import org.socialworld.actions.AbstractAction;
import org.socialworld.actions.ActionMode;
import org.socialworld.actions.move.Path;
import org.socialworld.actions.move.PathFinder;
import org.socialworld.attributes.Attribute;
import org.socialworld.attributes.Position;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.FunctionByMatrix;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.core.IEventParam;
import org.socialworld.knowledge.KnowledgeFact_Criterion;
import org.socialworld.objects.concrete.animals.IRunnable;
import org.socialworld.objects.concrete.animals.ISeer;
import org.socialworld.objects.concrete.animals.StateBody;
import org.socialworld.objects.concrete.animals.StateInventory;
import org.socialworld.objects.concrete.animals.StateSeer;
import org.socialworld.tools.StringTupel;


/**
 * An animal is a simulation object with extensions to express the living kind.
 * There are methods for action handling and event effects.
 * @author Mathias Sikos (tyloesand)
 * 
 */
public abstract class Animal extends SimulationObject implements ISeer, IRunnable {

	private StateAnimal state;

	// add on states
	private StateSeer stateSeer;
	private StateBody stateBody;
	private StateInventory stateInventory;
	
	private PathFinder pathFinder;

	private boolean initialized = false;
	

///////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////	meta information    ////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	private static StringTupel[] propertiesMetaInfos = new StringTupel[]{
		new StringTupel("StateSeer", PropertyName.stateSeer.name()),
		new StringTupel("StateBody", PropertyName.stateBody.name()),
		new StringTupel("StateInventory", PropertyName.stateInventory.name())
		} ;

	public static List<StringTupel> getPropertiesMetaInfos() {
		List<StringTupel> listOfPropertyMetaInfo = SimulationObject.getPropertiesMetaInfos();
		for (int indexAdd = 0; indexAdd < propertiesMetaInfos.length; indexAdd++) {
			listOfPropertyMetaInfo.add(propertiesMetaInfos[indexAdd]);
		}
		return listOfPropertyMetaInfo;
	}

	private static KnowledgeFact_Criterion[] resultingKFCs = new KnowledgeFact_Criterion[] {
			KnowledgeFact_Criterion.colour,
		};

	public static List<KnowledgeFact_Criterion> getResultingKFCs() {
		List<KnowledgeFact_Criterion> listOfResultingKFCs = SimulationObject.getResultingKFCs();
		for (int indexAdd = 0; indexAdd < resultingKFCs.length; indexAdd++) {
		listOfResultingKFCs.add(resultingKFCs[indexAdd]);
		}
		return listOfResultingKFCs;
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    creating instance for simulation    //////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	public Animal() {
		super();
	}
	
	protected SimulationObject_Type getSimObjectType() {
		return SimulationObject_Type.animal;
	}

	protected void assignState(StateSimulationObject state) {
		if (checkIsMyState(state) ) this.state = (StateAnimal) state;
	}
	
	protected void init() {
		if (initialized == false) {
			pathFinder = new PathFinder(this, this.state.getKnownPathsPool());
			initialized = true;
		}
	}

	protected List<State> createAddOnStates() {
		
		List<State> result = super.createAddOnStates();
		
		this.stateSeer = (StateSeer) getInitState(StateSeer.class.getName());
		result.add(this.stateSeer);
		
		this.stateBody = (StateBody) getInitState(StateBody.class.getName());
		result.add(this.stateBody);
		
		this.stateInventory = (StateInventory) getInitState(StateInventory.class.getName());
		result.add(this.stateInventory);
		
		return result;
		
	};


	public ValueProperty getProperty(SimulationCluster cluster, PropertyName prop, String name) {
		switch (prop) {
		case simobj_attributeArray:
		case simobj_inventory:
		case simobj_knowledge:
		case simobj_directionChest:
		case simobj_directionActiveMove:
			return this.state.getProperty(cluster, prop, name);
		default:
			return super.getProperty(cluster, prop, name);
		}
	}
	

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    checking for interface  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	protected boolean isInterface(String nameInterface) {
		
		boolean result;
		
		switch (nameInterface) {
			case ISeer.NAME:
				result = (this instanceof ISeer);
				break;
			default:
				result = super.isInterface(nameInterface);

		}
		
		
		return result;
		
	}


///////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////    implementing ISeer     //////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public StateSeer getSavedStateSeer(SimulationCluster cluster) {
		// make a copy as ValueProperty
		ValueProperty vp = this.stateSeer.getAsValue(cluster);
		// the copy is permitted for cluster only
		return (StateSeer) vp.getValue();
	}
	
	public ValueProperty getStateSeerAsProperty(SimulationCluster cluster, String name) {
		return this.stateSeer.getAsValue(cluster, name);
	}


///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    ATTRIBUTES  //////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	final public FunctionByMatrix getMatrix() {
		return this.state.getMatrix();
	}

	final public int getAttribute(Attribute attributeName) {
		return this.state.getAttribute(attributeName);
	}
	

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    KNOWLEDGE  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	final public Path findPath(Position end) {
		return this.pathFinder.findPath(end);
	}
	

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    ACTION     ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	

	/**
	 * The method holds the implementation of bodilyFunctions.
	 * 
	 * @param action
	 */
	protected void bodilyFunction(final AbstractAction action) {
		final ActionMode mode = action.getMode();

		switch (mode) {
		case sleep:
			break;
		case drink:
			break;
		case eat:
			break;
		case piss:
			break;
		case shit:
			break;
		default:
		}
	}

	

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    INVENTORY  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	// TODO interface for more complex access to inventory
	// QUESTION Do we need access to inventory in class Animal?
/*	
	final public SimulationObject getLeftHandItem() {
	// no copy because it is a simulation object and that isn't allowed to be duplicated
	return this.state.getLeftHandItem();
	}
	
	final public SimulationObject getRightHandItem() {
	// no copy because it is a simulation object and that isn't allowed to be duplicated
	return this.state.getRightHandItem();
	}
	
	final public SimulationObject getMouthItem() {
	// no copy because it is a simulation object and that isn't allowed to be duplicated
	return this.state.getMouthItem();
	}
*/	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    PROPERTY LIST  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public void requestPropertyList(SimulationCluster cluster, IEventParam paramObject) {
	
		super.requestPropertyList(cluster, paramObject);
		
		ValueArrayList propertiesAsValueList = new ValueArrayList();
		
		propertiesAsValueList.add(getProperty(cluster, PropertyName.simobj_attributeArray));
		propertiesAsValueList.add(getProperty(cluster, PropertyName.simobj_directionChest));
		propertiesAsValueList.add(getProperty(cluster, PropertyName.stateSeer_directionView));
		propertiesAsValueList.add(getProperty(cluster, PropertyName.simobj_directionActiveMove));
		paramObject.answerPropertiesRequest(propertiesAsValueList);
	
	}

}
