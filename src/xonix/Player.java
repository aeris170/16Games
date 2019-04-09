package xonix;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.HashSet;
import java.util.Set;

import com.doa.engine.DoaObject;
import com.doa.engine.graphics.DoaGraphicsContext;
import com.doa.engine.graphics.DoaSprites;
import com.doa.engine.input.DoaKeyboard;
import com.doa.engine.task.DoaTaskGuard;
import com.doa.engine.task.DoaTasker;

public class Player extends DoaObject {

	private static final long serialVersionUID = -7679868735377370001L;

	private boolean trailing;
	private Tile currentTile;
	private DoaTaskGuard movementCooldown = new DoaTaskGuard(true);
	private Set<Tile> trailingTiles = new HashSet<>();

	public Player(Float x, Float y, Integer width, Integer height) {
		super(x, y, width, height, DoaObject.FRONT);
	}

	@Override
	public void tick() {
		Enemy.ALL_ENEMIES.forEach(enemy -> enemy.tick());
		if (!PlayArea.gameOver) {
			int keysIndex = 0;
			String[] keys = new String[4];
			velocity.x = 0;
			velocity.y = 0;
			if (DoaKeyboard.KEY_UP && velocity.y != height) {
				keys[keysIndex++] = "UP";
			}
			if (DoaKeyboard.KEY_DOWN && velocity.y != -height) {
				keys[keysIndex++] = "DOWN";
			}
			if (DoaKeyboard.KEY_LEFT && velocity.x != width) {
				keys[keysIndex++] = "LEFT";
			}
			if (DoaKeyboard.KEY_RIGHT && velocity.x != -width) {
				keys[keysIndex] = "RIGHT";
			}
			int lastPressedKeyIndex = -1;
			for (int i = keys.length - 1; i >= 0; i--) {
				if (keys[i] != null) {
					lastPressedKeyIndex = i;
					break;
				}
			}
			if (lastPressedKeyIndex != -1) {
				if (keys[lastPressedKeyIndex].equals("UP")) {
					velocity.y = -Xonix.BLOCK_Y;
				} else if (keys[lastPressedKeyIndex].equals("DOWN")) {
					velocity.y = +Xonix.BLOCK_Y;
				} else if (keys[lastPressedKeyIndex].equals("LEFT")) {
					velocity.x = -Xonix.BLOCK_X;
				} else if (keys[lastPressedKeyIndex].equals("RIGHT")) {
					velocity.x = +Xonix.BLOCK_X;
				}
			}
			if (movementCooldown.get()) {
				DoaTasker.guard(movementCooldown, 100);
				position.add(velocity);
				if (trailing) {
					trailingTiles.forEach(t -> {
						if (getBounds().intersects(t.getBounds()) && velocity.normSquared() != 0) {
							PlayArea.gameOver = true;
						}
					});
				}
			}
			if (trailing) {
				trailingTiles.forEach(t -> {
					for (Enemy e : Enemy.ALL_ENEMIES) {
						if (e.getBounds().intersects(t.getBounds())) {
							PlayArea.gameOver = true;
						}
					}
				});
			}
			if (position.x < 0) {
				position.x = 0;
			} else if (position.x > Xonix.WINDOW_WIDTH - Xonix.BLOCK_X) {
				position.x = (float) Xonix.WINDOW_WIDTH - Xonix.BLOCK_X;
			}
			if (position.y < 0) {
				position.y = 0;
			} else if (position.y > Xonix.WINDOW_HEIGHT - Xonix.BLOCK_Y) {
				position.y = (float) Xonix.WINDOW_HEIGHT - Xonix.BLOCK_Y;
			}
			boolean trailingIsFinished = false;
			for (int i = 0; i < PlayArea.TILES.length; i++) {
				for (int j = 0; j < PlayArea.TILES[i].length; j++) {
					Tile t = PlayArea.TILES[i][j];
					if (getBounds().intersects(t.getBounds())) {
						currentTile = t;
						break;
					}
				}
			}
			if (!trailing && !currentTile.getIsFilled()) {
				trailing = true;
			}
			if (trailing) {
				currentTile.setIsTrailed(true);
				trailingTiles.add(currentTile);
				if (currentTile.getIsFilled()) {
					trailing = false;
					trailingIsFinished = true;
					trailingTiles.clear();
				}
			}
			if (trailingIsFinished) {
				Set<Tile> areaToFill = Tile.findSmallestAreaAround(currentTile);
				boolean ok = Enemy.checkIfItsAppropriateToFill(areaToFill);
				if (ok) {
					for (Tile t : areaToFill) {
						t.setIsFilled(true);
					}
				}
				for (Tile[] ta : PlayArea.TILES) {
					for (Tile t : ta) {
						if (t.getIsTrailed()) {
							t.setIsTrailed(false);
							t.setIsFilled(true);
						}
					}
				}
			}
		}
	}

	@Override
	public void render(DoaGraphicsContext g) {
		Enemy.ALL_ENEMIES.forEach(enemy -> enemy.render(g));
		g.drawImage(DoaSprites.get("player"), position.x, position.y, width, height, null);
		for (int i = 0; i < 6; i++) {
			g.setColor(new Color(255, 255, 0, 20));
			g.fillOval(position.x - i - 3, position.y - i - 3, width + 2 * i + 5d, height + 2 * i + 5d);
		}
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) position.x, (int) position.y, width, height);
	}
}
