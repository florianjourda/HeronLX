package heronarts.lx.nodemask.sequence;

import heronarts.lx.HeronLX;
import heronarts.lx.nodemask.LineNodeMask;
import heronarts.lx.nodemask.NodeMask;
import heronarts.lx.nodemask.SquareNodeMask;
import processing.core.PVector;

import java.util.Arrays;
import java.util.Set;

/**
 * Sequence of parallel lines, each going from the top to the bottom, and each
 * being one nodes to the left of the previous one.
 */
public class ParalleLinesNodeMaskSequence extends NodeMaskSequence {

	private NodeMask[] nodeMaskArray;

	public ParalleLinesNodeMaskSequence(HeronLX lx) {
		this(lx, lx.width, lx.height);
	}

	public ParalleLinesNodeMaskSequence(HeronLX lx, int width, int height) {
		// @TODO(florian.jourda): give more options about direction
		super();
		this.nodeMaskArray = new NodeMask[width];
		for (int x = 0; x < width; x++) {
			PVector top = new PVector(x, 0);
			PVector bottom = new PVector(x, height - 1);
			LineNodeMask nodeMask = new LineNodeMask(lx, top, bottom);
			this.nodeMaskArray[x] = nodeMask;
		}
		this.length = this.nodeMaskArray.length;
	}

	public Iterable<NodeMask> getIteratable() {
		// @TODO(florian.jourda): give back an immutable object
		return Arrays.asList(this.nodeMaskArray);
	}

	public NodeMask get(int index) {
		return this.nodeMaskArray[index];
	}
}
