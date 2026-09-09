/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClasesDAO;

import ClasesDTO.BusDTO;
import ClasesDTO.RegistroTallerDTO;
import clasesAuxiliares.EstadoBus;
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
public class BusDAO {
    
    public static final String CREAR_BUS="""
                                         INSERT INTO bus(numero_de_placa, 
                                         marca, 
                                         modelo, 
                                         ano_de_fabricacion, 
                                         capacidad, 
                                         kilometraje_actual, 
                                         foto, 
                                         estado, 
                                         activo, 
                                         id_sucursal) 
                                         VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
                                         """;

    public static final String REGISTRAR_MANTENIMIENTO_TALLER="""
                                                              INSERT INTO registro_taller (numero_de_placa, 
                                                              monto_mano_de_obra, 
                                                              monto_repuestos, 
                                                              fecha_mantenimiento) 
                                                              VALUES (?, ?, ?, ?);
                                                              """;
    
    public static final String OBTENER_BUS_POR_PLACA="SELECT * FROM bus WHERE numero_de_placa=?;";

    public static final String MODIFICAR_BUS="""
                                             UPDATE bus SET 
                                             marca=?, 
                                             modelo=?, 
                                             ano_de_fabricacion=?, 
                                             capacidad=?, 
                                             foto=?, 
                                             id_sucursal=? 
                                             WHERE numero_de_placa=?;
                                             """;

    public static final String AMENTAR_KILOMETRAJE="""
                                                      UPDATE bus SET kilometraje_actual = kilometraje_actual + ? 
                                                      WHERE numero_de_placa=?;
                                                      """;

    public static final String CAMBIAR_ESTADO_BUS="UPDATE bus SET estado=? WHERE numero_de_placa=?;";

    public static final String DESACTIVAR_BUS="UPDATE bus SET activo= FALSE WHERE numero_de_placa=?;";

    public static final String OBTENER_BUSES_SUCURSAL="SELECT * FROM bus WHERE id_sucursal=? AND activo=TRUE;";

    public static final String OBTENER_BUSES_ESTADO="SELECT * FROM bus WHERE estado=? AND activo=TRUE AND id_sucursal=?;";

    public static final String OBTENER_REGISTROS_TALLER_POR_BUS="""
                                                                  SELECT * 
                                                                  FROM registro_taller 
                                                                  WHERE numero_de_placa=? 
                                                                  ORDER BY fecha_mantenimiento DESC;
                                                                  """;
    
    private Connection connection;
    
    public BusDAO(Connection connection) {
        this.connection = connection;
    }
    
    public boolean crearBus(BusDTO bus) throws SQLException{
        try (PreparedStatement ps=connection.prepareStatement(CREAR_BUS)){
            ps.setString(1, bus.getNumeroPlaca());
            ps.setString(2, bus.getMarca());
            ps.setString(3, bus.getModelo());
            ps.setInt(4, bus.getAnoFabricacion());
            ps.setInt(5, bus.getCapacidad());
            ps.setInt(6, bus.getKilometrajeActual());
            ps.setBytes(7, bus.getFoto());
            ps.setString(8, String.valueOf(bus.getEstadoBus()));
            ps.setBoolean(9, bus.isActivo());
            ps.setInt(10, bus.getIdSucursal());
            return ps.executeUpdate()>0;
        }
    }
    
    public boolean registrarMantenimiento(RegistroTallerDTO registro) throws SQLException{
        try (PreparedStatement ps = connection.prepareStatement(REGISTRAR_MANTENIMIENTO_TALLER)){
            ps.setString(1, registro.getNumeroPlaca());
            ps.setDouble(2, registro.getMontoManoDeObra());
            ps.setDouble(3, registro.getMontoRepuestos());
            ps.setDate(4, Date.valueOf(registro.getFechaMantenimiento()));
            return ps.executeUpdate()>0;
        }
    }
        
    public BusDTO obtenerBusConPlaca(String placa) throws SQLException{
        try (PreparedStatement ps= connection.prepareStatement(OBTENER_BUS_POR_PLACA)){
            ps.setString(1, placa);
            try (ResultSet rs= ps.executeQuery()) {
                if (rs.next()) {
                    return empaquetarBus(rs);
                }
            }
        }
        return null;
    }

