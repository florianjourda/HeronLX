package heronarts.lx.nodemask.sequence;

import heronarts.lx.nodemask.NodeMask;

import java.util.ArrayList;

/**
 * A sequence of NodeMasks, indexed from 0 to N-1.
 */
//public class NodeMaskSequence extends ArrayList<NodeMask> {}
abstract public class NodeMaskSequence {

	public int length;

	abstract public Iterable<NodeMask> getIteratable();

	abstract public NodeMask get(int index);

}
