package tetris;

import java.awt.Shape;
import java.awt.image.BufferedImage;

import com.doa.engine.DoaObject;
import com.doa.engine.graphics.DoaGraphicsContext;
import com.doa.engine.graphics.DoaSprites;
import com.doa.engine.input.DoaKeyboard;
import com.doa.engine.task.DoaTaskGuard;
import com.doa.engine.task.DoaTasker;
import com.doa.maths.DoaVectorI;

public class Tetronimo extends DoaObject {

	private static final long serialVersionUID = 9175486546853286721L;

	private static final int[][] FIGURES = { { 1, 3, 5, 7 }, /* I */
	        { 2, 4, 5, 7 }, /* Z */
	        { 3, 5, 4, 6 }, /* S */
	        { 3, 5, 4, 7 }, /* T */
	        { 2, 3, 5, 7 }, /* L */
	        { 3, 5, 7, 6 }, /* J */
	        { 2, 3, 4, 5 }, /* O */ };

	private DoaVectorI[] blocks = new DoaVectorI[4];
	private transient BufferedImage texture;

	private int dx = 0;
	private DoaTaskGuard rotate = new DoaTaskGuard(true);
	private DoaTaskGuard move = new DoaTaskGuard(true);
	private DoaTaskGuard gravity = new DoaTaskGuard(true);
	private boolean done = false;

	public Tetronimo(final Integer fig, final Integer texture, final Integer x) {
		super(0f, 0f);
		this.texture = DoaSprites.get("" + texture);
		for (int i = 0; i < 4; i++) {
			blocks[i] = new DoaVectorI(FIGURES[fig][i] % 2 + x, FIGURES[fig][i] / 2);
		}
	}

	private boolean checkBottom() {
		for (int i = 0; i < 4; i++) {
			if (blocks[i].y >= 19) {
				return true;
			}
		}
		return false;
	}

	private boolean checkSides() {
		for (int i = 0; i < 4; i++) {
			if (blocks[i].x < 0 || blocks[i].x >= 10) {
				return true;
			}
		}
		return false;
	}

	private boolean checkLeft() {
		for (int i = 0; i < 4; i++) {
			if (blocks[i].x < 0) {
				return true;
			}
		}
		return false;
	}

	private boolean checkRight() {
		for (int i = 0; i < 4; i++) {
			if (blocks[i].x >= 10) {
				return true;
			}
		}
		return false;
	}

	public DoaVectorI[] getBlocks() {
		return blocks;
	}

	@Override
	public void tick() {
		if (!done) {
			if (DoaKeyboard.KEY_UP && rotate.get()) {
				DoaTasker.guard(rotate, 200);
				final DoaVectorI[] backup = new DoaVectorI[4];
				for (int i = 0; i < 4; i++) {
					backup[i] = blocks[i].clone();
				}
				final DoaVectorI centerOfRotation = blocks[1];
				for (int i = 0; i < 4; i++) {
					final int x = blocks[i].y - centerOfRotation.y;
					final int y = blocks[i].x - centerOfRotation.x;
					blocks[i].x = centerOfRotation.x - x;
					blocks[i].y = centerOfRotation.y + y;
				}
				while (checkLeft()) {
					for (int i = 0; i < 4; i++) {
						blocks[i].x++;
					}
				}
				while (checkRight()) {
					for (int i = 0; i < 4; i++) {
						blocks[i].x--;
					}
				}
				if (GameField.check(this)) {
					blocks = backup;
				}
			}
			if (DoaKeyboard.KEY_LEFT && move.get()) {
				DoaTasker.guard(move, 100);
				dx = -1;
			} else if (DoaKeyboard.KEY_RIGHT && move.get()) {
				DoaTasker.guard(move, 100);
				dx = 1;
			}
			int gravitySpeed = 0;
			if (DoaKeyboard.KEY_DOWN) {
				gravitySpeed = 100;
			} else {
				gravitySpeed = 300;
			}
			final DoaVectorI[] backup = new DoaVectorI[4];
			for (int i = 0; i < 4; i++) {
				backup[i] = blocks[i].clone();
			}
			for (int i = 0; i < 4; i++) {
				blocks[i].x += dx;
			}
			if (checkSides()) {
				for (int i = 0; i < 4; i++) {
					blocks[i].x -= dx;
				}
			}
			if (GameField.check(this)) {
				blocks = backup;
			}
			dx = 0;
			if (gravity.get()) {
				DoaTasker.guard(gravity, gravitySpeed);
				for (int i = 0; i < 4; i++) {
					blocks[i].y += 1;
				}
				if (GameField.check(this)) {
					done = true;
					for (int i = 0; i < 4; i++) {
						blocks[i].y -= 1;
					}
					GameField.add(this);
					TetronimoFactory.createRandomTetronimo();
				} else if (checkBottom()) {
					done = true;
					GameField.add(this);
					TetronimoFactory.createRandomTetronimo();
				}
			}
		}
	}

	@Override
	public void render(final DoaGraphicsContext g) {
		if (!done) {
			for (int i = 0; i < 4; i++) {
				g.drawImage(texture, 54f + blocks[i].x * 36, 63f + blocks[i].y * 36, 36, 36, null);
			}
		}
	}

	@Override
	public Shape getBounds() {
		return null;
	}

	public BufferedImage getTexture() {
		return texture;
	}
}
