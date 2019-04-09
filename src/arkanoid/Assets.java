package arkanoid;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.doa.engine.graphics.DoaSprites;
import com.doa.engine.sound.DoaSounds;

public final class Assets {

	private Assets() {}

	public static void configureAssets() {
		try {
			DoaSprites.createSprite("background", "/arkanoid/background.png");
			DoaSprites.createSprite("ball", "/arkanoid/ball.png");
			DoaSprites.createSprite("paddle", "/arkanoid/paddle.png");
			DoaSprites.createSprite("block", "/arkanoid/block.png");
			DoaSounds.createSoundClip("arkanoidMusic", "/arkanoid/arkanoid.wav");
		} catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
			ex.printStackTrace();
		}
	}
}
