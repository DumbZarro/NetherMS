package nether.dao;

import java.sql.*;

public class DB {
	
	private String path;			// path to sqlite file
	private Connection conn;		// Connection to DB
	private Statement statement;	// SQL 语句
	
	/**
	 * 全局的DB连接
	 */
	private static DB GLOBALDB;
	static {
		GLOBALDB = new DB();
	}
	public static DB getGlobalDB() {
		return (null != GLOBALDB.conn) ? GLOBALDB : null;
	}
	
	/**
	 * 通过
	 * @param path
	 */
	public DB(String path) {
		this.path = path;
		try {
			this.conn = DriverManager.getConnection(this.path);
			this.statement = this.conn.createStatement();
			this.statement.setQueryTimeout(5);
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			this.conn = null;
			this.statement = null;
		} catch (Exception e) {
			e.printStackTrace();
			this.conn = null;
			this.statement = null;
		}
	}
	
	/**
	 * 默认是通过内存进行存储
	 * （也方便测试）
	 */
	public DB() {
//		this("jdbc:sqlite::memory:");
//		this("jdbc:sqlite:E:\\nether.db");
		this("jdbc:sqlite:./nether.db");
	}
	
	
	/**
	 * 关闭连接
	 */
	public void close() {
		try {
			this.conn.close();
			this.statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// ==== getter == & == setter ============
	
	public Statement getStatement() {
		return this.statement;
	}
	
	// ==== getter == & == setter ============
}
