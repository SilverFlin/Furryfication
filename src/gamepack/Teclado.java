package gamepack;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import level.Level;

public class Teclado implements KeyListener {

    public class Key {

        private int numTimesPressed = 0;
        public boolean pressed = false;

        public int TimesPressed() {
            return numTimesPressed;
        }

        public boolean isPressed() {
            return pressed;
        }

        public void toggle(boolean isPressed) {
            pressed = isPressed;
            if (isPressed) {
                numTimesPressed++;
            }
        }

    }

    public Teclado(Juego game) {
        game.addKeyListener(this);
    }

    //public List<Key> keys=new ArrayList<Key>();
    public Key upPlayer = new Key();
    public Key downPlayer = new Key();
    public Key leftPlayer = new Key();
    public Key rightPlayer = new Key();
    public Key upMascota = new Key();
    public Key downMascota = new Key();
    public Key leftMascota = new Key();
    public Key rightMascota = new Key();
    
    
    public Key enter = new Key();
    public Key x = new Key();
    public Key q = new Key();
//	public Key z=new Key();
//	public Key G=new Key();
//	public Key M=new Key();
    public Key Bk = new Key();
    public Key H = new Key();
    public Key P = new Key();
    public Key Sound = new Key();
    public Key Music = new Key();
    public Key VolUp = new Key();
    public Key VolDown = new Key();
    public Key LVolUp = new Key();
    public Key LVolDown = new Key();

    public void keyPressed(KeyEvent e) {
        toggleKey(e.getKeyCode(), true);

    }

    public void keyReleased(KeyEvent e) {
        toggleKey(e.getKeyCode(), false);

    }

    public void keyTyped(KeyEvent e) {

    }

    public void toggleKey(int keyCode, boolean isPressed) {
        if (keyCode == KeyEvent.VK_UP ) {
            upPlayer.toggle(isPressed);
        }
        if( keyCode == KeyEvent.VK_W){
            upMascota.toggle(isPressed);
        }
        
        if (keyCode == KeyEvent.VK_DOWN ) {
            downPlayer.toggle(isPressed);
        }
        if( keyCode == KeyEvent.VK_S){
            downMascota.toggle(isPressed);
        }
        
        
        if (keyCode == KeyEvent.VK_LEFT ) {
            leftPlayer.toggle(isPressed);
        }
        
        if(keyCode == KeyEvent.VK_A){
            leftMascota.toggle(isPressed);
        }
        
        if (keyCode == KeyEvent.VK_RIGHT ) {
            rightPlayer.toggle(isPressed);
        }
        
        if( keyCode == KeyEvent.VK_D){
            rightMascota.toggle(isPressed);
        }
        if (keyCode == KeyEvent.VK_ENTER) {

            enter.toggle(isPressed);

        }

        if (keyCode == KeyEvent.VK_X) {
            x.toggle(isPressed);

        }
//		if(keyCode==KeyEvent.VK_Z)
//		{
//			z.toggle(isPressed);
//			
//		}

        if (keyCode == KeyEvent.VK_Q) {
            q.toggle(isPressed);

        }
//		if(keyCode==KeyEvent.VK_G)
//		{
//			G.toggle(isPressed);
//			
//			
//		}

//		if(keyCode==KeyEvent.VK_M){
//			M.toggle(isPressed);
//			
//		}
        if (keyCode == KeyEvent.VK_BACK_SPACE) {
            Bk.toggle(isPressed);

        }
        if (keyCode == KeyEvent.VK_H) {
            H.toggle(isPressed);

        }
        if (keyCode == KeyEvent.VK_P) {
            P.toggle(isPressed);

        }
        if (keyCode == KeyEvent.VK_1) {
            Sound.toggle(isPressed);

        }
        if (keyCode == KeyEvent.VK_2) {
            //System.out.println("off");
            Music.toggle(isPressed);

        }
        if (keyCode == 61 || keyCode == 107) {

            System.out.println("lll");
            VolUp.toggle(isPressed);

        }
        if (keyCode == KeyEvent.VK_MINUS || keyCode == 109) {

            VolDown.toggle(isPressed);

        }
        if (keyCode == KeyEvent.VK_U) {

            LVolUp.toggle(isPressed);

        }
        if (keyCode == KeyEvent.VK_Y) {

            LVolDown.toggle(isPressed);

        }
        //System.out.println(keyCode);

    }

    public void disableButtons() {
        H.pressed = false;
        P.pressed = false;
        upPlayer.pressed = false;
        downPlayer.pressed = false;
        leftPlayer.pressed = false;
        rightPlayer.pressed = false;
        x.pressed = false;
        q.pressed = false;
// 		z.pressed=false;
// 		G.pressed=false;
        VolUp.pressed = false;
        VolDown.pressed = false;
        LVolDown.pressed = false;
        LVolDown.pressed = false;

    }
}
