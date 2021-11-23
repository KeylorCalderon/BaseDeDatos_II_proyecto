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
import modelo.Factura;
import modelo.Producto;
import modelo.Usuario;
import vista.ConsultaProductosForm;
import vista.FacturaForm;
import vista.LoginForm;

/**
 *
 * @author 1001001222
 */
class ControladorFactura implements ActionListener{
    public​ FacturaForm vista;
    public​ Usuario modelo;
    public int tipoUsuario;
    public int pais;
    public ArrayList<Factura> productos=new ArrayList<Factura>();
    
    public​ ControladorFactura(FacturaForm pVista, Usuario pModelo, int pTipoUsuario, int pPais){
        vista=pVista​;
        modelo=pModelo;
        tipoUsuario=pTipoUsuario;
        pais=pPais;
        System.out.println("FACTURACIÓN");
        //this.vista.tabla.setVisible(false);
        //this.vista.btConsultar.addActionListener(this);
        this.vista.btVolver.addActionListener(this);
        this.vista.refrescar.addActionListener(this);
        this.vista.BD.setText(Integer.toString(pPais));
        this.vista.BD.setVisible(false);
        //cargarProducto();
        cargarSQL();
        cargarTabla();
    }
    /*
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
    */
    public void cargarSQL(){
        try{
                ArrayList<Factura> temp=new ArrayList<Factura>();
                Conexion conexion=new Conexion();
                Connection con=conexion.conectar(pais);
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("{call getFacturasPendientes}");
                while (rs.next()) {
                    Factura producto=new Factura();
                    producto.ID=rs.getInt(1);
                    producto.Fecha=rs.getDate(2);
                    producto.NombreCliente=rs.getString(3);
                    producto.Sucursal="Sucursal"+Integer.toString(rs.getInt(4));
                    producto.envio=rs.getInt(5);
                    producto.NombreBebida=rs.getString(6);
                    producto.Cantidad=rs.getInt(7);
                    producto.subtotal=rs.getFloat(8);
                    producto.TipoDePago=rs.getString(9);
                    
                    temp.add(producto);
                       //System.out.println(rs.getString(1)+"--"+rs.getBlob(4));
                }
                rs.close();
                conexion.CerrarConexion(con);
                productos=temp;
            } catch(Exception e){
                System.out.println("ERROR: "+e);
            }
    }
    
    public void cargarTabla(){
        DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Serial factura");
            model.addColumn("Fecha");
            model.addColumn("Nombre cliente");
            model.addColumn("Sucursal");
            model.addColumn("Envio");
            model.addColumn("Bebida");
            model.addColumn("Cantidad");
            model.addColumn("Subtotal");
            model.addColumn("Tipo de pago");
            model.addColumn("");
            Object[] columna = new Object[10];
            for(int i=0; i<productos.size(); i++){
                //try {
                    columna[0]=productos.get(i).ID;
                    columna[1]=productos.get(i).Fecha;
                    columna[2]=productos.get(i).NombreCliente;
                    columna[3]=productos.get(i).Sucursal;
                    
                    if(productos.get(i).envio==1){
                        columna[4]="Sí";
                    }else{
                        columna[4]="No";
                    }
                    columna[5]=productos.get(i).NombreBebida;
                    
                    columna[6]=productos.get(i).Cantidad;
                    columna[7]=productos.get(i).subtotal;
                    columna[8]=productos.get(i).TipoDePago;

                    JButton boton = new JButton("Facturar");
                    boton.setSize(25,45);
                    boton.setVisible(true);
                    columna[9]=boton;
                    
                    
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
            case​ "Refrescar":
                //this.vista.tabla.setVisible(true);
                cargarSQL();
                cargarTabla();
                break;
            case​ "Volver":
                LoginForm vistaL=new LoginForm();
                ControladorUsuario controladorUsuario=new ControladorUsuario(vistaL, modelo, 2, pais);
                controladorUsuario.vista.setVisible(true);
                controladorUsuario.vista.setLocationRelativeTo(null);
                this.vista.dispose();
                break;
            default​:
                break​;
        }
        //vista.tablaProductos.removeColumn(vista.tablaProductos.getColumnModel().getColumn(1));
        //cargarTabla();
    }
    
}
