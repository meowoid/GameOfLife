package uk.ac.cam.yp242.tick6star;

public class CommandLineOptions {

	public static String WORLD_TYPE_LONG = "--long";
	public static String WORLD_TYPE_AGING = "--aging";
	public static String WORLD_TYPE_ARRAY = "--array";

	private String worldType = null;
	private Integer index = null;
	private String source = null;

	public CommandLineOptions(String[] args) throws CommandLineException {
		try {
				if (args.length == 0) throw new CommandLineException("Error: Specify the source of the patterns (at least).");
				worldType = args.length == 3 ? args[0] : "--array";
				source = args.length == 3 ? args[1] : args[0];
				index = args.length == 3 ? Integer.parseInt(args[2]) : Integer.parseInt(args[1]);
				if (index<0) throw new CommandLineException("Error: Index must be positive.");
			}
		catch (NumberFormatException nfe) {throw new CommandLineException("Error: second argument (index) must be an integer.");}
		catch (CommandLineException cle) {System.out.println(cle.getMessage());}

	}

	public String getWorldType() {return worldType;}
	public Integer getIndex() {return index;}
	public String getSource() {return source;}
}
