package videojuego;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import utils.BufferedImages;

public class Coin implements ActionListener{
    
    public BufferedImage image;
    private Point pos;
    public double rotation = 0;

    public Coin(int x, int y) {
        loadImage();

        pos = new Point(x, y);
    }

    private void loadImage() {
        try {
            image = ImageIO.read(new File("src/img/chokis.png"));
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

    public Point getPos() {
        return pos;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (rotation + 45 >= 360.0) {
            rotation = 0;
        }
        image = BufferedImages.rotateImageByDegrees(image, rotation + 25);

    }

}
