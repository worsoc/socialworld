package org.socialworld.collections;

public class IntHashMap {

	private IntHashMapNode startNode = new IntHashMapNode();
	
	public void set(int id, Object value) {
	
		if (id == 1002100528) {
			int myBreakpoint = 0;
		}
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
		
		if (id == 1002100528) {
			int myBreakpoint = 0;
		}
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
		
		String idHex = Integer.toHexString(id);
		idHex = "00000000" + idHex;
		idHex = idHex.substring(idHex.length() - 8);
		
		byte[] idsPerLevel = transformToByteArray(idHex);
		
		return idsPerLevel;
	}
	
	private byte[] transformToByteArray(String idHex) {
		
		char[] chars = idHex.toCharArray();
		byte [] result = new byte[chars.length];
		
		int index = 0;
		for (char c : chars) {
			switch (c) {
				case '0' : result[index] = 0; break;
				case '1' : result[index] = 1; break;
				case '2' : result[index] = 2; break;
				case '3' : result[index] = 3; break;
				case '4' : result[index] = 4; break;
				case '5' : result[index] = 5; break;
				case '6' : result[index] = 6; break;
				case '7' : result[index] = 7; break;
				case '8' : result[index] = 8; break;
				case '9' : result[index] = 9; break;
				case 'a' : result[index] = 10; break;
				case 'b' : result[index] = 11; break;
				case 'c' : result[index] = 12; break;
				case 'd' : result[index] = 13; break;
				case 'e' : result[index] = 14; break;
				case 'f' : result[index] = 15; break;
				default: result[index] = 0;
			}
			index++;
		}
		return result;
	}
}
