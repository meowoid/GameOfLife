package uk.ac.cam.yp242.tick6star;

import uk.ac.cam.acr31.life.World;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.BorderLayout;
import javax.swing.JLabel;

public class Graph extends JPanel{
	DataPlot dataPlot;

	public Graph(String title, ArrayList<Double> arr, ArrayList<Double> genArr){
		super();
		setLayout(new BorderLayout()); //Graph of BorderLayout so the DataPlot can resize

		JLabel label = new JLabel(title); //Title of graph
		label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		add(label, BorderLayout.NORTH);

		//Y values and generation passed along to DataPlot which returns the graph
		dataPlot = new DataPlot(arr, genArr);
		add(dataPlot, BorderLayout.CENTER);
	}

	public void display(){
		dataPlot.display();
	}

}