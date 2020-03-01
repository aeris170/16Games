package tetris;

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

import connectorgui.Main;

public class Tetris {

	public static final int WINDOW_WIDTH = 640;
	public static final int WINDOW_HEIGHT = 960;

	static DoaWindow w;
	static DoaEngine e;

	public static void main(final String[] args) {
		Assets.initializeAssets();
		DoaSounds.setGlobalVolume(0.1f);
		DoaSounds.get("tetrisMusic").loop(Clip.LOOP_CONTINUOUSLY);
		DoaHandler.instantiateDoaObject(Level.class, 0f, 0f, WINDOW_WIDTH, WINDOW_HEIGHT);
		DoaHandler.instantiateDoaObject(GameField.class);
		w = DoaWindow.getInstance();
		e = DoaEngine.getInstance();
		SwingUtilities.invokeLater(Tetris::configureGUI);
		TetronimoFactory.createRandomTetronimo();
	}

	private static void configureGUI() {
		JMenuBar menuBar = new JMenuBar();
		JMenu optionMenu = new JMenu("Options");
		optionMenu.setMnemonic(KeyEvent.VK_O);
		menuBar.add(optionMenu);
		JMenuItem restartGameItem = new JMenuItem("Restart", KeyEvent.VK_R);
		restartGameItem.addActionListener(e -> {
			DoaHandler.clear();
			DoaHandler.instantiateDoaObject(Level.class, 0f, 0f, WINDOW_WIDTH, WINDOW_HEIGHT);
			DoaHandler.instantiateDoaObject(GameField.class);
			TetronimoFactory.createRandomTetronimo();
		});
		optionMenu.add(restartGameItem);
		JMenuItem backToAllGameItem = new JMenuItem("Back To All Games", KeyEvent.VK_B);
		backToAllGameItem.addActionListener(e -> {
			DoaHandler.clear();
			w.removeAll();
			Main.createAndShowGUI();
		});
		optionMenu.add(backToAllGameItem);
		w.setJMenuBar(menuBar);
		w.setTitle("Java Tetris!");
		w.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		Dimension actualSize = w.getContentPane().getSize();
		int extraW = WINDOW_WIDTH - actualSize.width;
		int extraH = WINDOW_HEIGHT - actualSize.height;
		w.getContentPane().setSize(new Dimension(WINDOW_WIDTH + extraW - 10, WINDOW_HEIGHT + extraH - 10));
		w.setLocation(100, 60);
		w.setResizable(false);
		w.setVisible(true);
		w.add(e);
	}
}