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
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import modelo.Producto;
import modelo.Usuario;
import vista.AdminProductosForm;
import vista.AgregarProductoForm;
import vista.InicioForm;
import vista.LoginForm;

/**
 *
 * @author 1001001222
 */
public class ControladorAdminProductos implements ActionListener{
    public​ AdminProductosForm vista;
    public​ Usuario modelo;
    public int tipoUsuario;
    public int pais;
    public ArrayList<Producto> productos=new ArrayList<Producto>();
    
    public​ ControladorAdminProductos(AdminProductosForm pVista, Usuario pModelo, int pTipoUsuario, int pPais){
        vista=pVista​;
        modelo=pModelo;
        tipoUsuario=pTipoUsuario;
        pais=pPais;
        //System.out.println("País: "+pais);
        this.vista.actualizad.addActionListener(this);
        this.vista.volver.addActionListener(this);
        this.vista.btAgregar.addActionListener(this);
        this.vista.BD.setText(Integer.toString(pPais));
        cargarSQL();
        cargarTabla();
    }
    
    public void cargarSQL(){
        if(this.vista.modoFacturas.isSelected()){
            try{
                ArrayList<Producto> temp=new ArrayList<Producto>();
                Conexion conexion=new Conexion();
                Connection con=conexion.conectar(pais);
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("{call getProductos}");
                while (rs.next()) {
                    Producto producto=new Producto();
                    producto.Nombre=rs.getString(1);
                    producto.Anos=rs.getInt(2);
                    producto.TipoAnejado=rs.getString(3);
                    //producto.Foto=rs.getBlob(4);

                    InputStream binaryStream = rs.getBinaryStream(4);
                    Image image = ImageIO.read(binaryStream);
                    producto.Foto=image;

                    producto.Sucursal="Sucursal "+rs.getString(5);
                    producto.Cantidad=rs.getInt(6);
                    producto.Precio=rs.getFloat(7);
                    producto.Fecha=rs.getDate(8);
                    producto.Pais=rs.getString(9);
                    temp.add(producto);
                       //System.out.println(rs.getString(1)+"--"+rs.getBlob(4));
                }
                rs.close();
                stmt.close();
                conexion.CerrarConexion(con);
                productos=temp;
            } catch(Exception e){
                System.out.println("ERROR: "+e);
            }
        }else{
            try{
                ArrayList<Producto> temp=new ArrayList<Producto>();
                Conexion conexion=new Conexion();
                Connection con=conexion.conectar(pais);
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("{call getProductosSinFactura}");
                while (rs.next()) {
                    Producto producto=new Producto();
                    producto.Nombre=rs.getString(1);
                    producto.Anos=rs.getInt(2);
                    producto.TipoAnejado=rs.getString(3);
                    //producto.Foto=rs.getBlob(4);

                    InputStream binaryStream = rs.getBinaryStream(4);
                    Image image = ImageIO.read(binaryStream);
                    producto.Foto=image;

                    producto.Sucursal="Sucursal "+rs.getString(5);
                    producto.Cantidad=rs.getInt(6);
                    producto.Precio=rs.getFloat(7);
                    producto.Pais=rs.getString(8);
                    temp.add(producto);
                       //System.out.println(rs.getString(1)+"--"+rs.getBlob(4));
                }
                rs.close();
                stmt.close();
                conexion.CerrarConexion(con);
                productos=temp;
            } catch(Exception e){
                System.out.println("ERROR: "+e);
            }
        }
    }
    
