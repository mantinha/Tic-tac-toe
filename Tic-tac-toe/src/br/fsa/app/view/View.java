package br.fsa.app.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import br.fsa.app.controller.Controller;

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
 * --------
 * |      |   --------------
 * | View |   |            |
 * |      |---| Controller |
 * --------   |            |
 *            --------------
 * @version 1.0.0
 */
public class View extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private static final int N = 3;	
	private static final Font ARIALSD = new Font( "Arial", Font.BOLD, 50 );
	private static final Font ARIAL = new Font( "Arial", Font.BOLD, 75 );
	private static final Font ARIALBTN = new Font( "Arial", Font.BOLD, 120 );
	
	JPanel higherPanel 		= new JPanel();
	JPanel bottomPanel 		= new JPanel();
	JPanel sidePanel 		= new JPanel();
	JPanel boardPanel 		= new JPanel();
	JLabel superiorLabel 	= new JLabel();	
	JLabel inferiorLabel 	= new JLabel();
	JButton[][] matrix 		= new JButton[N][N];
	JButton clean 			= new JButton( "<html><center>LIMPA<br>TABULEIRO</html>" );
	JButton scoreBoard 		= new JButton( "PLACAR" );	
	Controller control;
	
	/**
	 * 
	 * Constroi toda a interface grafica
	 */
	public View(){
		
		setDefaultCloseOperation( EXIT_ON_CLOSE );
		confirmSaveGame();
		setTitle( "Jogo da Velha" );
		setSize( 1000, 720 );
		setLocationRelativeTo( null );
		setLayout( new BorderLayout() );
		setVisible( true );
		
		superiorLabel.setBackground( Color.GRAY );
		superiorLabel.setForeground( Color.BLUE );
		superiorLabel.setFont( ARIAL );
		superiorLabel.setHorizontalAlignment( JLabel.CENTER );
		superiorLabel.setText( "PLAYER O" );
		superiorLabel.setOpaque( true );
		
		inferiorLabel.setBackground( Color.GRAY );
		inferiorLabel.setForeground( Color.RED );
		inferiorLabel.setFont( ARIAL );
		inferiorLabel.setHorizontalAlignment( JLabel.CENTER );
		inferiorLabel.setText( "PLAYER X" );
		inferiorLabel.setOpaque( true );
		
		higherPanel.setLayout( new BorderLayout() );
		higherPanel.setBounds( 0, 0, 640, 100 );
		
		bottomPanel.setLayout( new BorderLayout() );
		bottomPanel.setBounds( 0, 0, 640, 100 );
		
		sidePanel.setLayout( new GridLayout( 0, 1 ) );
		sidePanel.setBounds( 0, 0, 640, 800 );		
		
		boardPanel.setLayout( new GridLayout( 3, 3 ) );		
		
		for( int row = 0; row < N; row++ ) {
			for( int col = 0; col < N; col++ ) {
				matrix[row][col] = new JButton();
				boardPanel.add( matrix[row][col] );
				matrix[row][col].setBackground( Color.WHITE );
				matrix[row][col].setFont( ARIALBTN );
				matrix[row][col].setFocusable( false );
			}
		}
		
		higherPanel.add( superiorLabel );
		bottomPanel.add( inferiorLabel );
		sidePanel.add( clean );
		sidePanel.add( scoreBoard );	
		
		clean.setFont( ARIALSD );
		clean.setFocusable( false );
		
		scoreBoard.setFont( ARIAL );
		scoreBoard.setFocusable( false );
		
		add( higherPanel, BorderLayout.NORTH );
		add( bottomPanel, BorderLayout.SOUTH );
		add( sidePanel, BorderLayout.EAST );
		add( boardPanel );
		
		control = new Controller( this );
	}
	
	/**
	 * Realiza chamada da janela para carregar jogo
	 * @param control - instancia do controlador
	 */
	public void confirmLoadGame( Controller control ) {
		if( JOptionPane.showConfirmDialog( 
									this, 
									"Deseja carregar ultimo jogo salvo?", 
									"Carregar Jogo", 
									JOptionPane.YES_NO_OPTION ) == 0 )
			control.loadGame();
	}
	
	/**
	 * 
	 * Realiza chamada da janela para salvar jogo
	 */
	public void confirmSaveGame() {
		addWindowListener( new WindowAdapter() {
			public void windowClosing( WindowEvent e ) {				
				if ( JOptionPane.showConfirmDialog(
									null, 
									"Deseja salvar o jogo?", 
									"Salvar Jogo",
									JOptionPane.YES_NO_OPTION ) == 0 )
					control.saveGame();
			}
		});
	}
	
	/**
	 * 
	 * Realiza chamada da janela para limpar jogo
	 */
	public void confirmNewGame() {
		if( JOptionPane.showConfirmDialog( 
									this, 
									"Deseja limpar o tabuleiro?", 
									"Confirmar limpeza", 
									JOptionPane.YES_NO_OPTION ) == 0 ) {
			enableBoard();
			control.newGame();
		}
	}
	
	/**
	 * 
	 * Realiza chamada da janela do ganhador para gravar iniciais
	 */
	public String whichVictory() {
		return JOptionPane.showInputDialog( "Insira suas iniciais ( 3 Caracteres )" );
	}
	
	/**
	 * 
	 * Aplica 'O' ao botao
	 * Aplica cor AZUL a Font do botao
	 * @param row - linha do tabuleiro
	 * @param col - coluna do tabuleiro
	 */
	public void putO( int row, int col ) {
		getMatrix()[row][col].setText( "O" );
		getMatrix()[row][col].setForeground( Color.BLUE );	
	}
	
	/**
	 * 
	 * Aplica 'X' ao botao
	 * Aplica cor VERMELHO a Font do botao
	 * @param row - linha do tabuleiro
	 * @param col - coluna do tabuleiro
	 */
	public void putX( int row, int col ) {
		getMatrix()[row][col].setText( "X" );
		getMatrix()[row][col].setForeground( Color.RED );
	}
	
	/**
	 * 
	 * Aplica nada ao botao
	 * @param row - linha do tabuleiro
	 * @param col - coluna do tabuleiro
	 */
	public void putBlank( int row, int col ) {
		getMatrix()[row][col].setText( " " );
	}
	
	/**
	 * 
	 * Aplica EMPATE aos rotulos
	 */
	public void putDrawGame() {
		getSuperiorLabel().setText( "EMPATE!" );
		getInferiorLabel().setText( "EMPATE!" );
		disableBoard();
	}
	
	/**
	 * 
	 * Aplica CAMPO OCUPADO aos rotulos
	 * @param player
	 */
	public void putBusy( int player ) {
		if( player == 1 )
			getSuperiorLabel().setText( "CAMPO OCUPADO!" );
		else
			getInferiorLabel().setText( "CAMPO OCUPADO!" );
	}
	
	/**
	 * 
	 * Aplica jogador ao rotulo
	 * @param player - 1 ou 2
	 */
	public void putPlayer( int player , boolean winner ) {
		if( !winner ) {
			if( player == 1 ) {
				getSuperiorLabel().setText( "Jogador 1 'O'" );
				getInferiorLabel().setText( " " );
			} else {
				getSuperiorLabel().setText( " " );
				getInferiorLabel().setText( "Jogador 2 'X'" );
			}
		} else {
			if( player == 1 ) {
				getSuperiorLabel().setText( "Jogador 1 GANHOU!" );
				getInferiorLabel().setText( " " );
			} else {
				getSuperiorLabel().setText( " " );
				getInferiorLabel().setText( "Jogador 2 GANHOU!" );
			}
		}		
	}
	
	/**
	 * 
	 * Habilita tabuleiro
	 */
	public void enableBoard() {
		for( int row = 0; row < N; row++ ) {
			for( int col = 0; col < N; col++ ) {
				getMatrix()[row][col].setEnabled( true );
			}
		}
	}
	
	/**
	 * 
	 * Desabilita tabuleiro
	 */
	public void disableBoard() {
		for( int row = 0; row < N; row++ ) {
			for( int col = 0; col < N; col++ ) {
				getMatrix()[row][col].setEnabled( false );
			}
		}
	}
	
	/**
	 * 
	 * Realiza chama da janela com uma tabela
	 * Tabela: 
	 *  |_Chama dados de consulta realizado na camada de controle
	 */
	public void showScoreBoard() {		
		String[] col = { "Jogador", "Vitorias" };
		JTable table = new JTable( control.consultScoreBoard(), col );
		JOptionPane.showMessageDialog( this, new JScrollPane( table ) );
	}

	/**
	 * 
	 * Getters.. Setters.. 
	 */
	public JButton[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(JButton[][] matrix) {
		this.matrix = matrix;
	}

	public JButton getClean() {
		return clean;
	}

	public void setClean(JButton clean) {
		this.clean = clean;
	}

	public JButton getScoreBoard() {
		return scoreBoard;
	}

	public void setScoreBoard(JButton scoreBoard) {
		this.scoreBoard = scoreBoard;
	}

	public JLabel getSuperiorLabel() {
		return superiorLabel;
	}

	public void setSuperiorLabel(JLabel superiorLabel) {
		this.superiorLabel = superiorLabel;
	}

	public JLabel getInferiorLabel() {
		return inferiorLabel;
	}

	public void setInferiorLabel(JLabel inferiorLabel) {
		this.inferiorLabel = inferiorLabel;
	}
	
}
