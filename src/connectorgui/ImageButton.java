package connectorgui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;

/* https://stackoverflow.com/questions/299495/how-to-add-an-image-to-a-jpanel */
public class ImageButton extends JButton {

	private static final long serialVersionUID = 5427359140123253969L;

	private transient BufferedImage image;

	public ImageButton(String path) {
		setMinimumSize(new Dimension(200, 200));
		setPreferredSize(new Dimension(200, 200));
		setMaximumSize(new Dimension(200, 200));

		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()));

		try {
			image = ImageIO.read(ImageButton.class.getResourceAsStream(path));
		} catch (IOException ex) {
			System.out.println("Cannot find image at" + path);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, 200, 200, this);
	}

}