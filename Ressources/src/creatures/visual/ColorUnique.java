package creatures.visual;

import java.awt.Color;

import creatures.IColorStrategy;

public class ColorUnique implements IColorStrategy {
	private float r = 0.0f;
	private float g = 0.0f;
	private float b = 0.0f;
	int n;

	public ColorUnique(int n_) {
		this.r = r;
		this.g = g;
		this.b = b;
		n=n_;
	}


	
	public Color getColor() {
		return new Color(r, g, b);
	}

}
