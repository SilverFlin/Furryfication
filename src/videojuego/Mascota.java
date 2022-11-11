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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import utils.BufferedImages;

/**
 *
 * @author hoshi
 */
public class Mascota {
  
    private BufferedImage image;
    private Point pos;
    private Player player;
    

    public Mascota(Player player) {
        loadImage();
        this.player = player;
        pos = new Point( player.getPos().x+1,  player.getPos().y - 1);
    }

    private void loadImage() {
        try {
            image = ImageIO.read(new File("src/img/Chokis.png"));
            image = BufferedImages.resize(image, 50, 50);
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
    }

    public void draw(Graphics g, ImageObserver observer) {
        g.drawImage(
            image, 
            pos.x * Board.TILE_SIZE, 
            pos.y * Board.TILE_SIZE, 
            observer
        );
    }
    
    public void move(KeyEvent e) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_UP)    pos.translate(0, -1);
        if (key == KeyEvent.VK_RIGHT) pos.translate(1, 0);
        if (key == KeyEvent.VK_DOWN)  pos.translate(0, 1);
        if (key == KeyEvent.VK_LEFT)  pos.translate(-1, 0);
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            image = ImageIO.read(new File("src/img/sable.png"));
            image = BufferedImages.resize(image, 50, 50);
        }
         
    }
    
     public void lightsOff(){
        try {
            image = ImageIO.read(new File("src/img/chokis.png"));
            image = BufferedImages.resize(image, 50, 50);
        } catch (IOException ex) {
            Logger.getLogger(Mascota.class.getName()).log(Level.SEVERE, null, ex);
        } 
     }

    public void tick() {
        if (pos.x < 0) {
            pos.x = 0;
        } else if (pos.x >= Board.COLUMNS) {
            pos.x = Board.COLUMNS - 1;
        }
        if (pos.y < 0) {
            pos.y = 0;
        } else if (pos.y >= Board.ROWS) {
            pos.y = Board.ROWS - 1;
        }
    }

    public Point getPos() {
        return pos;
    }

}