    public void cargarTabla(){
        if(this.vista.modoFacturas.isSelected()){
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Nombre");
            model.addColumn("Años");
            model.addColumn("Tipo de añejado");
            model.addColumn("Foto");
            model.addColumn("Sucursal");
            model.addColumn("Cantidad");
            model.addColumn("Precio");
            model.addColumn("Fecha");
            model.addColumn("Pais de origen");
            model.addColumn("Editar");
            Object[] columna = new Object[10];
            for(int i=0; i<productos.size(); i++){
                //try {
                    columna[0]=productos.get(i).Nombre;
                    columna[1]=productos.get(i).Anos;
                    columna[2]=productos.get(i).TipoAnejado;


                    //JLabel lblFoto = new JLabel();
                    //lblFoto.setIcon(new ImageIcon(productos.get(i).Foto));
                    //icon=new ImageIcon(productos.get(i).Foto);

                    columna[3]=new JLabel(new ImageIcon(productos.get(i).Foto));
                    columna[4]=productos.get(i).Sucursal;
                    columna[5]=productos.get(i).Cantidad;
                    columna[6]=productos.get(i).Precio;
                    columna[7]=productos.get(i).Fecha;
                    columna[8]=productos.get(i).Pais;

                    JButton boton = new JButton("Editar precio");
                    boton.setSize(25,45);
                    boton.setVisible(true);
                    /*ActionListener listener = new ActionListener(){ 
                       public void actionPerformed(ActionEvent e) {
                           System.out.println(e.getID());
                         eliminar(); //lo declaras en otra parte del documento
                       }         
                    };
                    boton.addActionListener(listener);*/

                    columna[9]=boton;
                    model.addRow(columna);
               /* } catch (IOException ex) {
                    Logger.getLogger(ControladorAdminProductos.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorAdminProductos.class.getName()).log(Level.SEVERE, null, ex);
                }*/

            }
            vista.tablaProductos.setDefaultRenderer(Object.class, new ImgTabla());
            vista.tablaProductos.setRowHeight(50);
            vista.tablaProductos.setModel(model);
            //vista.tablaProductos.setValueAt(icon, 1, 3);
        }else{
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Nombre");
            model.addColumn("Años");
            model.addColumn("Tipo de añejado");
            model.addColumn("Foto");
            model.addColumn("Sucursal");
            model.addColumn("Cantidad");
            model.addColumn("Precio");
            model.addColumn("Pais de origen");
            model.addColumn("Editar");
            Object[] columna = new Object[9];
            for(int i=0; i<productos.size(); i++){
                //try {
                    columna[0]=productos.get(i).Nombre;
                    columna[1]=productos.get(i).Anos;
                    columna[2]=productos.get(i).TipoAnejado;


                    //JLabel lblFoto = new JLabel();
                    //lblFoto.setIcon(new ImageIcon(productos.get(i).Foto));
                    //icon=new ImageIcon(productos.get(i).Foto);

                    columna[3]=new JLabel(new ImageIcon(productos.get(i).Foto));
                    columna[4]=productos.get(i).Sucursal;
                    columna[5]=productos.get(i).Cantidad;
                    columna[6]=productos.get(i).Precio;
                    columna[7]=productos.get(i).Pais;

                    JButton boton = new JButton("Editar precio");
                    boton.setSize(25,45);
                    boton.setVisible(true);
                    /*ActionListener listener = new ActionListener(){ 
                       public void actionPerformed(ActionEvent e) {
                           System.out.println(e.getID());
                         eliminar(); //lo declaras en otra parte del documento
                       }         
                    };
                    boton.addActionListener(listener);*/

                    columna[8]=boton;
                    model.addRow(columna);
               /* } catch (IOException ex) {
                    Logger.getLogger(ControladorAdminProductos.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorAdminProductos.class.getName()).log(Level.SEVERE, null, ex);
                }*/

            }
            vista.tablaProductos.setDefaultRenderer(Object.class, new ImgTabla());
            vista.tablaProductos.setRowHeight(50);
            vista.tablaProductos.setModel(model);
            //vista.tablaProductos.setValueAt(icon, 1, 3);
        }
    }
    
    public void eliminar(){
        System.out.println("ELIMINAR");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
            case​ "Actualizar":
                cargarSQL();
                cargarTabla();
                break;
            case​ "Agregar":
                AgregarProductoForm vista=new AgregarProductoForm();
        
                ControladorAgregarProducto controladorInicio=new ControladorAgregarProducto(vista,modelo,tipoUsuario, pais);
                controladorInicio.vista.setVisible(true);
                controladorInicio.vista.setLocationRelativeTo(null);
                this.vista.dispose();
                break;
            case​ "Volver":
                
                break;
            default​:
                break​;
        }
        //vista.tablaProductos.removeColumn(vista.tablaProductos.getColumnModel().getColumn(1));
        //cargarTabla();
    }
    
}
