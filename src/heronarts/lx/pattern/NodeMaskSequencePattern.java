package heronarts.lx.pattern;

import heronarts.lx.HeronLX;
import heronarts.lx.modulator.SawLFO;
import heronarts.lx.nodemask.NodeMask;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Braindead simple test pattern that iterates through all the nodes turning
 * them on one by one in fixed order.
 */
public class NodeMaskSequencePattern extends LXPattern {

	final private SawLFO modulator;
	final private NodeMask[] nodeMaskSequence;

	public NodeMaskSequencePattern(HeronLX lx, ArrayList<NodeMask> nodeMaskSequence) {
		this(lx, nodeMaskSequence.toArray(new NodeMask[nodeMaskSequence.size()]));
	}

	public NodeMaskSequencePattern(HeronLX lx, NodeMask[] nodeMaskSequence) {
		super(lx);
		this.nodeMaskSequence = nodeMaskSequence;
		int startVal = 0;
		int endVal = this.nodeMaskSequence.length;
		int durationMs = (endVal - startVal) * 1000; // One seconde per step
		this.modulator = new SawLFO(startVal, endVal, durationMs);
		this.addModulator(this.modulator).trigger();
	}

	public void run(int deltaMs) {
		int activeMaskIndex = (int) Math.floor(this.modulator.getValue());
		NodeMask activeNodeMask = this.nodeMaskSequence[activeMaskIndex];
		Set<Integer> activeMaskNodeIndexes = activeNodeMask.getNodeIndexes();
		for (int nodeIndex = 0; nodeIndex < lx.total; nodeIndex++)
		{
			int color = (activeMaskNodeIndexes.contains(nodeIndex)) ? 0xFFFFFFFF : 0xFFFF0000;
			this.setColor(nodeIndex, color);
		}
	}
}
