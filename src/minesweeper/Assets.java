package minesweeper;

import java.awt.Rectangle;
import java.io.IOException;

import com.doa.engine.graphics.DoaSprites;

public final class Assets {

	private static final String TILES = "/minesweeper/tiles.jpg";

	private Assets() {}

	public static void initializeAssets() {
		try {
			DoaSprites.createSpriteFromSpriteSheet("openTile", TILES, new Rectangle(0, 0, 32, 32));
			DoaSprites.createSpriteFromSpriteSheet("1tile", TILES, new Rectangle(32, 0, 32, 32));
			DoaSprites.createSpriteFromSpriteSheet("2tile", TILES, new Rectangle(64, 0, 32, 32));
			DoaSprites.createSpriteFromSpriteSheet("3tile", TILES, new Rectangle(96, 0, 32, 32));
			DoaSprites.createSpriteFromSpriteSheet("4tile", TILES, new Rectangle(128, 0, 32, 32));
			DoaSprites.createSpriteFromSpriteSheet("5tile", TILES, new Rectangle(160, 0, 32, 32));
			DoaSprites.createSpriteFromSpriteSheet("6tile", TILES, new Rectangle(192, 0, 32, 32));
			DoaSprites.createSpriteFromSpriteSheet("7tile", TILES, new Rectangle(224, 0, 32, 32));
			DoaSprites.createSpriteFromSpriteSheet("8tile", TILES, new Rectangle(256, 0, 32, 32));
			DoaSprites.createSpriteFromSpriteSheet("mine", TILES, new Rectangle(288, 0, 32, 32));
			DoaSprites.createSpriteFromSpriteSheet("closedTile", TILES, new Rectangle(320, 0, 32, 32));
			DoaSprites.createSpriteFromSpriteSheet("flagTile", TILES, new Rectangle(352, 0, 32, 32));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
