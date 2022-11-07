/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Toled
 */
public class Validaciones {
    
    
    public static boolean validateEmail(String email){
        // Regex
        String regex =  "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        
        // Compilar regex 
        Pattern p = Pattern.compile(regex); 
  
        // Entrada vacia 
        if (email == null) return false;  
  
        // Generar el Matcher
        Matcher m = p.matcher(email); 
  
        // Return: si coincide con el patron
        return m.matches();
    }
     public static boolean validatePassword(String password){
        // Regex: Min 8 car. 1 letra 1 num 
        String regex =  "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";
        
        // Compilar regex 
        Pattern p = Pattern.compile(regex); 
  
        // Entrada vacia 
        if (password == null) return false;  
  
        // Generar el Matcher
        Matcher m = p.matcher(password); 
  
        // Return: si coincide con el patron
        return m.matches();
    }
}
