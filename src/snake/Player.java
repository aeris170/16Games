package snake;

import java.awt.Rectangle;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.doa.engine.DoaHandler;
import com.doa.engine.DoaObject;
import com.doa.engine.graphics.DoaGraphicsContext;
import com.doa.engine.graphics.DoaSprites;
import com.doa.engine.input.DoaKeyboard;
import com.doa.engine.input.DoaMouse;
import com.doa.maths.DoaVectorF;

public class Player extends DoaObject {

	private static final long serialVersionUID = 6750275819830013238L;

	private List<DoaVectorF> bodyParts = new CopyOnWriteArrayList<>();
	private int length = 6;
	private Food food;

	public Player(Float x, Float y, Integer width, Integer height, Food food) {
		super(x, y, width, height, DoaObject.FRONT);
		velocity = new DoaVectorF(width, 0);
		for (int i = 0; i < length; i++) {
			bodyParts.add(new DoaVectorF(position.x - i * width, position.y));
		}
		this.food = food;
	}

	public void die() {
		length = 6;
		List<DoaVectorF> newBodyParts = new CopyOnWriteArrayList<>();
		for (int j = 0; j < length; j++) {
			newBodyParts.add(bodyParts.get(j));
		}
		bodyParts = newBodyParts;
		DoaHandler.getGameObjects().removeIf(o -> o instanceof Obstacle);
	}

	public int getLength() {
		return length;
	}

	@Override
	public void tick() {
		if (DoaKeyboard.KEY_UP && velocity.y != height) {
			velocity.x = 0;
			velocity.y = -height;
		}
		if (DoaKeyboard.KEY_DOWN && velocity.y != -height) {
			velocity.x = 0;
			velocity.y = height;
		}
		if (DoaKeyboard.KEY_LEFT && velocity.x != width) {
			velocity.x = -width;
			velocity.y = 0;
		}
		if (DoaKeyboard.KEY_RIGHT && velocity.x != -width) {
			velocity.x = width;
			velocity.y = 0;
		}
		for (int i = 1; i < length; i++) {
			DoaVectorF bodyPart = bodyParts.get(i);
			if (getBounds().contains(bodyPart.x, bodyPart.y)) {
				die();
				break;
			}
		}
		if (food.getBounds().intersects(getBounds()) || DoaMouse.MB1) {
			food.setPosition(findUnoccupiedCoordiate());
			DoaHandler.instantiateDoaObject(Obstacle.class, findUnoccupiedCoordiate(), Snake.BLOCK_X, Snake.BLOCK_Y, this);

			/* WHAT A BODGE, NO NEED TO SET POSITION! LOOP BELOW WILL SET IT BY ITSELF!
			 * WOW! */
			bodyParts.add(length, new DoaVectorF());
			length++;
		}

		if (position.x > (Snake.GRID_X - 1) * Snake.BLOCK_X) {
			position.x = 0;
		}
		if (position.y > (Snake.GRID_Y - 1) * Snake.BLOCK_Y) {
			position.y = 0;
		}
		if (position.x < 0) {
			position.x = (Snake.GRID_X - 1f) * Snake.BLOCK_X;
		}
		if (position.y < 0) {
			position.y = (Snake.GRID_Y - 1f) * Snake.BLOCK_Y;
		}

		for (int i = length - 1; i >= 1; i--) {
			bodyParts.set(i, bodyParts.get(i - 1).clone());
		}
		bodyParts.set(0, position.clone());
		position.add(velocity);
	}

	@Override
	public void render(DoaGraphicsContext g) {
		g.drawImage(DoaSprites.get("green"), position.x, position.y, width, height, null);
		bodyParts.forEach(bodyPart -> g.drawImage(DoaSprites.get("green"), bodyPart.x, bodyPart.y, width, height, null));
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) position.x, (int) position.y, width, height);
	}

	private DoaVectorF findUnoccupiedCoordiate() {
		final float x = (float) ThreadLocalRandom.current().nextInt(Snake.GRID_X) * Snake.BLOCK_X;
		final float y = (float) ThreadLocalRandom.current().nextInt(Snake.GRID_Y) * Snake.BLOCK_Y;
		final DoaVectorF coordinate = new DoaVectorF(x, y);
		if (DoaHandler.getGameObjects().stream().filter(doaObject -> !(doaObject instanceof Block)).anyMatch(doaObject -> doaObject.getPosition().equals(coordinate))
		        || bodyParts.stream().anyMatch(bodyPart -> bodyPart.equals(coordinate))) {
			return findUnoccupiedCoordiate();
		}
		return coordinate;
	}
}
