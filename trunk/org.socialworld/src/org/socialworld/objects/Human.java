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
import org.socialworld.conversation.Talk;
import org.socialworld.core.Action;
import java.util.ArrayList;
import java.util.ListIterator;

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
	
	private ArrayList<Talk> talks;
	private String lastSaidSentence;
	
	public Human() {
		super();
		talks = new ArrayList<Talk>();
		
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
	protected void doAction(final ActionType type, final Action action) {


		switch (type) {
		case touch:
			 touch(action);
			break;
		case sleep:
			 sleep(action);
			break;
		case changeMove:
			 changeMove(action);
			break;
		case kick:
			 kick(action);
			break;
		case controlHandManually:
			 controlHandManually(action);
			break;
		case spell:
			 spell(action);
			break;
		case useWeaponLeft:
			 useWeaponLeft(action);
			break;
		case useWeaponRight:
			 useWeaponRight(action);
			break;
		case move:
			 move(action);
			break;
		case say:
			 say(action);
			break;
		case handleItem:
			 handleItem(action);
			break;
		case listenTo:
			 listenTo(action);
		case understand:
			 understand(action);
		default:
			 super.doAction(type,  action);
			break;
		}
	}

	protected void handleItem(final Action action) {


	}

	protected void say(final Action action) {


	}

	protected void useWeaponRight(final Action action) {
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

		
	}

	protected void useWeaponLeft(final Action action) {
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

	}

	protected void spell(final Action action) {


	}

	protected void controlHandManually(final Action action) {


	}

	protected void touch(final Action action) {


	}

	protected void listenTo(final Action action) {
		final SimulationObject target = action.getTarget();
		Action actionUnderstand;
		
		if (target instanceof Human) {

			final Human human = (Human) target;
			String sentence;
			
			sentence = human.getLastSaidSentence();
			addSentence(sentence, false, human);
			
			actionUnderstand = new Action(action);
			actionUnderstand.setType(ActionType.understand);
			actionHandler.insertAction(actionUnderstand);
		}
		

	}

	protected void understand(final Action action) {
		final Human human = (Human) action.getTarget();
		KnowledgeSource source;
		String sentence;

		sentence = getSentence(human);
		if (sentence != null) {
			source = new KnowledgeSource();
			source.setSourceType( KnowledgeSourceType.heardOf);
			// get the acquaintance of target human (null if the there isn't an acquaintance of target human)
			source.setOrigin(acquaintance.getAcquaintance(human));
			knowledge.addFactsFromSentence(sentence, source);
		}

	}
	
	protected String getLastSaidSentence() {
		return lastSaidSentence;
	}
	
	protected void addSentence(String sentence, boolean asPlannedSentence, Human partner) {
		ListIterator<Talk> iterator ;
		Talk talk;
		
		iterator = talks.listIterator();
		
		if (partner == null) {
			
			if (asPlannedSentence)
				talks.get(0).addAPlannedSentence(sentence);
			else
				talks.get(0).addPartnersSentence(sentence);
		}
		else while (iterator.hasNext()) {
			talk = iterator.next();
			if (talk.getPartner() == partner) {
				if (asPlannedSentence)
					talk.addAPlannedSentence(sentence);
				else
					talk.addPartnersSentence(sentence);
				break;	
			}
		}
	}
	
	protected String getSentence(Human partner) {
		ListIterator<Talk> iterator ;
		Talk talk;
		String sentence = null;
		
		iterator = talks.listIterator();
		
		if (partner == null) {
			
			sentence = talks.get(0).getPartnersSentence();
		}
		else while (iterator.hasNext()) {
			talk = iterator.next();
			if (talk.getPartner() == partner) {
				sentence =	talk.getPartnersSentence();
				if (sentence == null) iterator.remove();
				break;
			}
		}
		return sentence;
	}
	
}
