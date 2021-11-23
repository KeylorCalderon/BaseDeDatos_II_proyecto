/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import modelo.Producto;
import modelo.Usuario;
import vista.AdminProductosForm;
import vista.EditarProductoForm;

/**
 *
 * @author 1001001222
 */
public class ControladorEditarProducto implements ActionListener{
    public​ EditarProductoForm vista;
    public​ Usuario modelo;
    public int tipoUsuario;
    public int pais;
    public String producto;
    public Image Foto;
    public float precio;
    
    public​ ControladorEditarProducto(EditarProductoForm pVista, String pProducto, int pPais){
        vista=pVista​;
        pais=pPais;
        producto=pProducto;
        //System.out.println("País: "+pais);
        //this.vista.actualizad.addActionListener(this);
        //this.vista.BD.setText(Integer.toString(pPais));
        cargarSQL();
        //cargarTabla();
        this.vista.Licor.setText(producto);
        this.vista.PrecioActual.setText("Precio actual: "+Float.toString(precio));
        //this.vista.Imagen.setIcon(new ImageIcon(Foto));
        this.vista.Imagen.setIcon(new ImageIcon(Foto.getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
        this.vista.btCambiar.addActionListener(this);
        
    }
    
    public void cargarSQL(){
        try{
            Conexion conexion=new Conexion();
            Connection con=conexion.conectar(pais);
            
            Statement stmt = con.createStatement();
            CallableStatement param;
            param = con.prepareCall("{call getPrecio(?)}");
            param.setString(1, producto);
            ResultSet rs = param.executeQuery();
            rs.next();
            
            producto=rs.getString(1);
            System.out.println("+---------------"+rs.getString(1));
            
            InputStream binaryStream = rs.getBinaryStream(2);
            //System.out.println("+---------------"+rs.getBinaryStream(2));
            Image image = ImageIO.read(binaryStream);
            Foto=image;
            
            precio=rs.getFloat(3);
            System.out.println("+---------------"+rs.getString(3));
            rs.close();
            stmt.close();
            conexion.CerrarConexion(con);
        } catch(Exception e){
            System.out.println("ERROR: "+e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            float precioCambio=Float.parseFloat(this.vista.campoPrecio.getText());
            System.out.println("Float: "+Float.toString(precioCambio));
            Conexion conexion=new Conexion();
            Connection con=conexion.conectar(pais);
            Statement stmt = con.createStatement();
            CallableStatement param;
            param = con.prepareCall("{call setPrecio(?,?)}");
            param.setString(1, producto);
            System.out.println("++++++++++++++++++++");
            param.setFloat(2, precioCambio);
            System.out.println("++++++++++++++++++++");
            param.executeUpdate();
            System.out.println("++++++++++++++++++++");
            stmt.close();
            conexion.CerrarConexion(con);
            JOptionPane.showMessageDialog(vista, "Precio cambiado");
            
            AdminProductosForm vistaM=new AdminProductosForm();
            ControladorAdminProductos controladorMenu=new ControladorAdminProductos(vistaM,modelo,tipoUsuario, pais);
            controladorMenu.vista.setVisible(true);
            controladorMenu.vista.setLocationRelativeTo(null);
            this.vista.dispose();
        }catch(Exception ex){
            System.out.println("Error: "+ex);
            JOptionPane.showMessageDialog(vista, "Ese no es un precio válido");
        }
    }
    
}
