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

/**
 * Universeller, domänenübergreifender Sammeltopf für alle strukturellen Ziel-Slots.
 * GSON mappt die String-Werte aus der JSON automatisch auf diese Konstanten.
 */
public enum ExtractionSlot {
    
    // --- STANDARD / DEFAULT ---
    DEFAULT,

    // --- KOGNITION / KNOWLEDGE-DOMÄNE ---
    KNOWLEDGE_SUBJECT,
    KNOWLEDGE_VERB,
    KNOWLEDGE_ADVERB,
    KNOWLEDGE_OBJECT_1,
    KNOWLEDGE_OBJECT_2,

    // --- BIOLOGIE / ATTRIBUT-DOMÄNE (Zukunftssicher vorbereitet) ---
    BODY_PROP,
    MIND_PROP,

    // --- PHYSIK / AUFPRALL-DOMÄNE (Zukunftssicher vorbereitet) ---
    PHYSICS_FORCE,
    PHYSICS_VECTOR
}
