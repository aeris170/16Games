package outrun;

import com.doa.engine.graphics.DoaGraphicsContext;

public class Line {

	public float x, y, z, xx, yy, ww, scale, curve, spriteX, clip;
	public Sprite sprite;

	public void project(int camX, int camY, int camZ) {
		scale = OutrunRenderer.CAMERA_DEPTH / (z - camZ);
		xx = (1 + scale * (x - camX)) * Outrun.WINDOW_WIDTH / 2;
		yy = (1 - scale * (y - camY)) * Outrun.WINDOW_HEIGHT / 2;
		ww = scale * OutrunRenderer.ROAD_WIDTH * Outrun.WINDOW_WIDTH / 2;
	}

	public void drawSprite(DoaGraphicsContext g) {
		Sprite s = sprite;
		if (sprite != null) {
			int w = s.getWidth();
			int h = s.getHeight();
			float destX = xx + scale * spriteX * Outrun.WINDOW_WIDTH / 2;
			float destY = yy + 4;
			float destW = w * ww / 266;
			float destH = h * ww / 266;
			destX += destW * spriteX;
			destY += destH * (-1);
			float clipH = destY + destH - clip;
			if (clipH < 0) {
				clipH = 0;
			}
			if (clipH >= destH || (int) (h - h * clipH / destH) <= 0) {
				return;
			}
			s.setTextureRect(0, 0, w, (int) (h - h * clipH / destH));
			g.drawImage(s.getTextureRect(), destX - 30, destY + 4, (int) (destW), (int) (clip - destY - 3), null);
		}
	}
}
