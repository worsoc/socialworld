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

    // 1. Definition der verschiedenen Cooldown-Typen als ID
    public static final int TYPE_PERCEPTION  = 0;
    public static final int TYPE_PERCEPTION_AMBIENT  = 1;
    public static final int TYPE_REFRESH  = 2;
//   public static final int TYPE_ACTION      = 3;
 //   public static final int TYPE_MOVEMENT    = 4;
    
    public static final int TYPE_COUNT       = 3; // Anzahl der registrierten Typen

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
        // Index-Schutz für Objekte und Typen
        if (objectId < 0 || objectId >= cooldownMatrix.length || cooldownType < 0 || cooldownType >= TYPE_COUNT) {
            return true; 
        }

        // FALL A: Wir sind in einem NEUEN Tick / einer neuen Phase
        if (currentTick >= cooldownMatrix[objectId][cooldownType]) {
            // Neue Phase bricht an: Ziel-Tick setzen und Zähler für dieses Objekt auf 1 zurücksetzen
            cooldownMatrix[objectId][cooldownType] = currentTick + cooldownInSeconds;
            cooldownCounter[objectId][cooldownType] = 0; 
         }

        // Dynamische Obergrenze für DIESEN spezifischen Typen holen
        int maxAllowed = getMaxAllowedForType(cooldownType);

        // FALL B: Wir sind noch INNERHALB der laufenden Cooldown-Phase
        // Prüfen, ob das erlaubte Kontingent für diese Phase noch NICHT erreicht ist
        if (cooldownCounter[objectId][cooldownType] < maxAllowed) {
            cooldownCounter[objectId][cooldownType]++; // Jetzt hochzählen (gilt für Fall A und B)
            return true; // Freigabe
        }

        return false; // Aktion blockiert, Objekt ist noch im Cooldown
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
            default:
                return 1; // Standard-Sicherheitsnetz
        }
    }

}
