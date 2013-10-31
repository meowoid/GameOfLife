package uk.ac.cam.yp242.tick7;

//TODO:  specify the appropriate import statements
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.util.List;
import java.util.ArrayList;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

abstract public class PatternPanel extends JPanel {

  private JList guiList;
  private Pattern currentPattern;
  private List<Pattern> patternList = new ArrayList<Pattern>();

  protected abstract void onPatternChange();

  public PatternPanel() {
    super();
    setLayout(new BorderLayout());
    guiList = new JList();
    add(new JScrollPane(guiList));
    currentPattern = null;
    guiList.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
          if (!e.getValueIsAdjusting() && (patternList != null)) {
            int sel = guiList.getSelectedIndex();
            if (sel != -1) {
              currentPattern = patternList.get(sel);
              onPatternChange();
            }
        }
      }
    });
  }

  public void setPatterns(List<Pattern> list) {
    patternList = list;
    if (list == null) {
      currentPattern = null;
      guiList.setListData(new String[]{});
      return;
    }

    ArrayList<String> names = new ArrayList<String>();
    for(Pattern p : list) {
      String name = p.getName();
      String author = p.getAuthor();
      String formatted = name + " (" + author + ")";
      names.add(formatted); 
    }
    guiList.setListData(names.toArray());   
    currentPattern = list.get(0); //set first element in list and guiList
    guiList.setSelectedIndex(0);

  }

  public Pattern getCurrentPattern(){
      return currentPattern;
    }
}
