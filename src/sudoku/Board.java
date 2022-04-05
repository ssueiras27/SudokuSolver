package sudoku;

import java.util.HashSet;
import java.util.Set;

public class Board {
	public Cell[][] cells;

	Board() {
		cells = new Cell[9][9];
	}

	public void loadValuesInLine(String values, int line) {
		for (int i = 0; i < 9; i++) {
			char c = values.charAt(i);
			if (Character.isDigit(c)) {
				cells[line][i] = new Cell(Character.getNumericValue(c), this, line, i);
			} else {
				cells[line][i] = new Cell(this, line, i);
			}
		}
	}

	public void getPossibles(Cell cell) {
		Set<Integer> possibles = new HashSet<>();
		for (int i = 1; i < 10; i++) {
			possibles.add(i);
		}
		cell.possibleValues = possibles;
		possibles = getPossiblesColumn(cell);
		possibles = getPossiblesLine(cell);
		possibles = getPossiblesCuadrant(cell);
	}

	public Set<Integer> getPossiblesColumn(Cell cell) {
		Set<Integer> possibles = cell.possibleValues;
		int column = cell.column;
		for (int i = 0; i < 9; i++) {
			Cell aux = cells[i][column];
			int auxValue = aux.value;
			if (auxValue != 0 && possibles.contains(auxValue)) {
				possibles.remove(auxValue);
			}
		}
		return possibles;
	}

	public Set<Integer> getPossiblesLine(Cell cell) {
		Set<Integer> possibles = cell.possibleValues;
		int line = cell.line;
		for (int i = 0; i < 9; i++) {
			Cell aux = cells[line][i];
			int auxValue = aux.value;
			if (auxValue != 0 && possibles.contains(auxValue)) {
				possibles.remove(auxValue);
			}
		}
		return possibles;
	}

	public Set<Integer> getPossiblesCuadrant(Cell cell) {
		Set<Integer> possibles = cell.possibleValues;
		int line = cell.line;
		int column = cell.column;
		int mini = 0, maxi = 0, minj = 0, maxj = 0;
		if (line >= 0 && line < 3 && column >= 0 && column < 3) {// 1
			mini = 0;
			maxi = 3;
			minj = 0;
			maxj = 3;
		} else if (line >= 0 && line < 3 && column >= 3 && column < 6) {// 2
			mini = 0;
			maxi = 3;
			minj = 3;
			maxj = 6;
		} else if (line >= 0 && line < 3 && column >= 6 && column < 9) {// 3
			mini = 0;
			maxi = 3;
			minj = 6;
			maxj = 9;
		} else if (line >= 3 && line < 6 && column >= 0 && column < 3) {// 4
			mini = 3;
			maxi = 6;
			minj = 0;
			maxj = 3;
		} else if (line >= 3 && line < 6 && column >= 3 && column < 6) {// 5
			mini = 3;
			maxi = 6;
			minj = 3;
			maxj = 6;
		} else if (line >= 3 && line < 6 && column >= 6 && column < 9) {// 6
			mini = 3;
			maxi = 6;
			minj = 6;
			maxj = 9;
		} else if (line >= 6 && line < 9 && column >= 0 && column < 3) {// 7
			mini = 6;
			maxi = 9;
			minj = 0;
			maxj = 3;
		} else if (line >= 6 && line < 9 && column >= 3 && column < 6) {// 8
			mini = 6;
			maxi = 9;
			minj = 3;
			maxj = 6;
		} else if (line >= 6 && line < 9 && column >= 6 && column < 9) {// 9
			mini = 6;
			maxi = 9;
			minj = 6;
			maxj = 9;
		}
		for (int i = mini; i < maxi; i++) {
			for (int j = minj; j < maxj; j++) {
				Cell aux = cells[i][j];
				int auxValue = aux.value;
				if (auxValue != 0 && possibles.contains(auxValue)) {
					possibles.remove(auxValue);
				}
			}
		}
		return possibles;
	}
}
