package bejeweled;

import java.awt.Rectangle;
import java.util.HashSet;
import java.util.Set;

import com.doa.engine.DoaHandler;
import com.doa.engine.DoaObject;
import com.doa.engine.graphics.DoaAnimations;
import com.doa.engine.graphics.DoaGraphicsContext;
import com.doa.engine.graphics.DoaSprites;
import com.doa.engine.input.DoaMouse;
import com.doa.engine.sound.DoaSounds;
import com.doa.engine.task.DoaTasker;
import com.doa.maths.DoaVectorF;

public class Tile extends DoaObject {

	private static final long serialVersionUID = -1340894845732943571L;

	private static boolean animationIsNotPlaying = true;
	private static Tile previouslySelectedTile;
	public static boolean isStable;
	public static Set<Set<Tile>> markedTiles = new HashSet<>();
	private static boolean isInitalFallComplete = false;

	GemType type;
	boolean mark;
	boolean hover;
	int animIndex = 0;
	boolean anim = false;
	boolean hyper = false;
	boolean gemhitSoundPlayed = false;
	boolean hoverSoundPlayed = false;

	public Tile(Float x, Float y) {
		super(x, y, Bejeweled.BLOCK_SIZE, Bejeweled.BLOCK_SIZE);
		type = Utils.randomEnum(GemType.class);
	}

	@Override
	public void tick() {
		if (animationIsNotPlaying) {
			if (getBounds().contains(DoaMouse.X, DoaMouse.Y) && DoaMouse.MB3) {
				type = GemType.RED;
			}
			if (getBounds().contains(DoaMouse.X, DoaMouse.Y) && DoaMouse.MB1) {
				if (previouslySelectedTile == null) {
					previouslySelectedTile = this;
				} else if (previouslySelectedTile == this) {
					previouslySelectedTile = null;
				} else {
					swapWith(previouslySelectedTile);
					previouslySelectedTile = null;
				}
			}
			if (DoaHandler.getGameObjects().stream().noneMatch(tile -> position.y < tile.getPosition().y && getBounds().intersects((Rectangle) tile.getBounds()))) {
				float oldY = position.y;
				position.y++;
				if (!isInitalFallComplete) {
					position.y++;
				}
				if (position.y > Bejeweled.Y_MAX) {
					position.y = Bejeweled.Y_MAX;
					isInitalFallComplete = true;
				}
				if (oldY != position.y) {
					isStable = false;
					gemhitSoundPlayed = false;
				}
			} else if (!gemhitSoundPlayed && isInitalFallComplete) {
				DoaSounds.get("gemhit").loop(0);
				gemhitSoundPlayed = true;
			}
		}

		if (getBoundsBig().contains(DoaMouse.X, DoaMouse.Y)) {
			hover = true;
			if (!hoverSoundPlayed) {
				// DoaSounds.get("hover").play();
				hoverSoundPlayed = true;
			}
		} else {
			hover = false;
			hoverSoundPlayed = false;
		}
	}

