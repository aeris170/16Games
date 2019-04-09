package carracing;

import java.awt.Shape;
import java.awt.geom.AffineTransform;

import com.doa.engine.DoaObject;
import com.doa.engine.graphics.DoaGraphicsContext;
import com.doa.engine.graphics.DoaSprites;

public final class Level extends DoaObject {

	private static final long serialVersionUID = -281665566540465171L;

	public Level() {
		super(0f, 0f, DoaObject.BACK);
	}

	@Override
	public void tick() {}

	@Override
	public void render(DoaGraphicsContext g) {
		AffineTransform oldTransform = g.getTransform();
		g.scale(2, 2);
		g.drawImage(DoaSprites.get("background"), position.x, position.y, null);
		g.setTransform(oldTransform);
	}

	@Override
	public Shape getBounds() {
		return null;
	}
}
