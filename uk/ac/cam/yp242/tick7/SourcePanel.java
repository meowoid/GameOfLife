package uk.ac.cam.yp242.tick7;

//TODO: specify the appropriate import statements
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

abstract public class SourcePanel extends JPanel {

	private JRadioButton current;
	private JRadioButton none, file, library, fourStar;

	protected abstract boolean setSourceFile();
	protected abstract boolean setSourceNone();
	protected abstract boolean setSourceLibrary();


	public SourcePanel() {
		super();
		setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		none = new JRadioButton(Strings.BUTTON_SOURCE_NONE, true);
		file = new JRadioButton(Strings.BUTTON_SOURCE_FILE, true);
		library = new JRadioButton(Strings.BUTTON_SOURCE_LIBRARY, true);
		fourStar = new JRadioButton(Strings.BUTTON_SOURCE_FOURSTAR, true);  

		//add RadioButtons to this JPanel
		add(none);
		add(file);
		add(library);
		add(fourStar);

		//create a ButtonGroup containing all four buttons
		//Only one Button in a ButtonGroup can be selected at once
		ButtonGroup group = new ButtonGroup();
		group.add(none);
		group.add(file);
		group.add(library);
		group.add(fourStar);

		file.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (setSourceFile())
					//successful: file found and patterns loaded
					current = file;
				else
					//unsuccessful: re-enable previous source choice
					current.setSelected(true);
			}
		});

		library.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (setSourceLibrary())
					current = library;
				else
					current.setSelected(true);
			}
		});

		none.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (setSourceNone())
					current = none;
				else
					current.setSelected(true);
			}
		});

		current = none;
	}
}
