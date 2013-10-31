package uk.ac.cam.yp242.tick3;

class ArrayLife {
	
   
	public static void main(String[] args) throws Exception {
		int size = Integer.parseInt(args[0]);
		long initial = Long.decode(args[1]);
		boolean[][] world = new boolean[size][size];
		for (int i = 0; i<8; i++){
			for (int j = 0; j<8; j++){
				world[i+size/2 -4][j + size/2 -4] = PackedLong.get(initial, i*8 +j);
			}
		}
		System.out.println(countNeighbours(world, 6, 6));
		System.out.println(computeCell(world,6,6));

		play(world);
	}
    
	public static boolean getCell(boolean[][] world, int col, int row) {
		if (col > (world[row].length - 1\) || row > (world.length - 1) || col < 0 || row < 0)
			return false;
		else 
			return world[row][col];

	}
	
	public static void setCell(boolean[][] world, int col, int row, boolean value) {
		if (!(col > (world[row].length - 1) || row > (world.length - 1) || col < 0 || row < 0))
			world[row][col] = value;		
	}
	
	// goes through all the cells and prints '#' if that cell is true and '_' if false.
	// prints new line all cells of a row have been checked.
	public static void print(boolean[][] world) {
		System.out.println("-");
		for (int row = 0; row <world.length; row ++) {
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
		boolean[][] new_world = new boolean[world.length][world[0].length];
		int width = world[0].length;
		int height = world.length;
		for (int row2 = 0; row2 < height; row2++) {
				for (int col2 = 0; col2 < width; col2++)
						setCell(new_world, col2, row2, (computeCell(world,col2,row2)));
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