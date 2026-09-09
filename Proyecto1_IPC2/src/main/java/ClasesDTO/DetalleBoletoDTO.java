/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClasesDTO;

/**
 *
 * @author dar333n
 */
public class DetalleBoletoDTO {
    private int idBoleto;
    private String idAsiento;

    public DetalleBoletoDTO(int idBoleto, String idAsiento) {
        this.idBoleto = idBoleto;
        this.idAsiento = idAsiento;
    }

    public int getIdBoleto() {
        return idBoleto;
    }

    public void setIdBoleto(int idBoleto) {
        this.idBoleto = idBoleto;
    }

    public String getIdAsiento() {
        return idAsiento;
    }

    public void setIdAsiento(String idAsiento) {
        this.idAsiento = idAsiento;
    }
    
    
}
