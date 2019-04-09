package puzzlefifteen;

import java.awt.Rectangle;
import java.io.IOException;

import com.doa.engine.graphics.DoaSprites;

public final class Assets {

	private Assets() {}

	public static void initializeAssets(int i) {
		try {
			for (int yy = 0; yy < Puzzle15.GRID_Y; yy++) {
				for (int xx = 0; xx < Puzzle15.GRID_X; xx++)
					if (i == 0) {
						DoaSprites.createSpriteFromSpriteSheet("" + (yy * Puzzle15.GRID_X + xx), "/puzzle15/bg.png", new Rectangle(xx * 128, yy * 128, 128, 128));
					} else {
						DoaSprites.createSpriteFromSpriteSheet("" + (yy * Puzzle15.GRID_X + xx), "/puzzle15/15.png", new Rectangle(xx * 64, yy * 64, 64, 64));
					}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
