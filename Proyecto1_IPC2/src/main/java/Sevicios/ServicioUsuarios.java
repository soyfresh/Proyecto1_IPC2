/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sevicios;

import ClasesDAO.CuentaDAO;
import ClasesDTO.Cuentas.AdministradorSistemaDTO;
import ClasesDTO.Cuentas.AdministradorSucursalDTO;
import ClasesDTO.Cuentas.ChoferDTO;
import ClasesDTO.Cuentas.ClienteDTO;
import ClasesDTO.Cuentas.CuentaDTO;
import ClasesDTO.RegistroRecargaDTO;
import ConexionDB.ConnectionDB;
import clasesAuxiliares.Exceptions.DatoInvalidoException;
import clasesAuxiliares.TipoLicencia;
import clasesAuxiliares.TipoUsuario;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author dar333n
 */
public class ServicioUsuarios {
    
    private ConnectionDB adminConexion;

    public ServicioUsuarios(ConnectionDB connection) {
        this.adminConexion = connection;
    }

    public boolean registrarUsuarioo(String contrasena, String correoElectronico, String dpi, String nombre, String nit, 
            String direccion, String telefono, String tipoUsuario) throws SQLException, DatoInvalidoException{
        
        try(Connection cone=adminConexion.getConnection()){
            try{
                CuentaDAO cuentaDao=new CuentaDAO(cone);
                cone.setAutoCommit(false);

                validarCuentaBase(contrasena, correoElectronico, dpi, nombre, nit, direccion, telefono, tipoUsuario);
                
                if (cuentaDao.existeDpi(dpi)) {
                    throw new DatoInvalidoException("El DPI ingresado ya se encuentra registrado");
                }

                if (cuentaDao.existeCorreo(correoElectronico)) {
                    throw new DatoInvalidoException("El correo electrónico ya está en uso");
                }
                
                TipoUsuario tipoUs;
                
                try{
                    tipoUs = TipoUsuario.valueOf(tipoUsuario);
                }catch(Exception e){
                    throw new DatoInvalidoException();
                }
                
                CuentaDTO cuenta=null;
                if(tipoUs.equals(TipoUsuario.CLIENTE)){
                    cuenta= new ClienteDTO(contrasena, correoElectronico, dpi, nombre, nit, direccion, telefono);
                }else if(tipoUs.equals(TipoUsuario.ADMINISTRADOR_SISTEMA)){
                    cuenta= new AdministradorSistemaDTO(contrasena, correoElectronico, dpi, nombre, nit, direccion, telefono);
                }else if(tipoUs.equals(TipoUsuario.ADMINISTRADOR_SUCURSAL)){
                    cuenta= new AdministradorSucursalDTO(contrasena, correoElectronico, dpi, nombre, nit, direccion, telefono);
                }

                if(cuenta==null){
                    throw new SQLException();
                }
                
                if(!cuentaDao.crearCuenta(cuenta)){
                    throw new SQLException();
                }

                boolean creado=false;
                if(cuenta instanceof ClienteDTO){
                    creado=cuentaDao.crearCliente((ClienteDTO) cuenta);
                }else if(cuenta instanceof AdministradorSucursalDTO){
                    creado=cuentaDao.crearAdminScursal((AdministradorSucursalDTO) cuenta);
                }else if(cuenta instanceof AdministradorSistemaDTO){
                    creado=cuentaDao.crearAdminSistema((AdministradorSistemaDTO) cuenta);
                }

                if(!creado){
                    throw new SQLException();
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

    public boolean registrarChofer(String contrasena, String correoElectronico, String dpi, String nombre, String nit, 
            String direccion, String telefono, String tipoUsuario, String numeroLicencia, String tipoLicencia, 
            String fechaVencimientoLicencia, String salario, byte[] foto, String idSucursal) throws SQLException, DatoInvalidoException{
        
        try(Connection cone=adminConexion.getConnection()){
            try{
                CuentaDAO cuentaDao=new CuentaDAO(cone);
                cone.setAutoCommit(false);
                
                validarCuentaBase(contrasena, correoElectronico, dpi, nombre, nit, direccion, telefono, tipoUsuario);
                
                if(cuentaDao.existeDpi(dpi)){
                    throw new DatoInvalidoException("El DPI ingresado ya se encuentra registrado");
                }

                if(cuentaDao.existeCorreo(correoElectronico)){
                    throw new DatoInvalidoException("El correo electrónico ya está en uso");
                }
                
                //numero de licencia existe
            
                //id sucursal ya asociada a ese dpi
            
            
                
                TipoUsuario tipoUs;
                try{
                    tipoUs = TipoUsuario.valueOf(tipoUsuario);
                }catch(Exception e){
                    throw new DatoInvalidoException();
                }
                
                if(!tipoUs.equals(TipoUsuario.CHOFER)){
                    throw new DatoInvalidoException();
                }
     
                ChoferDTO chofer=revisarDatosChofer(contrasena, correoElectronico, dpi, nombre, nit, direccion, telefono, 
                            numeroLicencia, tipoLicencia, fechaVencimientoLicencia, salario, foto, idSucursal);
                
                if(chofer==null){
                    throw new SQLException();
                }

                if(!cuentaDao.crearCuenta(chofer)){
                    throw new SQLException();
                }
                
                if(!cuentaDao.crearChofer(chofer)){
                    throw new SQLException();
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
    
    public Boolean actualizarCuenta(String dpiOriginal, String correoOriginal, String contrasena, String correoElectronico, String dpi, String nombre, String nit, 
            String direccion, String telefono, String tipoUsuario)throws SQLException, DatoInvalidoException{
        
        try(Connection cone=adminConexion.getConnection()){
            try{
                
                CuentaDAO cuentaDao=new CuentaDAO(cone);
                cone.setAutoCommit(false);

                validarCuentaBase(contrasena, correoElectronico, dpi, nombre, nit, direccion, telefono, tipoUsuario);

                if (dpiOriginal != null && !dpiOriginal.trim().equalsIgnoreCase(dpi.trim())){
                    if(cuentaDao.existeDpi(dpi)){
                       throw new DatoInvalidoException("El DPI ingresado ya se encuentra registrado"); 
                    }
                }

                if (correoElectronico!= null && !correoOriginal.trim().equalsIgnoreCase(correoElectronico) ){
                    throw new DatoInvalidoException("El correo electrónico ya está en uso");
                }
                
                TipoUsuario tipoUs;
                try{
                    tipoUs = TipoUsuario.valueOf(tipoUsuario);
                }catch(Exception e){
                    throw new DatoInvalidoException();
                }
                
                

                CuentaDTO cuenta=null;
                if(tipoUs.equals(TipoUsuario.CLIENTE)){
                    cuenta= new ClienteDTO(contrasena, correoElectronico, dpi, nombre, nit, direccion, telefono);
                }else if(tipoUs.equals(TipoUsuario.ADMINISTRADOR_SISTEMA)){
                    cuenta= new AdministradorSistemaDTO(contrasena, correoElectronico, dpi, nombre, nit, direccion, telefono);
                }else if(tipoUs.equals(TipoUsuario.ADMINISTRADOR_SUCURSAL)){
                    cuenta= new AdministradorSucursalDTO(contrasena, correoElectronico, dpi, nombre, nit, direccion, telefono);
                }else if(tipoUs.equals(TipoUsuario.CHOFER)){
                    cuenta=new ChoferDTO(contrasena, correoElectronico, dpi, nombre, nit, direccion, telefono);
                }

                if(cuenta==null){
                    throw new SQLException();
                }

                if(!cuentaDao.modificarCuenta(cuenta)){
                    throw new SQLException();
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

    public Boolean actualizarChofer(String contrasena, String correoElectronico, String dpi, String nombre, String nit, 
            String direccion, String telefono, String tipoUsuario, String numeroLicencia, String tipoLicencia, 
            String fechaVencimientoLicencia, String salario, byte[] foto, String idSucursal) throws SQLException, DatoInvalidoException{
        
        try(Connection cone=adminConexion.getConnection()){
            try{
                CuentaDAO cuentaDao=new CuentaDAO(cone);
                cone.setAutoCommit(false);

                validarCuentaBase(contrasena, correoElectronico, dpi, nombre, nit, direccion, telefono, tipoUsuario);

                TipoUsuario tipoUs;
                try{
                    tipoUs = TipoUsuario.valueOf(tipoUsuario);
                }catch(Exception e){
                    throw new DatoInvalidoException();
                }

                if(!tipoUs.equals(TipoUsuario.CHOFER)){
                        throw new DatoInvalidoException();
                }

                ChoferDTO chofer=revisarDatosChofer(contrasena, correoElectronico, dpi, nombre, nit, direccion, telefono, 
                            numeroLicencia, tipoLicencia, fechaVencimientoLicencia, salario, foto, idSucursal);

                if(chofer==null){
                    throw new SQLException();
                }

                if(!cuentaDao.modoficarChofer(chofer)){
                    throw new SQLException();
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
        
    public CuentaDTO autenticar(String correo, String contrasena) throws SQLException, DatoInvalidoException{
        try (Connection cone=adminConexion.getConnection()){
            CuentaDAO cuentaDao=new CuentaDAO(cone);
            
            if(correo==null || correo.isBlank() || contrasena==null || contrasena.isBlank()){
                throw new DatoInvalidoException("Debe llenar todas las casillas");
            }

            CuentaDTO cun=cuentaDao.ingresarCuenta(correo, contrasena);
            if(cun==null){
                throw new SQLException("Usuario o contraseña incorrectos");
            }
            return cun;
        }
    }
    
    public CuentaDTO buscarPorDpi(String dpi) throws SQLException, DatoInvalidoException{
        try (Connection cone=adminConexion.getConnection()){
            CuentaDAO cuentaDao=new CuentaDAO(cone);
            
            if(dpi==null || dpi.isEmpty() || dpi.isBlank() || dpi.length()!=13 || !dpi.trim().matches("\\d+")){
                throw new DatoInvalidoException();
            }
            
            CuentaDTO cun=cuentaDao.obtenerConDpi(dpi);
            if(cun==null){
                throw new SQLException("No se encontro ninguna cuenta con este dpi asociado");
            }
            return cun;
        }
    }
    
    public boolean cambiarContrasena(String dpi, String nuevaContrasena, String constraseñaAntigua, String verificarAntigua) throws SQLException, DatoInvalidoException{
        try (Connection cone=adminConexion.getConnection()){
            CuentaDAO cuentaDao= new CuentaDAO(cone);
            
            if(dpi==null || dpi.isEmpty() || dpi.isBlank() || dpi.length()!=13 || !dpi.trim().matches("\\d+")){
                throw new DatoInvalidoException();
            }
            
            if(nuevaContrasena.isEmpty() || nuevaContrasena.isBlank() || nuevaContrasena==null || 
                    constraseñaAntigua.isEmpty() || constraseñaAntigua.isBlank() || constraseñaAntigua==null ||
                    verificarAntigua.isEmpty() || verificarAntigua.isBlank() || verificarAntigua==null){
                
                throw new DatoInvalidoException();
            }
            
            if(!(constraseñaAntigua.equals(verificarAntigua))){
                throw new SQLException("Debe ingresar la constraseña antigua");
            }
            
            if(!(cuentaDao.cambiarContrasena(dpi, nuevaContrasena))){
                throw new SQLException("La constraseña no se pudo cambiar");
            }
            
            return true;
        }
    }
    
    //para desactivar por completo
    public boolean desactivarCuenta(String dpi) throws SQLException, DatoInvalidoException{
        try (Connection cone=adminConexion.getConnection()){
            CuentaDAO cuentaDao= new CuentaDAO(cone);
            
            if(dpi==null || dpi.isEmpty() || dpi.isBlank() || dpi.length()!=13 || !dpi.trim().matches("\\d+")){
                throw new DatoInvalidoException();
            }
            
            CuentaDTO cuenta=  cuentaDao.obtenerConDpi(dpi);
            if(cuenta==null){
                throw new DatoInvalidoException();
            }
            
            if (cuenta instanceof ChoferDTO) {
                if (cuentaDao.tieneViajesPendientesChofer(dpi)) {
                    throw new DatoInvalidoException("No se puede desactivar el chofer porque tiene viajes asignados o en tránsito");
                }
            }
            
            if (cuenta instanceof AdministradorSistemaDTO) {
                if (cuentaDao.contarAdminSistemaActivos() <= 1) {
                    throw new DatoInvalidoException("No se puede desactivar el único Administrador de Sistema activo");
                }
            }
            
            if(!cuentaDao.desactivarCuenta(dpi)){
                throw new SQLException("Esta cuenta no se pudo desactivar");
            }
            
            return true;
        }
    }
    
    public List<ChoferDTO> listarChoferesPorSucursal(String idSucursal) throws SQLException, DatoInvalidoException{
        try (Connection cone=adminConexion.getConnection()){
            CuentaDAO cuentaDao= new CuentaDAO(cone);
            
            if(idSucursal==null || idSucursal.isEmpty() || idSucursal.isBlank()){
                throw new DatoInvalidoException();
            }
            
            int id;
            try{
                id=Integer.parseInt(idSucursal.trim());
            }catch(NumberFormatException e){
                throw new DatoInvalidoException();
            }
            
            List<ChoferDTO> lista=cuentaDao.obtenerChoferesDeSucursal(id);
            if(lista==null){
                throw new SQLException("Lista vacia");
            }
            return lista;
        }
    }
    
    public List<AdministradorSucursalDTO> listarAdminSucursal() throws SQLException{
        try (Connection cone=adminConexion.getConnection()){
            CuentaDAO cuentaDao= new CuentaDAO(cone);
            
            List<AdministradorSucursalDTO> lista=cuentaDao.obtenerAdminSucursal();
            if(lista==null){
                throw new SQLException("Lista vacia");
            }
            return lista;
        }
    }
    
    public List<AdministradorSistemaDTO> listarAdminSistema() throws SQLException{
        try (Connection cone=adminConexion.getConnection()){
            CuentaDAO cuentaDao= new CuentaDAO(cone);
            
            List<AdministradorSistemaDTO> lista=cuentaDao.obtenerAdminSistema();
            if(lista==null){
                throw new SQLException("Lista vacia");
            }
            return lista;
        }
    }
    
    public boolean agregarSaldo(String dpi, String monto, String fechaRecarga) throws SQLException, DatoInvalidoException{
        try (Connection cone=adminConexion.getConnection()){
            try{
              CuentaDAO cuentaDao= new CuentaDAO(cone);
            cone.setAutoCommit(false);
            
            if(dpi==null || dpi.isEmpty() || dpi.isBlank() || dpi.length()!=13 || !dpi.trim().matches("\\d+")){
                throw new DatoInvalidoException();
            }
        
            if(monto==null || monto.isBlank() || monto.isEmpty() || fechaRecarga==null || fechaRecarga.isBlank() || fechaRecarga.isEmpty()){
                throw new DatoInvalidoException();
            }

            double montoFn;
            LocalDate fechaRecargaFn;

            try{
                montoFn = Double.parseDouble(monto.trim());
                fechaRecargaFn = LocalDate.parse(fechaRecarga);
            }catch(Exception e){
                throw new DatoInvalidoException();
            }

            if(montoFn<0){
                throw new DatoInvalidoException("El monto debe ser mayor a 0.00");
            }
            
            cuentaDao.agregarSaldo(dpi, montoFn);
            cuentaDao.registrarRecarga(dpi, montoFn, fechaRecargaFn);   
            
            cone.commit();
            return true;
            
            }catch(DatoInvalidoException | SQLException e){  
                try{
                    cone.rollback();
                }catch(SQLException ex){
                    ex.printStackTrace();
                }
                throw e;
            }finally{
                try {
                    cone.setAutoCommit(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    public List<RegistroRecargaDTO> obtenerHistorialRecargas(String dpi) throws SQLException, DatoInvalidoException{
        try (Connection cone=adminConexion.getConnection()) {
            CuentaDAO recargaDao = new CuentaDAO(cone);

            if(dpi==null || dpi.isEmpty() || dpi.isBlank() || dpi.length()!=13 || !dpi.trim().matches("\\d+")){
                throw new DatoInvalidoException();
            }
            
            List<RegistroRecargaDTO> historial = recargaDao.obtenerHistorialPorDpi(dpi);
            if (historial == null || historial.isEmpty()) {
                throw new SQLException("No existe ningun registro de recarga");
            }
            return historial;
        }
    }
    
    
    /*
    *
    *
    METODOS AUXILIARES
    *
    *
    */
    private ChoferDTO revisarDatosChofer(String contrasena, String correoElectronico, String dpi, String nombre, String nit, 
            String direccion, String telefono, String numeroLicencia, String tipoLicencia, 
            String fechaVencimientoLicencia, String salario, byte[] foto, String idSucursal) throws DatoInvalidoException{
            
        if(numeroLicencia==null || numeroLicencia.isEmpty() || numeroLicencia.isBlank() || numeroLicencia.length()!=13 || !numeroLicencia.trim().matches("\\d+")){
            throw new DatoInvalidoException();
        }

        if(tipoLicencia==null || tipoLicencia.trim().isEmpty() || tipoLicencia.isBlank()){
            throw new DatoInvalidoException();
        }

        if(fechaVencimientoLicencia==null || fechaVencimientoLicencia.trim().isEmpty() || fechaVencimientoLicencia.isBlank()){
            throw new DatoInvalidoException();
        }
        
        if(salario==null || salario.trim().isEmpty() || salario.isBlank()){
            throw new DatoInvalidoException();
        }
        
        if(foto==null){
            throw new DatoInvalidoException();
        }

        if(idSucursal==null || idSucursal.trim().isEmpty() || idSucursal.isBlank()){
            throw new DatoInvalidoException();
        }
        
        try{
            TipoLicencia tipoLic=TipoLicencia.valueOf(tipoLicencia);
            LocalDate fechVencimintL=LocalDate.parse(fechaVencimientoLicencia);
            double salrFinal= Double.parseDouble(salario);
            int idSuc=Integer.parseInt(idSucursal);
            
            return new ChoferDTO(
                contrasena,
                correoElectronico,
                dpi,
                nombre,
                nit,
                direccion,
                telefono,
                numeroLicencia,
                tipoLic,
                fechVencimintL,
                salrFinal,
                foto,
                idSuc
            );
            
        }catch(Exception e){
            throw new DatoInvalidoException();
        }
    }
    
    
    private void validarCuentaBase(String contrasena, String correoElectronico, String dpi, String nombre, 
            String nit, String direccion, String telefono, String tipoUsuario) throws DatoInvalidoException{
        
        if(contrasena==null || contrasena.trim().isEmpty() || contrasena.isBlank()){
            throw new DatoInvalidoException();
        }

        if(correoElectronico==null || correoElectronico.trim().isEmpty() || correoElectronico.isBlank() || !correoElectronico.contains("@")){
            throw new DatoInvalidoException();
        }

        if(dpi==null || dpi.isEmpty() || dpi.isBlank() || dpi.length()!=13 || !dpi.trim().matches("\\d+")){
            throw new DatoInvalidoException();
        }

        if(nombre==null || nombre.trim().isEmpty() || nombre.isBlank()){
            throw new DatoInvalidoException();
        }

        if(nit==null || nit.trim().isEmpty() || nit.isBlank()){
            throw new DatoInvalidoException();
        }

        if(direccion==null || direccion.trim().isEmpty() || direccion.isBlank()){
            throw new DatoInvalidoException();
        }

        if(telefono==null || telefono.isEmpty() || telefono.isBlank() || telefono.length()!=8 || !telefono.trim().matches("\\d+")){
            throw new DatoInvalidoException();
        }

        if(tipoUsuario==null || tipoUsuario.trim().isEmpty() || tipoUsuario.isBlank()){
            throw new DatoInvalidoException();
        }        
    }
    
    
}