	@Override
	public void render(DoaGraphicsContext g) {
		if (position.y > 0) {
			if (hover || anim || previouslySelectedTile == this || type == GemType.HYPER) {
				g.drawAnimation(DoaAnimations.get(type.toString()), position.x + Bejeweled.PADDING, position.y + Bejeweled.PADDING, width - Bejeweled.PADDING * 2f,
				        height - Bejeweled.PADDING * 2f);
			} else {
				g.drawImage(DoaAnimations.get(type.toString()).getFrames().get(0), position.x + Bejeweled.PADDING, position.y + Bejeweled.PADDING, width - Bejeweled.PADDING * 2f,
				        height - Bejeweled.PADDING * 2f);
			}
		}
		if (previouslySelectedTile == this) {
			// render hover, because select on top of hover will create a purple glow.
			g.drawImage(DoaSprites.get("hover"), position.x, position.y - Bejeweled.MARGIN, width, height + Bejeweled.MARGIN * 3.5f);
			g.drawImage(DoaSprites.get("select"), position.x, position.y - Bejeweled.MARGIN, width, height + Bejeweled.MARGIN * 3.5f);
		} else if (hover) {
			g.drawImage(DoaSprites.get("hover"), position.x, position.y - Bejeweled.MARGIN, width, height + Bejeweled.MARGIN * 3.5f);
		}
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) position.x + 10, (int) position.y - Bejeweled.MARGIN, width - 20, height + Bejeweled.MARGIN * 2 - 1);
	}

	public Rectangle getBoundsBig() {
		return new Rectangle((int) position.x, (int) position.y, width, height);
	}

	private enum GemType {
		BLUE, GREEN, ORANGE, PINK, RED, WHITE, YELLOW, HYPER;
	}

	public void swapWith(Tile t) {
		DoaVectorF p = position.clone();
		DoaVectorF tp = t.position.clone();
		int xDistance = (int) (position.x - t.position.x);
		int yDistance = (int) (position.y - t.position.y);
		// adjacency check
		if (Math.abs(xDistance + yDistance) != Bejeweled.BLOCK_SIZE + Bejeweled.MARGIN) {
			return;
		}
		int granularity = Bejeweled.BLOCK_SIZE + Bejeweled.MARGIN;
		animationIsNotPlaying = false;
		anim = true;
		t.anim = true;
		for (int i = 0; i < Math.abs(xDistance + yDistance); i++) {
			DoaTasker.executeLater(() -> {
				position.x -= (double) xDistance / granularity;
				t.position.x += (double) xDistance / granularity;
				position.y -= (double) yDistance / granularity;
				t.position.y += (double) yDistance / granularity;
			}, i * 5L);
		}
		DoaTasker.executeLater(() -> {
			t.position = p;
			position = tp;
			DoaVectorF pp = position.clone();
			DoaVectorF tpp = t.position.clone();
			animationIsNotPlaying = true;
			anim = false;
			t.anim = false;
			t.check();
			check();
			if (!mark && !t.mark) {
				if (type == GemType.HYPER || t.type == GemType.HYPER) {
					return;
				}
				DoaSounds.get("badMove").loop(0);
				animationIsNotPlaying = false;
				anim = true;
				t.anim = true;
				for (int i = 0; i < Math.abs(xDistance + yDistance); i++) {
					DoaTasker.executeLater(() -> {
						position.x += (double) xDistance / granularity;
						t.position.x -= (double) xDistance / granularity;
						position.y += (double) yDistance / granularity;
						t.position.y -= (double) yDistance / granularity;
					}, i * 5L);
				}
				DoaTasker.executeLater(() -> {
					t.position = pp;
					position = tpp;
					animationIsNotPlaying = true;
					anim = false;
					t.anim = false;
				}, Math.abs(xDistance + yDistance) * 5L);
			}
			mark = false;
			t.mark = false;
		}, Math.abs(xDistance + yDistance) * 5L);
	}

	public void check() {
		Set<Tile> udGems = checkUD();
		Set<Tile> rlGems = checkRL();
		if (hyper) {
			udGems.remove(this);
			rlGems.remove(this);
		}
		if (udGems.size() > 2) {
			markedTiles.add(udGems);
		}
		if (rlGems.size() > 2) {
			markedTiles.add(rlGems);
		}
	}

	public Set<Tile> checkUD() {
		Set<Tile> gems = new HashSet<>();
		gems.add(this);
		if (position.y > 0) {
			Tile up = up();
			Tile down = down();
			int upCount = 0;
			int downCount = 0;
			while (up != null && up.type == type) {
				gems.add(up);
				up = up.up();
				upCount++;
			}
			while (down != null && down.type == type) {
				gems.add(down);
				down = down.down();
				downCount++;
			}
			if (upCount + downCount >= 2) {
				mark = true;
				gems.forEach(gem -> gem.mark = true);
				if (upCount + downCount >= 4) {
					if (((upCount + downCount) % 2 == 0 && upCount == downCount) || ((upCount + downCount) % 2 != 0 && upCount + 1 == downCount)) {
						hyper = true;
					}
				}
			}
		}
		return gems;
	}

	public Set<Tile> checkRL() {
		Set<Tile> gems = new HashSet<>();
		gems.add(this);
		if (position.y > 0) {
			Tile right = right();
			Tile left = left();
			int rightCount = 0;
			int leftCount = 0;
			while (right != null && right.type == type) {
				gems.add(right);
				right = right.right();
				rightCount++;
			}
			while (left != null && left.type == type) {
				gems.add(left);
				left = left.left();
				leftCount++;
			}
			if (rightCount + leftCount >= 2) {
				mark = true;
				gems.forEach(gem -> gem.mark = true);
				if (rightCount + leftCount >= 4) {
					if (((rightCount + leftCount) % 2 == 0 && rightCount == leftCount) || ((rightCount + leftCount) % 2 != 0 && rightCount + 1 == leftCount)) {
						hyper = true;
					}
				}
			}
		}
		return gems;
	}

	public void hypercubeTransform() {
		type = GemType.HYPER;
		hyper = false;
		DoaSounds.get("hypercreate").loop(0);
	}

	private Tile left() {
		return (Tile) DoaHandler.getGameObjects().stream()
		        .filter(tile -> tile.getPosition().y == position.y && tile.getPosition().x == position.x - Bejeweled.BLOCK_SIZE - Bejeweled.MARGIN).findFirst().orElse(null);
	}

	private Tile right() {
		return (Tile) DoaHandler.getGameObjects().stream()
		        .filter(tile -> tile.getPosition().y == position.y && tile.getPosition().x == position.x + Bejeweled.BLOCK_SIZE + Bejeweled.MARGIN).findFirst().orElse(null);
	}

	private Tile up() {
		return (Tile) DoaHandler.getGameObjects().stream()
		        .filter(tile -> tile.getPosition().y > 0 && tile.getPosition().x == position.x && tile.getPosition().y == position.y - Bejeweled.BLOCK_SIZE - Bejeweled.MARGIN)
		        .findFirst().orElse(null);
	}

	private Tile down() {
		return (Tile) DoaHandler.getGameObjects().stream()
		        .filter(tile -> tile.getPosition().x == position.x && tile.getPosition().y == position.y + Bejeweled.BLOCK_SIZE + Bejeweled.MARGIN).findFirst().orElse(null);
	}
}
