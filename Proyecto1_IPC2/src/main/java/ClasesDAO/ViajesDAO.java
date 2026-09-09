/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClasesDAO;

import ClasesDTO.Viajes.ViajeDTO;
import ClasesDTO.Viajes.ViajePrivadoDTO;
import ClasesDTO.Viajes.ViajeRegularDTO;
import clasesAuxiliares.Departamento;
import clasesAuxiliares.EstadoViaje;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dar333n
 */
public class ViajesDAO {
    
    public static final String CREAR_VIAJE_PADRE="""
                                                INSERT INTO viaje(
                                                estado_viaje, 
                                                tipo, 
                                                id_sucursal) 
                                                VALUES(?, ?, ?);
                                                """;
    
    
    public static final String CREAR_VIAJE_REGULAR="""
                                                   INSERT INTO viaje_regular(
                                                   id_viaje, 
                                                   fecha_y_hora_de_salida, 
                                                   fecha_y_hora_estimada_de_llegada, 
                                                   id_ruta) 
                                                   VALUES(?, ?, ?, ?);
                                                   """;

    public static final String CREAR_VIAJE_PRIVADO="""
                                                   INSERT INTO viaje_privado(
                                                   id_viaje, 
                                                   origen, 
                                                   destino, 
                                                   departamentoOrigen, 
                                                   departamentoDestino, 
                                                   numero_de_pasajeros, 
                                                   fecha_de_salida, 
                                                   fecha_de_retorno, 
                                                   precio_estimado, 
                                                   dpi_cliente) 
                                                   VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
                                                    """;
    
    //RECUPERAN LOS DATOS PARA MOSTRARLOS EN LOS CUADROS DEL FORMULARIO
    public static final String OBTENER_VIAJE_REGULAR_CON_ID="""
                                                            SELECT viaje.*, viaje_regular.fecha_y_hora_de_salida, viaje_regular.fecha_y_hora_estimada_de_llegada, viaje_regular.id_ruta 
                                                            FROM viaje 
                                                            INNER JOIN viaje_regular ON viaje.id_viaje = viaje_regular.id_viaje 
                                                            WHERE viaje.id_viaje=?;
                                                            """;
    
    public static final String OBTENER_VIAJE_PRIVADO_CON_ID="""
                                                            SELECT 
                                                            viaje.*, 
                                                            viaje_privado.origen, 
                                                            viaje_privado.destino, 
                                                            viaje_privado.departamentoOrigen, 
                                                            viaje_privado.departamentoDestino, 
                                                            viaje_privado.numero_de_pasajeros, 
                                                            viaje_privado.fecha_de_salida, 
                                                            viaje_privado.fecha_de_retorno, 
                                                            viaje_privado.precio_estimado, 
                                                            viaje_privado.precio_final, 
                                                            viaje_privado.fecha_de_pago, 
                                                            viaje_privado.dpi_cliente 
                                                            FROM viaje 
                                                            INNER JOIN viaje_privado ON viaje.id_viaje = viaje_privado.id_viaje 
                                                            WHERE viaje.id_viaje=?;
                                                            """;
    
    //MODIFICAR VIAJE/VIAJE REGULAR
    public static final String MODIFICAR_VIAJE="""
                                               UPDATE viaje SET 
                                               numero_de_placa=?, 
                                               dpi_chofer=? 
                                               WHERE id_viaje=?;
                                               """;
    
    public static final String MODIFICAR_VIAJE_REGULAR="""
                                                       UPDATE viaje_regular SET 
                                                       fecha_y_hora_de_salida=?, 
                                                       fecha_y_hora_estimada_de_llegada=?, 
                                                       id_ruta=? 
                                                       WHERE id_viaje=?;
                                                       """;
    
    //REGISTROS DE SALIDA/LLEGADA
    public static final String REGISTRAR_SALIDA="""
                                                UPDATE viaje SET
                                                numero_de_placa=?, 
                                                dpi_chofer=?, 
                                                hora_real_salida=?, 
                                                kilometraje_actual_bus=?, 
                                                estado_viaje='INICIADO'
                                                WHERE id_viaje=?;
                                                """;
    
    public static final String REGISTRAR_LLEGADA="""
                                                 UPDATE viaje SET
                                                 hora_real_llegada=?,
                                                 kilometraje_final_bus=?,
                                                 total_de_gasto_combustible=?,
                                                 estado_viaje='TERMINADO'
                                                 WHERE id_viaje=?;
                                                 """;
    
