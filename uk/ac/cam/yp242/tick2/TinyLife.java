package uk.ac.cam.yp242.tick2;

class TinyLife {
	
   
	public static void main(String[] args) throws Exception {
		
		play(Long.decode(args[0]));
		 
		}
    
	public static boolean getCell(long world, int col, int row) {
		if (col > 7 || row > 7 || col < 0 || row < 0)
			return false;
			else {
				int bit = 8*row + col;
				return PackedLong.get(world, bit);
			}
	}
	
	public static long setCell(long world, int col, int row, boolean value) {
		long nworld = 0L;
		if (col < 8 && row < 8 && col >= 0 && row >= 0) {
			int bit = 8*row + col;
			nworld = PackedLong.set(world, bit, value);			
		}
		return nworld;
	}
	
	
	// goes through all the cells and prints '#' if that cell is true and '_' if false.
	// prints new line all cells of a row have been checked.
	public static void print(long world) {
		System.out.println("-");
		for (int row = 0; row <8; row ++) {
			for (int col = 0; col < 8; col++) {
				System.out.print(getCell(world,col,row) ? "#" : "_"); 
			}
			System.out.println();
		}
	}

	public static int countNeighbours(long world, int col, int row) {
		int neighbours = 0;
		for (int irow = -1; irow < 2; irow++) {
			for (int icol = -1; icol < 2; icol++) {
				if (irow != 0 || icol != 0) {
				  neighbours += getCell(world,(col + icol),(row + irow)) ? 1 : 0;
		}}}
		return neighbours;
	}
	
	public static boolean computeCell(long world, int col, int row) {
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
	
	public static long nextGeneration(long world) {
		long new_world = 0x0L;
		//boolean next = false;
		for (int row2 = 0; row2 < 12; row2++) {
			for (int col2 = 0; col2 < 12; col2++) {
				//next = computeCell(world, col, row)
					new_world = setCell(new_world, col2, row2, (computeCell(world,col2,row2)));
				
			}
		}
		return new_world;		
	}

	public static void play(long world) throws Exception {
			int userResponse = 0;
			while (userResponse != 'q') {
				print(world);
				userResponse = System.in.read();
				world = nextGeneration(world);
			}	
		}
}


