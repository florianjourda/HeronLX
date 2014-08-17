package heronarts.lx.nodemask.sequence;

import com.sun.tools.javac.code.Attribute;
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
	private PVector sequenceDirection;

	public ParalleLinesNodeMaskSequence(HeronLX lx, PVector sequenceDirection) {
		this(lx, sequenceDirection, lx.width, lx.height);
	}

	/**
	 * @param lx
	 * @param sequenceDirection
	 * @param width
	 * @param height
	 * @throws java.lang.Exception
	 */
	public ParalleLinesNodeMaskSequence(HeronLX lx, PVector sequenceDirection, int width, int height) {
		super();
		this.sequenceDirection = sequenceDirection;
		// @TODO(florian.jourda@gmail.com): make the code DRY once the API is stabilized
		if (sequenceDirection.equals(new PVector(1, 0))) {
			this.nodeMaskArray = new NodeMask[width];
			for (int x = 0; x < width; x++) {
				PVector top = new PVector(x, 0);
				PVector bottom = new PVector(x, height - 1);
				LineNodeMask nodeMask = new LineNodeMask(lx, top, bottom);
				this.nodeMaskArray[x] = nodeMask;
			}
		} else if (sequenceDirection.equals(new PVector(-1, 0))) {
			this.nodeMaskArray = new NodeMask[width];
			for (int x = 0; x < width; x++){
				PVector top = new PVector(x, 0);
				PVector bottom = new PVector(x, height - 1);
				LineNodeMask nodeMask = new LineNodeMask(lx, top, bottom);
				this.nodeMaskArray[width - 1 - x] = nodeMask;
			}
		} else if (sequenceDirection.equals(new PVector(0, 1))) {
			this.nodeMaskArray = new NodeMask[height];
			for (int y = 0; y < height; y++) {
				PVector left = new PVector(0, y);
				PVector right = new PVector(width - 1, y);
				LineNodeMask nodeMask = new LineNodeMask(lx, left, right);
				this.nodeMaskArray[y] = nodeMask;
			}
		} else if (sequenceDirection.equals(new PVector(0, -1))) {
			this.nodeMaskArray = new NodeMask[height];
			for (int y = 0; y < height; y++) {
				PVector left = new PVector(0, y);
				PVector right = new PVector(width - 1, y);
				LineNodeMask nodeMask = new LineNodeMask(lx, left, right);
				this.nodeMaskArray[height - 1 - y] = nodeMask;
			}
		} else {
			//throw new Exception("sequenceDirection should be {1 ,0}, {-1, 0}, {0, 1} or {0, -1}");
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
