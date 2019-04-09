package arkanoid;

import java.awt.Shape;
import java.awt.image.BufferedImage;

import com.doa.engine.DoaObject;
import com.doa.engine.graphics.DoaGraphicsContext;

public class Level extends DoaObject {

	private static final long serialVersionUID = 7476451552795839864L;

	public static final String LEVEL_TEXTURE_NAME = "background";

	private transient BufferedImage texture;// cache it

	public Level(BufferedImage texture) {
		super(0f, 0f, texture.getWidth(), texture.getHeight(), DoaObject.STATIC_BACK);
		this.texture = texture;
	}

	@Override
	public void tick() {}

	@Override
	public void render(DoaGraphicsContext g) {
		g.drawImage(texture, position.x, position.y, width, height, null);
	}

	@Override
	public Shape getBounds() {
		return null;
	}
}
