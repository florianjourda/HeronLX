package heronarts.lx.nodemask;

import heronarts.lx.HeronLX;
import processing.core.PVector;

import java.util.Set;

/**
 * Mask that contains all nodex on one line.
 */
public class LineNodeMask extends NodeMask {

	public LineNodeMask(HeronLX lx, PVector from, PVector to) {
		super(lx);
		Set<Integer> lineIndexes = calculateNodeIndexesOnLine(lx, from, to);
		this.addNodeIndexes(lineIndexes);
	}
}
