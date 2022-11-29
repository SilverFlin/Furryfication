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
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.JOptionPane;
import level.Level;

/**
 *
 * @author hoshi
 */
public class Mascota extends Mob{

     private Teclado input;
        private int colour = Colores.get(-1, 000, 200, 543);
        private int scale=1;
        int mov1=0;
        int up=0,down=0,left=0,right=0;
        boolean SoundOn=true;
        private static float vol=-28;
        long time=System.currentTimeMillis();
        long playtime=System.currentTimeMillis();
    
   
    public Mascota(Level level, int x, int y, Teclado input) {
        super(level, "Mascota", x, y, 1);
                this.input = input;
        
    }
    
     public static synchronized void playSound() {
            
  		  new Thread(new Runnable() {
  		  
  		    public void run() {
  		      try {
  		        Clip clip = AudioSystem.getClip();
  		        AudioInputStream inputStream = AudioSystem.getAudioInputStream(Jugador.class.getResourceAsStream("/img/steps.WAV"));
  		        clip.open(inputStream);
  		        FloatControl gainControl = 
    		    	    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
  		     // System.out.println(gainControl.getValue());
    		    	gainControl.setValue(vol);
  		        clip.start(); 
  		        
  		      } catch (Exception e) {
  		        System.err.println(e.getMessage());
  		      }
  		    }
  		  }).start();
  		}

    @Override
    public boolean hasCollided(int xa, int ya) {if(!Mob.write){
        	if(ya<0)
        		ya-=2;
        	ya+=4;
        	}
        	if(isSolidTile(xa, ya))
            	return true;
            return false;  
    }

