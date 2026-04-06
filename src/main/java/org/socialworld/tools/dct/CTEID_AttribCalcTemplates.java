/*
* Social World
* Copyright (C) 2026  Mathias Sikos
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
package org.socialworld.tools.dct;

import java.util.ArrayList;
import java.util.List;

public class CTEID_AttribCalcTemplates {

	private static List<String> templates = null;
	public static final int MAX_INDEX = 1;
	
	public static String getTemplate(int index) {
		
		if (templates == null) {
			initialize();
		}
		
		if (templates.size() > index) {
			return templates.get(index);
		}
		else {
			return "";
		}
	}
	
	private static  void initialize() {
		templates = new ArrayList<String>();
		
		String template;
		
		template = "if($ATTRIBUTE$>$CONST_ATTRIB_VALUE_MIN$and$ATTRIBUTE$<$CONST_ATTRIB_VALUE_MAX$,add(const($CONST_0_100$)))";
		templates.add(template);
		
		template = "if($ATTRIBUTE$>$CONST_ATTRIB_VALUE_MIN$and$ATTRIBUTE$<$CONST_ATTRIB_VALUE_MAX$,sub(const($CONST_0_100$)))";
		templates.add(template);

		template = "if($ATTRIBUTE$>$CONST_ATTRIB_VALUE_MIN$and$ATTRIBUTE$<$CONST_ATTRIB_VALUE_MAX$,percIncr(const($CONST_0_100$)))";
		templates.add(template);

		template = "if($ATTRIBUTE$>$CONST_ATTRIB_VALUE_MIN$and$ATTRIBUTE$<$CONST_ATTRIB_VALUE_MAX$,percDecr(const($CONST_0_100$)))";
		templates.add(template);

	}
}
