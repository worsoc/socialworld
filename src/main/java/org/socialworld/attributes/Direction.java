/*
 * Social World
 * Copyright (C) 2014  Mathias Sikos
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
package org.socialworld.attributes;


import java.util.List;

import org.socialworld.calculation.Type;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.core.IAccessToken;
import org.socialworld.tools.StringTupel;

public class Direction extends SimProperty {

	// Felder als final deklarieren, da Direction nach Erstellung stabil bleibt
	private final SVVector vector;
	private final float power;

///////////////////////////////////////////////////////////////////////////////////////////
//////////////////  static instance for meta information    ///////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	private static StringTupel[] propertiesMetaInfos = new StringTupel[]{
			new StringTupel(new String[] {"SVVector", PropertyName.direction_vector.name(), PropertyName.direction_vector.toString()}),
			new StringTupel(new String[] {Type.floatingpoint.getIndexWithSWTPraefix(), PropertyName.direction_power.name(), PropertyName.direction_power.toString()})
			} ;

	public static List<StringTupel> getPropertiesMetaInfos() {
		List<StringTupel> listOfPropertyMetaInfo = SimProperty.getPropertiesMetaInfos();
		for (int indexAdd = 0; indexAdd < propertiesMetaInfos.length; indexAdd++) {
			listOfPropertyMetaInfo.add(propertiesMetaInfos[indexAdd]);
		}
		return listOfPropertyMetaInfo;
	}

///////////////////////////////////////////////////////////////////////////////////////////
///////////// object nothing (abstract method from ISimProperty)    ///////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

private static final Direction objectNothing = new Direction();
	
	public static Direction getObjectNothing() {
		return objectNothing;
	}

	public boolean checkIsObjectNothing() {
		return (this == objectNothing);
	}

	// Privater Konstruktor exklusiv für das "Nichts"
	private Direction() {
		super();
		this.vector = SVVector.getObjectNothing();
		this.power = 0.0f;
		this.setToObjectNothing(); // Falls für die Elternklasse benötigt
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
//////////////////      creating instance for simulation    ///////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	// TODO Herleitung  property name (direction's vector)
	public Direction (PropertyName prop, Vector vector) {
		super();
		setPropertyName(prop);
		this.power = 0.0f;
		
		// Barriere: Aus dem "Nichts" wird keine sinnvolle Richtung
		if (vector == null || vector.checkIsObjectNothing()) {
			this.vector = SVVector.getObjectNothing();
		} else {
			this.vector = new SVVector(vector, prop);
		}
	}

	public Direction (PropertyName prop, Vector vector, float power) {
		super();
		setPropertyName(prop);
		this.power = power;
		
		if (vector == null || vector.checkIsObjectNothing()) {
			this.vector = SVVector.getObjectNothing();
		} else {
			this.vector = new SVVector(vector, prop);
		}
	}

	public Direction (Type propertyType, Direction original) {
		super();
		setPropertyName(original.getPropertyName().toType(propertyType));
		this.power = original.getPower();
		
		// PERFORMANZ: Da der SVVector im Original garantiert immutable ist,
		// teilen wir uns einfach die Referenz! 0 Byte Allokation.
		this.vector = original.vector; 
	}
	
	public Direction (PropertyName prop, Direction original) {
		super();
		setPropertyName(prop);
		this.power = original.getPower();
		this.vector = original.vector; // Direkte Referenzübernahme
	}

	public Direction (PropertyName prop) {
		super();
		setPropertyName(prop);
		this.power = 0.0f;
		// Nutzt den statischen 0-Vektor
		this.vector = new SVVector(Vector.get0Vector(), prop); 
	}

	private Direction(Direction original, PropertyProtection protectionOriginal, IAccessToken token) {
		super(protectionOriginal, token);
		setPropertyName(original.getPropertyName());
		this.power = original.getPower();
		this.vector = original.vector; // Direkte Referenzübernahme
	}
	
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    ISavedValue  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public SimProperty copyForProperty(IAccessToken token) {
		// Das Singleton wird niemals kopiert, sondern direkt zurückgereicht
		if (checkIsObjectNothing()) return objectNothing;
		return new Direction(this, getPropertyProtection(), token);
	}
	
	public  ValueProperty getProperty(IAccessToken token, PropertyName prop, String valueName) {
		switch (prop) {
		case direction_vector:
			return new ValueProperty(Type.vector, valueName, getVector());
		case direction_power:
			return new ValueProperty(Type.floatingpoint, valueName, this.power);
			
		default:
			return ValueProperty.getInvalid();
		}

	}
	

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    Direction  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	public final Vector getVector(IAccessToken token) {
		if (checkIsObjectNothing()) return Vector.getObjectNothing();
		
		return this.vector.getReleased(token);
	}

	
	private SVVector getVector() {
		return this.vector;
	}

	public final float getPower() {
		return this.power;
	}

	
}
