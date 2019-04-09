package snake;

import java.awt.Shape;

import com.doa.engine.DoaObject;
import com.doa.engine.graphics.DoaGraphicsContext;
import com.doa.engine.graphics.DoaSprites;

public class Block extends DoaObject {

	private static final long serialVersionUID = -7553717488215721701L;

	public Block(Float x, Float y, Integer width, Integer height) {
		super(x, y, width, height);
	}

	@Override
	public void tick() {}

	@Override
	public void render(DoaGraphicsContext g) {
		g.drawImage(DoaSprites.get("white"), position.x, position.y, width, height, null);
	}

	@Override
	public Shape getBounds() {
		return null;
	}
}
