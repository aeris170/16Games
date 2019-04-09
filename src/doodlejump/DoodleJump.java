package doodlejump;

import java.awt.Dimension;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

import com.doa.engine.DoaCamera;
import com.doa.engine.DoaEngine;
import com.doa.engine.DoaHandler;
import com.doa.engine.DoaWindow;

public final class DoodleJump {

	public static final int WINDOW_WIDTH = 400;
	public static final int WINDOW_HEIGHT = 800;

	static DoaEngine e;
	static DoaWindow w;

	public static void main(String[] args) {
		Assets.configureAssets();
		DoaHandler.instantiateDoaObject(PlatformCreator.class);
		DoaHandler.instantiateDoaObject(Level.class, 0f, 0f, WINDOW_WIDTH, WINDOW_HEIGHT);
		DoaCamera.adjustCamera(DoaHandler.instantiateDoaObject(Player.class, 50f, 50f), 0, Integer.MIN_VALUE, WINDOW_WIDTH, WINDOW_HEIGHT);
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

		w.setTitle("Java Doodle Jump!");
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
