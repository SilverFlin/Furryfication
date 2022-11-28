package gamepack;

import entities.Mob;
import gfx.Colores;
import gfx.GameFont;
import gfx.Screen;
import gfx.SpriteSheet;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import level.Level;

/**
 * Estos paquetes son para poder tener varios de los metodos
 *
 * @author hoshi
 */
public class Juego extends Canvas implements Runnable {

    private static final long serialVersionUID = 1L;
    public static double StartTime;

    public static final int WIDTH = 220;
    public static final int HEIGHT = WIDTH / 14 * 9;

    /**
     * Esto nos ayuda a que el frame del juego se vea mejor/mas grande
     */
    public static final int SCALE = 4;
    public static final String NAME = "Game";
    public static int plx = 16, ply = 10;

    private JFrame frame;

    public static int levelend = 0;
    public static int levelNo = 0;

    public boolean running = false;
    public int tickCount = 0;
    public static boolean IsPaused = false;
    public static double xPresstime;
    public static double ChangeTime = System.currentTimeMillis();

    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT,
            BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer())
            .getData();

    private int[] colours = new int[6 * 6 * 6];
    public static int highscore[] = new int[102];

    //Inicio instancias otras clases
    public static Screen screen;
    public static Teclado input;
    public static Level level;
//    MouseHandler mouseHandler;
    //Fin instancias otras clases

    public static double speed = 60D;
    public static double HpressTime;

    // public Jugador player;
    /**
     * Constructor que inicializa la clase canvas y elframe que estaremos usando
     */
    public Juego() {
        /**
         * Estos métodos nos ayudan a utilizar mejor los tamaños dependiendo los
         * valores de ancho y alto de la ventana, a diferencia que el método
         * setPreferredSize que usa tamaños fijos, manteniendo todo el frame en
         * un mismo tamaño
         */
        setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

        frame = new JFrame(NAME);

        //frame.addKeyListener(input);
        /**
         * Esto es para cuando el frame cierre, se cierre también como proyecto
         * y no quede corriendo de alguna forma si no que cierre en todo.
         */
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Esto es para que este todo centrado correctamente
        frame.setLayout(new BorderLayout());

        //Esto se esta agregando el canvas al frame
        frame.add(this, BorderLayout.CENTER);

        //Settea los valores del frame a que los tamaños esten:
        //Con los primeros tres métodos que pusimos, setMinimum,
        //setMaximum y setPreffered
        frame.pack();

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                File f = new File("level5.maze");
                if (f.exists()) {
                    f.delete();
                }
            }
        });
        //Este metodo es para que laventana no se pueda redimensionar
        frame.setResizable(false);
        //Para que no se pueda mover a ninguna locacion
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void init() {
        int index = 0;
        for (int r = 0; r < 6; r++) {
            for (int g = 0; g < 6; g++) {
                for (int b = 0; b < 6; b++) {
                    int rr = (r * 255 / 5);
                    int gg = (g * 255 / 5);
                    int bb = (b * 255 / 5);

                    colours[index++] = rr << 16 | gg << 8 | bb;
                }
            }
        }

        screen = new Screen(WIDTH, HEIGHT, new SpriteSheet("/img/design.png"));
//        mouseHandler = new MouseHandler(this);
        input = new Teclado(this);
        level = new Level(64, 64);

        int i = 1;
        File file = new File("hs.maze");
        Scanner sc = null;

        try {
            sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String s = sc.nextLine();
                highscore[i] = Integer.parseInt(s);
                System.out.println(highscore[i]);
                i++;
            }

            sc.close();

        } catch (FileNotFoundException e) {

        }

        //player = new Jugador(level,plx<<3,ply<<3, input);
        //level.addEntity(player);
    }

    public synchronized void start() {
        running = true;
        new Thread(this).start();
    }

    public synchronized void stop() {
        running = false;
    }

    public void run() {

        //Este método no depende del tiempo del la compuradora lo que 
        //lo hace muy factible
//               System.nanoTime();
        //Cantidad en el momento exacto de nanosegundos
        long referenciaActualizacion = System.nanoTime();

        long referenciaContador = System.currentTimeMillis();
        //System.out.println(speed);

        double nsPerTick = 1000000000D / speed;
        //Cantidad tiempo transcurrido hasta que se realiza una actualizacion
        double delta = 0;
        int ticks = 0;
        int frames = 0;

        init();

        //Esto es para que se ejecute una y otra vez
        //cuando enFuncionamiento se convierta en falso se detendra el juego
        while (running) {
            long now = System.nanoTime();
            if (speed <= 0) {
                speed = 1;
            }
            if (speed > 2000) {
                speed = 2000;
            }
            nsPerTick = 1000000000D / speed;

            delta += (now - referenciaActualizacion) / nsPerTick;
            referenciaActualizacion = now;
            //boolean shouldRender = true;

            while (delta >= 1) {
                ticks++;
                try {
                    tick();
                } catch (Exception e) {

                }
                delta -= 1;
                //shouldRender = true;
            }

            // if (shouldRender) {
            frames++;
            render();
            //}

            if (System.currentTimeMillis() - referenciaContador >= 1000) {
                referenciaContador += 1000;
                //System.out.printf("%d %d\n",frames,ticks);
                //System.out.println(speed);
                frames = 0;
                ticks = 0;
            }

        }
    }

    public void tick() {
        tickCount++;
        level.tick();
        if (System.currentTimeMillis() - StartTime > 120000 && levelNo > 0 && !IsPaused) {
            level.dead = true;
        }
        if (levelNo > 4 && System.currentTimeMillis() - ChangeTime > 35000 && !Mob.write) {
            level.changeKey();
            ChangeTime = System.currentTimeMillis();

        }
        if (levelend == 1 && System.currentTimeMillis() - level.KeyTime > 2000) {
            levelend = 0;
        }

        if (levelNo > 4) {
//           int cerrar = JOptionPane.showConfirmDialog
//                        (null, "Ganaste, ¡¡¡Felicidades!!!");
           
           int cerrarJ = JOptionPane.showConfirmDialog(null,"Ganaste",
                   "¡¡¡Felicidades!!!",JOptionPane.OK_OPTION);
          
           if(cerrarJ==0){
               System.exit(0);
           }
        }
    }

    public void render() {

        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        int xOffset = level.player.x - (screen.width / 2);
        int yOffset = level.player.y - (screen.height / 2);

        level.renderTiles(screen, xOffset, yOffset);
        level.renderEntities(screen);

        if (levelend == 1) {
            int x = 0, y = 0;
            x = level.player.x - (screen.width / 2) + 2;
            y = level.player.y - (screen.height / 2) + 2;
            if (x > 33 * 8) {
                x = 33 * 8;
            }
            if (y > 42 * 8 - 2) {
                y = 42 * 8 - 2;
            }
            if (x < 2) {
                x = 2;
            }
            if (y < 2) {
                y = 2;
            }
            GameFont.render("    Level   ", screen, x + 11 * 8, y + 16 + 60, Colores.get(100, -1, -1, 555), 1);
            GameFont.render("  Complete  ", screen, x + 11 * 8, y + 8 + 16 + 60, Colores.get(200, -1, -1, 555), 1);
            GameFont.render("   Level" + Integer.toString(levelNo) + "   ", screen, x + 11 * 8, y + 32 + 60, Colores.get(300, -1, -1, 555), 1);

        }

        if (levelNo == 0) {
            GameFont.render("Furry", Juego.screen, 5 * 8, 3 * 8, Colores.get(-1, -1, -1, 000), 2);
            GameFont.render(" fication", Juego.screen, 12 * 8, 5 * 8, Colores.get(-1, -1, -1, 000), 2);
            GameFont.render("Click Enter", Juego.screen, 19 * 8, 7 * 8, Colores.get(-1, -1, -1, 000), 1);
            
//            if (input.x.isPressed()) {
//                
//                GameFont.render("Press M for MakeMode", Juego.screen, 7 * 8, 12 * 8, Colores.get(-1, -1, -1, 511), 1);
//                GameFont.render("Press Enter for Campaign", Juego.screen, 5 * 8, 15 * 8, Colores.get(-1, -1, -1, 511), 1);
//                GameFont.render("Press H for Help", Juego.screen, 8 * 8, 18 * 8, Colores.get(-1, -1, -1, 511), 1);
//            }
        } else {
            int x = 0, y = 0;
            x = level.player.x - (screen.width / 2) + 2;
            y = level.player.y - (screen.height / 2) + 2;
            if (x > 33 * 8) {
                x = 33 * 8;
            }
            if (y > 42 * 8 - 2) {
                y = 42 * 8 - 2;
            }
            if (x < 2) {
                x = 2;
            }
            if (y < 2) {
                y = 2;
            }

            GameFont.render(Integer.toString(((int) (System.currentTimeMillis() - StartTime) / 1000) / 60) + ":" + Integer.toString(((int) (System.currentTimeMillis() - StartTime) / 1000) % 60), Juego.screen, x, y, Colores.get(-1, -1, -1, 000), 1);
            GameFont.render("Record", Juego.screen, x + 14 * 8, y, Colores.get(-1, -1, -1, 555), 1);
            GameFont.render(Integer.toString(highscore[levelNo] / 60000) + ":" + Integer.toString((highscore[levelNo] / 1000) % 60), Juego.screen, x + 15 * 8, y + 8, Colores.get(-1, -1, -1, 555), 1);

        }

        if (levelNo > 0) {
            int x = 0, y = 0;
            x = level.player.x + (screen.width / 2) - 20;
            y = level.player.y - (screen.height / 2) + 4;
            if (x > 62 * 8 - 4) {
                x = 62 * 8 - 4;
            }
            if (y > 42 * 8) {
                y = 42 * 8;
            }
            if (x < 29 * 8 - 2) {
                x = 29 * 8 - 2;
            }
            if (y < 4) {
                y = 4;
            }

            if (level.keyX << 3 < level.player.x) {
                GameFont.render(" +", screen, x, y, Colores.get(-1, -1, -1, 200), 1, 01);
            } else {
                GameFont.render(" +", screen, x, y, Colores.get(-1, -1, -1, 200), 1);
            }

            if (level.keyY << 3 < level.player.y) {
                GameFont.render("-", screen, x, y, Colores.get(-1, -1, -1, 200), 1);
            } else {
                GameFont.render("-", screen, x, y, Colores.get(-1, -1, -1, 200), 1, 02);
            }

        }

        if (speed > 200 && System.currentTimeMillis() - xPresstime <= 2000) {
            GameFont.render("Speed", screen, plx + 8, ply - 16, Colores.get(-1, -1, -1, 550), 1);
            GameFont.render(Double.toString(speed), screen, plx + 8, ply - 8, Colores.get(-1, -1, -1, 550), 1);

        }

        for (int y = 0; y < screen.height; y++) {
            for (int x = 0; x < screen.width; x++) {
                int ColourCode = screen.pixels[x + y * screen.width];
                if (ColourCode < 255) {
                    pixels[x + y * WIDTH] = colours[ColourCode];

                }
            }
        }

                
        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        g.dispose();
        bs.show();

    }

}
