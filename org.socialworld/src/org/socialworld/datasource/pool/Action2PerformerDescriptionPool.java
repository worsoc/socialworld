package org.socialworld.datasource.pool;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.actions.ActionMode;
import org.socialworld.attributes.Attribute;
import org.socialworld.calculation.Expression;
import org.socialworld.calculation.FunctionByExpression;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.descriptions.Action2PerformerDescription;
import org.socialworld.calculation.expressions.Calculate;
import org.socialworld.calculation.expressions.Nothing;

public class Action2PerformerDescriptionPool extends DescriptionPool {

	
	

	private static Action2PerformerDescriptionPool instance;
	
	private Action2PerformerDescription descriptions[];
	
	private Action2PerformerDescriptionPool () {
		
		sizeDescriptionsArray = ActionMode.maxIndex() + 1;
		descriptions = new Action2PerformerDescription[sizeDescriptionsArray];
		
		initialize();
		
	}
	
	public static Action2PerformerDescriptionPool getInstance() {
		if (instance == null) {
			instance = new Action2PerformerDescriptionPool();
		}
		return instance;
	}

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
	
	private List<FunctionByExpression> initializeWithTestData(ActionMode actionMode) {
		
		Expression startExpression = Nothing.getInstance();
		List<FunctionByExpression> result;
		result = new ArrayList<FunctionByExpression>();
		
		switch (actionMode)
		{
			case punchRightFistStraight:
				
				
				// directionHit
				startExpression = new Calculate("GET(" + Value.ARGUMENT_VALUE_BY_NAME_DIRECTION_VIEW + ")", Value.ARGUMENT_VALUE_BY_NAME_DIRECTION_EVENT );
				result.add( new FunctionByExpression(startExpression) );

				// actorsIntensity
				startExpression = new Calculate("GET(" + Value.ARGUMENT_VALUE_BY_NAME_INTENSITY_ACTION + ")", Value.ARGUMENT_VALUE_BY_NAME_INTENSITY_ACTION);
				result.add( new FunctionByExpression(startExpression) );

				// intensity
				startExpression = new Calculate("MUL(" +
													"GET(" + Value.ARGUMENT_VALUE_BY_NAME_INTENSITY_ACTION + ")" + "," +
													"ATTR(" + Attribute.power.getIndex() + ")" +
												")" , Value.ARGUMENT_VALUE_BY_NAME_INTENSITY_EVENT);
				result.add( new FunctionByExpression(startExpression) );

				// directionChest
				startExpression = new Calculate("GET(" + Value.ARGUMENT_VALUE_BY_NAME_DIRECTION_CHEST + ")", Value.ARGUMENT_VALUE_BY_NAME_DIRECTION_CHEST);
				result.add( new FunctionByExpression(startExpression) );

				// directionView
				startExpression = new Calculate("GET(" + Value.ARGUMENT_VALUE_BY_NAME_DIRECTION_VIEW + ")", Value.ARGUMENT_VALUE_BY_NAME_DIRECTION_VIEW);
				result.add( new FunctionByExpression(startExpression) );
				
				break;
				
			case weaponClub:
				
				// directionHit
				startExpression = new Calculate("GET(" + Value.ARGUMENT_VALUE_BY_NAME_DIRECTION_VIEW + ")", Value.ARGUMENT_VALUE_BY_NAME_DIRECTION_EVENT );
				result.add( new FunctionByExpression(startExpression) );

				// actorsIntensity
				startExpression = new Calculate("GET(" + Value.ARGUMENT_VALUE_BY_NAME_INTENSITY_ACTION + ")", Value.ARGUMENT_VALUE_BY_NAME_INTENSITY_ACTION);
				result.add( new FunctionByExpression(startExpression) );

				// intensity
				startExpression = new Calculate("MUL(" +
													"GET(" + Value.ARGUMENT_VALUE_BY_NAME_INTENSITY_ACTION + ")" + "," +
													"ATTR(" + Attribute.power.getIndex() + ")" +
												")" , Value.ARGUMENT_VALUE_BY_NAME_INTENSITY_EVENT);
				result.add( new FunctionByExpression(startExpression) );

				// weapon
				startExpression = new Calculate("GET(" + Value.ARGUMENT_VALUE_BY_NAME_WEAPON_ACTION + ")", Value.ARGUMENT_VALUE_BY_NAME_WEAPON_ACTION);
				result.add( new FunctionByExpression(startExpression) );

				// directionChest
				startExpression = new Calculate("GET(" + Value.ARGUMENT_VALUE_BY_NAME_DIRECTION_CHEST + ")", Value.ARGUMENT_VALUE_BY_NAME_DIRECTION_CHEST);
				result.add( new FunctionByExpression(startExpression) );

				// directionView
				startExpression = new Calculate("GET(" + Value.ARGUMENT_VALUE_BY_NAME_DIRECTION_VIEW + ")", Value.ARGUMENT_VALUE_BY_NAME_DIRECTION_VIEW);
				result.add( new FunctionByExpression(startExpression) );
				
				break;
				
				
			default:
				break;
				
		}
		
		return result;
		
	}
	

	
}
