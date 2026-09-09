/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClasesDAO;

import ClasesDTO.SucursalDTO;
import clasesAuxiliares.Departamento;
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
public class SucursalDAO {
    public static final String CREAR_SUCURSAL="""
                                              INSERT INTO sucursal (dpi, departamento_sucursal, direccion_de_la_sucursal)
                                              VALUES (?, ?, ?);
                                              """;

    public static final String MODIFICAR_SUCURSAL="""
                                                  UPDATE sucursal 
                                                  SET dpi=?, departamento_sucursal=?, direccion_de_la_sucursal=? 
                                                  WHERE id_sucursal= ?;
                                                  """;

    public static final String OBTENER_SUCURSAL_CON_ID="""
                                                       SELECT * FROM sucursal 
                                                       WHERE id_sucursal=?;
                                                       """;

    public static final String OBTENER_TODAS_LAS_SUCURSALES="SELECT * FROM sucursal;";

    public static final String CAMBIAR_ADMIN="""
                                             UPDATE sucursal 
                                             SET dpi=? 
                                             WHERE id_sucursal= ?;
                                             """;

    public static final String OBTENER_SUCURSAL_CON_DPI_ADMIN="SELECT * FROM sucursal WHERE dpi =?;";

    //CONTEO PAEA SABER SI SE PUEDE ELIMINAR 
    public static final String TIENE_ENTIDADES_ASOCIADAS="""
                                                        SELECT 
                                                        (SELECT COUNT(*) FROM bus WHERE id_sucursal = ?) +
                                                        (SELECT COUNT(*) FROM chofer WHERE id_sucursal = ?) +
                                                        (SELECT COUNT(*) FROM ruta WHERE id_sucursal_origen = ? OR id_sucursal_destino = ?) 
                                                        AS numero;
                                                        """;
    
    private Connection connection;
    
    public SucursalDAO(Connection connection) {
        this.connection = connection;
    }
    
    public boolean crearSucursal(SucursalDTO sucursal) throws SQLException{
        try (PreparedStatement ps= connection.prepareStatement(CREAR_SUCURSAL)){
            ps.setString(1, sucursal.getDpiAdministradorSc());
            ps.setString(2, String.valueOf(sucursal.getDepartamento()));
            ps.setString(3, sucursal.getDireccion());
            return ps.executeUpdate()>0;
        }
    }
    
    public boolean modificaSucursal(SucursalDTO sucursal) throws SQLException{
        try (PreparedStatement ps= connection.prepareStatement(MODIFICAR_SUCURSAL)){
            ps.setString(1, sucursal.getDpiAdministradorSc());
            ps.setString(2, String.valueOf(sucursal.getDepartamento()));
            ps.setString(3, sucursal.getDireccion());
            ps.setInt(4, sucursal.getIdScucursal());
            return ps.executeUpdate()>0;
        }
    }
    
    public SucursalDTO obtenerSucursalId(int id) throws SQLException{
        try (PreparedStatement ps= connection.prepareStatement(OBTENER_SUCURSAL_CON_ID)){
            ps.setInt(1, id);
            try (ResultSet rs=ps.executeQuery()) {
                if (rs.next()) {
                    return empaqeutarSucursal(rs);
                }
            }
        }
        return null;
    }
    
    public List<SucursalDTO> obtenerSucursales() throws SQLException{
        List<SucursalDTO> sucursales=new ArrayList<>();
        try (PreparedStatement ps= connection.prepareStatement(OBTENER_TODAS_LAS_SUCURSALES); ResultSet rs = ps.executeQuery()){
            while (rs.next()) {
            sucursales.add(empaqeutarSucursal(rs));
            }
        }
        return sucursales;
    }
    
    public boolean modificarAdminSucursal(int idSucursal, String dpiNuevo) throws SQLException{
        try (PreparedStatement ps= connection.prepareStatement(CAMBIAR_ADMIN)){
            ps.setString(1, dpiNuevo);
            ps.setInt(2, idSucursal);
            return ps.executeUpdate() > 0;
        }
    }
    
    public SucursalDTO obtenerConDPI(String dpi) throws SQLException{
        try (PreparedStatement ps=connection.prepareStatement(OBTENER_SUCURSAL_CON_DPI_ADMIN)){
            ps.setString(1, dpi);
            try (ResultSet rs=ps.executeQuery()) {
                if (rs.next()) {
                    return empaqeutarSucursal(rs);
                }
            }
        }
        return null;
    }
    
    public boolean tieneEntidadAsociada(int idSucursal) throws SQLException{
        try (PreparedStatement ps= connection.prepareStatement(TIENE_ENTIDADES_ASOCIADAS)) {
            ps.setInt(1, idSucursal);
            ps.setInt(2, idSucursal);
            ps.setInt(3, idSucursal);
            ps.setInt(4, idSucursal);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("numero")>0;
                }
            }
        }
        return false;
    }
    
    //METODOS AUXILIARES SIN QUERY
    public SucursalDTO empaqeutarSucursal(ResultSet rs) throws SQLException{
        int idSucursal = rs.getInt("id_sucursal");
        String dpi = rs.getString("dpi");
        Departamento departamento = Departamento.valueOf(rs.getString("departamento_sucursal"));
        String direccion = rs.getString("direccion_de_la_sucursal");
        return new SucursalDTO(idSucursal, direccion, dpi, departamento);
    }
}
