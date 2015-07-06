/*
* Social World
* Copyright (C) 2014  Mathias Sikos
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
package org.socialworld.datasource.loadObjects;

import org.socialworld.attributes.AttributeArray;
import org.socialworld.attributes.Inventory;
import org.socialworld.collections.SimulationObjectArray;
import org.socialworld.conversation.Talk;
import org.socialworld.conversation.Talk_SentenceType;
import org.socialworld.conversation.Word;
import org.socialworld.core.AllWords;
import org.socialworld.datasource.tablesSimulation.TableAcquaintance;
import org.socialworld.datasource.tablesSimulation.TableHuman;
import org.socialworld.datasource.tablesSimulation.TableInventory;
import org.socialworld.datasource.tablesSimulation.TableKnowledgeFactAndSource;
import org.socialworld.datasource.tablesSimulation.TableKnowledgePool;
import org.socialworld.datasource.tablesSimulation.TableSentenceList;
import org.socialworld.knowledge.Acquaintance;
import org.socialworld.knowledge.Knowledge;
import org.socialworld.knowledge.KnowledgeFact;
import org.socialworld.knowledge.KnowledgeFact_Criterion;
import org.socialworld.knowledge.KnowledgeFact_Value;
import org.socialworld.knowledge.KnowledgeSource;
import org.socialworld.knowledge.KnowledgeSource_Type;
import org.socialworld.objects.*;
import org.socialworld.objects.access.HiddenHuman;
import org.socialworld.SimpleClientActionHandler;

/**
 * Because of being a singleton there exists
 *         only one instance of the class. 
 *         The instance is responsible for creating human objects
 *         Therefore the object data is loaded from a data source
 *         and put to a new created instance of class Human
 * @author Mathias Sikos (tyloesand) 
 */
public class LoadHuman extends LoadAnimal {
	
	private static LoadHuman instance;
	
	TableHuman tableHuman;
	int rowTableHuman;
	
	TableInventory tableInventory;
	int rowTableInventory;
	
	TableKnowledgePool tableKnowledgePool;
	TableKnowledgeFactAndSource tableKnowledgeFactAndSource;
	int allLfdNrForObjectID[];
		
	TableAcquaintance tableAcquaintance;
	int allAcqPartnerIDsForObjectID[];
	
	TableSentenceList tableTalk;
	int allTalkPartnerIDsForObjectID[];

	
	/**
	 * Because of being a singleton the instance is created in a private
	 * constructor.
	 */
	private LoadHuman(SimulationObjectArray allObjects) {
		super(allObjects);

		tableHuman = new TableHuman();
		tableInventory = new TableInventory();
		tableKnowledgePool = new TableKnowledgePool();
		tableKnowledgeFactAndSource = new TableKnowledgeFactAndSource();
		tableAcquaintance = new TableAcquaintance();
		tableTalk = new TableSentenceList();
	}

	/**
	 * The method creates the only instance of the LoadAnimal.
	 * 
	 */
	public static LoadHuman createInstance(SimulationObjectArray allObjects) {
		if (instance == null) {
			instance = new LoadHuman(allObjects);
			
		}
		return instance;

	}
	
	protected void load(int objectID) {
		super.load(objectID);
		
		tableHuman.select(tableHuman.SELECT_ALL_COLUMNS, " WHERE id = " + objectID , "");
		tableInventory.select(tableInventory.SELECT_ALL_COLUMNS, " WHERE id = " + objectID , "");
		tableKnowledgePool.select(tableKnowledgePool.SELECT_ALL_COLUMNS, " WHERE id = " + objectID , "");
		tableAcquaintance.selectDistinct(tableAcquaintance.SELECT_ID_PARTNERID, " WHERE id = " + objectID , "");
		tableTalk.selectDistinct(tableTalk.SELECT_ID_PARTNERID, " WHERE id = " + objectID , "");
		
		rowTableHuman = tableHuman.getIndexFor1PK(objectID);
		rowTableInventory = tableInventory.getIndexFor1PK(objectID);
		allLfdNrForObjectID = tableKnowledgePool.getAllPK2ForPK1(objectID);
		allAcqPartnerIDsForObjectID = tableAcquaintance.getAllPK2ForPK1(objectID);
		allTalkPartnerIDsForObjectID = tableTalk.getAllPK2ForPK1(objectID);
	}

	
	/**
	 * The method creates an instance of class Human.
	 * 
	 * @param objectID
	 */
	public void createObject(int objectID) {
		Human createdHuman = new Human(objectID);
		allObjects.set(objectID, createdHuman);
	}

