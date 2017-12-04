package x.o.game.fun;

import javax.swing.JLabel;

public class Polje extends JLabel {

	private static final long serialVersionUID = 1L;
	public static final char X = 'x';
	public static final char O = 'o';
	public static final char EMPTY = ' ';
	private int position;
	private char value;
	
	public Polje(int position) {
		this.position = position;
		setValue(EMPTY);
	}
	
	public boolean equals(Polje p) {
		return value == p.getValue() && value != EMPTY;
	}
	public void setValue(char value) {
		this.value = value;
	}
	public int getPosition() {
		return position;
	}
	public char getValue() {
		return value;
	}
	public boolean isEmpty() {
		return value == EMPTY;
	}
	public void reset() {
		value = EMPTY;
		setIcon(null);
	}
	public String toString() {
		return value + "";
	}
}
