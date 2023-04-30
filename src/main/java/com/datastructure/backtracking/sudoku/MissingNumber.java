package com.datastructure.backtracking.sudoku;
class MissingNumber {
	
	int row = 0;
	int col = 0;
	
	public MissingNumber(int row, int col) {
		super();
		this.row = row;
		this.col = col;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
}