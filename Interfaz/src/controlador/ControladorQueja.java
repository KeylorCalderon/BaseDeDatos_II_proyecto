/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import modelo.Producto;
import modelo.Usuario;
import vista.AdminProductosForm;
import vista.AgregarProductoForm;
import vista.CompraProductosForm;
import vista.QuejaForm;

/**
 *
 * @author 1001001222
 */
public class ControladorQueja implements ActionListener{
    public​ QuejaForm vista;
    public​ Usuario modelo;
    public int tipoUsuario;
    public int pais;
    public ArrayList<Producto> productos=new ArrayList<Producto>();
    Image mImagen;
    ImageIcon mIcono;
    File image;
    
    public​ ControladorQueja(QuejaForm pVista, Usuario pModelo, int pTipoUsuario, int pPais){
        vista=pVista​;
        modelo=pModelo;
        tipoUsuario=pTipoUsuario;
        pais=pPais;
        //System.out.println("País: "+pais);
        this.vista.btAgregar.addActionListener(this);
        this.vista.btVolver.addActionListener(this);
        this.vista.tipo.addActionListener(this);
        this.vista.jLabel5.setVisible(false);
        this.vista.empleados.setVisible(false);
        cargarDesplegables();
        //cargarSQL();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
            case​ "Enviar":
                try{
                    String comentario=this.vista.comentario.getText();
                    int tipoID=this.vista.tipo.getSelectedIndex()+1;
                    int empleadoID=this.vista.empleados.getSelectedIndex()+1;
                    
                    Conexion conexion=new Conexion();
                    Connection con=conexion.conectar(pais);
                    Statement stmt = con.createStatement();
                    CallableStatement param;
                    param = con.prepareCall("{call comentarios(?,?,?,?)}");
                    param.setString(1, comentario);
                    param.setInt(2, tipoID);
                    param.setInt(3, 1);
                    param.setInt(4, empleadoID);
                    
                    
                    param.executeUpdate();
                    System.out.println("++++++++++++++++++++");
                    stmt.close();
                    conexion.CerrarConexion(con);
                    
                    JOptionPane.showMessageDialog(vista, "Comentario añadido");
                    cerrarVentana();
                }catch(Exception ex){
                    System.out.println("ERROR: "+ex);
                    JOptionPane.showMessageDialog(vista, "Hay un error en los datos");
                }
                break;
            case​ "Volver":
                cerrarVentana();
            default​:
                if(this.vista.tipo.getSelectedIndex()==2){
                    this.vista.jLabel5.setVisible(true);
                    this.vista.empleados.setVisible(true);
                }else{
                    this.vista.jLabel5.setVisible(false);
                    this.vista.empleados.setVisible(false);
                }
                break​;
        }
    }

    private void cargarDesplegables() {
        try{
           Conexion conexion=new Conexion();
            Connection con=conexion.conectar(pais);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("{call getEmpleadosSimple}");
            while (rs.next()) {
                this.vista.empleados.addItem(rs.getString(1)+" "+rs.getString(2));
                   //System.out.println(rs.getString(1)+"--"+rs.getBlob(4));
            }
            rs.close();
            stmt.close();
            
            conexion.CerrarConexion(con);
        } catch(Exception e){
            System.out.println("ERROR: "+e);
        }
    }

    private void cerrarVentana() {
        CompraProductosForm vistaO=new CompraProductosForm();
        ControladorCompraProducto controladorOenu=new ControladorCompraProducto(vistaO,modelo,4, pais);
        controladorOenu.vista.setVisible(true);
        controladorOenu.vista.setLocationRelativeTo(null);
        this.vista.dispose();
    }
}
