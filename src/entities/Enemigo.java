/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import static entities.Mob.write;
import gamepack.Juego;
import gamepack.Teclado;
import gfx.Colores;
import gfx.GameFont;
import gfx.Screen;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.JOptionPane;
import level.Level;

/**
 *
 * @author Toled
 */
public class Enemigo extends Mob {

    //File f=new File("level.txt");
    //private boolean ghost=false;
    private Teclado input;
    private int colour = Colores.get(-1, 000, 200, 543);
    private int scale = 1;
    int mov1 = 0;
    int up = 0, down = 0, left = 0, right = 0;
    boolean SoundOn = true;
    private static float vol = -28;
    long time = System.currentTimeMillis();
    long playtime = System.currentTimeMillis();
    public static ArrayList<Integer> queueMoves = new ArrayList<>();

    private final int QUEUE_UP = 1;
    private final int QUEUE_DOWN = 2;
    private final int QUEUE_LEFT = 3;
    private final int QUEUE_RIGHT = 4;

    public Enemigo(Level level, int x, int y, Teclado input) {
        super(level, "Enemigo", x, y, 1);
        this.input = input;
    }

    public static synchronized void playSound() {

        new Thread(new Runnable() {

            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(Jugador.class.getResourceAsStream("/img/steps.WAV"));
                    clip.open(inputStream);
                    FloatControl gainControl
                            = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    // System.out.println(gainControl.getValue());
                    gainControl.setValue(vol);
                    clip.start();

                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }

    public void startMove() {
//        System.out.println(Arrays.toString(queueMoves.toArray()));
        if (!queueMoves.isEmpty()) {
            switch (queueMoves.remove(0)) {
                case QUEUE_UP:
                    move(0, -1);
                    break;
                case QUEUE_DOWN:
                    move(0, 1);
                    break;
                case QUEUE_LEFT:
                    move(-1, 0);
                    break;
                case QUEUE_RIGHT:
                    move(1, 0);
                    break;
            }
        }

    }

    public void tick() {

        int Rtime = (int) ((15000) / Juego.speed);
        if (Rtime > 250) {
            Rtime = 250;
        }
        int xa = 0;
        int ya = 0;
        if (input.up.isPressed() && up == 0) {
//            ya -= 1;
            queueMoves.add(QUEUE_UP);
            if (mov1 != 2 && System.currentTimeMillis() - time > Rtime) {
                mov1 = 2;
                time = System.currentTimeMillis();
            } else if (mov1 != 3 && System.currentTimeMillis() - time > Rtime) {
                mov1 = 3;
                time = System.currentTimeMillis();
            }
            left = 1;
            right = 1;
            down = 1;
        }

        if (input.down.isPressed() && down == 0) {
//            ya += 1;
            queueMoves.add(QUEUE_DOWN);
            if (mov1 != 1 && System.currentTimeMillis() - time > Rtime) {
                mov1 = 1;
                time = System.currentTimeMillis();
            } else if (mov1 != 0 && System.currentTimeMillis() - time > Rtime) {
                mov1 = 0;
                time = System.currentTimeMillis();
            }
            left = 1;
            right = 1;
            up = 1;

        }
        if (input.left.isPressed() && left == 0) {
//            xa -= 1;
            queueMoves.add(QUEUE_LEFT);
            if (mov1 != 6 && System.currentTimeMillis() - time > Rtime) {
                mov1 = 6;
                time = System.currentTimeMillis();
            } else if (mov1 != 7 && System.currentTimeMillis() - time > Rtime) {
                mov1 = 7;
                time = System.currentTimeMillis();

            }
            down = 1;
            right = 1;
            up = 1;
        }
        if (input.right.isPressed() && right == 0) {
//            xa += 1;
            queueMoves.add(QUEUE_RIGHT);
            if (mov1 != 4 && System.currentTimeMillis() - time > Rtime) {
                mov1 = 4;
                time = System.currentTimeMillis();
            } else if (mov1 != 5 && System.currentTimeMillis() - time > Rtime) {
                mov1 = 5;
                time = System.currentTimeMillis();
            }
            down = 1;
            left = 1;
            up = 1;
        }
        if (!input.up.isPressed()) {
            left = 0;
            right = 0;
            down = 0;
        }
        if (!input.down.isPressed()) {
            left = 0;
            right = 0;
            up = 0;
        }
        if (!input.left.isPressed()) {
            up = 0;
            right = 0;
            down = 0;
        }
        if (!input.right.isPressed()) {
            left = 0;
            up = 0;
            down = 0;
        }

//        if (xa != 0 || ya != 0) {
//            move(xa, ya);
//            Juego.plx = x;
//            Juego.ply = y;
//
//            isMoving = true;
//            if (System.currentTimeMillis() - playtime > Rtime && SoundOn) {
//                this.playSound();
//                playtime = System.currentTimeMillis();
//            }
//        } else {
//            isMoving = false;
//        }

    }

    public void render(Screen screen) {
        int xTile = 0;
        int yTile = 28;
        if (Juego.speed > 200) {
            xTile += 12;

        }

        int modifier = 8 * scale;
        int xOffset = x - modifier / 2;
        int yOffset = y - modifier / 2;

        if (mov1 == 0) {

            screen.render(xOffset, yOffset, xTile + yTile * 32, colour, 0x00, scale); // upper body part 1
            screen.render(xOffset + modifier, yOffset, (xTile + 1) + yTile * 32, colour, 0x00, scale); // upper body part 2
            screen.render(xOffset, yOffset + modifier, xTile + (yTile + 1) * 32, colour, 0x00, scale); // lower body part 1
            screen.render(xOffset + modifier, yOffset + modifier, (xTile + 1) + (yTile + 1) * 32, colour, 0x00, scale); // lower body part 2

        }
        if (mov1 == 1) {
            xTile = 2;
            yTile = 28;
            if (Juego.speed > 200) {
                xTile += 12;

            }

            screen.render(xOffset, yOffset, xTile + yTile * 32, colour, 0x00, scale); // upper body part 1
            screen.render(xOffset + modifier, yOffset, (xTile + 1) + yTile * 32, colour, 0x00, scale); // upper body part 2
            screen.render(xOffset, yOffset + modifier, xTile + (yTile + 1) * 32, colour, 0x00, scale); // lower body part 1
            screen.render(xOffset + modifier, yOffset + modifier, (xTile + 1) + (yTile + 1) * 32, colour, 0x00, scale); // lower body part 2

        }
        if (mov1 == 2) {
            xTile = 4;
            yTile = 28;
            if (Juego.speed > 200) {
                xTile += 12;

            }

            screen.render(xOffset, yOffset, xTile + yTile * 32, colour, 0x00, scale); // upper

            screen.render(xOffset + modifier, yOffset, (xTile + 1) + yTile * 32, colour, 0x00, scale); // upper body part 2
            screen.render(xOffset, yOffset + modifier, xTile + (yTile + 1) * 32, colour, 0x00, scale); // lower body part 1
            screen.render(xOffset + modifier, yOffset + modifier, (xTile + 1) + (yTile + 1) * 32, colour, 0x00, scale); // lower body part 2

        }

        if (mov1 == 3) {
            xTile = 6;
            yTile = 28;
            if (Juego.speed > 200) {
                xTile += 12;

            }

            screen.render(xOffset, yOffset, xTile + yTile * 32, colour, 0x00, scale); // upper

            screen.render(xOffset + modifier, yOffset, (xTile + 1) + yTile * 32,
                    colour, 0x00, scale); // upper body part 2
            screen.render(xOffset, yOffset + modifier, xTile + (yTile + 1) * 32,
                    colour, 0x00, scale); // lower body part 1
            screen.render(xOffset + modifier, yOffset + modifier, (xTile + 1)
                    + (yTile + 1) * 32, colour, 0x00, scale); // lower body part 2

        }
        if (mov1 == 4) {
            xTile = 8;
            yTile = 28;
            if (Juego.speed > 200) {
                xTile += 12;

            }

            screen.render(xOffset, yOffset, xTile + yTile * 32, colour, 0x00, scale); // upper

            screen.render(xOffset + modifier, yOffset, (xTile + 1) + yTile * 32, colour, 0x00, scale); // upper body part 2
            screen.render(xOffset, yOffset + modifier, xTile + (yTile + 1) * 32, colour, 0x00, scale); // lower body part 1
            screen.render(xOffset + modifier, yOffset + modifier, (xTile + 1) + (yTile + 1) * 32, colour, 0x00, scale); // lower body part 2

        }
        if (mov1 == 5) {
            xTile = 16;
            yTile = 28;
            if (Juego.speed > 200) {
                xTile += 12;

            }

            screen.render(xOffset, yOffset, xTile + yTile * 32, colour, 0x00, scale); // upper body part  1
            screen.render(xOffset + modifier, yOffset, (xTile + 1) + yTile * 32, colour, 0x00, scale); // upper body part 2
            screen.render(xOffset, yOffset + modifier, xTile + (yTile + 1) * 32, colour, 0x00, scale); // lower body part 1
            screen.render(xOffset + modifier, yOffset + modifier, (xTile + 1) + (yTile + 1) * 32, colour, 0x00, scale); // lower body part 2

        }
        if (mov1 == 6) {
            xTile = 18;
            yTile = 28;
            if (Juego.speed > 200) {
                xTile += 12;

            }

            screen.render(xOffset + modifier, yOffset, xTile + yTile * 32, colour, 0x01, scale); // upper body part 2

            screen.render(xOffset, yOffset, (xTile + 1) + yTile * 32, colour, 0x01, scale); // upper
            screen.render(xOffset, yOffset + modifier, xTile + (yTile + 1) * 32, colour, 0x00, scale); // lower body part 1
            screen.render(xOffset + modifier, yOffset + modifier, (xTile + 1) + (yTile + 1) * 32, colour, 0x00, scale); // lower body part 2

        }
        if (mov1 == 7) {
            xTile = 22;
            yTile = 28;
            if (Juego.speed > 200) {
                xTile += 12;

            }

            screen.render(xOffset + modifier, yOffset, xTile + yTile * 32, colour, 0x01, scale); // upper body part 2

            screen.render(xOffset, yOffset, (xTile + 1) + yTile * 32, colour, 0x01, scale); // upper

            screen.render(xOffset + 1, yOffset + modifier, xTile + 1 + (yTile + 1) * 32, colour, 0x01, scale); // lower body part 1
            screen.render(xOffset + modifier + 1, yOffset + modifier, (xTile) + (yTile + 1) * 32, colour, 0x01, scale); // lower body part 2

        }

    }

    /**
     * Metodo para que colisione el jugador con los cuadros que son solidos, y
     * no pueda pasar por estos
     *
     * @param xa
     * @param ya
     * @return
     */
    @Override
    public boolean hasCollided(int xa, int ya) {
        if (!Mob.write) {
            if (ya < 0) {
                ya -= 2;
            }
            ya += 4;
        }

        return isSolidTile(xa, ya);
    }

}
