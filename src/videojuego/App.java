package videojuego;

import auth.Users;
import javax.swing.*;

public class App {

    public static JFrame window;
    public static Users users = new Users();
    private static void initWindow() {
        window = new JFrame("Furryfication");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Board board = new Board();
        window.add(board);
        window.addKeyListener(board);
     
        window.setResizable(false);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }


    public static void start() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                initWindow();
            }
        });
    }
}