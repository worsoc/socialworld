/*
 * Social World
 * Copyright (C) 2014  Mathias Sikos
 * Written with the assistance of Claude (Anthropic), 2026
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://gnu.org>.
 */
package org.socialworld.objects;

import java.util.List;
import java.util.Map;

import org.socialworld.attributes.PropertyName;
import org.socialworld.attributes.properties.BlowKind;
import org.socialworld.attributes.properties.Material;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.core.IAccessToken;
import org.socialworld.core.IEventParam;
import org.socialworld.knowledge.KnowledgeFact_Criterion;
import org.socialworld.objects.enums.EnumBaseSimObj;
import org.socialworld.objects.enums.EnumSide;
import org.socialworld.tools.StringTupel;

/**
 * The class Item describes all non-living simulation objects. It has a method
 * for handling an item. That implements what happens (in simulation sense) if
 * an item is used by a (living) simulation object.
 * 
 * @author Mathias Sikos (MatWorsoc)
 * 
 */
public abstract class Item extends SimulationObject {

///////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////meta information    ////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	private static StringTupel[] propertiesMetaInfos = new StringTupel[]{
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
			KnowledgeFact_Criterion.material
		};

	public static List<KnowledgeFact_Criterion> getResultingKFCs() {
		List<KnowledgeFact_Criterion> listOfResultingKFCs = SimulationObject.getResultingKFCs();
		for (int indexAdd = 0; indexAdd < resultingKFCs.length; indexAdd++) {
		listOfResultingKFCs.add(resultingKFCs[indexAdd]);
		}
		return listOfResultingKFCs;
	}

	private static PropertyName[] usedStateAppearanceColourPropertyNames = new PropertyName[] {
			PropertyName.stateAppearance_colourFrontside,
			PropertyName.stateAppearance_colourBackside,
			PropertyName.stateAppearance_colourLeftside,
			PropertyName.stateAppearance_colourRightside,
			PropertyName.stateAppearance_colourUpperside,
			PropertyName.stateAppearance_colourLowerside
		};
	
	private static byte[] stateAppearanceMainColourCalculationColourSetsShares = new byte[] {
			17,
			17,
			17,
			17,
			16,
			16
	};

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    creating instance for simulation    //////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public Item() {
		super();
		setBaseSimObjEnum(EnumBaseSimObj.Item);
	}

	@Override
	public final boolean isSimulationObject() {return true;}

	protected SimulationObject_Type getSimObjectType() {
		return SimulationObject_Type.item;
	}

	protected void initialize() {
		if (!isInitialized()) {
		}
	}

	// the item's own state, for the side (protection concept S3, 2026-11-24); null for an object without state
	private StateItem state;

	protected void assignState(StateSimulationObject state) {
		if (checkIsMyState(state) && state instanceof StateItem) this.state = (StateItem) state;
	}
	

	protected List<State> createAddOnStates() {
		
		List<State> result = super.createAddOnStates();
				
		return result;
		
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    STATE      ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public PropertyName[] getUsedStateAppearanceColourPropertyNames() {
		return usedStateAppearanceColourPropertyNames;
	}
	
	public byte[] getStateAppearanceMainColourCalculationColourSetsShares() {
		return stateAppearanceMainColourCalculationColourSetsShares;
	}

	/** The side from the state; none for an object without state (a prototype). */
	protected final EnumSide getSide() {
		return (this.state == null) ? EnumSide.none : this.state.getSide();
	}

///////////////////////////////////////////////////////////////////////////////////////////
//////////////////////    value, protection, burning from the material    ////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	// Concept value and protection from the material, step 3 (2026-11-26). The material gives only base values,
	// the class its factors; the formulas are here and may be overridden by a class that needs something special.
	// The shares are shares of the mass in percent; without a composition every result is 0.

	/** The width B of the S curve for igniting, in degrees Celsius. */
	public static final float IGNITION_CURVE_WIDTH = 10f;
	/** Below the ignition temperature minus this, in degrees Celsius, nothing ignites. */
	public static final float IGNITION_CUTOFF = 50f;

	// ------------------------------------------ value ------------------------------------------

	/**
	 * @return the value per kg of the material of this instance: sum of share / 100 x value per kg of the material
	 */
	protected float getMaterialValuePerKg() {
		float sum = 0;
		for (Map.Entry<Material, Integer> share : getMaterialShares().entrySet())
			sum += share.getValue() / 100f * share.getKey().getValuePerKg();
		return sum;
	}

	/**
	 * How much the making adds to the material: a ring high, a lump of ore low.
	 *
	 * @return the craft factor of the class, 1 by default
	 */
	protected float getCraftFactor() {
		return 1f;
	}

	/**
	 * The value until the mass comes (concept E11): value per kg of the material x craft factor, open to the top.
	 *
	 * @return the value of this instance
	 */
	protected float calculateValue() {
		return getMaterialValuePerKg() * getCraftFactor();
	}

	// ---------------------------------------- protection ----------------------------------------

	/**
	 * @param blow the kind of blow
	 * @return the protection of the material of this instance: sum of share / 100 x protection of the material
	 */
	protected float getMaterialProtection(BlowKind blow) {
		float sum = 0;
		for (Map.Entry<Material, Integer> share : getMaterialShares().entrySet())
			sum += share.getValue() / 100f * share.getKey().getProtectionAgainst(blow);
		return sum;
	}

	/**
	 * Thickness, area and kind of making: a helmet protects better than a cap of the same stuff.
	 *
	 * @return the form factor of the class for every kind of blow, 1 by default
	 */
	protected float getFormFactor() {
		return 1f;
	}

	/**
	 * A class whose making protects differently against the kinds of blow (chain mail) overrides this.
	 *
	 * @param blow the kind of blow
	 * @return the form factor of the class against this kind of blow, getFormFactor() by default
	 */
	protected float getFormFactor(BlowKind blow) {
		return getFormFactor();
	}

	/**
	 * @param blow the kind of blow
	 * @return the protection of this instance against the kind of blow: material x form factor, within 0 .. 100
	 */
	protected float calculateProtection(BlowKind blow) {
		float protection = getMaterialProtection(blow) * getFormFactor(blow);
		return Math.max(0f, Math.min(100f, protection));
	}

	// ----------------------------------------- burning -----------------------------------------

	/** @return the width B of the S curve in degrees Celsius, IGNITION_CURVE_WIDTH by default */
	protected float getIgnitionCurveWidth() {
		return IGNITION_CURVE_WIDTH;
	}

	/** @return how far below the ignition temperature igniting starts, IGNITION_CUTOFF by default */
	protected float getIgnitionCutoff() {
		return IGNITION_CUTOFF;
	}

	/**
	 * The S curve: 1 / (1 + e^(-(T - Tz) / B)), 0 below Tz - cutoff and for a material that does not burn.
	 *
	 * @param material the material
	 * @param temperature the temperature T in degrees Celsius
	 * @return the probability that the material ignites within one second at this temperature
	 */
	protected double getIgnitionProbabilityPerSecond(Material material, float temperature) {
		if (!material.canBurn()) return 0;
		float tz = material.getIgnitionTemperature();
		if (temperature < tz - getIgnitionCutoff()) return 0;
		return 1.0 / (1.0 + Math.exp(-(temperature - tz) / getIgnitionCurveWidth()));
	}

	/**
	 * Per material 1 - (1 - p)^seconds, so the result does not depend on the length of a tick; weighted by the
	 * shares of the mass.
	 *
	 * @param temperature the temperature T in degrees Celsius
	 * @param seconds how long the instance is exposed to it
	 * @return the probability that this instance catches fire, 0 .. 1
	 */
	protected double calculateIgnitionProbability(float temperature, float seconds) {
		if (!(seconds > 0)) return 0;
		double sum = 0;
		for (Map.Entry<Material, Integer> share : getMaterialShares().entrySet()) {
			double perSecond = getIgnitionProbabilityPerSecond(share.getKey(), temperature);
			sum += share.getValue() / 100.0 * (1.0 - Math.pow(1.0 - perSecond, seconds));
		}
		return Math.max(0.0, Math.min(1.0, sum));
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    PROPERTY LIST  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public void requestPropertyList(IAccessToken token, IEventParam paramObject) {
	
		super.requestPropertyList(token, paramObject);
		
		ValueArrayList propertiesAsValueList = new ValueArrayList();
		paramObject.answerPropertiesRequest(propertiesAsValueList);
	
	}





}
