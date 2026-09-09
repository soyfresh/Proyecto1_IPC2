/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClasesDTO.Cuentas;

import ClasesDTO.Cuentas.CuentaDTO;
import clasesAuxiliares.TipoLicencia;
import clasesAuxiliares.TipoUsuario;
import java.time.LocalDate;

/**
 *
 * @author dar333n
 */
public class ChoferDTO extends CuentaDTO{
    
    private String numeroLicencia;
    private TipoLicencia tipoLicencia;
    private LocalDate fechaVencimientoLicencia;
    private double salario;
    private byte[] foto;
    private int idSucursal;
    
    //CONSTRUCTOR PARA INSTANCIAS
    public ChoferDTO(String contrasena, String correoElectronico, String dpi, String nombre, 
                      String nit, String direccion, String telefono, String numeroLicencia, 
                      TipoLicencia tipoLicencia, LocalDate fechaVencimientoLicencia, 
                      double salario, byte[] foto, int idSucursal) {
        
        super(contrasena, correoElectronico, dpi, nombre, nit, direccion, telefono, TipoUsuario.CHOFER);
        
        this.numeroLicencia = numeroLicencia;
        this.tipoLicencia = tipoLicencia;
        this.fechaVencimientoLicencia = fechaVencimientoLicencia;
        this.salario = salario;
        this.foto = foto;
        this.idSucursal = idSucursal;
    }
    
    //PARA LECTURAS
    public ChoferDTO(String contrasena, String correoElectronico, String dpi, String nombre, 
                      String nit, String direccion, String telefono, double saldo, boolean activo, 
                      String numeroLicencia, TipoLicencia tipoLicencia, LocalDate fechaVencimientoLicencia, 
                      double salario, byte[] foto, int idSucursal) {
        
        super(contrasena, correoElectronico, dpi, nombre, nit, direccion, telefono, saldo, activo, TipoUsuario.CHOFER);
        
        this.numeroLicencia = numeroLicencia;
        this.tipoLicencia = tipoLicencia;
        this.fechaVencimientoLicencia = fechaVencimientoLicencia;
        this.salario = salario;
        this.foto = foto;
        this.idSucursal = idSucursal;
    }

    public String getNumeroLicencia() {
        return numeroLicencia;
    }

    public void setNumeroLicencia(String numeroLicencia) {
        this.numeroLicencia = numeroLicencia;
    }

    public TipoLicencia getTipoLicencia() {
        return tipoLicencia;
    }

    public void setTipoLicencia(TipoLicencia tipoLicencia) {
        this.tipoLicencia = tipoLicencia;
    }

    public LocalDate getFechaVencimientoLicencia() {
        return fechaVencimientoLicencia;
    }

    public void setFechaVencimientoLicencia(LocalDate fechaVencimientoLicencia) {
        this.fechaVencimientoLicencia = fechaVencimientoLicencia;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }
    
    
}
