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
public class RegistroRecargaDTO {
    
    private int idRecarga;
    private String dpi;
    private double monto;
    private LocalDate fechaRecarga;
    
    //PARA INSTANCIAS

    public RegistroRecargaDTO(String dpi, double monto, LocalDate fechaRecarga) {
        this.dpi = dpi;
        this.monto = monto;
        this.fechaRecarga = fechaRecarga;
    }
    
    //PARA LECTURAS

    public RegistroRecargaDTO(int idRecarga, String dpi, double monto, LocalDate fechaRecarga) {
        this.idRecarga = idRecarga;
        this.dpi = dpi;
        this.monto = monto;
        this.fechaRecarga = fechaRecarga;
    }

    public int getIdRecarga() {
        return idRecarga;
    }

    public void setIdRecarga(int idRecarga) {
        this.idRecarga = idRecarga;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public LocalDate getFechaRecarga() {
        return fechaRecarga;
    }

    public void setFechaRecarga(LocalDate fechaRecarga) {
        this.fechaRecarga = fechaRecarga;
    }
    
    
}
