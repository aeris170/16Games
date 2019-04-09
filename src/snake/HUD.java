package snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Shape;
import java.util.prefs.Preferences;

import com.doa.engine.DoaObject;
import com.doa.engine.graphics.DoaGraphicsContext;

public class HUD extends DoaObject {

	private static final long serialVersionUID = 7146311505255668493L;

	private static final Font HUD_FONT = new Font("Georgia", Font.BOLD, 24);
	private static final String HI_SCORE = "HI-SCORE: ";
	private static final String SCORE = "SCORE: ";
	private static final Preferences prefs = Preferences.userNodeForPackage(HUD.class);

	private Player player;
	private int currentScore;
	private int tickCount = 0;
	private int playerLastLength = 6;
	private int highscore; // retrieve from node

	public HUD(Player player) {
		super(0f, 0f, DoaObject.STATIC_FRONT);
		this.player = player;
		highscore = prefs.getInt("snake-highscore", 0);
	}

	@Override
	public void tick() {
		if (tickCount % 16 == 0) {
			currentScore++;
		}
		tickCount++;
		int playerLength = player.getLength();
		if (playerLastLength != playerLength) {
			currentScore += (player.getLength() - playerLastLength) * 100;
			playerLastLength = playerLength;
			if (playerLength == 6) {
				currentScore = 0;
			}
		}
		if (currentScore > highscore) {
			highscore = currentScore; // save to node
			prefs.putInt("snake-highscore", highscore);
		}
	}

	@Override
	public void render(DoaGraphicsContext g) {
		g.setFont(HUD_FONT);
		int x = (int) (Snake.WINDOW_WIDTH - g.getFontMetrics().getStringBounds(HI_SCORE + highscore, g.create()).getWidth()) - 4;
		int y = Snake.WINDOW_HEIGHT - g.getFontMetrics().getHeight() / 4;
		drawOutline(HI_SCORE + highscore, x, y, g);
		drawOutline(SCORE + currentScore, 2, y, g);
		g.setColor(Color.getHSBColor(currentScore % 100 / 100f, 1, 1));
		g.drawString(HI_SCORE + highscore, x, y);
		g.drawString(SCORE + currentScore, 2, y);
	}

	@Override
	public Shape getBounds() {
		return null;
	}

	private static void drawOutline(String s, int x, int y, DoaGraphicsContext g) {
		g.drawString(s, x, y - 2f);
		g.drawString(s, x, y + 2f);
		g.drawString(s, x - 2f, y);
		g.drawString(s, x + 2f, y);
		g.drawString(s, x, y - 1f);
		g.drawString(s, x, y + 1f);
		g.drawString(s, x - 1f, y);
		g.drawString(s, x + 1f, y);
	}
}
