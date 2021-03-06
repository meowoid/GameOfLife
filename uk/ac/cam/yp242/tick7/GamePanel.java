package uk.ac.cam.yp242.tick7;

import uk.ac.cam.acr31.life.World;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;


public class GamePanel extends JPanel {

    private int zoom = 10; //Number of pixels used to represent a cell
    private int width = 400; //Width of game board in pixels
    private int height = 400;//Height of game board in pixels
    private World current = null;

    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    protected void paintComponent(Graphics g) {
        if (current == null) return;
        g.setColor(java.awt.Color.WHITE);
        g.fillRect(0, 0, width, height);
        current.draw(g, width, height);
        int cellsh = height/zoom;
        int cellsw = width/zoom;
        if (zoom > 4) {
            g.setColor(java.awt.Color.LIGHT_GRAY);

            for(int i =0; i < cellsw; i++){
                g.drawLine(i*zoom,0,i*zoom,height);
            }
            for(int j = 0; j<cellsh; j++){
                g.drawLine(0,j*zoom,width,j*zoom);
            }
        }
    }
    public void setZoom(int value) {
        zoom = value;
    }
    private void computeSize() {
        if (current == null)  return;
        int newWidth = current.getWidth() * zoom;
        int newHeight = current.getHeight() * zoom;
        if (newWidth != width || newHeight != height) {
            width = newWidth;
            height = newHeight;
            revalidate(); //trigger the GamePanel to re-layout its components
        }
    }

    public void display(World w) {
        current = w;
        computeSize();
        repaint();
    }
}
