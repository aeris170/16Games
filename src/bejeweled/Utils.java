package bejeweled;

import java.util.concurrent.ThreadLocalRandom;

public final class Utils {

	private Utils() {}

	public static <T extends Enum<?>> T randomEnum(Class<T> clazz) {
		int x = ThreadLocalRandom.current().nextInt(clazz.getEnumConstants().length - 1);
		return clazz.getEnumConstants()[x];
	}
}
