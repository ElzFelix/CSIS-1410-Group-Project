package gui_PaintApp;

import java.awt.Color;
import java.awt.Point;
import java.util.Objects;
/**
 * A class for holding information about points drawn on the canvas
 * <br>
 * Written by: Eleazar Felix
 * @param point
 * @param size
 * @param color
 */
public class DrawingPoint implements Undoable{
    public Point point;
    public int size;
    public Color color;

    /**
     * Creates a new DrawingPoint with the given Point(as coordinates), (int) size, and Color
     * <br>
     * Written by: Eleazar Felix
     * @param point
     * @param size
     * @param color
     */
    public DrawingPoint(Point point, int size, Color color) {
    	
        this.point = point;
        
        this.size = size;
        
        this.color = color;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        
        if (!(obj instanceof DrawingPoint)) return false;
        
        DrawingPoint other = (DrawingPoint) obj;
        
        return point.equals(other.point) && size == other.size && color.equals(other.color);
    }

    @Override
    public int hashCode() {
    	
        return Objects.hash(point, size, color);
    }
}
