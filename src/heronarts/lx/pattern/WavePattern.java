package heronarts.lx.pattern;

import heronarts.lx.HeronLX;
import heronarts.lx.colorsource.ColorSource;
import heronarts.lx.nodemask.NodeMask;
import heronarts.lx.nodemask.sequence.NodeMaskSequence;

import java.util.ArrayList;
import java.util.List;

/**
 * Pattern where the 1st set of nodes displays the color of a color source,
 * and where the n-th set of nodes displays the same color as the n-1th but with a fixed delay.
 * Great to do wave effects.
 */
public class WavePattern extends LXPattern {

	final private ColorSource colorSource;
	final private NodeMaskSequence nodeMaskSequence;
	private int delayMs;
	private float time;
	final private ColorCache colorCache;

	final static int DEFAULT_COLOR = 0x00000000; // Invisible Black

	public WavePattern(HeronLX lx, NodeMaskSequence nodeMaskSequence, ColorSource colorSource, int delayMs) {
		super(lx);
		this.nodeMaskSequence = nodeMaskSequence;
		this.colorSource = colorSource;
		this.delayMs = delayMs;
		this.time = 0;
		this.colorCache = new ColorCache();
	}

	public void run(int deltaMs) {
		this.time += deltaMs;
		float nodeMaskTime = this.time;
		int newColor = this.colorSource.getColor(deltaMs);
		this.colorCache.saveColorAtTime(newColor, nodeMaskTime);
		this.clearColors();
		int nodeMaskColor;
//		System.out.println("\n\n===============\n\n");
		int i =0;
		for (NodeMask nodeMask : this.nodeMaskSequence.getIteratable()) {
//			System.out.println("Set color for " + i + ": " + nodeMask);
			i++;
			try {
				nodeMaskColor = this.colorCache.getColorAtTime(nodeMaskTime);
//				System.out.println(nodeMaskColor);
			} catch (ColorCacheException exception) {
//				System.out.println("USE DEFAULT COLOR");
				// If a color could not be found for the given time, we use the default color (black)
				nodeMaskColor = DEFAULT_COLOR;
			}
			this.setColor(nodeMask, nodeMaskColor);
			// The next mask will show the color of "delayMs" milliseconds ago.
			nodeMaskTime -= this.delayMs;
		}
	}
}

/**
 * Saves and return the values that one color takes over time.
 * Old values before one specigic time can be flushed if asked.
 */
class ColorCache {

	final private List<ColorAtTime> colorAtTimeList;

	public ColorCache() {
		this.colorAtTimeList = new ArrayList<ColorAtTime>();
	}

	public void saveColorAtTime(int color, float time) {
		ColorAtTime colorAtTime = new ColorAtTime(color, time);
		this.colorAtTimeList.add(colorAtTime);
	}

	/**
	 * @param time at time Time to get the color at
	 * @return color at the given time
	 * @throws ColorCacheException
	 */
	public int getColorAtTime(float time) throws ColorCacheException {
		ColorAtTime colorAtTimeBefore = null;
		ColorAtTime colorAtTimeAfter = null;
		for (ColorAtTime colorAtTime : this.colorAtTimeList) {
			if (time == colorAtTime.time) {
				return colorAtTime.color;
			} else if (time > colorAtTime.time) {
				colorAtTimeBefore = colorAtTime;
			} else {
				colorAtTimeAfter = colorAtTime;
				break;
			}
		}

		if (colorAtTimeAfter == null) {
			throw new ColorCacheException("Given time is too recent, no color value available yet for it.");
		} else if (colorAtTimeBefore == null) {
			throw new ColorCacheException("Given time is too old, no more color value available for it.");
		}

		return colorAtTimeBefore.color;
	}
}

class ColorAtTime {

	final public float time;
	final public int color;

	ColorAtTime(int color, float time) {
		this.time = time;
		this.color = color;
	}
}

class ColorCacheException extends Exception {

	ColorCacheException(String message) {
		super(message);
	}
};

