/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import javax.swing.*;
import java.awt.event.*;
import modelo.*;
import vista.*;
import dao.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
/**
 *
 * @author 1001001222
 */
public class ControladorUsuario implements ActionListener{
    public​ LoginForm vista;
    public​ UsuarioDAO dao;
    public​ Usuario modelo;
    public int tipoUsuario;
    public int pais;
    
    public​ ControladorUsuario​​(LoginForm pVista, Usuario pModelo, int pTipoUsuario, int pPais){
        vista=pVista​;
        modelo=pModelo;
        tipoUsuario=pTipoUsuario;
        pais=pPais;
        System.out.println("País: "+pais);
        dao=new UsuarioDAOXML();
        this.vista.btIniciarLogin.addActionListener(this);
        this.vista.btCancelarLogin.addActionListener(this);
    }
    
    public void actionPerformed​​(ActionEvent e){
        switch(e.getActionCommand()) {
            case​ "Iniciar logIn":
                logIn();
                break;
            case​ "Cancelar logIn":
                cerrarVentanaLogin();
                break;
            default​:
                break​;
        }
    }
    
    public void logIn(){
        if(vista.logInDatosCorrectos()==true){
            String nombreUsuario = vista.txtNombreUsuario.getText();
            String contraseña = vista.txtContrasena.getText();
            modelo=new Usuario(nombreUsuario, contraseña);
            //Usuario usuarioActual = dao.iniciarSesion(modelo);
            Usuario usuarioActual = cargarUsuario(modelo);
            if(usuarioActual!=null){
                vista.setVisible(true);
                JOptionPane.showMessageDialog(vista, "Bienvenido: " +modelo.getNombre());
                vista.setVisible(true);
                
                switch(tipoUsuario) {
                    case​ 1:
                        ConsultaProductosForm vistaN=new ConsultaProductosForm();
                        ControladorConsultaProductos controladorNenu=new ControladorConsultaProductos(vistaN,modelo,tipoUsuario, pais);
                        controladorNenu.vista.setVisible(true);
                        controladorNenu.vista.setLocationRelativeTo(null);
                        this.vista.dispose();
                        break;
                    case​ 2:
                        
                        break;
                    case 3:
                        AdminProductosForm vistaM=new AdminProductosForm();
                        ControladorAdminProductos controladorMenu=new ControladorAdminProductos(vistaM,modelo,tipoUsuario, pais);
                        controladorMenu.vista.setVisible(true);
                        controladorMenu.vista.setLocationRelativeTo(null);
                        vista.dispose();
                        break;
                    case 4:
                        CompraProductosForm vistaO=new CompraProductosForm();
                        ControladorCompraProducto controladorOenu=new ControladorCompraProducto(vistaO,modelo,tipoUsuario, pais);
                        controladorOenu.vista.setVisible(true);
                        controladorOenu.vista.setLocationRelativeTo(null);
                        this.vista.dispose();
                    default​:
                        break​;
                }
                
                /*MenuForm vistaM=new MenuForm();
                ControladorMenu controladorMenu=new ControladorMenu(vistaM,modelo.getNombre(),modelo.getContrasena());
                controladorMenu.vista.setVisible(true);
                controladorMenu.vista.setLocationRelativeTo(null);
                vista.dispose();*/
            }else{
                JOptionPane.showMessageDialog(vista, "El usuario indicado no existe");
            }
        }else{
            JOptionPane.showMessageDialog(vista, "Todos los datos son requeridos");
        }
    }
    
    public Usuario cargarUsuario(Usuario user){
        try{
            Conexion conexion=new Conexion();
            Connection con=conexion.conectar(pais);
            Statement stmt = con.createStatement();
            CallableStatement param;
            param = con.prepareCall("{call getUsuarios(?)}");
            param.setInt(1, tipoUsuario);
            ResultSet rs = param.executeQuery();
            
            while (rs.next()) {
                System.out.println(rs.getString(1)+"--"+rs.getString(2));
                if(rs.getString(1).equals(user.getNombre()) && rs.getString(2).equals(user.getContrasena())){
                    return user;
                }
            }
            rs.close();
            stmt.close();
            conexion.CerrarConexion(con);
        } catch(Exception e){
            System.out.println("ERROR: "+e);
        }
        
        return null;
    }
    
    public void cerrarVentanaLogin(){
        InicioForm vista=new InicioForm();
        
        ControladorInicio controladorInicio=new ControladorInicio(vista);
        controladorInicio.vista.setVisible(true);
        controladorInicio.vista.setLocationRelativeTo(null);
    }

    private void registrarse() {
        RegistroForm vistaM=new RegistroForm();
        ControladorRegistro controladorRegistro=new ControladorRegistro(modelo,vistaM);
        controladorRegistro.vista.setVisible(true);
        controladorRegistro.vista.setLocationRelativeTo(null);
        vista.dispose();
    }
}
