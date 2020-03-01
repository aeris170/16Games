package carracing;

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
import com.doa.engine.graphics.DoaSprites;
import com.doa.engine.sound.DoaSounds;

public final class CarRacing {

	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;

	static DoaEngine e;
	static DoaWindow w;

	public static void main(String[] args) {
		DoaEngine.TICK_RATE = 60;
		Assets.configureAssets();
		DoaSounds.setGlobalVolume(0.1f);
		setUpGame(6);
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

		w.setTitle("Java Car Racing Game!");
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

	private static void setUpGame(int carCount) {
		DoaHandler.clear();
		Car.ALL_CARS.clear();
		Car.COUNT = 0;
		for (int i = 0; i < carCount; i++) {
			DoaHandler.instantiateDoaObject(Car.class, 300f + i * 50, 1700f + i * 80, DoaSprites.get("car0").getWidth(), DoaSprites.get("car0").getHeight(), 4f * 0.5f, i == 0);
		}
		DoaHandler.instantiateDoaObject(Level.class);
		DoaCamera.adjustCamera(Car.ALL_CARS.get(0), 0, 0, DoaSprites.get("background").getWidth() * 2, DoaSprites.get("background").getHeight() * 2);
		DoaCamera.setTweenAmountX(0.1f);
		DoaCamera.setTweenAmountY(0.1f);
	}
}
