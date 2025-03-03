/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telcel.crotalo;

import com.telcel.crotalo.utilities.WsRControl;
//import static com.telcel.crotalo.utilities.WsRControl.log;
import java.util.ArrayList;
import java.util.List;


public class CrotaloProcesos extends CrotaloEntities {

    public WsRControl WS_RC = new WsRControl();
    public StringBuilder querySelectCRQValues;
    public StringBuilder queryInsertTASKValues;
    public String genericCI;
    public String genericCI_Type;

    public CrotaloEntityDAO Crotalo_DAO;
    public CrotaloEntityTASK taskEntry;
    public List<CrotaloEntityDAO> ListCrotaloDAO;

    public List<String> ListCI_CRQ_Generic;
    public List<String> ListCI_CRQ_SINGLE;
    public List<String> ListCI_CRQ_REGIONAL;
    public List<String> ListCI_CRQ_MULTIPLE;
    public List<String> ListCI_CRQ_MULTIPLE_F;
    public List<String> ListCI_CRQ_NACIONAL;

    public CrotaloProcesos() {
        //consultaCRQ();
    }
      
    public List<String> getListCI_CRQ_SINGLE() {
        return ListCI_CRQ_SINGLE;
    }

    public void setListCI_CRQ_SINGLE(List<String> ListCI_CRQ_SINGLE) {
        this.ListCI_CRQ_SINGLE = ListCI_CRQ_SINGLE;
    }

    public List<String> getListCI_CRQ_REGIONAL() {
        return ListCI_CRQ_REGIONAL;
    }

    public void setListCI_CRQ_REGIONAL(List<String> ListCI_CRQ_REGIONAL) {
        this.ListCI_CRQ_REGIONAL = ListCI_CRQ_REGIONAL;
    }

    public String getGenericCI() {
        return genericCI;
    }

    public void setGenericCI(String genericCI) {
        this.genericCI = genericCI;
    }

    public String getGenericCI_Type() {
        return genericCI_Type;
    }

    public void setGenericCI_Type(String genericCI_Type) {
        this.genericCI_Type = genericCI_Type;
    }

    public List<String> getListCI_CRQ_Generic() {
        return ListCI_CRQ_Generic;
    }

    public void setListCI_CRQ_Generic(List<String> ListCI_CRQ_Generic) {
        this.ListCI_CRQ_Generic = ListCI_CRQ_Generic;
    }

    public List<String> getListCI_CRQ_MULTIPLE() {
        return ListCI_CRQ_MULTIPLE;
    }

    public void setListCI_CRQ_MULTIPLE(List<String> ListCI_CRQ_MULTIPLE) {
        this.ListCI_CRQ_MULTIPLE = ListCI_CRQ_MULTIPLE;
    }

    public List<String> getListCI_CRQ_MULTIPLE_F() {
        return ListCI_CRQ_MULTIPLE_F;
    }

    public void setListCI_CRQ_MULTIPLE_F(List<String> ListCI_CRQ_MULTIPLE_F) {
        this.ListCI_CRQ_MULTIPLE_F = ListCI_CRQ_MULTIPLE_F;
    }

    public List<String> getListCI_CRQ_NACIONAL() {
        return ListCI_CRQ_NACIONAL;
    }

    public void setListCI_CRQ_NACIONAL(List<String> ListCI_CRQ_NACIONAL) {
        this.ListCI_CRQ_NACIONAL = ListCI_CRQ_NACIONAL;
    }

