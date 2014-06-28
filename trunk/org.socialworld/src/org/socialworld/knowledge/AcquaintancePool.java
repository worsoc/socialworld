package org.socialworld.knowledge;
import org.socialworld.objects.Human;

public class AcquaintancePool {
	final int MAXIMUM_ACQUAINTANCE_POOL_CAPACITY = 100;

	private Acquaintance acquaintanceList[];

	private int accessCount[];
	
	private int maxAccessCount;
	private int maxAccessCountIndex;
	
	public AcquaintancePool() {
		acquaintanceList = new Acquaintance[MAXIMUM_ACQUAINTANCE_POOL_CAPACITY];
		accessCount = new int[MAXIMUM_ACQUAINTANCE_POOL_CAPACITY];
		
		maxAccessCount = 0;
		maxAccessCountIndex = 0;
		
	}
	
	public int addAcquaintance(Acquaintance acquaintance) {
		int index;
		
		index = indexForNewEntry();
		acquaintanceList[index] = acquaintance;
		accessCount[index] = 0;
		
		return index;
		
	}

	public Acquaintance getAcquaintance(Human human) {
		int index;
		
		for (index = 0; index < MAXIMUM_ACQUAINTANCE_POOL_CAPACITY; index++ ) {
			if (acquaintanceList[index].isAcquaintanceOfHuman(human)) {
				accessCount[index]++;
				if (accessCount[index] > maxAccessCount) {
					maxAccessCount = accessCount[index];
					maxAccessCountIndex = index;
				}
				return acquaintanceList[index];
			}
		}
		return null;
	}

	public Acquaintance getAcquaintance(int index) {
		if ( index >= 0 & index < MAXIMUM_ACQUAINTANCE_POOL_CAPACITY) {
			accessCount[index]++;
			if (accessCount[index] > maxAccessCount) {
				maxAccessCount = accessCount[index];
				maxAccessCountIndex = index;
			}
			return acquaintanceList[index];
		}
		else
			return null;
	}

	private int indexForNewEntry() {
		Acquaintance acquaintance;
		int fewestAccess;
		int indexWithFewestAccess;
		
		// find empty or invalid entry
		for (int i = 0; i < MAXIMUM_ACQUAINTANCE_POOL_CAPACITY;i++) {
			acquaintance = acquaintanceList[i];
			if ((acquaintance == null) | (!acquaintance.isValid()) ) return i;
		}
		
		
		// find entry with fewest access
		fewestAccess = maxAccessCount + 1;
		indexWithFewestAccess = maxAccessCountIndex;
		for (int i = 0; i < MAXIMUM_ACQUAINTANCE_POOL_CAPACITY;i++) {
			if ( accessCount[i] < fewestAccess) {
				indexWithFewestAccess = i;
				fewestAccess = accessCount[i];
			}
		}
		return indexWithFewestAccess;
		
	}
	
}
