package br.fsa.app.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Random;

import br.fsa.app.connection.DatabaseService;
import br.fsa.app.view.View;
import br.fsa.app.model.Game;

/**
 * ###############################################  
 * ##                                           ##  
 * ##   Analise e Desenvolvimento de Sistemas   ##
 * ## Centro Universitario Fundacao Santo Andre ##
 * ##                                           ##
 * ###############################################
 * 
 * @author Adriano M. Santana 737679
 *                ------
 *                | DB |
 *                ------
 * --------         |          ----------
 * |      |   --------------   |        |
 * | View |   |            |---|  Game  |
 * |      |---| Controller |   |        |
 * --------   |            |   ----------
 *            --------------
 * @version 1.0.0 
 */
public class Controller implements ITicTacToe, ActionListener {
	
	private static final int N = 3;
	private static final int LIMITE = 9;
	private View view;
	private Random random = new Random();
	private Game game = new Game();	
	private DatabaseService dbService;
	
	/**
	 * 
	 * Carrega view e habilita listener aos buttons
	 * Carrega banco de dados
	 * Inicia turno
	 * @param view - interface grafica
	 */
	public Controller(View view) {
		this.view = view;		
		for( int row = 0; row < N; row++ ) {
			for( int col = 0; col < N; col++ ) {
				view.getMatrix()[row][col].addActionListener( this );
			}
		}
		view.getClean().addActionListener( this );
		view.getScoreBoard().addActionListener( this );		
		loadDatabase();		
		randomTime();
	}
	
	/**
	 * 
	 * Desabilita tabuleiro enquanto se conecta ao banco de dados
	 * Conecta ao banco de dados - Verifica jogo salvo
	 * Habilita tabuleiro
	 */
	public void loadDatabase() {
		view.disableBoard();
		view.getSuperiorLabel().setText( "Carregando.." );
		view.getInferiorLabel().setText( "Conectando.." );
		try {
			dbService = new DatabaseService();
			preloadGame();
			System.err.println( "CONNECTED TO THE DATABASE!" );
		} catch (SQLException e) {
			System.err.println( "UNABLE TO CONNECT TO DATABASE! " + e.getMessage() );
		}
		view.enableBoard();
	}
	
	/**
	 * 
	 * Mapeia os buttons
	 * Chama metodo do botao ao clicar no botao determinado 
	 */
	@Override
	public void actionPerformed( ActionEvent e ) {		
		if( e.getSource() == view.getClean() )
			view.confirmNewGame();
		else if( e.getSource() == view.getScoreBoard() )
			view.showScoreBoard();
		for( int row = 0; row < N; row++ ) {			
			for( int col = 0; col < N; col++ ) {
				if( e.getSource() == view.getMatrix()[row][col] ) {					
					makePlay( row, col );
				}
			}
		}
	}
	
	/**
	 * 
	 * Turno do X ? campo do tabuleiro vazio ? : aplica jogada
	 * Algum jogador venceu ? jogador atual vence : empate
	 * @param row - linha do tabuleiro
	 * @param col - coluna do tabuleiro
	 */
	public void makePlay( int row, int col ) {
		if( game.getTurn() == X ) {
			if( getBoard()[row][col] == 0 ) {								
				putX( row, col );							
				whosVictory();
				checkDrawGame();
			} else
				view.putBusy( game.getTurn() );
		} else {
			if( getBoard()[row][col] == 0 ) {				
				putO( row, col );
				whosVictory();
				checkDrawGame();
			} else
				view.putBusy( game.getTurn() );
		}		
	}
	
	/**
	 * 
	 * Existe jogadores ? Aplica ranking de jogadores
	 * @return ranking de jogadores ganhadores em ordem decrescente
	 */
	public String[][] consultScoreBoard() {		
		String[][] ranking = new String[10][2];
		dbService.bestPlayers();
		for( int row = 0; row < 10; row++ ) {
			try {
				if( dbService.getResult().next() ) {
					ranking[row][0] = dbService.getScore(1);					
					ranking[row][1] = dbService.getScore(2);
				}
			} catch ( SQLException exc ) {
				System.err.println( "CONSULTING SCORE ERROR: " + exc.getMessage() );
			}
		}
		return ranking;
	}

	/**
	 * 
	 * Aplica 'O' no modelo
	 * Conta jogada
	 * Aplica 'O' na view
	 */
	@Override
	public void putO( int x, int y ) {
		game.setMatrix( x, y, O );
		game.setPlays();
		view.putO( x, y );		
	}
	
	/**
	 * 
	 * Aplica 'X' no modelo
	 * Conta jogada
	 * Aplica 'X' na view
	 */
	@Override
	public void putX( int x, int y ) {
		game.setMatrix( x, y, X );
		game.setPlays();
		view.putX( x, y );		
	}
	
	/**
	 * 
	 * Sorteia turno
	 * Turno do player 1 ? Aplica modelo e view ao player 1 :
	 * Turno do player 2 ? Aplica modelo e view ao player 2
	 */
	public void randomTime() {
		if( ( random.nextInt( X ) + O == O ? O : X ) == O )
			game.setTurn( O );
		else
			game.setTurn( X );
		view.putPlayer( game.getTurn(), game.isWinner() );
	}
	
