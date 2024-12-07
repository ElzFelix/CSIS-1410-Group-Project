package gui_PaintApp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.FlowLayout;
import java.awt.BorderLayout;

/**
 * This class is the control panel on the left side of the screen, containing all of the tool buttons.
 */
public class ControlPanel extends JPanel {

	private static final long serialVersionUID = 3451685457751690119L; //idk what these are for
	private JSlider penSizeSlider, stampSizeSlider; 
    private JButton btnEraseTool, btnStampTool;
    private CanvasPanel canvasPanel; //this is so that the main canvasPanel can be effected by this controlPanel
    
    private JPanel penSliderPanel;
    private JPanel stampSliderPanel;
    
    private JColorChooser colorChooser;
    
    private JLabel penNumLabel;
    private JLabel stampNumLabel;
    
    private JPanel penPanel;
    private JPanel ButtonPanel;

    /**
     * Creates the control panel on the left side of the screen, containing the buttons, sliders and tools
     * @param canvasPanel
     */
    public ControlPanel(CanvasPanel canvasPanel) {
    	
    	this.setPreferredSize(new Dimension(600, getHeight()));
    	
    	this.canvasPanel = canvasPanel;
        
        
        this.penSliderPanel = createSliderPanel("Pen Size", penSizeSlider = new JSlider(1, 50, 5), e -> {updateSize();});
        this.stampSliderPanel = createSliderPanel("Stamp Size", stampSizeSlider = new JSlider(32, 1000, 100), e -> {updateStampSize();});
        
        this.penNumLabel = addNumberLabel(penSliderPanel, penSizeSlider);
        this.stampNumLabel = addNumberLabel(stampSliderPanel, stampSizeSlider);
        
        penPanel = new JPanel();
        penPanel.setLayout(new BorderLayout());
        
        //color swatch and chooser
        newColorChooser();
        
        
        JPanel sliders = new JPanel();
        sliders.setLayout(new GridLayout(0,1));
        
        sliders.add(penSliderPanel);
        sliders.add(stampSliderPanel);
        
        
        penPanel.add(sliders, BorderLayout.SOUTH);
        
        ButtonPanel = new JPanel();
        //erase tool
        createBtnErase(canvasPanel);

        //stamp tool
        createBtnStamp(canvasPanel);
        
        add(penPanel);
        add(ButtonPanel);
        
    }

	/**
	 * Adds the JColorChooser component to the control panel, replacing the previously used RGB sliders, which
	 * are now commented out.
	 * <br>
	 * Written by: Christian Miller
	 *
	 */
	public void newColorChooser()
	{
		colorChooser = new JColorChooser();
        colorChooser.setColor(Color.black);
        
        colorChooser.getSelectionModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent arg0) {
                updateColor();
            }
        });
        
        penPanel.add(colorChooser, BorderLayout.CENTER);
	}

	/**
	 * Creates the erase button, which toggles the eraser
	 * <br>
	 * Written by: Eleazar Felix
	 * @param canvasPanel
	 */
	private void createBtnErase(CanvasPanel canvasPanel)
	{
		btnEraseTool = new JButton("Erase Tool");
        
        btnEraseTool.addActionListener(e -> {
        	canvasPanel.setStamping(false);
        	canvasPanel.setErasing(!canvasPanel.isErasing());
        	});
        
        ButtonPanel.add(btnEraseTool);
	}

	/**
	 * Creates the stamp button, which toggles the stamp tool
	 * <br>
	 * Written by: Eleazar Felix
	 * <br>
	 * File selector by Christian Miller
	 *
	 * @param canvasPanel
	 */
	private void createBtnStamp(CanvasPanel canvasPanel)
	{
		btnStampTool = new JButton("Stamp Tool");
        
        btnStampTool.addActionListener(e -> {
        	canvasPanel.setErasing(false);
        	canvasPanel.setStamping(!canvasPanel.isStamping());
        	});
        
        ButtonPanel.add(btnStampTool);
        
        //file select
        JButton btnFileSelect = new JButton("Select Stamp image");
        btnFileSelect.addActionListener(e -> {
        	canvasPanel.setStampImage(ImageHandler.openStamp(canvasPanel.getStampSize()));
        	
        });
        
        ButtonPanel.add(btnFileSelect);
	}

	/**
	 * Generic method for creating a slider panel, used only to create the pen slider now.
	 * <br>
	 * Written by: Eleazar Felix
	 * @param label
	 * @param slider
	 * @param l
	 * @return
	 */
    private JPanel createSliderPanel(String label, JSlider slider, ChangeListener l) {
        JPanel panel = new JPanel();
        JLabel sliderLabel = new JLabel(label);
        
        panel.add(sliderLabel);
        
        slider.addChangeListener(l);
        
        panel.add(slider);
        
        return panel;
    }
    
    /**
     * Creates a number label for a slider panel
     * <br>
     * Written by: Christian Miller
     * @param panel
     * @param slider
     * @return
     */
    private JLabel addNumberLabel(JPanel panel, JSlider slider)
    {
    	//adds value of slider to the end -chris
        JLabel numberLabel = new JLabel(String.format("%03d", slider.getValue()));
        
        panel.add(numberLabel);
        
        return numberLabel;
    }

    /**
     * Reads the color chooser and sets <code>currentColor</code> the RGB color for the given values.
     * 
     * <br>
     * Created by: Eleazar Felix
     * <br>
     * Christian Miller cleaned up whitespace
     * <br>
     * Doc comment written by: Christian Miller 
     */
    private void updateColor() {
    	
    	canvasPanel.setCurrentColor(colorChooser.getColor());
    }
    
    /**
     * Updates the size of the pen
     * <br>
     * I forgot the author of this one, but it was probably Eleazar - Chris
     */
    private void updateSize() {
    	canvasPanel.setPenSize(penSizeSlider.getValue());
    	
    	penNumLabel.setText(String.format("%03d", penSizeSlider.getValue()));
    
    }
    
    /**
     * Reads the stamp slider and sets <code>currentColor</code> the RGB color for the given values.
     * 
     * <br>
     * Created by: Eleazar Felix
     * <br>
     * Christian Miller cleaned up whitespace
     * <br>
     * Doc comment written by: Christian Miller 
     */
    private void updateStampSize()
    {
    	canvasPanel.setStampSize(stampSizeSlider.getValue());
    	
    	stampNumLabel.setText(String.format("%04d", stampSizeSlider.getValue()));
    }
}