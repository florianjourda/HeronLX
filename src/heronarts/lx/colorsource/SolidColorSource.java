package heronarts.lx.colorsource;

/**
 * Always return one fixed color.
 */
public class SolidColorSource extends ColorSource {

	private int color;

	public SolidColorSource(int color) {
		this.color = color;
	}

	@Override
	public int getColor(int deltaMs) {
		return this.color;
	}
}
