package mAPI;

import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.interactive.Players;

public class Misc {
	
	public static boolean isFullyLoggedIn() {
		return Game.getClientState() == Game.INDEX_MAP_LOADED && Game.isLoggedIn() && Players.getLocal() != null ;
	}
	
	public static boolean inStrArr(final String str, final String[] arr) {
		for(String s: arr) 
			if (str.contains(s)) return true;
		return false;
	}
	
	public static boolean inIntArr(final int a, final int[] arr) {
		for(int i: arr) 
			if (i == a) return true;
		return false;
	}
	
	public static double getRate(double value, long time) {
		if (time == 0)
			time++;
		return ((value * 3600000D) / time);
	}
}
