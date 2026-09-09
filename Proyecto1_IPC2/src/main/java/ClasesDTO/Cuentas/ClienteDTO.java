/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClasesDTO.Cuentas;

import ClasesDTO.Cuentas.CuentaDTO;
import clasesAuxiliares.TipoUsuario;

/**
 *
 * @author dar333n
 */
public class ClienteDTO extends CuentaDTO{

    //CONSTRUCTOR PARA INSTANCIAS
    public ClienteDTO(String contrasena, String correoElectronico, String dpi, String nombre, String nit, String direccion, String telefono) {
        super(contrasena, correoElectronico, dpi, nombre, nit, direccion, telefono, TipoUsuario.CLIENTE);
    }

     //PARA LECTURAS
    public ClienteDTO(String contrasena, String correoElectronico, String dpi, String nombre, String nit, String direccion, String telefono, double saldo, boolean activo) {
        super(contrasena, correoElectronico, dpi, nombre, nit, direccion, telefono, saldo, activo, TipoUsuario.CLIENTE);
    }
    
    
    
}