	public void loadObject(int objectID) {
		Human createdHuman;
		HiddenHuman hiddenHuman = null;
		
		createdHuman = (Human) allObjects.get(objectID);
	
		load(objectID);
		
		StateHuman state = new StateHuman();

		// the constructor "returns" the hidden human object
		WriteAccessToHuman human = new WriteAccessToHuman(createdHuman, state, hiddenHuman);

		initState(hiddenHuman,  objectID);
		initObject(hiddenHuman,  objectID);	

		SimpleClientActionHandler.getInstance().setHumanWrite(objectID, human);

	}

	protected void initObject(HiddenHuman hiddenHuman, int objectID) {
		super.initObject(hiddenHuman,  objectID);
	}

	protected void initState(HiddenHuman hiddenHuman, int objectID) {
		super.initState(hiddenHuman,  objectID);	
		
		
		if (rowTableHuman >= 0) {
			String lastSentence;
			lastSentence = tableHuman.getLastSentence(rowTableHuman);
			hiddenHuman.setLastSaidSentence(lastSentence);
		}
		if (rowTableInventory >= 0) {
			Inventory inventory;
			inventory = new Inventory();
			
			int leftHandID;
			SimulationObject leftHand = null;
			
			int rightHandID;
			SimulationObject rightHand = null;
			
			leftHandID = tableInventory.getLeftHand(rowTableHuman);
			rightHandID = tableInventory.getRightHand(rowTableHuman);
			
			if (leftHandID > 0) {
				leftHand = allObjects.get(leftHandID);
				if (leftHand == null) {
					// TODO
				}
			}
			if (rightHandID > 0) {
				rightHand = allObjects.get(rightHandID);
				if (rightHand == null) {
					// TODO
				}
			}
			inventory.setLeftHand(leftHand);
			inventory.setRightHand(rightHand);
			hiddenHuman.setInventory(inventory);
		}

		setKnowledgePool(hiddenHuman,  objectID);
		setAcquaintancePool(hiddenHuman,  objectID);
		setTalks(hiddenHuman,  objectID);
	}

	private void setKnowledgePool(HiddenHuman hiddenHuman, int objectID) {
		int size;
		int index;
		int rowTableKnowledgePool;
		int word_id;
		int kfs_id;
		
		size = allLfdNrForObjectID.length;
		for (index = 0; index < size; index++) {
			rowTableKnowledgePool = tableKnowledgePool.getIndexFor2PK(objectID, allLfdNrForObjectID[index]);
			if (rowTableKnowledgePool >= 0) {
				word_id = tableKnowledgePool.getSubject(rowTableKnowledgePool);
				kfs_id = tableKnowledgePool.getKFSID(rowTableKnowledgePool);
				setKnowledge(hiddenHuman, word_id, kfs_id);
			}
		}
		
	}
	
	private void setKnowledge(HiddenHuman hiddenHuman, int subject, int kfs_id) {
		int allLfdNrForKFSID[];
		int size;
		int index;
		int row;
		
		int kfc_id;
		KnowledgeFact_Criterion kfc;
		int word_id;
		Word word;
		int sourceType_id;
		KnowledgeSource_Type sourceType;
		int origin_id;
		SimulationObject origin;
		
		KnowledgeFact facts[];
		KnowledgeSource sources[];
		
		tableKnowledgeFactAndSource.select(tableKnowledgeFactAndSource.SELECT_ALL_COLUMNS, " WHERE kfs_id = " + kfs_id, "");
		allLfdNrForKFSID = tableKnowledgeFactAndSource.getAllPK2ForPK1(kfs_id);
		size = allLfdNrForKFSID.length;
		
		facts = new KnowledgeFact[size];
		sources = new KnowledgeSource[size];
		
		for (index = 0; index < size; index++) {
			row = tableKnowledgeFactAndSource.getIndexFor2PK(kfs_id, allLfdNrForKFSID[index]);
			if (row >= 0) {
				kfc_id = tableKnowledgeFactAndSource.getKFC(row);
				kfc = KnowledgeFact_Criterion.getName(kfc_id);
				word_id = tableKnowledgeFactAndSource.getValue(row);
				word = AllWords.getWord(word_id);
				sourceType_id = tableKnowledgeFactAndSource.getSourceType(row);
				sourceType = KnowledgeSource_Type.getName(sourceType_id);
				origin_id = tableKnowledgeFactAndSource.getOrigin(row);
				origin = allObjects.get(origin_id);
				
				facts[index] = new KnowledgeFact(kfc, new KnowledgeFact_Value(word));
				sources[index] = new KnowledgeSource(sourceType, origin);
			}
		}
		hiddenHuman.addKnowledge(new Knowledge(AllWords.getWord(subject), facts, sources ));
	}


