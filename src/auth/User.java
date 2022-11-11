/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auth;

import java.io.Serializable;
import java.util.ArrayList;
import progress.Progress;

/**
 *
 * @author Toled
 */
public class User implements Serializable{
    private String usuario;
    private String contrasena;
    private String nombre;
    private String email;
    private String topScore;

    public User(String usuario, String contrasena, String nombre, String email) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.email = email;
    }

    public void setTopScore(String topScore) {
        this.topScore = topScore;
    }
    
    
    
    public boolean verifyPassword(String pass) {
        return this.contrasena.equals(pass);
    }

    public String getUsuario() {
        return usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public String toString() {
        return "User{" + "usuario=" + usuario + ", nombre=" + nombre + ", email=" + email +", topScore=" + topScore + '}';
    }
    
    
}
