package uk.ac.cam.yp242.tick6star;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.FontMetrics;
import java.awt.geom.Rectangle2D;

public class DataPlot extends JPanel {

	private int width;
	private int height;
	private int GAPX = width/5;
	private int GAPY = height/5;

	private ArrayList<Double> generation;
	private ArrayList<Double> arr;
	
	public DataPlot(ArrayList<Double> array, ArrayList<Double> genArr){
		this.arr = array;
		this.generation = genArr;
	}

	protected void paintComponent(Graphics g) {
		double gen = generation.get(0);

		height = 4*getHeight()/5; //Actual graph takes up 80% of the space while axis labels..
		width = 4*getWidth()/5;		
		this.GAPX = width/5; 	//..and other space takes up the other 20%
		this.GAPY = height/5;

		g.setColor(java.awt.Color.WHITE); //Background is white
		g.fillRect(GAPX,0,width,height-GAPY);

		g.setColor(java.awt.Color.BLACK);  //Draw black lines for axis
		g.drawLine(GAPX,0,GAPX,height-GAPY);
		g.drawLine(GAPX,height-GAPY, width+GAPX, height -GAPY);

		drawGraph(g, arr); //Draw Graph from y values

		//Draw the axis labels, rounding the values to 2dp
		g.drawString(Double.toString(round(getMax(arr),2)),GAPX/4,10);
		g.drawString(Double.toString(round(getMin(arr),2)),GAPX/4,height-GAPY);
		g.drawString(Double.toString(gen-arr.size()), GAPX, height);
		g.drawString(Double.toString(gen),width , height);
	}

	private void drawGraph(Graphics g, ArrayList<Double> arr) {
		
		g.setColor(java.awt.Color.BLUE);
		int size = arr.size();
		double max = getMax(arr);
		double min = getMin(arr);
		double range = max - min;
		int x1,x2,y1,y2; 

		int previousX = GAPX; //previous (X,Y) coordinates
		int previousY = (height - GAPY) - (int)((arr.get(0)-min)/range)*(height - GAPY);	

		if (size == 1) g.drawLine(GAPX,(height-GAPY)/2,width+GAPX,(height-GAPY)/2);
		else {
			int spacing = (width)/(size - 1); //Spacing between points

			//Draw line from one y value to the next.
			for(int i = 0; i < size-1; i++) { 
				x1 = i*spacing + GAPX;
				y1 = (int)((max -arr.get(i))*(height-GAPY)/range);
				x2 = (i+1)*spacing + GAPX;
				y2 = (int)((max -arr.get(i+1))*(height-GAPY)/range);
				g.drawLine(x1,y1,x2,y2);
			}
		}
	}

	private double getMax(ArrayList<Double> arr) {
		double temp = arr.get(0);
		for(double i : arr) {
			if (i > temp) temp = i;
		}
		return temp;
	}

	private double getMin(ArrayList<Double> arr) {
		double temp = arr.get(0);
		for(double i : arr) {
			if (i < temp) temp = i;
		}
		return temp;
	}

	public void display() {
		repaint();
	}

	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}

}