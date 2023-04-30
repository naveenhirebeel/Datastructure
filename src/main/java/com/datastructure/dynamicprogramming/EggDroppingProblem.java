package com.datastructure.dynamicprogramming;

public class EggDroppingProblem {

	public static void main(String[] args) {
		new EggDroppingProblem().solveEggDrop(8, 4);
	}

	private void solveEggDrop(int floors, int eggs){
		
		int[][] board = new int[floors+1][eggs+1];
		
		//f(n, 1e) = n
		for(int i = 0; i <= floors; i++) {
			board[i][1] = i;
		}
		
		//f(1, ne) = 1
		for(int j = 1; j<=eggs; j++) {
			board[1][j] = 1;
		}
		
		//f(n,e) = 1 + max(f(n-x, e), f(x-1,e-1))
		for(int i = 2; i <= floors; i++) {
			for(int j = 2; j <= eggs; j++) {
				board[i][j] = Integer.MAX_VALUE;
				for(int x = 1; x < i; x++) {
					int eggBroke = board[x-1][j-1];
					int eggSurvived = board[i-x][j];
					int temp = 1 + Math.max(eggSurvived, eggBroke);
					if (temp < board[i][j]) {
						board[i][j] = temp;
                    }
				}
			}
		}
		
		printGrid(board);
	}

	void printGrid(int grid[][]) {
		int rowLength = grid.length;
		int colLength = grid[0].length;
		for (int row = 0; row < rowLength; row++) {
			for (int col = 0; col < colLength; col++) {
				System.out.print(grid[row][col] + " ");
			}
			System.out.println();
		}
	}
}
