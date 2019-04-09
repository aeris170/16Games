package doodlejump;

import java.awt.Color;
import java.awt.Rectangle;

import com.doa.engine.DoaObject;
import com.doa.engine.graphics.DoaGraphicsContext;
import com.doa.engine.graphics.DoaSprites;
import com.doa.engine.input.DoaKeyboard;

public class Player extends DoaObject {

	private static final long serialVersionUID = 8943775908559878685L;

	public static final String PLAYER_TEXTURE_NAME = "doodle";

	private int orientation;

	public Player(Float x, Float y) {
		super(x, y, DoaSprites.get(PLAYER_TEXTURE_NAME).getWidth(), DoaSprites.get(PLAYER_TEXTURE_NAME).getHeight());
		orientation = 0;
	}

	@Override
	public void tick() {
		velocity.y += 0.01;
		PlatformCreator.PLATFORMS.forEach(platform -> {
			if (platform.getBounds().intersects(getBounds()) && velocity.y > 0) {
				velocity.y = -1.5f - velocity.y;
			}
		});
		if (DoaKeyboard.KEY_RIGHT) {
			position.x += 2;
			orientation = 0;
		}
		if (DoaKeyboard.KEY_LEFT) {
			position.x -= 2;
			orientation = 1;
		}
		position.add(velocity);
	}

	@Override
	public void render(DoaGraphicsContext g) {
		g.setColor(Color.GREEN);
		if (orientation == 0) {
			g.drawImage(DoaSprites.get(PLAYER_TEXTURE_NAME), position.x + width, position.y, -width, height, null);
		} else if (orientation == 1) {
			g.drawImage(DoaSprites.get(PLAYER_TEXTURE_NAME), position.x, position.y, width, height, null);
		}
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) (position.x + width * 0.2), (int) (position.y + height * 0.9), (int) (width * 0.6), (int) (height * 0.1));
	}

}
