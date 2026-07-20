/*
 * Social World
 * Copyright (C) 2026  Mathias Sikos
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


import java.util.List;

import org.socialworld.datasource.parsing.JsonExtractionStep;

/**
 * Repräsentiert eine kognitive Funktionseinheit (ehemals eine komplexe 'Line')
 * innerhalb der Wahrnehmungs-Pipeline.
 */
public class EventPerceptionDescriptionEntry {

    // Die Last-Schwelle für calculateFinalPerceptionThreshold
    public String relevanceThreshold;

    // KSrcT: Die Art der Quelle (z. B. 1)
    public int sourceCategory;

    // KSrc: Der universelle Pfad zur Quelle (z. B. ["GETVal(myself)"])
    public List<String> sourcePathSteps;

    // KSbj: Der universelle Pfad zum Subjekt (z. B. ["GETVal(event_params)", "GETVal(event_causer)"])
    public List<String> subjectPathSteps;

    // Das Array für alle aus diesem Reiz zu extrahierenden Werte
    public List<JsonExtractionStep> extractions;
}