    /**
     * toma los datos del CRQ para validarlos
     *
     * @param listCRQ
     */
    public void datosCRQ(List<String> listCRQ) {
    //    log.info("Entra a datosCRQ...");
    //    log.info("La lista de CRQ es: " +listCRQ);
        System.out.println("lista de CRQ: "+listCRQ);
        ListCrotaloDAO = new ArrayList<>();
        for (String indice : listCRQ) {
            System.out.println("\n***INDICE datosCRQ: "+ indice+"\n");
            if (!WS_RC.CRQ_VALUES(indice).isEmpty() && WS_RC.getListOfValues().size() == 9) {
                log.info("CRQ_VALUES TRUE..."); 
                
                Crotalo_DAO = new CrotaloEntityDAO(
                        WS_RC.getListOfValues().get(0),
                        WS_RC.getListOfValues().get(1),
                        WS_RC.getListOfValues().get(2),
                        WS_RC.getListOfValues().get(3).replace(" & ", " Y "), 
                        WS_RC.getListOfValues().get(4).replace(" & ", " Y "), 
                        WS_RC.getListOfValues().get(5),
                        WS_RC.getListOfValues().get(6),
                        WS_RC.getListOfValues().get(7), 
                        WS_RC.getListOfValues().get(8)
                );  //CRQ000002178567 - nacional
                    //CRQ000004536405 - nacional ejemplo 1
                    //CRQ000004536405 - nacional ejemplo 2
                ListCrotaloDAO.add(Crotalo_DAO);
//                System.out.println("***LISTA CROTALO DAO2***: " + ListCrotaloDAO);
            }else log.info("CRQ_VALUES FALSE...");
        }
        log.info("Lista de CRQ nueva: " + ListCrotaloDAO);
        CRQ_Process(ListCrotaloDAO);
    }

    /**
     * Valida el tipo de CRQ y toma los CI segun corresponda cad acaso
     *
     * @param EntityCRQ
     * @return
     */
    public String CRQ_Process(List<CrotaloEntityDAO> EntityCRQ) {
        log.info("Entra a CRQ_Process");
        for (CrotaloEntityDAO crqdao : EntityCRQ) {
            log.info("Procesando CRQ: " +crqdao.getID_CR()+ " ID: " +crqdao.getID_ENTRY());
            if (Crotalo_groupSupport(crqdao.getGRUPO_CROTALO()) 
                    && !CRQ_Successful(crqdao.getID_CR())) {
                log.info("CRQ: " +crqdao.getID_CR()+ " TIPO: " +crqdao.getTIPO_CR());
                
                
                if (crqdao.getTIPO_CR().equals("0") || crqdao.getTIPO_CR().equals("Individual")) {
                    log.info(crqdao.getID_CR() +". Lista de sitios: " +getCI_CRQ_Single(crqdao.getID_CR()));
                    if (!getCI_CRQ_Single(crqdao.getID_CR()).isEmpty()) {
                        log.info("Entro a Individual");
                        crqdao = parsearCRQDAO(crqdao);
                        taskEntry = new CrotaloEntityTASK(crqdao.getRESUMEN_CR(), crqdao.getNOTAS_CR(), crqdao.getREGION_CR(), crqdao.getID_CR(), crqdao.getID_CR(), crqdao.getFECHA_IN_CR(), crqdao.getFECHA_FIN_CR(), crqdao.getTIPO_CR());
                        createTask_CrotaloSingle(taskEntry, getListCI_CRQ_SINGLE());
                    }
                }
                else if (crqdao.getTIPO_CR().equals("1") || crqdao.getTIPO_CR().equals("Regional")) {
                    log.info(crqdao.getID_CR() +". Lista de sitios: " +getCI_CRQ_Reg(crqdao.getID_CR(), crqdao.getID_CR()).isEmpty());
                    if (!getCI_CRQ_Reg(crqdao.getID_ENTRY(), crqdao.getID_CR()).isEmpty()) {
                        log.info("Entro a Regional");
                        crqdao = parsearCRQDAO(crqdao);
                        taskEntry = new CrotaloEntityTASK(crqdao.getRESUMEN_CR(), crqdao.getNOTAS_CR(), crqdao.getREGION_CR(), crqdao.getID_CR(), crqdao.getID_CR(), crqdao.getFECHA_IN_CR(), crqdao.getFECHA_FIN_CR(), crqdao.getTIPO_CR());
                        createTask_CrotaloSingle(taskEntry, getListCI_CRQ_REGIONAL());
                    }
                }
                else if (crqdao.getTIPO_CR().equals("2") || crqdao.getTIPO_CR().equals("Multiple")) {
                    log.info(crqdao.getID_CR() +". Lista de sitios: " +getCI_CRQ_Multiple(crqdao.getID_CR()));
                    if (!getCI_CRQ_Multiple(crqdao.getID_CR()).isEmpty()) {
                        log.info("Entro a Multiple");
                        crqdao = parsearCRQDAO(crqdao);
                        taskEntry = new CrotaloEntityTASK(crqdao.getRESUMEN_CR(), crqdao.getNOTAS_CR(), crqdao.getREGION_CR(), crqdao.getID_CR(), crqdao.getID_CR(), crqdao.getFECHA_IN_CR(), crqdao.getFECHA_FIN_CR(), crqdao.getTIPO_CR());
                        createTask_CrotaloSingle(taskEntry, getListCI_CRQ_MULTIPLE_F());
                    }
                }
                else if (crqdao.getTIPO_CR().equals("3") || crqdao.getTIPO_CR().equals("Nacional")) {
                    log.info(crqdao.getID_CR() +". Lista de sitios: " +getCI_CRQ_Nacionales(crqdao.getID_CR()));
                    if (!getCI_CRQ_Nacionales(crqdao.getID_CR()).isEmpty()) {
                        log.info("Entro a Nacional");
                        crqdao = parsearCRQDAO(crqdao);
                        taskEntry = new CrotaloEntityTASK(crqdao.getRESUMEN_CR(), crqdao.getNOTAS_CR(), crqdao.getREGION_CR(), crqdao.getID_CR(), crqdao.getID_CR(), crqdao.getFECHA_IN_CR(), crqdao.getFECHA_FIN_CR(), crqdao.getTIPO_CR());
                        createTask_CrotaloSingle(taskEntry, getListCI_CRQ_NACIONAL());
                    }
                }else System.out.println("CRQ NO ES DE NINGUN TIPO...");
            } else {
                log.debug(crqdao.getID_CR() + " YA FUE PROCESADO");
            }
        }
        return TIPO_CR;
    }

