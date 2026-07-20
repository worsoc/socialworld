/*
 * Social World
 * Copyright (C) 2019  Mathias Sikos
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://gnu.org>.
 */
package org.socialworld.calculation.descriptions;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.calculation.functions.FunctionByExpression;

import com.google.gson.Gson;

public abstract class DescriptionBase {

	private static Gson gson = null;
	protected static Gson getGsonInstance() {
		if (gson == null) {
			gson = new Gson();
		}
		return gson;
	}

    // Die universelle Relevanz-Schwelle für den Cooldown-Schutz
    private int relevanceThreshold;

 
	
	private List<FunctionByExpression> functions;
	
	public DescriptionBase() {
		
		functions = new  ArrayList<FunctionByExpression>();
		
	}
	
	public abstract void setFunctions();
	
	public List<FunctionByExpression> getFunctions() {
		return functions;
	}

	
	public void addFunction(FunctionByExpression function) {

		functions.add(function);

	}
	
	public FunctionByExpression getFunction(int index) {
		
		return functions.get(index);
		
	}
	
	public int countFunctions() {
		
		return functions.size();
		
	}
	
	public boolean isEmpty() {
		return (functions.size() == 0);
	}

	   /**
     * Gibt die dynamische Schwelle für den Cooldown-Abgleich zurück.
     * final verhindert Overhead und erlaubt der JVM perfektes Inlining.
     */
    public final int getRelevanceThreshold() {
        return this.relevanceThreshold;
    }

    // Setter für den Konfigurations-Loader (falls benötigt)
    protected final void setRelevanceThreshold(int relevanceThreshold) {
        this.relevanceThreshold = relevanceThreshold;
    }

}
