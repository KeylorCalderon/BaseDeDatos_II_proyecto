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
/**
 *
 * @author 1001001222
 */
public class ControladorInicio implements ActionListener{
    public​ InicioForm vista;
    
    public​ ControladorInicio(InicioForm pVista){
        vista=pVista​;
        this.vista.btAdmin.addActionListener(this);
        this.vista.btConsulta.addActionListener(this);
        this.vista.btFactura.addActionListener(this);
        this.vista.btCliente.addActionListener(this);
    }
    
    public void actionPerformed​​(ActionEvent e){
        int i=0;
        switch(e.getActionCommand()) {
            case​ "Administrador":
                i=3;
                break;
            case​ "Consulta":
                i=1;
                break;
            case​ "Factura":
                i=2;
                break;
            case​ "Cliente":
                i=4;
                break;
            default​:
                break​;
        }
        LoginForm vista=new LoginForm();
        Usuario modelo = new Usuario();
        ControladorUsuario controladorUsuario=new ControladorUsuario(vista, modelo, i, this.vista.pais.getSelectedIndex());
        controladorUsuario.vista.setVisible(true);
        controladorUsuario.vista.setLocationRelativeTo(null);
        this.vista.dispose();
    }
}
