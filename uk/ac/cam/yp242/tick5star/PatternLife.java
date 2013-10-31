package uk.ac.cam.yp242.tick5star;

class PatternLife {
	
   
	public static void main(String[] args) throws Exception {
		//String format: NAME:AUTHOR:WIDTH:HEIGHT:STARTCOL:STARTROW:CELLS
		Pattern p = new Pattern(args[0]);
		boolean[][] world = new boolean[p.getHeight()][p.getWidth()]; //makes new object for world
		p.initialise(world); //sets intitial game board
		play(world);
		 
	}
    
	public static boolean getCell(boolean[][] world, int col, int row) {
		//if (row < 0 || row > world.length - 1) || (col < 0 || col > world[row].length -1) return false;
		if (row >= 0 && row < world.length && col >=0 && col < world[row].length)
			return world[row][col];
		else 
			return false;
	}
	
	public static void setCell(boolean[][] world, int col, int row, boolean value) {
		if (row >= 0 && row < world.length && col >=0 && col < world[row].length)
			world[row][col] = value;
	}
	
	
	public static void print(boolean[][] world) {
		System.out.println("-");
		for (int row = 0; row < world.length; row ++) {
			for (int col = 0; col < world[row].length; col++) {
				System.out.print(getCell(world,col,row) ? "#" : "_"); 
			}
			System.out.println();
		}
	}

	public static int countNeighbours(boolean[][] world, int col, int row) {
		int neighbours = 0;
		for (int irow = -1; irow < 2; irow++) {
			for (int icol = -1; icol < 2; icol++) {
				if (irow != 0 || icol != 0) {
				  neighbours += getCell(world,(col + icol),(row + irow)) ? 1 : 0;
		}}}
		return neighbours;
	}
	
	public static boolean computeCell(boolean[][] world, int col, int row) {
		int neighbours = countNeighbours(world, col, row);
		boolean current_state = getCell(world, col,row);
		boolean next_state = false;
		
		if (current_state)
			if (neighbours < 2 || neighbours > 3)
				next_state = false;
			else 
				next_state = true;
		else
			if (neighbours == 3)
				next_state = true;
				
		return next_state;
	}
	
	public static boolean[][] nextGeneration(boolean[][] world) {
		boolean state = false;
		boolean[][] new_world = new boolean[world.length][world[0].length];
		for (int row2 = 0; row2 < world.length; row2++) {
			for (int col2 = 0; col2 < world[row2].length; col2++) {
					state = computeCell(world,col2,row2);
					setCell(new_world, col2, row2, state);			
			}
		}
		return new_world;	
	}

	public static void play(boolean[][] world) throws Exception {
			int userResponse = 0;
			while (userResponse != 'q') {
				print(world);
				userResponse = System.in.read();
				world = nextGeneration(world);
			}	
		}
}


