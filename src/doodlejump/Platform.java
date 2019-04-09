package doodlejump;

import java.awt.Rectangle;

import com.doa.engine.DoaObject;
import com.doa.engine.graphics.DoaGraphicsContext;
import com.doa.engine.graphics.DoaSprites;

public class Platform extends DoaObject {

	private static final long serialVersionUID = -3721135616667783178L;

	public static final String PLATFORM_TEXTURE_NAME = "platform";

	public Platform(Float x, Float y) {
		super(x, y, DoaSprites.get(PLATFORM_TEXTURE_NAME).getWidth(), DoaSprites.get(PLATFORM_TEXTURE_NAME).getHeight());
	}

	@Override
	public void tick() {}

	@Override
	public void render(DoaGraphicsContext g) {
		g.drawImage(DoaSprites.get(PLATFORM_TEXTURE_NAME), position.x, position.y, width, height, null);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) position.x, (int) position.y, width, height);
	}

}
