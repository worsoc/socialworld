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
package org.socialworld.core;

public class TickObjectCooldown {

    // Konfigurierbare Obergrenze: Wie viele Elemente dürfen pro Phase/Tick maximal durch?
    public static final int MAX_PERCEPTION_ELEMS = 5; // Hohe sensorische Varianz
    public static final int MAX_PERCEPTION_AMBIENT_ELEMS = 1; 
    public static final int MAX_REFRESH_ELEMS    = 1; // Strikt gedeckelt,
    public static final int MAX_REACTION_ELEMS    = 3; 
    public static final int MAX_ACTION_ELEMS    = 1; 

    // 1. Definition der verschiedenen Cooldown-Typen als ID
    public static final int TYPE_PERCEPTION  = 0;
    public static final int TYPE_PERCEPTION_AMBIENT  = 1;
    public static final int TYPE_REFRESH  = 2;
    public static final int TYPE_REFRESH_ACTION  = 3;
    public static final int TYPE_ACTION      = 4;
    public static final int TYPE_REACTION      = 5;
 //   public static final int TYPE_MOVEMENT    = 6;
    
    public static final int TYPE_COUNT       = 6; // Anzahl der registrierten Typen

    // Allokationsfreies O(1)-Mapping für verknüpfte Typen.
    // Index = Auslösender Typ, Wert = Synchronisierter Ziel-Typ (-1 = Keiner)
    private static final int[] COOLDOWN_DEPENDENCIES = new int[TYPE_COUNT];
    
    static {
        // Initialisiere alle Verknüpfungen standardmäßig mit -1 (inaktiv)
        for (int i = 0; i < TYPE_COUNT; i++) {
            COOLDOWN_DEPENDENCIES[i] = -1;
        }
        // WICHTIG: Wenn REFRESH_ACTION läuft, simuliere/synchronisiere ACTION im Hintergrund!
        COOLDOWN_DEPENDENCIES[TYPE_REFRESH_ACTION] = TYPE_ACTION;
    }

    // Zeile = objectId, Spalte = Cooldown-Typ -> Absolut allokationsfrei im Betrieb!
    private final long[][] cooldownMatrix;
    private final int[][] cooldownCounter; // Trackt die verbrauchten Durchläufe im aktuellen Phase/Tick
    private long currentTick = 0;

    /**
     * @param maxObjectId maximale ID (exklusive)
     */
    public TickObjectCooldown(int maxObjectId) {
        // Reserviert den Speicher einmalig beim Systemstart
        this.cooldownMatrix = new long[maxObjectId][TYPE_COUNT];
        this.cooldownCounter = new int[maxObjectId][TYPE_COUNT];
  }

    public void nextTick() {
        currentTick++;
    }

    public void setTick(long tick) {
        this.currentTick = tick;
    }

    /**
     * Universeller allokationsfreier Guard für jede Art von Objekt-Sperre.
     * 
     * @param objectId Die ID des Simulations-Objekts
     * @param cooldownType Der Typ (z. B. TickObjectCooldown.TYPE_PERCEPTION)
     * @param cooldownInSeconds Die Dauer der Sperre in Sekunden (1 Tick = 1 Sekunde)
     * @return true, wenn das Objekt bereit ist; false, wenn es noch gesperrt ist.
     */
    public boolean checkAndApplyCooldown(int objectId, int cooldownType, int cooldownInSeconds) {
     int maxAllowed = getMaxAllowedForType(cooldownType);
        
        // Nutzt die Kern-Logik unter Übergabe des fixen Limits
        return checkAndApplyCooldown(objectId, cooldownType, cooldownInSeconds, maxAllowed);
    }
    
    /**
     * Universeller allokationsfreier Guard, der die dynamische Schwelle direkt berücksichtigt.
     */
    public boolean checkAndApplyCooldown(int objectId, int cooldownType, int cooldownInSeconds, int currentThreshold) {
        // Index-Schutz für Objekte und Typen
        if (objectId < 0 || objectId >= cooldownMatrix.length || cooldownType < 0 || cooldownType >= TYPE_COUNT) {
            return true; 
        }

        // FALL A: Wir sind in einem NEUEN Tick / einer neuen Phase
        if (currentTick >= cooldownMatrix[objectId][cooldownType]) {
            cooldownMatrix[objectId][cooldownType] = currentTick + cooldownInSeconds;
            cooldownCounter[objectId][cooldownType] = 0; 
        }

        // FALL B: Wir prüfen gegen die übergebene, hochgradig differenzierte Schwelle
        if (cooldownCounter[objectId][cooldownType] < currentThreshold) {
            cooldownCounter[objectId][cooldownType]++; // Gültigen Durchlauf registrieren
 
            
            // AUTOMATISCHER BYPASS / SIMULATION VERKNÜPFTER TYPEN
            int linkedType = COOLDOWN_DEPENDENCIES[cooldownType];
            if (linkedType != -1) {
                // Wenn beim verknüpften Typen die Zeit im Hintergrund ebenfalls abgelaufen ist:
                if (currentTick >= cooldownMatrix[objectId][linkedType]) {
                    // Simuliere den Reset: Zeitstempel hochziehen und Counter nullen.
                    // Wir buchen hier noch nicht (+1), damit die Endstation normal arbeiten kann.
                    cooldownMatrix[objectId][linkedType] = currentTick + 1; 
                    cooldownCounter[objectId][linkedType] = 0; 
                }
            }

            return true; // Freigabe
        }

        return false; // Blockiert, da die aktuelle Last die Schwelle erreicht/überschritten hat
    }
 
 
    /**
     * Ermittelt die maximale Obergrenze für den jeweiligen Typ (0 Allokation)
     */
    private int getMaxAllowedForType(int cooldownType) {
        switch (cooldownType) {
            case TYPE_PERCEPTION:
                return MAX_PERCEPTION_ELEMS; 
            case TYPE_PERCEPTION_AMBIENT:
                return MAX_PERCEPTION_AMBIENT_ELEMS; 
            case TYPE_REFRESH:
                return MAX_REFRESH_ELEMS;  
            case TYPE_REFRESH_ACTION:
                return MAX_ACTION_ELEMS;  
            case TYPE_REACTION:
                return MAX_REACTION_ELEMS;  
            case TYPE_ACTION:
                return MAX_ACTION_ELEMS;  
            default:
                return 1; // Standard-Sicherheitsnetz
        }
    }

}
