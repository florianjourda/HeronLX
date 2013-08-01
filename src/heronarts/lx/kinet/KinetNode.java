/**
 * ##library.name##
 * ##library.sentence##
 * ##library.url##
 *
 * Copyright ##copyright## ##author##
 * All Rights Reserved
 *
 * @author      ##author##
 * @modified    ##date##
 * @version     ##library.prettyVersion## (##library.version##)
 */

package heronarts.lx.kinet;

import heronarts.lx.RGBNode;

public class KinetNode extends RGBNode {
	final public KinetPort outputPort;

	public KinetNode(KinetPort outputPort, int nodeIndex) {
		super(nodeIndex);
		if (outputPort == null) {
			throw new NullPointerException();
		}
		this.outputPort = outputPort;
	}
}
