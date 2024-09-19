package simulator;

import com.raylib.java.Raylib;
import com.raylib.java.core.Color;
import com.raylib.java.core.input.Keyboard;
import com.raylib.java.core.rcamera.Camera2D;
import com.raylib.java.raymath.Vector2;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hexaredecimal
 */
public class Simulator {
	private static double amplitude = 100;  // Set a consistent amplitude for predictable results
	private static double frequency = 1;   // Frequency to control the oscillation speed
	private static double step = 0.01;     // Step to increment the angle for sine calculation
	private static double currentAngle = 0.0;  // Tracks the current angle in the sine wave

	
	public static void populateCandles(Raylib rlj, List<CandleStick> candles, int max, int height) {
		Color c = Color.RED; 
		int PADDING = 15; 
		for (int i = 1; i < max; i++) {
			int h = 10 + (int) (Math.random() * 80);
			int y = (int) (Math.sin(i * amplitude) * amplitude * frequency);
			double product = (amplitude * frequency);
			if (y <= -product + 5) {
				// System.out.println("Buy: " + y + " => " + product);
				c = Color.BLUE; 
				frequency =  1 +(int) (Math.random() * 2);
			} else if (y >= product - 5) {
				c = Color.RED;
				//System.out.println("Sell: " + y + " => " + (amplitude * frequency));
				frequency = 1 + (int) (Math.random() * 2);
			} else {
				//System.out.println(y + " : " + product);
			}
			candles.add(new CandleStick(rlj, c, i * 20 + PADDING, height - 350 - y, h));
		}
	}

	public static void regenerate(Raylib rlj, List<CandleStick> candles, int height) {
		Color c = Color.RED; 
		int PADDING = 15; 
		for (int i = 1; i < candles.size(); i++) {
			int h = 10 + (int) (Math.random() * 80);
			int y = (int) (Math.sin(i * amplitude) * amplitude * frequency);
			if (y <= -95) {
				c = Color.BLUE; 
			} else if (y > 95) {
				c = Color.RED;
			}
			var candle = new CandleStick(rlj, c, i * 20 + PADDING, height - 350 - y, h);
			candles.set(i, candle);
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		final int WIDTH = 800, HEIGHT = 600; 
		Raylib rlj = new Raylib();
		rlj.core.InitWindow(WIDTH, HEIGHT, "Raylib-J Example");

		CandleStick cs = new CandleStick(rlj, Color.RED, 10, 20, 40);
		List<CandleStick> list = new ArrayList<>();

		populateCandles(rlj, list, 200, HEIGHT);

	
		Camera2D camera = new Camera2D();
		Vector2 pos = camera.getOffset();
		String market = "MSFT";
		while (!rlj.core.WindowShouldClose()) {
			rlj.core.BeginDrawing();
			rlj.core.BeginMode2D(camera);
			rlj.core.ClearBackground(Color.WHITE);

			if (rlj.core.IsKeyDown(Keyboard.KEY_LEFT)) {
				pos.x += 10;
			}
			if (rlj.core.IsKeyDown(Keyboard.KEY_RIGHT)) {
				pos.x -= 10;
			}

			if (rlj.core.IsKeyDown(Keyboard.KEY_R)) {
				regenerate(rlj, list, HEIGHT);
			}
			
			if (rlj.core.IsKeyDown(Keyboard.KEY_A)) {
				populateCandles(rlj, list, 200, HEIGHT);
				regenerate(rlj, list, HEIGHT);
			}
			
			list.forEach(action -> action.draw());
			//cs.draw();
			// rlj.text.DrawText("Hello, World!", 800 - (rlj.text.MeasureText("Hello, World!", 20) / 2), 300, 20, Color.DARKGRAY);
			rlj.core.EndMode2D();
			rlj.text.DrawText(market, 10, 10, 15, Color.GOLD);
			rlj.core.EndDrawing();
		}
	}
}
