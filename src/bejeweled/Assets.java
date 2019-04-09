package bejeweled;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.doa.engine.graphics.DoaAnimations;
import com.doa.engine.graphics.DoaSprites;
import com.doa.engine.sound.DoaSounds;

public final class Assets {

	private static final long ANIMATION_DELAY = 40;

	private Assets() {}

	public static void configureAssets() {
		try {
			DoaSprites.createSprite("background", "/bejeweled/background.png");
			DoaSprites.createSprite("hover", "/bejeweled/hover.png");
			DoaSprites.createSprite("select", "/bejeweled/select.png");
			DoaAnimations.createAnimation("BLUE", "/bejeweled/blue.gif", ANIMATION_DELAY);
			DoaAnimations.createAnimation("GREEN", "/bejeweled/greenf.gif", ANIMATION_DELAY);
			DoaAnimations.createAnimation("ORANGE", "/bejeweled/orange.gif", ANIMATION_DELAY);
			DoaAnimations.createAnimation("PINK", "/bejeweled/pink.gif", ANIMATION_DELAY);
			DoaAnimations.createAnimation("RED", "/bejeweled/red.gif", ANIMATION_DELAY);
			DoaAnimations.createAnimation("WHITE", "/bejeweled/white.gif", ANIMATION_DELAY);
			DoaAnimations.createAnimation("YELLOW", "/bejeweled/yellow.gif", ANIMATION_DELAY);
			DoaAnimations.createAnimation("HYPER", "/bejeweled/hyper.gif", ANIMATION_DELAY);
			DoaSounds.createSoundClip("badMove", "/bejeweled/badmove.wav");
			DoaSounds.createSoundClip("hypercreate", "/bejeweled/hypercube_create.wav");
			DoaSounds.createSoundClip("gemhit", "/bejeweled/gem_hit.wav");
			DoaSounds.createSoundClip("hover", "/bejeweled/hover.wav");
			DoaSounds.createSoundClip("combo0", "/bejeweled/combo0.wav");
			DoaSounds.createSoundClip("combo1", "/bejeweled/speedmatch1.wav");
			DoaSounds.createSoundClip("combo2", "/bejeweled/speedmatch2.wav");
			DoaSounds.createSoundClip("combo3", "/bejeweled/speedmatch3.wav");
			DoaSounds.createSoundClip("combo4", "/bejeweled/speedmatch4.wav");
			DoaSounds.createSoundClip("combo5", "/bejeweled/speedmatch5.wav");
			DoaSounds.createSoundClip("combo6", "/bejeweled/speedmatch6.wav");
			DoaSounds.createSoundClip("combo7", "/bejeweled/speedmatch7.wav");
			DoaSounds.createSoundClip("combo8", "/bejeweled/speedmatch8.wav");
			DoaSounds.createSoundClip("combo9", "/bejeweled/speedmatch9.wav");
		} catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
			ex.printStackTrace();
		}
	}
}
