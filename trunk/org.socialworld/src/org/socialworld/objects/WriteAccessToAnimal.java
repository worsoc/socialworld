package org.socialworld.objects;
import org.socialworld.attributes.AttributeArray;
import org.socialworld.calculation.AttributeCalculatorMatrix;

public class WriteAccessToAnimal extends WriteAccessToSimulationObject {
	
	private  Animal animal;
	
	public WriteAccessToAnimal(Animal animal) {
		super(animal);
		this.animal = animal;
	}
	
	public void setAttributes(AttributeArray attributes, Object caller) {
		if (caller instanceof IAnimalWrite) animal.setAttributes(attributes, this);
	}

	public void setMatrix(AttributeCalculatorMatrix matrix, Object caller) {
		if (caller instanceof IAnimalWrite) animal.setMatrix(matrix, this);
	}

}
