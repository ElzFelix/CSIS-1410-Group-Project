package gui_PaintApp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

public class CanvasPanel extends JPanel {
	
    private Color currentColor = Color.BLACK;
    private int penSize = 5;
    public ArrayList<Point> points = new ArrayList<>();
    private BufferedImage canvasImage;
    private BufferedImage stampImage; //image for stamp tool
    
    private boolean isErasing = false;
    private boolean isStamping = false;


	/**
     * Creates the panel containing the drawing canvas and adds listeners for mouse.
     * <br>
     * Created by: Eleazar Felix
     * <br>
     * Christian Miller cleaned up whitespace
     * <br>
     * Doc comment written by: Christian Miller 
     */
    public CanvasPanel() {
    	
    	
        setBackground(Color.WHITE);
        
        addMouseListener(new MouseAdapter() {
            @Override
            //whenever the mouse is clicked
            public void mousePressed(MouseEvent e) {
            	
                if (isErasing) {
                    erase(e.getPoint());
                    
                } 
                else if (isStamping && stampImage != null) {
                    stamp(e.getPoint());
                    
                } 
                else {
                    points.add(e.getPoint());
                    repaint();
                }
            }
            
            //saves whenever the mouse is released
            @Override
            public void mouseReleased(MouseEvent e) {
                PaintApplication.saveStateForUndo(points);
            }
            
        });
        
        //whenever the mouse is dragged
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isErasing) {
                    erase(e.getPoint());
                } else {
                    points.add(e.getPoint());
                    repaint();
                }
            }
        });
        
    }
    
    @Override
    protected void paintComponent(Graphics g) {
    	
        super.paintComponent(g);
        
        if (canvasImage != null) {
            g.drawImage(canvasImage, 0, 0, null);
        }
        
        g.setColor(currentColor);
        
        for (Point point : points) {
            g.fillOval(point.x, point.y, penSize, penSize);
        }
    }
    

    /**
     * * Written by: Eleazar Felix
     * <br>
     * setter for current color
     */
    public void setCurrentColor(Color color) {
        this.currentColor = color;
    }

    /**
     * * Written by: Eleazar Felix
     * <br>
     * setter for pen size
     */
    public void setPenSize(int size) {
        this.penSize = size;
    }

    /**
     * * Written by: Eleazar Felix
     * <br>
     * Clears the canvas
     * 
     */
    public void clearCanvas() {
        points.clear();
        repaint();
    }

    public void loadCanvasImage(BufferedImage image) {
        this.canvasImage = image;
        repaint();
    }
    
    /**
     * Written by: Eleazar Felix
     * <br>
     * erases points within a radius of the penSize
     * Chris's note: I think it would be better to just have it function the same as the pen, but in white
     */
    private void erase(Point point) {
    	
        points.removeIf(p -> p.distance(point) <= penSize);
        repaint();
        
    }

    /**
     * Written by: Eleazar Felix
     * <br>
     * creates a stamp at the given point. not functional yet
     */
    private void stamp(Point point) {
    	
        if (stampImage != null) {
            Graphics g = getGraphics();
            
            g.drawImage(stampImage, point.x - stampImage.getWidth() / 2, point.y - stampImage.getHeight() / 2, null);
            
            repaint();
        }
    }

	/**
     * Written by: Christian Miller
     * <br>
     * 
     * Auto-generated getter or setter
     */
	public BufferedImage getCanvasImage()
	{
		return canvasImage;
	}

	/**
     * Written by: Christian Miller
     * <br>
     * 
     * Auto-generated getter or setter
     */
	public void setCanvasImage(BufferedImage canvasImage)
	{
		this.canvasImage = canvasImage;
	}

	/**
     * Written by: Christian Miller
     * <br>
     * 
     * Auto-generated getter or setter
     */
	public Color getCurrentColor()
	{
		return currentColor;
	}

	/**
     * Written by: Christian Miller
     * <br>
     * 
     * Auto-generated getter or setter
     */
	public int getPenSize()
	{
		return penSize;
	}
	
	/**
     * Written by: Christian Miller
     * <br>
     * 
     * Auto-generated getter or setter
     */
	public boolean isErasing()
	{
		return isErasing;
	}

	/**
     * Written by: Christian Miller
     * <br>
     * 
     * Auto-generated getter or setter
     */
	public void setErasing(boolean isErasing)
	{
		this.isErasing = isErasing;
	}

	/**
     * Written by: Christian Miller
     * <br>
     * 
     * Auto-generated getter or setter
     */
	public boolean isStamping()
	{
		return isStamping;
	}

	/**
     * Written by: Christian Miller
     * <br>
     * 
     * Auto-generated getter or setter
     */
	public void setStamping(boolean isStamping)
	{
		this.isStamping = isStamping;
	}
    
    
}
