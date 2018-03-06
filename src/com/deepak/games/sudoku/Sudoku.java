package com.deepak.games.sudoku;

import java.util.logging.Logger;

public class Sudoku {
	
	private static final Logger logger = Logger.getLogger(Sudoku.class.getName());

	private int[][] sudokuTable;

	public Sudoku(int[][] sudokuTable) {
		this.sudokuTable = sudokuTable;
	}

	public boolean isSolutionValid() {

		for (int i = 0; i < Constants.BOARD_SIZE; ++i) {
			for (int k = 0; k < Constants.BOARD_SIZE; ++k) {
				if (validateCell(i, k, sudokuTable[i][k]))
					continue;
				else
					return false;
			}
		}

		return true;
	}

	private boolean validateCell(int rowIndex, int columnIndex, int actualNumber) {

		// if the given number is already in the row: the number cannot be part of the
		// solution
		
		for (int i = 0; i < Constants.BOARD_SIZE; ++i) {
			
			//logger.info("Col: " + i + " Row: " + rowIndex);
			// check the rows
			if (i == columnIndex)
				continue;
			else if (sudokuTable[rowIndex][i] == actualNumber)
				return false;
		}

		// if the given number is already in the column: the number cannot be part of
		// the solution
		for (int k = 0; k < Constants.BOARD_SIZE; ++k) {
			if (k == rowIndex)
				continue;
			else if (sudokuTable[k][columnIndex] == actualNumber)
				return false;
		}
		
		// if the given number is already in the box: the number cannot be part of the
		// solution
		int boxRowOffset = (rowIndex / 3) * Constants.BOX_SIZE;
		int boxColumnOffset = (columnIndex / 3) * Constants.BOX_SIZE;

		for (int i = 0; i < Constants.BOX_SIZE; ++i) {
			for (int j = 0; j < Constants.BOX_SIZE; ++j) {
			
				if ((boxRowOffset + i) == rowIndex && (boxColumnOffset + j) == columnIndex)
					continue;
				else if (actualNumber == sudokuTable[boxRowOffset + i][boxColumnOffset + j])
					return false;
			}	
		}
		return true;
	}
}