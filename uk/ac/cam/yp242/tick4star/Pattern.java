package uk.ac.cam.yp242.tick4star;

public class Pattern {
	private String name;
	private String author;
	private int width;
	private int height;
	private int startCol;
	private int startRow;
	private String cells;

	public String getName() {
		return name;
	}

	public String getAuthor(){
		return author;
	}

	public int getWidth(){
		return width;
	}

	public int getHeight(){
		return height;
	}

	public int getStartCol(){
		return startCol;
	}

	public int getStartRow(){
		return startRow;
	}

	public String getCells(){
		return cells;
	}

	public Pattern(String format) throws PatternFormatException{

		try {
			String[] splitString = format.split(":");
			name 	= 	splitString[0];
			author 	= 	splitString[1];
			width 	= 	Integer.parseInt(splitString[2]);
			height 	= 	Integer.parseInt(splitString[3]);
			startCol =	Integer.parseInt(splitString[4]);
			startRow = 	Integer.parseInt(splitString[5]);
			cells 	= 	splitString[6];
			}
			catch (ArrayIndexOutOfBoundsException iob) { throw new PatternFormatException("Error: Check number of arguments in Format String."); }
			catch (NumberFormatException nf) {throw new PatternFormatException("Error: Check formatting of numbers in Format String.");}
			catch (Exception e) { throw new PatternFormatException("Unknown2 Error");}
	}	 

	public void initialise(boolean[][] world) throws PatternFormatException{
		//make cells 2D array
			
		try {
			String[] split_cells = cells.split(" ");
			char[][] cellArray = new char[split_cells.length][split_cells[0].length()];
			for (int i = 0; i < split_cells.length; i++){
					cellArray[i] = (split_cells[i]).toCharArray();
			}

		//set new world
			for (int row = 0; row < cellArray.length; row++) {
				for (int col = 0; col < cellArray[row].length; col++){
					//setCell(world, (startCol + col), (startRow + row), (cellArray[row][col] == '1') ? true : false );
					if (cellArray[row][col] == '0' || cellArray[row][col] == '1') {
						if (row >= 0 && row < world.length && col >=0 && col < world[row].length)
							world[startRow + row][startCol + col] = (cellArray[row][col] == '1') ? true : false ;
					 }
					 else throw new NumberFormatException();
				} 
			}
		}
		catch (ArrayIndexOutOfBoundsException iob) { throw new PatternFormatException("Error: Check number of arguments in Format String."); }
		catch (NumberFormatException nf) { throw new PatternFormatException("Error: Cell patterns must contain only 0s or 1s.");}
		//catch (Exception e) { throw new PatternFormatException("Unknown2 Error");}
	}
}