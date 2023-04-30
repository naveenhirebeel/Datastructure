package com.datastructure.backtracking.sudoku;

public class Sudoku4x4 {

	static int[][] grid = { 	
						{ 1, 0, 3, 4 }, 
						{ 0, 0, 2, 1 }, 
						{ 0, 1, 0, 2 }, 
						{ 2, 4, 0, 0 } 
					};
	
	public static void main(String[] args) {
		Sudoku4x4 sudoku = new Sudoku4x4();
		System.out.println("Result - >");
		sudoku.printGrid(grid);
		System.out.println("\nResult - >");
		if(sudoku.solveDudoku(grid)) {
			sudoku.printGrid(grid);
		} else {
			System.out.println("Not possible");
		}
		
	}
	
	/* A utility function to print grid  */
	void printGrid(int grid[][]) {
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
		
		for(int i = 1; i<=4; i++) {
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
		for(int col = 0; col < 4; col++) {
			if(num == grid[row][col]) {
				return true;
			}
		}
		return false;
	}
	
	boolean usedInCol(int[][] grid, int col, int num) {
		for(int row = 0; row < 4; row++) {
			if(num == grid[row][col]) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isSafe(int[][] grid2, int row, int col, int num) {
		return !usedInRow(grid2, row, num) &&
				!usedInCol(grid2, col, num);
	}


	private MissingNumber findMissingNumber(int[][] grid) {
		for(int i =0; i< 4; i++) {
			for(int j = 0; j < 4; j++) {
				if(grid[i][j] == 0) {
					return new MissingNumber(i, j);
				}
			}
		}
		return null;
	}
}


