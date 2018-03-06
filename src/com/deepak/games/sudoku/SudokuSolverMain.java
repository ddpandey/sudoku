package com.deepak.games.sudoku;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.logging.Logger;
import java.util.stream.Stream;

import com.deepak.games.sudoku.exception.InvalidSudokuSolutionException;

public class SudokuSolverMain {

	private static final Logger logger = Logger.getLogger(SudokuSolverMain.class.getName());

	public static void main(String[] args) {

		String fileName = args[0];

		// read file into stream, try-with-resources
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

			int[][] sudokuTable = sudokuTable = stream.filter(s -> s.trim().length() == Constants.BOARD_SIZE).map(s -> s.split(""))
					.flatMap(Arrays::stream).map(Integer::valueOf).map(i -> new int[] { i }).toArray(int[][]::new);

			int[] flatAry = Arrays.stream(sudokuTable).flatMapToInt(x -> Arrays.stream(x)).toArray();

			if (sudokuTable != null && sudokuTable.length != Constants.BOARD_SIZE* Constants.BOARD_SIZE)
				throw new InvalidSudokuSolutionException("Invalid Sudoku Solution provided.Please check!");

			Sudoku sudokuSolver = new Sudoku(parseIntArray(flatAry));
			
			if(sudokuSolver.isSolutionValid())
				logger.info("Congratulations! The Sudoku solution is correct");
			else
				logger.info("The Sudoku solution is incorrect. Please check the file");

		} catch (IOException ex) {
			logger.severe("Exception occurred while reading the file " + ex);
		}  catch (NumberFormatException nfe) {
			logger.severe("Number Parsing Error occurred while reading the file " + nfe);
		}

	}

	private static int[][] parseIntArray(int[] flatTable) {

		int[][] sudokuTable = new int[Constants.BOARD_SIZE][Constants.BOARD_SIZE];

		for (int i = 0; i < Constants.BOARD_SIZE; i++) {
			for (int j = 0; j < Constants.BOARD_SIZE; j++) {
				int index = i * Constants.BOARD_SIZE + j;
				sudokuTable[i][j] = flatTable[index];
			}
		}
		return sudokuTable;
	}

}
