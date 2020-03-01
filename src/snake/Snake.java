package snake;

import java.awt.Dimension;
import java.awt.event.KeyEvent;

import javax.sound.sampled.Clip;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

import com.doa.engine.DoaEngine;
import com.doa.engine.DoaHandler;
import com.doa.engine.DoaWindow;
import com.doa.engine.sound.DoaSounds;

public class Snake {

	public static final int GRID_X = 40;
	public static final int GRID_Y = 40;

	public static final int BLOCK_X = 16;
	public static final int BLOCK_Y = 16;

	public static final int WINDOW_WIDTH = GRID_X * BLOCK_X;
	public static final int WINDOW_HEIGHT = GRID_Y * BLOCK_Y;

	static DoaWindow w;
	static DoaEngine e;

	public static void main(final String[] args) {
		DoaEngine.TICK_RATE = 16;
		Assets.initializeAssets();
		DoaSounds.setGlobalVolume(0.1f);
		DoaSounds.get("snakeMusic").loop(Clip.LOOP_CONTINUOUSLY);
		PlayArea.createPlayArea(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, GRID_X, GRID_Y, BLOCK_X, BLOCK_Y);
		DoaHandler.instantiateDoaObject(HUD.class,
		        DoaHandler.instantiateDoaObject(Player.class, 160f, 160f, BLOCK_X, BLOCK_Y, DoaHandler.instantiateDoaObject(Food.class, 320f, 320f, BLOCK_X, BLOCK_Y)));
		w = DoaWindow.createWindow();
		e = new DoaEngine();
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

		w.setTitle("Java Snake!");
		w.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		w.setVisible(true);
		Dimension actualSize = w.getContentPane().getSize();
		int extraW = WINDOW_WIDTH - actualSize.width;
		int extraH = WINDOW_HEIGHT - actualSize.height;
		w.setSize(WINDOW_WIDTH + extraW - 1, WINDOW_HEIGHT + extraH - 1);
		w.setLocation(100, 60);
		w.setResizable(false);
		w.add(e);
	}

}
