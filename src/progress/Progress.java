/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progress;

import auth.User;
import java.util.ArrayList;
import videojuego.Board;
import videojuego.Coin;
import videojuego.Mascota;
import videojuego.Player;

/**
 *
 * @author Toled
 */
public class Progress {
    private Board board;
    private Mascota pet;
    private ArrayList<Coin> coins;
    private Player player;
    private User user;

    public Progress(User user, Board board, Mascota pet, ArrayList<Coin> coins, Player player) {
        this.board = board;
        this.pet = pet;
        this.coins = coins;
        this.player = player;
    }
    
}
