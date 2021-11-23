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
public class Producto {
    public String Nombre;
    public int Anos;
    public String TipoAnejado;
    public Image Foto;
    public String Sucursal;
    public int Cantidad;
    public float Precio;
    public Date Fecha;
    public String Pais;
    public float Distancia;
    
    public Producto(){
        
    }
    
    public Producto(String pNombre, int pAnos, String pTipoAnejado, Image pFoto, String pSucursal, int pCantidad, float pPrecio, Date pFecha, String pPais){
        Nombre=pNombre;
        Anos=pAnos;
        TipoAnejado=pTipoAnejado;
        Foto=pFoto;
        Sucursal=pSucursal;
        Cantidad=pCantidad;
        Precio=pPrecio;
        Fecha=pFecha;
        Pais=pPais;
    }
}
