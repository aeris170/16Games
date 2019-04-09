package tetris;

import java.awt.Rectangle;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.doa.engine.graphics.DoaSprites;
import com.doa.engine.sound.DoaSounds;

public final class Assets {

	private Assets() {}

	public static void initializeAssets() {
		try {
			DoaSprites.createSprite("background", "/background.png");
			DoaSprites.createSprite("frame", "/frame.png");
			for (int i = 0; i < 8; i++) {
				DoaSprites.createSpriteFromSpriteSheet("" + i, "/tiles.png", new Rectangle(i * 18, 0, 18, 18));
			}
			DoaSounds.createSoundClip("tetrisMusic", "/tetris.wav");
		} catch (final IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
			ex.printStackTrace();
		}
	}
}
