/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClasesDTO.Viajes;

import clasesAuxiliares.EstadoViaje;
import clasesAuxiliares.TipoViaje;
import java.time.LocalTime;

/**
 *
 * @author dar333n
 */
public abstract class ViajeDTO {
    private int idViaje;
    private TipoViaje tipoViaje;
    private int idSucursal;
    private EstadoViaje estadoViaje;
    private int kilometrajeActual;
    private LocalTime horaRealSalida;
    private LocalTime horaRealLlegada;
    private int kilometrajeFinal;
    private double gastoTotalCombustible;
    private String placaBusAsignado;
    private String dpiChofer;

    //CONSTRUCTOR PARA INSTANCIA VIAJE REGULAR/PRIVADO
    public ViajeDTO(EstadoViaje estadoViaje, int idSucursal, TipoViaje tipoViaje) {
        this.estadoViaje = estadoViaje;
        this.idSucursal=idSucursal;
        this.tipoViaje=tipoViaje;
    }

    //PARA LECTURAS
    public ViajeDTO(int idViaje, EstadoViaje estadoViaje, int kiloetrajeActual, LocalTime horaRealSalida, 
            LocalTime horaRealLlegada, int kilometrajeFinal, double gastoTotalCombustible, String placaBusAsignado, 
            String dpiChofer,  int idSucursal, TipoViaje tipoViaje) {
        
        this.idViaje = idViaje;
        this.idSucursal=idSucursal;
        this.tipoViaje=tipoViaje;
        this.estadoViaje = estadoViaje;
        this.kilometrajeActual = kiloetrajeActual;
        this.horaRealSalida = horaRealSalida;
        this.horaRealLlegada = horaRealLlegada;
        this.kilometrajeFinal = kilometrajeFinal;
        this.gastoTotalCombustible = gastoTotalCombustible;
        this.placaBusAsignado = placaBusAsignado;
        this.dpiChofer = dpiChofer;
    }

    
    public int getIdViaje() {
        return idViaje;
    }

    public void setIdViaje(int idViaje) {
        this.idViaje = idViaje;
    }

    public EstadoViaje getEstadoViaje() {
        return estadoViaje;
    }

    public void setEstadoViaje(EstadoViaje estadoViaje) {
        this.estadoViaje = estadoViaje;
    }

    public LocalTime getHoraRealSalida() {
        return horaRealSalida;
    }

    public void setHoraRealSalida(LocalTime horaRealSalida) {
        this.horaRealSalida = horaRealSalida;
    }

    public LocalTime getHoraRealLlegada() {
        return horaRealLlegada;
    }

    public void setHoraRealLlegada(LocalTime horaRealLlegada) {
        this.horaRealLlegada = horaRealLlegada;
    }

    public double getGastoTotalCombustible() {
        return gastoTotalCombustible;
    }

    public void setGastoTotalCombustible(double gastoTotalCombustible) {
        this.gastoTotalCombustible = gastoTotalCombustible;
    }

    public String getPlacaBusAsignado() {
        return placaBusAsignado;
    }

    public void setPlacaBusAsignado(String placaBusAsignado) {
        this.placaBusAsignado = placaBusAsignado;
    }

    public String getDpiChofer() {
        return dpiChofer;
    }

    public void setDpiChofer(String dpiChofer) {
        this.dpiChofer = dpiChofer;
    }

    public TipoViaje getTipoViaje() {
        return tipoViaje;
    }

    public void setTipoViaje(TipoViaje tipoViaje) {
        this.tipoViaje = tipoViaje;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public int getKilometrajeActual() {
        return kilometrajeActual;
    }

    public void setKilometrajeActual(int kilometrajeActual) {
        this.kilometrajeActual = kilometrajeActual;
    }

    public int getKilometrajeFinal() {
        return kilometrajeFinal;
    }

    public void setKilometrajeFinal(int kilometrajeFinal) {
        this.kilometrajeFinal = kilometrajeFinal;
    }

    
    
 
}
