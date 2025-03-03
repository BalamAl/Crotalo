/*
 * Telcel - Subdirección Sistemas de Gestión y Tecnologías de Información
Radiomovil DIPSA S.A. de C.V. Todos los Derechos Reservados © 2017.
 */
package com.telcel.crotalo;

public class CrotaloEntityTASK {


    public String resumenTASK;
    public String notasTASK;
    public String regionTASK;
    public String idCRQTASK;
    public String idCRQ_Relation_TASK;
    public String CI_TASK;
    public String dateStartTASK;
    public String dateEndTASK;
    public String typeCRQ;

    public CrotaloEntityTASK() {
    }

    public CrotaloEntityTASK(String resumenTASK, String notasTASK, String regionTASK, String idCRQTASK, 
            String idCRQ_Relation_TASK,  String dateStartTASK, String dateEndTASK) {
        this.resumenTASK = " '8'='" + resumenTASK;
        this.notasTASK = "'10000101'='" + notasTASK;
        this.regionTASK = "'200000012'='" + regionTASK;
        this.idCRQTASK = "'10000001'='" + idCRQTASK;
        this.idCRQ_Relation_TASK = "'10000000'='" + idCRQ_Relation_TASK;
//        this.idCRQ_Relation_TASK = "'10000006'='" + idCRQ_Relation_TASK;  ////////
        //this.CI_TASK = "'536900039'='" + CI_TASK;
        this.dateStartTASK = "'1000000350'='" + dateStartTASK;
        this.dateEndTASK = "'1000000362'='" + dateEndTASK;
    }

    public CrotaloEntityTASK(String resumenTASK, String notasTASK, String regionTASK, String idCRQTASK, 
            String idCRQ_Relation_TASK, String dateStartTASK, String dateEndTASK, String typeCRQ) {
        this.resumenTASK = " '8'='" + resumenTASK + "' ";
        this.notasTASK = "'10000101'='" + notasTASK + "' ";
        this.regionTASK = "'200000012'='" + regionTASK + "' ";
        this.idCRQTASK = "'10000001'='" + idCRQTASK + "' ";
        this.idCRQ_Relation_TASK = "'10000000'='" + idCRQ_Relation_TASK + "' ";
        //this.idCRQ_Relation_TASK = "'10000006'='" + idCRQ_Relation_TASK + "' ";
        this.dateStartTASK = "'1000000350'='" + dateStartTASK + "' ";
        this.dateEndTASK = "'1000000362'='" + dateEndTASK + "' ";
        this.typeCRQ = typeCRQ;
    }

    public String getResumenTASK() {
        return resumenTASK;
    }

    public void setResumenTASK(String resumenTASK) {
        this.resumenTASK = resumenTASK;
    }

    public String getNotasTASK() {
        return notasTASK;
    }

    public void setNotasTASK(String notasTASK) {
        this.notasTASK = notasTASK;
    }

    public String getRegionTASK() {
        return regionTASK;
    }

    public void setRegionTASK(String regionTASK) {
        this.regionTASK = regionTASK;
    }

    public String getIdCRQTASK() {
        return idCRQTASK;
    }

    public void setIdCRQTASK(String idCRQTASK) {
        this.idCRQTASK = idCRQTASK;
    }

    public String getIdCRQ_Relation_TASK() {
        return idCRQ_Relation_TASK;
    }

    public void setIdCRQ_Relation_TASK(String idCRQ_Relation_TASK) {
        this.idCRQ_Relation_TASK = idCRQ_Relation_TASK;
    }

    public String getCI_TASK() {
        return CI_TASK;
    }

    public void setCI_TASK(String CI_TASK) {
        this.CI_TASK = CI_TASK;
    }

    public String getDateStartTASK() {
        return dateStartTASK;
    }

    public void setDateStartTASK(String dateStartTASK) {
        this.dateStartTASK = dateStartTASK;
    }

    public String getDateEndTASK() {
        return dateEndTASK;
    }

    public void setDateEndTASK(String dateEndTASK) {
        this.dateEndTASK = dateEndTASK;
    }

    public String getTypeCRQ() {
        return typeCRQ;
    }

    public void setTypeCRQ(String typeCRQ) {
        this.typeCRQ = typeCRQ;
    }
}
