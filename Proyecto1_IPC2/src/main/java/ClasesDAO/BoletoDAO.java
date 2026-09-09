 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClasesDAO;

import ClasesDTO.BoletoDTO;
import ClasesDTO.DetalleBoletoDTO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dar333n
 */
public class BoletoDAO {
    
    
    public static final String CREAR_BOLETO="INSERT INTO boleto(total, fecha_de_compra, id_viaje, dpi_cliente) VALUES (?, ?, ?, ?);";
    
    public static final String GISTRAR_DETALLE_BOLETO="""
                                                      INSERT INTO detalle_boleto (id_boleto, numero_de_asiento_ocupado)
                                                      VALUES (?, ?);
                                                      """;
    
    public static final String OBTENER_BOLETO_CON_ID="SELECT * FROM boleto WHERE id_boleto=?;";
    
    
    public static final String OBTENER_DETALLE_DEL_BOLETO="SELECT * FROM detalle_boleto WHERE id_boleto=?;";
    
    public static final String OTENER_BOLETOS_CLIENTE="SELECT * FROM boleto WHERE dpi_cliente= ? ORDER BY fecha_de_compra DESC;";
    
    public static final String OBTENER_BOLETOS_VIAJE="SELECT * FROM boleto WHERE id_viaje=?";
    
    public static final String OBTENER_ASIENTOS_OCUPADOS="""
                                                         SELECT * FROM detalle_boleto INNER JOIN boleto.id_boleto=detalle_boleto.id_boleto
                                                         WHERE id_viaje=?;
                                                         """;
    
    
    
    private Connection connection;
    
    public BoletoDAO(Connection connection) {
        this.connection = connection;
    }
    
    public int crearBoleto(BoletoDTO boleto) throws SQLException{
        try (PreparedStatement ps=connection.prepareStatement(CREAR_BOLETO)){
            
            ps.setDouble(1, boleto.getTotal());
            ps.setDate(2, Date.valueOf(boleto.getFechaCompra()));
            ps.setInt(3, boleto.getIdViaje());
            ps.setString(4, boleto.getDpiCliente());
            
            int filasModificadas= ps.executeUpdate();
            if (filasModificadas==0) {
                return 0;
            }
        
            try(PreparedStatement ps2=connection.prepareStatement("SELECT LAST_INSERT_ID"); ResultSet rs=ps.executeQuery()){
                if(rs.next()){
                    return ps2.executeUpdate();
                }
            }
        }
        return 0;
    }
    
    public boolean agregarDetalle(DetalleBoletoDTO detalle) throws SQLException{
        try (PreparedStatement ps= connection.prepareStatement(GISTRAR_DETALLE_BOLETO)){
            ps.setInt(1, detalle.getIdBoleto());
            ps.setString(2, detalle.getIdAsiento());
            return ps.executeUpdate()>0;
        }
    }
    
    public BoletoDTO obtenerConId(int id) throws SQLException{
        try (PreparedStatement ps = connection.prepareStatement(OBTENER_BOLETO_CON_ID)){
            ps.setInt(1, id);
            
            try (ResultSet rs=ps.executeQuery()){
                if (rs.next()) {
                    BoletoDTO boleto= empaquetarBoleto(rs);
                    return boleto;
                }
            }
        }
        return null;
    }
    
    public List<DetalleBoletoDTO> obtenerDetalleBoleto(int idBoleto) throws SQLException{
        List<DetalleBoletoDTO> detalles = new ArrayList<>();
        try(PreparedStatement ps= connection.prepareStatement(OBTENER_DETALLE_DEL_BOLETO)){
            ps.setInt(1, idBoleto);
            
            try(ResultSet rs=ps.executeQuery()){
                while(rs.next()){
                    detalles.add(empaquetarDetalle(rs));
                }
                return detalles;
            }
        } 
    }
    
    public List<BoletoDTO> obtenerBoletosCliente(String dpi) throws SQLException{
        List<BoletoDTO> boletos = new ArrayList<>();
        try (PreparedStatement ps=connection.prepareStatement(OTENER_BOLETOS_CLIENTE)){
            ps.setString(1, dpi);
            try (ResultSet rs=ps.executeQuery()){
                while (rs.next()) {
                    boletos.add(empaquetarBoleto(rs));
                }
                return boletos;
            }
        }
    }
    
    public List<BoletoDTO> obtenerBoletosViaje(int id) throws SQLException{
        List<BoletoDTO> boletos = new ArrayList<>();
        try (PreparedStatement ps=connection.prepareStatement(OBTENER_BOLETOS_VIAJE)) {
            ps.setInt(1, id);
            try (ResultSet rs=ps.executeQuery()) {
                while (rs.next()) {
                    boletos.add(empaquetarBoleto(rs));
                }
                return boletos;
            }
        }
    }
    
    public List<DetalleBoletoDTO> obtenerAsientosOcupados(int idVioaje) throws SQLException{
        List<DetalleBoletoDTO> ocupados = new ArrayList<>();
        try(PreparedStatement ps= connection.prepareStatement(OBTENER_ASIENTOS_OCUPADOS)){
            ps.setInt(1, idVioaje);
            
            try(ResultSet rs=ps.executeQuery()){
                while(rs.next()){
                    ocupados.add(empaquetarDetalle(rs));
                }
                return ocupados;
            }
        } 
    }
    
    //METODOS AUXILIARES SIN QUERY
    public BoletoDTO empaquetarBoleto(ResultSet rs) throws SQLException{
        int idBoleto= rs.getInt("id_boleto");
        double total = rs.getDouble("total");
        LocalDate fechaCompra = rs.getDate("fecha_de_compra").toLocalDate();
        int idViaje=rs.getInt("id_viaje");
        String dpiCliente=rs.getString("dpi_cliente");
        
        List<DetalleBoletoDTO> detalles= obtenerDetalleBoleto(idBoleto);
        return new BoletoDTO(idBoleto, total, fechaCompra, idViaje, dpiCliente, detalles);
    }
    
    public DetalleBoletoDTO empaquetarDetalle(ResultSet rs) throws SQLException{
        int idBoleto=rs.getInt("id_boleto");
        String idAsiento=rs.getString("numero_de_asiento_ocupado");
        
        return new DetalleBoletoDTO(idBoleto, idAsiento);
    }

}
