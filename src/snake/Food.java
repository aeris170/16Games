package snake;

import java.awt.Rectangle;

import com.doa.engine.DoaObject;
import com.doa.engine.graphics.DoaGraphicsContext;
import com.doa.engine.graphics.DoaSprites;

public class Food extends DoaObject {

	private static final long serialVersionUID = -4788132118305542307L;

	public Food(Float x, Float y, Integer width, Integer height) {
		super(x, y, width, height, DoaObject.FRONT);
	}

	@Override
	public void tick() {}

	@Override
	public void render(DoaGraphicsContext g) {
		g.drawImage(DoaSprites.get("red"), position.x, position.y, width, height);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) position.x, (int) position.y, width, height);
	}

}
