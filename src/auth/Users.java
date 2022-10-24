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

    public User findUser(User user) {
        ArrayList<User> allUsers = this.readUser();
        
        if(allUsers == null) return null;
        
        
        for (User u : allUsers) {
            if(u.getNombre().equals(user.getNombre())){
                return u;
            }
        }
        return null;
    }
    
}