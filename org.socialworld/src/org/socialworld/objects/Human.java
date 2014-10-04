/**
 * 
 */
package org.socialworld.objects;

import org.socialworld.attributes.ActionType;
import org.socialworld.attributes.ActionMode;
import org.socialworld.attributes.Inventory;
import org.socialworld.knowledge.AcquaintancePool;
import org.socialworld.knowledge.KnowledgePool;
import org.socialworld.knowledge.KnowledgeSource;
import org.socialworld.knowledge.KnowledgeSourceType;
import org.socialworld.knowledge.Answer;
import org.socialworld.conversation.Talk;
import org.socialworld.conversation.TalkSentenceType;
import org.socialworld.conversation.PunctuationMark;
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
		case hear:
			 hear(action);
		default:
			 super.doAction(type,  action);
			break;
		}
	}

	protected void handleItem(final Action action) {


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

	protected void say(final Action action) {
		ActionMode mode = action.getMode();
		Action followingAction = null;
		final Human human = (Human) action.getTarget();
		String question;
		
		switch (mode) {
			case answer:
				Answer answer;
			
				question = getSentence(human, TalkSentenceType.partnersQuestion);
				if (question != null) {
					followingAction = new Action(action);
					// TODO
					answer = knowledge.getAnswerForQuestion(question);
					addAnswer(answer,  human);
					// TODO the mode depends on intensity
					followingAction.setMode(ActionMode.say);
				}
			case ask:
			
				question = getSentence(human, TalkSentenceType.myPlannedQuestion);
				if (question != null) {
				}
			default:
		}
		actionHandler.insertAction(followingAction);

	}
	
	protected void hear(final Action action) {
		ActionMode mode = action.getMode();
		
		switch (mode) {
			case  listenTo:
				final SimulationObject target = action.getTarget();
				Action followingAction;
				
				if (target instanceof Human) {

					final Human human = (Human) target;
					String sentence;
					PunctuationMark punctuationMark;
					
					sentence = human.getLastSaidSentence();
					punctuationMark = addPartnersSentence(sentence, human);
					
					followingAction = new Action(action);
					switch (punctuationMark) {
						case dot:
							followingAction.setMode(ActionMode.understand);
						case question:
							followingAction.setType(ActionType.say);
							followingAction.setMode(ActionMode.answer);
						default:
							followingAction.setType(ActionType.ignore);

					}
					actionHandler.insertAction(followingAction);
				}
				
			case understand:
				final Human human = (Human) action.getTarget();
				KnowledgeSource source;
				String sentence;

				sentence = getSentence(human, TalkSentenceType.partnersSentence);
				if (sentence != null) {
					source = new KnowledgeSource();
					source.setSourceType( KnowledgeSourceType.heardOf);
					// get the acquaintance of target human (null if the there isn't an acquaintance of target human)
					source.setOrigin(acquaintance.getAcquaintance(human));
					knowledge.addFactsFromSentence(sentence, source);
				}
			default:
		}
		
		

	}

	protected void understand(final Action action) {

	}
	
	protected String getLastSaidSentence() {
		return lastSaidSentence;
	}
	
	protected PunctuationMark addPartnersSentence(String sentence, Human partner) {
		PunctuationMark returnValue = null;
		TalkSentenceType type;
		
		returnValue = talks.get(0).getPunctuationMark(sentence);
		
		switch (returnValue) {
		case dot: 
			type = TalkSentenceType.partnersSentence;
		case question: 
			type = TalkSentenceType.partnersQuestion;
		default:
			type = TalkSentenceType.partnersUnknownType;
		}
		addSentence(sentence, type, partner);
		
		return returnValue;
	}

	protected void addAnswer(Answer answer,  Human partner) {
		
		Talk talk;
		String sentence;
		
		talk = getTalk(partner);
		
		sentence = talk.makeAnswerSentence(answer);
		talk.addSentence(sentence, TalkSentenceType.myPlannedSentence);
	}

	protected void addSentence(String sentence, TalkSentenceType type, Human partner) {
		Talk talk;
		
		talk = getTalk(partner);
		
		talk.addSentence(sentence, type);
	}
	
	protected String getSentence(Human partner, TalkSentenceType type) {
		ListIterator<Talk> iterator ;
		Talk talk;
		String sentence = null;
		
		iterator = talks.listIterator();
		
		if (partner == null) 
			
			sentence = talks.get(0).getSentence(type);		
		
		else while (iterator.hasNext()) {
			talk = iterator.next();
			if (talk.getPartner() == partner) {
				sentence = talk.getSentence(type);		
				if (sentence == null) iterator.remove();
				break;
			}
		}
		return sentence;
	}

	private Talk getTalk(Human partner) {
		ListIterator<Talk> iterator ;
		Talk talk;
		
		iterator = talks.listIterator();
		
		
		if (partner == null) {
			
				return talks.get(0);
		}
		else while (iterator.hasNext()) {
			talk = iterator.next();
			if (talk.getPartner() == partner) {
				return talk;
			}
		}
		return  talks.get(0);
	}
}
