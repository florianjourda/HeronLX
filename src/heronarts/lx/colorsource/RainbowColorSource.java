package heronarts.lx.colorsource;

import heronarts.lx.modulator.SawLFO;

import java.awt.*;

/**
 * Cycles through all the color hues, fully saturated and bright.
 */
public class RainbowColorSource extends ColorSource {

	private SawLFO modulator;

	public RainbowColorSource(int durationMs) {
		this.modulator = new SawLFO(0, 1, durationMs);
		this.modulator.trigger();
	}

	@Override
	public int getColor(int deltaMs) {
		this.modulator.run(deltaMs);
		float hue = (float) this.modulator.getValue();
		float brightness = 1;
		float saturation = 1;
		return Color.HSBtoRGB(hue, saturation, brightness);
	}
}
