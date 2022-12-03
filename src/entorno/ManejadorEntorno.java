/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entorno;

import gamepack.Juego;
import java.awt.Graphics2D;

/**
 *
 * @author Toled
 */
public class ManejadorEntorno {

    Juego gp;
    public Iluminacion iluminacion;

    public ManejadorEntorno(Juego gp) {
        this.gp = gp;
    }

    public void setup() {
        iluminacion = new Iluminacion(gp, 300,400);
    }

    public void draw(Graphics2D g2,int currentX, int currentY,boolean mascotaOn) {
        iluminacion.draw(g2,currentX, currentY,mascotaOn);
    }
}
