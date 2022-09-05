/*
* Social World
* Copyright (C) 2016  Mathias Sikos
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
package org.socialworld.knowledge;

import org.socialworld.conversation.Lexem;
import org.socialworld.conversation.Numerus;
import org.socialworld.conversation.Word;

public class AnswerRelationBinaer extends KnowledgeRelationBinaer implements IAnswer{

	private KnowledgeRelationBinaer originalRelation;

	private Lexem subjectOriginal;
	private Lexem subjectChanged;

	public AnswerRelationBinaer(KnowledgeRelationBinaer original) {
		super(original);
		this.originalRelation = original;
	}

	public KnowledgeFact_Type getType() { return KnowledgeFact_Type.relationBinaer; }

	
	public void changeSubject(Lexem subject) {
		this.subjectChanged = subject;
	}
	
	public Word getSubjectAsWord() { return this.subjectChanged.getWord(); }

	public void setSpeechRecognitionsSubjectWord(Word subject) {
		setSubject(subject.getLexem(), subject.getNumerus());
	}


	private void setSubject(Lexem subject, Numerus numerus) {
		this.subjectOriginal = subject;
	}

}
