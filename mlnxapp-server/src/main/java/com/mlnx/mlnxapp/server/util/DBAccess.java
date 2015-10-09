package com.mlnx.mlnxapp.server.util;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class DBAccess {

	public static java.sql.Connection conn = null;

	private String sqlStr = "";
	
	 //数据库连接  
	  private static final String URL ="jdbc:mysql://localhost:3306/mlnxapp";  
	  private static final String NAME = "root";  
	  private static final String PASS = "123456";  
	  private static final String DRIVER ="com.mysql.jdbc.Driver";  

	public DBAccess()

	{

		// new sun.jdbc.odbc.JdbcOdbcDriver();

		try {

			Class.forName(DRIVER);

			conn = DriverManager.getConnection(URL, NAME, PASS);

		}

		catch (ClassNotFoundException ex) {

			System.out.println(ex.toString());

		}

		catch (SQLException sqlEx) {

			System.out.println(sqlEx.toString());

		}

	}

	public ResultSet Search() {

		ResultSet rset = null;

		sqlStr = "SELECT * FROM doctor";

		Statement smt = null;

		try {

			smt = conn.createStatement();

			rset = smt.executeQuery(sqlStr);

		}

		catch (SQLException ex) {

			System.out.println("Exception:" + ex.toString());

		}

		return rset;

	}

	public void getResultSetMetaData()

	{

		ResultSet rs = null;

		try {

//			String[] tp = { "TABLE" };

			rs = this.Search();

			ResultSetMetaData rsmd = rs.getMetaData();

			System.out.println("下面这些方法是ResultSetMetaData中方法");

			System.out.println("获得1列所在的Catalog名字 : " + rsmd.getCatalogName(1));

			System.out.println("获得1列对应数据类型的类 " + rsmd.getColumnClassName(1));

			System.out.println("获得该ResultSet所有列的数目 " + rsmd.getColumnCount());

			System.out.println("1列在数据库中类型的最大字符个数"
					+ rsmd.getColumnDisplaySize(1));

			System.out.println(" 1列的默认的列的标题" + rsmd.getColumnLabel(1));

			System.out.println("1列的模式" + rsmd.getSchemaName(1));

			System.out.println("1列的类型,返回SqlType中的编号 " + rsmd.getColumnType(1));

			System.out.println("1列在数据库中的类型，返回类型全名" + rsmd.getColumnTypeName(1));

			System.out.println("1列类型的精确度(类型的长度): " + rsmd.getPrecision(1));

			System.out.println("1列小数点后的位数 " + rsmd.getScale(1));

			System.out.println("1列对应的模式的名称（应该用于Oracle） "
					+ rsmd.getSchemaName(1));

			System.out.println("1列对应的表名 " + rsmd.getTableName(1));

			System.out.println("1列是否自动递增" + rsmd.isAutoIncrement(1));

			System.out.println("1列在数据库中是否为货币型" + rsmd.isCurrency(1));

			System.out.println("1列是否为空" + rsmd.isNullable(1));

			System.out.println("1列是否为只读" + rsmd.isReadOnly(1));

			System.out.println("1列能否出现在where中" + rsmd.isSearchable(1));

		}

		catch (SQLException ex) {

			ex.printStackTrace();

		}

	}

	public static void main(String args[])

	{

		DBAccess dbAccess = new DBAccess();

		dbAccess.getResultSetMetaData();

	}

}