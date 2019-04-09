package outrun;

import java.awt.Color;
import java.awt.Shape;
import java.util.List;

import com.doa.engine.DoaObject;
import com.doa.engine.graphics.DoaGraphicsContext;
import com.doa.engine.graphics.DoaSprites;
import com.doa.engine.input.DoaKeyboard;

public class OutrunRenderer extends DoaObject {

	private static final long serialVersionUID = -5626665119282445110L;

	private static final Color GRASS1 = new Color(16, 200, 16);
	private static final Color GRASS2 = new Color(0, 154, 0);
	private static final Color RUMBLE1 = new Color(255, 255, 255);
	private static final Color RUMBLE2 = new Color(255, 0, 0);
	private static final Color ROAD1 = new Color(107, 107, 107);
	private static final Color ROAD2 = new Color(105, 105, 105);

	private static final float SPEED = 200;
	private static List<Line> lines = new CircularArrayList<>();

	public static final int ROAD_WIDTH = 2000;
	public static final int SEGMENT_LENGTH = 200;
	public static final float CAMERA_DEPTH = 0.84f;
	public static float xTransform = 0;

	int zPosition = 0;
	int xPosition = 0;

	public OutrunRenderer() {
		super(null);
		for (int i = 0; i < 1600; i++) {
			Line l = new Line();
			l.z = (float) i * SEGMENT_LENGTH;
			if (i > 300 && i < 700) {
				l.curve = 0.5f;
			}
			if (i > 1100) {
				l.curve = -0.7f;
			}
			if (i < 300 && i % 20 == 0) {
				l.spriteX = -1.5f;
				l.sprite = new Sprite(DoaSprites.get("tree5"));
			}
			if (i % 17 == 0) {
				l.spriteX = 1.0f;
				l.sprite = new Sprite(DoaSprites.get("bush"));
			}
			if (i > 300 && i % 20 == 0) {
				l.spriteX = -0.7f;
				l.sprite = new Sprite(DoaSprites.get("tree4"));
			}
			if (i > 800 && i % 20 == 0) {
				l.spriteX = -1.2f;
				l.sprite = new Sprite(DoaSprites.get("tree1"));
			}
			if (i == 400) {
				l.spriteX = -1.4f;
				l.sprite = new Sprite(DoaSprites.get("shop"));
			}
			if (i > 750) {
				l.y = (float) (Math.sin(i / 30.0) * i * 2.35);
			}
			lines.add(l);
		}
	}

	@Override
	public void tick() {
		if (DoaKeyboard.KEY_UP) {
			zPosition += SPEED;
		}
		if (DoaKeyboard.KEY_DOWN) {
			zPosition -= SPEED;
		}
		if (DoaKeyboard.KEY_RIGHT) {
			xPosition += SPEED / 3.5;
		}
		if (DoaKeyboard.KEY_LEFT) {
			xPosition -= SPEED / 3.5;
		}
		while (zPosition >= lines.size() * SEGMENT_LENGTH) {
			zPosition -= lines.size() * SEGMENT_LENGTH;
		}
		while (zPosition < 0) {
			zPosition += lines.size() * SEGMENT_LENGTH;
		}
	}

	@Override
	public void render(DoaGraphicsContext g) {
		g.drawImage(DoaSprites.get("bg"), -640 + xTransform / 250, 0, null);
		g.drawImage(DoaSprites.get("bg"), 128 + xTransform / 250, 0, null);
		g.drawImage(DoaSprites.get("bg"), 896 + xTransform / 250, 0, null);
		int startPos = zPosition / SEGMENT_LENGTH;
		int cameraHeight = (int) (1500 + lines.get(startPos).y);
		xTransform = 0;
		float dx = 0;
		int maxy = Outrun.WINDOW_HEIGHT;
		for (int i = startPos; i < startPos + 300; i++) {
			Line l = lines.get(i);
			l.project((int) (xPosition - xTransform), cameraHeight, zPosition - (i >= lines.size() ? lines.size() * SEGMENT_LENGTH : 0));
			xTransform += dx;
			dx += l.curve;
			l.clip = maxy;
			if (l.yy >= maxy) {
				continue;
			}
			maxy = (int) l.yy;
			Line p = lines.get(i - 1);
			drawSegment(g, p.xx, p.yy, p.ww, l.xx, l.yy, l.ww, (i / 3) % 2 == 0);
		}
		for (int i = startPos + 300; i > startPos; i--) {
			lines.get(i).drawSprite(g);
		}
	}

	@Override
	public Shape getBounds() {
		return null;
	}

	private static void drawSegment(DoaGraphicsContext g, double x1, double y1, double w1, double x2, double y2, double w2, boolean tone) {
		if (tone) {
			drawColoredQuad(g, GRASS1, 0, y1, Outrun.WINDOW_WIDTH, 0, y2, Outrun.WINDOW_WIDTH);
			drawColoredQuad(g, RUMBLE1, x1, y1, w1 * 1.2, x2, y2, w2 * 1.2);
			drawColoredQuad(g, ROAD1, x1, y1, w1, x2, y2, w2);
		} else {
			drawColoredQuad(g, GRASS2, 0, y1, Outrun.WINDOW_WIDTH, 0, y2, Outrun.WINDOW_WIDTH);
			drawColoredQuad(g, RUMBLE2, x1, y1, w1 * 1.2, x2, y2, w2 * 1.2);
			drawColoredQuad(g, ROAD2, x1, y1, w1, x2, y2, w2);
		}
	}

	private static void drawColoredQuad(DoaGraphicsContext g, Color c, double x1, double y1, double w1, double x2, double y2, double w2) {
		g.setColor(c);
		double[] xPoints = new double[] { x1 - w1, x2 - w2, x2 + w2, x1 + w1 };
		double[] yPoints = new double[] { y1, y2, y2, y1 };
		g.fillPolygon(xPoints, yPoints, 4);
	}

}
