/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package level.tiles;

import gfx.Screen;
import level.Level;

public class BasicTile extends Tile
{
	
	
	protected int tileId;
	protected int tileColour;
	
        /**
         * 
         * @param id
         * @param x
         * @param y
         * @param tileColour 
         */
	public BasicTile(int id, int x,int y,int tileColour) 
	{
		super(id, false, false);
		this.tileId=x+y;
		this.tileColour=tileColour;
		
	}

	
	public void render(Screen screen, Level level, int x, int y) 
	{
		screen.render(x, y, tileId, tileColour);
	}

}
