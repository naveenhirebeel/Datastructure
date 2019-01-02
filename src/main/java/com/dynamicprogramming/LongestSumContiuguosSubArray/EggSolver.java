package com.dynamicprogramming.LongestSumContiuguosSubArray;

import java.util.Arrays;

public class EggSolver {
	public static void main(String[] args) {
		System.out.println(new EggSolver().solveUsingDp(8, 4));
	}

	public int solveUsingDp(int n, int k) {
		final int results[][] = new int[n + 1][k + 1];
		for (int i = 0; i < n + 1; i++) {
			results[i][1] = i;
		}
		for (int j = 1; j < k + 1; j++) {
			results[1][j] = 1;
		}
		for (int i = 2; i < n + 1; i++) {
			for (int j = 2; j < k + 1; j++) {
				results[i][j] = Integer.MAX_VALUE;
				for (int x = 1; x < i; x++) {
					final int brokenEggResult = results[x - 1][j - 1];
					final int EggSurvivedResult = results[i - x][j];
					int temp = Math.max(brokenEggResult, EggSurvivedResult) + 1;
                    if (temp < results[i][j]) {
                        results[i][j] = temp;
                    }
				}
			}
		}
		
		printGrid(results);
		
		/*for (final int[] a : results) {
			System.out.println(Arrays.toString(a));
			
		}*/
		return results[n][k];
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
