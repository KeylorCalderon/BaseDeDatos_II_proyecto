/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion {
    String url="jdbc:sqlserver://DESKTOP-TE607J4\\BASESDEDATOS;databaseName=base_CostaRica;integratedSecurity=true";
    String url2="jdbc:sqlserver://DESKTOP-TE607J4\\BASESDEDATOS;databaseName=base_Nicaragua;integratedSecurity=true";
    String url3="jdbc:sqlserver://DESKTOP-TE607J4\\BASESDEDATOS;databaseName=base_Panama;integratedSecurity=true";
    
    public Conexion(){      
    }  

    public Connection conectar (int i)throws ClassNotFoundException, SQLException{      
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con= DriverManager.getConnection(url);
            System.out.println("Conexión exitosa");
            return con;
        } catch(Exception e){
            System.out.println("Falló al conectar "+e);
        }
        return null;
    }  

    public void CerrarConexion(Connection con){
        try {
            con.close();
        } catch (Exception e) {
        }
    }
}