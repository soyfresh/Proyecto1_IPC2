/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClasesDTO;

import clasesAuxiliares.Departamento;

/**
 *
 * @author dar333n
 */
public class SucursalDTO {
    
    private int idSucursal;
    private String direccion;
    private String dpiAdministradorSc;
    private Departamento departamento;

    //CONSTRUCTOR PARA INSTANCIAS
    public SucursalDTO(String direccion, String dpiAdministradorSc, Departamento departamento) {
        this.direccion = direccion;
        this.dpiAdministradorSc = dpiAdministradorSc;
        this.departamento=departamento;
    }  
    
    //LECTURAS
    public SucursalDTO(int idScucursal, String direccion, String dpiAdministradorSc, Departamento departamento) {
        this.idSucursal = idScucursal;
        this.direccion = direccion;
        this.dpiAdministradorSc=dpiAdministradorSc;
        this.departamento=departamento;
    }

    public int getIdScucursal() {
        return idSucursal;
    }

    public void setIdScucursal(int idScucursal) {
        this.idSucursal = idScucursal;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDpiAdministradorSc() {
        return dpiAdministradorSc;
    }

    public void setDpiAdministradorSc(String dpiAdministradorSc) {
        this.dpiAdministradorSc = dpiAdministradorSc;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }
    
    
    
}
