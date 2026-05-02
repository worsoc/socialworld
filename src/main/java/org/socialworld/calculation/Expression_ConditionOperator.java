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
package org.socialworld.calculation;

/**
 *  The enumeration holds all operators that
 *         can be used in conditions.

 * @author Mathias Sikos (MatWorsoc) 
 */
public enum Expression_ConditionOperator {
	
	notEqual(0), equal(1), less(2), lessEqual(3), greaterEqual(4), greater(5),
	and(6), or(7);

	public static final int NUMBER_OF_COMPARISON_OPERATORS = 6;
	
	
    private static final java.util.Map<String, Expression_ConditionOperator> NAME_CACHE;
    private static final java.util.Map<Integer, Expression_ConditionOperator> INDEX_CACHE;

    static {
        java.util.Map<String, Expression_ConditionOperator> nCache = new java.util.HashMap<>();
        java.util.Map<Integer, Expression_ConditionOperator> iCache = new java.util.HashMap<>();
        
        for (Expression_ConditionOperator op : Expression_ConditionOperator.values()) {
            nCache.put(op.name().toLowerCase(), op);
            iCache.put(op.index, op);
        }
        
        // Symbole für den String-Lookup hinzufügen
        nCache.put("==", equal);
        nCache.put("!=", notEqual);
        nCache.put("<",  less);
        nCache.put("<=", lessEqual);
        nCache.put(">",  greater);
        nCache.put(">=", greaterEqual);
        nCache.put("&",  and);
        nCache.put("|",  or);

        NAME_CACHE = java.util.Collections.unmodifiableMap(nCache);
        INDEX_CACHE = java.util.Collections.unmodifiableMap(iCache);
    }
	
	private int index;

	private Expression_ConditionOperator(int index) {
		this.index = index;
	}
	
	public int getIndex() { return this.index;}
	
	/**
	 * The method returns the expression condition operator name which belongs to the parameter
	 * operator index.
	 * 
	 * @param index
	 *            operator index
	 * @return expression condition operator name
	 */
	public static Expression_ConditionOperator getName(int index) {
        return INDEX_CACHE.get(index);
	}	
	
	
	public static Expression_ConditionOperator fromName(String name) {
	      if (name == null || name.isEmpty()) return null; 
        return  NAME_CACHE.get(name.toLowerCase().trim());
	}

	public String toString() {
		
		switch (this.index) {
		case 0: return "!="; 
		case 1: return "==";
		case 2: return "<"; 
		case 3: return "=<";
		case 4: return ">=";
		case 5: return ">"; 
		case 6: return "&";
		case 7: return "|"; 
		default: return "";
		}
		
	}

}
