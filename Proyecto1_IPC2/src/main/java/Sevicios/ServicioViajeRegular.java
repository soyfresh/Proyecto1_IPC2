/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sevicios;

import ClasesDAO.BoletoDAO;
import ClasesDAO.BusDAO;
import ClasesDAO.RutaDAO;
import ClasesDAO.SucursalDAO;
import ClasesDAO.ViajesDAO;
import ClasesDTO.BusDTO;
import ClasesDTO.RutaDTO;
import ClasesDTO.SucursalDTO;
import ClasesDTO.Viajes.ViajeDTO;
import ClasesDTO.Viajes.ViajeRegularDTO;
import ConexionDB.ConnectionDB;
import clasesAuxiliares.EstadoBus;
import clasesAuxiliares.EstadoViaje;
import clasesAuxiliares.Exceptions.DatoInvalidoException;
import clasesAuxiliares.TipoViaje;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dar333n
 */
public class ServicioViajeRegular {
    
    
    private ConnectionDB adminConexion;

    public ServicioViajeRegular(ConnectionDB connection) {
        this.adminConexion = connection;
    }

    public boolean programarViajeRegular(String placaBus, String dpiChofer, String idRuta, String fechaHoraSalidaEs, 
            String fechaHoraLlegadaEs, String idSucursal) throws SQLException, DatoInvalidoException{
        
        try(Connection cone= adminConexion.getConnection()){
            try{
                
            
            ViajesDAO viajeDao= new ViajesDAO(cone);
            BusDAO busDao= new BusDAO(cone);
            RutaDAO rutaDao= new RutaDAO(cone);
            SucursalDAO sucursalDao= new SucursalDAO(cone);
            
            if(dpiChofer==null || dpiChofer.isEmpty() || dpiChofer.isBlank() || dpiChofer.length()!=13 || !dpiChofer.trim().matches("\\d+")){
                throw new DatoInvalidoException();
            }
            
            cone.setAutoCommit(false);
            int idSucursalFn;
            int idRutaFn;
            LocalDateTime salidaFE;
            LocalDateTime llegadaFE;
            try{
                idSucursalFn=Integer.parseInt(idSucursal);
                idRutaFn= Integer.parseInt(idRuta);
                salidaFE= LocalDateTime.parse(fechaHoraSalidaEs);
                llegadaFE= LocalDateTime.parse(fechaHoraLlegadaEs);
            }catch(Exception e){
                throw new DatoInvalidoException();
            }
            
            if(!(salidaFE.isBefore(llegadaFE) && llegadaFE.isAfter(salidaFE))){
                throw new DatoInvalidoException();
            }

            BusDTO bus = busDao.obtenerBusConPlaca(placaBus);
                if (bus==null || !bus.isActivo()){
                    throw new DatoInvalidoException();
                }

            RutaDTO ruta = rutaDao.obtenerConId(idRutaFn);
            if (ruta==null || !ruta.isActivo()){
                throw new DatoInvalidoException();
            }
            
            SucursalDTO sucu=sucursalDao.obtenerSucursalId(ruta.getIdSucursalDestino());
            if(sucu==null){
                throw new SQLException();
            }
            
            if(bus.getEstadoBus().equals(EstadoBus.NO_DISPONIBLE)){
                if(bus.getIdSucursal()!=ruta.getIdSucursalDestino()){
                    throw new DatoInvalidoException("Debe asignar una ruta con destino a ID destino: "+
                            ruta.getIdSucursalDestino()+" Ubicacion: "+sucu.getDireccion());
                }
            }

            if(!ruta.isActivo()){
                throw new DatoInvalidoException();
            }        
                    
            if(!bus.isActivo()){
                throw new DatoInvalidoException();
            }
            
            int idViajeregular=viajeDao.crearViaje(EstadoViaje.PROGRAMADO.toString(), idSucursalFn, TipoViaje.REGULAR.toString());//SE CREA EL VIAJE Y SU ID
            if(idViajeregular==0){
                throw new SQLException();
            }
            
            if(!verificarDisponibilidad(cone, placaBus, dpiChofer, salidaFE, llegadaFE, idViajeregular)){
                throw new DatoInvalidoException();
            }

            //SE CREA LA DTO QUE SE MANDARA A LA DB Y SETTEAMOS EL ID DEL VIAJE
            ViajeRegularDTO viajeRegular = new ViajeRegularDTO(placaBus, dpiChofer, idRutaFn, salidaFE, llegadaFE, idSucursalFn);
            viajeRegular.setIdViaje(idViajeregular);
            
            if(!(viajeDao.crearViajeRegular(viajeRegular))){
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
 
    public boolean editarViajeRegular(String idViaje, String placaBus, String dpiChofer, String fechaHoraSalidaEs, 
            String fechaHoraLlegadaEs) throws SQLException, DatoInvalidoException{
        
        try (Connection cone=adminConexion.getConnection()){
            try {
                cone.setAutoCommit(false);

                ViajesDAO viajeDao= new ViajesDAO(cone);
                
                if(dpiChofer==null || dpiChofer.isEmpty() || dpiChofer.isBlank() || dpiChofer.length()!=13 || !dpiChofer.trim().matches("\\d+")){
                    throw new DatoInvalidoException();
                }
                
                int idViajeFn;
                LocalDateTime salidaFE;
                LocalDateTime llegadaFE;
                try {
                    idViajeFn=Integer.parseInt(idViaje);
                    salidaFE=LocalDateTime.parse(fechaHoraSalidaEs);
                    llegadaFE=LocalDateTime.parse(fechaHoraLlegadaEs);
                } catch (Exception e) {
                    throw new DatoInvalidoException();
                }

                if(!(salidaFE.isBefore(llegadaFE) && llegadaFE.isAfter(salidaFE))){
                    throw new DatoInvalidoException();
                }

                ViajeRegularDTO viajeActual = viajeDao.obtenerViajeRegularPorId(idViajeFn);
                if (viajeActual==null) {
                    throw new DatoInvalidoException();
                }

                if (!EstadoViaje.PROGRAMADO.equals(viajeActual.getEstadoViaje())){
                    throw new DatoInvalidoException();
                }

                if (!verificarDisponibilidad(cone, placaBus, dpiChofer, salidaFE, llegadaFE, idViajeFn)) {
                    throw new DatoInvalidoException();
                }

                if (!(viajeDao.modificarViajePadre(placaBus, dpiChofer, idViajeFn)) || 
                        !(viajeDao.modificarViajeRegular(salidaFE, llegadaFE, idViajeFn))) {
                    
                    throw new SQLException();
                }

                cone.commit();
                return true;

            } catch (SQLException | DatoInvalidoException e) {
                try {
                    cone.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                throw e;
            } finally {
                try {
                    cone.setAutoCommit(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    public boolean registrarSalidaViaje(String idViaje, String horaSalidaReal) throws SQLException, DatoInvalidoException{
        
        try (Connection cone = adminConexion.getConnection()){
            try {
                cone.setAutoCommit(false);

                ViajesDAO viajeDao=new ViajesDAO(cone);
                BusDAO busDao=new BusDAO(cone);

                int idViajeFn;
                LocalTime horaSalidaFE;
                try {
                    idViajeFn=Integer.parseInt(idViaje);
                    horaSalidaFE=LocalTime.parse(horaSalidaReal);
                } catch (Exception e) {
                    throw new DatoInvalidoException();
                }

                //FALTA HACER QUE LA SALID AY LLEGADA SEA PARA AMBOS
                ViajeDTO viaje = viajeDao.obtenerViajeRegularPorId(idViajeFn);
                if (viaje==null || !EstadoViaje.PROGRAMADO.equals(viaje.getEstadoViaje())){
                    throw new DatoInvalidoException();
                }

                BusDTO bus= busDao.obtenerBusConPlaca(viaje.getPlacaBusAsignado());
                if(bus==null){
                    throw new SQLException();
                };
                
                viaje.setHoraRealSalida(horaSalidaFE);
                viaje.setKilometrajeActual(bus.getKilometrajeActual());
                viaje.setEstadoViaje(EstadoViaje.INICIADO);
                
                busDao.cambiarEstado(viaje.getPlacaBusAsignado(), EstadoBus.EN_TRANSITO);
                
                if(!(viajeDao.registrarSalida(viaje))){
                    throw new SQLException();
                }
 
                cone.commit();
                return true;

            } catch (SQLException | DatoInvalidoException e) {
                try {
                    cone.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                throw e;
            } finally {
                try {
                    cone.setAutoCommit(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    
    public boolean registrarLlegadaViaje(String idViaje, String horaLlegadaReal, String kilometrajeFinal, 
            String costoCombustible) throws SQLException, DatoInvalidoException {
        
        try (Connection cone= adminConexion.getConnection()) {
            try {
                cone.setAutoCommit(false);

                ViajesDAO viajeDao=new ViajesDAO(cone);
                BusDAO busDao=new BusDAO(cone);
                RutaDAO rutaDao=new RutaDAO(cone);
                SucursalDAO sucursalDao=new SucursalDAO(cone);

                int idViajeFn;
                int kmFinalFn;
                double gastoCombustibleFn;
                LocalTime horaLlegadaFE;
                try {
                    idViajeFn=Integer.parseInt(idViaje);
                    kmFinalFn=Integer.parseInt(kilometrajeFinal);
                    gastoCombustibleFn=Double.parseDouble(costoCombustible);
                    horaLlegadaFE=LocalTime.parse(horaLlegadaReal);
                } catch (Exception e) {
                    throw new DatoInvalidoException();
                }

                ViajeRegularDTO viaje= viajeDao.obtenerViajeRegularPorId(idViajeFn);
                if (viaje == null || !EstadoViaje.INICIADO.equals(viaje.getEstadoViaje())) {
                    throw new DatoInvalidoException();
                }

                if (kmFinalFn<viaje.getKilometrajeActual()) {
                    throw new DatoInvalidoException();
                }

                viaje.setHoraRealLlegada(horaLlegadaFE);
                viaje.setKilometrajeFinal(kmFinalFn);
                viaje.setGastoTotalCombustible(gastoCombustibleFn);
                viaje.setEstadoViaje(EstadoViaje.TERMINADO);
                
                //de busca el id de la sucursal de destino 
                int idRuta=viaje.getIdRuta();
                RutaDTO rutaDeViaje=rutaDao.obtenerConId(idRuta);
                SucursalDTO sucursal= sucursalDao.obtenerSucursalId(rutaDeViaje.getIdSucursalDestino());
                
                //se busca el id de la sucursal del bus asociada a el
                BusDTO bus=busDao.obtenerBusConPlaca(viaje.getPlacaBusAsignado());
                
                //se actualiza la ubicacion actual del bus
                int idSucursalDestino = rutaDeViaje.getIdSucursalDestino();
                busDao.actualizarSucursalActual(bus.getNumeroPlaca(), idSucursalDestino);
                
                //se compara si el destino es igual al id de la sucusal del bus
                if(sucursal.getIdScucursal()!=bus.getIdSucursal()){
                   busDao.cambiarEstado(viaje.getPlacaBusAsignado(), EstadoBus.NO_DISPONIBLE); 
                }else{
                    busDao.cambiarEstado(viaje.getPlacaBusAsignado(), EstadoBus.DISPONIBLE); 
                }
                
                if (!(viajeDao.registrarLlegada(viaje))) {
                    throw new SQLException();
                }

                if (!(busDao.aumentarKilometraje(kmFinalFn, viaje.getPlacaBusAsignado()))) {
                    throw new SQLException();
                }

                cone.commit();
                return true;

            } catch (SQLException | DatoInvalidoException e) {
                try {
                    cone.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                throw e;
            } finally {
                try {
                    cone.setAutoCommit(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public boolean eliminarViajeRegular(String idViaje) throws SQLException, DatoInvalidoException{
        
        try (Connection cone=adminConexion.getConnection()){
            try {
                cone.setAutoCommit(false);

                ViajesDAO viajeDao= new ViajesDAO(cone);
                BoletoDAO boletoDao= new BoletoDAO(cone);
                
                int idViajeFn;
                try {
                    idViajeFn = Integer.parseInt(idViaje);
                } catch (Exception e) {
                    throw new DatoInvalidoException("ID de viaje invalido");
                }

                ViajeRegularDTO viaje = viajeDao.obtenerViajeRegularPorId(idViajeFn);
                if (viaje == null) {
                    throw new DatoInvalidoException("El viaje a eliminar no existe");
                }

                if (EstadoViaje.INICIADO.equals(viaje.getEstadoViaje()) || EstadoViaje.TERMINADO.equals(viaje.getEstadoViaje())){
                    throw new DatoInvalidoException("No se puede eliminar un viaje que ya inicio o finalizo");
                }

                if (boletoDao.tieneBoletosAsociados(idViajeFn)){
                    throw new DatoInvalidoException("No se puede eliminar el viaje porque ya contiene boletos vendidos");
                }

                if (!(viajeDao.eliminarViaje(idViajeFn))) {
                    throw new SQLException("Error al eliminar las relaciones del viaje");
                }

                cone.commit();
                return true;

            } catch (SQLException | DatoInvalidoException e) {
                try {
                    cone.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                throw e;
            } finally {
                try {
                    cone.setAutoCommit(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    
    public List<BusDTO> obtenerBusesSucursal(String idSucursal) throws SQLException, DatoInvalidoException{
        try (Connection cone= adminConexion.getConnection()){
            
            BusDAO busDao=new BusDAO(cone);
            SucursalDAO sucursalDao=new SucursalDAO(cone);
            
            if (idSucursal==null || idSucursal.isBlank()) {
                throw new DatoInvalidoException();
            }

            int idSucursalFn;
            try {
                idSucursalFn= Integer.parseInt(idSucursal.trim());
            } catch (NumberFormatException e) {
                throw new DatoInvalidoException();
            }

            if (idSucursalFn <= 0) {
                throw new DatoInvalidoException();
            }
            
            if (sucursalDao.obtenerSucursalId(idSucursalFn)==null){
                throw new DatoInvalidoException();
            }

            return busDao.obtenerBusEstado(EstadoBus.DISPONIBLE, idSucursalFn);
        }
    }
    
    //para el admin sucursal, que vera que buses regresar
    public List<BusDTO> obtenerBusesPendienteRetorno(String idSucursalActual) throws SQLException, DatoInvalidoException{
        try (Connection cone= adminConexion.getConnection()){
            
            BusDAO busDao=new BusDAO(cone);
            SucursalDAO sucursalDao=new SucursalDAO(cone);
            
            if (idSucursalActual==null || idSucursalActual.isBlank()) {
                throw new DatoInvalidoException();
            }

            int idSucursalFn;
            try {
                idSucursalFn= Integer.parseInt(idSucursalActual.trim());
            } catch (NumberFormatException e) {
                throw new DatoInvalidoException();
            }

            if (idSucursalFn <= 0) {
                throw new DatoInvalidoException();
            }
            
            if (sucursalDao.obtenerSucursalId(idSucursalFn)==null){
                throw new DatoInvalidoException();
            }

            return busDao.obtenerBusesPendientesDeRetorno(idSucursalFn);
        }
    }
    
    
    //para el admin susucrsal que vera los que ya puede marcar salida/Llegada
    public List<ViajeRegularDTO> obtenerViajesRegProgramados(String idSucursal) throws SQLException, DatoInvalidoException{
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
            
            List<ViajeRegularDTO> lista= viajesDAO.viajesRegProgramados(idSucursalFn);
            
            if (lista==null) {
                return new ArrayList<>();
            }
            return lista;
        }
    }
    
    //METODOS AUXILIARES
    private boolean verificarDisponibilidad(Connection cone, String placaBus, String dpiChofer, LocalDateTime salida, 
            LocalDateTime llegada, int viajeED) throws SQLException{
        
            ViajesDAO viajeDao=new ViajesDAO(cone);
  
            //ver que los viajes sin terminar de un chofer o programados, no choquen con la nueva fecha
            List<ViajeRegularDTO> viajes=viajeDao.viajesRegularesSinTerminarChofer(dpiChofer);
            if(viajes!=null){
                for (ViajeRegularDTO viaj : viajes) {
                    if(viaj.getIdViaje()!=viajeED && salida.isBefore(viaj.getFechaHoraEstimadaLlegada()) && llegada.isAfter(viaj.getFechaHoraProgramadaSalida())){
                        return false;
                    }
                }
            }
            
            
            //ver que los viajes sin terminar de un bus o programados, no choquen con la nueva fecha
            List<ViajeRegularDTO> viajesB=viajeDao.viajesRegularesSinTerminarBus(placaBus);
            if(viajesB!=null){
                for(ViajeRegularDTO viaj : viajesB){
                    if(viaj.getIdViaje()!=viajeED && salida.isBefore(viaj.getFechaHoraEstimadaLlegada()) && llegada.isAfter(viaj.getFechaHoraProgramadaSalida())){
                        return false;
                    }
                }
            }
        return true;
    }
    
}
