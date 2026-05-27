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
    private long countCallPoll = 0;
    private long countCallAdd = 0;
    private long countFinishedPoll = 0;
    private long countFinishedAdd = 0;
   
    public CapacityQueue(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        // Die ArrayBlockingQueue ist ein thread-sicherer Ring-Puffer
        this.queue = new ArrayBlockingQueue<Type>(capacity);
    }

    public boolean add(Type element) {
       	incCountCallAdd();
       // offer() versucht das Element hinzuzufügen und gibt sofort false zurück, wenn voll
        boolean success = this.queue.offer(element);
        
        if (!success && GlobalSwitches.OUTPUT_CAPACITY_QUEUE_IS_FULL == true) {
            System.err.println("CapacityQueue " + this.name + " is full!!!");
        }
        else {
           	incCountFinishAdd();
        }
        return success;
    }

    public Type poll(long timeout, TimeUnit unit) throws InterruptedException {
       	incCountCallPoll();
       	Type elem = this.queue.poll(timeout, unit);
       	incCountFinishPoll();
       return elem;
    }

    public Type poll() throws InterruptedException {
       	incCountCallPoll();
      	Type elem = this.queue.poll();
       	incCountFinishPoll();
       return elem;
    }

    public Type remove() {
       	incCountCallPoll();
      	Type elem = this.queue.poll();
       	incCountFinishPoll();
       return elem;
    }
    
    /**
     * Alternative für andere threads: Warten bis etwas da ist.
     * Das würde das manuelle sleep() im Calculator ersetzen.
     */
    public Type take() throws InterruptedException {
    	incCountCallPoll();
      	Type elem = this.queue.take();
       	incCountFinishPoll();
       return elem;
   }

    public int size() {
        return this.queue.size();
    }

    public int getCapacity() {
        return this.capacity;
    }
    
    private void incCountCallPoll() {
    	countCallPoll++;
    }
    
    private void incCountCallAdd() {
    	countCallAdd++;
    }
  
    private void incCountFinishPoll() {
    	countFinishedPoll++;
    }
    
    private void incCountFinishAdd() {
    	countFinishedAdd++;
    }

    public long getCountCallPoll() {return countCallPoll;}
    public long getCountCallAdd() {return countCallAdd;}
    
	public void printCounts() {
		System.out.println("CapacityQueue " + name +
				" - adds: " + countFinishedAdd + "/"+ countCallAdd + " - polls: " + countFinishedPoll + "/"+ countCallPoll );
	}


}
