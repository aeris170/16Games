package arkanoid;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.prefs.Preferences;

import com.doa.engine.DoaObject;
import com.doa.engine.graphics.DoaGraphicsContext;
import com.doa.engine.graphics.DoaSprites;

public class HUD extends DoaObject {

	private static final long serialVersionUID = -378391587063565954L;

	private static final String GAME_OVER_STRING = "GAME OVER!";
	public static final Font UI_FONT = new Font("Georgia", 1, Font.BOLD);

	private static final Preferences prefs = Preferences.userNodeForPackage(HUD.class);

	public static HUD hud;

	private int score;
	private int highLevel; // retrieve from node
	private int highScore; // retrieve from node
	private boolean isSavedToNode = false;

	public HUD() {
		super(0f, 0f, DoaSprites.get(Level.LEVEL_TEXTURE_NAME).getWidth(), DoaSprites.get(Level.LEVEL_TEXTURE_NAME).getHeight(), DoaObject.STATIC_FRONT);
		hud = this;
		highLevel = prefs.getInt("arkanoidHighLevel", 0);
		highScore = prefs.getInt("arkanoidHighScore", 0);
	}

	public void increaseScoreBy(int value) {
		score += value;
	}

	@Override
	public void tick() {}

	@Override
	public void render(DoaGraphicsContext g) {
		if (Player.player.getLives() <= 0) {
			if (!isSavedToNode) {
				highScore = score;
				highLevel = BlockFactory.blockFactory.getDifficulty();
				prefs.putInt("arkanoidHighLevel", highLevel);
				prefs.putInt("arkanoidHighScore", highScore);
				isSavedToNode = true;
			}
			g.setFont(UI_FONT.deriveFont(60f));
			g.turnOnLightContribution();
			g.setColor(Color.YELLOW);
			g.turnOffLightContribution();

			FontMetrics fm = g.getFontMetrics();
			Rectangle2D r = fm.getStringBounds(GAME_OVER_STRING, g.create());
			int x = (this.getWidth() - (int) r.getWidth()) / 2;
			int y = (this.getHeight() - (int) r.getHeight()) / 2 + fm.getAscent();

			g.drawString(GAME_OVER_STRING, x, y);
		}
		for (int i = 0; i < Player.player.getLives(); i++) {
			g.drawImage(DoaSprites.ORIGINAL_SPRITES.get(Player.PLAYER_TEXTURE_NAME), i * (Player.player.getWidth() / 3f + 2f), 0, Player.player.getWidth() / 3f,
			        Player.player.getHeight() / 2f);
		}
		g.setFont(UI_FONT.deriveFont(14f));
		g.setColor(Color.WHITE);
		g.drawString("LEVEL: " + BlockFactory.blockFactory.getDifficulty(), 10, Arkanoid.WINDOW_HEIGHT - 24f);
		g.drawString("SCORE: " + score, 10, Arkanoid.WINDOW_HEIGHT - 10f);

		FontMetrics fm = g.getFontMetrics();
		Rectangle2D hl = fm.getStringBounds("HI-LEVEL: " + highLevel, g.create());
		Rectangle2D hs = fm.getStringBounds("HI-SCORE: " + highScore, g.create());
		g.drawString("HI-LEVEL: " + highLevel, Arkanoid.WINDOW_WIDTH - hl.getWidth() - 10, Arkanoid.WINDOW_HEIGHT - 24f);
		g.drawString("HI-SCORE: " + highScore, Arkanoid.WINDOW_WIDTH - hs.getWidth() - 10, Arkanoid.WINDOW_HEIGHT - 10f);
	}

	@Override
	public Shape getBounds() {
		return null;
	}
}