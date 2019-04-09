package tetris;

import java.awt.Shape;
import java.awt.image.BufferedImage;

import com.doa.engine.DoaObject;
import com.doa.engine.graphics.DoaGraphicsContext;
import com.doa.maths.DoaVectorI;

public class GameField extends DoaObject {

	private static final long serialVersionUID = 1717172959162267389L;

	public static int[][] blockIndices;
	public static BufferedImage[][] textures;
	public static boolean gameOver;

	public GameField() {
		super(0f, 0f);
		blockIndices = new int[20][10];
		textures = new BufferedImage[20][10];
		gameOver = false;
	}

	@Override
	public void tick() {
		for (int i = 0; i < 20; i++) {
			int count = 0;
			for (int j = 0; j < 10; j++) {
				if (blockIndices[i][j] != 0) {
					count++;
				}
			}
			if (count == 10) {
				for (int k = i - 1; k >= 0; k--) {
					for (int j = 0; j < 10; j++) {
						blockIndices[k + 1][j] = blockIndices[k][j];
						textures[k + 1][j] = textures[k][j];
					}
				}
			}
		}
	}

	@Override
	public void render(final DoaGraphicsContext g) {
		for (int i = 0; i < blockIndices.length; i++) {
			for (int j = 0; j < blockIndices[i].length; j++) {
				if (blockIndices[i][j] != 0) {
					g.drawImage(textures[i][j], 54f + j * 36, 63f + i * 36, 36, 36, null);
				}
			}
		}
	}

	@Override
	public Shape getBounds() {
		return null;
	}

	public static boolean check(final Tetronimo tetronimo) {
		final DoaVectorI[] tetroBlocks = tetronimo.getBlocks();
		for (int i = 0; i < tetroBlocks.length; i++) {
			if (blockIndices[tetroBlocks[i].y][tetroBlocks[i].x] != 0) {
				return true;
			}
		}
		return false;
	}

	public static void add(final Tetronimo tetronimo) {
		if (!gameOver) {
			final DoaVectorI[] tetroBlocks = tetronimo.getBlocks();
			for (int i = 0; i < tetroBlocks.length; i++) {
				blockIndices[tetroBlocks[i].y][tetroBlocks[i].x] = 1;
				textures[tetroBlocks[i].y][tetroBlocks[i].x] = tetronimo.getTexture();
			}
			for (int i = 0; i < 10; i++) {
				if (blockIndices[0][i] != 0) {
					gameOver = true;
					break;
				}
			}
		}
	}
}
