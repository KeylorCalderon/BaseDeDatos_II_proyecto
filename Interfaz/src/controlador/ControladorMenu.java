/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.UsuarioDAO;
import dao.UsuarioDAOXML;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JOptionPane;
import modelo.Sala;
import modelo.Usuario;
import vista.LoginForm;
import vista.MenuForm;
import vista.SalasForm;

/**
 *
 * @author 1001001222
 */
public class ControladorMenu  implements ActionListener{

    public MenuForm vista;
    private String nombre;
    private String contrasena;
    private UsuarioDAO dao;
    public ControladorMenu(MenuForm pVista,String pNombre, String pContrasena){
        vista=pVista;
        nombre=pNombre;
        contrasena=pContrasena;
        dao=new UsuarioDAOXML();
        this.vista.btRegistrarSala.addActionListener(this);
        this.vista.btCerrarSesion.addActionListener(this);
        this.vista.btCambiarContrasena.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
            case​ "Cambiar contraseña":
                cambiarContrasena();
                break;
            case​ "Registrar salas":
                registrarSala();
                break;
            case​ "Cerrar sesion":
                cerrarVentanaMenu();
                break;
            default​:
                break;
        }
    }

    private void cambiarContrasena() {
        String nueva="";
        Random rnd=new Random();
        for(int i=0;i<7;i++){
            if(i<3){
                nueva+=rnd.nextInt(10);
            }else{
                nueva+=rnd.nextInt(91);
            }
        }
        if(dao.cambiarContraseña(nombre, contrasena, nueva)){
            JOptionPane.showMessageDialog(vista, "Contraseña automatica generada");
        }
    }

    private void registrarSala() {
        Sala modeloS=new Sala();
        SalasForm vistaS=new SalasForm();
        ControladorSalas controladorSalas=new ControladorSalas(vistaS,modeloS,nombre,contrasena);
        controladorSalas.vista.setVisible(true);
        controladorSalas.vista.setLocationRelativeTo(null);
        this.vista.dispose();
        
    }

    private void cerrarVentanaMenu() {
        LoginForm vistaL=new LoginForm();
        Usuario modeloL = new Usuario();
        ControladorUsuario controladorUsuario=new ControladorUsuario(vistaL, modeloL,0,0);
        vista.abrirVentanaAnterior(controladorUsuario.vista);
    }
    
}
