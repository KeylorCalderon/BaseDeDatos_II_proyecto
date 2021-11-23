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
import javax.swing.JOptionPane;
import modelo.Usuario;
import vista.LoginForm;
import vista.RegistroForm;

public class ControladorRegistro implements ActionListener{
    Usuario modelo;
    RegistroForm vista;
    UsuarioDAO dao;
    
    public ControladorRegistro(Usuario pModelo, RegistroForm vistaM){
        modelo=pModelo;
        vista=vistaM;
        dao=new UsuarioDAOXML();
        this.vista.btRegistro.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(vista.txtContra.getText().isEmpty()==true || vista.txtName.getText().isEmpty()==true){
            JOptionPane.showMessageDialog(vista, "Todos los datos son requeridos");
        }else{
            registrar();
        }
    }
    
    private void registrar() {
        Usuario user=new Usuario(vista.txtName.getText(),vista.txtContra.getText());
        if(dao.registrarUsuario(user)==true){
            JOptionPane.showMessageDialog(vista, "Usuario registrado con exito");
        }else{
            JOptionPane.showMessageDialog(vista, "Ya existe un usuario con ese nombre");
        }
        LoginForm vista=new LoginForm();
        Usuario modelo = new Usuario();
        ControladorUsuario controladorUsuario=new ControladorUsuario(vista, modelo,0,0);
        controladorUsuario.vista.setVisible(true);
        controladorUsuario.vista.setLocationRelativeTo(null);
        this.vista.dispose();
    }
    
}
