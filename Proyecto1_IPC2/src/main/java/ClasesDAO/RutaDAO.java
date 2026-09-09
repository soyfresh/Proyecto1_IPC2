/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClasesDAO;

import ClasesDTO.RutaDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dar333n
 */
public class RutaDAO {
    public static final String CREAR_RUTA="""
                                          INSERT INTO ruta (distancia_aproximada, 
                                          precio_de_asiento, 
                                          id_sucursal_origen, 
                                          id_sucursal_destino, 
                                          descripcion_ruta)
                                          VALUES (?, ?, ?, ?, ?);
                                          """;

    public static final String MODIFICAR_RUTA="""
                                              UPDATE ruta 
                                              SET distancia_aproximada=?, 
                                              precio_de_asiento=?, 
                                              id_sucursal_origen=?, 
                                              id_sucursal_destino=?, 
                                              descripcion_ruta=?
                                              WHERE id_ruta=?;
                                              """;

    public static final String OBTENER_RUTA_POR_ID="SELECT * FROM ruta WHERE id_ruta=?;";

    public static final String RUTAS_ACTAVAS_DE_SUCURSAL="SELECT * FROM ruta WHERE id_sucursal_origen= ? AND activo=TRUE;";

    public static final String DESACTIVAR_RUTA="UPDATE ruta SET activo=FALSE WHERE id_ruta=?;";

    public static final String ELIMINAR_RUTA="DELETE FROM ruta WHERE id_ruta=?;";

    public static final String TIENE_VIAJES_ASOCIADOS="SELECT COUNT(*) AS viajes FROM viaje WHERE id_ruta=?;";
    
    
    private Connection connection;
    
    public RutaDAO(Connection connection) {
        this.connection = connection;
    }
    
    public boolean crearRuta(RutaDTO ruta) throws SQLException{
        try (PreparedStatement ps= connection.prepareStatement(CREAR_RUTA)){
            ps.setDouble(1, ruta.getDistanciaApoximada());
            ps.setDouble(2, ruta.getPrecioBoleto());
            ps.setInt(3, ruta.getIdSucursalOrigen());
            ps.setInt(4, ruta.getIdSucursalDestino());
            ps.setString(5, ruta.getDescripcionRuta());
            
            return ps.executeUpdate()>0;
        }
    }
    
    public boolean modificarRuta(RutaDTO ruta) throws SQLException{
        try (PreparedStatement ps=connection.prepareStatement(MODIFICAR_RUTA)) {
            ps.setDouble(1, ruta.getDistanciaApoximada());
            ps.setDouble(2, ruta.getPrecioBoleto());
            ps.setInt(3, ruta.getIdSucursalOrigen());
            ps.setInt(4, ruta.getIdSucursalDestino());
            ps.setString(5, ruta.getDescripcionRuta());
            ps.setInt(6, ruta.getIdRuta());
            return ps.executeUpdate()> 0;
        }
    }
    
    public RutaDTO obtenerConId(int id) throws SQLException{
        try (PreparedStatement ps= connection.prepareStatement(OBTENER_RUTA_POR_ID)){
            ps.setInt(1, id);
            try (ResultSet rs=ps.executeQuery()){
                if (rs.next()) {
                    return empaquetarRuta(rs);
                }
            }
        }
        
        return null;
    }
    
    
    
    public List<RutaDTO> obtenerRutasSucursal(int id) throws SQLException{
        List<RutaDTO> rutas =new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(RUTAS_ACTAVAS_DE_SUCURSAL)){
                    ps.setInt(1, id);
            try (ResultSet rs= ps.executeQuery()){
                while (rs.next()) {
                    rutas.add(empaquetarRuta(rs));
                }
            }
        }
        return rutas;
    }
    
    public boolean desactivarRuta(int id) throws SQLException{
        try (PreparedStatement ps= connection.prepareStatement(DESACTIVAR_RUTA)){
            ps.setInt(1, id);
            return ps.executeUpdate()>0;
        }
    } 
    
    //ESTE METODO Y EL SIGUIENTE SE DEBEN USAR JUNTOS
    public boolean tieneViajesAsociadso(int id) throws SQLException{
        try (PreparedStatement ps=connection.prepareStatement(TIENE_VIAJES_ASOCIADOS)){
            ps.setInt(1, id);
            try (ResultSet rs=ps.executeQuery()){
                if (rs.next()) {
                    return rs.getInt("viajes")>0;
                }
            }
        }
        return false;
    }
    
    public boolean eliminarRuta(int id) throws SQLException{
        try (PreparedStatement ps= connection.prepareStatement(ELIMINAR_RUTA)) {
            ps.setInt(1, id);
            return ps.executeUpdate()>0;
        }
    }
    
    //METODOS AUXILIARES SIN QUERY
    public RutaDTO empaquetarRuta(ResultSet rs) throws SQLException{
        int idRuta = rs.getInt("id_ruta");
        double distancia = rs.getDouble("distancia_aproximada");
        double precio = rs.getDouble("precio_de_asiento");
        int origen = rs.getInt("id_sucursal_origen");
        int destino = rs.getInt("id_sucursal_destino");
        boolean activo = rs.getBoolean("activo");
        String descripcion = rs.getString("descripcion_ruta");

        return new RutaDTO(idRuta, distancia, precio, origen, destino, activo, descripcion); 
        
    }
}
