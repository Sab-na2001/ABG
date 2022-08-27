package io.github.oliviercailloux.abg;

public class Player {
	Color C ;//(color.java) 0 = Noir et 1 = Blanc

	public Color getC() {
		return C;
	}

	public void setC(Color c) {
		C = c;
	}

	public Player(Color c) {
		//super();
		C = c;
	}

}
