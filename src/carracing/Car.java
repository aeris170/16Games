package carracing;

import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import com.doa.engine.DoaObject;
import com.doa.engine.graphics.DoaGraphicsContext;
import com.doa.engine.graphics.DoaSprites;
import com.doa.engine.input.DoaKeyboard;

public class Car extends DoaObject {

	private static final long serialVersionUID = -6390069790694852194L;

	public static final List<Car> ALL_CARS = new ArrayList<>();
	public static int COUNT = 0;

	private static final int CHECKPOINT_COUNT = 8;
	private static final int[][] POINTS = new int[][] { { 300, 610 }, { 1270, 430 }, { 1380, 2380 }, { 1900, 2460 }, { 1970, 1700 }, { 2550, 1680 }, { 2560, 3150 },
	        { 500, 3300 } };
	private static float maxSpeed = 6f;
	private static float acceleration = 0.1f;
	private static float deceleration = 0.15f;
	private static float turnSpeed = 0.04f;

	private float speed = 2;
	private float angle = 0;
	private int n = 0;
	private int i = COUNT % 6;
	private boolean playerControlled = false;

	public Car(Float x, Float y, Integer width, Integer height, Float speed, Boolean playerControlled) {
		super(x, y, width, height);
		this.speed = speed;
		this.playerControlled = playerControlled;
		COUNT++;
		ALL_CARS.add(this);
	}

	@Override
	public void tick() {
		position.x += Math.sin(angle) * speed;
		position.y -= Math.cos(angle) * speed;
		if (playerControlled) {
			boolean up = false;
			boolean right = false;
			boolean down = false;
			boolean left = false;
			if (DoaKeyboard.KEY_UP) {
				up = true;
			}
			if (DoaKeyboard.KEY_RIGHT) {
				right = true;
			}
			if (DoaKeyboard.KEY_DOWN) {
				down = true;
			}
			if (DoaKeyboard.KEY_LEFT) {
				left = true;
			}
			if (up && speed < maxSpeed) {
				if (speed < 0) {
					speed += deceleration;
				} else {
					speed += acceleration;
				}
			}
			if (down && speed > -maxSpeed) {
				if (speed > 0) {
					speed -= deceleration;
				} else {
					speed -= acceleration;
				}
			}
			if (!up && !down) {
				if (speed - deceleration > 0) {
					speed -= deceleration;
				} else if (speed + deceleration < 0) {
					speed += deceleration;
				} else {
					speed = 0;
				}
			}
			if (right && speed != 0) {
				angle += turnSpeed * speed / maxSpeed;
			}
			if (left && speed != 0) {
				angle -= turnSpeed * speed / maxSpeed;
			}
		} else {
			float tx = POINTS[n][0];
			float ty = POINTS[n][1];
			float beta = (float) (angle - Math.atan2(tx - position.x, -ty + position.y));
			if (Math.sin(beta) < 0)
				angle += 0.005 * speed;
			else
				angle -= 0.005 * speed;
			if ((position.x - tx) * (position.x - tx) + (position.y - ty) * (position.y - ty) < 25 * 25) {
				n = (n + 1) % CHECKPOINT_COUNT;
			}
		}
		for (int j = 0; j < ALL_CARS.size(); j++) {
			int dx = 0, dy = 0;
			while (dx * dx + dy * dy < DoaSprites.get("car0").getWidth() * DoaSprites.get("car0").getHeight()) {
				ALL_CARS.get(j).position.x += dx / 10.0;
				ALL_CARS.get(j).position.x += dy / 10.0;
				this.position.x -= dx / 10.0;
				this.position.y -= dy / 10.0;
				dx = (int) (ALL_CARS.get(j).position.x - this.position.x);
				dy = (int) (ALL_CARS.get(j).position.y - this.position.y);
				if (dx == 0 && dy == 0) {
					break;
				}
			}
		}
	}

	@Override
	public void render(DoaGraphicsContext g) {
		AffineTransform oldTransform = g.getTransform();
		g.translate(position.x + width / 2f, position.y + height / 2f);
		g.rotate(angle);
		g.translate(-position.x - width / 2f, -position.y - height / 2f);
		g.drawImage(DoaSprites.get("car" + i), position.x, position.y, width, height, null);
		g.setTransform(oldTransform);
	}

	@Override
	public Rectangle getBounds() {
		return null;
	}
}
