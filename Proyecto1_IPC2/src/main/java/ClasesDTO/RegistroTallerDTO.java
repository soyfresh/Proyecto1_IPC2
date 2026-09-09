/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClasesDTO;

import java.time.LocalDate;

/**
 *
 * @author dar333n
 */
public class RegistroTallerDTO {
    private int idRegistro;
    private String numeroPlaca;
    private double montoManoDeObra;
    private double montoRepuestos;
    private LocalDate fechaMantenimiento;

    //PARA INSTANCIAS
    public RegistroTallerDTO(String numeroPlaca, double montoManoDeObra, double montoRepuestos, LocalDate fechaMantenimiento) {
        this.numeroPlaca = numeroPlaca;
        this.montoManoDeObra = montoManoDeObra;
        this.montoRepuestos = montoRepuestos;
        this.fechaMantenimiento = fechaMantenimiento;
    }
    
    //PARA LECTURAS
    public RegistroTallerDTO(int idRegistro, String numeroPlaca, double montoManoDeObra, double montoRepuestos, LocalDate fechaMantenimiento) {
        this.idRegistro = idRegistro;
        this.numeroPlaca = numeroPlaca;
        this.montoManoDeObra = montoManoDeObra;
        this.montoRepuestos = montoRepuestos;
        this.fechaMantenimiento = fechaMantenimiento;
    }

    public int getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }

    public String getNumeroPlaca() {
        return numeroPlaca;
    }

    public void setNumeroPlaca(String numeroPlaca) {
        this.numeroPlaca = numeroPlaca;
    }

    public double getMontoManoDeObra() {
        return montoManoDeObra;
    }

    public void setMontoManoDeObra(double montoManoDeObra) {
        this.montoManoDeObra = montoManoDeObra;
    }

    public double getMontoRepuestos() {
        return montoRepuestos;
    }

    public void setMontoRepuestos(double montoRepuestos) {
        this.montoRepuestos = montoRepuestos;
    }

    public LocalDate getFechaMantenimiento() {
        return fechaMantenimiento;
    }

    public void setFechaMantenimiento(LocalDate fechaMantenimiento) {
        this.fechaMantenimiento = fechaMantenimiento;
    }
    
    
}
