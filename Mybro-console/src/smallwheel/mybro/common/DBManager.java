package smallwheel.mybro.common;


import java.sql.*;

import org.apache.log4j.Logger;

public class DBManager {
	
	private final static Logger LOGGER = Logger.getLogger(DBManager.class);
	private Connection con_mssql = null;
	private Connection con_mysql = null;
	private Connection con_oracle = null;
	
	private boolean OKMSSQL = false;
	private boolean OKMYSQL = false;
	private boolean OKORACLE = false;
	
	PreparedStatement pstmt = null;

	/**
	 *연결이 끊어진 곳이 있는지 확인한다.
	 */
	public void checkConnection(String dbms) {
		LOGGER.info("MSG] " + dbms + " connect");

		if (dbms.equalsIgnoreCase("MSSQL")) {
			setConnectionMSSQL(ENV.serverIp, ENV.port, ENV.userId, ENV.userPass, ENV.dbName);
		} else if (dbms.equalsIgnoreCase("MYSQL")) {
			setConnectionMYSQL(ENV.serverIp, ENV.port, ENV.userId, ENV.userPass, ENV.dbName);
		} else if (dbms.equalsIgnoreCase("ORACLE")) {
			setConnectionOracle(ENV.serverIp, ENV.port, ENV.userId, ENV.userPass, ENV.dbName);
		}
	}

	/**
	 * 연결이 끊어진 곳이 있는지 확인한다.
	 * @param ip 데이터베이스 IP
	 * @param id 아이디
	 * @param pw 비번
	 * @param dbname 데이터베이스명
	 */
	public Connection getConnection(String dbms) {
		Connection con = null;
		if ("MSSQL".equalsIgnoreCase(dbms)) {
			con = con_mssql;
		} else if ("MYSQL".equalsIgnoreCase(dbms)) {
			con = con_mysql;
		} else if ("ORACLE".equalsIgnoreCase(dbms)) {
			con = con_oracle;
		}

		return con;
	}

	// 모두 닫는다.
	public void setClose() {
		setCloseConnectionMSSQL();
	}
	
	@SuppressWarnings("finally")
	public Statement getStatement() {
		Statement st = null;
		try {
			st = con_mssql.createStatement();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			return st;
		}
    }

	public void close() {
		destroyConnection(con_mssql);
	}

	/** 
	 * MS-SQL 연결
	 * @param ip 데이터베이스 IP
	 * @param port 데이터베이스 포트
	 * @param id 아이디
	 * @param pw 비번
	 * @param dbname 데이터베이스명
	 */
	public void setConnectionMSSQL(String ip, String  port, String id, String pw,String dbname) {
		try {
			OKMSSQL = false;
			destroyConnection(con_mssql);
//			String drivername = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
			String drivername = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			Class.forName(drivername);
//			String url = "jdbc:microsoft:sqlserver://" + ip + ":" + port + ";DatabaseName=" + dbname;
			String url = "jdbc:sqlserver://" + ip + ":" + port + ";databaseName=" + dbname;
			LOGGER.info(url);
			con_mssql = DriverManager.getConnection(url, id, pw);
			OKMSSQL = true;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			OKMSSQL = false;
			destroyConnection(con_mssql);
		}
	}
	
	/** 
	 * MYSQL 연결
	 * @param ip 데이터베이스 IP
	 * @param port 데이터베이스 포트
	 * @param id 아이디
	 * @param pw 비번
	 * @param dbname 데이터베이스명
	 */
	public void setConnectionMYSQL(String ip, String  port, String id, String pw, String dbname) {
		try {
			OKMYSQL = false;
			destroyConnection(con_mysql);

			String drivername = "com.mysql.jdbc.Driver";
			Class.forName(drivername);
			String url = "jdbc:mysql://" + ip + ":" + port + "/" + dbname;
			LOGGER.info("[mysql 연결] " + ip + ":" + port + " DB Connectionn 시도 id:" + id + " pass:" + pw);
			con_mysql = DriverManager.getConnection(url, id, pw);
			OKMYSQL = true;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			OKMYSQL = false;
			destroyConnection(con_mysql);
		}
	}
    
    /** 
	 * ORACLE 연결
	 * @param ip 데이터베이스 IP
	 * @param port 데이터베이스 포트
	 * @param id 아이디
	 * @param pw 비번
	 * @param dbname 데이터베이스명
	 */
	public void setConnectionOracle(String ip, String  port, String id, String pw, String dbname) {
		try {
			OKORACLE = false;
			destroyConnection(con_oracle);
			String drivername = "oracle.jdbc.OracleDriver";
			Class.forName(drivername);
			String url = "jdbc:oracle:thin:@" + ip + ":" + port + ":" + dbname;
			LOGGER.info("[DB연결] " + ip + " DB Connectionn 시도 id:" + id + " pass:" + pw);
			con_oracle = DriverManager.getConnection(url, id, pw);
			OKORACLE = true;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			OKORACLE = false;
			destroyConnection(con_oracle);
		}
	}

	/**
	 MSSQL Statement 리턴
	 */
	@SuppressWarnings("finally")
	public Statement getStatementMssql() {
		Statement st = null;
		try {
			if (OKMSSQL == true) {
				st = con_mssql.createStatement();
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			return st;
		}
	}

	/**
	 * MSSQL close
	 */
	public void setCloseConnectionMSSQL() {
		try {
			if (con_mssql != null) {
				con_mssql.close();
				con_mssql = null;
				OKMSSQL = false;
			}
		} catch (Exception e) {
			con_mssql = null;
			OKMSSQL = false;
		} finally {
			con_mssql = null;
			OKMSSQL = false;
		}
	}

	/**
	 * MSSQL connection 리턴
	 */
	public Connection getConnectionMSSQL() {
		return con_mssql;
	}

	/**
	 * MYSQL connection 리턴
	 * 
	 * @return
	 */
	public Connection getConnectionMYSQL() {
		return con_mysql;
	}

	/**
	 * ORACLE connection 리턴
	 * 
	 * @return
	 */
	public Connection getConnectionORACLE() {
		return con_oracle;
    }
    
	/**
	 * Connection 완전 초기화
	 */
	public void destroyConnection(Connection con) {
		try {
			if (con != null) {
				con.close();
			}
			con = null;
		} catch (Exception e1) {
			con = null;
		} finally {
			con = null;
		}
	}

	/**
	 * Connection 완전 초기화
	 * 
	 * @param st
	 */
	public void destroyStatement(Statement st) {
		try {
			if (st != null) {
				st.close();
			}
			st = null;
		} catch (Exception e1) {
			st = null;
		} finally {
			st = null;
		}
	}

	/**
	 * Connection 완전 초기화
	 */
	public void destroyResultSet(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
			rs = null;
		} catch (Exception e1) {
			rs = null;
		} finally {
			rs = null;
		}
	}
}