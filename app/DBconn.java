package app;

import java.sql.*;
import java.util.Properties;

public abstract class DBconn {
	protected Connection conn;
    public DBconn() {
    }
    public void connect() {
    	try {
            Class.forName("com.mysql.cj.jdbc.Driver"); //.newInstance() deprecated
	    // Class.forName("com.mysql.cj.jdbc.Driver"); when you are using MySQL 8.0.
	    // Properties for user and password.
            Properties p = new Properties();
            p.put("user", "root");
            p.put("password", "*****"); //insert correct database password here, and correct database name on the next line
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/*****?autoReconnect=true&useSSL=false&serverTimezone=UTC",p);
        } catch (Exception e)
    	{
            throw new RuntimeException("Unable to connect", e);
    	}
    }
}