    //VIAJES REGULARES DISPONIBLES, SOLO DE UNA SUCURSAL 
    public static final String VER_VIAJES_REGULARES="""
                                                    SELECT viaje.*, viaje_regular.fecha_y_hora_de_salida, viaje_regular.fecha_y_hora_estimada_de_llegada, viaje_regular.id_ruta 
                                                    FROM viaje 
                                                    INNER JOIN viaje_regular ON viaje.id_viaje=viaje_regular.id_viaje 
                                                    WHERE viaje.id_sucursal=? AND viaje.tipo='REGULAR' AND viaje.estado_viaje = 'PROGRAMADO';
                                                    """;
    
    //VIAJES PRIVADO, SOLO DE UNA SUCURSAL 
    public static final String VER_SOLICITUDES_PRIVADO="""
                                                        SELECT viaje.*, 
                                                        viaje_privado.origen, 
                                                        viaje_privado.destino, 
                                                        viaje_privado.departamentoOrigen, 
                                                        viaje_privado.departamentoDestino, 
                                                        viaje_privado.numero_de_pasajeros, 
                                                        viaje_privado.fecha_de_salida, 
                                                        viaje_privado.fecha_de_retorno, 
                                                        viaje_privado.precio_estimado, 
                                                        viaje_privado.precio_final, 
                                                        viaje_privado.fecha_de_pago, 
                                                        viaje_privado.dpi_cliente 
                                                        FROM viaje 
                                                        INNER JOIN viaje_privado ON viaje.id_viaje=viaje_privado.id_viaje 
                                                        WHERE viaje.id_sucursal=? AND viaje.tipo='PRIVADO';
                                                       """;
    
    public static final String RECHAZAR_VIAJE_PRIVADO="UPDATE viaje SET estado_viaje='RECHAZADO' WHERE id_viaje=?;";
    
    public static final String MODIFICAR_PRECIO_PRIVADO="UPDATE viaje_privado SET precio_final=? WHERE id_viaje=?;";
    
    public static final String ACEPTAR_VIAJE="UPDATE viaje SET estado_viaje='CONFIRMADO' WHERE id_viaje=?;";
    
    //SI SE PAGA, DEBE LLAMAR AL SIGUIENTE METODO
    public static final String PAGAR_VIAJE="""
                                            UPDATE viaje_privado 
                                            SET fecha_de_pago = ?, 
                                            precio_final = ? 
                                            WHERE id_viaje = ?;
                                           """;
    
    public static final String VIAJE_PAGADO="UPDATE viaje SET estado_viaje='PROGRAMADO' WHERE id_viaje=?";

    public static final String CANCELAR_VIAJE="UPDATE viaje SET estado_viaje='CANCELADO' WHERE id_viaje=?";

    public static final String ELIMINAR_VIAJE="DELETE FROM viaje WHERE id_viaje=?";
    
    public static final String VER_VIAJES_REGULARES_POR_CHOFER="""
                                                            SELECT viaje.*, 
                                                            viaje_regular.fecha_y_hora_de_salida, 
                                                            viaje_regular.fecha_y_hora_estimada_de_llegada, 
                                                            viaje_regular.id_ruta
                                                            FROM viaje
                                                            INNER JOIN viaje_regular 
                                                            ON viaje.id_viaje = viaje_regular.id_viaje
                                                            WHERE viaje.dpi_chofer = ? 
                                                            AND viaje.estado_viaje IN ('PROGRAMADO', 'INICIADO');
                                                            """;

    public static final String VER_VIAJES_PRIVADOS_POR_CHOFER="""
                                                            SELECT viaje.*, 
                                                            viaje_privado.origen, 
                                                            viaje_privado.destino, 
                                                            viaje_privado.departamentoOrigen, 
                                                            viaje_privado.departamentoDestino, 
                                                            viaje_privado.numero_de_pasajeros, 
                                                            viaje_privado.fecha_de_salida, 
                                                            viaje_privado.fecha_de_retorno, 
                                                            viaje_privado.precio_estimado, 
                                                            viaje_privado.precio_final, 
                                                            viaje_privado.fecha_de_pago, 
                                                            viaje_privado.dpi_cliente
                                                            FROM viaje
                                                            INNER JOIN viaje_privado 
                                                            ON viaje.id_viaje = viaje_privado.id_viaje
                                                            WHERE viaje.dpi_chofer = ? 
                                                            AND viaje.estado_viaje IN ('PROGRAMADO', 'INICIADO');
                                                            """;
    
