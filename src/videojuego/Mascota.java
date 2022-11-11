/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videojuego;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import utils.BufferedImages;
import static videojuego.Board.COLUMNS;
import static videojuego.Board.ROWS;

/**
 *
 * @author hoshi
 */
public class Mascota {
  
    // image that represents the player's position on the board
    private BufferedImage image;
    // current position of the player on the board grid
    private Point pos;
    // keep track of the player's score
    private int score;
    
    public int currentLv;

    public Mascota(int currentLv) {
        // load the assets
        loadImage();

        // initialize the state
        pos = new Point( COLUMNS /2,  ROWS);
        score = 0;
        this.currentLv = currentLv;
//        user.getUsuario();
    }

    private void loadImage() {
        try {
            // you can use just the filename if the image file is in your
            // project folder, otherwise you need to provide the file path.
            image = ImageIO.read(new File("src/img/mascota.png"));
            image = BufferedImages.resize(image, 50, 50);
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
    }

    public void draw(Graphics g, ImageObserver observer) {
        // with the Point class, note that pos.getX() returns a double, but 
        // pos.x reliably returns an int. https://stackoverflow.com/a/30220114/4655368
        // this is also where we translate board grid position into a canvas pixel
        // position by multiplying by the tile size.
        g.drawImage(
            image, 
            pos.x * Board.TILE_SIZE, 
            pos.y * Board.TILE_SIZE, 
            observer
        );
    }
    
    public void keyPressed(KeyEvent e) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        // every keyboard get has a certain code. get the value of that code from the
        // keyboard event so that we can compare it to KeyEvent constants
        int key = e.getKeyCode();
        
        // depending on which arrow key was pressed, we're going to move the player by
        // one whole tile for this input
        if (key == KeyEvent.VK_UP) {
            pos.translate(0, -1);
            image = ImageIO.read(new File("src/img/icon.png"));
            image = BufferedImages.resize(image, 50, 50);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/img/caminar.mp3").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            
            
        }
        if (key == KeyEvent.VK_RIGHT) {
            pos.translate(1, 0);
            image = ImageIO.read(new File("src/img/Recycle_Bin_Empty.png"));
            image = BufferedImages.resize(image, 50, 50);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/img/bark.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        }
        if (key == KeyEvent.VK_DOWN) {
            pos.translate(0, 1);
            image = ImageIO.read(new File("src/img/icon.png"));
            image = BufferedImages.resize(image, 50, 50);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/img/bark.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        }
        if (key == KeyEvent.VK_LEFT) {
            pos.translate(-1, 0);
            image = ImageIO.read(new File("src/img/r2d2.png"));
            image = BufferedImages.resize(image, 50, 50);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/img/bark.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        }
        
         
    }

    public void tick() {
        // this gets called once every tick, before the repainting process happens.
        // so we can do anything needed in here to update the state of the player.

        // prevent the player from moving off the edge of the board sideways
        if (pos.x < 0) {
            pos.x = 0;
        } else if (pos.x >= Board.COLUMNS) {
            pos.x = Board.COLUMNS - 1;
        }
        // prevent the player from moving off the edge of the board vertically
        if (pos.y < 0) {
            pos.y = 0;
        } else if (pos.y >= Board.ROWS) {
            pos.y = Board.ROWS - 1;
        }
    }

    public String getScore() {
        return String.valueOf(score);
    }
    
//    public String getUserName(User user){
//        return user.getUsuario();
//    }

    public void addScore(int amount) {
        score += amount;
    }

    public Point getPos() {
        return pos;
    }

}
