package heronarts.lx.colorsource;

import heronarts.lx.modulator.LXModulator;
import heronarts.lx.modulator.SawLFO;
import heronarts.lx.modulator.SinLFO;
import processing.core.PApplet;
import processing.core.PConstants;

import java.awt.*;

/**
 * Goes back and forth between two colors.
 */
public class BackAndForthColorSource extends ColorSource {

	private int firstColor;
	private int secondColor;
	private LXModulator modulator;

	public BackAndForthColorSource(int firstColor, int secondColor, int durationMs) {
		this(firstColor, secondColor, new SinLFO(0, 1, durationMs));
	}

	/**
	 *
	 * @param firstColor
	 * @param secondColor
	 * @param modulator Modulator that goes between 0 and 1.
	 */
	public BackAndForthColorSource(int firstColor, int secondColor, LXModulator modulator) {
		this.firstColor = firstColor;
		this.secondColor = secondColor;
		this.modulator = modulator;
		this.modulator.trigger();
	}

	@Override
	public int getColor(int deltaMs) {
		this.modulator.run(deltaMs);
		int color = PApplet.lerpColor(
				this.firstColor,
				this.secondColor,
				this.modulator.getValuef(),
				PConstants.RGB
		);
		return color;
	}
}
