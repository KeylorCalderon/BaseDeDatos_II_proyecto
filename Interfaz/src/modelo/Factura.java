/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.awt.Image;
import java.sql.Blob;
import java.sql.Date;
import javax.swing.ImageIcon;

/**
 *
 * @author 1001001222
 */
public class Factura {
    public int ID;
    public String NombreCliente;
    public String NombreBebida;
    public String TipoDePago;
    public String Sucursal;
    public int Cantidad;
    public float subtotal;
    public Date Fecha;
    public int envio;
    
    public Factura(){
        
    }
}
