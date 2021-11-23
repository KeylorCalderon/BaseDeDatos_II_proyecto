/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import modelo.Sala;

/**
 *
 * @author 1001001222
 */
public abstract class SalaDAO {
    public abstract boolean registrarSala(Sala sala);
    public abstract ArrayList<Sala> cargarSalas();
}
