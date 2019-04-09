package minesweeper;

import java.awt.Dimension;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

import com.doa.engine.DoaEngine;
import com.doa.engine.DoaWindow;

public class MineSweeper {

	public static final int GRID_X = 20;
	public static final int GRID_Y = 20;

	public static final int BLOCK_X = 32;
	public static final int BLOCK_Y = 32;

	public static final int WINDOW_WIDTH = GRID_X * BLOCK_X;
	public static final int WINDOW_HEIGHT = GRID_Y * BLOCK_Y;

	static DoaWindow w;
	static DoaEngine e;

	public static void main(final String[] args) {
		DoaEngine.DEBUG_ENABLED = true;
		Assets.initializeAssets();
		TileCreator.createTiles(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, GRID_X, GRID_Y, BLOCK_X, BLOCK_Y);
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
		restartGameItem.addActionListener(e -> TileCreator.createTiles(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, GRID_X, GRID_Y, BLOCK_X, BLOCK_Y));
		optionMenu.add(restartGameItem);
		JMenuItem backToAllGameItem = new JMenuItem("Back To All Games", KeyEvent.VK_B);
		optionMenu.add(backToAllGameItem);
		w.setJMenuBar(menuBar);

		w.setTitle("Java MineSweeper!");
		w.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		w.setVisible(true);
		Dimension actualSize = w.getContentPane().getSize();
		int extraW = WINDOW_WIDTH - actualSize.width;
		int extraH = WINDOW_HEIGHT - actualSize.height;
		w.setSize(WINDOW_WIDTH + extraW - 10, WINDOW_HEIGHT + extraH - 10);
		w.setLocation(100, 60);
		w.setResizable(false);
		w.add(e);
	}
}