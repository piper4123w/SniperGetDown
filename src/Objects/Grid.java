package Objects;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;

public class Grid {
	public int rows;
	public int cols;

	public double cellSize;

	public char[][] gridCode;

	public Grid() {
		rows = 1;
		cols = 1;
		cellSize = 20;

	}

	public Grid(int r, int c, double cellSize) {
		rows = r;
		cols = c;

		this.cellSize = cellSize;
		initGridCode();
	}

	private void initGridCode() { // draw a grid with blocks as boundary
		gridCode = new char[rows][cols];

		for (int i = 0; i < cols; i++) {
			gridCode[0][i] = BrickBlock.gridCode;
			gridCode[rows - 1][i] = BrickBlock.gridCode; // place cement
															// blocks in borders
			// cement
			// blocks in borders
		}
		for (int j = 0; j < rows; j++) {
			gridCode[j][0] = BrickBlock.gridCode;
			gridCode[j][cols - 1] = BrickBlock.gridCode;

		}
		// debugPrintGrid();
	}

	public void debugPrintGrid() {
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				System.out.print(gridCode[j][i] + " ");
			}
			System.out.println();
		}
	}

	public void stringToGrid(String levelString) {
		int lineNum = 0;
		String lvlAr[] = levelString.split("\n");
		this.rows = Integer.parseInt(lvlAr[0].substring(0, lvlAr[0].indexOf(',')));
		this.cols = Integer.parseInt(lvlAr[0].substring(lvlAr[0].indexOf(',') + 1, lvlAr[0].length()));
		System.out.println(rows + " " + cols);
		this.gridCode = new char[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				this.gridCode[i][j] = lvlAr[j + 1].charAt(i);
			}
		}
		debugPrintGrid();
	}

	public void fileToGrid(File file) {
		if (file != null) {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String levelString = "";
				String str;
				while ((str = reader.readLine()) != null) {
					levelString += str + '\n';
				}
				stringToGrid(levelString);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void remove(char code) {
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				if (gridCode[j][i] == code)
					gridCode[j][i] = ' ';
			}
		}
	}

	public Class<?> getClassByPosition(int r, int c) {
		char code = gridCode[r][c];
		System.out.println("code:" + code);
		switch (code) {
		case BrickBlock.gridCode:
			return BrickBlock.class;
		case CoverBlock.gridCode:
			return CoverBlock.class;
		case Bank.gridCode:
			return Bank.class;
		case Van.gridCode:
			return Van.class;
		default:
			System.out.println("Found nothing");
			return null;
		}
	}

}
