/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package clasesAuxiliares;

/**
 *
 * @author dar333n
 */
public enum Departamento {
    ALTA_VERAPAZ,//ya
    BAJA_VERAPAZ,//ya
    CHIMALTENANGO,//ya
    CHIQUIMULA,//ya
    EL_PROGRESO,//ya
    ESCUINTLA,//ya
    GUATEMALA,//ya
    HUEHUETENANGO,
    IZABAL,
    JALAPA,
    JUTIAPA,
    PETEN,
    QUETZALTENANGO,//ya
    QUICHE,
    RETALHULEU,
    SACATEPEQUEZ,
    SAN_MARCOS,
    SANTA_ROSA,
    SOLOLA,
    SUCHITEPEQUEZ,
    TOTONICAPAN,
    ZACAPA;
    
    /*
    case ALTA_VERAPAZ: return ;
    case BAJA_VERAPAZ: return ;
    case CHIMALTENANGO: return ;
    case CHIQUIMULA: return ;
    case EL_PROGRESO: return ;
    case ESCUINTLA: return ;
    case GUATEMALA: return ;
    case HUEHUETENANGO: return ;
    case IZABAL: return ;
    case JALAPA: return ;
    case JUTIAPA: return ;
    case PETEN: return ;
    case QUETZALTENANGO: return ;
    case QUICHE: return ;
    case RETALHULEU: return ;
    case SACATEPEQUEZ: return ;
    case SAN_MARCOS: return ;
    case SANTA_ROSA: return ;
    case SOLOLA: return ;
    case SUCHITEPEQUEZ: return ;
    case TOTONICAPAN: return ;
    case ZACAPA: return ;
    */
    
