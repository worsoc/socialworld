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
package org.socialworld.datasource.tablesSimulation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.socialworld.Constants;
import org.socialworld.attributes.properties.Colour;
import org.socialworld.attributes.properties.Material;
import org.socialworld.attributes.properties.Nutrient;
import org.socialworld.attributes.properties.Taste;
import org.socialworld.conversation.Lexem;
import org.socialworld.conversation.Relation;
import org.socialworld.datasource.mariaDB.Table;
import org.socialworld.objects.GroupingOfSimulationObjects;
import org.socialworld.objects.statics.GetLexemIDHigherPartFromMapping;
import org.socialworld.objects.statics.GetLexemIDLowerPartFromMapping;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;

/**
 * @author Mathias Sikos
 *
 */
public class TableWord extends Table {

	public final  String 	ALL_COLUMNS 		=	" word_id, word, lexem_id, numerus, pronoun_word_id ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;

	int word_id[];
	String word[];
	int lexem_id[];
	int numerus[];
	int pronoun_word_id[];
	
	@Override
	protected String getTableName() {
		return "sw_word";
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
		setPK1(word_id);
	}

	public void insert(int word_id, String word,  int lexem_id,  int numerus, int pronoun_word_id) {
		String statement;
			
		if (word_id > 0) {
	

			statement 	= "INSERT INTO sw_word (word_id, word, lexem_id, numerus, pronoun_word_id) VALUES (" + 
					word_id + ", '" + word + "', " + lexem_id + ", " + numerus + ", " + pronoun_word_id  +")";
			
			insert(statement);
		}
	}

	public void insert(int word_id, String word,  int lexem_id) {
		String statement;
			
		if (word_id > 0) {
	

			statement 	= "INSERT INTO sw_word (word_id, word, lexem_id) VALUES (" + 
					word_id + ", '" + word + "', " + lexem_id + ")";
			
			insert(statement);
		}
	}

	public void delete(int word_id) {
		String statement;
			
		if (word_id > 0) {
	
			statement 	= "DELETE FROM sw_word WHERE word_id = " + word_id  ;
			
			delete(statement);
		}
	}

