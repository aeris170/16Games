package outrun;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Sprite extends BufferedImage {

	private int x1, y1, x2, y2;

	public Sprite(BufferedImage bf) {
		super(bf.getWidth(), bf.getHeight(), bf.getType());
		Graphics g = this.createGraphics();
		g.drawImage(bf, 0, 0, null);
		g.dispose();
		x1 = 0;
		y1 = 0;
		x2 = this.getWidth();
		y2 = this.getHeight();
	}

	public void setTextureRect(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	public BufferedImage getTextureRect() {
		return this.getSubimage(x1, y1, x2, y2);
	}
}
