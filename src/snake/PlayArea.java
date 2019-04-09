package snake;

import com.doa.engine.DoaHandler;

public final class PlayArea {

	private PlayArea() {}

	public static void createPlayArea(int startX, int startY, int endX, int endY, int gridX, int gridY, int blockX, int blockY) {
		for (int i = startX; i < endX && i < gridX * blockX; i += blockX) {
			for (int j = startY; j < endY && j < gridY * blockY; j += blockY) {
				DoaHandler.instantiateDoaObject(Block.class, (float) i, (float) j, blockX, blockY);
			}
		}
	}
}