    public static int distanciaEntreDepartamentos(Departamento departamentoOrigen, Departamento departamentoDestino){
        
        if (departamentoOrigen==departamentoDestino) {
            return 0;
        }
        
        switch (departamentoOrigen) {
            case GUATEMALA:
                switch (departamentoDestino) {
                    case ALTA_VERAPAZ: return 214;
                    case BAJA_VERAPAZ: return 276;
                    case CHIMALTENANGO: return 149;
                    case CHIQUIMULA: return 370;
                    case EL_PROGRESO: return 293;
                    case ESCUINTLA: return 63;
                    case HUEHUETENANGO: return 215;
                    case IZABAL: return 308;
                    case JALAPA: return 96;
                    case JUTIAPA: return 122;
                    case PETEN: return 500;
                    case QUETZALTENANGO: return 227;
                    case QUICHE: return 144;
                    case RETALHULEU: return 190;
                    case SACATEPEQUEZ: return 40;
                    case SAN_MARCOS: return 252;
                    case SANTA_ROSA: return 83;
                    case SOLOLA: return 118;
                    case SUCHITEPEQUEZ: return 165;
                    case TOTONICAPAN: return 156;
                    case ZACAPA: return 148;
                    default: return 120;
                }
                
                default:
                return 100;
                
             /*
            case QUETZALTENANGO:
                switch (departamentoDestino) {
                    case ALTA_VERAPAZ: return ;
                    case BAJA_VERAPAZ: return ;
                    case CHIMALTENANGO: return ;
                    case CHIQUIMULA: return ;
                    case EL_PROGRESO: return ;
                    case ESCUINTLA: return ;
                    case GUATEMALA: return ;
                    case HUEHUETENANGO: return ;
                    case IZABAL: return ;
                    case JALAPA: return ;
                    case JUTIAPA: return ;
                    case PETEN: return ;
                    case QUICHE: return ;
                    case RETALHULEU: return ;
                    case SACATEPEQUEZ: return ;
                    case SAN_MARCOS: return ;
                    case SANTA_ROSA: return ;
                    case SOLOLA: return ;
                    case SUCHITEPEQUEZ: return ;
                    case TOTONICAPAN: return ;
                    case ZACAPA: return ;
                }
           
            case ALTA_VERAPAZ:
                switch (departamentoDestino) {
                    case ALTA_VERAPAZ: return ;
                    case BAJA_VERAPAZ: return ;
                    case CHIMALTENANGO: return ;
                    case CHIQUIMULA: return ;
                    case EL_PROGRESO: return ;
                    case ESCUINTLA: return ;
                    case GUATEMALA: return ;
                    case HUEHUETENANGO: return ;
                    case IZABAL: return ;
                    case JALAPA: return ;
                    case JUTIAPA: return ;
                    case PETEN: return ;
                    case QUETZALTENANGO: return ;
                    case QUICHE: return ;
                    case RETALHULEU: return ;
                    case SACATEPEQUEZ: return ;
                    case SAN_MARCOS: return ;
                    case SANTA_ROSA: return ;
                    case SOLOLA: return ;
                    case SUCHITEPEQUEZ: return ;
                    case TOTONICAPAN: return ;
                    case ZACAPA: return ;
                }
            case BAJA_VERAPAZ:
                switch(departamentoDestino) {
                    case ALTA_VERAPAZ: return ;
                    case CHIMALTENANGO: return ;
                    case CHIQUIMULA: return ;
                    case EL_PROGRESO: return ;
                    case ESCUINTLA: return ;
                    case GUATEMALA: return ;
                    case HUEHUETENANGO: return ;
                    case IZABAL: return ;
                    case JALAPA: return ;
                    case JUTIAPA: return ;
                    case PETEN: return ;
                    case QUETZALTENANGO: return ;
                    case QUICHE: return ;
                    case RETALHULEU: return ;
                    case SACATEPEQUEZ: return ;
                    case SAN_MARCOS: return ;
                    case SANTA_ROSA: return ;
                    case SOLOLA: return ;
                    case SUCHITEPEQUEZ: return ;
                    case TOTONICAPAN: return ;
                    case ZACAPA: return ;
                }
                    
            case CHIMALTENANGO:
                switch(departamentoDestino) {
                    case ALTA_VERAPAZ: return ;
                    case BAJA_VERAPAZ: return ;
                    case CHIQUIMULA: return ;
                    case EL_PROGRESO: return ;
                    case ESCUINTLA: return ;
                    case GUATEMALA: return ;
                    case HUEHUETENANGO: return ;
                    case IZABAL: return ;
                    case JALAPA: return ;
                    case JUTIAPA: return ;
                    case PETEN: return ;
                    case QUETZALTENANGO: return ;
                    case QUICHE: return ;
                    case RETALHULEU: return ;
                    case SACATEPEQUEZ: return ;
                    case SAN_MARCOS: return ;
                    case SANTA_ROSA: return ;
                    case SOLOLA: return ;
                    case SUCHITEPEQUEZ: return ;
                    case TOTONICAPAN: return ;
                    case ZACAPA: return ;
                }
                
            case CHIQUIMULA:
                switch(departamentoDestino) {
                    case ALTA_VERAPAZ: return ;
                    case BAJA_VERAPAZ: return ;
                    case CHIMALTENANGO: return ;
                    case EL_PROGRESO: return ;
                    case ESCUINTLA: return ;
                    case GUATEMALA: return ;
                    case HUEHUETENANGO: return ;
                    case IZABAL: return ;
                    case JALAPA: return ;
                    case JUTIAPA: return ;
                    case PETEN: return ;
                    case QUETZALTENANGO: return ;
                    case QUICHE: return ;
                    case RETALHULEU: return ;
                    case SACATEPEQUEZ: return ;
                    case SAN_MARCOS: return ;
                    case SANTA_ROSA: return ;
                    case SOLOLA: return ;
                    case SUCHITEPEQUEZ: return ;
                    case TOTONICAPAN: return ;
                    case ZACAPA: return ;
                }
            
            case EL_PROGRESO:
                switch(departamentoDestino) {
                    case ALTA_VERAPAZ: return ;
                    case BAJA_VERAPAZ: return ;
                    case CHIMALTENANGO: return ;
                    case CHIQUIMULA: return ;
                    case ESCUINTLA: return ;
                    case GUATEMALA: return ;
                    case HUEHUETENANGO: return ;
                    case IZABAL: return ;
                    case JALAPA: return ;
                    case JUTIAPA: return ;
                    case PETEN: return ;
                    case QUETZALTENANGO: return ;
                    case QUICHE: return ;
                    case RETALHULEU: return ;
                    case SACATEPEQUEZ: return ;
                    case SAN_MARCOS: return ;
                    case SANTA_ROSA: return ;
                    case SOLOLA: return ;
                    case SUCHITEPEQUEZ: return ;
                    case TOTONICAPAN: return ;
                    case ZACAPA: return ;
                }
                
            case ESCUINTLA:
                switch(departamentoDestino) {
                    case ALTA_VERAPAZ: return ;
                    case BAJA_VERAPAZ: return ;
                    case CHIMALTENANGO: return ;
                    case CHIQUIMULA: return ;
                    case EL_PROGRESO: return ;
                    case ESCUINTLA: return ;
                    case GUATEMALA: return ;
                    case HUEHUETENANGO: return ;
                    case IZABAL: return ;
                    case JALAPA: return ;
                    case JUTIAPA: return ;
                    case PETEN: return ;
                    case QUETZALTENANGO: return ;
                    case QUICHE: return ;
                    case RETALHULEU: return ;
                    case SACATEPEQUEZ: return ;
                    case SAN_MARCOS: return ;
                    case SANTA_ROSA: return ;
                    case SOLOLA: return ;
                    case SUCHITEPEQUEZ: return ;
                    case TOTONICAPAN: return ;
                    case ZACAPA: return ;
                }
            */
                

        }
    }
}
