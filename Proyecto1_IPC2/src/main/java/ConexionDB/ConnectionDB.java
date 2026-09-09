/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author dar333n
 */
public class ConnectionDB {
    
    private static final String IP = "localhost";
    private static final int PUERTO = 3306;
    private static final String SCHEMA = "AppWeb_Buses";
    public static final String USER_NAME = "daren";
    public static final String PASSWORD = "Gye36_ftoc6";
    
    public static final String URL = "jdbc:mysql://"
            + IP + ":" + PUERTO + "/" + SCHEMA;
    
    private Connection connection;
    
    public void conectar() throws SQLException{
        if(connection==null || connection.isClosed()){
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            System.out.println("Se creo la conexion");
        }
    }

    public void cerrarConexion() throws SQLException{
        if(connection!=null && !connection.isClosed()){
            connection.close();
            System.out.println("Conexion cerrada");
        }
    } 

    public Connection getConnection() throws SQLException{
        conectar();
        return connection;
    }
}
