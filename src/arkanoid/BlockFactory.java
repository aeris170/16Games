package arkanoid;

import java.awt.Color;
import java.awt.Shape;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.doa.engine.DoaHandler;
import com.doa.engine.DoaObject;
import com.doa.engine.graphics.DoaGraphicsContext;
import com.doa.engine.graphics.DoaLights;
import com.doa.engine.graphics.DoaSprites;
import com.doa.maths.DoaVectorF;

public class BlockFactory extends DoaObject {

	private static final long serialVersionUID = -2498353177135085846L;

	public static final List<Block> BLOCKS = new CopyOnWriteArrayList<>();

	public static BlockFactory blockFactory;

	private int level = 0;

	public BlockFactory() {
		super(0f, 0f, 0, 0);
		spawnBlocks();
		blockFactory = this;
	}

	private void spawnBlocks() {
		level++;
		float randomFloat = ThreadLocalRandom.current().nextFloat();
		float hue = randomFloat - (int) randomFloat;
		DoaLights.ambientLight(Color.getHSBColor(hue, 1f, 1f));
		for (int i = 0; i < 13; i++) {
			for (int j = 12; j >= 3 - level; j--) {
				BLOCKS.add(DoaHandler.instantiateDoaObject(Block.class, 25f + i * 43, 30f + j * 20, DoaSprites.get("block")));
			}
		}
		if (Ball.ball != null) {
			DoaVectorF ballDirection = Ball.ball.getVelocity().clone().normalise();
			Ball.ball.setVelocity(new DoaVectorF(ballDirection.x * level * 2f, ballDirection.y * level * 3f));
		}
		if (Player.player != null) {
			Player.player.setVelocity(new DoaVectorF(level * 4f, 0f));
			Player.player.gainOneLife();
		}
	}

	@Override
	public void tick() {
		if (BLOCKS.isEmpty()) {
			spawnBlocks();
		}
	}

	@Override
	public void render(DoaGraphicsContext g) {}

	@Override
	public Shape getBounds() {
		return null;
	}

	public int getDifficulty() {
		return level;
	}
}
