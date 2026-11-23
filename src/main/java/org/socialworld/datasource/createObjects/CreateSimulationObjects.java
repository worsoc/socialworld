/*
 * Social World
 * Copyright (C) 2015  Mathias Sikos
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
package org.socialworld.datasource.createObjects;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.socialworld.GlobalSwitches;
import org.socialworld.attributes.ActualTime;
import org.socialworld.attributes.Position;
import org.socialworld.attributes.PropertyName;
import org.socialworld.core.IncompleteSimulationObject;
import org.socialworld.datasource.pool.GaussPoolInfluenceType;
import org.socialworld.datasource.pool.GaussPoolPerceptionType;
import org.socialworld.datasource.pool.GaussPoolPosition;
import org.socialworld.datasource.pool.GaussPoolReactionType;
import org.socialworld.datasource.pool.GaussPoolState2ActionType;
import org.socialworld.objects.NoSimulationObject;
import org.socialworld.objects.access.HiddenSimulationObject;

/**
 * Its the basic class for creating simulation objects.
 * for inherited classes of class SimulationObject
 *  there exists an inherited class of CreateSimulationObjects.
 *  
 * @author Mathias Sikos (MatWorsoc) 
 */
public abstract class CreateSimulationObjects {

	protected static final int THRESHOLD_RANDOM_GAUSSIAN_VALUE = 4;
	protected Random random;

    private Set<Integer> usedPositionIndex;

    // a start position given by the caller (e.g. the carrier of a carried item, inventory concept V3): then no
    // start position is drawn from GaussPoolPosition. Set and cleared by ObjectMaster around one creation.
    private Position presetStartPosition = null;
	
	CreateSimulationObjects() {
		random = new Random();
        usedPositionIndex = new HashSet<Integer>();
	}

	public abstract IncompleteSimulationObject getObject(int objectID, String fullClassName) ;
	
	protected final Object createObjectForName(String fullClassName) {
		
		
		Object createdObject = NoSimulationObject.getObjectNothing();
		Object noObject = NoSimulationObject.getObjectNothing();
		try {
			if (GlobalSwitches.OUTPUT_CREATE_OBJECT_CLASS_FOR_NAME) System.out.println("Erstellen SimObj > CreateSimulationObject.createObjectForName(" + fullClassName + ") Start " + ActualTime.asTime().toString());
			createdObject = Class.forName(fullClassName).getDeclaredConstructor().newInstance();
			if (GlobalSwitches.OUTPUT_CREATE_OBJECT_CLASS_FOR_NAME) System.out.println("Erstellen SimObj > CreateSimulationObject.createObjectForName(" + fullClassName + ") Ende " + ActualTime.asTime().toString());
		}
		catch (ClassNotFoundException cnfe ) {
			System.out.println(cnfe.getMessage());
			return noObject;
		}
		catch (InvocationTargetException ite ) {
			System.out.println(ite.getMessage());
			return noObject;
		}
		catch (NoSuchMethodException nsme ) {
			System.out.println(nsme.getMessage());
			return noObject;
		}
		catch (IllegalAccessException iae ) {
			System.out.println(iae.getMessage());
			return noObject;
		}
		catch (InstantiationException ie) {
			ie.printStackTrace();
			return noObject;
		}
		
		return createdObject;
	}
	
	protected int mapGaussToIndex(double gaussValue, int arrayCapacity) {
		double factor;
		int result;
		factor = arrayCapacity / THRESHOLD_RANDOM_GAUSSIAN_VALUE;
		result = (int) (gaussValue * factor);
		return result;
	}
	

	protected void initObject(HiddenSimulationObject hiddenObject) {
	
	}
	

	public final void setPresetStartPosition(Position position) {
		this.presetStartPosition = position;
	}

