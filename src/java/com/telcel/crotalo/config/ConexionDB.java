/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telcel.crotalo.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

/**
 *
 * @author iHector
 */
public class ConexionDB {

    Logger LOG = Logger.getLogger(ConexionDB.class);

    Context context;
    DataSource dataSource;
    Connection conecta = null;

    public Connection getConecta() {
        return conecta;
    }

    public void setConecta(Connection conecta) {
        this.conecta = conecta;
    }

    public Connection ConexionCrotalo() {
        try {
            context = new InitialContext();
            dataSource = (DataSource) context.lookup("java:comp/env/Crotalo_Pool");
            conecta = dataSource.getConnection();
        } catch (SQLException | NamingException exc) {
            LOG.error("<- .:.:. ERROR EN LA CONEXION A LA DB DE RCONTROL DEV .:.:. ->");
            LOG.error(exc);
            exc.printStackTrace();
        }

        return conecta;
    }

    public Connection conexionPlanaControl() {
        String user = "remedy";
        String pass = "Temp.123";
        String urlOracle = "jdbc:oracle:thin:@100.127.3.15:1521:ARS81";
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conecta = DriverManager.getConnection(urlOracle, user, pass);
            LOG.debug("<- .:.:. GENERA LA CONEXION A LA DB DE RCONTROL .:.:. ->");
            if (conecta == null) {
                LOG.error("<- .:.:. ERROR EN GENERAR LA CONEXION A LA DB DE RCONTROL .:.:. ->");
            }
        } catch (SQLException | ClassNotFoundException exc) {
            LOG.error("<- .:.:. ERROR EN LA CONEXION A LA DB DE RCONTROL .:.:. ->");
            exc.printStackTrace();
            conecta = null;
        }
        return conecta;
    }

    public void cierraConexion() throws SQLException {
        conecta.close();
    }
}
