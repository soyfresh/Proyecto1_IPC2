/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClasesDAO;

import ClasesDTO.ConfiguracionDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author dar333n
 */
public class ConfiguracionDAO {
    
    public static final String INSERTAR_CONFIGURACION="""
                                                      INSERT INTO configuracion (monto_por_km)
                                                      VALUES (?);
                                                      """;

    public static final String OBTENER_CONFIGURACION_ACTUAL="""
                                                            SELECT id_configuracion, monto_por_km 
                                                            FROM configuracion
                                                            ORDER BY id_configuracion DESC;
                                                            """;
    
    private Connection connection;
    
    public ConfiguracionDAO(Connection connection) {
        this.connection = connection;
    }
    
    public boolean cambiarConfiguracion(ConfiguracionDTO configuracion) throws SQLException{
        try (PreparedStatement ps=connection.prepareStatement(INSERTAR_CONFIGURACION)){
            ps.setInt(1, configuracion.getMontoPorKm());
            return ps.executeUpdate()>0;
        }
    }
    
    public ConfiguracionDTO obtenerConfiguracionActual() throws SQLException{
        try (PreparedStatement ps=connection.prepareStatement(OBTENER_CONFIGURACION_ACTUAL); ResultSet rs= ps.executeQuery()) {
            if (rs.next()){
                int idConfiguracion=rs.getInt("id_configuracion");
                int montoPorKm=rs.getInt("monto_por_km");
                return new ConfiguracionDTO(idConfiguracion, montoPorKm);
            }
        }
        return null;
    }
}
