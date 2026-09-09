/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClasesDAO;

import ClasesDTO.Cuentas.AdministradorSistemaDTO;
import ClasesDTO.Cuentas.AdministradorSucursalDTO;
import ClasesDTO.Cuentas.ChoferDTO;
import ClasesDTO.Cuentas.ClienteDTO;
import ClasesDTO.Cuentas.CuentaDTO;
import clasesAuxiliares.TipoLicencia;
import clasesAuxiliares.TipoUsuario;
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
public class CuentasDAO {
    
    public static final String CREAR_CUENTA_PADRE="""
                                                  INSERT INTO cuenta (dpi, 
                                                  nit, 
                                                  nombre, 
                                                  correo_electronico, 
                                                  contrasena, telefono, 
                                                  direccion, 
                                                  saldo, tipo, activo)
                                                  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);  
                                                   """;
    
    
    public static final String CREAR_CLIENTE="INSERT INTO cliente (dpi) VALUES (?);";
    
    public static final String CREAR_ADMIN_SISTEMA="INSERT INTO administrador_sistema (dpi) VALUES (?);";
    
    public static final String CREAR_ADMIN_SUCURSAL="INSERT INTO administrador_sucursal (dpi) VALUES (?);";
    
    public static final String CREAR_CHOFER="""
                                             INSERT INTO chofer (dpi, 
                                             numero_de_licencia, 
                                             tipo_de_licencia, 
                                             fecha_de_vencimiento_licencia, 
                                             salario, foto, id_sucursal)
                                             VALUES (?, ?, ?, ?, ?, ?, ?);
                                             """;
    
    
    public static final String MODIFICAR_CUENTA="UPDATE cuenta SET nit=?, nombre=?, telefono=?, direccion=? WHERE dpi=?;";   
            
    public static final String MODIFICAR_CHOFER="""
                                                UPDATE chofer SET
                                                numero_de_licencia=?,
                                                tipo_de_licencia=?,
                                                fecha_de_vencimiento_licencia=?,
                                                salario=?,
                                                foto=?,
                                                id_sucursal=?
                                                WHERE dpi=?;
                                                """;
    
    public static final String AGREGAR_SALDO="UPDATE cuenta SET saldo=saldo+? WHERE dpi=?;";
    
    public static final String QUITAR_SALDO="UPDATE cuenta SET saldo=saldo-? WHERE dpi=?;";
    
    public static final String CAMBIAR_CONSTRASENA="UPDATE cuenta SET contrasena=? WHERE dpi=?;";
    
    public static final String CAMBIAR_ACTIVO="UPDATE cuenta SET activo=? WHERE dpi=?;";
    
    public static final String OBTENER_CUENTA_CONTRASENA_CORREO="""
                                                                SELECT cuenta.*, 
                                                                chofer.numero_de_licencia, 
                                                                chofer.tipo_de_licencia, 
                                                                chofer.fecha_de_vencimiento_licencia, 
                                                                chofer.salario, 
                                                                chofer.foto, 
                                                                chofer.id_sucursal
                                                                FROM cuenta
                                                                LEFT JOIN chofer ON cuenta.dpi = chofer.dpi
                                                                WHERE cuenta.correo_electronico=? AND cuenta.contrasena=? AND cuenta.activo = TRUE;
                                                                """;
    
    public static final String OBTENER_CUENTA_CON_DPI="""
                                                      SELECT cuenta.*, 
                                                      chofer.numero_de_licencia, 
                                                      chofer.tipo_de_licencia, 
                                                      chofer.fecha_de_vencimiento_licencia, 
                                                      chofer.salario, 
                                                      chofer.foto, 
                                                      chofer.id_sucursal
                                                      FROM cuenta
                                                      LEFT JOIN chofer ON cuenta.dpi = chofer.dpi
                                                      WHERE cuenta.dpi=?;
                                                      """;
    
    
    public static final String OBTENER_CHOFERES_POR_SUCURSAL="""
                                                             SELECT cuenta.*, 
                                                             chofer.numero_de_licencia, 
                                                             chofer.tipo_de_licencia, 
                                                             chofer.fecha_de_vencimiento_licencia, 
                                                             chofer.salario, 
                                                             chofer.foto, 
                                                             chofer.id_sucursal
                                                             FROM cuenta
                                                             INNER JOIN chofer ON cuenta.dpi = chofer.dpi
                                                             WHERE chofer.id_sucursal=? AND cuenta.activo=TRUE;
                                                             """;

