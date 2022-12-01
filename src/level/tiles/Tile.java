package level.tiles;

import gfx.Colores;
import gfx.Screen;
import level.Level;

public abstract class Tile
{
	
	 public static final Tile[] tiles = new Tile[256];
	 
         //Inicio de cuadros
         /**
          * Estos metodos/variables se crean con otro nombre, puesto que al esta clase ser 
          * abstracta no se pueden crear instancias de esta, y se crean a base
          * de la clase CuadroSolido y CuadroBasico, y desde la variable 
          * tileColour se crea una instancia de la clase colores con el metodo 
          * get y asignar los cuatro colores dentro de la gama RGB que se 
          * pintaran en la pantalla como el fondo del jugador
          */
         //Estos son de la clase CuadroSolido que estan asignados como solidos
         //para que el jugador NO pueda pasar a través de ellos 
	 public static final Tile VOID =new BasicSolidTile(0,0,0,Colores.get(000,-1,-1,-1));
	 public static final Tile STONE =new BasicSolidTile(1,1,0,Colores.get(-1,333,222,-1));
         //Este cuadro es especial puesto que al colisionar con la llave, se
         //debe pasar al siguiente nivel
	 public static final Tile KEY =new BasicSolidTile(3,3,0,Colores.get(-1,131,532,-1));
         
         //Cuadros basicos por los que el jugador puede pasar
	 public static final Tile GRASS =new BasicTile(2,2,0,Colores.get(-1,131,141,-1));
         //Fin de cuadros
         
	 protected byte id;
	 public boolean solid;
	 protected boolean emitter;
	 
	public Tile(int id,boolean isSolid,boolean isEmitter){
		this.id=(byte)id;
		if(tiles[id]!=null)throw new RuntimeException("Duplicate tile id on"+id);
		this.solid=isSolid;
		this.emitter=isEmitter;
		tiles[id]=this;
	}
	
	public byte getId(){
		return id;
	}
        
	public boolean isSolid(){
		return solid;
	}
	public boolean isEmitter(){
		return emitter;
	}
	 
	 public abstract void render(Screen screen,Level level,int x,int y);

}
