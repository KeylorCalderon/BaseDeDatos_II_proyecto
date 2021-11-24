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
import modelo.Empleado;
import modelo.Producto;
import modelo.Usuario;
import vista.AdminProductosForm;
import vista.AgregarProductoForm;
import vista.ConsultaEmpleadosForm;
import vista.ConsultaProductosForm;
import vista.LoginForm;

/**
 *
 * @author 1001001222
 */
public class ControladorConsultaEmpleados implements ActionListener{
    public​ ConsultaEmpleadosForm vista;
    public​ Usuario modelo;
    public int tipoUsuario;
    public int pais;
    public String sucursal;
    public int sucursalN;
    public ArrayList<Empleado> productos=new ArrayList<Empleado>();
    public String[] puestos={"Consultor","Cajero","Vendedor","Barrendero","Administrador","Vendedor de licores"};
    public String[] salarios={"1029332.94","502392.12","1203942.03","500932.0","130212.09","2192910.00"};
    
    public​ ControladorConsultaEmpleados(ConsultaEmpleadosForm pVista, String pSucursal, int pPais){
        vista=pVista​;
        sucursal=pSucursal;
        sucursalN=Integer.parseInt(sucursal.substring(sucursal.length()-1, sucursal.length()));
        pais=pPais;
        //System.out.println("País: "+pais);
        //this.vista.tabla.setVisible(false);
        //this.vista.btConsultar.addActionListener(this);
        this.vista.btVolver.addActionListener(this);
        this.vista.BD.setText(Integer.toString(pPais));
        this.vista.BD.setVisible(false);
        this.vista.tabla.setVisible(true);
        cargarSQL();
        cargarTabla();
    }
    
    public void cargarSQL(){
        try{
                ArrayList<Empleado> temp=new ArrayList<Empleado>();
                Conexion conexion=new Conexion();
                Connection con=conexion.conectar(pais);
                //Statement stmt = con.createStatement();
                
                CallableStatement param;
                param = con.prepareCall("{call getEmpleados(?)}");
                param.setInt(1, sucursalN);
                ResultSet rs = param.executeQuery();
                
                //ResultSet rs = stmt.executeQuery("{call getProductosConsultaPorSucursal}");
                while (rs.next()) {
                    Empleado producto=new Empleado();
                    producto.Nombre=rs.getString(1);
                    System.out.println(rs.getString(1));
                    producto.Apellido=rs.getString(2);
                    producto.telefono=rs.getInt(3);
                    producto.correo=rs.getString(4);
                    
                    InputStream binaryStream = rs.getBinaryStream(5);
                    Image image = ImageIO.read(binaryStream);
                    producto.Foto=image;
                    temp.add(producto);
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
            model.addColumn("Apellidos");
            model.addColumn("Telefono");
            model.addColumn("Correo");
            model.addColumn("Foto");
            model.addColumn("Salario");
            model.addColumn("Puesto");
            Object[] columna = new Object[7];
            System.out.println("HERE: "+productos.size());
            for(int i=0; i<productos.size(); i++){
                System.out.println("HERE2");
                //try {
                    columna[0]=productos.get(i).Nombre;
                    columna[1]=productos.get(i).Apellido;
                    columna[2]=productos.get(i).telefono;
                    columna[3]=productos.get(i).correo;

                    columna[4]=new JLabel(new ImageIcon(productos.get(i).Foto));
                    columna[5]=salarios[i];
                    columna[6]=puestos[i];
                    
                    
                    model.addRow(columna);

            }
            vista.tabla.setDefaultRenderer(Object.class, new ImgTabla());
            vista.tabla.setRowHeight(150);
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
                ConsultaProductosForm vistaN=new ConsultaProductosForm();
                ControladorConsultaProductos controladorNenu=new ControladorConsultaProductos(vistaN,modelo,tipoUsuario, pais);
                controladorNenu.vista.setVisible(true);
                controladorNenu.vista.setLocationRelativeTo(null);
                this.vista.dispose();
                break;
            default​:
                break​;
        }
        //vista.tablaProductos.removeColumn(vista.tablaProductos.getColumnModel().getColumn(1));
        //cargarTabla();
    }
    
}
