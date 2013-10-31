package uk.ac.cam.yp242.tick5;

import uk.ac.cam.acr31.life.World;
import java.io.Writer;
import java.awt.Graphics;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;

public class TestPackedWorld implements World {

   private int generation;
   private int width;
   private int height;
   private long cells;

   public TestPackedWorld() {
      width = 8;
      height = 8;
      // TODO: set generation equal to zero *
      generation = 0;
      // TODO: set cells to reference a new rectangular two-dimensional 
      //       boolean array of size height by width *
      cells = 0L;
   }

   protected TestPackedWorld(TestPackedWorld prev) {
      width = 8;
      height = 8;
      // TODO: set generation equal to prev.generation+1 *
      generation = prev.generation + 1;
      // TODO: set cells to reference a new rectangular two-dimensional 
      //       boolean array of size height by width  *
      cells = 0L;
   }

   public boolean getCell(int col, int row) { 
      if (col > 7 || row > 7 || col < 0 || row < 0)
         return false;
      else {
            int bit = 8*row + col;
            return PackedLong.get(cells, bit);
            }
   }

   public void setCell(int col, int row, boolean alive) {
      long nworld = 0L;
      if (col < 8 && row < 8 && col >= 0 && row >= 0) {
         int bit = 8*row + col;
         nworld = PackedLong.set(cells, bit, alive);        
      }
      cells = nworld;
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

   public TestPackedWorld nextGeneration() {
      TestPackedWorld new_world = new TestPackedWorld(this);
      boolean next = false;
      for (int row2 = 0; row2 < new_world.getHeight(); row2++) {
         for (int col2 = 0; col2 <new_world.getWidth(); col2++) {
            next = computeCell(col2, row2);
            new_world.setCell(col2, row2, next);
            
         }
      }
      return new_world;
   }

   public World nextGeneration(int log2StepSize)  { 
      TestPackedWorld world = this;
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
              neighbours += getCell((col + icol),(row + irow)) ? 1 : 0;
      }}}
      return neighbours;
   }
   
   public boolean computeCell(int col, int row) {
      int neighbours = countNeighbours(col, row);
      boolean current_state = getCell(col,row);
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

}