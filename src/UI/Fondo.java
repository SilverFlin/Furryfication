/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Toled
 */
public class Fondo extends JPanel{
    private Image backgroundImage;

    public Fondo(String fileName) throws IOException {
        File file = new File(fileName);
        System.out.println(file.getAbsolutePath());
        backgroundImage = ImageIO.read(file);
  }

    @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    // Draw the background image.
    g.drawImage(backgroundImage, 0, 0, this);
  }

}