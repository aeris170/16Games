package bejeweled;

import java.awt.Shape;

import com.doa.engine.DoaHandler;
import com.doa.engine.DoaObject;
import com.doa.engine.graphics.DoaGraphicsContext;
import com.doa.engine.graphics.DoaSprites;
import com.doa.engine.sound.DoaSounds;
import com.doa.maths.DoaVectorF;

public class PlayArea extends DoaObject {

	private static final long serialVersionUID = -7718479497508456363L;

	public static PlayArea INSTANCE;

	private int combo = 0;

	public PlayArea() {
		super(0f, 0f, 0, 0, DoaObject.STATIC_BACK);
		INSTANCE = this;
	}

	@Override
	public synchronized void tick() {
		DoaHandler.getGameObjects().forEach(object -> {
			Tile t = (Tile) object;
			if (Tile.isStable) {
				t.check();
			}
		});
		Tile.markedTiles.forEach(gems -> {
			gems.forEach(gem -> {
				if (!gem.hyper) {
					DoaHandler.remove(gem);
					spawnTopRowOnlyAt(gem.getPosition().x);
				}
			});
			DoaSounds.get("combo" + (combo <= 9 ? combo : 9)).play();
			combo++;
			Tile.isStable = false;
		});
		DoaHandler.getGameObjects().forEach(object -> {
			Tile t = (Tile) object;
			if (t.hyper) {
				t.hypercubeTransform();
			}
		});
		if (Tile.isStable && Tile.markedTiles.isEmpty()) {
			combo = 0;
		}
		Tile.markedTiles.clear();
		Tile.isStable = true;
	}

	@Override
	public void render(DoaGraphicsContext g) {
		g.drawImage(DoaSprites.get("background"), 0, 0, Bejeweled.WINDOW_WIDTH, Bejeweled.WINDOW_HEIGHT, null);
	}

	@Override
	public Shape getBounds() {
		return null;
	}

	public void spawnTopRow() {
		for (int i = 0; i < Bejeweled.GRID_COUNT; i++) {
			DoaHandler.instantiateDoaObject(Tile.class, (float) Bejeweled.X_OFFSET + i * (Bejeweled.BLOCK_SIZE + Bejeweled.MARGIN) + Bejeweled.MARGIN, -25f);
		}
	}

	public void spawnTopRowOnlyAt(float x) {
		float lowestY = Integer.MAX_VALUE;
		for (DoaObject d : DoaHandler.getGameObjects()) {
			DoaVectorF currentPosition = d.getPosition();
			if (currentPosition.x == x) {
				float currentY = currentPosition.y;
				if (lowestY > currentY) {
					lowestY = currentY;
				}
			}
		}
		DoaHandler.instantiateDoaObject(Tile.class, x, lowestY - Bejeweled.BLOCK_SIZE - Bejeweled.MARGIN);
	}
}
