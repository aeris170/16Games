package arkanoid;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.doa.engine.DoaHandler;
import com.doa.engine.DoaObject;
import com.doa.engine.graphics.DoaGraphicsContext;
import com.doa.engine.graphics.DoaSprites;
import com.doa.engine.input.DoaKeyboard;
import com.doa.maths.DoaVectorF;

public class Ball extends DoaObject {

	private static final long serialVersionUID = -1774553793627767387L;

	public static Ball ball;

	private transient BufferedImage texture;
	private boolean willChangeDirectionX = false;
	private boolean willChangeDirectionY = false;
	private boolean gameStarted = false;
	private int launchDirection = 1;

	public Ball(BufferedImage texture) {
		super(0f, 0f, 12, 12);
		this.texture = texture;
		ball = this;
	}

	@Override
	public void tick() {
		if (DoaKeyboard.SPACE) {
			gameStarted = true;
			int difficulty = BlockFactory.blockFactory.getDifficulty();
			velocity = new DoaVectorF(difficulty * launchDirection * 2f, difficulty * -3f);
		}
		if (gameStarted) {
			position.add(velocity);
		} else {
			if (DoaKeyboard.KEY_RIGHT) {
				launchDirection = 1;
			}
			if (DoaKeyboard.KEY_LEFT) {
				launchDirection = -1;
			}
			position = new DoaVectorF(Player.player.getPosition().x + (Player.player.getWidth() - width) / 2f, Player.player.getPosition().y - height);
		}
		if (getBounds().intersects(Player.player.getBounds())) {
			willChangeDirectionY = true;
		}
		BlockFactory.BLOCKS.forEach(block -> {
			if (getBounds().intersects(block.getBounds())) {
				if (position.y > block.getPosition().y && position.y < block.getPosition().y + block.getHeight()) {
					willChangeDirectionY = true;
				} else {
					willChangeDirectionX = true;
				}
				return;
			}
		});
		if (position.x < 0 || position.x + width > DoaSprites.get("background").getWidth()) {
			willChangeDirectionX = true;
		}
		if (position.y < 0) {
			willChangeDirectionY = true;
		}
		if (willChangeDirectionX) {
			velocity.x *= -1;
			willChangeDirectionX = false;
		}
		if (willChangeDirectionY) {
			velocity.y *= -1;
			willChangeDirectionY = false;
		}
		if (position.y + height > DoaSprites.get("background").getHeight()) {
			Player.player.loseOneLife();
			if (Player.player.getLives() > 0) {
				DoaHandler.remove(this);
				DoaHandler.instantiateDoaObject(Ball.class, DoaSprites.get("ball"));
			}
		}
	}

	@Override
	public void render(DoaGraphicsContext g) {
		g.drawImage(texture, position.x, position.y, width, height, null);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) position.x, (int) position.y, width, height);
	}

}
