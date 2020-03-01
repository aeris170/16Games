package puzzlefifteen;

import java.awt.Dimension;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

import com.doa.engine.DoaEngine;
import com.doa.engine.DoaHandler;
import com.doa.engine.DoaWindow;

public class Puzzle15 {

	public static final int GRID_X = 4;
	public static final int GRID_Y = 4;

	public static final int BLOCK_X = 128;
	public static final int BLOCK_Y = 128;

	public static final int WINDOW_WIDTH = GRID_X * BLOCK_X;
	public static final int WINDOW_HEIGHT = GRID_Y * BLOCK_Y;

	static DoaWindow w;
	static DoaEngine e;

	static int i = 1;

	public static void main(final String[] args) {
		Assets.initializeAssets(i);
		setUpGame();
		w = DoaWindow.createWindow();
		e = new DoaEngine();
		SwingUtilities.invokeLater(() -> configureGUI());
	}

	private static void configureGUI() {
		JMenuBar menuBar = new JMenuBar();
		JMenu optionMenu = new JMenu("Options");
		optionMenu.setMnemonic(KeyEvent.VK_O);
		menuBar.add(optionMenu);
		JMenuItem restartGameItem = new JMenuItem("Restart", KeyEvent.VK_R);
		restartGameItem.addActionListener(e -> setUpGame());
		optionMenu.add(restartGameItem);

		JMenuItem changeTileItem = new JMenuItem("Change tiles", KeyEvent.VK_R);
		changeTileItem.addActionListener(e -> Assets.initializeAssets(++i % 2));
		optionMenu.add(changeTileItem);

		JMenuItem backToAllGameItem = new JMenuItem("Back To All Games", KeyEvent.VK_B);
		optionMenu.add(backToAllGameItem);
		w.setJMenuBar(menuBar);

		w.setTitle("Java Puzzle15!");
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

	private static void setUpGame() {
		Tile.ALL_TILES.clear();
		DoaHandler.clear();
		for (int yy = 0; yy < GRID_Y; yy++) {
			for (int xx = 0; xx < GRID_X; xx++) {
				DoaHandler.instantiateDoaObject(Tile.class, (float) xx * BLOCK_X, (float) yy * BLOCK_Y, BLOCK_X, BLOCK_Y, yy * GRID_X + xx, false);
			}
		}
		Tile.shuffleAll();
		Tile.setMoveables(Tile.ALL_TILES.get(Tile.ALL_TILES.size() - 1));
	}
}