	private void setAcquaintancePool(HiddenHuman hiddenHuman, int objectID) {
		int size;
		int index;
		int partner_id;
		int attributes[];
		
		size = allAcqPartnerIDsForObjectID.length;
		for (index = 0; index < size; index++) {
			
			partner_id = allAcqPartnerIDsForObjectID[index];
			
			tableAcquaintance.select(tableAcquaintance.SELECT_ALL_COLUMNS, " WHERE id = " + objectID +  " AND partner_id = partner_id " , " ORDER BY attrib_nr");
			
			attributes = tableAcquaintance.getAttributes();
			hiddenHuman.addAcquaintance(new Acquaintance((Human)allObjects.get(partner_id), new AttributeArray(attributes) ));
			
		}
		
	}

	private void setTalks(HiddenHuman hiddenHuman, int objectID) {
		int size;
		int index;
		int partner_id;

		int rowTableTalks;
		int rowCountTableTalks;

		Talk talk;
		Talk_SentenceType type; 
		String sentence;
		
		size = allTalkPartnerIDsForObjectID.length;
		for (index = 0; index < size; index++) {
			partner_id = allTalkPartnerIDsForObjectID[index];
			talk = new Talk((Human)allObjects.get(partner_id));
			tableTalk.select(tableTalk.SELECT_ALL_COLUMNS, " WHERE id = " + objectID + " AND partner_id = " + partner_id, " ORDER BY type, lfd_nr ");
			rowCountTableTalks = tableTalk.rowCount();
			for (rowTableTalks = 0; rowTableTalks < rowCountTableTalks; rowCountTableTalks++) {
				type = Talk_SentenceType.getName(tableTalk.getType(rowTableTalks)) ;
				sentence = tableTalk.getSentence(rowTableTalks);
				talk.addSentence(sentence, type);
			}
		}
		
	}

}
/* save
 * 
	public Human getObject(long objectID) {
		
		double gauss_value;
		int indexACMP;
		int indexAAP;
		int indexITP; 
		int indexRTP; 
		int indexS2AP; 
		int indexPosition;
		
		gauss_value = random.nextGaussian();
		indexACMP = mapGaussToIndex(gauss_value, AttributeCalculatorMatrixPool.CAPACITY_ACMP_ARRAY);
		indexAAP = mapGaussToIndex(gauss_value, AttributeArrayPool.CAPACITY_AAP_ARRAY);
		indexITP = mapGaussToIndex(gauss_value, InfluenceTypePool.CAPACITY_ITP_ARRAY);
		indexRTP = mapGaussToIndex(gauss_value, ReactionTypePool.CAPACITY_RTP_ARRAY);
		indexS2AP = mapGaussToIndex(gauss_value, State2ActionTypePool.CAPACITY_S2AP_ARRAY);
		indexPosition = mapGaussToIndex(gauss_value, PositionPool.CAPACITY_PosP_ARRAY);
		
		Human createdHuman = new Human();
		WriteAccessToHuman human = new WriteAccessToHuman(createdHuman);
		
		human.setObjectID(objectID, this);
		human.setMatrix(	
					AttributeCalculatorMatrixPool.getInstance().getMatrix(indexACMP),
					this);
		human.setAttributes(
					AttributeArrayPool.getInstance().getArray(indexAAP),
					this);
		human.setInfluenceTypes(InfluenceTypePool.getInstance().getInfluenceTypes(indexITP), this);
		human.setReactionTypes(ReactionTypePool.getInstance().getReactionTypes(indexRTP), this);
		human.setState2ActionType(State2ActionTypePool.getInstance().getState2ActionType(indexS2AP), this);
		human.setPosition(PositionPool.getInstance().getPosition(indexPosition), this);

		SimpleClientActionHandler.getInstance().setHumanWrite((int)objectID, human);

		return createdHuman;
	}
 * 
 */
