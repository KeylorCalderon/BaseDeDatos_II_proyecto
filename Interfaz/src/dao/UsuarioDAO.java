/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import java.util.*;
import modelo.Usuario;
/**
 *
 * @author 1001001222
 */
public abstract class UsuarioDAO {
    public abstract Usuario iniciarSesion(Usuario usuario);
    public abstract void restaurarContraseña(String nombreUsuario);
    public abstract ArrayList<Usuario> cargarListaUsuarios();
    public abstract boolean cambiarContraseña(String nombreUsuario, String contrasena, String contrasenaNueva);
    public abstract boolean registrarUsuario(Usuario usuario);
}
