/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClasesDTO;

import clasesAuxiliares.EstadoBus;

/**
 *
 * @author dar333n
 */
public class BusDTO {
    private String numeroPlaca;
    private String modelo;
    private String marca;
    private int anoFabricacion;
    private int capacidad;
    private double kilometrajeActual;
    private byte[] foto;
    private EstadoBus estadoBus;
    private boolean activo; 

    //PARA INSTANCIAS
    public BusDTO(String numeroPlaca, String modelo, String marca, int anoFabricacion, int capacidad, double kilometrajeActual, byte[] foto) {
        this.numeroPlaca = numeroPlaca;
        this.modelo = modelo;
        this.marca = marca;
        this.anoFabricacion = anoFabricacion;
        this.capacidad = capacidad;
        this.kilometrajeActual = kilometrajeActual;
        this.foto = foto;
    }
    
    //PARA LECTURAS
    public BusDTO(String numeroPlaca, String modelo, String marca, int anoFabricacion, int capacidad, double kilometrajeActual, byte[] foto, EstadoBus estadoBus, boolean activo) {
        this.numeroPlaca = numeroPlaca;
        this.modelo = modelo;
        this.marca = marca;
        this.anoFabricacion = anoFabricacion;
        this.capacidad = capacidad;
        this.kilometrajeActual = kilometrajeActual;
        this.foto = foto;
        this.estadoBus = estadoBus;
        this.activo = activo;
    }

    public String getNumeroPlaca() {
        return numeroPlaca;
    }

    public void setNumeroPlaca(String numeroPlaca) {
        this.numeroPlaca = numeroPlaca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getAnoFabricacion() {
        return anoFabricacion;
    }

    public void setAnoFabricacion(int añoFabricacion) {
        this.anoFabricacion = añoFabricacion;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public double getKilometrajeActual() {
        return kilometrajeActual;
    }

    public void setKilometrajeActual(double kilometrajeActual) {
        this.kilometrajeActual = kilometrajeActual;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public EstadoBus getEstadoBus() {
        return estadoBus;
    }

    public void setEstadoBus(EstadoBus estadoBus) {
        this.estadoBus = estadoBus;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    
}
