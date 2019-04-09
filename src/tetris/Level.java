package tetris;

import java.awt.Color;
import java.awt.Font;
import java.awt.Shape;

import com.doa.engine.DoaObject;
import com.doa.engine.graphics.DoaGraphicsContext;
import com.doa.engine.graphics.DoaSprites;

public class Level extends DoaObject {

	private static final long serialVersionUID = -1224989122516639378L;

	public Level(Float x, Float y, Integer width, Integer height) {
		super(x, y, width, height);
		super.setWidth(width);
		System.out.println(position.x + " " + position.y + " " + super.width + " " + super.height);
		new Frame(x, y, width, height);
	}

	@Override
	public void tick() {}

	@Override
	public void render(final DoaGraphicsContext g) {
		g.drawImage(DoaSprites.get("background"), position.x, position.y, width, height, null);
	}

	@Override
	public Shape getBounds() {
		return null;
	}

	private class Frame extends DoaObject {

		private static final long serialVersionUID = -2101436312918309261L;

		private static final String GAME = "GAME";
		private static final String OVER = "OVER!";

		public Frame(float x, float y, int width, int height) {
			super(x, y, width, height, DoaObject.FRONT);
		}

		@Override
		public void tick() {}

		@Override
		public void render(DoaGraphicsContext g) {
			g.drawImage(DoaSprites.get("frame"), position.x, position.y, width, height, null);
			if (GameField.gameOver) {
				g.setFont(new Font("Comic Sans MS", Font.BOLD, 160));
				g.setColor(Color.BLACK);
				g.drawString(GAME, 64, 399);
				g.drawString(GAME, 64, 401);
				g.drawString(GAME, 66, 399);
				g.drawString(GAME, 66, 401);
				g.drawString(OVER, 64, 549);
				g.drawString(OVER, 64, 551);
				g.drawString(OVER, 66, 549);
				g.drawString(OVER, 66, 551);
				g.setColor(Color.YELLOW);
				g.drawString(GAME, 65, 400);
				g.drawString(OVER, 65, 550);
			}
		}

		@Override
		public Shape getBounds() {
			return null;
		}
	}
}
