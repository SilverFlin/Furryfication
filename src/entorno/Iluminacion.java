/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entorno;

import gamepack.Juego;
import static gamepack.Juego.bs;
import static gamepack.Juego.g;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Toled
 */
public class Iluminacion {

    Juego gp;
    BufferedImage darknessFilter;
    int circleSize;
    Area screenArea;
    private final int width;
    private final int height;
    private  int centerX;
    private  int centerY;
//    Graphics2D g2;
//    private final Graphics2D g3;
    public Iluminacion(Juego gp, int circleSize) {
        
        this.circleSize = circleSize;
        this.gp = gp;
        width = gp.screen.width * gp.SCALE;
        height = gp.screen.height * gp.SCALE;
        darknessFilter = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // centro del circulo de luz
//                System.out.println(Juego.currentPlayerY);
        Graphics2D g2 = (Graphics2D) darknessFilter.getGraphics();

       screenArea = new Area(new Rectangle2D.Double(0, 0, width, height));

        centerX = 0 + (16 / 2);
        centerY = 0 + (16 / 2);

        // punto superior izquierdo del circulo
        double x = centerX - (circleSize / 2);
        double y = centerY - (circleSize / 2);

        // crear forma circulo de luz
        Shape circleShape = new Ellipse2D.Double(x, y, circleSize, circleSize);

        // crear area circulo de luz
        Area lightArea = new Area(circleShape);

        // quitarle el circulo de luz a la pantalla
        screenArea.subtract(lightArea);
//        System.out.println(g2);
//        System.out.println(bs);
            g2.setColor(new Color(0, 0, 0, 0.95f));

            g2.fill(screenArea);

            g2.dispose();
        
     
                
//        drawLight(circleSize,screenArea, (Graphics2D) g,Juego.currentPlayerX,Juego.currentPlayerY);
        
    }

    public void draw(Graphics2D g2, int currentX, int currentY) {
        
        screenArea = new Area(new Rectangle2D.Double(0, 0, width, height));
        System.out.println(currentX + " " + currentY);
        centerX = currentX;
        centerY = currentY;

        // punto superior izquierdo del circulo
        double x = centerX - (circleSize / 2);
        double y = centerY - (circleSize / 2);

        // crear forma circulo de luz
        Shape circleShape = new Ellipse2D.Double(x, y, circleSize, circleSize);

        // crear area circulo de luz
        Area lightArea = new Area(circleShape);

        // quitarle el circulo de luz a la pantalla
        screenArea.subtract(lightArea);
//        System.out.println(g2);
//        System.out.println(bs);
            g2.setColor(new Color(0, 0, 0, 0.95f));

            g2.fill(screenArea);

            g2.dispose();
//        drawLight(this.circleSize, screenArea, g2,currentX,currentY);
    }

    private void repaint( int currentX, int currentY) {
//    
        
    }

}