    @Override
    public void tick() {
       if(input.VolUp.isPressed()){vol+=10;if(vol>6)vol=6;input.VolUp.pressed=false;}
        	if(input.VolDown.isPressed()){vol-=10;if(vol<-35)vol=-35;input.VolDown.pressed=false;}
        	if(input.LVolUp.isPressed()){Level.vol+=10;if(Level.vol>6)Level.vol=6;input.LVolUp.pressed=false;}
        	if(input.LVolDown.isPressed()){Level.vol-=10;if(Level.vol<-30)Level.vol=-30;input.LVolDown.pressed=false;}
        	if(input.Sound.isPressed()){if(SoundOn)SoundOn=false;else SoundOn=true; input.Sound.pressed=false;}
        	if(input.Music.isPressed()){if(Level.MusicOn)Level.MusicOn=false;else Level.MusicOn=true;input.Music.pressed=false;}
        	
        	if(input.P.isPressed() && Juego.levelNo>0)
        	{
        		double time=System.currentTimeMillis();
        		Juego.IsPaused=true;
        		for (int i = 0; i < 100; i++) 
            		input.disableButtons();
        		JOptionPane.showMessageDialog(null, "Game Paused");
        		Juego.StartTime+=System.currentTimeMillis()-time;
        		Juego.IsPaused=false;
        		return;
        	}
        	
        	if(input.H.isPressed() )
        	{
        		double time=System.currentTimeMillis();
        		for (int i = 0; i < 100; i++) 
            		input.disableButtons();
        		JOptionPane.showMessageDialog(null, "Find the Key in the Maze\nSee upper right corner for directions\n"
        				+ "Press enter in Make Mode to play\nPress H anytime for Help\nPress 1 to Mute Player Sound and "
        				+ "2 to mute Game Music\nPress U and Y to control Music, + and - to control Player Sound\n"
        				+ "Press P to pause\nPress BackSpace anytime to Quit Game\n"
        				+ "Try to Press & Hold other buttons also?(Maybe Cheats)\nEnjoy!!\n");
        		input.disableButtons();
        		
        		Juego.StartTime+=System.currentTimeMillis()-time;
        		Juego.IsPaused=false;
        		return;
        	}
        
        	if(input.Bk.isPressed()){
        		Juego.StartTime=System.currentTimeMillis();
				Juego.ChangeTime=System.currentTimeMillis();
				write=false;
				this.x=16<<3;
				this.y=10<<3;
				Juego.levelNo=0;
				mov1=0;
				Juego.level.generateLevel();
        	}
        	
        	if(Juego.level.dead)
        		{ 
        		 
        			if(input.enter.isPressed())
        			{
        				
        				Juego.ChangeTime=System.currentTimeMillis();
        				Juego.StartTime=System.currentTimeMillis();
        				Juego.level.dead=false;
        				Juego.level.generateLevel();
        				
        			}
        			return;
        		}
        
        	if(System.currentTimeMillis()-Juego.StartTime>120000 && write)
        	{
        		write=false;
        		level.generateLevel();
        	
        	}
        	if(input.enter.isPressed() && write)
        	{
        		write=false;
        		Juego.ChangeTime=System.currentTimeMillis();
				Juego.StartTime=System.currentTimeMillis();
        		level.generateLevel();
        	}
        		if(Juego.levelNo==0)
        		{
        			if(input.enter.isPressed()){
        				
        				Juego.ChangeTime=System.currentTimeMillis();
        				Juego.StartTime=System.currentTimeMillis();
        				Juego.levelNo++;
        				if(Juego.levelNo==100)
        					Juego.levelNo=0;
        				Juego.level.generateLevel();
        				
        				
        			}
        			
//        			if(input.M.isPressed() ){
//                		Juego.levelNo=101;
//                		Juego.ChangeTime=System.currentTimeMillis();
//        				Juego.StartTime=System.currentTimeMillis();
//                		Juego.level.generateLevel();
//                	}
        			
        			
        			
        			
        			return;
        		}
        		
        			int Rtime=(int) ((15000)/Juego.speed);
        		if(Rtime>250)
        				Rtime=250;
                int xa = 0;
                int ya = 0;
//                if(input.G.TimesPressed()%3==0 && input.G.TimesPressed()!=0)
//                		{Tile.STONE.solid=false;}
//
//                if(input.G.TimesPressed()%6==0 && input.G.TimesPressed()!=0)
//                		{Tile.STONE.solid=true;}
          
//                if(input.x.isPressed())
//                {
//                	Juego.speed+=1;
//                	Juego.xPresstime=System.currentTimeMillis();
//                }
                
//                if(input.z.isPressed()){
//                	Juego.speed-=1;
//                	Juego.xPresstime=System.currentTimeMillis();
//                }

                if (input.up.isPressed() && up==0) {
                        ya -= 1;
                        if(mov1!=2 && System.currentTimeMillis()-time>Rtime)
                        {
                        	mov1=2;
                        	time=System.currentTimeMillis();
                        }
                        else if(mov1!=3 && System.currentTimeMillis()-time>Rtime)
                        	{
                        	mov1=3;
                        	time=System.currentTimeMillis();
                        	
                        	}
                        left=1;
                        right=1;
                        down=1;	
      
                }
                
                if (input.down.isPressed() && down==0) {
                        ya += 1;
                        if(mov1!=1 && System.currentTimeMillis()-time>Rtime)
                        {
                        	mov1=1;
                        	time=System.currentTimeMillis();
                        }
                        else if(mov1!=0 && System.currentTimeMillis()-time>Rtime)
                        	{
                        	mov1=0;
                        	time=System.currentTimeMillis();
                        	
                        	}
                        left=1;
                        right=1;
                        up=1;	
                        
                }
                if (input.left.isPressed() && left==0) {
                        xa -= 1;
                        if(mov1!=6 && System.currentTimeMillis()-time>Rtime)
                        {
                        	mov1=6;
                        	time=System.currentTimeMillis();
                        }
                        else if(mov1!=7 && System.currentTimeMillis()-time>Rtime)
                        	{
                        	mov1=7;
                        	time=System.currentTimeMillis();
                        	
                        	}
                        down=1;
                        right=1;
                        up=1;
                }
                if (input.right.isPressed() && right==0) {
                        xa += 1;
                        if(mov1!=4 && System.currentTimeMillis()-time>Rtime)
                        {
                        	mov1=4;
                        	time=System.currentTimeMillis();
                        }
                        else if(mov1!=5 && System.currentTimeMillis()-time>Rtime)
                        	{
                        	mov1=5;
                        	time=System.currentTimeMillis();
                        	
                        	}
                        down=1;
                        left=1;
                        up=1;
                }
                if (!input.up.isPressed() )
                {
               	 	left=0;
                    right=0;
                    down=0;
                }
                if (!input.down.isPressed() )
                {
               	 	left=0;
                    right=0;
                    up=0;
                } 
                if (!input.left.isPressed() )
                {
               	 	up=0;
                    right=0;
                    down=0;
                }
                if (!input.right.isPressed() )
                {
               	 	left=0;
                    up=0;
                    down=0;
                }
 
                if (xa != 0 || ya != 0) {
                	
                		
                		move(xa, ya);
                		Juego.plx=x;
                		Juego.ply=y;
                		
                        isMoving = true;
                        if(System.currentTimeMillis()-playtime>Rtime && SoundOn)
                        	{this.playSound();
                        	 playtime=System.currentTimeMillis();
                        	}
                        
                       
                } else {
                        isMoving = false;
                }
                
                  
    }

