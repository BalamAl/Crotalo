/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telcel.crotalo;

import com.telcel.crotalo.utilities.WsRControl;
import org.apache.log4j.Logger;

/**
 *
 * @author iHector
 * @updated KC
 */
public class Crotalo extends CrotaloEntities{

    public static final Logger log = Logger.getLogger(Crotalo.class);
    public static final WsRControl WS_RC = new WsRControl();
 
    public static void main(String[] args) throws Exception {
        CrotaloProcesos procesosCrotalo;
 
        try {
            procesosCrotalo = new CrotaloProcesos();
        //    log.info("Primera consulta...");
            StringBuilder querySelectCRQ = new StringBuilder();
        /*    querySelectCRQ.append("&cForma=CHG:Infrastructure Change");
            querySelectCRQ.append("&cColumnas=1");
            querySelectCRQ.append("&cCondiciones=");
            querySelectCRQ.append("'7'='7' ");
            querySelectCRQ.append("'536870913'='(0,1,2,3)' ");*/
        ///////////////////////////////////////////////////////////////////
            querySelectCRQ.append("formulario/CHG:Infrastructure Change/");
            querySelectCRQ.append("campos/1/");
            querySelectCRQ.append("condiciones/'7'='7' ");
        ///////////////////////////////////////////////////////////////////
            querySelectCRQ.append("'536870913'='2' ");
            querySelectCRQ.append("'1000000001'='TELCEL' ");
            querySelectCRQ.append("'1000001270'='SOFTWARE' ");
            querySelectCRQ.append("'1000001271'='APLICACION' ");
            querySelectCRQ.append("'1000001272'='RCONTROL' ");
            querySelectCRQ.append("'1000002268'='CROTALO' ");
            querySelectCRQ.append("'1000000015'='CORP-OPMA-ORDENES DE TRABAJO MSC' ");
            querySelectCRQ.append("'1000000568'='6000'");
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