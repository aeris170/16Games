package doodlejump;

import java.awt.Shape;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.doa.engine.DoaCamera;
import com.doa.engine.DoaHandler;
import com.doa.engine.DoaObject;
import com.doa.engine.DoaWindow;
import com.doa.engine.graphics.DoaGraphicsContext;
import com.doa.engine.graphics.DoaSprites;

public class PlatformCreator extends DoaObject {

	private static final long serialVersionUID = 2200702511108270066L;

	public static final List<Platform> PLATFORMS = new ArrayList<>();

	private int safeY = 0;

	public PlatformCreator() {
		super(0f, 0f, 0, 0);
		for (int i = 0; i < 3; i++) {
			float x = ThreadLocalRandom.current().nextInt(400);
			float y = DoaCamera.getY() - i * 100 + ThreadLocalRandom.current().nextInt(50) - 25 + 300f;
			PLATFORMS.add(DoaHandler.instantiateDoaObject(Platform.class, x, y));
		}
		PLATFORMS.add(DoaHandler.instantiateDoaObject(Platform.class, 300f, 100f));
		PLATFORMS.add(DoaHandler.instantiateDoaObject(Platform.class, 240f, 180f));
		PLATFORMS.add(DoaHandler.instantiateDoaObject(Platform.class, 50f, 350f));
	}

	@Override
	public void tick() {
		if (DoaCamera.getY() <= safeY) {
			for (int i = 0; i < 3; i++) {
				float x = ThreadLocalRandom.current().nextInt(DoaWindow.getInstance().getWidth() - DoaSprites.get(Platform.PLATFORM_TEXTURE_NAME).getWidth());
				float y = DoaCamera.getY() - i * 100 + ThreadLocalRandom.current().nextInt(50) - 50f;
				PLATFORMS.add(DoaHandler.instantiateDoaObject(Platform.class, x, y));
			}
			safeY = (int) (DoaCamera.getY() - 300);
		}
	}

	@Override
	public void render(DoaGraphicsContext g) {}

	@Override
	public Shape getBounds() {
		return null;
	}

}
