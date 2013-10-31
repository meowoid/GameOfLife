package uk.ac.cam.yp242.tick4;

public class Repeat {
	public static void main(String[] args) {
		System.out.println(parseAndRep(args));
	}

	public static String parseAndRep(String[] args) {
		String str = null;
		int repeat = 0;
		String out = null;

		try {
			str = args[0];
			repeat = Integer.parseInt(args[1]);

			out = str;
			for (int i = 1; i < repeat; i++) out += " " + str;

			if (repeat < 1) out = "Error: second argument is not a positive integer";
		} 
		catch (ArrayIndexOutOfBoundsException e) {out = "Error: insufficient arguments";}
		catch (NumberFormatException nf) {out = "Error: second argument is not a positive integer";}

		return out;
	}
}