    /**
     * Metodo para parsear los valores dentro de todos los campos recuperados del cambio
     * 
     * @param crqdao
     * @return 
     */
    public CrotaloEntityDAO parsearCRQDAO(CrotaloEntityDAO crqdao){
        CrotaloEntityDAO parsedCRQDAO = new CrotaloEntityDAO(crqdao.getID_ENTRY(), crqdao.getREGION_CR(), crqdao.getTIPO_CR(), "", "", crqdao.getID_CR(), crqdao.getFECHA_IN_CR(), crqdao.getFECHA_FIN_CR(), crqdao.getGRUPO_CROTALO());
        log.info("\nVALORES ANTES DEL PARSEO:\n"
                + "IDEntry: " +crqdao.getID_ENTRY()+ "\n"
                + "Region: " +crqdao.getREGION_CR()+ "\n" 
                + "Tipo Crotalo: " +crqdao.getTIPO_CR()+ "\n"
                + "Resumen: " +crqdao.getRESUMEN_CR()+ "\n"
                + "Notas: " +crqdao.getNOTAS_CR()+ "\n"
                + "IDCambio: " +crqdao.getID_CR()+ "\n"
                + "Fecha inicio: " +crqdao.getFECHA_IN_CR()+ "\n"
                + "Fecha fin: " +crqdao.getFECHA_FIN_CR()+ "\n"
                + "Grupo de soporte: " +crqdao.getGRUPO_CROTALO());
        //Inicia el parseo
        parsedCRQDAO.setNOTAS_CR(parsearValores(crqdao.getNOTAS_CR()));
        parsedCRQDAO.setRESUMEN_CR(parsearValores(crqdao.getRESUMEN_CR()));
        log.info("\nVALORES PARSEADOS:\n"
                + "Resumen: " +parsedCRQDAO.getRESUMEN_CR()+ "\n"
                + "Notas: " +parsedCRQDAO.getNOTAS_CR());
        return parsedCRQDAO;
    }
    
