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
package org.socialworld.collections;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.socialworld.GlobalSwitches;

public class CapacityQueue<Type> {

    private final String name;
    private final ArrayBlockingQueue<Type> queue;
    private final int capacity;

    public CapacityQueue(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        // Die ArrayBlockingQueue ist ein thread-sicherer Ring-Puffer
        this.queue = new ArrayBlockingQueue<Type>(capacity);
    }

    public boolean add(Type element) {
        // offer() versucht das Element hinzuzufügen und gibt sofort false zurück, wenn voll
        boolean success = this.queue.offer(element);
        
        if (!success && GlobalSwitches.OUTPUT_CAPACITY_QUEUE_IS_FULL == true) {
            System.err.println("CapacityQueue " + this.name + " is full!!!");
        }
        return success;
    }

    public Type poll(long timeout, TimeUnit unit) throws InterruptedException {
        return this.queue.poll(timeout, unit);
    }

    public Type remove() {
        // poll() gibt das erste Element zurück oder null, wenn die Queue leer ist
        return this.queue.poll();
    }
    
    /**
     * Alternative für andere threads: Warten bis etwas da ist.
     * Das würde das manuelle sleep() im Calculator ersetzen.
     */
    public Type take() throws InterruptedException {
        return this.queue.take();
    }

    public int size() {
        return this.queue.size();
    }

    public int getCapacity() {
        return this.capacity;
    }
}
