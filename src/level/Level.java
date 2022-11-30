package level;

import entities.Enemigo;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import level.tiles.Tile;
//import sun.applet.Main;
import entities.Entity;
import entities.Mob;
import entities.Jugador;
import gamepack.Juego;
import gfx.Colores;
import gfx.GameFont;
import gfx.Screen;
import java.util.Timer;





public class Level
{
	
	private long playtime=System.currentTimeMillis();
	public static byte[] tiles;
	public static  int width;
	public int height;
	public ArrayList<Entity> entities= new ArrayList<Entity>();
	public Jugador player;
        public static Enemigo enemigo;
	//int TimesPlayed=0;
	public static long KeyTime;
	public int keyX,keyY;
	static Clip clip=null;
	public boolean dead=false,key=false;
	static int clipSize;
	public static boolean MusicOn=true;
	public static float vol=0;
            public static boolean moverEnemigo = false;

        
	public static synchronized void playSound() {
		  new Thread(new Runnable() {
		  
		    public void run() {
		      try {
		        clip = AudioSystem.getClip();
		        AudioInputStream inputStream = AudioSystem.getAudioInputStream(
		          Level.class.getResourceAsStream("/img/music.WAV"));
		          
		          clip.open(inputStream);
		          
		          Level.clipSize=(int) (clip.getMicrosecondLength()/1000);
		          clip.start();
		          
		      
		        } 
		      
		       catch (Exception e) {
		        System.err.println(e.getMessage());
		      }
		    }
		  }).start();
		}
	


	
	public Level(int width,int height) 
	{
		tiles=new byte[width*height];
		this.width=width;
		this.height=height;
		
		this.generateLevel();
		this.playSound();
		
		player = new Jugador(this,Juego.plx<<3,Juego.ply<<3, Juego.input);	
                System.out.println("Level()");
		this.addEntity(player);
		
	}
	
	public void generateLevel() {

        if (Juego.levelNo != 0) {

            if (entities.contains(player)) {
                entities.remove(player);
            }
            Juego.plx = 8;
            Juego.ply = 4;
            player = new Jugador(this, Juego.plx, Juego.ply, Juego.input);
            enemigo = new Enemigo(this, Juego.plx, Juego.ply, Juego.input);
            this.addEntity(player);
            this.addEntity(enemigo);
            Timer t = new java.util.Timer();
        t.schedule(
                new java.util.TimerTask() {
            @Override
            public synchronized void run() {
                
                moverEnemigo = true;
                t.cancel();
            }
        }, 5000);

        }
	for(int y=0;y<height;y++){
		for(int x=0;x<width;x++){
			tiles[x+y*width]=Tile.STONE.getId();
		}
		
	}
	tiles[1+1*width]=Tile.GRASS.getId();
	if(Juego.levelNo==0)
	{
		
		
		for(int y=0;y<height;y++){
			for(int x=0;x<width;x++){
				tiles[x+y*width]=Tile.GRASS.getId();
			}
			
		}
		
		for(int y=0;y<height;y++)
		{
			tiles[15+y*width]=Tile.STONE.getId();
			tiles[17+y*width]=Tile.STONE.getId();
		}
		for(int x=0;x<width;x++)
		{
			tiles[x+9*width]=Tile.STONE.getId();
			tiles[x+11*width]=Tile.STONE.getId();
		}
		tiles[16+9*width]=Tile.GRASS.getId();
		tiles[16+11*width]=Tile.GRASS.getId();
		tiles[15+10*width]=Tile.GRASS.getId();
		tiles[17+10*width]=Tile.GRASS.getId();
		return;
		}
		
		
	

		
		String s1="level"+Integer.toString(Juego.levelNo)+".maze";
		File file = new File(s1);
		Scanner sc = null;
		int i1=1,i2=1;
		try {
			sc = new Scanner(file);
			 while (sc.hasNextLine()) {
				 String s= sc.nextLine();		           
				 
				 StringTokenizer t=new StringTokenizer(s);
				 int i = 0,j=0;
				while(t.hasMoreTokens())
				   {i=Integer.parseInt(t.nextToken());
				   j=Integer.parseInt(t.nextToken());
				  //System.out.println(i);
				  //System.out.println(j);
				  }
				tiles[i+j*width]=Tile.GRASS.getId();
				i1=i;i2=j;
			 }
		} catch (FileNotFoundException e) {
			
			
		}
		try{sc.close();}catch(Exception e){}
		
		for (int i = 0; i < 10; i++) {
			changeKey();
		}
		if(key==false && !Mob.write)
		{
			tiles[i1+i2*width]=Tile.KEY.getId();
		}
		
			
	}
        
