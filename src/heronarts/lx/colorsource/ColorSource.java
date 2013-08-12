package heronarts.lx.colorsource;

abstract public class ColorSource {

	/**
	 *
	 * @param deltaMs Number of milliseconds elapsed since last invocation
	 * @return int a color for the given time
	 */
	abstract public int getColor(int deltaMs);
}
