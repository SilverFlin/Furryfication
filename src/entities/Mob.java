package entities;
 
import gamepack.Juego;
import java.awt.Rectangle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import level.Level;
import level.tiles.Tile;
public abstract class Mob extends Entity {
 
        protected String name;
        protected int speed;
        protected int numSteps = 0;
        protected boolean isMoving;
        protected int movingDir = 1;
        protected int scale = 1;
        public static boolean write;
        File f=new File("level"+Integer.toString(Juego.levelNo)+".maze");
    	
        File f1=new File("hs.maze");
 
        public Mob(Level level, String name, int x, int y, int speed) {
                super(level);
                this.name = name;
                this.x = x;
                this.y = y;
                this.speed = speed;
                if(!f.exists())
                write=true;
        }
 
        public void move(int xa, int ya) {
              /*  if (xa != 0 && ya != 0) {
                        move(xa, 0);
                        move(0, ya);
                        numSteps -= 1;
                        return;
                }*/                
        	   numSteps += 1;
                if (!hasCollided(xa, ya)) {
                
                        x += xa * speed;
                        y += ya * speed;
                        //System.out.printf("%d %d\n",x,y);
                }
        }
 
        public abstract boolean hasCollided(int xa, int ya);

        public Rectangle getBounds() {
            return new Rectangle(x-8, y+8, 8, 8);
        }

        public boolean isSolidTile(int xa,int ya)
        {
        	if(level == null) return false;
        	if(xa>0)
        		xa=xa+7;
        	if(ya>0)
        		ya=ya+7;
        	Tile newTile=level.getTile((this.x+xa)>>3,(this.y+ya)>>3);
        	
        	if(newTile.getId()==Tile.KEY.getId())
        	{
				
				
        		if(Juego.levelNo==101)
        		{
        			
        			Juego.level.player.mov1=0;
        			Juego.StartTime=System.currentTimeMillis();
    				Juego.ChangeTime=System.currentTimeMillis();
    				Juego.level.KeyTime=System.currentTimeMillis();
    				//Game.levelNo=1;
    				this.x=16<<3;
    				this.y=10<<3;
    				Juego.levelNo=0;
    				
    				Juego.level.generateLevel();
    				return true;
        			
        		}
        		if(System.currentTimeMillis()-Juego.StartTime<Juego.highscore[Juego.levelNo]||Juego.highscore[Juego.levelNo]==0)
        		Juego.highscore[Juego.levelNo]=(int) (System.currentTimeMillis()-Juego.StartTime);
        				//System.out.println(Juego.highscore[Juego.levelNo]);
        		FileWriter fileWritter;
				try {
					if(!f1.exists())
					f1.createNewFile();
					fileWritter = new FileWriter(f1.getName(),false);
					BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
					//Scanner sc = new Scanner(f);
					for (int i = 1; i <Juego.levelNo+1; i++) {
						bufferWritter.write(Integer.toString(Juego.highscore[i]));
						System.out.println(Integer.toString(Juego.highscore[i]));
						bufferWritter.newLine();
						
					}
        			bufferWritter.close();
        			fileWritter.close();
        			//System.out.println(sc.nextLine());
       				
        			
				} catch (IOException e) {
					
					e.printStackTrace();
				}
        				
        				
        				Juego.StartTime=System.currentTimeMillis();
        				Juego.ChangeTime=System.currentTimeMillis();
        				Juego.level.KeyTime=System.currentTimeMillis();
        				Juego.plx=this.x;
                		Juego.ply=this.y;
                		Juego.levelend=1;	
                		Juego.levelNo++;
        				Juego.level.generateLevel();
        				
        				
        			
        		
        	}	
        	
        	if(newTile.getId()==Tile.STONE.getId() && write)  //here
        	{
        		if(xa>0)
            		xa=xa-7;
            	if(ya>0)
            		ya=ya-7;
        		Level.tiles[(Juego.level.player.x/8)+xa+((Juego.level.player.y/8)+ya)*Level.width]=Tile.GRASS.getId();
        		FileWriter fileWritter;
				try {
					fileWritter = new FileWriter(f.getName(),true);
					BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
					//Scanner sc = new Scanner(f);
					//String s=Integer.toString(x/8+xa)+" "+Integer.toString(y/8+xa)+"\n";
					
							 bufferWritter.write(Integer.toString(Juego.level.player.x/8+xa));
       				 		 bufferWritter.write(" ");
       				 		 bufferWritter.write(Integer.toString((Juego.level.player.y/8+ya)));
       						 bufferWritter.newLine();
        					 bufferWritter.close();
        					 fileWritter.close();
        					 //System.out.println(sc.nextLine());
       				
        			
				} catch (IOException e) {
					
					e.printStackTrace();
				}
        	}//here
        	if(newTile.isSolid())
        		return true;
        	return false;
        }
        
        //Método para obtener el nombre
        public String getName() {
                return name;
        }
}