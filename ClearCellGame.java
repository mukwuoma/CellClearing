package model;

import java.util.Random;

/* This class must extend Game */
public class ClearCellGame extends Game {

	private int score = 0;

	private Random random = new Random();

	public ClearCellGame(int maxRows, int maxCols, Random random, int strategy) {
		super(maxRows, maxCols);
		strategy = 1;
		this.random = random;
	}

	@Override
	public boolean isGameOver() {

		return emptyRow(board.length - 1) ? false : true;
	}

	@Override
	public int getScore() {
		return score;
	}

	@Override
	public void nextAnimationStep() {

		if (!isGameOver()) {
			BoardCell temp[] = new BoardCell[board[0].length], temp2[] = new BoardCell[board[0].length];
			int index = firstEmptyRowIndex(board);
			if (index == 0) {
				getRandomRow(0, board);
			} else {
				for (int i = index; i > 0 && index < board.length; i--) {
					temp = board[i - 1];
					board[i] = temp;
				}
				for (int col = 0; col < board[0].length; col++) {
					temp2[col] = BoardCell.getNonEmptyRandomBoardCell(random);
				}
				board[0] = temp2;
			}
		}
	}

	@Override
	public void processCell(int rowIndex, int colIndex) {
		if (!isGameOver()) {
			vertical(rowIndex, colIndex);
			horizontal(rowIndex, colIndex);
			diagonal(rowIndex, colIndex);
			board[rowIndex][colIndex] = BoardCell.EMPTY;
			score++;
			collapse(rowIndex);
		}
	}

	private void vertical(int rowIndex, int colIndex) {
		if (!isGameOver()) {
			String color = board[rowIndex][colIndex].getName();
			for (int row = rowIndex - 1; row >= 0; row--) {
				if (board[row][colIndex].getName().equals(color)) {
					board[row][colIndex] = BoardCell.EMPTY;
					score++;
				} else {
					break;
				}
			}
			for (int row = rowIndex + 1; row < board.length; row++) {
				if (board[row][colIndex].getName().equals(color)) {
					board[row][colIndex] = BoardCell.EMPTY;
					score++;
				} else {
					break;
				}
			}
		}
	}

	private void horizontal(int rowIndex, int colIndex) {
		if (!isGameOver()) {
			String color = board[rowIndex][colIndex].getName();
			for (int col = colIndex - 1; col >= 0; col--) {
				if (board[rowIndex][col].getName().equals(color)) {
					board[rowIndex][col] = BoardCell.EMPTY;
					score++;
				} else {
					break;
				}
			}
			for (int col = colIndex + 1; col < board[0].length; col++) {
				if (board[rowIndex][col].getName().equals(color)) {
					board[rowIndex][col] = BoardCell.EMPTY;
					score++;
				} else {
					break;
				}
			}
		}
	}

	private void diagonal(int rowIndex, int colIndex) {
		if (!isGameOver()) {
			String color = board[rowIndex][colIndex].getName();
			for (int row = rowIndex + 1, col = colIndex + 1; row < board.length
					&& col < board[0].length; row++, col++) {
				if (board[row][col].getName().equals(color)) {
					board[row][col] = BoardCell.EMPTY;
					score++;
				} else {
					break;
				}
			}
			for (int row = rowIndex + 1, col = colIndex - 1; (row < board.length) && col >= 0; row++, col--) {
				if (board[row][col].getName().equals(color)) {
					board[row][col] = BoardCell.EMPTY;
					score++;
				} else {
					break;
				}
			}
			for (int row = rowIndex - 1, col = colIndex - 1; row >= 0 && col >= 0; row--, col--) {
				if (board[row][col].getName().equals(color)) {
					board[row][col] = BoardCell.EMPTY;
					score++;
				} else {
					break;
				}
			}
			for (int row = rowIndex - 1, col = colIndex + 1; row >= 0 && col < board[0].length; row--, col++) {
				if (board[row][col].getName().equals(color)) {
					board[row][col] = BoardCell.EMPTY;
					score++;
				} else {
					break;
				}
			}
		}

	}

	private void collapse(int rowIndex) {
		if (!isGameOver()) {
			for (int row = 0; row < board.length - 1; row++) {
				if (emptyRow(row)) {
					BoardCell temp;
					for (int rows = row; rows < board.length - 1; rows++) {
						for (int col = 0; col < board[0].length; col++) {
							temp = board[rows + 1][col];
							board[rows][col] = temp;
						}
					}

				}
			}
		}

	}

	private boolean emptyRow(int row) {
		boolean empty = true;

		for (int i = 0; i < board[0].length; i++) {
			if (board[row][i] != BoardCell.EMPTY) {
				empty = false;
				break;
			}

		}
		return empty;

	}

	private int firstEmptyRowIndex(BoardCell[][] board) {

		int count = 0, index = 0;
		for (int row = 0; row <= board.length - 1; row++) {
			for (int col = 0; col < board[0].length; col++) {
				if (board[row][col] == BoardCell.EMPTY) {
					count++;
				}
			}
			if (count == board[0].length) {
				index = row;
				break;
			} else {
				count = 0;
			}
		}
		return index;
	}

	private BoardCell[] getRandomRow(int rowIndex, BoardCell[][] board) {
		for (int col = 0; col < board[0].length; col++) {
			board[rowIndex][col] = BoardCell.getNonEmptyRandomBoardCell(random);
		}
		return board[rowIndex];
	}

}