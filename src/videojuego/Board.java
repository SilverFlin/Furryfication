package videojuego;

import java.applet.AudioClip;
import static UI.frmLogin.currentUser;
import auth.User;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import progress.Progress;
import progress.Progresses;
import static videojuego.App.users;
import static videojuego.App.window;

public class Board extends JPanel implements ActionListener, KeyListener {

    private final int DELAY = 25;
    public static final int TILE_SIZE = 50;
    public static final int ROWS = 12;
    public static final int COLUMNS = 18;
    public static final int NUM_COINS = 5;
    private Timer timer;
    private Player player;
    private Mascota pet;
    private ArrayList<Coin> coins;
    
    
    

    public Board() {
        setPreferredSize(new Dimension(TILE_SIZE * COLUMNS, TILE_SIZE * ROWS));
        
        player = new Player(1);
        pet  = new Mascota( player);
        coins = populateCoins();

        timer = new Timer(DELAY, this);
        
        timer.start();
        
        AudioInputStream audioInputStream;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File("src/img/carlin_boring.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(200);
        } catch (UnsupportedAudioFileException | IOException|LineUnavailableException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        player.tick();
        pet.tick();

        collectCoins();
        nextLv();
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        ImageIcon imagenFondo;
        switch (player.currentLv) {
            case 1:
                imagenFondo = new ImageIcon(getClass().getResource("/img/fondoStarWars.jpg"));
                break;
            case 2:
                imagenFondo = new ImageIcon(getClass().getResource("/img/fondoStarWars2.jpg"));
                break;
            default:
                return;
        }
        
        g.drawImage(imagenFondo.getImage(), 0, 0, null);
        drawScore(g);
        for (Coin coin : coins) coin.draw(g, this);
        
        Door door = new Door(COLUMNS/2, 0);
        door.draw(g,this);
        player.draw(g, this);
        pet.draw(g, this);

        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_Q){
            saveGame();
            window.dispose();
            System.exit(0);
        }

        try {
            player.keyPressed(e);
            pet.move(e);
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            pet.lightsOff();
        }
    } 

    private void drawScore(Graphics g) {
        String score = "$" + player.getScore();
        String username = currentUser.getUsuario();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
            RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(
            RenderingHints.KEY_RENDERING,
            RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(
            RenderingHints.KEY_FRACTIONALMETRICS,
            RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setColor(new Color(30, 201, 139));
        g2d.setFont(new Font("Lato", Font.BOLD, 25));
        FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
        Rectangle rect = new Rectangle(0, TILE_SIZE * (ROWS - 1), TILE_SIZE * COLUMNS, TILE_SIZE);
        int x = rect.x + (rect.width - metrics.stringWidth(score)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        

        g2d.drawString(username + " - " + score, x -50, y);
    }

    private ArrayList<Coin> populateCoins() {
        ArrayList<Coin> coinList = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < NUM_COINS; i++) {
            int coinX = rand.nextInt(COLUMNS);
            int coinY = rand.nextInt(ROWS);
            Coin coin = new Coin(coinX, coinY);
            Timer timerCoin = new Timer(150, coin);
            timerCoin.start();
            coinList.add(coin);
        }

        return coinList;
    }

    private void collectCoins() {
        ArrayList<Coin> collectedCoins = new ArrayList<>();
        for (Coin coin : coins) {
            if (player.getPos().equals(coin.getPos())) {
                player.addScore(100);
                collectedCoins.add(coin);
            }
        }
        coins.removeAll(collectedCoins);
    }
    
    private void nextLv() {
        Door door = new Door(COLUMNS / 2, 0);
        if (player.getPos().equals(door.getPos())) {
            if (player.currentLv < 2) {
                player = new Player(player.currentLv + 1);
                pet = new Mascota(player);
            }
        }
    }
    
    private void saveGame(){
        currentUser.setTopScore(player.getScore());
        User user = users.findByUser(currentUser.getUsuario());
        users.deleteUser(user);
        users.writeUser(currentUser);
    }
}
