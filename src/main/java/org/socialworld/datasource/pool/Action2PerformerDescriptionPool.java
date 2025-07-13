package org.socialworld.datasource.pool;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.actions.ActionMode;
import org.socialworld.attributes.Attribute;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.Expression;
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

	protected final void initialize() {
		initializeFromLines();
	}

	@Override
	protected void initializeWithTestData(InitializeDataModus modus) {
		
		switch (modus) {
		case lines: 
			initializeWithTestData_Lines();
		
		default:
			// do nothing
		}
	}
	

	protected  final DescriptionBase getNewDescription() {
		return new Action2PerformerDescription();
	}

	protected final DescriptionBase getDescription(String description) {
		return new Action2PerformerDescription(getGsonInstance(), description);
	}

	protected final Expression getStartExpressionForLines(List<String> lines4OneExpression) {
		return new Calculate(lines4OneExpression.get(0), lines4OneExpression.get(1));
	}

	protected final Expression getStartExpressionForIDs(List<Integer> ids4OneExpression) {
		return  Nothing.getInstance();
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
	
	private void initializeWithTestData_Lines() {
		
		List<Lines> allLines;
		allLines = new ArrayList<Lines>();
		
		Lines lines;
		
		for (ActionMode actionmode : ActionMode.values()) {
			lines = new Lines(actionmode.getIndex(), rangeSecondIndex);
			initializeWithTestData(actionmode, lines);
			allLines.add(lines);
		}
		
		createExpressions(allLines);
		
	}

	private void initializeWithTestData(ActionMode actionMode, Lines lines) {

		// TODO implement adding lines (instead of returning List<FunctionByExpression>) )

		
		switch (actionMode)
		{
			case walk:
			case run:
			case sneak:
			case jump:
			case swim:
			case fly:
				
				
				
				// directionEvent
				lines.add(0, 0, "GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_DIRECTION + "))");
				lines.add(0, 0, Value.VALUE_BY_NAME_EVENT_DIRECTION);

				// acceleration
				lines.add(0, 1, "GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_MOVE_ACCELERATION + "))");
				lines.add(0, 1, Value.VALUE_BY_NAME_EVENT_MOVE_ACCELERATION);

				// velocity
				lines.add(0, 2, "ADD(" +
									"GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_MOVE_VELOCITY + "))" + "," +
									"GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_MOVE_ACCELERATION + "))" +
								")" ); 
				lines.add(0, 2, Value.VALUE_BY_NAME_EVENT_MOVE_VELOCITY);
				
				break;
		
			case look:
				
				// Target
				lines.add(0, 0, "GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_TARGET + "))");
				lines.add(0, 0, Value.VALUE_BY_NAME_EVENT_TARGET);

				break;
				
			case hand:
			case foot:
	
				// directionEvent
				lines.add(0, 0, "GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_DIRECTION + "))");
				lines.add(0, 0, Value.VALUE_BY_NAME_EVENT_DIRECTION);
	
				// actorsIntensity
				lines.add(0, 1, "GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_INTENSITY + "))");
				lines.add(0, 1, Value.VALUE_BY_NAME_ACTION_INTENSITY);
	
				// intensity
				lines.add(0, 2, "MUL(" +
									"GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_INTENSITY + "))" + "," +
									"ATTR(" + Attribute.power.getIndex() + ")" +
								")");
				lines.add(0, 2, Value.VALUE_BY_NAME_EVENT_INTENSITY);
							
				break;
		
				

				
				
			case pull:
			case push:
				
				// directionEvent
				lines.add(0, 0, "GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_DIRECTION + "))");
				lines.add(0, 0, Value.VALUE_BY_NAME_EVENT_DIRECTION);

				// actorsIntensity
				lines.add(0, 1, "GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_INTENSITY + "))");
				lines.add(0, 1, Value.VALUE_BY_NAME_ACTION_INTENSITY);

				// intensity
				lines.add(0, 2, "MUL(" +
									"GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_INTENSITY + "))" + "," +
									"ATTR(" + Attribute.power.getIndex() + ")" +
								")");
				lines.add(0, 2, Value.VALUE_BY_NAME_EVENT_INTENSITY);
				
				// Item1
				lines.add(0, 3, "GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_ITEM1 + "))");
				lines.add(0, 3, Value.VALUE_BY_NAME_EVENT_HANDLE_ITEM1);

				break;
				
			case useItemLeftHand:
			case useItemRightHand:

				// directionEvent
				lines.add(0, 0, "GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_DIRECTION + "))");
				lines.add(0, 0, Value.VALUE_BY_NAME_EVENT_DIRECTION);

				// actorsIntensity
				lines.add(0, 1, "GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_INTENSITY + "))");
				lines.add(0, 1, Value.VALUE_BY_NAME_ACTION_INTENSITY);

				// intensity
				lines.add(0, 2, "MUL(" +
									"GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_INTENSITY + "))" + "," +
									"ATTR(" + Attribute.power.getIndex() + ")" +
								")");
				lines.add(0, 2, Value.VALUE_BY_NAME_EVENT_INTENSITY);
				
				// Item1
				lines.add(0, 3, "GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_ITEM1 + "))");
				lines.add(0, 3, Value.VALUE_BY_NAME_EVENT_HANDLE_ITEM1);
			
				break;
				
			case useTwoItems:
			case combineItems_AddRightToLeft:
			case combineItems_AddLeftToRight:
				
				// directionEvent
				lines.add(0, 0, "GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_DIRECTION + "))");
				lines.add(0, 0, Value.VALUE_BY_NAME_EVENT_DIRECTION);

				// actorsIntensity
				lines.add(0, 1, "GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_INTENSITY + "))");
				lines.add(0, 1, Value.VALUE_BY_NAME_ACTION_INTENSITY);

				// intensity
				lines.add(0, 2, "MUL(" +
									"GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_INTENSITY + "))" + "," +
									"ATTR(" + Attribute.power.getIndex() + ")" +
								")");
				lines.add(0, 2, Value.VALUE_BY_NAME_EVENT_HANDLE_INTENSITY);
				
				// Item1
				lines.add(0, 3, "GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_ITEM1 + "))");
				lines.add(0, 3, Value.VALUE_BY_NAME_EVENT_HANDLE_ITEM1);

				// Item2
				lines.add(0, 4, "GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_ITEM2 + "))");
				lines.add(0, 4, Value.VALUE_BY_NAME_EVENT_HANDLE_ITEM2);

				break;
				
			case punchRightFistStraight:
				
				
				// directionHit
				lines.add(0, 0, "GET(GETVal(" + PropertyName.stateSeer_directionView.toString() + "))");
				lines.add(0, 0, Value.VALUE_BY_NAME_EVENT_PUNCH_DIRECTION);

				// actorsIntensity
				lines.add(0, 1, "GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_INTENSITY + "))");
				lines.add(0, 1, Value.VALUE_BY_NAME_ACTION_INTENSITY);

				// intensity
				lines.add(0, 2, "MUL(" +
									"GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_INTENSITY + "))" + "," +
									"ATTR(" + Attribute.power.getIndex() + ")" +
								")");
				lines.add(0, 2, Value.VALUE_BY_NAME_EVENT_PUNCH_INTENSITY);

				// directionChest
				lines.add(0, 3, "GET(GETVal(" + PropertyName.simobj_directionChest.toString() + "))");
				lines.add(0, 3, PropertyName.simobj_directionChest.toString());

				// directionView
				lines.add(0, 4, "GET(GETVal(" + PropertyName.stateSeer_directionView.toString() + "))");
				lines.add(0, 4, PropertyName.stateSeer_directionView.toString());
				
				break;
				
			case weaponClub:
				
				// directionHit
				lines.add(0, 0, "GET(GETVal(" + PropertyName.stateSeer_directionView.toString() + "))");
				lines.add(0, 0, Value.VALUE_BY_NAME_EVENT_USEWEAPON_DIRECTION);

				// actorsIntensity
				lines.add(0, 1, "GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_INTENSITY  + "))");
				lines.add(0, 1, Value.VALUE_BY_NAME_ACTION_INTENSITY);

				// intensity
				lines.add(0, 2, "MUL(" +
									"GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_INTENSITY + "))" + "," +
									"ATTR(" + Attribute.power.getIndex() + ")" +
								")");
				lines.add(0, 2, Value.VALUE_BY_NAME_EVENT_USEWEAPON_INTENSITY);

				// weapon
				lines.add(0, 3, "GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_WEAPON  + "))");
				lines.add(0, 3, Value.VALUE_BY_NAME_EVENT_WEAPON);

				// directionChest
				lines.add(0, 4, "GET(GETVal(" + PropertyName.simobj_directionChest.toString()  + "))");
				lines.add(0, 4, PropertyName.simobj_directionChest.toString());

				// directionView
				lines.add(0, 5, "GET(GETVal(" + PropertyName.stateSeer_directionView.toString()  + "))");
				lines.add(0, 5, PropertyName.stateSeer_directionView.toString());
				
				break;
				
			case listenToStatement:
			case listenToQuestion:
			case listenToInstruction:
			case understand:
				
				// Sentence
				lines.add(0, 0, "GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_SENTENCE + "))");
				lines.add(0, 0, Value.VALUE_BY_NAME_EVENT_SENTENCE);
				
				// Target
				lines.add(0, 1, "GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_TARGET + "))");
				lines.add(0, 1, Value.VALUE_BY_NAME_EVENT_TARGET);

				// directionView
				lines.add(0, 2, "GET(GETVal(" + PropertyName.stateSeer_directionView.toString() + "))");
				lines.add(0, 2, PropertyName.stateSeer_directionView.toString());

				break;
				
			case answerNormal:
			case answerScream:
			case answerWhisper:
				
				// directionEvent
				lines.add(0, 0, "GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_DIRECTION + "))");
				lines.add(0, 0, Value.VALUE_BY_NAME_EVENT_DIRECTION);

				// Target (Partner)
				lines.add(0, 1, "GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_TARGET + "))");
				lines.add(0, 1, Value.VALUE_BY_NAME_EVENT_TARGET);
				
				// Sentence
				lines.add(0, 2, "GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_SENTENCE + "))");
				lines.add(0, 2, Value.VALUE_BY_NAME_EVENT_SENTENCE);

				// SentenceType
				lines.add(0, 3, "GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_SENTENCETYPE + "))");
				lines.add(0, 3, Value.VALUE_BY_NAME_EVENT_SENTENCETYPE);
				
				break;
				
			case askNormal:
			case askScream:
			case askWhisper:
	
				// directionEvent
				lines.add(0, 0, "GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_DIRECTION + "))");
				lines.add(0, 0, Value.VALUE_BY_NAME_EVENT_DIRECTION);

				// Target (Partner)
				lines.add(0, 1, "GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_TARGET + "))");
				lines.add(0, 1, Value.VALUE_BY_NAME_EVENT_TARGET);
				
				// Sentence
				lines.add(0, 2, "GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_SENTENCE + "))");
				lines.add(0, 2, Value.VALUE_BY_NAME_EVENT_SENTENCE);

				// SentenceType
				lines.add(0, 3, "GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_SENTENCETYPE + "))");
				lines.add(0, 3, Value.VALUE_BY_NAME_EVENT_SENTENCETYPE);
				
				break;
			
			case sayNormal:
				
				// directionEvent
				lines.add(0, 0, "GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_DIRECTION + "))");
				lines.add(0, 0, Value.VALUE_BY_NAME_EVENT_DIRECTION);

				// Target (Partner)
				lines.add(0, 1, "GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_TARGET + "))");
				lines.add(0, 1, Value.VALUE_BY_NAME_EVENT_TARGET);
				
				// Sentence
				lines.add(0, 2, "GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_SENTENCE + "))");
				lines.add(0, 2, Value.VALUE_BY_NAME_EVENT_SENTENCE);

				// SentenceType
				lines.add(0, 3, "GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_SENTENCETYPE + "))");
				lines.add(0, 3, Value.VALUE_BY_NAME_EVENT_SENTENCETYPE);
				
				break;
				
			case sayScream:
				
				// loudness
				lines.add(0, 0, "GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_INTENSITY + "))");
				lines.add(0, 0, Value.VALUE_BY_NAME_EVENT_SAY_LOUDNESS);

				// directionEvent
				lines.add(0, 1, "GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_DIRECTION + "))");
				lines.add(0, 1, Value.VALUE_BY_NAME_EVENT_DIRECTION);

				// Target (Partner)
				lines.add(0, 2, "GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_TARGET + "))");
				lines.add(0, 2, Value.VALUE_BY_NAME_EVENT_TARGET);
				
				// Sentence
				lines.add(0, 3, "GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_SENTENCE + "))");
				lines.add(0, 3, Value.VALUE_BY_NAME_EVENT_SENTENCE);

				// SentenceType
				lines.add(0, 4, "GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_SENTENCETYPE + "))");
				lines.add(0, 4, Value.VALUE_BY_NAME_EVENT_SENTENCETYPE);
				
				break;
				
			case sayWhisper:
	
				// directionEvent
				lines.add(0, 0, "GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_DIRECTION + "))");
				lines.add(0, 0, Value.VALUE_BY_NAME_EVENT_DIRECTION);

				// Target (Partner)
				lines.add(0, 1, "GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_TARGET + "))");
				lines.add(0, 1, Value.VALUE_BY_NAME_EVENT_TARGET);
				
				// Sentence
				lines.add(0, 2, "GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_SENTENCE + "))");
				lines.add(0, 2, Value.VALUE_BY_NAME_EVENT_SENTENCE);

				// SentenceType
				lines.add(0, 3, "GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_SENTENCETYPE + "))");
				lines.add(0, 3, Value.VALUE_BY_NAME_EVENT_SENTENCETYPE);
				
				break;
				
			case takeItem:

				// equip item
				lines.add(0, 0, "GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_EQUIP_ITEM + "))");
				lines.add(0, 0, Value.VALUE_BY_NAME_EVENT_EQUIP_ITEM);

				break;
				
			case setItemToInventory:
				
				// itemIsDrinkable
				lines.add(0, 0, "GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_EQUIP_ITEM + ").IsA(IDrinkable))");
				lines.add(0, 0, Value.VALUE_BY_NAME_EVENT_EQUIP_ITEMISDRINKABLE);

				// itemIsEatable
				lines.add(0, 1, "GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_EQUIP_ITEM + ").IsA(IEatable))");
				lines.add(0, 1, Value.VALUE_BY_NAME_EVENT_EQUIP_ITEMISEATABLE);

				break;
				
			case sleep:

				// intensity
				lines.add(0, 0, "GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_INTENSITY + "))");
				lines.add(0, 0, Value.VALUE_BY_NAME_EVENT_INTENSITY);
				
				break;
				
			case drink:
				

				// itemDrink
				lines.add(0, 0, "GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_BF_ITEMDRINK + "))");
				lines.add(0, 0, Value.VALUE_BY_NAME_EVENT_BF_ITEMDRINK);
				
				break;
				
			case eat:
				

				// itemEat
				lines.add(0, 0, "GET(GETVal(" + Value.VALUE_BY_NAME_ACTION_BF_ITEMEAT + "))");
				lines.add(0, 0, Value.VALUE_BY_NAME_EVENT_BF_ITEMEAT);
				
				break;
				
			case piss:
			case shit: 	

				break;
				
			default:
				
				break;
				
		}
		
		return;
		
	}


	
}
