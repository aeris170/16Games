package snake;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.doa.engine.graphics.DoaSprites;
import com.doa.engine.sound.DoaSounds;

public final class Assets {

	private Assets() {}

	public static void initializeAssets() {
		try {
			DoaSprites.createSprite("white", "/snake/white.png");
			DoaSprites.createSprite("red", "/snake/red.png");
			DoaSprites.createSprite("black", "/snake/black.png");
			DoaSprites.createSprite("green", "/snake/green.png");
			DoaSounds.createSoundClip("snakeMusic", "/snake/snake.wav");
		} catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
			ex.printStackTrace();
		}
	}
}
