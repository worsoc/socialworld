/**
 * 
 */
package org.socialworld.objects;

import org.socialworld.attributes.ActionType;
import org.socialworld.attributes.Inventory;
import org.socialworld.knowledge.AcquaintancePool;
import org.socialworld.knowledge.KnowledgePool;
import org.socialworld.knowledge.KnowledgeSource;
import org.socialworld.knowledge.KnowledgeSourceType;
import org.socialworld.core.Action;

/**
 * A human is described in most details. It is the most important simulation
 * object. The simulation of a human is the main target of the game. A human has
 * an attribute array that describes his inner state. There are values for
 * courage, spirituality and morals for example. There is a detailed
 * differentiation of action handling an event influence. A human is the only
 * simulation object that has an inventory. So it can carry items and use them.
 * 
 * @author Mathias Sikos (tyloesand)
 * 
 */
 public class Human extends Mammal {
	 
	protected Inventory inventory;
	protected KnowledgePool knowledge;
	protected AcquaintancePool acquaintance;
	
	private String lastSaidSentence;
	
	public Human() {
		super();
		
	}

	/**
	 * @param inventory
	 *            the inventory to set
	 */
	void setInventory(final Inventory inventory, WriteAccessToHuman guard) {
		if (this.guard == guard ) this.inventory = inventory;
	}

	/**
	 * Depending on the action type the method calls the according procedure
	 * with special implementation of the action.
	 */
	@Override
	protected int doAction(final ActionType type, final Action action) {

		int actionDone = 0;

		switch (type) {
		case touch:
			actionDone = touch(action);
			break;
		case sleep:
			actionDone = sleep(action);
			break;
		case changeMove:
			actionDone = changeMove(action);
			break;
		case kick:
			actionDone = kick(action);
			break;
		case controlHandManually:
			actionDone = controlHandManually(action);
			break;
		case spell:
			actionDone = spell(action);
			break;
		case useWeaponLeft:
			actionDone = useWeaponLeft(action);
			break;
		case useWeaponRight:
			actionDone = useWeaponRight(action);
			break;
		case move:
			actionDone = move(action);
			break;
		case say:
			actionDone = say(action);
			break;
		case handleItem:
			actionDone = handleItem(action);
			break;
		case listenTo:
			actionDone = listenTo(action);
		default:
			actionDone = super.doAction(type,  action);
			break;
		}
		return actionDone;
	}

	protected int handleItem(final Action action) {
		return action.isDone();

	}

	protected int say(final Action action) {
		return action.isDone();

	}

	protected int useWeaponRight(final Action action) {
		final SimulationObject rightHand = this.inventory.getRightHand();
		if (rightHand != null ) 
			if (rightHand instanceof Weapon){
				final Weapon weapon = (Weapon) rightHand;
				weapon.handle(action, this);
			}
			else if (rightHand instanceof Item) {
				final Item item = (Item) rightHand;
				item.handle(action, this);
			}
		return action.isDone();
		
	}

	protected int useWeaponLeft(final Action action) {
		final SimulationObject leftHand = this.inventory.getLeftHand();
		if (leftHand != null ) 
			if (leftHand instanceof Weapon){
				final Weapon weapon = (Weapon) leftHand;
				weapon.handle(action, this);
			}
			else if (leftHand instanceof Item) {
				final Item item = (Item) leftHand;
				item.handle(action, this);
			}
		return action.isDone();
	}

	protected int spell(final Action action) {
		return action.isDone();

	}

	protected int controlHandManually(final Action action) {
		return action.isDone();

	}

	protected int touch(final Action action) {
		return action.isDone();

	}

	protected int listenTo(final Action action) {
		final SimulationObject target = action.getTarget();
		

		if (target instanceof Human) {

			final Human human = (Human) target;
			String sentence;
			KnowledgeSource source;
			
			sentence = human.getLastSaidSentence();
			
			source = new KnowledgeSource();
			source.setSourceType( KnowledgeSourceType.heardOf);
			// get the acquaintance of target human (null if the there isn't an acquaintance of target human)
			source.setOrigin(acquaintance.getAcquaintance(human));
			
			knowledge.addFactsFromSentence(sentence, source);
		}
		
		return action.isDone();
	}

	protected String getLastSaidSentence() {
		return lastSaidSentence;
	}
	
}
