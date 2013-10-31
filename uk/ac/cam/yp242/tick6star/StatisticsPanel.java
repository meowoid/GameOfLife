package uk.ac.cam.yp242.tick6star;

import uk.ac.cam.acr31.life.World;
import javax.swing.JPanel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.util.ArrayList;
import java.awt.Graphics;
import java.text.DecimalFormat;


public class StatisticsPanel extends JPanel {

	private DecimalFormat df = new DecimalFormat("###.##");

	private double generation;
	private double population;
	private double maxPop;
	private double minPop = -1; //error value
	private double maxGrowth;
	private double maxDeath;

	private JLabel genLabel, popLabel, maxPopLabel, minPopLabel, maxGrowthLabel, maxDeathLabel;
	private Graph popGraph, popChangeGraph, growthGraph;

	private ArrayList<Double> popArr = new ArrayList<Double>();
	private ArrayList<Double> popChangeArr = new ArrayList<Double>();
	private ArrayList<Double> growthArr = new ArrayList<Double>();
	private ArrayList<Double> genArr = new ArrayList<Double>();


	public StatisticsPanel() {
		super();
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        
        // Add the stat labels and values
        add(Box.createVerticalStrut(10)); 

        genLabel = createLabel(Strings.LABEL_GEN, round(generation,0));
        popLabel = createLabel(Strings.LABEL_POP, round(population,0));
        maxPopLabel = createLabel(Strings.LABEL_MAX_POP, round(maxPop,2));
        minPopLabel = createLabel(Strings.LABEL_MIN_POP, round(minPop,0));
        maxGrowthLabel = createLabel(Strings.LABEL_MAX_GROWTH, round(maxGrowth,0));
        maxDeathLabel = createLabel(Strings.LABEL_MAX_DEATH, round(maxDeath,2));

        add(Box.createVerticalStrut(10)); 
        genArr.add(0,0.0);

        // Add the graphs, passing the title, y values and the generation
        popGraph = createGraph(Strings.LABEL_POP, popArr, genArr);
        popChangeGraph = createGraph(Strings.GRAPH_POPCHANGE, popChangeArr, genArr);
        growthGraph = createGraph(Strings.GRAPH_GROWTH_RATE, growthArr, genArr);
	}

	//Calculates and updates variables. Updates the labels by changing the variable.
	//Updates the Arrays with the new calculated variables. Repaints the Graphs.
	public void update(World w) {
		this.population = w.getPopulation();
		this.generation = w.getGeneration();
		genArr.set(0,generation); //to pass by reference

		if (population > maxPop) maxPop = population;
		if (population < minPop || minPop == -1) minPop = population;

		World nextWorld = w.nextGeneration(0);
		double nextPop = nextWorld.getPopulation();
		double tGrowth = (nextPop - population)/population;
		double tDeath = (population - nextPop)/population;
		if (tGrowth > maxGrowth) maxGrowth = tGrowth;
		if (tDeath > maxDeath) maxDeath = tDeath;

		setLabel(genLabel, Strings.LABEL_GEN, generation);
		setLabel(popLabel, Strings.LABEL_POP, population);
		setLabel(maxPopLabel, Strings.LABEL_MAX_POP, maxPop);
		setLabel(minPopLabel, Strings.LABEL_MIN_POP, minPop);
		setLabel(maxGrowthLabel, Strings.LABEL_MAX_GROWTH, maxGrowth);
		setLabel(maxDeathLabel, Strings.LABEL_MAX_DEATH, maxDeath);

		updateArray(population, popArr);
		updateArray((nextPop - population), popChangeArr);
		updateArray(tGrowth, growthArr);

		//Repaint graphs
		popGraph.display();
		popChangeGraph.display();
		growthGraph.display();
	}

	//create Labels from text and value adding them to the Panel as well
	private JLabel createLabel(String text, double stat) { 
		String string = df.format(stat);
		JLabel result = new JLabel(text + ": "+ string);
		add(result);
		return result;
	}

	//Change the value of the variable in a Label
	private void setLabel(JLabel label, String text, double stat){
		String string = df.format(stat);
		label.setText(text + ": " + string);
	}

	//Array of y values: A queue, max length 100.
	private void updateArray(double val, ArrayList<Double> arr) {
		if (arr.size() > 100) arr.remove(0);
		arr.add(val);
	}

	//Creates a Graph by passing title, y values and generation
	private Graph createGraph(String title, ArrayList<Double> arr, ArrayList<Double> genArr){
		Graph result = new Graph(title, arr, genArr);
		add(result);
		return result;
	}

	//A function to round a double to a given number of decimal places
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
}