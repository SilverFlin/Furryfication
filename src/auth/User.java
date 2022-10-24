/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auth;

import java.io.Serializable;
import java.util.Arrays;

/**
 *
 * @author Toled
 */
public class User implements Serializable{
    private String usuario;
    private String contrasena;
    private String nombre;
    private String email;

    public User(String usuario, String contrasena, String nombre, String email) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.email = email;
    }

    public boolean verifyPassword(String pass) {
        return Arrays.toString(pass.split("")).equals(this.contrasena);
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
    
    
    
    @Override
    public String toString() {
        return "User{" + "usuario=" + usuario + ", nombre=" + nombre + ", email=" + email + '}';
    }
    
    
    
}
