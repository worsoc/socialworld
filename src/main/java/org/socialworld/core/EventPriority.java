/*
 * Social World
 * Copyright (C) 2014  Mathias Sikos
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

package org.socialworld.core;

public enum EventPriority {
    /** Prio 1: Überlebenskritisch (z.B. Werte von 80 bis 100) */
    CRITICAL(false),
    
    /** Prio 2: Dynamische Umwelt (z.B. Werte von 30 bis 79) */
    DYNAMIC(true),
    
    /** Prio 3: Statisches Rauschen (z.B. Werte von 0 bis 29) */
    AMBIENT(true);

    private final boolean throttleable;

    EventPriority(boolean throttleable) {
        this.throttleable = throttleable;
    }

    public boolean isThrottleable() {
        return this.throttleable;
    }

    /**
     * Allokationsfreie O(1) Weiche basierend auf der bestehenden int-Priorität
     */
    public static EventPriority fromInt(int priorityValue) {
        if (priorityValue >= 80) { // Schwellenwert für CRITICAL
            return CRITICAL;
        }
        if (priorityValue >= 30) { // Schwellenwert für DYNAMIC
            return DYNAMIC;
        }
        return AMBIENT;            // Alles darunter ist AMBIENT
    }
}