    /**
     * Metodo para parsear los valores dentro de todos los campos recuperados del cambio
     * $ & % #
     * %24 %26 %25 %23
     * 
     * @param valor
     * @return 
     */
    public String parsearValores(String valor){
        String parsedValor;
        parsedValor = valor.replaceAll("&#8220", "")    //////  
                            .replaceAll("&#8221", "")   //////  
                            .replaceAll(";", "")        //////
                            .replaceAll("/", "-")       //////
                            .replaceAll("&amp;", "%26")
                            .replaceAll("#", "%23");
        return parsedValor;
    }
    
    /**
     * Meotodo para generar las Tareas que provienen de CRQ CROTALO
     *
     * @param taskEntry
     * @param ListCI_CRQ
     */
    public void createTask_CrotaloSingle(CrotaloEntityTASK taskEntry, List<String> ListCI_CRQ) {
        log.info("Entro a createTask");
        String res = "";
        try {
            for (String ID_CI : ListCI_CRQ) {
                log.info("CRQ: " + taskEntry.getIdCRQTASK().substring(12, 27) + " TIPO: " + taskEntry.getTypeCRQ());
                queryInsertTASKValues = new StringBuilder();
                queryInsertTASKValues.append(prop.getProperty("FORM_TAS"));
                queryInsertTASKValues.append(prop.getProperty("DAT_TAS"));
                queryInsertTASKValues.append(taskEntry.getResumenTASK());
                queryInsertTASKValues.append(taskEntry.getNotasTASK());
                queryInsertTASKValues.append(taskEntry.getRegionTASK());
                queryInsertTASKValues.append(taskEntry.getIdCRQTASK());
                queryInsertTASKValues.append(taskEntry.getIdCRQ_Relation_TASK());
                queryInsertTASKValues.append(taskEntry.getDateStartTASK());
                queryInsertTASKValues.append(taskEntry.getDateEndTASK());
                queryInsertTASKValues.append("'536900039'='").append(ID_CI).append("'");
                log.info("PETICION CREAR TASK: " +queryInsertTASKValues);
                res = WS_RC.Insert_Ws(queryInsertTASKValues.toString());
            }
            log.info("CRQ: " +taskEntry.getIdCRQTASK()+ " " +res);
            insertaRegistroDB(taskEntry.getIdCRQTASK(), taskEntry.getTypeCRQ());
        } catch (Exception exc) {
            log.error("ERROR createTask_CrotaloSingle: "+exc);
//            exc.printStackTrace();
        }
    }

    /**
     * funcion que obtine los CI de los CRQ tipo individual
     *
     * @param ID_CRQ
     * @return
     */
    public List<String> getCI_CRQ_Single(String ID_CRQ) {
        ListCI_CRQ_SINGLE = new ArrayList<>();
        ListCI_CRQ_SINGLE = WS_RC.query_CRQ_Single(ID_CRQ);
        log.debug(ListCI_CRQ_SINGLE);
        return ListCI_CRQ_SINGLE;
    }

