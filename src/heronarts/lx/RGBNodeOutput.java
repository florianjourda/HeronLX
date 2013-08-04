package heronarts.lx;

public abstract class RGBNodeOutput {

	/**
	 * In practice this is as hard as some DMX devices can be pushed
	 * before they start dropping frames.
	 */
	protected final int DEFAULT_FRAMERATE = 24;

	final protected RGBNode[] outputNodes;

	private long sendThrottleMillis;
	private long lastFrameMillis;

	public RGBNodeOutput(RGBNode[] outputNodes) {
		this.outputNodes = outputNodes;
		this.setFramerate(DEFAULT_FRAMERATE);
		this.lastFrameMillis = 0;
	}

	final public int size() {
		return this.outputNodes.length;
	}

	final public RGBNodeOutput setFramerate(double frameRate) {
		this.sendThrottleMillis = (long) (1000. / frameRate);
		return this;
	}

	final public void sendThrottledColors(int[] colors) {
		long now = System.currentTimeMillis();
		if (now - this.lastFrameMillis > this.sendThrottleMillis) {
			this.lastFrameMillis = now;
			this.sendColors(colors);
		}
	}

	abstract public void sendColors(int[] colors);
}
