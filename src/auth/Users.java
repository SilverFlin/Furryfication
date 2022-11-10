/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auth;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Toled
 */
public class Users {
    
    public void writeUser(User user) {
        ArrayList<User> tempUsers = readUser();
        if(tempUsers == null) tempUsers = new ArrayList<>();
        tempUsers.add(user);
        try {
            FileOutputStream writeData = new FileOutputStream("users.ser");
            try (ObjectOutputStream writeStream = new ObjectOutputStream(writeData)) {
                writeStream.writeObject(tempUsers);
                writeStream.flush();
            }
        } catch (IOException e) {}
    }
    
    public ArrayList<User> readUser() {

        try {
            FileInputStream readData = new FileInputStream("users.ser");
            ArrayList<User> user;
            try (ObjectInputStream readStream = new ObjectInputStream(readData)) {
                user = (ArrayList<User>) readStream.readObject();
            }
            return user;
        } catch (IOException | ClassNotFoundException e) {}

        return null;
    }
    
    public void deleteUsers() {
        File myObj = new File("users.ser");
        if (myObj.delete()) {
            System.out.println("Deleted the file: " + myObj.getName());
        } else {
            System.out.println("Failed to delete the file.");
        }
    }
    public void deleteUser(User userToDelete){
        ArrayList<User> users = this.readUser();
        for (int i = 0; i < users.size(); i++) {
            if(users.get(i).getEmail().equals(userToDelete.getEmail())){
                users.remove(users.get(i));
            }
        }
        this.deleteUsers();
        this.addUsers(users);
    }

    public User findUser(User user) {
        ArrayList<User> allUsers = this.readUser();
        
        if(allUsers == null) return null;
        
        
        for (User u : allUsers) {
            if(u.getEmail().equals(user.getEmail())){
                return u;
            }
        }
        return null;
    }
     public User findByUser(String username) {
        ArrayList<User> allUsers = this.readUser();
        
        if(allUsers == null) return null;
        
        
        for (User u : allUsers) {
            if(u.getUsuario().equals(username)){
                return u;
            }
        }
        return null;
    }
    
    public User findEmail(User user) {
        ArrayList<User> allUsers = this.readUser();
        
        if(allUsers == null) return null;
        
        
        for (User u : allUsers) {
            if(u.getEmail().equals(user.getEmail())){
                return u;
            }
        }
        return null;
    }
    
    public User existentUser(String username){
        
        ArrayList<User> allUsers = this.readUser();
        
        if(allUsers == null) return null;
                
        for (User u : allUsers) {
            if(u.getUsuario().equals(username)) return u;
        }
        return null;
    }

    private void addUsers(ArrayList<User> users) {
        for (User user : users) this.writeUser(user);
    }   
}