    public static final String VER_MIS_SOLICITUDES_PRIVADO="""
                                                        SELECT viaje.*, 
                                                        viaje_privado.origen, 
                                                        viaje_privado.destino, 
                                                        viaje_privado.departamentoOrigen, 
                                                        viaje_privado.departamentoDestino, 
                                                        viaje_privado.numero_de_pasajeros, 
                                                        viaje_privado.fecha_de_salida, 
                                                        viaje_privado.fecha_de_retorno, 
                                                        viaje_privado.precio_estimado, 
                                                        viaje_privado.precio_final, 
                                                        viaje_privado.fecha_de_pago, 
                                                        viaje_privado.dpi_cliente 
                                                        FROM viaje 
                                                        INNER JOIN viaje_privado ON viaje.id_viaje=viaje_privado.id_viaje 
                                                        WHERE viaje_privado.dpi_cliente=?;
                                                        """;
    
    private Connection connection;
    
    public ViajesDAO(Connection connection) {
        this.connection = connection;
    }
    
    public int crearViaje(String estadoViaje, int idSucursal, String tipoViaje) throws SQLException{
        
        try(PreparedStatement ps= connection.prepareStatement(CREAR_VIAJE_PADRE)){
            ps.setString(1, estadoViaje);
            ps.setString(2, tipoViaje);
            ps.setInt(3, idSucursal);
            int filasModificadas=ps.executeUpdate();
            if(filasModificadas==0){
                return 0;
            }
        }

        try(PreparedStatement ps2=connection.prepareStatement("SELECT LAST_INSERT_ID()");ResultSet rs=ps2.executeQuery()){
            if(rs.next()){
                return rs.getInt(1);
            }
        }
        return 0;
    }
    
    public boolean crearViajeRegular(ViajeRegularDTO viajeRegular) throws SQLException{
        try(PreparedStatement ps=connection.prepareStatement(CREAR_VIAJE_REGULAR)){
            ps.setInt(1, viajeRegular.getIdViaje());
            ps.setObject(2, viajeRegular.getFechaHoraProgramadaSalida());
            ps.setObject(3, viajeRegular.getFechaHoraEstimadaLlegada());
            ps.setInt(4, viajeRegular.getIdRuta());
            return ps.executeUpdate() > 0;
        }
    }
    
    public boolean crearViajePrivado(ViajePrivadoDTO viajePrivado) throws SQLException{
        try(PreparedStatement ps=connection.prepareStatement(CREAR_VIAJE_PRIVADO)){
            ps.setInt(1, viajePrivado.getIdViaje());
            ps.setString(2, viajePrivado.getOrigen());
            ps.setString(3, viajePrivado.getDestino());
            ps.setString(4, String.valueOf(viajePrivado.getDepartamentoOrigen()));
            ps.setString(5, String.valueOf(viajePrivado.getDepartamentoDestino()));
            ps.setInt(6, viajePrivado.getNumeroPasajeros());
            ps.setObject(7, viajePrivado.getFechaSalida());
            ps.setObject(8, viajePrivado.getFechaRetorno());
            ps.setDouble(9, viajePrivado.getPrecioEstimado());
            ps.setString(10, viajePrivado.getDpiCliente());
        return ps.executeUpdate() > 0;
        }
    }
    
