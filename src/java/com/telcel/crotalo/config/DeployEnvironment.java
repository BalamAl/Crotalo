package com.telcel.crotalo.config;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

public class DeployEnvironment {
    private String url;
    private String user;
    private String pass;
    private String port;
    private String ambiente;  // Nuevo atributo para almacenar el ambiente detectado
    private Properties prop = new Properties();

    public DeployEnvironment() {
        cargarPropiedades();
    }

    private void cargarPropiedades() {
        try (InputStream input = DeployEnvironment.class.getClassLoader().getResourceAsStream("crotalo2.properties")) {
            if (input == null) {
                throw new IOException("No se pudo cargar el archivo de propiedades");
            }
            prop.load(input);
            user = prop.getProperty("REMEDY_USER");
            port = prop.getProperty("ARS_PORT");
        } catch (IOException ex) {
            System.out.println("Error al cargar el archivo de propiedades");
        }
    }

    public void peticionIp() {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            String ip = localHost.getHostAddress();

            if (ip.equals(prop.getProperty("QA_RC_IP"))) {
                url = prop.getProperty("QA_ARS_IP");
                pass = prop.getProperty("QA_REMEDY_PASS");
                ambiente = "QA";
                System.out.println(url);
                System.out.println("ambiente detectado QA");
                System.out.println("Puerto: " +port);
            } else if (ip.equals(prop.getProperty("PROD_RC_IP"))) {
                url = prop.getProperty("PROD_ARS_IP");
                pass = prop.getProperty("PROD_REMEDY_PASS");
                ambiente = "PROD";
                System.out.println("ambiente detectado Prod");
                System.out.println(url);
                System.out.println("Puerto: " +port);
            } else {
                url = prop.getProperty("DEV_ARS_IP");
                pass = prop.getProperty("DEV_REMEDY_PASS");
                ambiente = "DEV";
                System.out.println("ambiente detectado DEV");
                System.out.println(url);
                System.out.println("Puerto: " +port);
            }
        } catch (UnknownHostException e) {
            System.out.println("Error al obtener la IP del Host");
        }
    }

    // Getters
    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public String getPort() {
        return port;
    }

    public String getAmbiente() {
        return ambiente;
    }
}
