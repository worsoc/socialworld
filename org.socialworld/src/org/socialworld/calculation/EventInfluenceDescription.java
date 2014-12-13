/**
 * 
 */
package org.socialworld.calculation;

/**
 * The class holds all informations for
 *         describing how an event influences a simulation object.

 * @author Mathias Sikos (tyloesand)   
 */
public class EventInfluenceDescription {

	protected Expression expression;
	protected int eventType;
	protected int influenceType;
	
	public EventInfluenceDescription() {
		expression = new Expression();
	}
	
	public void setExpression(Expression expression) {
		this.expression = expression;
	}
	
	public void setEventType(int eventType ) {
		this.eventType =  eventType;
	}
	
	public void setInfluenceType(int influenceType ) {
		this.influenceType =  influenceType;
	}	

}