	public void tick(){
		for(Entity e:entities) {

//                    System.out.println(e);
//                    if (e.getClass() == Enemigo.class) {
//                        Timer t = new java.util.Timer();
//                        t.schedule(
//                                new java.util.TimerTask() {
//                            @Override
//                            public synchronized void run() {
//                                e.tick();
//                                t.cancel();
//                            }
//                        },500);
//                    } else {
//                if (e.getClass() != Enemigo.class)
                e.tick();
            }
            if(moverEnemigo)enemigo.startMove();

            //System.out.println(Level.vol);

		if(!MusicOn)
		{
			clip.stop();
			//System.out.println("off");
			playtime=0;
		}
		if(MusicOn)
		{
			FloatControl gainControl = 
		    	    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
          gainControl.setValue(Level.vol);
		}
		if(System.currentTimeMillis()-playtime>=clipSize+10000 && MusicOn)
		{  
		  this.playSound();
		  playtime=System.currentTimeMillis();
		 
		}
		
	}
	public void renderTiles(Screen screen, int xOffset, int yOffset)
	{
		if(xOffset<0)
			xOffset=0;
		if(xOffset>((width<<3)-screen.width))
			xOffset=((width<<3))-screen.width;
		if(yOffset<0)
			yOffset=0;
		if(yOffset>((height<<3)-screen.height))
			yOffset=((height<<3)-screen.height);
		
		screen.setOffset(xOffset,yOffset);
		
		for(int y=(yOffset>>3);y<(yOffset+screen.height>>3)+1;y++)
		{ for(int x=(xOffset>>3);x<(xOffset+screen.width>>3)+1;x++)
			{
				getTile(x,y).render(screen,this,x<<3,y<<3);
			}
		}
		
		
	}
	public void renderEntities(Screen screen)
	{
		for(Entity e:entities)
		{
			e.render(screen);
			
	}
	}
	
	public Tile getTile(int x,int y)
	{
		if(0>x||x>=width||0>y||y>=height)
			return Tile.VOID;
		return Tile.tiles[tiles[x+y*width]];
	}
	
	public void addEntity(Entity entity)
	{
		this.entities.add(entity);;
	}




	public void changeKey() 
	{
		
		int x1=4;
		
		int yy=(int)(Math.random()*20)+(int)(Math.random()*20)+(int)(Math.random()*20);
		int xx1=0;	//(int)(Math.random()*20)+(int)(Math.random()*20)+(int)(Math.random()*20);
		int xx2=30;
		if(player.x/8<30)
			{
			//System.out.println(player.x/8);
			xx1=30;
			xx2=60;
			}
		//System.out.printf("%d %d\n",ri,rj);
		for(int y=0;y<height;y++){
			for(int x=0;x<width;x++){
				if(tiles[x+y*width]==Tile.GRASS.getId()){
				if((int)(Math.random()*100)<x1 && x>xx1 && x<xx2 && y>yy)
				{//System.out.println((int)Math.random()*1000);
				tiles[x+y*width]=Tile.KEY.getId();
				tiles[keyX+keyY*width]=Tile.GRASS.getId();
				keyX=x;
				keyY=y;
				//System.out.printf("%d %d\n",keyX,keyY);
				key=true;
				return;
				}
				}
			}
			
		}
		return;
		
	}
	
//        public void finalizado(){
//            if (Juego.levelNo == 4){
//                System.exit(0);
//            }
//        }

}