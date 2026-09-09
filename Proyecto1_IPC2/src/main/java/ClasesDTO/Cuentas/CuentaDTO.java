/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClasesDTO.Cuentas;

import clasesAuxiliares.TipoUsuario;

/**
 *
 * @author dar333n
 */
public abstract class CuentaDTO {
    
    private String contrasena;
    private String correoElectronico;
    private String dpi;
    private String nombre;
    private String nit;
    private String direccion;
    private String telefono;
    private double saldo;
    private boolean activo;
    private TipoUsuario tipoUsuario;

    //CONSTRUCTOR PARA INSTANCIAS
    public CuentaDTO(String contrasena, String correoElectronico, String dpi, String nombre, String nit, String direccion, String telefono, TipoUsuario tipoUsuario) {
        this.contrasena = contrasena;
        this.correoElectronico = correoElectronico;
        this.dpi = dpi;
        this.nombre = nombre;
        this.nit = nit;
        this.direccion = direccion;
        this.telefono = telefono;
        this.tipoUsuario = tipoUsuario;
    }

    //PARA LECTURAS
    public CuentaDTO(String contrasena, String correoElectronico, String dpi, String nombre, String nit, String direccion, String telefono, double saldo, boolean activo, TipoUsuario tipoUsuario) {
        this.contrasena = contrasena;
        this.correoElectronico = correoElectronico;
        this.dpi = dpi;
        this.nombre = nombre;
        this.nit = nit;
        this.direccion = direccion;
        this.telefono = telefono;
        this.saldo = saldo;
        this.activo = activo;
        this.tipoUsuario = tipoUsuario;
    }
    
    

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contraseña) {
        this.contrasena = contraseña;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
    
    
}
