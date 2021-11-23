/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author 1001001222
 */
public class Usuario {
    private String nombre;
    private String contrasena;
    
    public Usuario(){
        
    }
    
    public Usuario(String pNombre, String pContrasena){
        nombre=pNombre;
        contrasena=pContrasena;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public String getContrasena(){
        return contrasena;
    }
    
    public void setNombre(String pNombre){
        nombre=pNombre;
    }
    
    public void setContrasena(String pContrasena){
        contrasena=pContrasena;
    }
    
    public boolean equals(Usuario comp){
        if(this.getNombre().equals(comp.getNombre()) && this.getContrasena().equals(comp.getContrasena())){
            return true;
        }else{
            return false;
        }
    }
}
