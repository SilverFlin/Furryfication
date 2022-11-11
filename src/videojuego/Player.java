package videojuego;

import auth.User;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import utils.BufferedImages;
import utils.PlaySound;
import static videojuego.Board.COLUMNS;
import static videojuego.Board.ROWS;

public class Player {

//    User user;
    Mascota mascota; 
    private BufferedImage image;
    private BufferedImage playerL;
    private BufferedImage playerR;
    private BufferedImage currImg;
    private Point pos;
    private int score;
    public int currentLv;

    public Player(int currentLv) {
            loadImage();
            pos = new Point( COLUMNS /2,  ROWS);
            score = 0;
            this.currentLv = currentLv;
            
         try {   
            playerR = ImageIO.read(new File("src/img/PersonajePrincipall.png"));
            playerR = BufferedImages.resize(playerR, 50, 50);
            playerL = ImageIO.read(new File("src/img/PersonajePrincipal11.png"));
            playerL = BufferedImages.resize(playerL, 50, 50);
            currImg = playerR;
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadImage() {
            currImg = playerR;

    }

    public void draw(Graphics g, ImageObserver observer) {
        g.drawImage(
            currImg, 
            pos.x * Board.TILE_SIZE, 
            pos.y * Board.TILE_SIZE, 
            observer
        );
    }
    
    public void keyPressed(KeyEvent e) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_UP) {
            pos.translate(0, -1);
            currImg = playerR;
            
           PlaySound.play("src/img/steps.wav");
            
        }
        if (key == KeyEvent.VK_RIGHT) {
            pos.translate(1, 0);
            currImg = playerR;
            PlaySound.play("src/img/steps.wav");
        }
        if (key == KeyEvent.VK_DOWN) {
            pos.translate(0, 1);
            currImg = playerL;
            PlaySound.play("src/img/steps.wav");
        }
        if (key == KeyEvent.VK_LEFT) {
            pos.translate(-1, 0);
            currImg = playerL;
            PlaySound.play("src/img/steps.wav");
//            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/img/steps.wav").getAbsoluteFile());
//            Clip clip = AudioSystem.getClip();
//            clip.open(audioInputStream);
//            clip.start();
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

    public String getScore() {
        return String.valueOf(score);
    }

    public void addScore(int amount) {
        score += amount;
    }

    public Point getPos() {
        return pos;
    }

}