    public static final String LISTAR_ADMINS_SUCURSAL="""
                                                      SELECT cuenta.* FROM cuenta
                                                      INNER JOIN administrador_sucursal ON cuenta.dpi = administrador_sucursal.dpi;
                                                      """;

    private Connection connection;
    
    public CuentasDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean crearCuenta(CuentaDTO cuenta) throws SQLException{
        try(PreparedStatement ps= connection.prepareStatement(CREAR_CUENTA_PADRE)){
            ps.setString(1, cuenta.getDpi());
            ps.setString(2, cuenta.getNit());
            ps.setString(3, cuenta.getNombre());
            ps.setString(4, cuenta.getCorreoElectronico());
            ps.setString(5, cuenta.getContrasena());
            ps.setString(6, cuenta.getTelefono());
            ps.setString(7, cuenta.getDireccion());
            ps.setDouble(8, cuenta.getSaldo());
            ps.setString(9, String.valueOf(cuenta.getTipoUsuario()));
            ps.setBoolean(10, cuenta.isActivo());
            return ps.executeUpdate()>0;
        }
        
    }
    
    public boolean crearCliente(ClienteDTO cliente) throws SQLException{
        try (PreparedStatement ps= connection.prepareStatement(CREAR_CLIENTE)){
            ps.setString(1, cliente.getDpi());
            return ps.executeUpdate()> 0;
        }
    }
    
    public boolean crearAdminSistema(AdministradorSistemaDTO admin) throws SQLException{
        try (PreparedStatement ps= connection.prepareStatement(CREAR_ADMIN_SISTEMA)){
            ps.setString(1, admin.getDpi());
            return ps.executeUpdate() > 0;
        }
    }
    
    public boolean crearAdminScursal(AdministradorSucursalDTO admin) throws SQLException{
        try (PreparedStatement ps=connection.prepareStatement(CREAR_ADMIN_SUCURSAL)){
            ps.setString(1, admin.getDpi());
            return ps.executeUpdate()> 0;
        }
    }
    
    public boolean crearChofer(ChoferDTO chofer) throws SQLException{
        try (PreparedStatement ps = connection.prepareStatement(CREAR_CHOFER)){
            ps.setString(1, chofer.getDpi());
            ps.setString(2, chofer.getNumeroLicencia());
            ps.setString(3, String.valueOf(chofer.getTipoLicencia()));
            ps.setObject(4, chofer.getFechaVencimientoLicencia());
            ps.setDouble(5, chofer.getSalario());
            ps.setBytes(6, chofer.getFoto());
            ps.setInt(7, chofer.getIdSucursal());
            return ps.executeUpdate() > 0;
        }
    }
    
    public boolean modificarCuenta(CuentaDTO cuenta) throws SQLException{
        try (PreparedStatement ps= connection.prepareStatement(MODIFICAR_CUENTA)){
            ps.setString(1, cuenta.getNit());
            ps.setString(2, cuenta.getNombre());
            ps.setString(3, cuenta.getTelefono());
            ps.setString(4, cuenta.getDireccion());
            ps.setString(5, cuenta.getDpi());
            return ps.executeUpdate() > 0;
        }
    }
    
    //metodo para el admin de sucursal
    public boolean modoficarChofer(ChoferDTO chofer) throws SQLException{
        try (PreparedStatement ps=connection.prepareStatement(MODIFICAR_CHOFER)){
            ps.setString(1, chofer.getNumeroLicencia());
            ps.setString(2, String.valueOf(chofer.getTipoLicencia()));
            ps.setObject(3, chofer.getFechaVencimientoLicencia());
            ps.setDouble(4, chofer.getSalario());
            ps.setBytes(5, chofer.getFoto());
            ps.setInt(6, chofer.getIdSucursal());
            ps.setString(7, chofer.getDpi());
            return ps.executeUpdate() > 0;
        }
    }
    
    public boolean agregarSaldo(String dpi, double monto) throws SQLException{
        try (PreparedStatement ps= connection.prepareStatement(AGREGAR_SALDO)){
            ps.setDouble(1, monto);
            ps.setString(2, dpi);
            return ps.executeUpdate()> 0;
        }
    }
    
    public boolean quitarSaldo(String dpi, double monto) throws SQLException{
        try (PreparedStatement ps= connection.prepareStatement(QUITAR_SALDO)){
            ps.setDouble(1, monto);
            ps.setString(2, dpi);
            return ps.executeUpdate()> 0;
        }
    }
    
