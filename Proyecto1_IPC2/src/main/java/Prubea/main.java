/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Prubea;

import ConexionDB.ConnectionDB;
import java.sql.SQLException;

/**
 *
 * @author dar333n
 */
public class main {
    public static void main(String[] args) throws SQLException {
        ConnectionDB cded=new ConnectionDB();
        cded.conectar();
        
        
    }
}
