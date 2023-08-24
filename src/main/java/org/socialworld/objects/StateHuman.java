/*
* Social World
* Copyright (C) 2015  Mathias Sikos
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.  
*
* or see http://www.gnu.org/licenses/gpl-2.0.html
*
*/
package org.socialworld.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.socialworld.calculation.application.Scheduler;
import org.socialworld.conversation.Talk;
import org.socialworld.conversation.Talk_SentenceType;
import org.socialworld.core.Event;
import org.socialworld.knowledge.Acquaintance;
import org.socialworld.knowledge.AcquaintancePool;
import org.socialworld.knowledge.IAnswer;
import org.socialworld.knowledge.KnowledgeSource;
import org.socialworld.objects.access.GrantedAccessToProperty;
import org.socialworld.objects.access.HiddenHuman;
import org.socialworld.objects.properties.IWeapon;
import org.socialworld.tools.Generation;
import org.socialworld.tools.StringTupel;

/**
 * @author Mathias Sikos
 *
 */
public class StateHuman extends StateAnimal {

	private AcquaintancePool acquaintance;
	
	private ArrayList<Talk> talks;
	private String lastSaidSentence;

	private GrantedAccessToProperty grantAccessToPropertyTalk[];
	
	
	///////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////	meta information    ///////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	private static StateHuman singletonDummyForGenerationTools;
	private static StringTupel[] propertiesMetaInfos = new StringTupel[]{
		} ;
	
	public static StateHuman getInstance(Generation calledFromGeneration) {
		if (singletonDummyForGenerationTools == null) {
			singletonDummyForGenerationTools = new StateHuman(calledFromGeneration);
		}
		return singletonDummyForGenerationTools;
	}
	
	protected StateHuman(Generation calledFromGeneration) {
		super(calledFromGeneration);
		listOfPropertyMetaInfo = super.getPropertiesMetaInfos();
		for (int indexAdd = 0; indexAdd < propertiesMetaInfos.length; indexAdd++) {
			listOfPropertyMetaInfo.add(propertiesMetaInfos[indexAdd]);
		}
	}


///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    creating instance for simulation    //////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	
	public StateHuman() {
		super();
		
		talks = new ArrayList<Talk>();
		acquaintance = new AcquaintancePool();
		
		grantAccessToPropertyTalk = new GrantedAccessToProperty[1];
		grantAccessToPropertyTalk[0] = GrantedAccessToProperty.talks;
		

	}

	
	
	void calculateEventInfluence(Event event) {
		
		super.calculateEventInfluence(event);
		
		Scheduler.getInstance().calculateTalkChangedByEvent(event, ((StateHuman)getMeReadableOnly()), ((HiddenHuman)getMeWritableButHidden(grantAccessToPropertyTalk) ));
		
		
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    KNOWLEDGE  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	final void addFactsFromSentence(String sentence, KnowledgeSource source, WriteAccessToHuman guard) {
		if (checkGuard(guard)) {
			this.knowledge.addFactsFromSentence(sentence, source);
		}
	}


	final void addAcquaintance(Acquaintance acquaintance, WriteAccessToHuman guard) {
		if (checkGuard(guard)) {
			this.acquaintance.addAcquaintance(acquaintance);
		}
	}

	final public Acquaintance getAcquaintance(Human partner) {
		return new Acquaintance(this.acquaintance.getAcquaintance(partner));
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    TALK       ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	final void setTalks(ArrayList<Talk> talks, WriteAccessToHuman guard) {
		if (checkGuard(guard)) {
			this.talks = talks;
		}
	}

	final void addSentence(Human partner, Talk_SentenceType type,  String sentence, WriteAccessToHuman guard) {
		if (checkGuard(guard)) {
			Talk talk = findTalk(partner);
			talk.addSentence(sentence, type);
		}
	}

	final void addAnswer(IAnswer answer,  Human partner, WriteAccessToHuman guard) {
		if (checkGuard(guard)) {
			Talk talk = findTalk(partner);
			talk.addAnswer(answer);
		}
	}
	
	final void setLastSaidSentence(String sentence, WriteAccessToHuman guard) {
		if (checkGuard(guard)) {
			lastSaidSentence = sentence;
		}
	}
	
	final public String getLastSaidSentence() {
		return new String(lastSaidSentence);
	}
	
	final public List<IAnswer> getAnswersForQuestion(String question) {
		// no copy
		// because a new answer is created in method KnowledgePool.getAnswerForQuestion()
		return this.knowledge.getAnswersForQuestion(question);
	}

	final String findSentence(Human partner, Talk_SentenceType type) {
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
		return new String(sentence);

	}

	private Talk findTalk(Human partner) {
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

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    INVENTORY  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

/*	
	final public IWeapon getLeftHandWeapon() {
		return _getLeftHandWeapon();
	}
	
	final public IWeapon getRightHandWeapon() {
		return _getRightHandWeapon();
	}
*/

}
