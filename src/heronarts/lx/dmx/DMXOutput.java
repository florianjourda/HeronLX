
package heronarts.lx.dmx;

import dmxP512.*;
import heronarts.lx.RGBNodeOutput;
import processing.core.PApplet;
//import processing.serial.*;

import java.util.Arrays;


// @TODO(florian.jourda): This and Kinet should extend a RGBNodeOutput class
/**
 * DMX driver that outputs to the Enttec USB-DMX dongle.
 */
public class DMXOutput extends RGBNodeOutput {

	final protected DMXRGBNode[] outputNodes;

	private final DmxP512 dmxP512;
	private final int universeSize;
	static private final int firstAddress = 1;


	/**
	 * Creates a DMXOuput instance. This instance will send color
	 * signal to the DMX device for all nodes.
	 *
	 * Case matters for DMXProPort:
	 * - On Windows, port must be upper cased: "COM18"
	 * - On Mac, look at "ls -al /dev/tty.usbserial*" to get your port
	 *
	 * @param applet
	 * @param DMXProPort
	 * @param DMXProBaudrate
	 * @param universeSize
	 * @param outputNodes
	 */
	public DMXOutput(PApplet applet, String DMXProPort, int DMXProBaudrate, int universeSize, DMXRGBNode[] outputNodes) {
		// @TODO(florian.jourda): throw exception if any channel is used twice
		super(outputNodes);
		this.outputNodes = outputNodes;
		this.universeSize = universeSize;
		// We do the buffering ourselves with sendThrottledColors.
		boolean buffered = false;
		this.dmxP512 = new DmxP512(applet, this.universeSize, buffered);
		this.dmxP512.setupDmxPro(DMXProPort, DMXProBaudrate);
	}

	final public void sendColors(int[] colors) {
		int[] channelValues = new int[this.universeSize -1];
		Arrays.fill(channelValues, 0);
		//System.out.println("outputNodes length");
		//System.out.println(this.outputNodes.length);
		// Set the channel values for all nodes
		for (int i = 0; i < colors.length; ++i) {
			//System.out.println("i");
			//System.out.println(i);

			int c = colors[i];
			byte r = (byte) ((c >> 16) & 0xFF);
			byte g = (byte) ((c >> 8) & 0xFF);
			byte b = (byte) ((c) & 0xFF);
			DMXRGBNode node = this.outputNodes[i];
			//System.out.println("rgb channels:");
			//System.out.println(node.rChannelId);
			//System.out.println(node.gChannelId);
			//System.out.println(node.bChannelId);
			channelValues[node.rChannelId] = r;
			channelValues[node.gChannelId] = g;
			channelValues[node.bChannelId] = b;
		}
		this.dmxP512.set(firstAddress, channelValues);
	}
}

/**
 * Reset all channels to 0
 */
/*
void reset() {
		// reset is not working for some reason so we do it by hand
		//this.dmxP512.reset();
		println("Reset");
int[] zeros = new int[511];
Arrays.fill(zeros, 0);
this.dmxP512.set(1, zeros);
println("End");
}
*/
