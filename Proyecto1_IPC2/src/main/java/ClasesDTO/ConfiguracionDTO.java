/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClasesDTO;

/**
 *
 * @author dar333n
 */
public class ConfiguracionDTO {
    private int idConfiguracion;
    private int montoPorKm;

    //PARA INSTANCIAS
    public ConfiguracionDTO(int montoPorKm) {
        this.montoPorKm = montoPorKm;
    }

    //PARA LECTURAS
    public ConfiguracionDTO(int idConfiguracion, int montoPorKm) {
        this.idConfiguracion = idConfiguracion;
        this.montoPorKm = montoPorKm;
    }

    public int getIdConfiguracion() {
        return idConfiguracion;
    }

    public void setIdConfiguracion(int idConfiguracion) {
        this.idConfiguracion = idConfiguracion;
    }

    public int getMontoPorKm() {
        return montoPorKm;
    }

    public void setMontoPorKm(int montoPorKm) {
        this.montoPorKm = montoPorKm;
    }
}
