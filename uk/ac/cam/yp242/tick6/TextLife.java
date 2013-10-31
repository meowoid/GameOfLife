package uk.ac.cam.yp242.tick6;

import java.util.List;
import uk.ac.cam.acr31.life.World;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.io.IOException;

public class TextLife {

	public static void main(String[] args) throws IOException, PatternFormatException, CommandLineException {
		CommandLineOptions c = new CommandLineOptions(args);
		List<Pattern> list;
		
		try {
			if (c.getSource().startsWith("http://"))
					list = PatternLoader.loadFromURL(c.getSource());
			else
				list = PatternLoader.loadFromDisk(c.getSource());
			if (c.getIndex() == null) {
				int i = 0;
				for (Pattern p : list)
					System.out.println((i++)+" "+p.getName()+" "+p.getAuthor());
			} else {
				Pattern p = list.get(c.getIndex());
				World w = null;
				if (c.getWorldType().equals(CommandLineOptions.WORLD_TYPE_AGING)) {
					w = new AgingWorld(p.getWidth(), p.getHeight());
				} else if (c.getWorldType().equals(CommandLineOptions.WORLD_TYPE_ARRAY)) {
					w = new ArrayWorld(p.getWidth(), p.getHeight());
				} else {
					w = new PackedWorld();
				}
				p.initialise(w);
				int userResponse = 0;
				while (userResponse != 'q') {
					w.print(new OutputStreamWriter(System.out));
					try {
						userResponse = System.in.read();
					} catch (IOException e) {}
					w = w.nextGeneration(0);
				}
			}
		}
		catch (PatternFormatException pfe) { System.out.println(pfe.getMessage()); }
		//catch (CommandLineException cle) { System.out.println(cle.getMessage()); }
		catch (NumberFormatException nf) { System.out.println("Error: second argument (index) must be an integer.");}
		catch (ArrayIndexOutOfBoundsException iob) { System.out.println("Error. Array Index out of bounds. Check Input values.");}
		//catch (Exception e) {System.out.println("Unknown Error.")}
	}
}