    public ViajeRegularDTO obtenerViajeRegularPorId(int idViaje) throws SQLException{
        try(PreparedStatement ps = connection.prepareStatement(OBTENER_VIAJE_REGULAR_CON_ID)){
            ps.setInt(1, idViaje);
            
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    return empaquetarViajeRegular(rs);
                }
            }
        }
        return null;
    }
    
    public ViajePrivadoDTO obtenerViajePrivadoPorId(int idViaje) throws SQLException {
        try(PreparedStatement ps = connection.prepareStatement(OBTENER_VIAJE_PRIVADO_CON_ID)){
            ps.setInt(1, idViaje);
            
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    return empaquetarViajePrivado(rs);
                }
            }
        }
        return null;
    }

    public boolean modificarViajePadre(String numeroPlaca, String dpiChofer, int idViaje) throws SQLException{
        try (PreparedStatement ps=connection.prepareStatement(MODIFICAR_VIAJE)) {
            ps.setString(1, numeroPlaca);
            ps.setString(2, dpiChofer);
            ps.setInt(3, idViaje);
            return ps.executeUpdate() > 0;
        }
    }
    
    public boolean modificarViajeRegular(LocalDateTime fechaSalida, LocalDateTime fechaLlegadaEstimada, int idRuta, int idViaje) throws SQLException {
        try (PreparedStatement ps=connection.prepareStatement(MODIFICAR_VIAJE_REGULAR)) {
            ps.setObject(1, fechaSalida);
            ps.setObject(2, fechaLlegadaEstimada);
            ps.setInt(3, idRuta);
            ps.setInt(4, idViaje);
            return ps.executeUpdate() > 0;
        }
    }
    
    public boolean registrarSalida(ViajeDTO viaje) throws SQLException{
        try(PreparedStatement ps=connection.prepareStatement(REGISTRAR_SALIDA)){
            ps.setString(1, viaje.getPlacaBusAsignado());
            ps.setString(2, viaje.getDpiChofer());
            ps.setObject(3, viaje.getHoraRealSalida());
            ps.setDouble(4, viaje.getKilometrajeActual());
            ps.setInt(5, viaje.getIdViaje());
            return ps.executeUpdate()> 0;
        }
    }
    
    public boolean registrarLlegada(ViajeDTO viaje) throws SQLException{
        try(PreparedStatement ps= connection.prepareStatement(REGISTRAR_LLEGADA)){
            ps.setObject(1, viaje.getHoraRealLlegada());
            ps.setDouble(2, viaje.getKilometrajeFinal());
            ps.setDouble(3, viaje.getGastoTotalCombustible());
            ps.setInt(4, viaje.getIdViaje());
            return ps.executeUpdate() > 0;
        }
    }
    
    public List<ViajeRegularDTO> verViajesRegularesPorSucursal(int idSucursal) throws SQLException{
        List<ViajeRegularDTO> lista=new ArrayList<>();
        try(PreparedStatement ps=connection.prepareStatement(VER_VIAJES_REGULARES)){
            ps.setInt(1, idSucursal);
            try(ResultSet rs=ps.executeQuery();){
                while (rs.next()) {
                    lista.add(empaquetarViajeRegular(rs));
                }
                return lista;
            }
        }
    }
    
    public List<ViajePrivadoDTO> verSolicitudesPrivadoPorSucursal(int idSucursal) throws SQLException{
        List<ViajePrivadoDTO> lista=new ArrayList<>();
        try(PreparedStatement ps= connection.prepareStatement(VER_SOLICITUDES_PRIVADO)){
            ps.setInt(1, idSucursal);
            try(ResultSet rs=ps.executeQuery()){
                while (rs.next()) {
                    lista.add(empaquetarViajePrivado(rs));
                }
            }
            return lista;
        }
    }

    public boolean rechazarViajePrivado(int idViaje) throws SQLException {
        try(PreparedStatement ps= connection.prepareStatement(RECHAZAR_VIAJE_PRIVADO)){
            ps.setInt(1, idViaje);
            return ps.executeUpdate()>0;
        }
    }

    public boolean modificarPrecioPrivado(double precioFinal, int idViaje) throws SQLException{
        try(PreparedStatement ps= connection.prepareStatement(MODIFICAR_PRECIO_PRIVADO)){
            ps.setDouble(1, precioFinal);
            ps.setInt(2, idViaje);
            return ps.executeUpdate()> 0;
        }
    }

    public boolean aceptarViaje(int idViaje) throws SQLException{
        try(PreparedStatement ps=connection.prepareStatement(ACEPTAR_VIAJE)){
            ps.setInt(1, idViaje);
            return ps.executeUpdate()> 0;
        }
    }
    
    /*
    *
    METODOS DEBEN USARSE JUNTOS 
    *
    */
    public boolean pagarViajePrivado(LocalDate fechaPago, double precioFinal, int idViaje) throws SQLException {
        try(PreparedStatement ps = connection.prepareStatement(PAGAR_VIAJE)){
            ps.setObject(1, fechaPago);
            ps.setDouble(2, precioFinal);
            ps.setInt(3, idViaje);
            return ps.executeUpdate()>0;
        }
    }
    
    public boolean marcarViajeComoPagado(int idViaje) throws SQLException {
        try(PreparedStatement ps = connection.prepareStatement(VIAJE_PAGADO)){
            ps.setInt(1, idViaje);
            return ps.executeUpdate() > 0;
        }   
    }
    /*
    *
    *
    */
    
    public boolean CancelarViajePrivado(int idViaje) throws SQLException{
        try(PreparedStatement ps= connection.prepareStatement(CANCELAR_VIAJE)){
            ps.setInt(1, idViaje);
            return ps.executeUpdate()>0;
        }
    }
    
    public boolean eliminarViaje(int idViaje) throws SQLException{
        try(PreparedStatement ps = connection.prepareStatement(ELIMINAR_VIAJE)){
            ps.setInt(1, idViaje);
            return ps.executeUpdate() > 0;
        }
    }
    
    public List<ViajeRegularDTO> verViajesRegularesPorChofer(String dpiChofer) throws SQLException{
        List<ViajeRegularDTO> lista=new ArrayList<>();
        try (PreparedStatement ps=connection.prepareStatement(VER_VIAJES_REGULARES_POR_CHOFER)){
            ps.setString(1, dpiChofer);
            try (ResultSet rs= ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(empaquetarViajeRegular(rs));
                }
            }
        }
        return lista;
    }
    
    public List<ViajePrivadoDTO> verViajesPrivadosPorChofer(String dpiChofer) throws SQLException {
        List<ViajePrivadoDTO> lista = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(VER_VIAJES_PRIVADOS_POR_CHOFER)){
            ps.setString(1, dpiChofer);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(empaquetarViajePrivado(rs));
                }
            }
        }
        return lista;
    }
    
    
    //METODOS AUXILIARES SIN QUERY
    public ViajeRegularDTO empaquetarViajeRegular(ResultSet rs) throws SQLException{
        EstadoViaje estado=EstadoViaje.valueOf(rs.getString("estado_viaje"));
        LocalTime horaSalida= rs.getTimestamp("hora_real_salida") != null ? 
                rs.getTimestamp("hora_real_salida").toLocalDateTime().toLocalTime() : null;
        LocalTime horaLlegada=rs.getTimestamp("hora_real_llegada") != null ? 
                rs.getTimestamp("hora_real_llegada").toLocalDateTime().toLocalTime() : null;

        return new ViajeRegularDTO(
            rs.getInt("id_viaje"),
            estado,
            rs.getDouble("kilometraje_actual_bus"),
            horaSalida,
            horaLlegada,
            rs.getDouble("kilometraje_final_bus"),
            rs.getDouble("total_de_gasto_combustible"),
            rs.getString("numero_de_placa"),
            rs.getString("dpi_chofer"),
            rs.getInt("id_ruta"),
            rs.getObject("fecha_y_hora_de_salida", LocalDateTime.class),
            rs.getObject("fecha_y_hora_estimada_de_llegada", LocalDateTime.class),
            rs.getInt("id_sucursal")
        );
    }
    
    public ViajePrivadoDTO empaquetarViajePrivado(ResultSet rs) throws SQLException{
        EstadoViaje estado=EstadoViaje.valueOf(rs.getString("estado_viaje"));
        Departamento depOrigen=Departamento.valueOf(rs.getString("departamentoOrigen"));
        Departamento depDestino= Departamento.valueOf(rs.getString("departamentoDestino"));
        
        LocalTime horaSalida= rs.getTimestamp("hora_real_salida") != null ? 
                rs.getTimestamp("hora_real_salida").toLocalDateTime().toLocalTime() : null;
        LocalTime horaLlegada= rs.getTimestamp("hora_real_llegada") != null ? 
                rs.getTimestamp("hora_real_llegada").toLocalDateTime().toLocalTime() : null;

        return new ViajePrivadoDTO(
            rs.getString("dpi_cliente"),
            rs.getString("origen"),
            rs.getString("destino"),
            rs.getInt("numero_de_pasajeros"),
            rs.getObject("fecha_de_salida", LocalDate.class),
            rs.getObject("fecha_de_retorno", LocalDate.class),
            rs.getObject("fecha_de_pago", LocalDate.class),
            rs.getDouble("precio_estimado"),
            rs.getDouble("precio_final"),
            rs.getInt("id_viaje"),
            estado,
            rs.getDouble("kilometraje_actual_bus"),
            horaSalida,
            horaLlegada,
            rs.getDouble("kilometraje_final_bus"),
            rs.getDouble("total_de_gasto_combustible"),
            rs.getString("numero_de_placa"),
            rs.getString("dpi_chofer"),
            depOrigen,
            depDestino,
            rs.getInt("id_sucursal")
        );
    }
}
