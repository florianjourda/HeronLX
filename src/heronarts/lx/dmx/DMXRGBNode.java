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

package heronarts.lx.dmx;


import heronarts.lx.RGBNode;

/**
 * Mapping of a RGB pixel to its 3 channels (R, G and B) on the DMXOutput
 */
public class DMXRGBNode extends RGBNode {
	final public int rChannelId;
	final public int gChannelId;
	final public int bChannelId;

	public DMXRGBNode(int nodeIndex, int rChannelId, int gChannelId, int bChannelId) {
		super(nodeIndex);
		this.rChannelId = rChannelId;
		this.gChannelId = gChannelId;
		this.bChannelId = bChannelId;
	}
}

