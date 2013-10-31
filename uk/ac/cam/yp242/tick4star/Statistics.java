package uk.ac.cam.yp242.tick4star;
import java.util.ArrayList;
import uk.ac.cam.yp242.tick4star.Pattern;
import java.util.Arrays;

public class Statistics {

	private double MaximumDeathRate;
	private double MaximumGrowthRate;
	private int LoopStart;
	private int LoopEnd;
	private int MaximumPopulation;
	
	private Pattern p;
	
	boolean[][] world;

	public Statistics(Pattern p) throws PatternFormatException{
		this.p = p;
		this.world = new boolean[p.getHeight()][p.getWidth()];
		p.initialise(this.world);
		initGens();
	}
	
	private int countLive(boolean[][] world) { //counts number of live cells in a given world
		int counter = 0;
		int rows = world.length;
		int cols = world[0].length;
		
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				if (world[r][c]) counter++;
			}
		}
		return counter;
	}
	
	
	private void initGens() { //initialises generations array and does all the checks
		
		//local variables to store information of current world to compare to the global max values
		int i = 0;
		int live = 0;
		double growth;
		double death;
		boolean[][] nextWorld;
		boolean test = true;
		ArrayList generations = new ArrayList<boolean[][]>();
		
		
		while(test){

			//if current world is in generations array, break loop and return indexes
			if(isIn(generations, world)) {
				LoopEnd = i - 1;
				for (Object o : generations) {
					if (Arrays.deepEquals(((boolean[][])o),world)) LoopStart = generations.indexOf(o);
				}
				test = false;
				break;
			}

			generations.add(world);
			live = countLive(world);
			nextWorld = StatisticsLife.nextGeneration(world);
			double dlive = live; //double type for real division (live cells for world)
			double dnlive = countLive(nextWorld); //(number of live cells for next world)

			if (live > MaximumPopulation) MaximumPopulation = live;

			growth = (dnlive - dlive)/dlive;
			if (growth > MaximumGrowthRate) MaximumGrowthRate = growth;
			
			death = (dlive - dnlive)/dlive;
			if (death > MaximumDeathRate) MaximumDeathRate = death;

			world = nextWorld;
			i++;
		}
	}
	
	public boolean isIn(ArrayList<boolean[][]> gens, boolean[][] w) {
		//check each cell ..
		int size = gens.size();
		boolean[][] world;
		boolean brk = false;
		int height, width;
		
	  skipworld:
		for(int i = 0; i < size; i++) { 			//for each previous world
			world = gens.get(i);
			height = world[0].length;
			width = world.length;

			for(int r = 0; r < width; r ++){		//for each row of the each world
				for(int c = 0; c < height; c++) {	//check each individual cell
					if (world[r][c] != w[r][c]) {
						continue skipworld;
					}
				}
			}
		return true; //if you finish a world without hitting the continue statement
		}
		return false; //if you finish going through all worlds without hitting return true
	}

	public double getMaximumGrowthRate() {
		return MaximumGrowthRate;
	}

	public double getMaximumDeathRate() {
		return MaximumDeathRate;
	}

	public int getLoopStart() {
		return LoopStart;
	}

	public int getLoopEnd() {
		return LoopEnd;
	}

	public int getCycleLength() {
		return (LoopEnd - LoopStart);
	}

	public int getMaximumPopulation() {
		return MaximumPopulation;
	}

}