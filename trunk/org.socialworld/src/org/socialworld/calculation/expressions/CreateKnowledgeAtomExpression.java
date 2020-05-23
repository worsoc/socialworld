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
package org.socialworld.calculation.expressions;


import java.util.ArrayList;
import java.util.List;

import org.socialworld.calculation.Expression;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.knowledge.KnowledgeAtomType;
import org.socialworld.knowledge.KnowledgeFact_Criterion;

public class CreateKnowledgeAtomExpression extends CreateValue {

	public CreateKnowledgeAtomExpression(
			Expression subject, Expression verb, Expression adverb) {
		
		super(Type.knowledgeAtom);
		
		initRelationUnaer(subject, verb, adverb);

		setValid();
		
	}

	public CreateKnowledgeAtomExpression(
			Expression subject, Expression verb, Expression adverb, Expression object1) {
		
		super(Type.knowledgeAtom);
		
		initRelationBinaer(subject, verb, adverb, object1);

		setValid();
		
	}

	public CreateKnowledgeAtomExpression(
			Expression subject, Expression verb, Expression adverb, Expression object1, Expression object2) {
		
		super(Type.knowledgeAtom);
		
		initRelationTrinaer(subject, verb, adverb, object1, object2);
		
		setValid();
		
	}
	
	public CreateKnowledgeAtomExpression(Expression expression) {
		
		super(Type.knowledgeAtom);
		
		initValue(expression);
			
		setValid();
					
	}

	public CreateKnowledgeAtomExpression(KnowledgeFact_Criterion criterion, List<Expression> listExpressions) {
		
		super(Type.knowledgeAtom);

		if (listExpressions.size() > 0) {
		
			initProperty(criterion, listExpressions);
			
			setValid();
			
		}
		
	}
	
	private void initRelationUnaer(
			Expression subject, Expression verb, Expression adverb) {

		List<Expression> listExpressions = new ArrayList<Expression>();
		List<String> names = new ArrayList<String>();
		
		listExpressions.add(subject);
		names.add("subject");

		listExpressions.add(verb);
		names.add("verb");

		listExpressions.add(adverb);
		names.add("adverb");
		
		// setting the knowledge atom sub type 
		setExpression1(new Constant(new Value(Type.integer, KnowledgeAtomType.relationUnaer.getIndex())));

		setExpression2(new AddOrSetValuesToArguments(listExpressions, names));
		
	}

	private void initRelationBinaer(
			Expression subject, Expression verb, Expression adverb, Expression object1) {

		List<Expression> listExpressions = new ArrayList<Expression>();
		List<String> names = new ArrayList<String>();
		
		listExpressions.add(subject);
		names.add("subject");

		listExpressions.add(verb);
		names.add("verb");

		listExpressions.add(adverb);
		names.add("adverb");

		listExpressions.add(object1);
		names.add("object1");

		// setting the knowledge atom sub type 
		setExpression1(new Constant(new Value(Type.integer, KnowledgeAtomType.relationBinaer.getIndex())));

		setExpression2(new AddOrSetValuesToArguments(listExpressions, names));
		
	}
	
	private void initRelationTrinaer(
			Expression subject, Expression verb, Expression adverb, Expression object1, Expression object2) {

		List<Expression> listExpressions = new ArrayList<Expression>();
		List<String> names = new ArrayList<String>();
		
		listExpressions.add(subject);
		names.add("subject");

		listExpressions.add(verb);
		names.add("verb");

		listExpressions.add(adverb);
		names.add("adverb");

		listExpressions.add(object1);
		names.add("object1");

		listExpressions.add(object2);
		names.add("object2");
		

		// setting the knowledge atom sub type 
		setExpression1(new Constant(new Value(Type.integer, KnowledgeAtomType.relationTrinaer.getIndex())));

		setExpression2(new AddOrSetValuesToArguments(listExpressions, names));
		
	}
	

	private void initProperty(KnowledgeFact_Criterion criterion, List<Expression> listExpressions) {
		
		List<Expression> listExpressionsWithCriterion = new ArrayList<Expression>();
		List<String> names = new ArrayList<String>();

		names.add("criterion");
		listExpressionsWithCriterion.add(new Constant(new Value(Type.integer, criterion.getIndex())));

		for (int index = 0; index < listExpressions.size(); index++) {
			names.add("value_" + index);
			listExpressionsWithCriterion.add(listExpressions.get(index));
		}

		// sub type property
		setExpression1(new Constant(new Value(Type.integer, KnowledgeAtomType.property.getIndex())));

		setExpression2(new AddOrSetValuesToArguments(listExpressionsWithCriterion, names));
		
	}

	private void initValue(Expression expression) {
		
		List<Expression> listExpressions = new ArrayList<Expression>();

		listExpressions.add(expression);

		// setting the knowledge atom sub type 
		setExpression1(new Constant(new Value(Type.integer, KnowledgeAtomType.value.getIndex())));
		
		setExpression2(new AddOrSetValuesToArguments(listExpressions));
		
	}

}
