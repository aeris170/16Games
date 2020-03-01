package connectorgui;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.doa.engine.DoaEngine;
import com.doa.engine.DoaWindow;

import tetris.Tetris;

public final class Main {

	static DoaWindow d;

	// update doa engine flushlar!!!!
	public static void main(String[] args) {
		new DoaEngine();
		d = DoaWindow.createWindow();
		SwingUtilities.invokeLater(Main::createAndShowGUI);
	}

	public static void createAndShowGUI() {
		d.setTitle("16 Games by ResaRf");

		JPanel p = new JPanel(new GridLayout(4, 4));

		ImageButton p1 = new ImageButton("/connectorgui/tetris.png");
		ImageButton p2 = new ImageButton("/connectorgui/doodle.png");
		ImageButton p3 = new ImageButton("/connectorgui/arkanoid.png");
		ImageButton p4 = new ImageButton("/connectorgui/snake.png");
		ImageButton p5 = new ImageButton("/connectorgui/minesweeper.png");
		ImageButton p6 = new ImageButton("/connectorgui/puzzle15.png");
		ImageButton p7 = new ImageButton("/connectorgui/carracing.png");
		ImageButton p8 = new ImageButton("/connectorgui/outrun.png");
		ImageButton p9 = new ImageButton("/connectorgui/xonix.png");
		ImageButton p10 = new ImageButton("/connectorgui/bejeweled.png");
		ImageButton p11 = new ImageButton("/connectorgui/tetris.png");
		ImageButton p12 = new ImageButton("/connectorgui/doodle.png");
		ImageButton p13 = new ImageButton("/connectorgui/qmark.png");
		ImageButton p14 = new ImageButton("/connectorgui/qmark.png");
		ImageButton p15 = new ImageButton("/connectorgui/qmark.png");
		ImageButton p16 = new ImageButton("/connectorgui/qmark.png");

		p1.addActionListener(e -> {
			d.remove(p);
			d.setVisible(false);
			Tetris.main(null);
		});

		p.add(p1);
		p.add(p2);
		p.add(p3);
		p.add(p4);
		p.add(p5);
		p.add(p6);
		p.add(p7);
		p.add(p8);
		p.add(p9);
		p.add(p10);
		p.add(p11);
		p.add(p12);
		p.add(p13);
		p.add(p14);
		p.add(p15);
		p.add(p16);

		d.add(p);
		d.setVisible(false);
		d.pack();
		d.setVisible(true);
	}
}
