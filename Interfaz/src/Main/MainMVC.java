/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;
import vista.*;
import controlador.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import modelo.*; 
/**
 *
 * @author 1001001222
 */
public class​ MainMVC{
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException{
        //Conexion conexion=new Conexion();
        
        
        
        
        InicioForm vista=new InicioForm();
        
        ControladorInicio controladorInicio=new ControladorInicio(vista);
        controladorInicio.vista.setVisible(true);
        controladorInicio.vista.setLocationRelativeTo(null);
        
    }
}