    public boolean cambiarContrasena(String dpi, String contrasena) throws SQLException{
        try (PreparedStatement ps= connection.prepareStatement(CAMBIAR_CONSTRASENA)){
            ps.setString(1, contrasena);
            ps.setString(2, dpi);
            return ps.executeUpdate() > 0;
        }
    }
    
    //para desactivar cuentas o reactiuvarlas en caso de ser chofer
    public boolean cambiarEstado(String dpi, Boolean estado) throws SQLException{
        try (PreparedStatement ps = connection.prepareStatement(CAMBIAR_ACTIVO)){
            ps.setBoolean(1, estado);
            ps.setString(2, dpi);
            return ps.executeUpdate()> 0;
        }
    }
    
    public CuentaDTO ingresarCuenta(String correo, String constrasena) throws SQLException{
        try (PreparedStatement ps= connection.prepareStatement(OBTENER_CUENTA_CONTRASENA_CORREO)){
            ps.setString(1, correo);
            ps.setString(2, constrasena);
            try (ResultSet rs= ps.executeQuery()) {
                if (rs.next()) {
                    return empaquetarCuenta(rs);
                }
            }
        }
        return null;
    }
    
    public CuentaDTO obtenerConDpi(String dpi) throws SQLException{
        try (PreparedStatement ps= connection.prepareStatement(OBTENER_CUENTA_CON_DPI)) {
            ps.setString(1, dpi);
            try (ResultSet rs=ps.executeQuery()) {
                if (rs.next()) {
                    return empaquetarCuenta(rs);
                }
            }
        } 
        return null;
    }
    
    public List<ChoferDTO> obtenerChoferesDeSucursal(int idSucursal) throws SQLException{
        List<ChoferDTO> choferes= new ArrayList<>();
        try (PreparedStatement ps= connection.prepareStatement(OBTENER_CHOFERES_POR_SUCURSAL)){
            ps.setInt(1, idSucursal);
            try (ResultSet rs=ps.executeQuery()) {
                while (rs.next()) {
                    choferes.add((ChoferDTO) empaquetarCuenta(rs));
                }
            }
        }
        return choferes;
    }
    
    public List<AdministradorSucursalDTO> obtenerAdminSucursal() throws SQLException{
        List<AdministradorSucursalDTO> administradores= new ArrayList<>();
        try (PreparedStatement ps= connection.prepareStatement(LISTAR_ADMINS_SUCURSAL)){
            try (ResultSet rs=ps.executeQuery()){
                while (rs.next()) {
                    administradores.add((AdministradorSucursalDTO) empaquetarCuenta(rs));
                }
            }
        }
        return administradores;
    }
    
    
    
    //METODOS AUXILIARES SIN QUERY
    public CuentaDTO empaquetarCuenta(ResultSet rs) throws SQLException{
        String dpi = rs.getString("dpi");
        String nit = rs.getString("nit");
        String nombre = rs.getString("nombre");
        String correo = rs.getString("correo_electronico");
        String contrasena = rs.getString("contrasena");
        String telefono = rs.getString("telefono");
        String direccion = rs.getString("direccion");
        double saldo = rs.getDouble("saldo");
        boolean activo = rs.getBoolean("activo");
        TipoUsuario tipo = TipoUsuario.valueOf(rs.getString("tipo"));

        switch (tipo){
            case CLIENTE: 
                return new ClienteDTO(contrasena, correo, dpi, nombre, nit, direccion, telefono, saldo, activo);
                
            case ADMINISTRADOR_SISTEMA: 
                return new AdministradorSistemaDTO(contrasena, correo, dpi, nombre, nit, direccion, telefono, saldo, activo);
                
            case ADMINISTRADOR_SUCURSAL: 
                return new AdministradorSucursalDTO(contrasena, correo, dpi, nombre, nit, direccion, telefono, saldo, activo);
                
            case CHOFER: 
                return new ChoferDTO(
                    contrasena, correo, dpi, nombre, nit, direccion, telefono, saldo, activo,
                    rs.getString("numero_de_licencia"),
                    TipoLicencia.valueOf(rs.getString("tipo_de_licencia")),
                    rs.getDate("fecha_de_vencimiento_licencia").toLocalDate(),
                    rs.getDouble("salario"),
                    rs.getBytes("foto"),
                    rs.getInt("id_sucursal")
            );
        }; 
        return null; 
    }
}
