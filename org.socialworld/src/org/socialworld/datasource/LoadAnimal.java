package org.socialworld.datasource;

import org.socialworld.objects.Animal;
import org.socialworld.objects.IAnimalWrite;
import org.socialworld.objects.WriteAccessToAnimal;

public class LoadAnimal extends LoadSimulationObjects implements IAnimalWrite {

	protected LoadAnimal() {
	}

	@Override
	public Animal getObject(long objectID) {
		double gauss_value;
		
		Animal createdAnimal = new Animal();
		WriteAccessToAnimal animal = new WriteAccessToAnimal(createdAnimal);
		
		gauss_value = random.nextGaussian();

		initObject(animal, objectID, gauss_value);	
		
		return createdAnimal;
	}

	protected void initObject(WriteAccessToAnimal object, long objectID, double gauss_value) {
		int indexACMP;
		int indexAAP;

		super.initObject(object, objectID, gauss_value);

		indexACMP = mapGaussToIndex(gauss_value, AttributeCalculatorMatrixPool.CAPACITY_ACMP_ARRAY);
		indexAAP = mapGaussToIndex(gauss_value, AttributeArrayPool.CAPACITY_AAP_ARRAY);

		object.setMatrix(	
				AttributeCalculatorMatrixPool.getInstance().getMatrix(indexACMP),
				this);
		object.setAttributes(
				AttributeArrayPool.getInstance().getArray(indexAAP),
				this);
		
	}
	
}
