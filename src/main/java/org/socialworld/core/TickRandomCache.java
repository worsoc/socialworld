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


public class TickRandomCache {

 

    private final double[] randomCache;

    private final long[] lastTickUsed;

 

    private long currentTick = 0;

 

    /**

     * @param maxObjectId maximale ID (exklusive), z. B. Anzahl aktiver Objekte

     */

    public TickRandomCache(int maxObjectId) {

        this.randomCache = new double[maxObjectId];

        this.lastTickUsed = new long[maxObjectId];

    }

 

    /**

     * Wird pro Simulations-Tick aufgerufen.

     */

    public void nextTick() {

        currentTick++;

    }

 

    /**

     * Optional: falls du Tick direkt setzen willst

     */

    public void setTick(long tick) {

        this.currentTick = tick;

    }

 

    /**

     * Liefert den Random-Wert für (objectId, aktueller Tick).

     * Wird lazy berechnet und im Cache gespeichert.

     */

    public double getRandom(int objectId) {

 

        // optional: Schutz (je nach Design)

        if (objectId < 0 || objectId >= randomCache.length) {

            return 0.0;

        }

 

        // prüfen, ob Wert schon für diesen Tick berechnet wurde

        if (lastTickUsed[objectId] != currentTick) {

 

            lastTickUsed[objectId] = currentTick;

 

            long seed = objectId * 73856093L ^ currentTick * 19349663L;

 

            randomCache[objectId] = hashToDouble(seed);

        }

 

        return randomCache[objectId];

    }

 

    /**

     * Deterministische Hash-Funktion → double in [0,1)

     */

    private static double hashToDouble(long seed) {

 

        seed ^= (seed >>> 33);

        seed *= 0xff51afd7ed558ccdL;

 

        seed ^= (seed >>> 33);

        seed *= 0xc4ceb9fe1a85ec53L;

 

        seed ^= (seed >>> 33);

 

        // 53 Bit → double [0,1)

        return (seed >>> 11) * (1.0 / (1L << 53));

    }

}