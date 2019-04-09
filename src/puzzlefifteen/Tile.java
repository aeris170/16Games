package puzzlefifteen;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.doa.engine.DoaObject;
import com.doa.engine.graphics.DoaGraphicsContext;
import com.doa.engine.graphics.DoaSprites;
import com.doa.engine.input.DoaMouse;
import com.doa.engine.task.DoaTaskGuard;
import com.doa.engine.task.DoaTasker;
import com.doa.maths.DoaVectorF;

public class Tile extends DoaObject {

	private static final long serialVersionUID = -1002163948596571239L;

	public static final List<Tile> ALL_TILES = new ArrayList<>();

	private static int animationFrameCount = 64;
	private static DoaTaskGuard animationGuard = new DoaTaskGuard(true);

	private int number;
	private boolean isMoveable;

	public Tile(Float x, Float y, Integer width, Integer height, Integer number, Boolean isMoveable) {
		super(x, y, width, height);
		if (number == 15) {
			super.zOrder = DoaObject.BACK;
		}
		this.number = number + 1;
		this.isMoveable = isMoveable;
		ALL_TILES.add(this);
	}

	public static void shuffleAll() {
		for (int i = 0; i < 100; i++) {
			Tile t1 = ALL_TILES.get(ThreadLocalRandom.current().nextInt(16));
			Tile t2 = ALL_TILES.get(ThreadLocalRandom.current().nextInt(16));
			if (t1 != t2) {
				DoaVectorF tmp = t1.position.clone();
				t1.position = t2.position.clone();
				t2.position = tmp;
			}
		}
	}

	public static void setMoveables(Tile zeroTile) {
		ALL_TILES.forEach(tile -> tile.isMoveable = false);
		zeroTile.isMoveable = true;
		Tile up = ALL_TILES.stream().filter(tile -> tile.position.x == zeroTile.position.x && tile.position.y == zeroTile.position.y - zeroTile.height).findAny()
		        .orElse(zeroTile);
		Tile down = ALL_TILES.stream().filter(tile -> tile.position.x == zeroTile.position.x && tile.position.y == zeroTile.position.y + zeroTile.height).findAny()
		        .orElse(zeroTile);
		Tile left = ALL_TILES.stream().filter(tile -> tile.position.y == zeroTile.position.y && tile.position.x == zeroTile.position.x - zeroTile.width).findAny()
		        .orElse(zeroTile);
		Tile right = ALL_TILES.stream().filter(tile -> tile.position.y == zeroTile.position.y && tile.position.x == zeroTile.position.x + zeroTile.width).findAny()
		        .orElse(zeroTile);
		up.isMoveable = true;
		down.isMoveable = true;
		left.isMoveable = true;
		right.isMoveable = true;
	}

	@Override
	public void tick() {
		if (animationGuard.get() && isMoveable && getBounds().contains(DoaMouse.X, DoaMouse.Y) && DoaMouse.MB1) {
			DoaTasker.guardExecution(() -> {
				Tile zeroTile = ALL_TILES.stream().filter(tile -> tile.number == 16).findAny().get();
				DoaVectorF thisPosition = position;
				int stepAmountX = (int) (zeroTile.position.x - thisPosition.x) / animationFrameCount;
				int stepAmountY = (int) (zeroTile.position.y - thisPosition.y) / animationFrameCount;
				zeroTile.position = thisPosition.clone();
				for (int i = 0; i < animationFrameCount; i++) {
					DoaTasker.executeLater(() -> {
						thisPosition.x += stepAmountX;
						thisPosition.y += stepAmountY;
					}, i * 10);
				}
				DoaTasker.executeLater(() -> setMoveables(zeroTile), animationFrameCount * 10);
			}, animationGuard, animationFrameCount * 10);
		}
	}

	@Override
	public void render(DoaGraphicsContext g) {
		if (number == 16) {
			g.drawImage(DoaSprites.get("" + (number - 1)), 0, 0, Puzzle15.WINDOW_WIDTH, Puzzle15.WINDOW_HEIGHT, null);
		} else {
			g.drawImage(DoaSprites.get("" + (number - 1)), position.x, position.y, width, height, null);
		}
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) position.x, (int) position.y, width, height);
	}

	public int getNumber() {
		return number;
	}

	public boolean isMoveable() {
		return isMoveable;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public void setMoveable(boolean newValue) {
		this.isMoveable = newValue;
	}
}
