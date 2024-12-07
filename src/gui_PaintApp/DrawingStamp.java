package gui_PaintApp;

import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * A class for holding information on stamped images made using the stamp tool. 
 * Implements Undoable so that it can be undone
 * <br>
 * Written by: Christian Miller
 */
public class DrawingStamp implements Undoable
{
	public Point point;
	public BufferedImage img;
	
	/**
	 * Creates a new DrawingStamp with given point and image
	 * <br>
	 * Written by: Christian Miller
	 * @param point
	 * @param size
	 * @param file
	 */
	public DrawingStamp(Point point, BufferedImage img)
	{
		this.point = point;
		this.img = img;
	}

}
