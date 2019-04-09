package arkanoid;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.doa.engine.DoaHandler;
import com.doa.engine.DoaObject;
import com.doa.engine.graphics.DoaGraphicsContext;

public class Block extends DoaObject {

	private static final long serialVersionUID = -5344712579384257480L;

	private transient BufferedImage texture;
	private boolean processed;

	public Block(Float x, Float y, BufferedImage texture) {
		super(x, y, texture.getWidth(), texture.getHeight());
		this.texture = texture;
	}

	@Override
	public void tick() {
		if (getBounds().intersects(Ball.ball.getBounds())) {
			HUD.hud.increaseScoreBy(BlockFactory.blockFactory.getDifficulty() * 10);
			BlockFactory.BLOCKS.remove(this);
			DoaHandler.getGameObjects().remove(this);
		}
	}

	@Override
	public void render(DoaGraphicsContext g) {
		g.drawImage(texture, position.x, position.y, width, height, null);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) (position.x), (int) (position.y), width, height);
	}

	public boolean getProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}
}
