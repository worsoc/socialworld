package org.socialworld.collections;

public class IntHashMap {

	private IntHashMapNode startNode = new IntHashMapNode();
	
	public void set(int id, Object value) {
	
		byte[] idsPerLevel = getIDsPerLevel(id);

		IntHashMapNode node = this.startNode;
		IntHashMapNode nextNode = null;
		
		for (int i = 0; i < 8 ; i++) {
			nextNode = node.getNextNode(idsPerLevel[i]);
			if (nextNode != null) {
				node = nextNode;
			}
			else {
				// add new node
				nextNode = new IntHashMapNode();
				if (i == 7) {
					// at last level add value
					nextNode.setValue(value);
				}
				node.addChildNode(idsPerLevel[i], nextNode);
				
				node = nextNode;
			}
		}

	}
	
	public Object get(int id) {
		
 		byte[] idsPerLevel = getIDsPerLevel(id);

 		IntHashMapNode node = this.startNode;
		IntHashMapNode nextNode = null;
			
		for (int i = 0; i < 8 ; i++) {
			nextNode = node.getNextNode(idsPerLevel[i]);
			if (nextNode != null) {
				node = nextNode;
			}
			else {
				return null;
			}
		}
		
		if (nextNode == null) return null;
		else return nextNode.getValue();
	}
	
	private byte[] getIDsPerLevel(int id) {
		
 		int rest = id;
		byte[] idsPerLevel = new byte[8];
		byte idPerLevel;
		for (int i = 7; i >= 0; i--) {
			idPerLevel = (byte) (rest % 16);
			idsPerLevel[i]= idPerLevel;
			rest = rest >> 4;
		}
		
 		return idsPerLevel;
	}


}
