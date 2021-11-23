/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

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
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import modelo.Producto;
import modelo.Usuario;
import vista.CompraProductosForm;
import vista.ConsultaProductosForm;

/**
 *
 * @author 1001001222
 */
public class ControladorCompraProducto  implements ActionListener{
    public​ CompraProductosForm vista;
    public​ Usuario modelo;
    public int tipoUsuario;
    public int pais;
    public ArrayList<Producto> productos=new ArrayList<Producto>();
    
    public​ ControladorCompraProducto(CompraProductosForm pVista, Usuario pModelo, int pTipoUsuario, int pPais){
        vista=pVista​;
        modelo=pModelo;
        tipoUsuario=pTipoUsuario;
        pais=pPais;
        //System.out.println("País: "+pais);
        this.vista.tabla.setVisible(false);
        this.vista.btConsultar.addActionListener(this);
        this.vista.btVolver.addActionListener(this);
        this.vista.BD.setText(Integer.toString(pPais));
        cargarProducto();
        //cargarSQL();
        //cargarTabla();
    }
    
    public void cargarProducto(){
        try{
                Conexion conexion=new Conexion();
                Connection con=conexion.conectar(pais);
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("{call getProductosSimple}");
                while (rs.next()) {
                    this.vista.productos.addItem(rs.getString(1));
                }
                rs.close();
                stmt.close();
                conexion.CerrarConexion(con);
            } catch(Exception e){
                System.out.println("ERROR: "+e);
            }
    }
    
    public void cargarSQL(){
        try{
                ArrayList<Producto> temp=new ArrayList<Producto>();
                Conexion conexion=new Conexion();
                Connection con=conexion.conectar(pais);
                //Statement stmt = con.createStatement();
                
                CallableStatement param;
                param = con.prepareCall("{call getProductosConsultaPorSucursal(?)}");
                param.setString(1, this.vista.productos.getSelectedItem().toString());
                ResultSet rs = param.executeQuery();
                
                //ResultSet rs = stmt.executeQuery("{call getProductosConsultaPorSucursal}");
                while (rs.next()) {
                    Producto producto=new Producto();
                    producto.Nombre=rs.getString(1);
                    
                    InputStream binaryStream = rs.getBinaryStream(2);
                    Image image = ImageIO.read(binaryStream);
                    producto.Foto=image;

                    producto.Sucursal="Sucursal "+rs.getString(3);
                    producto.Precio=rs.getFloat(4);
                    producto.Distancia=rs.getFloat(5);
                    temp.add(producto);
                       //System.out.println(rs.getString(1)+"--"+rs.getBlob(4));
                }
                rs.close();
                param.close();
                conexion.CerrarConexion(con);
                productos=temp;
            } catch(Exception e){
                System.out.println("ERROR: "+e);
            }
    }
    
    public void cargarTabla(){
        DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Nombre");
            model.addColumn("Foto");
            model.addColumn("Sucursal");
            model.addColumn("Precio");
            model.addColumn("Distancia");
            model.addColumn("Comprar");
            model.addColumn("Ver");
            Object[] columna = new Object[7];
            for(int i=0; i<productos.size(); i++){
                //try {
                    columna[0]=productos.get(i).Nombre;

                    columna[1]=new JLabel(new ImageIcon(productos.get(i).Foto));
                    columna[2]=productos.get(i).Sucursal;
                    columna[3]=productos.get(i).Precio;
                    columna[4]=productos.get(i).Distancia;

                    JButton boton = new JButton("Comprar");
                    boton.setSize(25,45);
                    boton.setVisible(true);
                    columna[5]=boton;
                    
                    JButton boton2 = new JButton("Ver");
                    boton2.setSize(25,45);
                    boton2.setVisible(true);
                    columna[6]=boton2;
                    
                    
                    model.addRow(columna);

            }
            vista.tabla.setDefaultRenderer(Object.class, new ImgTabla());
            vista.tabla.setRowHeight(50);
            vista.tabla.setModel(model);
            //vista.tablaProductos.setValueAt(icon, 1, 3);
    }
    
    public void eliminar(){
        System.out.println("ELIMINAR");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
            case​ "Consultar":
                this.vista.tabla.setVisible(true);
                cargarSQL();
                cargarTabla();
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
