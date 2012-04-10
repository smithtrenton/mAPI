package mAPI;

import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.Tile;

public class Tiles {
	
	public static double distanceTo(Tile t) {
		return Calculations.distance(t, Players.getLocal().getPosition());
	}
	
	public static Tile getNearestTile(Tile[] arr) {
		Tile tile = new Tile(0, 0, 0);
    	Tile from = Players.getLocal().getPosition();
    	double distance = 9999999, temp;
    	int index = 0;
    	
    	for(int i=0;i<arr.length;i++) {
    		tile = arr[i];
    		temp = Calculations.distance(from, tile);
    		if(temp<distance) {
    			distance = temp;
    			index = i;
    		}    		
    	}
    	
    	return arr[index];
	}
	
	public static Tile randomizeTile(Tile a, int off) {
		return new Tile(Random.nextInt(a.getX() - off, a.getX() + off),
				Random.nextInt(a.getY() - off, a.getY() + off), a.getPlane());
	}
	
	public static boolean tileOnMM(Tile a) {
		return Calculations.isPointOnScreen(Calculations.worldToMap(a.getX(), a.getY()));
	}
	
	public static boolean tilesEqual(Tile a, Tile b) {
		return (a.getX() == b.getX()) && (a.getY() == b.getY()) && (a.getPlane() == b.getPlane());
	}
	
}
