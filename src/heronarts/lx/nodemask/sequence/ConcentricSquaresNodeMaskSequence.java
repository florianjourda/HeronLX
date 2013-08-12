package heronarts.lx.nodemask.sequence;

import heronarts.lx.HeronLX;
import heronarts.lx.nodemask.NodeMask;
import heronarts.lx.nodemask.SquareNodeMask;
import processing.core.PVector;

import java.util.ArrayList;

/**
 * Sequence of square outlines that start from the center and finish at the edges of the matrix.
 */
public class ConcentricSquaresNodeMaskSequence extends NodeMaskSequence {

	private ArrayList<NodeMask> nodeMaskArrayList;

	public ConcentricSquaresNodeMaskSequence(HeronLX lx) {
		this(lx, new PVector((int)lx.midwidth, (int)lx.midheight), lx.width, lx.height);
	}

	public ConcentricSquaresNodeMaskSequence(HeronLX lx, PVector center, int width, int height) {
		super();
		this.nodeMaskArrayList = new ArrayList<NodeMask>();
		for (int squareSize = 1; squareSize <= Math.max(lx.width, lx.height); squareSize += 2) {
			PVector topLeft = new PVector(center.x - squareSize / 2, center.y - squareSize / 2);
			SquareNodeMask nodeMask = new SquareNodeMask(lx, topLeft, squareSize, squareSize);
			this.nodeMaskArrayList.add(nodeMask);
		}
		this.length = this.nodeMaskArrayList.size();
	}

	public Iterable<NodeMask> getIteratable() {
		// @TODO(florian.jourda): give back an immutable object
		return this.nodeMaskArrayList;
	}

	public NodeMask get(int index) {
		return this.nodeMaskArrayList.get(index);
	}
}
