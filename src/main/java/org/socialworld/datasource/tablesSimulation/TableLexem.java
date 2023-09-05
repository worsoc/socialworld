/*
* Social World
* Copyright (C) 2023  Mathias Sikos
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
package org.socialworld.datasource.tablesSimulation;

import java.sql.ResultSet;
import java.sql.SQLException;

import io.github.classgraph.ScanResult;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;

import org.socialworld.datasource.mariaDB.Table;

import org.socialworld.attributes.properties.Colour;
import org.socialworld.attributes.properties.Material;
import org.socialworld.attributes.properties.Nutrient;
import org.socialworld.attributes.properties.Taste;
import org.socialworld.conversation.Lexem;
import org.socialworld.conversation.Relation;
import org.socialworld.conversation.Word_Type;

public class TableLexem extends Table {

	public final  String 	ALL_COLUMNS 		=	" lexem_id, subjectable, type ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;
	
	int lexem_id[];
	int type[];
	int subjectable[];
	
	@Override
	protected String getTableName() {
		return "sw_lexem";
	}

	@Override
	protected String getSelectList(int selectList) {
		switch (selectList) {
		case SELECT_ALL_COLUMNS:
			return  ALL_COLUMNS;
		default:
			return ALL_COLUMNS;
		}
	}

	@Override
	public void select(String statement) {
		ResultSet rs;
		
		rs = connection.executeQuery(statement);
		
		
		switch (selectList) {
		
		case SELECT_ALL_COLUMNS:
			selectAllColumns(rs);

			break;
		default:
			selectAllColumns(rs);
		}
		setPK1(lexem_id);

	}

	public void insert(int lexem_id,  int subjectable,  int type) {
		String statement;
			
		if (lexem_id > 0) {
	

			statement 	= "INSERT INTO sw_lexem (lexem_id, subjectable, type) VALUES (" + 
					lexem_id +  ", " + subjectable + ", " + type  + ")";
			
			insert(statement);
		}
	}

	public void delete(int lexem_id) {
		String statement;
			
		if (lexem_id > 0) {
	
			statement 	= "DELETE FROM sw_lexem WHERE lexem_id = " + lexem_id  ;
			
			delete(statement);
		}
	}	
	
	private void selectAllColumns(ResultSet rs) {
		int row = 0;
		lexem_id = new int[rowCount];
		subjectable = new int[rowCount];
		type = new int[rowCount];

		try {
			while (rs.next()) {
				
				lexem_id[row] = rs.getInt(1);
				subjectable[row] = rs.getInt(2);
				type[row] = rs.getInt(3);
				
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}

	}	
	
	public int getLexemID(int index) {
		return lexem_id[index];
	}


	public int getSubjectable(int index) {
		return subjectable[index];
	}
	
	public int getType(int index) {
		return type[index];
	}	
	
	public void fillTableForRelations() {

		String statement;
		int wordTypeVerb = Word_Type.infinitive.getIndex();
		
		statement 	= "DELETE FROM sw_relation";
		delete(statement);
		statement 	= "DELETE FROM sw_word WHERE " +
				" lexem_id >= " + 	Relation.nothing.getLexemID() +  
				" and lexem_id < " + (Relation.nothing.getLexemID() + Lexem.LEXEMID_RANGE_RELATION);
		delete(statement);
		statement 	= "DELETE FROM sw_lexem WHERE " +
				" lexem_id >= " + 	Relation.nothing.getLexemID() +  
				" and lexem_id < " + (Relation.nothing.getLexemID() + Lexem.LEXEMID_RANGE_RELATION);
		delete(statement);
		for(Relation relation : Relation.values()) {
			insert(relation.getLexemID(),  0,  wordTypeVerb);
		}
	}
	
	public void fillTableForEnumProperties() {

		String statement;
		int wordTypeAdjective = Word_Type.adjective.getIndex();
		
		// Colour
		statement 	= "DELETE FROM sw_word WHERE " +
				" lexem_id >= " + 	Colour.nothing.getLexemID() +  
				" and lexem_id < " + (Colour.nothing.getLexemID() + Lexem.LEXEMID_RANGE_PROPERTIES_1);
		delete(statement);
		statement 	= "DELETE FROM sw_lexem WHERE " +
				" lexem_id >= " + 	Colour.nothing.getLexemID() +  
				" and lexem_id < " + (Colour.nothing.getLexemID() + Lexem.LEXEMID_RANGE_PROPERTIES_1);
		delete(statement);
		for(Colour prop : Colour.values()) {
			insert(prop.getLexemID(),  0,  wordTypeAdjective);
		}

		// Material
		statement 	= "DELETE FROM sw_word WHERE " +
				" lexem_id >= " + 	Material.nothing.getLexemID() +  
				" and lexem_id < " + (Material.nothing.getLexemID() + Lexem.LEXEMID_RANGE_PROPERTIES_1);
		delete(statement);
		statement 	= "DELETE FROM sw_lexem WHERE " +
				" lexem_id >= " + 	Material.nothing.getLexemID() +  
				" and lexem_id < " + (Material.nothing.getLexemID() + Lexem.LEXEMID_RANGE_PROPERTIES_1);
		delete(statement);
		for(Material prop : Material.values()) {
			insert(prop.getLexemID(),  0,  wordTypeAdjective);
		}

		// Nutrient
		statement 	= "DELETE FROM sw_word WHERE " +
				" lexem_id >= " + 	Nutrient.nothing.getLexemID() +  
				" and lexem_id < " + (Nutrient.nothing.getLexemID() + Lexem.LEXEMID_RANGE_PROPERTIES_1);
		delete(statement);
		statement 	= "DELETE FROM sw_lexem WHERE " +
				" lexem_id >= " + 	Nutrient.nothing.getLexemID() +  
				" and lexem_id < " + (Nutrient.nothing.getLexemID() + Lexem.LEXEMID_RANGE_PROPERTIES_1);
		delete(statement);
		for(Nutrient prop : Nutrient.values()) {
			insert(prop.getLexemID(),  0,  wordTypeAdjective);
		}

		// Taste
		statement 	= "DELETE FROM sw_word WHERE " +
				" lexem_id >= " + 	Taste.nothing.getLexemID() +  
				" and lexem_id < " + (Taste.nothing.getLexemID() + Lexem.LEXEMID_RANGE_PROPERTIES_1);
		delete(statement);
		statement 	= "DELETE FROM sw_lexem WHERE " +
				" lexem_id >= " + 	Taste.nothing.getLexemID() +  
				" and lexem_id < " + (Taste.nothing.getLexemID() + Lexem.LEXEMID_RANGE_PROPERTIES_1);
		delete(statement);
		for(Taste prop : Taste.values()) {
			insert(prop.getLexemID(),  0,  wordTypeAdjective);
		}

	}
	
	public void fillTableForSimObjects() {
		
		String statement;
		int wordTypeNoun = Word_Type.noun.getIndex();
		
		statement 	= "DELETE FROM sw_word WHERE " +
				" lexem_id >= " + Lexem.OFFSET_LEXEMID_NOUN_SIMOBJ +  
				" and lexem_id < " + (2 *  Lexem.OFFSET_LEXEMID_NOUN_SIMOBJ );
		delete(statement);
		statement 	= "DELETE FROM sw_lexem WHERE " +
				" lexem_id >= " + Lexem.OFFSET_LEXEMID_NOUN_SIMOBJ +  
				" and lexem_id < " + (2 *  Lexem.OFFSET_LEXEMID_NOUN_SIMOBJ );
		delete(statement);

		
		
		try (ScanResult result = new ClassGraph().enableClassInfo().enableAnnotationInfo().scan()) {
//			  .whitelistPackages(getClass().getPackage().getName()).scan()) {
				    
				    ClassInfoList classInfos = result.getSubclasses("org.socialworld.objects.SimulationObject");
				    
				}		
		
		
	}

}