package uk.ac.cam.yp242.tick6star;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.util.List;
import java.util.ArrayList;

public class PatternPanel extends JPanel {

  private JList guiList;

  public PatternPanel() {
    super();
    setLayout(new BorderLayout());
    guiList = new JList();
    add(new JScrollPane(guiList));
  }

  public void setPatterns(List<Pattern> list) {
    ArrayList<String> names = new ArrayList<String>();

    for(Pattern p : list) {
      String name = p.getName();
      String author = p.getAuthor();
      String formatted = name + " (" + author + ")";
      names.add(formatted);
    }

    guiList.setListData(names.toArray());
  } 
}
