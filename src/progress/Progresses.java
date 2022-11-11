/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progress;

import auth.User;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 *
 * @author Toled
 */
public class Progresses {
    public void writeProgress(Progress progress) {
        ArrayList<Progress> tempProgress = readProgress();
        if(tempProgress == null) tempProgress = new ArrayList<>();
        tempProgress.add(progress);
        try {
            FileOutputStream writeData = new FileOutputStream("progress.ser");
            try (ObjectOutputStream writeStream = new ObjectOutputStream(writeData)) {
                writeStream.writeObject(tempProgress);
                writeStream.flush();
            }
        } catch (IOException e) {}
    }
    
    public ArrayList<Progress> readProgress() {

        try {
            FileInputStream readData = new FileInputStream("progress.ser");
            ArrayList<Progress> progress;
            try (ObjectInputStream readStream = new ObjectInputStream(readData)) {
                progress = (ArrayList<Progress>) readStream.readObject();
            }
            return progress;
        } catch (IOException | ClassNotFoundException e) {}

        return null;
    }
}
