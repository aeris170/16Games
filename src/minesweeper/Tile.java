package minesweeper;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.doa.engine.DoaObject;
import com.doa.engine.graphics.DoaGraphicsContext;
import com.doa.engine.graphics.DoaSprites;
import com.doa.engine.input.DoaKeyboard;
import com.doa.engine.input.DoaMouse;
import com.doa.engine.task.DoaTaskGuard;
import com.doa.engine.task.DoaTasker;

public class Tile extends DoaObject {

	private static final long serialVersionUID = -7641257913617594077L;

	private static final List<Tile> ALL_TILES = new ArrayList<>();
	private static boolean isGameOver = false;

	private int adjacentMineCount = 0;
	private boolean isMine = false;
	private boolean isFlagged = false;
	private boolean isRevealed = false;

	private List<Tile> adjacentTiles = new ArrayList<>();
	private boolean recursionGuardFlag = false;
	private DoaTaskGuard flagCooldownGuard = new DoaTaskGuard(true);

	public Tile(Float x, Float y, Integer width, Integer height, Boolean isMine) {
		super(x, y, width, height);
		this.isMine = isMine;
		ALL_TILES.add(this);
	}

	public static void setAdjacentMineCountForAllTiles() {
		for (int i = 0; i < ALL_TILES.size(); i++) {
			Tile thisTile = ALL_TILES.get(i);
			for (int j = 0; j < ALL_TILES.size(); j++) {
				if (i != j) {
					Tile candidateAdjacentTile = ALL_TILES.get(j);
					double distance = Point2D.distance(thisTile.position.x, thisTile.position.y, candidateAdjacentTile.position.x, candidateAdjacentTile.position.y);
					if (distance == thisTile.width || distance == thisTile.height
					        || distance == Math.sqrt((double) thisTile.width * thisTile.width + thisTile.height * thisTile.height)) {
						thisTile.adjacentTiles.add(candidateAdjacentTile);
						if (candidateAdjacentTile.isMine) {
							thisTile.adjacentMineCount++;
						}
					}
				}
			}
		}
	}

	public static void clearTiles() {
		isGameOver = false;
		ALL_TILES.clear();
	}

	@Override
	public void tick() {
		if (!isGameOver && getBounds().contains(DoaMouse.X, DoaMouse.Y)) {
			if (DoaMouse.MB1 && !isRevealed) {
				isRevealed = true;
				if (isMine) {
					isGameOver = true;
					ALL_TILES.forEach(tile -> tile.isRevealed = true);
				} else if (adjacentMineCount == 0) {
					floodFill(this);
					ALL_TILES.forEach(tile -> tile.recursionGuardFlag = false);
				}
			} else if (flagCooldownGuard.get() && DoaMouse.MB3_HOLD) {
				DoaTasker.guard(flagCooldownGuard, 200);
				isFlagged = !isFlagged;
			}
		}
		if (DoaKeyboard.Q) {
			ALL_TILES.forEach(tile -> tile.isRevealed = true);
		} else if (DoaKeyboard.W) {
			ALL_TILES.forEach(tile -> tile.isRevealed = false);
		}
	}

	@Override
	public void render(DoaGraphicsContext g) {
		if (isRevealed) {
			if (isMine) {
				g.drawImage(DoaSprites.get("mine"), position.x, position.y, width, height, null);
			} else {
				switch (adjacentMineCount) {
					case 0:
						g.drawImage(DoaSprites.get("openTile"), position.x, position.y, width, height, null);
						break;
					case 1:
						g.drawImage(DoaSprites.get("1tile"), position.x, position.y, width, height, null);
						break;
					case 2:
						g.drawImage(DoaSprites.get("2tile"), position.x, position.y, width, height, null);
						break;
					case 3:
						g.drawImage(DoaSprites.get("3tile"), position.x, position.y, width, height, null);
						break;
					case 4:
						g.drawImage(DoaSprites.get("4tile"), position.x, position.y, width, height, null);
						break;
					case 5:
						g.drawImage(DoaSprites.get("5tile"), position.x, position.y, width, height, null);
						break;
					case 6:
						g.drawImage(DoaSprites.get("6tile"), position.x, position.y, width, height, null);
						break;
					case 7:
						g.drawImage(DoaSprites.get("7tile"), position.x, position.y, width, height, null);
						break;
					case 8:
						g.drawImage(DoaSprites.get("8tile"), position.x, position.y, width, height, null);
						break;
					default:
						break;
				}
			}
		} else {
			if (isFlagged) {
				g.drawImage(DoaSprites.get("flagTile"), position.x, position.y, width, height, null);
			} else {
				g.drawImage(DoaSprites.get("closedTile"), position.x, position.y, width, height, null);
			}
		}
		if (getBounds().contains(DoaMouse.X, DoaMouse.Y)) {
			g.setStroke(new BasicStroke(2));
			g.setColor(Color.RED);
			g.drawRect(position.x + 1, position.y + 1, width - 3f, height - 3f);
		}
	}

	@Override
	public Shape getBounds() {
		return new Rectangle((int) position.x + 1, (int) position.y + 1, width - 2, height - 2);
	}

	private static void floodFill(Tile origin) {
		for (int i = 0; i < origin.adjacentTiles.size(); i++) {
			Tile currentTile = origin.adjacentTiles.get(i);
			currentTile.isRevealed = true;
			if (!currentTile.recursionGuardFlag && currentTile.adjacentMineCount == 0) {
				currentTile.recursionGuardFlag = true;
				floodFill(currentTile);
			}
		}
	}
}