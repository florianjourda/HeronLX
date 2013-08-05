package heronarts.lx.nodemask;

import heronarts.lx.HeronLX;
import processing.core.PVector;

import java.util.HashSet;
import java.util.Set;

/**
 * Mask that contains the outline of a square.
 */
public class NodeMaskSquare extends NodeMask {
	public NodeMaskSquare(HeronLX lx, PVector topLeft, int width, int height) {
		super(lx, calculateNodeIndexes(lx, topLeft, new PVector(topLeft.x + width - 1, topLeft.y + height - 1)));
	}

	public NodeMaskSquare(HeronLX lx, PVector topLeft, PVector bottomRight) {
		super(lx, calculateNodeIndexes(lx, topLeft, bottomRight));
	}

	static Set<Integer> calculateNodeIndexes(HeronLX lx, PVector topLeft, PVector bottomRight) {
		Set<Integer> nodeIndexes = new HashSet<Integer>();
		PVector topRight = new PVector(bottomRight.x, topLeft.y);
		PVector bottomLeft = new PVector(topLeft.x, bottomRight.y);
		Set<Integer> topLine = calculateNodeIndexesOnLine(lx, topLeft, topRight);
		Set<Integer> rightLine = calculateNodeIndexesOnLine(lx, topRight, bottomRight);
		Set<Integer> bottomLine = calculateNodeIndexesOnLine(lx, bottomRight, bottomLeft);
		Set<Integer> leftLine = calculateNodeIndexesOnLine(lx, bottomLeft, topLeft);
		nodeIndexes.addAll(topLine);
		nodeIndexes.addAll(rightLine);
		nodeIndexes.addAll(bottomLine);
		nodeIndexes.addAll(leftLine);
		return nodeIndexes;
	}
}
