package com.zs.framework.util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConectionFactory {
	//获取数据库连接池
	// 获得数据库连接 --- 通过c3p0连接池
	// 自动读取c3p0-config.xml
	private  static  DataSource  dataSource = new ComboPooledDataSource();
	public static DataSource getDataSource() {
		return  dataSource;
	}
	
	//获取连接
	public static Connection getConnection() {
		try {
			return  dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//关闭资源
	public static void close(Connection conn,PreparedStatement preparedStatement,ResultSet rs) {
		try {
			if(rs != null) rs.close();
			if(preparedStatement != null) preparedStatement.close();
			if(conn != null)  conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
