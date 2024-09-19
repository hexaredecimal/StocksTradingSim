package simulator;

import com.raylib.java.Raylib;
import com.raylib.java.core.Color;

/**
 *
 * @author hexaredecimal
 */
public class CandleStick {
	private Raylib rlj;
	private Color color;
	private int x, y, h; 
	public CandleStick(Raylib rlj, Color color, int x, int y, int h) {
		this.rlj = rlj;
		this.color = color;
		this.x = x; 
		this.y = y;
		this.h = h;
	}
	
	public void draw() {
		int height = h + 40;
		rlj.shapes.DrawRectangle(x + 3, y - 10 , 3, h + 40, this.color);
		rlj.shapes.DrawRectangle(x, y + 10, 10, h, this.color);
	}
}
