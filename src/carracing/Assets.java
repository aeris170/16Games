package carracing;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.doa.engine.graphics.DoaSprites;
import com.doa.engine.sound.DoaSounds;

public final class Assets {

	private Assets() {}

	public static void configureAssets() {
		try {
			DoaSprites.createSprite("background", "/carracing/background.png");
			DoaSprites.createSprite("car0", "/carracing/car0.png");
			DoaSprites.createSprite("car1", "/carracing/car1.png");
			DoaSprites.createSprite("car2", "/carracing/car2.png");
			DoaSprites.createSprite("car3", "/carracing/car3.png");
			DoaSprites.createSprite("car4", "/carracing/car4.png");
			DoaSprites.createSprite("car5", "/carracing/car5.png");
			DoaSounds.createSoundClip("carRacingMusic", "/carracing/carracingsound.wav");
		} catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
			ex.printStackTrace();
		}
	}
}
