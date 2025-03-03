/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telcel.crotalo.utilities;

import com.infomedia.utils.PropertyLoader;
import java.io.BufferedReader;
//import static java.io.File.separator;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
//import java.util.logging.Level;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.telcel.crotalo.service.Token;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.google.gson.*;
import javax.xml.parsers.ParserConfigurationException;
import okhttp3.*;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

/**
 *
 * @author INFOTELCEL
 * @updated 2023 KC
 */
public class WsRControl extends Thread {

    public static final Logger log = Logger.getLogger(WsRControl.class);
    public static final Properties prop = PropertyLoader.load("crotalo2.properties");
    public static final String userWs = prop.getProperty("CLIENTE_ARS");        //////////////////
    public StringBuilder querySelectCRQValues;

    public List<String> ListCRQ_Single;
    public List<String> ListCRQRegional;
    public List<String> ListCRQMultiple;

    public String lineConsole;
    public String urlParametros;
    public String resultInsert;
    public String resultadoConsulta = "";
    public String sistemaWS;
    public String consultaWS;
    public List<String> ListaResultado = new ArrayList<>();
    public List<String> ListOfValues;

    public String getResultadoConsulta() {
        return resultadoConsulta;
    }

    public void setResultadoConsulta(String resultadoConsulta) {
        this.resultadoConsulta = resultadoConsulta;
    }

    public List<String> getListaResultado() {
        return ListaResultado;
    }

    public void setListaResultado(List<String> ListaResultado) {
        this.ListaResultado = ListaResultado;
    }

    public String getSistemaWS() {
        return sistemaWS;
    }

    public void setSistemaWS(String sistemaWS) {
        this.sistemaWS = sistemaWS;
    }

    public String getConsultaWS() {
        return consultaWS;
    }

    public void setConsultaWS(String consultaWS) {
        this.consultaWS = consultaWS;
    }

    public List<String> getListOfValues() {
        return ListOfValues;
    }

    public void setListOfValues(List<String> ListOfValues) {
        this.ListOfValues = ListOfValues;
    }

    public List<String> getListCRQ_Single() {
        return ListCRQ_Single;
    }

    public void setListCRQ_Single(List<String> ListCRQ_Single) {
        this.ListCRQ_Single = ListCRQ_Single;
    }

    public List<String> getListCRQRegional() {
        return ListCRQRegional;
    }

    public void setListCRQRegional(List<String> ListCRQRegional) {
        this.ListCRQRegional = ListCRQRegional;
    }

    public List<String> getListCRQMultiple() {
        return ListCRQMultiple;
    }

    public void setListCRQMultiple(List<String> ListCRQMultiple) {
        this.ListCRQMultiple = ListCRQMultiple;
    }

    /**
     * Funcion para la generación del ADM, recibe los datos de las columnas y
     * del sistema para hacer la consulta del API del REMEDYCONTROL
     *
     * @author HARL
     * @param sistemaWs
     * @param consultaWs
     * @since 25/02/2015
     */
    public WsRControl(String sistemaWs, String consultaWs) {

        this.sistemaWS = sistemaWs;
        this.consultaWS = consultaWs;

    }

    public WsRControl() {

    }