    @Override
    public void render(Screen screen) {
       int xTile = 0;
                int yTile = 28;
                if(Juego.speed>200)
                {
                	 xTile += 12;
                    
                }
               
 
                int modifier = 8 * scale;
                int xOffset = x - modifier / 2;
                int yOffset = y - modifier / 2 ;
               
                if(Juego.level.dead)
                {
                	int x=0,y=0;
                	x=level.player.x - (screen.width / 2)+2;
                	y=level.player.y - (screen.height / 2)+2;
                	if(x>33*8)
                		x=33*8;
                	if(y>42*8-2)
                		y=42*8-2;
                	if(x<2)
                		x=2;
                	if(y<2)
                		y=2;
                	y+=7*8;
                    
                	
                    GameFont.render("LEVEL ", Juego.screen, x+14, y, Colores.get(-1, -1, -1, 500), 4);
                    GameFont.render("FAILED  ", Juego.screen, x+14, y+40, Colores.get(-1, -1, -1, 500), 4);
                    GameFont.render("Press Enter", Juego.screen, x+14, y+16+60, Colores.get(-1, -1, -1, 500), 1);
                    return;
                	
                }
                
                if(mov1==0){
                
                screen.render(xOffset, yOffset, xTile + yTile * 32, colour, 0x00,scale); // upper body part 1
                                                                                                                                                        
                screen.render(xOffset + modifier, yOffset, (xTile + 1) + yTile * 32,
                                colour, 0x00,scale); // upper body part 2
                
                screen.render(xOffset, yOffset + modifier, xTile + (yTile + 1) * 32,
                		colour, 0x00,scale); // lower body part 1
                screen.render(xOffset + modifier, yOffset + modifier, (xTile + 1)
                		+ (yTile + 1) * 32, colour, 0x00,scale); // lower body part 2
            	
              
                }
                if(mov1==1){
                	 xTile = 12;
                     yTile = 28;
                if(Juego.speed>200)
                {
                	 xTile += 12;
                    
                }
               
               
                screen.render(xOffset, yOffset, xTile + yTile * 32, colour, 0x00,scale); // upper body part 1
                screen.render(xOffset + modifier, yOffset, (xTile + 1) + yTile * 32,
                		colour, 0x00,scale); // upper body part 2
                screen.render(xOffset, yOffset + modifier, xTile + (yTile + 1) * 32,
                		colour, 0x00,scale); // lower body part 1
                screen.render(xOffset + modifier, yOffset + modifier, (xTile + 1)
                		+ (yTile + 1) * 32, colour, 0x00,scale); // lower body part 2
                
                }
                if(mov1==2){
                	xTile = 16;
                    yTile = 28;
                    if(Juego.speed>200)
                    {
                    	 xTile += 12;
                        
                    }
                	
                    screen.render(xOffset, yOffset, xTile + yTile * 32, colour, 0x00,scale); // upper
                                                                                                                                                           
                    screen.render(xOffset + modifier, yOffset, (xTile + 1) + yTile * 32,
                                    colour, 0x00,scale); // upper body part 2
                    screen.render(xOffset, yOffset + modifier, xTile + (yTile + 1) * 32,
                                    colour, 0x00,scale); // lower body part 1
                    screen.render(xOffset + modifier, yOffset + modifier, (xTile + 1)
                                    + (yTile + 1) * 32, colour, 0x00,scale); // lower body part 2
                   
                  
                    }
                
                
                if(mov1==3){
                    xTile = 18;
                    yTile = 28;
                    if(Juego.speed>200)
                    {
                    	 xTile += 12;
                        
                    }
                   
                    screen.render(xOffset, yOffset, xTile + yTile * 32, colour, 0x00,scale); // upper
                   
                    screen.render(xOffset + modifier, yOffset, (xTile + 1) + yTile * 32,
                    		colour, 0x00,scale); // upper body part 2
                    screen.render(xOffset, yOffset + modifier, xTile + (yTile + 1) * 32,
                    		colour, 0x00,scale); // lower body part 1
                    screen.render(xOffset + modifier, yOffset + modifier, (xTile + 1)
                    		+ (yTile + 1) * 32, colour, 0x00,scale); // lower body part 2
                    
                    }
                if(mov1==4){
                    xTile = 20;
                    yTile = 28;
                    if(Juego.speed>200)
                    {
                    	 xTile += 12;
                        
                    }
                  
                    screen.render(xOffset, yOffset, xTile + yTile * 32, colour, 0x00,scale); // upper
                 
                    screen.render(xOffset + modifier, yOffset, (xTile + 1) + yTile * 32,
                    		colour, 0x00,scale); // upper body part 2
                    screen.render(xOffset, yOffset + modifier, xTile + (yTile + 1) * 32,
                    		colour, 0x00,scale); // lower body part 1
                    screen.render(xOffset + modifier, yOffset + modifier, (xTile + 1)
                    		+ (yTile + 1) * 32, colour, 0x00,scale); // lower body part 2
                    
                    }
                if(mov1==5){
                    xTile = 22;
                    yTile = 28;
                    if(Juego.speed>200)
                    {
                    	 xTile += 12;
                        
                    }
                    
                    screen.render(xOffset, yOffset, xTile + yTile * 32, colour, 0x00,scale); // upper
                    // body
                    // part
                    // 1
                    screen.render(xOffset + modifier, yOffset, (xTile + 1) + yTile * 32,
                    		colour, 0x00,scale); // upper body part 2
                    screen.render(xOffset, yOffset + modifier, xTile + (yTile + 1) * 32,
                    		colour, 0x00,scale); // lower body part 1
                    screen.render(xOffset + modifier, yOffset + modifier, (xTile + 1)
                    		+ (yTile + 1) * 32, colour, 0x00,scale); // lower body part 2
                    
                    }
                if(mov1==6){
                    xTile = 20;
                    yTile = 28;
                    if(Juego.speed>200)
                    {
                    	 xTile += 12;
                        
                    }
                    
                    screen.render(xOffset + modifier, yOffset, xTile  + yTile * 32,
                    		colour, 0x01,scale); // upper body part 2
                    
                    screen.render(xOffset, yOffset, (xTile+1)  + yTile * 32, colour, 0x01,scale); // upper
                
                   
                    screen.render(xOffset, yOffset + modifier, xTile + (yTile + 1) * 32,
                    		colour, 0x00,scale); // lower body part 1
                    screen.render(xOffset + modifier, yOffset + modifier, (xTile + 1)
                    		+ (yTile + 1) * 32, colour, 0x00,scale); // lower body part 2
                    
                    }
                if(mov1==7){
                    xTile = 22;
                    yTile = 28;
                    if(Juego.speed>200)
                    {
                    	 xTile += 12;
                        
                    }
                    
                    screen.render(xOffset + modifier, yOffset, xTile  + yTile * 32,
                    		colour, 0x01,scale); // upper body part 2
                    
                    screen.render(xOffset, yOffset, (xTile+1)  + yTile * 32, colour, 0x01,scale); // upper
                  
                   
                    screen.render(xOffset+1, yOffset + modifier, xTile+1 + (yTile + 1) * 32,
                    		colour, 0x01,scale); // lower body part 1
                    screen.render(xOffset + modifier+1, yOffset + modifier, (xTile )
                    		+ (yTile + 1) * 32, colour, 0x01,scale); // lower body part 2
                    
                    }
    }
    
    
    
}
