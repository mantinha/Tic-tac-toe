package br.fsa.app.model;

import java.time.LocalDateTime;

/**
 * ###############################################  
 * ##                                           ##  
 * ##   Analise e Desenvolvimento de Sistemas   ##
 * ## Centro Universitario Fundacao Santo Andre ##
 * ##                                           ##
 * ###############################################
 * 
 * @author Adriano M. Santana 737679
 *                  ------
 *                  | DB |
 *                  ------
 *                    |
 *              --------------
 * ----------   |            |
 * |        |---| Controller |
 * |  Game  |   |            |
 * |        |   --------------
 * ----------
 *  * @version 1.0.0
 */
public class Game {
	
	private static final int N = 3;
	private int turn;
	private int[][] matrix = new int[N][N];
	private int plays;
	private boolean winner;
	private String player;
	private LocalDateTime now;
	
	/**
	 * 
	 * Pre configuracao da entidade
	 */
	public Game() {
		turn = 0;
		setMatrix( 0 );
		plays = 0;
	}
	
	public int getMatrixValue(int row, int col) {
		return matrix[row][col];
	}

	public void setMatrix( int value ) {
		for( int row = 0; row < N; row++ ) {
			for( int col = 0; col < N; col++ ) {
				matrix[row][col] = value;
			}
		}
	}

	public void setMatrix( int row, int col, int value ) {
		matrix[row][col] = value;
	}
	
	public void setMatrix( int[][] matrix ) {
		this.matrix = matrix;
	}
	
	public void setPlays() {
		plays++;
	}
	
	/**
	 * 
	 * Getters.. Setters.. 
	 */
	public void setPlays( int plays ) {
		this.plays = plays;
	}
	
	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}
	
	public int[][] getMatrix() {
		return matrix;
	}	

	public int getPlays() {
		return plays;
	}	

	public boolean isWinner() {
		return winner;
	}

	public void setWinner(boolean winner) {
		this.winner = winner;
	}

	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}

	public LocalDateTime getNow() {
		return now;
	}

	public void setNow(LocalDateTime now) {
		this.now = now;
	}
	
}
