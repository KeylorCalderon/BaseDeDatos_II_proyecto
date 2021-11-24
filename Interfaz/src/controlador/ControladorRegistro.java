/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.UsuarioDAO;
import dao.UsuarioDAOXML;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import modelo.Producto;
import modelo.Usuario;
import vista.AdminProductosForm;
import vista.AgregarProductoForm;
import vista.InicioForm;
import vista.LoginForm;
import vista.RegistroForm;

public class ControladorRegistro implements ActionListener{
    public​ RegistroForm vista;
    public​ Usuario modelo;
    public int tipoUsuario;
    public int pais;
    public ArrayList<Producto> productos=new ArrayList<Producto>();
    Image mImagen;
    ImageIcon mIcono;
    File image;
    LocalDate fechaActual;
    
    public​ ControladorRegistro(RegistroForm pVista, Usuario pModelo, int pTipoUsuario, int pPais){
        vista=pVista​;
        modelo=pModelo;
        tipoUsuario=pTipoUsuario;
        pais=pPais;
        //System.out.println("País: "+pais);
        this.vista.btRegistrar.addActionListener(this);
        this.vista.btImagen.addActionListener(this);
        this.vista.btVolver.addActionListener(this);
        this.vista.mes.addActionListener(this);
        //cargarDesplegables();
        cargarAnoYMes();
        
        fechaActual = LocalDate.now();
        System.out.println(fechaActual);
        fechaActual=fechaActual.minusYears(17);
        System.out.println(fechaActual);
        //cargarSQL();
    }
    
    public void cargarAnoYMes(){
        this.vista.ano.removeAllItems();
        this.vista.mes.removeAllItems();
        for(int i=1930; i<2022; i++){
            this.vista.ano.addItem(Integer.toString(i));
        }
        this.vista.mes.addItem("Enero");
        this.vista.mes.addItem("Febrero");
        this.vista.mes.addItem("Marzo");
        this.vista.mes.addItem("Abril");
        this.vista.mes.addItem("Mayo");
        this.vista.mes.addItem("Junio");
        this.vista.mes.addItem("Julio");
        this.vista.mes.addItem("Agosto");
        this.vista.mes.addItem("Septiembre");
        this.vista.mes.addItem("Octubre");
        this.vista.mes.addItem("Noviembre");
        this.vista.mes.addItem("Diciembre");
    }
    
    public void cargarDia(){
        this.vista.dia.removeAllItems();
        int cantDias=0;
        switch(this.vista.mes.getSelectedIndex()) {
            case 0:
                cantDias=31;
                break;
            case 1:
                cantDias=28;
                break;
            case 2:
                cantDias=31;
                break;
            case 3:
                cantDias=30;
                break;
            case 4:
                cantDias=31;
                break;
            case 5:
                cantDias=30;
                break;
            case 6:
                cantDias=31;
                break;
            case 7:
                cantDias=31;
                break;
            case 8:
                cantDias=30;
                break;
            case 9:
                cantDias=31;
                break;
            case 10:
                cantDias=30;
                break;
            case 11:
                cantDias=31;
                break;
        }
        for(int i=0; i<cantDias; i++){
            this.vista.dia.addItem(Integer.toString(i+1));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
            case​ "Registrarse":
                try{
                    String dia=this.vista.dia.getSelectedItem().toString();
                    if(Integer.parseInt(dia)<=9){
                        dia="0"+dia;
                    }
                    String mes=Integer.toString((this.vista.mes.getSelectedIndex()+1));
                    if(Integer.parseInt(mes)<=9){
                        mes="0"+mes;
                    }
                    
                    String nacimiento=this.vista.ano.getSelectedItem().toString()+"-"+mes+"-"+dia;
                    
                    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate date = LocalDate.parse(nacimiento,df);
                    
                    Period period = Period.between(date, fechaActual);

                    if(period.getYears()>0){
                        System.out.println("Mayor de edad: "+period.getYears()+" "+fechaActual+"--"+date);
                        String user=this.vista.usuario.getText();
                        String contra=this.vista.contrasena.getText();
                        String nombre=this.vista.nombre.getText();
                        String apellidos=this.vista.apellido.getText();
                        int telefono=Integer.parseInt(this.vista.telefono.getText());
                        String correo=this.vista.correo.getText();
                        //ImageIcon mIcono

                        Conexion conexion=new Conexion();
                        Connection con=conexion.conectar(pais);
                        Statement stmt = con.createStatement();
                        CallableStatement param;
                        param = con.prepareCall("{call registrar(?,?,?,?,?,?,?)}");
                        param.setString(1, user);
                        param.setString(2, contra);
                        param.setString(3, nombre);
                        param.setString(4, apellidos);
                        param.setInt(5, telefono);
                        param.setString(6, correo);
                        param.setString(7, nacimiento);


                        param.executeUpdate();
                        System.out.println("++++++++++++++++++++");
                        stmt.close();
                        conexion.CerrarConexion(con);
                        
                        
                        JOptionPane.showMessageDialog(vista, "Registro completado");
                        cerrarVentana();
                    }else{
                        System.out.println("Menor de edad: "+period.getYears()+" "+fechaActual+"--"+date);
                        JOptionPane.showMessageDialog(vista, "Necesita ser mayor de edad para usar este servicio");
                    }
                }catch(Exception ex){
                    System.out.println("ERROR: "+ex);
                    JOptionPane.showMessageDialog(vista, "Hay un error en los datos");
                }
                break;
            case​ "Volver":
                cerrarVentana();
                break;
            case​ "Imagen":
                String Ruta = "";
                JFileChooser jFileChooser = new JFileChooser();
                FileNameExtensionFilter filtrado = new FileNameExtensionFilter("JGP, PNG & GIF", "jpg", "png", "gif");
                jFileChooser.setFileFilter(filtrado);

                int respuesta = jFileChooser.showOpenDialog(this.vista);

                if (respuesta == JFileChooser.APPROVE_OPTION) {
                    Ruta = jFileChooser.getSelectedFile().getPath();

                    image = new File(Ruta);
                    //mImagen = new ImageIcon(Ruta).getImage();
                    //mIcono = new ImageIcon(mImagen.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
                    //lblImagen.setIcon(mIcono); 

                }
                break;
            default​:
                cargarDia();
                break​;
        }
    }

    private void cerrarVentana() {
        InicioForm vista=new InicioForm();
        ControladorInicio controladorInicio=new ControladorInicio(vista);
        controladorInicio.vista.setVisible(true);
        controladorInicio.vista.setLocationRelativeTo(null);
        this.vista.dispose();
    }
}