    /**
     * Funcion para procesar los CI de CRQ de tipo Regionales
     *
     * @param Entry_CRQ
     * @param ID_CRQ
     * @return
     */
    public List<String> getCI_CRQ_Reg(String Entry_CRQ, String ID_CRQ) {

        StringBuilder querySelect = new StringBuilder();
        querySelectCRQValues = new StringBuilder();
/*        querySelectCRQValues.append("&cForma=CHG:Infrastructure Change");
        querySelectCRQValues.append("&cColumnas=536870957");
        querySelectCRQValues.append("&cCondiciones='1'=");  */

        querySelectCRQValues.append("formulario/CHG:Infrastructure Change/");
        querySelectCRQValues.append("campos/536870957/");
        querySelectCRQValues.append("condiciones/'1'=");
        
        querySelectCRQValues.append("'").append(Entry_CRQ).append("'");

        ListCI_CRQ_Generic = WS_RC.query_CRQ_Single(ID_CRQ);

        if (!WS_RC.queryCRQRegional(querySelectCRQValues.toString()).isEmpty()) {
            for (String idCI : ListCI_CRQ_Generic) {
/*                querySelect.append("&cForma=AST:BaseElement");
                querySelect.append("&cColumnas=200000020");
                querySelect.append("&cCondiciones=");   */
                querySelect.append("formulario/AST:BaseElement/");
                querySelect.append("campos/200000020/");
                querySelect.append("condiciones/");
                
                querySelect.append("'7'='3' ");
                querySelect.append("'400127400'='BMC.ASSET' ");
                querySelect.append("'200000012'=");
                querySelect.append("'").append(WS_RC.getResultadoConsulta()).append("' ");
                querySelect.append("'8'=%25");      ////////////////
                querySelect.append("'").append(parseCI(idCI)).append("'");
                ListCI_CRQ_REGIONAL = WS_RC.consultaGCMDBList(USUARIO_WS, querySelect.toString());
            }
        }
        return ListCI_CRQ_REGIONAL;
    }//CRQ de tipo Regionales

    /**
     * Funicon para procesar los CI de CRQ de tipo Multiples
     *
     * @param ID_CRQ
     * @return
     */
    public List<String> getCI_CRQ_Multiple(String ID_CRQ) {
        
        ListCI_CRQ_MULTIPLE = new ArrayList<>();
        ListCI_CRQ_MULTIPLE_F = new ArrayList<>();

        ListCI_CRQ_Generic = WS_RC.query_CRQ_Single(ID_CRQ);    //Entra a buscar CI

        StringBuilder querySelect = new StringBuilder();
/*        querySelect.append("&cForma=AMX:CHG:InfrastructureChange:TipoCrotalo-Region");
        querySelect.append("&cColumnas=536870914");
        querySelect.append("&cCondiciones='536870915'=");*/

        querySelect.append("formulario/AMX:CHG:InfrastructureChange:TipoCrotalo-Region/");
        querySelect.append("campos/536870914/");
        querySelect.append("condiciones/'536870915'=");
        
        querySelect.append("'").append(ID_CRQ).append("'");
//querySelect=  formulario/AMX:CHG:InfrastructureChange:TipoCrotalo-Region/campos/536870914/condiciones/'536870915'=''

        String querySelectCI = "";
        if (!WS_RC.queryCRQMulti(querySelect.toString()).isEmpty()) {
            for (String ID_CI : ListCI_CRQ_Generic) {
                for (String ID_REGION : WS_RC.getListCRQMultiple()) {
                    querySelectCI += FORM_AMX_SIT;
                    querySelectCI += DAT_AMS2;
                    querySelectCI += CON_AMS2;
                    querySelectCI += "'" + ID_REGION + "' '8'=%25'" + parseCI(ID_CI) + "'";
                    
                    ListCI_CRQ_MULTIPLE = WS_RC.consultaGCMDBList(USUARIO_WS, querySelectCI);
                    querySelectCI = "";
//querySelectCI =   formulario/AST:BaseElement/campos/200000020/condiciones/'7'='3' '400127400'='BMC.ASSET' '200000012'='9' '8'='%25GSM HLR%25'
                    for (String Site : ListCI_CRQ_MULTIPLE) {
                        ListCI_CRQ_MULTIPLE_F.add(Site);
                    }
                }
            }
        }
        return ListCI_CRQ_MULTIPLE_F;
    }//CRQ de tipo Multiples

