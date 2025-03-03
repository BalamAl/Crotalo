package com.telcel.crotalo.pruebas;

import com.telcel.crotalo.utilities.WsRControl;

import java.util.List;

public class TestMain {
    public static void main(String[] args) {


        // Crear instancia de WsRControl
        WsRControl wsControl = new WsRControl();

        // Validar si la URL de Remedy es válida
        if (wsControl.prop == null || wsControl.prop.getProperty("URL_SELECT") == null) {
            System.out.println("Error: La URL de Remedy no está configurada correctamente.");
            return; // Termina la ejecución si la URL no está configurada
        }

        // Definir el query para la consulta
        StringBuilder querySelectCRQ = new StringBuilder();
        querySelectCRQ.append("CHG:Infrastructure Change/");
        querySelectCRQ.append("?fields=values(1)");
        querySelectCRQ.append("&q=");  // Iniciar el query
        querySelectCRQ.append("'7'=\"0\"AND");  // status = Implementation In Progress
        querySelectCRQ.append("'536870913'=\"2\"AND");// Tipo Crotalo__c = Multiple
        querySelectCRQ.append("'1000000001'=\"TELCEL\"AND");// Location Company
        querySelectCRQ.append("'1000001270'=\"SOFTWARE\"AND");// Product Cat Tier 1
        querySelectCRQ.append("'1000001271'=\"APLICACION\"AND");// Product Cat Tier 2
        querySelectCRQ.append("'1000001272'=\"RCONTROL\"AND");// Product Cat Tier 3
        querySelectCRQ.append("'1000002268'=\"CROTALO\"AND"); // Product Name
        querySelectCRQ.append("'1000000015'=\"CORP-OPMA-ORDENES DE TRABAJO MSC\"AND");// Support Group Name
        querySelectCRQ.append("'1000000568'=\"6000\"");  // Change Timing



        // Llamar al metodo CRQ_CROTALO
        System.out.println("Ejecutando CRQ_CROTALO...");
        List<String> resultados = wsControl.CRQ_CROTALO(querySelectCRQ.toString());

        // Imprimir los resultados obtenidos
        if (resultados.isEmpty()) {
            System.out.println("No se encontraron resultados.");
        } else {
            System.out.println("\nResultados obtenidos:");
            for (String resultado : resultados) {
                System.out.println(resultado);
            }
        }



    }
    }
