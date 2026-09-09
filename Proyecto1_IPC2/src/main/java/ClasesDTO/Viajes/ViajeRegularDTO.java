/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClasesDTO.Viajes;

import clasesAuxiliares.EstadoViaje;
import clasesAuxiliares.TipoViaje;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 *
 * @author dar333n
 */
public class ViajeRegularDTO extends ViajeDTO{

    private int idRuta;
    private LocalDateTime fechaHoraProgramadaSalida;
    private LocalDateTime fechaHoraEstimadaLlegada;
    
    //CONSTRUCTOR PARA INSTANCIAS
    public ViajeRegularDTO(String placaBusAsignado, String dpiChofer, int idRuta,
            LocalDateTime fechaHoraProgramadaSalida, LocalDateTime fechaHoraEstimadaLlegada, int idSucursal) {
        super(EstadoViaje.PROGRAMADO, idSucursal, TipoViaje.REGULAR);
        
        this.idRuta=idRuta;
        this.fechaHoraProgramadaSalida=fechaHoraProgramadaSalida;
        this.fechaHoraEstimadaLlegada=fechaHoraEstimadaLlegada;
    }

    //PARA LECTURAS-lectura completa del viaje
    public ViajeRegularDTO(int idViaje, EstadoViaje estadoViaje, int kiloetrajeActual, LocalTime horaRealSalida, 
            LocalTime horaRealLlegada, int kilometrajeFinal, double gastoTotalCombustible, String placaBusAsignado, 
            String dpiChofer, int idRuta, LocalDateTime fechaHoraProgramadaSalida, LocalDateTime fechaHoraEstimadaLlegada, int idSucursal) {
        super(idViaje, estadoViaje, kiloetrajeActual, horaRealSalida, horaRealLlegada, kilometrajeFinal, 
                gastoTotalCombustible, placaBusAsignado, dpiChofer, idSucursal, TipoViaje.REGULAR);
        
        this.idRuta=idRuta;
        this.fechaHoraProgramadaSalida=fechaHoraProgramadaSalida;
        this.fechaHoraEstimadaLlegada=fechaHoraEstimadaLlegada;
    }

    public int getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(int idRuta) {
        this.idRuta = idRuta;
    }

    public LocalDateTime getFechaHoraProgramadaSalida() {
        return fechaHoraProgramadaSalida;
    }

    public void setFechaHoraProgramadaSalida(LocalDateTime fechaHoraProgramadaSalida) {
        this.fechaHoraProgramadaSalida = fechaHoraProgramadaSalida;
    }

    public LocalDateTime getFechaHoraEstimadaLlegada() {
        return fechaHoraEstimadaLlegada;
    }

    public void setFechaHoraEstimadaLlegada(LocalDateTime fechaHoraEstimadaLlegada) {
        this.fechaHoraEstimadaLlegada = fechaHoraEstimadaLlegada;
    }
    
    
}
