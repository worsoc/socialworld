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

    protected Move move;

    public Animal() {
	super();
    }

    /**
     * @return the Move
     */
    public Move getMove() {
	return move;
    }

    /**
     * @param Move
     *                the Move to set
     */
    public void setMove(Move move) {
	this.move = move;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.socialworld.objects.SimulationObject#determineInfluence(org.socialworld.objects.SimulationEvent)
     */
    @Override
    public void determineInfluence(SimulationEvent simualationEvent) {

    }

}
