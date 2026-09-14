/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sevicios;

import ClasesDAO.CuentaDAO;
import ClasesDAO.SucursalDAO;
import ClasesDAO.ViajesDAO;
import ClasesDTO.Viajes.ViajePrivadoDTO;
import ClasesDTO.Viajes.ViajeRegularDTO;
import ConexionDB.ConnectionDB;
import clasesAuxiliares.Departamento;
import clasesAuxiliares.EstadoViaje;
import clasesAuxiliares.Exceptions.DatoInvalidoException;
import clasesAuxiliares.TipoViaje;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dar333n
 */
public class ServicioViajePrivado {
    
     
    
    private ConnectionDB adminConexion;

    public ServicioViajePrivado(ConnectionDB connection) {
        this.adminConexion = connection;
    }
    
    
    //CLIENTE SOLICITA
    public boolean solicitarViajePrivado(String origen, String destino, String numeroPasajeros, 
            String fechasalida, String fechaRetorno, String dpiCliente, String departamentoOrigen,
            String departamentoDestino, String idSucursal) throws DatoInvalidoException, SQLException{
        
        try (Connection cone= adminConexion.getConnection()){
            try{
                ViajesDAO viajesDAO = new ViajesDAO(cone);
                ServicioCalculoPrecio calculoPrecioService = new ServicioCalculoPrecio(cone);
                SucursalDAO sucursalDao= new SucursalDAO(cone);
                
                cone.setAutoCommit(false);
                
                int idSucursalFn;
                int cantidadPasajerosFn;
                LocalDate fechaSalidaFn;
                LocalDate fechaRetornoFn=null; 
                Departamento depSucursalFn;
                Departamento depEncuentroFn;
                Departamento depDestinoFn;
                try {
                    idSucursalFn = Integer.parseInt(idSucursal);
                    cantidadPasajerosFn = Integer.parseInt(numeroPasajeros);
                    fechaSalidaFn = LocalDate.parse(fechasalida);
                    
                    if (fechaRetorno != null && !fechaRetorno.trim().isEmpty()) {
                        fechaRetornoFn= LocalDate.parse(fechaRetorno);
                    }
                    
                    depSucursalFn =sucursalDao.obtenerSucursalId(idSucursalFn).getDepartamento();
                    depEncuentroFn = Departamento.valueOf(departamentoOrigen.toUpperCase());
                    depDestinoFn = Departamento.valueOf(departamentoDestino.toUpperCase());
                }catch(Exception e){
                    throw new DatoInvalidoException();
                }
                
                boolean quiereRetorno=true;
                if(fechaRetorno==null || fechaRetorno.trim().isEmpty()){
                    quiereRetorno=false;
                }
                
                double precioFinal=calculoPrecioService.calcularPrecioPrivado(depSucursalFn, depEncuentroFn, depDestinoFn, 
                        quiereRetorno);
 
                ViajePrivadoDTO viajePrivado=new ViajePrivadoDTO(origen, destino, cantidadPasajerosFn, fechaSalidaFn, 
                        fechaRetornoFn, precioFinal, dpiCliente, depEncuentroFn, depDestinoFn, idSucursalFn);
                
                int idViaje=viajesDAO.crearViaje(EstadoViaje.PENDIENTE.toString(), idSucursalFn, TipoViaje.PRIVADO.toString());
                if(idViaje==0){
                    throw new SQLException();
                }
                
                viajePrivado.setIdViaje(idViaje);
                
                if(!(viajesDAO.crearViajePrivado(viajePrivado))){
                    throw new SQLException();
                }
                
                cone.commit();
                return true;
                
            }catch(SQLException | DatoInvalidoException e){
                try{
                   cone.rollback();
                }catch(SQLException ex){
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
    
    //ADMIN ACEPTA
    public boolean aceptarSolicitud(String idViaje, String precioFinal) throws DatoInvalidoException, SQLException{
   
       
        try (Connection cone=adminConexion.getConnection()) {
            try {
                ViajesDAO viajesDAO= new ViajesDAO(cone);
                
                 cone.setAutoCommit(false);
                
                int idViajeFn;
                double precioFinalFn;
                try{
                    idViajeFn=Integer.parseInt(idViaje);
                    precioFinalFn=Double.parseDouble(precioFinal);

                    if (precioFinalFn<=0){
                        throw new DatoInvalidoException();
                    }
                    
                }catch (Exception e){
                    throw new DatoInvalidoException();
                }
                
                ViajePrivadoDTO viaje= viajesDAO.obtenerViajePrivadoPorId(idViajeFn);
                
                if (viaje==null){
                    throw new DatoInvalidoException();
                }
                
                //validar el estado actual del viaje
                if (!EstadoViaje.PENDIENTE.toString().equalsIgnoreCase(viaje.getEstadoViaje().toString())){
                    throw new DatoInvalidoException();
                }
                
                if (!viajesDAO.modificarPrecioPrivado(precioFinalFn, idViajeFn)){
                    throw new SQLException();
                }
                
                if (!viajesDAO.aceptarViaje(idViajeFn)){
                    throw new SQLException();
                }
                
                cone.commit();
                return true;
                
            }catch (SQLException | DatoInvalidoException e){
                try{
                    cone.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                throw e;
            }finally{
                try{
                    cone.setAutoCommit(true);
                }catch (SQLException ex){
                    ex.printStackTrace();
                }
            }
        }
    }
    
    //ADMIN RECHAZA VIAJE
    public boolean rechazarSolicitud(String idViajeStr) throws DatoInvalidoException, SQLException{
        try (Connection cone= adminConexion.getConnection()){
            try{
                ViajesDAO viajesDAO=new ViajesDAO(cone);
                
                cone.setAutoCommit(false);
                
                int idViajeFn;
                try{
                    idViajeFn= Integer.parseInt(idViajeStr);
                }catch (NumberFormatException e){
                    throw new DatoInvalidoException();
                }
                
                ViajePrivadoDTO viaje= viajesDAO.obtenerViajePrivadoPorId(idViajeFn);
                
                if (viaje == null) {
                    throw new DatoInvalidoException();
                }
                
                //validar el estado actual del viaje
                if (!EstadoViaje.PENDIENTE.toString().equalsIgnoreCase(viaje.getEstadoViaje().toString()) ||
                        !EstadoViaje.CONFIRMADO.toString().equalsIgnoreCase(viaje.getEstadoViaje().toString())){
                    
                    throw new DatoInvalidoException();
                }
                
                if (!viajesDAO.rechazarViajePrivado(idViajeFn)){
                    throw new SQLException();
                }
                
                cone.commit();
                return true;
            }catch (SQLException | DatoInvalidoException e){
                try {
                    cone.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                throw e;
                
            }finally{
                try{
                    cone.setAutoCommit(true);
                }catch (SQLException ex){
                    ex.printStackTrace();
                }
            }
        }
    }
    
    
    //CLIENTE PAGA VIAJE Y SE PROGRAMA
    public boolean pagarViajePrivado(String idViaje, String dpiCliente, String fechaPago) throws DatoInvalidoException, SQLException{
        try (Connection cone=adminConexion.getConnection()){
            try{
                ViajesDAO viajesDAO=new ViajesDAO(cone);
                CuentaDAO cuentaDAao= new CuentaDAO(cone);
                
                cone.setAutoCommit(false);
                
                int idViajeFn;
                LocalDate fechaPagoFn;
                try{
                    idViajeFn=Integer.parseInt(idViaje);
                    fechaPagoFn=LocalDate.parse(fechaPago);
                }catch (Exception e){
                    throw new DatoInvalidoException();
                }

                //verificar que toda la informacion existea y sea valida
                ViajePrivadoDTO viaje=viajesDAO.obtenerViajePrivadoPorId(idViajeFn);
                
                if (viaje==null){
                    throw new DatoInvalidoException();
                }
                
                if (!viaje.getDpiCliente().equals(dpiCliente)){
                    throw new DatoInvalidoException();
                }
                
                //sino esra confirmado no se puede pagar
                if (!EstadoViaje.CONFIRMADO.toString().equalsIgnoreCase(viaje.getEstadoViaje().toString())){
                    throw new DatoInvalidoException();
                }
                
                //PAGO
                double precioFinal = viaje.getPrecioFinal();
                double saldoCliente = cuentaDAao.obtenerConDpi(dpiCliente).getSaldo();
                
                if(saldoCliente < precioFinal){
                    throw new DatoInvalidoException("Saldo insuficiente en la billetera digital para realizar el pago");
                }
                
                if(!cuentaDAao.quitarSaldo(dpiCliente, precioFinal)){
                    throw new SQLException();
                }
                
                if(!viajesDAO.pagarViajePrivado(fechaPagoFn, precioFinal, idViajeFn)){
                    throw new SQLException();
                }
                
                if(!viajesDAO.marcarViajeComoPagado(idViajeFn)){
                    throw new SQLException();
                }
                
                cone.commit();
                return true;

            }catch (SQLException | DatoInvalidoException e){
                try {
                    cone.rollback();
                }catch (SQLException ex){
                    ex.printStackTrace();
                }
                throw e;
                
            }finally{
                try{
                    cone.setAutoCommit(true);
                }catch (SQLException ex){
                    ex.printStackTrace();
                }
            }
        }
    }
    

    public boolean cancelarViaje(String idViajeStr) throws DatoInvalidoException, SQLException{
        try (Connection cone = adminConexion.getConnection()){
            try{
                ViajesDAO viajesDAO = new ViajesDAO(cone);
                
                cone.setAutoCommit(false);
                
                int idViajeFn;
                try{
                    idViajeFn = Integer.parseInt(idViajeStr);
                } catch (NumberFormatException e){
                    throw new DatoInvalidoException();
                }

                ViajePrivadoDTO viaje= viajesDAO.obtenerViajePrivadoPorId(idViajeFn);
                
                if(viaje == null){
                    throw new DatoInvalidoException();
                }
                
                //validar el estado actual del viaje
                if(!EstadoViaje.PROGRAMADO.toString().equalsIgnoreCase(viaje.getEstadoViaje().toString())){
                    throw new DatoInvalidoException();
                }
                
                //cancelar viaje
                if (!viajesDAO.CancelarViajePrivado(idViajeFn)){
                    throw new SQLException();
                }
                
                cone.commit();
                return true;
                
            }catch (SQLException | DatoInvalidoException e){
                
                
                try{
                    cone.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                throw e;
                
            }finally{
                try{
                    cone.setAutoCommit(true);
                }catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    public boolean asignarBusChofer(String idViajeStr, String placaBus, String dpiChofer) throws DatoInvalidoException, SQLException{
        
        try (Connection cone=adminConexion.getConnection()){
            try{
                ViajesDAO viajesDAO=new ViajesDAO(cone);
                
                cone.setAutoCommit(false);
                
                int idViajeFn;
                try{
                    idViajeFn=Integer.parseInt(idViajeStr);
                }catch (NumberFormatException e){
                    throw new DatoInvalidoException();
                }

                if(dpiChofer==null || dpiChofer.isEmpty() || dpiChofer.isBlank() || dpiChofer.length()!=13 || 
                        !dpiChofer.trim().matches("\\d+")){
                    
                    throw new DatoInvalidoException();
                }

                ViajePrivadoDTO viaje=viajesDAO.obtenerViajePrivadoPorId(idViajeFn);

                if(viaje==null){
                    throw new DatoInvalidoException();
                }
                
                //validar el estado actual del viaje
                if(!EstadoViaje.CONFIRMADO.toString().equalsIgnoreCase(viaje.getEstadoViaje().toString()) ||
                    !EstadoViaje.PENDIENTE.toString().equalsIgnoreCase(viaje.getEstadoViaje().toString())){
                    
                    throw new DatoInvalidoException();
                }
                
                //buscamos las fechas
                LocalDate fechaInicio=viaje.getFechaSalida();
                LocalDate fechaFin= (viaje.getFechaRetorno() != null) ? viaje.getFechaRetorno() : fechaInicio;
                
                //validamos que el bus y chofer esten disponibles en esa fecha
                if(!busChoferOcupados(cone, placaBus, dpiChofer, fechaInicio, fechaFin, idViajeFn)){
                    throw new DatoInvalidoException();
                }
                
                if(!viajesDAO.modificarViajePadre(placaBus, dpiChofer, idViajeFn)){
                    throw new SQLException();
                }
                
                cone.commit();
                return true;
                
            }catch (SQLException | DatoInvalidoException e){
                
                
                try{
                    cone.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                throw e;
                
            }finally{
                try{
                    cone.setAutoCommit(true);
                }catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    //para el admin susucrsal que vera los que ya puede marcar salida/Llegada
    public List<ViajePrivadoDTO> obtenerViajesPrivaProgramados(String idSucursal) throws SQLException, DatoInvalidoException{
        try (Connection cone=adminConexion.getConnection()) {
            
            ViajesDAO viajesDAO = new ViajesDAO(cone);
            
            int idSucursalFn;
            try {
                idSucursalFn=Integer.parseInt(idSucursal);
                if (idSucursalFn <= 0) {
                    throw new DatoInvalidoException();
                }
            } catch (NumberFormatException e) {
                throw new DatoInvalidoException();
            }
            
            List<ViajePrivadoDTO> lista= viajesDAO.viajesPriProgramados(idSucursalFn);
            
            if (lista==null) {
                return new ArrayList<>();
            }
            return lista;
        }
    }
    
    //para el admin, sus viajes en Negociacion
    public List<ViajePrivadoDTO> obtenerViajPrivNegociadosAdm(String idSucursal) throws SQLException, DatoInvalidoException{
        
        try (Connection cone=adminConexion.getConnection()) {
            
            ViajesDAO viajesDAO = new ViajesDAO(cone);
            
            int idSucFin;
            try{
                idSucFin=Integer.parseInt(idSucursal);
            }catch(Exception e){
                throw new DatoInvalidoException();
            }
            
            List<ViajePrivadoDTO> viajes=viajesDAO.privadoNegAdmin(idSucFin);
            if(viajes==null){
                throw new SQLException();
            }
            
            return viajes;
        }
    }
        
    //paraa el cliente, sus viajes en Negociacion
    public List<ViajePrivadoDTO> obtenerViajPrivNegociadosClien(String dpiClient) throws SQLException, DatoInvalidoException{
        try(Connection cone=adminConexion.getConnection()){
             ViajesDAO viajesDAO = new ViajesDAO(cone);
            
            if(dpiClient==null || dpiClient.isEmpty() || dpiClient.isBlank() || dpiClient.length()!=13 || !dpiClient.trim().matches("\\d+")){
                    throw new DatoInvalidoException();
            }
            
            List<ViajePrivadoDTO> viajes=viajesDAO.privadoNegCliente(dpiClient);
            if(viajes==null){
                throw new SQLException();
            }
            
            return viajes;

        }
    }
    
    //Viaje para el chofer que puede marcar como iniciados
    public List<ViajePrivadoDTO> obtenerViajProgramadBus(String dpiChofer) throws SQLException, DatoInvalidoException{
        try(Connection cone=adminConexion.getConnection()){
             ViajesDAO viajesDAO = new ViajesDAO(cone);
            
            if(dpiChofer==null || dpiChofer.isEmpty() || dpiChofer.isBlank() || dpiChofer.length()!=13 || !dpiChofer.trim().matches("\\d+")){
                    throw new DatoInvalidoException();
            }
            
            List<ViajePrivadoDTO> viajes=viajesDAO.privadosProgramadosChofer(dpiChofer);
            if(viajes==null){
                throw new SQLException();
            }
            
            return viajes;

        }
    }
    
    
    //METODOS AUXILIARES
    private boolean busChoferOcupados(Connection cone, String placaBus, String dpiChofer, LocalDate salida, 
            LocalDate retorno, int idViajeActual) throws SQLException{
        
        ViajesDAO viajeDao = new ViajesDAO(cone);
        
        //ver que los viajes sin terminar de un chofer o programados, no choquen con la nueva fecha 
        List<ViajeRegularDTO> viajesRegChofer= viajeDao.viajesRegularesSinTerminarChofer(dpiChofer);
        if (viajesRegChofer != null){
            for (ViajeRegularDTO viaj : viajesRegChofer){
                
                LocalDate salidaProg=viaj.getFechaHoraProgramadaSalida().toLocalDate();
                LocalDate llegadaProg=viaj.getFechaHoraEstimadaLlegada().toLocalDate();
                
                if(viaj.getIdViaje()!=idViajeActual && !salida.isAfter(llegadaProg) && !retorno.isBefore(salidaProg)){
                    return true;
                }
            }
        }
        
        //ver que los viajes sin terminar de un bus o programados, no choquen con la nueva fecha
        List<ViajeRegularDTO> viajesRegBus= viajeDao.viajesRegularesSinTerminarBus(placaBus);
        if (viajesRegBus != null) {
            for (ViajeRegularDTO viaj : viajesRegBus) {
                LocalDate salidaProg=viaj.getFechaHoraProgramadaSalida().toLocalDate();
                LocalDate llegadaProg=viaj.getFechaHoraEstimadaLlegada().toLocalDate();
                
                if(viaj.getIdViaje()!=idViajeActual && !salida.isAfter(llegadaProg) && !retorno.isBefore(salidaProg)){
                    return true;
                }
            }
        }
            
            
        /*
            ver viajes privados, sin terminar o sin cancelar/rechazar
            
            se ve si en la lista de viajes privados si el chofer o el bus no se estan asignando en un viaje privado previamente.
            Si alguno esta previamente asignado se analiza el horario del viaje, sino se ignora y pasa al siguiente
            
        */
        List<ViajePrivadoDTO> viajesPrivados= viajeDao.verViajesPrivadoSinTerminar();
        if (viajesPrivados != null){
            for (ViajePrivadoDTO viaj : viajesPrivados){
                if (viaj.getIdViaje() != idViajeActual) {//mismo viaje se debe omitir
                    boolean mismoChofer= dpiChofer.equals(viaj.getDpiChofer());
                    boolean mismoBus= placaBus.equals(viaj.getPlacaBusAsignado());
                    if (mismoChofer || mismoBus){//chofer o bus estan asignnado en un viaje, ver que no choquen fechas
                        LocalDate salidaPriv=viaj.getFechaSalida();
                        LocalDate retornoPriv=(viaj.getFechaRetorno() != null) ? viaj.getFechaRetorno() : salidaPriv;
                        if (!salida.isAfter(retornoPriv) && !retorno.isBefore(salidaPriv)){
                            return true;
                        }
                    }
                }   
            }
        }
        return false;
    }
}
