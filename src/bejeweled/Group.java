package bejeweled;

import java.util.HashSet;
import java.util.Set;

public class Group {

	private Set<Tile> tiles = new HashSet<>();

	public Group() {}

	public Group(Set<Tile> tiles) {
		this.tiles = tiles;
	}

	public void add(Tile t) {
		tiles.add(t);
	}

	public void addAll(Group g) {
		this.tiles.addAll(g.tiles);
	}

	public Set<Tile> getTiles() {
		return tiles;
	}
}
