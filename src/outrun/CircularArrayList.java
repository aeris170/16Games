package outrun;

import java.util.ArrayList;

public class CircularArrayList<E> extends ArrayList<E> {

	private static final long serialVersionUID = 3234644550704608046L;

	@Override
	public E get(int index) {
		return super.get((index + size()) % size());
	}
}
