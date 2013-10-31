package uk.ac.cam.yp242.tick5;

import uk.ac.cam.acr31.life.World;
import java.io.Writer;
import java.awt.Graphics;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;

public class PackedWorld extends WorldImpl {

   private long cells;
   private int generation;

   public PackedWorld() {
      super(8,8);
      cells = 0L;
   }

   protected PackedWorld(PackedWorld prev) {
      super(prev);
      generation = prev.getGeneration() + 1;
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

   public PackedWorld nextGeneration() {
      PackedWorld new_world = new PackedWorld(this);
      boolean next = false;
      for (int row2 = 0; row2 < new_world.getHeight(); row2++) {
         for (int col2 = 0; col2 <new_world.getWidth(); col2++) {
            next = computeCell(col2, row2);
            new_world.setCell(col2, row2, next);    
         }
      }
      return new_world;
   }
}