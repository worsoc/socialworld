// https://forum.byte-welt.net/t/mithilfe-von-koordinaten-und-farbe-ein-pixel-zeichnen/4475/6
// https://stackoverflow.com/questions/4392722/how-to-repaint-a-jpanel-after-have-drawn-on-it

package org.socialworld.tools.mct;

import javax.swing.JPanel;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Color;

public class ShowMapPanel extends JPanel {

	
    private BufferedImage image;
    
    public ShowMapPanel(int width, int height)
    {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        
    	int x;
    	int y;
    	int [] [] colorsForXY = new int[729][729];
       	for( x = 0; x < 729; x++) {
    		for (y = 0; y < 729; y++) {
    			colorsForXY[x][y] = (new Color(x % 256, y % 256, (x + y) % 256)).getRGB();
    		}
       	}
    	setPixels(colorsForXY);
        //g.dispose();
    }
    
    public void setPixels(int [] [] colorsForXY) {
    	int x;
    	int y;
    	for( x = 0; x < 729; x++) {
    		for (y = 0; y < 729; y++) {
    			setPixel(x, y, colorsForXY[x][y]);
    		}
    	}
    	
    }
    
    private void setPixel(int x, int y, int colorRGB)
    {
        image.setRGB(x, y, colorRGB);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);

    }
	
}


/*
 * 
 *    private static void createAndShowGUI()
    {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        PixelImageComponent pixelImageComponent =
            new PixelImageComponent(100,100);
        f.getContentPane().add(pixelImageComponent);
        
        pixelImageComponent.setPixel(10,10, Color.RED);
        pixelImageComponent.setPixel(11,10, Color.GREEN);
        pixelImageComponent.setPixel(12,10, Color.BLUE);
        pixelImageComponent.setPixel(13,10, Color.CYAN);
        pixelImageComponent.setPixel(14,10, Color.MAGENTA);
        pixelImageComponent.setPixel(15,10, Color.YELLOW);
        
        f.setSize(200,200);
        f.setVisible(true);
    }

 * 
 * 
 * 
 * 
 * 
 * 
 * public class Main extends javax.swing.JFrame {
...

public Main() {                                   //This works great.
    board = new Board( ... );
    somePanel.add(board, BorderLayout.CENTER);

}

public void someButtonActionPerformed(Event e) { //This is not working

    somePanel.remove(board);
    board = new Board( ... );
    somePanel.add(board);
    somePanel.invalidate()
    board.repaint();
}
 * 
 */
