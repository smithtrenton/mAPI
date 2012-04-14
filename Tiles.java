package mAPI;

import java.util.Comparator;

import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.util.Timer;
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
	
	public static Tile MidPoint(Tile a, Tile b) {
		return new Tile(a.getX()+b.getX()/2, a.getY()+b.getY()/2, a.getPlane());
	}
	
	public static Tile randomizeTile(Tile a, int off) {
		return new Tile(Random.nextInt(a.getX() - off, a.getX() + off),
				Random.nextInt(a.getY() - off, a.getY() + off), a.getPlane());
	}
	
	public static Tile[] reversePath(Tile[] arr) {
		for (int left=0, right=arr.length-1; left<right; left++, right--) {
		    Tile temp = arr[left]; arr[left] = arr[right]; arr[right] = temp;
		}
		return arr;
	}
	
	public static final Comparator<Tile> TILE_DIST = new Comparator<Tile>() {
		@Override
		public int compare(Tile a, Tile b) {			
			return distanceTo(a) < distanceTo(b) ? 1 : 
				distanceTo(a) > distanceTo(b) ? -1 : 0;
		}
    };
	
	public static boolean tileOnMM(Tile a) {
		return (distanceTo(a) < 18);
	}
	
	public static boolean tileOnMS(Tile a) {
		return distanceTo(a) < 3;
	}
	
	public static boolean tilesEqual(Tile a, Tile b) {
		return (a.getX() == b.getX()) && (a.getY() == b.getY()) && (a.getPlane() == b.getPlane());
	}
	
	public static boolean walkTileMM(Tile a, int r_off) {
		a = randomizeTile(a, r_off);
		if (Walking.walk(a));
		Timer timeout = new Timer(30000);
		while (distanceTo(a) > (r_off+1)) {
			Time.sleep(500 + Random.nextInt(0, 100));
			if ((timeout.getRemaining() == 0) || (Walking.getDestination() == null))
				break;
		}
		return distanceTo(a) <= (r_off+1);
	}
	
}
