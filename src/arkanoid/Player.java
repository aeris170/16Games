package arkanoid;

import java.awt.Rectangle;

import com.doa.engine.DoaObject;
import com.doa.engine.graphics.DoaGraphicsContext;
import com.doa.engine.graphics.DoaSprites;
import com.doa.engine.input.DoaKeyboard;
import com.doa.maths.DoaMath;
import com.doa.maths.DoaVectorF;

public class Player extends DoaObject {

	private static final long serialVersionUID = -9201035636727755415L;

	public static final String PLAYER_TEXTURE_NAME = "paddle";

	public static Player player;

	private int lives = 3;

	public Player(Float x, Float y) {
		super(x, y, DoaSprites.get(PLAYER_TEXTURE_NAME).getWidth(), DoaSprites.get(PLAYER_TEXTURE_NAME).getHeight());
		velocity = new DoaVectorF(BlockFactory.blockFactory.getDifficulty() * 4f, 0f);
		player = this;
	}

	public int getLives() {
		return lives;
	}

	public void gainOneLife() {
		lives++;
	}

	public void loseOneLife() {
		lives--;
	}

	@Override
	public void tick() {
		if (DoaKeyboard.KEY_RIGHT) {
			position.x += velocity.x;
		}
		if (DoaKeyboard.KEY_LEFT) {
			position.x -= velocity.x;
		}
		position.x = DoaMath.clamp(position.x, 0, (float) DoaSprites.get(Level.LEVEL_TEXTURE_NAME).getWidth() - DoaSprites.get(PLAYER_TEXTURE_NAME).getWidth());
	}

	@Override
	public void render(DoaGraphicsContext g) {
		g.drawImage(DoaSprites.get(PLAYER_TEXTURE_NAME), position.x, position.y, width, height, null);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) position.x, (int) position.y, width, height);
	}
}
