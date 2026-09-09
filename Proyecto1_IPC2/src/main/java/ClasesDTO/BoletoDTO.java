/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClasesDTO;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author dar333n
 */
public class BoletoDTO {

    private int idBoleto;
    private double total;
    private LocalDate fechaCompra;
    private int idViaje;
    private String dpiCliente;
    private List<DetalleBoletoDTO> detalle;

    //PARA INSTANCIAS
    public BoletoDTO(int total, LocalDate fechaCompra, int idViaje, String dpiCliente) { 

        /*
        debe haber un metodo que genere el detalle en la clase donde se cree la instancia 
        
        primero el boleto y luego los detallers
        */
        
        this.total = total;
        this.fechaCompra = fechaCompra;
        this.idViaje = idViaje;
        this.dpiCliente = dpiCliente;
    }

    //PARA LECTURAS
    public BoletoDTO(int idBoleto, int total, LocalDate fechaCompra, int idViaje, String dpiCliente, List<DetalleBoletoDTO> detalle) {
        this.idBoleto = idBoleto;
        this.total = total;
        this.fechaCompra = fechaCompra;
        this.idViaje = idViaje;
        this.dpiCliente = dpiCliente;
        this.detalle = detalle;
    }

    public int getIdBoleto() {
        return idBoleto;
    }

    public void setIdBoleto(int idBoleto) {
        this.idBoleto = idBoleto;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public int getIdViaje() {
        return idViaje;
    }

    public void setIdViaje(int idViaje) {
        this.idViaje = idViaje;
    }

    public String getDpiCliente() {
        return dpiCliente;
    }

    public void setDpiCliente(String dpiCliente) {
        this.dpiCliente = dpiCliente;
    }

    public List<DetalleBoletoDTO> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<DetalleBoletoDTO> detalle) {
        this.detalle = detalle;
    }

    
 
}
