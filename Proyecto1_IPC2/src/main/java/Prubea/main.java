/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Prubea;

import ClasesDAO.ConfiguracionDAO;
import ClasesDAO.SucursalDAO;
import ClasesDTO.ConfiguracionDTO;
import ClasesDTO.Cuentas.CuentaDTO;
import ClasesDTO.SucursalDTO;
import ConexionDB.ConnectionDB;
import Sevicios.ServicioCalculoPrecio;
import Sevicios.ServicioUsuarios;
import clasesAuxiliares.Departamento;
import clasesAuxiliares.Exceptions.DatoInvalidoException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author dar333n
 */
public class main {
    public static void main(String[] args) throws SQLException, DatoInvalidoException {
        ConnectionDB cded=new ConnectionDB();
        Connection conec =cded.getConnection();
        
        ConfiguracionDAO cinf=new ConfiguracionDAO(conec);
        //ConfiguracionDTO confFn= new ConfiguracionDTO(20);
        //cinf.cambiarConfiguracion(confFn);
        
        ServicioCalculoPrecio servCalculo=new ServicioCalculoPrecio(conec);
            double km=servCalculo.calcularPrecioPrivado(Departamento.ESCUINTLA, Departamento.ESCUINTLA, null, true);
            System.out.println("Km: "+km);
        
        ServicioUsuarios servUsuar=new ServicioUsuarios(cded);
        
        
        //if(servUsuar.registrarUsuarioo("123", "admSistema1@gmail.com", "1234567890122", "AdminSis1", "123456790", "Quetzaltenango", "23232323", "ADMINISTRADOR_SISTEMA")){
        //    System.out.println("Se agrego correctamente");
        //}
        
        /*
        if(servUsuar.registrarUsuarioo("123", "adminSucursal1@gmail.com", "1234445353432", "AdminSuc1", "12345234", "Quetzltenango", "22330909", "ADMINISTRADOR_SUCURSAL")){
            System.out.println("se registro correctamente");
            SucursalDAO sucursal=new SucursalDAO(cded.getConnection());
            SucursalDTO sucDto=new SucursalDTO("Zona 1", "1234445353432", Departamento.GUATEMALA);
            if(sucursal.crearSucursal(sucDto)){
                System.out.println("Se creo correcatemnte");
            }
        }
        */
        
        
        
        
        
        
        
        
        
    }
}
