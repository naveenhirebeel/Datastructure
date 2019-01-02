package com.backtracking.sudoku;

import java.util.Scanner;

public class Sudoku9x9 {

	static int[][] grid = {{3, 0, 6, 5, 0, 8, 4, 0, 0},
            {5, 2, 0, 0, 0, 0, 0, 0, 0},
            {0, 8, 7, 0, 0, 0, 0, 3, 1},
            {0, 0, 3, 0, 1, 0, 0, 8, 0},
            {9, 0, 0, 8, 6, 3, 0, 0, 5},
            {0, 5, 0, 0, 9, 0, 6, 0, 0},
            {1, 3, 0, 0, 0, 0, 2, 5, 0},
            {0, 0, 0, 0, 0, 0, 0, 7, 4},
            {0, 0, 5, 2, 0, 6, 3, 0, 0}};
	
	int gridSize = 9;
	
	public static void main(String[] args) {
		
		getMatrixInput();
		
		Sudoku9x9 sudoku = new Sudoku9x9();
		System.out.println("Result - >");
		printGrid(grid);
		System.out.println("\nResult - >");
		if(sudoku.solveDudoku(grid)) {
			printGrid(grid);
		} else {
			System.out.println("Not possible");
		}
		
	}
	
	private static void getMatrixInput() {
		Scanner sc = new Scanner(System.in);
		int[][] grid = new int[9][9];
		System.out.println("Enter ");
		for(int i = 0; i < 9; i ++) {
			for(int j = 0;  j < 9; j++) {
				System.out.println("Enter row "+i+", col "+j);
				String in = sc.nextLine();
				grid[i][j] = Integer.valueOf(in); 
			}
			printGrid(grid);
		}
	}

	/* A utility function to print grid  */
	static void printGrid(int grid[][]) {
		int length = grid.length;
		for (int row = 0; row < length; row++) {
			for (int col = 0; col < length; col++) {
				System.out.print(grid[row][col] + " ");
			}
			System.out.println();
		}
	}
	
	boolean solveDudoku(int[][] grid) {
		MissingNumber mn = findMissingNumber(grid);
		
		if(null == mn) {
			return true;
		}
		
		int row = mn.getRow();
		int col = mn.getCol();
		
		for(int i = 1; i<=gridSize; i++) {
			if(isSafe(grid, row, col, i)) {
				grid[row][col] = i;
				
				if(solveDudoku(grid)) {
					return true;
				}
				grid[row][col] = 0;	
			}
		}
		return false;
	}

	boolean usedInRow(int[][] grid, int row, int num) {
		for(int col = 0; col < gridSize; col++) {
			if(num == grid[row][col]) {
				return true;
			}
		}
		return false;
	}
	
	boolean usedInCol(int[][] grid, int col, int num) {
		for(int row = 0; row < gridSize; row++) {
			if(num == grid[row][col]) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isSafe(int[][] grid2, int row, int col, int num) {
		return !usedInRow(grid2, row, num) &&
				!usedInCol(grid2, col, num) &&
				!usedInBox(grid2, row -row%3, col-col%3, num);
	}

	boolean usedInBox(int grid[][], int boxStartRow, int boxStartCol, int num) {
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				if (grid[boxStartRow + row ][boxStartCol + col] == num) {
					return true;
				}
			}
		}
		return false;
	}

	private MissingNumber findMissingNumber(int[][] grid) {
		for(int i =0; i< gridSize; i++) {
			for(int j = 0; j < gridSize; j++) {
				if(grid[i][j] == 0) {
					return new MissingNumber(i, j);
				}
			}
		}
		return null;
	}
}



