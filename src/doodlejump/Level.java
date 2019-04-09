package doodlejump;

import java.awt.Shape;

import com.doa.engine.DoaObject;
import com.doa.engine.graphics.DoaGraphicsContext;
import com.doa.engine.graphics.DoaSprites;

public final class Level extends DoaObject {

	public static final String LEVEL_TEXTURE_NAME = "background";

	private static final long serialVersionUID = -1575538202207554612L;

	public Level(Float x, Float y, Integer width, Integer height) {
		super(x, y, width, height, DoaObject.STATIC_BACK);
	}

	@Override
	public void tick() {}

	@Override
	public void render(DoaGraphicsContext g) {
		g.drawImage(DoaSprites.get(LEVEL_TEXTURE_NAME), position.x, position.y, width, height, null);
	}

	@Override
	public Shape getBounds() {
		return null;
	}
}
