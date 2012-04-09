package mAPI;

public class Misc {
	
	public static boolean inStrArr(final String str, final String[] arr) {
		for(String s: arr) 
			if (str.equals(s)) return true;
		return false;
	}
	
	public static boolean inIntArr(final int a, final int[] arr) {
		for(int i: arr) 
			if (i == a) return true;
		return false;
	}
	
}
