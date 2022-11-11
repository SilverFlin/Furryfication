package videojuego;

import auth.User;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Point;
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

public class Player {

//    User user;
    Mascota mascota; 
    private BufferedImage image;
    private Point pos;
    private int score;
    public int currentLv;

    public Player(int currentLv) {
        loadImage();

        pos = new Point( COLUMNS /2,  ROWS);
        score = 0;
        this.currentLv = currentLv;
//        user.getUsuario();
    }

    private void loadImage() {
        try {
            image = ImageIO.read(new File("src/img/mascota.png"));
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
    
    public void keyPressed(KeyEvent e) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_UP) {
            pos.translate(0, -1);
            image = ImageIO.read(new File("src/img/icon.png"));
            image = BufferedImages.resize(image, 50, 50);
            
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/img/bark.wav").getAbsoluteFile());
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
