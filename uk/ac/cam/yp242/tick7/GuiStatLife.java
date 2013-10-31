package uk.ac.cam.yp242.tick7;

import uk.ac.cam.acr31.life.World;
import java.awt.BorderLayout;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import java.io.File;
import java.io.FileReader;


public class GuiStatLife extends JFrame {

	PatternPanel patternPanel;
	ControlPanel controlPanel;
	GamePanel gamePanel;
	StatisticsPanel statisticsPanel;

	private World world;
	private int timeDelay = 500;	//time delay between updates in ms
	private int timeStep = 0; 		//progress by 2^timeStep each time

	public GuiStatLife() {
		super("GUI Life");
		setSize(640,480);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		JComponent optionsPanel = createOptionsPanel();
		add(optionsPanel, BorderLayout.WEST);

		JComponent gamePanel = createGamePanel();
		add(gamePanel, BorderLayout.CENTER);

		JComponent sPanel = createStatisticsPanel();
		add(sPanel, BorderLayout.EAST);

	}

	private void addBorder(JComponent component, String title) {
		Border etch = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		Border tb = BorderFactory.createTitledBorder(etch,title);
		component.setBorder(tb);
	}

	private JComponent createOptionsPanel() {
		Box result = Box.createVerticalBox();
		result.add(createSourcePanel());
		result.add(createPatternPanel());
		result.add(createControlPanel());
		return result;
	}

	private JComponent createStatisticsPanel() {
		Box result = Box.createVerticalBox();
		statisticsPanel = new StatisticsPanel();
		addBorder(statisticsPanel, Strings.PANEL_STATISTICS);
		result.add(statisticsPanel);
		return result;
	}


	private JComponent createGamePanel() {
		gamePanel = new GamePanel();
		JPanel holder = new JPanel();
		addBorder(holder,Strings.PANEL_GAMEVIEW);
		JPanel result = gamePanel;
		holder.add(result);

		return new JScrollPane(holder);
	}

	private JComponent createSourcePanel() {
		JPanel result = new SourcePanel(){
			protected boolean setSourceFile() {
				JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showOpenDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File f = chooser.getSelectedFile();
					try {
						List<Pattern> list = PatternLoader.load(new FileReader(f));
						patternPanel.setPatterns(list);
						resetWorld();
						return true;
					} catch (IOException ioe) {}
				}
				return false;
			}
			protected boolean setSourceNone() {
				world = null;
				patternPanel.setPatterns(null);
				resetWorld();
				return true;
			}
			protected boolean setSourceLibrary() {
				String u = "http://www.cl.cam.ac.uk/teaching/current/ProgJava/nextlife.txt";
				return setSourceWeb(u);
			}
			private boolean setSourceWeb(String url) {
				try {
					List<Pattern> list = PatternLoader.loadFromURL(url);
					patternPanel.setPatterns(list);
					resetWorld();
					return true;
				} catch (IOException ioe) {}
				return false;
			}
		};

		addBorder(result,Strings.PANEL_SOURCE);
		return result; 
	}

	private JComponent createPatternPanel() {
		patternPanel = new PatternPanel(){
			protected void onPatternChange() {
				resetWorld();
			}
		};
		JPanel result = patternPanel;
		addBorder(result, Strings.PANEL_PATTERN);
		return result;
	}

 	private JComponent createControlPanel() {
 		controlPanel = new ControlPanel() {
 			protected void onSpeedChange(int value) 
 				{ playTimer.setDelay(1+(100-value)*10); } //define here so we have access to the local vars.
 			protected void onStepChange(int value)			
 				{ timeStep = value; }
 			protected void onZoomChange(int value)
	 				{ gamePanel.setZoom(value); }

 		};
 		JPanel result = controlPanel;
 		return controlPanel;
 	}

 	private Timer playTimer = new Timer(timeDelay, new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				doTimeStep();
			}
		});

	private void doTimeStep() {
		if (world != null) {
			world = world.nextGeneration(timeStep);
			gamePanel.display(world);
			statisticsPanel.update(world);
		}
	}

	private void resetWorld() {
		Pattern current = patternPanel.getCurrentPattern();
		world = null;
		if (current != null) {
			try {
				world = controlPanel.initialiseWorld(current);
				statisticsPanel.update(world);
			} catch (PatternFormatException e) {
				JOptionPane.showMessageDialog(this,
					"Error initialising world",
					"An error occurred when initialising the world. "+e.getMessage(),
					JOptionPane.ERROR_MESSAGE);
			}
		}
		gamePanel.display(world);

		repaint();
	}

	public static void main(String[] args) {
		GuiStatLife gui = new GuiStatLife();
		gui.playTimer.start();
		gui.resetWorld();
		gui.setVisible(true);
	}
}
