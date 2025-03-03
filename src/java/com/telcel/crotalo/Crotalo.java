/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telcel.crotalo;

import com.telcel.crotalo.utilities.WsRControl;
import org.apache.log4j.Logger;

public class Crotalo extends CrotaloEntities{

    public static final Logger log = Logger.getLogger(Crotalo.class);
    public static final WsRControl WS_RC = new WsRControl();
 
    public static void main(String[] args) throws Exception {
        CrotaloProcesos procesosCrotalo;
 
        try {
            procesosCrotalo = new CrotaloProcesos();
        //    log.info("Primera consulta...");
            StringBuilder querySelectCRQ = new StringBuilder();

            querySelectCRQ.append("CHG:Infrastructure Change/");
            querySelectCRQ.append("?fields=values(1)");
            //TODO: cambiar 0 por 7 al finalizar
            querySelectCRQ.append("&q='7'=\"0\" "); //status = Implementation In Progress
            querySelectCRQ.append("AND'536870913'=\"2\" "); // Tipo Crotalo__c = 0=Individual, 1=Regional, 2=Multiple, 3=Nacional
            querySelectCRQ.append("AND'1000000001'=\"TELCEL\" "); // Location Company
            querySelectCRQ.append("AND'1000001270'=\"SOFTWARE\" "); // Product Cat Tier 1(2)
            querySelectCRQ.append("AND'1000001271'=\"APLICACION\" "); // Product Cat Tier 2 (2)
            querySelectCRQ.append("AND'1000001272'=\"RCONTROL\" "); // Product Cat Tier 3 (2)
            querySelectCRQ.append("AND'1000002268'=\"CROTALO\" "); // Product Name (2)
            querySelectCRQ.append("AND'1000000015'=\"CORP-OPMA-ORDENES DE TRABAJO MSC\" "); // Support Group Name
            querySelectCRQ.append("AND'1000000568'=\"6000\""); // Change Timing



            procesosCrotalo.datosCRQ(WS_RC.CRQ_CROTALO(querySelectCRQ.toString()));
                                            //primeraconsulta(1)
                    //Entra a datosCRQ(2)     
                    //La lista de CRQ es
                    //Lista de CRQ nueva
                        //CRQ_Process()-->//Entra a CRQ_Process (3)
        } catch (Exception exc) {
            log.error("ERROR Crotalo: " + exc);
            exc.printStackTrace();
        }
    }
}