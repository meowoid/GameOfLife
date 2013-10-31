package uk.ac.cam.yp242.tick5;

import uk.ac.cam.acr31.life.World;
import java.io.Writer;
import java.awt.Graphics;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;

public class TestArrayWorld implements World {

   private int generation;
   private int width;
   private int height;
   private boolean[][] cells;

   public TestArrayWorld(int w, int h) {
      width = w;
      height = h;
      // TODO: set generation equal to zero *
      generation = 0;
      // TODO: set cells to reference a new rectangular two-dimensional 
      //       boolean array of size height by width *
      cells = new boolean[height][width];
   }

   protected TestArrayWorld(TestArrayWorld prev) {
      width = prev.width;
      height = prev.height;
      // TODO: set generation equal to prev.generation+1 *
      generation = prev.generation + 1;
      // TODO: set cells to reference a new rectangular two-dimensional 
      //       boolean array of size height by width  *
      cells = new boolean[height][width];
   }

   public boolean getCell(int col, int row) { 
   if (row >= 0 && row < height && col >=0 && col < width)
      return cells[row][col];
   else 
      return false; 
   }

   public void setCell(int col, int row, boolean alive) {
      if (row >= 0 && row < height && col >=0 && col < width)
         this.cells[row][col] = alive;
   }

   public int getWidth()  { return width; }
   public int getHeight()  { return height; }
   public int getGeneration()  { return generation; }

   public int getPopulation()  { return 0; }

   public void print(Writer w)  { 
      PrintWriter pw = new PrintWriter(w);
      pw.print("-");
      pw.println();
      for (int row = 0; row < height; row ++) {
         for (int col = 0; col < width; col++) {
            pw.print(getCell(col,row) ? "#" : "_"); 
         }
         pw.println();
         pw.flush();
      }
   }

   public void draw(Graphics g, int width, int height)  { /*Leave empty*/ }

   public TestArrayWorld nextGeneration() {
      //Construct a new TestArrayWorld object to hold the next generation:
      TestArrayWorld new_world = new TestArrayWorld(this);
      int height = new_world.getHeight();
      int width = new_world.getWidth();

      //TODO: Use for loops with "setCell" and "computeCell" to populate "world"
      for (int row = 0; row < height; row++) {
         for (int col = 0; col < width; col++) {
               boolean state = computeCell(col,row);
               new_world.setCell(col, row, state);       
         }
      }
      return new_world;
   }

   public World nextGeneration(int log2StepSize)  { 
      TestArrayWorld world = this;
      int iter = 1<<log2StepSize;
      for(int i=0; i<iter;i++){
         world = world.nextGeneration();
      }
      return world;
   }

   public int countNeighbours(int col, int row) {
      int neighbours = 0;
      for (int irow = -1; irow < 2; irow++) {
         for (int icol = -1; icol < 2; icol++) {
            if (irow != 0 || icol != 0) {
              neighbours += this.getCell((col + icol),(row + irow)) ? 1 : 0;
      }}}
      return neighbours;
   }
   
   public boolean computeCell(int col, int row) {
      int neighbour = this.countNeighbours(col, row);
      boolean current_state = getCell(col,row);
      boolean next_state = false;
      
      if (current_state)
         if (neighbour < 2 || neighbour > 3)
            next_state = false;
         else 
            next_state = true;
      else
         if (neighbour == 3)
            next_state = true;
            
      return next_state;
   }

}