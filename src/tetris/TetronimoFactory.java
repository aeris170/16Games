package tetris;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.doa.engine.DoaHandler;

public final class TetronimoFactory {

	private static int counter = 0;
	private static int[] randomBlockIndices;
	private static final Random r = new Random();

	private TetronimoFactory() {}

	public static void createRandomTetronimo() {
		if (counter == 0) {
			randomBlockIndices = new int[] { 0, 1, 2, 3, 4, 5, 6 };
			final List<Integer> randomList = new ArrayList<>();
			for (final int i : randomBlockIndices) {
				randomList.add(i);
			}
			Collections.shuffle(randomList);
			randomBlockIndices = randomList.stream().mapToInt(i -> i).toArray();
		}
		createRandomTetronimo(randomBlockIndices[counter], r.nextInt(8), r.nextInt(9));
		counter++;
		if (counter == 6) {
			counter = 0;
		}
	}

	private static Tetronimo createRandomTetronimo(final int figureIndex, final int textureIndex, final int x) {
		return DoaHandler.instantiateDoaObject(Tetronimo.class, figureIndex, textureIndex, x);
	}
}
