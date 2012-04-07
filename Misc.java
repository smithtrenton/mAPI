package mAPI;

public class Misc {
	
	public static boolean inStrArr(String str, String[] arr) {
		for(String s: arr) 
			if (str.equals(s)) return true;
		return false;
	}
	
	public static boolean inIntArr(int a, int[] arr) {
		for(int i: arr) 
			if (i == a) return true;
		return false;
	}
	
}
