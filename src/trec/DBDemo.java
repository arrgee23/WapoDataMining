package trec;

import java.sql.*;
import java.util.Properties;

public class DBDemo
{
  

  public static void main(String[] args) throws
                             ClassNotFoundException,SQLException
  {
    System.out.println(Constants.dbClassName);
    // Class.forName(xxx) loads the jdbc classes and
    // creates a drivermanager class factory
    Class.forName(Constants.dbClassName);

    // Properties for user and password. Here the user and password are both 'paulr'
    Properties p = new Properties();
    p.put("user",Constants.DB_USER);
    p.put("password",Constants.DB_PASSWORD);

    // Now try to connect
    Connection c = DriverManager.getConnection(Constants.CONNECTION,p);

    System.out.println("It works !");
    c.close();
    }
}