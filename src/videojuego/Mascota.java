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
  
    private BufferedImage petImg;
    private BufferedImage swordImg;
    private BufferedImage currImg;
    private Point pos;
    private Player player;
    

    public Mascota(Player player) {
        loadImage();
        this.player = player;
        pos = new Point( player.getPos().x+1,  player.getPos().y - 1);
        
        try {
            petImg = ImageIO.read(new File("src/img/icon.png"));
            petImg = BufferedImages.resize(petImg, 50, 50);
            swordImg = ImageIO.read(new File("src/img/sword.png"));
            swordImg = BufferedImages.resize(swordImg, 50, 50);
        } catch (IOException ex) {
            Logger.getLogger(Mascota.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void loadImage() {
            this.currImg = petImg;
    }

    public void draw(Graphics g, ImageObserver observer) {
        g.drawImage(
            currImg, 
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
            currImg = swordImg;
        }
         
    }
    
     public void lightsOff(){
        currImg = petImg;
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
