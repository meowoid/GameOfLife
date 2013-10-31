package uk.ac.cam.yp242.tick3;

public class FibonacciCache {
	public static long[] fib = new long[20];
	public static void main(String[] args) {
		

		store();
		for (int i =0; i < fib.length; i++){
			System.out.println(fib[i]);
		}

		reset();
		for (int i =0; i < fib.length; i++){
			System.out.println(fib[i]);
		}

		store();
		System.out.println(get(4));
		System.out.println(get(92));
		
	}

	

	public static void store() {
		for (int i = 0; i < fib.length; i++){
			if 
				(i == 0 || i == 1) fib[i] = 1;
			else
				fib[i] = fib[i-1] + fib[i-2];
		}
	}

	public static void reset() {
		for (int i = 0; i < fib.length; i++){
			fib[i] = 0;
		}
	}

	public static long get(int i) {
		if (i < fib.length && i >= 0) return fib[i];
		else return -1L;
	}
}