	protected void initState(HiddenSimulationObject hiddenObject) {
		int indexPosition;

		int indexGPIT; 
		int indexGPRT; 
		int indexGPS2A; 
		int indexGPPT; 
		
		double gauss_value;
		
	
		gauss_value = random.nextGaussian();
		while ((gauss_value > THRESHOLD_RANDOM_GAUSSIAN_VALUE) || (gauss_value < -THRESHOLD_RANDOM_GAUSSIAN_VALUE))
			gauss_value = random.nextGaussian();
		indexGPIT = mapGaussToIndex(gauss_value, GaussPoolInfluenceType.CAPACITY_GPIT_ARRAY);
		hiddenObject.setInfluenceTypes(GaussPoolInfluenceType.getInstance().getInfluenceTypes(indexGPIT));

		gauss_value = random.nextGaussian();
		while ((gauss_value > THRESHOLD_RANDOM_GAUSSIAN_VALUE) || (gauss_value < -THRESHOLD_RANDOM_GAUSSIAN_VALUE))
			gauss_value = random.nextGaussian();
		indexGPRT = mapGaussToIndex(gauss_value, GaussPoolReactionType.CAPACITY_GPRT_ARRAY);
		hiddenObject.setReactionTypes(GaussPoolReactionType.getInstance().getReactionTypes(indexGPRT));

		gauss_value = random.nextGaussian();
		while ((gauss_value > THRESHOLD_RANDOM_GAUSSIAN_VALUE) || (gauss_value < -THRESHOLD_RANDOM_GAUSSIAN_VALUE)) 
			gauss_value = random.nextGaussian();
		indexGPS2A = mapGaussToIndex(gauss_value, GaussPoolState2ActionType.CAPACITY_GPS2A_ARRAY);
		hiddenObject.setState2ActionType(GaussPoolState2ActionType.getInstance().getState2ActionType(indexGPS2A));

		gauss_value = random.nextGaussian();
		while ((gauss_value > THRESHOLD_RANDOM_GAUSSIAN_VALUE) || (gauss_value < -THRESHOLD_RANDOM_GAUSSIAN_VALUE)) 
			gauss_value = random.nextGaussian();
		indexGPPT = mapGaussToIndex(gauss_value, GaussPoolPerceptionType.CAPACITY_GPPT_ARRAY);
		hiddenObject.setPerceptionTypes(GaussPoolPerceptionType.getInstance().getPerceptionTypes(indexGPPT));
		
        if (this.presetStartPosition != null && !this.presetStartPosition.checkIsObjectNothing()) {
            Position preset = this.presetStartPosition.getCopy(PropertyName.simobj_position, 0, 0, 0);
            hiddenObject.setPosition(preset);
            return;
        }

        // Every object of a kind gets its own start position. The drawn index lies in
        // -(CAPACITY - 1) .. CAPACITY - 1, so there are 2 * CAPACITY - 1 of them. When they are used up,
        // the loop below would draw for ever (seen 2026-11-17 with 800 items), so stop here instead.
        int countPositions = 2 * GaussPoolPosition.CAPACITY_GPPos_ARRAY - 1;
        if (usedPositionIndex.size() >= countPositions) {
            String message = getClass().getSimpleName() + ": all " + countPositions
                    + " start positions of GaussPoolPosition (swgauss_position) are used up, no position for one more object."
                    + " Create fewer objects of this kind or add positions (CAPACITY_GPPos_ARRAY and the table).";
            System.err.println(message);
            throw new IllegalStateException(message);
        }
        do {
            indexPosition = random.nextInt(GaussPoolPosition.CAPACITY_GPPos_ARRAY);
            if (random.nextBoolean() == false) indexPosition = indexPosition * -1;
        } while (usedPositionIndex.contains(indexPosition)); 
		usedPositionIndex.add(indexPosition);
		Position position = GaussPoolPosition.getInstance().getPosition(indexPosition);
		position.setPropertyName(PropertyName.simobj_position);
		hiddenObject.setPosition(position);

	}
	

}
