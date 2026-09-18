/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sevicios;

import ClasesDAO.BusDAO;
import ClasesDAO.RutaDAO;
import ClasesDTO.BusDTO;
import ClasesDTO.RegistroTallerDTO;
import ClasesDTO.RutaDTO;
import ConexionDB.ConnectionDB;
import clasesAuxiliares.EstadoBus;
import clasesAuxiliares.Exceptions.DatoInvalidoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author dar333n
 */
public class servicioAdminSucursal {
    
    private ConnectionDB adminConexion;

    public servicioAdminSucursal(ConnectionDB adminConexion) {
        this.adminConexion = adminConexion;
    }
    
    public boolean registrarBus(BusDTO bus) throws SQLException, DatoInvalidoException{
        try (Connection cone = adminConexion.getConnection()){
            BusDAO busDAO = new BusDAO(cone);
            
            if (bus.getNumeroPlaca()==null || bus.getNumeroPlaca().trim().isEmpty()){
                throw new DatoInvalidoException();
            }

            if (bus.getCapacidad() <= 0){
                throw new DatoInvalidoException();
            }

            if (bus.getEstadoBus()==null){
                bus.setEstadoBus(EstadoBus.DISPONIBLE);
            }

            BusDTO busExistente = busDAO.obtenerBusConPlaca(bus.getNumeroPlaca());
            if (busExistente != null){
                throw new SQLException();
            }

            return busDAO.crearBus(bus);

        }
    }
    
    public boolean editarBus(BusDTO bus) throws SQLException, DatoInvalidoException{
        try(Connection cone=adminConexion.getConnection()){
            
            if(bus.getCapacidad()<= 0){
                throw new DatoInvalidoException();
            }

        
            BusDAO busDAO= new BusDAO(cone);

            BusDTO busExistente= busDAO.obtenerBusConPlaca(bus.getNumeroPlaca());
            if(busExistente==null){
                throw new SQLException("El bus a modificar no existe");
            }

            return busDAO.modificarBus(bus);
        }
    }
    
    public boolean desactivarBus(String placa) throws SQLException{
        try(Connection cone=adminConexion.getConnection()) {
            BusDAO busDAO= new BusDAO(cone);

            BusDTO bus=busDAO.obtenerBusConPlaca(placa);
            if(bus==null){
                throw new SQLException("El bus no existe");
            }

            return busDAO.desactivarBUs(placa);
        }
    }
    
    public BusDTO buscarBusPorPlaca(String placa) throws SQLException {
        try(Connection cone=adminConexion.getConnection()) {
            BusDAO busDAO= new BusDAO(cone);
            return busDAO.obtenerBusConPlaca(placa);
        }
    }
    
    public boolean registrarMantenimientoTaller(RegistroTallerDTO registro) throws SQLException, DatoInvalidoException{
        try(Connection cone=adminConexion.getConnection()){
           try { 
            BusDAO busDAO= new BusDAO(cone);
            cone.setAutoCommit(false);
            
            if(registro.getMontoManoDeObra()< 0 || registro.getMontoRepuestos() < 0){
                throw new DatoInvalidoException("No pued ingresar numero negativos");
            }

            BusDTO bus= busDAO.obtenerBusConPlaca(registro.getNumeroPlaca());
            if(bus==null){
                throw new SQLException();
            }

            if(!busDAO.registrarMantenimiento(registro)){
                throw new SQLException("No se pudo insertar el registro de mantenimiento");
            }

            cone.commit();
            return true;
            }catch(SQLException | DatoInvalidoException e){
                try {
                    cone.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                throw e;
            }finally {
                try {
                    cone.setAutoCommit(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    public List<RegistroTallerDTO> obtenerHistorialTallerBus(String placa) throws SQLException, DatoInvalidoException{
        try(Connection cone=adminConexion.getConnection()){
            BusDAO busDAO = new BusDAO(cone);
            return busDAO.obtenerRegistrosBus(placa);
        }
    }
    
    //RUTAS
    public boolean crearRuta(RutaDTO ruta) throws SQLException, DatoInvalidoException{
        try(Connection cone=adminConexion.getConnection()){
            RutaDAO rutaDAO = new RutaDAO(cone);
            
            if(ruta.getDistanciaApoximada() <= 0){
                throw new DatoInvalidoException("La distancia aproximada debe ser mayor a 0");
            }

            if(ruta.getPrecioBoleto()<= 0){
                throw new DatoInvalidoException("El precio del asiento debe ser mayor a 0");
            }

            if(ruta.getIdSucursalOrigen()==ruta.getIdSucursalDestino()){
                throw new DatoInvalidoException();
            }

            
            return rutaDAO.crearRuta(ruta);
        }
    }
    
    
    public boolean modificarRuta(RutaDTO ruta) throws SQLException, DatoInvalidoException{
        try(Connection cone=adminConexion.getConnection()){
            
            RutaDAO rutaDAO = new RutaDAO(cone);
            
            if(ruta.getDistanciaApoximada()<= 0){
                throw new DatoInvalidoException();
            }

            if(ruta.getPrecioBoleto()<= 0){
                throw new DatoInvalidoException();
            }

            if(ruta.getIdSucursalOrigen()== ruta.getIdSucursalDestino()){
                throw new DatoInvalidoException();
            }

            RutaDTO rutaExistente = rutaDAO.obtenerConId(ruta.getIdRuta());
            if(rutaExistente == null){
                throw new SQLException("La ruta especificada no existe");
            }

            return rutaDAO.modificarRuta(ruta);
        }
    }
    
    public RutaDTO buscarRutaPorId(int idRuta) throws SQLException{
        try(Connection cone=adminConexion.getConnection()){
            RutaDAO rutaDAO= new RutaDAO(cone);
            return rutaDAO.obtenerConId(idRuta);
        }
    }
    
    
    public List<RutaDTO> obtenerRutasDeSucursal(int idSucursal) throws SQLException {
        try(Connection cone=adminConexion.getConnection()){
            RutaDAO rutaDAO=new RutaDAO(cone);
            return rutaDAO.obtenerRutasSucursal(idSucursal);
        }
    }
    
    public boolean eliminarODesactivarRuta(int idRuta)throws SQLException, DatoInvalidoException{
        try(Connection cone=adminConexion.getConnection()){
            RutaDAO rutaDAO= new RutaDAO(cone);

            RutaDTO ruta= rutaDAO.obtenerConId(idRuta);
            if(ruta==null){
                throw new DatoInvalidoException();
            }

            if(rutaDAO.tieneViajesAsociadso(idRuta)){
                return rutaDAO.desactivarRuta(idRuta);
            } else {
                return rutaDAO.eliminarRuta(idRuta);
            }
        }
    }
}
