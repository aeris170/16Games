package doodlejump;

import java.io.IOException;

import com.doa.engine.graphics.DoaSprites;

public final class Assets {

	private Assets() {}

	public static void configureAssets() {
		try {
			DoaSprites.createSprite("background", "/doodlejump/background.png");
			DoaSprites.createSprite("doodle", "/doodlejump/doodle.png");
			DoaSprites.createSprite("platform", "/doodlejump/platform.png");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
