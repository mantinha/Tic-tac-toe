package br.fsa.app.connection;
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
public enum SqlString {
	
	INSERTWINNER( "INSERT INTO WINNER(PLAYER,MOVES,HISTORY) VALUES(?,?,?)" ),
	INSERTGAME( "INSERT INTO GAME(M1N1,M1N2,M1N3,M2N1,M2N2,M2N3,M3N1,M3N2,M3N3,MOVES) VALUES (?,?,?,?,?,?,?,?,?,?)" ),
	SELECTGAME( "SELECT * FROM GAME ORDER BY ID DESC LIMIT 1" ),
	SELECTPLAYERS( "SELECT PLAYER, COUNT(PLAYER) FROM WINNER GROUP BY PLAYER ORDER BY COUNT(PLAYER) DESC" );
	
	private String sql;
	
	SqlString( String sql ) {
		this.sql = sql;
	}

	public String getSql() {
		return sql;
	}	
	
}
