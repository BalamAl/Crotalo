/*
 * Telcel - Subdirección Sistemas de Gestión y Tecnologías de Información
Radiomovil DIPSA S.A. de C.V. Todos los Derechos Reservados © 2017.
 */
package com.telcel.crotalo;

/**
 *
 * @author iHector
 */
public class CrotaloEntityDAO {
    
    public String ID_ENTRY;
    public String REGION_CR;
    public String TIPO_CR;
    public String RESUMEN_CR;
    public String NOTAS_CR;
    public String ID_CR;
    public String NEMONICO;
    public String FECHA_IN_CR;
    public String FECHA_FIN_CR;
    public String GRUPO_CROTALO;

    public CrotaloEntityDAO(String ID_ENTRY, String REGION_CR, String TIPO_CR, 
                            String RESUMEN_CR, String NOTAS_CR, String ID_CR, 
                            String FECHA_IN_CR, String FECHA_FIN_CR, String GRUPO_CROTALO) {
        this.ID_ENTRY = ID_ENTRY;
        this.REGION_CR = REGION_CR;
        this.TIPO_CR = TIPO_CR;
        this.RESUMEN_CR = RESUMEN_CR;
        this.NOTAS_CR = NOTAS_CR;
        this.ID_CR = ID_CR;
        this.FECHA_IN_CR = FECHA_IN_CR;
        this.FECHA_FIN_CR = FECHA_FIN_CR;
        this.GRUPO_CROTALO = GRUPO_CROTALO;
    }

    public String getID_ENTRY() {
        return ID_ENTRY;
    }

    public void setID_ENTRY(String ID_ENTRY) {
        this.ID_ENTRY = ID_ENTRY;
    }

    public String getREGION_CR() {
        return REGION_CR;
    }

    public void setREGION_CR(String REGION_CR) {
        this.REGION_CR = REGION_CR;
    }

    public String getTIPO_CR() {
        return TIPO_CR;
    }

    public void setTIPO_CR(String TIPO_CR) {
        this.TIPO_CR = TIPO_CR;
    }

    public String getRESUMEN_CR() {
        return RESUMEN_CR;
    }

    public void setRESUMEN_CR(String RESUMEN_CR) {
        this.RESUMEN_CR = RESUMEN_CR;
    }

    public String getNOTAS_CR() {
        return NOTAS_CR;
    }

    public void setNOTAS_CR(String NOTAS_CR) {
        this.NOTAS_CR = NOTAS_CR;
    }

    public String getID_CR() {
        return ID_CR;
    }

    public void setID_CR(String ID_CR) {
        this.ID_CR = ID_CR;
    }

    public String getNEMONICO() {
        return NEMONICO;
    }

    public void setNEMONICO(String NEMONICO) {
        this.NEMONICO = NEMONICO;
    }

    public String getFECHA_IN_CR() {
        return FECHA_IN_CR;
    }

    public void setFECHA_IN_CR(String FECHA_IN_CR) {
        this.FECHA_IN_CR = FECHA_IN_CR;
    }

    public String getFECHA_FIN_CR() {
        return FECHA_FIN_CR;
    }

    public void setFECHA_FIN_CR(String FECHA_FIN_CR) {
        this.FECHA_FIN_CR = FECHA_FIN_CR;
    }

    public String getGRUPO_CROTALO() {
        return GRUPO_CROTALO;
    }

    public void setGRUPO_CROTALO(String GRUPO_CROTALO) {
        this.GRUPO_CROTALO = GRUPO_CROTALO;
    }
    
    
}
