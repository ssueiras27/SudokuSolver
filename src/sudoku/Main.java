package sudoku;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
	private File input;
	private File output;
	private FileWriter fw;
	private Board board;

	public static void main(String[] args) {
		Main sudoku = new Main();
		sudoku.input = new File("input.txt"); // Input file
		sudoku.output = new File("student_output.txt"); // Output file
		sudoku.Start(); // Main routine
	}

	Main() {
	}

	/**
	 * Start(): Main routine of the program, it basically reads sudokus from the
	 * input file one line at a time and loads the value into a Board, then solves
	 * it and finally prints it in the output file.
	 */
	private void Start() {
		try {
			Scanner scan = new Scanner(input);
			fw = new FileWriter(output);
			int lineCounter = 0;
			while (scan.hasNextLine()) {
				if (lineCounter == 0) {
					board = new Board();
				}
				String inputLine = scan.nextLine();
				if (!inputLine.isBlank()) {
					board.loadValuesInLine(inputLine, lineCounter);
					if (lineCounter == 8) {
						Solve();
						PrintBoardToFile(scan.hasNextLine());
					}
					lineCounter = (lineCounter + 1) % 9;
				}
			}
			scan.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * PrintBoardToFile(boolean isLast): The auxiliary method that prints the Board
	 * in the output file, it has a boolean parameter used to prevent from writing
	 * an extra empty line.
	 */
	private void PrintBoardToFile(boolean isLast) {
		try {

			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					int value = board.cells[i][j].value;
					char c = Character.forDigit(value, 10);
					fw.write(c);
				}
				fw.write('\n');
			}
			if (isLast)
				fw.write('\n');
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Solve(): The method that start solving the sudoku calling the recursive
	 * function with its initial parameter.
	 */
	private void Solve() {

		SolveREC(InitializeBestFirst());
	}

	/**
	 * ArrayList<Main.Cell> InitializeBestFirst(): This function returns a list of
	 * the cells that are empty sorted by its freedom().
	 */
	private ArrayList<Cell> InitializeBestFirst() {
		ArrayList<Cell> BestFirst = new ArrayList<>();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				Cell c = board.cells[i][j];
				if (board.cells[i][j].value == 0) {
					BestFirst.add(c);
				}
			}
		}
		BestFirst.sort(new Comparator<Cell>() {
			public int compare(Cell o1, Cell o2) {
				return o1.freedom() - o2.freedom();
			}
		});
		return BestFirst;
	}

	/**
	 * boolean SolveREC(ArrayList<Cell.> auxBestFirst): The recursive method that
	 * solves the sudoku by trying values using the sorted list of cell for
	 * heuristic purposes and optimization, will return true if done;
	 */
	private boolean SolveREC(ArrayList<Cell> auxBestFirst) {
		// PrintLn(auxBestFirst);
		boolean solved = false;
		if (!auxBestFirst.isEmpty()) {
			Cell cell = auxBestFirst.remove(0);
			int freedom = cell.freedom();
			Integer[] possibleValues = cell.possibleValues.toArray(new Integer[freedom]);
			if (freedom >= 1) {
				for (int i = 0; i < freedom && !solved; i++) {
					cell.value = possibleValues[i];
					solved = SolveREC(UpdateBestFirst(auxBestFirst));
					if (!solved)
						cell.value = 0;
				}
			}
			return solved;
		}
		return true;
	}

	/**
	 * ArrayList<Cell.> UpdateBestFirst(ArrayList<Cell.> oldBestFirst): The method
	 * that updates the sorted list by reordering the elements by its freedom again.
	 */
	private ArrayList<Cell> UpdateBestFirst(ArrayList<Cell> oldBestFirst) {
		ArrayList<Cell> BestFirst = new ArrayList<>();
		for (int i = 0; i < oldBestFirst.size(); i++) {
			BestFirst.add(oldBestFirst.get(i));
		}
		BestFirst.sort(new Comparator<Cell>() {
			public int compare(Cell o1, Cell o2) {
				return o1.freedom() - o2.freedom();
			}
		});
		return BestFirst;
	}

}
