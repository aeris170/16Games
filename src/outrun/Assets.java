package outrun;

import java.io.IOException;

import com.doa.engine.graphics.DoaSprites;

public final class Assets {

	private Assets() {}

	public static void configureAssets() {
		try {
			DoaSprites.createSprite("bg", "/outrun/bg.png");
			DoaSprites.createSprite("tree1", "/outrun/1.png");
			DoaSprites.createSprite("tree2", "/outrun/2.png");
			DoaSprites.createSprite("tree3", "/outrun/3.png");
			DoaSprites.createSprite("tree4", "/outrun/4.png");
			DoaSprites.createSprite("tree5", "/outrun/5.png");
			DoaSprites.createSprite("bush", "/outrun/6.png");
			DoaSprites.createSprite("shop", "/outrun/7.png");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
