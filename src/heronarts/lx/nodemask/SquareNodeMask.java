package heronarts.lx.nodemask;

import heronarts.lx.HeronLX;
import processing.core.PVector;

import java.util.HashSet;
import java.util.Set;

/**
 * Mask that contains the outline of a square.
 */
public class SquareNodeMask extends NodeMask {
	public SquareNodeMask(HeronLX lx, PVector topLeft, int width, int height) {
		this(lx, topLeft, new PVector(topLeft.x + width - 1, topLeft.y + height - 1));
	}

	public SquareNodeMask(HeronLX lx, PVector topLeft, PVector bottomRight) {
		super(lx);
		PVector topRight = new PVector(bottomRight.x, topLeft.y);
		PVector bottomLeft = new PVector(topLeft.x, bottomRight.y);
		Set<Integer> topLine = calculateNodeIndexesOnLine(lx, topLeft, topRight);
		Set<Integer> rightLine = calculateNodeIndexesOnLine(lx, topRight, bottomRight);
		Set<Integer> bottomLine = calculateNodeIndexesOnLine(lx, bottomRight, bottomLeft);
		Set<Integer> leftLine = calculateNodeIndexesOnLine(lx, bottomLeft, topLeft);
		this.addNodeIndexes(topLine);
		this.addNodeIndexes(rightLine);
		this.addNodeIndexes(bottomLine);
		this.addNodeIndexes(leftLine);
	}
}
