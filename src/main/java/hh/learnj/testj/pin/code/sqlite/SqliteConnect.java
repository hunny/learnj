package hh.learnj.testj.pin.code.sqlite;

import java.sql.Connection;

public interface SqliteConnect<T> {
	
	public T doConnect(Connection connect);
}
