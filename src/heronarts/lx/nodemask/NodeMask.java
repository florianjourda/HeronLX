package heronarts.lx.nodemask;

import heronarts.lx.HeronLX;
import processing.core.PVector;

import java.util.*;

/**
 * A node mask is a subset of all the nodes (pixels) in the HeronLX width * height matrix.
 * It can contain from 0 to width * height nodes.
 */
public class NodeMask {
	final private Set<Integer> nodeIndexes;
	final private int width;
	final private int height;

	public NodeMask(HeronLX lx) {
		this(lx, new HashSet<Integer>());
	}

	public NodeMask(HeronLX lx, int[] nodeIndexesAsInt) {
		this(lx, convertIntToInteger(nodeIndexesAsInt));
	}

	static private Integer[] convertIntToInteger(int[] nodeIndexesAsInt) {
		Integer[] nodeIndexes = new Integer[nodeIndexesAsInt.length];
		int i = 0;
		for (int value : nodeIndexesAsInt) {
			nodeIndexes[i++] = Integer.valueOf(value);
		}
		return nodeIndexes;
	}

	public NodeMask(HeronLX lx, Integer[] nodeIndexes) {
		this(lx, new HashSet<Integer>(Arrays.asList(nodeIndexes)));
	}

	public NodeMask(HeronLX lx, Set<Integer> nodeIndexes) {
		this.width = lx.width;
		this.height = lx.height;
		this.nodeIndexes = nodeIndexes;
	}

	private int minIndex() {
		return 0;
	}

	private int maxIndex() {
		return this.width * this.height - 1;
	}

	public void addNodeIndex(int index) {
		assert this.minIndex() <= index && index <= this.maxIndex();
		this.nodeIndexes.add(index);
	}

	public void addNodeIndexes(Integer[] indexes) {
		for (int i = 0; i < indexes.length; i++) {
			int index = indexes[i];
			// Don't use directly nodeIndexe.addAll because we want to use
			// the checks inside addNodeIndex
			this.addNodeIndex(index);
		}
	}

	public Set<Integer>getNodeIndexes() {
		Set<Integer> copiedNodeIndexes = new HashSet<Integer>();
		copiedNodeIndexes.addAll(this.nodeIndexes);
		return copiedNodeIndexes;
	}

	/**
	 * @param lx
	 * @param from
	 * @param to
	 * @return The set of nodes located a line from a point to another.
	 */
	static Set<Integer>calculateNodeIndexesOnLine(HeronLX lx, PVector from, PVector to) {
		System.out.println("LINE:");
		System.out.println(from.x);
		System.out.println(from.y);
		System.out.println(to.x);
		System.out.println(to.y);
		float maxDistance = Math.max(Math.abs(to.x - from.x), Math.abs(to.y - from.y));
		float deltaX, deltaY;
		if (maxDistance > 0) {
			deltaX = (to.x - from.x) / maxDistance;
			deltaY = (to.y - from.y) / maxDistance;
		} else {
			deltaX = 0;
			deltaY = 0;
		}
		System.out.println(deltaX);
		System.out.println(deltaY);
		Set<Integer> nodeIndexes = new HashSet<Integer>();
		for (int step = 0; step <= maxDistance; step++) {
			int x = (int)(from.x + step * deltaX);
			int y = (int)(from.y + step * deltaY);
			int nodeIndex = lx.index(x, y);
			System.out.println("add:");
			System.out.println(x);
			System.out.println(y);
			nodeIndexes.add(nodeIndex);
		}
		System.out.println("ALL:");
		System.out.println(nodeIndexes);
		return nodeIndexes;
	}
}
