package xonix;

import java.awt.Shape;

import com.doa.engine.DoaHandler;
import com.doa.engine.DoaObject;
import com.doa.engine.graphics.DoaGraphicsContext;
import com.doa.engine.graphics.DoaSprites;

public class PlayArea extends DoaObject {

	private static final long serialVersionUID = 1796099516102218048L;

	public static final Tile[][] TILES = new Tile[Xonix.GRID_X][Xonix.GRID_Y];

	public static boolean gameOver = false;

	public PlayArea(Float x, Float y, Integer width, Integer height, Player player) {
		super(x, y, width, height, DoaObject.FRONT);
		for (int i = 0; i < TILES.length; i++) {
			for (int j = 0; j < TILES[j].length; j++) {
				TILES[i][j] = DoaHandler.instantiateDoaObject(Tile.class, (float) i * Xonix.BLOCK_X, (float) j * Xonix.BLOCK_Y, Xonix.BLOCK_X, Xonix.BLOCK_Y, player);
			}
		}
	}

	@Override
	public void tick() {}

	@Override
	public void render(DoaGraphicsContext g) {
		if (gameOver) {
			g.drawImage(DoaSprites.get("gameover"), 300, 300, null);
		}
	}

	@Override
	public Shape getBounds() {
		return null;
	}

	public static boolean isValidIndex(int i, int j) {
		return i >= 0 && j >= 0 && i < TILES.length && j < TILES[i].length;
	}
}