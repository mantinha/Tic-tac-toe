package br.fsa.app.controller;

public interface ITicTacToe {
	
	public static final int O = 1;
	public static final int X = 2;

	public void putO(int x, int y);
	public void putX(int x, int y);
	public int whosTurn();
	public int whosVictory();
	public int whichVictory();
	public void newGame();
	public void saveGame();
	public void loadGame();
	public int[][] getBoard();
	
}
