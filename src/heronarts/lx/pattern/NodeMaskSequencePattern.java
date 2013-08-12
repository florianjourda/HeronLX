package heronarts.lx.pattern;

import heronarts.lx.HeronLX;
import heronarts.lx.modulator.SawLFO;
import heronarts.lx.nodemask.NodeMask;
import heronarts.lx.nodemask.sequence.NodeMaskSequence;
import processing.core.PApplet;
import processing.core.PConstants;

import java.util.Set;

/**
 * Braindead simple test pattern that iterates through all the nodes turning
 * them on one by one in fixed order.
 */
public class NodeMaskSequencePattern extends LXPattern {

	final private SawLFO modulator;
	final private NodeMaskSequence nodeMaskSequence;
	int activeColor;

	public NodeMaskSequencePattern(HeronLX lx, NodeMaskSequence nodeMaskSequence, int stepDurationMs, int activeColor) {
		super(lx);
		this.nodeMaskSequence = nodeMaskSequence;
		this.activeColor = activeColor;
		int startVal = 0;
		int endVal = this.nodeMaskSequence.length;
		int durationMs = (endVal - startVal) * stepDurationMs; // One seconde per step
		this.modulator = new SawLFO(startVal, endVal, durationMs);
		this.addModulator(this.modulator).trigger();
	}

	public void run(int deltaMs) {
		this.clearColors();
		double step = this.modulator.getValue();
		int activeMaskIndex = (int) Math.floor(step);
		float stepInsideStep = (float) step % 1;
		stepInsideStep = (float) 1.0;
		NodeMask activeNodeMask = this.nodeMaskSequence.get(activeMaskIndex);
		Set<Integer> activeMaskNodeIndexes = activeNodeMask.getNodeIndexes();
		for (int nodeIndex : activeMaskNodeIndexes) {
			int color = PApplet.lerpColor(
				0xFF000000,
				this.activeColor,
				stepInsideStep,
				PConstants.RGB);
			this.setColor(nodeIndex, color);
		}
	}
}
