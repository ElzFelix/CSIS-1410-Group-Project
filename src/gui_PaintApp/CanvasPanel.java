package gui_PaintApp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

public class CanvasPanel extends JPanel {
	
    private Color currentColor = Color.BLACK;
    private int penSize = 5;
    public ArrayList<DrawingPoint> points = new ArrayList<>();
    private BufferedImage canvasImage;
    private BufferedImage stampImage; //image for stamp tool
    
    private boolean isErasing = false;
    private boolean isStamping = false;
    private Color lastUsedColor = Color.BLACK;
    private int lastUsedPenSize = 5;

    private Point lastPoint;
    
    
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
            	
            	lastPoint = e.getPoint();
            	
                if (isErasing) {
                    erase(e.getPoint());
                    
                } else if (isStamping && stampImage != null) {
                    stamp(e.getPoint());
                    
                } else {
                	
                    points.add(new DrawingPoint(e.getPoint(), penSize, currentColor));
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
                	
                	double x = lastPoint.getX();
                	double y = lastPoint.getY();
                	
                	//draws a line between last position and current position,
                	//written by christian miller
                	while (Point.distance(x, y, e.getPoint().getX(), e.getPoint().getY()) >= 2)
                	{
                		x += Math.signum(e.getPoint().getX() - x);
                		y += Math.signum(e.getPoint().getY() - y);
                		
                		points.add(new DrawingPoint(new Point((int) x, (int) y), penSize, currentColor));
                	}
                	
                    repaint();
                }
                
                lastPoint = e.getPoint();
            }
        });
        
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (canvasImage != null) {
            g.drawImage(canvasImage, 0, 0, null);
        }

        for (DrawingPoint dp : points) {
            g.setColor(dp.color);
            g.fillOval(dp.point.x, dp.point.y, dp.size, dp.size);
        }
    }
    

    /**
     * * Written by: Eleazar Felix
     * <br>
     * sets the pen size and stores it as the last used pen size.
     */
    public void setCurrentColor(Color color) {
        this.currentColor = color;
        this.lastUsedColor = color;
    }

    /**
     * * Written by: Eleazar Felix
     * <br>
     * Set the pen size and stores it as the last used pen size.
     */
    public void setPenSize(int size) {
        this.penSize = size;
        this.lastUsedPenSize = size;
    }
    
    /**
     * returns
     * @return
     */
    public Color getLastUsedColor () {
    	return lastUsedColor;
    }
    
    public int getLastUsedPenSize() {
    	return lastUsedPenSize;
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

    /**
     * Written by: Eleazar Felix
     * <br>
     * clears the canvas and loads an image into it, centered and resized.
     */
    public void loadCanvasImage(BufferedImage image) {
    	
    	//opening an image should clear the canvas -chris
    	clearCanvas();
    	
    	//made loaded image scale to canvas -chris
    	//nvm this code doesn't work
    	int width = image.getWidth();
    	int height = image.getHeight();
    	
    	if (width > this.getWidth() || width < this.getWidth())
    	{
    		width = this.getWidth();
    		
    		height += (width - image.getWidth());
    	}
    	
    	Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
    	//hopefully this works
    	
        this.canvasImage = (BufferedImage) scaledImage;
        repaint();
    }
    
    /**
     * Written by: Eleazar Felix
     * <br>
     * erases points within a radius of the penSize
     * Chris's note: I think it would be better to just have it function the same as the pen, but in white
     */
    private void erase(Point point) {
    	
    	points.removeIf(dp -> dp.point.distance(point) <= penSize);
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
