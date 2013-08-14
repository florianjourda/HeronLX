package heronarts.lx.nodemask.sequence;

import heronarts.lx.HeronLX;
import heronarts.lx.nodemask.NodeMask;
import processing.core.PVector;

import java.util.ArrayList;

/**
 * Sequence of one node at a time, starting from the center and going outwards until
 * the edges of the matrix in a spiral path.
 */
public class SpiralNodeMaskSequence extends NodeMaskSequence {

	private ArrayList<NodeMask> nodeMaskArrayList;

	public SpiralNodeMaskSequence(HeronLX lx) {
		this(
			lx,
			lx.getCenterPosition(),
			new PVector(0, 1),  // Down for y increasing downward.
			1
		);
	}

	public SpiralNodeMaskSequence(HeronLX lx, PVector center, PVector stepDirection, int ratio) {
		super();
		float distanceIncrementAfterRotation = (float) .5 * ratio;
		float rotationAngle = (float) -Math.PI / 2;  // Counter-clockwise for y increasing downward.
		SquareSpiralWalker squareSpiralWalker = new SquareSpiralWalker(center, stepDirection, distanceIncrementAfterRotation, rotationAngle);

		this.nodeMaskArrayList = new ArrayList<NodeMask>();
		// TODO(florian.jourda): make this work for spirals that are not centered
		int width = Math.max(lx.width, lx.height);
		int max = width * width;// / ratio;
		for (int i = 0; i < max; i ++) {
			NodeMask nodeMask = new NodeMask(lx);
			PVector currentPosition = squareSpiralWalker.getCurrentPosition();
			try {
				nodeMask.addNodeIndex(currentPosition);
			} catch (AssertionError exception) {
				// Ignore
			}
			this.nodeMaskArrayList.add(nodeMask);
			squareSpiralWalker.walkNextStep();
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

/**
 * Iterator that find the next position to go to in order to complete a square spiral outward-going.
 */
class SquareSpiralWalker {

	private PVector currentPosition;
	private PVector stepDirection;
	private float distanceIncrementAfterRotation;
	final private float rotationAngle;
	private float distanceSinceLastRotation;
	private float distanceBeforeNextRotation;

	public SquareSpiralWalker(PVector center, PVector stepDirection, float distanceIncrementAfterRotation, float rotationAngle) {
		this.currentPosition = copyPVector(center);
		this.stepDirection = copyPVector(stepDirection);
		this.distanceIncrementAfterRotation = distanceIncrementAfterRotation;
		this.rotationAngle = rotationAngle;
		this.distanceBeforeNextRotation = 0;
		this.setUpAfterRotation();
	}

	public String toString() {
		return (
			"SquareSpiralWalker:\n" +
			"\tcurrentPosition: " + currentPosition + "\n" +
			"\tstepDirection: " + stepDirection + "\n" +
			"\tdistanceSinceLastRotation: " + distanceSinceLastRotation + "\n" +
			"\tdistanceBeforeNextRotation: " + distanceBeforeNextRotation
		);
	}

	public PVector getCurrentPosition() {
		return this.currentPosition;
	}

	public void walkNextStep() {
		this.currentPosition = copyPVector(this.currentPosition);
		this.goForward();
		if (this.needsToRotate()) {
			this.rotate();
		}
	}

	static private PVector copyPVector(PVector vector) {
		PVector copiedVector = new PVector();
		copiedVector.add(vector);
		return copiedVector;
	}

	private void goForward() {
		this.currentPosition.add(this.stepDirection);
		this.distanceSinceLastRotation += this.stepDirection.mag();
	}

	private Boolean needsToRotate() {
		return this.distanceSinceLastRotation >= this.distanceBeforeNextRotation;
	}

	private void rotate() {
		this.stepDirection.rotate(this.rotationAngle);
		// Rotation with PI looses precisseness, so we fix it here
		this.stepDirection.x = Math.round(this.stepDirection.x);
		this.stepDirection.y = Math.round(this.stepDirection.y);
		this.setUpAfterRotation();
	}

	private void setUpAfterRotation() {
		this.distanceSinceLastRotation = 0;
		this.distanceBeforeNextRotation += this.distanceIncrementAfterRotation;
	}
}
