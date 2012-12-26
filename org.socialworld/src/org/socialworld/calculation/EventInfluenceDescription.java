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

	EventInfluenceExpression expression;

	public EventInfluenceDescription() {
		expression = new EventInfluenceExpression();
	}
	
	public void setExpression(EventInfluenceExpression expression) {
		this.expression = expression;
	}
}
