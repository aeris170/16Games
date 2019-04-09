package xonix;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.doa.engine.DoaObject;
import com.doa.engine.graphics.DoaGraphicsContext;
import com.doa.engine.graphics.DoaSprites;
import com.doa.maths.DoaVectorF;

public class Enemy extends DoaObject {

	private static final long serialVersionUID = -6323844141547613267L;

	public static final List<Enemy> ALL_ENEMIES = new ArrayList<>();

	private static final int BASE_SPEED = 6;
	private static final int RANDOMNESS = 12;

	private double angle = 0;
	private double deltaAngle;

	public Enemy(Float x, Float y, Integer width, Integer height) {
		super(x, y, width, height, DoaObject.FRONT);
		velocity = new DoaVectorF(BASE_SPEED - (float) Math.random() * RANDOMNESS, BASE_SPEED - (float) Math.random() * RANDOMNESS);
		deltaAngle = velocity.normSquared() / 70;
		ALL_ENEMIES.add(this);
	}

	@Override
	public void tick() {
		if (!PlayArea.gameOver) {
			position.x += velocity.x;
			for (Tile[] ta : PlayArea.TILES) {
				for (Tile t : ta) {
					if (t.getIsFilled() && t.getBounds().intersects(getBounds())) {
						velocity.x *= -1;
						position.x += velocity.x;
					}
				}
			}
			position.y += velocity.y;
			for (Tile[] ta : PlayArea.TILES) {
				for (Tile t : ta) {
					if (t.getIsFilled() && t.getBounds().intersects(getBounds())) {
						velocity.y *= -1;
						position.y += velocity.y;
					}
				}
			}
			angle += deltaAngle;
		}
	}

	@Override
	public void render(DoaGraphicsContext g) {
		AffineTransform oldTransform = g.getTransform();
		g.translate(position.x + width / 2d, position.y + height / 2d);
		g.rotate(angle);
		g.translate(-position.x - width / 2d, -position.y - height / 2d);
		g.drawImage(DoaSprites.get("enemy"), position.x, position.y, width, height, null);
		g.setColor(Color.YELLOW);
		g.draw(getBounds());
		g.setTransform(oldTransform);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) position.x + 12, (int) position.y + 12, width - 24, height - 24);
	}

	public static boolean checkIfItsAppropriateToFill(Set<Tile> areaToFill) {
		for (Enemy e : ALL_ENEMIES) {
			for (Tile t : areaToFill) {
				if (t == PlayArea.TILES[(int) (e.position.x / 27)][(int) (e.position.y / 27)]) {
					return false;
				}
			}
		}
		return true;
	}
}
