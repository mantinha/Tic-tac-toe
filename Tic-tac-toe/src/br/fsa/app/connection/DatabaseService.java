package br.fsa.app.connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
 * 
 * |_Referenced libraries
 *   |__mysql-connector-java-8.0.21.jar
 *   
 * < Requirements >
create database tictactoe;
use tictactoe;
create table winner(
	id 			int(4) not null auto_increment,
	player		varchar(3),
    moves		int(1) not null,
    history 	timestamp not null,
	primary 	key(id)
);    
create table game(
	id 			int(4) not null auto_increment,
	m1n1		int(1) not null,
	m1n2		int(1) not null,
	m1n3		int(1) not null,
	m2n1		int(1) not null,
	m2n2		int(1) not null,
	m2n3		int(1) not null,
	m3n1		int(1) not null,
	m3n2		int(1) not null,
	m3n3		int(1) not null,
	moves		int(1) not null,
	primary key(id)
);
 * @version 1.0.0
 */
public class DatabaseService extends DatabaseConfig implements IDBConnection {	

	private static final int N = 3;
	private PreparedStatement pstmt;
	private ResultSet result;
	
	public DatabaseService() throws SQLException {
		super();
	}
	
    @Override
	public void saveScore( String player, int moves, LocalDateTime timeStamp ) {
    	Timestamp localTimeStamp = Timestamp.valueOf( timeStamp.now() );
    	try {
			pstmt = getConnection().prepareStatement( SqlString.INSERTWINNER.getSql() );			
			pstmt.setString( 1, player );
			pstmt.setInt( 2, moves );
			pstmt.setTimestamp( 3, localTimeStamp );
			pstmt.executeUpdate();
			System.out.println( "SCORE SUCCESSFULLY SAVED!" );
		} catch ( SQLException exc ) {
			System.err.println( "ERROR SAVING TO DATABASE: " + exc.getMessage() );
		}
	}
    
    public void saveGame( int[][] board, int moves ) {    	
    	try {
			pstmt = getConnection().prepareStatement( SqlString.INSERTGAME.getSql() );
			int index = 1;
			for( int row = 0; row < N; row++ ) {
				for( int col = 0; col < N; col++ ) {
					pstmt.setInt( index, board[row][col] );
					index++;
				}
			}
			pstmt.setInt( index, moves );
			pstmt.executeUpdate();
			System.out.println( "GAME SAVED SUCCESSFULLY!" );
		} catch ( SQLException exc ) {
			System.err.println( "ERROR SAVING TO DATABASE: " + exc.getMessage() );
		}
    }
    
    public boolean preloadGame() {    	
    	try {
    		setResult( getConnection().createStatement( 
    				ResultSet.TYPE_SCROLL_INSENSITIVE, 
    				ResultSet.CONCUR_UPDATABLE )
    				.executeQuery( SqlString.SELECTGAME.getSql() ) );
    		System.out.println( "GAME PRELOADED SUCCESSFULLY!" );
			return true;
		} catch ( SQLException exc ) {
			System.err.println( "PRELOAD GAME ERROR: " + exc.getMessage() );
			return false;
		}
    }
    
    public int[][] loadBoard() {
    	int temp[][] = new int[N][N];
    	int dbCol = 2;
		try {
			System.out.println( "LOADING.." );
			getResult().first();
			for( int row = 0; row < N; row++ ) {
				for( int col = 0; col < N; col++ ) {
					temp[row][col] = Integer.valueOf( getResult().getString( dbCol ) );					
					dbCol++;
				}
			}
		} catch ( NumberFormatException exc ) {
			System.err.println( "NUMERIC FORMAT ERROR WHILE LOADING BOARD: " + exc.getMessage() );			
		} catch ( SQLException exc ) {
			System.err.println( "LOAD BOARD ERROR: " + exc.getMessage() );			
		}
		return temp;
    }
    
    public int loadMoves() {
    	try {
    		System.out.println( "LOADING.." );
			return Integer.valueOf( getResult().getString( "moves" ) );
		} catch ( NumberFormatException exc ) {
			System.err.println( "NUMERIC FORMAT ERROR WHILE LOADING MOVES: " + exc.getMessage() );
			return -1;
		} catch ( SQLException exc ) {
			System.err.println( "LOAD MOVES ERROR: " + exc.getMessage() );
			return -2;
		}
    }
    
    public void bestPlayers() {
    	try {
    		System.out.println( "LOADING.." );
			setResult( getConnection().createStatement( 
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
					ResultSet.CONCUR_UPDATABLE )
					.executeQuery( SqlString.SELECTPLAYERS.getSql() ) );
			System.out.println( "PLAYERS LOADED SUCCESSFULLY!" );
			getResult().first();
			getResult().previous();
		} catch ( SQLException exc ) {
			System.err.println( "BEST PLAYERS ERROR: " + exc.getMessage() );
		}
    }

	@Override
	public String getScore( int qtd ) {		
		try {			
			return getResult().getString( qtd );			
		} catch ( SQLException exc ) {
			System.err.println( "GET SCORE ERROR: " + exc.getMessage() );
		}
		return null;
	}

	/**
	 * 
	 * Getters.. Setters.. 
	 */
	public ResultSet getResult() {
		return result;
	}

	public void setResult(ResultSet result) {
		this.result = result;
	}
	
}
