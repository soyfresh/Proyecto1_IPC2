/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClasesDTO;

/**
 *
 * @author dar333n
 */
public class RutaDTO {
    private int idRuta;
    private double distanciaApoximada;
    private double precioBoleto;
    private int idSucursalOrigen;
    private int idSucursalDestino;
    private boolean activo;
    private String descripcionRuta;

    //CONSTRUCTOR PARA INSTANCIAS
    public RutaDTO(double distanciaApoximada, double precioBoleto, int idSucursalOrigen, int idSucursalDestino, String descripcionRuta) {
        this.distanciaApoximada = distanciaApoximada;
        this.precioBoleto = precioBoleto;
        this.idSucursalOrigen = idSucursalOrigen;
        this.idSucursalDestino = idSucursalDestino;
        this.descripcionRuta=descripcionRuta;
    }
    
    //PARA LECTURAS
    public RutaDTO(int idRuta, double distanciaApoximada, double precioBoleto, int idSucursalOrigen, 
            int idSucursalDestino, boolean activo, String descripcionRuta) {
        
        this.idRuta = idRuta;
        this.distanciaApoximada = distanciaApoximada;
        this.precioBoleto = precioBoleto;
        this.idSucursalOrigen = idSucursalOrigen;
        this.idSucursalDestino = idSucursalDestino;
        this.activo = activo;
        this.descripcionRuta=descripcionRuta;
    }

    public int getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(int idRuta) {
        this.idRuta = idRuta;
    }

    public double getDistanciaApoximada() {
        return distanciaApoximada;
    }

    public void setDistanciaApoximada(double distanciaApoximada) {
        this.distanciaApoximada = distanciaApoximada;
    }

    public double getPrecioBoleto() {
        return precioBoleto;
    }

    public void setPrecioBoleto(double precioBoleto) {
        this.precioBoleto = precioBoleto;
    }

    public int getIdSucursalOrigen() {
        return idSucursalOrigen;
    }

    public void setIdSucursalOrigen(int idSucursalOrigen) {
        this.idSucursalOrigen = idSucursalOrigen;
    }

    public int getIdSucursalDestino() {
        return idSucursalDestino;
    }

    public void setIdSucursalDestino(int idSucursalDestino) {
        this.idSucursalDestino = idSucursalDestino;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getDescripcionRuta() {
        return descripcionRuta;
    }

    public void setDescripcionRuta(String descripcionRuta) {
        this.descripcionRuta = descripcionRuta;
    }
    
    
}
