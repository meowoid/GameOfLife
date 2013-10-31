package uk.ac.cam.yp242.tick6star;

import uk.ac.cam.acr31.life.World;


public class ArrayWorld extends WorldImpl {

   private boolean[][] cells;
   private int generation;

   public ArrayWorld(int width, int height) {
      super(width,height);
      generation = 0;
      cells = new boolean[height][width];
   }

   protected ArrayWorld(ArrayWorld prev) {
      super(prev);
      this.generation = prev.getGeneration() + 1;
      cells = new boolean[prev.getHeight()][prev.getWidth()];
   }

   public boolean getCell(int col, int row) { 
   int w = cells[0].length;
   int h = cells.length;
   if (row >= 0 && row < h && col >=0 && col < w)
      return cells[row][col];
   else 
      return false; 
   }

   public void setCell(int col, int row, boolean alive) {
      int h = cells.length;
      int w = cells[0].length;
      if (row >= 0 && row < h && col >=0 && col < w)
         cells[row][col] = alive;
   }

   public ArrayWorld nextGeneration() {
      //Construct a new TestArrayWorld object to hold the next generation:
      ArrayWorld new_world = new ArrayWorld(this);
      int height = new_world.getHeight();
      int width = new_world.getWidth();

      for (int row = 0; row < height; row++) {
         for (int col = 0; col < width; col++) {
               boolean state = computeCell(col,row);
               new_world.setCell(col, row, state);       
         }
      }
      return new_world;
   }

}