	/**
	 * 
	 * Turno do 'O' ? 'O' : 'X'
	 */
	@Override
	public int whosTurn() {
		return game.getTurn() != O ? O : X;
	}

	/**
	 * 
	 * Tabuleiro coincide com gabarito ? Define ganhador : -1
	 * Retorna m�todo para input do vencedor
	 */
	@Override
	public int whosVictory() {
		if( game.getMatrixValue( 0, 0 ) == game.getTurn() && 
			game.getMatrixValue( 0, 1 ) == game.getTurn() &&
			game.getMatrixValue( 0, 2 ) == game.getTurn() ||
			game.getMatrixValue( 1, 0 ) == game.getTurn() && 
			game.getMatrixValue( 1, 1 ) == game.getTurn() &&
			game.getMatrixValue( 1, 2 ) == game.getTurn() ||
			game.getMatrixValue( 2, 0 ) == game.getTurn() && 
			game.getMatrixValue( 2, 1 ) == game.getTurn() &&
			game.getMatrixValue( 2, 2 ) == game.getTurn() ||
			game.getMatrixValue( 0, 0 ) == game.getTurn() && 
			game.getMatrixValue( 1, 0 ) == game.getTurn() &&
			game.getMatrixValue( 2, 0 ) == game.getTurn() ||
			game.getMatrixValue( 0, 1 ) == game.getTurn() && 
			game.getMatrixValue( 1, 1 ) == game.getTurn() &&
			game.getMatrixValue( 2, 1 ) == game.getTurn() ||
			game.getMatrixValue( 0, 2 ) == game.getTurn() && 
			game.getMatrixValue( 1, 2 ) == game.getTurn() &&
			game.getMatrixValue( 2, 2 ) == game.getTurn() ||
			game.getMatrixValue( 0, 0 ) == game.getTurn() && 
			game.getMatrixValue( 1, 1 ) == game.getTurn() &&
			game.getMatrixValue( 2, 2 ) == game.getTurn() ||
			game.getMatrixValue( 0, 2 ) == game.getTurn() && 
			game.getMatrixValue( 1, 1 ) == game.getTurn() &&
			game.getMatrixValue( 2, 0 ) == game.getTurn()) {
			game.setWinner( true );	
			view.disableBoard();
			view.putPlayer( game.getTurn(), game.isWinner() );
			return whichVictory();				
		}
		game.setWinner( false );
		game.setTurn( whosTurn() );
		view.putPlayer( game.getTurn(), game.isWinner() );
		return -1;
	}

	/**
	 * 
	 * Chama janela
	 * Grava string somente de tamanho 3
	 * Chama metodo para gravar no banco
	 */
	@Override
	public int whichVictory() {
		boolean pass = false;
		do {
			game.setPlayer( view.whichVictory().toUpperCase() );
			if( game.getPlayer().length() == N )				
				pass = !pass;
		}while( !pass );
		dbService.saveScore( game.getPlayer(), game.getPlays(), game.getNow() );
		return game.getTurn();
	}
	
	/**
	 * 
	 * Atingiu limite de jogadas sem vencedor ? EMPATE : nada
	 */
	public void checkDrawGame() {
		if( game.getPlays() == LIMITE && game.isWinner() == false ){			
			view.putDrawGame();
		}		
	}

	/**
	 * 
	 * Jogo sem dados = novo jogo
	 */
	@Override
	public void newGame() {
		game.setMatrix( 0 );
		game.setPlays( 0 );
		for( int row = 0; row < N; row++ ) {			
			for( int col = 0; col < N; col++ ) {
				view.putBlank( row, col );					
			}
		}
		randomTime();
	}

	/**
	 * 
	 * Salva estado do tabuleiro
	 * Salva quantidade de jogadas
	 */
	@Override
	public void saveGame() {
		dbService.saveGame( getBoard(), game.getPlays() );
	}

	/**
	 * 
	 * Existe jogo salvo ? chama view : nada
	 */
	public void preloadGame() {
		if( dbService.preloadGame() ) {
			view.confirmLoadGame( this );				
		}
	}
	
	/**
	 * 
	 * Recupera ultimo jogo Salvo
	 * Aplica modelo com os dados recuperados
	 * Aplica view com os dados recuperados 
	 */
	@Override
	public void loadGame() {
		game.setMatrix( dbService.loadBoard() );
		game.setPlays( dbService.loadMoves() );		
		for( int row = 0; row < N; row++ ) {
			for( int col = 0; col < N; col++ ) {				
				if( game.getMatrixValue( row, col ) == O ) {
					view.putO( row, col );
				} else if( game.getMatrixValue( row, col ) == X ) {
					view.putX( row, col );
				} else
					view.putBlank( row, col );
			}
		}		
	}

	/**
	 * 
	 * Recupera modelo do tabuleiro
	 */
	@Override
	public int[][] getBoard() {
		return game.getMatrix();
	}	

}
