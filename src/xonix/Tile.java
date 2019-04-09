package xonix;

import java.awt.Rectangle;
import java.util.HashSet;
import java.util.Set;

import com.doa.engine.DoaObject;
import com.doa.engine.graphics.DoaGraphicsContext;
import com.doa.engine.graphics.DoaSprites;
import com.doa.maths.DoaVectorI;

public class Tile extends DoaObject {

	private static final long serialVersionUID = 7347119365723730404L;

	private boolean isFilled = false;
	private boolean isTrailed = false;
	private boolean isRecursed = false;
	private Player player;

	public Tile(Float x, Float y, Integer width, Integer height, Player player) {
		super(x, y, width, height);
		if (x == 0 || y == 0 || x == Xonix.WINDOW_WIDTH - Xonix.BLOCK_X || y == Xonix.WINDOW_HEIGHT - Xonix.BLOCK_Y) {
			isFilled = true;
		}
		this.player = player;
	}

	@Override
	public void tick() {
		if (getBounds().contains(player.getBounds()) && !isFilled) {
			isTrailed = true;
		}
	}

	@Override
	public void render(DoaGraphicsContext g) {
		if (isFilled) {
			g.drawImage(DoaSprites.get("area"), position.x, position.y, width, height, null);
		}
		if (isTrailed) {
			g.drawImage(DoaSprites.get("trail"), position.x, position.y, width, height, null);
		}
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) position.x, (int) position.y, width, height);
	}

	public boolean getIsFilled() {
		return isFilled;
	}

	public boolean getIsTrailed() {
		return isTrailed;
	}

	public void setIsFilled(boolean isFilled) {
		this.isFilled = isFilled;
	}

	public void setIsTrailed(boolean isTrailed) {
		this.isTrailed = isTrailed;
	}

	public static DoaVectorI tileToIndex(Tile t) {
		for (int i = 0; i < PlayArea.TILES.length; i++) {
			for (int j = 0; j < PlayArea.TILES[i].length; j++) {
				if (PlayArea.TILES[i][j] == t) {
					return new DoaVectorI(i, j);
				}
			}
		}
		return new DoaVectorI(Integer.MAX_VALUE, Integer.MAX_VALUE);
	}

	@SuppressWarnings("unchecked")
	public static Set<Tile> findSmallestAreaAround(Tile t) {
		Set<Tile>[] areas = new Set[4];
		for (int i = 0; i < areas.length; i++) {
			areas[i] = new HashSet<>();
		}
		DoaVectorI index = tileToIndex(t);
		findAreaRecursingFrom(index.x - 1, index.y - 1, areas[0]);
		areas[0].forEach(tile -> tile.isRecursed = false);
		findAreaRecursingFrom(index.x + 1, index.y - 1, areas[1]);
		areas[1].forEach(tile -> tile.isRecursed = false);
		findAreaRecursingFrom(index.x + 1, index.y + 1, areas[2]);
		areas[2].forEach(tile -> tile.isRecursed = false);
		findAreaRecursingFrom(index.x - 1, index.y + 1, areas[3]);
		areas[3].forEach(tile -> tile.isRecursed = false);
		Set<Tile> areaToFill = areas[0];
		for (Set<Tile> area : areas) {
			if (area.contains(null)) {
				area.remove(null);
			}
			if (areaToFill.isEmpty() || (!area.isEmpty() && area.size() < areaToFill.size())) {
				areaToFill = area;
			}
		}
		return areaToFill;
	}

	private static void findAreaRecursingFrom(int i, int j, Set<Tile> area) {
		if (!PlayArea.isValidIndex(i, j)) {
			return;
		}
		if (PlayArea.TILES[i][j].isFilled || PlayArea.TILES[i][j].isTrailed) {
			return;
		}
		if (PlayArea.TILES[i][j].isRecursed) {
			return;
		}
		PlayArea.TILES[i][j].isRecursed = true;
		area.add(PlayArea.TILES[i][j]);
		findAreaRecursingFrom(i - 1, j, area);
		findAreaRecursingFrom(i, j - 1, area);
		findAreaRecursingFrom(i + 1, j, area);
		findAreaRecursingFrom(i, j + 1, area);
	}
}
