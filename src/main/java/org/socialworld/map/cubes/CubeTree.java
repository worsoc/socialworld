/*
* Social World
* Copyright (C) 2026  Mathias Sikos
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.  
*
* or see http://www.gnu.org/licenses/gpl-2.0.html
*
*/
package org.socialworld.map.cubes;

import java.util.ArrayList;
import java.util.List;

/**
 * The cubes of one tile in a compact form, built from their end addresses.
 *
 * Every split cube is one node. Its 27 children need 2 bits each (empty, full
 * or split), so a node fits into one long. The nodes lie breadth first, which
 * means that the split children of a node follow each other.
 *
 * The positions are measured in cells, the cubes of the deepest level.
 */
final class CubeTree {

	private static final int CHILDREN = 27;

	private static final int CODE_EMPTY = 0;
	private static final int CODE_FULL = 1;
	private static final int CODE_SPLIT = 2;
	private static final int CODE_MASK = 3;
	private static final long ALL_SPLIT_BITS = 0xAAAAAAAAAAAAAAAAL;

	static final int HAS_FULL = 1;
	static final int HAS_EMPTY = 2;
	private static final int HAS_BOTH = HAS_FULL | HAS_EMPTY;

	private static final class BuildNode {
		BuildNode[] children;
		boolean isFull;
	}

	final int cellsPerEdge;

	private final int rootCode;
	private final long[] nodes;
	private final int[] firstSplitChild;

	CubeTree(List<String> addresses, int depth) {
		this.cellsPerEdge = (int) Math.pow(3, depth);

		BuildNode root = new BuildNode();
		for (String address : addresses) {
			insert(root, address, depth);
		}
		mergeFullChildren(root);

		this.rootCode = codeOf(root);

		List<BuildNode> order = new ArrayList<BuildNode>();
		if (this.rootCode == CODE_SPLIT) {
			order.add(root);
		}
		List<Integer> firstChildren = new ArrayList<Integer>();
		for (int i = 0; i < order.size(); i++) {
			firstChildren.add(order.size());
			for (BuildNode child : order.get(i).children) {
				if (codeOf(child) == CODE_SPLIT) {
					order.add(child);
				}
			}
		}

		this.nodes = new long[order.size()];
		this.firstSplitChild = new int[order.size()];
		for (int i = 0; i < this.nodes.length; i++) {
			this.firstSplitChild[i] = firstChildren.get(i);
			BuildNode[] children = order.get(i).children;
			long node = 0;
			for (int child = 0; child < CHILDREN; child++) {
				node |= ((long) codeOf(children[child])) << (2 * child);
			}
			this.nodes[i] = node;
		}
	}

	private static void insert(BuildNode root, String address, int depth) {
		if (address.length() > depth) {
			throw new IllegalArgumentException("The address " + address + " is deeper than " + depth + ".");
		}
		BuildNode node = root;
		for (int position = 0; position < address.length() && !node.isFull; position++) {
			if (node.children == null) {
				node.children = new BuildNode[CHILDREN];
			}
			int child = CubeTileAnalysis.LETTERS.indexOf(address.charAt(position));
			if (node.children[child] == null) {
				node.children[child] = new BuildNode();
			}
			node = node.children[child];
		}
		node.isFull = true;
		node.children = null;
	}

	/** A cube without any gap is one full cube, however it was found. */
	private static void mergeFullChildren(BuildNode node) {
		if (node.children == null) {
			return;
		}
		boolean allFull = true;
		for (BuildNode child : node.children) {
			if (child != null) {
				mergeFullChildren(child);
			}
			allFull = allFull && child != null && child.isFull;
		}
		if (allFull) {
			node.isFull = true;
			node.children = null;
		}
	}

	private static int codeOf(BuildNode node) {
		if (node == null) {
			return CODE_EMPTY;
		}
		if (node.isFull) {
			return CODE_FULL;
		}
		return node.children == null ? CODE_EMPTY : CODE_SPLIT;
	}

	int getNodeCount() {
		return this.nodes.length;
	}

	/**
	 * Looks into the box from the minimum (included) to the maximum (excluded).
	 *
	 * @return the flags HAS_FULL and HAS_EMPTY for what the box contains
	 */
	int findContent(int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
		switch (this.rootCode) {
		case CODE_EMPTY:
			return HAS_EMPTY;
		case CODE_FULL:
			return HAS_FULL;
		default:
			return findContentInNode(0, 0, 0, 0, this.cellsPerEdge, minX, maxX, minY, maxY, minZ, maxZ);
		}
	}

	private int findContentInNode(int nodeIndex, int originX, int originY, int originZ, int edge, int minX, int maxX,
			int minY, int maxY, int minZ, int maxZ) {

		int childEdge = edge / 3;
		long node = this.nodes[nodeIndex];
		int content = 0;

		for (int z = Math.max(0, (minZ - originZ) / childEdge); z <= Math.min(2, (maxZ - 1 - originZ) / childEdge); z++) {
			for (int y = Math.max(0, (minY - originY) / childEdge); y <= Math.min(2, (maxY - 1 - originY) / childEdge); y++) {
				for (int x = Math.max(0, (minX - originX) / childEdge); x <= Math.min(2, (maxX - 1 - originX) / childEdge); x++) {
					int child = x + 3 * y + 9 * z;
					int code = (int) (node >>> (2 * child)) & CODE_MASK;

					if (code == CODE_EMPTY) {
						content |= HAS_EMPTY;
					} else if (code == CODE_FULL) {
						content |= HAS_FULL;
					} else {
						int childX = originX + x * childEdge;
						int childY = originY + y * childEdge;
						int childZ = originZ + z * childEdge;
						boolean isInsideBox = minX <= childX && childX + childEdge <= maxX && minY <= childY
								&& childY + childEdge <= maxY && minZ <= childZ && childZ + childEdge <= maxZ;
						if (isInsideBox) {
							// a split cube has both, otherwise it would have been merged
							return HAS_BOTH;
						}
						content |= findContentInNode(indexOfSplitChild(nodeIndex, child), childX, childY, childZ, childEdge,
								minX, maxX, minY, maxY, minZ, maxZ);
					}
					if (content == HAS_BOTH) {
						return HAS_BOTH;
					}
				}
			}
		}
		return content;
	}

	private int indexOfSplitChild(int nodeIndex, int child) {
		long splitChildrenBefore = this.nodes[nodeIndex] & ALL_SPLIT_BITS & ((1L << (2 * child)) - 1);
		return this.firstSplitChild[nodeIndex] + Long.bitCount(splitChildrenBefore);
	}
}