	private void selectAllColumns(ResultSet rs) {
		int row = 0;
		word_id = new int[rowCount];
		word = new String[rowCount];
		lexem_id = new int[rowCount];
		numerus = new int[rowCount];
		pronoun_word_id = new int[rowCount];

		try {
			while (rs.next()) {
				
				word_id[row] = rs.getInt(1);
				word[row] = rs.getString(2);
				lexem_id[row] = rs.getInt(3);
				numerus[row] = rs.getInt(4);
				pronoun_word_id[row] = rs.getInt(5);
				
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}

	}
	
	
	
	public int getWordID(int index) {
		return word_id[index];
	}

	public String getWord(int index) {
		return word[index];
	}
	
	public int getLexemID(int index) {
		return lexem_id[index];
	}

	
	public int getNumerus(int index) {
		return numerus[index];
	}

	public int getPronounWordID(int index) {
		return pronoun_word_id[index];
	}

	public void fillTableForRelations() {

		String statement;

		int lexemID;
		int wordID;
		String word;

		statement 	= "DELETE FROM sw_word WHERE " +
				" lexem_id >= " + 	Relation.nothing.getLexemID() +  
				" and lexem_id < " + (Relation.nothing.getLexemID() + Lexem.LEXEMID_RANGE_RELATION);
		delete(statement);
		for(Relation relation : Relation.values()) {
			lexemID = relation.getLexemID();
			wordID = lexemID;
			word = relation.toString();
			insert(wordID, word, lexemID );
		}
	}

	public void fillTableForEnumProperties() {
		
		String statement;
		
		int lexemID;
		int wordID;
		String word;
		
		
		// Colour
		statement 	= "DELETE FROM sw_word WHERE " +
				" lexem_id >= " + 	Colour.nothing.getLexemID() +  
				" and lexem_id < " + (Colour.nothing.getLexemID() + Lexem.LEXEMID_RANGE_PROPERTIES_1);
		delete(statement);
		for(Colour prop : Colour.values()) {
			lexemID = prop.getLexemID();
			wordID = lexemID;
			word = prop.toString();
			insert(wordID, word, lexemID );
		}
		
		// Material
		statement 	= "DELETE FROM sw_word WHERE " +
				" lexem_id >= " + 	Material.nothing.getLexemID() +  
				" and lexem_id < " + (Material.nothing.getLexemID() + Lexem.LEXEMID_RANGE_PROPERTIES_1);
		delete(statement);
		for(Material prop : Material.values()) {
			lexemID = prop.getLexemID();
			wordID = lexemID;
			word = prop.toString();
			insert(wordID, word, lexemID );
		}

		// Nutrient
		statement 	= "DELETE FROM sw_word WHERE " +
				" lexem_id >= " + 	Nutrient.nothing.getLexemID() +  
				" and lexem_id < " + (Nutrient.nothing.getLexemID() + Lexem.LEXEMID_RANGE_PROPERTIES_1);
		delete(statement);
		for(Nutrient prop : Nutrient.values()) {
			lexemID = prop.getLexemID();
			wordID = lexemID;
			word = prop.toString();
			insert(wordID, word, lexemID );
		}

		// Taste
		statement 	= "DELETE FROM sw_word WHERE " +
				" lexem_id >= " + 	Taste.nothing.getLexemID() +  
				" and lexem_id < " + (Taste.nothing.getLexemID() + Lexem.LEXEMID_RANGE_PROPERTIES_1);
		delete(statement);
		for(Taste prop : Taste.values()) {
			lexemID = prop.getLexemID();
			wordID = lexemID;
			word = prop.toString();
			insert(wordID, word, lexemID );
		}

	}
	
	public void fillTableForSimObjects() {
		
		String statement;
		
		statement 	= "DELETE FROM sw_word WHERE " +
				" lexem_id >= " + Lexem.OFFSET_LEXEMID_NOUN_SIMOBJ +  
				" and lexem_id < " + (2 *  Lexem.OFFSET_LEXEMID_NOUN_SIMOBJ );
		delete(statement);

 		System.out.println("-------------------------------");
		
		
		try (ScanResult result = new ClassGraph().enableClassInfo().enableAnnotationInfo().scan()) {
//			  .whitelistPackages(getClass().getPackage().getName()).scan()) {
				    
					String className;
					
				    ClassInfoList classInfos = result.getSubclasses("org.socialworld.objects.SimulationObject");
				     	classInfos = classInfos.getStandardClasses();
				     	List<Class<?>> list = classInfos.loadClasses();
				     	for (Class<?> simObjClass : list) {
				     		
				     		className = simObjClass.getName();
				     		System.out.print(className);
				     		
				     		int lexemIdLowerValue = GetLexemIDLowerPartFromMapping.getForClassName(className);
				     		if (lexemIdLowerValue == Constants.MAPPING_NO_ENTRY_FOR_KEY) {	
				     			System.out.println(" --> ignore");
				     			continue;
				     		}
				     				
				      		int lexemIdHigherValue = GetLexemIDHigherPartFromMapping.getForClassName(className);
				     		if (lexemIdHigherValue == Constants.MAPPING_NO_ENTRY_FOR_KEY) {
					     		System.out.println(" --> ignore");
				     			continue;
				     		}

				     		
 		
						    int lexemID = Lexem.OFFSET_LEXEMID_NOUN_SIMOBJ + lexemIdHigherValue * GroupingOfSimulationObjects.RANGE_FOR_LOWER_VALUE + lexemIdLowerValue;
				     		System.out.println(" : " + lexemID);

				     		// ignore if both id parts are 0
				      		if (lexemIdHigherValue  == GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_IGNORE &&
				      				lexemIdLowerValue == GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_IGNORE) continue;

				      		// TODO word for the simulation object class
				      		if (className.length() > 45) {
								insert(lexemID, className.substring(className.length() - 40) ,  lexemID); 			
				      		}
				      		else {
								insert(lexemID, className ,  lexemID); 					      			
				      		}
				     	}
				}		
		
		
	}
	
	
	
}