    public boolean modificarBus(BusDTO bus) throws SQLException{
        try (PreparedStatement ps=connection.prepareStatement(MODIFICAR_BUS)){
            ps.setString(1, bus.getMarca());
            ps.setString(2, bus.getModelo());
            ps.setInt(3, bus.getAnoFabricacion());
            ps.setInt(4, bus.getCapacidad());
            ps.setBytes(5, bus.getFoto());
            ps.setInt(6, bus.getIdSucursal());
            ps.setString(7, bus.getNumeroPlaca());
            
            return ps.executeUpdate()>0;
        }
    }
    
    public boolean aumentarKilometraje(int nuevoKilometraje, String placa) throws SQLException{
        try (PreparedStatement ps= connection.prepareStatement(AMENTAR_KILOMETRAJE)){
            ps.setDouble(1, nuevoKilometraje);
            ps.setString(2, placa);
            return ps.executeUpdate()>0;
        }
    }
    
    public boolean cambiarEstado(String placa, EstadoBus nuevoEstado) throws SQLException{
        try (PreparedStatement ps= connection.prepareStatement(CAMBIAR_ESTADO_BUS)){
            ps.setString(1, String.valueOf(nuevoEstado));
            ps.setString(2, placa);
            return ps.executeUpdate()>0;
        }
    }

    public boolean desactivarBUs(String placa) throws SQLException{
        try (PreparedStatement ps= connection.prepareStatement(DESACTIVAR_BUS)){
            ps.setString(1, placa);
            return ps.executeUpdate()>0;
        }
    }
    
    public List<BusDTO> obtenerBusesSucursal(int idSiucrusal) throws SQLException{
        List<BusDTO> buses= new ArrayList<>();
        try (PreparedStatement ps= connection.prepareStatement(OBTENER_BUSES_SUCURSAL)){
            ps.setInt(1, idSiucrusal);
            try (ResultSet rs=ps.executeQuery()) {
                while (rs.next()) {
                    buses.add(empaquetarBus(rs));
                }
                return buses;
            }
        }        
    }
    
    //----SOLO DE UNA SUCURSAL
    public List<BusDTO> obtenerBusEstado(EstadoBus estado, int idSucusal) throws SQLException{
        List<BusDTO> buses= new ArrayList<>();
        try (PreparedStatement ps= connection.prepareStatement(OBTENER_BUSES_ESTADO)) {
            ps.setString(1, String.valueOf(estado));
            ps.setInt(2, idSucusal);
            try (ResultSet rs=ps.executeQuery()) {
                while (rs.next()) {
                    buses.add(empaquetarBus(rs));
                }
                return buses;
            }
        }
    }
    
    public List<RegistroTallerDTO> obtenerRegistrosBus(String placa) throws SQLException{
        List<RegistroTallerDTO> registros= new ArrayList<>();
        try (PreparedStatement ps= connection.prepareStatement(OBTENER_REGISTROS_TALLER_POR_BUS)) {
            ps.setString(1, placa);
            try (ResultSet rs=ps.executeQuery()) {
                while (rs.next()) {
                    registros.add(empaquetarRegistroT(rs));
                }
                    return registros;
            }
        }
    }
    
    
    //METODOS AUXILIARES SIN QUERY
    public BusDTO empaquetarBus(ResultSet rs) throws SQLException{
        String numeroPlaca = rs.getString("numero_de_placa");
        String marca = rs.getString("marca");
        String modelo = rs.getString("modelo");
        int anoFabricacion = rs.getInt("ano_de_fabricacion");
        int capacidad = rs.getInt("capacidad");
        int kilometrajeActual = rs.getInt("kilometraje_actual");
        byte[] foto = rs.getBytes("foto");
        EstadoBus estadoBus = EstadoBus.valueOf(rs.getString("estado"));
        boolean activo = rs.getBoolean("activo");
        int idSucursal = rs.getInt("id_sucursal");

        return new BusDTO(
            numeroPlaca, 
            modelo, 
            marca, 
            anoFabricacion, 
            capacidad, 
            kilometrajeActual, 
            foto, 
            estadoBus, 
            activo, 
            idSucursal
        );
    }
    
    public RegistroTallerDTO empaquetarRegistroT(ResultSet rs) throws SQLException{
        int idRegistro = rs.getInt("id_registro");
        String numeroPlaca = rs.getString("numero_de_placa");
        double montoManoDeObra = rs.getDouble("monto_mano_de_obra");
        double montoRepuestos = rs.getDouble("monto_repuestos");
        LocalDate fechaMantenimiento = rs.getDate("fecha_mantenimiento").toLocalDate();

        return new RegistroTallerDTO(
            idRegistro, 
            numeroPlaca, 
            montoManoDeObra, 
            montoRepuestos, 
            fechaMantenimiento
        );
    }
    
}