    /**
     * funcion para procesar los CI de CRQ tipo Nacionales
     *
     * @param ID_CRQ
     * @return
     */
    public List<String> getCI_CRQ_Nacionales(String ID_CRQ) {

        ListCI_CRQ_NACIONAL = new ArrayList<>();
        StringBuilder querySelect = new StringBuilder();
        ListCI_CRQ_Generic = WS_RC.query_CRQ_Single(ID_CRQ);    //("Entra a buscar CI");
        
        if (!getListCI_CRQ_Generic().isEmpty()) {
            for (String idCI : ListCI_CRQ_Generic) {
                System.out.println("\nCI NACIONAL: "+idCI);
/*                querySelect.append("&cForma=AST:BaseElement");
                querySelect.append("&cColumnas=200000020");
                querySelect.append("&cCondiciones=");       */
                
                querySelect.append("formulario/AST:BaseElement/");
                querySelect.append("campos/200000020/");
                querySelect.append("condiciones/");
                querySelect.append("'7'='3' ");   //ACTIVAR CONDICION
                querySelect.append("'400127400'='BMC.ASSET' ");
//                querySelect.append("'8'=('GSM EPAP 2 EMILIANO ZAPATA','GSM EPAP 1 GARMENDIA')");
                querySelect.append("'8'=%25");
                querySelect.append("'").append(parseCI(idCI)).append("'");
                ListCI_CRQ_NACIONAL = WS_RC.consultaGCMDBList(USUARIO_WS, querySelect.toString());
            }
        }
        return ListCI_CRQ_NACIONAL;
    }

    /**
     * funcion para hacer la validacion del grupo que generó el CRQ
     *
     * @param SupportGroup
     * @return
     */


    public boolean Crotalo_groupSupport(String SupportGroup) {
        boolean datosOk = false;
        System.out.println("Crotalo_groupSupport GRUPO: '"+ SupportGroup +"'");
        try {
            querySelectCRQValues = new StringBuilder();
        /*    querySelectCRQValues.append("&cForma=CTM:Support Group");
            querySelectCRQValues.append("&cColumnas=1");
            querySelectCRQValues.append("&cCondiciones=");  */
            
            querySelectCRQValues.append("formulario/CTM:Support Group/");
            querySelectCRQValues.append("campos/1/");
            querySelectCRQValues.append("condiciones/");
            
            querySelectCRQValues.append("'1000000000'='CROTALO' ");
            querySelectCRQValues.append("'1000000015'=");
            querySelectCRQValues.append("'").append(SupportGroup).append("'");
            datosOk = WS_RC.queryBoolean(DAT_MAS, querySelectCRQValues.toString());
//            formulario/CTM:Support Group/campos/1/condiciones/'1000000000'='CROTALO' '1000000015'='(SupportGroup)'
            System.out.println("Crotalo_groupSupport datosOk: "+ datosOk);
        } catch (Exception exc) {
            log.error("ERROR Crotalo_groupSupport: ");
            exc.printStackTrace();
            log.error("ERROR param: *" + querySelectCRQValues+"*");
//            exc.printStackTrace();
        }
        return datosOk;
    }

    public void cancelaCrCrotalo_1(String Entry_ID) {
        try { //http://{{IP_RC}}:{{PORT_TC_8}}/{{APP_REMEDYCONTROL}}rest/update/cliente/ADM/formulario/CHG:ChangeInterface/id/CRQ000002186195/campos/'1000000000'='PRUEBA AAMM - UPDATE REST'
            log.debug(".:.:. HACE LA CANCELACION DEL CR.:.:.");
            String URL_UPDATE = "";
            URL_UPDATE += FORM_ICH;//formulario/CHG:ChangeInterface/id/CRq0000/
//            URL_UPDATE += "&cID=" + Entry_ID + "";
            URL_UPDATE += "id/" + Entry_ID + "/";
            URL_UPDATE += DAT_ICH;
            log.debug(URL_UPDATE);
            //srm.ACTUALIZA_RM(USUARIO_WS, URL_UPDATE);
        } catch (Exception exc) {
            log.error("ERROR cancelaCrCrotalo: " + exc);
//            exc.printStackTrace();
        }

    }

