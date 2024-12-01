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
    private ArrayList<Point> points = new ArrayList<>();
    private BufferedImage canvasImage;

    public CanvasPanel() {
    	
        setBackground(Color.WHITE);
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                points.add(e.getPoint());
                repaint();
            }
        });
        
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                points.add(e.getPoint());
                repaint();
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

    public void setCurrentColor(Color color) {
        this.currentColor = color;
    }

    public void setPenSize(int size) {
        this.penSize = size;
    }

    public void clearCanvas() {
        points.clear();
        repaint();
    }

    public void loadCanvasImage(BufferedImage image) {
        this.canvasImage = image;
        repaint();
    }
}
