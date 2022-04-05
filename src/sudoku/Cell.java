package sudoku;

import java.util.HashSet;
import java.util.Set;

public class Cell {
	public Board tablero;
	public int value;
	public Set<Integer> possibleValues;
	public int line;
	public int column;

	Cell(Board t, int l, int c) {
		value = 0;
		tablero = t;
		line = l;
		column = c;
		possibleValues = new HashSet<>();
	}

	Cell(int v, Board t, int l, int c) {
		value = v;
		tablero = t;
		line = l;
		column = c;
	}

	public int freedom() {
		tablero.getPossibles(this);
		return possibleValues.size();
	}

}
