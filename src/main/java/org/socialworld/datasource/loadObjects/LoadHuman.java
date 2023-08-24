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
import org.socialworld.collections.SimulationObjectArray;
import org.socialworld.conversation.Lexem;
import org.socialworld.conversation.Talk;
import org.socialworld.conversation.Talk_SentenceType;
import org.socialworld.conversation.Word;
import org.socialworld.core.AllWords;
import org.socialworld.datasource.tablesSimulation.TableAcquaintance;
import org.socialworld.datasource.tablesSimulation.TableHuman;
import org.socialworld.datasource.tablesSimulation.TableInventory;
import org.socialworld.datasource.tablesSimulation.TableKnowledgeFact;
import org.socialworld.datasource.tablesSimulation.TableKnowledgePool;
import org.socialworld.datasource.tablesSimulation.TableKnowledgeSource;
import org.socialworld.datasource.tablesSimulation.TableSentenceList;
import org.socialworld.knowledge.Acquaintance;
import org.socialworld.knowledge.KnowledgeElement;
import org.socialworld.knowledge.KnowledgeFact;
import org.socialworld.knowledge.KnowledgeFact_Criterion;
import org.socialworld.knowledge.KnowledgeFact_Atoms;
import org.socialworld.knowledge.KnowledgeProperty;
import org.socialworld.knowledge.KnowledgeSource;
import org.socialworld.knowledge.KnowledgeSource_Type;
import org.socialworld.objects.*;
import org.socialworld.objects.access.GrantedAccessToProperty;
import org.socialworld.objects.access.HiddenHuman;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.SimpleClientActionHandler;
import org.socialworld.actions.handle.Inventory;

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
	private static boolean hasBeenCreatedYet = false;
	
	TableHuman tableHuman;
	int rowTableHuman;
	
	TableInventory tableInventory;
	int rowTableInventory;
	
	TableKnowledgePool tableKnowledgePool;
	TableKnowledgeSource tableKnowledgeSource;
	TableKnowledgeFact tableKnowledgeFact;
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
		tableKnowledgeSource = new TableKnowledgeSource();
		tableKnowledgeFact = new TableKnowledgeFact();
		tableAcquaintance = new TableAcquaintance();
		tableTalk = new TableSentenceList();
	}

	/**
	 * The method returns the only instance of the LoadHuman class to first caller.
	 * The method returns null to all further callers. 
	 * 
	 */
	public static LoadHuman getExlusiveInstance(SimulationObjectArray allObjects) {
		if (hasBeenCreatedYet == false) {
			instance = new LoadHuman(allObjects);
			hasBeenCreatedYet = true;
			return instance;
		}
		else return null;
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
	public void createObject(int objectID,  String fullClassName) {
		Object createdObject = createObjectForName(fullClassName);
		if (createdObject == null) return;

		Animal createdHuman = (Animal) createdObject;
		createdHuman.setObjectID(objectID);
		allObjects.set(objectID, createdHuman);
	}

	public void loadObject(int objectID) {
		Human createdHuman;
		WriteAccessToHuman wa;
		GrantedAccessToProperty propertiesToInit[];
		HiddenHuman hiddenHuman = null;
		
		createdHuman = (Human) allObjects.get(objectID);
	
		load(objectID);
		
		StateHuman state = new StateHuman();

		wa = new WriteAccessToHuman(createdHuman, state);
		propertiesToInit = new GrantedAccessToProperty[1];
		propertiesToInit[0] = GrantedAccessToProperty.all;
		hiddenHuman = wa.getMeHidden(propertiesToInit);

		initState(hiddenHuman,  objectID);
		initObject(hiddenHuman,  objectID);	

		SimpleClientActionHandler.getInstance().setHumanWrite(objectID, hiddenHuman);

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
			Inventory inventory = Inventory.getObjectNothing();
			boolean inventoryComplete = true;
			
			int leftHandID;
			SimulationObject leftHand = null;
			
			int rightHandID;
			SimulationObject rightHand = null;
			
			leftHandID = tableInventory.getLeftHand(rowTableHuman);
			rightHandID = tableInventory.getRightHand(rowTableHuman);
			
			if (leftHandID > 0) {
				leftHand = allObjects.get(leftHandID);
				if (leftHand == null) 	inventoryComplete = false;
				else leftHandID = 0;
			}
			if (rightHandID > 0) {
				rightHand = allObjects.get(rightHandID);
				if (rightHand == null)	inventoryComplete = false;
				else rightHandID = 0;
			}
			
			inventory = new Inventory(SimulationObject_Type.human);
			if (leftHandID > 0) inventory.setLeftHandID(leftHandID);
			if (rightHandID > 0) inventory.setLeftHandID(rightHandID);
				
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
		int ks_id;
		int kf_id;
		
		size = allLfdNrForObjectID.length;
		for (index = 0; index < size; index++) {
			rowTableKnowledgePool = tableKnowledgePool.getIndexFor2PK(objectID, allLfdNrForObjectID[index]);
			if (rowTableKnowledgePool >= 0) {
				word_id = tableKnowledgePool.getSubject(rowTableKnowledgePool);
				ks_id = tableKnowledgePool.getKSID(rowTableKnowledgePool);
				kf_id = tableKnowledgePool.getKFID(rowTableKnowledgePool);
				setKnowledge(hiddenHuman, word_id, ks_id, kf_id);
			}
		}
		
	}
	
	private void setKnowledge(HiddenHuman hiddenHuman, int subject, int ks_id, int kf_id) {
		int allLfdNrForKFID[];
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
		
		KnowledgeFact fact;
		KnowledgeSource source;
		KnowledgeElement knowledgeElement;
		
		List<Lexem> lexems;
	
		tableKnowledgeSource.select(tableKnowledgeSource.SELECT_ALL_COLUMNS, " WHERE ks_id = " + ks_id, "");
		sourceType_id = tableKnowledgeSource.getSourceType(0);
		sourceType = KnowledgeSource_Type.getName(sourceType_id);
		origin_id = tableKnowledgeSource.getOrigin(0);
		origin = allObjects.get(origin_id);
		source = new KnowledgeSource(sourceType, origin);

		
		tableKnowledgeFact.select(tableKnowledgeFact.SELECT_ALL_COLUMNS, " WHERE kf_id = " + kf_id, "");
		allLfdNrForKFID = tableKnowledgeFact.getAllPK2ForPK1(kf_id);
		size = allLfdNrForKFID.length;
		
		
		knowledgeElement = new KnowledgeElement(source, AllWords.getWord(subject).getLexem());

		for (index = 0; index < size; index++) {
			row = tableKnowledgeFact.getIndexFor2PK(kf_id, allLfdNrForKFID[index]);
			if (row >= 0) {
				kfc_id = tableKnowledgeFact.getKFC(row);
				kfc = KnowledgeFact_Criterion.getName(kfc_id);
				word_id = tableKnowledgeFact.getValue(row);
				word = AllWords.getWord(word_id);
				
				lexems = new ArrayList<Lexem>();
				lexems.add(word.getLexem());
				
				fact = new KnowledgeProperty(kfc, 
						new KnowledgeFact_Atoms(KnowledgeFact_Atoms.translateToAtoms(lexems)));
				
				knowledgeElement.add(fact);

			}
		}
		
		hiddenHuman.addKnowledgeElement(knowledgeElement);
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
		human.setPerceptionTypes(PerceptionTypePool.getInstance().getPerceptionTypes(indexPTP), this);
		human.setPosition(PositionPool.getInstance().getPosition(indexPosition), this);

		SimpleClientActionHandler.getInstance().setHumanWrite((int)objectID, human);

		return createdHuman;
	}
 * 
 */
