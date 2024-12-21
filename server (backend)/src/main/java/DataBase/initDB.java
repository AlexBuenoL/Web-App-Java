package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;

public class initDB {
    
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/pr2","pr2","pr2");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } 
        return connection;
    } 
    
    public static void closeConnection(Connection c) {
        if (c != null) {
            try {
                c.close();
            }
            catch (Exception e) {
                System.err.println(e.getMessage());
            } 
        }
    }
}


