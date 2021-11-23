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

/**
 *
 * @author 1001001222
 */
public class ControladorAgregarProducto implements ActionListener{
    public​ AgregarProductoForm vista;
    public​ Usuario modelo;
    public int tipoUsuario;
    public int pais;
    public ArrayList<Producto> productos=new ArrayList<Producto>();
    Image mImagen;
    ImageIcon mIcono;
    File image;
    
    public​ ControladorAgregarProducto(AgregarProductoForm pVista, Usuario pModelo, int pTipoUsuario, int pPais){
        vista=pVista​;
        modelo=pModelo;
        tipoUsuario=pTipoUsuario;
        pais=pPais;
        //System.out.println("País: "+pais);
        this.vista.btAgregar.addActionListener(this);
        this.vista.btImagen.addActionListener(this);
        this.vista.btVolver.addActionListener(this);
        cargarDesplegables();
        //cargarSQL();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
            case​ "Agregar":
                try{
                    String procedencia=this.vista.paises.getSelectedItem().toString();
                    int anos=Integer.parseInt(this.vista.anos.getText());
                    String anejado=this.vista.anejado.getSelectedItem().toString();
                    //ImageIcon mIcono
                    int sucursal=this.vista.sucursal.getSelectedIndex();
                    int cant=Integer.parseInt(this.vista.cantidad.getText());
                    float prec=Float.parseFloat(this.vista.precio.getText());
                    String nombre=this.vista.name.getText();
                    
                    
                    Conexion conexion=new Conexion();
                    Connection con=conexion.conectar(pais);
                    Statement stmt = con.createStatement();
                    CallableStatement param;
                    param = con.prepareCall("{call insertarProducto(?,?,?,?,?,?,?,?)}");
                    param.setString(1, procedencia);
                    param.setInt(2, anos);
                    param.setString(3, anejado);
                    
                    FileInputStream  fis ;
                    fis = new FileInputStream(image);
                    param.setBinaryStream(4,fis,(int) (image.length()));
                    
                    param.setInt(5, sucursal);
                    param.setInt(6, cant);
                    param.setFloat(7, prec);
                    param.setString(8, nombre);
                    
                    
                    param.executeUpdate();
                    System.out.println("++++++++++++++++++++");
                    stmt.close();
                    conexion.CerrarConexion(con);
                    
                    JOptionPane.showMessageDialog(vista, "Objeto añadido");
                }catch(Exception ex){
                    System.out.println("ERROR: "+ex);
                    JOptionPane.showMessageDialog(vista, "Hay un error en los datos");
                }
                break;
            case​ "Volver":
                AdminProductosForm vistaM=new AdminProductosForm();
                ControladorAdminProductos controladorMenu=new ControladorAdminProductos(vistaM,modelo,tipoUsuario, pais);
                controladorMenu.vista.setVisible(true);
                controladorMenu.vista.setLocationRelativeTo(null);
                vista.dispose();
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
                break​;
        }
    }

    private void cargarDesplegables() {
        try{
           Conexion conexion=new Conexion();
            Connection con=conexion.conectar(pais);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("{call getPaises}");
            while (rs.next()) {
                this.vista.paises.addItem(rs.getString(1));
                   //System.out.println(rs.getString(1)+"--"+rs.getBlob(4));
            }
            rs.close();
            stmt.close();
            
            Statement stmt2 = con.createStatement();
            ResultSet rs2 = stmt2.executeQuery("{call getAnejado}");
            while (rs2.next()) {
                this.vista.anejado.addItem(rs2.getString(1));
                   //System.out.println(rs.getString(1)+"--"+rs.getBlob(4));
            }
            rs2.close();
            stmt2.close();
            
            Statement stmt3 = con.createStatement();
            ResultSet rs3 = stmt3.executeQuery("{call getSucursal}");
            while (rs3.next()) {
                this.vista.sucursal.addItem("Sucursal"+rs3.getString(1));
                   //System.out.println(rs.getString(1)+"--"+rs.getBlob(4));
            }
            rs3.close();
            stmt3.close();
            conexion.CerrarConexion(con);
        } catch(Exception e){
            System.out.println("ERROR: "+e);
        }
    }
}
