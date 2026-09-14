/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sevicios;

import ClasesDAO.BoletoDAO;
import ClasesDAO.CuentaDAO;
import ClasesDTO.BoletoDTO;
import ClasesDTO.DetalleBoletoDTO;
import ConexionDB.ConnectionDB;
import clasesAuxiliares.Exceptions.DatoInvalidoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dar333n
 */
public class ServicioVenta {
    
    private ConnectionDB adminConexion;

    public ServicioVenta(ConnectionDB connection) {
        this.adminConexion = connection;
    }

    public boolean comprarBoletos(String dpiCliente, String idViaje, List<String> numAsientos, String totalPagar, 
            String fechaPago) throws SQLException, DatoInvalidoException{
        try (Connection cone=adminConexion.getConnection()){
            try{
                cone.setAutoCommit(false);
                
                //parseo primeor
                int idViajeFn;
                double totalPagarFn;
                LocalDate fechaPagoFn;
                List<Integer> numAsientosFn;
                try{
                    idViajeFn=Integer.parseInt(idViaje);
                    totalPagarFn=Double.parseDouble(totalPagar);
                    fechaPagoFn = LocalDate.parse(fechaPago);

                    numAsientosFn=new ArrayList<>();
                    for (String asiento : numAsientos) {
                        numAsientosFn.add(Integer.parseInt(asiento));
                    }
                }catch(NumberFormatException | DateTimeParseException e){
                    throw new DatoInvalidoException();
                }
                
                BoletoDAO boletoDAO = new BoletoDAO(cone);
                CuentaDAO cuentaDAO = new CuentaDAO(cone);

                //comparar que ninguno de los elegidos esten ocupados
                List<DetalleBoletoDTO> ocupados= boletoDAO.obtenerAsientosOcupados(idViajeFn);
                for (DetalleBoletoDTO ocupa : ocupados){
                    if (numAsientos.contains(ocupa.getIdAsiento())){
                        throw new SQLException();
                    }
                }

                if (!(cuentaDAO.quitarSaldo(dpiCliente, totalPagarFn))){
                    throw new SQLException();
                }

                //crear boleto y agregar su detalle de boleto
                BoletoDTO boleto=new BoletoDTO(totalPagarFn, fechaPagoFn, idViajeFn, dpiCliente);

                int idBoleto=boletoDAO.crearBoleto(boleto);
                if (idBoleto <= 0) {
                    throw new SQLException();
                }

                for (Integer asiento : numAsientosFn){
                    DetalleBoletoDTO detalle=new DetalleBoletoDTO(idBoleto, asiento);

                    if (!(boletoDAO.agregarDetalle(detalle))) {
                        throw new SQLException();
                    }
                }

                cone.commit();
                return true;

            }catch(DatoInvalidoException | SQLException e){
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

    public List<Integer> obtenerAsientosOcupados(String idViaje) throws SQLException, DatoInvalidoException{
        try (Connection cone= adminConexion.getConnection()){
            try{
                BoletoDAO boletoDAO = new BoletoDAO(cone);
                
                int idViajeFN;
                try{
                    idViajeFN=Integer.parseInt(idViaje);
                }catch(NumberFormatException ex){
                    throw new DatoInvalidoException();
                }   

                List<DetalleBoletoDTO> detalles= boletoDAO.obtenerAsientosOcupados(idViajeFN);
                List<Integer> ocupados= new ArrayList<>();
                for (DetalleBoletoDTO det : detalles) {
                    ocupados.add(det.getIdAsiento());
                }
                return ocupados;
            
            } catch (SQLException | DatoInvalidoException e) {
                e.printStackTrace();
                throw e;
            }
        }
    }


    public List<BoletoDTO> listarBoletosComprados(String dpiCliente) throws SQLException{
        try (Connection cone= adminConexion.getConnection()){
            try{
                BoletoDAO boletoDAO=new BoletoDAO(cone);
                return boletoDAO.obtenerBoletosCliente(dpiCliente);
            } catch (SQLException e) {
                e.printStackTrace();
                return new ArrayList<>();
            }
        }
    }
}
    

