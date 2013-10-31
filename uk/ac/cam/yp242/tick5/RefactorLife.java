package uk.ac.cam.yp242.tick5;
import java.util.LinkedList;
import java.util.List;
import java.io.OutputStreamWriter;
import java.io.Writer;
import uk.ac.cam.yp242.tick5.TestArrayWorld;
import uk.ac.cam.yp242.tick5.TestPackedWorld;
import uk.ac.cam.acr31.life.World;
import uk.ac.cam.acr31.life.WorldViewer;

class RefactorLife {
	
	public static void main(String[] args) throws Exception{
		List<Pattern> resultList = new LinkedList<Pattern>();
		try {

				if (args.length == 0) throw new PatternFormatException("Error: Specify the source of the patterns (at least).");
				String worldType = args.length == 3 ? args[0] : "--array";
				String source = args.length == 3 ? args[1] : args[0];
				resultList = (source.startsWith("http://")) ? PatternLoader.loadFromURL(source) : PatternLoader.loadFromDisk(source);
				int index = args.length == 3 ? Integer.parseInt(args[2]) : Integer.parseInt(args[1]);
				System.out.println(source);
				if (index<0) throw new PatternFormatException("Error: Index must be positive.");
				Pattern p = resultList.get(index);

				World world = null;
				if (worldType.equals("--array")){
					world = new ArrayWorld(p.getWidth(),p.getHeight());
				} else if (worldType.equals("--long")) {
					world = new PackedWorld();
				} else if (worldType.equals("--aging")) {
					world = new AgingWorld(p.getWidth(),p.getHeight());
				} else {
					throw new PatternFormatException("Error: leave switch blank, '--long' or '--array'.");
				}
				p.initialise(world);
				view(world);
			}

		catch (PatternFormatException error) { System.out.println(error.getMessage()); }
		catch (NumberFormatException nf) { System.out.println("Error: second argument (index) must be an integer.");}
		catch (ArrayIndexOutOfBoundsException e) {
			int i = 0;
			for (Pattern p: resultList){
			System.out.println(i+") " + convert(p));
			i++;
			}
		}
		catch (Exception e) {System.out.println("Unknown Error");}
	}

    public static String convert(Pattern p){
    	String str = p.getName() + ":" + p.getAuthor() + ":" + p.getWidth() + ":" + p.getHeight() + ":" + p.getStartCol() + ":" + p.getStartRow() + ":" + p.getCells();
    	return str;
    }

	public static void play(World world) throws Exception {
			int userResponse = 0;
			Writer w = new OutputStreamWriter(System.out);
			while (userResponse != 'q') {
				world.print(w);
				userResponse = System.in.read();
				world = world.nextGeneration(0);
			}	
		}

	public static void view(World world) throws Exception {
		int userResponse = 0;
		WorldViewer viewer = new WorldViewer();
		while(userResponse != 'q') {
			viewer.show(world);
			userResponse = System.in.read();
			world = world.nextGeneration(0);
		}
		viewer.close();
	}

}


