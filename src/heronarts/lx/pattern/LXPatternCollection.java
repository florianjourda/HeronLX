package heronarts.lx.pattern;

import heronarts.lx.HeronLX;

/**
 * Class to group multiple patterns under one.
 */
public class LXPatternCollection extends LXPattern {

	final private LXPattern[] patterns;
//	private static final int BLACK = 0x00000000;

	public LXPatternCollection(HeronLX lx, LXPattern[] patterns) {
		super(lx);
		this.patterns = patterns;
	}

	@Override
	protected void run(int deltaMs) {
		this.clearColors();
		for (LXPattern pattern : this.patterns) {
			pattern.go(deltaMs);
			this.addColors(pattern.getColors());
		}
	}
}
