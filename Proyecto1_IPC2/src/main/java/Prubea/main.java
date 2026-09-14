/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Prubea;

import ClasesDAO.ConfiguracionDAO;
import ClasesDTO.ConfiguracionDTO;
import ConexionDB.ConnectionDB;
import Sevicios.ServicioCalculoPrecio;
import Sevicios.ServicioUsuarios;
import clasesAuxiliares.Departamento;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author dar333n
 */
public class main {
    public static void main(String[] args) throws SQLException {
        ConnectionDB cded=new ConnectionDB();
        Connection conec =cded.getConnection();
        
        ConfiguracionDAO cinf=new ConfiguracionDAO(conec);
        //ConfiguracionDTO confFn= new ConfiguracionDTO(20);
        //cinf.cambiarConfiguracion(confFn);
        
        ServicioCalculoPrecio servCalculo=new ServicioCalculoPrecio(conec);
            double km=servCalculo.calcularPrecioPrivado(Departamento.ESCUINTLA, Departamento.ESCUINTLA, null, true);
            System.out.println("Km: "+km);
        
        ServicioUsuarios servUsuar=new ServicioUsuarios(cded);
        
        
        //servUsuar.
        
        
        
        
        
    }
}
