package xonix;

import java.awt.Rectangle;
import java.io.IOException;

import com.doa.engine.graphics.DoaSprites;

public final class Assets {

	private static final String TILES = "/xonix/tiles.png";

	private Assets() {}

	public static void configureAssets() {
		try {
			DoaSprites.createSprite("enemy", "/xonix/enemy.png");
			DoaSprites.createSpriteFromSpriteSheet("trail", TILES, new Rectangle(54, 0, 18, 18));
			DoaSprites.createSpriteFromSpriteSheet("player", TILES, new Rectangle(72, 0, 18, 18));
			DoaSprites.createSpriteFromSpriteSheet("area", TILES, new Rectangle(90, 0, 18, 18));
			DoaSprites.createSprite("gameover", "/xonix/gameover.png");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