    public boolean insertaRegistroDB(String idCRQ, String tipoCRQ) {
        boolean datosOk = false;
        try {//&cForma=AMX:CHG:InfrastructureChange:TipoCrotalo-Region&cColumnas='4'='CROTALO' '2'='29751.telcel.mex.amx' '7'='0' '536870913'='4000' '536870915'='CRQ00000885522'
            String URL_INSERTA = "";
            URL_INSERTA += FORM_MAS; //   formulario/AMX:CHG:InfrastructureChange:TipoCrotalo-Region/condiciones/'4'='CROTALO' '2'='29751.telcel.mex.amx' '7'='0' '536870913'='4000' '536870915'='CRQ00000885522'
            URL_INSERTA += CON_MAS3;
            URL_INSERTA += " '536870913'='" + tipoCRQ + "' '536870915'='" + idCRQ.substring(12, 27) + "'";
            log.info("PARAM INSERT DB: " + URL_INSERTA);
            WS_RC.Insert_Ws2(URL_INSERTA);
//          formulario/AMX:CHG:InfrastructureChange:TipoCrotalo-Region/condiciones/'4'='CROTALO' '2'='29751.telcel.mex.amx' '7'='0' '536870913'='4000' '536870915'='CRQ00000885522'
        } catch (Exception exc) {
            log.error("ERROR insertaRegistroDB - " + exc);
//            exc.printStackTrace();
        }
        return datosOk;
    }

    /**
     * Valida si el CRQ ya fue procesado
     *
     * @param CRQ_Crotalo
     * @return
     */
    public boolean CRQ_Successful(String CRQ_Crotalo) {
        boolean datosOk = false;
        try {
            querySelectCRQValues = new StringBuilder();
/*            querySelectCRQValues.append("&cForma=AMX:CHG:InfrastructureChange:TipoCrotalo-Region");
            querySelectCRQValues.append("&cColumnas=1");
            querySelectCRQValues.append("&cCondiciones='4'='CROTALO' ");    */

            querySelectCRQValues.append("formulario/AMX:CHG:InfrastructureChange:TipoCrotalo-Region/");
            querySelectCRQValues.append("campos/1/");
            querySelectCRQValues.append("condiciones/'4'='CROTALO' ");
            
            querySelectCRQValues.append("'536870915'=");
            querySelectCRQValues.append("'").append(CRQ_Crotalo).append("'");
//          formulario/AMX:CHG:InfrastructureChange:TipoCrotalo-Region/campos/1/condiciones/'4'='CROTALO' '536870915'='CRQ000002178567'
            datosOk = WS_RC.queryBoolean(USUARIO_WS, querySelectCRQValues.toString());
            System.out.println("CRQ_Successful datosOk: "+ datosOk);
        } catch (Exception exc) {
            log.error("ERROR CRQ_Successful: " +  exc);
//            exc.printStackTrace();
        }
        return datosOk;
    }

    /**
     * funcion para el parseo de tipo de CI cuando no aplica el individual
     *
     * @param ciCrq
     * @return
     */
    public String parseCI(String ciCrq) {
        switch (ciCrq) {
            case "GENERICO_HLR":
                genericCI = "%25GSM HLR%25";
                break;
            case "GENERICO_MSC":
                genericCI = "%25GSM MSC%25";
                break;
            case "GENERICO_SNA":
                genericCI = "%25SNA%25";
                break;
            case "GENERICO_HSS-FE":
                genericCI = "%25LTE HSS%25";
                break;
            case "GENERICO_VPN":
                genericCI = "%25V-VPN%25";
                break;
            case "GENERICO_TSP":
                genericCI = "%25GSM CCN%25";
                break;
            case "GENERICO_DRA":
                genericCI = "%25LTE DRA%25";
                break;
            case "GENERICO_STP":
                genericCI = "%25GSM STP%25";
                break;
            case "GENERICO_EPAP":
                genericCI = "%25GSM EPAP%25";
                break;
            case "GENERICO_MGW":
                genericCI = "%25GSM MGW%25";
                break;
            case "GENERICO_BSC":
                genericCI = "%25GSM BSC%25";
                break;
            case "GENERICO_TRC":
                genericCI = "%25GSM TRC%25";
                break;
        }
        return genericCI;
    }
}
