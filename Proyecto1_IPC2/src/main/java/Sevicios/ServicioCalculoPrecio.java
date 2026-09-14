/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sevicios;

import ClasesDAO.ConfiguracionDAO;
import ClasesDTO.ConfiguracionDTO;
import clasesAuxiliares.Departamento;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author dar333n
 */
public class ServicioCalculoPrecio {
    
    
    private Connection cone;

    public ServicioCalculoPrecio(Connection connection) {
        this.cone = connection;
    }
    
    public double calcularPrecioPrivado(Departamento depSucursal, Departamento depEncuentro, Departamento depDestino, 
            boolean tieneRetorno) throws SQLException{

            ConfiguracionDAO confDao=new ConfiguracionDAO(cone);
            
            int distanciaSucEnc = Departamento.distanciaEntreDepartamentos(depSucursal, depEncuentro);
            int distcanciaEncDes= Departamento.distanciaEntreDepartamentos(depEncuentro, depDestino);
            int distcanciRet= 0;

            if(tieneRetorno){
                distcanciRet=distcanciaEncDes;
            }

            int distanciaTotal= distanciaSucEnc+distcanciaEncDes+distcanciRet;
            ConfiguracionDTO confFinal=confDao.obtenerConfiguracionActual();
            
            return distanciaTotal*confFinal.getMontoPorKm();
    }

}
