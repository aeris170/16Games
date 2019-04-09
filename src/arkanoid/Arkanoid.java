package arkanoid;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.sound.sampled.Clip;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

import com.doa.engine.DoaEngine;
import com.doa.engine.DoaHandler;
import com.doa.engine.DoaWindow;
import com.doa.engine.graphics.DoaSprites;
import com.doa.engine.sound.DoaSounds;

public final class Arkanoid {

	public static int WINDOW_WIDTH;
	public static int WINDOW_HEIGHT;

	static DoaEngine e;
	static DoaWindow w;

	static Random r = new Random();

	public static void main(String[] args) {
		Assets.configureAssets();
		DoaSounds.setGlobalVolume(0.1f);
		DoaSounds.get("arkanoidMusic").loop(Clip.LOOP_CONTINUOUSLY);
		WINDOW_WIDTH = DoaSprites.get(Level.LEVEL_TEXTURE_NAME).getWidth();
		WINDOW_HEIGHT = DoaSprites.get(Level.LEVEL_TEXTURE_NAME).getHeight();
		DoaEngine.TICK_RATE = 80;
		DoaHandler.instantiateDoaObject(Level.class, DoaSprites.get(Level.LEVEL_TEXTURE_NAME));
		DoaHandler.instantiateDoaObject(BlockFactory.class);
		DoaHandler.instantiateDoaObject(Player.class, (WINDOW_WIDTH - DoaSprites.get(Player.PLAYER_TEXTURE_NAME).getWidth()) / 2f,
		        WINDOW_HEIGHT - DoaSprites.get(Player.PLAYER_TEXTURE_NAME).getWidth() / 1.5f);
		DoaHandler.instantiateDoaObject(Ball.class, DoaSprites.get("ball"));
		DoaHandler.instantiateDoaObject(HUD.class);

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

		w.setTitle("Java Arkanoid!");
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
