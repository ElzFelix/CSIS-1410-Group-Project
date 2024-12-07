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
    private CanvasPanel canvasPanel;
    private ControlPanel controlPanel;
    
    //top panel buttons
    private JButton btnNewImage, btnOpenImage, btnSaveImage, btnUndo, btnRedo;
    
    //for undo/redo
    private static Stack<ArrayList<Undoable>> undoStack = new Stack<>();
    private static Stack<ArrayList<Undoable>> redoStack = new Stack<>();
   

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
    	setIconImage(Toolkit.getDefaultToolkit().getImage(PaintApplication.class.getResource("/gui_PaintApp/icon.png")));
    	setTitle("Paint Application - 1410 Final Project");
    	
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1280, 720); //made size bigger to accomodate color selector
        getContentPane().setLayout(new BorderLayout());

        // Create components
        canvasPanel = new CanvasPanel();
        controlPanel = new ControlPanel(canvasPanel);

        // Add components to frame
        getContentPane().add(canvasPanel, BorderLayout.CENTER);
        getContentPane().add(controlPanel, BorderLayout.WEST);

        createTopPanel();
        
        // Save the initial state for undo
        saveStateForUndo(canvasPanel.points);
    }

    /**
     * Creates the top panel with buttons
     * 
     * <br>
     * Created by: Eleazar Felix
     * 
     * Refactored into method by Christian Miller
     */
	private void createTopPanel()
	{
		JPanel topPanel = new JPanel();
        
		btnNewImage = new JButton("New Image");
        btnOpenImage = new JButton("Open Image");
        btnSaveImage = new JButton("Save Image");
        btnUndo = new JButton("Undo");
        btnRedo = new JButton("Redo");

        topPanel.add(btnNewImage);
        topPanel.add(btnOpenImage);
        topPanel.add(btnSaveImage);
        topPanel.add(btnUndo);
        topPanel.add(btnRedo);
        
        getContentPane().add(topPanel, BorderLayout.NORTH);
        
        // Add listeners
        btnNewImage.addActionListener(e -> canvasPanel.clearCanvas());
        btnOpenImage.addActionListener(e -> ImageHandler.openImage(canvasPanel));
        btnSaveImage.addActionListener(e -> ImageHandler.saveImage(canvasPanel));
        btnUndo.addActionListener(e -> undo());
        btnRedo.addActionListener(e -> redo());
	}

    //moved erase and stamp to CanvasPanel Class

    /**
     * Saves the state of the canvas for later undo
     * 
     * <br>
     * Created by: Eleazar Felix
     * <br>
     * Christian Miller cleaned up whitespace
     * <br>
     * Doc comment written by: Christian Miller 
     * 
     * made static by Christian Miller, in order to move a lot of functionality to CanvasPanel
     */
	public static void saveStateForUndo(ArrayList<Undoable> points) {
    	
		if (undoStack.isEmpty() || !undoStack.peek().equals(points)) {
	        undoStack.push(new ArrayList<>(points));
	    }
		
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
        	
    		 redoStack.push(new ArrayList<>(canvasPanel.points));
    	     canvasPanel.points = undoStack.pop();
    	     canvasPanel.repaint();
            
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
    		 
    		 undoStack.push(new ArrayList<>(canvasPanel.points));
    	     canvasPanel.points = redoStack.pop();
    	     canvasPanel.repaint();
        }
        
    }

    
//end of PaintApplication class  
}