/**
 * 
 */
package org.socialworld.objects;

/**
 * 
 * @author Mathias Sikos (tyloesand)
 *
 */
 public class Animal extends SimulationObject {

	protected MoveType moveType;
	
	public Animal() {
		super ();
	}

	/**
	 * @return the moveType
	 */
	public MoveType getMoveType() {
		return moveType;
	}

	/**
	 * @param moveType the moveType to set
	 */
	public void setMoveType(MoveType moveType) {
		this.moveType = moveType;
	}

	/* (non-Javadoc)
	 * @see org.socialworld.objects.SimulationObject#determineInfluence(org.socialworld.objects.SimulationEvent)
	 */
	@Override
	public void determineInfluence(SimulationEvent simualationEvent) {

	}

}
