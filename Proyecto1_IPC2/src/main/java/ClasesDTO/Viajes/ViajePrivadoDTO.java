/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClasesDTO.Viajes;

import clasesAuxiliares.Departamento;
import clasesAuxiliares.EstadoViaje;
import clasesAuxiliares.TipoViaje;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author dar333n
 */
public class ViajePrivadoDTO extends ViajeDTO{

    private String origen;
    private String destino;
    private int numeroPasajeros;
    private LocalDate fechaSalida;
    private LocalDate fechaRetorno;
    private LocalDate fechaPago;
    private double precioEstimado;
    private Double precioFinal;
    private String dpiCliente;
    private Departamento departamentoOrigen;
    private Departamento departamentoDestino;
    
    
    //CONSTRUCTOR PARA INSTANCIAS
    public ViajePrivadoDTO(String origen, String destino, int numeroPasajeros, 
            LocalDate fechasalida, LocalDate fechaRetorno, double PrecioEstimado, String dpiCliente, Departamento departamentoOrigen,
            Departamento departamentoDestino, int idSucursal) {
        
        super(EstadoViaje.PENDIENTE, idSucursal, TipoViaje.PRIVADO);

        this.origen=origen;
        this.destino=destino;
        this.numeroPasajeros=numeroPasajeros;
        this.fechaSalida=fechasalida;
        this.fechaRetorno=fechaRetorno;
        this.precioEstimado=PrecioEstimado;
        this.departamentoOrigen=departamentoOrigen;
        this.departamentoDestino=departamentoDestino;
    }

    //PARA LECTURAS-lectura completa del viaje
    public ViajePrivadoDTO(String dpiCliente, String origen, String destino, int numeroPasajeros, LocalDate fechasalida, 
            LocalDate fechaRetorno, LocalDate fechaPago, double PrecioEstimado, double precioFinal, int idViaje, 
            EstadoViaje estadoViaje, int kiloetrajeActual, LocalTime horaRealSalida, LocalTime horaRealLlegada, 
            int kilometrajeFinal, double gastoTotalCombustible, String placaBusAsignado, String dpiChofer, Departamento departamentoOrigen, 
            Departamento departamentoDestino, int idSucursal) {
        
        super(idViaje, estadoViaje, kiloetrajeActual, horaRealSalida, horaRealLlegada, kilometrajeFinal, 
                gastoTotalCombustible, placaBusAsignado, dpiChofer, idSucursal, TipoViaje.PRIVADO);
        
        this.departamentoOrigen=departamentoOrigen;
        this.departamentoDestino=departamentoDestino;
        this.dpiCliente=dpiCliente;
        this.origen = origen;
        this.destino = destino;
        this.numeroPasajeros = numeroPasajeros;
        this.fechaSalida = fechasalida;
        this.fechaRetorno = fechaRetorno;
        this.fechaPago = fechaPago;
        this.precioEstimado = PrecioEstimado;
        this.precioFinal = precioFinal;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public int getNumeroPasajeros() {
        return numeroPasajeros;
    }

    public void setNumeroPasajeros(int numeroPasajeros) {
        this.numeroPasajeros = numeroPasajeros;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public LocalDate getFechaRetorno() {
        return fechaRetorno;
    }

    public void setFechaRetorno(LocalDate fechaRetorno) {
        this.fechaRetorno = fechaRetorno;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }

    public double getPrecioEstimado() {
        return precioEstimado;
    }

    public void setPrecioEstimado(double precioEstimado) {
        this.precioEstimado = precioEstimado;
    }

    public Double getPrecioFinal() {
        return precioFinal;
    }

    public void setPrecioFinal(Double precioFinal) {
        this.precioFinal = precioFinal;
    }

    public String getDpiCliente() {
        return dpiCliente;
    }

    public void setDpiCliente(String dpiCliente) {
        this.dpiCliente = dpiCliente;
    }

    public Departamento getDepartamentoOrigen() {
        return departamentoOrigen;
    }

    public void setDepartamentoOrigen(Departamento departamentoOrigen) {
        this.departamentoOrigen = departamentoOrigen;
    }

    public Departamento getDepartamentoDestino() {
        return departamentoDestino;
    }

    public void setDepartamentoDestino(Departamento departamentoDestino) {
        this.departamentoDestino = departamentoDestino;
    }

    
    
}
