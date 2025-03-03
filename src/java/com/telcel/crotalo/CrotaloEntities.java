/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telcel.crotalo;

import com.infomedia.utils.PropertyLoader;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 *
 * @author iHector
 */
public class CrotaloEntities {

    public final Logger log = Logger.getLogger(CrotaloEntities.class);
    public final Properties prop = PropertyLoader.load("crotalo2.properties");

    public final String USUARIO_WS = prop.getProperty("CLIENTE_ARS");

    public final String FORM_AMX_SIT = prop.getProperty("FORM_AMS");
//    public final String formCRQ = "&cForma=CHG:Infrastructure Change";//prop.getProperty("FORM_CHC");
    public final String formCRQ = "formulario/CHG:Infrastructure Change/";
    public final String FORM_TAS = prop.getProperty("FORM_TAS");
    public final String FORM_MAS = prop.getProperty("FORM_MAS");
    public final String FORM_ICH = prop.getProperty("FORM_ICH");
    public final String FORM_GRP = prop.getProperty("FORM_GRP");

    public final String CON_CHC1 = prop.getProperty("CON_CHC1");
    public final String CON_CHC2 = prop.getProperty("CON_CHC2");
    public final String CON_CHC3 = prop.getProperty("CON_CHC3");
    public final String CON_AMS1 = prop.getProperty("CON_AMS1");
    public final String CON_AMS2 = prop.getProperty("CON_AMS2");
    public final String CON_AMS3 = prop.getProperty("CON_AMS3");
    public final String CON_MAS  = prop.getProperty("CON_MAS");
    public final String CON_MAS2 = prop.getProperty("CON_MAS2");
    public final String CON_MAS3 = prop.getProperty("CON_MAS3");
    public final String CON_SQL  = prop.getProperty("CON_SQL");
    public final String CON_GRP  = prop.getProperty("CON_GRP");

    public final String DAT_CHC1 = prop.getProperty("DAT_CHC1");
    public final String DAT_CHC2 = prop.getProperty("DAT_CHC2");
    public final String DAT_CHC3 = prop.getProperty("DAT_CHC3");
    public final String DAT_AMS1 = prop.getProperty("DAT_AMS1");
    public final String DAT_AMS2 = prop.getProperty("DAT_AMS2");
    public final String DAT_TAS  = prop.getProperty("DAT_TAS");
    public final String DAT_MAS  = prop.getProperty("DAT_MAS");
    public final String DAT_ICH  = prop.getProperty("DAT_ICH");
    public final String DAT_MAS2 = prop.getProperty("DAT_MAS2");
    public final String DAT_GRP  = prop.getProperty("DAT_GRP");

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

    public String TIPO_REGIONAL;

    public List<String> ID_CR_LIST;
    public List<String> ID_CR_ENTITIE;
    public List<String> ID_NEMONICO;
    public List<String> ID_REGIONES_MASIVOS;
    public List<String> ID_NEMONICO_AMX;

    public String getUSUARIO_WS() {
        return USUARIO_WS;
    }

    public String getFORM_AMX_SIT() {
        return FORM_AMX_SIT;
    }

    public String getID_CR() {
        return ID_CR;
    }

    public void setID_CR(String ID_CR) {
        this.ID_CR = ID_CR;
    }

    public List<String> getID_NEMONICO() {
        return ID_NEMONICO;
    }

    public void setID_NEMONICO(List<String> ID_NEMONICO) {
        this.ID_NEMONICO = ID_NEMONICO;
    }

    public String getTIPO_CR() {
        return TIPO_CR;
    }

    public void setTIPO_CR(String TIPO_CR) {
        this.TIPO_CR = TIPO_CR;
    }

    public String getCON_CHC1() {
        return CON_CHC1;
    }

    public String getDAT_CHC1() {
        return DAT_CHC1;
    }

    public String getFormCRQ() {
        return formCRQ;
    }

    public String getCON_AMS1() {
        return CON_AMS1;
    }

    public String getDAT_AMS1() {
        return DAT_AMS1;
    }

