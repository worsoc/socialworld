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

public class CreateKnowledgeSourceExpression extends CreateValue {

	public CreateKnowledgeSourceExpression(Expression sourceType, Expression source ) {
		
		super(Type.knowledgeSource);

		List<Expression> listExpressions = new ArrayList<Expression>();
		List<String> names = new ArrayList<String>();
		
		listExpressions.add(sourceType);
		names.add(Value.VALUE_NAME_KNOWLEDGE_SOURCE_TYPE);

		listExpressions.add(source);
		names.add(Value.VALUE_NAME_KNOWLEDGE_SOURCE);

		setExpression2(new AddOrSetValuesToArguments(listExpressions, names));

		setValid();

	}

}
