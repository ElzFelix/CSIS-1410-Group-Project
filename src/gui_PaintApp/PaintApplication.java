package gui_PaintApp;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PaintApplication extends JFrame {

    private static final long serialVersionUID = 1L;

    // Panels and components
    private JPanel canvasPanel;
    private JPanel controlPanel;
    private JSlider redSlider, greenSlider, blueSlider, penSizeSlider;
    private JButton btnEraseTool, btnStampTool, btnOpenImage, btnSaveImage, btnUndo, btnRedo;

    // Drawing variables
    private Color currentColor = Color.BLACK;
    private int penSize = 5;
    
    private boolean isErasing = false;
    private boolean isStamping = false;
    
    private BufferedImage canvasImage;
    private BufferedImage stampImage; // Stamp tool image
    
    private ArrayList<Point> points = new ArrayList<>();
    
    //for undo/redo
    private Stack<ArrayList<Point>> undoStack = new Stack<>();
    private Stack<ArrayList<Point>> redoStack = new Stack<>();
    
    FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "jpeg");

    /**
     * Main method, kickstarts the application
     */
    public static void main(String[] args) {
    	
        EventQueue.invokeLater(() -> {
            try {
            	
                PaintApplication frame = new PaintApplication();
                frame.setVisible(true);
                
            } catch (Exception e) {
            	
                e.printStackTrace();
                
            }
        });
    }

    /**
     * The main JFrame containing everything in the application.
     * <br>
     * Created by: Eleazar Felix
     * <br>
     * Doc comment written by: Christian Miller 
     */
    public PaintApplication() {
    	
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 700);
        getContentPane().setLayout(new BorderLayout());

        // Create components
        createCanvasPanel();
        createControlPanel();

        // Add components to frame
        getContentPane().add(canvasPanel, BorderLayout.CENTER);
        getContentPane().add(controlPanel, BorderLayout.WEST);

        // Create top panel with buttons
        JPanel topPanel = new JPanel();
        btnOpenImage = new JButton("Open Image");
        btnSaveImage = new JButton("Save Image");
        btnUndo = new JButton("Undo");
        btnRedo = new JButton("Redo");

        topPanel.add(btnOpenImage);
        topPanel.add(btnSaveImage);
        topPanel.add(btnUndo);
        topPanel.add(btnRedo);
        getContentPane().add(topPanel, BorderLayout.NORTH);

        // Add listeners
        btnOpenImage.addActionListener(e -> openImage());
        btnSaveImage.addActionListener(e -> saveImage());
        btnUndo.addActionListener(e -> undo());
        btnRedo.addActionListener(e -> redo());
    }

    /**
     * Creates the panel containing the drawing canvas and adds listeners for mouse.
     */
    private void createCanvasPanel() {
    	
        canvasPanel = new JPanel() {
        	
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
        };
        canvasPanel.setBackground(Color.WHITE);
        
        
        canvasPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (isErasing) {
                    erase(e.getPoint());
                } else if (isStamping && stampImage != null) {
                    stamp(e.getPoint());
                } else {
                    points.add(e.getPoint());
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                saveStateForUndo();
            }
            
        });
        
        
        canvasPanel.addMouseMotionListener(new MouseAdapter() {
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

    private void createControlPanel() {
        controlPanel = new JPanel(new GridLayout(6, 1));
        controlPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        redSlider = createColorSlider("Red");
        greenSlider = createColorSlider("Green");
        blueSlider = createColorSlider("Blue");

        penSizeSlider = new JSlider(1, 20, 5);
        penSizeSlider.setBorder(BorderFactory.createTitledBorder("Pen Size"));
        penSizeSlider.addChangeListener(e -> penSize = penSizeSlider.getValue());
        controlPanel.add(penSizeSlider);

        btnEraseTool = new JButton("Erase Tool");
        btnEraseTool.addActionListener(e -> isErasing = !isErasing);
        controlPanel.add(btnEraseTool);

        btnStampTool = new JButton("Stamp Tool");
        btnStampTool.addActionListener(e -> isStamping = !isStamping);
        controlPanel.add(btnStampTool);
    }

    private JSlider createColorSlider(String label) {
    	
        JSlider slider = new JSlider(0, 255, 0);
        slider.setBorder(BorderFactory.createTitledBorder(label));
        slider.addChangeListener(e -> updateColor());
        
        controlPanel.add(slider);
        
        return slider;
        
    }

    /**
     * Reads the color sliders and sets <code>currentColor</code> the RGB color for the given values.
     * 
     * <br>
     * Created by: Eleazar Felix
     * <br>
     * Christian Miller cleaned up whitespace
     * <br>
     * Doc comment written by: Christian Miller 
     */
    private void updateColor() {
    	
        int red = redSlider.getValue();
        int green = greenSlider.getValue();
        int blue = blueSlider.getValue();
        
        currentColor = new Color(red, green, blue);
    }

    private void erase(Point point) {
    	
        points.removeIf(p -> p.distance(point) <= penSize);
        repaint();
        
    }

    private void stamp(Point point) {
    	
        if (stampImage != null) {
            Graphics g = canvasPanel.getGraphics();
            
            g.drawImage(stampImage, point.x - stampImage.getWidth() / 2, point.y - stampImage.getHeight() / 2, null);
            
            repaint();
        }
    }

    /**
     * Saves the state of the canvas for later undo
     * 
     * <br>
     * Created by: Eleazar Felix
     * <br>
     * Christian Miller cleaned up whitespace
     * <br>
     * Doc comment written by: Christian Miller 
     */
    private void saveStateForUndo() {
    	
        undoStack.push(new ArrayList<>(points));
        redoStack.clear();
        
    }

    /**
     * Undoes the last action taken in the canvas and adds the undone action to the redo stack
     * 
     * <br>
     * Created by: Eleazar Felix
     * <br>
     * Christian Miller cleaned up whitespace
     * <br>
     * Doc comment written by: Christian Miller 
     */
    private void undo() {
    	
        if (!undoStack.isEmpty()) {
        	
            redoStack.push(new ArrayList<>(points));
            points = undoStack.pop();
            
            repaint();
            
        }
        
    }

    /**
     * Undoes the last undo and removes the points from the redo stack
     * 
     * <br>
     * Created by: Eleazar Felix
     * <br>
     * Christian Miller cleaned up whitespace
     * <br>
     * Doc comment written by: Christian Miller 
     */
    private void redo() {
    	
        if (!redoStack.isEmpty()) {
        	
            undoStack.push(new ArrayList<>(points));
            points = redoStack.pop();
            
            repaint();
        }
        
    }

    /**
     * Prompts the user to select an image and pastes it into the top left of the canvas.
     * <br>
     * Created by: Eleazar Felix
     * <br>
     * Christian Miller cleaned up whitespace
     * <br>
     * Doc comment written by: Christian Miller
     */
    private void openImage() {
    	
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(fileFilter);
        
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            
        	try {
                canvasImage = ImageIO.read(fileChooser.getSelectedFile());
                repaint();
            }
        	
        	catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }//end of openImage

    /**
     * Prompts the user to select a save location and saves the canvas as an image file.
     * <br>
     * Created by: Eleazar Felix
     * <br>
     * Christian Miller cleaned up whitespace
     * <br>
     * Doc comment written by: Christian Miller
     */
    private void saveImage() {
    	
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(fileFilter);
        
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
        	
            try {
                ImageIO.write(canvasImage, "PNG", fileChooser.getSelectedFile());
            } 
            
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }//end of saveImage
    
//end of PaintApplication class  
}