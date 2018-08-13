/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swlab.ic.uff.br.ConnectionMysql;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author angelo
 */
public class ConnectionBD {
    
    public static java.sql.Connection Connect() throws ClassNotFoundException {
        java.sql.Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Features_Completo?autoReconnect=true&useSSL=false","root","123");
            return connection; 
        } catch (SQLException e) {
            System.out.println("Error connection mysql" + e);
            return null;
        }

    }
    
    
    
    
}
