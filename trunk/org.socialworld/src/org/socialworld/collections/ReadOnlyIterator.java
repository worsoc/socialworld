package org.socialworld.collections;

import java.util.Iterator;

public class ReadOnlyIterator<T> {

	private Iterator<T> iterator;
	
	public ReadOnlyIterator(Iterator<T> iterator) {
		this.iterator = iterator;
	}
	
	public boolean hasNext() {
		return iterator.hasNext();
	}
	
	public T next() {
		return iterator.next();
	}

	// the iterator method remove() ist not supported
}
