package xonix;

import java.awt.Dimension;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

import com.doa.engine.DoaEngine;
import com.doa.engine.DoaHandler;
import com.doa.engine.DoaWindow;

public final class Xonix {

	public static final int GRID_X = 40;
	public static final int GRID_Y = 25;

	public static final int BLOCK_X = 27;
	public static final int BLOCK_Y = 27;

	public static final int WINDOW_WIDTH = GRID_X * BLOCK_X;
	public static final int WINDOW_HEIGHT = GRID_Y * BLOCK_Y;

	public static final int ENEMY_COUNT = 4;

	static DoaEngine e;
	static DoaWindow w;

	public static void main(String[] args) {
		DoaEngine.TICK_RATE = 100;
		Assets.configureAssets();
		for (int i = 0; i < ENEMY_COUNT; i++) {
			DoaHandler.instantiateDoaObject(Enemy.class, 300f, 300f, 40, 40);
		}
		DoaHandler.instantiateDoaObject(PlayArea.class, 0f, 0f, WINDOW_WIDTH, WINDOW_HEIGHT, DoaHandler.instantiateDoaObject(Player.class, 0f, 0f, BLOCK_X, BLOCK_Y));
		e = new DoaEngine();
		w = DoaWindow.createWindow();
		SwingUtilities.invokeLater(() -> configureGUI());
	}

	private static void configureGUI() {
		JMenuBar menuBar = new JMenuBar();
		JMenu optionMenu = new JMenu("Options");
		optionMenu.setMnemonic(KeyEvent.VK_O);
		menuBar.add(optionMenu);
		JMenuItem backToAllGameItem = new JMenuItem("Back To All Games", KeyEvent.VK_B);
		optionMenu.add(backToAllGameItem);
		w.setJMenuBar(menuBar);

		w.setTitle("Java Xonix!");
		w.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		w.setVisible(true);
		Dimension actualSize = w.getContentPane().getSize();
		int extraW = WINDOW_WIDTH - actualSize.width;
		int extraH = WINDOW_HEIGHT - actualSize.height;
		w.setSize(WINDOW_WIDTH + extraW, WINDOW_HEIGHT + extraH);
		w.setLocation(100, 60);
		w.setResizable(false);
		w.add(e);
	}
}
