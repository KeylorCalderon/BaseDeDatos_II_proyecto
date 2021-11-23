/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.Sala;
import vista.MenuForm;
import vista.SalasForm;

/**
 *
 * @author 1001001222
 */
public class ControladorSalas implements ActionListener{
    public​ SalasForm vista;
    public​ SalaDAO dao;
    public​ Sala modelo;
    private String nombre;
    private String contrasena;
    
    public ControladorSalas(SalasForm pVista, Sala pModelo, String pNombre, String pContrasena){
        nombre=pNombre;
        contrasena=pContrasena;
        vista=pVista;
        modelo=pModelo;
        dao=new SalaDAOXML();
        this.vista.btSalir.addActionListener(this);
        this.vista.btRegistrar.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
            case​ "Registrar":
                Registrar();
                break;
            case​ "Salir":
                cerrarVentana();
                break;
            default​:
                break​;
        }
    }

    private void Registrar() {
        if(this.vista.salaDatosCorrectos()){
            if(this.vista.salaDatosValidos()){
                String identificador = vista.txtIdentificador.getText();
                String ubicacion = vista.txtUbicacion.getText();
                int capacidad=Integer.parseInt(vista.txtCapacidad.getText());
                modelo= new Sala(identificador,ubicacion,capacidad);
                if(dao.registrarSala(modelo)){
                    JOptionPane.showMessageDialog(vista, "Sala "+identificador+" registrada con exito");
                    Sala modeloS=new Sala();
                    SalasForm vistaS=new SalasForm();
                    ControladorSalas controladorSalas=new ControladorSalas(vistaS,modeloS,nombre,contrasena);
                    controladorSalas.vista.setVisible(true);
                    controladorSalas.vista.setLocationRelativeTo(null);
                    this.vista.dispose();
                }else{
                    JOptionPane.showMessageDialog(vista, "Ya existe una sala con ese identificador");
                }
            }else{
                JOptionPane.showMessageDialog(vista, "Es necesario que dijiste datos validos");
            }
        }else{
            JOptionPane.showMessageDialog(vista, "Todos los datos son requeridos");
        }
    }

    private void cerrarVentana() {
        MenuForm vistaM=new MenuForm();
        ControladorMenu controladorMenu=new ControladorMenu(vistaM,nombre,contrasena);
        vista.abrirVentanaAnterior(controladorMenu.vista);
    }
    
}