    public String getNEMONICO() {
        return NEMONICO;
    }

    public void setNEMONICO(String NEMONICO) {
        this.NEMONICO = NEMONICO;
    }

    public List<String> getID_CR_ENTITIE() {
        return ID_CR_ENTITIE;
    }

    public void setID_CR_ENTITIE(List<String> ID_CR_ENTITIE) {
        this.ID_CR_ENTITIE = ID_CR_ENTITIE;
    }

    public List<String> getID_CR_LIST() {
        return ID_CR_LIST;
    }

    public void setID_CR_LIST(List<String> ID_CR_LIST) {
        this.ID_CR_LIST = ID_CR_LIST;
    }

    public String getDAT_CHC2() {
        return DAT_CHC2;
    }

    public String getCON_CHC2() {
        return CON_CHC2;
    }

    public String getREGION_CR() {
        return REGION_CR;
    }

    public void setREGION_CR(String REGION_CR) {
        this.REGION_CR = REGION_CR;
    }

    public String getNOTAS_CR() {
        return NOTAS_CR;
    }

    public void setNOTAS_CR(String NOTAS_CR) {
        this.NOTAS_CR = NOTAS_CR;
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

    public String getRESUMEN_CR() {
        return RESUMEN_CR;
    }

    public void setRESUMEN_CR(String RESUMEN_CR) {
        this.RESUMEN_CR = RESUMEN_CR;
    }

    public List<String> getID_NEMONICO_AMX() {
        return ID_NEMONICO_AMX;
    }

    public void setID_NEMONICO_AMX(List<String> ID_NEMONICO_AMX) {
        this.ID_NEMONICO_AMX = ID_NEMONICO_AMX;
    }

    public String getFORM_TAS() {
        return FORM_TAS;
    }

    public String getDAT_TAS() {
        return DAT_TAS;
    }

    public String getID_ENTRY() {
        return ID_ENTRY;
    }

    public void setID_ENTRY(String ID_ENTRY) {
        this.ID_ENTRY = ID_ENTRY;
    }

    public String getDAT_CHC3() {
        return DAT_CHC3;
    }

    public String getTIPO_REGIONAL() {
        return TIPO_REGIONAL;
    }

    public void setTIPO_REGIONAL(String TIPO_REGIONAL) {
        this.TIPO_REGIONAL = TIPO_REGIONAL;
    }

    public String getCON_AMS2() {
        return CON_AMS2;
    }

    public String getDAT_AMS2() {
        return DAT_AMS2;
    }

    public String getFORM_MAS() {
        return FORM_MAS;
    }

    public String getCON_AMS3() {
        return CON_AMS3;
    }

    public String getCON_MAS() {
        return CON_MAS;
    }

    public String getDAT_MAS() {
        return DAT_MAS;
    }

    public List<String> getID_REGIONES_MASIVOS() {
        return ID_REGIONES_MASIVOS;
    }

    public void setID_REGIONES_MASIVOS(List<String> ID_REGIONES_MASIVOS) {
        this.ID_REGIONES_MASIVOS = ID_REGIONES_MASIVOS;
    }

    public String getCON_SQL() {
        return CON_SQL;
    }

    public String getCON_MAS2() {
        return CON_MAS2;
    }

    public String getDAT_MAS2() {
        return DAT_MAS2;
    }

    public String getCON_MAS3() {
        return CON_MAS3;
    }

    public String getFORM_ICH() {
        return FORM_ICH;
    }

    public String getCON_CHC3() {
        return CON_CHC3;
    }

    public String getDAT_ICH() {
        return DAT_ICH;
    }

    public String getGRUPO_CROTALO() {
        return GRUPO_CROTALO;
    }

    public void setGRUPO_CROTALO(String GRUPO_CROTALO) {
        this.GRUPO_CROTALO = GRUPO_CROTALO;
    }

    public String getFORM_GRP() {
        return FORM_GRP;
    }

    public String getCON_GRP() {
        return CON_GRP;
    }

    public String getDAT_GRP() {
        return DAT_GRP;
    }

}
