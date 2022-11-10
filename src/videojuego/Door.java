/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videojuego;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import utils.BufferedImages;

/**
 *
 * @author Toled
 */
public class Door {
     // image that represents the coin's position on the board
    private BufferedImage image;
    // current position of the coin on the board grid
    private Point pos;

    public Door(int x, int y) {
        // load the assets
        loadImage();

        // initialize the state
        pos = new Point(x, y);
    }

    private void loadImage() {
        try {
            // you can use just the filename if the image file is in your
            // project folder, otherwise you need to provide the file path.
//            System.out.println((new File("src/img/chokis.png")).getAbsolutePath());
            image = ImageIO.read(new File("src/img/Error.png"));
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

    public Point getPos() {
        return pos;
    }

}
