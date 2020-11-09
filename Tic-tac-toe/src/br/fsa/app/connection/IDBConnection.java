package br.fsa.app.connection;

import java.time.LocalDateTime;

public interface IDBConnection {
	
	public void saveScore(String winner, int moves, LocalDateTime timeStamp);

	public String getScore(int qtd);
}
