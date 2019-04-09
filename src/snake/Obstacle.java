package snake;

import java.awt.Rectangle;
import java.awt.Shape;

import com.doa.engine.DoaObject;
import com.doa.engine.graphics.DoaGraphicsContext;
import com.doa.engine.graphics.DoaSprites;
import com.doa.maths.DoaVectorF;

public class Obstacle extends DoaObject {

	private static final long serialVersionUID = -8042805809292627452L;

	private Player player;

	public Obstacle(DoaVectorF position, Integer width, Integer height, Player player) {
		super(position, width, height);
		this.player = player;
	}

	@Override
	public void tick() {
		if (getBounds().intersects(player.getBounds())) {
			player.die();
		}
	}

	@Override
	public void render(DoaGraphicsContext g) {
		g.drawImage(DoaSprites.get("black"), position.x, position.y, width, height);
	}

	@Override
	public Shape getBounds() {
		return new Rectangle((int) position.x, (int) position.y, width, height);
	}

}
