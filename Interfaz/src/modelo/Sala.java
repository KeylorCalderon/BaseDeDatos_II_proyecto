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
public class Sala {
    String identificador;
    String ubicacion;
    int capacidad;
    
    public Sala(String pIdentificador, String pUbicacion, int pCapacidad){
        identificador=pIdentificador;
        ubicacion=pUbicacion;
        capacidad=pCapacidad;
        
    }
    
    public Sala(){
        
    }
    
    public String getIdentificador(){
        return identificador;
    }
    
    public String getUbicacion(){
        return ubicacion;
    }
    
    public int getCapacidad(){
        return capacidad;
    }
    
    public void setIdentificador(String pIdentificador){
        identificador=pIdentificador;
    }
    
    public void setUbicacion(String pUbicacion){
        ubicacion=pUbicacion;
    }
    
    public void setCapacidad(int pCapacidad){
        capacidad=pCapacidad;
    }
}
