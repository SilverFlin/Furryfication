/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package level.tiles;

//Clase que extiende la clase CuadroBásico como clase padre y por ende la clase
//Cuadro como clase abuelo
public class BasicSolidTile extends BasicTile{

        /**
         * Método constructor en la clase CuadroSolido que recibe como parámetros
         * cuatro variables, iguales a las del constructor de la clase 
         * CuadroBasico, y recibe la variable solido de la clase abuelo, 
         * que regresa si el cuadro es solido como verdadero.
         * @param id
         * @param x
         * @param y
         * @param tileColour 
         */
	public BasicSolidTile(int id, int x, int y, int tileColour) {
		super(id, x, y, tileColour);
		this.solid=true;
	}

}
