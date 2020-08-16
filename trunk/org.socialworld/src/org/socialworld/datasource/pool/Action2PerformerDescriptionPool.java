package org.socialworld.datasource.pool;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.actions.ActionMode;
import org.socialworld.attributes.Attribute;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.Expression;
import org.socialworld.calculation.FunctionByExpression;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.descriptions.Action2PerformerDescription;
import org.socialworld.calculation.descriptions.DescriptionBase;
import org.socialworld.calculation.expressions.Calculate;
import org.socialworld.calculation.expressions.Nothing;

public class Action2PerformerDescriptionPool extends DescriptionPool {

	
	

	private static Action2PerformerDescriptionPool instance;
	
	
	private Action2PerformerDescriptionPool () {

		super(ActionMode.maxIndex() + 1, 1);

		this.descriptions = new Action2PerformerDescription[sizeDescriptionsArray];
		
		initialize();
		
	}
	
	public static Action2PerformerDescriptionPool getInstance() {
		if (instance == null) {
			instance = new Action2PerformerDescriptionPool();
		}
		return instance;
	}

	protected  final DescriptionBase getNewDescription() {
		return new Action2PerformerDescription();
	}

	protected final Expression getStartExpression(List<String> lines4OneExpression) {
		return new Calculate(lines4OneExpression.get(0), lines4OneExpression.get(1));
	}

	/*
	public Action2PerformerDescription getDescription(ActionMode actionMode ) {
		int index;
		Action2PerformerDescription description = null;
		
		index = actionMode.getIndex() ;
		
		if (index >= 0 & sizeDescriptionsArray > index) 
			description = descriptions[index];
		else
			// create a dummy description with an expression that returns the invalid "nothing" value
			description = new Action2PerformerDescription();
		
		return description;
	}
*/
	/*
	@Override
	protected void initialize() {

		Action2PerformerDescription description;
		List<FunctionByExpression> expressions;
		
		for (int index = 0; index < sizeDescriptionsArray; index++) {
			
			if (ActionMode.getName(index) == ActionMode.ignore) {
				continue;
			}
			else {
				
				expressions = initializeWithTestData(ActionMode.getName(index));
				
				if (expressions.size() > 0) {
					
					description = new Action2PerformerDescription();
					
					for (int i = 0; i < expressions.size(); i++) {
						description.addFunction(expressions.get(i));
					}
					descriptions[index] = description;
					
				}
				else {
					// empty description
					descriptions[index] = new Action2PerformerDescription();
				}
			}
	
		}

	}
	*/
	
	protected void initializeWithTestData_FunctionByExpression() {
		// TODO implement lines (get from initializeWithTestData(ActionMode actionMode) )
	}

	private List<FunctionByExpression> initializeWithTestData(ActionMode actionMode) {
		
		Expression startExpression = Nothing.getInstance();
		List<FunctionByExpression> result;
		result = new ArrayList<FunctionByExpression>();
		
		switch (actionMode)
		{
			case walk:
			case run:
			case sneak:
			case jump:
			case swim:
			case fly:
				
				// directionEvent
				startExpression = new Calculate("GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_DIRECTION + "))", Value.VALUE_BY_NAME_EVENT_DIRECTION );
				result.add( new FunctionByExpression(startExpression) );

				// acceleration
				startExpression = new Calculate("GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_MOVE_ACCELERATION + "))", Value.VALUE_BY_NAME_EVENT_MOVE_ACCELERATION);
				result.add( new FunctionByExpression(startExpression) );