    public String Insert_Ws(String columnas) {

        try {
//            URL urlRemedy = new URL("http://10.191.205.236:8081/RemedyControl/rest/insert/");
            URL urlRemedy = new URL(prop.getProperty("URL_INSERT"));
            urlParametros =  columnas;
            
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = RequestBody.create(mediaType, "");
            Request request = new Request.Builder().url(urlRemedy + urlParametros).method("POST", body).build();
            Response response = client.newCall(request).execute();
            
            String Json = response.body().string();
            System.out.println("RESPUESTA Insert_Ws: "+ Json);
            Gson gson=new Gson();
            try{
                if (Json.contains("values\":{")){
                    JsonObject gsonobject2 = gson.fromJson(Json, JsonObject.class);
                    JsonElement data2 = gsonobject2.get("values");
                    String objectjson2 =data2.toString();

                    String [] split, split2; 
                    split = objectjson2.split(",");

                    for (String linea : split){
                        linea = linea.replace("\":\"",";").replace("\"","").replace("}","");     //quita separador (":"), comillas (") y llave (}) de cierre
                        split2 = linea.split(";");
                        resultInsert = split2[1];
                    }
                }else resultInsert = "\nNo se pudo insertar TAS...";
                
            }catch(JsonSyntaxException | java.lang.NullPointerException ex){
                System.out.println("consulta vacía: \n" + ex);
            }
        } catch (IOException exc) {
                log.error("ERROR Insert_Ws: "+exc);
                exc.printStackTrace();
        }
        log.info("RESULTADO Insert_Ws:" + resultInsert);
        return resultInsert;
    }
    
    
    public String Insert_Ws2(String columnas) {
        System.out.println("\nENTRO A Insert_Ws2\n");
        String userws = "&cSistema=CROTALO";
        try {
            resultInsert = "";
            urlParametros = userws + columnas;  //  &cSistema=CROTALO&cForma=AMX:CHG:InfrastructureChange:TipoCrotalo-Region&cColumnas='4'='CROTALO' '2'='29751.telcel.mex.amx' '7'='0' '536870913'='4000' '536870915'='CRQ00000885522'
            URL urlRemedy = new URL(prop.getProperty("URL_INSERT2"));
            URLConnection conn = urlRemedy.openConnection();
            conn.setDoOutput(true);
            BufferedReader reader;
            log.debug("Insert_Ws2: " + urlRemedy + urlParametros);
            try (OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream())) {
                writer.write(urlParametros);
                writer.flush();
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((lineConsole = reader.readLine()) != null) {
                    if (lineConsole.startsWith("<NUEVO>")) {
                        resultInsert = lineConsole.substring(7, 22);
                    } else if (lineConsole.startsWith("<ERROR>")) {
                        resultInsert = lineConsole.substring(7, lineConsole.indexOf("</ERROR>"));
                    }
                }//http://10.191.205.236:8081/RemedyControl/rest/select/cliente/CROTALO/formulario/TMS:Task/campos/'10007000'='CROTALO'%20'10002506'='CORP-OPMA-FRONT%20OFFICE%20MSC%20RBS'%20'10000005'='CHG:Infrastructure%20Change'%20'536900001'='7'%20'536900150'='1'%20'1000000251'='TELCEL'%20'200000012'='REGION%209'%20'10000001'='CRQ000000033402'%20'10000006'='CRQ000000033402'%20'10002506'='CORP-OPMA-GESTION%20DE%20PROCESOS'%20'7'='2000'%20'8'='ALTA%20DE%20CAT2'%20'10000003'='0'%20'10001980'='2000'%20'10000101'='ALTA%20DE%20CAT2'%20%20'1000000063'='SOFTWARE'%20'1000000064'='APLICACION'%20'1000001270'='SOFTWARE'%20'1000001271'='APLICACION'%20'10001980'='3000'
            }
            reader.close();
        } catch (IOException exc) {
                log.error("ERROR Insert_Ws2: "+exc);
        }
        log.debug("RESULTADO Insert_Ws2: " + resultInsert);
        return resultInsert;
    }

    /**
     * Funcion para la consulta del usuario dentro del formulario de Nomina
     *
     * @author HARL
     * @param sistema
     * @param columna
     * @return datosOk
     * @since 25/02/2015
     */
    public boolean queryBoolean(String sistema, String columna) {
        System.out.println("ENTRANDO queryBoolean...");
        boolean datosOk = false;
        try {
            String urlRemedy = prop.getProperty("URL_SELECT");
            urlParametros = columna;
            System.out.println("urlParametros: "+urlParametros);
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            Request request = new Request.Builder().url(urlRemedy + urlParametros).build();
            Response response = client.newCall(request).execute();

            String Json = response.body().string();
            System.out.println("RESPUESTA queryBoolean: "+Json);
//            Gson gson=new Gson();
//            JsonObject gsonobject = gson.fromJson(Json, JsonObject.class);
//            JsonElement data = gsonobject.get("entries");
//            String objectjson = data.toString().replace("[", "").replace("]", "");
//            System.out.println("objectjson: \n"+objectjson);
            
                if (Json.contains("Support Group ID") || Json.contains("Request ID__c")){
                   System.out.println("boolean TRUE");
                   datosOk = true;
                }else System.out.println("boolean false");
//            URLConnection conn = urlRemedy.openConnection();
//            conn.setDoOutput(true);
//            BufferedReader reader;
//            try (OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream())) {
//                writer.write(urlParametros);
//                writer.flush();
//                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                while ((lineConsole = reader.readLine()) != null) {
//                /*    if (lineConsole.startsWith("<value>")) {
//                        datosOk = true;
//                    }   */
//                    if(lineConsole.contains("values")){
//                        datosOk = true;
//                    }
//                }
//            }
            System.out.println("queryBoolean datosOk: " + datosOk);
//            reader.close();
        } catch (IOException exc) {
            log.error("ERROR queryBoolean: "+exc);
            log.error("params queryBoolean: "+urlParametros);
        }
        return datosOk;
    }

    /**
     *
     * @param columna
     * @return
     */
    public List<String> queryCRQMulti(String columna) {
        ListCRQMultiple = new ArrayList<>();
        try {
            urlParametros = columna;
            URL urlRemedy = new URL(prop.getProperty("URL_SELECT"));

            OkHttpClient client = new OkHttpClient().newBuilder().build();
            MediaType mediaType = MediaType.parse("application/json");
            okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, "");
            
            Request request = new Request.Builder().url(urlRemedy + urlParametros).build();
            Response response = client.newCall(request).execute();
            System.out.println("PARAMS MULTI: " + urlRemedy + urlParametros);
            String Json = response.body().string();
            System.out.println("RESPUESTA queryCRQMulti: "+ Json);
            
            Gson gson=new Gson();
            JsonObject gsonobject = gson.fromJson(Json, JsonObject.class);
            JsonElement data = gsonobject.get("entries");
            String objectjson = data.toString().replace("[", "").replace("]", "");
            System.out.println("RESPUESTA sin entries: \n"+objectjson);

            String [] split, split2; 
            split = objectjson.split(",");

            for (String linea : split){
                if (linea.contains("values\":{")) {
                    linea = linea.replace("{\"values\":{\"AMX_Region__c\":\"",";").replace("\"","").replace("}","");     //quita separador (":"), comillas (") y llave (}) de cierre
                    split2 = linea.split(";");
                    ListCRQMultiple.add(split2[1]);
                    System.out.println(split2[1]+"\n");    ///////
                }
            }
            System.out.println("\nListCRQMultiple queryCRQMulti:  " + ListCRQMultiple);

            
        } catch (IOException exc) {
            log.error("ERROR queryCRQMulti: "+exc);
            log.error("PARAMS: " + urlParametros);
//            exc.printStackTrace();
        }
        log.debug(ListCRQMultiple);
        return ListCRQMultiple;
    }

    /**
     * funcion para consultar las regiones relacionadas al CRQ
     *
     * @param query
     * @return
     */
    public String queryCRQRegional(String query) {

        try {
            URL urlRemedy = new URL(prop.getProperty("URL_SELECT"));
            urlParametros =   query;
/*            URLConnection conn = urlRemedy.openConnection();
            conn.setDoOutput(true);
            BufferedReader reader;
            try (OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream())) {
                writer.write(urlParametros);
                writer.flush();
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((lineConsole = reader.readLine()) != null) {
                    if (lineConsole.startsWith("<value>") && !lineConsole.startsWith("<value>CRQ")) {
                        resultadoConsulta = lineConsole.substring(7, lineConsole.indexOf("</val"));
                    }
                }
            }
            reader.close();     */
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            MediaType mediaType = MediaType.parse("application/json");
            okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, "");
            
            Request request = new Request.Builder().url(urlRemedy + urlParametros).build();
            System.out.println("PARAMS: " + urlParametros);
            Response response = client.newCall(request).execute();
            
            String Json = response.body().string();
            System.out.println("RESPUESTA queryCRQRegional: " + Json);
            
            Gson gson=new Gson();
            JsonObject gsonobject = gson.fromJson(Json, JsonObject.class);
            JsonElement data = gsonobject.get("entries");
            String objectjson = data.toString().replace("[", "").replace("]", "");
//            String objectjson = data.toString();
            System.out.println("RESPUESTA sin entries: "+objectjson);
                
            JsonObject gsonobject2 = gson.fromJson(objectjson, JsonObject.class);
            JsonElement data2 = gsonobject2.get("values");
            String objectjson2 =data2.toString();
//                System.out.println("JSON sin entries: "+objectjson2);

            String [] split, split2; 
            split = objectjson2.split(",");

            for (String linea : split){
                linea = linea.replace("\":\"",";").replace("\"","").replace("}","");     //quita separador (":"), comillas (") y llave (}) de cierre
                split2 = linea.split(";");
                resultadoConsulta = split2[1];
                System.out.println(split2[1]+"\n");    ///////
            }
            System.out.println("\nresultadoConsulta queryCRQRegional:  " + resultadoConsulta);

            
        } catch (JsonSyntaxException | IOException exc) {
            log.error("ERROR queryCRQRegional: " + exc);
            log.error("PARAMS: " + urlParametros);
//            exc.printStackTrace();
        }
        log.debug(resultadoConsulta);
        return resultadoConsulta;
    }

    /**
     * funcion para hacer la consulta de los CRQ que apliquen al CROTALO
     *
     * @param columna
     * @return
     */
    public List<String> CRQ_CROTALO(String columna) {

        List<String> listOfValues = new ArrayList<>();

        try {
            // Generar token
            Token tokenGenerado = new Token();
            tokenGenerado.Generar_token();
            String token = tokenGenerado.getToken();
            System.out.println("Token: " + token);

            if (token == null || token.isEmpty()) {
                System.out.println("Error: El token no se pudo generar.");
                return listOfValues;
            }

            String baseUrl = prop.getProperty("URL_SELECT");
            String fullUrl = baseUrl + columna;
            System.out.println("URL generada: " + fullUrl);

            OkHttpClient client = new OkHttpClient().newBuilder().build();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = RequestBody.create(mediaType, "");
            Request request = new Request.Builder()
                    .url(fullUrl)
                    .method("GET", null)
                    .addHeader("Authorization", "AR-JWT " + token)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                System.out.println("Response Code: " + response.code());

                // Verificar si la respuesta es exitosa
                if (!response.isSuccessful()) {
                    System.out.println("Error: Respuesta no exitosa.");
                    return listOfValues;
                }

                String jsonResponse = response.body().string();
                System.out.println("Respuesta JSON de Remedy: " + jsonResponse);

                // Procesar la respuesta JSON
                Gson gson = new Gson();
                JsonElement jsonElement = gson.fromJson(jsonResponse, JsonElement.class);

                if (!jsonElement.isJsonObject()) {
                    System.out.println("Error: La API devolvió un formato inesperado.");
                    return listOfValues;
                }

                JsonObject jsonObject = jsonElement.getAsJsonObject();
                JsonArray entries = jsonObject.getAsJsonArray("entries");

                if (entries == null || entries.size() == 0) {
                    System.out.println("Error: No se encontraron datos en 'entries'.");
                    return listOfValues;
                }

                // Extraer valores
                for (JsonElement entryElement : entries) {
                    JsonObject entryObject = entryElement.getAsJsonObject();
                    JsonObject values = entryObject.getAsJsonObject("values");

                    if (values.has("Request ID")) {
                        String requestId = values.get("Request ID").getAsString();
                        listOfValues.add(requestId);
                    }
                }

                System.out.println("\n\nLISTA CRQ_CROTALO: " + listOfValues);

            } catch (IOException e) {
                System.err.println("ERROR en la solicitud HTTP: " + e.getMessage());
                e.printStackTrace();
            }

        } catch (Exception e) {
            System.err.println("ERROR CRQ_CROTALO: " + e.getMessage());
            e.printStackTrace();
        }

        return listOfValues;
    }

    /**
     * funcion de consulta valores del CRQ que apliquen al CROTALO
     *
     * @param indice
     * @return
     */
    public List<String> CRQ_VALUES(String indice) {
        ListOfValues = new ArrayList<>();
        querySelectCRQValues = new StringBuilder();
        querySelectCRQValues.append("formulario/CHG:Infrastructure Change/");
        querySelectCRQValues.append("campos/1,260000001,536870913,1000000000,1000000151,1000000182,1000000350,1000000362,1000003229/");
        querySelectCRQValues.append("condiciones/'1'=");
        querySelectCRQValues.append("'").append(indice).append("'");
//      querySelectCRQValues = formulario/CHG:Infrastructure Change/campos/536870913,1000000000,1000000151,260000001,1000000182,1000000350,1000000362,1000003229/condiciones/'1'='CRQ000002178567'
/*respuesta ListOfValues:
        "Tipo Crotalo__c":"Nacional",
        "Description":"APERTURA DE SERIES TELCEL 82 EN SERVIDORES EPAP",
        "Detailed Description":"Actividad sin afectacion",
        "Site":"REGION 9",
        "Infrastructure Change ID":"CRQ000004527107",
        "Scheduled Start Date":"2020-12-11T18:18:29.000+0000",
        "Scheduled End Date":"2020-12-18T18:18:29.000+0000",
        "ASGRP":"CORP-INCR-DISEÑO Y OPTIMIZACION DE LA RED"
*/
        try {
            URL urlRemedy = new URL(prop.getProperty("URL_SELECT"));
            urlParametros = querySelectCRQValues.toString();
//            System.out.println("PARAMS CRQ VALUES: "+urlParametros);
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            
            Request request = new Request.Builder().url(urlRemedy + urlParametros).build();
            Response response = client.newCall(request).execute();
            
            String Json = response.body().string();
//            System.out.println("\nRESPUESTA VALUES: "+Json);
            
            Gson gson=new Gson();
            JsonObject gsonobject = gson.fromJson(Json, JsonObject.class);
            JsonElement data = gsonobject.get("entries");
            String objectjson = data.toString().replace("[", "").replace("]", "");
//            System.out.println("RESPUESTA SIN ENTRIES: \n"+objectjson);
            try{
                JsonObject gsonobject2 = gson.fromJson(objectjson, JsonObject.class);
                JsonElement data2 = gsonobject2.get("values");
                String objectjson2 =data2.toString();
                
//                System.out.println("\nRESPUESTA VALUES: "+objectjson2);
                
                String [] split, split2; 
                split = objectjson2.split("\",\"");
//                System.out.println("SPLIT: "+split);
                
                for (String linea : split){
//                    System.out.println("\nLINEA: "+linea);
                    linea = linea.replace("\":\"",";").replace("\"","").replace("}","");
                    split2 = linea.split(";");
                    ListOfValues.add(split2[1]);
//                    System.out.println(Arrays.toString(split2));  
//                    System.out.println(split2[0]+":"+split2[1]);
                }   System.out.println("Lista CRQ_VALUES:  " + ListOfValues);
            }catch(JsonSyntaxException | java.lang.NullPointerException ex){
                System.out.println("Consulta vacía o incorrecta:\t" + ex);
            }
            
            
        /*    URLConnection conn = urlRemedy.openConnection();
            conn.setDoOutput(true);
            BufferedReader reader;
            String SaltoLinea = "";
            String SiguienteLinea = "";
            String consultaNormal = "";
            try (OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream())) {
                writer.write(urlParametros);
                writer.flush();
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((lineConsole = reader.readLine()) != null) {
                    if (lineConsole.startsWith("<value>") && !lineConsole.endsWith("</value>")) {
                        SaltoLinea = lineConsole.substring(7, lineConsole.length());
                        //<value>hcfewkekew<>
                    }
                    if (!lineConsole.startsWith("<value>") && lineConsole.endsWith("</value>")) {
                        SiguienteLinea = lineConsole.substring(0, lineConsole.indexOf("</value>"));
                        log.debug("Linea sin salto: "+SaltoLinea+" "+SiguienteLinea);
                        ListOfValues.add(SaltoLinea+" "+SiguienteLinea);
                        //<>asdfghjklñ</value>
                    }
                    if (lineConsole.startsWith("<value>") && lineConsole.endsWith("</value>")) {
                        consultaNormal = lineConsole.substring(7, lineConsole.indexOf("</value>"));
                        ListOfValues.add(consultaNormal);
                        //<value>asdfghjklñ</value>
                    }
                }    
            }
            reader.close();     */
        } catch (JsonSyntaxException | IOException exc) {
            log.error("ERROR CRQ_VALUES: " + exc);
            log.error("PARAMS: " + urlParametros);
            exc.printStackTrace();
        }
        //log.debug(ListOfValues);
        return ListOfValues;
    }

    /**
     * Funcion para consultas de datos dependiendo del formulario que reciba, en
     * este caso sistema
     *
     * @param sistema
     * @param columna
     * @return datosOk
     * @since 25/02/2015
     */
    public List<String> consultaGList_1(String sistema, String columna) {
        List<String> ListaFinal = new ArrayList<>();
        try {
            URL urlRemedy = new URL(prop.getProperty("URL_SELECT"));
            urlParametros = sistema + columna;
            URLConnection conn = urlRemedy.openConnection();
            conn.setDoOutput(true);
            BufferedReader reader;
            //LOG.debug(urlRemedy + urlParametros);
            try (OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream())) {
                writer.write(urlParametros);
                writer.flush();
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String documento = "";
                while ((lineConsole = reader.readLine()) != null) {
                    documento = documento + lineConsole;
                }
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();

                InputSource archivo = new InputSource();
                archivo.setCharacterStream(new StringReader(documento));

                Document doc = db.parse(archivo);
                doc.getDocumentElement().normalize();

                NodeList nodeLista = doc.getElementsByTagName("Field");

                for (int indice = 0; indice < nodeLista.getLength(); indice++) {
                    Node primerNodo = nodeLista.item(indice);
                    String ID ;
                    String VALUE ;
                    if (primerNodo.getNodeType() == Node.ELEMENT_NODE) {

                        Element elementFirst = (Element) primerNodo;

                        NodeList firstNameElementList = elementFirst.getElementsByTagName("id");
                        Element firstNameElement = (Element) firstNameElementList.item(0);
                        NodeList firstName = firstNameElement.getChildNodes();
                        ID = ((Node) firstName.item(0)).getNodeValue();

                        NodeList secondNameElementList = elementFirst.getElementsByTagName("value");
                        Element secontNameElement = (Element) secondNameElementList.item(0);
                        NodeList secondName = secontNameElement.getChildNodes();
                        VALUE = ((Node) secondName.item(0)).getNodeValue();
                        ListaFinal.add(VALUE);
                        //LOG.debug(ListOfValues);
                    }
                }
            }
            reader.close();

        } catch (IOException | ParserConfigurationException | DOMException | SAXException exc) {
            log.error("ERROR consultaGList: " + exc);
            log.error("PARAMS: " + urlParametros);
//            exc.printStackTrace();
        }
        return ListaFinal;
    }

    public List<String> consultaGCMDBList(String sistema, String columna) {
        log.info("ENTRO A consultaGCMDBList");
        List<String> ListaFinal = new ArrayList<>();
        
        try {
            URL urlRemedy = new URL(prop.getProperty("URL_SELECT"));
            urlParametros = sistema + columna;

            OkHttpClient client = new OkHttpClient().newBuilder().build();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = RequestBody.create(mediaType, "");
            
            Request request = new Request.Builder().url(urlRemedy + urlParametros).build();
            System.out.println("PARAMS: " + urlParametros);
            Response response = client.newCall(request).execute();
            String Json = response.body().string();
            System.out.println("RESPUESTA: "+Json);

            Gson gson=new Gson();
            JsonObject gsonobject = gson.fromJson(Json, JsonObject.class);
            JsonElement data = gsonobject.get("entries");
            String objectjson = data.toString().replace("[", "").replace("]", "");
            System.out.println("RESPUESTA SIN ENTRIES: "+objectjson);
        
            String[] split;
            String valor;

            split = objectjson.split(",");
            for (String split1 : split) {
                if (split1.contains("values\":{")) {
                    valor = split1.replace("{\"values\":{\"Name\":\"", "").replace("\"}", "");
                    System.out.println("VALOR: " +valor);
                    ListaFinal.add(valor);
                }
            }
            
            System.out.println("\nListaFinal GCMDB:  " + ListaFinal);
    
        } catch (JsonSyntaxException | IOException | java.lang.NullPointerException exc) {
            log.error("ERROR consultaGCMDBList: " + exc);
            log.error("PARAMS:" + urlParametros);
            exc.printStackTrace();
        }
        log.debug(ListaFinal);
        return ListaFinal;
    }

    public String consultaCRQ_CI_Multiple_1(String sistema, String columna) {
        List<String> ListaFinal = new ArrayList<>();
        try {
            URL urlRemedy = new URL(prop.getProperty("URL_SELECT"));
            urlParametros = sistema + columna;
            URLConnection conn = urlRemedy.openConnection();
            conn.setDoOutput(true);
            BufferedReader reader;
            log.debug(urlRemedy + urlParametros);
            try (OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream())) {
                writer.write(urlParametros);
                writer.flush();
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((lineConsole = reader.readLine()) != null) {
                    if (lineConsole.startsWith("<value>") && !lineConsole.startsWith("<value>00000") && !lineConsole.startsWith("<value>OI-") && !lineConsole.startsWith("<value>BMC_COMPUTERSYSTEM") && !lineConsole.startsWith("<value>BMC.ASSET")) {
                        resultadoConsulta = lineConsole.substring(7, lineConsole.indexOf("</value>"));
                    }
                }
            }
            reader.close();
        } catch (IOException exc) {
            log.error("ERROR consultaCRQ_CI_Multiple: " + exc);
            log.error("PARAMS: " + urlParametros);
//            exc.printStackTrace();
        }
        log.debug(ListaFinal);
        return resultadoConsulta;
    }

    /**
     *
     * @param ID_CRQ
     * @return
     */
    public List<String> query_CRQ_Single(String ID_CRQ) {
        ListCRQ_Single = new ArrayList<>();
        log.info("Entra a buscar CI");
        querySelectCRQValues = new StringBuilder();
//        h ttp://localhost:8080/Remedy/servicios/RMDSelect?&cForma=CHG:Associations&cColumnas=1000000206 1000000211&cCondiciones='1000000101'='AST:ComputerSystem' '1000000203'='CHG:Infrastructure Change' '230000009'='BMC_COMPUTERSYSTEM' '1000000211'='6000' '301763400'='BMC.ASSET' '301763500'='BMC.ASSET' '1000000205'='CRQ000004536404'
/*        querySelectCRQValues.append("&cForma=CHG:Associations");
        querySelectCRQValues.append("&cColumnas=1000000206 1000000211");
        querySelectCRQValues.append("&cCondiciones=");*/
        
        querySelectCRQValues.append("formulario/CHG:Associations/");
        querySelectCRQValues.append("campos/1000000206/");
        querySelectCRQValues.append("condiciones/");
        
        querySelectCRQValues.append("'1000000101'='AST:ComputerSystem' ");
        querySelectCRQValues.append("'1000000203'='CHG:Infrastructure Change' ");
        querySelectCRQValues.append("'230000009'='BMC_COMPUTERSYSTEM' ");
        querySelectCRQValues.append("'1000000211'='6000' ");
        querySelectCRQValues.append("'301763400'='BMC.ASSET' ");
        querySelectCRQValues.append("'301763500'='BMC.ASSET' ");
        querySelectCRQValues.append("'1000000205'=");
        querySelectCRQValues.append("'").append(ID_CRQ).append("'");
//      querySelectCRQValues= formulario/CHG:Associations/campos/1000000206/condiciones/'1000000101'='AST:ComputerSystem' '1000000203'='CHG:Infrastructure Change' '230000009'='BMC_COMPUTERSYSTEM' '1000000211'='6000' '301763400'='BMC.ASSET' '301763500'='BMC.ASSET' '1000000205'='CRQ000004470010'
/*
    "Request Description01": "ITHHWBESRK1BCK01",
    "Request Type01": "Configuration Item"
*/
        try {
            URL urlRemedy = new URL(prop.getProperty("URL_SELECT"));
            urlParametros = querySelectCRQValues.toString();
        
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            Request request = new Request.Builder().url(urlRemedy + urlParametros).build();
            Response response = client.newCall(request).execute();
            System.out.println("\n\n**PARAMS SINGLE: "+urlParametros);

            String Json = response.body().string();
            System.out.println("\nRESPUESTA SINGLE: "+Json);
            Gson gson=new Gson();
            JsonObject gsonobject = gson.fromJson(Json, JsonObject.class);
            JsonElement data = gsonobject.get("entries");
            String objectjson = data.toString().replace("[", "").replace("]", "");
//            System.out.println("RESPUESTA: \n"+objectjson);

            String [] split, split2; 
            split = objectjson.split(",");

            for (String linea : split){
                if (linea.contains("values\":{")) {
                    linea = linea.replace("{\"values\":{\"Request Description01\":\"",";").replace("\"","").replace("}","");     //quita separador (":"), comillas (") y llave (}) de cierre
                    split2 = linea.split(";");
                    ListCRQ_Single.add(split2[1]);
                    System.out.println(split2[1]+"\n");    ///////
                }//else log.info("Uno o mas nodos no cumplen las condiciones, o no hay nodos...");
            }
            System.out.println("\nListCRQ_Single:  " + ListCRQ_Single);

        
        } catch (JsonSyntaxException | IOException exc) {
            log.error("ERROR query_CRQ_Single: " + exc);
            log.error("PARAMS: " + urlParametros);
//            exc.printStackTrace();
        }
        log.info(ListCRQ_Single);
        return ListCRQ_Single;
    }

    /**
     * Función para actualizar el estatus de la rutina de mantenimiento en
     * estado de inactivada
     *
     * @author HARL
     * @since 17/07/2015
     * @param sistema
     * @param columna
     */
    public void ACTUALIZA_RM_1(String sistema, String columna) {
        try {
            urlParametros = sistema + columna;
            URL urlRemedy = new URL(prop.getProperty("URL_UPDATE"));
            URLConnection conn = urlRemedy.openConnection();
            conn.setDoOutput(true);
            BufferedReader reader;
            //LOG.debug(urlRemedy + urlParametros);
            try (OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream())) {
                writer.write(urlParametros);
                writer.flush();
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((lineConsole = reader.readLine()) != null) {
                    if (lineConsole.startsWith("<UPDATED")) {
                        int finalConsulta = lineConsole.indexOf("</UPDATED>");
                        //LOG.debug("SE ACTUALIZA EL REGISTRO");
                    } else if (lineConsole.startsWith("<ERROR>")) {
                        log.error("ERROR ACTUALIZA_RM:\n" + urlRemedy + urlParametros);
                        log.error("OCURRIO UN ERROR INTERNO DE APLICACIÓN, FAVOR DE REPORTARLO CON EL ADMINISTRADOR DEL SISTEMA");
                        log.error(columna);
                        log.error(lineConsole);
                    }
                }
            }
            reader.close();
        } catch (IOException exc) {
            log.error("ERROR ACTUALIZA_RM: " + exc);
            log.error("PARAMS: " + urlParametros);
        }
    }

    public void LimpiaArreglo() {
        ListaResultado.removeAll(getListaResultado());
    }
}
