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

package heronarts.lx;

import processing.core.PConstants;
import processing.core.PGraphics;
import java.lang.Math;

public class Simulation implements PConstants {

	final private HeronLX lx;

	private int x;
	private int y;
	private int w;
	private int h;
	private int pixelStyle;
	private double xSpacing;
	private double ySpacing;

	// To draw a small point for each pixel.
	public static final int PIXEL_STYLE_SMALL_POINT = 0;
	// To draw a rectangle for each pixel that fills the x and y spacing.
	public static final int PIXEL_STYLE_FULL_RECT = 1;

	protected Simulation(HeronLX lx) {
		this.lx = lx;
		// Set defaults.
		this.setBounds(0, 0, lx.applet.width, lx.applet.height);
		this.setPixelStyle(PIXEL_STYLE_SMALL_POINT);
	}

	public void setBounds(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.w = width;
		this.h = height;
		this.xSpacing = width / (double) lx.width;
		this.ySpacing = height / (double) lx.height;
	}

	public void setPixelStyle(int pixelStyle) {
		this.pixelStyle = pixelStyle;
	}

	protected void draw(int[] colors) {
		PGraphics g = lx.getGraphics();
		g.noStroke();
		g.fill(0);
		g.rect(this.x, this.y, this.w, this.h);
		for (int i = 0; i < this.lx.width; ++i) {
			for (int j = 0; j < this.lx.height; ++j) {
				int color = colors[i + j * this.lx.width];
				int x = (int) (this.x + (i + 0.5) * this.xSpacing);
				int y = (int) (this.y + (j + 0.5) * this.ySpacing);
				this.drawPixel(g, x, y, color);
			}
		}
	}

	private void drawPixel(PGraphics g, int x, int y, int color) {
		g.fill(color);
		switch(this.pixelStyle) {
			case PIXEL_STYLE_FULL_RECT:
				g.rectMode(CENTER);
				// We use "+ 1" to avoid black gap between the rectangles due to rounding.
				g.rect(x, y, (int) this.xSpacing + 1, (int) this.ySpacing + 1);
				break;
			case PIXEL_STYLE_SMALL_POINT:
				g.ellipseMode(CENTER);
				int ellipse_size = (int) Math.max(2, Math.min(this.xSpacing, this.ySpacing) / 3.);
				g.ellipse(x, y, ellipse_size, ellipse_size);
				break;
		}
	}
}