				// velocity
				startExpression = new Calculate("ADD(" +
													"GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_MOVE_VELOCITY + "))" + "," +
													"GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_MOVE_ACCELERATION + "))" +
												")" , Value.VALUE_BY_NAME_EVENT_MOVE_VELOCITY);
				result.add( new FunctionByExpression(startExpression) );
				
				break;
		
			case look:
				
				// Target
				startExpression = new Calculate("GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_TARGET + "))", Value.VALUE_BY_NAME_EVENT_TARGET);
				result.add( new FunctionByExpression(startExpression) );

				break;
				
			case hand:
			case foot:
	
				// directionEvent
				startExpression = new Calculate("GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_DIRECTION + "))", Value.VALUE_BY_NAME_EVENT_DIRECTION );
				result.add( new FunctionByExpression(startExpression) );
	
				// actorsIntensity
				startExpression = new Calculate("GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_INTENSITY + "))", Value.VALUE_BY_NAME_ACTION_INTENSITY);
				result.add( new FunctionByExpression(startExpression) );
	
				// intensity
				startExpression = new Calculate("MUL(" +
													"GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_INTENSITY + "))" + "," +
													"ATTR(" + Attribute.power.getIndex() + ")" +
												")" , Value.VALUE_BY_NAME_EVENT_INTENSITY);
				result.add( new FunctionByExpression(startExpression) );
							
				break;
		
				

				
				
			case pull:
			case push:
				
				// directionEvent
				startExpression = new Calculate("GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_DIRECTION + "))", Value.VALUE_BY_NAME_EVENT_DIRECTION );
				result.add( new FunctionByExpression(startExpression) );

				// actorsIntensity
				startExpression = new Calculate("GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_INTENSITY + "))", Value.VALUE_BY_NAME_ACTION_INTENSITY);
				result.add( new FunctionByExpression(startExpression) );

				// intensity
				startExpression = new Calculate("MUL(" +
													"GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_INTENSITY + "))" + "," +
													"ATTR(" + Attribute.power.getIndex() + ")" +
												")" , Value.VALUE_BY_NAME_EVENT_INTENSITY);
				result.add( new FunctionByExpression(startExpression) );
				
				// Item1
				startExpression = new Calculate("GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_ITEM1 + "))", Value.VALUE_BY_NAME_EVENT_HANDLE_ITEM1);
				result.add( new FunctionByExpression(startExpression) );

				break;
				
			case useItemLeftHand:
			case useItemRightHand:

				// directionEvent
				startExpression = new Calculate("GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_DIRECTION + "))", Value.VALUE_BY_NAME_EVENT_DIRECTION );
				result.add( new FunctionByExpression(startExpression) );

				// actorsIntensity
				startExpression = new Calculate("GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_INTENSITY + "))", Value.VALUE_BY_NAME_ACTION_INTENSITY);
				result.add( new FunctionByExpression(startExpression) );

				// intensity
				startExpression = new Calculate("MUL(" +
													"GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_INTENSITY + "))" + "," +
													"ATTR(" + Attribute.power.getIndex() + ")" +
												")" , Value.VALUE_BY_NAME_EVENT_INTENSITY);
				result.add( new FunctionByExpression(startExpression) );
				
				// Item1
				startExpression = new Calculate("GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_ITEM1 + "))", Value.VALUE_BY_NAME_EVENT_HANDLE_ITEM1);
				result.add( new FunctionByExpression(startExpression) );
				
				break;
				
			case useTwoItems:
			case combineItems_AddRightToLeft:
			case combineItems_AddLeftToRight:
				
				// directionEvent
				startExpression = new Calculate("GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_DIRECTION + "))", Value.VALUE_BY_NAME_EVENT_DIRECTION );
				result.add( new FunctionByExpression(startExpression) );

				// actorsIntensity
				startExpression = new Calculate("GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_INTENSITY + "))", Value.VALUE_BY_NAME_ACTION_INTENSITY);
				result.add( new FunctionByExpression(startExpression) );

				// intensity
				startExpression = new Calculate("MUL(" +
													"GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_INTENSITY + "))" + "," +
													"ATTR(" + Attribute.power.getIndex() + ")" +
												")" , Value.VALUE_BY_NAME_EVENT_HANDLE_INTENSITY);
				result.add( new FunctionByExpression(startExpression) );
				
				// Item1
				startExpression = new Calculate("GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_ITEM1 + "))", Value.VALUE_BY_NAME_EVENT_HANDLE_ITEM1);
				result.add( new FunctionByExpression(startExpression) );

				// Item2
				startExpression = new Calculate("GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_ITEM2 + "))", Value.VALUE_BY_NAME_EVENT_HANDLE_ITEM2);
				result.add( new FunctionByExpression(startExpression) );

				break;
				
			case punchRightFistStraight:
				
				
				// directionHit
				startExpression = new Calculate("GET(GETVal(" + PropertyName.stateSeer_directionView.toString() + "))", Value.VALUE_BY_NAME_EVENT_PUNCH_DIRECTION );
				result.add( new FunctionByExpression(startExpression) );

				// actorsIntensity
				startExpression = new Calculate("GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_INTENSITY + "))", Value.VALUE_BY_NAME_ACTION_INTENSITY);
				result.add( new FunctionByExpression(startExpression) );

				// intensity
				startExpression = new Calculate("MUL(" +
													"GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_INTENSITY + "))" + "," +
													"ATTR(" + Attribute.power.getIndex() + ")" +
												")" , Value.VALUE_BY_NAME_EVENT_PUNCH_INTENSITY);
				result.add( new FunctionByExpression(startExpression) );

				// directionChest
				startExpression = new Calculate("GET(GETVal(" + PropertyName.simobj_directionChest.toString() + "))", PropertyName.simobj_directionChest.toString());
				result.add( new FunctionByExpression(startExpression) );

				// directionView
				startExpression = new Calculate("GET(GETVal(" + PropertyName.stateSeer_directionView.toString() + "))", PropertyName.stateSeer_directionView.toString());
				result.add( new FunctionByExpression(startExpression) );
				
				break;
				
			case weaponClub:
				
				// directionHit
				startExpression = new Calculate("GET(GETVal(" + PropertyName.stateSeer_directionView.toString() + "))", Value.VALUE_BY_NAME_EVENT_USEWEAPON_DIRECTION );
				result.add( new FunctionByExpression(startExpression) );

				// actorsIntensity
				startExpression = new Calculate("GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_INTENSITY + "))", Value.VALUE_BY_NAME_ACTION_INTENSITY);
				result.add( new FunctionByExpression(startExpression) );

				// intensity
				startExpression = new Calculate("MUL(" +
													"GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_INTENSITY + "))" + "," +
													"ATTR(" + Attribute.power.getIndex() + ")" +
												")" , Value.VALUE_BY_NAME_EVENT_USEWEAPON_INTENSITY);
				result.add( new FunctionByExpression(startExpression) );

				// weapon
				startExpression = new Calculate("GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_WEAPON + "))", Value.VALUE_BY_NAME_EVENT_WEAPON);
				result.add( new FunctionByExpression(startExpression) );

				// directionChest
				startExpression = new Calculate("GET(GETVal(" + PropertyName.simobj_directionChest.toString() + "))", PropertyName.simobj_directionChest.toString());
				result.add( new FunctionByExpression(startExpression) );

				// directionView
				startExpression = new Calculate("GET(GETVal(" + PropertyName.stateSeer_directionView.toString() + "))", PropertyName.stateSeer_directionView.toString());
				result.add( new FunctionByExpression(startExpression) );
				
				break;
				
			case listenTo:
			case understand:
				
				// Sentence
				startExpression = new Calculate("GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_SENTENCE + "))", Value.VALUE_BY_NAME_EVENT_SENTENCE);
				result.add( new FunctionByExpression(startExpression) );
				
				// Target
				startExpression = new Calculate("GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_TARGET + "))", Value.VALUE_BY_NAME_EVENT_TARGET);
				result.add( new FunctionByExpression(startExpression) );

				// directionView
				startExpression = new Calculate("GET(GETVal(" + PropertyName.stateSeer_directionView.toString() + "))", PropertyName.stateSeer_directionView.toString());
				result.add( new FunctionByExpression(startExpression) );

				break;
				
			case answerNormal:
			case answerScream:
			case answerWhisper:
				
				// directionEvent
				startExpression = new Calculate("GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_DIRECTION + "))", Value.VALUE_BY_NAME_EVENT_DIRECTION );
				result.add( new FunctionByExpression(startExpression) );

				// Target (Partner)
				startExpression = new Calculate("GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_TARGET + "))", Value.VALUE_BY_NAME_EVENT_TARGET);
				result.add( new FunctionByExpression(startExpression) );
				
				// Sentence
				startExpression = new Calculate("GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_SENTENCE + "))", Value.VALUE_BY_NAME_EVENT_SENTENCE);
				result.add( new FunctionByExpression(startExpression) );

				// SentenceType
				startExpression = new Calculate("GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_SENTENCETYPE + "))", Value.VALUE_BY_NAME_EVENT_SENTENCETYPE);
				result.add( new FunctionByExpression(startExpression) );
				
				break;
				
			case askNormal:
			case askScream:
			case askWhisper:
	
				// directionEvent
				startExpression = new Calculate("GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_DIRECTION + "))", Value.VALUE_BY_NAME_EVENT_DIRECTION );
				result.add( new FunctionByExpression(startExpression) );

				// Target (Partner)
				startExpression = new Calculate("GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_TARGET + "))", Value.VALUE_BY_NAME_EVENT_TARGET);
				result.add( new FunctionByExpression(startExpression) );
				
				// Sentence
				startExpression = new Calculate("GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_SENTENCE + "))", Value.VALUE_BY_NAME_EVENT_SENTENCE);
				result.add( new FunctionByExpression(startExpression) );

				// SentenceType
				startExpression = new Calculate("GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_SENTENCETYPE + "))", Value.VALUE_BY_NAME_EVENT_SENTENCETYPE);
				result.add( new FunctionByExpression(startExpression) );
				
				break;
			
			case normal:
				
				// directionEvent
				startExpression = new Calculate("GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_DIRECTION + "))", Value.VALUE_BY_NAME_EVENT_DIRECTION );
				result.add( new FunctionByExpression(startExpression) );

				// Target (Partner)
				startExpression = new Calculate("GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_TARGET + "))", Value.VALUE_BY_NAME_EVENT_TARGET);
				result.add( new FunctionByExpression(startExpression) );
				
				// Sentence
				startExpression = new Calculate("GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_SENTENCE + "))", Value.VALUE_BY_NAME_EVENT_SENTENCE);
				result.add( new FunctionByExpression(startExpression) );

				// SentenceType
				startExpression = new Calculate("GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_SENTENCETYPE + "))", Value.VALUE_BY_NAME_EVENT_SENTENCETYPE);
				result.add( new FunctionByExpression(startExpression) );
				
				break;
				
			case scream:
				
				// loudness
				startExpression = new Calculate("GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_INTENSITY + "))", Value.VALUE_BY_NAME_EVENT_SAY_LOUDNESS);
				result.add( new FunctionByExpression(startExpression) );

				// directionEvent
				startExpression = new Calculate("GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_DIRECTION + "))", Value.VALUE_BY_NAME_EVENT_DIRECTION );
				result.add( new FunctionByExpression(startExpression) );

				// Target (Partner)
				startExpression = new Calculate("GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_TARGET + "))", Value.VALUE_BY_NAME_EVENT_TARGET);
				result.add( new FunctionByExpression(startExpression) );
				
				// Sentence
				startExpression = new Calculate("GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_SENTENCE + "))", Value.VALUE_BY_NAME_EVENT_SENTENCE);
				result.add( new FunctionByExpression(startExpression) );

				// SentenceType
				startExpression = new Calculate("GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_SENTENCETYPE + "))", Value.VALUE_BY_NAME_EVENT_SENTENCETYPE);
				result.add( new FunctionByExpression(startExpression) );
				
				break;
				
			case whisper:
	
				// directionEvent
				startExpression = new Calculate("GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_DIRECTION + "))", Value.VALUE_BY_NAME_EVENT_DIRECTION );
				result.add( new FunctionByExpression(startExpression) );

				// Target (Partner)
				startExpression = new Calculate("GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_TARGET + "))", Value.VALUE_BY_NAME_EVENT_TARGET);
				result.add( new FunctionByExpression(startExpression) );
				
				// Sentence
				startExpression = new Calculate("GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_SENTENCE + "))", Value.VALUE_BY_NAME_EVENT_SENTENCE);
				result.add( new FunctionByExpression(startExpression) );

				// SentenceType
				startExpression = new Calculate("GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_SENTENCETYPE + "))", Value.VALUE_BY_NAME_EVENT_SENTENCETYPE);
				result.add( new FunctionByExpression(startExpression) );
				
				break;
				
			case takeItem:

				// equip item
				startExpression = new Calculate("GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_EQUIP_ITEM + "))", Value.VALUE_BY_NAME_EVENT_EQUIP_ITEM);
				result.add( new FunctionByExpression(startExpression) );

				break;
				
			case setItemToInventory:
				
				// itemIsDrinkable
				startExpression = new Calculate("GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_EQUIP_ITEM + ").IsA(IDrinkable))", Value.VALUE_BY_NAME_EVENT_EQUIP_ITEMISDRINKABLE);
				result.add( new FunctionByExpression(startExpression) );

				// itemIsEatable
				startExpression = new Calculate("GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_EQUIP_ITEM + ").IsA(IEatable))", Value.VALUE_BY_NAME_EVENT_EQUIP_ITEMISEATABLE);
				result.add( new FunctionByExpression(startExpression) );

				break;
				
			case sleep:
				break;
				
			case drink:
				

				// itemDrink
				startExpression = new Calculate("GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_BF_ITEMDRINK + "))", Value.VALUE_BY_NAME_EVENT_BF_ITEMDRINK);
				result.add( new FunctionByExpression(startExpression) );
				
				break;
				
			case eat:
				

				// itemEat
				startExpression = new Calculate("GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_BF_ITEMEAT + "))", Value.VALUE_BY_NAME_EVENT_BF_ITEMEAT);
				result.add( new FunctionByExpression(startExpression) );
				
				break;
				
			case piss:
			case shit: 	

				break;
				
			default:
				
				break;
				
		}
		
		return result;
		
	}
	

	
}
