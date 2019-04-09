package bejeweled;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

import com.doa.engine.DoaEngine;
import com.doa.engine.DoaHandler;
import com.doa.engine.DoaWindow;
import com.doa.engine.graphics.DoaLights;
import com.doa.engine.task.DoaTasker;

public final class Bejeweled {

	public static final int GRID_COUNT = 8;

	public static final int WINDOW_WIDTH = 1360;
	public static final int WINDOW_HEIGHT = 768;
	public static final int X_OFFSET = 568;
	public static final int Y_OFFSET = 55;
	public static final int BLOCK_SIZE = 80;
	public static final int PADDING = 10;
	public static final int MARGIN = 2;
	public static final int Y_MAX = 631;

	static DoaEngine e;
	static DoaWindow w;
	static PlayArea pa;

	// TODO NO MORE MOVES IMPLEMENTATION!!!
	public static void main(String[] args) {
		DoaEngine.TICK_RATE = 240;
		DoaLights.ambientLight(new Color(200, 200, 200));
		DoaEngine.MULTI_THREAD_ENABLED = false;
		Assets.configureAssets();
		pa = DoaHandler.instantiateDoaObject(PlayArea.class);
		e = new DoaEngine();
		w = DoaWindow.createWindow();
		SwingUtilities.invokeLater(() -> {
			configureGUI();
			for (int i = 0; i < GRID_COUNT; i++) {
				DoaTasker.executeLater(() -> pa.spawnTopRow(), i * 100L);
			}
		});
	}

	private static void configureGUI() {
		JMenuBar menuBar = new JMenuBar();
		JMenu optionMenu = new JMenu("Options");
		optionMenu.setMnemonic(KeyEvent.VK_O);
		menuBar.add(optionMenu);
		JMenuItem backToAllGameItem = new JMenuItem("Back To All Games", KeyEvent.VK_B);
		optionMenu.add(backToAllGameItem);
		w.setJMenuBar(menuBar);

		w.setTitle("Java Bejeweled!");
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
