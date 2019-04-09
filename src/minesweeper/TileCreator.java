package minesweeper;

import java.util.concurrent.ThreadLocalRandom;

import com.doa.engine.DoaHandler;

public class TileCreator {

	private TileCreator() {}

	public static void createTiles(int startX, int startY, int endX, int endY, int gridX, int gridY, int blockX, int blockY) {
		Tile.clearTiles();
		for (int i = startX; i < endX && i < gridX * blockX; i += blockX) {
			for (int j = startY; j < endY && j < gridY * blockY; j += blockY) {
				DoaHandler.instantiateDoaObject(Tile.class, (float) i, (float) j, blockX, blockY, ThreadLocalRandom.current().nextInt(100) < 10);
			}
		}
		Tile.setAdjacentMineCountForAllTiles();
	}
}
