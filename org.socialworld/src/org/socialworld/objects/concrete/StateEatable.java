/*
* Social World
* Copyright (C) 2020  Mathias Sikos
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
package org.socialworld.objects.concrete;


import org.socialworld.attributes.PropertyName;
import org.socialworld.attributes.PropertyProtection;
import org.socialworld.attributes.properties.NutrientProperty;
import org.socialworld.attributes.properties.TasteProperty;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.objects.State;

public class StateEatable extends State {

	public static final String VALUENAME_NUTRIENT_PROPERTIES = "nutritionProps";
	public static final String VALUENAME_TASTE_PROPERTIES = "tasteProps";

	public static final String METHODNAME_GET_NUTRIENT_PROPERTIES = "getNutrientProperties";
	public static final String METHODNAME_GET_TASTE_PROPERTIES = "getTasteProperties";

	public static final String METHODNAME_SET_NUTRIENT_PROPERTIES = "setNutrientProperties";
	public static final String METHODNAME_SET_TASTE_PROPERTIES = "setTasteProperties";

	private NutrientProperty nutrientProps;
	private TasteProperty tasteProps;
	
	public StateEatable() {
		super();
	}

	private StateEatable( StateEatable original, PropertyProtection protectionOriginal, SimulationCluster cluster) {
		super(protectionOriginal, cluster);
		// TODO implement copy constructor
	}

	protected  void initPropertyName() {
		setPropertyName(PropertyName.stateEatable);
	}

	public State copyForProperty(SimulationCluster cluster) {
		return new StateEatable(this, getPropertyProtection(), cluster);
	}

	public  ValueProperty getProperty(SimulationCluster cluster, PropertyName prop, String name) {
		// TODO implement getProperty()
		return ValueProperty.getInvalid();
	}

	protected void setProperty(PropertyName propName, ValueProperty property) {
		// TODO setProperty
	}

	protected ValueProperty getNutrientProperties() {
		return new ValueProperty(Type.object, VALUENAME_NUTRIENT_PROPERTIES, nutrientProps);
	}
	
	protected void setNutrientProperties(NutrientProperty changed) {  }
	
	protected ValueProperty getTasteProperties() {
		return new ValueProperty(Type.object, VALUENAME_TASTE_PROPERTIES, tasteProps);
	}
	
	protected void setTasteProperties(TasteProperty changed) {  }

}
