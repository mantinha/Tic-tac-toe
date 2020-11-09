package br.fsa.app.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
public class DatabaseConfig {

	private Connection connection;	
	private final String PROTOCOL = "jdbc:mysql://";
	private final String HOST = "localhost:3306";
	private final String DATABASE = "/tictactoe";
	private final String PROPERTY = "?useTimezone=true&serverTimezone=UTC";
	private final String USER = "root";
	private final String PASSWORD = "";
	
	/**
	 * 
	 * Base de conexao JDBC to MySQL
	 * @throws SQLException 
	 */
	public DatabaseConfig() throws SQLException {
			connection = DriverManager.getConnection( PROTOCOL +
					HOST +
					DATABASE +
					PROPERTY,
					USER,
					PASSWORD );
	}
	
	public Connection getConnection() {
		return connection;
	}
	
}
